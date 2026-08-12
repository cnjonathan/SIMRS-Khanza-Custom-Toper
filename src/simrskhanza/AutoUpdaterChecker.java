package simrskhanza;

import fungsi.koneksiDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * AutoUpdaterChecker for SIMRS Khanza
 * Mengecek ketersediaan versi rilis baru dari API Backend RSUD RME.
 */
public class AutoUpdaterChecker {
    public static final String CURRENT_VERSION = "3.0.0";
    private static boolean isChecking = false;
    private static ScheduledExecutorService scheduler;

    /**
     * Memulai timer periodik untuk mengecek update di background secara berkala
     * @param intervalMinutes Interval pengecekan dalam menit (misal: 1 menit / 30 menit)
     */
    public static synchronized void startPeriodicCheck(int intervalMinutes) {
        if (scheduler != null && !scheduler.isShutdown()) return;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            checkUpdateAsync(false);
        }, intervalMinutes, intervalMinutes, TimeUnit.MINUTES);
    }

    public static String getClientVersion() {
        try {
            File vFile = new File("version.txt");
            if (vFile.exists()) {
                try (BufferedReader br = new BufferedReader(new java.io.FileReader(vFile))) {
                    String v = br.readLine();
                    if (v != null && !v.trim().isEmpty()) {
                        return v.trim();
                    }
                }
            }
        } catch (Exception e) {}
        return CURRENT_VERSION;
    }

    private static String getRMEBaseUrl() {
        try {
            File xmlFile = new File("setting/database.xml");
            if (xmlFile.exists()) {
                Properties prop = new Properties();
                try (InputStream is = new FileInputStream(xmlFile)) {
                    prop.loadFromXML(is);
                    String urlRme = prop.getProperty("URLRSUDRME");
                    if (urlRme != null && !urlRme.trim().isEmpty()) {
                        urlRme = urlRme.trim();
                        if (urlRme.endsWith("/")) {
                            urlRme = urlRme.substring(0, urlRme.length() - 1);
                        }
                        return urlRme;
                    }
                }
            }
        } catch (Exception e) {}
        
        String hostRME = koneksiDB.HOSTHYBRIDWEB();
        String portRME = "";
        try {
            portRME = koneksiDB.PORTWEB();
        } catch (Exception ex) {}

        return "http://" + hostRME + (portRME.isEmpty() || portRME.equals("80") ? "" : ":" + portRME);
    }

    private static HttpURLConnection connectWithFallback(String primaryUrlStr) throws Exception {
        List<String> candidates = new ArrayList<>();
        candidates.add(primaryUrlStr);

        if (primaryUrlStr.contains("localhost")) {
            candidates.add(primaryUrlStr.replace("localhost", "127.0.0.1"));
            candidates.add(primaryUrlStr.replace("localhost", "[::1]"));
        } else if (primaryUrlStr.contains("127.0.0.1")) {
            candidates.add(primaryUrlStr.replace("127.0.0.1", "[::1]"));
            candidates.add(primaryUrlStr.replace("127.0.0.1", "localhost"));
        }

        Exception lastException = null;
        for (String urlStr : candidates) {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                
                int code = conn.getResponseCode();
                if (code == 200) {
                    return conn;
                }
            } catch (Exception e) {
                lastException = e;
            }
        }
        if (lastException != null) throw lastException;
        throw new Exception("Tidak dapat terhubung ke server " + primaryUrlStr);
    }

    /**
     * Mengecek update secara asynchronous (background thread)
     * @param showNoUpdateMsg true jika ingin menampilkan pesan saat sudah versi terbaru (untuk tombol manual check)
     */
    public static void checkUpdateAsync(boolean showNoUpdateMsg) {
        if (isChecking) return;
        isChecking = true;

        new Thread(() -> {
            try {
                String baseUrl = getRMEBaseUrl();
                String currentVer = getClientVersion();

                String hostName = "PC-Client";
                String ipAddr = "";
                try {
                    hostName = java.net.InetAddress.getLocalHost().getHostName();
                    ipAddr = java.net.InetAddress.getLocalHost().getHostAddress();
                } catch (Exception e) {}

                String apiUrl = baseUrl + "/api/updater/check?current_version=" + currentVer
                              + "&hostname=" + java.net.URLEncoder.encode(hostName, "UTF-8")
                              + "&ip=" + java.net.URLEncoder.encode(ipAddr, "UTF-8");

                HttpURLConnection conn = connectWithFallback(apiUrl);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String json = response.toString();
                String latestVersion = parseJsonVal(json, "latest_version");
                if (!latestVersion.isEmpty()) {
                    simrskhanza.frmUtama.setVersionText(latestVersion);
                }

                boolean hasUpdate = parseJsonBool(json, "has_update");

                if (hasUpdate) {
                    String changelog = parseJsonVal(json, "changelog");
                    boolean isMandatory = parseJsonBool(json, "is_mandatory");

                    SwingUtilities.invokeLater(() -> promptUpdate(latestVersion, changelog, isMandatory));
                } else if (showNoUpdateMsg) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null,
                            "Aplikasi SIMRS Khanza Anda sudah menggunakan versi terbaru (v" + (latestVersion.isEmpty() ? currentVer : latestVersion) + ").",
                            "Auto Updater", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            } catch (Exception e) {
                System.out.println("AutoUpdater Warning: Tidak dapat terhubung ke server update (" + e.getMessage() + ")");
                sendErrorToRME("AutoUpdater Check Failed: " + e.getMessage(), e);
                if (showNoUpdateMsg) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null,
                            "Gagal terhubung ke server Auto Updater RSUD RME!\nError: " + e.getMessage(),
                            "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
                    });
                }
            } finally {
                isChecking = false;
            }
        }).start();
    }

    private static void sendErrorToRME(String errorMsg, Throwable t) {
        new Thread(() -> {
            try {
                String baseUrl = getRMEBaseUrl();
                String apiUrl = baseUrl + "/api/updater/log_error";

                String hostName = "PC-Client";
                try {
                    hostName = java.net.InetAddress.getLocalHost().getHostName();
                } catch (Exception e) {}

                String stackTrace = "";
                if (t != null) {
                    java.io.StringWriter sw = new java.io.StringWriter();
                    java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                    t.printStackTrace(pw);
                    stackTrace = sw.toString();
                }

                String jsonPayload = String.format(
                    "{\"hostname\":\"%s\",\"version\":\"%s\",\"error\":\"%s\",\"stack_trace\":\"%s\"}",
                    hostName, getClientVersion(),
                    errorMsg.replace("\"", "\\\"").replace("\r", "").replace("\n", " "),
                    stackTrace.replace("\"", "\\\"").replace("\r", "").replace("\n", "\\n")
                );

                HttpURLConnection conn = connectWithFallback(apiUrl);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                try (java.io.OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes("UTF-8");
                    os.write(input, 0, input.length);
                }

                conn.getResponseCode();
            } catch (Exception ex) {
                System.out.println("Gagal mengirim log error: " + ex.getMessage());
            }
        }).start();
    }

    private static void promptUpdate(String latestVersion, String changelog, boolean isMandatory) {
        String msg = "Tersedia Versi Terbaru SIMRS Khanza!\n\n"
                   + "Versi Anda Saat Ini   : v" + getClientVersion() + "\n"
                   + "Versi Terbaru Server : v" + latestVersion + "\n\n"
                   + "Catatan Perubahan (Changelog):\n" + (changelog.isEmpty() ? "-" : changelog) + "\n\n"
                   + "Apakah Anda ingin memperbarui aplikasi sekarang?";

        if (isMandatory) {
            JOptionPane.showMessageDialog(null,
                msg + "\n\n(Perhatian: Update ini wajib dilakukan untuk melanjutkan penggunaan aplikasi)",
                "Update Wajib SIMRS Khanza", JOptionPane.WARNING_MESSAGE);
            launchLauncherAndExit();
        } else {
            int option = JOptionPane.showConfirmDialog(null, msg, "Pembaruan SIMRS Khanza Tersedia",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                launchLauncherAndExit();
            }
        }
    }

    private static void launchLauncherAndExit() {
        try {
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe";
            if (!new File(javaBin).exists()) {
                javaBin = "javaw";
            }
            new ProcessBuilder(javaBin, "-Xss2m", "-Xms32m", "-Xmx1024m", "-jar", "KhanzaLauncher.jar").start();
            System.exit(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Gagal menjalankan KhanzaLauncher.jar!\nPastikan file KhanzaLauncher.jar berada di folder aplikasi SIMRS Khanza.\nError: " + e.getMessage(),
                "Error Launcher", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String parseJsonVal(String json, String key) {
        try {
            int keyIdx = json.indexOf("\"" + key + "\"");
            if (keyIdx != -1) {
                int colonIdx = json.indexOf(":", keyIdx);
                if (colonIdx != -1) {
                    int quoteStart = json.indexOf("\"", colonIdx);
                    if (quoteStart != -1) {
                        int quoteEnd = json.indexOf("\"", quoteStart + 1);
                        if (quoteEnd != -1) {
                            return json.substring(quoteStart + 1, quoteEnd).replace("\\n", "\n").replace("\\r", "").trim();
                        }
                    }
                }
            }
        } catch (Exception e) {}
        return "";
    }

    private static boolean parseJsonBool(String json, String key) {
        try {
            int keyIdx = json.indexOf("\"" + key + "\"");
            if (keyIdx != -1) {
                int colonIdx = json.indexOf(":", keyIdx);
                if (colonIdx != -1) {
                    int endIdx = json.indexOf(",", colonIdx);
                    if (endIdx == -1) endIdx = json.indexOf("}", colonIdx);
                    if (endIdx != -1) {
                        String valStr = json.substring(colonIdx + 1, endIdx).trim();
                        return valStr.equalsIgnoreCase("true");
                    }
                }
            }
        } catch (Exception e) {}
        return false;
    }
}

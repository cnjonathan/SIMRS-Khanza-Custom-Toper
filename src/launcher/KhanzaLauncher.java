package launcher;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * KhanzaLauncher - Professional GUI Auto Updater
 * RSUD Kartini Karanganyar (Standalone Module)
 */
public class KhanzaLauncher extends JFrame {

    private JLabel lblStatus;
    private JLabel lblSubStatus;
    private JProgressBar progressBar;
    private JLabel lblLogo;

    static {
        disableSSLVerification();
    }

    private static void disableSSLVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            System.out.println("Warning: Gagal inisialisasi SSL bypass: " + e.getMessage());
        }
    }

    public KhanzaLauncher() {
        initUI();
        startUpdateProcess();
    }

    private void initUI() {
        setTitle("SIMRS Khanza Auto Updater");
        setUndecorated(true);
        setSize(520, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(13, 71, 161), 2));
        mainPanel.setBackground(Color.WHITE);

        // Header Panel (Dark Navy Blue Gradient)
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(13, 71, 161), getWidth(), 0, new Color(25, 118, 210));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(520, 85));
        headerPanel.setBorder(new EmptyBorder(12, 15, 12, 15));

        // Logo
        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(60, 60));
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        loadLogoAsync();
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Title Texts
        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1, 0, 2));
        titleTextPanel.setOpaque(false);

        JLabel lblHospital = new JLabel("RSUD KARTINI KARANGANYAR");
        lblHospital.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHospital.setForeground(Color.WHITE);

        JLabel lblAppName = new JLabel("SIMRS KHANZA - AUTO UPDATER");
        lblAppName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAppName.setForeground(new Color(227, 242, 253));

        titleTextPanel.add(lblHospital);
        titleTextPanel.add(lblAppName);
        headerPanel.add(titleTextPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center Content Panel
        JPanel bodyPanel = new JPanel(new GridLayout(4, 1, 0, 6));
        bodyPanel.setBackground(Color.WHITE);
        bodyPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        lblStatus = new JLabel("Menghubungkan ke server update...");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setForeground(new Color(33, 33, 33));

        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(470, 24));
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 11));
        progressBar.setForeground(new Color(30, 136, 229));
        progressBar.setBackground(new Color(238, 238, 238));

        lblSubStatus = new JLabel("Mohon tunggu sebentar...");
        lblSubStatus.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblSubStatus.setForeground(new Color(117, 117, 117));

        JLabel lblFooter = new JLabel("© IT RSUD Kartini Karanganyar", SwingConstants.RIGHT);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblFooter.setForeground(new Color(158, 158, 158));

        bodyPanel.add(lblStatus);
        bodyPanel.add(progressBar);
        bodyPanel.add(lblSubStatus);
        bodyPanel.add(lblFooter);

        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private File getAppDir() {
        try {
            File jarFile = new File(KhanzaLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (jarFile.isFile()) {
                return jarFile.getParentFile();
            }
            if (jarFile.isDirectory()) {
                return jarFile;
            }
        } catch (Exception e) {}
        return new File(".");
    }

    private String getLocalVersion() {
        try {
            File vFile = new File(getAppDir(), "version.txt");
            if (vFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(vFile))) {
                    String v = br.readLine();
                    if (v != null && !v.trim().isEmpty()) {
                        return v.trim();
                    }
                }
            }
        } catch (Exception e) {}
        return "0.0.0";
    }

    private void saveLocalVersion(String ver) {
        if (ver == null || ver.trim().isEmpty()) return;
        try {
            File vFile = new File(getAppDir(), "version.txt");
            try (FileWriter fw = new FileWriter(vFile)) {
                fw.write(ver.trim());
            }
        } catch (Exception e) {}
    }

    private String getClientHostName() {
        try {
            return java.net.InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {}
        return "PC-Client";
    }

    private String getClientIpAddress() {
        try {
            return java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {}
        return "127.0.0.1";
    }

    private String getRMEBaseUrl() {
        try {
            File xmlFile = new File(getAppDir(), "setting/database.xml");
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
        return "http://localhost:8084";
    }

    private void loadLogoAsync() {
        new Thread(() -> {
            try {
                File appDir = getAppDir();
                File localLogo = new File(appDir, "icon.png");
                if (!localLogo.exists()) {
                    localLogo = new File(appDir, "picture/logo.png");
                }
                if (!localLogo.exists()) {
                    localLogo = new File(appDir, "rsudkartinikaranganyar.jpg");
                }

                // Jika belum ada di lokal, otomatis tarik icon.png dari RSUD RME
                if (!localLogo.exists() || localLogo.length() == 0) {
                    try {
                        String baseUrl = getRMEBaseUrl();
                        String iconUrlStr = baseUrl + "/icon.png";
                        downloadFileSimple(iconUrlStr, new File(appDir, "icon.png").getAbsolutePath());
                        localLogo = new File(appDir, "icon.png");
                    } catch (Exception ex) {
                        System.out.println("AutoUpdater Info: Menggunakan logo default (" + ex.getMessage() + ")");
                    }
                }

                Image img = null;
                if (localLogo.exists() && localLogo.length() > 0) {
                    img = ImageIO.read(localLogo);
                }

                if (img != null) {
                    final Image finalImg = img;
                    Image scaled = img.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
                    SwingUtilities.invokeLater(() -> {
                        lblLogo.setIcon(new ImageIcon(scaled));
                        setIconImage(finalImg);
                    });
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    lblLogo.setText("RSUD");
                    lblLogo.setForeground(Color.WHITE);
                    lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 18));
                });
            }
        }).start();
    }

    private HttpURLConnection connectWithFallback(String primaryUrlStr) throws Exception {
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
                if (conn instanceof HttpsURLConnection) {
                    try {
                        SSLContext sc = SSLContext.getInstance("TLS");
                        sc.init(null, new TrustManager[]{
                            new X509TrustManager() {
                                public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                            }
                        }, new SecureRandom());
                        ((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
                        ((HttpsURLConnection) conn).setHostnameVerifier((hostname, session) -> true);
                    } catch (Exception ignored) {}
                }
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(12000);
                
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

    private String getFileMD5(File file) {
        if (file == null || !file.exists() || !file.isFile()) return "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            try (InputStream is = new FileInputStream(file)) {
                byte[] buffer = new byte[8192];
                int read;
                while ((read = is.read(buffer)) > 0) {
                    digest.update(buffer, 0, read);
                }
            }
            byte[] md5sum = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : md5sum) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private void logDebug(String msg) {
        try {
            File appDir = getAppDir();
            File logFile = new File(appDir, "launcher_debug.log");
            try (FileWriter fw = new FileWriter(logFile, true)) {
                fw.write(new java.util.Date() + " - " + msg + "\r\n");
            }
        } catch (Exception e) {}
    }

    private void startUpdateProcess() {
        new Thread(() -> {
            try {
                updateStatus("Memeriksa informasi rilis terbaru...", 5);
                
                File appDir = getAppDir();
                logDebug("=== LAUNCHER STARTING ===");
                logDebug("AppDir: " + appDir.getAbsolutePath());

                String localVer = getLocalVersion();
                File jarFile = new File(appDir, "SIMRSKhanza.jar");

                String hostName = getClientHostName();
                String ipAddr = getClientIpAddress();

                String baseUrl = getRMEBaseUrl();
                String apiUrl = baseUrl + "/api/updater/check?current_version=" + localVer
                              + "&hostname=" + java.net.URLEncoder.encode(hostName, "UTF-8")
                              + "&ip=" + java.net.URLEncoder.encode(ipAddr, "UTF-8");
                logDebug("RME BaseUrl: " + baseUrl + " | LocalVer: " + localVer + " | Host: " + hostName + " | IP: " + ipAddr);

                HttpURLConnection conn = connectWithFallback(apiUrl);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String json = response.toString();
                logDebug("API Response: " + json);

                boolean hasUpdate = parseJsonBool(json, "has_update");
                String latestVersion = parseJsonVal(json, "latest_version");

                // Step 1: Check & Download Libs (jika ada library .jar baru / beda hash)
                updateStatus("Memeriksa pustaka library (.jar)...", 10);
                boolean libsUpdated = downloadLibsIfAny(json, baseUrl);

                // Step 2: Check & Download Report Files (jika ada yang baru / beda hash)
                updateStatus("Memeriksa laporan (.jasper)...", 20);
                boolean reportsUpdated = downloadReportsIfAny(json, baseUrl);

                // Step 3: Check & Download Setting Files (seperti database.xml)
                updateStatus("Memeriksa variabel setting (.xml)...", 35);
                boolean settingsUpdated = downloadSettingsIfAny(json, baseUrl);

                // Step 4: Check & Download SIMRSKhanza.jar (jika ada pembaruan versi / beda hash)
                String serverJarHash = parseJsonVal(json, "jar_hash");
                String localJarHash = getFileMD5(jarFile);

                boolean jarNeedsUpdate = !jarFile.exists() || jarFile.length() == 0 ||
                    (hasUpdate && !serverJarHash.isEmpty() && !localJarHash.equalsIgnoreCase(serverJarHash));

                if (jarNeedsUpdate) {
                    String jarUrlStr = parseJsonVal(json, "jar_url");
                    if (jarUrlStr.isEmpty()) {
                        jarUrlStr = baseUrl + "/api/updater/download/jar";
                    }
                    long jarSize = parseJsonLong(json, "jar_size");

                    updateStatus("Mengunduh pembaruan SIMRSKhanza.jar (v" + latestVersion + ")...", 45);
                    File tmpJar = new File(appDir, "SIMRSKhanza.jar.tmp");
                    downloadFileWithFallback(jarUrlStr, tmpJar.getAbsolutePath(), jarSize, 45, 95);

                    if (tmpJar.exists() && tmpJar.length() > 0) {
                        updateSubStatus("Memperbarui file executable SIMRSKhanza.jar...");
                        Files.move(tmpJar.toPath(), jarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }

                String activeVer = latestVersion.isEmpty() ? localVer : latestVersion;
                saveLocalVersion(activeVer);

                updateStatus("SIMRS Khanza v" + activeVer + " Siap Digunakan", 100);
                launchApplication();

            } catch (Exception e) {
                logDebug("EXCEPTION IN STARTUPDATE: " + e.toString());
                for (StackTraceElement ste : e.getStackTrace()) {
                    logDebug("  at " + ste.toString());
                }
                SwingUtilities.invokeLater(() -> {
                    int choice = JOptionPane.showConfirmDialog(this,
                        "Gagal melakukan pembaruan otomatis: " + e.getMessage() + "\n\nApakah Anda tetap ingin menjalankan SIMRS Khanza versi lokal?",
                        "Kesalahan Auto Updater", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        launchApplication();
                    } else {
                        System.exit(0);
                    }
                });
            }
        }).start();
    }

    private void launchApplication() {
        try {
            updateStatus("Membuka SIMRS Khanza...", 100);
            updateSubStatus("Menyiapkan aplikasi (Mohon tunggu)...");

            File appDir = getAppDir();
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe";
            if (!new File(javaBin).exists()) {
                javaBin = "javaw";
            }

            File targetJar = new File(appDir, "SIMRSKhanza.jar");
            ProcessBuilder pb = new ProcessBuilder(javaBin, "-Xss2m", "-Xms32m", "-Xmx1024m", "-jar", targetJar.getAbsolutePath());
            pb.directory(appDir);
            Process p = pb.start();

            // Tahan tampilan Launcher selama 3.5 detik agar memberikan jeda transisi halus sampai GUI SIMRS Khanza muncul
            for (int i = 1; i <= 35; i++) {
                Thread.sleep(100);
                if (!p.isAlive()) {
                    int exitCode = p.exitValue();
                    if (exitCode != 0) {
                        throw new Exception("Proses SIMRSKhanza.jar terhenti dengan exit code: " + exitCode);
                    }
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Gagal menjalankan SIMRS Khanza: " + ex.getMessage(),
                    "Error Executable", JOptionPane.ERROR_MESSAGE);
            });
        }

        // Tutup launcher setelah SIMRS Khanza berhasil dimuat
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            dispose();
            System.exit(0);
        });
    }

    private void downloadFileWithFallback(String fileUrl, String destinationPath, long totalBytes, int progressStart, int progressEnd) throws Exception {
        HttpURLConnection conn = connectWithFallback(fileUrl);

        if (totalBytes <= 0) {
            totalBytes = conn.getContentLengthLong();
        }

        InputStream inputStream = conn.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(destinationPath);

        byte[] buffer = new byte[16384];
        int bytesRead;
        long totalBytesRead = 0;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;

            if (totalBytes > 0) {
                double percent = (double) totalBytesRead / totalBytes;
                int currentProgress = progressStart + (int) (percent * (progressEnd - progressStart));
                String downloadedMb = String.format("%.1f", (double) totalBytesRead / (1024 * 1024));
                String totalMb = String.format("%.1f", (double) totalBytes / (1024 * 1024));

                updateStatus("Mengunduh SIMRSKhanza.jar (" + downloadedMb + " MB / " + totalMb + " MB)...", currentProgress);
            }
        }

        outputStream.close();
        inputStream.close();
    }

    private int findMatchingBracket(String json, int startBracket) {
        boolean inString = false;
        boolean escape = false;
        int depth = 0;
        for (int i = startBracket; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escape) {
                escape = false;
                continue;
            }
            if (c == '\\' && inString) {
                escape = true;
                continue;
            }
            if (c == '"') {
                inString = !inString;
                continue;
            }
            if (!inString) {
                if (c == '[') depth++;
                else if (c == ']') {
                    depth--;
                    if (depth == 0) return i;
                }
            }
        }
        return -1;
    }

    private boolean downloadLibsIfAny(String json, String baseUrl) {
        boolean downloadedAny = false;
        try {
            int libsIdx = json.indexOf("\"libs\"");
            if (libsIdx != -1) {
                int startBracket = json.indexOf("[", libsIdx);
                if (startBracket != -1) {
                    int endBracket = findMatchingBracket(json, startBracket);
                    if (endBracket != -1) {
                        String libsJson = json.substring(startBracket, endBracket + 1);
                        File appDir = getAppDir();
                        File libDir = new File(appDir, "lib");
                        if (!libDir.exists()) {
                            libDir.mkdirs();
                        }

                        int cur = 0;
                        while ((cur = libsJson.indexOf("\"filename\"", cur)) != -1) {
                            int colonIdx = libsJson.indexOf(":", cur);
                            if (colonIdx == -1) break;
                            int startQuote = libsJson.indexOf("\"", colonIdx);
                            if (startQuote == -1) break;
                            int endQuote = libsJson.indexOf("\"", startQuote + 1);
                            if (endQuote == -1) break;

                            String filename = libsJson.substring(startQuote + 1, endQuote).trim();
                            cur = endQuote + 1;

                            int nextFn = libsJson.indexOf("\"filename\"", cur);
                            int objEnd = nextFn != -1 ? nextFn : libsJson.length();

                            String serverHash = "";
                            int hashIdx = libsJson.indexOf("\"hash\"", endQuote);
                            if (hashIdx != -1 && hashIdx < objEnd) {
                                int hColon = libsJson.indexOf(":", hashIdx);
                                if (hColon != -1 && hColon < objEnd) {
                                    int hStart = libsJson.indexOf("\"", hColon);
                                    if (hStart != -1 && hStart < objEnd) {
                                        int hEnd = libsJson.indexOf("\"", hStart + 1);
                                        if (hEnd != -1 && hEnd <= objEnd) {
                                            serverHash = libsJson.substring(hStart + 1, hEnd).trim();
                                        }
                                    }
                                }
                            }
                            logDebug("[LibHashTrace] file=" + filename + " hashIdx=" + hashIdx + " serverHash=" + serverHash);

                            File localLib = new File(libDir, filename);
                            String localHash = getFileMD5(localLib);

                            boolean needsDownload = !localLib.exists() || localLib.length() == 0 ||
                                (!serverHash.isEmpty() && !localHash.equalsIgnoreCase(serverHash));

                            logDebug("[LibCheck] File: " + filename + " | Local MD5: " + localHash + " | Server MD5: " + serverHash + " | NeedsDownload: " + needsDownload);

                            if (needsDownload) {
                                String downloadUrl = baseUrl + "/api/updater/download/lib/" + filename;
                                updateSubStatus("Mengunduh library: " + filename);
                                try {
                                    File tmpFile = new File(libDir, filename + ".tmp");
                                    downloadFileSimple(downloadUrl, tmpFile.getAbsolutePath());
                                    if (tmpFile.exists() && tmpFile.length() > 0) {
                                        Files.move(tmpFile.toPath(), localLib.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                        downloadedAny = true;
                                        logDebug("[LibCheck] SUCCESS updated lib: " + filename + " to " + localLib.getAbsolutePath());
                                    }
                                } catch (Exception ex) {
                                    logDebug("[LibCheck] ERROR download lib " + filename + ": " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logDebug("Error parsing libs: " + e.getMessage());
            e.printStackTrace();
        }
        return downloadedAny;
    }

    private boolean downloadReportsIfAny(String json, String baseUrl) {
        boolean downloadedAny = false;
        try {
            int reportsIdx = json.indexOf("\"reports\"");
            if (reportsIdx != -1) {
                int startBracket = json.indexOf("[", reportsIdx);
                if (startBracket != -1) {
                    int endBracket = findMatchingBracket(json, startBracket);
                    if (endBracket != -1) {
                        String reportsJson = json.substring(startBracket, endBracket + 1);
                        File appDir = getAppDir();
                        File reportDir = new File(appDir, "report");
                        if (!reportDir.exists()) {
                            reportDir.mkdirs();
                        }

                        int cur = 0;
                        while ((cur = reportsJson.indexOf("\"filename\"", cur)) != -1) {
                            int colonIdx = reportsJson.indexOf(":", cur);
                            if (colonIdx == -1) break;
                            int startQuote = reportsJson.indexOf("\"", colonIdx);
                            if (startQuote == -1) break;
                            int endQuote = reportsJson.indexOf("\"", startQuote + 1);
                            if (endQuote == -1) break;

                            String filename = reportsJson.substring(startQuote + 1, endQuote).trim();
                            cur = endQuote + 1;

                            int nextFn = reportsJson.indexOf("\"filename\"", cur);
                            int objEnd = nextFn != -1 ? nextFn : reportsJson.length();

                            String serverHash = "";
                            int hashIdx = reportsJson.indexOf("\"hash\"", endQuote);
                            int hColon = -1, hStart = -1, hEnd = -1;
                            if (hashIdx != -1 && hashIdx < objEnd) {
                                hColon = reportsJson.indexOf(":", hashIdx);
                                if (hColon != -1 && hColon < objEnd) {
                                    hStart = reportsJson.indexOf("\"", hColon);
                                    if (hStart != -1 && hStart < objEnd) {
                                        hEnd = reportsJson.indexOf("\"", hStart + 1);
                                        if (hEnd != -1 && hEnd <= objEnd) {
                                            serverHash = reportsJson.substring(hStart + 1, hEnd).trim();
                                        }
                                    }
                                }
                            }
                            logDebug("[HashTrace] file=" + filename + " hashIdx=" + hashIdx + " serverHash=" + serverHash);

                            File localReport = new File(reportDir, filename);
                            String localHash = getFileMD5(localReport);

                            boolean needsDownload = !localReport.exists() || localReport.length() == 0 ||
                                (!serverHash.isEmpty() && !localHash.equalsIgnoreCase(serverHash));

                            logDebug("[ReportCheck] File: " + filename + " | Local MD5: " + localHash + " | Server MD5: " + serverHash + " | NeedsDownload: " + needsDownload);

                            if (needsDownload) {
                                String downloadUrl = baseUrl + "/api/updater/download/report/" + filename;
                                updateSubStatus("Mengunduh report: " + filename);
                                try {
                                    downloadFileSimple(downloadUrl, localReport.getAbsolutePath());
                                    downloadedAny = true;
                                    logDebug("[ReportCheck] SUCCESS updated report: " + filename + " to " + localReport.getAbsolutePath());
                                } catch (Exception ex) {
                                    logDebug("[ReportCheck] ERROR download report " + filename + ": " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logDebug("Error parsing reports: " + e.getMessage());
            e.printStackTrace();
        }
        return downloadedAny;
    }

    private boolean downloadSettingsIfAny(String json, String baseUrl) {
        boolean downloadedAny = false;
        try {
            int settingsIdx = json.indexOf("\"settings\"");
            if (settingsIdx != -1) {
                int startBracket = json.indexOf("[", settingsIdx);
                if (startBracket != -1) {
                    int endBracket = findMatchingBracket(json, startBracket);
                    if (endBracket != -1) {
                        String settingsJson = json.substring(startBracket, endBracket + 1);
                        File appDir = getAppDir();
                        File settingDir = new File(appDir, "setting");
                        if (!settingDir.exists()) {
                            settingDir.mkdirs();
                        }

                        int cur = 0;
                        while ((cur = settingsJson.indexOf("\"filename\"", cur)) != -1) {
                            int colonIdx = settingsJson.indexOf(":", cur);
                            if (colonIdx == -1) break;
                            int startQuote = settingsJson.indexOf("\"", colonIdx);
                            if (startQuote == -1) break;
                            int endQuote = settingsJson.indexOf("\"", startQuote + 1);
                            if (endQuote == -1) break;

                            String filename = settingsJson.substring(startQuote + 1, endQuote).trim();
                            cur = endQuote + 1;

                            int nextFn = settingsJson.indexOf("\"filename\"", cur);
                            int objEnd = nextFn != -1 ? nextFn : settingsJson.length();

                            String serverHash = "";
                            int hashIdx = settingsJson.indexOf("\"hash\"", endQuote);
                            if (hashIdx != -1 && hashIdx < objEnd) {
                                int hColon = settingsJson.indexOf(":", hashIdx);
                                if (hColon != -1 && hColon < objEnd) {
                                    int hStart = settingsJson.indexOf("\"", hColon);
                                    if (hStart != -1 && hStart < objEnd) {
                                        int hEnd = settingsJson.indexOf("\"", hStart + 1);
                                        if (hEnd != -1 && hEnd <= objEnd) {
                                            serverHash = settingsJson.substring(hStart + 1, hEnd).trim();
                                        }
                                    }
                                }
                            }

                            File localSetting = new File(settingDir, filename);
                            String localHash = getFileMD5(localSetting);

                            boolean needsDownload = !localSetting.exists() || localSetting.length() == 0 ||
                                (!serverHash.isEmpty() && !localHash.equalsIgnoreCase(serverHash));

                            logDebug("[SettingCheck] File: " + filename + " | Local MD5: " + localHash + " | Server MD5: " + serverHash + " | NeedsDownload: " + needsDownload);

                            if (needsDownload) {
                                String downloadUrl = baseUrl + "/api/updater/download/setting/" + filename
                                                   + "?hostname=" + java.net.URLEncoder.encode(getClientHostName(), "UTF-8")
                                                   + "&ip=" + java.net.URLEncoder.encode(getClientIpAddress(), "UTF-8");
                                updateSubStatus("Mengunduh setting: " + filename);
                                
                                try {
                                    File tmpFile = new File(settingDir, filename + ".tmp");
                                    downloadFileSimple(downloadUrl, tmpFile.getAbsolutePath());

                                    File targetFile = new File(settingDir, filename);

                                    if (tmpFile.exists()) {
                                        if (filename.equalsIgnoreCase("database.xml") && targetFile.exists()) {
                                            mergeXmlProperties(targetFile, tmpFile);
                                            tmpFile.delete();
                                        } else {
                                            Files.move(tmpFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                        }
                                        downloadedAny = true;
                                        logDebug("[SettingCheck] SUCCESS updated setting: " + filename);
                                    }
                                } catch (Exception ex) {
                                    logDebug("[SettingCheck] ERROR download setting " + filename + ": " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logDebug("Error parsing settings: " + e.getMessage());
            e.printStackTrace();
        }
        return downloadedAny;
    }

    private void mergeXmlProperties(File targetLocalFile, File newServerFile) {
        try {
            Properties localProps = new Properties();
            if (targetLocalFile.exists() && targetLocalFile.length() > 0) {
                try (InputStream is = new FileInputStream(targetLocalFile)) {
                    localProps.loadFromXML(is);
                }
            }

            Properties serverProps = new Properties();
            try (InputStream is = new FileInputStream(newServerFile)) {
                serverProps.loadFromXML(is);
            }

            boolean modified = false;
            int changeCount = 0;

            for (String key : serverProps.stringPropertyNames()) {
                if (!localProps.containsKey(key)) {
                    localProps.setProperty(key, serverProps.getProperty(key));
                    modified = true;
                    changeCount++;
                    logDebug("[MergeXML] Added new key: " + key + " = " + serverProps.getProperty(key));
                } else if (!key.equalsIgnoreCase("HOST") && 
                           !key.equalsIgnoreCase("DATABASE") && 
                           !key.equalsIgnoreCase("PORT") && 
                           !key.equalsIgnoreCase("USER") && 
                           !key.equalsIgnoreCase("PAS") && 
                           !key.equalsIgnoreCase("PORTWEB") && 
                           !key.equalsIgnoreCase("HOSTHYBRIDWEB") && 
                           !key.equalsIgnoreCase("URLRSUDRME")) {
                    String serverVal = serverProps.getProperty(key);
                    String localVal = localProps.getProperty(key);
                    if (serverVal != null && !serverVal.equals(localVal)) {
                        localProps.setProperty(key, serverVal);
                        modified = true;
                        changeCount++;
                        logDebug("[MergeXML] Updated key: " + key + " ('" + localVal + "' -> '" + serverVal + "')");
                    }
                }
            }

            if (modified) {
                try (OutputStream os = new FileOutputStream(targetLocalFile)) {
                    localProps.storeToXML(os, "SIMRS Khanza Database Properties - Auto Updated", "UTF-8");
                }
                updateSubStatus("Variabel setting/database.xml berhasil diperbarui (" + changeCount + " variabel)!");
                logDebug("[MergeXML] SUCCESS wrote " + changeCount + " changes to " + targetLocalFile.getAbsolutePath());
            } else {
                logDebug("[MergeXML] No property changes needed.");
            }
        } catch (Exception e) {
            logDebug("[MergeXML] ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void downloadFileSimple(String fileUrl, String targetPath) throws Exception {
        HttpURLConnection conn = connectWithFallback(fileUrl);

        try (InputStream in = conn.getInputStream();
             FileOutputStream out = new FileOutputStream(targetPath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private void updateStatus(String statusText, int progressValue) {
        SwingUtilities.invokeLater(() -> {
            lblStatus.setText(statusText);
            progressBar.setValue(progressValue);
            progressBar.setString(progressValue + "%");
        });
    }

    private void updateSubStatus(String subStatusText) {
        SwingUtilities.invokeLater(() -> lblSubStatus.setText(subStatusText));
    }

    private String parseJsonVal(String json, String key) {
        try {
            int keyIdx = json.indexOf("\"" + key + "\"");
            if (keyIdx != -1) {
                int colonIdx = json.indexOf(":", keyIdx);
                if (colonIdx != -1) {
                    int quoteStart = json.indexOf("\"", colonIdx);
                    if (quoteStart != -1) {
                        int quoteEnd = json.indexOf("\"", quoteStart + 1);
                        if (quoteEnd != -1) {
                            return json.substring(quoteStart + 1, quoteEnd).trim();
                        }
                    }
                }
            }
        } catch (Exception e) {}
        return "";
    }

    private long parseJsonLong(String json, String key) {
        try {
            int keyIdx = json.indexOf("\"" + key + "\"");
            if (keyIdx != -1) {
                int colonIdx = json.indexOf(":", keyIdx);
                if (colonIdx != -1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = colonIdx + 1; i < json.length(); i++) {
                        char c = json.charAt(i);
                        if (Character.isDigit(c)) {
                            sb.append(c);
                        } else if (sb.length() > 0) {
                            break;
                        }
                    }
                    if (sb.length() > 0) {
                        return Long.parseLong(sb.toString());
                    }
                }
            }
        } catch (Exception e) {}
        return 0;
    }

    private boolean parseJsonBool(String json, String key) {
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new KhanzaLauncher().setVisible(true);
        });
    }
}

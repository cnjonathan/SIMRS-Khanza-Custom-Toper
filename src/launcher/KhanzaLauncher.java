package launcher;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
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

    private String getLocalVersion() {
        try {
            File vFile = new File("version.txt");
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
            File vFile = new File("version.txt");
            try (FileWriter fw = new FileWriter(vFile)) {
                fw.write(ver.trim());
            }
        } catch (Exception e) {}
    }

    private String getRMEBaseUrl() {
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
        return "http://localhost:8084";
    }

    private void loadLogoAsync() {
        new Thread(() -> {
            try {
                File localLogo = new File("icon.png");
                if (!localLogo.exists()) {
                    localLogo = new File("picture/logo.png");
                }
                if (!localLogo.exists()) {
                    localLogo = new File("rsudkartinikaranganyar.jpg");
                }

                // Jika belum ada di lokal, otomatis tarik icon.png dari RSUD RME
                if (!localLogo.exists() || localLogo.length() == 0) {
                    try {
                        String baseUrl = getRMEBaseUrl();
                        String iconUrlStr = baseUrl + "/icon.png";
                        downloadFileSimple(iconUrlStr, "icon.png");
                        localLogo = new File("icon.png");
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

    private void startUpdateProcess() {
        new Thread(() -> {
            try {
                updateStatus("Memeriksa informasi rilis terbaru...", 5);
                
                String localVer = getLocalVersion();
                File jarFile = new File("SIMRSKhanza.jar");

                String baseUrl = getRMEBaseUrl();
                String apiUrl = baseUrl + "/api/updater/check?current_version=" + localVer;

                HttpURLConnection conn = connectWithFallback(apiUrl);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String json = response.toString();
                boolean hasUpdate = parseJsonBool(json, "has_update");
                String latestVersion = parseJsonVal(json, "latest_version");

                // JIKA TIDAK ADA UPDATE DAN FILE JAR SUDAH ADA LOKAL: LANGSUNG JALANKAN TANPA DOWNLOAD
                if (!hasUpdate && jarFile.exists() && jarFile.length() > 0) {
                    String activeVer = latestVersion.isEmpty() ? localVer : latestVersion;
                    updateStatus("SIMRS Khanza versi v" + activeVer + " sudah terbaru", 100);
                    saveLocalVersion(activeVer);
                    launchApplication();
                    return;
                }

                // JIKA ADA UPDATE ATAU FILE JAR BELUM TERSEDIA: BARU MELAKUKAN DOWNLOAD
                String jarUrlStr = parseJsonVal(json, "jar_url");
                if (jarUrlStr.isEmpty()) {
                    jarUrlStr = baseUrl + "/api/updater/download/jar";
                }
                long jarSize = parseJsonLong(json, "jar_size");

                // Step 1: Download SIMRSKhanza.jar
                updateStatus("Mengunduh pembaruan SIMRSKhanza.jar (v" + latestVersion + ")...", 10);
                downloadFileWithFallback(jarUrlStr, "SIMRSKhanza.jar.tmp", jarSize, 10, 80);

                // Replace file jar utama
                File newJar = new File("SIMRSKhanza.jar.tmp");
                if (newJar.exists() && newJar.length() > 0) {
                    updateSubStatus("Memperbarui file executable SIMRSKhanza.jar...");
                    Files.move(newJar.toPath(), jarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Step 2: Download Report Files (jika ada)
                updateStatus("Memeriksa laporan (.jasper)...", 85);
                downloadReportsIfAny(json, baseUrl);

                // Step 2b: Download & Smart-Merge Setting Files (seperti database.xml)
                updateStatus("Memeriksa variabel setting (.xml)...", 92);
                downloadSettingsIfAny(json, baseUrl);

                // Simpan versi lokal yang baru
                saveLocalVersion(latestVersion);

                // Step 3: Selesai & Buka Aplikasi
                updateStatus("Pembaruan Berhasil!", 100);
                launchApplication();

            } catch (Exception e) {
                e.printStackTrace();
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

            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe";
            if (!new File(javaBin).exists()) {
                javaBin = "javaw";
            }

            Process p = new ProcessBuilder(javaBin, "-Xss2m", "-Xms32m", "-Xmx1024m", "-jar", "SIMRSKhanza.jar").start();

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

    private void downloadReportsIfAny(String json, String baseUrl) {
        try {
            int reportsIdx = json.indexOf("\"reports\":[");
            if (reportsIdx != -1) {
                int endIdx = json.indexOf("]", reportsIdx);
                if (endIdx != -1) {
                    String reportsJson = json.substring(reportsIdx, endIdx + 1);
                    
                    File reportDir = new File("report");
                    if (!reportDir.exists()) {
                        reportDir.mkdirs();
                    }

                    int cur = 0;
                    while ((cur = reportsJson.indexOf("\"filename\":\"", cur)) != -1) {
                        cur += 12;
                        int endName = reportsJson.indexOf("\"", cur);
                        if (endName != -1) {
                            String filename = reportsJson.substring(cur, endName);
                            String downloadUrl = baseUrl + "/api/updater/download/report/" + filename;
                            
                            updateSubStatus("Mengunduh report: " + filename);
                            
                            try {
                                downloadFileSimple(downloadUrl, "report/" + filename);
                            } catch (Exception ex) {
                                System.out.println("Gagal download report " + filename + ": " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing reports: " + e.getMessage());
        }
    }

    private void downloadSettingsIfAny(String json, String baseUrl) {
        try {
            int settingsIdx = json.indexOf("\"settings\":[");
            if (settingsIdx != -1) {
                int endIdx = json.indexOf("]", settingsIdx);
                if (endIdx != -1) {
                    String settingsJson = json.substring(settingsIdx, endIdx + 1);
                    
                    File settingDir = new File("setting");
                    if (!settingDir.exists()) {
                        settingDir.mkdirs();
                    }

                    int cur = 0;
                    while ((cur = settingsJson.indexOf("\"filename\":\"", cur)) != -1) {
                        cur += 12;
                        int endName = settingsJson.indexOf("\"", cur);
                        if (endName != -1) {
                            String filename = settingsJson.substring(cur, endName);
                            String downloadUrl = baseUrl + "/api/updater/download/setting/" + filename;
                            
                            updateSubStatus("Mengunduh setting: " + filename);
                            
                            try {
                                String tmpPath = "setting/" + filename + ".tmp";
                                downloadFileSimple(downloadUrl, tmpPath);

                                File tmpFile = new File(tmpPath);
                                File targetFile = new File("setting/" + filename);

                                if (tmpFile.exists()) {
                                    if (filename.equalsIgnoreCase("database.xml") && targetFile.exists()) {
                                        mergeXmlProperties(targetFile, tmpFile);
                                        tmpFile.delete();
                                    } else {
                                        Files.move(tmpFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    }
                                }
                            } catch (Exception ex) {
                                System.out.println("Gagal download setting " + filename + ": " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing settings: " + e.getMessage());
        }
    }

    private void mergeXmlProperties(File targetLocalFile, File newServerFile) {
        try {
            Properties localProps = new Properties();
            try (InputStream is = new FileInputStream(targetLocalFile)) {
                localProps.loadFromXML(is);
            }

            Properties serverProps = new Properties();
            try (InputStream is = new FileInputStream(newServerFile)) {
                serverProps.loadFromXML(is);
            }

            boolean modified = false;

            for (String key : serverProps.stringPropertyNames()) {
                // If local properties does not contain key, add new key
                if (!localProps.containsKey(key)) {
                    localProps.setProperty(key, serverProps.getProperty(key));
                    modified = true;
                } else if (!key.equalsIgnoreCase("HOST") && 
                           !key.equalsIgnoreCase("DATABASE") && 
                           !key.equalsIgnoreCase("PORT") && 
                           !key.equalsIgnoreCase("USER") && 
                           !key.equalsIgnoreCase("PAS") && 
                           !key.equalsIgnoreCase("PORTWEB") && 
                           !key.equalsIgnoreCase("HOSTHYBRIDWEB") && 
                           !key.equalsIgnoreCase("URLRSUDRME")) {
                    // For non-local database connection keys, update value if changed
                    String serverVal = serverProps.getProperty(key);
                    String localVal = localProps.getProperty(key);
                    if (serverVal != null && !serverVal.equals(localVal)) {
                        localProps.setProperty(key, serverVal);
                        modified = true;
                    }
                }
            }

            if (modified) {
                try (OutputStream os = new FileOutputStream(targetLocalFile)) {
                    localProps.storeToXML(os, "SIMRS Khanza Database Properties - Auto Updated", "UTF-8");
                }
                updateSubStatus("Variabel setting/database.xml berhasil diperbarui!");
            }
        } catch (Exception e) {
            System.out.println("Gagal merge xml properties: " + e.getMessage());
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

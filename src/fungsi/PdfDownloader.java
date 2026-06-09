/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Christopher Jonathan
 */
public class PdfDownloader {
    public static File downloadPdf(String url, String savePath) throws Exception {
        URL pdfUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) pdfUrl.openConnection();
        conn.setRequestMethod("GET");

        InputStream in = conn.getInputStream();
        File file = new File(savePath);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        return file;
    }
}

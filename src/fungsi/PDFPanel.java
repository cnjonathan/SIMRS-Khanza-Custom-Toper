/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

/**
 *
 * @author Christopher Nanda Jonathan
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFPanel extends JPanel {
    private BufferedImage pdfImage;
    private BufferedImage scaledImage;
    private List<BufferedImage> pdfImages = new ArrayList<>();

    public void loadPDF(String filePath) {
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            //pdfImage = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
            // Render semua halaman PDF
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage pageImage = pdfRenderer.renderImageWithDPI(i, 100, ImageType.RGB);
                pdfImages.add(pageImage);
            }
            document.close();
            
            // Atur ukuran preferensi berdasarkan ukuran gambar PDF
            // int width = (int) (pdfImage.getWidth() * 0.8);
            // int height = (int) (pdfImage.getHeight() * 0.8);
            
            // Atur ukuran preferensi berdasarkan total tinggi semua gambar PDF
            int totalHeight = pdfImages.stream().mapToInt(BufferedImage::getHeight).sum();
            int maxWidth = pdfImages.stream().mapToInt(BufferedImage::getWidth).max().orElse(0);
            setPreferredSize(new Dimension(maxWidth, totalHeight));
            revalidate();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!pdfImages.isEmpty()) {
            int yOffset = 0; // Offset vertikal untuk menggambar setiap halaman
            for (BufferedImage image : pdfImages) {
                g.drawImage(image, 0, yOffset, this);
                yOffset += image.getHeight(); // Tambahkan tinggi gambar ke offset
            }
        }
    }
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (pdfImage != null) {
//            // Hitung skala untuk menyesuaikan lebar PDF dengan lebar JPanel
//            int panelWidth = getWidth();
//            double scale = (double) panelWidth / pdfImage.getWidth();
//            int scaledHeight = (int) (pdfImage.getHeight() * scale);
//            
//            // Skala gambar PDF
//            scaledImage = new BufferedImage(panelWidth, scaledHeight, pdfImage.getType());
//            Graphics2D g2d = scaledImage.createGraphics();
//            g2d.drawImage(pdfImage, 0, 0, panelWidth, scaledHeight, null);
//            g2d.dispose();
//            
//            // Gambar gambar yang telah diskalakan
//            g.drawImage(scaledImage, 0, 0, this);
//        }
//    }
}

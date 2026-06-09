package fungsi;
import java.awt.image.BufferedImage;
import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;
import uk.co.mmscomputing.device.scanner.ScannerListener;

import java.io.File;
import java.util.Properties;
import javax.imageio.ImageIO;

public class TwainScan implements ScannerListener {

    private Scanner scanner;
    
    public TwainScan() {
        try {
            scanner = Scanner.getDevice();
            scanner.addListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startScan() {
        try {
            scanner.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata) {
        if (type.equals(ScannerIOMetadata.ACQUIRED)) {
            try {
                // Ambil current dir
                Properties systemProp = System.getProperties();
                String currentDir = systemProp.getProperty("user.dir");
                BufferedImage image = metadata.getImage();
                // currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
                File outputfile = new File(currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"scanned_image.jpg");
                ImageIO.write(image, "jpg", outputfile);
                System.out.println("Scan complete: " + outputfile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (type.equals(ScannerIOMetadata.STATECHANGE)) {
            System.out.println("Scanner state: " + metadata.getStateStr());
        }
    }

    public static void main(String[] args) {
        new TwainScan();
    }
}

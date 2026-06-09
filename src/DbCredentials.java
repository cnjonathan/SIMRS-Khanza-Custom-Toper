import AESsecurity.EnkripsiAES;
import java.io.FileInputStream;
import java.util.Properties;

public class DbCredentials {
    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            System.out.println("Host: " + EnkripsiAES.decrypt(prop.getProperty("HOST")));
            System.out.println("Database: " + EnkripsiAES.decrypt(prop.getProperty("DATABASE")));
            System.out.println("Port: " + EnkripsiAES.decrypt(prop.getProperty("PORT")));
            System.out.println("User: " + EnkripsiAES.decrypt(prop.getProperty("USER")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

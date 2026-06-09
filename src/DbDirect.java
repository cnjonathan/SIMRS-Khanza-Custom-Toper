import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DbDirect {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/simrskhanza?useSSL=false", "root", "root");
            System.out.println("Connection established successfully to 127.0.0.1:3307!");
            
            // Get columns of resume_idrg
            Statement stmt = conn.createStatement();
            ResultSet rsCol = stmt.executeQuery("SELECT * FROM resume_idrg LIMIT 1");
            ResultSetMetaData meta = rsCol.getMetaData();
            System.out.println("COLUMNS IN resume_idrg:");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.print(meta.getColumnName(i) + " (" + meta.getColumnTypeName(i) + "), ");
            }
            System.out.println("\n");
            rsCol.close();
            
            // Get columns of topup_idrg
            ResultSet rsCol2 = stmt.executeQuery("SELECT * FROM topup_idrg LIMIT 1");
            ResultSetMetaData meta2 = rsCol2.getMetaData();
            System.out.println("COLUMNS IN topup_idrg:");
            for (int i = 1; i <= meta2.getColumnCount(); i++) {
                System.out.print(meta2.getColumnName(i) + " (" + meta2.getColumnTypeName(i) + "), ");
            }
            System.out.println("\n");
            rsCol2.close();
            
            // Query last few rows of resume_idrg
            ResultSet rs = stmt.executeQuery("SELECT id_resume_idrg, no_rawat, cost_weight, total_cost_weight, total_tarif_idrg, status_final_idrg FROM resume_idrg ORDER BY id_resume_idrg DESC LIMIT 3");
            while (rs.next()) {
                String id = rs.getString(1);
                System.out.printf("ID: %s, NoRawat: %s, CostWeight: %s, TotalCostWeight: %s, TotalTarif: %s, Final: %s\n",
                    id, rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT type_topup, code_topup, description_topup, cost_weight_topup FROM topup_idrg WHERE id_resume_idrg = '" + id + "'");
                while (rs2.next()) {
                    System.out.printf("  -> Topup: %s | %s | %s | %s\n",
                        rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4));
                }
                rs2.close();
                stmt2.close();
            }
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (Exception e) {}
            }
        }
    }
}

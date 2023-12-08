/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;


import java.awt.Desktop;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRExporterContext;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;
import uz.ncipro.calendar.JDateTimePicker;
/**
 *
 * @author Owner
 */
public final class validasi2 {
    private int a,j,i,result=0;
    private String s,s1,auto,PEMBULATANHARGAOBAT=koneksiDB.PEMBULATANHARGAOBAT();
    private final Connection connect=koneksiDB.condb();
    private final sekuel sek=new sekuel();
    private final java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
    private final DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");  
    private final DecimalFormat df4 = new DecimalFormat("###,###,###,###,###,###,###.#################");  
    private final DecimalFormat df5 = new DecimalFormat("###,###,###,###,###,###,###.##");  
    private final DecimalFormat df3 = new DecimalFormat("######"); 
    private final DecimalFormat df6 = new DecimalFormat("######.###"); 
    private final DecimalFormat df7 = new DecimalFormat("######.#"); 
    private PreparedStatement ps;
    private ResultSet rs;
    private final Calendar now = Calendar.getInstance();
    private final int year=(now.get(Calendar.YEAR));
    private static final Properties prop = new Properties();  
    
    public validasi2(){
        super();
    };

    public void autoNomer(DefaultTableModel tabMode,String strAwal,Integer pnj,javax.swing.JTextField teks){        
        s=Integer.toString(tabMode.getRowCount()+1);
        j=s.length();
        s1="";
        for(i = 1;i<=pnj-j;i++){
            s1=s1+"0";
        }
        teks.setText(strAwal+s1+s);
    }

    public void autoNomer(String tabel,String strAwal,Integer pnj,javax.swing.JTextField teks){
        try {
            ps=connect.prepareStatement("select * from "+tabel);
            try{
               rs=ps.executeQuery();
               rs.last();
               s=Integer.toString(rs.getRow()+1);
               j=s.length();
               s1="";
               for(i = 1;i<=pnj-j;i++){
                   s1=s1+"0";
               }
               teks.setText(strAwal+s1+s);      
            }catch(Exception e){
               System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void autoNomer2(String sql,String strAwal,Integer pnj,javax.swing.JTextField teks){
        try {
            ps=connect.prepareStatement(sql);
            try{
                rs=ps.executeQuery();
                rs.last();
                s=Integer.toString(rs.getRow()+1);
                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                teks.setText(strAwal+s1+s);
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void autoNomer3(String sql,String strAwal,Integer pnj,javax.swing.JTextField teks){
        try {
            ps=connect.prepareStatement(sql);
            try{   
                rs=ps.executeQuery();
                s="1";
                while(rs.next()){
                    s=Integer.toString(Integer.parseInt(rs.getString(1))+1);
                }            

                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                teks.setText(strAwal+s1+s);
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Query tidak bisa dijalankan...!!!!");
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void autoNomer4(String sql,String strAwal,Integer pnj,javax.swing.JTextField teks){
        try {
            ps=connect.prepareStatement(sql);
            try{   
                rs=ps.executeQuery();
                s="1";
                while(rs.next()){
                    s=Integer.toString(Integer.parseInt(rs.getString(1))+1);
                }            

                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                teks.setText((strAwal+s1+s).substring(4,6)+(strAwal+s1+s).substring(2,4)+(strAwal+s1+s).substring(0,2));
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Query tidak bisa dijalankan...!!!!");
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void autoNomer5(String sql,String strAwal,Integer pnj,javax.swing.JTextField teks){
        try {
            ps=connect.prepareStatement(sql);
            try{   
                rs=ps.executeQuery();
                s="1";
                while(rs.next()){
                    s=Integer.toString(Integer.parseInt(rs.getString(1))+1);
                }            

                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                teks.setText((strAwal+s1+s).substring(2,4)+(strAwal+s1+s).substring(0,2)+(strAwal+s1+s).substring(4,6));
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Query tidak bisa dijalankan...!!!!");
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void autoNomer6(String sql,String strAwal,Integer pnj,javax.swing.JTextField teks){
        try {
            ps=connect.prepareStatement(sql);
            try{   
                rs=ps.executeQuery();
                s="1";
                while(rs.next()){
                    s=Integer.toString(Integer.parseInt(rs.getString(1))+1);
                }            

                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                teks.setText(s1+s+strAwal);
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Query tidak bisa dijalankan...!!!!");
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public String autoNomer(String tabel,String strAwal,Integer pnj){
        try {
            auto="";
            ps=connect.prepareStatement("select * from "+tabel);
            try{        
                rs=ps.executeQuery();
                rs.last();
                s=Integer.toString(rs.getRow()+1);
                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                auto=strAwal+s1+s;             
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Query tidak bisa dijalankan...!!!!");
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
            
        return auto;        
    }
    
    public String autoNomer3(String sql,String strAwal,Integer pnj){
        try {
            auto="";
            ps=connect.prepareStatement(sql);
            try{
                rs=ps.executeQuery();
                s="1";
                while(rs.next()){
                    s=Integer.toString(Integer.parseInt(rs.getString(1))+1);
                }            

                j=s.length();
                s1="";
                for(i = 1;i<=pnj-j;i++){
                    s1=s1+"0";
                }
                auto=strAwal+s1+s;
             }catch(Exception e){
                System.out.println("Notifikasi : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Query tidak bisa dijalankan...!!!!");
             }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
            
        return auto;
    }

    public void editTable(DefaultTableModel tabMode,String table,String field_acuan,JTextField nilai_field,String update) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            nilai_field.requestFocus();
        }else if(nilai_field.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
        }else if(! nilai_field.getText().trim().equals("")){            
            sek.mengedit(table,field_acuan+"='"+nilai_field.getText()+"'", update);                 
        }
    }
    
    public void editTable(String table,String field_acuan,JTextField nilai_field,String update) {
        if(nilai_field.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
        }else if(! nilai_field.getText().trim().equals("")){            
            sek.mengedit(table,field_acuan+"='"+nilai_field.getText()+"'", update);                 
        }
    }
    
    public void editTable(DefaultTableModel tabMode,String table,String field_acuan,String nilai_field,String update,int i, String[] a) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else if(nilai_field.trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
        }else if(! nilai_field.trim().equals("")){            
            sek.mengedit(table,field_acuan+"="+nilai_field, update,i,a);                 
        }
    }

    public void editTable(DefaultTableModel tabMode,String table,String field_acuan,JComboBox nilai_field,String update) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            nilai_field.requestFocus();
        }else if(nilai_field.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
        }else if(nilai_field.getSelectedItem()!=""){            
            sek.mengedit(table,field_acuan+"='"+nilai_field.getSelectedItem()+"'", update);            
            
        }
    }

    public void editTable(DefaultTableModel tabMode,String table,String field_acuan,JLabel nilai_field,String update) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            nilai_field.requestFocus();
        }else if(nilai_field.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengedit. Pilih dulu data yang mau diedit.\nKlik data pada table untuk memilih...!!!!");
        }else if(! nilai_field.getText().trim().equals("")){            
            sek.mengedit(table,field_acuan+"='"+nilai_field.getText()+"'", update);            
            
        }
    }
    
    public void fillData(DefaultTableModel model,JTable table, File file) {
        try {
            WritableWorkbook workbook1 = Workbook.createWorkbook(file);
            WritableSheet sheet1 = workbook1.createSheet("First Sheet", 0); 
            model = (DefaultTableModel) table.getModel();

            for (i = 0; i < model.getColumnCount(); i++) {
                Label column = new Label(i, 0, model.getColumnName(i));
                sheet1.addCell(column);
            }
            for (i = 0; i < model.getRowCount(); i++) {
                for (j = 0; j < model.getColumnCount(); j++) {
                    Label row = new Label(j, i + 1, 
                            model.getValueAt(i, j).toString());
                    sheet1.addCell(row);
                }
            }
            workbook1.write();
            workbook1.close();
        } catch (IOException | WriteException ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }

    public void hapusTable(DefaultTableModel tabMode,JTextField nilai_field,String table,String field) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            nilai_field.requestFocus();
        }else if(nilai_field.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(! nilai_field.getText().trim().equals("")){            
            sek.meghapus(table,field,nilai_field.getText());            
            
        }
    }

    public void hapusTable(DefaultTableModel tabMode,JComboBox nilai_field,String table,String field) {
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            nilai_field.requestFocus();
        }else if(nilai_field.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(nilai_field.getSelectedItem()!=""){            
            String data=nilai_field.getSelectedItem().toString();
            sek.meghapus(table,field,data);     
        }
    }

    public void loadCombo(JComboBox cmb,String field,String table){
        cmb.removeAllItems();
        try {
            ps=connect.prepareStatement("select "+field+" from "+table+" order by "+field);
            try{
                rs=ps.executeQuery();
                while(rs.next()){
                    String item=rs.getString(1);
                    cmb.addItem(item);
                    a++;
                }          
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rs != null){
                    rs.close();
                }
                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }

    public void LoadTahun(JComboBox cmb){        
        cmb.removeAllItems();
        for(i =(year+1);i>=1980;i--){
            cmb.addItem(i);
        }
        cmb.setSelectedItem(year);
    }

    public void LoadTahunAkd(JComboBox cmb){
        cmb.removeAllItems();
        for(i = 1950;i<=year;i++){
            cmb.addItem(i+"1");
            cmb.addItem(i+"2");
        }
        cmb.setSelectedItem(year+"1");
    }

    @SuppressWarnings("empty-statement")
    public void MyReport(String reportName,String reportDirName,String judul,Map parameters){
        Properties systemProp = System.getProperties();

        // Ambil current dir
        String currentDir = systemProp.getProperty("user.dir");

        File dir = new File(currentDir);

        File fileRpt;
        String fullPath = "";
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName + File.separatorChar + reportName);
                if (fileRpt.isFile()) { // Cek apakah file RptMaster.jasper ada
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if

        try {
            try (Statement stm = connect.createStatement()) {
                try {
                    
                    String namafile="./"+reportDirName+"/"+reportName;
                    JasperPrint jasperPrint = JasperFillManager.fillReport(namafile, parameters, connect);
                    
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                    jasperViewer.setTitle(judul);
                    Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
                    jasperViewer.setSize(screen.width-50,screen.height-50);
                    jasperViewer.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
                    jasperViewer.setLocationRelativeTo(null);
                    jasperViewer.setVisible(true);
                } catch (Exception rptexcpt) {
                    System.out.println("Report Can't view because : " + rptexcpt);
                    JOptionPane.showMessageDialog(null,"Report Can't view because : "+ rptexcpt);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void MyReportPDF(String reportName,String reportDirName,String judul,Map parameters){
        Properties systemProp = System.getProperties();

        // Ambil current dir
        String currentDir = systemProp.getProperty("user.dir");

        File dir = new File(currentDir);

        File fileRpt;
        String fullPath = "";
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName + File.separatorChar + reportName);
                if (fileRpt.isFile()) { // Cek apakah file RptMaster.jasper ada
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if

        try {
            try (Statement stm = connect.createStatement()) {
                try {
                    File f = new File("./"+reportDirName+"/"+reportName.replaceAll("jasper","pdf")); 
                    String namafile="./"+reportDirName+"/"+reportName;
                    JasperPrint jasperPrint = JasperFillManager.fillReport(namafile, parameters, connect);
                    JasperExportManager.exportReportToPdfFile(jasperPrint,"./"+reportDirName+"/"+reportName.replaceAll("jasper","pdf"));
                    Desktop.getDesktop().open(f);
                } catch (Exception rptexcpt) {
                    System.out.println("Report Can't view because : " + rptexcpt);
                    JOptionPane.showMessageDialog(null,"Report Can't view because : "+ rptexcpt);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void MyReport2(String reportName,String reportDirName,String judul,Map parameters){
        Properties systemProp = System.getProperties();

        // Ambil current dir
        String currentDir = systemProp.getProperty("user.dir");

        File dir = new File(currentDir);

        File fileRpt;
        String fullPath = "";
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName + File.separatorChar + reportName);
                if (fileRpt.isFile()) { // Cek apakah file RptMaster.jasper ada
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if

        try {
            try (Statement stm = connect.createStatement()) {
                try {
                    
                    String namafile="./"+reportDirName+"/"+reportName;
                    JasperPrint jasperPrint = JasperFillManager.fillReport(namafile, parameters, connect);
                    
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                    jasperViewer.setTitle(judul);
                    Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
                    jasperViewer.setSize(screen.width-50,screen.height-50);
                    jasperViewer.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
                    jasperViewer.setLocationRelativeTo(null);
                    jasperViewer.setVisible(true);
                } catch (Exception rptexcpt) {
                    System.out.println("Report Can't view because : " + rptexcpt);
                    JOptionPane.showMessageDialog(null,"Report Can't view because : "+ rptexcpt);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
        //Function untuk report query print jasper secara otomatis pilih
    public void MyReportqry(String reportName,String reportDirName,String judul,String qry,Map parameters, Integer copy){
        Properties systemProp = System.getProperties();
        PreparedStatement ps_workstation;
        ResultSet rs_workstation;
        System.out.println("MyReportqry");

        // Ambil current dir
        String currentDir = systemProp.getProperty("user.dir");

        File dir = new File(currentDir);

        File fileRpt;
        String fullPath = "";
        System.out.println("Cek apakah file RptMaster.jrxml ada");
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName + File.separatorChar + reportName);
                if (fileRpt.isFile()) { 
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if

        try {
            ps=connect.prepareStatement(qry);
            System.out.println("try ps=connect.prepareStatement(qry)");
            try {
                System.out.println("try JRResultSetDataSource rsdt = new JRResultSetDataSource(rs)");
                String namafile="./"+reportDirName+"/"+reportName;
                rs=ps.executeQuery();
                System.out.println("Query: "+qry);
                JRResultSetDataSource rsdt = new JRResultSetDataSource(rs);
                
                JasperPrint jasperPrint = JasperFillManager.fillReport(namafile, parameters,rsdt);

                //Get the printers names
                PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

                //Lets set the printer name based on the registered printers driver name (you can see the printer names in the services variable at debugging) 
                //String selectedPrinter = "Toper Printer";   
                InetAddress inetAddress = InetAddress.getLocalHost();
                String hostname = InetAddress.getLocalHost().getHostName();
                String ip_address = inetAddress.getHostAddress();
                try {
                    String query = "SELECT wc.*, w.ip_address, w.workstation, wcl.config_name, wcl.jasper_report_name\n" +
                                   "FROM workstation_config wc\n" +
                                   "LEFT JOIN workstation w ON wc.id_workstation = w.id_workstation\n" +
                                   "LEFT JOIN workstation_config_list wcl ON wc.id_workstation_config = wcl.id_workstation_config\n"+
                                   "WHERE w.ip_address  = '"+ip_address+"' AND w.workstation = '"+hostname+"' AND wcl.jasper_report_name = '"+reportName+"'";
                    ps_workstation = connect.prepareStatement(query);
                    rs_workstation = ps_workstation.executeQuery();
                    System.out.println("try rs_workstation = ps_workstation.executeQuery");
                    System.out.println("query: "+query);
                    
                    if (rs_workstation.next()) {
                        System.out.println("Sharing printer: "+rs_workstation.getString("sharing_printer"));
                        Integer x = rs_workstation.getInt("margin_x");
                        Integer y = rs_workstation.getInt("margin_y");
                        Integer width = rs_workstation.getInt("width");
                        Integer height = rs_workstation.getInt("height");
                        //String selectedPrinter = "\\\\10.77.41.99\\Canon LBP2900"; // examlpe to network shared printer
                        
                        String selectedPrinter = rs_workstation.getString("sharing_printer");
                        System.out.println("IP Address: " + ip_address);
                        System.out.println("Hostname: " + hostname);
                        System.out.println("Share printer: " + selectedPrinter);
                        System.out.println("Number of print services: " + services.length);
                        PrintService selectedService = null;

                        //Set the printing settings
                        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//                        printRequestAttributeSet.add(MediaSizeName.ISO_A4);
//                        x,y adalah margin; w, h adalah ukuran kertas (kertas label zebra zd230 menggunakan ukuran 66mm dan 35mm dengan margin 0
                        if(x != 0 && y != 0 && width != 0 && height != 0){
                            printRequestAttributeSet.add(new MediaPrintableArea(x, y, width, height, MediaPrintableArea.MM));
                        }else{
                            printRequestAttributeSet.add(MediaSizeName.ISO_A4);
                            
                        }
                        printRequestAttributeSet.add(new Copies(copy));

                        if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) { 
                          printRequestAttributeSet.add(OrientationRequested.LANDSCAPE); 
                        } else { 
                          printRequestAttributeSet.add(OrientationRequested.PORTRAIT); 
                        } 

                        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
                        printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

                        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
                        SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
                        configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
                        configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
                        configuration.setDisplayPageDialog(false);
                        configuration.setDisplayPrintDialog(false);

                        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                        exporter.setConfiguration(configuration);

                        //Iterate through available printer, and once matched with our <selectedPrinter>, go ahead and print!
                        if(services != null && services.length != 0){
                          for(PrintService service : services){
                              String existingPrinter = service.getName();
                              if(existingPrinter.equals(selectedPrinter))
                              {
                                  selectedService = service;
                                  break;
                              }
                          }
                        }
                        if(selectedService != null)
                        {   
                          try{
                              //Lets the printer do its magic!
                              exporter.exportReport();
                          }catch(JRException e){
                        System.out.println("JasperReport Error: "+e.getMessage());
                          }
                        }else{
                          System.out.println("JasperReport Error: Printer not found!");
                        }
                    }else{
                        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                        jasperViewer.setTitle(judul);
                        Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
                        jasperViewer.setSize(screen.width-50,screen.height-50);
                        jasperViewer.setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
                        jasperViewer.setLocationRelativeTo(null);
                        jasperViewer.setVisible(true);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                }
            } catch (HeadlessException | UnknownHostException | SQLException | JRException rptexcpt) {
                System.out.println("Report Can't view because : " + rptexcpt);
                JOptionPane.showMessageDialog(null,"Report Can't view because : "+ rptexcpt);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (HeadlessException | SQLException e) {
            System.out.println(e);
        }
    }
    
    public void MyReportqrypdf(String reportName,String reportDirName,String judul,String qry,Map parameters){
        Properties systemProp = System.getProperties();

        // Ambil current dir
        String currentDir = systemProp.getProperty("user.dir");

        File dir = new File(currentDir);

        File fileRpt;
        String fullPath = "";
        if (dir.isDirectory()) {
            String[] isiDir = dir.list();
            for (String iDir : isiDir) {
                fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + reportDirName + File.separatorChar + reportName);
                if (fileRpt.isFile()) { // Cek apakah file RptMaster.jrxml ada
                    fullPath = fileRpt.toString();
                    System.out.println("Found Report File at : " + fullPath);
                } // end if
            } // end for i
        } // end if

        try {
            ps=connect.prepareStatement(qry);
            try {
                String namafile="./"+reportDirName+"/"+reportName;
                File f = new File("./"+reportDirName+"/"+reportName.replaceAll("jasper","pdf")); 
                rs=ps.executeQuery();
                JRResultSetDataSource rsdt = new JRResultSetDataSource(rs);
                JasperPrint jasperPrint = JasperFillManager.fillReport(namafile, parameters,rsdt);
                JasperExportManager.exportReportToPdfFile(jasperPrint,"./"+reportDirName+"/"+reportName.replaceAll("jasper","pdf"));
                Desktop.getDesktop().open(f);
            } catch (Exception rptexcpt) {
                System.out.println("Report Can't view because : " + rptexcpt);
                JOptionPane.showMessageDialog(null,"Report Can't view because : "+ rptexcpt);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    public void MyReport(String reportName,Map parameters,String title){
        try {
                JasperViewer jasperViewer =new JasperViewer(JasperFillManager.fillReport("./report/"+reportName,parameters,connect), false);
                jasperViewer.setTitle(title);
                jasperViewer.setLocationRelativeTo(null);
                jasperViewer.setVisible(true);
                //JasperViewer.viewReport(JasperFillManager.fillReport(JasperCompileManager.compileReport("./report/"+reportName),parameters,connect),false);
        } catch (Exception ex) {
           System.out.println("Notifikasi : "+ex);
        } 
    }


    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JTextField kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }
    
    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JCheckBox kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }
    
    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JTextField kanan,JTextField bawah){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            bawah.requestFocus();
        }
    }
    
    public void pindah2(java.awt.event.KeyEvent evt,JTextField kiri,JTextField kanan){
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }
    
    public void pindah2(java.awt.event.KeyEvent evt,JTextField kiri,JComboBox kanan){
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JTextArea kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(KeyEvent evt, JComboBox kiri, JTextArea kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JButton kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }
    
    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JButton kanan,JTextField bawah){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            bawah.requestFocus();
        }
    }
    
    public void pindah2(java.awt.event.KeyEvent evt,JTextField kiri,JButton kanan){
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JButton kiri,JTextField kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JButton kiri,JButton kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JTextField kiri,JComboBox kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JTextArea kiri,JComboBox kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JComboBox kiri,JTextField kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JComboBox kiri,JDateTimePicker kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }
    
    public void pindah(java.awt.event.KeyEvent evt,JCheckBox kiri,JDateTimePicker kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JDateTimePicker kiri,JTextField kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JComboBox kiri,JComboBox kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(java.awt.event.KeyEvent evt,JComboBox kiri,JButton kanan){
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(KeyEvent evt, JButton kiri, JComboBox kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(KeyEvent evt, JTextArea kiri, JButton kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }

    public void pindah(KeyEvent evt, JTextArea kiri, JTextField kanan) {
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kanan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kiri.requestFocus();
        }
    }
    
    public void panggilUrl(String url){
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();                                
        try{ 
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            if(os.contains("win")) {
                rt.exec( "rundll32 url.dll,FileProtocolHandler " + "http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+url);
            }else if (os.contains("mac")) {
                rt.exec( "open " + "http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+url);
            }else if (os.contains("nix") || os.contains("nux")) {
                String[] browsers = {"x-www-browser","epiphany", "firefox", "mozilla", "konqueror","chrome","chromium","netscape","opera","links","lynx","midori"};
                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuilder cmd = new StringBuilder();
                for(i=0; i<browsers.length; i++) cmd.append(i==0  ? "" : " || ").append(browsers[i]).append(" \"").append("http://").append(koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")).append("/").append(prop.getProperty("HYBRIDWEB")).append("/").append(url).append( "\" ");
                rt.exec(new String[] { "sh", "-c", cmd.toString() });
            } 
        }catch (Exception e){
            System.out.println("Notif Browser : "+e);
        } 
    }
    
    public void panggilUrl2(String url){
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();                                
        try{ 
            Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            if(os.contains("win")) {
                rt.exec( "rundll32 url.dll,FileProtocolHandler "+url);
            }else if (os.contains("mac")) {
                rt.exec( "open " +url);
            }else if (os.contains("nix") || os.contains("nux")) {
                String[] browsers = {"x-www-browser","epiphany", "firefox", "mozilla", "konqueror","chrome","chromium","netscape","opera","links","lynx","midori"};
                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuilder cmd = new StringBuilder();
                for(i=0; i<browsers.length; i++) cmd.append(i==0  ? "" : " || ").append(browsers[i]).append(" \"").append(url).append( "\" ");
                rt.exec(new String[] { "sh", "-c", cmd.toString() });
            } 
        }catch (Exception e){
            System.out.println("Notif Browser : "+e);
        } 
    }
    
    public void printUrl(String url) throws URISyntaxException{
        try{
           Properties prop = new Properties();
           prop.loadFromXML(new FileInputStream("setting/database.xml"));            
           desktop.print(new File(new java.net.URI("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+url)));  
        }catch (Exception e) {
           System.out.println(e);
        }
    }

    public void SetTgl(DefaultTableModel tabMode,JTable table,JDateTimePicker dtp,int i){
        j=table.getSelectedRow();
        try {
           Date dtpa = new SimpleDateFormat("yyyy-MM-dd").parse(tabMode.getValueAt(j,i).toString());
           dtp.setDate(dtpa);
        } catch (ParseException ex) {
           dtp.setDate(new Date());
        }
    }
    
    public String SetTgl(String original){
        s = "";
        try {
            s=original.substring(6,10)+"-"+original.substring(3,5)+"-"+original.substring(0,2);
        }catch (Exception e) {
        }   
        return s;
    }
    
    public String SetTgl3(String original){
        s = "";
        try {
            s=original.substring(8,10)+"-"+original.substring(5,7)+"-"+original.substring(0,4);
        }catch (Exception e) {
        }   
        return s;
    }
    
    public String MaxTeks(String original,int max){
        if(original.length()>=max){
            s=original.substring(0,(max-1));
        }else{
            s=original;
        }
        return original;
    }
    
    public void SetTgl(JDateTimePicker dtp,String tgl){            
        try {
           Date dtpa = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
           dtp.setDate(dtpa);
        } catch (ParseException ex) {
           dtp.setDate(new Date());
        }
    }
    
    public void SetTgl2(JDateTimePicker dtp,String tgl){            
        try {
           Date dtpa = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tgl);
           dtp.setDate(dtpa);
        } catch (ParseException ex) {
           dtp.setDate(new Date());
        }
    }
    
    public Date SetTgl2(String tgl){
        try {
           Date dtpa = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
           return dtpa;
        } catch (ParseException ex) {
           return new Date();
        }
    }
    
    public void textKosong(JTextField teks,String pesan){
        JOptionPane.showMessageDialog(null,"Maaf, "+pesan+" tidak boleh kosong...!!!");
        teks.requestFocus();
    }

    public void textKosong(JTextArea teks,String pesan){
        JOptionPane.showMessageDialog(null,"Maaf, "+pesan+" tidak boleh kosong...!!!");
        teks.requestFocus();
    }
    
    public void textKosong(JButton teks,String pesan){
        JOptionPane.showMessageDialog(null,"Maaf, "+pesan+" tidak boleh kosong...!!!");
        teks.requestFocus();
    }

    public void tabelKosong(DefaultTableModel tabMode) {
        j=tabMode.getRowCount();
        for(i=0;i<j;i++){
            tabMode.removeRow(0);
        }
    }

    public void textKosong(JComboBox teks, String pesan) {
        JOptionPane.showMessageDialog(null,"Maaf, "+pesan+" tidak boleh kosong...!!!");
        teks.requestFocus();
    }
    
    public String SetAngka(double nilai){        
       return df2.format(nilai);
    }
    
    public String SetAngka3(double nilai){        
       return df4.format(nilai);
    }
    
    public String SetAngka4(double nilai){        
       return df5.format(nilai);
    }
    
    public String SetAngka2(double nilai){        
       return df3.format(nilai);
    }
    
    public String SetAngka5(double nilai){        
       return df6.format(nilai);
    }
    
    public String SetAngka6(double nilai){        
       return df7.format(nilai);
    }
    
    public double SetAngka7(double nilai){        
       return Double.parseDouble(df7.format(nilai));
    }
    
    public double SetAngka8(double value,int places){      
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }
    
    public double SetAngka(String txt){
        double x;   
        try {
            if(txt.equals("")){
                x=0;
            }else{
                x=Double.parseDouble(txt);
            }
        } catch (Exception e) {
            x=0;
        }
            
        return x;
    }
    
    public double roundUp(double number, int multiple) {
        if(PEMBULATANHARGAOBAT.equals("yes")){
            result = multiple;
            if (number % multiple == 0) {
                return (int) number;
            }

            if (number % multiple != 0) {
                int division = (int) ((number / multiple) + 1);
                result = division * multiple;
            }
            return result;
        }else{
            return Math.round(number);
        }
    }
       
}

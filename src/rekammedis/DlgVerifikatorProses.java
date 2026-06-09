/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */


package rekammedis;

import keuangan.*;
import bridging.INACBGCariCoderNIK;
import bridging.INACBGHybrid;
import bridging.koneksiDBFUJI;
import fungsi.PDFPanel;
import setting.*;
import fungsi.WarnaTable;
import fungsi.WarnaTablePenjaminan;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.validasi2;
import fungsi.TwainScan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javax.management.remote.JMXConnectorFactory.connect;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

/**
 *
 * @author Christopher Nanda Jonathan the greatest IT in the world ever
 */
public class DlgVerifikatorProses extends javax.swing.JDialog {
    private final DefaultTableModel tabLabMode, tabRadMode;
    private Connection koneksi=koneksiDB.condb();
    // private Connection koneksiradiologi;
    private sekuel Sequel=new sekuel();
    private validasi2 Valid=new validasi2();
    private ResultSet rs, rs2,rssetjam;
    private PreparedStatement ps, psakunbayar, pssetjam,pscaripiutang,psdiagnosa,psibu,psanak,pstarif,psdpjp,pscariumur;
    private int i=0,pilihan=0,sudah=0,jmlparsial=0;
    private String export_path = "", merged_path = "", naps2_path = "", naps2_profile = "", filepath = "", filescanpath = "", fileinacbgpath="", filemergedpath;
    private static final Properties prop = new Properties();  
    private final INACBGCariCoderNIK cariNIK=new INACBGCariCoderNIK(null,true);
    private final INACBGHybrid inacbgklaim=new INACBGHybrid(null,true);
    private String coder_nik="",pilihpage="",judulform="";
    public  DlgBilingRanap billing=new DlgBilingRanap( null,true);
    public DlgBilingRalan billingralan=new DlgBilingRalan(null,false);
    

    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgVerifikatorProses(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,10);
        setSize(457,249);
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            export_path = prop.getProperty("EXPORTPDF");
            merged_path = prop.getProperty("MERGEDPDF");
            naps2_path = prop.getProperty("NAPS2PATH");
            naps2_profile = prop.getProperty("NAPS2PROFILE");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DlgVerifikatorProses.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DlgVerifikatorProses.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cariNIK.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    if(cariNIK.getTable().getSelectedRow()!= -1){                   
                        coder_nik=cariNIK.getTable().getValueAt(cariNIK.getTable().getSelectedRow(),2).toString();
                        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        try {
                            inacbgklaim.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"inacbg/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&page="+pilihpage+"&codernik="+coder_nik);                    
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : "+ex);
                        }

                        inacbgklaim.setJudul(judulform);
                        inacbgklaim.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        inacbgklaim.setLocationRelativeTo(internalFrame1);                    
                        inacbgklaim.setVisible(true);
                        setCursor(Cursor.getDefaultCursor());
                    }                         
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        Object[] row={"Tipe",
                      "No Order",
                      "No Rawat",
                      "No RM",
                      "Pasien",
                      "Tgl Permintaan",
                      "Jam Permintaan",
                      "Tgl Sampel",
                      "Jam Sampel",
                      "Tgl Hasil",
                      "Jam Hasil",
                      "Dokter Perujuk",
                      "Status",
                      "Info Tambahan",
                      "Diagnosa Klinis"
                    };
       
        tabLabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbLab.setModel(tabLabMode);
        //tampil();
        tbLab.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbLab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 15; i++) {
            TableColumn column = tbLab.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150); //Tipe
            }else if(i==1){
                column.setPreferredWidth(150); //No Order
            }else if(i==2){
                column.setPreferredWidth(150); //No Rawat
            }else if(i==3){
                column.setPreferredWidth(150); //No RM
            }else if(i==4){
                column.setPreferredWidth(150); //Pasien
            }else if(i==5){
                column.setPreferredWidth(150); //Tgl Permintaan
            }else if(i==6){
                column.setPreferredWidth(150); //Jam Permintaan
            }else if(i==7){
                column.setPreferredWidth(150); //Tgl Sampel
            }else if(i==8){
                column.setPreferredWidth(150); //Jam Sampel
            }else if(i==9){
                column.setPreferredWidth(150); //Tgl Hasil
            }else if(i==10){
                column.setPreferredWidth(150); //Jam Hasil
            }else if(i==11){
                column.setPreferredWidth(150); //Dokter Perujuk
            }else if(i==12){
                column.setPreferredWidth(150); //Status
            }else if(i==13){
                column.setPreferredWidth(150); //Info Tambahan
            }else if(i==14){
                column.setPreferredWidth(150); //Diagnosa Klinis
            }
        }
        tbLab.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] row_rad={  "No Order",
                            "No Rawat",
                            "No RM",
                            "SEP",
                            "Pasien",
                            "Tgl Permintaan",
                            "Jam Permintaan",
                            "Tgl Sampel",
                            "Jam Sampel",
                            "Tgl Hasil",
                            "Jam Hasil",
                            "Dokter Perujuk",
                            "Status",
                            "Info Tambahan",
                            "Diagnosa Klinis"
                    };
       
        tabRadMode=new DefaultTableModel(null,row_rad){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbRad.setModel(tabRadMode);
        //tampil();
        tbRad.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRad.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 15; i++) {
            TableColumn column_rad = tbRad.getColumnModel().getColumn(i);
            if(i==0){
                column_rad.setPreferredWidth(150); //No Order
            }else if(i==1){
                column_rad.setPreferredWidth(150); //No Rawat
            }else if(i==2){
                column_rad.setPreferredWidth(150); //No RM
            }else if(i==3){
                column_rad.setPreferredWidth(150); //SEP
            }else if(i==4){
                column_rad.setPreferredWidth(150); //Pasien
            }else if(i==5){
                column_rad.setPreferredWidth(150); //Tgl Permintaan
            }else if(i==6){
                column_rad.setPreferredWidth(150); //Jam Permintaan
            }else if(i==7){
                column_rad.setPreferredWidth(150); //Tgl Sampel
            }else if(i==8){
                column_rad.setPreferredWidth(150); //Jam Sampel
            }else if(i==9){
                column_rad.setPreferredWidth(150); //Tgl Hasil
            }else if(i==10){
                column_rad.setPreferredWidth(150); //Jam Hasil
            }else if(i==11){
                column_rad.setPreferredWidth(150); //Dokter Perujuk
            }else if(i==12){
                column_rad.setPreferredWidth(150); //Status
            }else if(i==13){
                column_rad.setPreferredWidth(150); //Info Tambahan
            }else if(i==14){
                column_rad.setPreferredWidth(150); //Diagnosa Klinis
            }
        }
        tbRad.setDefaultRenderer(Object.class, new WarnaTable());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        temp_sep = new javax.swing.JTextField();
        btnSuratKontrol = new javax.swing.JButton();
        internalFrame1 = new widget.InternalFrame();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        Scroll = new widget.ScrollPane();
        tbLab = new widget.Table();
        jPanel9 = new javax.swing.JPanel();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbRad = new widget.Table();
        jPanel8 = new javax.swing.JPanel();
        jInternalFrame3 = new javax.swing.JInternalFrame();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel25 = new widget.Label();
        DiagnosaSekunder2 = new widget.TextBox();
        jLabel26 = new widget.Label();
        DiagnosaUtama = new widget.TextBox();
        jLabel27 = new widget.Label();
        DiagnosaSekunder3 = new widget.TextBox();
        jLabel28 = new widget.Label();
        DiagnosaSekunder4 = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        Keluhan = new widget.TextArea();
        jLabel11 = new widget.Label();
        jLabel14 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        JalannyaPenyakit = new widget.TextArea();
        jLabel15 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        PemeriksaanPenunjang = new widget.TextArea();
        jLabel16 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        HasilLaborat = new widget.TextArea();
        jLabel17 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Obat2an = new widget.TextArea();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        DiagnosaSekunder1 = new widget.TextBox();
        jLabel31 = new widget.Label();
        KodeDiagnosaUtama = new widget.TextBox();
        KodeDiagnosaSekunder1 = new widget.TextBox();
        KodeDiagnosaSekunder2 = new widget.TextBox();
        KodeDiagnosaSekunder3 = new widget.TextBox();
        KodeDiagnosaSekunder4 = new widget.TextBox();
        jLabel32 = new widget.Label();
        ProsedurUtama = new widget.TextBox();
        KodeProsedurUtama = new widget.TextBox();
        ProsedurSekunder1 = new widget.TextBox();
        jLabel33 = new widget.Label();
        KodeProsedurSekunder1 = new widget.TextBox();
        jLabel34 = new widget.Label();
        ProsedurSekunder2 = new widget.TextBox();
        KodeProsedurSekunder2 = new widget.TextBox();
        KodeProsedurSekunder3 = new widget.TextBox();
        ProsedurSekunder3 = new widget.TextBox();
        jLabel35 = new widget.Label();
        label14 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        Kondisi = new widget.ComboBox();
        jLabel18 = new widget.Label();
        BtnDokter1 = new widget.Button();
        BtnDokter2 = new widget.Button();
        BtnDokter3 = new widget.Button();
        BtnDokter4 = new widget.Button();
        BtnDokter5 = new widget.Button();
        jPanel6 = new javax.swing.JPanel();
        scrollInput1 = new widget.ScrollPane();
        FormInput1 = new widget.PanelBiasa();
        jLabel19 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        TNoRM1 = new widget.TextBox();
        jLabel36 = new widget.Label();
        DiagnosaSekunder5 = new widget.TextBox();
        jLabel37 = new widget.Label();
        DiagnosaUtama1 = new widget.TextBox();
        jLabel38 = new widget.Label();
        DiagnosaSekunder6 = new widget.TextBox();
        jLabel39 = new widget.Label();
        DiagnosaSekunder7 = new widget.TextBox();
        scrollPane7 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        scrollPane8 = new widget.ScrollPane();
        PemeriksaanFisik = new widget.TextArea();
        jLabel22 = new widget.Label();
        scrollPane9 = new widget.ScrollPane();
        PemeriksaanRad = new widget.TextArea();
        jLabel23 = new widget.Label();
        scrollPane10 = new widget.ScrollPane();
        HasilLaborat1 = new widget.TextArea();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        DiagnosaSekunder8 = new widget.TextBox();
        jLabel42 = new widget.Label();
        KodeDiagnosaUtama1 = new widget.TextBox();
        KodeDiagnosaSekunder5 = new widget.TextBox();
        KodeDiagnosaSekunder6 = new widget.TextBox();
        KodeDiagnosaSekunder7 = new widget.TextBox();
        KodeDiagnosaSekunder8 = new widget.TextBox();
        jLabel43 = new widget.Label();
        ProsedurUtama1 = new widget.TextBox();
        KodeProsedurUtama1 = new widget.TextBox();
        ProsedurSekunder4 = new widget.TextBox();
        jLabel44 = new widget.Label();
        KodeProsedurSekunder4 = new widget.TextBox();
        jLabel45 = new widget.Label();
        ProsedurSekunder5 = new widget.TextBox();
        KodeProsedurSekunder5 = new widget.TextBox();
        KodeProsedurSekunder6 = new widget.TextBox();
        ProsedurSekunder6 = new widget.TextBox();
        jLabel46 = new widget.Label();
        label15 = new widget.Label();
        KodeDokter1 = new widget.TextBox();
        NamaDokter1 = new widget.TextBox();
        BtnDokter6 = new widget.Button();
        BtnDokter7 = new widget.Button();
        BtnDokter8 = new widget.Button();
        BtnDokter9 = new widget.Button();
        jLabel47 = new widget.Label();
        CaraKeluar = new widget.ComboBox();
        jLabel48 = new widget.Label();
        Keadaan = new widget.ComboBox();
        BtnDokter10 = new widget.Button();
        jLabel24 = new widget.Label();
        KdRuang = new widget.TextBox();
        jLabel49 = new widget.Label();
        Masuk = new widget.TextBox();
        jLabel50 = new widget.Label();
        Keluar = new widget.TextBox();
        jLabel51 = new widget.Label();
        JamMasuk = new widget.TextBox();
        jLabel52 = new widget.Label();
        JamKeluar = new widget.TextBox();
        jLabel53 = new widget.Label();
        KdPj = new widget.TextBox();
        label16 = new widget.Label();
        KodeDokterPengirim = new widget.TextBox();
        NamaDokterPengirim = new widget.TextBox();
        jLabel54 = new widget.Label();
        Alasan = new widget.TextBox();
        jLabel55 = new widget.Label();
        DiagnosaAwal = new widget.TextBox();
        jLabel56 = new widget.Label();
        BtnDokter16 = new widget.Button();
        scrollPane11 = new widget.ScrollPane();
        TindakanSelamaDiRS = new widget.TextArea();
        jLabel57 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel58 = new widget.Label();
        KetKeluar = new widget.TextBox();
        jLabel59 = new widget.Label();
        BtnDokter17 = new widget.Button();
        scrollPane12 = new widget.ScrollPane();
        Diet = new widget.TextArea();
        jLabel60 = new widget.Label();
        scrollPane13 = new widget.ScrollPane();
        Edukasi = new widget.TextArea();
        KetKeadaanPulang = new widget.TextBox();
        jLabel61 = new widget.Label();
        DIlanjutkan = new widget.ComboBox();
        KetDilanjutkan = new widget.TextBox();
        Kontrol = new widget.Tanggal();
        label13 = new widget.Label();
        label17 = new widget.Label();
        CaraBayar = new widget.TextBox();
        NmRuang = new widget.TextBox();
        BtnDokter18 = new widget.Button();
        scrollPane14 = new widget.ScrollPane();
        LabBelum = new widget.TextArea();
        jLabel62 = new widget.Label();
        scrollPane15 = new widget.ScrollPane();
        JalannyaPenyakit1 = new widget.TextArea();
        scrollPane16 = new widget.ScrollPane();
        ObatPulang = new widget.TextArea();
        BtnDokter19 = new widget.Button();
        BtnDokter20 = new widget.Button();
        scrollPane17 = new widget.ScrollPane();
        ObatSelamaDiRS = new widget.TextArea();
        jLabel63 = new widget.Label();
        jInternalFrame4 = new javax.swing.JInternalFrame();
        jPanel4 = new javax.swing.JPanel();
        Scroll2 = new widget.ScrollPane();
        jPanel2 = new javax.swing.JPanel();
        txtPdfTambahan = new javax.swing.JButton();
        txtScanTambahan = new javax.swing.JButton();
        txtMergedPdf = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        panelGlass5 = new widget.panelisi();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        BtnCari = new widget.Button();
        BtnPrintSemua = new widget.Button();
        BtnAttachment = new widget.Button();
        BtnDeletePdf = new widget.Button();
        BtnScan = new widget.Button();
        BtnDeleteScan = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnDeleteMergedPdf = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        txtNorawat = new widget.TextBox();
        jLabel6 = new widget.Label();
        txtNoRm = new widget.TextBox();
        jLabel7 = new widget.Label();
        txtSep = new widget.TextBox();
        BtnPrint1 = new widget.Button();

        temp_sep.setText("jTextField1");
        temp_sep.setName("temp_sep"); // NOI18N

        btnSuratKontrol.setText("jButton1");
        btnSuratKontrol.setName("btnSuratKontrol"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Menu Penjaminan");
        setMinimumSize(new java.awt.Dimension(1424, 750));
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu Verifikator"));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(490, 750));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jInternalFrame1.setTitle("Preview Laboratorium");
        jInternalFrame1.setToolTipText("");
        jInternalFrame1.setName("jInternalFrame1"); // NOI18N
        jInternalFrame1.setVisible(true);

        jPanel1.setMinimumSize(new java.awt.Dimension(470, 500));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(480, 500));
        jPanel1.setLayout(new java.awt.BorderLayout());

        Scroll.setBorder(javax.swing.BorderFactory.createTitledBorder("List Penunjang"));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(460, 200));

        tbLab.setAutoCreateRowSorter(true);
        tbLab.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbLab.setName("tbLab"); // NOI18N
        tbLab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLabMouseClicked(evt);
            }
        });
        tbLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbLabKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbLab);

        jPanel1.add(Scroll, java.awt.BorderLayout.LINE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview Data"));
        jPanel9.setMinimumSize(new java.awt.Dimension(20, 500));
        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(20, 500));
        jPanel1.add(jPanel9, java.awt.BorderLayout.CENTER);

        jInternalFrame1.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Penunjang Lab", jInternalFrame1);

        jInternalFrame2.setTitle("Preview Radiologi");
        jInternalFrame2.setName("jInternalFrame2"); // NOI18N
        jInternalFrame2.setVisible(true);

        jPanel3.setMinimumSize(new java.awt.Dimension(470, 500));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setLayout(new java.awt.BorderLayout());

        Scroll1.setBorder(javax.swing.BorderFactory.createTitledBorder("List Penunjang"));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(460, 200));

        tbRad.setAutoCreateRowSorter(true);
        tbRad.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRad.setName("tbRad"); // NOI18N
        tbRad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRadMouseClicked(evt);
            }
        });
        tbRad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRadKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRad);

        jPanel3.add(Scroll1, java.awt.BorderLayout.LINE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview Data"));
        jPanel8.setMinimumSize(new java.awt.Dimension(20, 500));
        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(20, 500));
        jPanel3.add(jPanel8, java.awt.BorderLayout.CENTER);

        jInternalFrame2.getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Penunjang Rad", jInternalFrame2);

        jInternalFrame3.setTitle("Form Resume Medis");
        jInternalFrame3.setToolTipText("");
        jInternalFrame3.setName("jInternalFrame3"); // NOI18N
        jInternalFrame3.setVisible(true);

        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane2.setName("jTabbedPane2"); // NOI18N

        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(102, 500));
        jPanel5.setLayout(new java.awt.BorderLayout());

        scrollInput.setName("scrollInput"); // NOI18N

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 651));
        FormInput.setLayout(null);

        jLabel4.setText("Keluhan Utama Riwayat Penyakit Yang Positif :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 70, 240, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(361, 10, 424, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 10, 112, 23);

        jLabel25.setText("Diagnosa Sekunder 2 :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 380, 145, 23);

        DiagnosaSekunder2.setHighlighter(null);
        DiagnosaSekunder2.setName("DiagnosaSekunder2"); // NOI18N
        DiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder2);
        DiagnosaSekunder2.setBounds(149, 380, 520, 23);

        jLabel26.setText("Diagnosa Sekunder 3 :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 410, 145, 23);

        DiagnosaUtama.setHighlighter(null);
        DiagnosaUtama.setName("DiagnosaUtama"); // NOI18N
        DiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaUtama);
        DiagnosaUtama.setBounds(149, 320, 520, 23);

        jLabel27.setText("Diagnosa Utama :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 320, 145, 23);

        DiagnosaSekunder3.setHighlighter(null);
        DiagnosaSekunder3.setName("DiagnosaSekunder3"); // NOI18N
        DiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder3);
        DiagnosaSekunder3.setBounds(149, 410, 520, 23);

        jLabel28.setText("Diagnosa Sekunder 4 :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 440, 145, 23);

        DiagnosaSekunder4.setHighlighter(null);
        DiagnosaSekunder4.setName("DiagnosaSekunder4"); // NOI18N
        DiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder4KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder4);
        DiagnosaSekunder4.setBounds(149, 440, 520, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Keluhan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Keluhan.setColumns(20);
        Keluhan.setRows(5);
        Keluhan.setName("Keluhan"); // NOI18N
        Keluhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Keluhan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(244, 70, 541, 50);

        jLabel11.setText("No.Rawat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 10, 100, 23);

        jLabel14.setText("Jalannya Penyakit Selama Perawatan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 127, 240, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        JalannyaPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JalannyaPenyakit.setColumns(20);
        JalannyaPenyakit.setRows(5);
        JalannyaPenyakit.setName("JalannyaPenyakit"); // NOI18N
        JalannyaPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalannyaPenyakitKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(JalannyaPenyakit);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(244, 127, 541, 50);

        jLabel15.setText("Pemeriksaan Penunjang Yang Positif :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 184, 240, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        PemeriksaanPenunjang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanPenunjang.setColumns(20);
        PemeriksaanPenunjang.setRows(5);
        PemeriksaanPenunjang.setName("PemeriksaanPenunjang"); // NOI18N
        PemeriksaanPenunjang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanPenunjangKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(PemeriksaanPenunjang);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(244, 184, 541, 50);

        jLabel16.setText("Hasil Laboratorium Yang Positif :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 241, 240, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        HasilLaborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilLaborat.setColumns(20);
        HasilLaborat.setRows(5);
        HasilLaborat.setName("HasilLaborat"); // NOI18N
        HasilLaborat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilLaboratKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(HasilLaborat);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(244, 241, 541, 50);

        jLabel17.setText("Obat-obatan Waktu Pulang/Nasihat :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 590, 240, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Obat2an.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Obat2an.setColumns(20);
        Obat2an.setRows(5);
        Obat2an.setName("Obat2an"); // NOI18N
        Obat2an.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat2anKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Obat2an);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(244, 590, 541, 50);

        jLabel29.setText("Diagnosa Akhir :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 297, 97, 23);

        jLabel30.setText("Diagnosa Sekunder 1 :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 350, 145, 23);

        DiagnosaSekunder1.setHighlighter(null);
        DiagnosaSekunder1.setName("DiagnosaSekunder1"); // NOI18N
        DiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaSekunder1);
        DiagnosaSekunder1.setBounds(149, 350, 520, 23);

        jLabel31.setText("Kode ICD :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(530, 297, 210, 23);

        KodeDiagnosaUtama.setHighlighter(null);
        KodeDiagnosaUtama.setName("KodeDiagnosaUtama"); // NOI18N
        KodeDiagnosaUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaUtama);
        KodeDiagnosaUtama.setBounds(710, 320, 75, 23);

        KodeDiagnosaSekunder1.setHighlighter(null);
        KodeDiagnosaSekunder1.setName("KodeDiagnosaSekunder1"); // NOI18N
        KodeDiagnosaSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder1);
        KodeDiagnosaSekunder1.setBounds(710, 350, 75, 23);

        KodeDiagnosaSekunder2.setHighlighter(null);
        KodeDiagnosaSekunder2.setName("KodeDiagnosaSekunder2"); // NOI18N
        KodeDiagnosaSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder2);
        KodeDiagnosaSekunder2.setBounds(710, 380, 75, 23);

        KodeDiagnosaSekunder3.setHighlighter(null);
        KodeDiagnosaSekunder3.setName("KodeDiagnosaSekunder3"); // NOI18N
        KodeDiagnosaSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder3);
        KodeDiagnosaSekunder3.setBounds(710, 410, 75, 23);

        KodeDiagnosaSekunder4.setHighlighter(null);
        KodeDiagnosaSekunder4.setName("KodeDiagnosaSekunder4"); // NOI18N
        KodeDiagnosaSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder4KeyPressed(evt);
            }
        });
        FormInput.add(KodeDiagnosaSekunder4);
        KodeDiagnosaSekunder4.setBounds(710, 440, 75, 23);

        jLabel32.setText("Prosedur Utama :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(0, 470, 145, 23);

        ProsedurUtama.setHighlighter(null);
        ProsedurUtama.setName("ProsedurUtama"); // NOI18N
        ProsedurUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurUtamaKeyPressed(evt);
            }
        });
        FormInput.add(ProsedurUtama);
        ProsedurUtama.setBounds(149, 470, 520, 23);

        KodeProsedurUtama.setHighlighter(null);
        KodeProsedurUtama.setName("KodeProsedurUtama"); // NOI18N
        KodeProsedurUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurUtamaKeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurUtama);
        KodeProsedurUtama.setBounds(710, 470, 75, 23);

        ProsedurSekunder1.setHighlighter(null);
        ProsedurSekunder1.setName("ProsedurSekunder1"); // NOI18N
        ProsedurSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(ProsedurSekunder1);
        ProsedurSekunder1.setBounds(149, 500, 520, 23);

        jLabel33.setText("Prosedur Sekunder 1 :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 500, 145, 23);

        KodeProsedurSekunder1.setHighlighter(null);
        KodeProsedurSekunder1.setName("KodeProsedurSekunder1"); // NOI18N
        KodeProsedurSekunder1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder1KeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurSekunder1);
        KodeProsedurSekunder1.setBounds(710, 500, 75, 23);

        jLabel34.setText("Prosedur Sekunder 2 :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 530, 145, 23);

        ProsedurSekunder2.setHighlighter(null);
        ProsedurSekunder2.setName("ProsedurSekunder2"); // NOI18N
        ProsedurSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(ProsedurSekunder2);
        ProsedurSekunder2.setBounds(149, 530, 520, 23);

        KodeProsedurSekunder2.setHighlighter(null);
        KodeProsedurSekunder2.setName("KodeProsedurSekunder2"); // NOI18N
        KodeProsedurSekunder2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder2KeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurSekunder2);
        KodeProsedurSekunder2.setBounds(710, 530, 75, 23);

        KodeProsedurSekunder3.setHighlighter(null);
        KodeProsedurSekunder3.setName("KodeProsedurSekunder3"); // NOI18N
        KodeProsedurSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(KodeProsedurSekunder3);
        KodeProsedurSekunder3.setBounds(710, 560, 75, 23);

        ProsedurSekunder3.setHighlighter(null);
        ProsedurSekunder3.setName("ProsedurSekunder3"); // NOI18N
        ProsedurSekunder3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder3KeyPressed(evt);
            }
        });
        FormInput.add(ProsedurSekunder3);
        ProsedurSekunder3.setBounds(149, 560, 520, 23);

        jLabel35.setText("Prosedur Sekunder 3 :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 560, 145, 23);

        label14.setText("Dokter P.J. :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 40, 100, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setName("KodeDokter"); // NOI18N
        KodeDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterKeyPressed(evt);
            }
        });
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(104, 40, 141, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        NamaDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(247, 40, 270, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(519, 40, 28, 23);

        Kondisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hidup", "Meninggal" }));
        Kondisi.setName("Kondisi"); // NOI18N
        Kondisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiKeyPressed(evt);
            }
        });
        FormInput.add(Kondisi);
        Kondisi.setBounds(685, 40, 100, 23);

        jLabel18.setText("Kondisi Pasien Pulang :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(551, 40, 130, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('2');
        BtnDokter1.setToolTipText("Alt+2");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter1);
        BtnDokter1.setBounds(180, 96, 28, 23);

        BtnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter2.setMnemonic('2');
        BtnDokter2.setToolTipText("Alt+2");
        BtnDokter2.setName("BtnDokter2"); // NOI18N
        BtnDokter2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter2);
        BtnDokter2.setBounds(212, 210, 28, 23);

        BtnDokter3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter3.setMnemonic('2');
        BtnDokter3.setToolTipText("Alt+2");
        BtnDokter3.setName("BtnDokter3"); // NOI18N
        BtnDokter3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter3);
        BtnDokter3.setBounds(212, 267, 28, 23);

        BtnDokter4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter4.setMnemonic('2');
        BtnDokter4.setToolTipText("Alt+2");
        BtnDokter4.setName("BtnDokter4"); // NOI18N
        BtnDokter4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter4);
        BtnDokter4.setBounds(212, 616, 28, 23);

        BtnDokter5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter5.setMnemonic('2');
        BtnDokter5.setToolTipText("Alt+2");
        BtnDokter5.setName("BtnDokter5"); // NOI18N
        BtnDokter5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter5);
        BtnDokter5.setBounds(212, 96, 28, 23);

        scrollInput.setViewportView(FormInput);

        jPanel5.add(scrollInput, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Resume Medis Rawat Jalan", jPanel5);

        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(102, 500));
        jPanel6.setLayout(new java.awt.BorderLayout());

        scrollInput1.setName("scrollInput1"); // NOI18N

        FormInput1.setBackground(new java.awt.Color(250, 255, 245));
        FormInput1.setBorder(null);
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(100, 1172));
        FormInput1.setLayout(null);

        jLabel19.setText("Keluhan Utama Riwayat Penyakit :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput1.add(jLabel19);
        jLabel19.setBounds(0, 160, 220, 23);

        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRw1KeyPressed(evt);
            }
        });
        FormInput1.add(TNoRw1);
        TNoRw1.setBounds(104, 10, 141, 23);

        TPasien1.setEditable(false);
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasien1KeyPressed(evt);
            }
        });
        FormInput1.add(TPasien1);
        TPasien1.setBounds(361, 10, 424, 23);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRM1KeyPressed(evt);
            }
        });
        FormInput1.add(TNoRM1);
        TNoRM1.setBounds(247, 10, 112, 23);

        jLabel36.setText("Diagnosa Sekunder 2 :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput1.add(jLabel36);
        jLabel36.setBounds(0, 640, 145, 23);

        DiagnosaSekunder5.setHighlighter(null);
        DiagnosaSekunder5.setName("DiagnosaSekunder5"); // NOI18N
        DiagnosaSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder5KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaSekunder5);
        DiagnosaSekunder5.setBounds(150, 640, 520, 23);

        jLabel37.setText("Diagnosa Sekunder 3 :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput1.add(jLabel37);
        jLabel37.setBounds(0, 670, 145, 23);

        DiagnosaUtama1.setHighlighter(null);
        DiagnosaUtama1.setName("DiagnosaUtama1"); // NOI18N
        DiagnosaUtama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaUtama1KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaUtama1);
        DiagnosaUtama1.setBounds(150, 580, 520, 23);

        jLabel38.setText("Diagnosa Utama :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput1.add(jLabel38);
        jLabel38.setBounds(0, 580, 145, 23);

        DiagnosaSekunder6.setHighlighter(null);
        DiagnosaSekunder6.setName("DiagnosaSekunder6"); // NOI18N
        DiagnosaSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder6KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaSekunder6);
        DiagnosaSekunder6.setBounds(150, 670, 520, 23);

        jLabel39.setText("Diagnosa Sekunder 4 :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput1.add(jLabel39);
        jLabel39.setBounds(0, 700, 145, 23);

        DiagnosaSekunder7.setHighlighter(null);
        DiagnosaSekunder7.setName("DiagnosaSekunder7"); // NOI18N
        DiagnosaSekunder7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder7KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaSekunder7);
        DiagnosaSekunder7.setBounds(150, 700, 520, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(KeluhanUtama);

        FormInput1.add(scrollPane7);
        scrollPane7.setBounds(224, 160, 561, 50);

        jLabel20.setText("No.Rawat :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput1.add(jLabel20);
        jLabel20.setBounds(0, 10, 100, 23);

        jLabel21.setText("Pemeriksaan Fisik :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput1.add(jLabel21);
        jLabel21.setBounds(0, 217, 220, 23);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane8.setName("scrollPane8"); // NOI18N

        PemeriksaanFisik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanFisik.setColumns(20);
        PemeriksaanFisik.setRows(5);
        PemeriksaanFisik.setName("PemeriksaanFisik"); // NOI18N
        PemeriksaanFisik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanFisikKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(PemeriksaanFisik);

        FormInput1.add(scrollPane8);
        scrollPane8.setBounds(224, 217, 561, 50);

        jLabel22.setText("Pemeriksaan Penunjang Rad Terpenting :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput1.add(jLabel22);
        jLabel22.setBounds(0, 331, 220, 23);

        scrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane9.setName("scrollPane9"); // NOI18N

        PemeriksaanRad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        PemeriksaanRad.setColumns(20);
        PemeriksaanRad.setRows(5);
        PemeriksaanRad.setName("PemeriksaanRad"); // NOI18N
        PemeriksaanRad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanRadKeyPressed(evt);
            }
        });
        scrollPane9.setViewportView(PemeriksaanRad);

        FormInput1.add(scrollPane9);
        scrollPane9.setBounds(224, 331, 561, 50);

        jLabel23.setText("Pemeriksaan Penunjang Lab Terpenting :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput1.add(jLabel23);
        jLabel23.setBounds(0, 388, 220, 23);

        scrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane10.setName("scrollPane10"); // NOI18N

        HasilLaborat1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilLaborat1.setColumns(20);
        HasilLaborat1.setRows(5);
        HasilLaborat1.setName("HasilLaborat1"); // NOI18N
        HasilLaborat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilLaborat1KeyPressed(evt);
            }
        });
        scrollPane10.setViewportView(HasilLaborat1);

        FormInput1.add(scrollPane10);
        scrollPane10.setBounds(224, 388, 561, 50);

        jLabel40.setText("Diagnosa Akhir :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput1.add(jLabel40);
        jLabel40.setBounds(0, 560, 100, 23);

        jLabel41.setText("Diagnosa Sekunder 1 :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput1.add(jLabel41);
        jLabel41.setBounds(0, 610, 145, 23);

        DiagnosaSekunder8.setHighlighter(null);
        DiagnosaSekunder8.setName("DiagnosaSekunder8"); // NOI18N
        DiagnosaSekunder8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaSekunder8KeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaSekunder8);
        DiagnosaSekunder8.setBounds(150, 610, 520, 23);

        jLabel42.setText("Kode ICD :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput1.add(jLabel42);
        jLabel42.setBounds(530, 560, 210, 23);

        KodeDiagnosaUtama1.setHighlighter(null);
        KodeDiagnosaUtama1.setName("KodeDiagnosaUtama1"); // NOI18N
        KodeDiagnosaUtama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaUtama1KeyPressed(evt);
            }
        });
        FormInput1.add(KodeDiagnosaUtama1);
        KodeDiagnosaUtama1.setBounds(710, 580, 75, 23);

        KodeDiagnosaSekunder5.setHighlighter(null);
        KodeDiagnosaSekunder5.setName("KodeDiagnosaSekunder5"); // NOI18N
        KodeDiagnosaSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder5KeyPressed(evt);
            }
        });
        FormInput1.add(KodeDiagnosaSekunder5);
        KodeDiagnosaSekunder5.setBounds(710, 610, 75, 23);

        KodeDiagnosaSekunder6.setHighlighter(null);
        KodeDiagnosaSekunder6.setName("KodeDiagnosaSekunder6"); // NOI18N
        KodeDiagnosaSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder6KeyPressed(evt);
            }
        });
        FormInput1.add(KodeDiagnosaSekunder6);
        KodeDiagnosaSekunder6.setBounds(710, 640, 75, 23);

        KodeDiagnosaSekunder7.setHighlighter(null);
        KodeDiagnosaSekunder7.setName("KodeDiagnosaSekunder7"); // NOI18N
        KodeDiagnosaSekunder7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder7KeyPressed(evt);
            }
        });
        FormInput1.add(KodeDiagnosaSekunder7);
        KodeDiagnosaSekunder7.setBounds(710, 670, 75, 23);

        KodeDiagnosaSekunder8.setHighlighter(null);
        KodeDiagnosaSekunder8.setName("KodeDiagnosaSekunder8"); // NOI18N
        KodeDiagnosaSekunder8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDiagnosaSekunder8KeyPressed(evt);
            }
        });
        FormInput1.add(KodeDiagnosaSekunder8);
        KodeDiagnosaSekunder8.setBounds(710, 700, 75, 23);

        jLabel43.setText("Prosedur Utama :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput1.add(jLabel43);
        jLabel43.setBounds(0, 730, 145, 23);

        ProsedurUtama1.setHighlighter(null);
        ProsedurUtama1.setName("ProsedurUtama1"); // NOI18N
        ProsedurUtama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurUtama1KeyPressed(evt);
            }
        });
        FormInput1.add(ProsedurUtama1);
        ProsedurUtama1.setBounds(150, 730, 520, 23);

        KodeProsedurUtama1.setHighlighter(null);
        KodeProsedurUtama1.setName("KodeProsedurUtama1"); // NOI18N
        KodeProsedurUtama1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurUtama1KeyPressed(evt);
            }
        });
        FormInput1.add(KodeProsedurUtama1);
        KodeProsedurUtama1.setBounds(710, 730, 75, 23);

        ProsedurSekunder4.setHighlighter(null);
        ProsedurSekunder4.setName("ProsedurSekunder4"); // NOI18N
        ProsedurSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder4KeyPressed(evt);
            }
        });
        FormInput1.add(ProsedurSekunder4);
        ProsedurSekunder4.setBounds(150, 760, 520, 23);

        jLabel44.setText("Prosedur Sekunder 1 :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput1.add(jLabel44);
        jLabel44.setBounds(0, 760, 145, 23);

        KodeProsedurSekunder4.setHighlighter(null);
        KodeProsedurSekunder4.setName("KodeProsedurSekunder4"); // NOI18N
        KodeProsedurSekunder4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder4KeyPressed(evt);
            }
        });
        FormInput1.add(KodeProsedurSekunder4);
        KodeProsedurSekunder4.setBounds(710, 760, 75, 23);

        jLabel45.setText("Prosedur Sekunder 2 :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput1.add(jLabel45);
        jLabel45.setBounds(0, 790, 145, 23);

        ProsedurSekunder5.setHighlighter(null);
        ProsedurSekunder5.setName("ProsedurSekunder5"); // NOI18N
        ProsedurSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder5KeyPressed(evt);
            }
        });
        FormInput1.add(ProsedurSekunder5);
        ProsedurSekunder5.setBounds(150, 790, 520, 23);

        KodeProsedurSekunder5.setHighlighter(null);
        KodeProsedurSekunder5.setName("KodeProsedurSekunder5"); // NOI18N
        KodeProsedurSekunder5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder5KeyPressed(evt);
            }
        });
        FormInput1.add(KodeProsedurSekunder5);
        KodeProsedurSekunder5.setBounds(710, 790, 75, 23);

        KodeProsedurSekunder6.setHighlighter(null);
        KodeProsedurSekunder6.setName("KodeProsedurSekunder6"); // NOI18N
        KodeProsedurSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeProsedurSekunder6KeyPressed(evt);
            }
        });
        FormInput1.add(KodeProsedurSekunder6);
        KodeProsedurSekunder6.setBounds(710, 820, 75, 23);

        ProsedurSekunder6.setHighlighter(null);
        ProsedurSekunder6.setName("ProsedurSekunder6"); // NOI18N
        ProsedurSekunder6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurSekunder6KeyPressed(evt);
            }
        });
        FormInput1.add(ProsedurSekunder6);
        ProsedurSekunder6.setBounds(150, 820, 520, 23);

        jLabel46.setText("Prosedur Sekunder 3 :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput1.add(jLabel46);
        jLabel46.setBounds(0, 820, 145, 23);

        label15.setText("Dokter P.J. :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label15);
        label15.setBounds(0, 40, 100, 23);

        KodeDokter1.setEditable(false);
        KodeDokter1.setName("KodeDokter1"); // NOI18N
        KodeDokter1.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokter1KeyPressed(evt);
            }
        });
        FormInput1.add(KodeDokter1);
        KodeDokter1.setBounds(104, 40, 100, 23);

        NamaDokter1.setEditable(false);
        NamaDokter1.setName("NamaDokter1"); // NOI18N
        NamaDokter1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NamaDokter1);
        NamaDokter1.setBounds(206, 40, 200, 23);

        BtnDokter6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter6.setMnemonic('2');
        BtnDokter6.setToolTipText("Alt+2");
        BtnDokter6.setName("BtnDokter6"); // NOI18N
        BtnDokter6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter6ActionPerformed(evt);
            }
        });
        BtnDokter6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter6KeyPressed(evt);
            }
        });
        FormInput1.add(BtnDokter6);
        BtnDokter6.setBounds(409, 40, 28, 23);

        BtnDokter7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter7.setMnemonic('2');
        BtnDokter7.setToolTipText("Alt+2");
        BtnDokter7.setName("BtnDokter7"); // NOI18N
        BtnDokter7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter7ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter7);
        BtnDokter7.setBounds(192, 186, 28, 23);

        BtnDokter8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter8.setMnemonic('2');
        BtnDokter8.setToolTipText("Alt+2");
        BtnDokter8.setName("BtnDokter8"); // NOI18N
        BtnDokter8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter8ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter8);
        BtnDokter8.setBounds(192, 357, 28, 23);

        BtnDokter9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter9.setMnemonic('2');
        BtnDokter9.setToolTipText("Alt+2");
        BtnDokter9.setName("BtnDokter9"); // NOI18N
        BtnDokter9.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter9ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter9);
        BtnDokter9.setBounds(192, 414, 28, 23);

        jLabel47.setText("Cara Keluar :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput1.add(jLabel47);
        jLabel47.setBounds(379, 1051, 70, 23);

        CaraKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas Izin Dokter", "Pindah RS", "Pulang Atas Permintaan Sendiri", "Lainnya" }));
        CaraKeluar.setName("CaraKeluar"); // NOI18N
        CaraKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraKeluarKeyPressed(evt);
            }
        });
        FormInput1.add(CaraKeluar);
        CaraKeluar.setBounds(453, 1051, 205, 23);

        jLabel48.setText("Keadaan Pulang :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput1.add(jLabel48);
        jLabel48.setBounds(0, 1051, 100, 23);

        Keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Membaik", "Sembuh", "Keadaan Khusus", "Meninggal" }));
        Keadaan.setName("Keadaan"); // NOI18N
        Keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanKeyPressed(evt);
            }
        });
        FormInput1.add(Keadaan);
        Keadaan.setBounds(104, 1051, 130, 23);

        BtnDokter10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter10.setMnemonic('2');
        BtnDokter10.setToolTipText("Alt+2");
        BtnDokter10.setName("BtnDokter10"); // NOI18N
        BtnDokter10.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter10ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter10);
        BtnDokter10.setBounds(192, 243, 28, 23);

        jLabel24.setText("Bangsal/Kamar :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput1.add(jLabel24);
        jLabel24.setBounds(445, 40, 90, 23);

        KdRuang.setEditable(false);
        KdRuang.setHighlighter(null);
        KdRuang.setName("KdRuang"); // NOI18N
        FormInput1.add(KdRuang);
        KdRuang.setBounds(539, 40, 75, 23);

        jLabel49.setText("Tanggal Masuk :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput1.add(jLabel49);
        jLabel49.setBounds(0, 100, 100, 23);

        Masuk.setEditable(false);
        Masuk.setHighlighter(null);
        Masuk.setName("Masuk"); // NOI18N
        FormInput1.add(Masuk);
        Masuk.setBounds(104, 100, 80, 23);

        jLabel50.setText("Tanggal Keluar :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput1.add(jLabel50);
        jLabel50.setBounds(0, 130, 100, 23);

        Keluar.setEditable(false);
        Keluar.setHighlighter(null);
        Keluar.setName("Keluar"); // NOI18N
        FormInput1.add(Keluar);
        Keluar.setBounds(104, 130, 80, 23);

        jLabel51.setText("Jam Masuk :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput1.add(jLabel51);
        jLabel51.setBounds(185, 100, 70, 23);

        JamMasuk.setEditable(false);
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        FormInput1.add(JamMasuk);
        JamMasuk.setBounds(259, 100, 70, 23);

        jLabel52.setText("Jam Keluar :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput1.add(jLabel52);
        jLabel52.setBounds(185, 130, 70, 23);

        JamKeluar.setEditable(false);
        JamKeluar.setHighlighter(null);
        JamKeluar.setName("JamKeluar"); // NOI18N
        FormInput1.add(JamKeluar);
        JamKeluar.setBounds(259, 130, 70, 23);

        jLabel53.setText("Cara Bayar :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput1.add(jLabel53);
        jLabel53.setBounds(445, 70, 90, 23);

        KdPj.setEditable(false);
        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N
        FormInput1.add(KdPj);
        KdPj.setBounds(539, 70, 50, 23);

        label16.setText("Dokter Pengirim :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label16);
        label16.setBounds(0, 70, 100, 23);

        KodeDokterPengirim.setEditable(false);
        KodeDokterPengirim.setName("KodeDokterPengirim"); // NOI18N
        KodeDokterPengirim.setPreferredSize(new java.awt.Dimension(80, 23));
        KodeDokterPengirim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterPengirimKeyPressed(evt);
            }
        });
        FormInput1.add(KodeDokterPengirim);
        KodeDokterPengirim.setBounds(104, 70, 100, 23);

        NamaDokterPengirim.setEditable(false);
        NamaDokterPengirim.setName("NamaDokterPengirim"); // NOI18N
        NamaDokterPengirim.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NamaDokterPengirim);
        NamaDokterPengirim.setBounds(206, 70, 231, 23);

        jLabel54.setText("Alasan Masuk Dirawat :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput1.add(jLabel54);
        jLabel54.setBounds(351, 130, 120, 23);

        Alasan.setHighlighter(null);
        Alasan.setName("Alasan"); // NOI18N
        Alasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanKeyPressed(evt);
            }
        });
        FormInput1.add(Alasan);
        Alasan.setBounds(475, 130, 310, 23);

        jLabel55.setText("Diagnosa Awal Masuk :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput1.add(jLabel55);
        jLabel55.setBounds(351, 100, 120, 23);

        DiagnosaAwal.setHighlighter(null);
        DiagnosaAwal.setName("DiagnosaAwal"); // NOI18N
        DiagnosaAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaAwalKeyPressed(evt);
            }
        });
        FormInput1.add(DiagnosaAwal);
        DiagnosaAwal.setBounds(475, 100, 310, 23);

        jLabel56.setText("Tindakan/Operasi Selama Perawatan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput1.add(jLabel56);
        jLabel56.setBounds(0, 445, 220, 23);

        BtnDokter16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter16.setMnemonic('2');
        BtnDokter16.setToolTipText("Alt+2");
        BtnDokter16.setName("BtnDokter16"); // NOI18N
        BtnDokter16.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter16ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter16);
        BtnDokter16.setBounds(192, 471, 28, 23);

        scrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane11.setName("scrollPane11"); // NOI18N

        TindakanSelamaDiRS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TindakanSelamaDiRS.setColumns(20);
        TindakanSelamaDiRS.setRows(5);
        TindakanSelamaDiRS.setName("TindakanSelamaDiRS"); // NOI18N
        TindakanSelamaDiRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanSelamaDiRSKeyPressed(evt);
            }
        });
        scrollPane11.setViewportView(TindakanSelamaDiRS);

        FormInput1.add(scrollPane11);
        scrollPane11.setBounds(224, 445, 561, 50);

        jLabel57.setText("Alergi Obat :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput1.add(jLabel57);
        jLabel57.setBounds(0, 850, 100, 23);

        Alergi.setHighlighter(null);
        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput1.add(Alergi);
        Alergi.setBounds(104, 850, 681, 23);

        jLabel58.setText("Diet :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput1.add(jLabel58);
        jLabel58.setBounds(0, 880, 100, 23);

        KetKeluar.setHighlighter(null);
        KetKeluar.setName("KetKeluar"); // NOI18N
        KetKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKeluarKeyPressed(evt);
            }
        });
        FormInput1.add(KetKeluar);
        KetKeluar.setBounds(660, 1051, 125, 23);

        jLabel59.setText("Hasil Lab Yang Belum Selesai (Pending) :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput1.add(jLabel59);
        jLabel59.setBounds(0, 937, 230, 23);

        BtnDokter17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter17.setMnemonic('2');
        BtnDokter17.setToolTipText("Alt+2");
        BtnDokter17.setName("BtnDokter17"); // NOI18N
        BtnDokter17.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter17ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter17);
        BtnDokter17.setBounds(202, 963, 28, 23);

        scrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane12.setName("scrollPane12"); // NOI18N

        Diet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Diet.setColumns(20);
        Diet.setRows(5);
        Diet.setName("Diet"); // NOI18N
        Diet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DietKeyPressed(evt);
            }
        });
        scrollPane12.setViewportView(Diet);

        FormInput1.add(scrollPane12);
        scrollPane12.setBounds(104, 880, 681, 50);

        jLabel60.setText("Instruksi/Anjuran Dan Edukasi (Follow Up) :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput1.add(jLabel60);
        jLabel60.setBounds(0, 994, 230, 23);

        scrollPane13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane13.setName("scrollPane13"); // NOI18N

        Edukasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Edukasi.setColumns(20);
        Edukasi.setRows(5);
        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        scrollPane13.setViewportView(Edukasi);

        FormInput1.add(scrollPane13);
        scrollPane13.setBounds(234, 994, 551, 50);

        KetKeadaanPulang.setHighlighter(null);
        KetKeadaanPulang.setName("KetKeadaanPulang"); // NOI18N
        KetKeadaanPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetKeadaanPulangKeyPressed(evt);
            }
        });
        FormInput1.add(KetKeadaanPulang);
        KetKeadaanPulang.setBounds(236, 1051, 130, 23);

        jLabel61.setText("Dilanjutkan :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput1.add(jLabel61);
        jLabel61.setBounds(0, 1081, 100, 23);

        DIlanjutkan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kembali Ke RS", "RS Lain", "Dokter Luar", "Puskesmes", "Lainnya" }));
        DIlanjutkan.setName("DIlanjutkan"); // NOI18N
        DIlanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DIlanjutkanKeyPressed(evt);
            }
        });
        FormInput1.add(DIlanjutkan);
        DIlanjutkan.setBounds(104, 1081, 130, 23);

        KetDilanjutkan.setHighlighter(null);
        KetDilanjutkan.setName("KetDilanjutkan"); // NOI18N
        KetDilanjutkan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetDilanjutkanKeyPressed(evt);
            }
        });
        FormInput1.add(KetDilanjutkan);
        KetDilanjutkan.setBounds(236, 1081, 270, 23);

        Kontrol.setForeground(new java.awt.Color(50, 70, 50));
        Kontrol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-01-2025 15:01:44" }));
        Kontrol.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Kontrol.setName("Kontrol"); // NOI18N
        Kontrol.setOpaque(false);
        Kontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KontrolKeyPressed(evt);
            }
        });
        FormInput1.add(Kontrol);
        Kontrol.setBounds(650, 1081, 135, 23);

        label13.setText("Tanggal & Jam Kontrol :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label13);
        label13.setBounds(516, 1081, 130, 23);

        label17.setText("Obat Pulang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label17);
        label17.setBounds(0, 1111, 100, 23);

        CaraBayar.setEditable(false);
        CaraBayar.setHighlighter(null);
        CaraBayar.setName("CaraBayar"); // NOI18N
        FormInput1.add(CaraBayar);
        CaraBayar.setBounds(591, 70, 194, 23);

        NmRuang.setEditable(false);
        NmRuang.setHighlighter(null);
        NmRuang.setName("NmRuang"); // NOI18N
        FormInput1.add(NmRuang);
        NmRuang.setBounds(616, 40, 169, 23);

        BtnDokter18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter18.setMnemonic('2');
        BtnDokter18.setToolTipText("Alt+2");
        BtnDokter18.setName("BtnDokter18"); // NOI18N
        BtnDokter18.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter18ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter18);
        BtnDokter18.setBounds(72, 906, 28, 23);

        scrollPane14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane14.setName("scrollPane14"); // NOI18N

        LabBelum.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        LabBelum.setColumns(20);
        LabBelum.setRows(5);
        LabBelum.setName("LabBelum"); // NOI18N
        LabBelum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LabBelumKeyPressed(evt);
            }
        });
        scrollPane14.setViewportView(LabBelum);

        FormInput1.add(scrollPane14);
        scrollPane14.setBounds(234, 937, 551, 50);

        jLabel62.setText("Jalannya Penyakit Selama Perawatan :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput1.add(jLabel62);
        jLabel62.setBounds(0, 274, 220, 23);

        scrollPane15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane15.setName("scrollPane15"); // NOI18N

        JalannyaPenyakit1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        JalannyaPenyakit1.setColumns(20);
        JalannyaPenyakit1.setRows(5);
        JalannyaPenyakit1.setName("JalannyaPenyakit1"); // NOI18N
        JalannyaPenyakit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JalannyaPenyakit1KeyPressed(evt);
            }
        });
        scrollPane15.setViewportView(JalannyaPenyakit1);

        FormInput1.add(scrollPane15);
        scrollPane15.setBounds(224, 274, 561, 50);

        scrollPane16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane16.setName("scrollPane16"); // NOI18N

        ObatPulang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ObatPulang.setColumns(20);
        ObatPulang.setRows(5);
        ObatPulang.setName("ObatPulang"); // NOI18N
        ObatPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatPulangKeyPressed(evt);
            }
        });
        scrollPane16.setViewportView(ObatPulang);

        FormInput1.add(scrollPane16);
        scrollPane16.setBounds(104, 1111, 681, 50);

        BtnDokter19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter19.setMnemonic('2');
        BtnDokter19.setToolTipText("Alt+2");
        BtnDokter19.setName("BtnDokter19"); // NOI18N
        BtnDokter19.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter19ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter19);
        BtnDokter19.setBounds(72, 1137, 28, 23);

        BtnDokter20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter20.setMnemonic('2');
        BtnDokter20.setToolTipText("Alt+2");
        BtnDokter20.setName("BtnDokter20"); // NOI18N
        BtnDokter20.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter20ActionPerformed(evt);
            }
        });
        FormInput1.add(BtnDokter20);
        BtnDokter20.setBounds(192, 528, 28, 23);

        scrollPane17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane17.setName("scrollPane17"); // NOI18N

        ObatSelamaDiRS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ObatSelamaDiRS.setColumns(20);
        ObatSelamaDiRS.setRows(5);
        ObatSelamaDiRS.setName("ObatSelamaDiRS"); // NOI18N
        ObatSelamaDiRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatSelamaDiRSKeyPressed(evt);
            }
        });
        scrollPane17.setViewportView(ObatSelamaDiRS);

        FormInput1.add(scrollPane17);
        scrollPane17.setBounds(224, 502, 561, 50);

        jLabel63.setText("Obat-obatan Selama Perawatan :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput1.add(jLabel63);
        jLabel63.setBounds(0, 502, 220, 23);

        scrollInput1.setViewportView(FormInput1);

        jPanel6.add(scrollInput1, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Resume Medis Rawat Inap", jPanel6);

        jInternalFrame3.getContentPane().add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Proses Verifikasi", jInternalFrame3);

        jInternalFrame4.setTitle("Preview Dokumen Tambahan");
        jInternalFrame4.setName("jInternalFrame4"); // NOI18N
        jInternalFrame4.setVisible(true);

        jPanel4.setMinimumSize(new java.awt.Dimension(470, 500));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());

        Scroll2.setBorder(javax.swing.BorderFactory.createTitledBorder("List Penunjang"));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);
        Scroll2.setPreferredSize(new java.awt.Dimension(460, 200));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtPdfTambahan.setText("PDF Tambahan");
        txtPdfTambahan.setName("txtPdfTambahan"); // NOI18N
        txtPdfTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPdfTambahanActionPerformed(evt);
            }
        });
        jPanel2.add(txtPdfTambahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 430, -1));

        txtScanTambahan.setText("Scan Tambahan");
        txtScanTambahan.setName("txtScanTambahan"); // NOI18N
        txtScanTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtScanTambahanActionPerformed(evt);
            }
        });
        jPanel2.add(txtScanTambahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 430, -1));

        txtMergedPdf.setText("Preview Combined PDF");
        txtMergedPdf.setName("txtMergedPdf"); // NOI18N
        txtMergedPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMergedPdfActionPerformed(evt);
            }
        });
        jPanel2.add(txtMergedPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 430, -1));

        Scroll2.setViewportView(jPanel2);

        jPanel4.add(Scroll2, java.awt.BorderLayout.LINE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview Data"));
        jPanel7.setMinimumSize(new java.awt.Dimension(20, 500));
        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setPreferredSize(new java.awt.Dimension(20, 500));
        jPanel4.add(jPanel7, java.awt.BorderLayout.CENTER);

        jInternalFrame4.getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Dokumen Tambahan", jInternalFrame4);

        internalFrame1.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panelGlass5.setMinimumSize(new java.awt.Dimension(1414, 110));
        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 80));
        panelGlass5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setText("Rubah Status Jadi:");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 13, 100, -1));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Telah Selesai Dicek Oleh Verifikator", "Sedang Dicek Oleh Verifikator" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass5.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 13, 200, -1));

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Save.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setText("Simpan");
        BtnCari.setToolTipText("Alt+6");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 110, 20));

        BtnPrintSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PrinterSettings.png"))); // NOI18N
        BtnPrintSemua.setMnemonic('K');
        BtnPrintSemua.setText("Combine PDF");
        BtnPrintSemua.setToolTipText("Alt+K");
        BtnPrintSemua.setIconTextGap(3);
        BtnPrintSemua.setName("BtnPrintSemua"); // NOI18N
        BtnPrintSemua.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnPrintSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintSemuaActionPerformed(evt);
            }
        });
        panelGlass5.add(BtnPrintSemua, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 170, 20));

        BtnAttachment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAttachment.setMnemonic('K');
        BtnAttachment.setText("Tambah PDF");
        BtnAttachment.setToolTipText("Alt+K");
        BtnAttachment.setIconTextGap(3);
        BtnAttachment.setName("BtnAttachment"); // NOI18N
        BtnAttachment.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnAttachment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnAttachmentMouseClicked(evt);
            }
        });
        BtnAttachment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAttachmentActionPerformed(evt);
            }
        });
        BtnAttachment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAttachmentKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnAttachment, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 170, 20));

        BtnDeletePdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/delete-16x16.png"))); // NOI18N
        BtnDeletePdf.setMnemonic('K');
        BtnDeletePdf.setText("Hapus PDF Tambahan");
        BtnDeletePdf.setToolTipText("Alt+K");
        BtnDeletePdf.setIconTextGap(3);
        BtnDeletePdf.setName("BtnDeletePdf"); // NOI18N
        BtnDeletePdf.setPreferredSize(new java.awt.Dimension(190, 30));
        BtnDeletePdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnDeletePdfMouseClicked(evt);
            }
        });
        BtnDeletePdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeletePdfActionPerformed(evt);
            }
        });
        BtnDeletePdf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDeletePdfKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnDeletePdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, 190, 20));

        BtnScan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/report24.png"))); // NOI18N
        BtnScan.setMnemonic('K');
        BtnScan.setText("Scan");
        BtnScan.setToolTipText("Alt+K");
        BtnScan.setIconTextGap(3);
        BtnScan.setName("BtnScan"); // NOI18N
        BtnScan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnScanActionPerformed(evt);
            }
        });
        BtnScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnScanKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnScan, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 10, 110, 20));

        BtnDeleteScan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/delete-16x16.png"))); // NOI18N
        BtnDeleteScan.setMnemonic('K');
        BtnDeleteScan.setText("Hapus Scan Tambahan");
        BtnDeleteScan.setToolTipText("Alt+K");
        BtnDeleteScan.setIconTextGap(3);
        BtnDeleteScan.setName("BtnDeleteScan"); // NOI18N
        BtnDeleteScan.setPreferredSize(new java.awt.Dimension(190, 30));
        BtnDeleteScan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnDeleteScanMouseClicked(evt);
            }
        });
        BtnDeleteScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteScanActionPerformed(evt);
            }
        });
        BtnDeleteScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDeleteScanKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnDeleteScan, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 190, 20));

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setIconTextGap(3);
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 110, 20));

        BtnDeleteMergedPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/delete-16x16.png"))); // NOI18N
        BtnDeleteMergedPdf.setMnemonic('K');
        BtnDeleteMergedPdf.setText("Hapus Combined PDF");
        BtnDeleteMergedPdf.setToolTipText("Alt+K");
        BtnDeleteMergedPdf.setIconTextGap(3);
        BtnDeleteMergedPdf.setName("BtnDeleteMergedPdf"); // NOI18N
        BtnDeleteMergedPdf.setPreferredSize(new java.awt.Dimension(190, 30));
        BtnDeleteMergedPdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnDeleteMergedPdfMouseClicked(evt);
            }
        });
        BtnDeleteMergedPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteMergedPdfActionPerformed(evt);
            }
        });
        BtnDeleteMergedPdf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDeleteMergedPdfKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnDeleteMergedPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 190, 20));

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass7.setLayout(null);

        jLabel5.setText("No Rawat");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(160, 10, 70, 23);

        txtNorawat.setHighlighter(null);
        txtNorawat.setName("txtNorawat"); // NOI18N
        txtNorawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNorawatActionPerformed(evt);
            }
        });
        txtNorawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNorawatKeyPressed(evt);
            }
        });
        panelGlass7.add(txtNorawat);
        txtNorawat.setBounds(240, 10, 220, 23);

        jLabel6.setText("RM");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass7.add(jLabel6);
        jLabel6.setBounds(10, 10, 30, 23);

        txtNoRm.setName("txtNoRm"); // NOI18N
        panelGlass7.add(txtNoRm);
        txtNoRm.setBounds(50, 10, 90, 24);

        jLabel7.setText("No SEP");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass7.add(jLabel7);
        jLabel7.setBounds(470, 10, 60, 23);

        txtSep.setName("txtSep"); // NOI18N
        txtSep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSepKeyPressed(evt);
            }
        });
        panelGlass7.add(txtSep);
        txtSep.setBounds(540, 10, 230, 24);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/editcopy.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Copy SEP");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass7.add(BtnPrint1);
        BtnPrint1.setBounds(780, 10, 100, 30);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
        emptTeks();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
                dispose();
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
//        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
//        String status = cmbStatus.getSelectedItem().toString();
//        System.out.println("Status rubah menjadi: "+status);
//        Sequel.queryu("UPDATE `surat_kontrol_bpjs` SET `status`='"+status+"' WHERE `no_rawat`='"+txtNorawat.getText()+"'");
//        DlgPenjaminan jamin = new DlgPenjaminan(null, false);
//        if(Sequel.cariIsi("SELECT status FROM surat_kontrol_bpjs WHERE no_rawat = '"+txtNorawat.getText()+"'").equals("Sudah")){
//            JOptionPane.showMessageDialog(null, "Data berhasil disimpan. \nStatus dikerjakan: "+status);
//            jamin.tampil();
//        }
//        if(Sequel.cariIsi("SELECT status FROM surat_kontrol_bpjs WHERE no_rawat = '"+txtNorawat.getText()+"'").equals("Belum")){
//            JOptionPane.showMessageDialog(null, "Data berhasil disimpan. \nStatus pengerjaan dikembalikan menjadi: "+status);
//        }
//        if(Sequel.cariIsi("SELECT status FROM surat_kontrol_bpjs WHERE no_rawat = '"+txtNorawat.getText()+"'").equals("Batal")){
//            JOptionPane.showMessageDialog(null, "Data berhasil disimpan. \nPengerjaan dibatalkan. \nStatus dikerjakan: "+status);
//        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
//            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnPrintSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintSemuaActionPerformed
//        // Ambil current dir
//        Properties systemProp = System.getProperties();
//        String currentDir = systemProp.getProperty("user.dir");
//        String[] filesToMerge = {}; // Ubah dengan daftar file PDF yang ingin Anda gabungkan
//        
//        // cek Scan INACBG
//        // String nama_file_export_ina = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_inacbg = export_path+"merged_inacbg_"+temp_sep.getText()+".pdf";
//        Path path_inacbg = Paths.get(nama_file_inacbg);
//        if (!Files.notExists(path_inacbg)) {
//            filesToMerge = append(filesToMerge, nama_file_inacbg);
//        }
//        
//        // cek lembar bukti pelayanan
//        // String nama_file_export_ina = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_export_ina = export_path+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        Path path_ina = Paths.get(nama_file_export_ina);
//        if (!Files.notExists(path_ina)) {
//            filesToMerge = append(filesToMerge, nama_file_export_ina);
//        }
//        
//        // cek nota
//        // String nama_file_export_nota = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_export_nota = export_path+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        Path path_nota = Paths.get(nama_file_export_nota);
//        if (!Files.notExists(path_nota)) {
//            filesToMerge = append(filesToMerge, nama_file_export_nota);
//        }
//        
//        // cek surat kontrol
//        // String nama_file_export_suratkontrol = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_export_suratkontrol = export_path+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        Path path_surat_kontrol = Paths.get(nama_file_export_suratkontrol);
//        if (!Files.notExists(path_surat_kontrol)) {
//            filesToMerge = append(filesToMerge, nama_file_export_suratkontrol);
//        }
//        
//        // cek lab
//        // String nama_file_export_lab = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_export_lab = export_path+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        Path path_lab = Paths.get(nama_file_export_lab);
//        if (!Files.notExists(path_lab)) {
//            filesToMerge = append(filesToMerge, nama_file_export_lab);
//        }
//        
//        // cek radiologi
//        // String nama_file_export_radiologi = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_export_radiologi = export_path+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        Path path_radiologi = Paths.get(nama_file_export_radiologi);
//        if (!Files.notExists(path_radiologi)) {
//            filesToMerge = append(filesToMerge, nama_file_export_radiologi);
//        }
//        
//        // cek pdf tambahan
//        String nama_file_pdf_tambahan = export_path+"merged_pdftambahan_"+temp_sep.getText()+".pdf";
//        Path path_pdf_tambahan = Paths.get(nama_file_pdf_tambahan);
//        if (!Files.notExists(path_pdf_tambahan)) {
//            filesToMerge = append(filesToMerge, nama_file_pdf_tambahan);
//        }
//        
//        // cek Scan Tambahan
//        // String nama_file_export_ina = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
//        String nama_file_export_scan = export_path+"tambahan_scan_"+temp_sep.getText()+".pdf";
//        Path path_scan_tambahan = Paths.get(nama_file_export_scan);
//        if (!Files.notExists(path_scan_tambahan)) {
//            filesToMerge = append(filesToMerge, nama_file_export_scan);
//        }
//
//        // String mergedFilePath = merged_path+"merged_file_"+txtNorawat.getText().replace("/", "-")+"_"+txtSEP.getText()+"_"+txtNoRm.getText()+".pdf";
//        String mergedFilePath = merged_path+temp_sep.getText()+".pdf";
//        
//        try {
//            mergePDFs(filesToMerge, mergedFilePath);
//            System.out.println("PDF files merged successfully!");
//            JOptionPane.showMessageDialog(null, "PDF berhasil di combine pada: "+mergedFilePath);
//            txtMergedPdf.setEnabled(true);
//            txtMergedPdf.setText("Preview Combined PDF: Ada");
//            BtnDeleteMergedPdf.setEnabled(true);
//        } catch (IOException ex) {
//            Logger.getLogger(DlgVerifikatorProses.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_BtnPrintSemuaActionPerformed

    private void BtnScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnScanActionPerformed
//        try {
//             // Gunakan jalur penuh ke executable naps2.console dan pilih profil
//            // String filepath = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"tambahan_scan_"+txtNorawat.getText().replace("/", "-")+".pdf";
//            filepath = export_path+"tambahan_scan_"+txtSep.getText()+".pdf";
//            System.out.println("filepath: "+filepath);
//            String command = "\""+naps2_path+":\\Program Files\\NAPS2\\naps2.console.exe\" scan -p \""+naps2_profile+"\" -o "+filepath+" -f";
//            System.out.println("command: "+command);
//            
//            // Menjalankan perintah
//            Process process = Runtime.getRuntime().exec(command);
//
//            // Membaca output dari perintah
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // Menunggu proses selesai
//            int exitCode = process.waitFor();
//            System.out.println("Exited with code: " + exitCode);
//            if(exitCode == 0){
//                statusScan.setText("Scan Sukses: "+filepath);
//                txtScanTambahan.setEnabled(true);
//                txtScanTambahan.setText("Scan Tambahan: Ada");
//                BtnDeleteScan.setEnabled(true);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            statusScan.setText("Scan Gagal: " + e);
//        }
    }//GEN-LAST:event_BtnScanActionPerformed

    private void BtnScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnScanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnScanKeyPressed

    private void BtnAttachmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAttachmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAttachmentActionPerformed

    private void BtnAttachmentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAttachmentKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAttachmentKeyPressed

    private void BtnAttachmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnAttachmentMouseClicked
//        tambah_file("pdftambahan_", txtPdfTambahan, "PDF Tambahan: Ada", BtnDeletePdf);
    }//GEN-LAST:event_BtnAttachmentMouseClicked

    private void BtnDeletePdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeletePdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeletePdfMouseClicked

    private void BtnDeletePdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeletePdfActionPerformed
//        hapus_data("pdftambahan_", "PDF", txtPdfTambahan, "PDF Tambahan: Tidak Ada", "export");
    }//GEN-LAST:event_BtnDeletePdfActionPerformed

    private void BtnDeletePdfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeletePdfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeletePdfKeyPressed

    private void BtnDeleteScanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeleteScanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteScanMouseClicked

    private void BtnDeleteScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteScanActionPerformed
//        hapus_data("tambahan_scan_", "Tambahan Scan", txtScanTambahan, "Scan Tambahan: Tidak Ada", "export");
    }//GEN-LAST:event_BtnDeleteScanActionPerformed

    private void BtnDeleteScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeleteScanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteScanKeyPressed

    private void BtnDeleteMergedPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeleteMergedPdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteMergedPdfMouseClicked

    private void BtnDeleteMergedPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteMergedPdfActionPerformed
//        hapus_data("", "Combined PDF", txtMergedPdf, "Preview Combined PDF: Tidak Ada", "merge");
    }//GEN-LAST:event_BtnDeleteMergedPdfActionPerformed

    private void BtnDeleteMergedPdfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeleteMergedPdfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteMergedPdfKeyPressed

    private void ObatSelamaDiRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatSelamaDiRSKeyPressed
        Valid.pindah2(evt,TindakanSelamaDiRS,DiagnosaUtama);
    }//GEN-LAST:event_ObatSelamaDiRSKeyPressed

    private void BtnDokter20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter20ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            cariobat.setNoRawat(TNoRw.getText());
            //            cariobat.tampil();
            //            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            cariobat.setLocationRelativeTo(internalFrame1);
            //            cariobat.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter20ActionPerformed

    private void BtnDokter19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter19ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            cariobatpulang.setNoRawat(TNoRw.getText());
            //            cariobatpulang.tampil();
            //            cariobatpulang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            cariobatpulang.setLocationRelativeTo(internalFrame1);
            //            cariobatpulang.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter19ActionPerformed

    private void ObatPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatPulangKeyPressed
        //        Valid.pindah2(evt,Kontrol,BtnSimpan);
    }//GEN-LAST:event_ObatPulangKeyPressed

    private void JalannyaPenyakit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalannyaPenyakit1KeyPressed
        Valid.pindah2(evt,PemeriksaanFisik,PemeriksaanRad);
    }//GEN-LAST:event_JalannyaPenyakit1KeyPressed

    private void LabBelumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LabBelumKeyPressed
        Valid.pindah2(evt,Diet,Edukasi);
    }//GEN-LAST:event_LabBelumKeyPressed

    private void BtnDokter18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter18ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            caridiet.setNoRawat(TNoRw.getText());
            //            caridiet.tampil();
            //            caridiet.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            caridiet.setLocationRelativeTo(internalFrame1);
            //            caridiet.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter18ActionPerformed

    private void KontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KontrolKeyPressed
        Valid.pindah2(evt,KetDilanjutkan,ObatPulang);
    }//GEN-LAST:event_KontrolKeyPressed

    private void KetDilanjutkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetDilanjutkanKeyPressed
        Valid.pindah(evt,DIlanjutkan,Kontrol);
    }//GEN-LAST:event_KetDilanjutkanKeyPressed

    private void DIlanjutkanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DIlanjutkanKeyPressed
        Valid.pindah(evt,KetKeluar,KetDilanjutkan);
    }//GEN-LAST:event_DIlanjutkanKeyPressed

    private void KetKeadaanPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKeadaanPulangKeyPressed
        Valid.pindah(evt,Keadaan,CaraKeluar);
    }//GEN-LAST:event_KetKeadaanPulangKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        Valid.pindah2(evt,LabBelum,Keadaan);
    }//GEN-LAST:event_EdukasiKeyPressed

    private void DietKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DietKeyPressed
        Valid.pindah2(evt,Alergi,LabBelum);
    }//GEN-LAST:event_DietKeyPressed

    private void BtnDokter17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter17ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            carilabpending.setNoRawat(TNoRw.getText());
            //            carilabpending.tampil();
            //            carilabpending.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            carilabpending.setLocationRelativeTo(internalFrame1);
            //            carilabpending.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter17ActionPerformed

    private void KetKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetKeluarKeyPressed
        Valid.pindah(evt,CaraKeluar,DIlanjutkan);
    }//GEN-LAST:event_KetKeluarKeyPressed

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        Valid.pindah(evt,KodeProsedurSekunder3,Diet);
    }//GEN-LAST:event_AlergiKeyPressed

    private void TindakanSelamaDiRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanSelamaDiRSKeyPressed
        Valid.pindah2(evt,HasilLaborat,ObatSelamaDiRS);
    }//GEN-LAST:event_TindakanSelamaDiRSKeyPressed

    private void BtnDokter16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter16ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            caritindakan.setNoRawat(TNoRw.getText());
            //            caritindakan.tampil();
            //            caritindakan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            caritindakan.setLocationRelativeTo(internalFrame1);
            //            caritindakan.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter16ActionPerformed

    private void DiagnosaAwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaAwalKeyPressed
        //        Valid.pindah(evt,TCari,Alasan);
    }//GEN-LAST:event_DiagnosaAwalKeyPressed

    private void AlasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanKeyPressed
        Valid.pindah(evt,DiagnosaAwal,KeluhanUtama);
    }//GEN-LAST:event_AlasanKeyPressed

    private void KodeDokterPengirimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterPengirimKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokterPengirimKeyPressed

    private void BtnDokter10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter10ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            caripemeriksaan.setNoRawat(TNoRw.getText());
            //            caripemeriksaan.tampil();
            //            caripemeriksaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            caripemeriksaan.setLocationRelativeTo(internalFrame1);
            //            caripemeriksaan.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter10ActionPerformed

    private void KeadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanKeyPressed
        Valid.pindah(evt,Edukasi,KetKeadaanPulang);
    }//GEN-LAST:event_KeadaanKeyPressed

    private void CaraKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraKeluarKeyPressed
        Valid.pindah(evt, KetKeadaanPulang,KetKeluar);
    }//GEN-LAST:event_CaraKeluarKeyPressed

    private void BtnDokter9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter9ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            carilaborat.setNoRawat(TNoRw.getText());
            //            carilaborat.tampil();
            //            carilaborat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            carilaborat.setLocationRelativeTo(internalFrame1);
            //            carilaborat.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter9ActionPerformed

    private void BtnDokter8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter8ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            cariradiologi.setNoRawat(TNoRw.getText());
            //            cariradiologi.tampil();
            //            cariradiologi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            cariradiologi.setLocationRelativeTo(internalFrame1);
            //            cariradiologi.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter8ActionPerformed

    private void BtnDokter7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter7ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            carikeluhan.setNoRawat(TNoRw.getText());
            //            carikeluhan.tampil();
            //            carikeluhan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            carikeluhan.setLocationRelativeTo(internalFrame1);
            //            carikeluhan.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter7ActionPerformed

    private void BtnDokter6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter6KeyPressed
        //        Valid.pindah(evt,TCari,CaraKeluar);
    }//GEN-LAST:event_BtnDokter6KeyPressed

    private void BtnDokter6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter6ActionPerformed
        //        dokter.emptTeks();
        //        dokter.isCek();
        //        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        //        dokter.setLocationRelativeTo(internalFrame1);
        //        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokter6ActionPerformed

    private void KodeDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokter1KeyPressed
        //        Valid.pindah(evt,TCari,CaraKeluar);
    }//GEN-LAST:event_KodeDokter1KeyPressed

    private void ProsedurSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder6KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder2,KodeProsedurSekunder3);
    }//GEN-LAST:event_ProsedurSekunder6KeyPressed

    private void KodeProsedurSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder6KeyPressed
        Valid.pindah(evt,ProsedurSekunder3,Alergi);
    }//GEN-LAST:event_KodeProsedurSekunder6KeyPressed

    private void KodeProsedurSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder5KeyPressed
        Valid.pindah(evt,ProsedurSekunder2,ProsedurSekunder3);
    }//GEN-LAST:event_KodeProsedurSekunder5KeyPressed

    private void ProsedurSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder5KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder1,KodeProsedurSekunder2);
    }//GEN-LAST:event_ProsedurSekunder5KeyPressed

    private void KodeProsedurSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder4KeyPressed
        Valid.pindah(evt,ProsedurSekunder1,ProsedurSekunder2);
    }//GEN-LAST:event_KodeProsedurSekunder4KeyPressed

    private void ProsedurSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder4KeyPressed
        Valid.pindah(evt,KodeProsedurUtama,KodeProsedurSekunder1);
    }//GEN-LAST:event_ProsedurSekunder4KeyPressed

    private void KodeProsedurUtama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurUtama1KeyPressed
        Valid.pindah(evt,ProsedurUtama,ProsedurSekunder1);
    }//GEN-LAST:event_KodeProsedurUtama1KeyPressed

    private void ProsedurUtama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurUtama1KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder4,KodeProsedurUtama);
    }//GEN-LAST:event_ProsedurUtama1KeyPressed

    private void KodeDiagnosaSekunder8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder8KeyPressed
        Valid.pindah(evt,DiagnosaSekunder4,ProsedurUtama);
    }//GEN-LAST:event_KodeDiagnosaSekunder8KeyPressed

    private void KodeDiagnosaSekunder7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder7KeyPressed
        Valid.pindah(evt,DiagnosaSekunder3,DiagnosaSekunder4);
    }//GEN-LAST:event_KodeDiagnosaSekunder7KeyPressed

    private void KodeDiagnosaSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder6KeyPressed
        Valid.pindah(evt,DiagnosaSekunder2,DiagnosaSekunder3);
    }//GEN-LAST:event_KodeDiagnosaSekunder6KeyPressed

    private void KodeDiagnosaSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder5KeyPressed
        Valid.pindah(evt,DiagnosaSekunder1,DiagnosaSekunder2);
    }//GEN-LAST:event_KodeDiagnosaSekunder5KeyPressed

    private void KodeDiagnosaUtama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaUtama1KeyPressed
        Valid.pindah(evt,DiagnosaUtama,DiagnosaSekunder1);
    }//GEN-LAST:event_KodeDiagnosaUtama1KeyPressed

    private void DiagnosaSekunder8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder8KeyPressed
        Valid.pindah(evt,KodeDiagnosaUtama,KodeDiagnosaSekunder1);
    }//GEN-LAST:event_DiagnosaSekunder8KeyPressed

    private void HasilLaborat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLaborat1KeyPressed
        Valid.pindah2(evt,PemeriksaanRad,TindakanSelamaDiRS);
    }//GEN-LAST:event_HasilLaborat1KeyPressed

    private void PemeriksaanRadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanRadKeyPressed
        Valid.pindah2(evt,JalannyaPenyakit,HasilLaborat);
    }//GEN-LAST:event_PemeriksaanRadKeyPressed

    private void PemeriksaanFisikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanFisikKeyPressed
        Valid.pindah2(evt,KeluhanUtama,JalannyaPenyakit);
    }//GEN-LAST:event_PemeriksaanFisikKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
        Valid.pindah2(evt,Alasan,PemeriksaanFisik);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void DiagnosaSekunder7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder7KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder3,KodeDiagnosaSekunder4);
    }//GEN-LAST:event_DiagnosaSekunder7KeyPressed

    private void DiagnosaSekunder6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder6KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder2,KodeDiagnosaSekunder3);
    }//GEN-LAST:event_DiagnosaSekunder6KeyPressed

    private void DiagnosaUtama1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtama1KeyPressed
        Valid.pindah(evt,ObatSelamaDiRS,KodeDiagnosaUtama);
    }//GEN-LAST:event_DiagnosaUtama1KeyPressed

    private void DiagnosaSekunder5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder5KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder1,KodeDiagnosaSekunder2);
    }//GEN-LAST:event_DiagnosaSekunder5KeyPressed

    private void TNoRM1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRM1KeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRM1KeyPressed

    private void TPasien1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasien1KeyPressed
        //        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasien1KeyPressed

    private void TNoRw1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRw1KeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            //            isRawat();
            //        }else{
            //            Valid.pindah(evt,TCari,BtnDokter);
            //        }
    }//GEN-LAST:event_TNoRw1KeyPressed

    private void BtnDokter5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter5ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            caripemeriksaan.setNoRawat(TNoRw.getText());
            //            caripemeriksaan.tampil();
            //            caripemeriksaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            caripemeriksaan.setLocationRelativeTo(internalFrame1);
            //            caripemeriksaan.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter5ActionPerformed

    private void BtnDokter4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter4ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            cariobat.setNoRawat(TNoRw.getText());
            //            cariobat.tampil();
            //            cariobat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            cariobat.setLocationRelativeTo(internalFrame1);
            //            cariobat.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter4ActionPerformed

    private void BtnDokter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter3ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            carilaborat.setNoRawat(TNoRw.getText());
            //            carilaborat.tampil();
            //            carilaborat.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            carilaborat.setLocationRelativeTo(internalFrame1);
            //            carilaborat.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter3ActionPerformed

    private void BtnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter2ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            cariradiologi.setNoRawat(TNoRw.getText());
            //            cariradiologi.tampil();
            //            cariradiologi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            cariradiologi.setLocationRelativeTo(internalFrame1);
            //            cariradiologi.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter2ActionPerformed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        //        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            //            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
            //        }else{
            //            carikeluhan.setNoRawat(TNoRw.getText());
            //            carikeluhan.tampil();
            //            carikeluhan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            //            carikeluhan.setLocationRelativeTo(internalFrame1);
            //            carikeluhan.setVisible(true);
            //        }
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void KondisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiKeyPressed
        Valid.pindah(evt, KodeDokter, Keluhan);
    }//GEN-LAST:event_KondisiKeyPressed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //        Valid.pindah(evt,TCari,Kondisi);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        //        dokter.emptTeks();
        //        dokter.isCek();
        //        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        //        dokter.setLocationRelativeTo(internalFrame1);
        //        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void KodeDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterKeyPressed
        //        Valid.pindah(evt,TCari,Kondisi);
    }//GEN-LAST:event_KodeDokterKeyPressed

    private void ProsedurSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder3KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder2,KodeProsedurSekunder3);
    }//GEN-LAST:event_ProsedurSekunder3KeyPressed

    private void KodeProsedurSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder3KeyPressed
        Valid.pindah(evt,ProsedurSekunder3,Obat2an);
    }//GEN-LAST:event_KodeProsedurSekunder3KeyPressed

    private void KodeProsedurSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder2KeyPressed
        Valid.pindah(evt,ProsedurSekunder2,ProsedurSekunder3);
    }//GEN-LAST:event_KodeProsedurSekunder2KeyPressed

    private void ProsedurSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder2KeyPressed
        Valid.pindah(evt,KodeProsedurSekunder1,KodeProsedurSekunder2);
    }//GEN-LAST:event_ProsedurSekunder2KeyPressed

    private void KodeProsedurSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurSekunder1KeyPressed
        Valid.pindah(evt,ProsedurSekunder1,ProsedurSekunder2);
    }//GEN-LAST:event_KodeProsedurSekunder1KeyPressed

    private void ProsedurSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurSekunder1KeyPressed
        Valid.pindah(evt,KodeProsedurUtama,KodeProsedurSekunder1);
    }//GEN-LAST:event_ProsedurSekunder1KeyPressed

    private void KodeProsedurUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeProsedurUtamaKeyPressed
        Valid.pindah(evt,ProsedurUtama,ProsedurSekunder1);
    }//GEN-LAST:event_KodeProsedurUtamaKeyPressed

    private void ProsedurUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurUtamaKeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder4,KodeProsedurUtama);
    }//GEN-LAST:event_ProsedurUtamaKeyPressed

    private void KodeDiagnosaSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder4KeyPressed
        Valid.pindah(evt,DiagnosaSekunder4,ProsedurUtama);
    }//GEN-LAST:event_KodeDiagnosaSekunder4KeyPressed

    private void KodeDiagnosaSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder3KeyPressed
        Valid.pindah(evt,DiagnosaSekunder3,DiagnosaSekunder4);
    }//GEN-LAST:event_KodeDiagnosaSekunder3KeyPressed

    private void KodeDiagnosaSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder2KeyPressed
        Valid.pindah(evt,DiagnosaSekunder2,DiagnosaSekunder3);
    }//GEN-LAST:event_KodeDiagnosaSekunder2KeyPressed

    private void KodeDiagnosaSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaSekunder1KeyPressed
        Valid.pindah(evt,DiagnosaSekunder1,DiagnosaSekunder2);
    }//GEN-LAST:event_KodeDiagnosaSekunder1KeyPressed

    private void KodeDiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDiagnosaUtamaKeyPressed
        Valid.pindah(evt,DiagnosaUtama,DiagnosaSekunder1);
    }//GEN-LAST:event_KodeDiagnosaUtamaKeyPressed

    private void DiagnosaSekunder1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder1KeyPressed
        Valid.pindah(evt,KodeDiagnosaUtama,KodeDiagnosaSekunder1);
    }//GEN-LAST:event_DiagnosaSekunder1KeyPressed

    private void Obat2anKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat2anKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //            if(evt.isShiftDown()){
                //                BtnSimpan.requestFocus();
                //            }
            //        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            //            KodeProsedurSekunder3.requestFocus();
            //        }
    }//GEN-LAST:event_Obat2anKeyPressed

    private void HasilLaboratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLaboratKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                DiagnosaUtama.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            PemeriksaanPenunjang.requestFocus();
        }
    }//GEN-LAST:event_HasilLaboratKeyPressed

    private void PemeriksaanPenunjangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanPenunjangKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                HasilLaborat.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            JalannyaPenyakit.requestFocus();
        }
    }//GEN-LAST:event_PemeriksaanPenunjangKeyPressed

    private void JalannyaPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JalannyaPenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                PemeriksaanPenunjang.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Keluhan.requestFocus();
        }
    }//GEN-LAST:event_JalannyaPenyakitKeyPressed

    private void KeluhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(evt.isShiftDown()){
                JalannyaPenyakit.requestFocus();
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Kondisi.requestFocus();
        }
    }//GEN-LAST:event_KeluhanKeyPressed

    private void DiagnosaSekunder4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder4KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder3,KodeDiagnosaSekunder4);
    }//GEN-LAST:event_DiagnosaSekunder4KeyPressed

    private void DiagnosaSekunder3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder3KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder2,KodeDiagnosaSekunder3);
    }//GEN-LAST:event_DiagnosaSekunder3KeyPressed

    private void DiagnosaUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaUtamaKeyPressed
        Valid.pindah(evt,HasilLaborat,KodeDiagnosaUtama);
    }//GEN-LAST:event_DiagnosaUtamaKeyPressed

    private void DiagnosaSekunder2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaSekunder2KeyPressed
        Valid.pindah(evt,KodeDiagnosaSekunder1,KodeDiagnosaSekunder2);
    }//GEN-LAST:event_DiagnosaSekunder2KeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        //        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        //        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            //            isRawat();
            //            isPsien();
            //        }else{
            //            Valid.pindah(evt,TCari,BtnDokter);
            //        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void tbRadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRadKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadKeyPressed

    private void tbRadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRadMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRadMouseClicked

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        String sepText = txtSep.getText();
        StringSelection stringSelectionObj = new StringSelection(sepText);
        Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboardObj.setContents(stringSelectionObj, null);
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void txtSepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSepKeyPressed

    private void txtNorawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNorawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNorawatKeyPressed

    private void txtNorawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNorawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNorawatActionPerformed

    private void tbLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbLabKeyPressed
        if(tabLabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbLabKeyPressed

    private void tbLabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLabMouseClicked
        // tb_lab
    }//GEN-LAST:event_tbLabMouseClicked

    private void txtPdfTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPdfTambahanActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();

        // Tentukan jalur file PDF
        filepath = export_path+"merged_pdftambahan_"+txtSep.getText()+".pdf";

        // Buat instance PDFPanel
        PDFPanel pdfPanel = new PDFPanel();

        // Muat PDF ke dalam PDFPanel
        pdfPanel.loadPDF(filepath);

        // Hapus semua komponen dari jPanel2
        jPanel2.removeAll();

        // Tambahkan PDFPanel ke jPanel2
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(new JScrollPane(pdfPanel), BorderLayout.CENTER);
        txtPdfTambahan.setBackground(Color.GREEN);
    }//GEN-LAST:event_txtPdfTambahanActionPerformed

    private void txtScanTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtScanTambahanActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();

        // Tentukan jalur file PDF
        filepath = export_path+"tambahan_scan_"+txtSep.getText()+".pdf";

        // Buat instance PDFPanel
        PDFPanel pdfPanel = new PDFPanel();

        // Muat PDF ke dalam PDFPanel
        pdfPanel.loadPDF(filepath);

        // Hapus semua komponen dari jPanel2
        jPanel2.removeAll();

        // Tambahkan PDFPanel ke jPanel2
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(new JScrollPane(pdfPanel), BorderLayout.CENTER);
        txtScanTambahan.setBackground(Color.GREEN);
    }//GEN-LAST:event_txtScanTambahanActionPerformed

    private void txtMergedPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMergedPdfActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();

        // Tentukan jalur file PDF
        filepath = merged_path+txtSep.getText()+".pdf";

        // Buat instance PDFPanel
        PDFPanel pdfPanel = new PDFPanel();

        // Muat PDF ke dalam PDFPanel
        pdfPanel.loadPDF(filepath);

        // Hapus semua komponen dari jPanel2
        jPanel2.removeAll();

        // Tambahkan PDFPanel ke jPanel2
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(new JScrollPane(pdfPanel), BorderLayout.CENTER);
        txtMergedPdf.setBackground(Color.GREEN);
    }//GEN-LAST:event_txtMergedPdfActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgVerifikatorProses dialog = new DlgVerifikatorProses(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox Alasan;
    private widget.TextBox Alergi;
    private widget.Button BtnAttachment;
    private widget.Button BtnCari;
    private widget.Button BtnDeleteMergedPdf;
    private widget.Button BtnDeletePdf;
    private widget.Button BtnDeleteScan;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnDokter10;
    private widget.Button BtnDokter16;
    private widget.Button BtnDokter17;
    private widget.Button BtnDokter18;
    private widget.Button BtnDokter19;
    private widget.Button BtnDokter2;
    private widget.Button BtnDokter20;
    private widget.Button BtnDokter3;
    private widget.Button BtnDokter4;
    private widget.Button BtnDokter5;
    private widget.Button BtnDokter6;
    private widget.Button BtnDokter7;
    private widget.Button BtnDokter8;
    private widget.Button BtnDokter9;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint1;
    private widget.Button BtnPrintSemua;
    private widget.Button BtnScan;
    private widget.TextBox CaraBayar;
    private widget.ComboBox CaraKeluar;
    private widget.ComboBox DIlanjutkan;
    private widget.TextBox DiagnosaAwal;
    private widget.TextBox DiagnosaSekunder1;
    private widget.TextBox DiagnosaSekunder2;
    private widget.TextBox DiagnosaSekunder3;
    private widget.TextBox DiagnosaSekunder4;
    private widget.TextBox DiagnosaSekunder5;
    private widget.TextBox DiagnosaSekunder6;
    private widget.TextBox DiagnosaSekunder7;
    private widget.TextBox DiagnosaSekunder8;
    private widget.TextBox DiagnosaUtama;
    private widget.TextBox DiagnosaUtama1;
    private widget.TextArea Diet;
    private widget.TextArea Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.TextArea HasilLaborat;
    private widget.TextArea HasilLaborat1;
    private widget.TextArea JalannyaPenyakit;
    private widget.TextArea JalannyaPenyakit1;
    private widget.TextBox JamKeluar;
    private widget.TextBox JamMasuk;
    private widget.TextBox KdPj;
    private widget.TextBox KdRuang;
    private widget.ComboBox Keadaan;
    private widget.TextBox Keluar;
    private widget.TextArea Keluhan;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox KetDilanjutkan;
    private widget.TextBox KetKeadaanPulang;
    private widget.TextBox KetKeluar;
    private widget.TextBox KodeDiagnosaSekunder1;
    private widget.TextBox KodeDiagnosaSekunder2;
    private widget.TextBox KodeDiagnosaSekunder3;
    private widget.TextBox KodeDiagnosaSekunder4;
    private widget.TextBox KodeDiagnosaSekunder5;
    private widget.TextBox KodeDiagnosaSekunder6;
    private widget.TextBox KodeDiagnosaSekunder7;
    private widget.TextBox KodeDiagnosaSekunder8;
    private widget.TextBox KodeDiagnosaUtama;
    private widget.TextBox KodeDiagnosaUtama1;
    private widget.TextBox KodeDokter;
    private widget.TextBox KodeDokter1;
    private widget.TextBox KodeDokterPengirim;
    private widget.TextBox KodeProsedurSekunder1;
    private widget.TextBox KodeProsedurSekunder2;
    private widget.TextBox KodeProsedurSekunder3;
    private widget.TextBox KodeProsedurSekunder4;
    private widget.TextBox KodeProsedurSekunder5;
    private widget.TextBox KodeProsedurSekunder6;
    private widget.TextBox KodeProsedurUtama;
    private widget.TextBox KodeProsedurUtama1;
    private widget.ComboBox Kondisi;
    private widget.Tanggal Kontrol;
    private widget.TextArea LabBelum;
    private widget.TextBox Masuk;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaDokter1;
    private widget.TextBox NamaDokterPengirim;
    private widget.TextBox NmRuang;
    private widget.TextArea Obat2an;
    private widget.TextArea ObatPulang;
    private widget.TextArea ObatSelamaDiRS;
    private widget.TextArea PemeriksaanFisik;
    private widget.TextArea PemeriksaanPenunjang;
    private widget.TextArea PemeriksaanRad;
    private widget.TextBox ProsedurSekunder1;
    private widget.TextBox ProsedurSekunder2;
    private widget.TextBox ProsedurSekunder3;
    private widget.TextBox ProsedurSekunder4;
    private widget.TextBox ProsedurSekunder5;
    private widget.TextBox ProsedurSekunder6;
    private widget.TextBox ProsedurUtama;
    private widget.TextBox ProsedurUtama1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw1;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.TextArea TindakanSelamaDiRS;
    private javax.swing.JButton btnSuratKontrol;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JInternalFrame jInternalFrame4;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollInput1;
    private widget.ScrollPane scrollPane10;
    private widget.ScrollPane scrollPane11;
    private widget.ScrollPane scrollPane12;
    private widget.ScrollPane scrollPane13;
    private widget.ScrollPane scrollPane14;
    private widget.ScrollPane scrollPane15;
    private widget.ScrollPane scrollPane16;
    private widget.ScrollPane scrollPane17;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.ScrollPane scrollPane9;
    private widget.Table tbLab;
    private widget.Table tbRad;
    private javax.swing.JTextField temp_sep;
    private javax.swing.JButton txtMergedPdf;
    private widget.TextBox txtNoRm;
    private widget.TextBox txtNorawat;
    private javax.swing.JButton txtPdfTambahan;
    private javax.swing.JButton txtScanTambahan;
    private widget.TextBox txtSep;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String no_rawat = txtNorawat.getText();
        String no_rm = txtNoRm.getText();
        String no_sep = txtSep.getText();
        tampil_hasil_lab(no_rawat, no_rm, no_sep);
        tampil_hasil_rad(no_rawat, no_rm, no_sep);
//        
//        //################################
//        //||                            ||
//        //||      Cek data billing      ||
//        //||                            ||
//        //################################
//        
//        int cek_nota_ralan = Sequel.cariInteger("SELECT COUNT(*) FROM nota_jalan WHERE no_rawat = '"+no_rawat+"'");
//        int cek_nota_inap = Sequel.cariInteger("SELECT COUNT(*) FROM nota_inap WHERE no_rawat = '"+no_rawat+"'");
//        if(cek_nota_ralan > 0){
//            btnNota.setText("Nota Ada");
//            btnNota.setEnabled(true);
//        }else if(cek_nota_inap > 0){
//            btnNota.setText("Nota Ada");
//            btnNota.setEnabled(true);
//        }else{
//            btnNota.setText("Nota Belum Ada");
//            btnNota.setEnabled(false);
//        }
//        
//        //################################
//        //||                            ||
//        //||        Cek data lab        ||
//        //||                            ||
//        //################################
//        
//        int cek_lab = Sequel.cariInteger("select \n" +
//                                        "  count(periksa_lab.kd_jenis_prw) AS jml_lab \n" +
//                                        "from \n" +
//                                        "  periksa_lab \n"+ 
//                                        "inner join reg_periksa on periksa_lab.no_rawat = reg_periksa.no_rawat \n" +
//                                        "where \n" +
//                                        "  periksa_lab.kategori = 'PK' \n" +
//                                        "  and periksa_lab.no_rawat like '"+txtNorawat.getText()+"' \n" +
//                                        "  and reg_periksa.no_rkm_medis like '"+txtNoRm.getText()+"' \n");
//        System.out.println("Jumlah lab: "+cek_lab);
//        if(cek_lab > 0){
//            txtLab.setText("Lab Ada");
//            txtLab.setEnabled(true);
//        }else{
//            txtLab.setText("Lab Belum/Tidak Ada");
//            txtLab.setEnabled(false);
//        }
//        
//        //################################
//        //||                            ||
//        //||    Cek data radiologi      ||
//        //||                            ||
//        //################################
//        
//        int cek_radiologi = Sequel.cariInteger("SELECT COUNT(*) FROM permintaan_radiologi WHERE no_rawat = '"+no_rawat+"'");
//        if(cek_radiologi > 0){
//            txtRadiologi.setText("Radiologi Ada");
//            txtRadiologi.setEnabled(true);
//        }else{
//            txtRadiologi.setText("Radiologi Belum/Tidak Ada");
//            txtRadiologi.setEnabled(false);
//        }
//        
//        
//        //################################
//        //||                            ||
//        //||        Cek Data INA        ||
//        //||                            ||
//        //################################
//        
//        int cek_jml_sep = Sequel.cariInteger("SELECT COUNT(*) FROM bridging_sep WHERE no_rawat = '"+no_rawat+"'");
//        System.out.println("cek_jml_sep: "+cek_jml_sep);
//        if(cek_jml_sep > 1){
//            txtIna.setText("Lembar Bukti Pelayanan: SEP Lebih dari 2");
//            txtIna.setEnabled(false);
//        }else if(cek_jml_sep == 0){
//            txtIna.setText("Lembar Bukti Pelayanan: SEP Tidak Ada");
//            txtIna.setEnabled(false);
//        }else{
//            txtIna.setText("Lembar Bukti Pelayanan: Ada");
//            txtIna.setEnabled(true);
//        }
//        
//        //################################
//        //||                            ||
//        //||       Cek Data INACBG      ||
//        //||                            ||
//        //################################
//        
//        // Tentukan jalur file
//        fileinacbgpath = export_path+"merged_inacbg_"+txtSep.getText()+".pdf";
//        // Buat objek File
//        File file_inacbg = new File(fileinacbgpath);
//        
//        // Periksa apakah file ada
//        if (file_inacbg.exists()) {
//            System.out.println("File ada di jalur: " + fileinacbgpath);
//            txtScanInacbg.setText("Berkas INACBG: Ada");
//            txtScanInacbg.setEnabled(true);
//            BtnDeleteInacbg.setEnabled(true);
//        } else {
//            System.out.println("File tidak ditemukan di jalur: " + fileinacbgpath);
//            txtScanInacbg.setText("Berkas INACBG: Tidak Ada");
//            txtScanInacbg.setEnabled(false);
//            BtnDeleteInacbg.setEnabled(false);
//        }
//        
//        //################################
//        //||                            ||
//        //||  Cek Data PDF Tambahan    ||
//        //||                            ||
//        //################################
//        
//        // Tentukan jalur file
//        filepath = export_path+"merged_pdftambahan_"+txtSep.getText()+".pdf";
//        // Buat objek File
//        File file = new File(filepath);
//        
//        // Periksa apakah file ada
//        if (file.exists()) {
//            System.out.println("File ada di jalur: " + filepath);
//            txtPdfTambahan.setText("PDF Tambahan: Ada");
//            txtPdfTambahan.setEnabled(true);
//            BtnDeletePdf.setEnabled(true);
//        } else {
//            System.out.println("File tidak ditemukan di jalur: " + filepath);
//            txtPdfTambahan.setText("PDF Tambahan: Tidak Ada");
//            txtPdfTambahan.setEnabled(false);
//            BtnDeletePdf.setEnabled(false);
//        }
//        
//        //################################
//        //||                            ||
//        //||  Cek Data Scan Tambahan    ||
//        //||                            ||
//        //################################
//        
//        // Tentukan jalur file
//        filescanpath = export_path+"tambahan_scan_"+txtSep.getText()+".pdf";
//        // Buat objek File
//        File file_scan = new File(filescanpath);
//        
//        // Periksa apakah file ada
//        if (file_scan.exists()) {
//            System.out.println("File ada di jalur: " + filescanpath);
//            txtScanTambahan.setText("Scan Tambahan: Ada");
//            txtScanTambahan.setEnabled(true);
//            BtnDeleteScan.setEnabled(true);
//        } else {
//            System.out.println("File tidak ditemukan di jalur: " + filescanpath);
//            txtScanTambahan.setText("Scan Tambahan: Tidak Ada");
//            txtScanTambahan.setEnabled(false);
//            BtnDeleteScan.setEnabled(false);
//        }
//        
//        //################################
//        //||                            ||
//        //||  Cek Data merged pdf       ||
//        //||                            ||
//        //################################
//        
//        // Tentukan jalur file
//        filemergedpath = merged_path+txtSep.getText()+".pdf";
//        // Buat objek File
//        File file_merged = new File(filemergedpath);
//        
//        // Periksa apakah file ada
//        if (file_merged.exists()) {
//            System.out.println("File ada di jalur: " + filemergedpath);
//            txtMergedPdf.setText("Preview Combined PDF: Ada");
//            txtMergedPdf.setEnabled(true);
//            BtnDeleteMergedPdf.setEnabled(true);
//        } else {
//            System.out.println("File tidak ditemukan di jalur: " + filemergedpath);
//            txtMergedPdf.setText("Preview Combined PDF: Tidak Ada");
//            txtMergedPdf.setEnabled(false);
//            BtnDeleteMergedPdf.setEnabled(false);
//        }
    }
    
    private void tambah_file(String nama_depan_file, javax.swing.JButton tombol_tampil_file, String txtTombol, widget.Button tombol_hapus_file){
        // Ambil current dir
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String[] filesToMerge = {}; // Ubah dengan daftar file PDF yang ingin Anda gabungkan
        // Daftar file PDF yang akan digabungkan
        List<Path> pdfToMerge = new ArrayList<>();

        // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();
        
         // Set file chooser to allow multiple file selection
        fileChooser.setMultiSelectionEnabled(true);

        // Set file filter to only show PDF files
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
            }

            @Override
            public String getDescription() {
                return "PDF Documents (*.pdf)";
            }
        });

        // Show open dialog and check if a file was chosen
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFile = fileChooser.getSelectedFiles();
            // Process the selected files
            int count = 1;
            for (File file : selectedFile) {
                // Ask user to enter a new name for each file
                String newName = nama_depan_file+temp_sep.getText()+"_("+count+").pdf";

                if (newName != null && !newName.trim().isEmpty()) {
                    // Create new file path with the specified name
                    Path destinationPath = Path.of(export_path, newName);

                    try {
                        // Copy file to new location with the new name
                        Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        pdfToMerge.add(destinationPath);
                        count++;
                        // JOptionPane.showMessageDialog(null, "File saved as: " + destinationPath);
                        System.out.print("File saved as: " + destinationPath);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid name for file: " + file.getName());
                }
            }
            
            // proses merge beberapa file pdf tambahan
            // Tambahkan setiap file yang akan digabungkan ke PDFMergerUtility
            for (Path file : pdfToMerge) {
                System.out.println("pdf tambahan: "+file.toString());
                filesToMerge = append(filesToMerge, file.toString());
            }
            
            String mergedFilePath = export_path+"merged_"+nama_depan_file+temp_sep.getText()+".pdf";

            try {
                mergePDFs(filesToMerge, mergedFilePath);
                System.out.println("PDF files merged successfully!");
                JOptionPane.showMessageDialog(null, "PDF berhasil di combine pada: "+mergedFilePath);
                tombol_tampil_file.setEnabled(true);
                tombol_tampil_file.setText(txtTombol);
                tombol_hapus_file.setEnabled(true);
            } catch (IOException ex) {
                Logger.getLogger(DlgVerifikatorProses.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file selected.");
        }
    }
    
    private void hapus_data(String nama_depan_file, String judul_file, javax.swing.JButton tombol_tampil_file, String txtTombol, String tipe){
        // Lokasi file PDF yang akan dihapus
        // Direktori tempat file berada
        String filehapusPath = "", directoryPath = "";
        if (tipe == "merge") {
            filehapusPath = merged_path+txtSep.getText()+".pdf";
            directoryPath = merged_path;
        }else if(tipe == "export_with_no_rawat"){
            filehapusPath = export_path+nama_depan_file+txtNorawat.getText().replace("/", "-")+".pdf";
            directoryPath = export_path;
        }else{
            filehapusPath = export_path+"merged_"+nama_depan_file+txtSep.getText()+".pdf";
            directoryPath = export_path;
        }

        // Membuat objek File
        File file_hapus = new File(filehapusPath);
        
        if (file_hapus.exists()) {
            // Tampilkan dialog konfirmasi
            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah Anda yakin ingin menghapus file "+judul_file+": " + filehapusPath + "?",
                    "Konfirmasi Penghapusan",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            
            if (response == JOptionPane.YES_OPTION) {
                // Menghapus file
                if (file_hapus.delete()) {
                    System.out.println("File berhasil dihapus: " + filehapusPath);
                    JOptionPane.showMessageDialog(
                            null,
                            "File "+judul_file+" berhasil dihapus: " + filehapusPath,
                            "Informasi",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    System.out.println("Gagal menghapus file "+judul_file+": " + filehapusPath);
                }
            }else {
                JOptionPane.showMessageDialog(
                        null,
                        "Penghapusan file "+judul_file+" dibatalkan.",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            System.out.println("File tidak ditemukan: " + filehapusPath);
        }

        // Kata kunci yang dicari dalam nama file
        String keyword = nama_depan_file+txtSep.getText()+"_";

        // Membuat objek File untuk direktori
        File directory = new File(directoryPath);

        // Pastikan direktori ada
        if (directory.exists() && directory.isDirectory()) {
            // Filter file berdasarkan nama yang mengandung kata kunci
            File[] filesToDelete = directory.listFiles((dir, name) -> 
                name.contains(keyword) && name.endsWith(".pdf")
            );

            // Periksa apakah ada file yang sesuai
            if (filesToDelete != null && filesToDelete.length > 0) {
                for (File file_data : filesToDelete) {
                    if (file_data.delete()) {
                        System.out.println("File "+judul_file+" berhasil dihapus: " + file_data.getName());
                    } else {
                        System.out.println("Gagal menghapus file "+judul_file+": " + file_data.getName());
                    }
                }
            } else {
                System.out.println("Tidak ada file yang sesuai dengan kata kunci: " + keyword);
            }
        } else {
            System.out.println("Direktori tidak ditemukan: " + directoryPath);
        }
        tombol_tampil_file.setEnabled(false);
        tombol_tampil_file.setText(txtTombol);
    }

    private void getData() {
        int row=tbLab.getSelectedRow();
        if(row!= -1){
//            String id=tabMode.getValueAt(row,0).toString();
//            String ip_address=tabMode.getValueAt(row,1).toString();
//            String workstation=tabMode.getValueAt(row,2).toString();
//            txtNorawat.setText(id);
//            txtNoNota.setText(workstation);
//            txtNoSurat.setText(ip_address);

        }
    }
    
    public void setData(String no_rawat, String no_mr, String no_sep){
        txtNorawat.setText(no_rawat);
        txtNoRm.setText(no_mr);
        txtSep.setText(no_sep);
        tampil();
    }

    public void emptTeks() {
//        txtNorawat.setText("");
    }
    
    public JTable getTable(){
        return tbLab;
    }
    
    
    public static void mergePDFs(String[] filesToMerge, String mergedFilePath) throws IOException {
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        pdfMergerUtility.setDestinationFileName(mergedFilePath);

        for (String filePath : filesToMerge) {
            File file = new File(filePath);
            pdfMergerUtility.addSource(file);
        }

        pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }
    
    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    private void tampil_hasil_lab(String no_rawat, String no_rm, String no_sep) {
        //###################################
        //||                               ||
        //||    List data hasil lab        ||
        //||                               ||
        //###################################
        Valid.tabelKosong(tabLabMode);
        try{
            String query_lab = "SELECT * \n" +
                                "FROM (\n" +
                                "	SELECT\n" +
                                "		'LAB PK' AS tipe_lab,\n" +
                                "		permintaan_lab.noorder,\n" +
                                "		permintaan_lab.no_rawat,\n" +
                                "		reg_periksa.no_rkm_medis,\n" +
                                "		pasien.nm_pasien,\n" +
                                "		permintaan_lab.tgl_permintaan,\n" +
                                "		permintaan_lab.jam_permintaan,\n" +
                                "		permintaan_lab.tgl_sampel,\n" +
                                "		permintaan_lab.jam_sampel,\n" +
                                "		permintaan_lab.tgl_hasil,\n" +
                                "		permintaan_lab.jam_hasil,\n" +
                                "		permintaan_lab.dokter_perujuk,\n" +
                                "		permintaan_lab.`status`,\n" +
                                "		permintaan_lab.informasi_tambahan,\n" +
                                "		permintaan_lab.diagnosa_klinis\n" +
                                "	FROM\n" +
                                "		permintaan_lab\n" +
                                "	LEFT JOIN reg_periksa ON permintaan_lab.no_rawat = reg_periksa.no_rawat\n" +
                                "	LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n" +
                                "	\n" +
                                "	UNION ALL\n" +
                                "	\n" +
                                "	SELECT\n" +
                                "		'LAB PA' AS tipe_lab,\n" +
                                "		permintaan_labpa.noorder,\n" +
                                "		permintaan_labpa.no_rawat,\n" +
                                "		reg_periksa.no_rkm_medis,\n" +
                                "		pasien.nm_pasien,\n" +
                                "		permintaan_labpa.tgl_permintaan,\n" +
                                "		permintaan_labpa.jam_permintaan,\n" +
                                "		permintaan_labpa.tgl_sampel,\n" +
                                "		permintaan_labpa.jam_sampel,\n" +
                                "		permintaan_labpa.tgl_hasil,\n" +
                                "		permintaan_labpa.jam_hasil,\n" +
                                "		permintaan_labpa.dokter_perujuk,\n" +
                                "		permintaan_labpa.`status`,\n" +
                                "		permintaan_labpa.informasi_tambahan,\n" +
                                "		CONCAT('Diagnosa Klinis: ',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.diagnosa_klinis,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Pengambilan Bahan:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.pengambilan_bahan,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Diperoleh Dengan:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.diperoleh_dengan,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Lokasi Jaringan:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.lokasi_jaringan,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Diawetkan Dengan:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.diawetkan_dengan,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Pernah Dilakukan Di:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.pernah_dilakukan_di,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Tgl PA Sebelumnya:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.tanggal_pa_sebelumnya,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Nomor PA Sebelumnya:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.nomor_pa_sebelumnya,\n" +
                                "					CHAR(10),\n" +
                                "					'----------------',\n" +
                                "					CHAR(10),\n" +
                                "					'Diagnosa PA Sebelumnya:',\n" +
                                "					CHAR(10),\n" +
                                "					permintaan_labpa.diagnosa_pa_sebelumnya\n" +
                                "				) AS diagnosa_klinis\n" +
                                "	FROM\n" +
                                "		permintaan_labpa\n" +
                                "	LEFT JOIN reg_periksa ON permintaan_labpa.no_rawat = reg_periksa.no_rawat\n" +
                                "	LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n" +
                                ") AS combined_data\n" +
                                "WHERE no_rawat = '"+no_rawat+"'\n" +
                                "ORDER BY tgl_permintaan, jam_permintaan ASC";
            System.out.println("query_tampil: "+query_lab);
            ps=koneksi.prepareStatement(query_lab);
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabLabMode.addRow(new Object[]{
                        rs.getString("tipe_lab"),
                        rs.getString("noorder"),
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("tgl_permintaan"),
                        rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),
                        rs.getString("jam_sampel"),
                        rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),
                        rs.getString("dokter_perujuk"),
                        rs.getString("status"),
                        rs.getString("informasi_tambahan"),
                        rs.getString("diagnosa_klinis")
                    });
                 }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void tampil_hasil_rad(String no_rawat, String no_rm, String no_sep) {
        //###################################
        //||                               ||
        //||    List data hasil rad        ||
        //||                               ||
        //###################################
        Valid.tabelKosong(tabRadMode);
        try{
            String query_rad = "SELECT \n" +
                                "	permintaan_radiologi.noorder,\n" +
                                "	permintaan_radiologi.no_rawat,\n" +
                                "	reg_periksa.no_rkm_medis, \n" +
                                "	bridging_sep.no_sep, \n" +
                                "	pasien.nm_pasien,\n" +
                                "	permintaan_radiologi.tgl_permintaan,\n" +
                                "	permintaan_radiologi.jam_permintaan,\n" +
                                "	permintaan_radiologi.tgl_sampel,\n" +
                                "	permintaan_radiologi.jam_sampel,\n" +
                                "	permintaan_radiologi.tgl_hasil,\n" +
                                "	permintaan_radiologi.jam_hasil,\n" +
                                "	permintaan_radiologi.dokter_perujuk,\n" +
                                "	permintaan_radiologi.`status`,\n" +
                                "	permintaan_radiologi.informasi_tambahan,\n" +
                                "	permintaan_radiologi.diagnosa_klinis\n" +
                                "FROM\n" +
                                "	permintaan_radiologi\n" +
                                "LEFT JOIN reg_periksa ON permintaan_radiologi.no_rawat = reg_periksa.no_rawat\n" +
                                "LEFT JOIN bridging_sep ON permintaan_radiologi.no_rawat = bridging_sep.no_rawat\n" +
                                "LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n" +
                                "WHERE\n" +
                                "	permintaan_radiologi.no_rawat = '"+no_rawat+"'";
            System.out.println("query_tampil: "+query_rad);
            ps=koneksi.prepareStatement(query_rad);
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabRadMode.addRow(new Object[]{
                        rs.getString("noorder"),
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("no_sep"),
                        rs.getString("nm_pasien"),
                        rs.getString("tgl_permintaan"),
                        rs.getString("jam_permintaan"),
                        rs.getString("tgl_sampel"),
                        rs.getString("jam_sampel"),
                        rs.getString("tgl_hasil"),
                        rs.getString("jam_hasil"),
                        rs.getString("dokter_perujuk"),
                        rs.getString("status"),
                        rs.getString("informasi_tambahan"),
                        rs.getString("diagnosa_klinis")
                    });
                 }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
}

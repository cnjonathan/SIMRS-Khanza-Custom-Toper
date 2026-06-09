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


package keuangan;

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
public class DlgPenjaminanProses extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    // private Connection koneksiradiologi;
    private sekuel Sequel=new sekuel();
    private validasi2 Valid=new validasi2();
    // private TwainScan twain = new TwainScan();
    private ResultSet rs, rs2,rssetjam;
    private PreparedStatement ps, psakunbayar, pssetjam,pscaripiutang,psdiagnosa,psibu,psanak,pstarif,psdpjp,pscariumur;
    private int i=0,pilihan=0,sudah=0,jmlparsial=0;
    private String export_path = "", merged_path = "", naps2_path = "", naps2_profile = "", filepath = "", filescanpath = "", fileinacbgpath="", filemergedpath;
    private static final Properties prop = new Properties();  
    private final INACBGCariCoderNIK cariNIK=new INACBGCariCoderNIK(null,true);
    private final INACBGHybrid inacbgklaim=new INACBGHybrid(null,true);
    private String coder_nik="",pilihpage="",judulform="";
    private String gabungkan="",norawatgabung="",kamaryangdigabung="",dokterranap="",bangsal="",diagnosa_akhir="",namakamar="",umur="0",sttsumur="Th";
    public  DlgBilingRanap billing=new DlgBilingRanap( null,true);
    public DlgBilingRalan billingralan=new DlgBilingRalan(null,false);
    private PreparedStatement psotomatis,psotomatis2,pskasir,psrekening;
    private ResultSet rskasir,rsrekening;
    private String aktifkanparsial="no",kamar_inap_kasir_ralan=Sequel.cariIsi("select set_jam_minimal.kamar_inap_kasir_ralan from set_jam_minimal"),caripenjab="",filter="no",nonota="",
            sqlpsotomatis2="insert into rawat_jl_dr values (?,?,?,?,?,?,?,?,?,?,?,'Belum')",
            sqlpsotomatis2petugas="insert into rawat_jl_pr values (?,?,?,?,?,?,?,?,?,?,?,'Belum')",
            sqlpsotomatis2dokterpetugas="insert into rawat_jl_drpr values (?,?,?,?,?,?,?,?,?,?,?,?,?,'Belum')",
            sqlpsotomatisdokterpetugas=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakandr,jns_perawatan.tarif_tindakanpr,jns_perawatan.total_byrdrpr,"+
                "jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan_dokterpetugas "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan_dokterpetugas.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan_dokterpetugas.kd_dokter=? and set_otomatis_tindakan_ralan_dokterpetugas.kd_pj=?",
            sqlpsotomatispetugas=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakanpr,jns_perawatan.total_byrpr,jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan_petugas "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan_petugas.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan_petugas.kd_pj=?",
            sqlpsotomatis=
                "select jns_perawatan.kd_jenis_prw,jns_perawatan.material,jns_perawatan.bhp,"+
                "jns_perawatan.tarif_tindakandr,jns_perawatan.total_byrdr,jns_perawatan.kso,jns_perawatan.menejemen from set_otomatis_tindakan_ralan "+
                "inner join jns_perawatan on set_otomatis_tindakan_ralan.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                "where set_otomatis_tindakan_ralan.kd_dokter=? and set_otomatis_tindakan_ralan.kd_pj=?",
            namadokter="",namapoli="",
            validasicatatan=Sequel.cariIsi("select set_validasi_catatan.tampilkan_catatan from set_validasi_catatan"),
            Suspen_Piutang_Tindakan_Ralan="",Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="",Beban_KSO_Tindakan_Ralan="",Utang_KSO_Tindakan_Ralan="",
            Beban_Jasa_Sarana_Tindakan_Ralan="",Utang_Jasa_Sarana_Tindakan_Ralan="",HPP_BHP_Tindakan_Ralan="",Persediaan_BHP_Tindakan_Ralan="",terbitsep="",
            Beban_Jasa_Menejemen_Tindakan_Ralan="",Utang_Jasa_Menejemen_Tindakan_Ralan="",tampildiagnosa="",finger="",norawatdipilih="",normdipilih="",
            variabel="";
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttljasasarana=0,ttlbhp=0,ttlmenejemen=0,ttlpendapatan=0;
    private boolean sukses=false;
    private Jurnal jur=new Jurnal();

    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgPenjaminanProses(java.awt.Frame parent, boolean modal) {
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
            Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
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
        
        Object[] row={"Pilih Surat",
                      "Tgl Surat",
                      "No Surat",
                      "No RM",
                      "Pasien",
                      "Penilaian",
                      "RTL",
                      "Keluhan",
                      "Instruksi",
                      "Evaluasi",
                      "Tgl Rencana",
                      "Poli",
                      "Dokter",
                      "Kode Poli"};
       
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              
              Class[] types = new Class[] {
                java.lang.Boolean.class, //Pilih Surat
                java.lang.Object.class, //tgl_surat
                java.lang.Object.class, //no_surat
                java.lang.Object.class, //no_rkm_medis
                java.lang.Object.class, //nm_pasien
                java.lang.Object.class, //penilaian
                java.lang.Object.class, //rtl
                java.lang.Object.class, //keluhan
                java.lang.Object.class, //instruksi
                java.lang.Object.class, //evaluasi
                java.lang.Object.class, //tgl_rencana
                java.lang.Object.class, //nm_poli_bpjs
                java.lang.Object.class, //nm_dokter_bpjs
                java.lang.Object.class //kd_poli
             };
             
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbSuratKontrol.setModel(tabMode);
        //tampil();
        tbSuratKontrol.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbSuratKontrol.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbSuratKontrol.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50); //Pilih Surat
            }else if(i==1){
                column.setPreferredWidth(80); //tgl_surat
            }else if(i==2){
                column.setPreferredWidth(180); //no_surat
            }else if(i==3){
                column.setPreferredWidth(80); //no_rkm_medis
            }else if(i==4){
                column.setPreferredWidth(150); //nm_pasien
            }else if(i==5){
                column.setPreferredWidth(300); //penilaian
            }else if(i==6){
                column.setPreferredWidth(300); //rtl
            }else if(i==7){
                column.setPreferredWidth(300); //keluhan
            }else if(i==8){
                column.setPreferredWidth(300); //instruksi
            }else if(i==9){
                column.setPreferredWidth(300); //evaluasi
            }else if(i==10){
                column.setPreferredWidth(80); //tgl_rencana
            }else if(i==11){
                column.setPreferredWidth(150); //nm_poli_bpjs
            }else if(i==12){
                column.setPreferredWidth(250); //nm_dokter_bpjs
            }else if(i==13){
                column.setPreferredWidth(0); //kd_poli
            }
        }
        tbSuratKontrol.setDefaultRenderer(Object.class, new WarnaTable());
        
        try {
            psrekening=koneksi.prepareStatement(
                    "select set_akun_ralan.Suspen_Piutang_Tindakan_Ralan,set_akun_ralan.Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,set_akun_ralan.Beban_KSO_Tindakan_Ralan,set_akun_ralan.Utang_KSO_Tindakan_Ralan,"+
                    "set_akun_ralan.Beban_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Utang_Jasa_Sarana_Tindakan_Ralan,set_akun_ralan.Beban_Jasa_Menejemen_Tindakan_Ralan,"+
                    "set_akun_ralan.Utang_Jasa_Menejemen_Tindakan_Ralan,set_akun_ralan.HPP_BHP_Tindakan_Ralan,set_akun_ralan.Persediaan_BHP_Tindakan_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Tindakan_Ralan=rsrekening.getString("Suspen_Piutang_Tindakan_Ralan");
                    Tindakan_Ralan=rsrekening.getString("Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rsrekening.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rsrekening.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Tindakan_Ralan");
                    Utang_Jasa_Sarana_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Tindakan_Ralan");
                    Beban_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Tindakan_Ralan");
                    Utang_Jasa_Menejemen_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Tindakan_Ralan");
                    HPP_BHP_Tindakan_Ralan=rsrekening.getString("HPP_BHP_Tindakan_Ralan");
                    Persediaan_BHP_Tindakan_Ralan=rsrekening.getString("Persediaan_BHP_Tindakan_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }            
        } catch (Exception e) {
            System.out.println(e);
        }
        
//        txtNorawat.setEditable(false);
//        txtNoNota.setDocument(new batasInput((byte)30).getKata(txtNoNota));
//        txtNoSurat.setDocument(new batasInput((byte)30).getKata(txtNoSurat));
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        Scroll = new widget.ScrollPane();
        tbSuratKontrol = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        txtRadiologi = new javax.swing.JButton();
        btnNota = new javax.swing.JButton();
        txtLab = new javax.swing.JButton();
        txtIna = new javax.swing.JButton();
        txtPdfTambahan = new javax.swing.JButton();
        btnINA = new javax.swing.JButton();
        txtScanInacbg = new javax.swing.JButton();
        txtScanTambahan = new javax.swing.JButton();
        txtMergedPdf = new javax.swing.JButton();
        panelGlass7 = new widget.panelisi();
        jLabel3 = new widget.Label();
        txtNoNota = new widget.TextBox();
        jLabel4 = new widget.Label();
        txtNoSurat = new widget.TextBox();
        jLabel5 = new widget.Label();
        txtNorawat = new widget.TextBox();
        jLabel6 = new widget.Label();
        txtNoRm = new widget.TextBox();
        jLabel7 = new widget.Label();
        txtSep = new widget.TextBox();
        BtnPrint1 = new widget.Button();
        panelGlass5 = new widget.panelisi();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        BtnCari = new widget.Button();
        BtnPrintSemua = new widget.Button();
        BtnAttachmentINACBG = new widget.Button();
        BtnDeleteInacbg = new widget.Button();
        BtnAttachment = new widget.Button();
        BtnDeletePdf = new widget.Button();
        BtnScan = new widget.Button();
        BtnDeleteScan = new widget.Button();
        BtnEditBilling = new widget.Button();
        BtnKeluar = new widget.Button();
        statusScan = new javax.swing.JLabel();
        BtnDeleteMergedPdf = new widget.Button();
        BtnDeleteSuratKontrol = new widget.Button();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder("Menu Penjaminan"));
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(490, 750));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setMinimumSize(new java.awt.Dimension(470, 500));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(480, 500));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview Data"));
        jPanel2.setMinimumSize(new java.awt.Dimension(20, 500));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(20, 500));
        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        Scroll.setBorder(javax.swing.BorderFactory.createTitledBorder("List Surat Kontrol"));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(460, 200));

        tbSuratKontrol.setAutoCreateRowSorter(true);
        tbSuratKontrol.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbSuratKontrol.setName("tbSuratKontrol"); // NOI18N
        tbSuratKontrol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSuratKontrolMouseClicked(evt);
            }
        });
        tbSuratKontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbSuratKontrolKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbSuratKontrol);

        jSplitPane1.setLeftComponent(Scroll);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Pendukung (Klik Tombol Untuk Preview Data)"));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(450, 300));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtRadiologi.setText("Radiologi: Tidak Ada Data");
        txtRadiologi.setName("txtRadiologi"); // NOI18N
        txtRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRadiologiActionPerformed(evt);
            }
        });
        jPanel4.add(txtRadiologi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 440, -1));

        btnNota.setText("Nota: Belum Proses Billing");
        btnNota.setName("btnNota"); // NOI18N
        btnNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotaActionPerformed(evt);
            }
        });
        jPanel4.add(btnNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 440, -1));

        txtLab.setText("Lab: Tidak Ada Data");
        txtLab.setName("txtLab"); // NOI18N
        txtLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLabActionPerformed(evt);
            }
        });
        jPanel4.add(txtLab, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 440, -1));

        txtIna.setText("Lembar Bukti Pelayanan");
        txtIna.setName("txtIna"); // NOI18N
        txtIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInaActionPerformed(evt);
            }
        });
        jPanel4.add(txtIna, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, 440, 20));

        txtPdfTambahan.setText("PDF Tambahan");
        txtPdfTambahan.setName("txtPdfTambahan"); // NOI18N
        txtPdfTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPdfTambahanActionPerformed(evt);
            }
        });
        jPanel4.add(txtPdfTambahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 440, -1));

        btnINA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/upload24.png"))); // NOI18N
        btnINA.setText("INACBG");
        btnINA.setName("btnINA"); // NOI18N
        btnINA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnINAActionPerformed(evt);
            }
        });
        jPanel4.add(btnINA, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 440, -1));

        txtScanInacbg.setText("Berkas INACBG");
        txtScanInacbg.setName("txtScanInacbg"); // NOI18N
        txtScanInacbg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtScanInacbgActionPerformed(evt);
            }
        });
        jPanel4.add(txtScanInacbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 440, -1));

        txtScanTambahan.setText("Scan Tambahan");
        txtScanTambahan.setName("txtScanTambahan"); // NOI18N
        txtScanTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtScanTambahanActionPerformed(evt);
            }
        });
        jPanel4.add(txtScanTambahan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 440, -1));

        txtMergedPdf.setText("Preview Combined PDF");
        txtMergedPdf.setName("txtMergedPdf"); // NOI18N
        txtMergedPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMergedPdfActionPerformed(evt);
            }
        });
        jPanel4.add(txtMergedPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 440, -1));

        jSplitPane1.setRightComponent(jPanel4);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.LINE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass7.setLayout(null);

        jLabel3.setText("No Nota");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass7.add(jLabel3);
        jLabel3.setBounds(470, 10, 60, 23);

        txtNoNota.setHighlighter(null);
        txtNoNota.setName("txtNoNota"); // NOI18N
        txtNoNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoNotaActionPerformed(evt);
            }
        });
        txtNoNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoNotaKeyPressed(evt);
            }
        });
        panelGlass7.add(txtNoNota);
        txtNoNota.setBounds(540, 10, 250, 23);

        jLabel4.setText("No Surat");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(790, 10, 60, 23);

        txtNoSurat.setName("txtNoSurat"); // NOI18N
        txtNoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoSuratKeyPressed(evt);
            }
        });
        panelGlass7.add(txtNoSurat);
        txtNoSurat.setBounds(860, 10, 230, 24);

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
        jLabel7.setBounds(1100, 10, 60, 23);

        txtSep.setName("txtSep"); // NOI18N
        txtSep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSepKeyPressed(evt);
            }
        });
        panelGlass7.add(txtSep);
        txtSep.setBounds(1170, 10, 230, 24);

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
        BtnPrint1.setBounds(1410, 10, 100, 30);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        panelGlass5.setMinimumSize(new java.awt.Dimension(1414, 110));
        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 120));
        panelGlass5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setText("Rubah Status Klaim Jadi:");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 13, -1, -1));

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Belum", "Sudah", "Batal" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass5.add(cmbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 13, -1, -1));

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Save.png"))); // NOI18N
        BtnCari.setMnemonic('6');
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
        panelGlass5.add(BtnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(286, 13, -1, -1));

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

        BtnAttachmentINACBG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAttachmentINACBG.setMnemonic('K');
        BtnAttachmentINACBG.setText("Tambah Berkas INACBG");
        BtnAttachmentINACBG.setToolTipText("Alt+K");
        BtnAttachmentINACBG.setIconTextGap(3);
        BtnAttachmentINACBG.setName("BtnAttachmentINACBG"); // NOI18N
        BtnAttachmentINACBG.setPreferredSize(new java.awt.Dimension(190, 30));
        BtnAttachmentINACBG.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnAttachmentINACBGMouseClicked(evt);
            }
        });
        BtnAttachmentINACBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAttachmentINACBGActionPerformed(evt);
            }
        });
        BtnAttachmentINACBG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAttachmentINACBGKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnAttachmentINACBG, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 170, 20));

        BtnDeleteInacbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/delete-16x16.png"))); // NOI18N
        BtnDeleteInacbg.setMnemonic('K');
        BtnDeleteInacbg.setText("Hapus Berkas INACBG");
        BtnDeleteInacbg.setToolTipText("Alt+K");
        BtnDeleteInacbg.setIconTextGap(3);
        BtnDeleteInacbg.setName("BtnDeleteInacbg"); // NOI18N
        BtnDeleteInacbg.setPreferredSize(new java.awt.Dimension(190, 30));
        BtnDeleteInacbg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnDeleteInacbgMouseClicked(evt);
            }
        });
        BtnDeleteInacbg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteInacbgActionPerformed(evt);
            }
        });
        BtnDeleteInacbg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDeleteInacbgKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnDeleteInacbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 190, 20));

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
        panelGlass5.add(BtnAttachment, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 50, 170, 20));

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
        panelGlass5.add(BtnDeletePdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, 190, 20));

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
        panelGlass5.add(BtnScan, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 110, 20));

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

        BtnEditBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Money-32x32.png"))); // NOI18N
        BtnEditBilling.setMnemonic('K');
        BtnEditBilling.setText("Edit Billing");
        BtnEditBilling.setToolTipText("Alt+K");
        BtnEditBilling.setIconTextGap(3);
        BtnEditBilling.setName("BtnEditBilling"); // NOI18N
        BtnEditBilling.setPreferredSize(new java.awt.Dimension(150, 30));
        BtnEditBilling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnEditBillingMouseClicked(evt);
            }
        });
        BtnEditBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditBillingActionPerformed(evt);
            }
        });
        BtnEditBilling.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditBillingKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnEditBilling, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 110, 20));

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
        panelGlass5.add(BtnKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 310, 20));

        statusScan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusScan.setName("statusScan"); // NOI18N
        statusScan.setPreferredSize(new java.awt.Dimension(500, 16));
        panelGlass5.add(statusScan, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 300, 30));

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
        panelGlass5.add(BtnDeleteMergedPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 190, 20));

        BtnDeleteSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/delete-16x16.png"))); // NOI18N
        BtnDeleteSuratKontrol.setMnemonic('K');
        BtnDeleteSuratKontrol.setText("Hapus Surat Kontrol");
        BtnDeleteSuratKontrol.setToolTipText("Alt+K");
        BtnDeleteSuratKontrol.setIconTextGap(3);
        BtnDeleteSuratKontrol.setName("BtnDeleteSuratKontrol"); // NOI18N
        BtnDeleteSuratKontrol.setPreferredSize(new java.awt.Dimension(190, 30));
        BtnDeleteSuratKontrol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnDeleteSuratKontrolMouseClicked(evt);
            }
        });
        BtnDeleteSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDeleteSuratKontrolActionPerformed(evt);
            }
        });
        BtnDeleteSuratKontrol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDeleteSuratKontrolKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnDeleteSuratKontrol, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 30, 190, 20));

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoNotaKeyPressed
        
}//GEN-LAST:event_txtNoNotaKeyPressed

    private void txtNoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoSuratKeyPressed
        
}//GEN-LAST:event_txtNoSuratKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
        emptTeks();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
                dispose();
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbSuratKontrolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSuratKontrolMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(evt.getClickCount()==1){
                        
                    jPanel2.removeAll();
                    jPanel2.repaint();
                    jPanel2.revalidate();

                    int count_row = tbSuratKontrol.getRowCount();
                    for (int j = 0; j < count_row; j++) {
                        if (j == tbSuratKontrol.getSelectedRow()) {
                            tbSuratKontrol.setValueAt(true, tbSuratKontrol.getSelectedRow(), 0);
                        }else{
                            tbSuratKontrol.setValueAt(false, j, 0);
                        }
                    }

                    String query_surat_kontrol = "SELECT \n" +
                                                "    bskb.no_surat,\n" +
                                                "    rp.no_rkm_medis,\n" +
                                                "    p.nm_pasien,\n" +
                                                "    pr.penilaian,\n" +
                                                "    pr.rtl,\n" +
                                                "    bskb.tgl_surat,\n" +
                                                "    pr.keluhan,\n" +
                                                "    pr.instruksi,\n" +
                                                "    pr.evaluasi,\n" +
                                                "    bskb.tgl_rencana,\n" +
                                                "    bskb.nm_poli_bpjs,\n" +
                                                "    bskb.nm_dokter_bpjs,\n" +
                                                "    rp.kd_poli\n" +
                                                "FROM\n" +
                                                "	bridging_surat_kontrol_bpjs bskb\n" +
                                                "INNER JOIN\n" +
                                                "	bridging_sep bs ON bskb.no_sep = bs.no_sep\n" +
                                                "INNER JOIN\n" +
                                                "	reg_periksa rp ON bs.no_rawat = rp.no_rawat\n" +
                                                "INNER JOIN\n" +
                                                "	pemeriksaan_ralan pr ON rp.no_rawat = pr.no_rawat AND rp.kd_dokter = pr.nip\n" +
                                                "INNER JOIN\n" +
                                                "	pasien p ON rp.no_rkm_medis = p.no_rkm_medis\n" +
                                                "WHERE \n" +
                                                // "	rp.no_rawat  = '"+txtNorawat.getText()+"' AND"+
                                                "	bskb.no_surat = '"+tbSuratKontrol.getValueAt(tbSuratKontrol.getSelectedRow(), 2).toString()+"' ";

                    System.out.println("query_surat_kontrolllll: "+query_surat_kontrol);
                    ps=koneksi.prepareStatement(query_surat_kontrol);
                    rs=ps.executeQuery();
                    
                    rs.next();
                    int nomor = 1;
                    String no_surat = rs.getString("no_surat");
                    String no_rkm_medis = rs.getString("no_rkm_medis");
                    String nm_pasien = rs.getString("nm_pasien");
                    String penilaian = rs.getString("penilaian");
                    String rtl = rs.getString("rtl");
                    String tgl_surat = rs.getString("tgl_surat");
                    String keluhan = "";
                    String instruksi = "";
                    String evaluasi = "";
                    String tgl_rencana = rs.getString("tgl_rencana");
                    String nm_poli_bpjs = rs.getString("nm_poli_bpjs");
                    String nm_dokter_bpjs = rs.getString("nm_dokter_bpjs");
                    String kd_poli = rs.getString("kd_poli");
                    
                    // mengolah string keluhan
                    rs.beforeFirst();
                    while (rs.next()) {
                        if(!rs.getString("keluhan").equals("")){
                            keluhan = keluhan+nomor+". "+rs.getString("keluhan")+". \n";
                            nomor++;
                        }
                    }
                    keluhan = keluhan+nomor+". Belum membaik dan membutuhkan evaluasi. \n";
                    nomor = 1;
                    
                    // mengolah string instruksi
                    rs.beforeFirst();
                    while (rs.next()) {
                        instruksi = instruksi+nomor+". "+(rs.getString("instruksi").equals("") ? "- \n" : rs.getString("instruksi")+". \n");
                        nomor++;
                    }
                    
                    // mengolah string evaluasi
                    rs.beforeFirst();
                    while (rs.next()) {
                        evaluasi = evaluasi+nomor+". "+(rs.getString("evaluasi").equals("") ? "- \n" : rs.getString("evaluasi")+". \n");
                        nomor++;
                    }
                    
                    System.out.println("Keluhan: "+keluhan);
                    System.out.println("Instruksi: "+instruksi);
                    System.out.println("Evaluasi: "+evaluasi);
                    
                    PreparedStatement ps_date = koneksi.prepareStatement("select current_date as tanggal;");
                    ResultSet rs_date = ps_date.executeQuery();
                    JRResultSetDataSource rsdt = new JRResultSetDataSource(rs_date);

                    Map<String, Object> parameter = new HashMap<>();  
                    parameter.put("namars",akses.getnamars());
                    parameter.put("alamatrs",akses.getalamatrs());
                    parameter.put("kotars",akses.getkabupatenrs());
                    parameter.put("propinsirs",akses.getpropinsirs());
                    parameter.put("kontakrs",akses.getkontakrs());
                    parameter.put("emailrs",akses.getemailrs());   
                    parameter.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                    parameter.put("no_surat",no_surat);
                    parameter.put("no_rkm_medis",no_rkm_medis);
                    parameter.put("nm_pasien",nm_pasien);
                    parameter.put("penilaian",penilaian);
                    parameter.put("rtl",rtl);
                    parameter.put("tgl_surat",tgl_surat);
                    parameter.put("keluhan",keluhan);
                    parameter.put("instruksi",instruksi);
                    parameter.put("evaluasi",evaluasi);
                    parameter.put("tgl_rencana",tgl_rencana);
                    parameter.put("nm_poli_bpjs",nm_poli_bpjs);
                    parameter.put("nm_dokter_bpjs",nm_dokter_bpjs);
                    parameter.put("kd_poli",kd_poli);

                    Properties systemProp = System.getProperties();

                    // Ambil current dir
                    String currentDir = systemProp.getProperty("user.dir");

                    File dir = new File(currentDir);

                    File fileRpt;
                    String fullPath = "";
                    if (dir.isDirectory()) {
                        String[] isiDir = dir.list();
                        for (String iDir : isiDir) {
                            fileRpt = new File(currentDir + File.separatorChar + iDir + File.separatorChar + "rptSuratKontrolPenjaminan.jasper");
//                            String path = currentDir + File.separatorChar + iDir + File.separatorChar + "rptNotaRalan.jasper";
                            if (fileRpt.isFile()) { // Cek apakah file RptMaster.jrxml ada
                                fullPath = fileRpt.toString();
                                System.out.println("Found Report File at : " + fullPath);
                            } // end if
                        } // end for i
                    } // end if
                    System.out.println("fullpath surat kontrol: "+fullPath);
                    JasperPrint jprint = JasperFillManager.fillReport(fullPath, parameter, rsdt);

                    JRViewer v = new JRViewer(jprint);
                    jPanel2.setLayout(new BorderLayout());
                    jPanel2.add(v);
                    
                    // Export to PDF
                    // String nama_file_export = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
                    String nama_file_export = export_path+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
                    Path path = Paths.get(nama_file_export);

                    // Export to PDF
                    if (Files.notExists(path)) {
                        // file is not exist
                        System.out.println("file is not exist: ");
                        JasperExportManager.exportReportToPdfFile(jprint, nama_file_export);
                    }else{
                        System.out.println("file is exist: ");
                        // Files.delete(path);
                        if (Files.deleteIfExists(path)) {
                            JasperExportManager.exportReportToPdfFile(jprint, nama_file_export);
                        }
                    }
                    
                }else if(evt.getClickCount()==2){
                    i=tbSuratKontrol.getSelectedColumn();
                    if(i==0){
                        // something else
                    }
                }
            } catch (java.lang.NullPointerException e) {
            } catch (SQLException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}//GEN-LAST:event_tbSuratKontrolMouseClicked

    private void tbSuratKontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSuratKontrolKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbSuratKontrolKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
//        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void txtNoNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoNotaActionPerformed

    private void txtNorawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNorawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNorawatActionPerformed

    private void txtNorawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNorawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNorawatKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        String status = cmbStatus.getSelectedItem().toString();
        System.out.println("Status rubah menjadi: "+status);
        Sequel.queryu("UPDATE `surat_kontrol_bpjs` SET `status`='"+status+"' WHERE `no_rawat`='"+txtNorawat.getText()+"'");
        DlgPenjaminan jamin = new DlgPenjaminan(null, false);
        if(Sequel.cariIsi("SELECT status FROM surat_kontrol_bpjs WHERE no_rawat = '"+txtNorawat.getText()+"'").equals("Sudah")){
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan. \nStatus dikerjakan: "+status);
            jamin.tampil();
        }
        if(Sequel.cariIsi("SELECT status FROM surat_kontrol_bpjs WHERE no_rawat = '"+txtNorawat.getText()+"'").equals("Belum")){
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan. \nStatus pengerjaan dikembalikan menjadi: "+status);
        }
        if(Sequel.cariIsi("SELECT status FROM surat_kontrol_bpjs WHERE no_rawat = '"+txtNorawat.getText()+"'").equals("Batal")){
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan. \nPengerjaan dibatalkan. \nStatus dikerjakan: "+status);
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
//            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void txtRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRadiologiActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        int cek_radiologi = Sequel.cariInteger("SELECT COUNT(*) FROM permintaan_radiologi WHERE no_rawat = '"+txtNorawat.getText()+"'");
        if(cek_radiologi > 0){
            // koneksiradiologi=koneksiDBFUJI.condb();
            String pemeriksaan = "";
            String hasil_expertise = "";
            String dokter_radiologi = "";

            int count_permintaan_radiologi = Sequel.cariInteger("SELECT COUNT(noorder) as total FROM permintaan_radiologi WHERE no_rawat = '"+txtNorawat.getText()+"'");
            String query_permintaan_radiologi = "SELECT noorder FROM permintaan_radiologi WHERE no_rawat = '"+txtNorawat.getText()+"'";
            String query_pemeriksaan="select \n" +
                                "  jns_perawatan_radiologi.kd_jenis_prw, \n" +
                                "  jns_perawatan_radiologi.nm_perawatan, \n" +
                                "  jns_perawatan_radiologi.total_byr, \n" +
                                "  jns_perawatan_radiologi.bagian_rs, \n" +
                                "  jns_perawatan_radiologi.bhp, \n" +
                                "  jns_perawatan_radiologi.tarif_perujuk, \n" +
                                "  jns_perawatan_radiologi.tarif_tindakan_dokter, \n" +
                                "  jns_perawatan_radiologi.tarif_tindakan_petugas, \n" +
                                "  jns_perawatan_radiologi.kso, \n" +
                                "  jns_perawatan_radiologi.menejemen, \n" +
                                "  penjab.png_jawab \n" +
                                "from \n" +
                                "  jns_perawatan_radiologi \n" +
                                "  inner join penjab \n" +
                                "  inner join permintaan_pemeriksaan_radiologi on penjab.kd_pj = jns_perawatan_radiologi.kd_pj \n" +
                                "  and jns_perawatan_radiologi.kd_jenis_prw = permintaan_pemeriksaan_radiologi.kd_jenis_prw \n" +
                                "where \n" +
                                "  permintaan_pemeriksaan_radiologi.noorder = ? \n" +
                                "order by \n" +
                                "  jns_perawatan_radiologi.kd_jenis_prw";
            String query_order_out = "select \n" +
                                    "  proyeksi, \n" +
                                    "  kV, \n" +
                                    "  mAS, \n" +
                                    "  FFD, \n" +
                                    "  BSF, \n" +
                                    "  inak, \n" +
                                    "  jml_penyinaran, \n" +
                                    "  dosis, \n" +
                                    "  link_ris, \n" +
                                    "  expertise_finding, \n" +
                                    "  expertise_conclusion, \n" +
                                    "  expertise_bookmark \n" +
                                    "from \n" +
                                    "  hasil_pacs_radiologi \n" +
                                    "where \n" +
                                    "  kd_jenis_prw = ? \n" +
                                    "  and no_order = ?";
            String query_expertise = "select \n" + 
                                "  dokter_radiolog, \n" +
                                "  expertise_finding, \n" +
                                "  expertise_conclusion, \n" +
                                "  expertise_bookmark \n" +
                                "from \n" +
                                "  hasil_pacs_radiologi \n" +
                                "where \n" +
                                "  kd_jenis_prw = ? AND \n" +
                                "  no_order = ?";
            String query_detail_pasien = "select \n" +
                                        "  permintaan_radiologi.noorder, \n" +
                                        "  permintaan_radiologi.no_rawat, \n" +
                                        "  reg_periksa.no_rkm_medis, \n" +
                                        "  pasien.nm_pasien, \n" +
                                        "  pasien.jk, \n" +
                                        "  pasien.umur, \n" +
                                        "  pasien.alamat, \n" +
                                        "  DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') AS lahir, \n" +
                                        "  permintaan_radiologi.tgl_permintaan, \n" +
                                        "  if(\n" +
                                        "    permintaan_radiologi.jam_permintaan = '00:00:00', \n" +
                                        "    '', permintaan_radiologi.jam_permintaan\n" +
                                        "  ) as jam_permintaan, \n" +
                                        "  reg_periksa.kd_pj, \n" +
                                        "  penjab.png_jawab, \n" +
                                        "  if(\n" +
                                        "    permintaan_radiologi.tgl_sampel = '0000-00-00', \n" +
                                        "    '', permintaan_radiologi.tgl_sampel\n" +
                                        "  ) as tgl_sampel, \n" +
                                        "  if(\n" +
                                        "    permintaan_radiologi.jam_sampel = '00:00:00', \n" +
                                        "    '', permintaan_radiologi.jam_sampel\n" +
                                        "  ) as jam_sampel, \n" +
                                        "  permintaan_radiologi.tgl_hasil, \n" +
                                        "  if(\n" +
                                        "    permintaan_radiologi.jam_hasil = '00:00:00', \n" +
                                        "    '', permintaan_radiologi.jam_hasil\n" +
                                        "  ) as jam_hasil, \n" +
                                        "  permintaan_radiologi.dokter_perujuk, \n" +
                                        "  dokter.nm_dokter, \n" +
                                        "  poliklinik.nm_poli, \n" +
                                        "  permintaan_radiologi.informasi_tambahan, \n" +
                                        "  permintaan_radiologi.diagnosa_klinis \n" +
                                        "from \n" +
                                        "  permintaan_radiologi \n" +
                                        "  inner join reg_periksa on permintaan_radiologi.no_rawat = reg_periksa.no_rawat \n" +
                                        "  inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis \n" +
                                        "  inner join dokter on permintaan_radiologi.dokter_perujuk = dokter.kd_dokter \n" +
                                        "  inner join poliklinik on reg_periksa.kd_poli = poliklinik.kd_poli \n" +
                                        "  inner join penjab on reg_periksa.kd_pj = penjab.kd_pj \n" +
                                        "where \n" +
                                        "  permintaan_radiologi.noorder = ? \n" +
                                        "  AND permintaan_radiologi.no_rawat = ?";

            System.out.println("count_permintaan_radiologi: "+count_permintaan_radiologi);

            try {
                PreparedStatement ps_permintaan_radiologi = koneksi.prepareStatement(query_permintaan_radiologi);
                ResultSet rs_permintaan_radiologi = ps_permintaan_radiologi.executeQuery();

                for (int j = 0; j < count_permintaan_radiologi; j++) {
                    rs_permintaan_radiologi.next();

                    System.out.println("noorder: "+rs_permintaan_radiologi.getString("noorder"));

                    PreparedStatement ps_pemeriksaan = koneksi.prepareStatement(query_pemeriksaan);
                    ps_pemeriksaan.setString(1, rs_permintaan_radiologi.getString("noorder"));
                    System.out.println("query_pemeriksaan: "+ps_pemeriksaan.toString());
                    ResultSet rs_pemeriksaan = ps_pemeriksaan.executeQuery();

                    while (rs_pemeriksaan.next()) {
                        System.out.println("nm_perawatan: "+rs_pemeriksaan.getString("nm_perawatan"));
                        pemeriksaan = pemeriksaan+" \n"+rs_pemeriksaan.getString("nm_perawatan");

                        PreparedStatement ps_order_out = koneksi.prepareStatement(query_order_out);
                        ps_order_out.setString(1, rs_pemeriksaan.getString("kd_jenis_prw"));
                        ps_order_out.setString(2, rs_permintaan_radiologi.getString("noorder").replace("PR", ""));
                        System.out.println("query_order_out: "+ps_order_out.toString());
                        ResultSet rs_order_out = ps_order_out.executeQuery();

                        PreparedStatement ps_expertise = koneksi.prepareStatement(query_expertise);
                        ps_expertise.setString(1, rs_pemeriksaan.getString("kd_jenis_prw"));
                        ps_expertise.setString(2, rs_permintaan_radiologi.getString("noorder").replace("PR", ""));
                        // ps_expertise.setString(2, rs_permintaan_radiologi.getString("noorder"));
                        System.out.println("query_expertise: "+ps_expertise.toString());
                        ResultSet rs_expertise = ps_expertise.executeQuery();

                        while (rs_order_out.next()) {
                            System.out.println("link_ris: "+rs_order_out.getString("link_ris"));
                            System.out.println("expertise_finding: "+rs_order_out.getString("expertise_finding"));

                            pemeriksaan=pemeriksaan+" dengan Proyeksi : "+rs_order_out.getString("proyeksi")+
                                        ", kV : "+rs_order_out.getString("kV")+
                                        ", mAS : "+rs_order_out.getString("mAS")+
                                        ", FFD : "+rs_order_out.getString("FFD")+
                                        ", BSF : "+rs_order_out.getString("BSF")+
                                        ", Inak : "+rs_order_out.getString("inak")+
                                        ", Jml Penyinaran : "+rs_order_out.getString("jml_penyinaran")+
                                        ", Dosis Radiasi : "+rs_order_out.getString("dosis")+
                                        ", \n";
                        }

                        while (rs_expertise.next()) {
                            hasil_expertise += "\n\n Expertise Finding: \n"+rs_expertise.getString("expertise_finding")+"\n"+
                                              "\n\n Expertise Conclusion: \n"+rs_expertise.getString("expertise_conclusion")+"\n"+
                                              "\n\n Expertise Bookmark: \n"+rs_expertise.getString("expertise_bookmark")+"\n";
                            dokter_radiologi = rs_expertise.getString("dokter_radiolog");
                        }


                    }
                }

                System.out.println("Pemeriksaan: \n"+pemeriksaan);
                System.out.println("Expertise: \n"+hasil_expertise);

                PreparedStatement ps_pasien_detail = koneksi.prepareStatement(query_detail_pasien);
                ps_pasien_detail.setString(1, rs_permintaan_radiologi.getString("noorder"));
                ps_pasien_detail.setString(2, txtNorawat.getText());
                ResultSet rs_pasien_detail = ps_pasien_detail.executeQuery();
                rs_pasien_detail.next();


                Map<String, Object> param = new HashMap<>();
                param.put("noperiksa",txtNorawat.getText());
                param.put("norm",txtNoRm.getText());
                param.put("namapasien",rs_pasien_detail.getString("nm_pasien"));
                param.put("jkel",rs_pasien_detail.getString("jk"));
                param.put("umur",rs_pasien_detail.getString("umur"));
                param.put("lahir",rs_pasien_detail.getString("lahir"));
                param.put("pengirim",rs_pasien_detail.getString("nm_dokter"));
                param.put("tanggal",rs_pasien_detail.getString("tgl_permintaan")); //tgl_permintaan
                param.put("penjab",dokter_radiologi); //png_jawab dokter_radiologi
                param.put("petugas","Petugas Radiologi");
                param.put("alamat",rs_pasien_detail.getString("alamat"));
                param.put("kamar","Poli");
                param.put("namakamar","Rawat Jalan");
                param.put("pemeriksaan",pemeriksaan);
                param.put("jam",rs_pasien_detail.getString("jam_permintaan"));
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("hasil",hasil_expertise);
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                // finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KodePj.getText());
                //param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmDokterPj.getText()+"\nID "+(finger.equals("")?KodePj.getText():finger)+"\n"+Tanggal.getSelectedItem());  
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh Dokter Radiologi"+dokter_radiologi);
                // finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",KdPtg.getText());
                //param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+NmPtg.getText()+"\nID "+(finger.equals("")?KdPtg.getText():finger)+"\n"+Tanggal.getSelectedItem());  
                param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh Petugas Radiologi");

                Properties systemProp = System.getProperties();

                // Ambil current dir
                String currentDir = systemProp.getProperty("user.dir");
                System.out.println("currentDir: "+currentDir);

                File dir = new File(currentDir);

                File fileRpt;
                String fullPath = "";
                if (dir.isDirectory()) {
                    String[] isiDir = dir.list();
                    for (String iDir : isiDir) {
                        String path = currentDir + File.separatorChar + iDir + File.separatorChar + "rptPeriksaRadiologi.jasper";
                        // System.out.println("fullPath: "+path);
                        fileRpt = new File(path);
                        if (fileRpt.isFile()) { // Cek apakah file rptNotaRalan.jrxml ada
                            fullPath = fileRpt.toString();
                            System.out.println("Found Report File at : " + fullPath);
                        } // end if
                    } // end for i
                } // end if

                PreparedStatement ps_rsdt = koneksi.prepareStatement("select current_date as tanggal;");
                ResultSet rs_rsdt = ps_rsdt.executeQuery();

                JRResultSetDataSource rsdt = new JRResultSetDataSource(rs_rsdt);

                JasperPrint jprint;
                jprint = JasperFillManager.fillReport(fullPath, param, rsdt);
                JRViewer v = new JRViewer(jprint);
                jPanel2.setLayout(new BorderLayout());
                jPanel2.add(v);

                // Export to PDF
                // String nama_file_export = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
                String nama_file_export = export_path+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
                Path path = Paths.get(nama_file_export);

                // Export to PDF
                if (Files.notExists(path)) {
                    // file is not exist
                    System.out.println("file is not exist: ");
                    JasperExportManager.exportReportToPdfFile(jprint, nama_file_export);
                }else{
                    System.out.println("file is exist: ");
                }
                txtRadiologi.setBackground(Color.GREEN);
            } catch (SQLException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }else{
            txtRadiologi.setText("Radiologi Belum/Tidak Ada");
            JOptionPane.showMessageDialog(null, "Tidak ada data radiologi");
        } 
    }//GEN-LAST:event_txtRadiologiActionPerformed

    private void btnNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotaActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        String query_nota = "";
        int cek_nota_jalan = Sequel.cariInteger("SELECT COUNT(*) FROM nota_jalan WHERE no_rawat = '"+txtNorawat.getText()+"'");
        int cek_nota_inap = Sequel.cariInteger("SELECT COUNT(*) FROM nota_inap WHERE no_rawat = '"+txtNorawat.getText()+"'");
        if(cek_nota_jalan > 0 || cek_nota_inap > 0){
            query_nota = "SELECT \n" +
                            "  billing.no, \n" +
                            "  billing.nm_perawatan, \n" +
                            "  billing.pemisah, \n" +
                            "  IF(\n" +
                            "    billing.biaya = 0, \"\", billing.biaya\n" +
                            "  ) AS biaya, \n" +
                            "  IF(\n" +
                            "    billing.jumlah = 0, \"\", billing.jumlah\n" +
                            "  ) AS jumlah, \n" +
                            "  IF(\n" +
                            "    billing.tambahan = 0, \"\", billing.tambahan\n" +
                            "  ) AS tambahan, \n" +
                            "  IF(\n" +
                            "    billing.totalbiaya = 0, \"\", billing.totalbiaya\n" +
                            "  ) AS totalbiaya \n" +
                            "FROM \n" +
                            "  billing \n" +
                            "WHERE \n" +
                            "  no_rawat = '"+txtNorawat.getText()+"' \n" +
                            "ORDER BY \n" +
                            "  noindex ASC;";

            System.out.println("query_surat_kontrolllll: "+query_nota);
            try {
                // cari total biaya semua
                String str_totalbiaya = Sequel.cariIsi("SELECT SUM(billing.totalbiaya) AS totalbiaya FROM billing WHERE no_rawat = '"+txtNorawat.getText()+"' ORDER BY noindex ASC;");
                Double totalbiaya = Double.parseDouble(str_totalbiaya == null ? "0.0" : str_totalbiaya);
                // cari PPN dari akun bayar pasien
                if(cek_nota_jalan > 0){
                    psakunbayar=koneksi.prepareStatement(
                         "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_jalan.besar_bayar,"+
                         "akun_bayar.ppn,detail_nota_jalan.besarppn from akun_bayar inner join detail_nota_jalan "+
                         "on akun_bayar.nama_bayar=detail_nota_jalan.nama_bayar where detail_nota_jalan.no_rawat=? order by nama_bayar");
                } else if(cek_nota_inap > 0){
                    psakunbayar=koneksi.prepareStatement(
                         "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_inap.besar_bayar,"+
                         "akun_bayar.ppn,detail_nota_inap.besarppn from akun_bayar inner join detail_nota_inap "+
                         "on akun_bayar.nama_bayar=detail_nota_inap.nama_bayar where detail_nota_inap.no_rawat=? order by nama_bayar");
                }
                
                psakunbayar.setString(1,txtNorawat.getText());
                ResultSet rsakunbayar=psakunbayar.executeQuery();
                Double besarppn = 0.0;
                Double besarbayar = 0.0;
                while(rsakunbayar.next()){
                    besarppn = Double.parseDouble(rsakunbayar.getString("besarppn"))+besarppn;
                    besarbayar = Double.parseDouble(rsakunbayar.getString("besar_bayar"))+besarbayar;
                } 

                // String str_besarppn = Sequel.cariIsi("SELECT detail_nota_jalan.besarppn  FROM detail_nota_jalan WHERE detail_nota_jalan.no_rawat LIKE '"+txtNorawat.getText()+"';");
                // Double besarppn = Double.parseDouble(str_besarppn == null ? "0.0" : str_besarppn);

                // String str_besarbiaya = Sequel.cariIsi("SELECT detail_nota_jalan.besar_bayar  FROM detail_nota_jalan WHERE detail_nota_jalan.no_rawat LIKE '"+txtNorawat.getText()+"';");
                // Double besarbayar = Double.parseDouble(str_besarbiaya == null ? "0.0" : str_besarbiaya);

                Double tagihanppn = totalbiaya + besarppn;
                Double ekses = besarbayar;

                String str_piutang = "0.0";
                Double piutang = 0.0;
                str_piutang = Sequel.cariIsi("SELECT detail_piutang_pasien.totalpiutang  FROM detail_piutang_pasien WHERE detail_piutang_pasien.no_rawat LIKE '"+txtNorawat.getText()+"' ORDER BY detail_piutang_pasien.no_rawat DESC");
                piutang = Double.parseDouble(str_piutang.isEmpty() ? "0.0" : str_piutang);


                //PreparedStatement psakunpiutang=koneksi.prepareStatement(
                //         "select akun_piutang.nama_bayar,akun_piutang.kd_rek,akun_piutang.kd_pj, "+
                //         "detail_piutang_pasien.totalpiutang,date_format(detail_piutang_pasien.tgltempo,'%d/%m/%Y') from "+
                //         "akun_piutang inner join detail_piutang_pasien on akun_piutang.nama_bayar=detail_piutang_pasien.nama_bayar "+
                //         "where detail_piutang_pasien.no_rawat=? order by nama_bayar");
                //psakunpiutang.setString(1,txtNorawat.getText());
                //ResultSet rsakunpiutang=psakunpiutang.executeQuery();
                //while(rsakunpiutang.next()){                    
                //
                //} 

                ps=koneksi.prepareStatement(query_nota);
                rs=ps.executeQuery();
                JRResultSetDataSource rsdt = new JRResultSetDataSource(rs);
                
                String tgl_nota = "";
                Map<String, Object> parameter = new HashMap<>();  
                parameter.put("namars",akses.getnamars());
                parameter.put("alamatrs",akses.getalamatrs());
                parameter.put("kotars",akses.getkabupatenrs());
                parameter.put("propinsirs",akses.getpropinsirs());
                parameter.put("kontakrs",akses.getkontakrs());
                parameter.put("emailrs",akses.getemailrs());   
                parameter.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                if(cek_nota_jalan > 0){
                    tgl_nota = Sequel.cariIsi("SELECT CONCAT(nota_jalan.tanggal, ' ', nota_jalan.jam) FROM nota_jalan WHERE nota_jalan.no_rawat = '"+txtNorawat.getText()+"' ");
                }else if(cek_nota_inap > 0){
                    tgl_nota = Sequel.cariIsi("SELECT CONCAT(nota_inap.tanggal, ' ', nota_inap.jam) FROM nota_inap WHERE nota_inap.no_rawat = '"+txtNorawat.getText()+"' ");
                }
                parameter.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh Dwi Retnowati,SE\n"+tgl_nota);  
                parameter.put("tgl_bayar", tgl_nota);
                parameter.put("petugas", "Petugas Kasir");
                if(piutang<=0){
                    parameter.put("ket_1", "TOTAL TAGIHAN");
                    parameter.put("ket_2", "PPN");
                    parameter.put("ket_3", "TOTAL BAYAR");
                    parameter.put("ket_4", "");
                    parameter.put("ket_5", "");
                    parameter.put("ket_6", "");
                    parameter.put("bayar_1", Valid.SetAngka(totalbiaya)+"");
                    parameter.put("bayar_2", Valid.SetAngka(besarppn)+"");
                    parameter.put("bayar_3", Valid.SetAngka(besarbayar)+"");
                    parameter.put("bayar_4", "");
                    parameter.put("bayar_5", "");
                    parameter.put("bayar_6", "");
                }else if(piutang>0){
                    parameter.put("ket_1", "TOTAL TAGIHAN");
                    parameter.put("ket_2", "PPN");
                    parameter.put("ket_3", "TAGIHAN + PPN");
                    parameter.put("ket_4", "EKSES");
                    parameter.put("ket_5", "PIUTANG");
                    parameter.put("ket_6", "");
                    parameter.put("bayar_1", Valid.SetAngka(totalbiaya)+"");
                    parameter.put("bayar_2", Valid.SetAngka(besarppn)+"");
                    parameter.put("bayar_3", Valid.SetAngka(tagihanppn)+"");
                    parameter.put("bayar_4", Valid.SetAngka(ekses)+"");
                    parameter.put("bayar_5", Valid.SetAngka(piutang)+"");
                    parameter.put("bayar_6", "");
                }
                Properties systemProp = System.getProperties();

                // Ambil current dir
                String currentDir = systemProp.getProperty("user.dir");

                File dir = new File(currentDir);

                File fileRpt;
                String fullPath = "";
                if (dir.isDirectory()) {
                    String[] isiDir = dir.list();
                    for (String iDir : isiDir) {
                        String path = currentDir + File.separatorChar + iDir + File.separatorChar + "rptNotaRalan.jasper";
                        System.out.println("fullPath: "+path);
                        fileRpt = new File(path);
                        if (fileRpt.isFile()) { // Cek apakah file rptNotaRalan.jrxml ada
                            fullPath = fileRpt.toString();
                            System.out.println("Found Report File at : " + fullPath);
                        } // end if
                    } // end for i
                } // end if

                JasperPrint jprint;
                jprint = JasperFillManager.fillReport(fullPath, parameter, rsdt);
                JRViewer v = new JRViewer(jprint);
                jPanel2.setLayout(new BorderLayout());
                jPanel2.add(v);

                // String nama_file_export = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
                String nama_file_export = export_path+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
                Path path = Paths.get(nama_file_export);

                // Export to PDF
                if (Files.notExists(path)) {
                    // file is not exist
                    System.out.println("file is not exist: ");
                    JasperExportManager.exportReportToPdfFile(jprint, nama_file_export);
                }else{
                    System.out.println("file is exist: ");
                }
                btnNota.setBackground(Color.GREEN);

            } catch (SQLException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Nota belum ada");
        }
    }//GEN-LAST:event_btnNotaActionPerformed

    private void txtLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLabActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        int cek_lab = Sequel.cariInteger("select \n" +
                                        "  count(periksa_lab.kd_jenis_prw) AS jml_lab \n" +
                                        "from \n" +
                                        "  periksa_lab \n"+ 
                                        "inner join reg_periksa on periksa_lab.no_rawat = reg_periksa.no_rawat \n" +
                                        "where \n" +
                                        "  periksa_lab.kategori = 'PK' \n" +
                                        "  and periksa_lab.no_rawat like '"+txtNorawat.getText()+"' \n" +
                                        "  and reg_periksa.no_rkm_medis like '"+txtNoRm.getText()+"' \n");
        System.out.println("Jumlah lab: "+cek_lab);
        if(cek_lab > 0){
            String query_count = "select \n" +
                            "  count(periksa_lab.kd_jenis_prw) AS jml_lab \n" +
                            "from \n" +
                            "  periksa_lab \n"+ 
                            "inner join reg_periksa on periksa_lab.no_rawat = reg_periksa.no_rawat \n" +
                            "where \n" +
                            "  periksa_lab.kategori = 'PK' \n" +
                            "  and periksa_lab.no_rawat like '"+txtNorawat.getText()+"' \n" +
                            "  and reg_periksa.no_rkm_medis like '"+txtNoRm.getText()+"' \n";
        
            System.out.println("cek_lab: "+query_count);
        
            String tgl_periksa = Sequel.cariIsi("SELECT tgl_periksa FROM periksa_lab WHERE no_rawat = '"+txtNorawat.getText()+"'");
            String jam = Sequel.cariIsi("SELECT jam FROM periksa_lab WHERE no_rawat = '"+txtNorawat.getText()+"'");

            String query_lab = "SELECT DISTINCT "
                                    + "dt.no_rawat, "
                                    + "jp.nm_perawatan AS perawatan, "
                                    + "tp.pemeriksaan, "
                                    + "dt.nilai AS hasil, "
                                    + "tp.satuan, "
                                    + "dt.nilai_rujukan, "
                                    + "dt.keterangan, "
                                    + "pl.dokter_perujuk,"
                                    + "rp.no_rkm_medis, "
                                    + "p.nm_pasien, "
                                    + "p.jk, "
                                    + "p.umur, "
                                    + "pl.tgl_periksa, "
                                    + "pl.jam, "
                                    + "p.alamat, "
                                    + "pl.nip, "
                                    + "ptg.nama, "
                                    + "pl.kd_dokter, "
                                    + "dr.nm_dokter "
                                + "FROM "
                                    + "periksa_lab pl " +
                                    "  LEFT JOIN detail_periksa_lab dt ON dt.no_rawat = pl.no_rawat " +
                                    "  LEFT JOIN reg_periksa rp ON dt.no_rawat = rp.no_rawat " +
                                    "  LEFT JOIN pasien p ON rp.no_rkm_medis = p.no_rkm_medis " +
                                    "  LEFT JOIN jns_perawatan_lab jp ON dt.kd_jenis_prw = jp.kd_jenis_prw " +
                                    "  LEFT JOIN template_laboratorium tp ON dt.id_template = tp.id_template " +
                                    "  LEFT JOIN petugas ptg ON pl.nip = ptg.nip " +
                                    "  LEFT JOIN dokter dr ON pl.kd_dokter = dr.kd_dokter "
                                + "WHERE "
                                   + "dt.no_rawat = '"+txtNorawat.getText()+"' AND "
                                   + "dt.tgl_periksa = '"+tgl_periksa+"' AND "
                                   + "dt.jam = '"+jam+"' ";

            System.out.println("query_lab: "+query_lab);
            
            try {
                ps=koneksi.prepareStatement(query_lab);
                rs=ps.executeQuery();
                if(rs.next()){
                    String kamar = "";
                    String namakamar = "";
                    String query_get_kamar = "select ifnull(kamar_inap.kd_kamar,'') AS ifnull from kamar_inap where kamar_inap.no_rawat='"+rs.getString("no_rawat")+"' order by kamar_inap.tgl_masuk desc limit 1";
                    System.out.println("query_get_kamar: "+query_get_kamar);
                    kamar=Sequel.cariIsi(query_get_kamar);
                    if(!kamar.isEmpty()){
                        namakamar=kamar+", "+Sequel.cariIsi("select bangsal.nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                " where kamar.kd_kamar='"+kamar+"' ");            
                        kamar="Kamar";
                    }else if(kamar.isEmpty()){
                        kamar="Poli";
                        namakamar=Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                    }
                    
                    System.out.println("kamar: "+kamar);
                    System.out.println("nama kamar: "+namakamar);

                    String finger = "";

                    Map<String, Object> parameter = new HashMap<>();  
                    parameter.put("namars",akses.getnamars());
                    parameter.put("alamatrs",akses.getalamatrs());
                    parameter.put("kotars",akses.getkabupatenrs());
                    parameter.put("propinsirs",akses.getpropinsirs());
                    parameter.put("kontakrs",akses.getkontakrs());
                    parameter.put("emailrs",akses.getemailrs());   
                    parameter.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    String tgl_nota = Sequel.cariIsi("SELECT CONCAT(nota_jalan.tanggal, ' ', nota_jalan.jam) FROM nota_jalan WHERE nota_jalan.no_rawat = '"+txtNorawat.getText()+"' ");
                    parameter.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh Petugas Kasir\n"+tgl_nota);  
                    parameter.put("tgl_bayar", tgl_nota);
                    parameter.put("petugas", "Admin Utama");
                    parameter.put("noperiksa",rs.getString("no_rawat"));
                    parameter.put("norm",rs.getString("no_rkm_medis"));
                    parameter.put("namapasien",rs.getString("nm_pasien"));
                    parameter.put("jkel",rs.getString("jk"));
                    parameter.put("umur",rs.getString("umur"));
                    parameter.put("pengirim",Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs.getString("dokter_perujuk")));
                    parameter.put("tanggal",rs.getString("tgl_periksa"));
                    parameter.put("penjab",rs.getString("nm_dokter"));
                    parameter.put("petugas",rs.getString("nama"));
                    parameter.put("jam",rs.getString("jam"));
                    parameter.put("alamat",rs.getString("alamat"));
                    parameter.put("kamar",kamar);
                    parameter.put("namakamar",namakamar);
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                    parameter.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+rs.getString("tgl_periksa"));  
                    finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip"));
                    parameter.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nip"):finger)+"\n"+rs.getString("tgl_periksa"));  

                    PreparedStatement pspermintaan=koneksi.prepareStatement(
                            "select noorder,DATE_FORMAT(tgl_permintaan,'%d-%m-%Y') as tgl_permintaan,jam_permintaan from permintaan_lab where "+
                            "no_rawat=? and tgl_hasil=? and jam_hasil=?");
                    pspermintaan.setString(1,rs.getString("no_rawat"));
                    pspermintaan.setString(2,rs.getString("tgl_periksa"));
                    pspermintaan.setString(3,rs.getString("jam"));
                    ResultSet rspermintaan=pspermintaan.executeQuery();
                    
                    String jasper_file = "";
                    
                    if(rspermintaan.next()){
                        parameter.put("nopermintaan",rspermintaan.getString("noorder"));   
                        parameter.put("tanggalpermintaan",rspermintaan.getString("tgl_permintaan"));  
                        parameter.put("jampermintaan",rspermintaan.getString("jam_permintaan"));
                        
                        jasper_file = "rptPeriksaLabPermintaanPenjaminan.jasper";
                    }else{
                        jasper_file = "rptPeriksaLabPenjaminan.jasper";
                    }
                    
                    Properties systemProp = System.getProperties();

                    // Ambil current dir
                    String currentDir = systemProp.getProperty("user.dir");

                    File dir = new File(currentDir);

                    File fileRpt;
                    String fullPath = "";
                    if (dir.isDirectory()) {
                        String[] isiDir = dir.list();
                        for (String iDir : isiDir) {
                            String path = currentDir + File.separatorChar + iDir + File.separatorChar + jasper_file;
                            System.out.println("fullPath: "+path);
                            fileRpt = new File(path);
                            if (fileRpt.isFile()) { // Cek apakah file rptNotaRalan.jrxml ada
                                fullPath = fileRpt.toString();
                                System.out.println("Found Report File at : " + fullPath);
                            } // end if
                        } // end for i
                    } // end if
                    
                    PreparedStatement ps_rsdt = koneksi.prepareStatement(query_lab);
                    ResultSet rs_rsdt = ps_rsdt.executeQuery();
                    
                    JRResultSetDataSource rsdt = new JRResultSetDataSource(rs_rsdt);
                    
                    JasperPrint jprint;
                    jprint = JasperFillManager.fillReport(fullPath, parameter, rsdt);
                    JRViewer v = new JRViewer(jprint);
                    jPanel2.setLayout(new BorderLayout());
                    jPanel2.add(v);
                    
                    // Export to PDF
                    // String nama_file_export = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
                    String nama_file_export = export_path+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
                    Path path = Paths.get(nama_file_export);

                    // Export to PDF
                    if (Files.notExists(path)) {
                        // file is not exist
                        System.out.println("file is not exist: ");
                        JasperExportManager.exportReportToPdfFile(jprint, nama_file_export);
                    }else{
                        System.out.println("file is exist: ");
                    }
                }
                txtLab.setBackground(Color.GREEN);
            } catch (SQLException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Tidak ada data Lab");
        }
    }//GEN-LAST:event_txtLabActionPerformed

    private void BtnPrintSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintSemuaActionPerformed
        // Ambil current dir
        Properties systemProp = System.getProperties();
        String currentDir = systemProp.getProperty("user.dir");
        String[] filesToMerge = {}; // Ubah dengan daftar file PDF yang ingin Anda gabungkan
        
        // cek Scan INACBG
        // String nama_file_export_ina = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_inacbg = export_path+"merged_inacbg_"+temp_sep.getText()+".pdf";
        Path path_inacbg = Paths.get(nama_file_inacbg);
        if (!Files.notExists(path_inacbg)) {
            filesToMerge = append(filesToMerge, nama_file_inacbg);
        }
        
        // cek lembar bukti pelayanan
        // String nama_file_export_ina = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_export_ina = export_path+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
        Path path_ina = Paths.get(nama_file_export_ina);
        if (!Files.notExists(path_ina)) {
            filesToMerge = append(filesToMerge, nama_file_export_ina);
        }
        
        // cek nota
        // String nama_file_export_nota = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_export_nota = export_path+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
        Path path_nota = Paths.get(nama_file_export_nota);
        if (!Files.notExists(path_nota)) {
            filesToMerge = append(filesToMerge, nama_file_export_nota);
        }
        
        // cek surat kontrol
        // String nama_file_export_suratkontrol = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_export_suratkontrol = export_path+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
        Path path_surat_kontrol = Paths.get(nama_file_export_suratkontrol);
        if (!Files.notExists(path_surat_kontrol)) {
            filesToMerge = append(filesToMerge, nama_file_export_suratkontrol);
        }
        
        // cek lab
        // String nama_file_export_lab = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_export_lab = export_path+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
        Path path_lab = Paths.get(nama_file_export_lab);
        if (!Files.notExists(path_lab)) {
            filesToMerge = append(filesToMerge, nama_file_export_lab);
        }
        
        // cek radiologi
        // String nama_file_export_radiologi = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_export_radiologi = export_path+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
        Path path_radiologi = Paths.get(nama_file_export_radiologi);
        if (!Files.notExists(path_radiologi)) {
            filesToMerge = append(filesToMerge, nama_file_export_radiologi);
        }
        
        // cek pdf tambahan
        String nama_file_pdf_tambahan = export_path+"merged_pdftambahan_"+temp_sep.getText()+".pdf";
        Path path_pdf_tambahan = Paths.get(nama_file_pdf_tambahan);
        if (!Files.notExists(path_pdf_tambahan)) {
            filesToMerge = append(filesToMerge, nama_file_pdf_tambahan);
        }
        
        // cek Scan Tambahan
        // String nama_file_export_ina = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
        String nama_file_export_scan = export_path+"tambahan_scan_"+temp_sep.getText()+".pdf";
        Path path_scan_tambahan = Paths.get(nama_file_export_scan);
        if (!Files.notExists(path_scan_tambahan)) {
            filesToMerge = append(filesToMerge, nama_file_export_scan);
        }

        // String mergedFilePath = merged_path+"merged_file_"+txtNorawat.getText().replace("/", "-")+"_"+txtSEP.getText()+"_"+txtNoRm.getText()+".pdf";
        String mergedFilePath = merged_path+temp_sep.getText()+".pdf";
        
        try {
            mergePDFs(filesToMerge, mergedFilePath);
            System.out.println("PDF files merged successfully!");
            JOptionPane.showMessageDialog(null, "PDF berhasil di combine pada: "+mergedFilePath);
            txtMergedPdf.setEnabled(true);
            txtMergedPdf.setText("Preview Combined PDF: Ada");
            BtnDeleteMergedPdf.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnPrintSemuaActionPerformed

    private void txtInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInaActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        String no_sep = "";
        String finger = "";
        String query_data_sep = "select \n" +
                                    "  bridging_sep.no_sep, \n" +
                                    "  bridging_sep.no_rawat, \n" +
                                    "  bridging_sep.nomr, \n" +
                                    "  bridging_sep.nama_pasien, \n" +
                                    "  bridging_sep.tglsep, \n" +
                                    "  bridging_sep.tglrujukan, \n" +
                                    "  bridging_sep.no_rujukan, \n" +
                                    "  bridging_sep.kdppkrujukan, \n" +
                                    "  bridging_sep.nmppkrujukan, \n" +
                                    "  bridging_sep.kdppkpelayanan, \n" +
                                    "  bridging_sep.nmppkpelayanan, \n" +
                                    "  if(\n" +
                                    "    bridging_sep.jnspelayanan = '1', '1. Ranap', \n" +
                                    "    '2. Ralan'\n" +
                                    "  ) as jnspelayanan, \n" +
                                    "  bridging_sep.catatan, \n" +
                                    "  bridging_sep.diagawal, \n" +
                                    "  bridging_sep.nmdiagnosaawal, \n" +
                                    "  bridging_sep.kdpolitujuan, \n" +
                                    "  bridging_sep.nmpolitujuan, \n" +
                                    "  if(\n" +
                                    "    bridging_sep.klsrawat = '1', \n" +
                                    "    '1. Kelas 1', \n" +
                                    "    if(\n" +
                                    "      bridging_sep.klsrawat = '2', '2. Kelas 2', \n" +
                                    "      '3. Kelas 3'\n" +
                                    "    )\n" +
                                    "  ) as klsrawat, \n" +
                                    "  bridging_sep.klsnaik, \n" +
                                    "  bridging_sep.pembiayaan, \n" +
                                    "  bridging_sep.pjnaikkelas, \n" +
                                    "  bridging_sep.lakalantas, \n" +
                                    "  bridging_sep.user, \n" +
                                    "  bridging_sep.tanggal_lahir, \n" +
                                    "  bridging_sep.peserta, \n" +
                                    "  bridging_sep.jkel, \n" +
                                    "  bridging_sep.no_kartu, \n" +
                                    "  bridging_sep.tglpulang, \n" +
                                    "  bridging_sep.asal_rujukan, \n" +
                                    "  bridging_sep.eksekutif, \n" +
                                    "  bridging_sep.cob, \n" +
                                    "  bridging_sep.notelep, \n" +
                                    "  bridging_sep.katarak, \n" +
                                    "  bridging_sep.tglkkl, \n" +
                                    "  bridging_sep.keterangankkl, \n" +
                                    "  bridging_sep.suplesi, \n" +
                                    "  bridging_sep.no_sep_suplesi, \n" +
                                    "  bridging_sep.kdprop, \n" +
                                    "  bridging_sep.nmprop, \n" +
                                    "  bridging_sep.kdkab, \n" +
                                    "  bridging_sep.nmkab, \n" +
                                    "  bridging_sep.kdkec, \n" +
                                    "  bridging_sep.nmkec, \n" +
                                    "  bridging_sep.noskdp, \n" +
                                    "  bridging_sep.kddpjp, \n" +
                                    "  bridging_sep.nmdpdjp, \n" +
                                    "  bridging_sep.tujuankunjungan, \n" +
                                    "  bridging_sep.flagprosedur, \n" +
                                    "  bridging_sep.penunjang, \n" +
                                    "  bridging_sep.asesmenpelayanan, \n" +
                                    "  bridging_sep.kddpjplayanan, \n" +
                                    "  bridging_sep.nmdpjplayanan \n" +
                                    "from \n" +
                                    "  bridging_sep \n" +
                                    "where \n" +
                                    // "  bridging_sep.tglsep between ? and ? and\n" +
                                    "  bridging_sep.no_rawat = ? \n" +
                                    "order by \n" +
                                    "  bridging_sep.tglsep";
        
        PreparedStatement pssep;
        try {
            pssep = koneksi.prepareStatement(query_data_sep);
            pssep.setString(1, txtNorawat.getText());
            
            ResultSet rssep = pssep.executeQuery();
            rssep.last();
            int size = rssep.getRow();
            rssep.beforeFirst();
            
            System.out.println("Jumlah data SEP: "+size);
            if(rssep.next()){
                System.out.println("Data SEP: "+rssep.getString(1));
                temp_sep.setText(rssep.getString(1));
                
                no_sep = rssep.getString(1);
                String query_diagnosa = "SELECT \n" +
                                        "  d.kd_penyakit, \n" +
                                        "  p.nm_penyakit \n" +
                                        "FROM \n" +
                                        "  diagnosa_pasien d \n" +
                                        "  INNER JOIN penyakit p ON d.kd_penyakit = p.kd_penyakit \n" +
                                        "WHERE \n" +
                                        "  d.no_rawat = ? \n"+
                                        "ORDER BY d.prioritas; ";
                PreparedStatement ps_diagnosa = koneksi.prepareStatement(query_diagnosa);
                ps_diagnosa.setString(1, txtNorawat.getText());
                System.out.println("Query diagnosa: "+ps_diagnosa);
                
                ResultSet rs_diagnosa = ps_diagnosa.executeQuery();
                
                int nomor = 1;
                String diagnosa = "";
                while(rs_diagnosa.next()){
                    diagnosa = diagnosa+nomor+". "+rs_diagnosa.getString("nm_penyakit")+" ("+rs_diagnosa.getString("kd_penyakit")+") \n";
                    nomor++;
                }
                System.out.println("Diagnosa: "+diagnosa);
                
                String query_prosedur = "SELECT \n" +
                                        "  p.kode,\n" +
                                        "  i.deskripsi_panjang\n" +
                                        "FROM \n" +
                                        "  `prosedur_pasien` p\n" +
                                        "INNER JOIN icd9 i ON p.kode = i.kode\n" +
                                        "WHERE \n" +
                                        "  p.`no_rawat` = ?;";
                PreparedStatement ps_prosedur = koneksi.prepareStatement(query_prosedur);
                ps_prosedur.setString(1, txtNorawat.getText());
                System.out.println("Query prosedur: "+ps_prosedur);
                
                ResultSet rs_prosedur = ps_prosedur.executeQuery();
                
                nomor = 1;
                String prosedur = "";
                while(rs_prosedur.next()){
                    prosedur = prosedur+nomor+". "+rs_prosedur.getString("deskripsi_panjang")+" ("+rs_prosedur.getString("kode")+") \n";
                    nomor++;
                }
                System.out.println("Prosedur: "+prosedur);
                
                Map<String, Object> param = new HashMap<>();  
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("emailrs",akses.getemailrs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("penanggung",Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",txtNorawat.getText())));               
                param.put("propinsirs",akses.getpropinsirs());
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",Sequel.cariIsi("SELECT kd_dokter FROM maping_dokter_dpjpvclaim WHERE kd_dokter_bpjs = '"+rssep.getString("kddpjp")+"'"));
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rssep.getString("nmdpdjp")+"\nID "+(finger.equals("")?Sequel.cariIsi("SELECT kd_dokter FROM maping_dokter_dpjpvclaim WHERE kd_dokter_bpjs = '"+rssep.getString("kddpjp")+"'"):finger)+"\n");
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                param.put("logo_bpjs",Sequel.cariGambar("select gambar.bpjs from gambar")); 
                param.put("no_sep", no_sep);
                param.put("no_kartu",Sequel.cariIsi("select bridging_sep.no_kartu from bridging_sep where bridging_sep.no_rawat=?",txtNorawat.getText()));
                param.put("prb",Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?",no_sep));
                param.put("noreg",Sequel.cariIsi("SELECT no_reg FROM reg_periksa WHERE no_rawat=?",txtNorawat.getText()));
                param.put("no_sep_bridging", rssep.getString("no_sep"));
                param.put("no_rawat", rssep.getString("no_rawat"));
                param.put("nomr", rssep.getString("nomr"));
                param.put("nama_pasien", rssep.getString("nama_pasien"));
                param.put("tglsep", rssep.getString("tglsep"));
                param.put("tglrujukan", rssep.getString("tglrujukan"));
                param.put("no_rujukan", rssep.getString("no_rujukan"));
                param.put("kdppkrujukan", rssep.getString("kdppkrujukan"));
                param.put("nmppkrujukan", rssep.getString("nmppkrujukan"));
                param.put("kdppkpelayanan", rssep.getString("kdppkpelayanan"));
                param.put("nmppkpelayanan", rssep.getString("nmppkpelayanan"));
                if (rssep.getString("jnspelayanan") == "1") {
                    param.put("jnspelayanan", "Rawat Inap");
                } else{
                    param.put("jnspelayanan", "Rawat Jalan");
                }
                param.put("catatan", rssep.getString("catatan"));
                param.put("diagawal", rssep.getString("diagawal"));
                param.put("nmdiagnosaawal", rssep.getString("nmdiagnosaawal"));
                param.put("kdpolitujuan", rssep.getString("kdpolitujuan"));
                param.put("nmpolitujuan", rssep.getString("nmpolitujuan"));

                switch (rssep.getString("klsrawat")) {
                    case "1":
                        param.put("klsrawat", "Kelas 1");
                        break;
                    case "2":
                        param.put("klsrawat", "Kelas 2");
                        break;
                    case "3":
                        param.put("klsrawat", "Kelas 3");
                        break;
                    default:
                        // throw new AssertionError();
                        param.put("klsrawat", "-");
                }

                switch (rssep.getString("klsnaik")) {
                    case "1":
                        param.put("klsnaik", "VVIP");
                        break;
                    case "2":
                        param.put("klsnaik", "VIP");
                        break;
                    case "3":
                        param.put("klsnaik", "Kelas I");
                        break;
                    case "4":
                        param.put("klsnaik", "Kelas II");
                        break;
                    case "5":
                        param.put("klsnaik", "Kelas III");
                        break;
                    case "6":
                        param.put("klsnaik", "ICCU");
                        break;
                    case "7":
                        param.put("klsnaik", "ICU");
                        break;
                    case "8":
                        param.put("klsnaik", "Diatas Kelas 1");
                        break;
                    default:
                        // throw new AssertionError();
                        param.put("klsnaik", "-");
                }

                param.put("pembiayaan", rssep.getString("pembiayaan"));
                param.put("pjnaikkelas", rssep.getString("pjnaikkelas"));

                switch (rssep.getString("lakalantas")) {
                    case "0":
                        param.put("lakalantas", "BPJS Kesehatan");
                        break;
                    case "1":
                        param.put("lakalantas", "Jasa Raharja");
                        break;
                    case "2":
                        param.put("lakalantas", "Jasa Raharja & BPJS Ketenagakerjaan/Taspen");
                        break;
                    case "3":
                        param.put("lakalantas", "BPJS Ketenagakerjaan, Taspen, dll");
                        break;
                    default:
                        // throw new AssertionError();
                        param.put("lakalantas", "-");
                }

                param.put("user", rssep.getString("user"));
                param.put("tanggal_lahir", rssep.getString("tanggal_lahir"));
                param.put("peserta", rssep.getString("peserta"));
                param.put("jkel", rssep.getString("jkel"));
                param.put("no_kartu_bridging", rssep.getString("no_kartu"));
                param.put("tglpulang", rssep.getString("tglpulang"));
                param.put("asal_rujukan", rssep.getString("asal_rujukan"));
                param.put("eksekutif", rssep.getString("eksekutif"));
                param.put("cob", rssep.getString("cob"));
                param.put("notelep", rssep.getString("notelep"));
                param.put("katarak", rssep.getString("katarak"));
                param.put("tglkkl", rssep.getString("tglkkl"));
                param.put("keterangankkl", rssep.getString("keterangankkl"));
                param.put("suplesi", rssep.getString("suplesi"));
                param.put("no_sep_suplesi", rssep.getString("no_sep_suplesi"));
                param.put("kdprop", rssep.getString("kdprop"));
                param.put("nmprop", rssep.getString("nmprop"));
                param.put("kdkab", rssep.getString("kdkab"));
                param.put("nmkab", rssep.getString("nmkab"));
                param.put("kdkec", rssep.getString("kdkec"));
                param.put("nmkec", rssep.getString("nmkec"));
                param.put("noskdp", rssep.getString("noskdp"));
                param.put("kddpjp", rssep.getString("kddpjp"));
                param.put("nmdpdjp", rssep.getString("nmdpdjp"));

                if (rssep.getString("tujuankunjungan") == "0") {
                    param.put("tujuankunjungan", "Konsultasi dokter(pertama)");
                } else {
                    param.put("tujuankunjungan", "Kunjungan Kontrol(ulangan)");
                }

                switch (rssep.getString("flagprosedur")) {
                    case "0":
                        param.put("flagprosedur", "- Prosedur Tidak Berkelanjutan");
                        break;
                    case "1":
                        param.put("flagprosedur", "- Prosedur dan Terapi Berkelanjutan");
                        break;
                    default:
                        // throw new AssertionError();
                        param.put("flagprosedur", "-");
                }

                param.put("penunjang", rssep.getString("penunjang"));
                param.put("asesmenpelayanan", rssep.getString("asesmenpelayanan"));
                param.put("kddpjplayanan", rssep.getString("kddpjplayanan"));
                param.put("nmdpjplayanan", rssep.getString("nmdpjplayanan"));
                
                param.put("diagnosa", diagnosa);
                param.put("prosedur", prosedur);
                
                
                Properties systemProp = System.getProperties();

                // Ambil current dir
                String currentDir = systemProp.getProperty("user.dir");
                System.out.println("currentDir: "+currentDir);

                File dir = new File(currentDir);

                File fileRpt;
                String fullPath = "";
                if (dir.isDirectory()) {
                    String[] isiDir = dir.list();
                    for (String iDir : isiDir) {
                        String path = currentDir + File.separatorChar + iDir + File.separatorChar + "rptLembarPelayanan.jasper";
                        System.out.println("fullPath: "+path);
                        fileRpt = new File(path);
                        if (fileRpt.isFile()) { // Cek apakah file rptNotaRalan.jrxml ada
                            fullPath = fileRpt.toString();
                            System.out.println("Found Report File at : " + fullPath);
                        } // end if
                    } // end for i
                } // end if

                PreparedStatement ps_rsdt = koneksi.prepareStatement("select current_date as tanggal;");
                ResultSet rs_rsdt = ps_rsdt.executeQuery();

                JRResultSetDataSource rsdt = new JRResultSetDataSource(rs_rsdt);

                JasperPrint jprint;
                jprint = JasperFillManager.fillReport(fullPath, param, rsdt);
                JRViewer v = new JRViewer(jprint);
                jPanel2.setLayout(new BorderLayout());
                jPanel2.add(v);

                // Export to PDF
                // String nama_file_export = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
                String nama_file_export = export_path+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
                Path path = Paths.get(nama_file_export);

                // Export to PDF
                if (Files.notExists(path)) {
                    // file is not exist
                    System.out.println("file is not exist: ");
                    JasperExportManager.exportReportToPdfFile(jprint, nama_file_export);
                }else{
                    System.out.println("file is exist: ");
                }
                txtIna.setBackground(Color.GREEN);
            }else{
                JOptionPane.showMessageDialog(null, "SEP Tidak ada data");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtInaActionPerformed

    private void BtnScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnScanActionPerformed
        try {
            // Gunakan jalur penuh ke executable naps2.console dan pilih profil
            // String filepath = currentDir+File.separatorChar+"export_pdf"+File.separatorChar+"tambahan_scan_"+txtNorawat.getText().replace("/", "-")+".pdf";
            filepath = export_path+"tambahan_scan_"+txtSep.getText()+".pdf";
            System.out.println("filepath: "+filepath);
            String command = "\""+naps2_path+":\\Program Files\\NAPS2\\naps2.console.exe\" scan -p \""+naps2_profile+"\" -o "+filepath+" -f";
            System.out.println("command: "+command);
            
            // Menjalankan perintah
            Process process = Runtime.getRuntime().exec(command);

            // Membaca output dari perintah
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Menunggu proses selesai
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
            if(exitCode == 0){
                statusScan.setText("Scan Sukses: "+filepath);
                txtScanTambahan.setEnabled(true);
                txtScanTambahan.setText("Scan Tambahan: Ada");
                BtnDeleteScan.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusScan.setText("Scan Gagal: " + e);
        }
    }//GEN-LAST:event_BtnScanActionPerformed

    private void BtnScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnScanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnScanKeyPressed

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

    private void BtnAttachmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAttachmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAttachmentActionPerformed

    private void BtnAttachmentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAttachmentKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAttachmentKeyPressed

    private void BtnAttachmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnAttachmentMouseClicked
        tambah_file("pdftambahan_", txtPdfTambahan, "PDF Tambahan: Ada", BtnDeletePdf);
    }//GEN-LAST:event_BtnAttachmentMouseClicked

    private void btnINAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnINAActionPerformed
        if(akses.getkode().equals("Admin Utama")){
            pilihpage="KlaimBaruManual";
            judulform="::[ Klaim Manual Pasien Baru Dari Data SEP Ke INACBG ]::";
            cariNIK.setSize(this.getWidth()-20,this.getHeight()-20);
            cariNIK.setLocationRelativeTo(this);
            cariNIK.setVisible(true);
            btnINA.setBackground(Color.GREEN);
        }else{
            coder_nik=Sequel.cariIsi("select inacbg_coder_nik.no_ik from inacbg_coder_nik where inacbg_coder_nik.nik=?",akses.getkode());
            if(!coder_nik.equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    inacbgklaim.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"inacbg/login.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&page=KlaimBaruManual&codernik="+coder_nik);                    
                } catch (Exception ex) {
                    System.out.println("Notifikasi : "+ex);
                }

                inacbgklaim.setSize(this.getWidth()-20,this.getHeight()-20);
                inacbgklaim.setLocationRelativeTo(this);
                inacbgklaim.setJudul("::[ Klaim Manual Pasien Baru Dari Data SEP Ke INACBG ]::");
                inacbgklaim.setVisible(true);
                btnINA.setBackground(Color.GREEN);
                this.setCursor(Cursor.getDefaultCursor());
            }else{
                JOptionPane.showMessageDialog(null,"Coder NIK tidak ditemukan, silahkan hubungi Admin Utama..!!");
            }            
        }
    }//GEN-LAST:event_btnINAActionPerformed

    private void BtnEditBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnEditBillingMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEditBillingMouseClicked

    private void BtnEditBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditBillingActionPerformed
        if(Sequel.cariInteger("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.no_rawat=?",txtNorawat.getText())>0){
            try {
                String kd_kamar = Sequel.cariIsi("SELECT kd_kamar FROM kamar_inap WHERE no_rawat = ?", txtNorawat.getText());
                pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                try {
                    pscaripiutang.setString(1,txtNoRm.getText());
                    rs=pscaripiutang.executeQuery();
                    if(rs.next()){
                        i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if(i==JOptionPane.YES_OPTION){
                             DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                             piutang.setNoRm(txtNoRm.getText(),rs.getDate(1));
                             piutang.tampil();
                             piutang.isCek();
                             piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                             piutang.setLocationRelativeTo(internalFrame1);
                             piutang.setVisible(true);
                        }else{
                            bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",Sequel.cariIsi("select kamar.kd_bangsal from kamar where kamar.kd_kamar=?",kd_kamar));
                            if(bangsal.equals("")){
                                if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                                    akses.setkdbangsal(Sequel.cariIsi("select kamar.kd_bangsal from kamar where kamar.kd_kamar=?",kd_kamar));
                                }else{
                                    akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
                                }
                            }else{
                                akses.setkdbangsal(bangsal);
                            }

                            billing.TNoRw.setText(txtNorawat.getText());                   
                            billing.isCek();  
                            billing.isRawat();          
                            billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            billing.setLocationRelativeTo(internalFrame1);
                            billing.setVisible(true);
                        }
                    }else{
                        bangsal=Sequel.cariIsi("select set_depo_ranap.kd_depo from set_depo_ranap where set_depo_ranap.kd_bangsal=?",Sequel.cariIsi("select kamar.kd_bangsal from kamar where kamar.kd_kamar=?",kd_kamar));
                        if(bangsal.equals("")){
                            if(Sequel.cariIsi("select set_lokasi.asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")){
                                akses.setkdbangsal(Sequel.cariIsi("select kamar.kd_bangsal from kamar where kamar.kd_kamar=?",kd_kamar));
                            }else{
                                akses.setkdbangsal(Sequel.cariIsi("select set_lokasi.kd_bangsal from set_lokasi"));
                            }
                        }else{
                            akses.setkdbangsal(bangsal);
                        }

                        billing.TNoRw.setText(txtNorawat.getText());  
                        billing.isCek();
                        billing.isRawat(); 
                        billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        billing.setLocationRelativeTo(internalFrame1);
                        billing.setVisible(true);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rs != null){
                        rs.close();
                    }
                    if(pscaripiutang != null){
                        pscaripiutang.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else {
            try {
                sudah=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",txtNorawat.getText());
                pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                try{                                                
                    pscaripiutang.setString(1,txtNoRm.getText());
                    rskasir=pscaripiutang.executeQuery();
                    if(rskasir.next()){
                        i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if(i==JOptionPane.YES_OPTION){
                             DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                             piutang.setNoRm(txtNoRm.getText(),rskasir.getDate(1));
                             piutang.tampil();
                             piutang.isCek();
                             piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                             piutang.setLocationRelativeTo(internalFrame1);
                             piutang.setVisible(true);
                        }else{
                            if(akses.getbilling_ralan()==true){
                                otomatisRalan();
                            }

                            akses.setform("DlgKasirRalan");
                            billingralan.TNoRw.setText(txtNorawat.getText());  
                            billingralan.isCek();
                            billingralan.isRawat(); 
                            if(sudah>0){
                                billingralan.setPiutang();
                            }
                            billingralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            billingralan.setLocationRelativeTo(internalFrame1);
                            billingralan.setVisible(true);
                        }
                    }else{
                        if(akses.getbilling_ralan()==true){
                            otomatisRalan();
                        }
                        akses.setform("DlgKasirRalan");
                        billingralan.TNoRw.setText(txtNorawat.getText());  
                        billingralan.isCek();
                        billingralan.isRawat(); 
                        billingralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                        billingralan.setLocationRelativeTo(internalFrame1);
                        billingralan.setVisible(true);
                    }
                }catch(Exception ex){
                    System.out.println("Notifikasi : "+ex);
                } finally{
                    if(rskasir!=null){
                        rskasir.close();
                    }
                    if(pscaripiutang!=null){
                        pscaripiutang.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }    
    }//GEN-LAST:event_BtnEditBillingActionPerformed

    private void BtnEditBillingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditBillingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEditBillingKeyPressed

    private void txtSepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSepKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        String sepText = txtSep.getText();
        StringSelection stringSelectionObj = new StringSelection(sepText);
        Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboardObj.setContents(stringSelectionObj, null);
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void txtScanInacbgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtScanInacbgActionPerformed
        jPanel2.removeAll();
        jPanel2.repaint();
        jPanel2.revalidate();
        
        // Tentukan jalur file PDF
        fileinacbgpath = export_path+"merged_inacbg_"+txtSep.getText()+".pdf";

        // Buat instance PDFPanel
        PDFPanel pdfPanel = new PDFPanel();

        // Muat PDF ke dalam PDFPanel
        pdfPanel.loadPDF(fileinacbgpath);

        // Hapus semua komponen dari jPanel2
        jPanel2.removeAll();

        // Tambahkan PDFPanel ke jPanel2
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(new JScrollPane(pdfPanel), BorderLayout.CENTER);
        txtScanInacbg.setBackground(Color.GREEN);
    }//GEN-LAST:event_txtScanInacbgActionPerformed

    private void BtnDeletePdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeletePdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeletePdfMouseClicked

    private void BtnDeletePdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeletePdfActionPerformed
        hapus_data("pdftambahan_", "PDF", txtPdfTambahan, "PDF Tambahan: Tidak Ada", "export");
    }//GEN-LAST:event_BtnDeletePdfActionPerformed

    private void BtnDeletePdfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeletePdfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeletePdfKeyPressed

    private void BtnAttachmentINACBGMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnAttachmentINACBGMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAttachmentINACBGMouseClicked

    private void BtnAttachmentINACBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAttachmentINACBGActionPerformed
        tambah_file("inacbg_", txtScanInacbg, "Berkas INACBG: Ada", BtnDeleteInacbg);
    }//GEN-LAST:event_BtnAttachmentINACBGActionPerformed

    private void BtnAttachmentINACBGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAttachmentINACBGKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAttachmentINACBGKeyPressed

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

    private void BtnDeleteScanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeleteScanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteScanMouseClicked

    private void BtnDeleteScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteScanActionPerformed
        hapus_data("tambahan_scan_", "Tambahan Scan", txtScanTambahan, "Scan Tambahan: Tidak Ada", "export");
    }//GEN-LAST:event_BtnDeleteScanActionPerformed

    private void BtnDeleteScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeleteScanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteScanKeyPressed

    private void BtnDeleteInacbgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeleteInacbgMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteInacbgMouseClicked

    private void BtnDeleteInacbgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteInacbgActionPerformed
        hapus_data("inacbg_", "INACBG", txtScanInacbg, "Berkas INACBG: Tidak Ada", "export");
    }//GEN-LAST:event_BtnDeleteInacbgActionPerformed

    private void BtnDeleteInacbgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeleteInacbgKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteInacbgKeyPressed

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

    private void BtnDeleteMergedPdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeleteMergedPdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteMergedPdfMouseClicked

    private void BtnDeleteMergedPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteMergedPdfActionPerformed
        hapus_data("", "Combined PDF", txtMergedPdf, "Preview Combined PDF: Tidak Ada", "merge");
    }//GEN-LAST:event_BtnDeleteMergedPdfActionPerformed

    private void BtnDeleteMergedPdfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeleteMergedPdfKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteMergedPdfKeyPressed

    private void BtnDeleteSuratKontrolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnDeleteSuratKontrolMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteSuratKontrolMouseClicked

    private void BtnDeleteSuratKontrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDeleteSuratKontrolActionPerformed
        // String nama_file_export = export_path+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
        hapus_data("suratkontrol_", "Surat Kontrol", btnSuratKontrol, "Surat Kontrol: Tidak Ada", "export_with_no_rawat");
    }//GEN-LAST:event_BtnDeleteSuratKontrolActionPerformed

    private void BtnDeleteSuratKontrolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDeleteSuratKontrolKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDeleteSuratKontrolKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPenjaminanProses dialog = new DlgPenjaminanProses(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAttachment;
    private widget.Button BtnAttachmentINACBG;
    private widget.Button BtnCari;
    private widget.Button BtnDeleteInacbg;
    private widget.Button BtnDeleteMergedPdf;
    private widget.Button BtnDeletePdf;
    private widget.Button BtnDeleteScan;
    private widget.Button BtnDeleteSuratKontrol;
    private widget.Button BtnEditBilling;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint1;
    private widget.Button BtnPrintSemua;
    private widget.Button BtnScan;
    private widget.ScrollPane Scroll;
    private javax.swing.JButton btnINA;
    private javax.swing.JButton btnNota;
    private javax.swing.JButton btnSuratKontrol;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSplitPane jSplitPane1;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private javax.swing.JLabel statusScan;
    private widget.Table tbSuratKontrol;
    private javax.swing.JTextField temp_sep;
    private javax.swing.JButton txtIna;
    private javax.swing.JButton txtLab;
    private javax.swing.JButton txtMergedPdf;
    private widget.TextBox txtNoNota;
    private widget.TextBox txtNoRm;
    private widget.TextBox txtNoSurat;
    private widget.TextBox txtNorawat;
    private javax.swing.JButton txtPdfTambahan;
    private javax.swing.JButton txtRadiologi;
    private javax.swing.JButton txtScanInacbg;
    private javax.swing.JButton txtScanTambahan;
    private widget.TextBox txtSep;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String no_rawat = txtNorawat.getText();
        String no_nota = txtNoNota.getText();
        String no_surat = txtNoSurat.getText();
        String no_rm = txtNoRm.getText();
        
        String no_sep = Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat = '"+txtNorawat.getText()+"'");
        temp_sep.setText(no_sep);
        //###################################
        //||                               ||
        //||List data riwayat surat kontrol||
        //||                               ||
        //###################################

        Valid.tabelKosong(tabMode);
        try{
            String query_surat_kontrol_list = "SELECT \n" +
                                            "    bskb.tgl_surat,\n" +
                                            "    bskb.no_surat,\n" +
                                            "    rp.no_rkm_medis,\n" +
                                            "    p.nm_pasien,\n" +
                                            "    GROUP_CONCAT(pr.penilaian SEPARATOR ', ') AS penilaian,\n" +
                                            "    GROUP_CONCAT(pr.rtl SEPARATOR ', ') AS rtl,\n" +
                                            "    GROUP_CONCAT(pr.keluhan SEPARATOR ', ') AS keluhan,\n" +
                                            "    GROUP_CONCAT(pr.instruksi SEPARATOR ', ') AS instruksi,\n" +
                                            "    GROUP_CONCAT(pr.evaluasi SEPARATOR ', ') AS evaluasi,\n" +
                                            "    bskb.tgl_rencana,\n" +
                                            "    bskb.nm_poli_bpjs,\n" +
                                            "    bskb.nm_dokter_bpjs,\n" +
                                            "    rp.kd_poli\n" +
                                            "FROM\n" +
                                            "	bridging_surat_kontrol_bpjs bskb\n" +
                                            "INNER JOIN\n" +
                                            "	bridging_sep bs ON bskb.no_sep = bs.no_sep\n" +
                                            "INNER JOIN\n" +
                                            "	reg_periksa rp ON bs.no_rawat = rp.no_rawat\n" +
                                            "INNER JOIN\n" +
                                            "	pemeriksaan_ralan pr ON rp.no_rawat = pr.no_rawat\n" +
                                            "INNER JOIN\n" +
                                            "	pasien p ON rp.no_rkm_medis = p.no_rkm_medis\n" +
                                            "WHERE \n" +
                                            "	rp.no_rkm_medis = '"+no_rm+"' \n" +
                                            "GROUP BY\n" +
                                            "	rp.no_rawat\n"+
                                            "ORDER BY\n" +
                                            "	bskb.tgl_surat DESC LIMIT 0,5";
            System.out.println("query_tampil: "+query_surat_kontrol_list);
            ps=koneksi.prepareStatement(query_surat_kontrol_list);
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,
                        rs.getString("tgl_surat"),
                        rs.getString("no_surat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("penilaian"),
                        rs.getString("rtl"),
                        rs.getString("keluhan"),
                        rs.getString("instruksi"),
                        rs.getString("evaluasi"),
                        rs.getString("tgl_rencana"),
                        rs.getString("nm_poli_bpjs"),
                        rs.getString("nm_dokter_bpjs"),
                        rs.getString("kd_poli")
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
                        
        //######################################
        //||                                  ||
        //||  Cek Data sukon PDF Exported     ||
        //||                                  ||
        //######################################
        
        // Tentukan jalur file
        String sukonpdf = export_path+"suratkontrol_"+txtNorawat.getText().replace("/", "-")+".pdf";
        // Buat objek File
        File file_sukon_pdf= new File(sukonpdf);
        
        // Periksa apakah file ada
        if (file_sukon_pdf.exists()) {
            tbSuratKontrol.setBackground(Color.GREEN);
        }
        
        //################################
        //||                            ||
        //||      Cek data billing      ||
        //||                            ||
        //################################
        
        int cek_nota_ralan = Sequel.cariInteger("SELECT COUNT(*) FROM nota_jalan WHERE no_rawat = '"+no_rawat+"'");
        int cek_nota_inap = Sequel.cariInteger("SELECT COUNT(*) FROM nota_inap WHERE no_rawat = '"+no_rawat+"'");
        if(cek_nota_ralan > 0){
            btnNota.setText("Nota Ada");
            btnNota.setEnabled(true);
        }else if(cek_nota_inap > 0){
            btnNota.setText("Nota Ada");
            btnNota.setEnabled(true);
        }else{
            btnNota.setText("Nota Belum Ada");
            btnNota.setEnabled(false);
        }
        
                
        //######################################
        //||                                  ||
        //||  Cek Data billing PDF Exported   ||
        //||                                  ||
        //######################################
        
        // Tentukan jalur file
        String billingpdf = export_path+"nota_"+txtNorawat.getText().replace("/", "-")+".pdf";
        // Buat objek File
        File file_billing_pdf= new File(billingpdf);
        
        // Periksa apakah file ada
        if (file_billing_pdf.exists()) {
            btnNota.setBackground(Color.GREEN);
        }
        
        //################################
        //||                            ||
        //||        Cek data lab        ||
        //||                            ||
        //################################
        
        int cek_lab = Sequel.cariInteger("select \n" +
                                        "  count(periksa_lab.kd_jenis_prw) AS jml_lab \n" +
                                        "from \n" +
                                        "  periksa_lab \n"+ 
                                        "inner join reg_periksa on periksa_lab.no_rawat = reg_periksa.no_rawat \n" +
                                        "where \n" +
                                        "  periksa_lab.kategori = 'PK' \n" +
                                        "  and periksa_lab.no_rawat like '"+txtNorawat.getText()+"' \n" +
                                        "  and reg_periksa.no_rkm_medis like '"+txtNoRm.getText()+"' \n");
        System.out.println("Jumlah lab: "+cek_lab);
        if(cek_lab > 0){
            txtLab.setText("Lab Ada");
            txtLab.setEnabled(true);
        }else{
            txtLab.setText("Lab Belum/Tidak Ada");
            txtLab.setEnabled(false);
        }
        
        //######################################
        //||                                  ||
        //||  Cek Data lab PDF Exported       ||
        //||                                  ||
        //######################################
        
        // Tentukan jalur file
        String labpdf = export_path+"lab_"+txtNorawat.getText().replace("/", "-")+".pdf";
        // Buat objek File
        File file_lab_pdf= new File(labpdf);
        
        // Periksa apakah file ada
        if (file_lab_pdf.exists()) {
            txtLab.setBackground(Color.GREEN);
        }
        
        //################################
        //||                            ||
        //||    Cek data radiologi      ||
        //||                            ||
        //################################
        
        int cek_radiologi = Sequel.cariInteger("SELECT COUNT(*) FROM permintaan_radiologi WHERE no_rawat = '"+no_rawat+"'");
        if(cek_radiologi > 0){
            txtRadiologi.setText("Radiologi Ada");
            txtRadiologi.setEnabled(true);
        }else{
            txtRadiologi.setText("Radiologi Belum/Tidak Ada");
            txtRadiologi.setEnabled(false);
        }
        
        //######################################
        //||                                  ||
        //||  Cek Data radiologi PDF Exported ||
        //||                                  ||
        //######################################
        
        // Tentukan jalur file
        String radiologipdf = export_path+"radiologi_"+txtNorawat.getText().replace("/", "-")+".pdf";
        // Buat objek File
        File file_radiologi_pdf= new File(radiologipdf);
        
        // Periksa apakah file ada
        if (file_radiologi_pdf.exists()) {
            txtRadiologi.setBackground(Color.GREEN);
        }
        
        
        //################################
        //||                            ||
        //||        Cek Data INA        ||
        //||                            ||
        //################################
        
        int cek_jml_sep = Sequel.cariInteger("SELECT COUNT(*) FROM bridging_sep WHERE no_rawat = '"+no_rawat+"'");
        System.out.println("cek_jml_sep: "+cek_jml_sep);
        if(cek_jml_sep > 1){
            txtIna.setText("Lembar Bukti Pelayanan: SEP Lebih dari 2");
            txtIna.setEnabled(false);
        }else if(cek_jml_sep == 0){
            txtIna.setText("Lembar Bukti Pelayanan: SEP Tidak Ada");
            txtIna.setEnabled(false);
        }else{
            txtIna.setText("Lembar Bukti Pelayanan: Ada");
            txtIna.setEnabled(true);
        }
        
        //################################
        //||                            ||
        //||  Cek Data INA PDF Exported ||
        //||                            ||
        //################################
        
        // Tentukan jalur file
        String inapdf = export_path+"lembar_pelayanan_"+txtNorawat.getText().replace("/", "-")+".pdf";
        // Buat objek File
        File file_inacbg_pdf= new File(inapdf);
        
        // Periksa apakah file ada
        if (file_inacbg_pdf.exists()) {
            txtIna.setBackground(Color.GREEN);
        }
        
        //################################
        //||                            ||
        //||       Cek Data INACBG      ||
        //||                            ||
        //################################
        
        // Tentukan jalur file
        fileinacbgpath = export_path+"merged_inacbg_"+txtSep.getText()+".pdf";
        // Buat objek File
        File file_inacbg = new File(fileinacbgpath);
        
        // Periksa apakah file ada
        if (file_inacbg.exists()) {
            System.out.println("File ada di jalur: " + fileinacbgpath);
            txtScanInacbg.setText("Berkas INACBG: Ada");
            txtScanInacbg.setEnabled(true);
            BtnDeleteInacbg.setEnabled(true);
            txtScanInacbg.setBackground(Color.GREEN);
        } else {
            System.out.println("File tidak ditemukan di jalur: " + fileinacbgpath);
            txtScanInacbg.setText("Berkas INACBG: Tidak Ada");
            txtScanInacbg.setEnabled(false);
            BtnDeleteInacbg.setEnabled(false);
        }
        
        //################################
        //||                            ||
        //||  Cek Data PDF Tambahan    ||
        //||                            ||
        //################################
        
        // Tentukan jalur file
        filepath = export_path+"merged_pdftambahan_"+txtSep.getText()+".pdf";
        // Buat objek File
        File file = new File(filepath);
        
        // Periksa apakah file ada
        if (file.exists()) {
            System.out.println("File ada di jalur: " + filepath);
            txtPdfTambahan.setText("PDF Tambahan: Ada");
            txtPdfTambahan.setEnabled(true);
            BtnDeletePdf.setEnabled(true);
            txtPdfTambahan.setBackground(Color.GREEN);
        } else {
            System.out.println("File tidak ditemukan di jalur: " + filepath);
            txtPdfTambahan.setText("PDF Tambahan: Tidak Ada");
            txtPdfTambahan.setEnabled(false);
            BtnDeletePdf.setEnabled(false);
        }
        
        //################################
        //||                            ||
        //||  Cek Data Scan Tambahan    ||
        //||                            ||
        //################################
        
        // Tentukan jalur file
        filescanpath = export_path+"tambahan_scan_"+txtSep.getText()+".pdf";
        // Buat objek File
        File file_scan = new File(filescanpath);
        
        // Periksa apakah file ada
        if (file_scan.exists()) {
            System.out.println("File ada di jalur: " + filescanpath);
            txtScanTambahan.setText("Scan Tambahan: Ada");
            txtScanTambahan.setEnabled(true);
            BtnDeleteScan.setEnabled(true);
            txtScanTambahan.setBackground(Color.GREEN);
        } else {
            System.out.println("File tidak ditemukan di jalur: " + filescanpath);
            txtScanTambahan.setText("Scan Tambahan: Tidak Ada");
            txtScanTambahan.setEnabled(false);
            BtnDeleteScan.setEnabled(false);
        }
        
        //################################
        //||                            ||
        //||  Cek Data merged pdf       ||
        //||                            ||
        //################################
        
        // Tentukan jalur file
        filemergedpath = merged_path+txtSep.getText()+".pdf";
        // Buat objek File
        File file_merged = new File(filemergedpath);
        
        // Periksa apakah file ada
        if (file_merged.exists()) {
            System.out.println("File ada di jalur: " + filemergedpath);
            txtMergedPdf.setText("Preview Combined PDF: Ada");
            txtMergedPdf.setEnabled(true);
            BtnDeleteMergedPdf.setEnabled(true);
            txtMergedPdf.setBackground(Color.GREEN);
        } else {
            System.out.println("File tidak ditemukan di jalur: " + filemergedpath);
            txtMergedPdf.setText("Preview Combined PDF: Tidak Ada");
            txtMergedPdf.setEnabled(false);
            BtnDeleteMergedPdf.setEnabled(false);
        }
    }
    
    private void otomatisRalan() {
        if(Sequel.cariRegistrasi(txtNorawat.getText())==0){
            try {
                Sequel.AutoComitFalse();
                sukses=true;
                ttljmdokter=0;ttljmperawat=0;ttlkso=0;ttlpendapatan=0;ttljasasarana=0;ttlbhp=0;ttlmenejemen=0;
                psotomatis=koneksi.prepareStatement(sqlpsotomatis);
                String kd_dokter = Sequel.cariIsi("select reg_periksa.kd_dokter from reg_periksa where reg_periksa.no_rawat=?",txtNorawat.getText());
                try {
                    psotomatis.setString(1,kd_dokter);
                    psotomatis.setString(2,Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",txtNorawat.getText()));
                    rskasir=psotomatis.executeQuery();
                    while(rskasir.next()){    
                        if(Sequel.cariIsiAngka("select count(rawat_jl_dr.no_rawat) from rawat_jl_dr where "+
                           "rawat_jl_dr.no_rawat='"+txtNorawat.getText()+"' and rawat_jl_dr.kd_jenis_prw='"+rskasir.getString(1)+"' "+
                           "and rawat_jl_dr.kd_dokter='"+kd_dokter+"'")==0){
                            psotomatis2=koneksi.prepareStatement(sqlpsotomatis2);
                            try {
                                psotomatis2.setString(1,txtNorawat.getText()); 
                                psotomatis2.setString(2,rskasir.getString(1));
                                psotomatis2.setString(3,kd_dokter);
                                psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakandr"));
                                psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                psotomatis2.setDouble(11,rskasir.getDouble("total_byrdr"));
                                psotomatis2.executeUpdate();
                            } catch (Exception e) {
                                sukses=false;
                                System.out.println("proses input data "+e);
                            } finally{
                                if(psotomatis2!=null){
                                    psotomatis2.close();
                                }
                            } 
                            if(sukses==true){
                                ttljmdokter=ttljmdokter+rskasir.getDouble("tarif_tindakandr");
                                ttlkso=ttlkso+rskasir.getDouble("kso");
                                ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrdr");
                                ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                            }                
                        }
                    }   
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally {
                    if(rskasir!=null){
                        rskasir.close();
                    }
                    if(psotomatis!=null){
                        psotomatis.close();
                    }
                }    

                if(!akses.getkode().equals("Admin Utama")){
                    psotomatis=koneksi.prepareStatement(sqlpsotomatispetugas);
                    try {                
                        psotomatis.setString(1,Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",txtNorawat.getText()));
                        rskasir=psotomatis.executeQuery();
                        while(rskasir.next()){    
                            if(Sequel.cariIsiAngka("select count(rawat_jl_pr.no_rawat) from rawat_jl_pr where "+
                               "rawat_jl_pr.no_rawat='"+txtNorawat.getText()+"' and rawat_jl_pr.kd_jenis_prw='"+rskasir.getString(1)+"' "+
                               "and rawat_jl_pr.nip='"+akses.getkode()+"'")==0){
                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2petugas);
                                try {
                                    psotomatis2.setString(1,txtNorawat.getText()); 
                                    psotomatis2.setString(2,rskasir.getString(1));
                                    psotomatis2.setString(3,akses.getkode());
                                    psotomatis2.setString(4,Sequel.cariIsi("select current_date()"));
                                    psotomatis2.setString(5,Sequel.cariIsi("select current_time()"));
                                    psotomatis2.setDouble(6,rskasir.getDouble("material"));
                                    psotomatis2.setDouble(7,rskasir.getDouble("bhp"));
                                    psotomatis2.setDouble(8,rskasir.getDouble("tarif_tindakanpr"));
                                    psotomatis2.setDouble(9,rskasir.getDouble("kso"));
                                    psotomatis2.setDouble(10,rskasir.getDouble("menejemen"));
                                    psotomatis2.setDouble(11,rskasir.getDouble("total_byrpr"));
                                    psotomatis2.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("proses input data "+e);
                                } finally{
                                    if(psotomatis2!=null){
                                        psotomatis2.close();
                                    }
                                }
                                if(sukses==true){
                                    ttljmperawat=ttljmperawat+rskasir.getDouble("tarif_tindakanpr");
                                    ttlkso=ttlkso+rskasir.getDouble("kso");
                                    ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrpr");
                                    ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                    ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                    ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                                } 
                            }
                        }   
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally {
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(psotomatis!=null){
                            psotomatis.close();
                        }
                    } 
                }

                if(!akses.getkode().equals("Admin Utama")){
                    psotomatis=koneksi.prepareStatement(sqlpsotomatisdokterpetugas);
                    try {
                        psotomatis.setString(1,kd_dokter);
                        psotomatis.setString(2,Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",txtNorawat.getText()));
                        rskasir=psotomatis.executeQuery();
                        while(rskasir.next()){    
                            if(Sequel.cariIsiAngka("select count(rawat_jl_drpr.no_rawat) from rawat_jl_drpr where "+
                               "rawat_jl_drpr.no_rawat='"+txtNorawat.getText()+"' and rawat_jl_drpr.kd_jenis_prw='"+rskasir.getString(1)+"' "+
                               "and rawat_jl_drpr.kd_dokter='"+kd_dokter+"'")==0){
                                psotomatis2=koneksi.prepareStatement(sqlpsotomatis2dokterpetugas);
                                try {
                                    psotomatis2.setString(1,txtNorawat.getText()); 
                                    psotomatis2.setString(2,rskasir.getString(1));
                                    psotomatis2.setString(3,kd_dokter);
                                    psotomatis2.setString(4,akses.getkode());
                                    psotomatis2.setString(5,Sequel.cariIsi("select current_date()"));
                                    psotomatis2.setString(6,Sequel.cariIsi("select current_time()"));
                                    psotomatis2.setDouble(7,rskasir.getDouble("material"));
                                    psotomatis2.setDouble(8,rskasir.getDouble("bhp"));
                                    psotomatis2.setDouble(9,rskasir.getDouble("tarif_tindakandr"));
                                    psotomatis2.setDouble(10,rskasir.getDouble("tarif_tindakanpr"));
                                    psotomatis2.setDouble(11,rskasir.getDouble("kso"));
                                    psotomatis2.setDouble(12,rskasir.getDouble("menejemen"));
                                    psotomatis2.setDouble(13,rskasir.getDouble("total_byrdrpr"));
                                    psotomatis2.executeUpdate();
                                } catch (Exception e) {
                                    sukses=false;
                                    System.out.println("proses input data "+e);
                                } finally{
                                    if(psotomatis2!=null){
                                        psotomatis2.close();
                                    }
                                } 
                                if(sukses==true){
                                    ttljmdokter=ttljmdokter+rskasir.getDouble("tarif_tindakandr");
                                    ttljmperawat=ttljmperawat+rskasir.getDouble("tarif_tindakanpr");
                                    ttlkso=ttlkso+rskasir.getDouble("kso");
                                    ttlpendapatan=ttlpendapatan+rskasir.getDouble("total_byrdrpr");
                                    ttljasasarana=ttljasasarana+rskasir.getDouble("material");
                                    ttlbhp=ttlbhp+rskasir.getDouble("bhp");
                                    ttlmenejemen=ttlmenejemen+rskasir.getDouble("menejemen");
                                } 
                            }
                        }   
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally {
                        if(rskasir!=null){
                            rskasir.close();
                        }
                        if(psotomatis!=null){
                            psotomatis.close();
                        }
                    } 
                }
                
                if(sukses==true){
                    Sequel.queryu("delete from tampjurnal");    
                    if(ttlpendapatan>0){
                        Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ralan+"','Suspen Piutang Tindakan Ralan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Tindakan_Ralan+"'");    
                        Sequel.menyimpan("tampjurnal","'"+Tindakan_Ralan+"','Pendapatan Tindakan Rawat Inap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Tindakan_Ralan+"'");                             
                    }
                    if(ttljmdokter>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"','Beban Jasa Medik Dokter Tindakan Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'");                               
                    }
                    if(ttljmperawat>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Beban Jasa Medik Paramedis Tindakan Ralan','"+ttljmperawat+"','0'","debet=debet+'"+(ttljmperawat)+"'","kd_rek='"+Beban_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"','Utang Jasa Medik Paramedis Tindakan Ralan','0','"+ttljmperawat+"'","kredit=kredit+'"+(ttljmperawat)+"'","kd_rek='"+Utang_Jasa_Medik_Paramedis_Tindakan_Ralan+"'");                             
                    }
                    if(ttlkso>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ralan+"','Beban KSO Tindakan Ralan','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Beban_KSO_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ralan+"','Utang KSO Tindakan Ralan','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Utang_KSO_Tindakan_Ralan+"'");                              
                    }
                    if(ttljasasarana>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Tindakan_Ralan+"','Beban Jasa Sarana Tindakan Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Tindakan_Ralan+"'");     
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Tindakan_Ralan+"','Utang Jasa Sarana Tindakan Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Tindakan_Ralan+"'");                              
                    }
                    if(ttlbhp>0){
                        Sequel.menyimpan("tampjurnal","'"+HPP_BHP_Tindakan_Ralan+"','HPP BHP Tindakan Ralan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+HPP_BHP_Tindakan_Ralan+"'");      
                        Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Tindakan_Ralan+"','Persediaan BHP Tindakan Ralan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Tindakan_Ralan+"'");                           
                    }
                    if(ttlmenejemen>0){
                        Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Tindakan_Ralan+"','Beban Jasa Menejemen Tindakan Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Tindakan_Ralan+"'");       
                        Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Tindakan_Ralan+"','Utang Jasa Menejemen Tindakan Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Tindakan_Ralan+"'");                            
                    }
                    sukses=jur.simpanJurnal(txtNorawat.getText(),"U","TINDAKAN RAWAT JALAN PASIEN "+txtNorawat.getText()+" DIPOSTING OLEH "+akses.getkode());     
                }
                
                if(sukses==true){
                    Sequel.Commit();
                }else{
                    System.out.println("Terjadi kesalahan saat pemrosesan data, transaksi tindakan otomatis dibatalkan!!");
                    Sequel.RollBack();
                }

                Sequel.AutoComitTrue();
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            }
        }            
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
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
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
        int row=tbSuratKontrol.getSelectedRow();
        if(row!= -1){
//            String id=tabMode.getValueAt(row,0).toString();
//            String ip_address=tabMode.getValueAt(row,1).toString();
//            String workstation=tabMode.getValueAt(row,2).toString();
//            txtNorawat.setText(id);
//            txtNoNota.setText(workstation);
//            txtNoSurat.setText(ip_address);

        }
    }
    
    public void setData(String no_rawat, String no_mr, String no_nota, String no_surat, String no_sep){
        txtNorawat.setText(no_rawat);
        txtNoRm.setText(no_mr);
        txtNoNota.setText(no_nota);
        txtNoSurat.setText(no_surat);
        txtSep.setText(no_sep);
        tampil();
    }

    public void emptTeks() {
        txtNorawat.setText("");
        txtNoNota.setText("");
        txtNoSurat.setText("");
        txtNoNota.requestFocus();
    }
    
    public JTable getTable(){
        return tbSuratKontrol;
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
}

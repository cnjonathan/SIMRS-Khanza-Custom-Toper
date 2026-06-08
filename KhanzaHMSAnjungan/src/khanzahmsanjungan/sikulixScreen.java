/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package khanzahmsanjungan;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.validasi2;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class sikulixScreen extends javax.swing.JFrame {
    
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private validasi2 Valid2=new validasi2();
    private Timer timer;
    private static final int TIMEOUT = 120000; // 30 seconds
    private BufferedImage image;
    private ImageIcon imageIcon;
    private String validasiregistrasi=Sequel.cariIsi("select set_validasi_registrasi.wajib_closing_kasir from set_validasi_registrasi");
    private final Connection koneksi=koneksiDB.condb();
    private String cek_booking_registrasi, cek_booking_mobile_jkn, cek_reg_periksa, cek_booking_poli, cek_booking_kddokter, cek_reg_periksa_poli, cek_reg_periksa_kddokter, cek_rujukan_bridging_sep, cek_status_checkin, cek_status_checkin_mobile_jkn, no_sep = "";
    Integer sisahari, cek_rujukan_expired = 0, ulangi_sidik_jari = 1;
    boolean booking, checkin = false;
    private String nama_instansi, alamat_instansi, kabupaten, propinsi,kontak,email,poli,
            antrian,nama,norm,dokter,no_rawat,bayar,penjab;
    private String sikulixpath="",sikuliximage="";
    private Thread sikuliThread, sikuliResetBpjs, sikuli_peserta_belum_terdaftar_fingerprint;
    private ImagePanel jPanel1; // Deklarasikan sebagai ImagePanel
    private static final Properties prop = new Properties();
    private volatile boolean running = true;

    public sikulixScreen(JFrame jFrame, boolean par) throws IOException {
        initComponents();
        setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        int anjunganHeight = (int) (Math.round(screen.height * 0.96));
        this.setSize(700,anjunganHeight);
        prop.loadFromXML(new FileInputStream("setting/database.xml"));
        sikulixpath = prop.getProperty("SIKULIX");
        sikuliximage = prop.getProperty("SIKULIXIMAGE");
        System.out.println("sikulix path: "+sikulixpath);
        
        try {
            image = ImageIO.read(new File(sikuliximage+"tunggu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        jPanel1 = new ImagePanel(image);
        jPanel9.add(jPanel1, BorderLayout.CENTER);
        updateImage(sikuliximage+"tunggu.gif");
        // jPanel8.setLayout(new BorderLayout()); // Atur layout manager untuk jPanel8
//        jPanel8.add(jPanel1, BorderLayout.CENTER); // Tambahkan jPanel1 ke jPanel8
        
        // Initialize Timer
        timer = new Timer(TIMEOUT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // sikuliThread.stop();
                dispose();
                // System.exit(0); // Optionally exit the application
            }
        });

        // Start Timer
        timer.setRepeats(false);
        timer.start();

        // Add Listeners to detect user activity
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetTimer();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                resetTimer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                requestFocusInWindow();
            }
        });
        
        sikuliResetBpjs = new Thread(() -> {
            try {
                Thread.sleep(1000);
                reset_sidik_jari_bpjs();
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        sikuliResetBpjs.start();
        
        jButton2.setEnabled(false);

    }
    
    private final Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNoRM = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtKTP = new javax.swing.JTextField();
        txtNamaPasien = new javax.swing.JTextField();
        btnCopyBPJS = new component.Button();
        jPanel9 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new component.Panel();
        jLabel3 = new component.Label();
        jPanel5 = new component.Panel();
        jLabel4 = new component.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.white);
        setLocation(new java.awt.Point(0, 0));
        setPreferredSize(new java.awt.Dimension(570, 795));

        jPanel3.setMinimumSize(new java.awt.Dimension(452, 110));
        jPanel3.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setForeground(java.awt.Color.white);
        jPanel2.setMaximumSize(new java.awt.Dimension(1000, 1000));

        jPanel6.setBackground(java.awt.Color.white);
        jPanel6.setPreferredSize(new java.awt.Dimension(500, 800));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jButton2.setBackground(new java.awt.Color(142, 68, 173));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(236, 240, 241));
        jButton2.setText("Sudah sidik jari? Klik untuk lanjut....");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMaximumSize(new java.awt.Dimension(500, 500));
        jButton2.setMinimumSize(new java.awt.Dimension(400, 80));
        jButton2.setPreferredSize(new java.awt.Dimension(500, 100));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton2, java.awt.BorderLayout.PAGE_END);

        jPanel7.setPreferredSize(new java.awt.Dimension(500, 100));
        jPanel7.setLayout(new java.awt.BorderLayout());

        txtKTP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtKTP.setPreferredSize(new java.awt.Dimension(30, 30));
        txtKTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKTPActionPerformed(evt);
            }
        });
        jPanel7.add(txtKTP, java.awt.BorderLayout.CENTER);

        txtNamaPasien.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel7.add(txtNamaPasien, java.awt.BorderLayout.PAGE_START);

        btnCopyBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Edit.png"))); // NOI18N
        btnCopyBPJS.setMnemonic('M');
        btnCopyBPJS.setToolTipText("Alt+M");
        btnCopyBPJS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCopyBPJS.setMargin(new java.awt.Insets(0, 3, 0, 0));
        btnCopyBPJS.setPreferredSize(new java.awt.Dimension(30, 30));
        btnCopyBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopyBPJSActionPerformed(evt);
            }
        });
        btnCopyBPJS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCopyBPJSKeyPressed(evt);
            }
        });
        jPanel7.add(btnCopyBPJS, java.awt.BorderLayout.PAGE_END);

        jPanel8.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel9.setPreferredSize(new java.awt.Dimension(500, 500));
        jPanel8.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel6.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jButton3.setBackground(java.awt.Color.white);
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(236, 240, 241));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cancel.png"))); // NOI18N
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setMaximumSize(new java.awt.Dimension(500, 500));
        jButton3.setMinimumSize(new java.awt.Dimension(400, 80));
        jButton3.setPreferredSize(new java.awt.Dimension(500, 100));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton3, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel6);

        jPanel3.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(130, 50, 130));
        jPanel4.setBorder(null);
        jPanel4.setForeground(java.awt.Color.white);
        jPanel4.setPreferredSize(new java.awt.Dimension(560, 50));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setForeground(new java.awt.Color(254, 184, 254));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/finger.png"))); // NOI18N
        jLabel3.setText("Silahkan lakukan scan sidik jari dahulu");
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel3.setIconTextGap(10);
        jLabel3.setPreferredSize(new java.awt.Dimension(650, 50));
        jPanel4.add(jLabel3, java.awt.BorderLayout.PAGE_END);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(130, 50, 130));
        jPanel5.setBorder(null);
        jPanel5.setForeground(java.awt.Color.white);
        jPanel5.setPreferredSize(new java.awt.Dimension(560, 30));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel4.setForeground(new java.awt.Color(254, 184, 254));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Dikembangkan Oleh Khanza.Soft Media and modified by IT RSUD Kartini 2024");
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jPanel5.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        proses_lanjut();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtKTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKTPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKTPActionPerformed

    private void btnCopyBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopyBPJSActionPerformed
        String KTPText = txtKTP.getText();
        StringSelection stringSelectionObj = new StringSelection(KTPText);
        Clipboard clipboardObj = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboardObj.setContents(stringSelectionObj, null);
    }//GEN-LAST:event_btnCopyBPJSActionPerformed

    private void btnCopyBPJSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCopyBPJSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCopyBPJSKeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        stopProcess();
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(sikulixScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sikulixScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sikulixScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sikulixScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new sikulixScreen(new javax.swing.JFrame(), true).setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(sikulixScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Button btnCopyBPJS;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private component.Label jLabel3;
    private component.Label jLabel4;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private component.Panel jPanel4;
    private component.Panel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField txtKTP;
    private javax.swing.JTextField txtNamaPasien;
    private javax.swing.JTextField txtNoRM;
    // End of variables declaration//GEN-END:variables
    
    private void cek_pendaftaran_bpjs(String noRm) throws SQLException{
        // cek apakah status di booking sudah check in di booking_registrasi dan apakah sudah ada di reg_periksa
        // di booking apakah ada
        cek_booking_registrasi= Sequel.cariIsi("SELECT\n"+
                                                "booking_registrasi.tanggal_periksa \n"+
                                                "FROM \n"+
                                                "booking_registrasi \n"+
                                                "WHERE \n"+
                                                "booking_registrasi.tanggal_periksa=CURDATE() AND \n"+
                                                "kd_pj = 'BPJ' AND \n"+
                                                "booking_registrasi.no_rkm_medis=?",noRm);
        cek_booking_mobile_jkn= Sequel.cariIsi("SELECT\n"+
                                                "referensi_mobilejkn_bpjs.tanggalperiksa \n"+
                                                "FROM \n"+
                                                "referensi_mobilejkn_bpjs \n"+
                                                "WHERE \n"+
                                                "referensi_mobilejkn_bpjs.tanggalperiksa=CURDATE() AND \n"+
                                                "referensi_mobilejkn_bpjs.norm=?",noRm);
        
        if(!cek_booking_registrasi.equals("") || !cek_booking_mobile_jkn.equals("")){
            booking = true;
        }
        
        if(booking){
            // apakah statusnya Belum checkin?
            
            //#################
            //||   URGENT    ||
            //#################
            // cek di table booking registrasi untuk checkin lewat si doel dan referensi_mobilejkn_bpjs untuk checkin lewat mobile JKN
            cek_status_checkin = Sequel.cariIsi("SELECT \n"+
                                                "booking_registrasi.status \n"+
                                                "FROM \n"+
                                                "booking_registrasi \n"+
                                                "WHERE \n"+
                                                "booking_registrasi.tanggal_periksa=CURDATE() AND \n"+
                                                "booking_registrasi.no_rkm_medis=?",noRm);
            cek_status_checkin_mobile_jkn = Sequel.cariIsi("SELECT \n"+
                                                "referensi_mobilejkn_bpjs.status \n"+
                                                "FROM \n"+
                                                "referensi_mobilejkn_bpjs \n"+
                                                "WHERE \n"+
                                                "referensi_mobilejkn_bpjs.tanggalperiksa=CURDATE() AND \n"+
                                                "referensi_mobilejkn_bpjs.norm=?",noRm);

            
            if(!cek_status_checkin.equals("Belum") || !cek_status_checkin_mobile_jkn.equals("Belum")){
                checkin = true;
            }else{
                checkin = false;
            }
            
            if(!checkin){
                if(cek_status_checkin.equals("Belum")){
                    JOptionPane.showMessageDialog(null,"Anda belum checkin di aplikasi si Doel, \n"+
                                                                    "Silahkan buka aplikasi si Doel dan klik Checkin untuk melanjutkan proses masuk.");
                }else if(cek_status_checkin_mobile_jkn.equals("Belum")){
                    JOptionPane.showMessageDialog(null,"Anda belum checkin di aplikasi Mobile JKN, \n"+
                                                                    "Silahkan buka aplikasi Mobile JKN dan klik Checkin untuk melanjutkan proses masuk.");
                }
            }else{
                //#################
                //||   URGENT    ||
                //#################
                DlgRegistrasi regis=new DlgRegistrasi(null,true);
                regis.setSize(this.getWidth(),this.getHeight());
                regis.setLocationRelativeTo(this);
                //###########################################################
                //||                                                       ||
                //||tujuan poli dari db booking_registrasi dicocokan dengan||
                //||rujukan pertama di db bridging_sep supaya poli rujukan ||
                //||  yang dari BPJS = Poli tujuan sesuai booking pasien   ||
                //||                                                       ||
                //###########################################################
                
                // pemisah if antara si doel dan mobile jkn
                if(!cek_booking_registrasi.equals("")){
                    // tujuan poli dari booking si doel harus sama dengan rujukan dari puskesmas
                    String kd_poli_bpjs = Sequel.cariIsi("SELECT "+
                                                         "m.kd_poli_bpjs "+
                                                         "FROM "+
                                                         "booking_registrasi br "+
                                                         "LEFT JOIN maping_poli_bpjs m ON br.kd_poli = m.kd_poli_rs "+
                                                         "WHERE "+
                                                         "br.tanggal_periksa=CURDATE() AND "+
                                                         "br.no_rkm_medis = ?",noRm);
                    
                    String query_cek_rujukan_pertama_bridging_sep = "SELECT "+
                                                                    "  b.nomr, "+
                                                                    "  b.nama_pasien, "+
//                                                                    "  MIN("+
//                                                                    "    REPLACE(b.no_rawat, '/', '')"+
//                                                                    "  ) AS no_rawat_min, "+
                                                                    "  b.no_rawat AS no_rawat, "+
                                                                    "  b.no_rujukan, "+
                                                                    "  b.kdpolitujuan, "+
                                                                    "  m.kd_poli_rs, "+
                                                                    "  m.kd_poli_bpjs, "+
                                                                    "  b.nmpolitujuan, "+
                                                                    "  ("+
                                                                    "    90 - DATEDIFF(CURRENT_DATE, b.tglrujukan)"+
                                                                    "  ) AS sisahari "+
                                                                    "FROM "+
                                                                    "  bridging_sep b "+
                                                                    "  LEFT JOIN maping_poli_bpjs m ON b.kdpolitujuan = m.kd_poli_bpjs "+
                                                                    "WHERE "+
                                                                    "  b.nomr = '"+noRm+"' "+
                                                                    "  AND b.jnspelayanan = '2' "+
                                                                    "  AND b.no_rujukan <> '' "+
                                                                    "  ORDER BY sisahari DESC "
//                                                                    "  AND ("+
//                                                                    "    90 - DATEDIFF(CURRENT_DATE, b.tglrujukan)"+
//                                                                    "  ) > 1"
                            ;
                    
                    System.out.println("query_cek_rujukan_pertama_bridging_sep: "+query_cek_rujukan_pertama_bridging_sep);
                    
                    String query_count_rujukan_pertama_bridging_sep = "SELECT "+
                                                                    "  COUNT(b.nomr) AS jml_data, "+
                                                                    "  b.nomr, "+
                                                                    "  b.nama_pasien, "+
//                                                                    "  MIN("+
//                                                                    "    REPLACE(b.no_rawat, '/', '')"+
//                                                                    "  ) AS no_rawat_min, "+
                                                                    "  b.no_rawat AS no_rawat, "+
                                                                    "  b.no_rujukan, "+
                                                                    "  b.kdpolitujuan, "+
                                                                    "  m.kd_poli_rs, "+
                                                                    "  m.kd_poli_bpjs, "+
                                                                    "  b.nmpolitujuan, "+
                                                                    "  ("+
                                                                    "    90 - DATEDIFF(CURRENT_DATE, b.tglrujukan)"+
                                                                    "  ) AS sisahari "+
                                                                    "FROM "+
                                                                    "  bridging_sep b "+
                                                                    "  LEFT JOIN maping_poli_bpjs m ON b.kdpolitujuan = m.kd_poli_bpjs "+
                                                                    "WHERE "+
                                                                    "  b.nomr = '"+noRm+"' "+
                                                                    "  AND b.jnspelayanan = '2' "+
                                                                    "  AND b.no_rujukan <> '' "
//                                                                    "  AND ("+
//                                                                    "    90 - DATEDIFF(CURRENT_DATE, b.tglrujukan)"+
//                                                                    "  ) > 1"
                            ;

                    try {
                        PreparedStatement ps_rujukan_bridging_sep = koneksi.prepareStatement(query_cek_rujukan_pertama_bridging_sep);
                        ResultSet rs_rujukan_bridging_sep = ps_rujukan_bridging_sep.executeQuery();
                        
                        PreparedStatement ps_count_rujukan_bridging_sep = koneksi.prepareStatement(query_count_rujukan_pertama_bridging_sep);
                        ResultSet rs_count_rujukan_bridging_sep = ps_count_rujukan_bridging_sep.executeQuery();
                        
                        rs_count_rujukan_bridging_sep.next();
                        int jml_data = rs_count_rujukan_bridging_sep.getInt("jml_data");
                        if(jml_data<1){
                            System.out.println("null");
                            printLabelAtasSaja(noRm, "Belum ada rujukan yang tersimpan di kami,");
                            pasienKeRegistrasi(noRm);
                        }else{
                            System.out.println("not null");
                            // dicocokan dari si doel dan bridging sep pertama apakah sama?
                            // jika ya, maka lanjut ke filter selanjutnya
                            
                            rs_rujukan_bridging_sep.next();
                            String kode_poli_bpjs = rs_rujukan_bridging_sep.getString("kd_poli_bpjs")+" ";
                            if(kode_poli_bpjs.equals(kd_poli_bpjs+" ")){ //kode_poli_bpjs = bridging_bpjs, kd_poli_bpjs = booking_registrasi
                            // if(!rs_rujukan_bridging_sep.wasNull()){
                              System.out.println("line 735 "+rs_rujukan_bridging_sep.getString("kd_poli_bpjs"));
                              sisahari = rs_rujukan_bridging_sep.getInt("sisahari");
                              System.out.println("sisahari: "+sisahari);
                                if(sisahari <= 0){
                                    printLabelAtasSaja(noRm, "Mohon Maaf, Rujukan anda sudah habis");
                                    pasienKeRegistrasi(noRm);
                                }else if(sisahari > 1){
                                    String no_rawat = rs_rujukan_bridging_sep.getString("no_rawat");
                                    cek_reg_periksa_poli = Sequel.cariIsi("SELECT \n"+
                                                                            "reg_periksa.kd_poli \n"+
                                                                            "FROM \n"+
                                                                            "reg_periksa \n"+
                                                                            // "LEFT JOIN reg_periksa ON bridging_sep.no_rawat = reg_periksa.no_rawat \n"+
                                                                            "WHERE \n"+
                                                                            "reg_periksa.no_rawat = ?", no_rawat);
                                    cek_reg_periksa_kddokter = Sequel.cariIsi("SELECT \n"+
                                                                              "reg_periksa.kd_dokter \n"+
                                                                              "FROM \n"+
                                                                              "reg_periksa \n"+
                                                                              // "LEFT JOIN reg_periksa ON bridging_sep.no_rawat = reg_periksa.no_rawat \n"+
                                                                              "WHERE \n"+
                                                                              "reg_periksa.no_rawat = ?", no_rawat);

                                    cek_reg_periksa= Sequel.cariIsi("SELECT \n"+
                                                                    "reg_periksa.no_rawat \n"+
                                                                    "FROM \n"+
                                                                    "reg_periksa \n"+
                                                                    "WHERE \n"+
                                                                    "reg_periksa.tgl_registrasi=CURDATE() AND \n"+
                                                                    "kd_pj = 'BPJ' \n"+
                                                                    "AND reg_periksa.no_rkm_medis=?",noRm);
                                    
                                    //#########################################
                                    //||                                     ||
                                    //||cek surat kontrol apakah ada dan bisa||
                                    //||                                     ||
                                    //#########################################
                                    String query_cek_bridging_sk = "SELECT "+
                                                                    "	bs.no_sep, "+
                                                                    "   bs.kdpolitujuan, "+
                                                                    "	sk.tgl_rencana "+
                                                                    "FROM "+
                                                                    "	bridging_sep bs "+
                                                                    "INNER JOIN "+
                                                                    "	bridging_surat_kontrol_bpjs sk ON bs.no_sep = sk.no_sep "+
                                                                    "WHERE "+
                                                                    "	bs.nomr = '"+noRm+"' "+
                                                                    "	AND bs.kdpolitujuan = '"+kd_poli_bpjs+"' "+
                                                                    "ORDER BY sk.tgl_rencana DESC; ";
                                    System.out.println("query_cek_bridging_sk: "+query_cek_bridging_sk);
                                    PreparedStatement ps_query_cek_bridging_sk = koneksi.prepareStatement(query_cek_bridging_sk);
                                    ResultSet rs_query_cek_bridging_sk = ps_query_cek_bridging_sk.executeQuery();
                                    
                                    if(rs_query_cek_bridging_sk.next()){
                                        // tanggal hari ini
                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Date tanggal_hari_ini = new Date();
                                        Date tanggal_rencana_kontrol;
                                        try {
                                          no_sep = rs_query_cek_bridging_sk.getString("no_sep");
                                          System.out.println("cek_booking_registrasi no_sep: "+no_sep);
                                          tanggal_rencana_kontrol = dateFormat.parse(rs_query_cek_bridging_sk.getString("tgl_rencana"));
                                          System.out.println("tanggal_rencana_kontrol: "+tanggal_rencana_kontrol.toString());
                                          if (tanggal_rencana_kontrol.compareTo(tanggal_hari_ini) < 0) {
                                            System.out.println("tanggal hari ini lebih besar daripada tanggal rencana kontrol, bisa lanjut");
                                            if(!cek_reg_periksa.equals("")){
                                                // langsung cetak
                                                regis.setPasien(noRm, cek_reg_periksa_poli, cek_reg_periksa_kddokter, "true", "bpjs", "Si Doel", no_sep);
                                            }else{
                                                regis.setPasien(noRm, cek_reg_periksa_poli, cek_reg_periksa_kddokter, "false", "bpjs", "Si Doel", no_sep);
                                            }
                                            regis.setVisible(true);
                                            } else {
                                                System.out.println("tanggal hari ini lebih kecil daripada tanggal rencana kontrol alias maju");
                                                JOptionPane.showMessageDialog(null,"Anda kontrol tanggal lebih maju dari yang seharusnya, daftarlah sesuai dengan tanggal kontrol atau tanggal lebih mundur dari seharusnya. ");
                                                dispose();
                                            }
                                        } catch (ParseException ex) {
                                            Logger.getLogger(FormBPJS.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        System.out.println("tanggal hari ini: "+tanggal_hari_ini.toString());
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Surat Kontrol anda tidak terdaftar di sistem kami,  \n"+
                                                                                    "silahkan ambil antrian ke petugas registrasi.");
                                        printLabelAtasSajaTanpaOpsi(noRm);
                                        dispose();
                                    }
                                }
                            }else{
                                JOptionPane.showMessageDialog(null,"Tujuan Poliklinik anda ("+kd_poli_bpjs+") tidak sama dengan Poli Rujukan("+rs_rujukan_bridging_sep.getString("kd_poli_bpjs")+"), \n"+
                                                                                    "silahkan konfirmasi ke petugas registrasi.");
                                printLabelAtasSajaTanpaOpsi(noRm);
                                dispose();
                            }
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(FormBPJS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(!cek_booking_mobile_jkn.equals("")){
                    cek_reg_periksa_poli = Sequel.cariIsi("SELECT \n"+
                                                        "reg_periksa.kd_poli \n"+
                                                        "FROM \n"+
                                                        "reg_periksa \n"+
                                                        "WHERE \n"+
                                                        "reg_periksa.tgl_registrasi=CURDATE() AND \n"+
                                                        "kd_pj = 'BPJ' AND \n"+
                                                        "reg_periksa.no_rkm_medis=?",noRm);
                    cek_reg_periksa_kddokter = Sequel.cariIsi("SELECT \n"+
                                                          "reg_periksa.kd_dokter \n"+
                                                          "FROM \n"+
                                                          "reg_periksa \n"+
                                                          "WHERE \n"+
                                                          "reg_periksa.tgl_registrasi=CURDATE() AND \n"+
                                                          "kd_pj = 'BPJ' AND \n"+
                                                          "reg_periksa.no_rkm_medis=?",noRm);
                    System.out.println("cek_booking_mobile_jkn no_sep: "+no_sep);
                    regis.setPasien(noRm, cek_reg_periksa_poli, cek_reg_periksa_kddokter, "false", "bpjs", "Mobile JKN", no_sep);
                    regis.setVisible(true);
                }
            }
        }else{
            if(cek_booking_registrasi.equals("") && cek_booking_mobile_jkn.equals("")){
                JOptionPane.showMessageDialog(null,"Anda belum booking melalui aplikasi si Doel atau Mobile JKN, \n"+
                                                                "silahkan pilih poli dan dokter berikut ini:");
                DlgPilihPoli pilih=new DlgPilihPoli(this,true);
                pilih.setSize(this.getWidth()-20,this.getHeight()-70);
                pilih.setLocationRelativeTo(this);
                pilih.setPasien(noRm, "bpjs");
                pilih.tampil();
                pilih.setVisible(true);
            }
        }
    }
    
    public void printLabelAtasSaja(String noRm, String notif){
        if (JOptionPane.showConfirmDialog(null, notif+"\n Apakah anda membawa surat rujukan baru?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // yes option
            Map<String, Object> param_expired = new HashMap<>();
            param_expired.put("namars",nama_instansi);
            param_expired.put("alamatrs",alamat_instansi);
            param_expired.put("kotars",kabupaten);
            param_expired.put("propinsirs",propinsi);
            param_expired.put("kontakrs",kontak);
            param_expired.put("emailrs",email);
            param_expired.put("logo",Sequel.cariGambar("select setting.logo from setting"));

            String cek_booking_registrasi= Sequel.cariIsi("SELECT\n"+
                            "booking_registrasi.tanggal_periksa \n"+
                            "FROM \n"+
                            "booking_registrasi \n"+
                            "WHERE \n"+
                            "booking_registrasi.tanggal_periksa=CURDATE() AND \n"+
                            "kd_pj = 'BPJ' AND \n"+
                            "booking_registrasi.no_rkm_medis=?",noRm);

            String cek_booking_mobile_jkn= Sequel.cariIsi("SELECT\n"+
                                                    "referensi_mobilejkn_bpjs.tanggalperiksa \n"+
                                                    "FROM \n"+
                                                    "referensi_mobilejkn_bpjs \n"+
                                                    "WHERE \n"+
                                                    "referensi_mobilejkn_bpjs.tanggalperiksa=CURDATE() AND \n"+
                                                    "referensi_mobilejkn_bpjs.norm=?",noRm);

            if(!cek_booking_registrasi.equals("")){
                Valid2.MyReportqry("rptAnjungan.jasper",
                                 "report",
                                       "::[ Label Anjungan ]::",
                                            "SELECT "+
                                            "  p.no_ktp, "+
                                            "  p.jk, "+
                                            "  p.nm_pasien, "+
                                            "  poli.nm_poli, "+
                                            "  d.nm_dokter, "+
                                            "  b.no_reg, "+
                                            "  b.no_rkm_medis, "+
                                            "  b.tanggal_periksa AS tgl_registrasi "+
                                            "FROM "+
                                            "  booking_registrasi b "+
                                            "  INNER JOIN pasien p ON b.no_rkm_medis = p.no_rkm_medis "+
                                            "  INNER JOIN poliklinik poli ON b.kd_poli = poli.kd_poli "+
                                            "  INNER JOIN dokter d ON b.kd_dokter = d.kd_dokter "+
                                            "WHERE "+
                                            "  b.tanggal_periksa = CURDATE() "+
                                            "  AND b.kd_pj = 'BPJ' "+
                                            "  AND b.no_rkm_medis = "+noRm,
                                   param_expired, 
                                        1);
            }else if(!cek_booking_mobile_jkn.equals("")){
                Valid2.MyReportqry("rptAnjungan.jasper",
                                 "report",
                                       "::[ Label Anjungan ]::",
                                            "SELECT "+
                                            "  p.no_ktp, "+
                                            "  p.jk, "+
                                            "  p.nm_pasien, "+
                                            "  m.nm_poli_bpjs AS nm_poli, "+
                                            "  d.nm_dokter, "+
                                            "  r.nomorantrean AS no_reg, "+
                                            "  r.norm AS no_rkm_medis, "+
                                            "  r.tanggalperiksa AS tgl_registrasi "+
                                            "FROM "+
                                            "  referensi_mobilejkn_bpjs r "+
                                            "  INNER JOIN pasien p ON r.norm = p.no_rkm_medis "+
                                            "  INNER JOIN maping_poli_bpjs m ON r.kodepoli = m.kd_poli_bpjs "+
                                            "  INNER JOIN dokter d ON r.kodedokter = d.kd_dokter "+
                                            "WHERE "+
                                            "  r.tanggalperiksa = CURDATE() "+
                                            "  AND r.norm ="+noRm,
                                   param_expired, 
                                        1);
            }
            JOptionPane.showMessageDialog(null,"Silahkan ambil antrian untuk verifikasi ke petugas registrasi");
        } else {
            // no option
            JOptionPane.showMessageDialog(null,"Silahkan ke Faskes Pertama untuk membuat rujukan yang baru. ");
        }
    }
        
    public void printLabelAtasSajaTanpaOpsi(String noRm){
        Map<String, Object> param_expired = new HashMap<>();
        param_expired.put("namars",nama_instansi);
        param_expired.put("alamatrs",alamat_instansi);
        param_expired.put("kotars",kabupaten);
        param_expired.put("propinsirs",propinsi);
        param_expired.put("kontakrs",kontak);
        param_expired.put("emailrs",email);
        param_expired.put("logo",Sequel.cariGambar("select setting.logo from setting"));

        String cek_booking_registrasi= Sequel.cariIsi("SELECT\n"+
                        "booking_registrasi.tanggal_periksa \n"+
                        "FROM \n"+
                        "booking_registrasi \n"+
                        "WHERE \n"+
                        "booking_registrasi.tanggal_periksa=CURDATE() AND \n"+
                        "kd_pj = 'BPJ' AND \n"+
                        "booking_registrasi.no_rkm_medis=?",noRm);

        String cek_booking_mobile_jkn= Sequel.cariIsi("SELECT\n"+
                                                "referensi_mobilejkn_bpjs.tanggalperiksa \n"+
                                                "FROM \n"+
                                                "referensi_mobilejkn_bpjs \n"+
                                                "WHERE \n"+
                                                "referensi_mobilejkn_bpjs.tanggalperiksa=CURDATE() AND \n"+
                                                "referensi_mobilejkn_bpjs.norm=?",noRm);

        if(!cek_booking_registrasi.equals("")){
            Valid2.MyReportqry("rptAnjungan.jasper",
                             "report",
                                   "::[ Label Anjungan ]::",
                                        "SELECT "+
                                        "  p.no_ktp, "+
                                        "  p.jk, "+
                                        "  p.nm_pasien, "+
                                        "  poli.nm_poli, "+
                                        "  d.nm_dokter, "+
                                        "  b.no_reg, "+
                                        "  b.no_rkm_medis, "+
                                        "  b.tanggal_periksa AS tgl_registrasi "+
                                        "FROM "+
                                        "  booking_registrasi b "+
                                        "  INNER JOIN pasien p ON b.no_rkm_medis = p.no_rkm_medis "+
                                        "  INNER JOIN poliklinik poli ON b.kd_poli = poli.kd_poli "+
                                        "  INNER JOIN dokter d ON b.kd_dokter = d.kd_dokter "+
                                        "WHERE "+
                                        "  b.tanggal_periksa = CURDATE() "+
                                        "  AND b.kd_pj = 'BPJ' "+
                                        "  AND b.no_rkm_medis = "+noRm,
                               param_expired, 
                                    1);
        }else if(!cek_booking_mobile_jkn.equals("")){
            Valid2.MyReportqry("rptAnjungan.jasper",
                             "report",
                                   "::[ Label Anjungan ]::",
                                        "SELECT "+
                                        "  p.no_ktp, "+
                                        "  p.jk, "+
                                        "  p.nm_pasien, "+
                                        "  m.nm_poli_bpjs AS nm_poli, "+
                                        "  d.nm_dokter, "+
                                        "  r.nomorantrean AS no_reg, "+
                                        "  r.norm AS no_rkm_medis, "+
                                        "  r.tanggalperiksa AS tgl_registrasi "+
                                        "FROM "+
                                        "  referensi_mobilejkn_bpjs r "+
                                        "  INNER JOIN pasien p ON r.norm = p.no_rkm_medis "+
                                        "  INNER JOIN maping_poli_bpjs m ON r.kodepoli = m.kd_poli_bpjs "+
                                        "  INNER JOIN dokter d ON r.kodedokter = d.kd_dokter "+
                                        "WHERE "+
                                        "  r.tanggalperiksa = CURDATE() "+
                                        "  AND r.norm ="+noRm,
                               param_expired, 
                                    1);
        }
        Valid2.MyReportqry("rptNotifPetugas.jasper",
                             "report",
                                   "::[ Label Anjungan ]::",
                                        "SELECT CURDATE() AS tgl_registrasi",
                               param_expired, 
                                    1);
    }

    private void pasienKeRegistrasi(String noRm) {
        PreparedStatement ps_data_booking_registrasi, ps_update_pasien_ke_registrasi;
        String tanggal_periksa;
        try {
            ps_data_booking_registrasi = koneksi.prepareStatement("SELECT "+
                                                                "br.* "+
                                                                "FROM "+
                                                                "booking_registrasi br "+
                                                                // "LEFT JOIN maping_poli_bpjs m ON br.kd_poli = m.kd_poli_rs "+
                                                                "WHERE "+
                                                                "br.tanggal_periksa=CURDATE() AND "+
                                                                "br.no_rkm_medis = ?");
            ps_data_booking_registrasi.setString(1, noRm);
            ResultSet rs_data_booking_registrasi = ps_data_booking_registrasi.executeQuery();
            if(rs_data_booking_registrasi.next()){
                tanggal_periksa = rs_data_booking_registrasi.getString("tanggal_periksa");
                String update_trace_progress = "UPDATE view_anjungan SET trace_progress=?, petugas_registrasi_at=CURRENT_TIMESTAMP() WHERE param_1=? AND param_2=?";
                ps_update_pasien_ke_registrasi = koneksi.prepareStatement(update_trace_progress);
                ps_update_pasien_ke_registrasi.setString(1, "petugas_registrasi");
                ps_update_pasien_ke_registrasi.setString(2, noRm);
                ps_update_pasien_ke_registrasi.setString(3, tanggal_periksa);
                ps_update_pasien_ke_registrasi.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "Data booking tidak ditemukan, silahkan ke petugas registrasi");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormBPJS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void resetTimer() {
        System.out.println("Reset timer");
        timer.restart();
    }

    void setNoRm(String norm) {
        txtNoRM.setText(norm);
        txtKTP.setText(Sequel.cariIsi("select pasien.no_ktp from pasien where pasien.no_rkm_medis=?",norm));
        txtNamaPasien.setText(Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?",norm));
    }
    
    private void cek_sikulix_sukses() {
        if(running == true){
//            updateImage(sikuliximage+"arahan_finger.jpeg");
            updateImage(sikuliximage+"arahan_finger.gif");
        
            // Path ke file JAR
            String jarFilePath = sikulixpath+"deteksi_sidik_jari_sikuli.jar";
            System.out.println("jarFilePath: "+jarFilePath);
            // Membuat ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
            processBuilder.redirectErrorStream(true); // Gabungkan error stream ke output stream

            try {
                // Menjalankan proses
                Process process = processBuilder.start();

                // Membaca output proses
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null && running) {
                    System.out.println(line); // Cetak log ke konsol

                    // Periksa apakah ada "Notification detected!" dalam log
                    if (line.contains("Notification detected!")) {
                        // Berikan action ketika "Notification detected!" ditemukan
                        System.out.println("Action triggered: Notification detected!");
                        jButton2.setEnabled(true);
                        sikuli_lanjut_proses();
                    }else if(line.contains("Notification belum terdaftar fingerprint!")){
                        // Notification belum terdaftar fingerprint!
                        System.out.println("Action triggered: Notification belum terdaftar fingerprint!");
                        ulangi_sidik_jari();
                    }else if(line.contains("Notification belum terdaftar menjadi peserta BPJS!")){
                        // Notification belum terdaftar menjadi peserta BPJS!
                        System.out.println("Action triggered: Notification belum terdaftar menjadi peserta BPJS!");
                        ulangi_sidik_jari();
                    }else if(line.contains("Notification sidik jari tidak dikenal!")){
                        // Notification sidik jari tidak dikenal!
                        System.out.println("Action triggered: Notification sidik jari tidak dikenal!");
                        updateImage(sikuliximage+"sidik_jari_gagal.gif");
                        ulangi_sidik_jari();
                    }else{
                        cek_sikulix_sukses();
                    }
                }

                // Menunggu hingga proses selesai
                int exitCode = process.waitFor();
                System.out.println("Exited with code: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void ulangi_sidik_jari() {
         // Path ke file JAR
        String jarFilePath = sikulixpath+"ulangi_sidik_jari_sikuli.jar";
        System.out.println("jarFilePath: "+jarFilePath);
        System.out.println("ulangi_sidik_jari: "+ulangi_sidik_jari);
        if(ulangi_sidik_jari < 3 && running == true){
            ulangi_sidik_jari++;
            // Membuat ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
            processBuilder.redirectErrorStream(true); // Gabungkan error stream ke output stream

            try {
                // Menjalankan proses
                Process process = processBuilder.start();

                // Membaca output proses
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null && running) {
                    System.out.println(line); // Cetak log ke konsol
                    reset_sidik_jari_bpjs();
                }

                // Menunggu hingga proses selesai
                int exitCode = process.waitFor();
                System.out.println("ulangi_sidik_jari_sikuli: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            stopProcess();
            // Membuat JPanel
            JPanel panel = new JPanel();

            // Menampilkan message dialog dengan JOptionPane
            JOptionPane optionPane = new JOptionPane("<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Sidik jari tidak dapat diproses, silahkan konfirmasi ke petugas</font></div></html>",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

            // Membuat JDialog dari JOptionPane
            JDialog dialog = optionPane.createDialog(panel, "Auto Close Message");

            // Set dialog always on top
            dialog.setAlwaysOnTop(true); // Ini memastikan dialog berada di atas window lainnya
        
            // Timer untuk menutup dialog otomatis
            Timer timer = new Timer(3000, new ActionListener() { // 3000 ms = 3 detik
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose(); // Menutup dialog
                    
                }
            });
            timer.setRepeats(false); // Timer hanya berjalan sekali
            timer.start();

            // Menampilkan dialog
            dialog.setVisible(true);
            
            // Membuat ProcessBuilder
            ProcessBuilder processBuilderStop = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
            processBuilderStop.redirectErrorStream(true); // Gabungkan error stream ke output stream

            try {
                // Menjalankan proses
                Process processStop = processBuilderStop.start();
                stopProcess();
                dispose();

                // Menunggu hingga proses selesai
                int exitCode = processStop.waitFor();
                System.out.println("ulangi_sidik_jari_sikuli selesai: " + exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void stopProcess() {
        running = false;
    }
    
    private void cek_sikulix_peserta_belum_terdaftar_fingerprint() {
         // Path ke file JAR
        String jarFilePath = sikulixpath+"peserta_belum_terdaftar_fingerprint_sikuli.jar";
        
        // Membuat ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
        processBuilder.redirectErrorStream(true); // Gabungkan error stream ke output stream
        
        try {
            // Menjalankan proses
            Process process = processBuilder.start();
            
            // Membaca output proses
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Cetak log ke konsol
                
                // Periksa apakah ada "Notification detected!" dalam log
                if (line.contains("Notification detected!")) {
                    // Berikan action ketika "Notification detected!" ditemukan
                    System.out.println("Action triggered: Notification cek_sikulix_peserta_belum_terdaftar_fingerprint detected!");
                    sikuliThread.stop();
                    sikuli_peserta_belum_terdaftar_fingerprint.stop();
                }
            }
            
            // Menunggu hingga proses selesai
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void reset_sidik_jari_bpjs() {
        if(running == true){
            // Path ke file JAR
            String jarFilePath = sikulixpath+"project1_sikuli.jar";
            System.out.println("jarFilePath: "+jarFilePath);
            // Membuat ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
            processBuilder.inheritIO();

            try {
                // Menjalankan proses
                Process process = processBuilder.start();

                // Menunggu hingga proses selesai
                process.waitFor();

                // Mengecek exit code
                int exitCode = process.exitValue();
                System.out.println("Exited with code: " + exitCode);
                if(exitCode == 0){
                    copy_ktp_bpjs();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            dispose();
        }
    }

    private void copy_ktp_bpjs() {
        if(running == true){
            // Path ke file JAR
            String jarFilePath = sikulixpath+"copyktp_sikuli.jar";
            System.out.println("jarFilePath: "+jarFilePath);
            // Membuat ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
            processBuilder.inheritIO();

            try {
                // Menjalankan proses
                Process process = processBuilder.start();

                // Menunggu hingga proses selesai
                process.waitFor();

                // Mengecek exit code
                int exitCode = process.exitValue();
                System.out.println("Exited with code: " + exitCode);

                if(exitCode == 0){
                    // paste_ktp();
                    cek_sikulix_sukses();
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            dispose();
        }
    }

    private void proses_lanjut() {
        String norm = txtNoRM.getText();
        if(txtNoRM.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Silahkan masukkan No.RM/KTP/Asuransi/JKN !!!</font></div></html>");
        }else if(norm.length() < 6){
            JOptionPane.showMessageDialog(rootPane,"<html><div align='center'><font size='5' face='Tahoma' color='#825082'>No RM kurang dari 6 digit. </font></div></html>");
        }
        else{
            if(Sequel.cariInteger("select count(no_rkm_medis) from pasien where no_rkm_medis=?",txtNoRM.getText().trim())>0){
                 if(validasiregistrasi.equals("Yes")){
                     if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",txtNoRM.getText())>0){
                         JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                     }else{
                         try {
                            //DlgPilihPoli pilih=new DlgPilihPoli(this,true);
                            //pilih.setSize(this.getWidth()-20,this.getHeight()-70);
                            //pilih.setLocationRelativeTo(this);
                            //pilih.setPasien(txtNoRM.getText());
                            //pilih.tampil();
                            //pilih.setVisible(true);
                            cek_pendaftaran_bpjs(txtNoRM.getText().trim());
                         } catch (SQLException ex) {
                             Logger.getLogger(FormBPJS.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                 }else{
                     try {
                        //DlgPilihPoli pilih=new DlgPilihPoli(this,true);
                        //pilih.setSize(this.getWidth()-20,this.getHeight()-70);
                        //pilih.setLocationRelativeTo(this);
                        //pilih.setPasien(txtNoRM.getText());
                        //pilih.tampil();
                        //pilih.setVisible(true);
                        cek_pendaftaran_bpjs(txtNoRM.getText().trim());
                     } catch (SQLException ex) {
                         Logger.getLogger(FormBPJS.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }  
            }else if(Sequel.cariInteger("select count(no_ktp) from pasien where no_ktp=?",txtNoRM.getText().trim())>0){
                 if(validasiregistrasi.equals("Yes")){
                     if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",txtNoRM.getText().trim()))>0){
                         JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                     }else{
//                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                         pilih.setLocationRelativeTo(this);
//                         pilih.setPasien(Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",txtNoRM.getText().trim()));
//                         pilih.tampil();
//                         pilih.setVisible(true);
                     }
                 }else{
//                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                     pilih.setLocationRelativeTo(this);
//                     pilih.setPasien(Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",txtNoRM.getText().trim()));
//                     pilih.tampil();
//                     pilih.setVisible(true);
                 }
            }else if(Sequel.cariInteger("select count(no_peserta) from pasien where no_peserta=?",txtNoRM.getText().trim())>0){
                 if(validasiregistrasi.equals("Yes")){
                     if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",txtNoRM.getText().trim()))>0){
                         JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                     }else{
//                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                         pilih.setLocationRelativeTo(this);
//                         pilih.setPasien(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",txtNoRM.getText().trim()));
//                         pilih.tampil();
//                         pilih.setVisible(true);
                     }
                 }else{
//                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                     pilih.setLocationRelativeTo(this);
//                     pilih.setPasien(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",txtNoRM.getText().trim()));
//                     pilih.tampil();
//                     pilih.setVisible(true); 
                 }
            }else{
                JOptionPane.showMessageDialog(rootPane,
                    "<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Maaf, data pasien dengan No.RM/KTP/Asuransi/JKN tersebut tidak ditemukan." +
                    "<br>Bagi pasien yang baru pertama kali periksa, silahkan ke petugas pendaftaran </font></div></html>");
                txtNoRM.requestFocus();
            }
        }
    }

    private void paste_ktp() {
        // Path ke file JAR
        String jarFilePath = sikulixpath+"pastektp_sikuli.jar";
        
        // Membuat ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
        processBuilder.inheritIO();
        
        try {
            // Menjalankan proses
            Process process = processBuilder.start();
            
            // Menunggu hingga proses selesai
            process.waitFor();
            
            // Mengecek exit code
            int exitCode = process.exitValue();
            System.out.println("Exited with code: " + exitCode);
            
            if(exitCode == 0){
                cek_sikulix_sukses();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sikuli_lanjut_proses() {
        // Path ke file JAR
        String jarFilePath = sikulixpath+"lanjut_proses_sikuli.jar";
        System.out.println("jarFilePath: "+jarFilePath);
        // Membuat ProcessBuilder
        ProcessBuilder processBuilder = new ProcessBuilder(Arrays.asList("java", "-jar", jarFilePath));
        processBuilder.inheritIO();
        
        try {
            // Menjalankan proses
            Process process = processBuilder.start();
            
            // Menunggu hingga proses selesai
            process.waitFor();
            
            // Mengecek exit code
            int exitCode = process.exitValue();
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void updateImage(String imagePath) {
        try {
            if (imagePath.toLowerCase().endsWith(".gif")) {
                // Jika file adalah GIF, gunakan ImageIcon
                ImageIcon newImageIcon = new ImageIcon(imagePath);
                jPanel1.setImageIcon(newImageIcon);
            } else {
                // Jika file adalah gambar statis, gunakan BufferedImage
                BufferedImage newImage = ImageIO.read(new File(imagePath));
                jPanel1.setImage(newImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ImagePanel extends JPanel {
    private BufferedImage bufferedImage;
    private ImageIcon imageIcon;

    // Constructor untuk BufferedImage
    public ImagePanel(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        setPreferredSize(new Dimension(500, 500)); // Set ukuran panel
    }

    // Constructor untuk ImageIcon
    public ImagePanel(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        setPreferredSize(new Dimension(500, 500)); // Set ukuran panel
    }

    // Setter untuk BufferedImage
    public void setImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        this.imageIcon = null; // Clear ImageIcon jika di-set BufferedImage
        repaint(); // Memanggil repaint untuk memperbarui tampilan
    }

    // Setter untuk ImageIcon
    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        this.bufferedImage = null; // Clear BufferedImage jika di-set ImageIcon
        repaint(); // Memanggil repaint untuk memperbarui tampilan
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bufferedImage != null) {
            g.drawImage(bufferedImage, 0, 0, 500, 500, this);
        } else if (imageIcon != null) {
            imageIcon.paintIcon(this, g, 0, 0);
        }
    }
}

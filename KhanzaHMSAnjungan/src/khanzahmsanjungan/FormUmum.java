/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmUtama.java
 *
 * Created on 04 Des 13, 11:19:32
 */
package khanzahmsanjungan;

import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class FormUmum extends javax.swing.JFrame {
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private String validasiregistrasi=Sequel.cariIsi("select set_validasi_registrasi.wajib_closing_kasir from set_validasi_registrasi");
    private String cek_booking_registrasi, cek_reg_periksa, cek_booking_poli, cek_booking_kddokter, cek_reg_periksa_poli, cek_reg_periksa_kddokter = "";
    Integer sisahari = 0;
        
    /** Creates new form frmUtama */
    public FormUmum() {
        initComponents();
        setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());      
//        this.setSize(600,screen.height);
        int anjunganHeight = (int) (Math.round(screen.height * 0.80));
        this.setSize(600,anjunganHeight);
    }    
    private final Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelMR = new javax.swing.JLabel();
        jPanel1 = new component.Panel();
        jLabel1 = new component.Label();
        jLabel2 = new component.Label();
        jPanel3 = new component.Panel();
        jLabel3 = new component.Label();
        jPanel4 = new javax.swing.JPanel();
        panel1 = new usu.widget.glass.PanelGlass();
        jLabel6 = new component.Label();
        TCari = new component.TextBox();
        BtnCari = new component.Button();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        btnKembali = new component.Button();

        labelMR.setText("labelMR");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("::[ SIMRS KhanzaHMS, Sub Menu Anjungan Registrasi Mandiri Pasien ]::");
        setBackground(java.awt.Color.white);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(130, 50, 130));
        jPanel1.setBorder(null);
        jPanel1.setForeground(java.awt.Color.white);
        jPanel1.setPreferredSize(new java.awt.Dimension(560, 55));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setForeground(new java.awt.Color(254, 184, 254));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/folder.png"))); // NOI18N
        jLabel1.setText("Anjungan Registrasi Mandiri");
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 28)); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIconTextGap(10);
        jLabel1.setPreferredSize(new java.awt.Dimension(650, 135));
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jLabel2.setPreferredSize(new java.awt.Dimension(20, 10));
        jPanel1.add(jLabel2, java.awt.BorderLayout.LINE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(130, 50, 130));
        jPanel3.setBorder(null);
        jPanel3.setForeground(java.awt.Color.white);
        jPanel3.setPreferredSize(new java.awt.Dimension(560, 30));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel3.setForeground(new java.awt.Color(254, 184, 254));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Dikembangkan Oleh Khanza.Soft Media, Email : khanza_media@yahoo.com, Skype : khanza.media, HP : 08562675039");
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jPanel3.add(jLabel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(java.awt.Color.white);
        jPanel4.setForeground(java.awt.Color.white);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/wallpaper.jpg"))); // NOI18N
        panel1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_LEFT_BOTTOM);
        panel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(50, 1, 1, 1));
        panel1.setOpaque(true);
        panel1.setOpaqueImage(false);
        panel1.setPreferredSize(new java.awt.Dimension(200, 200));
        panel1.setRound(false);
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        panel1.setLayout(flowLayout1);

        jLabel6.setForeground(new java.awt.Color(130, 80, 130));
        jLabel6.setText("No.RM/KTP:");
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel6.setIconTextGap(10);
        jLabel6.setPreferredSize(new java.awt.Dimension(150, 45));
        panel1.add(jLabel6);

        TCari.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 45));
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panel1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('O');
        BtnCari.setToolTipText("Alt+O");
        BtnCari.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCari.setIconTextGap(0);
        BtnCari.setMargin(new java.awt.Insets(0, 3, 0, 0));
        BtnCari.setPreferredSize(new java.awt.Dimension(45, 45));
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
        panel1.add(BtnCari);

        jPanel4.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 590, 110));

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton1.setText("3");
        jButton1.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 110, 110));

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton2.setText("1");
        jButton2.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 110, 110));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton3.setText("2");
        jButton3.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 110, 110));

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton4.setText("6");
        jButton4.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 110, 110));

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton5.setText("4");
        jButton5.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 110, 110));

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton6.setText("5");
        jButton6.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 110, 110));

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton7.setText("<-");
        jButton7.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 390, 110, 110));

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton8.setText("7");
        jButton8.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 110, 110));

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton9.setText("0");
        jButton9.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 110, 110));

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton10.setText("8");
        jButton10.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 110, 110));

        jButton11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jButton11.setText("9");
        jButton11.setBorder(new javax.swing.border.LineBorder(java.awt.Color.lightGray, 1, true));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 110, 110));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 400, 510));

        btnKembali.setBackground(new java.awt.Color(130, 50, 130));
        btnKembali.setForeground(java.awt.Color.white);
        btnKembali.setMnemonic('O');
        btnKembali.setText("Kembali Ke Menu Awal");
        btnKembali.setToolTipText("Alt+O");
        btnKembali.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnKembali.setIconTextGap(0);
        btnKembali.setMargin(new java.awt.Insets(0, 3, 0, 0));
        btnKembali.setPreferredSize(new java.awt.Dimension(45, 45));
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });
        jPanel4.add(btnKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 320, -1));

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if(TCari.getText().trim().equals("")){
                JOptionPane.showMessageDialog(rootPane,"<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Silahkan masukkan No.RM/KTP/Asuransi/JKN !!!</font></div></html>");
            }else{
                if(Sequel.cariInteger("select count(no_rkm_medis) from pasien where no_rkm_medis=?",TCari.getText().trim())>0){
                     if(validasiregistrasi.equals("Yes")){
                         if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",TCari.getText())>0){
                             JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                         }else{
    //                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
    //                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
    //                         pilih.setLocationRelativeTo(this);
    //                         pilih.setPasien(TCari.getText());
    //                         pilih.tampil();
    //                         pilih.setVisible(true);
                               cek_pendaftaran(TCari.getText().trim());
                         }
                     }else{
    //                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
    //                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
    //                     pilih.setLocationRelativeTo(this);
    //                     pilih.setPasien(TCari.getText());
    //                     pilih.tampil();
    //                     pilih.setVisible(true);
                           cek_pendaftaran(TCari.getText().trim());
                     }  
                }else if(Sequel.cariInteger("select count(no_ktp) from pasien where no_ktp=?",TCari.getText().trim())>0){
                     if(validasiregistrasi.equals("Yes")){
                         if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",TCari.getText().trim()))>0){
                             JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                         }else{
    //                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
    //                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
    //                         pilih.setLocationRelativeTo(this);
    //                         pilih.setPasien(Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",TCari.getText().trim()));
    //                         pilih.tampil();
    //                         pilih.setVisible(true);
                         }
                     }else{
    //                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
    //                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
    //                     pilih.setLocationRelativeTo(this);
    //                     pilih.setPasien(Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",TCari.getText().trim()));
    //                     pilih.tampil();
    //                     pilih.setVisible(true);
                     }
                }else if(Sequel.cariInteger("select count(no_peserta) from pasien where no_peserta=?",TCari.getText().trim())>0){
                     if(validasiregistrasi.equals("Yes")){
                         if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",TCari.getText().trim()))>0){
                             JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                         }else{
    //                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
    //                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
    //                         pilih.setLocationRelativeTo(this);
    //                         pilih.setPasien(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",TCari.getText().trim()));
    //                         pilih.tampil();
    //                         pilih.setVisible(true);
                         }
                     }else{
    //                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
    //                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
    //                     pilih.setLocationRelativeTo(this);
    //                     pilih.setPasien(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",TCari.getText().trim()));
    //                     pilih.tampil();
    //                     pilih.setVisible(true); 
                     }
                }else{
                    JOptionPane.showMessageDialog(rootPane,
                        "<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Maaf, data pasien dengan No.RM/KTP/Asuransi/JKN tersebut tidak ditemukan." +
                        "<br>Bagi pasien yang baru pertama kali periksa, silahkan ke petugas pendaftaran </font></div></html>");
                    TCari.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        TCari.setText("");
    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"3");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"1");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"2");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"6");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"4");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"5");
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String tmp = TCari.getText();
        StringBuffer sb= new StringBuffer(tmp);  
        sb.deleteCharAt(sb.length()-1);  
        String str = sb.toString();
        TCari.setText(str);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"7");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"0");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"8");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String tmp = TCari.getText();
        TCari.setText(tmp+"9");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        String norm = TCari.getText();
        if(TCari.getText().trim().equals("")){
            JOptionPane.showMessageDialog(rootPane,"<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Silahkan masukkan No.RM/KTP/Asuransi/JKN !!!</font></div></html>");
        }else if(norm.length() < 6){
            JOptionPane.showMessageDialog(rootPane,"<html><div align='center'><font size='5' face='Tahoma' color='#825082'>No RM kurang dari 6 digit. </font></div></html>");
        }
        else{
            if(Sequel.cariInteger("select count(no_rkm_medis) from pasien where no_rkm_medis=?",TCari.getText().trim())>0){
                 if(validasiregistrasi.equals("Yes")){
                     if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",TCari.getText())>0){
                         JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                     }else{
//                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                         pilih.setLocationRelativeTo(this);
//                         pilih.setPasien(TCari.getText());
//                         pilih.tampil();
//                         pilih.setVisible(true);
                           cek_pendaftaran(TCari.getText().trim());
                     }
                 }else{
//                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                     pilih.setLocationRelativeTo(this);
//                     pilih.setPasien(TCari.getText());
//                     pilih.tampil();
//                     pilih.setVisible(true);
                       cek_pendaftaran(TCari.getText().trim());
                 }  
            }else if(Sequel.cariInteger("select count(no_ktp) from pasien where no_ktp=?",TCari.getText().trim())>0){
                 if(validasiregistrasi.equals("Yes")){
                     if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",TCari.getText().trim()))>0){
                         JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                     }else{
//                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                         pilih.setLocationRelativeTo(this);
//                         pilih.setPasien(Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",TCari.getText().trim()));
//                         pilih.tampil();
//                         pilih.setVisible(true);
                     }
                 }else{
//                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                     pilih.setLocationRelativeTo(this);
//                     pilih.setPasien(Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",TCari.getText().trim()));
//                     pilih.tampil();
//                     pilih.setVisible(true);
                 }
            }else if(Sequel.cariInteger("select count(no_peserta) from pasien where no_peserta=?",TCari.getText().trim())>0){
                 if(validasiregistrasi.equals("Yes")){
                     if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",TCari.getText().trim()))>0){
                         JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
                     }else{
//                         DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                         pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                         pilih.setLocationRelativeTo(this);
//                         pilih.setPasien(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",TCari.getText().trim()));
//                         pilih.tampil();
//                         pilih.setVisible(true);
                     }
                 }else{
//                     DlgPilihPoli pilih=new DlgPilihPoli(this,true);
//                     pilih.setSize(this.getWidth()-20,this.getHeight()-70);
//                     pilih.setLocationRelativeTo(this);
//                     pilih.setPasien(Sequel.cariIsi("select pasien.no_rkm_medis from pasien where pasien.no_peserta=?",TCari.getText().trim()));
//                     pilih.tampil();
//                     pilih.setVisible(true); 
                 }
            }else{
                JOptionPane.showMessageDialog(rootPane,
                    "<html><div align='center'><font size='5' face='Tahoma' color='#825082'>Maaf, data pasien dengan No.RM/KTP/Asuransi/JKN tersebut tidak ditemukan." +
                    "<br>Bagi pasien yang baru pertama kali periksa, silahkan ke petugas pendaftaran </font></div></html>");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariKeyPressed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        welcomeScreen menuUtama = new welcomeScreen();
        menuUtama.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormUmum().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private component.Button BtnCari;
    private component.TextBox TCari;
    private component.Button btnKembali;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private component.Label jLabel1;
    private component.Label jLabel2;
    private component.Label jLabel3;
    private component.Label jLabel6;
    private component.Panel jPanel1;
    private javax.swing.JPanel jPanel2;
    private component.Panel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labelMR;
    private usu.widget.glass.PanelGlass panel1;
    // End of variables declaration//GEN-END:variables

    private void cek_pendaftaran(String noRm){
        //notifikasi pasien daftar ganda pada hari yang sama
        cek_reg_periksa= Sequel.cariIsi("SELECT reg_periksa.no_rawat FROM reg_periksa WHERE reg_periksa.tgl_registrasi=LEFT(NOW(),10) AND reg_periksa.kd_pj = '01' AND reg_periksa.no_rkm_medis=?",noRm);
        cek_booking_registrasi= Sequel.cariIsi("SELECT booking_registrasi.tanggal_periksa FROM booking_registrasi WHERE booking_registrasi.tanggal_periksa=LEFT(NOW(),10) AND booking_registrasi.kd_pj = '01' AND booking_registrasi.no_rkm_medis=?",noRm);
        cek_booking_poli = Sequel.cariIsi("SELECT booking_registrasi.kd_poli FROM booking_registrasi WHERE booking_registrasi.tanggal_periksa=LEFT(NOW(),10) AND booking_registrasi.kd_pj = '01' AND booking_registrasi.no_rkm_medis=?",noRm);
        cek_booking_kddokter = Sequel.cariIsi("SELECT booking_registrasi.kd_dokter FROM booking_registrasi WHERE booking_registrasi.tanggal_periksa=LEFT(NOW(),10) AND booking_registrasi.kd_pj = '01' AND booking_registrasi.no_rkm_medis=?",noRm);
        sisahari = Sequel.cariInteger("SELECT (90 - DATEDIFF(CURRENT_DATE,bridging_sep.tglrujukan)) AS sisahari FROM bridging_sep WHERE bridging_sep.no_rawat =?",noRm);
        if(cek_reg_periksa.equals("")){
            if(cek_booking_registrasi.equals("")){
                JOptionPane.showMessageDialog(null,"Anda belum booking melalui aplikasi, silahkan isi data setelah berikut ini.");
                DlgPilihPoli pilih=new DlgPilihPoli(this,true);
                pilih.setSize(this.getWidth()-20,this.getHeight()-70);
                pilih.setLocationRelativeTo(this);
                pilih.setPasien(noRm, "umum");
                pilih.tampil();
                pilih.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Anda sudah terdaftar. ");
                DlgRegistrasi regis=new DlgRegistrasi(null,true);
                regis.setSize(this.getWidth(),this.getHeight());
                regis.setLocationRelativeTo(this);
                //public void setPasien(String norm,String kodepoli,String kddokter)
                regis.setPasien(noRm, cek_booking_poli, cek_booking_kddokter, "false", "umum", "Anjungan");
                regis.setVisible(true);
            }
//            else if(sisahari<=15){
//                JOptionPane.showMessageDialog(null,"Masa Aktif Rujukan Tersisa : "+ sisahari +" !!!");
//            }else{
//                JOptionPane.showMessageDialog(null, "lanjutkan");
//            }
        }else{
            JOptionPane.showMessageDialog(null,"Pasien Sudah Terdaftar ..!!");
            DlgRegistrasi regis=new DlgRegistrasi(null,true);
            regis.setSize(this.getWidth(),this.getHeight());
            regis.setLocationRelativeTo(this);
            //public void setPasien(String norm,String kodepoli,String kddokter)
            cek_reg_periksa_poli = Sequel.cariIsi("SELECT reg_periksa.kd_poli FROM reg_periksa WHERE reg_periksa.tgl_registrasi=LEFT(NOW(),10) and reg_periksa.no_rkm_medis=?",noRm);
            cek_reg_periksa_kddokter = Sequel.cariIsi("SELECT reg_periksa.kd_dokter FROM reg_periksa WHERE reg_periksa.tgl_registrasi=LEFT(NOW(),10) and reg_periksa.no_rkm_medis=?",noRm);
            System.out.println("form umum noRm: "+noRm);
            System.out.println("form umum cek_booking_poli: "+cek_reg_periksa_poli);
            System.out.println("form umum cek_booking_kddokter: "+cek_reg_periksa_kddokter);
            regis.setPasien(noRm, cek_reg_periksa_poli, cek_reg_periksa_kddokter, "true", "umum", "Anjungan");
            regis.setVisible(true);
        }
        
        
    }
}

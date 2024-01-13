/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAdmin.java
 *
 * Created on 04 Des 13, 12:59:34
 */
package khanzahmsanjungan;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Kode
 */
public class DlgCetak extends javax.swing.JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String nama_instansi, alamat_instansi, kabupaten, propinsi,kontak,email,poli,
            antrian,nama,norm,dokter,no_rawat,bayar,penjab;

    /** Creates new form DlgAdmin
     * @param parent
     * @param id */
    public DlgCetak(java.awt.Frame parent, boolean id) {
        super(parent, id);
        initComponents();
        try {
            ps=koneksi.prepareStatement("select nama_instansi, alamat_instansi, kabupaten, propinsi, aktifkan, wallpaper,kontak,email,logo from setting");
            rs=ps.executeQuery();
            while(rs.next()){                
                nama_instansi=rs.getString("nama_instansi");
                alamat_instansi=rs.getString("alamat_instansi");
                kabupaten=rs.getString("kabupaten");
                propinsi=rs.getString("propinsi");
                kontak=rs.getString("kontak");
                email=rs.getString("email");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LblNoRw = new component.Label();
        jPanel1 = new component.Panel();
        jPanel4 = new component.Panel();
        jLabel4 = new component.Label();
        BtnAbout = new component.Button();
        BtnAbout4 = new component.Button();
        BtnAbout5 = new component.Button();
        BtnAbout1 = new component.Button();
        BtnAbout3 = new component.Button();
        BtnAbout2 = new component.Button();
        BtnKeluar = new component.Button();

        LblNoRw.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNoRw.setText("Norm");
        LblNoRw.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNoRw.setPreferredSize(new java.awt.Dimension(20, 14));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 255)), "::[ Proses Registrasi Berhasil, Silahkan Cetak Dokumen Yang Diperlukan !!! ]::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(160, 130, 160))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 70));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 1));

        jPanel4.setPreferredSize(new java.awt.Dimension(390, 51));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 3, 9));

        jLabel4.setPreferredSize(new java.awt.Dimension(50, 23));
        jPanel4.add(jLabel4);

        BtnAbout.setBorder(null);
        BtnAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        BtnAbout.setText("Bukti Register 1");
        BtnAbout.setBorderPainted(false);
        BtnAbout.setContentAreaFilled(false);
        BtnAbout.setFocusPainted(false);
        BtnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAbout.setIconTextGap(15);
        BtnAbout.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnAbout.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAboutActionPerformed(evt);
            }
        });
        jPanel4.add(BtnAbout);

        BtnAbout4.setBorder(null);
        BtnAbout4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        BtnAbout4.setText("Bukti Register 2");
        BtnAbout4.setBorderPainted(false);
        BtnAbout4.setContentAreaFilled(false);
        BtnAbout4.setFocusPainted(false);
        BtnAbout4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAbout4.setIconTextGap(15);
        BtnAbout4.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnAbout4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnAbout4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnAbout4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAbout4ActionPerformed(evt);
            }
        });
        jPanel4.add(BtnAbout4);

        BtnAbout5.setBorder(null);
        BtnAbout5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        BtnAbout5.setText("Bukti Register 3");
        BtnAbout5.setBorderPainted(false);
        BtnAbout5.setContentAreaFilled(false);
        BtnAbout5.setFocusPainted(false);
        BtnAbout5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAbout5.setIconTextGap(15);
        BtnAbout5.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnAbout5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnAbout5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnAbout5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAbout5ActionPerformed(evt);
            }
        });
        jPanel4.add(BtnAbout5);

        BtnAbout1.setBorder(null);
        BtnAbout1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        BtnAbout1.setText("Lembar Periksa 1");
        BtnAbout1.setBorderPainted(false);
        BtnAbout1.setContentAreaFilled(false);
        BtnAbout1.setFocusPainted(false);
        BtnAbout1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAbout1.setIconTextGap(15);
        BtnAbout1.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnAbout1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnAbout1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnAbout1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAbout1ActionPerformed(evt);
            }
        });
        jPanel4.add(BtnAbout1);

        BtnAbout3.setBorder(null);
        BtnAbout3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        BtnAbout3.setText("Lembar Periksa 2");
        BtnAbout3.setBorderPainted(false);
        BtnAbout3.setContentAreaFilled(false);
        BtnAbout3.setFocusPainted(false);
        BtnAbout3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAbout3.setIconTextGap(15);
        BtnAbout3.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnAbout3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnAbout3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnAbout3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAbout3ActionPerformed(evt);
            }
        });
        jPanel4.add(BtnAbout3);

        BtnAbout2.setBorder(null);
        BtnAbout2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        BtnAbout2.setText("Barcode Perawatan");
        BtnAbout2.setBorderPainted(false);
        BtnAbout2.setContentAreaFilled(false);
        BtnAbout2.setFocusPainted(false);
        BtnAbout2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnAbout2.setIconTextGap(15);
        BtnAbout2.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnAbout2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnAbout2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnAbout2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAbout2ActionPerformed(evt);
            }
        });
        jPanel4.add(BtnAbout2);

        BtnKeluar.setBorder(null);
        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/logout.png"))); // NOI18N
        BtnKeluar.setText("Selesai");
        BtnKeluar.setBorderPainted(false);
        BtnKeluar.setContentAreaFilled(false);
        BtnKeluar.setFocusPainted(false);
        BtnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnKeluar.setIconTextGap(15);
        BtnKeluar.setPreferredSize(new java.awt.Dimension(200, 110));
        BtnKeluar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/backup-restore.png"))); // NOI18N
        BtnKeluar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        jPanel4.add(BtnKeluar);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        BtnKeluar.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void BtnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAboutActionPerformed
        if(LblNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",nama_instansi);
            param.put("alamatrs",alamat_instansi);
            param.put("kotars",kabupaten);
            param.put("propinsirs",propinsi);
            param.put("kontakrs",kontak);
            param.put("emailrs",email);
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBuktiRegister2.jasper","report","::[ Bukti Register ]::",
                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+LblNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAboutActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        int jawab=JOptionPane.showConfirmDialog(null, "Yakin anda mau keluar dari program ini ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            Window[] wins = Window.getWindows();
            for (Window win : wins) {
                if (win instanceof JDialog) {
                    win.dispose();
                }
            }           
        }
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnAbout1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAbout1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!LblNoRw.getText().equals("")){
                Map<String, Object> param = new HashMap<>();
                param.put("poli",poli);
                param.put("antrian",antrian);
                param.put("nama",nama);
                param.put("norm",norm);                
                param.put("dokter",dokter);
                param.put("no_rawat",no_rawat);
                param.put("bayar",bayar);
                param.put("penjab",penjab);
                param.put("namars",nama_instansi);
                param.put("alamatrs",alamat_instansi);
                param.put("kotars",kabupaten);
                param.put("propinsirs",propinsi);
                param.put("kontakrs",kontak);
                param.put("emailrs",email);
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReportqry("rptLembarPeriksa.jasper","report","::[ Lembar Periksa ]::",
                        "select date_format(current_date(),'%d/%m/%Y') as sekarang",param); 
            }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAbout1ActionPerformed

    private void BtnAbout2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAbout2ActionPerformed
        if(!LblNoRw.getText().equals("")){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("nama",nama);
                param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",norm));
                param.put("norm",norm);   
                param.put("namars",nama_instansi);
                param.put("alamatrs",alamat_instansi);
                param.put("kotars",kabupaten);
                param.put("propinsirs",propinsi);
                param.put("kontakrs",kontak);
                param.put("emailrs",email);   
                Valid.MyReportqry("rptBarcodeRawat.jasper","report","::[ Barcode No.Rawat ]::",
                        "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+LblNoRw.getText()+"'",param); 
                this.setCursor(Cursor.getDefaultCursor());
            }  
    }//GEN-LAST:event_BtnAbout2ActionPerformed

    private void BtnAbout3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAbout3ActionPerformed
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(!LblNoRw.getText().equals("")){
                Map<String, Object> param = new HashMap<>();
                param.put("poli",poli);
                param.put("antrian",antrian);
                param.put("nama",nama);
                param.put("norm",norm);                
                param.put("dokter",dokter);
                param.put("no_rawat",no_rawat);
                param.put("bayar",bayar);
                param.put("penjab",penjab);
                param.put("namars",nama_instansi);
                param.put("alamatrs",alamat_instansi);
                param.put("kotars",kabupaten);
                param.put("propinsirs",propinsi);
                param.put("kontakrs",kontak);
                param.put("emailrs",email);
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReportqry("rptLembarPeriksa2.jasper","report","::[ Lembar Periksa ]::",
                        "select date_format(current_date(),'%d/%m/%Y') as sekarang",param); 
            }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAbout3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void BtnAbout4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAbout4ActionPerformed
        if(LblNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",nama_instansi);
            param.put("alamatrs",alamat_instansi);
            param.put("kotars",kabupaten);
            param.put("propinsirs",propinsi);
            param.put("kontakrs",kontak);
            param.put("emailrs",email);
            param.put("norawat",LblNoRw.getText());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReport("rptBuktiRegister3.jasper",param,"::[ Bukti Register ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAbout4ActionPerformed

    private void BtnAbout5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAbout5ActionPerformed
        if(LblNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",nama_instansi);
            param.put("alamatrs",alamat_instansi);
            param.put("kotars",kabupaten);
            param.put("propinsirs",propinsi);
            param.put("kontakrs",kontak);
            param.put("emailrs",email);
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptBuktiRegister8.jasper","report","::[ Bukti Register ]::",
                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp, "+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_peserta,pasien.no_ktp,"+
                   "pasien.jk,pasien.tgl_lahir,pasien.umur,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,"+
                   "reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab from reg_periksa inner join dokter inner join pasien "+
                   "inner join poliklinik inner join penjab on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+LblNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnAbout5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCetak dialog = new DlgCetak(new javax.swing.JFrame(), true);
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
    private component.Button BtnAbout;
    private component.Button BtnAbout1;
    private component.Button BtnAbout2;
    private component.Button BtnAbout3;
    private component.Button BtnAbout4;
    private component.Button BtnAbout5;
    private component.Button BtnKeluar;
    private component.Label LblNoRw;
    private component.Label jLabel4;
    private component.Panel jPanel1;
    private component.Panel jPanel4;
    // End of variables declaration//GEN-END:variables
    
    
    public void setPasien(String norawat,String Poli,String Antrian,String Nama, String NoRM,
            String Dokter,String Bayar, String Penjab){
        LblNoRw.setText(norawat);
        poli=Poli;
        antrian=Antrian;
        nama=Nama;
        norm=NoRM;
        dokter=Dokter;
        bayar=Bayar;
        penjab=Penjab;
    }
}

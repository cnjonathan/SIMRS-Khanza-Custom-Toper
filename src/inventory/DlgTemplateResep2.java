package inventory;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgTemplateResep2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private String aktifkanparsial="no",norm="",kddokter="",kode_pj="",norawat="",status="";
    private final Properties prop = new Properties();
    private int jmlparsial=0;
    
    /** Creates new form 
     * @param parent
     * @param modal */
    public DlgTemplateResep2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"No.Resep","Tgl.Resep","Jam Resep","No.Rawat","No.RM","Pasien","Dokter Peresep","Kode Dokter", "Template Resep"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPemisahan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemisahan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemisahan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbPemisahan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(60);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(300);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(300);
            }
        }
        tbPemisahan.setDefaultRenderer(Object.class, new WarnaTable());
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanparsial=prop.getProperty("AKTIFKANBILLINGPARSIAL");
        } catch (Exception ex) {
            aktifkanparsial="no";
        }
        
        tampil2();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        panelisi1 = new widget.panelisi();
        ChkTanggal = new widget.CekBox();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        BtnHapus = new widget.Button();
        BtnTambah = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbPemisahan = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ List Template2 Resep ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        ChkTanggal.setText("Tgl.Resep :");
        ChkTanggal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkTanggal.setName("ChkTanggal"); // NOI18N
        ChkTanggal.setOpaque(false);
        ChkTanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        ChkTanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkTanggalItemStateChanged(evt);
            }
        });
        panelisi1.add(ChkTanggal);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-08-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelisi1.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-08-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi1.add(DTPCari2);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi1.add(BtnCari);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(20, 23));
        panelisi1.add(jLabel7);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/editcopy.png"))); // NOI18N
        BtnTambah.setMnemonic('S');
        BtnTambah.setText("Copy");
        BtnTambah.setToolTipText("Alt+S");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('U');
        BtnEdit.setText("Ubah");
        BtnEdit.setToolTipText("Alt+U");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelisi1.add(BtnEdit);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
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
        panelisi1.add(BtnKeluar);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi1.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(245, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbPemisahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPemisahan.setName("tbPemisahan"); // NOI18N
        tbPemisahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemisahanMouseClicked(evt);
            }
        });
        tbPemisahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemisahanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbPemisahan);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil2();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil2();
        }else{
            Valid.pindah(evt, DTPCari1,BtnKeluar);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbPemisahanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemisahanMouseClicked
        if(tabMode.getRowCount()!=0){
            if(evt.getClickCount()==2){
                if(akses.getberi_obat()==true){
                    BtnTambahActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbPemisahanMouseClicked

    private void tbPemisahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemisahanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                if(akses.getberi_obat()==true){
                    BtnTambahActionPerformed(null);
                }                    
            }
        }
}//GEN-LAST:event_tbPemisahanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,DTPCari1,BtnTambah);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter..!!");
        }else{
            if(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih No.Resep..!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kode_pj);
                }
                if(jmlparsial>0){
                    panggilform();
                }else{
                    if(Sequel.cariRegistrasi(norawat)>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        panggilform();                             
                    }
                }                
            }
        }
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnTambahActionPerformed(null);
        }else{
           Valid.pindah(evt,DTPCari1,BtnKeluar);
        }
}//GEN-LAST:event_BtnTambahKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void ChkTanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkTanggalItemStateChanged
        tampil();
    }//GEN-LAST:event_ChkTanggalItemStateChanged

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter..!!");
        }else{
            if(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih No.Resep ..!!");
            }else {
                jmlparsial=0;
                if(aktifkanparsial.equals("yes")){
                    jmlparsial=Sequel.cariInteger("select count(set_input_parsial.kd_pj) from set_input_parsial where set_input_parsial.kd_pj=?",kode_pj);
                }
                if(jmlparsial>0){
                    panggilform2();
                }else{
                    if(Sequel.cariRegistrasi(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),3).toString())>0){
                        JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi ..!!");
                    }else{ 
                        panggilform2();                             
                    }
                }                
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        }else if(tbPemisahan.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data resep dokter..!!");
        }else{
            if(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString().equals("")){
                JOptionPane.showMessageDialog(rootPane,"Silahkan pilih No.Resep..!!");
            }else {
                Sequel.meghapus("resep_obat","no_resep",tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString()); 
                tampil2();               
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            BtnCariActionPerformed(null);
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
//            BtnCari.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            BtnKeluar.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
//            tbResep.requestFocus();
//        }
    }//GEN-LAST:event_TCariKeyPressed
    
    //    template resep
    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
//            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTemplateResep2 dialog = new DlgTemplateResep2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnTambah;
    private widget.CekBox ChkTanggal;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel21;
    private widget.Label jLabel7;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbPemisahan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{  
            if(ChkTanggal.isSelected()==true){
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,resep_obat.status as status_asal "+
                    " from resep_obat inner join reg_periksa inner join pasien inner join dokter on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where "+
                    " resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.tgl_peresepan between ? and ? and pasien.no_rkm_medis=? and resep_obat.kd_dokter=? order by resep_obat.tgl_perawatan,resep_obat.jam desc");
            }else{
                ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                    " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,resep_obat.status as status_asal "+
                    " from resep_obat inner join reg_periksa inner join pasien inner join dokter on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where "+
                    " resep_obat.tgl_peresepan<>'0000-00-00' and pasien.no_rkm_medis=? and resep_obat.kd_dokter=? order by resep_obat.tgl_perawatan,resep_obat.jam desc");
            }
            try{
                if(ChkTanggal.isSelected()==true){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,norm);
                    ps.setString(4,kddokter);
                }else{
                    ps.setString(1,norm);
                    ps.setString(2,kddokter);
                }                
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("kd_dokter")
                    });  
                    tabMode.addRow(new String[]{"","Jumlah","Satuan","Aturan Pakai","Kode/No","Nama Obat/Racikan","",""});                
                    ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                        "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                        "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new String[]{
                                "",rs2.getString("jml"),rs2.getString("kode_sat"),rs2.getString("aturan_pakai"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),"",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                            "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                            "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                            "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                            "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                            "resep_dokter_racikan.no_resep=? ");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new String[]{
                                "",rs2.getString("jml_dr"),rs2.getString("metode"),rs2.getString("aturan_pakai"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),"",""
                            });
                            ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1,rs.getString("no_resep"));
                                ps3.setString(2,rs2.getString("no_racik"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    tabMode.addRow(new String[]{
                                        "","   "+rs3.getString("jml"),"   "+rs3.getString("kode_sat"),"","   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),"","",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }                
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
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

    public void isCek(){
        BtnTambah.setEnabled(akses.getresep_dokter());
    }
    
    public void setRM(String norawat,String norm,String kodedokter,String kodepj,String status){
        this.norm=norm;
        this.status=status;
        this.norawat=norawat;
        this.kddokter=kodedokter;
        this.kode_pj=kodepj;
    }

    private void panggilform() {
        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.setNoRm(norawat,tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),7).toString(),
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),6).toString(), 
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),4).toString()+" "+
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),5).toString(), 
                kode_pj,status);
        resep.isCek();
        resep.tampilobat2(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
        resep.setVisible(true);
    }
    
    private void panggilform2() {
        DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.MatikanJam();
        resep.setNoRm(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),3).toString(),
                Valid.SetTgl2(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),1).toString()),
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),2).toString().substring(0,2),
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),2).toString().substring(3,5),
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),2).toString().substring(6,8),
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),7).toString(),
                tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),6).toString(),status);
        resep.isCek();
        resep.tampilobat(tbPemisahan.getValueAt(tbPemisahan.getSelectedRow(),0).toString());
        resep.setVisible(true);   
    }
    
//    template resep
    public void tampil2() {
        System.out.println("function tampil2");
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "+
                " if(resep_obat.tgl_perawatan='0000-00-00','Belum Terlayani','Sudah Terlayani') as status,resep_obat.status as status_asal, resep_obat.nama_template "+
                " from resep_obat inner join reg_periksa inner join pasien inner join dokter on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where "+
                " resep_obat.kd_dokter=? and resep_obat.nama_template<>'' order by resep_obat.tgl_perawatan,resep_obat.jam desc");
            try{
                ps.setString(1,kddokter);
//                ps.setString(5,"'%"+TCari.getText()+"%'");                
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_resep"),rs.getString("tgl_peresepan"),rs.getString("jam_peresepan"),rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("nm_dokter"),rs.getString("kd_dokter"), rs.getString("nama_template")
                    });
                    tabMode.addRow(new String[]{"","Jumlah","Satuan","Aturan Pakai","Kode/No","Nama Obat/Racikan","",""});                
                    ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                        "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                        "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new String[]{
                                "",rs2.getString("jml"),rs2.getString("kode_sat"),rs2.getString("aturan_pakai"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),"",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps2=koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                            "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                            "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                            "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                            "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                            "resep_dokter_racikan.no_resep=? ");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new String[]{
                                "",rs2.getString("jml_dr"),rs2.getString("metode"),rs2.getString("aturan_pakai"),"No.Racik : "+rs2.getString("no_racik"),rs2.getString("nama_racik"),"",""
                            });
                            ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1,rs.getString("no_resep"));
                                ps3.setString(2,rs2.getString("no_racik"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    tabMode.addRow(new String[]{
                                        "","   "+rs3.getString("jml"),"   "+rs3.getString("kode_sat"),"","   "+rs3.getString("kode_brng"),"   "+rs3.getString("nama_brng"),"","",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }                
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
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

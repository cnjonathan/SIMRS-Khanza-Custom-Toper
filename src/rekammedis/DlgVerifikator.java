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
import setting.*;
import fungsi.WarnaTableResumeMedisVerifikator;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.validasi2;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import surat.SuratKeteranganSehat;

/**
 *
 * @author Christopher Nanda Jonathan the greatest IT in the world ever
 */
public class DlgVerifikator extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi2 Valid=new validasi2();
    private ResultSet rs;
    private PreparedStatement ps;
    private int i=0;

    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgVerifikator(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,10);
        setSize(457,249);

        Object[] row={"No Rawat",
                      "No RM",
                      "SEP",
                      "Nama Pasien",
                      "Dokter",
                      "Diagnosa Utama",
                      "Diagnosa Sekunder 1",
                      "Diagnosa Sekunder 2",
                      "Diagnosa Sekunder 3",
                      "Diagnosa Sekunder 4",
                      "Prosedur Utama",
                      "Prosedur Sekunder 1",
                      "Prosedur Sekunder 2",
                      "Prosedur Sekunder 3",
                      "Sumber Data",
                      "Status"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbNota.setModel(tabMode);
        //tampil();
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbNota.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbNota.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 16; i++) {
            TableColumn column = tbNota.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150); //NoRawat
            }else if(i==1){
                column.setPreferredWidth(150); //NoRM
            }else if(i==2){
                column.setPreferredWidth(150); //SEP
            }else if(i==3){
                column.setPreferredWidth(150); //NamaPasien
            }else if(i==4){
                column.setPreferredWidth(150); //Dokter
            }else if(i==5){
                column.setPreferredWidth(150); //DiagnosaUtama
            }else if(i==6){
                column.setPreferredWidth(150); //DiagnosaSekunder1
            }else if(i==7){
                column.setPreferredWidth(150); //DiagnosaSekunder2
            }else if(i==8){
                column.setPreferredWidth(150); //DiagnosaSekunder3
            }else if(i==9){
                column.setPreferredWidth(150); //DiagnosaSekunder4
            }else if(i==10){
                column.setPreferredWidth(150); //ProsedurUtama
            }else if(i==11){
                column.setPreferredWidth(150); //ProsedurSekunder1
            }else if(i==12){
                column.setPreferredWidth(150); //ProsedurSekunder2
            }else if(i==13){
                column.setPreferredWidth(150); //ProsedurSekunder3
            }else if(i==14){
                column.setPreferredWidth(150); //SumberData
            }else if(i==15){
                column.setPreferredWidth(150); //Status
            }
        }

        tbNota.setDefaultRenderer(Object.class, new WarnaTableResumeMedisVerifikator());
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        sortSepAsc = new javax.swing.JMenuItem();
        sortSepDesc = new javax.swing.JMenuItem();
        sortSep = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbNota = new widget.Table();
        panelGlass7 = new widget.panelisi();
        txtNoRm = new widget.TextBox();
        jLabel5 = new widget.Label();
        txtNorawat = new widget.TextBox();
        jLabel7 = new widget.Label();
        jLabel8 = new widget.Label();
        txtSep = new widget.TextBox();
        panelGlass5 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel13 = new widget.Label();
        cmbStatusLanjut = new widget.ComboBox();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenu1.setText("Sort Data");
        jMenu1.setName("jMenu1"); // NOI18N

        sortSepAsc.setText("Sorting SEP ASC");
        sortSepAsc.setName("sortSepAsc"); // NOI18N
        sortSepAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortSepAscActionPerformed(evt);
            }
        });
        jMenu1.add(sortSepAsc);

        sortSepDesc.setText("Sorting SEP DESC");
        sortSepDesc.setName("sortSepDesc"); // NOI18N
        sortSepDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortSepDescActionPerformed(evt);
            }
        });
        jMenu1.add(sortSepDesc);

        jPopupMenu1.add(jMenu1);

        sortSep.setText("ASC");
        sortSep.setName("sortSep"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Menu Verifikator ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbNota.setAutoCreateRowSorter(true);
        tbNota.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbNota.setComponentPopupMenu(jPopupMenu1);
        tbNota.setName("tbNota"); // NOI18N
        tbNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNotaMouseClicked(evt);
            }
        });
        tbNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNotaKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbNota);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass7.setLayout(null);

        txtNoRm.setName("txtNoRm"); // NOI18N
        txtNoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoRmKeyPressed(evt);
            }
        });
        panelGlass7.add(txtNoRm);
        txtNoRm.setBounds(50, 10, 90, 24);

        jLabel5.setText("RM");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(10, 10, 30, 23);

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
        txtNorawat.setBounds(220, 10, 220, 23);

        jLabel7.setText("No Rawat");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass7.add(jLabel7);
        jLabel7.setBounds(140, 10, 70, 23);

        jLabel8.setText("SEP");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass7.add(jLabel8);
        jLabel8.setBounds(450, 10, 30, 23);

        txtSep.setName("txtSep"); // NOI18N
        txtSep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSepKeyPressed(evt);
            }
        });
        panelGlass7.add(txtSep);
        txtSep.setBounds(500, 10, 230, 24);

        internalFrame1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-01-2025" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass5.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-01-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(303, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        jLabel12.setText("Status Verifikasi :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass5.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Disusun Oleh Dokter", "Sedang Dicek Oleh Verifikator", "Telah Selesai Dicek Oleh Verifikator", "Dicek Ulang Oleh Dokter", "Proses Tanda Tangan Elektronik", "Proses Tanda Tangan Elektronik Selesai" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(200, 23));
        panelGlass5.add(cmbStatus);

        jLabel13.setText("Status Lanjut");
        jLabel13.setName("jLabel13"); // NOI18N
        jLabel13.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(jLabel13);

        cmbStatusLanjut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Ralan", "Ranap" }));
        cmbStatusLanjut.setName("cmbStatusLanjut"); // NOI18N
        cmbStatusLanjut.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass5.add(cmbStatusLanjut);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
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
        panelGlass5.add(BtnCari);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoRmKeyPressed
        
}//GEN-LAST:event_txtNoRmKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
                dispose();
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNotaMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(evt.getClickCount()==1){
                    //i=tbNota.getSelectedColumn();
                    //if(i==0){
                        txtNorawat.setText(tbNota.getValueAt(tbNota.getSelectedRow(), 0).toString());
                        txtNoRm.setText(tbNota.getValueAt(tbNota.getSelectedRow(), 1).toString());
                        txtSep.setText(tbNota.getValueAt(tbNota.getSelectedRow(), 2).toString());
                    //}
                }else if(evt.getClickCount()==2){
                    if(tbNota.getRowCount()==0){
                        JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
                    }else if(txtNoRm.getText().trim().equals("")){
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data resume medis pada table...!!!");
                        TCari.requestFocus();
                    }else{
                        if(tbNota.getSelectedRow()!= -1){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgVerifikatorProses proses=new DlgVerifikatorProses(null,false);
                            proses.emptTeks();
                            proses.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            proses.setLocationRelativeTo(internalFrame1);
                            proses.setData(
                                    tbNota.getValueAt(tbNota.getSelectedRow(), 0).toString(),
                                    tbNota.getValueAt(tbNota.getSelectedRow(), 1).toString(),
                                    tbNota.getValueAt(tbNota.getSelectedRow(), 2).toString()
                            );
                            proses.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
                System.out.println(e);
            }
        }
}//GEN-LAST:event_tbNotaMouseClicked

    private void tbNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNotaKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbNotaKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void txtNorawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNorawatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNorawatActionPerformed

    private void txtNorawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNorawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNorawatKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
//        TabRawatMouseClicked(null);
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
//            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
//            if(TabRawat.getSelectedIndex()==0){
//                tbKasirRalan.requestFocus();
//            }else if(TabRawat.getSelectedIndex()==1){
//                tbKasirRalan2.requestFocus();
//            }
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void sortSepAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortSepAscActionPerformed
        sortSep.setText("ASC");
        tampil();
    }//GEN-LAST:event_sortSepAscActionPerformed

    private void sortSepDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortSepDescActionPerformed
        sortSep.setText("DESC");
        tampil();
    }//GEN-LAST:event_sortSepDescActionPerformed

    private void txtSepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSepKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSepKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgVerifikator dialog = new DlgVerifikator(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.ComboBox cmbStatus;
    private widget.ComboBox cmbStatusLanjut;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private javax.swing.JTextField sortSep;
    private javax.swing.JMenuItem sortSepAsc;
    private javax.swing.JMenuItem sortSepDesc;
    private widget.Table tbNota;
    private widget.TextBox txtNoRm;
    private widget.TextBox txtNorawat;
    private widget.TextBox txtSep;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            String date_1 = Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00";
            String date_2 = Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59";
            String search = TCari.getText();
            String status = "";
            String status_lanjut = "";
            String query_tampil = "";
            String query_search = "";
            
            switch (cmbStatusLanjut.getSelectedItem().toString()) {
                case "Semua":
                    query_tampil = "SELECT *\n" +
                                    "FROM (\n" +
                                    "    SELECT \n" +
                                    "        resume_pasien.no_rawat, \n" +
                                    "        reg_periksa.no_rkm_medis, \n" +
                                    "        bridging_sep.no_sep, \n" +
                                    "        pasien.nm_pasien, \n" +
                                    "        resume_pasien.kd_dokter, \n" +
                                    "        dokter.nm_dokter, \n" +
                                    "        resume_pasien.diagnosa_utama, \n" +
                                    "        resume_pasien.diagnosa_sekunder, \n" +
                                    "        resume_pasien.diagnosa_sekunder2, \n" +
                                    "        resume_pasien.diagnosa_sekunder3, \n" +
                                    "        resume_pasien.diagnosa_sekunder4, \n" +
                                    "        resume_pasien.prosedur_utama, \n" +
                                    "        resume_pasien.prosedur_sekunder, \n" +
                                    "        resume_pasien.prosedur_sekunder2, \n" +
                                    "        resume_pasien.prosedur_sekunder3, \n" +
                                    "        resume_pasien.keluhan_utama, \n" +
                                    "        'rawat_jalan' AS sumber_data, \n" +
                                    "        resume_pasien.status, \n" +
                                    "        resume_pasien.created_at, \n" +
                                    "        resume_pasien.updated_at \n" +
                                    "    FROM \n" +
                                    "        resume_pasien \n" +
                                    "        LEFT JOIN reg_periksa ON resume_pasien.no_rawat = reg_periksa.no_rawat \n" +
                                    "        LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis \n" +
                                    "        LEFT JOIN dokter ON resume_pasien.kd_dokter = dokter.kd_dokter \n" +
                                    "        LEFT JOIN bridging_sep ON resume_pasien.no_rawat = bridging_sep.no_rawat \n" +
                                    "\n" +
                                    "    UNION ALL \n" +
                                    "\n" +
                                    "    SELECT \n" +
                                    "        resume_pasien_ranap.no_rawat, \n" +
                                    "        reg_periksa.no_rkm_medis, \n" +
                                    "        bridging_sep.no_sep, \n" +
                                    "        pasien.nm_pasien, \n" +
                                    "        resume_pasien_ranap.kd_dokter, \n" +
                                    "        dokter.nm_dokter, \n" +
                                    "        resume_pasien_ranap.diagnosa_utama, \n" +
                                    "        resume_pasien_ranap.diagnosa_sekunder, \n" +
                                    "        resume_pasien_ranap.diagnosa_sekunder2, \n" +
                                    "        resume_pasien_ranap.diagnosa_sekunder3, \n" +
                                    "        resume_pasien_ranap.diagnosa_sekunder4, \n" +
                                    "        resume_pasien_ranap.prosedur_utama, \n" +
                                    "        resume_pasien_ranap.prosedur_sekunder, \n" +
                                    "        resume_pasien_ranap.prosedur_sekunder2, \n" +
                                    "        resume_pasien_ranap.prosedur_sekunder3, \n" +
                                    "        resume_pasien_ranap.keluhan_utama, \n" +
                                    "        'rawat_inap' AS sumber_data, \n" +
                                    "        resume_pasien_ranap.status, \n" +
                                    "        resume_pasien_ranap.created_at, \n" +
                                    "        resume_pasien_ranap.updated_at \n" +
                                    "    FROM \n" +
                                    "        resume_pasien_ranap \n" +
                                    "        LEFT JOIN reg_periksa ON resume_pasien_ranap.no_rawat = reg_periksa.no_rawat \n" +
                                    "        LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis \n" +
                                    "        LEFT JOIN dokter ON resume_pasien_ranap.kd_dokter = dokter.kd_dokter \n" +
                                    "        LEFT JOIN bridging_sep ON resume_pasien_ranap.no_rawat = bridging_sep.no_rawat \n" +
                                    ") AS combined_data " +
                                    "WHERE "+
                                    "  created_at BETWEEN '"+date_1+"' AND '"+date_2+"' "+
                                    "";
                    query_search = "AND (\n" +
                                    "        no_rawat LIKE '%"+search+"%' \n" +
                                    "        OR no_rkm_medis LIKE '%"+search+"%' \n" +
                                    "        OR no_sep LIKE '%"+search+"%' \n" +
                                    "        OR nm_pasien LIKE '%"+search+"%' \n" +
                                    "        OR nm_dokter LIKE '%"+search+"%' \n" +
                                    "        OR diagnosa_utama LIKE '%"+search+"%' \n" +
                                    "        OR diagnosa_sekunder LIKE '%"+search+"%' \n" +
                                    "        OR diagnosa_sekunder2 LIKE '%"+search+"%' \n" +
                                    "        OR diagnosa_sekunder3 LIKE '%"+search+"%' \n" +
                                    "        OR diagnosa_sekunder4 LIKE '%"+search+"%' \n" +
                                    "        OR prosedur_utama LIKE '%"+search+"%' \n" +
                                    "        OR prosedur_sekunder LIKE '%"+search+"%' \n" +
                                    "        OR prosedur_sekunder2 LIKE '%"+search+"%' \n" +
                                    "        OR prosedur_sekunder3 LIKE '%"+search+"%' \n" +
                                    "        OR keluhan_utama LIKE '%"+search+"%'\n" +
                                    "    )";
                    switch (cmbStatus.getSelectedItem().toString()) {
                        case "Semua":
                            status = "";
                            break;
                        case "Disusun Oleh Dokter":
                            status = " AND status = 'Disusun Oleh Dokter' ";
                            break;
                        case "Sedang Dicek Oleh Verifikator":
                            status = " AND status = 'Sedang Dicek Oleh Verifikator' ";
                            break;
                        case "Telah Selesai Dicek Oleh Verifikator":
                            status = " AND status = 'Telah Selesai Dicek Oleh Verifikator' ";
                            break;
                        case "Dicek Ulang Oleh Dokter":
                            status = " AND status = 'Dicek Ulang Oleh Dokter' ";
                            break;
                        case "Proses Tanda Tangan Elektronik":
                            status = " AND status = 'Proses Tanda Tangan Elektronik' ";
                            break;
                        case "Proses Tanda Tangan Elektronik Selesai":
                            status = " AND status = 'Proses Tanda Tangan Elektronik Selesai' ";
                            break;
                        default:
                            //throw new AssertionError();
                            status = " ";
                    }
                    break;
                case "Ranap":
                    query_tampil = "SELECT \n" +
                                    "    resume_pasien_ranap.no_rawat,\n" +
                                    "    reg_periksa.no_rkm_medis,\n" +
                                    "    bridging_sep.no_sep, \n" +
                                    "    pasien.nm_pasien,\n" +
                                    "    resume_pasien_ranap.kd_dokter,\n" +
                                    "    dokter.nm_dokter,\n" +
                                    "    resume_pasien_ranap.diagnosa_utama,\n" +
                                    "    resume_pasien_ranap.diagnosa_sekunder,\n" +
                                    "    resume_pasien_ranap.diagnosa_sekunder2,\n" +
                                    "    resume_pasien_ranap.diagnosa_sekunder3,\n" +
                                    "    resume_pasien_ranap.diagnosa_sekunder4,\n" +
                                    "    resume_pasien_ranap.prosedur_utama,\n" +
                                    "    resume_pasien_ranap.prosedur_sekunder,\n" +
                                    "    resume_pasien_ranap.prosedur_sekunder2,\n" +
                                    "    resume_pasien_ranap.prosedur_sekunder3,\n" +
                                    "    resume_pasien_ranap.keluhan_utama,\n" +
                                    "    'rawat_inap' AS sumber_data,\n" +
                                    "    resume_pasien_ranap.status,\n" +
                                    "    resume_pasien_ranap.created_at,\n" +
                                    "    resume_pasien_ranap.updated_at\n" +
                                    "FROM \n" +
                                    "    resume_pasien_ranap\n" +
                                    "LEFT JOIN reg_periksa ON resume_pasien_ranap.no_rawat = reg_periksa.no_rawat\n" +
                                    "LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n" +
                                    "LEFT JOIN dokter ON resume_pasien_ranap.kd_dokter = dokter.kd_dokter "+
                                    "LEFT JOIN bridging_sep ON resume_pasien_ranap.no_rawat = bridging_sep.no_rawat \n" +
                                    "WHERE "+
                                    "  created_at BETWEEN '"+date_1+"' AND '"+date_2+"' "+
                                    "";
                    query_search = "AND (resume_pasien_ranap.no_rawat LIKE '%"+search+"%' "+
                                    "OR reg_periksa.no_rkm_medis LIKE '%"+search+"%' "+
                                    "OR pasien.nm_pasien LIKE '%"+search+"%' "+
                                    "OR dokter.nm_dokter LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.diagnosa_utama LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.diagnosa_sekunder LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.diagnosa_sekunder2 LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.diagnosa_sekunder3 LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.diagnosa_sekunder4 LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.prosedur_utama LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.prosedur_sekunder LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.prosedur_sekunder2 LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.prosedur_sekunder3 LIKE '%"+search+"%' "+
                                    "OR resume_pasien_ranap.keluhan_utama LIKE '%"+search+"%') ";
                    switch (cmbStatus.getSelectedItem().toString()) {
                        case "Semua":
                            status = "";
                            break;
                        case "Disusun Oleh Dokter":
                            status = " AND resume_pasien_ranap.status = 'Disusun Oleh Dokter' ";
                            break;
                        case "Sedang Dicek Oleh Verifikator":
                            status = " AND resume_pasien_ranap.status = 'Sedang Dicek Oleh Verifikator' ";
                            break;
                        case "Telah Selesai Dicek Oleh Verifikator":
                            status = " AND resume_pasien_ranap.status = 'Telah Selesai Dicek Oleh Verifikator' ";
                            break;
                        case "Dicek Ulang Oleh Dokter":
                            status = " AND resume_pasien_ranap.status = 'Dicek Ulang Oleh Dokter' ";
                            break;
                        case "Proses Tanda Tangan Elektronik":
                            status = " AND resume_pasien_ranap.status = 'Proses Tanda Tangan Elektronik' ";
                            break;
                        case "Proses Tanda Tangan Elektronik Selesai":
                            status = " AND resume_pasien_ranap.status = 'Proses Tanda Tangan Elektronik Selesai' ";
                            break;
                        default:
                            //throw new AssertionError();
                            status = " ";
                    }
                    break;
                case "Ralan":
                    query_tampil = "SELECT \n" +
                                    "    resume_pasien.no_rawat,\n" +
                                    "    reg_periksa.no_rkm_medis,\n" +
                                    "    bridging_sep.no_sep, \n" +
                                    "    pasien.nm_pasien,\n" +
                                    "    resume_pasien.kd_dokter,\n" +
                                    "    dokter.nm_dokter,\n" +
                                    "    resume_pasien.diagnosa_utama,\n" +
                                    "    resume_pasien.diagnosa_sekunder,\n" +
                                    "    resume_pasien.diagnosa_sekunder2,\n" +
                                    "    resume_pasien.diagnosa_sekunder3,\n" +
                                    "    resume_pasien.diagnosa_sekunder4,\n" +
                                    "    resume_pasien.prosedur_utama,\n" +
                                    "    resume_pasien.prosedur_sekunder,\n" +
                                    "    resume_pasien.prosedur_sekunder2,\n" +
                                    "    resume_pasien.prosedur_sekunder3,\n" +
                                    "    resume_pasien.keluhan_utama,\n" +
                                    "    'rawat_jalan' AS sumber_data,\n" +
                                    "    resume_pasien.status,\n" +
                                    "    resume_pasien.created_at,\n" +
                                    "    resume_pasien.updated_at\n" +
                                    "FROM \n" +
                                    "    resume_pasien\n" +
                                    "LEFT JOIN reg_periksa ON resume_pasien.no_rawat = reg_periksa.no_rawat\n" +
                                    "LEFT JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis\n" +
                                    "LEFT JOIN dokter ON resume_pasien.kd_dokter = dokter.kd_dokter "+
                                    "LEFT JOIN bridging_sep ON resume_pasien.no_rawat = bridging_sep.no_rawat \n" +
                                    "WHERE "+
                                    "  created_at BETWEEN '"+date_1+"' AND '"+date_2+"' "+
                                    "";
                    query_search = "AND (resume_pasien.no_rawat LIKE '%"+search+"%' "+
                                    "OR reg_periksa.no_rkm_medis LIKE '%"+search+"%' "+
                                    "OR pasien.nm_pasien LIKE '%"+search+"%' "+
                                    "OR dokter.nm_dokter LIKE '%"+search+"%' "+
                                    "OR resume_pasien.diagnosa_utama LIKE '%"+search+"%' "+
                                    "OR resume_pasien.diagnosa_sekunder LIKE '%"+search+"%' "+
                                    "OR resume_pasien.diagnosa_sekunder2 LIKE '%"+search+"%' "+
                                    "OR resume_pasien.diagnosa_sekunder3 LIKE '%"+search+"%' "+
                                    "OR resume_pasien.diagnosa_sekunder4 LIKE '%"+search+"%' "+
                                    "OR resume_pasien.prosedur_utama LIKE '%"+search+"%' "+
                                    "OR resume_pasien.prosedur_sekunder LIKE '%"+search+"%' "+
                                    "OR resume_pasien.prosedur_sekunder2 LIKE '%"+search+"%' "+
                                    "OR resume_pasien.prosedur_sekunder3 LIKE '%"+search+"%' "+
                                    "OR resume_pasien.keluhan_utama LIKE '%"+search+"%') ";
                    switch (cmbStatus.getSelectedItem().toString()) {
                        case "Semua":
                            status = "";
                            break;
                        case "Disusun Oleh Dokter":
                            status = " AND resume_pasien.status = 'Disusun Oleh Dokter' ";
                            break;
                        case "Sedang Dicek Oleh Verifikator":
                            status = " AND resume_pasien.status = 'Sedang Dicek Oleh Verifikator' ";
                            break;
                        case "Telah Selesai Dicek Oleh Verifikator":
                            status = " AND resume_pasien.status = 'Telah Selesai Dicek Oleh Verifikator' ";
                            break;
                        case "Dicek Ulang Oleh Dokter":
                            status = " AND resume_pasien.status = 'Dicek Ulang Oleh Dokter' ";
                            break;
                        case "Proses Tanda Tangan Elektronik":
                            status = " AND resume_pasien.status = 'Proses Tanda Tangan Elektronik' ";
                            break;
                        case "Proses Tanda Tangan Elektronik Selesai":
                            status = " AND resume_pasien.status = 'Proses Tanda Tangan Elektronik Selesai' ";
                            break;
                        default:
                            //throw new AssertionError();
                            status = " ";
                    }
                    break;
                default:
                    throw new AssertionError();
//                    status = " ";
            }
            String query_sort = " ORDER BY created_at "+sortSep.getText()+" ";
            System.out.println("query_tampil: "+query_tampil+query_search+status+status_lanjut+query_sort);
            ps=koneksi.prepareStatement(query_tampil+query_search+status+status_lanjut+query_sort);
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("no_sep") == null ? "-":rs.getString("no_sep"),
                        rs.getString("nm_pasien"),
                        rs.getString("nm_dokter"),
                        rs.getString("diagnosa_utama"),
                        rs.getString("diagnosa_sekunder"),
                        rs.getString("diagnosa_sekunder2"),
                        rs.getString("diagnosa_sekunder3"),
                        rs.getString("diagnosa_sekunder4"),
                        rs.getString("prosedur_utama"),
                        rs.getString("prosedur_sekunder"),
                        rs.getString("prosedur_sekunder2"),
                        rs.getString("prosedur_sekunder3"),
                        rs.getString("sumber_data"),
                        rs.getString("status")
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

    private void getData() {
        int row=tbNota.getSelectedRow();
        if(row!= -1){
//            String id=tabMode.getValueAt(row,0).toString();
//            String ip_address=tabMode.getValueAt(row,1).toString();
//            String workstation=tabMode.getValueAt(row,2).toString();
//            txtNorawat.setText(id);
//            txtNoNota.setText(workstation);
//            txtNoSurat.setText(ip_address);
        }
    }
    
    public void setData(String no_rawat){
        txtNorawat.setText(no_rawat);
        tampil();
    }

    public void emptTeks() {
        txtNorawat.setText("");
        txtNoRm.setText("");
    }
    
    public JTable getTable(){
        return tbNota;
    }
}

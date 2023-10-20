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


package setting;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.validasi2;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgWorkstationSetup extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi2 Valid=new validasi2();
    private ResultSet rs;
    private PreparedStatement ps;
    public DlgWorkstationConfigList workstation_list = new DlgWorkstationConfigList(null, false);
    private int pilihan=0;

    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgWorkstationSetup(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,10);
        setSize(457,249);
        
//        tabel workstation
        Object[] row={"ID",
                      "IP Address",
                      "Workstation"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbWorkstation.setModel(tabMode);
        //tampil();
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbWorkstation.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbWorkstation.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbWorkstation.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(250);
            }
        }

        tbWorkstation.setDefaultRenderer(Object.class, new WarnaTable());
        
//        tabel config
        Object[] row_config={"ID",
                             "Nama Config",
                             "Printer Sharing",
                             "Margin X",
                             "Margin Y",
                             "Width",
                             "Height"};
        tabMode2 = new DefaultTableModel(null,row_config){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbConfig.setModel(tabMode2);
        //tampil();
        tbConfig.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbConfig.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbConfig.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(50);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(250);
            }
        }

        tbConfig.setDefaultRenderer(Object.class, new WarnaTable());
        
        txtIdWorkstation.setEditable(false);
        txtWorkstation.setDocument(new batasInput((byte)30).getKata(txtWorkstation));
        txtAlamatIp.setDocument(new batasInput((byte)30).getKata(txtAlamatIp));
        
        workstation_list.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
//                if(akses.getform().equals("DlgWorkstationSetup")){
                    if(workstation_list.getTable().getSelectedRow()!= -1){  
                        txtIdConfig.setText(workstation_list.getTable().getValueAt(workstation_list.getTable().getSelectedRow(),0).toString());
                        txtNamaConfig.setText(workstation_list.getTable().getValueAt(workstation_list.getTable().getSelectedRow(),1).toString());
                    }  
                    txtPrinterSharing.requestFocus();
//                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
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
        panelGlass7 = new widget.panelisi();
        txtWorkstation = new widget.TextBox();
        jLabel4 = new widget.Label();
        txtAlamatIp = new widget.TextBox();
        jLabel5 = new widget.Label();
        txtIdWorkstation = new widget.TextBox();
        jLabel6 = new widget.Label();
        btnSearchConfig = new widget.Button();
        jLabel8 = new widget.Label();
        txtIdConfig = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        txtPrinterSharing = new widget.TextBox();
        txtNamaConfig = new widget.TextBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new widget.Label();
        txtMarginX = new widget.TextBox();
        jLabel9 = new widget.Label();
        txtMarginY = new widget.TextBox();
        jLabel13 = new widget.Label();
        txtWidth = new widget.TextBox();
        jLabel14 = new widget.Label();
        txtHeight = new widget.TextBox();
        Scroll = new widget.ScrollPane();
        tbWorkstation = new widget.Table();
        panelGlass5 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbConfig = new widget.Table();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Workstation Setup ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setMinimumSize(new java.awt.Dimension(500, 85));
        internalFrame1.setName("internalFrame1"); // NOI18N

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass7.setLayout(null);

        txtWorkstation.setHighlighter(null);
        txtWorkstation.setName("txtWorkstation"); // NOI18N
        txtWorkstation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWorkstationActionPerformed(evt);
            }
        });
        txtWorkstation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtWorkstationKeyPressed(evt);
            }
        });
        panelGlass7.add(txtWorkstation);
        txtWorkstation.setBounds(120, 40, 210, 23);

        jLabel4.setText("Nama Config");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(340, 40, 90, 23);

        txtAlamatIp.setName("txtAlamatIp"); // NOI18N
        txtAlamatIp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAlamatIpKeyPressed(evt);
            }
        });
        panelGlass7.add(txtAlamatIp);
        txtAlamatIp.setBounds(120, 70, 210, 24);

        jLabel5.setText("IP Computer");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(10, 70, 90, 23);

        txtIdWorkstation.setHighlighter(null);
        txtIdWorkstation.setName("txtIdWorkstation"); // NOI18N
        txtIdWorkstation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdWorkstationActionPerformed(evt);
            }
        });
        txtIdWorkstation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdWorkstationKeyPressed(evt);
            }
        });
        panelGlass7.add(txtIdWorkstation);
        txtIdWorkstation.setBounds(120, 10, 50, 23);

        jLabel6.setText("Printer Sharing");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass7.add(jLabel6);
        jLabel6.setBounds(340, 70, 90, 23);

        btnSearchConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSearchConfig.setMnemonic('3');
        btnSearchConfig.setToolTipText("ALt+3");
        btnSearchConfig.setName("btnSearchConfig"); // NOI18N
        btnSearchConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchConfigActionPerformed(evt);
            }
        });
        panelGlass7.add(btnSearchConfig);
        btnSearchConfig.setBounds(660, 40, 28, 20);

        jLabel8.setText("ID Config");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass7.add(jLabel8);
        jLabel8.setBounds(350, 10, 80, 23);

        txtIdConfig.setHighlighter(null);
        txtIdConfig.setName("txtIdConfig"); // NOI18N
        txtIdConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdConfigActionPerformed(evt);
            }
        });
        txtIdConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdConfigKeyPressed(evt);
            }
        });
        panelGlass7.add(txtIdConfig);
        txtIdConfig.setBounds(440, 10, 50, 23);

        jLabel10.setText("ID");
        jLabel10.setName("jLabel10"); // NOI18N
        panelGlass7.add(jLabel10);
        jLabel10.setBounds(10, 10, 90, 23);

        jLabel11.setText("Nama Workstation");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass7.add(jLabel11);
        jLabel11.setBounds(10, 40, 90, 23);

        txtPrinterSharing.setName("txtPrinterSharing"); // NOI18N
        txtPrinterSharing.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPrinterSharingKeyPressed(evt);
            }
        });
        panelGlass7.add(txtPrinterSharing);
        txtPrinterSharing.setBounds(440, 70, 210, 24);

        txtNamaConfig.setName("txtNamaConfig"); // NOI18N
        txtNamaConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaConfigKeyPressed(evt);
            }
        });
        panelGlass7.add(txtNamaConfig);
        txtNamaConfig.setBounds(440, 40, 210, 24);

        jLabel1.setText("Menggunakan double back slash");
        jLabel1.setName("jLabel1"); // NOI18N
        panelGlass7.add(jLabel1);
        jLabel1.setBounds(710, 30, 270, 20);

        jLabel2.setText("Example Hotsname: \\\\\\\\10.77.1.2\\\\Canon LBP2900");
        jLabel2.setName("jLabel2"); // NOI18N
        panelGlass7.add(jLabel2);
        jLabel2.setBounds(710, 10, 270, 20);

        jLabel7.setText("X");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass7.add(jLabel7);
        jLabel7.setBounds(710, 70, 20, 23);

        txtMarginX.setName("txtMarginX"); // NOI18N
        txtMarginX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMarginXKeyPressed(evt);
            }
        });
        panelGlass7.add(txtMarginX);
        txtMarginX.setBounds(740, 70, 40, 24);

        jLabel9.setText("Y");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass7.add(jLabel9);
        jLabel9.setBounds(790, 70, 20, 23);

        txtMarginY.setName("txtMarginY"); // NOI18N
        txtMarginY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMarginYKeyPressed(evt);
            }
        });
        panelGlass7.add(txtMarginY);
        txtMarginY.setBounds(820, 70, 40, 24);

        jLabel13.setText("W");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass7.add(jLabel13);
        jLabel13.setBounds(870, 70, 20, 23);

        txtWidth.setName("txtWidth"); // NOI18N
        txtWidth.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtWidthKeyPressed(evt);
            }
        });
        panelGlass7.add(txtWidth);
        txtWidth.setBounds(900, 70, 40, 24);

        jLabel14.setText("H");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass7.add(jLabel14);
        jLabel14.setBounds(950, 70, 20, 23);

        txtHeight.setName("txtHeight"); // NOI18N
        txtHeight.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHeightKeyPressed(evt);
            }
        });
        panelGlass7.add(txtHeight);
        txtHeight.setBounds(980, 70, 40, 24);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 300));

        tbWorkstation.setAutoCreateRowSorter(true);
        tbWorkstation.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbWorkstation.setName("tbWorkstation"); // NOI18N
        tbWorkstation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbWorkstationMouseClicked(evt);
            }
        });
        tbWorkstation.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbWorkstationKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbWorkstation);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setIconTextGap(3);
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setIconTextGap(3);
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
        panelGlass5.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setIconTextGap(3);
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
        panelGlass5.add(BtnEdit);

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

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);
        Scroll2.setPreferredSize(new java.awt.Dimension(452, 300));

        tbConfig.setAutoCreateRowSorter(true);
        tbConfig.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbConfig.setName("tbConfig"); // NOI18N
        tbConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbConfigMouseClicked(evt);
            }
        });
        tbConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbConfigKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbConfig);

        javax.swing.GroupLayout internalFrame1Layout = new javax.swing.GroupLayout(internalFrame1);
        internalFrame1.setLayout(internalFrame1Layout);
        internalFrame1Layout.setHorizontalGroup(
            internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame1Layout.createSequentialGroup()
                .addComponent(panelGlass5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(internalFrame1Layout.createSequentialGroup()
                .addComponent(Scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 1092, Short.MAX_VALUE)
                    .addComponent(panelGlass7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        internalFrame1Layout.setVerticalGroup(
            internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame1Layout.createSequentialGroup()
                .addGroup(internalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame1Layout.createSequentialGroup()
                        .addComponent(panelGlass7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                    .addComponent(Scroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelGlass5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtWorkstationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtWorkstationKeyPressed
        Valid.pindah(evt,BtnSimpan,txtAlamatIp);
}//GEN-LAST:event_txtWorkstationKeyPressed

    private void txtAlamatIpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAlamatIpKeyPressed
        Valid.pindah(evt,txtWorkstation,BtnSimpan);
}//GEN-LAST:event_txtAlamatIpKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(txtIdConfig.getText().trim().equals("")){
            Valid.textKosong(txtIdConfig,"Config");
        }else if(txtPrinterSharing.getText().trim().equals("")){
            Valid.textKosong(txtPrinterSharing,"Printer Sharing");
        }else{
            System.out.println("Menyimpan workstation: "+txtIdWorkstation.getText()+", "+txtIdConfig.getText()+", "+txtPrinterSharing.getText());
            Sequel.menyimpan("`workstation_config`(`id_workstation`, `id_workstation_config`, `sharing_printer`, `margin_x`, `margin_y`, `width`, `height`)","'"+txtIdWorkstation.getText()+"','"+txtIdConfig.getText()+"', '"+txtPrinterSharing.getText()+"', '"+txtMarginX.getText()+"', '"+txtMarginY.getText()+"', '"+txtWidth.getText()+"', '"+txtHeight.getText()+"'","Workstation Config");
            tampil_list_config_workstation();
            emptTeks();
        }
//        else if(tabMode2.getRowCount()>0){
//            JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu Admin Utama ...!!!!");
//            txtWorkstation.requestFocus();
//        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,txtAlamatIp,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode2.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            txtWorkstation.requestFocus();
        }else if(txtIdConfig.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(! txtIdConfig.getText().trim().equals("")){
            int reallyDelete = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin menghapus data tersebut?");
            if(reallyDelete == 0){
                try {
                    String id = txtIdConfig.getText();
                    Sequel.queryu("DELETE FROM `workstation_config` WHERE id_config = "+id);
                } catch (Exception e) {
                    System.out.println("Ada yang error: "+e);
                }finally{
                    tampil_list_config_workstation();
                    emptTeks();
                }   
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(txtWorkstation.getText().trim().equals("")){
            Valid.textKosong(txtWorkstation,"Workstation/Hostname");
        }else if(txtAlamatIp.getText().trim().equals("")){
            Valid.textKosong(txtAlamatIp,"Alamat IP");
        }else{
            String id = txtIdConfig.getText();
            Sequel.queryu("UPDATE `workstation_config` SET `sharing_printer`='"+txtPrinterSharing.getText()+"', `margin_x` = '"+txtMarginX.getText()+"', `margin_y` = '"+txtMarginY.getText()+"', `width` = '"+txtWidth.getText()+"', `height` = '"+txtHeight.getText()+"' WHERE `id_config`="+id);
            if(tabMode2.getRowCount()!=0){tampil_list_config_workstation();}
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data admin tidak boleh kosong ...!!!!");
            txtWorkstation.requestFocus();
        }else if(! (tabMode.getRowCount()==0)) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,BtnKeluar);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbWorkstationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbWorkstationMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
                tampil_list_config_workstation();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbWorkstationMouseClicked

    private void tbWorkstationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbWorkstationKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                    tampil_list_config_workstation();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbWorkstationKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
//        emptTeks();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void txtWorkstationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtWorkstationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWorkstationActionPerformed

    private void txtIdWorkstationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdWorkstationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdWorkstationActionPerformed

    private void txtIdWorkstationKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdWorkstationKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdWorkstationKeyPressed

    private void btnSearchConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchConfigActionPerformed
        pilihan=1;
        akses.setform("DlgWorkstationSetup");
//        if(akses.getkode().equals("Admin Utama")){
//            workstation.TCari.requestFocus();
            workstation_list.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            workstation_list.setLocationRelativeTo(internalFrame1);
            workstation_list.setVisible(true);
//        }
    }//GEN-LAST:event_btnSearchConfigActionPerformed

    private void txtIdConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdConfigActionPerformed

    private void txtIdConfigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdConfigKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdConfigKeyPressed

    private void tbConfigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbConfigMouseClicked
        // TODO add your handling code here:     
        if(tabMode2.getRowCount()!=0){
            try {
                getData_list_config_workstation();
//                tampil_list_config_workstation();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbConfigMouseClicked

    private void tbConfigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbConfigKeyPressed
        // TODO add your handling code here:
        if(tabMode2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData_list_config_workstation();
//                    tampil_list_config_workstation();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbConfigKeyPressed

    private void txtPrinterSharingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrinterSharingKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrinterSharingKeyPressed

    private void txtNamaConfigKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaConfigKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaConfigKeyPressed

    private void txtMarginXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarginXKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarginXKeyPressed

    private void txtMarginYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarginYKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarginYKeyPressed

    private void txtWidthKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtWidthKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtWidthKeyPressed

    private void txtHeightKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHeightKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHeightKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgWorkstationSetup dialog = new DlgWorkstationSetup(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.Button btnSearchConfig;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private javax.swing.JLabel jLabel2;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private widget.Table tbConfig;
    private widget.Table tbWorkstation;
    private widget.TextBox txtAlamatIp;
    private widget.TextBox txtHeight;
    private widget.TextBox txtIdConfig;
    private widget.TextBox txtIdWorkstation;
    private widget.TextBox txtMarginX;
    private widget.TextBox txtMarginY;
    private widget.TextBox txtNamaConfig;
    private widget.TextBox txtPrinterSharing;
    private widget.TextBox txtWidth;
    private widget.TextBox txtWorkstation;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
//            ps=koneksi.prepareStatement("select AES_DECRYPT(usere,'nur'),AES_DECRYPT(passworde,'windi') from admin");
              ps=koneksi.prepareStatement("SELECT * FROM `workstation`");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
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
        
//    menampilkan list config-config yang sudah tersimpan
    public void tampil_list_config_workstation() {
        Valid.tabelKosong(tabMode2);
        String id_workstation = tbWorkstation.getValueAt(tbWorkstation.getSelectedRow(), 0).toString();
        try{
//            ps=koneksi.prepareStatement("select AES_DECRYPT(usere,'nur'),AES_DECRYPT(passworde,'windi') from admin");
            String query = "SELECT wc.*, w.ip_address, w.workstation, wcl.config_name, wcl.jasper_report_name\n" +
                           "FROM workstation_config wc\n" +
                           "LEFT JOIN workstation w ON wc.id_workstation = w.id_workstation\n" +
                           "LEFT JOIN workstation_config_list wcl ON wc.id_workstation_config = wcl.id_workstation_config\n"+
                           "WHERE wc.id_workstation = '"+id_workstation+"'";
                  ps=koneksi.prepareStatement(query);
            try {
                rs=ps.executeQuery();
                boolean found = false;
                while(rs.next()){
                    found = true;
                    tabMode2.addRow(new String[]{
                        rs.getString("id_config"),
                        rs.getString("config_name"),
                        rs.getString("sharing_printer"),
                        rs.getString("margin_x"),
                        rs.getString("margin_y"),
                        rs.getString("width"),
                        rs.getString("height")
                    });
                }
                if(found == false){
                    tabMode2.addRow(new String[]{
                        "---",
                        "No Data",
                        "---"
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
        int row=tbWorkstation.getSelectedRow();
        if(row!= -1){
            String id=tabMode.getValueAt(row,0).toString();
            String ip_address=tabMode.getValueAt(row,1).toString();
            String workstation=tabMode.getValueAt(row,2).toString();
            txtIdWorkstation.setText(id);
            txtWorkstation.setText(workstation);
            txtAlamatIp.setText(ip_address);
        }
    }
    
    private void getData_list_config_workstation() {
        int row=tbConfig.getSelectedRow();
        if(row!= -1){
            String id=tabMode2.getValueAt(row,0).toString();
            String config_name=tabMode2.getValueAt(row,1).toString();
            String sharing_printer=tabMode2.getValueAt(row,2).toString();
            String x=tabMode2.getValueAt(row,3).toString();
            String y=tabMode2.getValueAt(row,4).toString();
            String w=tabMode2.getValueAt(row,5).toString();
            String h=tabMode2.getValueAt(row,6).toString();
            txtIdConfig.setText(id);
            txtNamaConfig.setText(config_name);
            txtPrinterSharing.setText(sharing_printer);
            txtMarginX.setText(x);
            txtMarginY.setText(y);
            txtWidth.setText(w);
            txtHeight.setText(h);
        }
    }

    public void emptTeks() {
        txtIdConfig.setText("");
        txtNamaConfig.setText("");
        txtPrinterSharing.setText("");
        txtNamaConfig.requestFocus();
    }
    
    public JTable getTable(){
        return tbWorkstation;
    }
}

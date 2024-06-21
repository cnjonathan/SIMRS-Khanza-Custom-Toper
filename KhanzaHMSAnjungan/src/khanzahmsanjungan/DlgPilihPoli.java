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

import component.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Kode
 */
public class DlgPilihPoli extends javax.swing.JDialog {
    private final DefaultTableModel tabmode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private Calendar cal = Calendar.getInstance();
    private int day = cal.get(Calendar.DAY_OF_WEEK);
    private String hari="";

    /** Creates new form DlgAdmin
     * @param parent
     * @param id */
    public DlgPilihPoli(java.awt.Frame parent, boolean id) {
        super(parent, id);
        initComponents();
        tabmode=new DefaultTableModel(new Object[]{"Kode","Nama Unit/Poliklinik","Baru","Lama"},0){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbAdmin.setModel(tabmode);
        tbAdmin.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 4; i++) {
            TableColumn column = tbAdmin.getColumnModel().getColumn(i);
            if(i==1){
                column.setPreferredWidth(600);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbAdmin.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));             
        
        FormBPJS bpjs=new FormBPJS(null, false);
        FormUmum umum=new FormUmum();
        
        // Add Listeners to detect user activity
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bpjs.resetTimer();
                umum.resetTimer();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                bpjs.resetTimer();
                umum.resetTimer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                requestFocusInWindow();
            }
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

        LblPenjab = new component.Label();
        jPanel1 = new component.Panel();
        jPanel2 = new component.Panel();
        jLabel2 = new component.Label();
        jLabel6 = new component.Label();
        LblNama = new component.Label();
        jLabel8 = new component.Label();
        jLabel9 = new component.Label();
        LblNoRm = new component.Label();
        jScrollPane1 = new component.ScrollPane();
        tbAdmin = new component.Table();
        jPanel4 = new component.Panel();
        jLabel3 = new component.Label();
        TCari = new component.TextBox();
        btnCari = new component.Button();
        btnSemua = new component.Button();
        btnKeluar = new component.Button();
        jLabel4 = new component.Label();

        LblPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblPenjab.setText("Norm");
        LblPenjab.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblPenjab.setPreferredSize(new java.awt.Dimension(20, 14));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 255)), "::[ Silahkan Pilih Unit/Poliklinik !!! ]::", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24), new java.awt.Color(160, 130, 160))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 70));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 1));

        jPanel2.setPreferredSize(new java.awt.Dimension(390, 84));
        jPanel2.setLayout(null);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Nama Pasien");
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 45, 120, 23);

        jLabel6.setForeground(new java.awt.Color(160, 110, 160));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText(":");
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel6);
        jLabel6.setBounds(145, 45, 20, 23);

        LblNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNama.setText("Nama");
        LblNama.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNama.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNama);
        LblNama.setBounds(165, 45, 440, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("No.RM");
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel8);
        jLabel8.setBounds(20, 15, 120, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText(":");
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(jLabel9);
        jLabel9.setBounds(145, 15, 20, 23);

        LblNoRm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblNoRm.setText("Norm");
        LblNoRm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LblNoRm.setPreferredSize(new java.awt.Dimension(20, 14));
        jPanel2.add(LblNoRm);
        LblNoRm.setBounds(165, 15, 440, 23);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

        tbAdmin.setAutoCreateRowSorter(true);
        tbAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAdminMouseClicked(evt);
            }
        });
        tbAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAdminKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbAdmin);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(390, 51));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel3.setText("Kata Kunci :");
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(110, 30));
        jPanel4.add(jLabel3);

        TCari.setPreferredSize(new java.awt.Dimension(260, 30));
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
        jPanel4.add(TCari);

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCari.setMnemonic('O');
        btnCari.setToolTipText("Alt+O");
        btnCari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCari.setIconTextGap(0);
        btnCari.setMargin(new java.awt.Insets(0, 3, 0, 0));
        btnCari.setPreferredSize(new java.awt.Dimension(30, 30));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });
        jPanel4.add(btnCari);

        btnSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        btnSemua.setMnemonic('M');
        btnSemua.setToolTipText("Alt+M");
        btnSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSemua.setMargin(new java.awt.Insets(0, 3, 0, 0));
        btnSemua.setPreferredSize(new java.awt.Dimension(30, 30));
        btnSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSemuaActionPerformed(evt);
            }
        });
        btnSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSemuaKeyPressed(evt);
            }
        });
        jPanel4.add(btnSemua);

        btnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        btnKeluar.setMnemonic('K');
        btnKeluar.setToolTipText("Alt+K");
        btnKeluar.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        btnKeluar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnKeluar.setPreferredSize(new java.awt.Dimension(30, 30));
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        btnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKeluarKeyPressed(evt);
            }
        });
        jPanel4.add(btnKeluar);

        jLabel4.setPreferredSize(new java.awt.Dimension(50, 23));
        jPanel4.add(jLabel4);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAdminKeyPressed
        if(tabmode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    DlgPilihDokter pilih=new DlgPilihDokter(null,true);
                    pilih.setSize(this.getWidth(),this.getHeight());
                    pilih.setLocationRelativeTo(this);
                    pilih.setPasien(LblNoRm.getText(),tbAdmin.getValueAt(tbAdmin.getSelectedRow(),0).toString(), LblPenjab.getText());
                    pilih.tampil();
                    pilih.setVisible(true);
                    dispose();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbAdminKeyPressed

    private void tbAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAdminMouseClicked
        if(tabmode.getRowCount()!=0){
            try {
                DlgPilihDokter pilih=new DlgPilihDokter(null,true);
                pilih.setSize(this.getWidth(),this.getHeight());
                pilih.setLocationRelativeTo(this);
                pilih.setPasien(LblNoRm.getText(),tbAdmin.getValueAt(tbAdmin.getSelectedRow(),0).toString(), LblPenjab.getText());
                pilih.tampil();
                pilih.setVisible(true);
                dispose();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbAdminMouseClicked

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        tampil();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSemuaActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_btnSemuaActionPerformed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void btnSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSemuaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnSemuaActionPerformed(null);
        }
    }//GEN-LAST:event_btnSemuaKeyPressed

    private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnCariActionPerformed(null);
        }
    }//GEN-LAST:event_btnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        TCari.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void btnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            btnKeluarActionPerformed(null);
        }
    }//GEN-LAST:event_btnKeluarKeyPressed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPilihPoli dialog = new DlgPilihPoli(new javax.swing.JFrame(), true);
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
    private component.Label LblNama;
    private component.Label LblNoRm;
    private component.Label LblPenjab;
    private component.TextBox TCari;
    private component.Button btnCari;
    private component.Button btnKeluar;
    private component.Button btnSemua;
    private component.Label jLabel2;
    private component.Label jLabel3;
    private component.Label jLabel4;
    private component.Label jLabel6;
    private component.Label jLabel8;
    private component.Label jLabel9;
    private component.Panel jPanel1;
    private component.Panel jPanel2;
    private component.Panel jPanel4;
    private component.ScrollPane jScrollPane1;
    private component.Table tbAdmin;
    // End of variables declaration//GEN-END:variables
    public void tampil(){
        Valid.tabelKosong(tabmode);
        try{
            ps=koneksi.prepareStatement(
                    "select poliklinik.kd_poli,poliklinik.nm_poli,poliklinik.registrasi,poliklinik.registrasilama "+
                    "from poliklinik inner join jadwal on poliklinik.kd_poli=jadwal.kd_poli "+
                    "where jadwal.hari_kerja=? and poliklinik.kd_poli like ? or "+
                    "jadwal.hari_kerja=? and poliklinik.nm_poli like ? group by poliklinik.kd_poli order by poliklinik.nm_poli "); 
            try{                
                if(day==1){
                    hari="AKHAD";
                }else if(day==2){
                    hari="SENIN";
                }else if(day==3){
                    hari="SELASA";
                }else if(day==4){
                    hari="RABU";
                }else if(day==5){
                    hari="KAMIS";
                }else if(day==6){
                    hari="JUMAT";
                }else if(day==7){
                    hari="SABTU";
                }
                ps.setString(1,hari);
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,hari);
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery(); 
                while(rs.next()){
                    tabmode.addRow(new Object[]{rs.getString(1),"  "+rs.getString(2),rs.getString(3),rs.getString(4)});
                }  
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                     
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
    
    public void setPasien(String norm, String penjab){
        LblNoRm.setText(norm);
        LblPenjab.setText(penjab);
        LblNama.setText(Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", norm));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPemberianObat.java
 *
 * Created on 27 Mei 10, 14:52:31
 */

package laporan;

import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.JOptionPane;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentEvent;
import keuangan.DlgPenjaminanProses;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;
import simrskhanza.DlgRawatJalan;

/**
 *
 * @author perpustakaan
 */
public class DlgDiagnosaPenyakit extends javax.swing.JDialog {
    private validasi Valid=new validasi();
    private final sekuel Sequel=new sekuel();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;

    /** Creates new form DlgPemberianObat
     * @param parent
     * @param modal */
    public DlgDiagnosaPenyakit(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));       
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
                        panelDiagnosa1.pilihTab();
                        LCount.setText(panelDiagnosa1.getRecord()+"");
                    }                        
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
                        panelDiagnosa1.pilihTab();
                        LCount.setText(panelDiagnosa1.getRecord()+"");
                    } 
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
                        panelDiagnosa1.pilihTab();
                        LCount.setText(panelDiagnosa1.getRecord()+"");
                    } 
                }
            });
        } 
        
        panelDiagnosa1.tbDiagnosaPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),4).toString());
                }                
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbDiagnosaPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbDiagnosaPasien.getValueAt(panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(),4).toString());
                } 
            }
        });
        
        panelDiagnosa1.tbTindakanPasien.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(panelDiagnosa1.tbTindakanPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),4).toString());
                }                
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        panelDiagnosa1.tbTindakanPasien.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {
                if(panelDiagnosa1.tbTindakanPasien.getSelectedRow()!= -1){
                    TNoRw.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),2).toString());
                    TNoRM.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),3).toString());
                    TPasien.setText(panelDiagnosa1.tbTindakanPasien.getValueAt(panelDiagnosa1.tbTindakanPasien.getSelectedRow(),4).toString());
                } 
            }
        });
    }

    //private DlgCariObatPenyakit dlgobtpny=new DlgCariObatPenyakit(null,false);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel14 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel19 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel17 = new widget.Label();
        Status = new widget.ComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        panelDiagnosa1 = new laporan.PanelDiagnosa();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPemeriksaanFull = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Riwayat Diagnosa & Prosedur Tindakan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass8.add(LCount);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel14.setText("Tgl.Rawat :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(63, 23));
        panelGlass9.add(jLabel14);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-09-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(DTPCari1);

        jLabel19.setText("s.d");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(18, 23));
        panelGlass9.add(jLabel19);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-09-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(DTPCari2);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(87, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(367, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 44));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(-2, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(71, 10, 140, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(213, 10, 110, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(25, 28));
        FormInput.add(TPasien);
        TPasien.setBounds(325, 10, 330, 23);

        jLabel17.setText("Status :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(667, 10, 50, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ralan", "Ranap" }));
        Status.setName("Status"); // NOI18N
        Status.setPreferredSize(new java.awt.Dimension(308, 23));
        FormInput.add(Status);
        Status.setBounds(720, 10, 108, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelDiagnosa1.setBorder(javax.swing.BorderFactory.createTitledBorder("Diagnosa"));
        panelDiagnosa1.setName("panelDiagnosa1"); // NOI18N
        jPanel1.add(panelDiagnosa1, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(java.awt.Color.white);
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("SOAPIE"));
        jPanel6.setName("jPanel6"); // NOI18N
        jPanel6.setPreferredSize(new java.awt.Dimension(2, 200));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        textPemeriksaanFull.setColumns(20);
        textPemeriksaanFull.setRows(5);
        textPemeriksaanFull.setName("textPemeriksaanFull"); // NOI18N
        jScrollPane1.setViewportView(textPemeriksaanFull);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Billing"));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 23));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 253));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel2);

        internalFrame1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
            panelDiagnosa1.pilihTab();
            LCount.setText(panelDiagnosa1.getRecord()+"");
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        panelDiagnosa1.setRM(TNoRw.getText(),"",Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
        panelDiagnosa1.pilihTab();
        LCount.setText(panelDiagnosa1.getRecord()+"");
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,TCari,panelDiagnosa1.Diagnosa);
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{ 
            panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
            panelDiagnosa1.simpan();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,panelDiagnosa1.Diagnosa,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        panelDiagnosa1.batal();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
            panelDiagnosa1.hapus();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        panelDiagnosa1.cetak();
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        panelDiagnosa1.setRM(TNoRw.getText(),"",Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
        panelDiagnosa1.pilihTab();
        LCount.setText(panelDiagnosa1.getRecord()+"");
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
        panelDiagnosa1.pilihTab();
        LCount.setText(panelDiagnosa1.getRecord()+"");
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<605){   
            panelDiagnosa1.ScrollInput.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            panelDiagnosa1.FormData.setPreferredSize(new Dimension(panelDiagnosa1.FormData.WIDTH,420));
            if(this.getWidth()<900){
                panelDiagnosa1.ScrollInput.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                panelDiagnosa1.FormData.setPreferredSize(new Dimension(890,420));
            }else{
                panelDiagnosa1.ScrollInput.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            panelDiagnosa1.ScrollInput.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<900){
                panelDiagnosa1.ScrollInput.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                panelDiagnosa1.FormData.setPreferredSize(new Dimension(890,panelDiagnosa1.FormData.HEIGHT));
            }else{
                panelDiagnosa1.ScrollInput.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDiagnosaPenyakit dialog = new DlgDiagnosaPenyakit(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    public laporan.PanelDiagnosa panelDiagnosa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JTextArea textPemeriksaanFull;
    // End of variables declaration//GEN-END:variables

    

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRM.getText());
    }


    
    public void setNoRm(String norwt, Date tgl1, Date tgl2,String status) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Status.setSelectedItem(status);
        isRawat();
        isPsien();   
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        panelDiagnosa1.setRM(TNoRw.getText(),TNoRM.getText(),Valid.SetTgl(DTPCari1.getSelectedItem()+""),Valid.SetTgl(DTPCari2.getSelectedItem()+""),Status.getSelectedItem().toString(),TCari.getText().trim());
        tampilPemeriksaanRalan();
    }
    
    
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdiagnosa_pasien());
        BtnHapus.setEnabled(akses.getdiagnosa_pasien());        
        BtnPrint.setEnabled(akses.getdiagnosa_pasien());
        panelDiagnosa1.btnTambahPenyakit.setEnabled(akses.getpenyakit());
        panelDiagnosa1.btnTambahProsedur.setEnabled(akses.geticd9());
    }

    private void tampilPemeriksaanRalan(){
        //################################
        //||                            ||
        //||       history soapie       ||
        //||                            ||
        //################################
        String query_pemeriksaan;
        int cek_nota;
        String tgl_nota;
        textPemeriksaanFull.setText("");
        String no_sep = Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat = ?;", TNoRw.getText());
        String pemeriksaan = "";
        String select = Status.getSelectedItem().toString();
        System.out.println("selected: "+select);
        switch (select) {
            case "Ralan":
                query_pemeriksaan = "SELECT \n" +
                                    "reg_periksa.no_rawat AS no_rawat_reg_periksa, \n" +
                                    "pemeriksaan_ralan.* \n" +
                                    "FROM reg_periksa \n" +
                                    "INNER JOIN pemeriksaan_ralan ON reg_periksa.no_rawat = pemeriksaan_ralan.no_rawat \n" +
                                    "WHERE reg_periksa.no_rkm_medis = ? \n" +
                                    "ORDER BY reg_periksa.no_rawat DESC \n" +
                                    "LIMIT 0,10;";
                break;
            case "Ranap":
                query_pemeriksaan = "SELECT \n" +
                                    "reg_periksa.no_rawat AS no_rawat_reg_periksa, \n" +
                                    "pemeriksaan_ranap.* \n" +
                                    "FROM reg_periksa \n" +
                                    "INNER JOIN pemeriksaan_ranap ON reg_periksa.no_rawat = pemeriksaan_ranap.no_rawat \n" +
                                    "WHERE reg_periksa.no_rkm_medis = ?\n" +
                                    "ORDER BY reg_periksa.no_rawat DESC \n" +
                                    "LIMIT 0,10;";
                break;
            default:
                throw new AssertionError();
        }
        try {
            ps = koneksi.prepareStatement(query_pemeriksaan);
            ps.setString(1, TNoRM.getText());
            rs = ps.executeQuery();
            System.out.println("query pemeriksaan: " + query_pemeriksaan + "'" + TNoRM.getText() + "'");
            while (rs.next()) {
                pemeriksaan += "Riwayat Asesmen " + rs.getString("no_rawat") + ": \n"
                        + "Subjek: " + rs.getString("keluhan") + " \n"
                        + "Objek: " + rs.getString("pemeriksaan") + " \n"
                        + "Asesmen: " + rs.getString("penilaian") + " \n"
                        + "Plan: \n" + rs.getString("rtl").trim() + " \n"
                        + "Instruksi: " + rs.getString("instruksi") + " \n"
                        + "Evaluasi: " + rs.getString("evaluasi")+ " \n\n"
                        + "##########################################\n\n";
            }
            textPemeriksaanFull.setText(pemeriksaan);
        } catch (SQLException ex) {
            Logger.getLogger(DlgRawatJalan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //################################
        //||                            ||
        //||          billing           ||
        //||                            ||
        //################################
        switch (select) {
            case "Ralan":
                cek_nota = Sequel.cariInteger("SELECT COUNT(*) FROM nota_jalan WHERE no_rawat = '"+TNoRw.getText()+"'");
                tgl_nota = Sequel.cariIsi("SELECT CONCAT(nota_jalan.tanggal, ' ', nota_jalan.jam) FROM nota_jalan WHERE nota_jalan.no_rawat = '"+TNoRw.getText()+"' ");
                break;
            case "Ranap":
                cek_nota = Sequel.cariInteger("SELECT COUNT(*) FROM nota_inap WHERE no_rawat = '"+TNoRw.getText()+"'");
                tgl_nota = Sequel.cariIsi("SELECT CONCAT(nota_inap.tanggal, ' ', nota_inap.jam) FROM nota_inap WHERE nota_inap.no_rawat = '"+TNoRw.getText()+"' ");
                break;
            default:
                throw new AssertionError();
        }
        jPanel4.removeAll();
        jPanel4.repaint();
        jPanel4.revalidate();
        
        if(cek_nota > 0){
            String query_nota = "SELECT \n" +
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
                            "  no_rawat = '"+TNoRw.getText()+"' \n" +
                            "ORDER BY \n" +
                            "  noindex ASC;";

            System.out.println("query_surat_kontrolllll: "+query_nota);
            try {
                // cari total biaya semua
                String str_totalbiaya = Sequel.cariIsi("SELECT SUM(billing.totalbiaya) AS totalbiaya FROM billing WHERE no_rawat = '"+TNoRw.getText()+"' ORDER BY noindex ASC;");
                Double totalbiaya = Double.parseDouble(str_totalbiaya == null ? "0.0" : str_totalbiaya);

                // cari PPN dari akun bayar pasien
                PreparedStatement psakunbayar=koneksi.prepareStatement(
                         "select akun_bayar.nama_bayar,akun_bayar.kd_rek,detail_nota_jalan.besar_bayar,"+
                         "akun_bayar.ppn,detail_nota_jalan.besarppn from akun_bayar inner join detail_nota_jalan "+
                         "on akun_bayar.nama_bayar=detail_nota_jalan.nama_bayar where detail_nota_jalan.no_rawat=? order by nama_bayar");
                psakunbayar.setString(1,TNoRw.getText());
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
                str_piutang = Sequel.cariIsi("SELECT detail_piutang_pasien.totalpiutang  FROM detail_piutang_pasien WHERE detail_piutang_pasien.no_rawat LIKE '"+TNoRw.getText()+"' ORDER BY detail_piutang_pasien.no_rawat DESC");
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

                Map<String, Object> parameter = new HashMap<>();  
                parameter.put("namars",akses.getnamars());
                parameter.put("alamatrs",akses.getalamatrs());
                parameter.put("kotars",akses.getkabupatenrs());
                parameter.put("propinsirs",akses.getpropinsirs());
                parameter.put("kontakrs",akses.getkontakrs());
                parameter.put("emailrs",akses.getemailrs());   
                parameter.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                parameter.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh Petugas Kasir\n"+tgl_nota);  
                parameter.put("tgl_bayar", tgl_nota);
                parameter.put("petugas", "Admin Utama");
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
                jPanel4.setLayout(new BorderLayout());
                jPanel4.add(v);
            } catch (SQLException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(DlgPenjaminanProses.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Nota belum ada");
        }
    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPemberianObat.java
 *
 * Created on 27 Mei 10, 14:52:31
 */

package simrskhanza;

import bridging.ApiSatuSehat;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kepegawaian.DlgDokter;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableCellRenderer;
import kepegawaian.DlgCariDokter;
import keuangan.DlgIDRGProses;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleExporterInputItem;
import net.sf.jasperreports.view.JRViewer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author perpustakaan
 */
public class DlgDpjp extends javax.swing.JDialog {
    private final DefaultTableModel TabModePasien;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgCariPasien pasien=new DlgCariPasien(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs;
    private int jml=0,i=0,index=0;
    private String[] kode,nama;
    private boolean[] pilih; 
    private String encounter_id="",json_encounter="",kd_kamar="", display_kamar="", kd_bangsal="",
                    nm_bangsal="", link_satu_sehat="", status_kirim_encounter="",nik_dokter="",
                    id_satu_sehat_pasien="",jenis_kelamin_satu_sehat="",kode_status_nikah_satu_sehat="",
                    display_status_nikah_satu_sehat="",nama_province_satu_sehat="",nama_city_satu_sehat="",
                    nama_district_satu_sehat="",nama_sub_district_satu_sehat="",no_ktp="",no_telp="",email="",
                    tgl_lahir="",alamat="";
    private ApiSatuSehat api=new ApiSatuSehat();

    /** Creates new form DlgPemberianObat
     * @param parent
     * @param modal */
    public DlgDpjp(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={
            "Tgl.Rawat",
            "Jam.Rawat",
            "No.Rawat",
            "No.R.M.",
            "Nama Pasien",
            "Kode Dokter",
            "Nama Dokter",
            "IHS Practitioner",
            "Status DPJP"
        };
        TabModePasien=new DefaultTableModel(null,row){
             Class[] types = new Class[] {
                java.lang.Object.class, 
                java.lang.Object.class,
                java.lang.Object.class, 
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class,
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPasien.setModel(TabModePasien);
        //tampil("");

        tbPasien.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tbPasien.setDefaultRenderer(Object.class, new WarnaTable());

        this.setLocation(8,1);
        setSize(885,674);

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCariPasien.setDocument(new batasInput((byte)20).getKata(TCariPasien));     
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        }
        
        try {
            link_satu_sehat=koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }

        ChkInput.setSelected(false);
        isForm();
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){                   
                    TCariPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),0).toString());
                } 
                TCariPasien.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){    
                        KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                        Practitioner_SatuSehat.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        if(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString().equals("null")||dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString() == null){
                            BtnSendPasienSatuSehat.setEnabled(false);
                        }else{
                            BtnSendPasienSatuSehat.setEnabled(true);
                        }
                        KdDokter.requestFocus();
                        cek_jml_dpjp();
                }
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

    //private DlgCariObatDokter dlgobtpny=new DlgCariObatDokter(null,false);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtnSeek4 = new widget.Button();
        TCariPasien = new widget.TextBox();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel14 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel13 = new widget.Label();
        Tanggal = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel11 = new widget.Label();
        IHS_SatuSehat = new widget.TextBox();
        jLabel27 = new widget.Label();
        Practitioner_SatuSehat = new widget.TextBox();
        jLabel26 = new widget.Label();
        Location_SatuSehat = new widget.TextBox();
        jLabel28 = new widget.Label();
        Patient_SatuSehat = new widget.TextBox();
        jLabel18 = new widget.Label();
        Jam = new widget.TextBox();
        KdDokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel15 = new widget.Label();
        Status = new widget.TextBox();
        jLabel20 = new widget.Label();
        StatusEncounter = new widget.TextBox();
        BtnSendPasienSatuSehat = new widget.Button();
        jLabel21 = new widget.Label();
        EncounterID = new widget.TextBox();
        BtnUpdatePasienSatuSehat = new widget.Button();
        ChkInput = new widget.CekBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        Scroll = new widget.ScrollPane();
        tbPasien = new widget.Table();
        panelSEP = new javax.swing.JPanel();

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("Alt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(130, 23));
        TCariPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPasienKeyPressed(evt);
            }
        });

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-01-2026" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel16.setText("No.RM :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(50, 23));

        jLabel19.setText("s.d");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(18, 23));

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-01-2026" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));

        jLabel14.setText("Tgl.Rawat :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(63, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Dokter Penaggung Jawab Pasien Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
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
        panelGlass8.add(BtnEdit);

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

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 137));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(-2, 12, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(81, 12, 360, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(81, 42, 360, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(25, 28));
        FormInput.add(TPasien);
        TPasien.setBounds(81, 72, 360, 23);

        jLabel13.setText("Status:");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(440, 42, 100, 23);

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        FormInput.add(Tanggal);
        Tanggal.setBounds(81, 102, 140, 23);

        jLabel17.setText("Tgl.Rawat :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(-2, 102, 80, 23);

        jLabel4.setText("Nomer RM :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(-2, 42, 80, 23);

        jLabel5.setText("Pasien :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(-2, 72, 80, 23);

        jLabel11.setText("IHS Satu Sehat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(930, 10, 120, 23);

        IHS_SatuSehat.setEditable(false);
        IHS_SatuSehat.setBackground(new java.awt.Color(204, 204, 204));
        IHS_SatuSehat.setHighlighter(null);
        IHS_SatuSehat.setName("IHS_SatuSehat"); // NOI18N
        IHS_SatuSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IHS_SatuSehatKeyPressed(evt);
            }
        });
        FormInput.add(IHS_SatuSehat);
        IHS_SatuSehat.setBounds(1050, 10, 150, 23);

        jLabel27.setText("Practitioner Satu Sehat :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(930, 40, 120, 23);

        Practitioner_SatuSehat.setEditable(false);
        Practitioner_SatuSehat.setBackground(new java.awt.Color(204, 204, 204));
        Practitioner_SatuSehat.setHighlighter(null);
        Practitioner_SatuSehat.setName("Practitioner_SatuSehat"); // NOI18N
        Practitioner_SatuSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Practitioner_SatuSehatKeyPressed(evt);
            }
        });
        FormInput.add(Practitioner_SatuSehat);
        Practitioner_SatuSehat.setBounds(1050, 40, 150, 23);

        jLabel26.setText("Location Satu Sehat :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(930, 70, 120, 23);

        Location_SatuSehat.setEditable(false);
        Location_SatuSehat.setBackground(new java.awt.Color(204, 204, 204));
        Location_SatuSehat.setHighlighter(null);
        Location_SatuSehat.setName("Location_SatuSehat"); // NOI18N
        Location_SatuSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Location_SatuSehatKeyPressed(evt);
            }
        });
        FormInput.add(Location_SatuSehat);
        Location_SatuSehat.setBounds(1050, 70, 150, 23);

        jLabel28.setText("Patient Satu Sehat :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(930, 100, 120, 23);

        Patient_SatuSehat.setEditable(false);
        Patient_SatuSehat.setBackground(new java.awt.Color(204, 204, 204));
        Patient_SatuSehat.setHighlighter(null);
        Patient_SatuSehat.setName("Patient_SatuSehat"); // NOI18N
        Patient_SatuSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Patient_SatuSehatKeyPressed(evt);
            }
        });
        FormInput.add(Patient_SatuSehat);
        Patient_SatuSehat.setBounds(1050, 100, 150, 23);

        jLabel18.setText("Jam.Rawat :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(220, 102, 80, 23);

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        FormInput.add(Jam);
        Jam.setBounds(300, 102, 140, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(550, 10, 119, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(670, 10, 209, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(880, 10, 28, 23);

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(440, 12, 100, 23);

        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(550, 40, 119, 23);

        jLabel20.setText("Status Encounter:");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(450, 100, 90, 23);

        StatusEncounter.setHighlighter(null);
        StatusEncounter.setName("StatusEncounter"); // NOI18N
        StatusEncounter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusEncounterKeyPressed(evt);
            }
        });
        FormInput.add(StatusEncounter);
        StatusEncounter.setBounds(550, 100, 170, 23);

        BtnSendPasienSatuSehat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnSendPasienSatuSehat.setMnemonic('M');
        BtnSendPasienSatuSehat.setText("Send");
        BtnSendPasienSatuSehat.setToolTipText("Alt+M");
        BtnSendPasienSatuSehat.setName("BtnSendPasienSatuSehat"); // NOI18N
        BtnSendPasienSatuSehat.setPreferredSize(new java.awt.Dimension(32, 22));
        BtnSendPasienSatuSehat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSendPasienSatuSehatActionPerformed(evt);
            }
        });
        BtnSendPasienSatuSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSendPasienSatuSehatKeyPressed(evt);
            }
        });
        FormInput.add(BtnSendPasienSatuSehat);
        BtnSendPasienSatuSehat.setBounds(730, 100, 80, 22);

        jLabel21.setText("Encounter:");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(440, 72, 100, 23);

        EncounterID.setHighlighter(null);
        EncounterID.setName("EncounterID"); // NOI18N
        EncounterID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EncounterIDKeyPressed(evt);
            }
        });
        FormInput.add(EncounterID);
        EncounterID.setBounds(550, 70, 330, 23);

        BtnUpdatePasienSatuSehat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/42a.png"))); // NOI18N
        BtnUpdatePasienSatuSehat.setMnemonic('M');
        BtnUpdatePasienSatuSehat.setText("Update");
        BtnUpdatePasienSatuSehat.setToolTipText("Alt+M");
        BtnUpdatePasienSatuSehat.setName("BtnUpdatePasienSatuSehat"); // NOI18N
        BtnUpdatePasienSatuSehat.setPreferredSize(new java.awt.Dimension(32, 22));
        BtnUpdatePasienSatuSehat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdatePasienSatuSehatActionPerformed(evt);
            }
        });
        BtnUpdatePasienSatuSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnUpdatePasienSatuSehatKeyPressed(evt);
            }
        });
        FormInput.add(BtnUpdatePasienSatuSehat);
        BtnUpdatePasienSatuSehat.setBounds(820, 100, 90, 22);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPasien.setAutoCreateRowSorter(true);
        tbPasien.setName("tbPasien"); // NOI18N
        tbPasien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPasienMouseClicked(evt);
            }
        });
        tbPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPasienKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPasien);

        jSplitPane1.setLeftComponent(Scroll);

        panelSEP.setBackground(new java.awt.Color(255, 255, 255));
        panelSEP.setName("panelSEP"); // NOI18N
        jSplitPane1.setRightComponent(panelSEP);

        internalFrame1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
//        int jml_dpjp_tersimpan = Sequel.cariInteger("SELECT COUNT(*) AS jml FROM dpjp_ranap WHERE no_rawat = '"+TNoRw.getText()+"'");
//        String status = "";
//        
//        if(jml_dpjp_tersimpan > 0){
//            status = "Utama";
//        }else{
//            status = "Pendukung";
//        }
        
        String query = "INSERT INTO dpjp_ranap (no_rawat, kd_dokter, status)\n" +
            "VALUES ('"+TNoRw.getText()+"', '"+KdDokter.getText()+"', '"+Status.getText()+"');";
        
        try {
            ps = koneksi.prepareStatement(query);
            int affected_row = ps.executeUpdate();
            if(affected_row > 0){
                BtnCariActionPerformed(evt);
                KdDokter.setText("");
                TDokter.setText("");
                Practitioner_SatuSehat.setText("");
                if(Status.getText().equals("Utama")){
                    kirim_satu_sehat_encounter();
                }
                LCount.setText(""+TabModePasien.getRowCount());
            }else{
                notif_auto_close("Kirim Encounter Rawat Inap gagal. Silahkan hubungi IT.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgDpjp.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            // Valid.pindah(evt,Dokter,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        KdDokter.setText("");
        TDokter.setText("");
        Practitioner_SatuSehat.setText("");
        ChkInput.setSelected(true);
        isForm(); 
        cek_encounter();
        cek_jml_dpjp();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbPasien.getRowCount()==0){
             notif_auto_close("Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             notif_auto_close("Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            if(Sequel.cariIsi("SELECT status FROM dpjp_ranap WHERE no_rawat='"+TNoRw.getText()+"' AND kd_dokter='"+KdDokter.getText()+"';").equals("Utama")){
                notif_auto_close("DPJP Utama tidak dapat dihapus.");
            }else{
                String query_delete = "DELETE FROM dpjp_ranap WHERE no_rawat='"+TNoRw.getText()+"' AND kd_dokter='"+KdDokter.getText()+"';";
                try {
                    ps = koneksi.prepareStatement(query_delete);
                    int updated_row = ps.executeUpdate();
                    if(updated_row > 0){
                        notif_auto_close("Hapus DPJP berhasil");
                        tampil();
                    }else{
                        notif_auto_close("Hapus DPJP tidak berhasil!");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DlgDpjp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        LCount.setText(""+TabModePasien.getRowCount());
        cek_encounter();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TabModePasien.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(TabModePasien.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptDpjp.jasper","report","::[ Data Diagnosa Pasien ]::",
                    "select reg_periksa.tgl_registrasi,dpjp_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "dpjp_ranap.kd_dokter,dokter.nm_dokter from dpjp_ranap inner join reg_periksa inner join pasien inner join dokter "+
                    "on dpjp_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and dpjp_ranap.kd_dokter=dokter.kd_dokter "+
                    "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' and reg_periksa.tgl_registrasi like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' and dpjp_ranap.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' and dokter.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCariPasien.getText()+"%' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' "+
                    "order by reg_periksa.tgl_registrasi",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
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
        TCariPasien.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TCariPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPasienKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSeek4.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            DTPCari2.requestFocus();
        }
    }//GEN-LAST:event_TCariPasienKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        pasien.emptTeks();    
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        Valid.pindah(evt,TCariPasien,DTPCari1);
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void tbPasienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPasienMouseClicked
        if(TabModePasien.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
                System.out.println("Error on click table: "+e);
            }
        }
}//GEN-LAST:event_tbPasienMouseClicked

    private void tbPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPasienKeyPressed
        if(TabModePasien.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                    System.out.println("Error on click table: "+e);
                }
            }
        }
}//GEN-LAST:event_tbPasienKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        cek_jml_dpjp();
        Sequel.cariIsi("select satu_sehat_pasien.ihs_number from satu_sehat_pasien where satu_sehat_pasien.no_rkm_medis=? ",IHS_SatuSehat,TNoRM.getText());
        Sequel.cariIsi("select satu_sehat_pasien.patient_name_satu_sehat from satu_sehat_pasien where satu_sehat_pasien.no_rkm_medis=? ",Patient_SatuSehat,TNoRM.getText());
        kd_kamar = Sequel.cariIsi("SELECT kamar_inap.kd_kamar FROM kamar_inap WHERE kamar_inap.no_rawat = ? ORDER BY kamar_inap.no_rawat, kamar_inap.tgl_masuk, kamar_inap.jam_masuk DESC", TNoRw.getText());
        kd_bangsal = Sequel.cariIsi("SELECT kamar.kd_bangsal FROM kamar WHERE kamar.kd_kamar = ? ", kd_kamar);
        nm_bangsal = Sequel.cariIsi("SELECT bangsal.nm_bangsal FROM bangsal WHERE bangsal.kd_bangsal = ? ", kd_bangsal);
        display_kamar = nm_bangsal+" Nomor "+kd_kamar;
        Sequel.cariIsi("select satu_sehat_mapping_lokasi_ranap.id_lokasi_satusehat from satu_sehat_mapping_lokasi_ranap where satu_sehat_mapping_lokasi_ranap.kd_kamar=? ",Location_SatuSehat,kd_kamar);
        // nik_dokter = Sequel.cariIsi("SELECT pegawai.no_ktp FROM pegawai WHERE pegawai.pegawai = ? ", KdDokter.getText());
        Sequel.cariIsi("select satu_sehat_practitioner.practitioner_ihs from satu_sehat_practitioner where satu_sehat_practitioner.nik=? ",Practitioner_SatuSehat,KdDokter.getText());
        Status.setEditable(false);
        IHS_SatuSehat.setEditable(false);
        Practitioner_SatuSehat.setEditable(false);
        Location_SatuSehat.setEditable(false);
        Patient_SatuSehat.setEditable(false);
        EncounterID.setText(Sequel.cariIsi("SELECT id_encounter FROM satu_sehat_encounter_imp WHERE no_rawat='"+TNoRw.getText()+"'; "));
        if(IHS_SatuSehat.getText().equals("") || IHS_SatuSehat.getText() == null){
            BtnSimpan.setEnabled(false);
            BtnDokter.setEnabled(false);
            notif_auto_close("Pasien tidak terdaftar di Satu Sehat, silahkan konfirmasi ke petugas registrasi. ");
        }
        cek_encounter();
    }//GEN-LAST:event_formWindowOpened

    private void IHS_SatuSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IHS_SatuSehatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IHS_SatuSehatKeyPressed

    private void Practitioner_SatuSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Practitioner_SatuSehatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Practitioner_SatuSehatKeyPressed

    private void Location_SatuSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Location_SatuSehatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Location_SatuSehatKeyPressed

    private void Patient_SatuSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Patient_SatuSehatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Patient_SatuSehatKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDokterActionPerformed(null);
        }else{
            
        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        akses.setform("DlgIGD");
        dokter.isCek();
        // dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusKeyPressed

    private void StatusEncounterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusEncounterKeyPressed
        
    }//GEN-LAST:event_StatusEncounterKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        try {
            String query_update = "UPDATE dpjp_ranap SET  \n" +
                    "kd_dokter = '"+KdDokter.getText()+"' \n" +
                    "WHERE no_rawat = '"+TNoRw.getText()+"' AND kd_dokter = '"+tbPasien.getValueAt(tbPasien.getSelectedRow(),5).toString()+"';";
            ps=koneksi.prepareStatement(query_update);
            int affected_row = ps.executeUpdate();
            if(affected_row > 0){
                notif_auto_close("Update data DPJP berhasil. ");
                tampil();
            }else{
                notif_auto_close("Update data DPJP gagal. Silahkan hubungi IT.");
            }
            cek_encounter();
        } catch (SQLException ex) {
            Logger.getLogger(DlgPasien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnSendPasienSatuSehatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSendPasienSatuSehatActionPerformed
        kirim_satu_sehat_encounter();
    }//GEN-LAST:event_BtnSendPasienSatuSehatActionPerformed

    private void BtnSendPasienSatuSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSendPasienSatuSehatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSendPasienSatuSehatKeyPressed

    private void EncounterIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EncounterIDKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EncounterIDKeyPressed

    private void BtnUpdatePasienSatuSehatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdatePasienSatuSehatActionPerformed
        update_satu_sehat_encounter();
    }//GEN-LAST:event_BtnUpdatePasienSatuSehatActionPerformed

    private void BtnUpdatePasienSatuSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnUpdatePasienSatuSehatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnUpdatePasienSatuSehatKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDpjp dialog = new DlgDpjp(new javax.swing.JFrame(), true);
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
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek4;
    private widget.Button BtnSendPasienSatuSehat;
    private widget.Button BtnSimpan;
    private widget.Button BtnUpdatePasienSatuSehat;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox EncounterID;
    private widget.PanelBiasa FormInput;
    private widget.TextBox IHS_SatuSehat;
    private widget.TextBox Jam;
    private widget.TextBox KdDokter;
    private widget.Label LCount;
    private widget.TextBox Location_SatuSehat;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Patient_SatuSehat;
    private widget.TextBox Practitioner_SatuSehat;
    private widget.ScrollPane Scroll;
    private widget.TextBox Status;
    private widget.TextBox StatusEncounter;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TDokter;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JPanel panelSEP;
    private widget.Table tbPasien;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(TabModePasien);
        tampil_bukti_pelayanan();
        try{            
            ps2=koneksi.prepareStatement("select \n" +
                                        "  reg_periksa.tgl_registrasi, \n" +
                                        "  reg_periksa.jam_reg, \n" +
                                        "  dpjp_ranap.no_rawat, \n" +
                                        "  reg_periksa.no_rkm_medis, \n" +
                                        "  pasien.nm_pasien, \n" +
                                        "  dpjp_ranap.kd_dokter, \n" +
                                        "  dpjp_ranap.status, \n" +
                                        "  dokter.nm_dokter,\n" +
                                        "  satu_sehat_practitioner.practitioner_ihs\n" +
                                        "from \n" +
                                        "  dpjp_ranap \n" +
                                        "  inner join reg_periksa on dpjp_ranap.no_rawat = reg_periksa.no_rawat \n" +
                                        "  inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis \n" +
                                        "  left join dokter on dpjp_ranap.kd_dokter = dokter.kd_dokter\n" +
                                        "  left join satu_sehat_practitioner on dpjp_ranap.kd_dokter = satu_sehat_practitioner.nik\n" +
                                        "where \n" +
                                        "  dpjp_ranap.no_rawat = '"+TCari.getText().trim()+"' \n" +
                                        "order by \n" +
                                        "  reg_periksa.tgl_registrasi");
            try {
                System.out.println("query dpjp: "+ps2.toString());
                rs=ps2.executeQuery();
                while(rs.next()){
                    TabModePasien.addRow(new Object[]{
                        rs.getString("tgl_registrasi"),
                        rs.getString("jam_reg"),
                        rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),
                        rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"),
                        rs.getString("practitioner_ihs") == null ? "":rs.getString("practitioner_ihs"),
                        rs.getString("status")
                    });
                }
                
                setTableAutoFit();
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }                            
            LCount.setText(""+TabModePasien.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        cek_jml_dpjp();
    }

    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=? ",TPasien,TNoRM.getText());
    }


    private void getData() {
        if(tbPasien.getSelectedRow()!= -1){
            Tanggal.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),0).toString()); //tgl_registrasi
            Jam.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),1).toString()); //jam_reg
            TNoRw.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),2).toString()); //no_rawat
            TNoRM.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),3).toString()); //no_rkm_medis
            TPasien.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),4).toString()); //nm_pasien
            KdDokter.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),5).toString()); //kd_dokter
            TDokter.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),6).toString()); //nm_dokter
            Practitioner_SatuSehat.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),7).toString()); //practitioner
            Status.setText(tbPasien.getValueAt(tbPasien.getSelectedRow(),8).toString()); //status
            System.out.println("Status: "+tbPasien.getValueAt(tbPasien.getSelectedRow(),8).toString());
            
            switch (tbPasien.getValueAt(tbPasien.getSelectedRow(),8).toString()) {
                case "Utama":
                    System.out.println("Utama");
                    BtnHapus.setEnabled(false);
                    BtnEdit.setEnabled(true);
                    if(tbPasien.getValueAt(tbPasien.getSelectedRow(),7).toString().equals("") || tbPasien.getValueAt(tbPasien.getSelectedRow(),7) == null){
                        BtnSendPasienSatuSehat.setEnabled(false);
                        BtnUpdatePasienSatuSehat.setEnabled(false);
                    }else{
                        cek_encounter();
                    }
                    break;
                case "Pendukung":
                    System.out.println("Pendukung");
                    BtnHapus.setEnabled(true);
                    BtnEdit.setEnabled(true);
                    BtnSendPasienSatuSehat.setEnabled(false);
                    BtnUpdatePasienSatuSehat.setEnabled(false);
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2,String status, String jam_rawat) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Tanggal.setText(status);
        Jam.setText(jam_rawat);
        isRawat();
        isPsien();   
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,158));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getdpjp_ranap());
        BtnHapus.setEnabled(akses.getdpjp_ranap());
        // btnTarif.setEnabled(akses.getdokter());
        BtnPrint.setEnabled(akses.getdpjp_ranap());
        
    }

    private void setTableAutoFit() {
        // table autofit
        for (int col = 0; col < tbPasien.getColumnCount(); col++) {
            TableColumn column = tbPasien.getColumnModel().getColumn(col);
            int maxWidth = 50; // minimal lebar kolom

            for (int row = 0; row < tbPasien.getRowCount(); row++) {
                TableCellRenderer renderer = tbPasien.getCellRenderer(row, col);
                Component comp = tbPasien.prepareRenderer(renderer, row, col);
                maxWidth = Math.max(comp.getPreferredSize().width + 10, maxWidth);
            }

            column.setPreferredWidth(maxWidth);
        }
    }
    
    private void notif_auto_close(String message){
        // Membuat JPanel
        JPanel panel = new JPanel();

        // Menampilkan message dialog dengan JOptionPane
        JOptionPane optionPane = new JOptionPane("<html><div align='center'><font size='5' face='Tahoma' color='#825082'>"+message+"</font></div></html>",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

        // Membuat JDialog dari JOptionPane
        JDialog dialog = optionPane.createDialog(panel, "Pemberitahuan");

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
    }

    private void kirim_satu_sehat_encounter() {
        // kirim satu sehat encounter
        // cek dulu di encounter apakah sudah ada
        encounter_id = Sequel.cariIsi("SELECT satu_sehat_encounter_imp.id_encounter FROM satu_sehat_encounter_imp WHERE satu_sehat_encounter_imp.no_rawat = ?", TNoRw.getText());
        if(encounter_id.equals("")){
            // jika belum ada kirim ke encounter satu sehat
            try {
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                MediaType mediaType = MediaType.parse("application/json");
                json_encounter = "{\n" +
                                    "    \"resourceType\": \"Encounter\",\n" +
                                    "    \"status\": \"arrived\",\n" +
                                    "    \"class\": {\n" +
                                    "        \"system\": \"http://terminology.hl7.org/CodeSystem/v3-ActCode\",\n" +
                                    "        \"code\": \"IMP\",\n" +
                                    "        \"display\": \"inpatient encounter\"\n" +
                                    "    },\n" +
                                    "    \"subject\": {\n" +
                                    "        \"reference\": \"Patient/"+IHS_SatuSehat.getText()+"\",\n" +
                                    "        \"display\": \""+Patient_SatuSehat.getText()+"\"\n" +
                                    "    },\n" +
                                    "    \"participant\": [\n" +
                                    "        {\n" +
                                    "            \"type\": [\n" +
                                    "                {\n" +
                                    "                    \"coding\": [\n" +
                                    "                        {\n" +
                                    "                            \"system\": \"http://terminology.hl7.org/CodeSystem/v3-ParticipationType\",\n" +
                                    "                            \"code\": \"ATND\",\n" +
                                    "                            \"display\": \"attender\"\n" +
                                    "                        }\n" +
                                    "                    ]\n" +
                                    "                }\n" +
                                    "            ],\n" +
                                    "            \"individual\": {\n" +
                                    "                \"reference\": \"Practitioner/"+Practitioner_SatuSehat.getText()+"\",\n" +
                                    "                \"display\": \""+TDokter.getText()+"\"\n" +
                                    "            }\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "    \"period\": {\n" +
                                    "        \"start\": \""+Tanggal.getText()+"T"+Jam.getText()+"+07:00\"\n" +
                                    "    },\n" +
                                    "    \"location\": [\n" +
                                    "        {\n" +
                                    "            \"location\": {\n" +
                                    "                \"reference\": \"Location/"+Location_SatuSehat.getText()+"\",\n" +
                                    "                \"display\": \""+display_kamar+"\"\n" +
                                    "            }\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "    \"statusHistory\": [\n" +
                                    "        {\n" +
                                    "            \"status\": \"arrived\",\n" +
                                    "            \"period\": {\n" +
                                    "                \"start\": \""+Tanggal.getText()+"T"+Jam.getText()+"+07:00\"\n" +
                                    "            }\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "    \"serviceProvider\": {\n" +
                                    "        \"reference\": \"Organization/"+koneksiDB.IDSATUSEHAT()+"\"\n" +
                                    "    },\n" +
                                    "    \"identifier\": [\n" +
                                    "        {\n" +
                                    "            \"system\": \"http://sys-ids.kemkes.go.id/encounter/dd3089d9-ee88-456a-8fb7-b9b97eb80299\",\n" +
                                    "            \"value\": \""+IHS_SatuSehat.getText()+"\"\n" +
                                    "        }\n" +
                                    "    ]\n" +
                                    "}";
                RequestBody body = RequestBody.create(mediaType,json_encounter);
                System.out.println("JSON Encounter: "+json_encounter);
                Request request = new Request.Builder()
                  .url(link_satu_sehat+"/Encounter")
                  .method("POST", body)
                  .addHeader("Content-Type", "application/json")
                  .addHeader("Authorization", "Bearer "+api.TokenSatuSehat()+"")
                  .build();
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    String responseBody = response.body().string(); // dari OkHttp
                    System.out.println("Response: "+responseBody);
                    JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
                    String id_encounter = root.get("id").getAsString();
                    String query_simpan_encounter = "INSERT INTO satu_sehat_encounter_imp(\n" +
                                                    "  no_rawat, \n" +
                                                    "  id_encounter \n" +
                                                    ") \n" +
                                                    "VALUES \n" +
                                                    "  (\n" +
                                                    "    '"+TNoRw.getText()+"', \n" +
                                                    "    '"+id_encounter+"' \n" +
                                                    "  );";
                    ps=koneksi.prepareStatement(query_simpan_encounter);
                    ps.executeUpdate();
                    status_kirim_encounter = "Berhasil";
                    notif_auto_close("Kirim Encounter Rawat Inap berhasil.");
                    EncounterID.setText(id_encounter);
                }else{
                    System.out.println("Ada error");
                    System.out.println("Error: "+response.body().string());
                    status_kirim_encounter = "Tidak berhasil";
                    notif_auto_close("Kirim Encounter Rawat Inap gagal. Silahkan hubungi IT.");
                }
                StatusEncounter.setText(status_kirim_encounter);
            } catch (IOException ex) {
                Logger.getLogger(DlgReg.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DlgReg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            // jika sudah ada tidak perlu kirim
            notif_auto_close("Encounter Rawat Inap sudah pernah terkirim.");
            System.out.println("Encounter ID: "+encounter_id);
            StatusEncounter.setText(encounter_id);
        }
        // kosongkan lagi encounter id nya
        encounter_id = "";
        cek_encounter();
    }
    
    private void update_satu_sehat_encounter() {
        // kirim satu sehat encounter
        // cek dulu di encounter apakah sudah ada
        encounter_id = Sequel.cariIsi("SELECT satu_sehat_encounter_imp.id_encounter FROM satu_sehat_encounter_imp WHERE satu_sehat_encounter_imp.no_rawat = ?", TNoRw.getText());
        if(!encounter_id.equals("")){
            // jika sudah ada update ke encounter satu sehat
            try {
                OkHttpClient client = new OkHttpClient();

                String json_patch = "[{\"op\":\"replace\",\"path\":\"/participant/0/individual/reference\",\"value\":\"Practitioner/" + Practitioner_SatuSehat.getText() + "\"},"
                                  + "{\"op\":\"replace\",\"path\":\"/participant/0/individual/display\",\"value\":\"" + TDokter.getText() + "\"}]";

                RequestBody body = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return MediaType.parse("application/json-patch+json");
                    }

                    @Override
                    public void writeTo(okio.BufferedSink sink) throws IOException {
                        sink.writeUtf8(json_patch);
                    }
                };

                Request request = new Request.Builder()
                    .url(link_satu_sehat + "/Encounter/" + EncounterID.getText())
                    .patch(body)
                    .addHeader("Authorization", "Bearer " + api.TokenSatuSehat())
                    .build();

                System.out.println("PATCH BODY:\n" + json_patch);
                System.out.println("Headers:\n" + request.headers());

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                System.out.println("Response code: " + response.code());
                System.out.println("Response body: " + responseBody);


                if(response.isSuccessful()){
                    System.out.println("Response: "+responseBody);
                    JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();
                    String id_encounter = root.get("id").getAsString();
                    status_kirim_encounter = "Update Berhasil";
                    notif_auto_close("Update Encounter Rawat Inap berhasil.");
                    EncounterID.setText(id_encounter);
                }else{
                    System.out.println("Ada error");
                    System.out.println("Error: "+responseBody);
                    status_kirim_encounter = "Update Tidak berhasil";
                    notif_auto_close("Update Encounter Rawat Inap gagal. Silahkan hubungi IT.");
                }
                StatusEncounter.setText(status_kirim_encounter);
            } catch (IOException ex) {
                Logger.getLogger(DlgReg.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            // jika sudah ada tidak perlu kirim
            notif_auto_close("Encounter Rawat Inap sudah pernah ter-update.");
            System.out.println("Encounter ID: "+encounter_id);
            StatusEncounter.setText(encounter_id);
        }
        // kosongkan lagi encounter id nya
        encounter_id = "";
    }

    private void cek_jml_dpjp() {
        int cek_jml_dpjp = Sequel.cariInteger("SELECT COUNT(*) AS jml FROM dpjp_ranap WHERE no_rawat = '"+TNoRw.getText()+"'");
        if(cek_jml_dpjp > 0){
            Status.setText("Pendukung");
        }else if(cek_jml_dpjp == 0){
            Status.setText("Utama");
        }
    }
    
    private void cek_encounter(){
        if(EncounterID.getText().equals("") || EncounterID.getText() == null){
            StatusEncounter.setText("Belum Terkirim");
            if(Practitioner_SatuSehat.getText().equals("") || Practitioner_SatuSehat.getText() == null){
                BtnSendPasienSatuSehat.setEnabled(false);
                BtnUpdatePasienSatuSehat.setEnabled(false);
            }else{
                BtnSendPasienSatuSehat.setEnabled(true);
                BtnUpdatePasienSatuSehat.setEnabled(false);
            }
        }else{
            StatusEncounter.setText("Sudah Terkirim");
            if(Practitioner_SatuSehat.getText().equals("") || Practitioner_SatuSehat.getText() == null){
                BtnSendPasienSatuSehat.setEnabled(false);
                BtnUpdatePasienSatuSehat.setEnabled(false);
            }else{
                BtnSendPasienSatuSehat.setEnabled(false);
                BtnUpdatePasienSatuSehat.setEnabled(true);
            }
        }
    }
    
    private void tampil_bukti_pelayanan() {
        panelSEP.removeAll();
        panelSEP.repaint();
        panelSEP.revalidate();
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
                // " bridging_sep.tglsep between ? and ? and\n" +
                "  bridging_sep.no_rawat = ? \n" +
                "order by \n" +
                "  bridging_sep.tglsep";

        PreparedStatement pssep;
        try {
            pssep = koneksi.prepareStatement(query_data_sep);
            pssep.setString(1, TNoRw.getText());

            ResultSet rssep = pssep.executeQuery();
            rssep.last();
            int size = rssep.getRow();
            rssep.beforeFirst();

            System.out.println("Jumlah data SEP: " + size);
            if (rssep.next()) {
                System.out.println("Data SEP: " + rssep.getString(1));

                no_sep = rssep.getString(1);

                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("emailrs", akses.getemailrs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("penanggung",
                        Sequel.cariIsi("select penjab.png_jawab from penjab where penjab.kd_pj=?",
                                Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat=?",
                                        TNoRw.getText())));
                param.put("propinsirs", akses.getpropinsirs());
                finger = Sequel.cariIsi(
                        "select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",
                        Sequel.cariIsi("SELECT kd_dokter FROM maping_dokter_dpjpvclaim WHERE kd_dokter_bpjs = '"
                                + rssep.getString("kddpjp") + "'"));
                param.put("finger",
                        "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs()
                                + "\nDitandatangani secara elektronik oleh " + rssep.getString("nmdpdjp") + "\nID "
                                + (finger.equals("") ? Sequel.cariIsi(
                                        "SELECT kd_dokter FROM maping_dokter_dpjpvclaim WHERE kd_dokter_bpjs = '"
                                                + rssep.getString("kddpjp") + "'")
                                        : finger)
                                + "\n");
                param.put("logo", Sequel.cariGambar("select setting.logo from setting"));
                param.put("logo_bpjs", Sequel.cariGambar("select gambar.bpjs from gambar"));
                param.put("no_sep", no_sep);
                param.put("no_kartu",
                        Sequel.cariIsi("select bridging_sep.no_kartu from bridging_sep where bridging_sep.no_rawat=?",
                                TNoRw.getText()));
                param.put("prb", Sequel.cariIsi("select bpjs_prb.prb from bpjs_prb where bpjs_prb.no_sep=?", no_sep));
                param.put("noreg",
                        Sequel.cariIsi("SELECT no_reg FROM reg_periksa WHERE no_rawat=?", TNoRw.getText()));
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
                } else {
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

                Properties systemProp = System.getProperties();

                // Ambil current dir
                String currentDir = systemProp.getProperty("user.dir");
                System.out.println("currentDir: " + currentDir);

                File dir = new File(currentDir);

                File fileRpt;
                String fullPath = "";
                if (dir.isDirectory()) {
                    String[] isiDir = dir.list();
                    for (String iDir : isiDir) {
                        String path = currentDir + File.separatorChar + iDir + File.separatorChar
                                + "rptSEP.jasper";
                        System.out.println("fullPath: " + path);
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

                JasperPrint jprint = JasperFillManager.fillReport(fullPath, param, rsdt);
                JRViewer v = new JRViewer(jprint);
                panelSEP.setLayout(new BorderLayout());
                panelSEP.add(v);
            } else {
                JOptionPane.showMessageDialog(null, "SEP Tidak ada data");
                JLabel label = new JLabel();
                label.setText("SEP Tidak ada data");
                panelSEP.setLayout(new FlowLayout());
                panelSEP.add(label);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DlgDpjp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(DlgDpjp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

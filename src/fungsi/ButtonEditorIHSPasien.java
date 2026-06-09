/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import simrskhanza.DlgPasien;
import simrskhanza.DlgReg;

/**
 *
 * @author Victus
 */
public class ButtonEditorIHSPasien extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean clicked;
    private JTable table;
    private DefaultTableModel model;
    private DlgPasien pasien=new DlgPasien(null,false);
    private DlgReg reg=new DlgReg(null,false);

    public ButtonEditorIHSPasien(JCheckBox checkBox, DefaultTableModel model, JTable table) {
        super(checkBox);
        this.table = table;
        this.model = model;

        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        label = (value == null) ? "Error" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int colIndex = table.getColumnModel().getColumnIndex("No.RM");
                String no_rkm_medis = table.getValueAt(selectedRow, colIndex).toString();

                // Lakukan aksi dengan no_rkm_medis
                System.out.println("Pasien terpilih: " + no_rkm_medis);

                // Contoh: buka dialog pasien
                pasien.edit_pasien(no_rkm_medis);
                pasien.setSize(reg.internalFrame1.getWidth() - 20, reg.internalFrame1.getHeight() - 20);
                pasien.setLocationRelativeTo(reg.internalFrame1);
                pasien.setVisible(true);
            }
            // pasien.emptTeks();
            // pasien.isCek();
            // pasien.edit_pasien(no_mr);
            // pasien.setSize(reg.internalFrame1.getWidth()-20,reg.internalFrame1.getHeight()-20);
            // pasien.setLocationRelativeTo(reg.internalFrame1);
            // pasien.setVisible(true);
        }
        clicked = false;
        return label;
    }

    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

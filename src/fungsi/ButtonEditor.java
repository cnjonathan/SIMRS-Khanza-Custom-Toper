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

/**
 *
 * @author Victus
 */
public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean clicked;
    private JTable table;
    private DefaultTableModel model;

    public ButtonEditor(JCheckBox checkBox, DefaultTableModel model, JTable table) {
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
        label = (value == null) ? "Delete" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            int row = table.getSelectedRow();
            // Proses hapus row
            model.removeRow(row);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fungsi;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Victus
 */
public class ButtonRendererRegPeriksa extends JButton implements TableCellRenderer {
    private String nama_button="";
    
    public ButtonRendererRegPeriksa(String button) {
        setOpaque(true);
        this.nama_button=button;
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? nama_button : value.toString());
        if(value == null){
            setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png")));
        }else{
            setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png")));
        }
        return this;
    }
}

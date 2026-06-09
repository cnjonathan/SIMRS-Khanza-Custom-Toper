/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTableIDRG extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        }
        
        if(table.getValueAt(row,14) == null){
            //component.setBackground(new Color(255,243,109));
            component.setBackground(new Color(255, 0, 0));
            component.setForeground(new Color(255, 255, 255));
        }else if(table.getValueAt(row,14).toString().equals("Sudah Coding") && table.getValueAt(row,15).toString().equals("Belum Compile")){
            component.setBackground(new Color(252, 227, 138)); //rgb(252, 227, 138)
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,14).toString().equals("Belum Coding") && table.getValueAt(row,15).toString().equals("Belum Compile")){
            component.setBackground(new Color(243, 129, 129));
            component.setForeground(new Color(0,0,0));
        }else if(table.getValueAt(row,14).toString().equals("Belum Coding") && table.getValueAt(row,15).toString().equals("Sudah Compile")){
            component.setBackground(new Color(0, 0, 0)); //rgb(0, 0, 0)
            component.setForeground(new Color(255,255,255));
        }else if(table.getValueAt(row,14).toString().equals("Sudah Coding") && table.getValueAt(row,15).toString().equals("Sudah Compile")){
            component.setBackground(new Color(149, 225, 211)); //rgb(149, 225, 211)
            component.setForeground(new Color(0,0,0));
        }
        
        return component;
    }

}

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
public class WarnaTablePenjaminan extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        if(table.getValueAt(row,8).toString().equals("Sudah")){
            //component.setBackground(new Color(200,0,0));
            component.setBackground(new Color(240,128,128));
            component.setForeground(new Color(255,230,230));
        }else if(table.getValueAt(row,8).toString().equals("Batal")){
            //component.setBackground(new Color(255,243,109));
            component.setBackground(new Color(240,230,140));
            component.setForeground(new Color(120,110,50));
        }
        
        return component;
    }

}

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
public class WarnaTableResumeMedisVerifikator extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        }
        
        if(table.getValueAt(row,15) == null){
            component.setBackground(new Color(255,0,0)); //red
            component.setForeground(new Color(255, 255, 255)); //white
        }else if(table.getValueAt(row,15).toString().equals("Disusun Oleh Dokter")){
            component.setBackground(new Color(0,255,0)); //Hijau lime
            component.setForeground(new Color(0, 0, 0)); //black
        }else if(table.getValueAt(row,15).toString().equals("Sedang Dicek Oleh Verifikator")){
            component.setBackground(new Color(0,0,255)); //blue
            component.setForeground(new Color(255, 255, 255)); //white
        }else if(table.getValueAt(row,15).toString().equals("Telah Selesai Dicek Oleh Verifikator")){
            component.setBackground(new Color(255,255,0)); //yellow
            component.setForeground(new Color(0, 0, 0)); //black
        }else if(table.getValueAt(row,15).toString().equals("Dicek Ulang Oleh Dokter")){
            component.setBackground(new Color(0,255,255)); //cyan
            component.setForeground(new Color(0, 0, 0)); //black
        }else if(table.getValueAt(row,15).toString().equals("Proses Tanda Tangan Elektronik")){
            component.setBackground(new Color(255,0,255)); //magenta
            component.setForeground(new Color(0, 0, 0)); //black
        }else if(table.getValueAt(row,15).toString().equals("Proses Tanda Tangan Elektronik Selesai")){
            component.setBackground(new Color(0,0,0)); //black
            component.setForeground(new Color(255, 255, 255)); //white
        }
        
        return component;
    }

}

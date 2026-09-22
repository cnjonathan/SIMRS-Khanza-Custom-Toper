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
public class WarnaTableKasirRalan extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(50,50,50));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(50,50,50));
        } 
        
        String stts = table.getValueAt(row, 13) != null ? table.getValueAt(row, 13).toString() : "";
        String statusBayar = table.getValueAt(row, 17) != null ? table.getValueAt(row, 17).toString() : "";
        String diagnosa = table.getValueAt(row, 22) != null ? table.getValueAt(row, 22).toString() : "";

        if(stts.equals("Sudah")){
            // Sudah Diperiksa
            component.setBackground(new Color(240,128,128));
            component.setForeground(new Color(255,230,230));
        }else if(stts.equals("Batal")){
            // Batal Periksa
            component.setBackground(new Color(240,230,140));
            component.setForeground(new Color(120,110,50));
        }else if(stts.equals("Dirujuk") || stts.equals("Meninggal") || stts.equals("Pulang Paksa")){
            component.setBackground(new Color(152,152,156));
            component.setForeground(new Color(245,245,255));
        }else if(stts.equals("Dirawat")){
            component.setBackground(new Color(119,221,119));
            component.setForeground(new Color(245,255,245));
        }
        
        if(statusBayar.equals("Sudah Bayar")){
            // Sudah Bayar
            component.setBackground(new Color(60,179,113));
            component.setForeground(new Color(255,255,255));
        }
        
        // Cek diagnosa 1 (Sudah Diindeks)
        if(diagnosa.equals("Sudah") && !statusBayar.equals("Sudah Bayar")){
            component.setBackground(new Color(147,112,219));
            component.setForeground(new Color(255,255,255));
        }
        
        // Sudah bayar dan sudah diindeks
        if (statusBayar.equals("Sudah Bayar") && diagnosa.equals("Sudah")){
            component.setBackground(new Color(50,50,50));
            component.setForeground(new Color(255,255,255));
        }
        
        return component;
    }

}

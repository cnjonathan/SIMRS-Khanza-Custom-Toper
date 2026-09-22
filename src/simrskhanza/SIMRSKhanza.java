/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simrskhanza;

import usu.widget.util.WidgetUtilities;

/**
 *
 * @author khanzasoft
 */
public class SIMRSKhanza {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AutoUpdaterChecker.installGlobalErrorHandler();
        WidgetUtilities.invokeLater(() -> {
            try {
                frmUtama utama=frmUtama.getInstance();
                utama.isWall();
                utama.setVisible(true);
            } catch (Throwable t) {
                System.err.println("Gagal meluncurkan form utama SIMRS Khanza: " + t.getMessage());
                t.printStackTrace(System.err);
            }
        }); 
    }
    
}

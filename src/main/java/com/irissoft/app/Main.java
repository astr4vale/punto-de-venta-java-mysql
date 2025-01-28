/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.irissoft.app;

import com.irissoft.capaPresentacion.Login;
import com.irissoft.capaPresentacion.MdiPrincipal;

/**
 *
 * @author KALI
 */
public class Main {

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MdiPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Login panelLogin = new Login();
        panelLogin.setVisible(true);

    }
}

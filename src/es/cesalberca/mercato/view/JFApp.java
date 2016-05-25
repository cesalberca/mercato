/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.cesalberca.mercato.view;

import es.cesalberca.mercato.controller.database.DatabaseConnector;
import es.cesalberca.mercato.controller.database.DatabaseHandler;
import es.cesalberca.mercato.model.Item;
import es.cesalberca.mercato.model.Order;
import static es.cesalberca.mercato.view.JPApp.selectedItems;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Clase principal del programa.
 * @author César Alberca
 */
public class JFApp extends javax.swing.JFrame {

    JPApp jpa = new JPApp();
    // Solo habrá un DatabaseHandler para toda la app.
    public static DatabaseHandler dbh = null;
  
    public JFApp() {
        initComponents();
        this.setBounds(100, 100, 500, 600);
        this.getContentPane().add(jpa);
        this.setTitle("Mercato");
        this.setVisible(true);
        
        try {
            // Al iniciar la aplicación se genera una nueva conexión.
            dbh = new DatabaseHandler();
            DatabaseConnector.newConnection();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "BBDD no disponible");
            Logger.getLogger(JFApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "BBDD no disponible");
            Logger.getLogger(JFApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiSave = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jMenu1.setText("Archivo");

        jmiSave.setText("Guardar");
        jmiSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveActionPerformed(evt);
            }
        });
        jMenu1.add(jmiSave);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            // Desonecta de la bbdd en caso que se haya llegado a conectar.
            if (DatabaseConnector.getConnection() != null) {
                DatabaseConnector.disconnect();
            }
            DatabaseConnector.disconnect();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JFApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JFApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jmiSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSaveActionPerformed
        try {
            if (JPApp.getUser() == null) {
                JOptionPane.showMessageDialog(null, "Necesitas iniciar sesión primero");
            } else if (jpa.getSelectedItems().size() == 0){
                JOptionPane.showMessageDialog(null, "Necesitas añadir items primero");
            } else {
                int orderId = dbh.getLastId(DatabaseConnector.getConnection(), "ORDER");
                Order order = new Order(orderId, selectedItems, JPApp.getUser());
                dbh.insert(DatabaseConnector.getConnection(), order);
                JOptionPane.showMessageDialog(null, "Pedido guardado correctamente");
            }
            // Limpar jtable aquí
        } catch (SQLException ex) {
            Logger.getLogger(JFApp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Pedido no guardado correctamente. Inténtalo de nuevo más tarde");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JFApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jmiSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFApp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    protected javax.swing.JMenuItem jmiSave;
    // End of variables declaration//GEN-END:variables
}

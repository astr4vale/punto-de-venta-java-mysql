/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.irissoft.capaPresentacion;

import com.irissoft.capaNegocios.NgUsuarios;
import com.irissoft.datos.DtUsuarios;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author KALI
 */
public class FrmEditarUsuario extends javax.swing.JInternalFrame {
    private String idUsuarioActual;
    private boolean modoEdicion = false;
    private String idUsuario;
    private MdiPrincipal mdiPrincipal;
    private final NgUsuarios ngUsuarios;

    public FrmEditarUsuario(Object[] datosUsuario) {
        initComponents();
        ngUsuarios = new NgUsuarios();
        cargarDatosUsuario(datosUsuario);
    }

    private void cargarDatosUsuario(Object[] datosUsuario) {
        txtDni.setText(datosUsuario[1].toString());
        txtNombreUsuario.setText(datosUsuario[2].toString());
        txtTelefonoUsuario.setText(datosUsuario[3].toString());
        txtDireccionUsuario.setText(datosUsuario[4].toString());
        txtCorreo.setText(datosUsuario[5].toString());
        cbRol.setSelectedItem(datosUsuario[6].toString());
        this.idUsuarioActual = datosUsuario[0].toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefonoUsuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccionUsuario = new javax.swing.JTextField();
        btnEditarUsuario = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cbRol = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(243, 244, 246));

        jLabel1.setText("Editar Usuario");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Dni");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));
        jPanel2.add(txtDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 320, 30));

        jLabel3.setText("Nombre");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        jPanel2.add(txtNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 320, 30));

        jLabel4.setText("Telefono");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));
        jPanel2.add(txtTelefonoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 320, 30));

        jLabel5.setText("Dirección");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));
        jPanel2.add(txtDireccionUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 320, 30));

        btnEditarUsuario.setBackground(new java.awt.Color(59, 130, 246));
        btnEditarUsuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO\\RECURSOS\\GUARDAR.png")); // NOI18N
        btnEditarUsuario.setText("Guardar");
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, -1, 30));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, 100, 30));

        lblCorreo.setText("Correo");
        jPanel2.add(lblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));
        jPanel2.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 320, 30));

        jLabel7.setText("Contraseña");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, -1));
        jPanel2.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 320, 30));

        cbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Vendedor" }));
        jPanel2.add(cbRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 320, 30));

        jLabel6.setText("Rol");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 27, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        if (validarCampos()) {
            actualizarUsuario();
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private boolean validarCampos() {
        if (txtDni.getText().trim().isEmpty()) {
            mostrarError("El DNI es obligatorio.");
            return false;
        }
        if (txtNombreUsuario.getText().trim().isEmpty()) {
            mostrarError("El nombre es obligatorio.");
            return false;
        }
        if (!validarFormatoCorreo()) {
            mostrarError("Ingrese un correo válido.");
            return false;
        }
        return true;
    }

    private void actualizarUsuario() {
        DtUsuarios usuario = crearUsuarioDesdeFormulario();

        if (ngUsuarios.update(usuario)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            actualizarTablaPrincipal();
            this.dispose();
        }
    }

    private DtUsuarios crearUsuarioDesdeFormulario() {
        DtUsuarios usuario = new DtUsuarios();
        usuario.setIdUsuario(Integer.parseInt(idUsuarioActual));
        usuario.setDniUsuario(txtDni.getText().trim());
        usuario.setNombreUsuario(txtNombreUsuario.getText().trim());
        usuario.setTelefono(txtTelefonoUsuario.getText().trim());
        usuario.setDireccion(txtDireccionUsuario.getText().trim());
        usuario.setCorreo(txtCorreo.getText().trim());
        usuario.setContrasena(new String(txtPass.getPassword()));
        usuario.setRol(cbRol.getSelectedItem().toString());
        return usuario;
    }
    
    private void actualizarTablaPrincipal() {
        ((MdiPrincipal) this.getDesktopPane().getTopLevelAncestor()).actualizarTablaUsuarios();
    }
    
    private boolean validarFormatoCorreo() {
        String correo = txtCorreo.getText().trim();
        return !correo.isEmpty() && correo.contains("@");
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmEditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JComboBox<String> cbRol;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccionUsuario;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombreUsuario;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtTelefonoUsuario;
    // End of variables declaration//GEN-END:variables
}

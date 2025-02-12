
package com.irissoft.capaPresentacion;

import com.irissoft.capaAccesoDatos.QClientes;
import com.irissoft.datos.DtClientes;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;


public class FrmDetalleFactura extends javax.swing.JInternalFrame {

    private MdiPrincipal mdiPrincipal;  // Referencia a MdiPrincipal

    public FrmDetalleFactura(MdiPrincipal mdiPrincipal) {
        initComponents();
        this.mdiPrincipal = mdiPrincipal;  // Asignar la referencia al constructor
    }

    private void detalleClientes() {
        String Dniruc = txtDniRucCliente.getText().trim();  // Asegurarse de eliminar los espacios en blanco
        String NombreCliente = txtNombreCliente.getText().trim();
        String TelefonoCliente = txtTelefonoCliente.getText().trim();
        String DireccionCliente = txtDireccionCliente.getText().trim();
        
        
        // Verificar que no haya campos vacíos
        if (Dniruc.isEmpty() || NombreCliente.isEmpty() || TelefonoCliente.isEmpty() || DireccionCliente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto DtClientes con los datos del formulario
        DtClientes dtClientes = new DtClientes();
        dtClientes.setDniRuc(Dniruc);
        dtClientes.setNombre(NombreCliente);
        dtClientes.setTelefono(TelefonoCliente);
        dtClientes.setDireccion(DireccionCliente);

        // Ahora pasas el cliente a MdiPrincipal
        mdiPrincipal.setCliente(dtClientes);
    }

    private void verificarClienteExistente() {
        String dniRuc = txtDniRucCliente.getText().trim();


        try {
            QClientes qClientes = new QClientes();
            QClientes.ClienteDatos datos = qClientes.verificarClienteExistente(dniRuc);

            if (datos != null) {
                // Cliente existe, llenar los campos con los datos obtenidos
                txtNombreCliente.setText(datos.nombre);
                txtTelefonoCliente.setText(datos.telefono);
                txtDireccionCliente.setText(datos.direccion);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontró al cliente, registre datos... ",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al verificar cliente: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDniRucCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTelefonoCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccionCliente = new javax.swing.JTextField();
        btnCompletarCompra = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnVerificar = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(243, 244, 246));

        jLabel1.setText("Datos del Cliente");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Dni");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        jPanel2.add(txtDniRucCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 320, 30));

        jLabel3.setText("Nombre");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
        jPanel2.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 320, 30));

        jLabel4.setText("Telefono");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        jPanel2.add(txtTelefonoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 320, 30));

        jLabel5.setText("Direccion");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));
        jPanel2.add(txtDireccionCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 320, 30));

        btnCompletarCompra.setBackground(new java.awt.Color(59, 130, 246));
        btnCompletarCompra.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO\\RECURSOS\\GUARDAR.png")); // NOI18N
        btnCompletarCompra.setText("Completar Compra");
        btnCompletarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompletarCompraActionPerformed(evt);
            }
        });
        jPanel2.add(btnCompletarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 170, 30));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 130, 30));

        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVerificar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(27, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(btnVerificar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCompletarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletarCompraActionPerformed
        detalleClientes();
        mdiPrincipal.completarCompra();
        this.dispose();
    }//GEN-LAST:event_btnCompletarCompraActionPerformed

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
        verificarClienteExistente();
    }//GEN-LAST:event_btnVerificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCompletarCompra;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDniRucCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtTelefonoCliente;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.irissoft.capaPresentacion;

import com.irissoft.capaNegocios.NgProductos;
import com.irissoft.capaNegocios.NgProveedores;
import com.irissoft.datos.DtProductos;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author KALI
 */
public class FrmEditarProducto extends javax.swing.JInternalFrame {
    private final NgProductos ngProductos;
    private final NgProveedores ngProveedores;
    private final Map<String, Integer> proveedoresMap;
    private Integer idProductoActual;
    private String skuOriginal;

    public FrmEditarProducto(Object[] datosProducto) {
        initComponents();
        ngProductos = new NgProductos();
        ngProveedores = new NgProveedores();
        proveedoresMap = new HashMap<>();
        cargarProveedores();
        cargarDatosProducto(datosProducto);
        this.idProductoActual = (Integer) datosProducto[0];
        this.skuOriginal = datosProducto[6].toString();
    }

    private void cargarProveedorEnCombo(int idProveedor) {
        // Iterar sobre las entradas del mapa para buscar el nombre del proveedor por ID
        for (Map.Entry<String, Integer> entry : proveedoresMap.entrySet()) {
            if (entry.getValue() == idProveedor) {
                cbProveedor.setSelectedItem(entry.getKey()); // Seleccionar el nombre en el combo
                return;
            }
        }

        // Si no se encuentra, seleccionar el ítem por defecto
        cbProveedor.setSelectedIndex(0); // "Seleccione un proveedor"
    }

    private void cargarDatosProducto(Object[] datosProducto) {
        // Campos obligatorios (sin riesgo de null)
        txtNombreProducto.setText(datosProducto[1].toString());
        txtDescripcionProducto.setText(datosProducto[2].toString());
        txtCantidadProducto.setText(datosProducto[3].toString());
        txtPrecioProducto.setText(datosProducto[4].toString());

        // Campos opcionales (manejo de null)
        txtSku.setText(datosProducto[6] != null ? datosProducto[6].toString() : "");
        txtTalla.setText(datosProducto[7] != null ? datosProducto[7].toString() : "");
        txtColor.setText(datosProducto[8] != null ? datosProducto[8].toString() : "");

        // Obtener el ID del proveedor desde datosProducto[5]
        int idProveedor = (int) datosProducto[5];
        cargarProveedorEnCombo(idProveedor); // Cargar en el combo
    }


       private DtProductos crearProductoDesdeFormulario() {
        if (idProductoActual == null) {
            throw new IllegalStateException("ID del producto no está definido");
        }
        DtProductos producto = new DtProductos();
        producto.setIdProducto(idProductoActual);
        producto.setNombreProducto(txtNombreProducto.getText().trim());
        producto.setDescripcion(txtDescripcionProducto.getText().trim());
        producto.setCantidad(Integer.parseInt(txtCantidadProducto.getText().trim()));
        producto.setPrecio(Double.parseDouble(txtPrecioProducto.getText().trim()));
        producto.setIdProveedor(proveedoresMap.get(cbProveedor.getSelectedItem().toString()));
        producto.setSku(txtSku.getText().trim());
        producto.setTalla(txtTalla.getText().trim());
        producto.setColor(txtColor.getText().trim());
        return producto;
    }

    private boolean validarCampos() {
        if (camposVacios()) {
            mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        if (skuModificado() && ngProductos.existeProductoPorSKU(txtSku.getText().trim())) {
            mostrarError("El SKU ya está registrado.");
            return false;
        }

        if (!validarSeleccionProveedor()) {
            mostrarError("Seleccione un proveedor válido.");
            return false;
        }

        if (!validarNumerosPositivos()) {
            mostrarError("Cantidad y precio deben ser mayores a cero.");
            return false;
        }

        return true;
    }
    
    private boolean camposVacios() {
        return txtNombreProducto.getText().trim().isEmpty()
                || txtDescripcionProducto.getText().trim().isEmpty()
                || txtCantidadProducto.getText().trim().isEmpty()
                || txtPrecioProducto.getText().trim().isEmpty()
                || txtSku.getText().trim().isEmpty()
                || txtTalla.getText().trim().isEmpty()
                || txtColor.getText().trim().isEmpty();
    }
   
    private boolean skuModificado() {
        return !txtSku.getText().trim().equals(skuOriginal);
    }
    
    private boolean validarSeleccionProveedor() {
        Object proveedor = cbProveedor.getSelectedItem();
        return proveedor != null
                && !proveedor.toString().equals("Seleccione un proveedor")
                && proveedoresMap.containsKey(proveedor.toString());
    }

    private boolean validarNumerosPositivos() {
        try {
            int cantidad = Integer.parseInt(txtCantidadProducto.getText().trim());
            double precio = Double.parseDouble(txtPrecioProducto.getText().trim());
            return cantidad > 0 && precio > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void cargarProveedores() {
        cbProveedor.removeAllItems();
        cbProveedor.addItem("Seleccione un proveedor");

        ngProveedores.getAllProveedores().forEach(p -> {
            cbProveedor.addItem(p.getNombre());
            proveedoresMap.put(p.getNombre(), p.getIdProveedor());
        });
    }

    private void actualizarTablaPrincipal() {
        ((MdiPrincipal) this.getDesktopPane().getTopLevelAncestor()).actualizarTablaProductos();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    


    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescripcionProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCantidadProducto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPrecioProducto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbProveedor = new javax.swing.JComboBox<>();
        btnGuardarProducto = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtSku = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        txtTalla = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(243, 244, 246));

        jLabel1.setText("Editar Producto");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Nombre");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        jPanel2.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 320, 30));

        jLabel3.setText("Descripción");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));
        jPanel2.add(txtDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 320, 30));

        jLabel4.setText("Cantidad");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));
        jPanel2.add(txtCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 320, 30));

        jLabel5.setText("Precio");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));
        jPanel2.add(txtPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 320, 30));

        jLabel8.setText("Proveedor");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        cbProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 320, 30));

        btnGuardarProducto.setBackground(new java.awt.Color(59, 130, 246));
        btnGuardarProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO\\RECURSOS\\GUARDAR.png")); // NOI18N
        btnGuardarProducto.setText("Guardar");
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, -1, 30));

        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 100, 30));

        jLabel6.setText("SKU");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, -1, -1));
        jPanel2.add(txtSku, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 200, 30));

        jLabel7.setText("Talla");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, -1));

        jLabel9.setText("Color");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, -1, -1));
        jPanel2.add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 180, 200, 30));
        jPanel2.add(txtTalla, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 200, 30));

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
                        .addGap(0, 592, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        if (!validarCampos()) {
            return;
        }

        try {
            DtProductos producto = crearProductoDesdeFormulario();

            if (ngProductos.update(producto)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaPrincipal();
                this.dispose();
            } else {
                mostrarError("Error al actualizar el producto.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Cantidad y precio deben ser números válidos.");
        }
    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        try {
            this.setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FrmEditarUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JComboBox<String> cbProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtDescripcionProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtSku;
    private javax.swing.JTextField txtTalla;
    // End of variables declaration//GEN-END:variables
}

package com.irissoft.capaPresentacion;

import com.irissoft.capaAccesoDatos.QDashboard;
import com.irissoft.capaNegocios.NgProductos;
import com.irissoft.capaNegocios.NgProveedores;
import com.irissoft.capaNegocios.NgUsuarios;
import com.irissoft.capaNegocios.NgVentas;
import com.irissoft.datos.DtCarrito;
import com.irissoft.datos.DtClientes;
import com.irissoft.datos.DtDashboard;
import com.irissoft.datos.DtProductos;
import com.irissoft.datos.DtUsuarios;
import com.irissoft.datos.DtVentas;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author KALI
 */
public final class MdiPrincipal extends javax.swing.JFrame {
    
    // Modelos de datos
    private final DefaultTableModel dtmUsuarios = new DefaultTableModel();
    private final DefaultTableModel dtmProductos = new DefaultTableModel();
    private final DefaultTableModel dtmVentas = new DefaultTableModel();
    private DefaultListModel<DtCarrito> modelCarrito;
    private double totalPagar = 0.0;
    
    // Capas de negocio
    private final NgUsuarios ngUsuarios = new NgUsuarios();
    private final NgProductos ngProductos = new NgProductos();
    private final NgVentas ngVentas = new NgVentas();
    private final NgProveedores ngProveedores = new NgProveedores();
    
    // Datos de la aplicación
    private DtUsuarios usuarioActual = new DtUsuarios();
    private QDashboard qDashboard = new QDashboard();
    private DtClientes cliente;

    public MdiPrincipal() {
        initComponents();
        inicializarComponentes();
        cargarDatosIniciales();
        iniciarActualizacionDashboard();
    }
 
    private void inicializarComponentes() {
        configurarPaneles();
        configurarTablas();
        configurarBuscadores();
        inicializarCarrito();
    }

    private void inicializarCarrito() {
        modelCarrito = new DefaultListModel<>();
        ListaProductosVender.setModel(modelCarrito);
    }
    
    private void iniciarActualizacionDashboard() {
        Timer timer = new Timer(2000, (e) -> actualizarDatosDashboard());
        timer.start();
    }
    
        // Métodos de configuración inicial
    private void configurarPaneles() {
        dashboardPanel.setVisible(true);
        configuracionPanel.setVisible(false);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false);
    }

    private void configurarTablas() {
        configurarTablaUsuarios();
        configurarTablaProductos();
        configurarTablaVentas();
    }

    private void configurarTablaUsuarios() {
        dtmUsuarios.addColumn("ID");
        dtmUsuarios.addColumn("Dni");
        dtmUsuarios.addColumn("Nombre");
        dtmUsuarios.addColumn("Telefono");
        dtmUsuarios.addColumn("Direccion");
        dtmUsuarios.addColumn("Rol");
        tablaUsuarios.setModel(dtmUsuarios);
        tablaUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
        tablaUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private void configurarTablaProductos() {
        dtmProductos.addColumn("ID");
        dtmProductos.addColumn("SKU");
        dtmProductos.addColumn("Nombre");
        dtmProductos.addColumn("Descripción");
        dtmProductos.addColumn("Cantidad");
        dtmProductos.addColumn("Precio");
        dtmProductos.addColumn("Proveedor");
        dtmProductos.addColumn("Talla");
        dtmProductos.addColumn("Color");
        tablaProductos.setModel(dtmProductos);
        tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private void configurarTablaVentas() {
        dtmVentas.addColumn("ID");
        dtmVentas.addColumn("Nombre");
        dtmVentas.addColumn("Stock");
        dtmVentas.addColumn("Precio");
        dtmVentas.addColumn("Talla");
        dtmVentas.addColumn("Color");
        tablaVentas.setModel(dtmVentas);
        tablaVentas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaVentas.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    // Métodos de datos iniciales
    private void cargarDatosIniciales() {
        cargarUsuariosIniciales();
        cargarProductosIniciales();
        cargarVentasIniciales();
    }

    private void cargarUsuariosIniciales() {
        actualizarTablaUsuarios(ngUsuarios.getAll());
    }

    private void cargarProductosIniciales() {
        actualizarTablaProductos(ngProductos.getAll());
    }

    private void cargarVentasIniciales() {
        actualizarTablaVentas(ngVentas.getAll());
    }

    // Métodos de actualización de datos
    public void actualizarTablaUsuarios() {
        actualizarTablaUsuarios(ngUsuarios.getAll());
    }

    private void actualizarTablaUsuarios(List<DtUsuarios> usuarios) {
        dtmUsuarios.setRowCount(0);
        usuarios.forEach(u -> dtmUsuarios.addRow(new Object[]{
            u.getIdUsuario(),
            u.getDniUsuario(),
            u.getNombreUsuario(),
            u.getTelefono(),
            u.getDireccion(),
            u.getRol()
        }));
    }

    public void actualizarTablaProductos() {
        actualizarTablaProductos(ngProductos.getAll());
    }

    public void actualizarTablaVentas() {
        actualizarTablaVentas(ngVentas.getAll());
    }

    private void actualizarTablaProductos(List<DtProductos> productos) {
        dtmProductos.setRowCount(0);
        productos.forEach(p -> dtmProductos.addRow(new Object[]{
            p.getIdProducto(),
            p.getSku(),
            p.getNombreProducto(),
            p.getDescripcion(),
            p.getCantidad(),
            p.getPrecio(),
            p.getProveedor().getNombre(),
            p.getTalla(),
            p.getColor()
        }));
    }

    private void actualizarTablaVentas(List<DtVentas> productos) {
        dtmVentas.setRowCount(0);
        productos.forEach(p -> dtmVentas.addRow(new Object[]{
            p.getIdProducto(),
            p.getNombreProducto(),
            p.getCantidad(),
            p.getPrecio(),
            p.getTalla(),
            p.getColor()
        }));
    }

    // Métodos de interacción de usuario
    public void setUsuarioActual(DtUsuarios usuario) {
        usuarioActual = usuario;
        actualizarInformacionUsuario();
    }

    private void actualizarInformacionUsuario() {
        if (usuarioActual != null) {
            lblNombreUsuario.setText(usuarioActual.getNombreUsuario());
            lblRol.setText(usuarioActual.getRol());
        }
    }

    public void setCliente(DtClientes cliente) {
        this.cliente = cliente;
    }

    // Métodos del dashboard
    private void actualizarDatosDashboard() {
        DtDashboard datos = qDashboard.obtenerDatosDashboard();
        DecimalFormat formatoMoneda = new DecimalFormat("$#,##0.00");
        DecimalFormat formatoNumero = new DecimalFormat("#,##0");

        lblVentasHoy.setText(formatoMoneda.format(datos.getTotalVentas()));
        lblOrdenes.setText(formatoNumero.format(datos.getTotalOrdenes()));
        lblClientes.setText(formatoNumero.format(datos.getTotalClientes()));
        lblProductos.setText(formatoNumero.format(datos.getTotalProductos()));
    }


    // Métodos del carrito de compras
    private void productoSeleccionado() {
        int filaSeleccionada = tablaVentas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            DtCarrito item = new DtCarrito();
            item.setIdProducto(Integer.parseInt(tablaVentas.getValueAt(filaSeleccionada, 0).toString()));
            item.setNombre(tablaVentas.getValueAt(filaSeleccionada, 1).toString());
            item.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
            item.setPrecio(Double.parseDouble(tablaVentas.getValueAt(filaSeleccionada, 3).toString()));

            modelCarrito.addElement(item);
            actualizarTotalPagar(item.getPrecio() * item.getCantidad());
            txtCantidadProducto.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void actualizarTotalPagar(double monto) {
        totalPagar += monto;
        lblTotal.setText("S/. " + totalPagar);
    }
    
    private void eliminarProducto() {
        int indiceSeleccionado = ListaProductosVender.getSelectedIndex();
        if (indiceSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DtCarrito item = modelCarrito.getElementAt(indiceSeleccionado);
        totalPagar -= item.getPrecio() * item.getCantidad();
        modelCarrito.remove(indiceSeleccionado);
        lblTotal.setText("S/. " + totalPagar);
    }

    // Métodos de procesamiento de ventas
    public void completarCompra() {
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean ventaExitosa = ngVentas.realizarVenta(
                usuarioActual.getIdUsuario(),
                cliente.getDniRuc(),
                cliente.getNombre(),
                cliente.getTelefono(),
                cliente.getDireccion(),
                convertirCarritoAJson()
        );

        if (ventaExitosa) {
            JOptionPane.showMessageDialog(this, "Venta registrada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            modelCarrito.clear();
            totalPagar = 0.0;
            lblTotal.setText("S/. 0.00");
            actualizarTablaProductos();
            actualizarTablaVentas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al procesar la venta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String convertirCarritoAJson() {
        JSONArray jsonCarrito = new JSONArray();
        for (int i = 0; i < modelCarrito.size(); i++) {
            DtCarrito item = modelCarrito.getElementAt(i);
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("idProducto", item.getIdProducto());
            jsonItem.put("cantidad", item.getCantidad());
            jsonCarrito.put(jsonItem);
        }
        return jsonCarrito.toString();
    }
  
    // Métodos de búsqueda
    private void configurarBuscadores() {
        agregarListenerBusqueda(txtBuscadorUsuarios, this::buscarUsuarios);
        agregarListenerBusqueda(txtBuscadorProductos, this::buscarProductos);
        agregarListenerBusqueda(txtBuscadorVentas, this::buscarVentas);
    }

    private void agregarListenerBusqueda(javax.swing.JTextField campo, Runnable accion) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                accion.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                accion.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                accion.run();
            }
        });
    }

    private void buscarUsuarios() {
        actualizarTablaUsuarios(ngUsuarios.buscarUsuarios(txtBuscadorUsuarios.getText().trim()));
    }

    private void buscarProductos() {
        actualizarTablaProductos(ngProductos.buscarProductos(txtBuscadorProductos.getText().trim()));
    }

    private void buscarVentas() {
        actualizarTablaVentas(ngVentas.buscarVentas(txtBuscadorVentas.getText().trim()));
    }

    // Métodos auxiliares
    private boolean validarFilaSeleccionada(int fila) {
        if (fila == -1) {
            mostrarAdvertencia("Seleccione un registro de la tabla");
            return false;
        }
        return true;
    }    

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void confirmarYEliminarUsuario(int idUsuario) {
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION && ngUsuarios.delete(String.valueOf(idUsuario))) {
            actualizarTablaUsuarios();
            JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private int obtenerIdUsuarioSeleccionado(int fila) {
        return (int) tablaUsuarios.getValueAt(fila, 0);
    }


    private void abrirFormularioEdicionUsuario(int idUsuario) {
        DtUsuarios usuario = ngUsuarios.getUsuarioPorId(idUsuario);
        Object[] datosUsuario = construirDatosUsuario(usuario);

        FrmEditarUsuario frmEditarUsuario = new FrmEditarUsuario(datosUsuario);
        desktopPane.add(frmEditarUsuario);
        frmEditarUsuario.setVisible(true);
    }

    private Object[] construirDatosUsuario(DtUsuarios usuario) {
        return new Object[]{
            usuario.getIdUsuario(),
            usuario.getDniUsuario(),
            usuario.getNombreUsuario(),
            usuario.getTelefono(),
            usuario.getDireccion(),
            usuario.getCorreo(),
            usuario.getRol()
        };
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        bgCerrarSesion = new javax.swing.JPanel();
        lblCerrarSesion = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bgDashboard = new javax.swing.JPanel();
        lblDashBoard = new javax.swing.JLabel();
        bgPuntoVenta = new javax.swing.JPanel();
        lblPuntoDeVenta = new javax.swing.JLabel();
        bgProductos = new javax.swing.JPanel();
        lblProducto = new javax.swing.JLabel();
        bgUsuarios = new javax.swing.JPanel();
        lblUsuarios = new javax.swing.JLabel();
        bgReportes = new javax.swing.JPanel();
        lblReportes = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        bgConfiguracion = new javax.swing.JPanel();
        lblConfiguracion = new javax.swing.JLabel();
        pnlContainer = new javax.swing.JPanel();
        dashboardPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblUstedes = new javax.swing.JLabel();
        lblbienvenido = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblNombreUsuario = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        lblProductos = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblVentasHoy = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblOrdenes = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblNumeroDeOrden = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblNumeroDeOrden2 = new javax.swing.JLabel();
        lblHora2 = new javax.swing.JLabel();
        lblMonto2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        lblNumeroDeOrden3 = new javax.swing.JLabel();
        lblHora3 = new javax.swing.JLabel();
        lblMonto3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        barraProgreso1 = new javax.swing.JProgressBar();
        jPanel17 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        barraProgreso2 = new javax.swing.JProgressBar();
        jPanel18 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        barraProgreso3 = new javax.swing.JProgressBar();
        puntoDeVentaPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtBuscadorVentas = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        txtCantidadProducto = new javax.swing.JTextField();
        btnAñadirCarrito = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListaProductosVender = new javax.swing.JList<>();
        jPanel26 = new javax.swing.JPanel();
        btnQuitarProductoDeLista = new javax.swing.JLabel();
        btnPagarCompra = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        productosPanel = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        btnEditarProducto = new javax.swing.JLabel();
        btnEliminarProducto = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        txtBuscadorProductos = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        btnAddProveedor = new javax.swing.JButton();
        jLabel47 = new javax.swing.JLabel();
        btnAddProducto = new javax.swing.JButton();
        usuariosPanel = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        btnEditarUsuario = new javax.swing.JLabel();
        btnEliminarUsuario = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        txtBuscadorUsuarios = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        reportesPanel = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        configuracionPanel = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 650));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        bgCerrarSesion.setBackground(new java.awt.Color(255, 255, 255));
        bgCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgCerrarSesionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgCerrarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgCerrarSesionMouseExited(evt);
            }
        });

        lblCerrarSesion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCerrarSesion.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\CERRAR SESION.png")); // NOI18N
        lblCerrarSesion.setText("Cerrar Sesión");
        lblCerrarSesion.setToolTipText("");
        lblCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarSesionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgCerrarSesionLayout = new javax.swing.GroupLayout(bgCerrarSesion);
        bgCerrarSesion.setLayout(bgCerrarSesionLayout);
        bgCerrarSesionLayout.setHorizontalGroup(
            bgCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgCerrarSesionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCerrarSesion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgCerrarSesionLayout.setVerticalGroup(
            bgCerrarSesionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Showcard Gothic", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(31, 41, 55));
        jLabel3.setText("TIENDAS ZARA");

        jLabel4.setForeground(new java.awt.Color(107, 114, 128));
        jLabel4.setText("Sistema de Punto de Venta");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        bgDashboard.setBackground(new java.awt.Color(255, 255, 255));
        bgDashboard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                bgDashboardFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                bgDashboardFocusLost(evt);
            }
        });
        bgDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgDashboardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgDashboardMouseExited(evt);
            }
        });

        lblDashBoard.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDashBoard.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\DASHBOARD3.png")); // NOI18N
        lblDashBoard.setText("Dashboard");
        lblDashBoard.setToolTipText("");
        lblDashBoard.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                lblDashBoardFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                lblDashBoardFocusLost(evt);
            }
        });
        lblDashBoard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDashBoardMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDashBoardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDashBoardMouseExited(evt);
            }
        });

        javax.swing.GroupLayout bgDashboardLayout = new javax.swing.GroupLayout(bgDashboard);
        bgDashboard.setLayout(bgDashboardLayout);
        bgDashboardLayout.setHorizontalGroup(
            bgDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDashBoard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgDashboardLayout.setVerticalGroup(
            bgDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgDashboardLayout.createSequentialGroup()
                .addComponent(lblDashBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bgPuntoVenta.setBackground(new java.awt.Color(255, 255, 255));
        bgPuntoVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgPuntoVentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgPuntoVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgPuntoVentaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bgPuntoVentaMousePressed(evt);
            }
        });

        lblPuntoDeVenta.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPuntoDeVenta.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\VENTA.png")); // NOI18N
        lblPuntoDeVenta.setText("Punto de venta");
        lblPuntoDeVenta.setToolTipText("");
        lblPuntoDeVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPuntoDeVentaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblPuntoDeVentaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblPuntoDeVentaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout bgPuntoVentaLayout = new javax.swing.GroupLayout(bgPuntoVenta);
        bgPuntoVenta.setLayout(bgPuntoVentaLayout);
        bgPuntoVentaLayout.setHorizontalGroup(
            bgPuntoVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgPuntoVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPuntoDeVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgPuntoVentaLayout.setVerticalGroup(
            bgPuntoVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPuntoDeVenta, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        bgProductos.setBackground(new java.awt.Color(255, 255, 255));
        bgProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgProductosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgProductosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgProductosMouseExited(evt);
            }
        });

        lblProducto.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\PRODUCTOS.png")); // NOI18N
        lblProducto.setText("Productos");
        lblProducto.setToolTipText("");
        lblProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProductoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblProductoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout bgProductosLayout = new javax.swing.GroupLayout(bgProductos);
        bgProductos.setLayout(bgProductosLayout);
        bgProductosLayout.setHorizontalGroup(
            bgProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProducto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgProductosLayout.setVerticalGroup(
            bgProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        bgUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        bgUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgUsuariosMouseExited(evt);
            }
        });

        lblUsuarios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblUsuarios.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\USUARIOS.png")); // NOI18N
        lblUsuarios.setText("Usuarios");
        lblUsuarios.setToolTipText("");
        lblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout bgUsuariosLayout = new javax.swing.GroupLayout(bgUsuarios);
        bgUsuarios.setLayout(bgUsuariosLayout);
        bgUsuariosLayout.setHorizontalGroup(
            bgUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUsuarios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgUsuariosLayout.setVerticalGroup(
            bgUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        bgReportes.setBackground(new java.awt.Color(255, 255, 255));
        bgReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgReportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgReportesMouseExited(evt);
            }
        });

        lblReportes.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblReportes.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\REPORTES.png")); // NOI18N
        lblReportes.setText("Reportes");
        lblReportes.setToolTipText("");
        lblReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReportesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblReportesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout bgReportesLayout = new javax.swing.GroupLayout(bgReportes);
        bgReportes.setLayout(bgReportesLayout);
        bgReportesLayout.setHorizontalGroup(
            bgReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgReportesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblReportes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgReportesLayout.setVerticalGroup(
            bgReportesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
        );

        bgConfiguracion.setBackground(new java.awt.Color(255, 255, 255));
        bgConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bgConfiguracionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bgConfiguracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bgConfiguracionMouseExited(evt);
            }
        });

        lblConfiguracion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConfiguracion.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\CONFIGURACION.png")); // NOI18N
        lblConfiguracion.setText("Configuración");
        lblConfiguracion.setToolTipText("");
        lblConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblConfiguracionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblConfiguracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblConfiguracionMouseExited(evt);
            }
        });

        javax.swing.GroupLayout bgConfiguracionLayout = new javax.swing.GroupLayout(bgConfiguracion);
        bgConfiguracion.setLayout(bgConfiguracionLayout);
        bgConfiguracionLayout.setHorizontalGroup(
            bgConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgConfiguracionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConfiguracion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bgConfiguracionLayout.setVerticalGroup(
            bgConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(bgCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bgConfiguracion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bgReportes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bgUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bgProductos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bgPuntoVenta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bgDashboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(10, 10, 10))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(bgDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(bgPuntoVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(bgProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(bgUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(bgReportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(bgConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(bgCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel19);

        pnlContainer.setLayout(new javax.swing.OverlayLayout(pnlContainer));

        dashboardPanel.setBackground(new java.awt.Color(243, 244, 246));
        dashboardPanel.setToolTipText("");

        jPanel5.setBackground(new java.awt.Color(243, 244, 246));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUstedes.setBackground(new java.awt.Color(0, 0, 0));
        lblUstedes.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        lblUstedes.setForeground(new java.awt.Color(0, 0, 0));
        lblUstedes.setText("Usted es: ");
        jPanel5.add(lblUstedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(415, 0, 140, 44));

        lblbienvenido.setBackground(new java.awt.Color(0, 0, 0));
        lblbienvenido.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        lblbienvenido.setForeground(new java.awt.Color(0, 0, 0));
        lblbienvenido.setText("Bienvenido: ");
        jPanel5.add(lblbienvenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 0, 150, 44));

        lblRol.setBackground(new java.awt.Color(0, 0, 0));
        lblRol.setFont(new java.awt.Font("Dutch801 XBd BT", 0, 14)); // NOI18N
        lblRol.setForeground(new java.awt.Color(102, 102, 102));
        lblRol.setText("ROL");
        jPanel5.add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 120, 44));

        lblNombreUsuario.setBackground(new java.awt.Color(0, 0, 0));
        lblNombreUsuario.setFont(new java.awt.Font("Dutch801 XBd BT", 0, 14)); // NOI18N
        lblNombreUsuario.setForeground(new java.awt.Color(102, 102, 102));
        lblNombreUsuario.setText("USUARIO");
        jPanel5.add(lblNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 170, 44));

        jPanel6.setBackground(new java.awt.Color(243, 244, 246));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setText("Productos");

        lblProductos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProductos.setForeground(new java.awt.Color(0, 0, 0));
        lblProductos.setText("S/N");

        jLabel22.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\PRODUCTOS2.png")); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(lblProductos))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblProductos)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Ventas Totales");

        lblVentasHoy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblVentasHoy.setForeground(new java.awt.Color(0, 0, 0));
        lblVentasHoy.setText("S/N");

        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\DOLAR.png")); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVentasHoy)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblVentasHoy)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setText("Ordenes");

        lblOrdenes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblOrdenes.setForeground(new java.awt.Color(0, 0, 0));
        lblOrdenes.setText("S/N");

        jLabel16.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\ORDENES.png")); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(lblOrdenes))
                .addGap(47, 47, 47))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOrdenes)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setText("Clientes");

        lblClientes.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClientes.setForeground(new java.awt.Color(0, 0, 0));
        lblClientes.setText("S/N");

        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\CLIENTES.png")); // NOI18N

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(lblClientes))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClientes)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(243, 244, 246));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Ventas Recientes");

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\RELOJ.png")); // NOI18N
        jPanel12.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 46));

        lblNumeroDeOrden.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNumeroDeOrden.setForeground(new java.awt.Color(0, 0, 0));
        lblNumeroDeOrden.setText("Orden #112211");
        jPanel12.add(lblNumeroDeOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 133, 22));

        lblHora.setText("Hace 2 horas");
        jPanel12.add(lblHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 133, 18));

        lblMonto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMonto.setForeground(new java.awt.Color(0, 0, 0));
        lblMonto.setText("$ 80.00");
        jPanel12.add(lblMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 10, 79, 46));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\RELOJ.png")); // NOI18N
        jPanel13.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 46));

        lblNumeroDeOrden2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNumeroDeOrden2.setForeground(new java.awt.Color(0, 0, 0));
        lblNumeroDeOrden2.setText("Orden #112211");
        jPanel13.add(lblNumeroDeOrden2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 133, 22));

        lblHora2.setText("Hace 2 horas");
        jPanel13.add(lblHora2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 133, 18));

        lblMonto2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMonto2.setForeground(new java.awt.Color(0, 0, 0));
        lblMonto2.setText("$ 80.00");
        jPanel13.add(lblMonto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 10, 79, 46));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\RELOJ.png")); // NOI18N
        jPanel15.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 46));

        lblNumeroDeOrden3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNumeroDeOrden3.setForeground(new java.awt.Color(0, 0, 0));
        lblNumeroDeOrden3.setText("Orden #112211");
        jPanel15.add(lblNumeroDeOrden3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 133, 22));

        lblHora3.setText("Hace 2 horas");
        jPanel15.add(lblHora3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 133, 18));

        lblMonto3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMonto3.setForeground(new java.awt.Color(0, 0, 0));
        lblMonto3.setText("$ 80.00");
        jPanel15.add(lblMonto3, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 10, 79, 46));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 0));
        jLabel31.setText("Venta por Categoria");

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 0));
        jLabel33.setText("Orden #112211");
        jPanel16.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 133, 22));

        barraProgreso1.setBackground(new java.awt.Color(0, 51, 255));
        jPanel16.add(barraProgreso1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 290, 10));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setText("Orden #112211");
        jPanel17.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 133, 22));

        barraProgreso2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel17.add(barraProgreso2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 290, 10));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 0));
        jLabel35.setText("Orden #112211");
        jPanel18.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 133, 22));

        barraProgreso3.setBackground(new java.awt.Color(153, 0, 153));
        jPanel18.add(barraProgreso3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 290, 10));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel31)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        pnlContainer.add(dashboardPanel);

        puntoDeVentaPanel.setBackground(new java.awt.Color(243, 244, 246));
        puntoDeVentaPanel.setLayout(new javax.swing.BoxLayout(puntoDeVentaPanel, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(243, 244, 246));

        jPanel4.setBackground(new java.awt.Color(243, 244, 246));

        jLabel32.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\BUSCAR.png")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtBuscadorVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel32)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBuscadorVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)))
        );

        jPanel23.setBackground(new java.awt.Color(243, 244, 246));

        tablaVentas.setBackground(new java.awt.Color(255, 255, 255));
        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaVentas);

        jPanel22.setBackground(new java.awt.Color(243, 244, 246));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\CANTIDAD.png")); // NOI18N
        jLabel44.setText("Cantidad");
        jPanel24.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 30, 110, -1));
        jPanel24.add(txtCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 35, 220, -1));

        btnAñadirCarrito.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\AÑADIR.png")); // NOI18N
        btnAñadirCarrito.setText("Añadir");
        btnAñadirCarrito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAñadirCarritoMouseClicked(evt);
            }
        });
        jPanel24.add(btnAñadirCarrito, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 93, -1));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        puntoDeVentaPanel.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(243, 244, 246));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 0));

        ListaProductosVender.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(ListaProductosVender);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnQuitarProductoDeLista.setBackground(new java.awt.Color(34, 197, 94));
        btnQuitarProductoDeLista.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\EFECTIVO.png")); // NOI18N
        btnQuitarProductoDeLista.setText("Eliminar Producto");
        btnQuitarProductoDeLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnQuitarProductoDeListaMouseClicked(evt);
            }
        });
        jPanel26.add(btnQuitarProductoDeLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        btnPagarCompra.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\PAGAR.png")); // NOI18N
        btnPagarCompra.setText("Pagar");
        btnPagarCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPagarCompraMouseClicked(evt);
            }
        });
        jPanel26.add(btnPagarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        jLabel1.setText("TOTAL: ");

        lblTotal.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        puntoDeVentaPanel.add(jPanel3);

        pnlContainer.add(puntoDeVentaPanel);

        productosPanel.setBackground(new java.awt.Color(243, 244, 246));

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tablaProductos);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );

        jPanel29.setBackground(new java.awt.Color(243, 244, 246));
        jPanel29.setForeground(new java.awt.Color(0, 0, 0));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEditarProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\EDITAR.png")); // NOI18N
        btnEditarProducto.setText("Editar");
        btnEditarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarProductoMouseClicked(evt);
            }
        });
        jPanel29.add(btnEditarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        btnEliminarProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\ELIMINAR.png")); // NOI18N
        btnEliminarProducto.setText("Eliminar");
        btnEliminarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarProductoMouseClicked(evt);
            }
        });
        jPanel29.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        jPanel27.setBackground(new java.awt.Color(243, 244, 246));

        jLabel46.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\BUSCAR.png")); // NOI18N

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(txtBuscadorProductos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBuscadorProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46)))
        );

        jPanel31.setBackground(new java.awt.Color(243, 244, 246));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAddProveedor.setBackground(new java.awt.Color(59, 130, 246));
        btnAddProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnAddProveedor.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\MAS.png")); // NOI18N
        btnAddProveedor.setText("Añadir Proveedor");
        btnAddProveedor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProveedorActionPerformed(evt);
            }
        });
        jPanel31.add(btnAddProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 188, -1));

        jLabel47.setBackground(new java.awt.Color(0, 0, 0));
        jLabel47.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Gestion de Productos");
        jPanel31.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 3, 270, -1));

        btnAddProducto.setBackground(new java.awt.Color(59, 130, 246));
        btnAddProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnAddProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\MAS.png")); // NOI18N
        btnAddProducto.setText("Añadir Producto");
        btnAddProducto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductoActionPerformed(evt);
            }
        });
        jPanel31.add(btnAddProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 0, 188, -1));

        javax.swing.GroupLayout productosPanelLayout = new javax.swing.GroupLayout(productosPanel);
        productosPanel.setLayout(productosPanelLayout);
        productosPanelLayout.setHorizontalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosPanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        productosPanelLayout.setVerticalGroup(
            productosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productosPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pnlContainer.add(productosPanel);

        usuariosPanel.setBackground(new java.awt.Color(243, 244, 246));

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tablaUsuarios);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );

        jPanel35.setBackground(new java.awt.Color(243, 244, 246));
        jPanel35.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEditarUsuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\EDITAR.png")); // NOI18N
        btnEditarUsuario.setText("Editar");
        btnEditarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarUsuarioMouseClicked(evt);
            }
        });
        jPanel35.add(btnEditarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        btnEliminarUsuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\ELIMINAR.png")); // NOI18N
        btnEliminarUsuario.setText("Eliminar");
        btnEliminarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarUsuarioMouseClicked(evt);
            }
        });
        jPanel35.add(btnEliminarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        jPanel36.setBackground(new java.awt.Color(243, 244, 246));

        jLabel54.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\BUSCAR.png")); // NOI18N

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(txtBuscadorUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBuscadorUsuarios)))
        );

        jPanel37.setBackground(new java.awt.Color(243, 244, 246));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new java.awt.Color(59, 130, 246));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\MAS.png")); // NOI18N
        jButton3.setText("Añadir Usuario");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel37.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 0, 188, -1));

        jLabel55.setBackground(new java.awt.Color(0, 0, 0));
        jLabel55.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Gestion de Usuarios");
        jPanel37.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 3, 270, -1));

        javax.swing.GroupLayout usuariosPanelLayout = new javax.swing.GroupLayout(usuariosPanel);
        usuariosPanel.setLayout(usuariosPanelLayout);
        usuariosPanelLayout.setHorizontalGroup(
            usuariosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuariosPanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(usuariosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        usuariosPanelLayout.setVerticalGroup(
            usuariosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(usuariosPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pnlContainer.add(usuariosPanel);

        reportesPanel.setBackground(new java.awt.Color(243, 244, 246));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        );

        jPanel38.setBackground(new java.awt.Color(243, 244, 246));
        jPanel38.setForeground(new java.awt.Color(0, 0, 0));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\EDITAR.png")); // NOI18N
        jLabel45.setText("Editar");
        jPanel38.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        jLabel56.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\ELIMINAR.png")); // NOI18N
        jLabel56.setText("Eliminar");
        jPanel38.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, -1));

        jPanel39.setBackground(new java.awt.Color(243, 244, 246));

        jLabel59.setBackground(new java.awt.Color(0, 0, 0));
        jLabel59.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 0, 0));
        jLabel59.setText("Detalle de Ventas");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel59, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPanel40.setBackground(new java.awt.Color(243, 244, 246));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setBackground(new java.awt.Color(59, 130, 246));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\BOLETA.png")); // NOI18N
        jButton4.setText("Nueva Boleta");
        jButton4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel40.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 188, -1));

        jLabel58.setBackground(new java.awt.Color(0, 0, 0));
        jLabel58.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 0, 0));
        jLabel58.setText("Reportes");
        jPanel40.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 3, 270, -1));

        jButton5.setBackground(new java.awt.Color(59, 130, 246));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\DESCARGAR.png")); // NOI18N
        jButton5.setText("Exportar");
        jButton5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel40.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(585, 0, 188, -1));

        javax.swing.GroupLayout reportesPanelLayout = new javax.swing.GroupLayout(reportesPanel);
        reportesPanel.setLayout(reportesPanelLayout);
        reportesPanelLayout.setHorizontalGroup(
            reportesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportesPanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(reportesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        reportesPanelLayout.setVerticalGroup(
            reportesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportesPanelLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pnlContainer.add(reportesPanel);

        configuracionPanel.setBackground(new java.awt.Color(243, 244, 246));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        jLabel50.setText("Nombre de la tienda");

        jLabel52.setText("RUC");

        jLabel57.setText("Direccion");

        jLabel60.setText("Teléfono");

        jButton1.setText("Guardar");

        jButton2.setText("Editar");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel33Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel50)
                            .addComponent(jLabel57)
                            .addComponent(jTextField3)
                            .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(130, 130, 130)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel60))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(66, 66, 66))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel52))
                .addGap(18, 18, 18)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addGap(52, 52, 52)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanel41.setBackground(new java.awt.Color(243, 244, 246));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 0));
        jLabel39.setIcon(new javax.swing.ImageIcon("C:\\Users\\PC\\OneDrive\\Escritorio\\UNAMBA\\SEMESTRE IV\\GESTION DE PROYECTOS AGILES\\PROYECTO-FINAL\\SitemaDeVentaRopa\\src\\main\\resources\\com\\irissoft\\recursos\\TIENDA.png")); // NOI18N
        jLabel39.setText("Informarcion de la Tienda");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel39))
        );

        jPanel42.setBackground(new java.awt.Color(243, 244, 246));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setBackground(new java.awt.Color(0, 0, 0));
        jLabel51.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 0));
        jLabel51.setText("Configuración del Sistema");
        jPanel42.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 3, 290, -1));

        javax.swing.GroupLayout configuracionPanelLayout = new javax.swing.GroupLayout(configuracionPanel);
        configuracionPanel.setLayout(configuracionPanelLayout);
        configuracionPanelLayout.setHorizontalGroup(
            configuracionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configuracionPanelLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(configuracionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        configuracionPanelLayout.setVerticalGroup(
            configuracionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(configuracionPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        pnlContainer.add(configuracionPanel);

        jPanel1.add(pnlContainer);

        desktopPane.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bgCerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgCerrarSesionMouseEntered
        bgCerrarSesion.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgCerrarSesionMouseEntered

    private void bgCerrarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgCerrarSesionMouseExited
        bgCerrarSesion.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgCerrarSesionMouseExited

    private void lblDashBoardFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblDashBoardFocusGained

    }//GEN-LAST:event_lblDashBoardFocusGained

    private void lblDashBoardFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lblDashBoardFocusLost

    }//GEN-LAST:event_lblDashBoardFocusLost

    private void lblDashBoardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashBoardMouseEntered
        bgDashboard.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_lblDashBoardMouseEntered

    private void lblDashBoardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashBoardMouseExited
        bgDashboard.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblDashBoardMouseExited

    private void bgDashboardFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bgDashboardFocusGained

    }//GEN-LAST:event_bgDashboardFocusGained

    private void bgDashboardFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bgDashboardFocusLost

    }//GEN-LAST:event_bgDashboardFocusLost

    private void bgDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgDashboardMouseClicked
        dashboardPanel.setVisible(true);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false);
        configuracionPanel.setVisible(false);
    }//GEN-LAST:event_bgDashboardMouseClicked

    private void bgDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgDashboardMouseEntered
        bgDashboard.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgDashboardMouseEntered

    private void bgDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgDashboardMouseExited
        bgDashboard.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgDashboardMouseExited

    private void bgPuntoVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgPuntoVentaMouseEntered
        bgPuntoVenta.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgPuntoVentaMouseEntered

    private void bgPuntoVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgPuntoVentaMouseExited
        bgPuntoVenta.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgPuntoVentaMouseExited

    private void bgProductosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgProductosMouseEntered
        bgProductos.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgProductosMouseEntered

    private void bgProductosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgProductosMouseExited
        bgProductos.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgProductosMouseExited

    private void bgUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgUsuariosMouseEntered
        bgUsuarios.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgUsuariosMouseEntered

    private void bgUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgUsuariosMouseExited
        bgUsuarios.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgUsuariosMouseExited

    private void bgReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgReportesMouseClicked
        reportesPanel.setVisible(true);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false);
    }//GEN-LAST:event_bgReportesMouseClicked

    private void bgReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgReportesMouseEntered
        bgReportes.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgReportesMouseEntered

    private void bgReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgReportesMouseExited
        bgReportes.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgReportesMouseExited

    private void bgConfiguracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgConfiguracionMouseEntered
        bgConfiguracion.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_bgConfiguracionMouseEntered

    private void bgConfiguracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgConfiguracionMouseExited
        bgConfiguracion.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_bgConfiguracionMouseExited

    private void bgPuntoVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgPuntoVentaMouseClicked
        puntoDeVentaPanel.setVisible(true);
        dashboardPanel.setVisible(false);
    }//GEN-LAST:event_bgPuntoVentaMouseClicked

    private void bgPuntoVentaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgPuntoVentaMousePressed

    }//GEN-LAST:event_bgPuntoVentaMousePressed

    private void lblPuntoDeVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPuntoDeVentaMouseClicked
        configuracionPanel.setVisible(false);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(true);
        productosPanel.setVisible(false);     
    }//GEN-LAST:event_lblPuntoDeVentaMouseClicked

    private void bgProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgProductosMouseClicked
        productosPanel.setVisible(true);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        
    }//GEN-LAST:event_bgProductosMouseClicked

    private void bgUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgUsuariosMouseClicked
        usuariosPanel.setVisible(true);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false);
    }//GEN-LAST:event_bgUsuariosMouseClicked

    private void bgConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgConfiguracionMouseClicked
        configuracionPanel.setVisible(true);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false);     
    }//GEN-LAST:event_bgConfiguracionMouseClicked

    private void bgCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgCerrarSesionMouseClicked
        Login login = new Login();
        this.dispose();
        login.setVisible(true);
    }//GEN-LAST:event_bgCerrarSesionMouseClicked

    private void btnAddProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductoActionPerformed
        FrmAddProducto frmAddProducto = new FrmAddProducto();
        desktopPane.add(frmAddProducto);
        frmAddProducto.setVisible(true);
    }//GEN-LAST:event_btnAddProductoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        FrmAddUsuario frmAddUsuario =  new FrmAddUsuario();
        desktopPane.add(frmAddUsuario);
        frmAddUsuario.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProveedorActionPerformed
        FrmAddProveedor frmAddProveedor = new FrmAddProveedor();
        desktopPane.add(frmAddProveedor);
        frmAddProveedor.setVisible(true);
    }//GEN-LAST:event_btnAddProveedorActionPerformed

    private void lblPuntoDeVentaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPuntoDeVentaMouseEntered
        bgPuntoVenta.setBackground(new Color(249,250,251));
    }//GEN-LAST:event_lblPuntoDeVentaMouseEntered

    private void lblPuntoDeVentaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPuntoDeVentaMouseExited
        bgPuntoVenta.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblPuntoDeVentaMouseExited

    private void lblProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductoMouseEntered
        bgProductos.setBackground(new Color(249, 250, 251));
    }//GEN-LAST:event_lblProductoMouseEntered

    private void lblProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductoMouseExited
        bgProductos.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblProductoMouseExited

    private void lblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProductoMouseClicked
        configuracionPanel.setVisible(false);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(true); 
    }//GEN-LAST:event_lblProductoMouseClicked

    private void lblDashBoardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashBoardMouseClicked
        configuracionPanel.setVisible(false);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(true);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false); 
    }//GEN-LAST:event_lblDashBoardMouseClicked

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
        configuracionPanel.setVisible(false);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(true);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false); 
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void lblReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseClicked
        configuracionPanel.setVisible(false);
        reportesPanel.setVisible(true);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false); 
    }//GEN-LAST:event_lblReportesMouseClicked

    private void lblConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionMouseClicked
        configuracionPanel.setVisible(true);
        reportesPanel.setVisible(false);
        usuariosPanel.setVisible(false);
        dashboardPanel.setVisible(false);
        puntoDeVentaPanel.setVisible(false);
        productosPanel.setVisible(false); 
    }//GEN-LAST:event_lblConfiguracionMouseClicked

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered
        bgUsuarios.setBackground(new Color(249, 250, 251));
    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited
        bgUsuarios.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseEntered
        bgReportes.setBackground(new Color(249, 250, 251));
    }//GEN-LAST:event_lblReportesMouseEntered

    private void lblReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReportesMouseExited
        bgReportes.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblReportesMouseExited

    private void lblConfiguracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionMouseEntered
        bgConfiguracion.setBackground(new Color(249, 250, 251));
    }//GEN-LAST:event_lblConfiguracionMouseEntered

    private void lblConfiguracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblConfiguracionMouseExited
        bgConfiguracion.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_lblConfiguracionMouseExited

    private void lblCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarSesionMouseClicked
        Login login = new Login();
        this.dispose();
        login.setVisible(true);
    }//GEN-LAST:event_lblCerrarSesionMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        FrmFactura frmFactura = new FrmFactura();
        desktopPane.add(frmFactura);
        frmFactura.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnEliminarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarUsuarioMouseClicked
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (!validarFilaSeleccionada(filaSeleccionada)) {
            return;
        }

        int idUsuario = obtenerIdUsuarioSeleccionado(filaSeleccionada);
        confirmarYEliminarUsuario(idUsuario);
    }//GEN-LAST:event_btnEliminarUsuarioMouseClicked

    private int obtenerIdProductoSeleccionado(int fila) {
        return (int) tablaProductos.getValueAt(fila, 0); // Columna ID
    }

 
    
    // ========== LÓGICA ELIMINACIÓN ========== //
    private void confirmarYEliminarProducto(int idProducto) {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            ejecutarEliminacionProducto(idProducto);
        }
    }

    private void ejecutarEliminacionProducto(int idProducto) {
        boolean exito = ngProductos.delete(idProducto);

        if (exito) {
            actualizarTablaProductos();
            JOptionPane.showMessageDialog(
                    this,
                    "Producto eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

// ========== LÓGICA EDICIÓN ========== //
    private void abrirFormularioEdicionProducto(int idProducto) {
        DtProductos producto = ngProductos.getProductoById(idProducto);
        Object[] datosProducto = construirDatosProducto(producto);

        FrmEditarProducto frmEditar = new FrmEditarProducto(datosProducto);
        desktopPane.add(frmEditar);
        frmEditar.setVisible(true);
    }

    private Object[] construirDatosProducto(DtProductos producto) {
        return new Object[]{
            producto.getIdProducto(),
            producto.getNombreProducto(),
            producto.getDescripcion(),
            producto.getCantidad(),
            producto.getPrecio(),
            producto.getIdProveedor(),
            producto.getSku(),
            producto.getTalla(),
            producto.getColor()
        };
    }

    private void btnEditarUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarUsuarioMouseClicked
        int filaSeleccionada = tablaUsuarios.getSelectedRow();

        if (validarFilaSeleccionada(filaSeleccionada)) {
            int idUsuario = obtenerIdUsuarioSeleccionado(filaSeleccionada);
            abrirFormularioEdicionUsuario(idUsuario);
        }
    }//GEN-LAST:event_btnEditarUsuarioMouseClicked

    private void btnEditarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarProductoMouseClicked
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (validarFilaSeleccionada(filaSeleccionada)) {
            int idProducto = obtenerIdProductoSeleccionado(filaSeleccionada);
            abrirFormularioEdicionProducto(idProducto);
        }
    }//GEN-LAST:event_btnEditarProductoMouseClicked

    private void btnEliminarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarProductoMouseClicked
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (!validarFilaSeleccionada(filaSeleccionada)) {
            return;
        }

        int idProducto = obtenerIdProductoSeleccionado(filaSeleccionada);
        confirmarYEliminarProducto(idProducto);
    }//GEN-LAST:event_btnEliminarProductoMouseClicked

    private void btnPagarCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagarCompraMouseClicked
        FrmDetalleFactura frmDetalleFactura = new FrmDetalleFactura(this); 
        desktopPane.add(frmDetalleFactura);
        frmDetalleFactura.setVisible(true);
    }//GEN-LAST:event_btnPagarCompraMouseClicked

    private void btnAñadirCarritoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAñadirCarritoMouseClicked
        productoSeleccionado();
        
        
    }//GEN-LAST:event_btnAñadirCarritoMouseClicked

    private void btnQuitarProductoDeListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQuitarProductoDeListaMouseClicked
        eliminarProducto();
    }//GEN-LAST:event_btnQuitarProductoDeListaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<DtCarrito> ListaProductosVender;
    private javax.swing.JProgressBar barraProgreso1;
    private javax.swing.JProgressBar barraProgreso2;
    private javax.swing.JProgressBar barraProgreso3;
    private javax.swing.JPanel bgCerrarSesion;
    private javax.swing.JPanel bgConfiguracion;
    private javax.swing.JPanel bgDashboard;
    private javax.swing.JPanel bgProductos;
    private javax.swing.JPanel bgPuntoVenta;
    private javax.swing.JPanel bgReportes;
    private javax.swing.JPanel bgUsuarios;
    private javax.swing.JButton btnAddProducto;
    private javax.swing.JButton btnAddProveedor;
    private javax.swing.JLabel btnAñadirCarrito;
    private javax.swing.JLabel btnEditarProducto;
    private javax.swing.JLabel btnEditarUsuario;
    private javax.swing.JLabel btnEliminarProducto;
    private javax.swing.JLabel btnEliminarUsuario;
    private javax.swing.JLabel btnPagarCompra;
    private javax.swing.JLabel btnQuitarProductoDeLista;
    private javax.swing.JPanel configuracionPanel;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JLabel lblCerrarSesion;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblConfiguracion;
    private javax.swing.JLabel lblDashBoard;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblHora2;
    private javax.swing.JLabel lblHora3;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblMonto2;
    private javax.swing.JLabel lblMonto3;
    private javax.swing.JLabel lblNombreUsuario;
    private javax.swing.JLabel lblNumeroDeOrden;
    private javax.swing.JLabel lblNumeroDeOrden2;
    private javax.swing.JLabel lblNumeroDeOrden3;
    private javax.swing.JLabel lblOrdenes;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JLabel lblPuntoDeVenta;
    private javax.swing.JLabel lblReportes;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUstedes;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JLabel lblVentasHoy;
    private javax.swing.JLabel lblbienvenido;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel productosPanel;
    private javax.swing.JPanel puntoDeVentaPanel;
    private javax.swing.JPanel reportesPanel;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtBuscadorProductos;
    private javax.swing.JTextField txtBuscadorUsuarios;
    private javax.swing.JTextField txtBuscadorVentas;
    private javax.swing.JTextField txtCantidadProducto;
    private javax.swing.JPanel usuariosPanel;
    // End of variables declaration//GEN-END:variables

}

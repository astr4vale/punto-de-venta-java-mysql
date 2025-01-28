/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtProductos;
import com.irissoft.datos.DtProveedores;
import com.irissoft.repositorio.RpProductos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KALI
 */
public class QProductos implements RpProductos<DtProductos>{
     private ConexionBD con;

    @Override
    public int insert(DtProductos dt) {
        con = new ConexionBD();
    String sql = "INSERT INTO productos (nombreProducto, descripcion, cantidad, precio, idProveedor, sku, talla, color) " 
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dt.getNombreProducto());
            ps.setString(2, dt.getDescripcion());
            ps.setInt(3, dt.getCantidad());
            ps.setDouble(4, dt.getPrecio());
            ps.setInt(5, dt.getIdProveedor());
            ps.setString(6, dt.getSku());    // Nuevo campo
            ps.setString(7, dt.getTalla());  // Nuevo campo
            ps.setString(8, dt.getColor());  // Nuevo campo

            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        } catch (SQLException ex) {
             Logger.getLogger(QProductos.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
            try { if (con.conexion != null) con.conexion.close(); } 
            catch (SQLException ex) { System.err.println("Error al cerrar conexión: " + ex.getMessage()); }
        }
         return 0;
    }

    @Override
    public List<DtProductos> getAll() {
        con = new ConexionBD();
        List<DtProductos> productos = new ArrayList<>();
        String sql = "SELECT p.*, pr.nombre AS nombreProveedor FROM productos p " +
                     "LEFT JOIN proveedores pr ON p.idProveedor = pr.idProveedor";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DtProductos producto = new DtProductos();
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setIdProveedor(rs.getInt("idProveedor"));
                producto.setSku(rs.getString("sku"));     // Mapear nuevos campos
                producto.setTalla(rs.getString("talla"));
                producto.setColor(rs.getString("color"));

                // Datos del proveedor
                DtProveedores proveedor = new DtProveedores();
                proveedor.setNombre(rs.getString("nombreProveedor"));
                producto.setProveedor(proveedor);

                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return productos;
    }

    @Override
    public boolean delete(int idProducto) {
        con = new ConexionBD();
        String sql = "DELETE FROM productos WHERE idProducto = ?";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    @Override
    public boolean update(DtProductos dt) {
        con = new ConexionBD();
        // Consulta SQL actualizada con los nuevos campos
        String sql = "UPDATE productos SET nombreProducto=?, descripcion=?, cantidad=?, precio=?, idProveedor=?, sku=?, talla=?, color=? WHERE idProducto=?";

        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            // Parámetros en el orden correcto
            ps.setString(1, dt.getNombreProducto());
            ps.setString(2, dt.getDescripcion());
            ps.setInt(3, dt.getCantidad());
            ps.setDouble(4, dt.getPrecio());
            ps.setInt(5, dt.getIdProveedor());
            ps.setString(6, dt.getSku());
            ps.setString(7, dt.getTalla());
            ps.setString(8, dt.getColor());
            ps.setInt(9, dt.getIdProducto()); // WHERE clause

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    @Override
    public DtProductos getProductoById(int idProducto) {
        con = new ConexionBD();
        String sql = "SELECT * FROM productos WHERE idProducto = ?";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DtProductos producto = new DtProductos();
                // Campos obligatorios
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setIdProveedor(rs.getInt("idProveedor"));

                // Campos adicionales (¡NUEVO!)
                producto.setSku(rs.getString("sku"));
                producto.setTalla(rs.getString("talla"));
                producto.setColor(rs.getString("color"));

                return producto;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener producto por ID: " + e.getMessage());
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public List<DtProductos> buscarProductos(String searchTerm) {
        con = new ConexionBD();
        List<DtProductos> resultados = new ArrayList<>();
        String sql = "SELECT p.*, pr.nombre AS nombreProveedor FROM productos p "
                + "LEFT JOIN proveedores pr ON p.idProveedor = pr.idProveedor "
                + "WHERE p.nombreProducto LIKE ? OR p.descripcion LIKE ? "
                + "OR p.sku LIKE ? OR p.talla LIKE ? OR p.color LIKE ?"; // Added search fields

        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            String term = "%" + searchTerm + "%";
            ps.setString(1, term);
            ps.setString(2, term);
            ps.setString(3, term);  // SKU
            ps.setString(4, term);  // Talla
            ps.setString(5, term);  // Color

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DtProductos producto = new DtProductos();
                // Mapeo completo de todos los campos
                producto.setIdProducto(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setCantidad(rs.getInt("cantidad"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setIdProveedor(rs.getInt("idProveedor"));
                producto.setSku(rs.getString("sku"));       // Campo agregado
                producto.setTalla(rs.getString("talla"));   // Campo agregado
                producto.setColor(rs.getString("color"));   // Campo agregado

                DtProveedores proveedor = new DtProveedores();
                proveedor.setNombre(rs.getString("nombreProveedor"));
                producto.setProveedor(proveedor);

                resultados.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error en búsqueda de productos: " + e.getMessage());
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return resultados;
    }

    @Override
    public boolean existeProductoPorSKU(String sku) {
        con = new ConexionBD();
        String sql = "SELECT COUNT(*) FROM productos WHERE sku = ?";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ps.setString(1, sku);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si encuentra coincidencias
            }
            return false;

        } catch (SQLException e) {
            System.err.println("Error al verificar SKU: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con.conexion != null && !con.conexion.isClosed()) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}

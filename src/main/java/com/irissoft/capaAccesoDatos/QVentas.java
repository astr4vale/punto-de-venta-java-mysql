/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtProveedores;
import com.irissoft.datos.DtVentas;
import com.irissoft.repositorio.RpVentas;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class QVentas implements RpVentas<DtVentas> {

    private ConexionBD con;
    
    @Override
    public List<DtVentas> getAll() {
        con = new ConexionBD();
        List<DtVentas> productos = new ArrayList<>();
        String sql = "SELECT p.*, pr.nombre AS nombreProveedor FROM productos p "
                + "LEFT JOIN proveedores pr ON p.idProveedor = pr.idProveedor";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DtVentas producto = new DtVentas();
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
    public DtVentas getProductoById(int idProducto) {
        con = new ConexionBD();
        String sql = "SELECT * FROM productos WHERE idProducto = ?";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DtVentas producto = new DtVentas();
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
    public List<DtVentas> buscarVentas(String searchTerm) {
        con = new ConexionBD();
        List<DtVentas> resultados = new ArrayList<>();
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
                DtVentas producto = new DtVentas();
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

    @Override
    public boolean realizarVenta(int idUsuario, String dniRucCliente, String nombreCliente, String telefonoCliente, String direccionCliente, String productosJSON) {
        con = new ConexionBD(); // Crear conexión
        String sql = "{CALL RealizarVenta(?, ?, ?, ?, ?, ?)}";

        try (Connection connection = con.conexion; CallableStatement stmt = connection.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setString(2, dniRucCliente);
            stmt.setString(3, nombreCliente);
            stmt.setString(4, telefonoCliente);
            stmt.setString(5, direccionCliente);
            stmt.setString(6, productosJSON);

            stmt.execute(); // Ejecutar procedimiento
            return true;
        } catch (SQLException e) {
            Logger.getLogger(QVentas.class.getName()).log(Level.SEVERE, "Error en realizarVenta: " + e.getMessage(), e);
            return false;
        }
    }
    
    

}

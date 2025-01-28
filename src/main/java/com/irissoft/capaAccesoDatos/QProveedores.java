/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtProveedores;
import com.irissoft.repositorio.RpProveedores;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KALI
 */
public class QProveedores implements RpProveedores<DtProveedores> {

    private ConexionBD con;
    private String query;
    private int quantityRowsAffected;

    @Override
    public DtProveedores getProveedorById(int idProveedor) {
        con = new ConexionBD();
        String sql = "SELECT * FROM proveedores WHERE idProveedor = ?";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DtProveedores proveedor = new DtProveedores();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setRuc(rs.getString("ruc"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setDireccion(rs.getString("direccion"));
                return proveedor;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedor: " + e.getMessage());
        } finally {
            try { if (con.conexion != null) con.conexion.close(); } 
            catch (SQLException ex) { System.err.println("Error al cerrar conexión: " + ex.getMessage()); }
        }
        return null;
    }

    @Override
    public List<DtProveedores> getAll() {
        con = new ConexionBD();
        List<DtProveedores> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (PreparedStatement ps = con.conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DtProveedores proveedor = new DtProveedores();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setRuc(rs.getString("ruc"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        } finally {
            try { if (con.conexion != null) con.conexion.close(); } 
    catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return proveedores;
    }

    @Override
    public int insert(DtProveedores proveedor) {
        con = new ConexionBD();
        query = "INSERT INTO proveedores(ruc, nombre, telefono, direccion) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, proveedor.getRuc());
            ps.setString(2, proveedor.getNombre());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getDireccion());

            quantityRowsAffected = ps.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna el ID generado
                }
            }
            con.conexion.close();
        } catch (SQLException e) {
            System.err.println("Error insert: " + e.getMessage());
        }
        return -1; // Fallo en la inserción
    }

    @Override
    public boolean delete(int idProveedor) {
        con = new ConexionBD();
        query = "DELETE FROM proveedores WHERE idProveedor = ?";
        
        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ps.setInt(1, idProveedor);
            quantityRowsAffected = ps.executeUpdate();
            con.conexion.close();
            return quantityRowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error delete: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(DtProveedores proveedor) {
        con = new ConexionBD();
        query = "UPDATE proveedores SET ruc = ?, nombre = ?, telefono = ?, direccion = ? WHERE idProveedor = ?";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ps.setString(1, proveedor.getRuc());
            ps.setString(2, proveedor.getNombre());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getDireccion());
            ps.setInt(5, proveedor.getIdProveedor());

            quantityRowsAffected = ps.executeUpdate();
            con.conexion.close();
            return quantityRowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error update: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean existeProveedorPorRUC(String ruc) {
        con = new ConexionBD();
        query = "SELECT COUNT(*) FROM proveedores WHERE ruc = ?";
        try (PreparedStatement ps = con.conexion.prepareStatement(query)) {
            ps.setString(1, ruc);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Error al verificar RUC: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
            ex.getMessage();
            }
        }
    }
}

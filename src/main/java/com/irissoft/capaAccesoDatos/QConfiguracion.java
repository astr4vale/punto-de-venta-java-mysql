
package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtConfiguracion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QConfiguracion {

    private ConexionBD con;
    private String query;

    public void actualizarConfiguracion(DtConfiguracion config) {
        con = new ConexionBD();
        query = "UPDATE configuracion SET "
                + "nombreTienda = ?, "
                + "ruc = ?, "
                + "direccion = ?, "
                + "telefono = ? "
                + "WHERE idConfiguracion = ?";

        try (PreparedStatement ps = con.conexion.prepareStatement(query)) {
            ps.setString(1, config.getNombreTienda());
            ps.setString(2, config.getRuc());
            ps.setString(3, config.getDireccion());
            ps.setString(4, config.getTelefono());
            ps.setInt(5, 1);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar configuración: " + e.getMessage());
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

    public DtConfiguracion obtenerConfiguracion() {
        con = new ConexionBD();
        DtConfiguracion config = null;

        query = "SELECT * FROM configuracion LIMIT 1";

        try (PreparedStatement ps = con.conexion.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                config = new DtConfiguracion();
                config.setNombreTienda(rs.getString("nombreTienda"));
                config.setRuc(rs.getString("ruc"));
                config.setDireccion(rs.getString("direccion"));
                config.setTelefono(rs.getString("telefono"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener configuración: " + e.getMessage());
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }

        return config;
    }

}

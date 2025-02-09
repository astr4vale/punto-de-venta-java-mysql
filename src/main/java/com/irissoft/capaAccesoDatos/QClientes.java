package com.irissoft.capaAccesoDatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QClientes {

    private ConexionBD conexion;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public QClientes() {
        this.conexion = new ConexionBD();
    }

    public class ClienteDatos {

        public String nombre;
        public String telefono;
        public String direccion;

        public ClienteDatos(String nombre, String telefono, String direccion) {
            this.nombre = nombre;
            this.telefono = telefono;
            this.direccion = direccion;
        }
    }

    public ClienteDatos verificarClienteExistente(String dniRuc) {
        ClienteDatos datos = null;

        try {
            String consulta = "SELECT nombre, telefono, direccion FROM clientes WHERE dniRuc = ?";
            pstmt = conexion.conexion.prepareStatement(consulta);
            pstmt.setString(1, dniRuc);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                datos = new ClienteDatos(
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                );
            }
        } catch (SQLException ex) {
            System.out.println("Error al verificar cliente: " + ex.getMessage());
        } finally {
            cerrarRecursos();
        }

        return datos;
    }

    private void cerrarRecursos() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar recursos: " + ex.getMessage());
        }
    }
}

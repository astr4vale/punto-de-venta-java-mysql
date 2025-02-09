
package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtUsuarios;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.irissoft.repositorio.RpUsuarios;
import java.sql.ResultSet;
import java.util.ArrayList;


public class QUsuarios implements RpUsuarios<DtUsuarios> {

    private ConexionBD con;
    private List<DtUsuarios> listaUsuarios;
    private String query;
    private int quantityRowsAffected;

    @Override
    public int insert(DtUsuarios dt) {
        con = new ConexionBD(); // Asumo que ya tienes una clase ConexionBD para manejar la conexión a la base de datos
        query = "INSERT INTO usuarios (dniUsuario, nombreUsuario, telefono, direccion, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ps.setString(1, dt.getDniUsuario());
            ps.setString(2, dt.getNombreUsuario());
            ps.setString(3, dt.getTelefono());
            ps.setString(4, dt.getDireccion());
            ps.setString(5, dt.getCorreo());
            ps.setString(6, dt.getContrasena());
            ps.setString(7, dt.getRol());

            quantityRowsAffected = ps.executeUpdate(); // Ejecuta la inserción
            con.conexion.close(); // Cierra la conexión

            return quantityRowsAffected; // Retorna el número de filas afectadas

        } catch (SQLException e) {
            System.out.println("Error en insert: " + e.getMessage());
            return 0; // Retorna 0 si hay un error
        }
    }

    @Override
    public List<DtUsuarios> getAll() {
        con = new ConexionBD();
        listaUsuarios = new ArrayList<>();

        query = "SELECT * FROM usuarios ORDER BY fechaCreacion DESC";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ResultSet rows = ps.executeQuery();

            while (rows.next()) {
                DtUsuarios dtUsuario = new DtUsuarios();

                dtUsuario.setIdUsuario(rows.getInt("idUsuario"));
                dtUsuario.setDniUsuario(rows.getString("dniUsuario"));
                dtUsuario.setNombreUsuario(rows.getString("nombreUsuario"));
                dtUsuario.setTelefono(rows.getString("telefono"));
                dtUsuario.setDireccion(rows.getString("direccion"));
                dtUsuario.setCorreo(rows.getString("correo"));
                dtUsuario.setContrasena(rows.getString("contrasena"));
                dtUsuario.setRol(rows.getString("rol"));
                dtUsuario.setFechaCreacion(rows.getTimestamp("fechaCreacion"));
                dtUsuario.setFechaActualizacion(rows.getTimestamp("fechaActualizacion"));

                listaUsuarios.add(dtUsuario);
            }

            con.conexion.close();
            return listaUsuarios;

        } catch (SQLException ex) {
            System.out.println("Error en getAll: " + ex.getMessage());
            return listaUsuarios;
        } finally {
            if (con.conexion != null) {
                try {
                    con.conexion.close();
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    }

    @Override
    public boolean delete(String idUsuario) {
        con = new ConexionBD();
        query = "DELETE FROM usuarios WHERE idUsuario = ?";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(idUsuario)); // Asumiendo que idUsuario es INT en la BD
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            System.err.println("Error al eliminar usuario: " + ex.getMessage());
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
    public boolean update(DtUsuarios dt) {
        con = new ConexionBD();
        query = "UPDATE usuarios SET dniUsuario = ?, nombreUsuario = ?, telefono = ?, "
                + "direccion = ?, correo = ?, contrasena = ?, rol = ?, fechaActualizacion = ? WHERE idUsuario = ?";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);

            // Configuración de los parámetros para la consulta SQL
            ps.setString(1, dt.getDniUsuario());
            ps.setString(2, dt.getNombreUsuario());
            ps.setString(3, dt.getTelefono());
            ps.setString(4, dt.getDireccion());
            ps.setString(5, dt.getCorreo());
            ps.setString(6, dt.getContrasena());
            ps.setString(7, dt.getRol());
            ps.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime())); // Fecha de actualización
            ps.setInt(9, dt.getIdUsuario()); // ID del usuario a actualizar

            // Ejecutar la actualización
            int rowsAffected = ps.executeUpdate();
            con.conexion.close();

            // Retornar true si al menos una fila fue afectada
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
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
    public DtUsuarios getUsuarioPorId(int idUsuario) {
        con = new ConexionBD();
        DtUsuarios usuario = null;
        query = "SELECT * FROM usuarios WHERE idUsuario = ?"; // Usar nombre de columna correcto

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new DtUsuarios();

                // Mapear todas las columnas igual que en getAll()
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setDniUsuario(rs.getString("dniUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setRol(rs.getString("rol"));
                usuario.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
                usuario.setFechaActualizacion(rs.getTimestamp("fechaActualizacion"));
            }

        } catch (SQLException e) {
            System.err.println("Error en getUsuarioPorId: " + e.getMessage());
        } finally {
            if (con.conexion != null) {
                try {
                    con.conexion.close(); // Cerrar conexión igual que en otros métodos
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }

        return usuario; // Retornará null si no se encuentra
    }

    @Override
    public List<DtUsuarios> buscarUsuarios(String searchTerm) {
        con = new ConexionBD();
        List<DtUsuarios> resultados = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE dniUsuario LIKE ? OR nombreUsuario LIKE ?";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(sql);
            String termino = "%" + searchTerm + "%";
            ps.setString(1, termino);
            ps.setString(2, termino);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DtUsuarios usuario = new DtUsuarios();
                // Mapear todos los campos como en getAll()
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setDniUsuario(rs.getString("dniUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setTelefono(rs.getString("telefono")); // <- Campo faltante
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setRol(rs.getString("rol")); // <- Campo faltante
                resultados.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error en buscarUsuarios: " + e.getMessage());
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
    public DtUsuarios autenticarUsuario(String identificador, String contrasena) {
        con = new ConexionBD();
        DtUsuarios usuario = null;
        query = "SELECT * FROM usuarios WHERE (correo = ? OR dniUsuario = ?) AND contrasena = ?";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ps.setString(1, identificador);
            ps.setString(2, identificador);
            ps.setString(3, contrasena);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new DtUsuarios();
                // Mapear todos los campos necesarios
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setDniUsuario(rs.getString("dniUsuario"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario")); // Asegúrate de mapear el nombre
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticarUsuario: " + e.getMessage());
        } finally {
            if (con.conexion != null) {
                try {
                    con.conexion.close();
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
        return usuario;
    }

}

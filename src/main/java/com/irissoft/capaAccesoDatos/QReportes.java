package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtReportes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QReportes  {

    private ConexionBD con;
    private List<DtReportes> listaVentas;
    private String query;

    public List<DtReportes> obtenerVentas() {
        con = new ConexionBD();
        listaVentas = new ArrayList<>();
        query = "SELECT * FROM VistaVentas";

        try {
            PreparedStatement ps = con.conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DtReportes venta = new DtReportes();
                venta.setUsuario(rs.getString("Usuario"));
                venta.setCliente(rs.getString("Cliente"));
                venta.setDniRuc(rs.getString("DNI_RUC"));
                venta.setTotal(rs.getDouble("Total"));
                venta.setFecha(rs.getDate("Fecha"));
                venta.setItemsVendidos(rs.getInt("Items_Vendidos"));
                venta.setCategoriaCliente(rs.getString("Categoria_Cliente"));
                listaVentas.add(venta);
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenerVentas: " + e.getMessage());
        } finally {
            try {
                if (con.conexion != null) {
                    con.conexion.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
        return listaVentas;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaAccesoDatos;

import com.irissoft.datos.DtDashboard;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author KALI
 */
public class QDashboard {
    
    private ConexionBD con;

    public DtDashboard obtenerDatosDashboard() {
        con = new ConexionBD();
        DtDashboard dashboard = new DtDashboard();
        
        try {
            CallableStatement cs = con.conexion.prepareCall("{CALL ObtenerDatosDashboard()}");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                dashboard.setTotalVentas(rs.getDouble("TotalVentas"));
                dashboard.setTotalOrdenes(rs.getInt("TotalOrdenes"));
                dashboard.setTotalClientes(rs.getInt("TotalClientes"));
                dashboard.setTotalProductos(rs.getInt("TotalProductos"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener datos del dashboard: " + e.getMessage());
        } finally {
            if (con.conexion != null) {
                try {
                    con.conexion.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
        
        return dashboard;
    }
}
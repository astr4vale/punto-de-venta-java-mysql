
package com.irissoft.datos;


public class DtDashboard {

    private double totalVentas;
    private int totalOrdenes;
    private int totalClientes;
    private int totalProductos;

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public void setTotalOrdenes(int totalOrdenes) {
        this.totalOrdenes = totalOrdenes;
    }

    public void setTotalClientes(int totalClientes) {
        this.totalClientes = totalClientes;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public int getTotalOrdenes() {
        return totalOrdenes;
    }

    public int getTotalClientes() {
        return totalClientes;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    
}


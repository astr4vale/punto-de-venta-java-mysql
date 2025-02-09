
package com.irissoft.datos;

import java.util.Date;

public class DtReportes extends DtGeneric {

    private String usuario;
    private String cliente;
    private String dniRuc;
    private double total;
    private Date fecha;
    private int itemsVendidos;
    private String categoriaCliente;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDniRuc() {
        return dniRuc;
    }

    public void setDniRuc(String dniRuc) {
        this.dniRuc = dniRuc;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getItemsVendidos() {
        return itemsVendidos;
    }

    public void setItemsVendidos(int itemsVendidos) {
        this.itemsVendidos = itemsVendidos;
    }

    public String getCategoriaCliente() {
        return categoriaCliente;
    }

    public void setCategoriaCliente(String categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
    }

}

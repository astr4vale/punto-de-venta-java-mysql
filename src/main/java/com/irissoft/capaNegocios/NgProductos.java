/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QProductos;
import com.irissoft.datos.DtProductos;
import com.irissoft.repositorio.RpProductos;
import java.util.List;

/**
 *
 * @author KALI
 */
public class NgProductos {

    private final RpProductos<DtProductos> rpProductos;

    public NgProductos() {
        rpProductos = new QProductos();
    }

    // Insertar producto (sin validaciones)
    public boolean insert(DtProductos producto) {
        return rpProductos.insert(producto) > 0;
    }

    // Actualizar producto (sin validaciones)
    public boolean update(DtProductos producto) {
        return rpProductos.update(producto);
    }

    // Otros métodos
    public List<DtProductos> getAll() {
        return rpProductos.getAll();
    }

    public boolean delete(int idProducto) {
        return rpProductos.delete(idProducto);
    }

    public DtProductos getProductoById(int idProducto) {
        return rpProductos.getProductoById(idProducto);
    }

    public List<DtProductos> buscarProductos(String searchTerm) {
        return rpProductos.buscarProductos(searchTerm);
    }

    public boolean existeProductoPorSKU(String sku) {
        return rpProductos.existeProductoPorSKU(sku);
    }
}

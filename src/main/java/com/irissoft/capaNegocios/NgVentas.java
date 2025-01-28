/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QVentas;
import com.irissoft.datos.DtVentas;
import com.irissoft.repositorio.RpVentas;
import java.util.List;

/**
 *
 * @author KALI
 */
public class NgVentas {

    private final RpVentas<DtVentas> rpVentas;

    public NgVentas() {
        rpVentas = new QVentas();
    }

    // Insertar producto (sin validaciones)
    public boolean insert(DtVentas producto) {
        return rpVentas.insert(producto) > 0;
    }

    // Actualizar producto (sin validaciones)
    public boolean update(DtVentas producto) {
        return rpVentas.update(producto);
    }

    // Otros métodos
    public List<DtVentas> getAll() {
        return rpVentas.getAll();
    }

    public boolean delete(int idProducto) {
        return rpVentas.delete(idProducto);
    }

    public DtVentas getProductoById(int idProducto) {
        return rpVentas.getProductoById(idProducto);
    }

    public List<DtVentas> buscarVentas(String searchTerm) {
        return rpVentas.buscarVentas(searchTerm);
    }

    public boolean existeProductoPorSKU(String sku) {
        return rpVentas.existeProductoPorSKU(sku);
    }

}

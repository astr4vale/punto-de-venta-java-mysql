/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QVentas;
import com.irissoft.datos.DtDetalleVenta;
import com.irissoft.datos.DtVentas;
import com.irissoft.repositorio.RpVentas;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author KALI
 */
public class NgVentas {

    private final RpVentas<DtVentas> rpVentas;

    public NgVentas() {
        rpVentas = new QVentas();
    }

    public boolean realizarVenta(int idUsuario, String dniRucCliente, String nombreCliente, String telefonoCliente, String direccionCliente, String carritoJson) {
        // El JSON ya está recibido como un String, no es necesario convertirlo nuevamente
        // Realizar la venta utilizando los datos de la venta y el carrito (en formato JSON)

        // Ahora pasamos el JSON a la capa de acceso a datos para insertar la venta.
        return rpVentas.realizarVenta(idUsuario, dniRucCliente, nombreCliente, telefonoCliente, direccionCliente, carritoJson);
    }

    // Otros métodos
    public List<DtVentas> getAll() {
        return rpVentas.getAll();
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

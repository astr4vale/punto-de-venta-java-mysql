/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.repositorio;

import java.util.List;

/**
 *
 * @author KALI
 */
public interface RpVentas<Dt> {

    List<Dt> getAll();

    Dt getProductoById(int idProducto);

    List<Dt> buscarVentas(String searchTerm);

    boolean existeProductoPorSKU(String sku);
    
    boolean realizarVenta(int idUsuario, String dniRucCliente, String nombreCliente, String telefonoCliente, String direccionCliente, String productosJSON);

}

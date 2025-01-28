/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.irissoft.repositorio;

import java.util.List;

/**
 *
 * @author KALI
 */
public interface RpProductos<Dt> {

    int insert(Dt dt);

    List<Dt> getAll();

    boolean delete(int idProducto);

    boolean update(Dt dt);

    Dt getProductoById(int idProducto);

    List<Dt> buscarProductos(String searchTerm);
    
    boolean existeProductoPorSKU(String sku);

}

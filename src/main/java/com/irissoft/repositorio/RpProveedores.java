/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.irissoft.repositorio;

import com.irissoft.datos.DtProveedores;
import java.util.List;

/**
 *
 * @author KALI
 */
public interface RpProveedores<Dt> {

    int insert(Dt proveedor);

    boolean delete(int idProveedor);

    boolean update(Dt proveedor);

    Dt getProveedorById(int idProveedor);

    public List<DtProveedores> getAll();

    boolean existeProveedorPorRUC(String ruc); // Nuevo método

}

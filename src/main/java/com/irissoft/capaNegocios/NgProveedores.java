/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QProveedores;
import com.irissoft.datos.DtProveedores;
import com.irissoft.repositorio.RpProveedores;
import java.util.List;

/**
 *
 * @author KALI
 */
public class NgProveedores {

    private final RpProveedores<DtProveedores> rpProveedores;

    public NgProveedores() {
        rpProveedores = new QProveedores();
    }

    public boolean insert(DtProveedores proveedor) {
        return rpProveedores.insert(proveedor) > 0;
    }

    // Actualizar proveedor (sin validaciones)
    public boolean update(DtProveedores proveedor) {
        return rpProveedores.update(proveedor);
    }

    // Eliminar proveedor
    public boolean delete(int idProveedor) {
        return rpProveedores.delete(idProveedor);
    }

    public boolean existeProveedor(int idProveedor) {
        return rpProveedores.getProveedorById(idProveedor) != null;
    }

    public List<DtProveedores> getAllProveedores() {
        return rpProveedores.getAll();
    }

    // Validar existencia de RUC
    public boolean existeProveedorPorRUC(String ruc) {
        return rpProveedores.existeProveedorPorRUC(ruc);
    }

}

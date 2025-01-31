/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QUsuarios;
import com.irissoft.datos.DtUsuarios;
import com.irissoft.repositorio.RpUsuarios;
import java.util.List;

/**
 *
 * @author KALI
 */
public class NgUsuarios {

    private final RpUsuarios<DtUsuarios> rpUsuarios;

    public NgUsuarios() {
        rpUsuarios = new QUsuarios();
    }


    public boolean insert(String dniUsuario,
            String nombreUsuario,
            String telefono,
            String direccion,
            String correo,
            String contrasena,
            String rol) {

        DtUsuarios dtUsuarios = new DtUsuarios();

        dtUsuarios.setDniUsuario(dniUsuario);
        dtUsuarios.setNombreUsuario(nombreUsuario);
        dtUsuarios.setTelefono(telefono);
        dtUsuarios.setDireccion(direccion);
        dtUsuarios.setCorreo(correo);
        dtUsuarios.setContrasena(contrasena);
        dtUsuarios.setRol(rol);

        return rpUsuarios.insert(dtUsuarios) > 0;
    }

    public List<DtUsuarios> getAll() {
        return rpUsuarios.getAll();
    }

    public boolean delete(String idUsuario) {
        return rpUsuarios.delete(idUsuario);
    }

    public boolean update(DtUsuarios usuario) {
        return rpUsuarios.update(usuario);
    }
    
    public DtUsuarios getUsuarioPorId(int idUsuario) {
        return rpUsuarios.getUsuarioPorId(idUsuario);
    }

    public List<DtUsuarios> buscarUsuarios(String searchTerm) {
        return rpUsuarios.buscarUsuarios(searchTerm);
    }

    public DtUsuarios iniciarSesion(String identificador, String contrasena) {
        // Validación básica de parámetros
        if (identificador == null || identificador.isEmpty() || contrasena == null || contrasena.isEmpty()) {
            return null;
        }

        return rpUsuarios.autenticarUsuario(identificador, contrasena);
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.irissoft.repositorio;

import java.util.List;

public interface RpUsuarios<Dt> {

    int insert(Dt dt);

    List<Dt> getAll();

    boolean delete(String idUsuario);

    boolean update(Dt dt);

    Dt getUsuarioPorId(int idUsuario);

    List<Dt> buscarUsuarios(String searchTerm);

    Dt autenticarUsuario(String identificador, String contrasena);
}

package com.sena.practica03.services;

import java.util.List;

import com.sena.practica03.dtos.UsuarioDto;
import com.sena.practica03.dtos.UsuarioDtoResponse;

public interface UsuarioServices {

    // registrar un nuevo usuario
    UsuarioDtoResponse guardar(UsuarioDto usuarioDto);

    // Listar todos los usuarios
    List<UsuarioDtoResponse> listarTodos();

    // consultar usuario por id
    UsuarioDtoResponse buscarPorId(Long id);

    // consultar usuario por numero de documento
    UsuarioDtoResponse buscarPorNumDoc(String numDoc);

    // Actualizar Usuario
    UsuarioDtoResponse actualizar(Long id, UsuarioDto usuarioDto);

    // Eliminar Usuario con cuerpo
    void eliminar(Long id);

}

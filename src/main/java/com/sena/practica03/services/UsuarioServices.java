package com.sena.practica03.services;

import java.util.List;

import com.sena.practica03.dtos.UsuarioDto;

public interface UsuarioServices {

    //registrar un nuevo usuario
    UsuarioDto guardar(UsuarioDto usuarioDto);

    //Listar todos los usuarios
    List<UsuarioDto> listarTodos();

    //consultar usuario por id
    UsuarioDto buscarPorId(Long id);

    //Actualizar Usuario
    UsuarioDto actualizar(Long id, UsuarioDto usuarioDto);

    //Eliminar Usuario con cuerpo
    void eliminar(Long id);

}

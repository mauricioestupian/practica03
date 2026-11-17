package com.sena.practica03.services;

import java.util.List;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;

public interface ClienteServices {

    ClienteDtoResponse guardar(ClienteDto clienteDto);

    List<ClienteDtoResponse> listarTodos();

    ClienteDtoResponse buscaCliId(Long id);

    ClienteDtoResponse actualizar(Long id, ClienteDto clienteDto);

    void eliminar(Long id);

}

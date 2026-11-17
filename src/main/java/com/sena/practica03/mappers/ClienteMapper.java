package com.sena.practica03.mappers;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;
import com.sena.practica03.models.usuarios.Cliente;

public interface ClienteMapper {

    //de Cliente a ClienteDto
    ClienteDtoResponse toDto(Cliente cliente);

    //de ClienteDto a Cliente
    Cliente toEntidad(ClienteDto clienteDto);

}

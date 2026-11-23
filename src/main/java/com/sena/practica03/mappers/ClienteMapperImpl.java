package com.sena.practica03.mappers;

import org.springframework.stereotype.Component;

import com.sena.practica03.dtos.ClienteDto;
import com.sena.practica03.dtos.ClienteDtoResponse;
import com.sena.practica03.models.usuarios.Cliente;

@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public ClienteDtoResponse toDto(Cliente cliente) {

        // valida que tenga datos
        if (cliente == null) {
            return null;
        }

        // concertir de Cliente a ClienteDTO
        return ClienteDtoResponse.builder()
                // atributos de UsuarioDtoResponse
                .id(cliente.getId())
                .nomb(cliente.getNom())
                .apell(cliente.getApe())
                .direc(cliente.getDir())
                .tele(cliente.getTel())
                // Nombre del tipo de documento
                .tipoDoc(cliente.getTipoDoc().getNombre())
                .numDoc(cliente.getNumDoc())
                // Ubicación
                .ciudad(cliente.getCiudad().getNombre())
                .departamento(
                        cliente.getCiudad()
                                .getDepartamento()
                                .getNombre())

                // atributos de ClienteDtoResponse
                .tipCli(cliente.getTipoCliente().getNom_TipoCli())
                .pts(cliente.getPuntos())
                .build();
    }

    @Override
    public Cliente toEntidad(ClienteDto clienteDto) {

        // valida que tenga datos
        if (clienteDto == null) {
            return null;
        }

        return Cliente.builder()
                // Atrubutos de usuarioDto
                .id(clienteDto.getId())
                .nom(clienteDto.getNomb())
                .ape(clienteDto.getApell())
                .tel(clienteDto.getTele())
                .dir(clienteDto.getDirec())
                .numDoc(clienteDto.getNumDoc())
                // atributos de ClienteDto
                .tipoCliente(null) // Se establece como null, ya que se asignará posteriormente en el servicio
                .tipoDoc(null) // Se establece como null, ya que se asignará posteriormente en el servicio
                .ciudad(null)

                // No es necesario mapear tipo de cliente porque
                // tiene relacion con la entidad TiposCliente
                .puntos(0) // Inicializa los puntos en 0 al crear un nuevo cliente
                .build();
    }

}

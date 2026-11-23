package com.sena.practica03.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sena.practica03.dtos.UsuarioDto;
import com.sena.practica03.dtos.UsuarioDtoResponse;
import com.sena.practica03.models.usuarios.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // convertir de Entidad a DTO
    @Mapping(source = "nom", target = "nomb")
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    @Mapping(source = "tipoDoc.nombre", target = "tipoDoc")
    @Mapping(source = "numDoc", target = "numDoc")
    @Mapping(source = "ciudad.nombre", target = "ciudad")
    @Mapping(source = "ciudad.departamento.nombre", target = "departamento")
    UsuarioDtoResponse convierteaDto(Usuario usuario);

    // convertir de DTO a Entidad
    @Mapping(source = "nomb", target = "nom")
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    @Mapping(source = "numDoc", target = "numDoc")

    // Las relaciones se asignan desde el services
    @Mapping(target = "tipoDoc", ignore = true)
    @Mapping(target = "ciudad", ignore = true)
    Usuario convierteaEntidad(UsuarioDto usuarioDto);

}

package com.sena.practica03.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;
import com.sena.practica03.models.usuarios.Trabajador;

@Mapper(componentModel = "spring")
public interface TrabajadorMapper {

    // convierte de Entidad a DTO
    // Trabajador a TrabajadorDto
    @Mapping(source = "nomb", target = "nom")
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    @Mapping(source = "numDoc", target = "numDoc")
    // para cargo y tipo de documento, el service se encargará de buscarlos y
    // asignarlos.
    @Mapping(target = "cargo", ignore = true) // AQUÍ: Le dice a MapStruct que ignore el campo y no intente convertirlo
    @Mapping(target = "tipoDoc", ignore = true)
    Trabajador toEntidad(TrabajadorDto trabajadorDto);

    // 2. De Entidad a DTO de Respuesta (devuelve el nombre del cargo)
    @Mapping(source = "nom", target = "nomb")
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    // Cargo del trabajador
    @Mapping(source = "cargo.cargoTrab", target = "cargo")

    // Tipo de documento
    // necesario mapear el id y el nombre del tipo de documento
    // por los dos dtos uno de ingreso y otro de respuesta
    @Mapping(source = "tipoDoc.id", target = "tipoDoc")
    @Mapping(source = "tipoDoc.nombre", target = "nomTipoDoc")

    // Número de documento
    @Mapping(source = "numDoc", target = "numDoc")
    TrabajadorDtoResponse toDto(Trabajador trabajador);
}

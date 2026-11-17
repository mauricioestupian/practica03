package com.sena.practica03.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;
import com.sena.practica03.models.usuarios.Trabajador;

@Mapper (componentModel = "spring")
public interface TrabajadorMapper {

    //convierte de Entidad a DTO
    //Trabajador a TrabajadorDto
    // 1. De DTO de Entrada a Entidad 
   // 1. De DTO de Entrada (recibe el código del cargo) a Entidad
    @Mapping(source = "nomb", target = "nom")
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    // para cargo El service se encargará de buscarlo y asignarlo.
    @Mapping(target = "cargo", ignore = true) // AQUÍ: Le dice a MapStruct que ignore el campo y no intente convertirlo
    Trabajador toEntidad(TrabajadorDto trabajadorDto);

    // 2. De Entidad a DTO de Respuesta (devuelve el nombre del cargo)
    @Mapping(source = "nom", target = "nomb")
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    @Mapping(source = "cargo.cargoTrab", target = "cargo") // Mapea el texto del cargo directamente al String del DTO
    TrabajadorDtoResponse toDto(Trabajador trabajador);
}



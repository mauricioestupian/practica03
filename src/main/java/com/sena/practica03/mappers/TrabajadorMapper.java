package com.sena.practica03.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sena.practica03.dtos.TrabajadorDto;
import com.sena.practica03.dtos.TrabajadorDtoResponse;
import com.sena.practica03.models.usuarios.Trabajador;

@Mapper(componentModel = "spring")
public interface TrabajadorMapper {

    // convierte de DTO a Entidad
    // TrabajadorDto a Trabajador
    @Mapping(source = "nomb", target = "nom")
    @Mapping(source = "apell", target = "ape")
    @Mapping(source = "direc", target = "dir")
    @Mapping(source = "tele", target = "tel")
    @Mapping(source = "numDoc", target = "numDoc")
    // para cargo, tipo de documento y ciudad, el service se encargará de buscarlos
    // y
    // asignarlos.
    @Mapping(target = "cargo", ignore = true) // AQUÍ: Le dice a MapStruct que ignore el campo y no intente convertirlo
    @Mapping(target = "tipoDoc", ignore = true)
    @Mapping(target = "ciudad", ignore = true)
    Trabajador toEntidad(TrabajadorDto trabajadorDto);

    // 2. De Entidad a DTO de Respuesta (devuelve el nombre del cargo)
    @Mapping(source = "nom", target = "nomb")
    @Mapping(source = "ape", target = "apell")
    @Mapping(source = "dir", target = "direc")
    @Mapping(source = "tel", target = "tele")
    // Nombre del tipo de documento y Documento
    @Mapping(source = "tipoDoc.nombre", target = "tipoDoc")
    @Mapping(source = "numDoc", target = "numDoc")

    // UbicacionNombre de la ciudad y departamento
    @Mapping(source = "ciudad.nombre", target = "ciudad")
    @Mapping(source = "ciudad.departamento.nombre", target = "departamento")

    // Cargo del trabajador
    @Mapping(source = "cargo.cargoTrab", target = "cargo")
    // Sueldo del trabajador no es necesario mapearlo, ya que el nombre del campo es
    // el mismo en ambas clases.

    TrabajadorDtoResponse toDto(Trabajador trabajador);
}

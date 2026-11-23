package com.sena.practica03.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TrabajadorDtoResponse extends UsuarioDtoResponse {

    private String cargo;
    private Double sueldo;
    // eliminamos nomTipoDoc, porque el nombre del
    // documento ya está representado por tipoDoc en la clase padre.

}

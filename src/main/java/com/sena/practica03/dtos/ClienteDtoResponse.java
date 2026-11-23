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
public class ClienteDtoResponse extends UsuarioDtoResponse {

    private String tipCli;
    private int pts;

    // extraemos el tipo de documento para mostrarlo en la respuesta
    private String nomTipoDoc;

}

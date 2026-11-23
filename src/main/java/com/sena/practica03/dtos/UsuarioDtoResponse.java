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
public class UsuarioDtoResponse {

    private Long id;

    private String nomb;

    private String apell;

    private String direc;

    private String tele;

    // Nombre del tipo de documento
    private String tipoDoc;

    private String numDoc;

    // Ubicación
    private String ciudad;

    private String departamento;
}

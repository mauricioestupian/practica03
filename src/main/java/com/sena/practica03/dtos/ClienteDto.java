package com.sena.practica03.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClienteDto extends UsuarioDto {

    private Byte tipCli;
    // se quita recepcion de puntos al crear el cliente, ya que se inicializa en 0

}
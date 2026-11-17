package com.sena.practica03.dtos;

import com.sena.practica03.enums.TipoCliente;

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
public class ClienteDto extends UsuarioDto{

    private Byte tipCli;
    private Integer pts;

}
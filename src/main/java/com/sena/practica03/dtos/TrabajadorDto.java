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
public class TrabajadorDto extends UsuarioDto{

    private Byte cargo;
    private Double sueldo;

}

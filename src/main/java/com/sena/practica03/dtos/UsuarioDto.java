package com.sena.practica03.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@SuperBuilder 
public class UsuarioDto {

    private Long id;

    @NotBlank (message = "El nombre es obligatorio")
    @Size (min = 2 , max = 60,
        message = "El nombre debe tener entre 2 y 60 caracteres"
    )
    private String nomb;

    @NotBlank (message = "El apellido es obligatorio")
    @Size (min = 2 , max = 60,
        message = "El apellido debe tener entre 2 y 60 caracteres"
    )
    private String apell;

    @Size (max = 120,
        message = "La dirección no puede superar los 120 caracteres"
    )
    private String direc;

    //comentario
    @Pattern (regexp = "^[0-9+\\-]+$", message = "El teléfono contine caracteres no permitidos")
    private String tele;

}

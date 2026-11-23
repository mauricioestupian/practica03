package com.sena.practica03.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UsuarioDto {

    private Long id;

    // nomb no puede ser nulo ni vacío, debe tener entre 2 y 60 caracteres
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 y 60 caracteres")
    private String nomb;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 60, message = "El apellido debe tener entre 2 y 60 caracteres")
    private String apell;

    @Size(max = 120, message = "La dirección no puede superar los 120 caracteres")
    private String direc;

    @Pattern(regexp = "^[0-9+\\-]+$", message = "El teléfono contine caracteres no permitidos")
    private String tele;

    // tipoDoc no puede ser nulo
    @NotNull(message = "El tipo de documento es obligatorio")
    private Byte tipoDoc;

    // numDoc no puede ser nulo y debe ser positivo
    @NotNull(message = "El número de documento es obligatorio")
    private String numDoc;

    // Código DANE de la ciudad
    @NotBlank(message = "La ciudad es obligatoria")
    @Pattern(regexp = "^[0-9]{5}$", message = "La ciudad debe tener un código DANE de 5 dígitos")
    private String ciudad;

    // No es necesario recibir el departamento ya que se optiene desde la ciudad.

    // Credenciales para el registro
    @NotBlank(message = "El nombre de usuario es obligatorio")
    // El user debe ser un correo electrónico válido
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 80, message = "El usuario no puede superar los 80 caracteres")
    private String user;

    @NotBlank(message = "La contraseña es obligatoria")
    // la propiedad password no se incluirá en la serialización JSON de salida, pero
    // sí se permitirá en la deserialización JSON de entrada
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    private String password;

}

package com.sena.practica03.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/*
 * Clase que representa la respuesta de error de la API.
 * definimos la estructura de la respuesta de error que se enviará al cliente
 * cuando ocurra una excepción en la API.
 */
@Getter
@Builder
@AllArgsConstructor
/*
 * No necesitamos @Setter, puesto que una vez construida la respuesta de error
 * no necesitamos modificarla.
 */
public class ApiErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}

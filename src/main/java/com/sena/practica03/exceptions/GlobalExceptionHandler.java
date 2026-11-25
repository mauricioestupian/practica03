package com.sena.practica03.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // 1. Recursos no encontrados
        /*
         * Este método maneja las excepciones de tipo EntityNotFoundException.
         * Cuando se lanza esta excepción, se construye una respuesta de error
         * con el estado HTTP 404 (Not Found) y se devuelve al cliente.
         */
        // ejemplo de uso: si un cliente solicita un recurso que no existe, se lanzará
        // una EntityNotFoundException y este método se encargará de manejarla.
        // consulta de un cliente por id que no existe, se lanza EntityNotFoundException
        // y se maneja aquí.

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> manejarRecursoNoEncontrado(
                        EntityNotFoundException ex,
                        HttpServletRequest request) {

                return construirRespuesta(
                                HttpStatus.NOT_FOUND,
                                ex.getMessage(),
                                request);
        }

        // 2. Argumentos inválidos
        /*
         * Este método maneja las excepciones de tipo IllegalArgumentException.
         * Cuando se lanza esta excepción, se construye una respuesta de error
         * con el estado HTTP 400 (Bad Request) y se devuelve al cliente.
         */
        // ejemplo de uso: si un cliente envía datos inválidos en una solicitud, se
        // lanzará una IllegalArgumentException y este método se encargará de manejarla.
        // consulta de un cliente por id con un valor inválido, se lanza
        // IllegalArgumentException y se maneja aquí.
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiErrorResponse> manejarArgumentoInvalido(
                        IllegalArgumentException ex,
                        HttpServletRequest request) {

                return construirRespuesta(
                                HttpStatus.BAD_REQUEST,
                                ex.getMessage(),
                                request);
        }

        // Método reutilizable para construir respuestas
        /* es usado por los métodos de manejo de excepciones */
        private ResponseEntity<ApiErrorResponse> construirRespuesta(
                        HttpStatus status,
                        String mensaje,
                        HttpServletRequest request) {

                ApiErrorResponse error = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .error(status.getReasonPhrase())
                                .message(mensaje)
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity
                                .status(status)
                                .body(error);
        }
}

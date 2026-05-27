package com.clinica.personal.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Estructura uniforme de respuesta de error que devuelve la API.
 * 'fieldErrors' es opcional y solo se completa en errores de validacion.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }
}

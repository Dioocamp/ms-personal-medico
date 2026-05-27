package com.clinica.personal.exception;

/**
 * Se lanza cuando no se encuentra un recurso por su id u otro criterio.
 * El GlobalExceptionHandler la traduce a un HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }

    public ResourceNotFoundException(String recurso, Object id) {
        super(recurso + " no encontrado(a) con id: " + id);
    }
}

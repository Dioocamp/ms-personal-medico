package com.clinica.personal.exception;

/**
 * Se lanza ante una violacion de reglas de negocio (por ejemplo, un RUT duplicado).
 * El GlobalExceptionHandler la traduce a un HTTP 409 Conflict.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensaje) {
        super(mensaje);
    }
}

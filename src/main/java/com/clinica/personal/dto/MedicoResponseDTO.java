package com.clinica.personal.dto;

/**
 * DTO de salida para Medico. Incluye datos "aplanados" de la especialidad
 * para que el cliente no tenga que hacer una segunda llamada.
 */
public record MedicoResponseDTO(
        Long id,
        String rut,
        String nombre,
        String apellido,
        String email,
        String registroSuperintendencia,
        Long especialidadId,
        String especialidadNombre
) {
}

package com.clinica.personal.dto;

/**
 * DTO de salida para Especialidad. Evita exponer la entidad JPA directamente
 * (previene recursion infinita en JSON y desacopla la API del modelo de datos).
 */
public record EspecialidadResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        int cantidadMedicos
) {
}

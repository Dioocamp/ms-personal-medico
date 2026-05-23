package com.clinica.personal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear/actualizar una Especialidad.
 * Las anotaciones de validacion se evaluan con @Valid en el controlador.
 */
public record EspecialidadRequestDTO(

        @NotBlank(message = "El nombre de la especialidad es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
        String descripcion
) {
}

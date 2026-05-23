package com.clinica.personal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para crear/actualizar un Medico.
 */
public record MedicoRequestDTO(

        @NotBlank(message = "El RUT es obligatorio")
        @Size(max = 12, message = "El RUT no puede superar los 12 caracteres")
        String rut,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato valido")
        String email,

        @Size(max = 50, message = "El registro no puede superar los 50 caracteres")
        String registroSuperintendencia,

        @NotNull(message = "Debe indicar el id de la especialidad")
        Long especialidadId
) {
}

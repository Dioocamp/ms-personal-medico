package com.clinica.personal.service;

import com.clinica.personal.dto.EspecialidadRequestDTO;
import com.clinica.personal.dto.EspecialidadResponseDTO;

import java.util.List;

/**
 * Contrato del servicio de Especialidades.
 * Trabajar contra una interfaz (no contra la implementacion) favorece el
 * bajo acoplamiento y facilita las pruebas (buenas practicas - IE2).
 */
public interface EspecialidadService {

    EspecialidadResponseDTO crear(EspecialidadRequestDTO dto);

    List<EspecialidadResponseDTO> listar();

    EspecialidadResponseDTO obtenerPorId(Long id);

    EspecialidadResponseDTO actualizar(Long id, EspecialidadRequestDTO dto);

    void eliminar(Long id);
}

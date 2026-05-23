package com.clinica.personal.service;

import com.clinica.personal.dto.MedicoRequestDTO;
import com.clinica.personal.dto.MedicoResponseDTO;

import java.util.List;

/**
 * Contrato del servicio de Medicos.
 */
public interface MedicoService {

    MedicoResponseDTO crear(MedicoRequestDTO dto);

    /** Lista todos los medicos; si especialidadId no es null, filtra por especialidad. */
    List<MedicoResponseDTO> listar(Long especialidadId);

    MedicoResponseDTO obtenerPorId(Long id);

    MedicoResponseDTO actualizar(Long id, MedicoRequestDTO dto);

    void eliminar(Long id);
}

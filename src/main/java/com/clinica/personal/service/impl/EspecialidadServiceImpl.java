package com.clinica.personal.service.impl;

import com.clinica.personal.dto.EspecialidadRequestDTO;
import com.clinica.personal.dto.EspecialidadResponseDTO;
import com.clinica.personal.exception.BusinessException;
import com.clinica.personal.exception.ResourceNotFoundException;
import com.clinica.personal.model.Especialidad;
import com.clinica.personal.repository.EspecialidadRepository;
import com.clinica.personal.service.EspecialidadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementacion de la logica de negocio de Especialidades.
 * Usa inyeccion de dependencias por constructor (recomendada por Spring).
 */
@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadServiceImpl(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    @Override
    @Transactional
    public EspecialidadResponseDTO crear(EspecialidadRequestDTO dto) {
        if (especialidadRepository.existsByNombreIgnoreCase(dto.nombre())) {
            throw new BusinessException("Ya existe una especialidad con el nombre: " + dto.nombre());
        }
        Especialidad especialidad = new Especialidad(dto.nombre(), dto.descripcion());
        return toResponse(especialidadRepository.save(especialidad));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EspecialidadResponseDTO> listar() {
        return especialidadRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EspecialidadResponseDTO obtenerPorId(Long id) {
        Especialidad especialidad = buscarOExcepcion(id);
        return toResponse(especialidad);
    }

    @Override
    @Transactional
    public EspecialidadResponseDTO actualizar(Long id, EspecialidadRequestDTO dto) {
        Especialidad especialidad = buscarOExcepcion(id);
        especialidad.setNombre(dto.nombre());
        especialidad.setDescripcion(dto.descripcion());
        return toResponse(especialidadRepository.save(especialidad));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Especialidad especialidad = buscarOExcepcion(id);
        if (!especialidad.getMedicos().isEmpty()) {
            throw new BusinessException(
                    "No se puede eliminar la especialidad porque tiene medicos asociados.");
        }
        especialidadRepository.delete(especialidad);
    }

    // --- Metodos auxiliares ---------------------------------------------

    private Especialidad buscarOExcepcion(Long id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad", id));
    }

    private EspecialidadResponseDTO toResponse(Especialidad e) {
        return new EspecialidadResponseDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getMedicos() != null ? e.getMedicos().size() : 0);
    }
}

package com.clinica.personal.service.impl;

import com.clinica.personal.dto.MedicoRequestDTO;
import com.clinica.personal.dto.MedicoResponseDTO;
import com.clinica.personal.exception.BusinessException;
import com.clinica.personal.exception.ResourceNotFoundException;
import com.clinica.personal.model.Especialidad;
import com.clinica.personal.model.Medico;
import com.clinica.personal.repository.EspecialidadRepository;
import com.clinica.personal.repository.MedicoRepository;
import com.clinica.personal.service.MedicoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementacion de la logica de negocio de Medicos.
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;
    private final EspecialidadRepository especialidadRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository,
                             EspecialidadRepository especialidadRepository) {
        this.medicoRepository = medicoRepository;
        this.especialidadRepository = especialidadRepository;
    }

    @Override
    @Transactional
    public MedicoResponseDTO crear(MedicoRequestDTO dto) {
        if (medicoRepository.existsByRut(dto.rut())) {
            throw new BusinessException("Ya existe un medico con el RUT: " + dto.rut());
        }
        Especialidad especialidad = buscarEspecialidad(dto.especialidadId());

        Medico medico = new Medico(
                dto.rut(), dto.nombre(), dto.apellido(), dto.email(),
                dto.registroSuperintendencia(), especialidad);

        return toResponse(medicoRepository.save(medico));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicoResponseDTO> listar(Long especialidadId) {
        List<Medico> medicos = (especialidadId == null)
                ? medicoRepository.findAll()
                : medicoRepository.findByEspecialidadId(especialidadId);
        return medicos.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MedicoResponseDTO obtenerPorId(Long id) {
        return toResponse(buscarOExcepcion(id));
    }

    @Override
    @Transactional
    public MedicoResponseDTO actualizar(Long id, MedicoRequestDTO dto) {
        Medico medico = buscarOExcepcion(id);

        // Si cambia el RUT, validar que el nuevo no este tomado por otro medico.
        if (!medico.getRut().equals(dto.rut()) && medicoRepository.existsByRut(dto.rut())) {
            throw new BusinessException("Ya existe otro medico con el RUT: " + dto.rut());
        }

        medico.setRut(dto.rut());
        medico.setNombre(dto.nombre());
        medico.setApellido(dto.apellido());
        medico.setEmail(dto.email());
        medico.setRegistroSuperintendencia(dto.registroSuperintendencia());
        medico.setEspecialidad(buscarEspecialidad(dto.especialidadId()));

        return toResponse(medicoRepository.save(medico));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Medico medico = buscarOExcepcion(id);
        medicoRepository.delete(medico);
    }

    // --- Metodos auxiliares ---------------------------------------------

    private Medico buscarOExcepcion(Long id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medico", id));
    }

    private Especialidad buscarEspecialidad(Long especialidadId) {
        return especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad", especialidadId));
    }

    private MedicoResponseDTO toResponse(Medico m) {
        return new MedicoResponseDTO(
                m.getId(),
                m.getRut(),
                m.getNombre(),
                m.getApellido(),
                m.getEmail(),
                m.getRegistroSuperintendencia(),
                m.getEspecialidad().getId(),
                m.getEspecialidad().getNombre());
    }
}

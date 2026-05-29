package com.clinica.personal.service;

import com.clinica.personal.dto.MedicoRequestDTO;
import com.clinica.personal.dto.MedicoResponseDTO;
import com.clinica.personal.exception.BusinessException;
import com.clinica.personal.model.Especialidad;
import com.clinica.personal.model.Medico;
import com.clinica.personal.repository.EspecialidadRepository;
import com.clinica.personal.repository.MedicoRepository;
import com.clinica.personal.service.impl.MedicoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Pruebas unitarias del servicio de Medicos usando Mockito.
 * No levantan el contexto de Spring ni la base de datos (son rapidas).
 */
@ExtendWith(MockitoExtension.class)
class MedicoServiceImplTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EspecialidadRepository especialidadRepository;

    @InjectMocks
    private MedicoServiceImpl medicoService;

    @Test
    void crear_conRutNuevo_devuelveMedicoCreado() {
        // Arrange
        MedicoRequestDTO dto = new MedicoRequestDTO(
                "44444444-4", "Jose", "Diaz", "jose.diaz@clinica.cl", "SIS-2000", 1L);

        Especialidad especialidad = new Especialidad("Cardiologia", "desc");
        especialidad.setId(1L);

        Medico guardado = new Medico(dto.rut(), dto.nombre(), dto.apellido(),
                dto.email(), dto.registroSuperintendencia(), especialidad);
        guardado.setId(10L);

        when(medicoRepository.existsByRut(dto.rut())).thenReturn(false);
        when(especialidadRepository.findById(1L)).thenReturn(Optional.of(especialidad));
        when(medicoRepository.save(any(Medico.class))).thenReturn(guardado);

        // Act
        MedicoResponseDTO resultado = medicoService.crear(dto);

        // Assert
        assertEquals(10L, resultado.id());
        assertEquals("Jose", resultado.nombre());
        assertEquals("Cardiologia", resultado.especialidadNombre());
    }

    @Test
    void crear_conRutDuplicado_lanzaBusinessException() {
        MedicoRequestDTO dto = new MedicoRequestDTO(
                "11111111-1", "Ana", "Soto", "ana@clinica.cl", "SIS-1", 1L);

        when(medicoRepository.existsByRut(dto.rut())).thenReturn(true);

        assertThrows(BusinessException.class, () -> medicoService.crear(dto));
    }
}

package com.clinica.personal.controller;

import com.clinica.personal.dto.MedicoRequestDTO;
import com.clinica.personal.dto.MedicoResponseDTO;
import com.clinica.personal.service.MedicoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST de Medicos. Recurso /api/medicos.
 */
@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    /** POST -> registra un medico. 201 Created. */
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> crear(@Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO creado = medicoService.crear(dto);
        return ResponseEntity
                .created(URI.create("/api/medicos/" + creado.id()))
                .body(creado);
    }

    /**
     * GET -> lista medicos. Permite filtrar por especialidad:
     *   /api/medicos?especialidadId=1
     */
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listar(
            @RequestParam(required = false) Long especialidadId) {
        return ResponseEntity.ok(medicoService.listar(especialidadId));
    }

    /** GET /{id} -> obtiene un medico. 200 OK o 404. */
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(medicoService.obtenerPorId(id));
    }

    /** PUT /{id} -> actualiza un medico. 200 OK o 404. */
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody MedicoRequestDTO dto) {
        return ResponseEntity.ok(medicoService.actualizar(id, dto));
    }

    /** DELETE /{id} -> elimina un medico. 204 No Content. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

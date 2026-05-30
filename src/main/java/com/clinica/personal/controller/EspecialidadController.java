package com.clinica.personal.controller;

import com.clinica.personal.dto.EspecialidadRequestDTO;
import com.clinica.personal.dto.EspecialidadResponseDTO;
import com.clinica.personal.service.EspecialidadService;
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
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST de Especialidades.
 * Expone el recurso /api/especialidades siguiendo las convenciones REST:
 * un verbo HTTP por operacion y codigos de estado adecuados.
 */
@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    /** POST -> crea una especialidad. Responde 201 Created con cabecera Location. */
    @PostMapping
    public ResponseEntity<EspecialidadResponseDTO> crear(@Valid @RequestBody EspecialidadRequestDTO dto) {
        EspecialidadResponseDTO creada = especialidadService.crear(dto);
        return ResponseEntity
                .created(URI.create("/api/especialidades/" + creada.id()))
                .body(creada);
    }

    /** GET -> lista todas las especialidades. Responde 200 OK. */
    @GetMapping
    public ResponseEntity<List<EspecialidadResponseDTO>> listar() {
        return ResponseEntity.ok(especialidadService.listar());
    }

    /** GET /{id} -> obtiene una especialidad. 200 OK o 404 Not Found. */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadService.obtenerPorId(id));
    }

    /** PUT /{id} -> actualiza una especialidad. 200 OK o 404 Not Found. */
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadResponseDTO> actualizar(
            @PathVariable Long id, @Valid @RequestBody EspecialidadRequestDTO dto) {
        return ResponseEntity.ok(especialidadService.actualizar(id, dto));
    }

    /** DELETE /{id} -> elimina una especialidad. 204 No Content. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especialidadService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

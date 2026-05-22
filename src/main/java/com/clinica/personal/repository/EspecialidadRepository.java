package com.clinica.personal.repository;

import com.clinica.personal.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para Especialidad.
 * Al extender JpaRepository se obtienen gratis los metodos CRUD
 * (save, findAll, findById, deleteById, etc.).
 */
@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    Optional<Especialidad> findByNombreIgnoreCase(String nombre);
}

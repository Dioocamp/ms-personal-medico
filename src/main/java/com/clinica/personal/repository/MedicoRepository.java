package com.clinica.personal.repository;

import com.clinica.personal.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de acceso a datos para Medico.
 * Incluye consultas derivadas (query methods) generadas por Spring Data.
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    boolean existsByRut(String rut);

    List<Medico> findByEspecialidadId(Long especialidadId);
}

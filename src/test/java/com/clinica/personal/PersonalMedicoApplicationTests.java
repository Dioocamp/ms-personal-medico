package com.clinica.personal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Prueba de humo: verifica que el contexto de Spring arranca correctamente.
 * Usa el perfil 'h2' para no depender de un MySQL en ejecucion.
 */
@SpringBootTest
@ActiveProfiles("h2")
class PersonalMedicoApplicationTests {

    @Test
    void contextLoads() {
        // Si el contexto no carga, esta prueba falla automaticamente.
    }
}

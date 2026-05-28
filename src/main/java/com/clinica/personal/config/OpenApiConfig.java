package com.clinica.personal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadatos de la documentacion OpenAPI (visible en /swagger-ui.html).
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI personalMedicoOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API - Microservicio Personal Medico")
                .description("Gestion de especialidades y medicos de la Clinica (EP2 - JVY0101)")
                .version("1.0.0")
                .contact(new Contact().name("Equipo Clinica")));
    }
}

package com.clinica.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del microservicio de Personal Medico.
 *
 * La anotacion @SpringBootApplication activa:
 *  - @Configuration: la clase puede definir beans.
 *  - @EnableAutoConfiguration: autoconfiguracion de Spring Boot (web, JPA, etc.).
 *  - @ComponentScan: escaneo de componentes en el paquete com.clinica.personal y subpaquetes.
 */
@SpringBootApplication
public class PersonalMedicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalMedicoApplication.class, args);
    }
}

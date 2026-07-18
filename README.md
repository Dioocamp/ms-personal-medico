# ms-personal-medico

Microservicio de **gestión de personal médico y especialidades** de la Clínica.
Forma parte de la solución de microservicios de la **Evaluación Parcial 2 (JVY0101 – Java: Diseño y Construcción de Soluciones nativas en Nube).**

> Microservicio relacionado: [`ms-citas`](../ms-citas) (gestión de pacientes y citas).

---

## Tecnologías

| Componente | Versión |
|---|---|
| Java (JDK) | 17 |
| Spring Boot | 3.3.5 |
| Spring Web / Spring Data JPA / Hibernate | (gestionadas por Spring Boot) |
| Base de datos | MySQL 8 (perfil por defecto) · H2 (perfil `h2`) |
| Build | Maven (con Maven Wrapper `mvnw`) |
| Documentación API | springdoc-openapi (Swagger UI) |

## Arquitectura en capas

```
controller  →  service (interface + impl)  →  repository  →  model (entidades JPA)
                         ↑ dto (records)            ↑ exception (manejo global de errores)
```

| Entidad | Relación |
|---|---|
| `Especialidad` | `@OneToMany` → `Medico` |
| `Medico` | `@ManyToOne` → `Especialidad` (columna FK `especialidad_id`) |



La aplicación queda disponible en **http://localhost:8081**.

| Recurso | URL |
|---|---|
| Swagger UI (documentación y pruebas) | http://localhost:8081/swagger-ui.html |
| Consola H2 (solo perfil `h2`) | http://localhost:8081/h2-console |

> Consola H2 → JDBC URL: `jdbc:h2:mem:clinica_personal`, usuario `sa`, sin contraseña.

---

## Endpoints REST

Base: `http://localhost:8081`

### Especialidades — `/api/especialidades`
| Método | Ruta | Descripción | Éxito |
|---|---|---|---|
| POST | `/api/especialidades` | Crear especialidad | 201 |
| GET | `/api/especialidades` | Listar todas | 200 |
| GET | `/api/especialidades/{id}` | Obtener por id | 200 / 404 |
| PUT | `/api/especialidades/{id}` | Actualizar | 200 / 404 |
| DELETE | `/api/especialidades/{id}` | Eliminar | 204 / 409 |

### Médicos — `/api/medicos`
| Método | Ruta | Descripción | Éxito |
|---|---|---|---|
| POST | `/api/medicos` | Crear médico | 201 |
| GET | `/api/medicos` | Listar (filtro opcional `?especialidadId=`) | 200 |
| GET | `/api/medicos/{id}` | Obtener por id | 200 / 404 |
| PUT | `/api/medicos/{id}` | Actualizar | 200 / 404 |
| DELETE | `/api/medicos/{id}` | Eliminar | 204 |

### Ejemplos `curl`

```bash
# Crear especialidad
curl -X POST http://localhost:8081/api/especialidades \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Cardiologia\",\"descripcion\":\"Enfermedades del corazon\"}"

# Crear médico (requiere una especialidad existente)
curl -X POST http://localhost:8081/api/medicos \
  -H "Content-Type: application/json" \
  -d "{\"rut\":\"11111111-1\",\"nombre\":\"Ana\",\"apellido\":\"Soto\",\"email\":\"ana.soto@clinica.cl\",\"registroSuperintendencia\":\"SIS-1001\",\"especialidadId\":1}"

# Listar médicos
curl http://localhost:8081/api/medicos
```


---

## 🧰 Comandos Maven útiles (IE7)

| Comando | Qué hace |
|---|---|
| `mvnw.cmd clean` | Borra `target/` |
| `mvnw.cmd compile` | Compila el código |
| `mvnw.cmd test` | Ejecuta las pruebas (JUnit + Mockito) |
| `mvnw.cmd package` | Genera el `.jar` ejecutable en `target/` |
| `mvnw.cmd clean install` | Limpia, testea, empaqueta e instala en el repo local |

---

## 🌿 Estructura de ramas Git (IE9)

| Rama | Propósito |
|---|---|
| `main` | Versión estable / entregable (tag `v1.0.0`) |
| `develop` | Integración de funcionalidades |
| `feature/*` | Una rama por funcionalidad (entidades, repositorios, servicios, controladores, etc.) |

---

## 🐳 Docker y despliegue cloud (EP3)

El microservicio se contenedoriza con un **Dockerfile multi-stage** (build con
Maven + Temurin 17, runtime JRE Alpine, usuario no root, `HEALTHCHECK` contra
`/actuator/health`).

```bash
# Construir y probar en local (perfil H2, sin MySQL)
docker build -t ms-personal-medico .
docker run -d -p 8081:8081 -e SPRING_PROFILES_ACTIVE=h2 ms-personal-medico
curl http://localhost:8081/actuator/health   # {"status":"UP"}
```

**Configuración por variables de entorno:** `SERVER_PORT`, `DB_URL`,
`DB_USERNAME`, `DB_PASSWORD`, `GATEWAY_SECRET`.

**Pipeline CI/CD** (`.github/workflows/ci-cd.yml`): build + tests con reporte
como artifact → imagen a Docker Hub (tags `latest` y SHA) → despliegue en
Docker Swarm vía SSH. Secrets: `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`,
`EC2_HOST`, `EC2_USER`, `EC2_SSH_KEY`.

El stack Swarm completo (compose, scripts de clúster, Lambda, API Gateway)
vive en el repositorio
[`ep3-infraestructura`](https://github.com/Dioocamp/ep3-infraestructura).

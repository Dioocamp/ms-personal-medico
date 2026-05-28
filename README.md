# ms-personal-medico

Microservicio de **gestión de personal médico y especialidades** de la Clínica.
Forma parte de la solución de microservicios de la **Evaluación Parcial 2 (JVY0101 – Java: Diseño y Construcción de Soluciones nativas en Nube).**

> Microservicio relacionado: [`ms-citas`](../ms-citas) (gestión de pacientes y citas).

---

## 🧱 Tecnologías

| Componente | Versión |
|---|---|
| Java (JDK) | 17 |
| Spring Boot | 3.3.5 |
| Spring Web / Spring Data JPA / Hibernate | (gestionadas por Spring Boot) |
| Base de datos | MySQL 8 (perfil por defecto) · H2 (perfil `h2`) |
| Build | Maven (con Maven Wrapper `mvnw`) |
| Documentación API | springdoc-openapi (Swagger UI) |

## 🏗️ Arquitectura en capas

```
controller  →  service (interface + impl)  →  repository  →  model (entidades JPA)
                         ↑ dto (records)            ↑ exception (manejo global de errores)
```

| Entidad | Relación |
|---|---|
| `Especialidad` | `@OneToMany` → `Medico` |
| `Medico` | `@ManyToOne` → `Especialidad` (columna FK `especialidad_id`) |

---

## ✅ Requisitos previos

- **JDK 17** instalado (`java -version` debe mostrar la versión 17).
- **MySQL 8** en ejecución en `localhost:3306` con usuario `root` / contraseña `root`
  *(o usa el perfil **H2** y no necesitas instalar MySQL — ver más abajo).*
- No necesitas instalar Maven: el proyecto incluye el **Maven Wrapper** (`mvnw` / `mvnw.cmd`).

> El esquema `clinica_personal` se crea automáticamente (`createDatabaseIfNotExist=true`).

---

## ▶️ Cómo clonar, instalar y ejecutar

```bash
# 1) Clonar el repositorio
git clone <URL-DEL-REPOSITORIO> ms-personal-medico
cd ms-personal-medico

# 2) Compilar, testear y empaquetar (genera target/ms-personal-medico-1.0.0.jar)
mvnw.cmd clean package         # Windows
./mvnw clean package           # Linux / Mac

# 3a) Ejecutar con MySQL (perfil por defecto)
java -jar target/ms-personal-medico-1.0.0.jar
#    o, sin empaquetar:
mvnw.cmd spring-boot:run

# 3b) Ejecutar SIN instalar MySQL, usando H2 en memoria (con datos de ejemplo):
java -jar target/ms-personal-medico-1.0.0.jar --spring.profiles.active=h2
#    o:
mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=h2"
```

La aplicación queda disponible en **http://localhost:8081**.

| Recurso | URL |
|---|---|
| Swagger UI (documentación y pruebas) | http://localhost:8081/swagger-ui.html |
| Consola H2 (solo perfil `h2`) | http://localhost:8081/h2-console |

> Consola H2 → JDBC URL: `jdbc:h2:mem:clinica_personal`, usuario `sa`, sin contraseña.

---

## 🔌 Endpoints REST

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

> En la carpeta [`postman/`](postman/) está la colección lista para importar en Postman.

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

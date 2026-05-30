-- Datos de ejemplo. Solo se ejecutan en el perfil 'h2' (spring.sql.init.mode=always).
-- En MySQL este archivo NO se ejecuta (spring.sql.init.mode=never).

INSERT INTO especialidades (nombre, descripcion) VALUES ('Cardiologia', 'Diagnostico y tratamiento de enfermedades del corazon');
INSERT INTO especialidades (nombre, descripcion) VALUES ('Pediatria', 'Atencion medica de ninos, ninas y adolescentes');
INSERT INTO especialidades (nombre, descripcion) VALUES ('Traumatologia', 'Lesiones del sistema musculoesqueletico');

INSERT INTO medicos (rut, nombre, apellido, email, registro_superintendencia, especialidad_id) VALUES ('11111111-1', 'Ana', 'Soto', 'ana.soto@clinica.cl', 'SIS-1001', 1);
INSERT INTO medicos (rut, nombre, apellido, email, registro_superintendencia, especialidad_id) VALUES ('22222222-2', 'Luis', 'Perez', 'luis.perez@clinica.cl', 'SIS-1002', 2);
INSERT INTO medicos (rut, nombre, apellido, email, registro_superintendencia, especialidad_id) VALUES ('33333333-3', 'Carla', 'Munoz', 'carla.munoz@clinica.cl', 'SIS-1003', 1);

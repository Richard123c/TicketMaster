CREATE TABLE usuarios (
id SERIAL PRIMARY KEY,
nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
contraseña VARCHAR(255) NOT NULL,
rol VARCHAR(20) NOT NULL
)

INSERT INTO usuarios (nombre_usuario, contraseña, rol)
VALUES ('admin', 'admin123', 'admin');

CREATE TABLE parametros_sistema (
id SERIAL PRIMARY KEY,
nombre_empresa VARCHAR(100)NOT NULL,
logo BYTEA,
idioma VARCHAR(20) NOT NULL,
zona_horaria VARCHAR(50) NOT NULL,
vencimiento_tickets INTEGER NOT NULL CHECK (vencimiento_tickets BETWEEN 1 AND 365),
prioridad_alta VARCHAR(50),
prioridad_media VARCHAR(50),
prioridad_baja VARCHAR(50),
ultima_modificacion TIMESTAMP,
modificado_por VARCHAR(50)
);

CREATE TABLE permisos (
id SERIAL PRIMARY KEY,
nombre VARCHAR(50) UNIQUE NOT NULL,
descripcion TEXT
);

CREATE TABLE roles (
id SERIAL PRIMARY KEY,
nombre VARCHAR(50) UNIQUE NOT NULL,
descripcion TEXT
);

CREATE TABLE roles_permisos (
rol_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
permiso_id INTEGER REFERENCES permisos(id) ON DELETE CASCADE,
PRIMARY KEY (rol_id, permiso_id)
);

INSERT INTO permisos (nombre, descripcion) VALUES 
('Crear tickets', 'Permite crear tickets de soporte'),
('Ver tickets', 'Permite visualizar tickets existentes'),
('Editar tickets', 'Permite editar el contenido de los tickets'),
('Eliminar tickets', 'Permite eliminar tickets'),
('Asignar tickets', 'Permite asignar tickets a técnicos'),
('Cambiar estado de tickets', 'Permite cambiar el estado de los tickets'),
('Agregar notas a tickets', 'Permite adjuntar notas adicionales a un ticket'),
('Gestionar usuarios', 'Permite agregar, modificar y eliminar usuarios del sistema'),
('Gestionar departamentos', 'Permite administrar los departamentos de la organización'),
('Gestionar flujos de trabajo', 'Permite configurar pasos y reglas de los tickets'),
('Configurar parámetros del sistema', 'Permite modificar la configuración general del sistema');
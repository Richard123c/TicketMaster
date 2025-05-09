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

CREATE TABLE departamentos (
id SERIAL PRIMARY KEY,
nombre VARCHAR(50) UNIQUE NOT NULL,
descripcion TEXT
);

CREATE TABLE usuarios_iniciales (
id SERIAL PRIMARY KEY,
nombre_completo VARCHAR(100) NOT NULL,
correo VARCHAR(100) NOT NULL,
nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
contraseña VARCHAR(100) NOT NULL,
rol VARCHAR(50) NOT NULL, 
departamento VARCHAR(100),
activo BOOLEAN DEFAULT TRUE
);

SELECT * FROM usuarios_iniciales LIMIT 1;

CREATE TABLE estados_ticket (
nombre VARCHAR(50) PRIMARY KEY,
descripcion TEXT,
es_final BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE transiciones_estado (
    estado_origen VARCHAR(50),
    siguiente_estado VARCHAR(50),
    PRIMARY KEY (estado_origen, siguiente_estado),
    FOREIGN KEY (estado_origen) REFERENCES estados_ticket(nombre) ON DELETE CASCADE,
    FOREIGN KEY (siguiente_estado) REFERENCES estados_ticket(nombre) ON DELETE CASCADE
);

CREATE TABLE tickets (
id SERIAL PRIMARY KEY,
titulo TEXT NOT NULL,
estado VARCHAR(50),
FOREIGN KEY (estado) REFERENCES estados_ticket(nombre)
);

INSERT INTO estados_ticket (nombre, descripcion, es_final) VALUES
('Pendiente', 'El ticket ha sido creado pero aun no atendido.', false),
('En Proceso', 'El ticket esta siendo gestionado.', false),
('En Espera', 'Esperando respuesta del usuario o de un tercero.', false),
('Resuelto', 'El problema ha sido solucionado.', true),
('Cerrado', 'El ticket ha cerrado definitamente.', true);

INSERT INTO transiciones_estado (estado_origen, siguiente_estado) VALUES
('Pendiente', 'En Proceso'),
('Pendiente', 'En Espera'),
('En Proceso', 'Resuelto'),
('En Proceso', 'En Espera'),
('En Espera', 'En Proceso'),
('Resuelto', 'Cerrado');

CREATE TABLE flujos_trabajo (
nombre VARCHAR(50) PRIMARY KEY
);

CREATE TABLE flujo_estados (
flujo VARCHAR(50),
estado VARCHAR(50),
PRIMARY KEY (flujo, estado),
FOREIGN KEY (flujo) REFERENCES flujos_trabajo(nombre) ON DELETE CASCADE,
FOREIGN KEY (estado) REFERENCES estados_ticket(nombre) ON DELETE CASCADE
);

CREATE TABLE flujo_transiciones (
flujo VARCHAR(50),
origen VARCHAR(50),
destino VARCHAR(50),
PRIMARY KEY (flujo, origen, destino),
FOREIGN KEY (flujo) REFERENCES flujos_trabajo(nombre) ON DELETE CASCADE,
FOREIGN KEY (origen) REFERENCES estados_ticket(nombre) ON DELETE CASCADE,
FOREIGN KEY (destino) REFERENCES estados_ticket(nombre) ON DELETE CASCADE
);

ALTER TABLE tickets ADD COLUMN flujo VARCHAR(50);
ALTER TABLE tickets ADD CONSTRAINT fk_flujo FOREIGN KEY (flujo) REFERENCES flujos_trabajo(nombre);
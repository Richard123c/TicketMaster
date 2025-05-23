 CREATE DATABASE ticketmaster_db;
USE ticketmaster_db;

CREATE TABLE usuarios (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(100) NOT NULL,
rol ENUM('admin', 'tecnico', 'usuario') NOT NULL
);

INSERT INTO usuarios (nombre, email, password, rol)
VALUES ('Admin', 'admin@ticketmaster.com', SHA2('admin123', 256), 'admin');

DROP TABLE IF EXISTS parametros_sistema;

CREATE TABLE parametros_sistema (
id SERIAL PRIMARY KEY,
nombre_empresa VARCHAR(100) NOT NULL,
logo TEXT,
idioma VARCHAR(50) NOT NULL,
zona_horaria VARCHAR(50) NOT NULL,
vencimiento_tickets INTEGER NOT NULL,
prioridad_alta VARCHAR(50) NOT NULL,
prioridad_media VARCHAR(50) NOT NULL,
prioridad_baja VARCHAR(50) NOT NULL
);

ALTER TABLE parametros_sistema
ADD COLUMN creado_por TEXT,
ADD COLUMN creado_en TIMESTAMP DEFAULT NOW();




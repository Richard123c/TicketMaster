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



create database veterinaria;
use veterinaria;

/*Insertar datos Tipo_documento*/
INSERT INTO tipos_documento (abreviatura, nombre) VALUES 
('CC', 'Cédula de Ciudadanía'),
('TI', 'Tarjeta de Identidad'),
('CE', 'Cédula de Extranjería'),
('NIT', 'Número de Identificación Tributaria'),
('PAS', 'Pasaporte');

INSERT INTO roles (rol,descripcion) VALUES 
('ROLE_ADMIN', 'Administrador general con acceso total a la configuración, usuarios y reportes del sistema'),
('ROLE_VETERINARIO', 'Médico veterinario autorizado para gestionar consultas, diagnósticos, fórmulas y cirugías'),
('ROLE_AUXILIAR', 'Personal de apoyo operativo para recepción, agenda de citas, peluquería y ventas de almacén'),
('ROLE_CLIENTE', 'Propietario de mascota con acceso a su historial clínico, citas y compras');

/*Insertar datos en cargo trabajador con Rol_id*/
INSERT INTO cargo_trabajador (cargo_trab, rol_id) VALUES 
    ('Administrador', 1),
    ('Médico Veterinario', 2),
    ('Auxiliar Veterinario', 2),
    ('Recepcionista', 3),
    ('Vendedor', 3),
    ('Estilista', 3);
    

/*Insertar datos en cargo trabajador y luego actualizar los roles*/
INSERT INTO cargo_trabajador (cargo_trab) VALUES 
    ('Administrador'),
    ('Médico Veterinario'),
    ('Auxiliar Veterinario'),
    ('Recepcionista'),
    ('Vendedor'),
    ('Estilista');
UPDATE cargo_trabajador 
SET rol_id = 1 
WHERE cargo_trab = 'Administrador';

UPDATE cargo_trabajador 
SET rol_id = 2 
WHERE cargo_trab IN ('Médico Veterinario', 'Auxiliar Veterinario');

UPDATE cargo_trabajador 
SET rol_id = 3 
WHERE cargo_trab IN ('Recepcionista', 'Vendedor', 'Estilista');


-- Proceso para Tabla tipo_Cliente
-- 1. Eliminar temporalmente la llave foránea en la tabla clientes
ALTER TABLE clientes 
DROP FOREIGN KEY FK52kv7ooyyrhkfsay1mht0j4p5;

-- 2. Convertir el id de tipo_cliente en AUTO_INCREMENT
ALTER TABLE tipo_cliente 
MODIFY COLUMN id TINYINT NOT NULL AUTO_INCREMENT;

-- 3. Volver a crear la llave foránea en la tabla clientes
-- (Ajusta el nombre de la columna en 'clientes' si no se llama 'tipo_cliente_id')
ALTER TABLE clientes 
ADD CONSTRAINT FK_tipos_cliente_Cliente 
FOREIGN KEY (tipo_cliente) REFERENCES tipo_cliente(id);

-- 4. Insertar los registros
INSERT INTO tipo_cliente (nom_tipo_cli) VALUES 
('Natural'),
('Juridico'),
('VIP');



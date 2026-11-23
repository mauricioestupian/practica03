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


--Listado departamentos completos

INSERT INTO departamentos (codigo, nombre) VALUES
('05', 'Antioquia'),
('08', 'Atlántico'),
('11', 'Bogotá D.C.'),
('13', 'Bolívar'),
('15', 'Boyacá'),
('17', 'Caldas'),
('18', 'Caquetá'),
('19', 'Cauca'),
('20', 'Cesar'),
('23', 'Córdoba'),
('25', 'Cundinamarca'),
('27', 'Chocó'),
('41', 'Huila'),
('44', 'La Guajira'),
('47', 'Magdalena'),
('50', 'Meta'),
('52', 'Nariño'),
('54', 'Norte de Santander'),
('63', 'Quindío'),
('66', 'Risaralda'),
('68', 'Santander'),
('70', 'Sucre'),
('73', 'Tolima'),
('76', 'Valle del Cauca'),
('81', 'Arauca'),
('85', 'Casanare'),
('86', 'Putumayo'),
('88', 'Archipiélago de San Andrés, Providencia y Santa Catalina'),
('91', 'Amazonas'),
('94', 'Guainía'),
('95', 'Guaviare'),
('97', 'Vaupés'),
('99', 'Vichada');


--muestra de municipios y ciudades principales, suficiente para comenzar las pruebas de registro de clientes y trabajadores.

INSERT INTO ciudades (codigo, nombre, departamento_codigo) VALUES
-- Antioquia
('05001', 'Medellín', '05'),
('05088', 'Bello', '05'),
('05266', 'Envigado', '05'),
('05360', 'Itagüí', '05'),
('05615', 'Rionegro', '05'),

-- Atlántico
('08001', 'Barranquilla', '08'),
('08758', 'Soledad', '08'),
('08573', 'Puerto Colombia', '08'),

-- Bogotá D.C.
('11001', 'Bogotá D.C.', '11'),

-- Bolívar
('13001', 'Cartagena de Indias', '13'),
('13430', 'Magangué', '13'),

-- Boyacá
('15001', 'Tunja', '15'),
('15238', 'Duitama', '15'),
('15759', 'Sogamoso', '15'),

-- Caldas
('17001', 'Manizales', '17'),
('17174', 'Chinchiná', '17'),

-- Caquetá
('18001', 'Florencia', '18'),

-- Cauca
('19001', 'Popayán', '19'),
('19698', 'Santander de Quilichao', '19'),

-- Cesar
('20001', 'Valledupar', '20'),
('20011', 'Aguachica', '20'),

-- Córdoba
('23001', 'Montería', '23'),
('23162', 'Cereté', '23'),

-- Cundinamarca
('25175', 'Chía', '25'),
('25269', 'Facatativá', '25'),
('25754', 'Soacha', '25'),
('25899', 'Zipaquirá', '25'),

-- Chocó
('27001', 'Quibdó', '27'),

-- Huila
('41001', 'Neiva', '41'),
('41551', 'Pitalito', '41'),

-- La Guajira
('44001', 'Riohacha', '44'),
('44430', 'Maicao', '44'),

-- Magdalena
('47001', 'Santa Marta', '47'),
('47189', 'Ciénaga', '47'),

-- Meta
('50001', 'Villavicencio', '50'),
('50313', 'Granada', '50'),

-- Nariño
('52001', 'Pasto', '52'),
('52835', 'Tumaco', '52'),

-- Norte de Santander
('54001', 'Cúcuta', '54'),
('54518', 'Pamplona', '54'),

-- Quindío
('63001', 'Armenia', '63'),
('63130', 'Calarcá', '63'),

-- Risaralda
('66001', 'Pereira', '66'),
('66170', 'Dosquebradas', '66'),

-- Santander
('68001', 'Bucaramanga', '68'),
('68276', 'Floridablanca', '68'),
('68307', 'Girón', '68'),
('68547', 'Piedecuesta', '68'),

-- Sucre
('70001', 'Sincelejo', '70'),
('70215', 'Corozal', '70'),

-- Tolima
('73001', 'Ibagué', '73'),
('73268', 'Espinal', '73'),

-- Valle del Cauca
('76001', 'Cali', '76'),
('76109', 'Buenaventura', '76'),
('76111', 'Guadalajara de Buga', '76'),
('76520', 'Palmira', '76'),
('76834', 'Tuluá', '76'),

-- Arauca
('81001', 'Arauca', '81'),

-- Casanare
('85001', 'Yopal', '85'),

-- Putumayo
('86001', 'Mocoa', '86'),

-- San Andrés
('88001', 'San Andrés', '88'),

-- Amazonas
('91001', 'Leticia', '91'),

-- Guainía
('94001', 'Inírida', '94'),

-- Guaviare
('95001', 'San José del Guaviare', '95'),

-- Vaupés
('97001', 'Mitú', '97'),

-- Vichada
('99001', 'Puerto Carreño', '99');

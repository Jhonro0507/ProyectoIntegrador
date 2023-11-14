SELECT * FROM GestionVuelos.vuelos;
-- Agregar una instancia de ejemplo a la tabla de vuelos
INSERT INTO GestionVuelos.vuelos (numero_vuelo, aerolinea, ciudad_origen, ciudad_destino, fecha_salida, hora_salida, fecha_llegada, hora_llegada)
VALUES
  ('V1', 'Aerolínea1', 'Ciudad1', 'Ciudad2', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('V2', 'Aerolínea2', 'Ciudad2', 'Ciudad3', '2023-12-01', '11:00:00', '2023-12-01', '12:30:00'),
  ('V3', 'Aerolínea3', 'Ciudad3', 'Ciudad4', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('V4', 'Aerolínea4', 'Ciudad1', 'Ciudad4', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('V5', 'Aerolínea5', 'Ciudad1', 'Ciudad3', '2023-11-06', '03:00:00', '2023-12-01', '10:30:00'),
  ('V6', 'Aerolínea6', 'Ciudad3', 'Ciudad4', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('v7', 'Aerolínea7', 'Ciudad2', 'Ciudad3', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('V8', 'Aerolínea8', 'Ciudad4', 'Ciudad5', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('V9', 'Aerolínea8', 'Ciudad1', 'Ciudad3', '2023-11-01', '08:00:00', '2023-12-01', '10:30:00'),
  ('V1', 'Aerolínea1', 'Ciudad1', 'Ciudad2', '2023-12-01', '10:00:00', '2023-12-01', '10:30:00'),
  ('V2', 'Aerolínea2', 'Ciudad2', 'Ciudad3', '2023-12-01', '11:30:00', '2023-12-01', '12:30:00'),
  ('V1', 'Aerolínea1', 'Ciudad1', 'Ciudad3', '2023-12-01', '10:00:00', '2023-12-01', '10:30:00');
  



SELECT * FROM GestionVuelos.pasajeros;
-- Insertar algunos registros de asajeros
INSERT INTO GestionVuelos.pasajeros (nombre, apellido1, apellido2, direccion, email, edad, pasajero_numero_documento, pasajero_tipo_documento)
VALUES 
('Juan', 'Pérez', 'Gómez', 'Calle Principal 123', 'juan@example.com', 30, '123456789', 'CC'),
('Norma', 'Restrepo', 'Gómez', 'Calle 123', 'norma@example.com', 20, '223451', 'TI');


SELECT * FROM GestionVuelos.clientes;
-- Insertar algunos registros de clientes
INSERT INTO GestionVuelos.clientes (id, usuario, password, email, nombre, apellidos, direccion, telefono, imagen_perfil, administrador)
VALUES
    (0x2b952a0c3ed044bda03b8a57a975d2ca, 'usuario1', 'contraseña1', 'usuario1@example.com', 'Nombre1', 'Apellido1', 'Dirección1', '123456789', 'imagen1.jpg', TRUE),
    (0x22f2a36fbf444b6a88a794f60fb9ff87, 'usuario2', 'contraseña2', 'usuario2@example.com', 'Nombre2', 'Apellido2', 'Dirección2', '987654321', 'imagen2.jpg', FALSE),
    (0x8e029865926f4e0e8ce33dcff89192a5, 'usuario3', 'contraseña3', 'usuario3@example.com', 'Nombre3', 'Apellido3', 'Dirección3', '555555555', 'imagen3.jpg', FALSE);


select * from GestionVuelos.asientos;
SET SQL_SAFE_UPDATES = 0;
DELETE FROM GestionVuelos.asientos;
-- Insertar algunos registros de asientos
INSERT INTO GestionVuelos.asientos (asiento_fila, asiento_columna, clase, estado, adicional, vuelo_id)
VALUES
    ('A', '1', 'E', 'disponible', NULL, 1),
    ('A', '2', 'EP', 'disponible', 'Ventana', 1),
    ('B', '1', 'B', 'reservado', 'Pasillo', 1),
    ('B', '2', 'P', 'ocupado', NULL, 1),
    ('C', '1', 'E', 'disponible', 'Ventana', 1),
    ('C', '2', 'EP', 'disponible', 'Ventana', 1),
    ('D', '1', 'B', 'disponible', 'Pasillo', 1),
    ('D', '2', 'P', 'ocupado', NULL, 1);


select * from GestionVuelos.mascotas;
-- Insertar algunos registros de mascotas
INSERT INTO GestionVuelos.mascotas (nombre, especie, raza, edad, peso, pasajero_tipo_documento, pasajero_numero_documento)
VALUES
    ('Max', 'Perro', 'Labrador', 3, 15.5, 'CC', '123456789'),
    ('Luna', 'Gato', 'Siames', 2, 8.0, 'CC', '123456789');
    

SELECT * FROM GestionVuelos.equipajes;
-- Insertar algunos registros de equipajes
INSERT INTO GestionVuelos.equipajes (descripcion, peso, bodega, pasajero_tipo_documento, pasajero_numero_documento)
VALUES 
  ('Maleta de mano', 8.5, false, 'CC', '123456789'),
  ('Maleta grande', 15.2, true, 'TI', '223451');

SELECT * FROM GestionVuelos.reservas;

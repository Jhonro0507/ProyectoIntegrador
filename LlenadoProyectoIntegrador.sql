SELECT * FROM GestionVuelos.vuelos;
-- Agregar una instancia de ejemplo a la tabla de vuelos
INSERT INTO GestionVuelos.vuelos (numero_vuelo, aerolinea, ciudad_origen, ciudad_destino, fecha_salida, hora_salida, fecha_llegada, hora_llegada, precio, asientos_disponibles, asientos_totales)
VALUES
  ('V1', 'Aerolínea1', 'Ciudad1', 'Ciudad2', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('V2', 'Aerolínea2', 'Ciudad2', 'Ciudad3', '2023-12-01', '11:00:00', '2023-12-01', '12:30:00', 250.00, 150, 200),
  ('V3', 'Aerolínea3', 'Ciudad3', 'Ciudad4', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('V4', 'Aerolínea4', 'Ciudad1', 'Ciudad4', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('V5', 'Aerolínea5', 'Ciudad1', 'Ciudad3', '2023-11-06', '03:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('V6', 'Aerolínea6', 'Ciudad3', 'Ciudad4', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('v7', 'Aerolínea7', 'Ciudad2', 'Ciudad3', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('V8', 'Aerolínea8', 'Ciudad4', 'Ciudad5', '2023-12-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200),
  ('V9', 'Aerolínea8', 'Ciudad1', 'Ciudad3', '2023-11-01', '08:00:00', '2023-12-01', '10:30:00', 250.00, 150, 200);
  
SELECT * FROM GestionVuelos.vuelos v
WHERE (v.ciudad_origen = 'Ciudad1' OR v.ciudad_destino = 'Ciudad3')
AND v.asientos_disponibles != 0
AND (
  (v.fecha_salida > '2023-12-01')
  OR
  (v.fecha_salida = '2023-12-01' AND v.hora_salida > '07:00:00')
);



SELECT * FROM GestionVuelos.pasajeros;
INSERT INTO GestionVuelos.pasajeros (nombre, apellido1, apellido2, direccion, email, edad, pasajero_numero_documento, pasajero_tipo_documento)
VALUES 
('Juan', 'Pérez', 'Gómez', 'Calle Principal 123', 'juan@example.com', 30, '123456789', 'CC'),
('Norma', 'Restrepo', 'Gómez', 'Calle 123', 'norma@example.com', 20, '223451', 'TI');


/* CREAMOS LA BASE DE DATOS PROVEEDORES */
CREATE DATABASE proveedores;

/* AHORA LE DECIMOS A BD QUE APUNTE A LA BD PROVEEDORES */
use proveedores;

/* CREAMOS LA TABLA PROVEEDORES */
CREATE TABLE proveedores (
  id_proveedor INT PRIMARY KEY,
  nombre VARCHAR(255),
  fecha_alta DATE,
  id_cliente INT
);

/* INSERTAMOS LOS DATOS A LOS CAMPOS DE LA TABLA PROVEEDORES */
INSERT INTO proveedores (id_proveedor, nombre, fecha_alta, id_cliente) 
VALUES (1, 'Coca-cola', '2022/02/02', 5), 
       (2, 'Pepsi', '2022/02/02', 5), 
       (3, 'Redbull', '2022/02/02', 6);

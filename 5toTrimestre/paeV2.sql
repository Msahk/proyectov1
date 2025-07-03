-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-06-2025 a las 14:05:58
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

drop database pae;

create database pae;
use pae;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pae`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id_Cliente` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `correo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_produccion`
--

CREATE TABLE `detalle_produccion` (
  `id_detpro` int(11) NOT NULL,
  `id_proc` int(11) NOT NULL,
  `id_sal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id_detalle` int(11) NOT NULL,
  `id_ven` int(11) NOT NULL,
  `id_proc` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumos`
--

CREATE TABLE `insumos` (
  `id_ins` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `cantidad` decimal(10,0) NOT NULL,
  `unidad_medida` varchar(10) NOT NULL,
  `stock_min` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_entradas`
--

CREATE TABLE `inv_entradas` (
  `id_ent` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `cantidad` decimal(10,0) NOT NULL,
  `id_ins` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_salidas`
--

CREATE TABLE `inv_salidas` (
  `id_sal` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `canidad` decimal(10,0) NOT NULL,
  `motivo` varchar(100) NOT NULL,
  `id_ins` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_ped` int(11) NOT NULL,
  `id_ven` int(11) NOT NULL,
  `fecha_entrega` datetime NOT NULL,
  `estado` enum('Tomado','Pendiente') GENERATED ALWAYS AS ('Pendiente') VIRTUAL,
  `observaciones_pedido` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id_proc` int(11) NOT NULL COMMENT 'Identificador de la prduccion',
  `fecha_produccion` date NOT NULL COMMENT 'Identificador del empleado que realizo la venta',
  `total_emp` int(11) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `id_prot` int(11) NOT NULL,
  `id_res` int(11) NOT NULL,
   `estado` varchar(20) NOT NULL DEFAULT 'Pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_roles`
--
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usu` int(11) NOT NULL COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)',
  `documento` int(11) NOT NULL,
  `nombres` varchar(50) NOT NULL COMMENT 'Nombres completos del usuario',
  `apellidos` varchar(50) NOT NULL COMMENT 'Apellidos completos del usuario',
  `telefono` BIGINT NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL COMMENT 'Llave foranea del tipo de usuario',
  `rol` enum('A','EP','EV') NOT NULL,
  `estado` enum('A','I') NOT NULL,
  `password` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_ven` int(11) NOT NULL COMMENT 'Identificacion de la venta',
  `Tipo` enum('directa','pedido') NOT NULL COMMENT 'Fecha en la que se realizo la venta',
  `fecha` datetime NOT NULL COMMENT 'Valor total de la venta en esa fecha',
  `id_usu` int(11)  NULL,
  `id_Cliente` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `estado` enum('Procesando','Completada') NOT NULL,
  `observaciones` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_Cliente`),
  ADD UNIQUE KEY `telefono_UNIQUE` (`telefono`),
  ADD UNIQUE KEY `correo_UNIQUE` (`correo`);

--
-- Indices de la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  ADD PRIMARY KEY (`id_detpro`),
  ADD KEY `fk_detalle_produccion_produccion1_idx` (`id_proc`),
  ADD KEY `fk_detalle_produccion_inv_salidas1_idx` (`id_sal`);

--
-- Indices de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `fk_Detalle_venta_Produccion1_idx` (`id_proc`),
  ADD KEY `fk_Detalle_venta_Ventas1` (`id_ven`);

--
-- Indices de la tabla `insumos`
--
ALTER TABLE `insumos`
  ADD PRIMARY KEY (`id_ins`);

--
-- Indices de la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  ADD PRIMARY KEY (`id_ent`),
  ADD KEY `fk_inv_entradas_insumos1_idx` (`id_ins`);

--
-- Indices de la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  ADD PRIMARY KEY (`id_sal`),
  ADD KEY `fk_inv_salidas_insumos1_idx` (`id_ins`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id_ped`),
  ADD KEY `id_ven_UNIQUE` (`id_ven`),
  ADD KEY `fk_pedidos_Ventas2_idx` (`id_ven`);

--
-- Indices de la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD PRIMARY KEY (`id_proc`),
  ADD KEY `fk_produccion_Productos1_idx` (`id_prot`),
  ADD KEY `fk_produccion_usuarios1_idx` (`id_res`);


-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usu`),
  ADD UNIQUE KEY `correo_UNIQUE` (`correo`),
  ADD UNIQUE KEY `documento_UNIQUE` (`documento`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_ven`),
  ADD KEY `fk_Ventas_Clientes1_idx` (`id_Cliente`),
  ADD KEY `fk_Ventas_usuarios1_idx` (`id_usu`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  MODIFY `id_detpro` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  MODIFY `id_ent` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  MODIFY `id_sal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_ped` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion';



--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)';

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta';

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  ADD CONSTRAINT `fk_detalle_produccion_inv_salidas1` FOREIGN KEY (`id_sal`) REFERENCES `inv_salidas` (`id_sal`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_detalle_produccion_produccion1` FOREIGN KEY (`id_proc`) REFERENCES `produccion` (`id_proc`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `fk_Detalle_venta_Productos1` FOREIGN KEY (`id_proc`) REFERENCES `produccion` (`id_proc`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Detalle_venta_Ventas1` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  ADD CONSTRAINT `fk_inv_entradas_insumos1` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  ADD CONSTRAINT `fk_inv_salidas_insumos1` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `fk_pedidos_Ventas2` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD CONSTRAINT `fk_produccion_usuarios1` FOREIGN KEY (`id_res`) REFERENCES `usuarios` (`id_usu`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `fk_Ventas_Clientes1` FOREIGN KEY (`id_Cliente`) REFERENCES `clientes` (`id_Cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Ventas_usuarios1` FOREIGN KEY (`id_usu`) REFERENCES `usuarios` (`id_usu`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

show tables;

describe usuarios;

INSERT INTO usuarios (documento, nombres, apellidos, telefono, direccion, correo, password, rol, estado) VALUES
(1034280742, 'Marlon', 'Avila', 3214108646, 'Cll 6 #2-47', 'avilamarlon31@gmail.com', '$2a$10$IGAAj/4G4K0Dph.8qZo01OqEMcprEi1CALAybarjcF4aujM7nhdNe', 'A', 'A'),
(123456789, 'Ana', 'Pérez', 3001234567, 'Cra 10 #23-45', 'ana.perez@example.com', '$2a$10$9LlIYlDlAZ/ZWci0JU3zZ.ICH7rGqrH7C/gynKi1nRtUP8C0XhDRy', 'EP', 'A'),
(987654321, 'Carlos', 'Ramírez', 3009876543, 'Av 5 #67-89', 'carlos.ramirez@example.com', '$2a$10$mOUXQ3T7iwyJSLUZug7Xs.Iu/BjlxHn35BukcKo/guUWLFLmMPH5C', 'EP', 'A'),
(112233445, 'Luisa', 'Martínez', 3011122334, 'Cll 12 #34-56', 'luisa.martinez@example.com', '$2a$10$JgJNP.G5pGDCbWSndvzV9.5nmfAg1NQoQo2wgulB7unbN8w/CEKcW', 'EP', 'A'),
(554433221, 'Andrés', 'Lopez', 3023344556, 'Cll 78 #90-12', 'andres.lopez@example.com', '$2a$10$4sdQf/iWdlo60jhH/9JW8eA.Do2fXYJa/LZO9GiWPlqN9CNI6utta', 'EV', 'A'),
(665544332, 'María', 'Gómez', 3034455667, 'Cra 45 #12-34', 'maria.gomez@example.com', '$2a$10$1Wncf/hqx2s7FMPcqIGIBuQQrIJ2WUqFXKQUuM5hva1gircNbWi0G', 'EV', 'A');

INSERT INTO clientes (id_Cliente, nombre, telefono, correo) VALUES
(1, 'Empresa A', '3112345678', 'contacto@empresaA.com'),
(2, 'Empresa B', '3223456789', 'ventas@empresaB.com'),
(3, 'Juan Ruiz', '3334567890', 'juan.ruiz@gmail.com'),
(4, 'Laura Niño', '3445678901', 'laura.nino@hotmail.com'),
(5, 'Pedro Paz', '3556789012', 'pedro.paz@gmail.com');


INSERT INTO insumos (id_ins, nombre, cantidad, unidad_medida, stock_min) VALUES
(1, 'Harina de trigo', 100, 'kg', '50'),
(2, 'Levadura', 20, 'kg', '10'),
(3, 'Azúcar', 50, 'kg', '25'),
(4, 'Sal', 30, 'kg', '10'),
(5, 'Queso', 40, 'kg', '15');

INSERT INTO inv_entradas (id_ent, fecha, cantidad, id_ins) VALUES
(1, '2025-06-01', 50, 1),
(2, '2025-06-02', 10, 2),
(3, '2025-06-03', 25, 3),
(4, '2025-06-04', 15, 4),
(5, '2025-06-05', 20, 5);

INSERT INTO inv_salidas (id_sal, fecha, canidad, motivo, id_ins) VALUES
(1, '2025-06-06', 10, 'Producción diaria', 1),
(2, '2025-06-07', 2, 'Producción diaria', 2),
(3, '2025-06-08', 5, 'Muestra para cliente', 3),
(4, '2025-06-09', 3, 'Prueba de calidad', 4),
(5, '2025-06-10', 4, 'Producción diaria', 5);

INSERT INTO produccion (id_proc, fecha_produccion, total_emp, tipo, cantidad, id_prot, id_res) VALUES
(1, '2025-06-11', 3, 'Carne', 100, 1, 2),
(2, '2025-06-12', 2, 'Pollo', 50, 2, 4),
(3, '2025-06-13', 4, 'Queso', 70, 3, 2),
(4, '2025-06-14', 1, 'Pollo', 30, 4, 4),
(5, '2025-06-15', 3, 'Carne', 60, 5, 2);

INSERT INTO detalle_produccion (id_detpro, id_proc, id_sal) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5);


INSERT INTO ventas (id_ven, Tipo, fecha, id_usu, id_Cliente, total, estado, observaciones) VALUES
(1, 'directa', '2025-06-16 09:00:00', 1, 1, 30000.00, 'Completada', 'Venta directa'),
(2, 'pedido', '2025-06-17 10:00:00', 2, 2, 50000.00, 'Procesando', 'Pedido a entregar'),
(3, 'directa', '2025-06-18 11:00:00', 3, 3, 15000.00, 'Completada', ''),
(4, 'pedido', '2025-06-19 12:00:00', 4, 4, 40000.00, 'Procesando', 'Cliente nuevo'),
(5, 'directa', '2025-06-20 13:00:00', 5, 5, 20000.00, 'Completada', 'Compra recurrente');


INSERT INTO detalle_venta (id_detalle, id_ven, id_proc, cantidad) VALUES
(1, 1, 1, 10),
(2, 2, 2, 20),
(3, 3, 3, 5),
(4, 4, 4, 10),
(5, 5, 5, 8);


INSERT INTO pedidos (id_ped, id_ven, fecha_entrega, observaciones_pedido) VALUES
(1, 2, '2025-06-22 09:00:00', 'Entregar antes del medio día'),
(2, 4, '2025-06-23 10:00:00', 'Cliente requiere empaque especial'),
(3, 2, '2025-06-24 11:00:00', 'Agregar factura impresa'),
(4, 4, '2025-06-25 08:00:00', 'Urgente'),
(5, 2, '2025-06-26 13:00:00', 'Revisar dirección');

select * from usuarios order by estado;

show tables;
describe ventas;


DELIMITER $$

CREATE PROCEDURE sp_insertarVentaClientePedido(
    IN  p_nombre_cliente        VARCHAR(100),
    IN  p_telefono_cliente      VARCHAR(20),
    IN  p_correo_cliente        VARCHAR(100),
    IN  p_tipo_venta            ENUM('directa','pedido'),
    IN  p_fecha_venta           DATETIME,
    IN  p_id_usuario            INT,
    IN  p_total_venta           DECIMAL(10,2),
    IN  p_estado_venta          ENUM('Procesando','Completada'),
    IN  p_obs_venta             LONGTEXT,
    IN  p_fecha_entrega_pedido  DATETIME,
    IN  p_obs_pedido            LONGTEXT
)
BEGIN
    DECLARE v_id_cliente INT;
    DECLARE v_id_venta   INT;

    -- Iniciar transacción
    START TRANSACTION;

    -- 1) Insertar cliente
    INSERT INTO clientes (nombre, telefono, correo)
    VALUES (p_nombre_cliente, p_telefono_cliente, p_correo_cliente);

    SET v_id_cliente = LAST_INSERT_ID();

    -- 2) Insertar venta usando el cliente
    INSERT INTO ventas (Tipo, fecha, id_usu, id_Cliente, total, estado, observaciones)
    VALUES (p_tipo_venta, p_fecha_venta, p_id_usuario, v_id_cliente, p_total_venta, p_estado_venta, p_obs_venta);

    SET v_id_venta = LAST_INSERT_ID();

    -- 3) Insertar pedido ligado a la venta
    INSERT INTO pedidos (id_ven, fecha_entrega, observaciones_pedido)
    VALUES (v_id_venta, p_fecha_entrega_pedido, p_obs_pedido);

    -- Confirmar
    COMMIT;
END$$

DELIMITER ;

CALL sp_insertarVentaClientePedido(
  'Nuevo Cliente',      -- nombre
  '3111234567',         -- teléfono
  'cliente@ejemplo.com',-- correo
  'pedido',             -- tipo de venta
  NOW(),                -- fecha de venta
  1,                    -- id_usu que realiza la venta
  15000.00,             -- total
  'Procesando',         -- estado de la venta
  'Observación venta',  -- observaciones de la venta
  '2025-07-05 15:30:00',-- fecha entrega pedido
  'Observación pedido'  -- observaciones del pedido
);

CALL sp_insertarVentaClientePedido(
  'Juan Perez',      -- nombre
  '3729394858',         -- teléfono
  'cliente2@ejemplo.com',-- correo
  'pedido',             -- tipo de venta
  NOW(),                -- fecha de venta
  3,                    -- id_usu que realiza la venta
  15000.00,             -- total
  'Procesando',         -- estado de la venta
  'Observación venta',  -- observaciones de la venta
  '2025-07-05 15:30:00',-- fecha entrega pedido
  'Observación pedido'  -- observaciones del pedido
);

DELIMITER $$

CREATE PROCEDURE sp_actualizarVentaClientePedido(
    IN  p_id_ven               INT,
    IN  p_id_cliente           INT,
    IN  p_nombre_cliente       VARCHAR(100),
    IN  p_telefono_cliente     VARCHAR(20),
    IN  p_correo_cliente       VARCHAR(100),
    IN  p_tipo_venta           ENUM('directa','pedido'),
    IN  p_total_venta          DECIMAL(10,2),
    IN  p_estado_venta         ENUM('Procesando','Completada'),
    IN  p_obs_venta            LONGTEXT,
    IN  p_fecha_entrega_pedido DATETIME,
    IN  p_obs_pedido           LONGTEXT
)
BEGIN
    START TRANSACTION;
    
    -- 1) Actualizar datos del cliente
    UPDATE clientes
      SET nombre  = p_nombre_cliente,
          telefono= p_telefono_cliente,
          correo  = p_correo_cliente
    WHERE id_Cliente = p_id_cliente;
    
    -- 2) Actualizar datos de la venta
    UPDATE ventas
      SET Tipo         = p_tipo_venta,
          total        = p_total_venta,
          estado       = p_estado_venta,
          observaciones= p_obs_venta
    WHERE id_ven = p_id_ven;
    
    -- 3) Actualizar datos del pedido
    UPDATE pedidos
      SET fecha_entrega        = p_fecha_entrega_pedido,
          observaciones_pedido = p_obs_pedido
    WHERE id_ven = p_id_ven;
    
    COMMIT;
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE sp_eliminarVentaClientePedido(
    IN p_id_ven     INT
)
BEGIN
    START TRANSACTION;
    
    -- 1) Borrar el pedido
    DELETE FROM pedidos
     WHERE id_ven = p_id_ven;
    
    -- 2) Borrar la venta
    DELETE FROM ventas
     WHERE id_ven = p_id_ven;
    
    COMMIT;
END$$

DELIMITER ;

CALL sp_actualizarVentaClientePedido(
  4,                -- p_id_ven (venta existente)
  2,                -- p_id_cliente
  'Nuevo Cliente',  -- p_nombre_cliente
  '3123456789',     -- p_telefono_cliente
  'nuevo@cliente.com',-- p_correo_cliente
  'directa',        -- p_tipo_venta
  45000.00,         -- p_total_venta
  'Completada',     -- p_estado_venta
  'Venta cerrada',  -- p_obs_venta
  '2025-07-10 10:00:00',-- p_fecha_entrega_pedido
  'Entrega urgente' -- p_obs_pedido
);

ALTER TABLE ventas
MODIFY COLUMN id_usu INT NULL;

select * from ventas;
select * from clientes;
select * from pedidos;
select * from insumos;

describe ventas;

select * from detalle_venta;    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
select * from usuarios;
UPDATE ventas
SET id_usu = 4
WHERE id_usu = 1;
describe detalle_venta;

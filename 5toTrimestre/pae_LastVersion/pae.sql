-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-07-2025 a las 04:06:01
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pae`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarVentaClientePedido` (IN `p_id_ven` INT, IN `p_id_cliente` INT, IN `p_nombre_cliente` VARCHAR(100), IN `p_telefono_cliente` VARCHAR(20), IN `p_correo_cliente` VARCHAR(100), IN `p_tipo_venta` ENUM('directa','pedido'), IN `p_total_venta` DECIMAL(10,2), IN `p_estado_venta` ENUM('Procesando','Completada'), IN `p_obs_venta` LONGTEXT, IN `p_fecha_entrega_pedido` DATETIME, IN `p_obs_pedido` LONGTEXT)   BEGIN
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

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarVentaClientePedido` (IN `p_id_ven` INT)   BEGIN
    START TRANSACTION;
    
    -- 1) Borrar el pedido
    DELETE FROM pedidos
     WHERE id_ven = p_id_ven;
    
    -- 2) Borrar la venta
    DELETE FROM ventas
     WHERE id_ven = p_id_ven;
    
    COMMIT;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarVentaClientePedido` (IN `p_nombre_cliente` VARCHAR(100), IN `p_telefono_cliente` VARCHAR(20), IN `p_correo_cliente` VARCHAR(100), IN `p_tipo_venta` ENUM('directa','pedido'), IN `p_fecha_venta` DATETIME, IN `p_id_usuario` INT, IN `p_total_venta` DECIMAL(10,2), IN `p_estado_venta` ENUM('Procesando','Completada'), IN `p_obs_venta` LONGTEXT, IN `p_fecha_entrega_pedido` DATETIME, IN `p_obs_pedido` LONGTEXT)   BEGIN
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

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_Cliente`, `nombre`, `telefono`, `correo`) VALUES
(1, 'Empresa A', '3112345678', 'contacto@empresaA.com'),
(2, 'Nuevo Cliente', '3123456789', 'nuevo@cliente.com'),
(3, 'Juan Ruiz', '3334567890', 'juan.ruiz@gmail.com'),
(4, 'Laura Niño', '3445678901', 'laura.nino@hotmail.com'),
(5, 'Pedro Paz', '3556789012', 'pedro.paz@gmail.com'),
(6, 'Nuevo Cliente', '3111234567', 'cliente@ejemplo.com'),
(7, 'Juan Perez', '3729394858', 'cliente2@ejemplo.com'),
(8, 'Jaime Rincon', '3229837423', 'jaime@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_produccion`
--

CREATE TABLE `detalle_produccion` (
  `id_detpro` int(11) NOT NULL,
  `id_proc` int(11) NOT NULL,
  `id_ins` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `detalle_produccion`
--

INSERT INTO `detalle_produccion` (`id_detpro`, `id_proc`, `id_ins`, `cantidad`) VALUES
(20, 21, 1, 12.00),
(21, 22, 1, 18.00);

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

--
-- Volcado de datos para la tabla `detalle_venta`
--

INSERT INTO `detalle_venta` (`id_detalle`, `id_ven`, `id_proc`, `cantidad`) VALUES
(1, 1, 1, 10),
(2, 2, 2, 20),
(3, 3, 3, 5),
(4, 4, 4, 10),
(5, 5, 5, 8),
(6, 2, 1, 5),
(7, 3, 2, 34);

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

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`id_ins`, `nombre`, `cantidad`, `unidad_medida`, `stock_min`) VALUES
(1, 'Harina de trigo', 100, 'kg', '50.0'),
(2, 'Levadura', 8, 'kg', '10.0'),
(3, 'Azúcar', 50, 'kg', '25'),
(4, 'Sal', 30, 'kg', '10'),
(5, 'Queso', 40, 'kg', '15'),
(11, 'Pan', 60, 'kg', '10.0'),
(13, 'chorizo', 12, 'kg', '1.0');

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

--
-- Volcado de datos para la tabla `inv_entradas`
--

INSERT INTO `inv_entradas` (`id_ent`, `fecha`, `cantidad`, `id_ins`) VALUES
(3, '2025-06-03', 25, 3),
(4, '2025-06-04', 15, 4),
(5, '2025-06-05', 20, 5),
(6, '2025-07-07', 12, 13),
(7, '2025-07-07', 12, 14),
(8, '2025-07-07', 12, 15);

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

--
-- Volcado de datos para la tabla `inv_salidas`
--

INSERT INTO `inv_salidas` (`id_sal`, `fecha`, `canidad`, `motivo`, `id_ins`) VALUES
(1, '2025-06-06', 10, 'Producción diaria', 1),
(2, '2025-06-07', 2, 'Producción diaria', 2),
(3, '2025-06-08', 5, 'Muestra para cliente', 3),
(4, '2025-06-09', 3, 'Prueba de calidad', 4),
(5, '2025-06-10', 4, 'Producción diaria', 5);

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

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id_ped`, `id_ven`, `fecha_entrega`, `observaciones_pedido`) VALUES
(1, 2, '2025-06-22 09:00:00', 'Entregar antes del medio día'),
(2, 4, '2025-07-10 10:00:00', 'Entrega urgente'),
(3, 2, '2025-06-24 11:00:00', 'Agregar factura impresa'),
(4, 4, '2025-07-10 10:00:00', 'Entrega urgente'),
(5, 2, '2025-06-26 13:00:00', 'Revisar dirección'),
(6, 6, '2025-07-05 15:30:00', 'Observación pedido'),
(7, 7, '2025-07-05 15:30:00', 'Observación pedido'),
(8, 8, '2025-07-03 12:00:00', 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id_proc` int(11) NOT NULL COMMENT 'Identificador de la prduccion',
  `fecha_produccion` date NOT NULL COMMENT 'Identificador del empleado que realizo la venta',
  `tipo` varchar(45) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `id_prot` int(11) NOT NULL,
  `id_res` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id_proc`, `fecha_produccion`, `tipo`, `cantidad`, `id_prot`, `id_res`, `estado`) VALUES
(1, '2025-06-11', 'Carne', 100, 1, 2, 'Pendiente'),
(2, '2025-06-12', 'Pollo', 50, 2, 4, 'Pendiente'),
(3, '2025-06-13', 'Queso', 70, 3, 2, 'Pendiente'),
(4, '2025-06-14', 'Pollo', 30, 4, 4, 'Listo'),
(5, '2025-06-15', 'Carne', 60, 5, 2, 'Produciendo'),
(21, '2025-06-11', 'Carne', 99, 0, 1, 'Pendiente'),
(22, '2025-12-17', 'Pollo', 18, 0, 1, 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usu` int(11) NOT NULL COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)',
  `documento` int(11) NOT NULL,
  `nombres` varchar(50) NOT NULL COMMENT 'Nombres completos del usuario',
  `apellidos` varchar(50) NOT NULL COMMENT 'Apellidos completos del usuario',
  `telefono` bigint(20) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL COMMENT 'Llave foranea del tipo de usuario',
  `rol` enum('A','EP','EV') NOT NULL,
  `estado` enum('A','I') NOT NULL,
  `password` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usu`, `documento`, `nombres`, `apellidos`, `telefono`, `direccion`, `correo`, `rol`, `estado`, `password`) VALUES
(1, 1034280742, 'Marlon', 'Avila', 3214108646, 'Cll 6 #2-47', 'avilamarlon31@gmail.com', 'A', 'A', '$2a$10$IGAAj/4G4K0Dph.8qZo01OqEMcprEi1CALAybarjcF4aujM7nhdNe'),
(2, 123456789, 'Ana', 'Pérez', 3001234567, 'Cra 10 #23-45', 'ana.perez@example.com', 'EP', 'I', '$2a$10$9LlIYlDlAZ/ZWci0JU3zZ.ICH7rGqrH7C/gynKi1nRtUP8C0XhDRy'),
(3, 987654321, 'Carlos Eduardo', 'Ramírez', 3009876543, 'Av 5 #67-89', 'carlos.ramirez@example.com', 'EP', 'A', '$2a$10$Q78YVImydd0pX4GOCSw6COzOiF13IC.Tqyo3jvtZVXP6C52Vf8viy'),
(4, 112233445, 'Luisa', 'Martínez', 3011122334, 'Cll 12 #34-56', 'luisa.martinez@example.com', 'EP', 'A', '$2a$10$JgJNP.G5pGDCbWSndvzV9.5nmfAg1NQoQo2wgulB7unbN8w/CEKcW'),
(5, 554433221, 'Andrés', 'Lopez', 3023344556, 'Cll 78 #90-12', 'andres.lopez@example.com', 'EV', 'A', '$2a$10$X5rw3ZYDzVaCsAIXw.pP9.asfWuFIVATr0KEbHvEDsGUGaQegmUvK'),
(6, 665544332, 'María', 'Gómez', 3034455667, 'Cra 45 #12-34', 'maria.gomez@example.com', 'EV', 'A', '$2a$10$bLHADCbjzH4IETQA2oiyHugtjj.zO.WLNGyjKAF4hMw4c2LzSzbOW'),
(8, 1103098783, 'Sebastián', 'Mercado', 3144418521, 'CL. 80 BIS B Sur #88 B-36, Bogotá', 'sebassmercado97@gmail.com', 'EP', 'A', '$2a$10$Ume4C4/yWuR.e5ayJKX.9.ddLwRf..mATE/NlqEjghQ384bxh8PLG'),
(9, 103923482, 'Alejandro', 'Zapata', 3004284823, 'Calle 6 sur', 'alejo@zapata.com', 'EV', 'A', '$2a$10$D6VxVCuA8qtD/jXUhuFTvuNtwFPEfC1234q3lwpITBFmIzMHJskyC');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_ven` int(11) NOT NULL COMMENT 'Identificacion de la venta',
  `Tipo` enum('directa','pedido') NOT NULL COMMENT 'Fecha en la que se realizo la venta',
  `fecha` datetime NOT NULL COMMENT 'Valor total de la venta en esa fecha',
  `id_usu` int(11) DEFAULT NULL,
  `id_Cliente` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `estado` enum('Procesando','Completada') NOT NULL,
  `observaciones` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_ven`, `Tipo`, `fecha`, `id_usu`, `id_Cliente`, `total`, `estado`, `observaciones`) VALUES
(1, 'directa', '2025-06-16 09:00:00', 4, 1, 30000.00, 'Completada', 'Venta directa'),
(2, 'pedido', '2025-06-17 10:00:00', 2, 2, 50000.00, 'Procesando', 'Pedido a entregar'),
(3, 'directa', '2025-06-18 11:00:00', 3, 3, 15000.00, 'Completada', ''),
(4, 'directa', '2025-06-19 12:00:00', 4, 4, 45000.00, 'Completada', 'Venta cerrada'),
(5, 'directa', '2025-06-20 13:00:00', 5, 5, 20000.00, 'Completada', 'Compra recurrente'),
(6, 'pedido', '2025-07-03 09:12:47', 4, 6, 15000.00, 'Procesando', 'Observación venta'),
(7, 'pedido', '2025-07-03 09:12:47', 3, 7, 15000.00, 'Procesando', 'Observación venta'),
(8, 'pedido', '2025-07-03 08:31:37', 5, 8, 150000.00, 'Procesando', 'Pendiente');

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
  ADD KEY `fk_detalle_produccion_insumo` (`id_ins`);

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

--
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
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  MODIFY `id_detpro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  MODIFY `id_ent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  MODIFY `id_sal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_ped` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion', AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)', AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta', AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  ADD CONSTRAINT `fk_detalle_produccion_id_proc` FOREIGN KEY (`id_proc`) REFERENCES `produccion` (`id_proc`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_detalle_produccion_insumo` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE CASCADE;

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `fk_Detalle_venta_Productos1` FOREIGN KEY (`id_proc`) REFERENCES `produccion` (`id_proc`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Detalle_venta_Ventas1` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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

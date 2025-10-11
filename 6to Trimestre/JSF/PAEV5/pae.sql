-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-10-2025 a las 20:05:34
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
(2, 'Empresa B', '3223456789', 'ventas@empresaB.com'),
(3, 'Juan Ruiz', '3334567890', 'juan.ruiz@gmail.com'),
(4, 'Laura Niño', '3445678901', 'laura.nino@hotmail.com'),
(5, 'Pedro Paz', '3556789012', 'pedro.paz@gmail.com'),
(8, 'Mercado', '3456784567', 'sebassmercado97@gmail.com'),
(9, 'fuquene', '32224567867', 'fuquene@gmail.com');

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
  `cantidad` decimal(10,2) DEFAULT NULL,
  `unidad_medida` varchar(10) NOT NULL,
  `stock_min` decimal(10,2) NOT NULL,
  `stock_actual` decimal(10,2) DEFAULT 0.00,
  `fecha_vencimiento` date DEFAULT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`id_ins`, `nombre`, `cantidad`, `unidad_medida`, `stock_min`, `stock_actual`, `fecha_vencimiento`, `estado`) VALUES
(3, 'Azúcar', 40.00, 'kg', 25.00, 0.00, NULL, 'Activo'),
(4, 'Sal', 50.00, 'kg', 10.00, 0.00, NULL, 'Activo'),
(17, 'chorizo', 3.00, 'kg', 20.00, 0.00, NULL, 'Activo'),
(23, 'Queso', 12.00, 'kg', 1.00, 0.00, NULL, 'Activo'),
(26, 'Pollo desmechado', 60.00, 'kg', 20.00, 0.00, NULL, 'Activo'),
(38, 'Pan', 25.00, 'kg', 5.00, 0.00, NULL, 'Activo'),
(39, 'Tomate', 40.00, 'kg', 15.00, 0.00, NULL, 'Activo'),
(40, 'Cebolla', 35.00, 'kg', 10.00, 0.00, NULL, 'Activo'),
(42, 'Carne desmecha\'', 5.00, 'ml', 1.00, 1.00, '2025-10-10', 'Inactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_entradas`
--

CREATE TABLE `inv_entradas` (
  `id_entrada` int(11) NOT NULL,
  `id_ins` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp(),
  `usuario` varchar(50) DEFAULT NULL,
  `observacion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inv_entradas`
--

INSERT INTO `inv_entradas` (`id_entrada`, `id_ins`, `cantidad`, `fecha_hora`, `usuario`, `observacion`) VALUES
(8, 26, 100.00, '2025-09-10 21:13:41', 'Sistema', 'Registro inicial del insumo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inv_salidas`
--

CREATE TABLE `inv_salidas` (
  `id_salida` int(11) NOT NULL,
  `id_ins` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp(),
  `usuario` varchar(50) DEFAULT NULL,
  `id_proc` int(11) DEFAULT NULL,
  `observacion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inv_salidas`
--

INSERT INTO `inv_salidas` (`id_salida`, `id_ins`, `cantidad`, `fecha_hora`, `usuario`, `id_proc`, `observacion`) VALUES
(15, 26, 25.00, '2025-09-10 21:17:02', 'Sistema', 50, 'Salida por producción finalizada'),
(16, 4, 5.00, '2025-09-10 21:17:02', 'Sistema', 50, 'Salida por producción finalizada'),
(17, 26, 5.00, '2025-09-10 21:18:48', 'Sistema', 51, 'Salida por producción finalizada'),
(18, 4, 1.00, '2025-09-10 21:18:48', 'Sistema', 51, 'Salida por producción finalizada'),
(19, 26, 5.00, '2025-09-10 21:21:31', 'Sistema', 52, 'Salida por producción finalizada'),
(20, 17, 1.00, '2025-09-10 21:21:31', 'Sistema', 52, 'Salida por producción finalizada'),
(22, 17, 1.00, '2025-09-10 21:22:10', 'Sistema', 53, 'Salida por producción finalizada'),
(23, 3, 2.00, '2025-09-12 20:07:52', 'Sistema', 54, 'Salida por producción finalizada'),
(24, 17, 5.00, '2025-09-23 06:57:53', 'UsuarioX', NULL, 'Salida por producción finalizada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id_ped` int(11) NOT NULL,
  `id_ven` int(11) NOT NULL,
  `fecha_entrega` datetime NOT NULL,
  `estado` enum('Pendiente','Tomado','Completado') DEFAULT NULL,
  `observaciones_pedido` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id_ped`, `id_ven`, `fecha_entrega`, `estado`, `observaciones_pedido`) VALUES
(1, 2, '2025-06-22 09:00:00', 'Pendiente', 'Entregar antes del medio día'),
(2, 4, '2025-06-23 10:00:00', 'Pendiente', 'Cliente requiere empaque especial'),
(3, 2, '2025-06-24 11:00:00', 'Pendiente', 'Agregar factura impresa'),
(4, 4, '2025-06-25 08:00:00', 'Pendiente', 'Urgente'),
(5, 2, '2025-06-26 13:00:00', 'Pendiente', 'Revisar dirección'),
(8, 8, '2025-09-23 11:35:26', 'Pendiente', 'empanada de queso'),
(9, 10, '2025-09-23 11:53:06', 'Pendiente', 'emapanda arroz'),
(10, 11, '2025-10-10 21:00:25', 'Pendiente', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id_proc` int(11) NOT NULL COMMENT 'Identificador de la prduccion',
  `fecha_produccion` date NOT NULL COMMENT 'Identificador del empleado que realizo la venta',
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente',
  `usuario` int(11) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id_proc`, `fecha_produccion`, `estado`, `usuario`, `fecha_hora`) VALUES
(40, '2025-09-07', 'FINALIZADA', 1, '2025-09-07 19:31:04'),
(41, '2025-09-07', 'FINALIZADA', 1, '2025-09-07 19:35:29'),
(42, '2025-09-07', 'FINALIZADA', 1, '2025-09-07 19:52:48'),
(50, '2025-09-10', 'FINALIZADA', 1, '2025-09-10 21:16:51'),
(51, '2025-09-10', 'FINALIZADA', 1, '2025-09-10 21:18:44'),
(52, '2025-09-10', 'FINALIZADA', 1, '2025-09-10 21:21:30'),
(53, '2025-09-10', 'FINALIZADA', 1, '2025-09-10 21:22:03'),
(54, '2025-09-12', 'FINALIZADA', 1, '2025-09-12 20:07:36'),
(66, '2025-10-17', 'Pendiente', 0, '2025-10-11 12:18:17'),
(67, '2025-10-10', 'Pendiente', 0, '2025-10-11 13:02:42');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion_recetas`
--

CREATE TABLE `produccion_recetas` (
  `id_detalle` int(11) NOT NULL,
  `id_produccion` int(11) NOT NULL,
  `id_rec` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `produccion_recetas`
--

INSERT INTO `produccion_recetas` (`id_detalle`, `id_produccion`, `id_rec`, `cantidad`) VALUES
(33, 40, 5, 1),
(35, 42, 5, 1),
(51, 54, 21, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recetas`
--

CREATE TABLE `recetas` (
  `id_rec` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `recetas`
--

INSERT INTO `recetas` (`id_rec`, `nombre`, `descripcion`, `estado`) VALUES
(5, 'Carne\'', 'Empanada de carne¿', 'Inactivo'),
(13, 'Champinon', 'Empanada de ChampiÃ±on', 'Activo'),
(21, 'Mixta', 'Empanada Mixta', 'Activo'),
(22, 'Mixta', 'EM', 'Activo'),
(27, 'carne mixta', 'DAWDADWAD', 'Inactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta_insumos`
--

CREATE TABLE `receta_insumos` (
  `id_rec_ins` int(11) NOT NULL,
  `id_rec` int(11) NOT NULL,
  `id_ins` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `unidad` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `receta_insumos`
--

INSERT INTO `receta_insumos` (`id_rec_ins`, `id_rec`, `id_ins`, `cantidad`, `unidad`) VALUES
(57, 13, 3, 1.00, 'Kilogramos'),
(65, 21, 3, 1.00, 'gramo'),
(67, 5, 3, 2.00, 'l'),
(68, 5, 42, 2.00, 'l'),
(70, 27, 42, 1.00, 'l');

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
(1, 1034280742, 'Marlon', 'Avila', 3214108646, 'Cll 6 #2-47', 'avilamarlon31@gmail.com', 'A', 'A', '81dc9bdb52d04dc20036dbd8313ed055'),
(2, 123456789, 'Ana', 'Pérez', 3001234567, 'Cra 10 #23-45', 'ana.perez@example.com', 'EP', 'A', '81dc9bdb52d04dc20036dbd8313ed055'),
(3, 987654321, 'Carlos', 'Ramírez', 3009876543, 'Av 5 #67-89', 'carlos.ramirez@example.com', 'EP', 'A', '$2a$10$mOUXQ3T7iwyJSLUZug7Xs.Iu/BjlxHn35BukcKo/guUWLFLmMPH5C'),
(4, 112233445, 'Luisa', 'Martínez', 3011122334, 'Cll 12 #34-56', 'luisa.martinez@example.com', 'EP', 'A', '$2a$10$JgJNP.G5pGDCbWSndvzV9.5nmfAg1NQoQo2wgulB7unbN8w/CEKcW'),
(5, 554433221, 'Andrés', 'Lopez', 3023344556, 'Cll 78 #90-12', 'andres.lopez@example.com', 'EV', 'A', '$2a$10$4sdQf/iWdlo60jhH/9JW8eA.Do2fXYJa/LZO9GiWPlqN9CNI6utta'),
(6, 665544332, 'María', 'Gómez', 3034455667, 'Cra 45 #12-34', 'maria.gomez@example.com', 'EV', 'A', '$2a$10$1Wncf/hqx2s7FMPcqIGIBuQQrIJ2WUqFXKQUuM5hva1gircNbWi0G'),
(14, 1103098783, 'Sebastián', 'Mercado', 0, 'Calle 18#123', 'sebassmercado97@gmail.com', 'A', 'A', 'e10adc3949ba59abbe56e057f20f883e'),
(16, 234567890, 'José', 'López', 3002345678, 'Calle 45 #12-34', 'jose.lopez@example.com', 'EV', 'A', '1e777b88dc1bd5273855e2f1173b5649'),
(17, 345678901, 'Luisa', 'Gómez', 3003456789, 'Cra 15 #56-78', 'luisa.gomez@example.com', 'EP', 'A', 'df61e033f317efc41439746b37266e12'),
(18, 456789012, 'Carlos', 'Martínez', 3004567890, 'Calle 78 #90-12', 'carlos.martinez@example.com', 'EV', 'A', '170a57f3020b63757aec78581e4b77f2'),
(19, 567890123, 'Marta', 'Sánchez', 3005678901, 'Cra 20 #34-56', 'marta.sanchez@example.com', 'EP', 'A', 'e9361b43c5dda947d6ff3920c3a48488'),
(20, 678901234, 'Juan', 'Fernández', 3006789012, 'Calle 90 #23-45', 'juan.fernandez@example.com', 'EV', 'A', '75eb3ee5c6e3a2770aa1d333b1e2b1e4'),
(21, 789012345, 'Luis', 'Rodríguez', 3007890123, 'Cra 30 #67-89', 'luis.rodriguez@example.com', 'EP', 'A', '68c55691d8cfa59a03218dbf6855004c'),
(22, 890123456, 'Mónica', 'Díaz', 3008901234, 'Calle 12 #89-01', 'monica.diaz@example.com', 'EV', 'A', 'a135ddbb71f7d2a97788665ab5afae04'),
(23, 901234567, 'Andrés', 'Vásquez', 3009012345, 'Cra 25 #12-34', 'andres.vasquez@example.com', 'EP', 'A', 'c90c91ecd22ecbeae1a7a72857cebeeb'),
(24, 123987654, 'Paula', 'García', 3001239876, 'Calle 50 #34-56', 'paula.garcia@example.com', 'EV', 'A', 'cb46bb7c65b2ac56de85e81b515ad71e');

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
(1, 'directa', '2025-06-16 09:00:00', 1, 1, 30000.00, 'Completada', 'Venta directa'),
(2, 'pedido', '2025-06-17 10:00:00', 2, 2, 50000.00, 'Procesando', 'Pedido a entregar'),
(3, 'directa', '2025-06-18 11:00:00', 3, 3, 15000.00, 'Completada', ''),
(4, 'pedido', '2025-06-19 12:00:00', 4, 4, 40000.00, 'Procesando', 'Cliente nuevo'),
(5, 'directa', '2025-06-20 13:00:00', 5, 5, 20000.00, 'Completada', 'Compra recurrente'),
(8, 'pedido', '2025-09-23 11:29:10', 6, 4, 50000.00, 'Procesando', 'empanada de queso'),
(9, 'directa', '2025-09-23 11:51:01', 3, 3, 35000.00, 'Procesando', 'emapanda arroz'),
(10, 'pedido', '2025-09-23 11:52:25', 5, 9, 4500.00, 'Procesando', 'emapanda arroz'),
(11, 'pedido', '2025-10-10 20:44:34', 1, 8, 500001.00, 'Procesando', '');

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
  ADD PRIMARY KEY (`id_ins`),
  ADD UNIQUE KEY `nombre` (`nombre`,`unidad_medida`);

--
-- Indices de la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  ADD PRIMARY KEY (`id_entrada`),
  ADD KEY `id_ins` (`id_ins`);

--
-- Indices de la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  ADD PRIMARY KEY (`id_salida`),
  ADD KEY `id_ins` (`id_ins`),
  ADD KEY `id_proc` (`id_proc`);

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
  ADD KEY `fk_produccion_usuario` (`usuario`);

--
-- Indices de la tabla `produccion_recetas`
--
ALTER TABLE `produccion_recetas`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `id_produccion` (`id_produccion`),
  ADD KEY `id_rec` (`id_rec`);

--
-- Indices de la tabla `recetas`
--
ALTER TABLE `recetas`
  ADD PRIMARY KEY (`id_rec`);

--
-- Indices de la tabla `receta_insumos`
--
ALTER TABLE `receta_insumos`
  ADD PRIMARY KEY (`id_rec_ins`),
  ADD KEY `id_rec` (`id_rec`),
  ADD KEY `id_ins` (`id_ins`);

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
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  MODIFY `id_entrada` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  MODIFY `id_salida` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_ped` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion', AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT de la tabla `produccion_recetas`
--
ALTER TABLE `produccion_recetas`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT de la tabla `recetas`
--
ALTER TABLE `recetas`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `receta_insumos`
--
ALTER TABLE `receta_insumos`
  MODIFY `id_rec_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)', AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta', AUTO_INCREMENT=12;

--
-- Restricciones para tablas volcadas
--

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
  ADD CONSTRAINT `inv_entradas_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE CASCADE;

--
-- Filtros para la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  ADD CONSTRAINT `inv_salidas_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE CASCADE,
  ADD CONSTRAINT `inv_salidas_ibfk_2` FOREIGN KEY (`id_proc`) REFERENCES `produccion` (`id_proc`) ON DELETE SET NULL;

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `fk_pedidos_Ventas2` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `produccion_recetas`
--
ALTER TABLE `produccion_recetas`
  ADD CONSTRAINT `produccion_recetas_ibfk_1` FOREIGN KEY (`id_produccion`) REFERENCES `produccion` (`id_proc`) ON DELETE CASCADE,
  ADD CONSTRAINT `produccion_recetas_ibfk_2` FOREIGN KEY (`id_rec`) REFERENCES `recetas` (`id_rec`) ON DELETE CASCADE;

--
-- Filtros para la tabla `receta_insumos`
--
ALTER TABLE `receta_insumos`
  ADD CONSTRAINT `receta_insumos_ibfk_1` FOREIGN KEY (`id_rec`) REFERENCES `recetas` (`id_rec`) ON DELETE CASCADE,
  ADD CONSTRAINT `receta_insumos_ibfk_2` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE CASCADE;

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

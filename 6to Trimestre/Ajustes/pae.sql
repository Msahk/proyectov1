-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-10-2025 a las 17:55:30
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
  `estado` enum('Activo','Inactivo','Insumo vencido','Stock insuficiente') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`id_ins`, `nombre`, `cantidad`, `unidad_medida`, `stock_min`, `stock_actual`, `fecha_vencimiento`, `estado`) VALUES
(3, 'Azúcar', 40.00, 'kg', 25.00, 20.00, '2025-10-31', 'Stock insuficiente'),
(4, 'Sal', 50.00, 'kg', 10.00, 20.00, '2025-10-31', 'Activo'),
(17, 'chorizo', 3.00, 'kg', 20.00, 0.00, NULL, 'Stock insuficiente'),
(23, 'Queso', 12.00, 'kg', 1.00, 0.00, NULL, 'Stock insuficiente'),
(26, 'Pollo desmechado', 60.00, 'kg', 20.00, 0.00, '2025-10-11', 'Insumo vencido'),
(38, 'Pan', 25.00, 'kg', 5.00, 0.00, NULL, 'Stock insuficiente'),
(39, 'Tomate', 40.00, 'kg', 15.00, 0.00, NULL, 'Stock insuficiente'),
(40, 'Cebolla', 35.00, 'kg', 10.00, 5.00, '2025-10-02', 'Insumo vencido'),
(42, 'Carne desmecha\'', 5.00, 'ml', 1.00, 1.00, '2025-10-10', 'Insumo vencido'),
(43, 'Carnita', 0.00, 'g', 10.00, 20.00, '2025-10-31', 'Activo'),
(44, 'Huevo', NULL, 'unidad', 10.00, 1.00, '2025-10-24', 'Insumo vencido'),
(45, 'Panela', NULL, 'g', 10.00, 40.00, '2025-10-31', 'Activo'),
(46, 'Cardamomo', NULL, 'g', 5.00, 6.00, '2025-10-31', 'Activo'),
(47, 'Bechamel', NULL, 'g', 10.00, 46.00, '2025-10-31', 'Activo'),
(48, 'peperoni', NULL, 'g', 15.00, 10.00, '2025-10-31', 'Stock insuficiente'),
(49, 'Espaguetti', NULL, 'g', 50.00, 48.00, '2025-10-31', 'Stock insuficiente'),
(50, 'Piña', NULL, 'g', 40.00, 38.00, '2025-10-31', 'Stock insuficiente');

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
(3, 2, '2025-06-24 11:00:00', 'Pendiente', 'Agregar factura impresa'),
(5, 2, '2025-06-26 13:00:00', 'Pendiente', 'Revisar dirección'),
(18, 35, '2025-10-18 22:45:31', 'Completado', 'wadawda'),
(19, 36, '2025-10-18 22:52:23', 'Completado', 'que sea rapido'),
(20, 37, '2025-10-21 00:10:14', 'Pendiente', 'que sea rapido'),
(21, 38, '2025-10-21 01:58:41', 'Completado', 'wena loco'),
(22, 39, '2025-10-21 02:00:07', 'Completado', 'eyyyy'),
(23, 42, '2025-10-21 02:28:05', 'Pendiente', ''),
(24, 43, '2025-10-21 03:17:30', 'Completado', ''),
(25, 44, '2025-10-21 03:18:16', 'Pendiente', ''),
(26, 45, '2025-10-28 00:48:54', 'Pendiente', 'eyyyy'),
(27, 46, '2025-10-28 00:57:21', 'Pendiente', ''),
(28, 47, '2025-10-28 01:00:49', 'Completado', ''),
(29, 48, '2025-10-28 11:50:04', 'Completado', 'wena loco');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id_proc` int(11) NOT NULL COMMENT 'Identificador de la prduccion',
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente',
  `usuario` varchar(150) DEFAULT NULL,
  `fecha_hora` datetime DEFAULT current_timestamp(),
  `fecha_aceptacion` datetime DEFAULT NULL,
  `fecha_finalizacion` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id_proc`, `estado`, `usuario`, `fecha_hora`, `fecha_aceptacion`, `fecha_finalizacion`) VALUES
(74, 'Finalizada', 'Marlon Avila', '2025-10-15 12:00:11', NULL, NULL),
(75, 'Pendiente', 'Sebastián Mercado', '2025-10-15 14:04:38', NULL, NULL),
(76, 'Completada', 'Marlon Avila', '2025-10-16 17:49:31', NULL, NULL),
(77, 'Finalizada', 'Marlon Avila', '2025-10-16 18:25:27', NULL, NULL),
(78, 'Finalizada', 'Sebastián Mercado', '2025-10-16 18:49:46', NULL, NULL),
(79, 'Finalizada', 'Sebastián Mercado', '2025-10-16 19:42:28', NULL, NULL),
(81, 'Finalizada', 'Marlon Avila', '2025-10-16 20:06:25', NULL, NULL),
(82, 'Finalizada', 'Paula García', '2025-10-16 20:17:57', NULL, NULL),
(84, 'Finalizada', 'Marlon Avila', '2025-10-16 20:27:01', NULL, NULL),
(85, 'Finalizada', 'Ana Pérez', '2025-10-16 21:07:07', NULL, NULL),
(86, 'Finalizada', 'Marlon Avila', '2025-10-18 17:45:31', NULL, NULL),
(87, 'Finalizada', 'Sebastián Mercado', '2025-10-18 17:52:23', NULL, NULL),
(88, 'Pendiente', 'Ana Pérez', '2025-10-20 19:10:14', NULL, NULL),
(89, 'Finalizada', 'Marlon Avila', '2025-10-20 20:58:41', NULL, NULL),
(90, 'Pendiente', 'Marlon Avila', '2025-10-20 20:59:40', NULL, NULL),
(91, 'Pendiente', 'Marlon Avila', '2025-10-20 20:59:41', NULL, NULL),
(92, 'Finalizada', 'Marlon Avila', '2025-10-20 21:00:07', NULL, NULL),
(93, 'Finalizada', 'Sebastián Mercado', '2025-10-20 21:13:36', NULL, NULL),
(94, 'Finalizada', 'Luis Rodríguez', '2025-10-20 21:25:51', NULL, NULL),
(95, 'Pendiente', 'Marlon Avila', '2025-10-20 21:28:05', NULL, NULL),
(96, 'Finalizada', 'Marlon Avila', '2025-10-20 22:17:30', NULL, NULL),
(97, 'Pendiente', 'Marlon Avila', '2025-10-20 22:18:16', NULL, NULL),
(98, 'Pendiente', 'Ana Pérez', '2025-10-27 19:48:54', NULL, NULL),
(99, 'Aceptada', 'Paula García', '2025-10-27 19:57:21', '2025-10-28 11:51:26', NULL),
(100, 'Finalizada', 'Marlon Avila', '2025-10-27 20:00:49', '2025-10-28 06:38:18', '2025-10-28 11:52:13'),
(101, 'Finalizada', 'Mónica Díaz', '2025-10-28 06:50:04', '2025-10-28 06:50:23', '2025-10-28 06:50:44');

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
(62, 74, 13, 2),
(63, 75, 13, 2),
(65, 76, 13, 2),
(66, 77, 13, 2),
(67, 78, 31, 2),
(68, 79, 32, 2),
(69, 81, 29, 1),
(70, 82, 29, 2),
(72, 84, 29, 1),
(73, 85, 33, 10),
(74, 86, 35, 2),
(75, 87, 13, 2),
(76, 87, 36, 2),
(77, 88, 35, 2),
(78, 89, 38, 2),
(79, 92, 38, 2),
(80, 93, 38, 2),
(81, 94, 38, 1),
(82, 95, 38, 1),
(83, 96, 39, 1),
(84, 97, 39, 1),
(85, 98, 35, 2),
(86, 99, 34, 2),
(87, 99, 30, 2),
(88, 100, 34, 2),
(89, 101, 40, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `recetas`
--

CREATE TABLE `recetas` (
  `id_rec` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL DEFAULT 0.00,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `recetas`
--

INSERT INTO `recetas` (`id_rec`, `nombre`, `descripcion`, `precio`, `estado`) VALUES
(5, 'Carne\'', 'Empanada de carne¿', 0.00, 'Inactivo'),
(13, 'Champinon', 'Empanada de ChampiÃ±on', 2000.00, 'Activo'),
(27, 'carne mixta', 'DAWDADWAD', 10000.00, 'Inactivo'),
(28, 'Empanada de pollo', 'Una deliciosa empanada de pollo', 0.00, 'Inactivo'),
(29, 'Empanada de Carne', 'Una deliciosa echa de las mejores Carnes', 0.00, 'Activo'),
(30, 'Variadita', 'Una empanda variada', 1100.00, 'Activo'),
(31, 'champolla', 'wena loco', 1500.00, 'Activo'),
(32, 'Empanada de huevo', '', 0.00, 'Activo'),
(33, 'Empanada de panela', 'Una deliciosa empanda', 1000.00, 'Activo'),
(34, 'Empanada de cardamomo', 'Wena', 2500.00, 'Activo'),
(35, 'Banano', '', 1500.00, 'Activo'),
(36, 'Empanda de bechamel', 'Una deliciosa empanada', 1500.00, 'Activo'),
(37, 'Buenaaaa', 'wddwdwd', 1500.00, 'Activo'),
(38, 'Empanada de piña', 'Una buena empanada', 1500.00, 'Activo'),
(39, 'Espaguetti', '', 1500.00, 'Activo'),
(40, 'Empanda de azucar', '', 1000.00, 'Activo');

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
(67, 5, 3, 2.00, 'l'),
(68, 5, 42, 2.00, 'l'),
(70, 27, 42, 1.00, 'l'),
(71, 28, 26, 5.00, 'g'),
(72, 29, 43, 5.00, 'g'),
(73, 30, 4, 1.00, 'g'),
(74, 30, 23, 2.00, 'g'),
(75, 31, 40, 1.00, 'g'),
(76, 31, 43, 1.00, 'g'),
(77, 32, 44, 2.00, 'U'),
(78, 33, 45, 1.00, 'g'),
(79, 34, 46, 2.00, 'g'),
(80, 36, 47, 2.00, 'g'),
(81, 37, 3, 10.00, 'kg'),
(82, 37, 49, 2.00, 'g'),
(83, 38, 50, 2.00, 'g'),
(84, 39, 49, 2.00, 'g'),
(86, 40, 3, 5.00, 'kg');

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
(35, 'pedido', '2025-10-18 22:45:31', 1, 1, 3000.00, 'Completada', 'wadawda'),
(36, 'pedido', '2025-10-18 22:52:23', 14, 2, 7000.00, 'Completada', 'que sea rapido'),
(37, 'pedido', '2025-10-21 00:10:14', 2, 1, 3000.00, 'Procesando', 'que sea rapido'),
(38, 'pedido', '2025-10-21 01:58:41', 1, 1, 3000.00, 'Completada', 'wena loco'),
(39, 'pedido', '2025-10-21 02:00:07', 1, 1, 3000.00, 'Completada', 'eyyyy'),
(40, 'directa', '2025-10-21 02:13:36', 14, 4, 3000.00, 'Completada', 'eyyyy'),
(41, 'directa', '2025-10-21 02:25:51', 21, 1, 1500.00, 'Completada', ''),
(42, 'pedido', '2025-10-21 02:28:05', 1, 1, 1500.00, 'Procesando', ''),
(43, 'pedido', '2025-10-21 03:17:30', 1, 1, 1500.00, 'Completada', ''),
(44, 'pedido', '2025-10-21 03:18:16', 1, 1, 1500.00, 'Procesando', ''),
(45, 'pedido', '2025-10-28 00:48:54', 2, 8, 3000.00, 'Completada', 'eyyyy'),
(46, 'pedido', '2025-10-28 00:57:21', 24, 2, 6200.00, 'Procesando', ''),
(47, 'pedido', '2025-10-28 01:00:49', 1, 1, 5000.00, 'Completada', ''),
(48, 'pedido', '2025-10-28 11:50:04', 22, 3, 2000.00, 'Completada', 'wena loco');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta_produccion`
--

CREATE TABLE `venta_produccion` (
  `id_ven_prod` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `id_produccion` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `venta_produccion`
--

INSERT INTO `venta_produccion` (`id_ven_prod`, `id_venta`, `id_produccion`, `cantidad`) VALUES
(19, 35, 86, 0),
(20, 36, 87, 0),
(21, 37, 88, 0),
(22, 38, 89, 0),
(23, 39, 92, 0),
(24, 40, 93, 0),
(25, 41, 94, 0),
(26, 42, 95, 0),
(27, 43, 96, 0),
(28, 44, 97, 0),
(29, 45, 98, 0),
(30, 46, 99, 0),
(31, 47, 100, 0),
(32, 48, 101, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta_recetas`
--

CREATE TABLE `venta_recetas` (
  `id_venta_receta` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `id_receta` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL DEFAULT 0.00,
  `subtotal` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `venta_recetas`
--

INSERT INTO `venta_recetas` (`id_venta_receta`, `id_venta`, `id_receta`, `cantidad`, `precio`, `subtotal`) VALUES
(22, 35, 35, 2, 0.00, 0.00),
(23, 36, 13, 2, 0.00, 0.00),
(24, 36, 36, 2, 0.00, 0.00),
(25, 37, 35, 2, 0.00, 0.00),
(26, 38, 38, 2, 0.00, 0.00),
(27, 39, 38, 2, 0.00, 0.00),
(28, 40, 38, 2, 0.00, 0.00),
(29, 41, 38, 1, 0.00, 0.00),
(30, 42, 38, 1, 0.00, 0.00),
(31, 43, 39, 1, 0.00, 0.00),
(32, 44, 39, 1, 0.00, 0.00),
(33, 45, 35, 2, 1500.00, 3000.00),
(36, 45, 34, 2, 2000.00, 4000.00),
(37, 46, 34, 2, 2000.00, 4000.00),
(38, 46, 30, 2, 1100.00, 2200.00),
(39, 47, 34, 2, 2500.00, 5000.00),
(40, 48, 40, 2, 1000.00, 2000.00);

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
-- Indices de la tabla `insumos`
--
ALTER TABLE `insumos`
  ADD PRIMARY KEY (`id_ins`),
  ADD UNIQUE KEY `nombre` (`nombre`,`unidad_medida`);

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
-- Indices de la tabla `venta_produccion`
--
ALTER TABLE `venta_produccion`
  ADD PRIMARY KEY (`id_ven_prod`),
  ADD KEY `fk_venta_produccion_venta` (`id_venta`),
  ADD KEY `fk_venta_produccion_produccion` (`id_produccion`);

--
-- Indices de la tabla `venta_recetas`
--
ALTER TABLE `venta_recetas`
  ADD PRIMARY KEY (`id_venta_receta`),
  ADD KEY `fk_venta_recetas_venta` (`id_venta`),
  ADD KEY `fk_venta_recetas_receta` (`id_receta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_ped` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion', AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT de la tabla `produccion_recetas`
--
ALTER TABLE `produccion_recetas`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT de la tabla `recetas`
--
ALTER TABLE `recetas`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de la tabla `receta_insumos`
--
ALTER TABLE `receta_insumos`
  MODIFY `id_rec_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=87;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)', AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta', AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `venta_produccion`
--
ALTER TABLE `venta_produccion`
  MODIFY `id_ven_prod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT de la tabla `venta_recetas`
--
ALTER TABLE `venta_recetas`
  MODIFY `id_venta_receta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Restricciones para tablas volcadas
--

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

--
-- Filtros para la tabla `venta_produccion`
--
ALTER TABLE `venta_produccion`
  ADD CONSTRAINT `fk_venta_produccion_produccion` FOREIGN KEY (`id_produccion`) REFERENCES `produccion` (`id_proc`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_venta_produccion_venta` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_ven`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `venta_recetas`
--
ALTER TABLE `venta_recetas`
  ADD CONSTRAINT `fk_venta_recetas_receta` FOREIGN KEY (`id_receta`) REFERENCES `recetas` (`id_rec`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_venta_recetas_venta` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_ven`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

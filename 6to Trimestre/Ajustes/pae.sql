-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-11-2025 a las 02:53:33
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
  `nit` varchar(20) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `correo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_Cliente`, `nombre`, `nit`, `telefono`, `correo`) VALUES
(1, 'Empresa A', 'TEMP-1', '3112345678', 'contacto@empresaA.com'),
(2, 'Empresa B', 'TEMP-2', '3223456789', 'ventas@empresaB.com'),
(3, 'Juan Ruiz', 'TEMP-3', '3334567890', 'juan.ruiz@gmail.com'),
(4, 'Laura Niño', 'TEMP-4', '3445678901', 'laura.nino@hotmail.com'),
(5, 'Pedro Paz', 'TEMP-5', '3556789012', 'pedro.paz@gmail.com'),
(8, 'Mercado', 'TEMP-8', '3456784567', 'sebassmercado97@gmail.com'),
(9, 'fuquene', 'TEMP-9', '32224567867', 'fuquene@gmail.com'),
(10, 'Chatgpt', 'TEMP-10', '31222121212', 'chatopneai');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `insumos`
--

CREATE TABLE `insumos` (
  `id_ins` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `unidad_medida` varchar(10) NOT NULL,
  `stock_min` decimal(10,2) NOT NULL,
  `stock_actual` decimal(10,2) DEFAULT 0.00,
  `fecha_vencimiento` date DEFAULT NULL,
  `estado` enum('Activo','Inactivo','Insumo vencido','Stock insuficiente') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`id_ins`, `nombre`, `unidad_medida`, `stock_min`, `stock_actual`, `fecha_vencimiento`, `estado`) VALUES
(3, 'Azúcar', 'kg', 25.00, 18.00, '2025-11-08', 'Stock insuficiente'),
(4, 'Sal', 'kg', 10.00, 20.00, '2025-10-31', 'Insumo vencido'),
(17, 'chorizo', 'kg', 20.00, 0.00, NULL, 'Stock insuficiente'),
(23, 'Queso', 'kg', 1.00, 0.00, NULL, 'Stock insuficiente'),
(26, 'Pollo desmechado', 'kg', 20.00, 30.00, '2025-10-11', 'Insumo vencido'),
(38, 'Pan', 'kg', 5.00, 0.00, NULL, 'Stock insuficiente'),
(39, 'Tomate', 'kg', 15.00, 0.00, NULL, 'Stock insuficiente'),
(40, 'Cebolla', 'kg', 10.00, 25.00, '2025-10-01', 'Insumo vencido'),
(42, 'Carne desmecha\'', 'ml', 1.00, 1.00, '2025-10-10', 'Insumo vencido'),
(43, 'Carnita', 'g', 10.00, 20.00, '2025-10-31', 'Insumo vencido'),
(44, 'Huevo', 'unidad', 10.00, 1.00, '2025-10-24', 'Insumo vencido'),
(45, 'Panela', 'g', 10.00, 40.00, '2025-10-31', 'Insumo vencido'),
(46, 'Cardamomo', 'g', 5.00, 4.00, '2025-11-08', 'Stock insuficiente'),
(47, 'Bechamel', 'g', 10.00, 5.00, '2025-10-31', 'Insumo vencido'),
(48, 'peperoni', 'g', 15.00, 10.00, '2025-10-31', 'Insumo vencido'),
(49, 'Espaguetti', 'g', 50.00, 98.00, '2025-11-05', 'Activo'),
(50, 'Piña', 'g', 40.00, 38.00, '2025-10-31', 'Insumo vencido');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `id_pago` int(11) NOT NULL,
  `id_ven` int(11) NOT NULL,
  `fecha_pago` datetime NOT NULL DEFAULT current_timestamp(),
  `monto` decimal(10,2) NOT NULL,
  `tipo_pago` enum('abono','total') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`id_pago`, `id_ven`, `fecha_pago`, `monto`, `tipo_pago`) VALUES
(18, 59, '2025-11-01 16:24:32', 10.00, 'abono'),
(22, 62, '2025-11-01 16:33:52', 3000.00, 'total'),
(23, 63, '2025-11-01 16:57:43', 1500.00, 'total'),
(24, 64, '2025-11-01 17:13:13', 3000.00, 'total'),
(25, 65, '2025-11-01 17:25:22', 1500.00, 'total'),
(26, 67, '2025-11-01 17:54:58', 3.00, 'total'),
(27, 67, '2025-11-01 17:55:30', 2997.00, 'total'),
(28, 68, '2025-11-01 17:56:58', 3000.00, 'total'),
(29, 69, '2025-11-01 18:52:40', 2000.00, 'total'),
(30, 70, '2025-11-01 19:55:43', 1000.00, 'abono'),
(31, 70, '2025-11-01 19:55:53', 1000.00, 'abono'),
(33, 75, '2025-11-01 21:06:28', 1000.00, 'abono'),
(34, 75, '2025-11-01 21:06:38', 1000.00, 'abono'),
(35, 76, '2025-11-01 21:09:40', 2000.00, 'total');

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
(101, 'Finalizada', 'Mónica Díaz', '2025-10-28 06:50:04', '2025-10-28 06:50:23', '2025-10-28 06:50:44'),
(102, 'Pendiente', 'Marlon Avila', '2025-10-30 17:41:02', NULL, NULL),
(103, 'Aceptada', 'Marlon Avila', '2025-10-31 18:59:36', '2025-10-31 19:45:34', NULL),
(104, 'Pendiente', 'Marlon', '2025-11-01 14:43:05', NULL, NULL),
(105, 'Pendiente', 'Marlon', '2025-11-01 16:18:15', NULL, NULL),
(106, 'Finalizada', 'Sebastián', '2025-11-01 16:26:15', '2025-11-01 16:26:31', '2025-11-01 16:26:34'),
(107, 'Finalizada', 'Sebastián', '2025-11-01 16:30:44', '2025-11-01 16:32:02', '2025-11-01 16:32:03'),
(108, 'Finalizada', 'Sebastián', '2025-11-01 16:33:52', '2025-11-01 16:34:00', '2025-11-01 16:34:02'),
(109, 'Finalizada', 'Sebastián', '2025-11-01 16:57:43', '2025-11-01 16:57:53', '2025-11-01 16:57:54'),
(110, 'Finalizada', 'Sebastián', '2025-11-01 17:13:13', '2025-11-01 17:13:26', '2025-11-01 17:13:27'),
(111, 'Finalizada', 'Sebastián', '2025-11-01 17:25:22', '2025-11-01 17:25:31', '2025-11-01 17:25:33'),
(112, 'Finalizada', 'Marlon', '2025-11-01 17:55:30', '2025-11-01 17:55:40', '2025-11-01 17:55:41'),
(113, 'Finalizada', 'Marlon', '2025-11-01 17:56:58', '2025-11-01 17:57:04', '2025-11-01 17:57:06'),
(114, 'Finalizada', 'Marlon', '2025-11-01 18:52:40', '2025-11-01 18:53:15', '2025-11-01 18:53:17'),
(115, 'Finalizada', 'Sebastián', '2025-11-01 19:55:53', '2025-11-01 19:56:09', '2025-11-01 19:56:10'),
(116, 'Pendiente', 'Marlon', '2025-11-01 19:58:17', NULL, NULL),
(117, 'Finalizada', 'Sebastián', '2025-11-01 21:06:38', '2025-11-01 21:06:54', '2025-11-01 21:07:06'),
(118, 'Finalizada', 'Marlon', '2025-11-01 21:09:40', '2025-11-01 21:09:46', '2025-11-01 21:09:48');

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
(96, 115, 43, 2),
(97, 116, 43, 1),
(98, 117, 43, 2),
(99, 118, 43, 2);

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
(42, 'wad', 'wd', 150.00, 'Activo'),
(43, 'Empanda de azucar', 'Una deliciosa empanda dulce', 1000.00, 'Activo');

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
(88, 43, 3, 2.00, 'kg');

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
  `id_asignado` int(11) DEFAULT NULL,
  `id_Cliente` int(11) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `estado` enum('Pago pendiente','Procesando','Pago completo','Completada') NOT NULL DEFAULT 'Pago pendiente',
  `observaciones` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_ven`, `Tipo`, `fecha`, `id_usu`, `id_asignado`, `id_Cliente`, `total`, `estado`, `observaciones`) VALUES
(1, 'directa', '2025-06-16 09:00:00', 1, NULL, 1, 30000.00, 'Completada', 'Venta directa'),
(2, 'pedido', '2025-06-17 10:00:00', 2, NULL, 2, 50000.00, 'Procesando', 'Pedido a entregar'),
(35, 'pedido', '2025-10-18 22:45:31', 1, NULL, 1, 3000.00, 'Completada', 'wadawda'),
(36, 'pedido', '2025-10-18 22:52:23', 14, NULL, 2, 7000.00, 'Completada', 'que sea rapido'),
(37, 'pedido', '2025-10-21 00:10:14', 2, NULL, 1, 3000.00, 'Procesando', 'que sea rapido'),
(38, 'pedido', '2025-10-21 01:58:41', 1, NULL, 1, 3000.00, 'Completada', 'wena loco'),
(39, 'pedido', '2025-10-21 02:00:07', 1, NULL, 1, 3000.00, 'Completada', 'eyyyy'),
(40, 'directa', '2025-10-21 02:13:36', 14, NULL, 4, 3000.00, 'Completada', 'eyyyy'),
(41, 'directa', '2025-10-21 02:25:51', 21, NULL, 1, 1500.00, 'Completada', ''),
(42, 'pedido', '2025-10-21 02:28:05', 1, NULL, 1, 1500.00, 'Procesando', ''),
(43, 'pedido', '2025-10-21 03:17:30', 1, NULL, 1, 1500.00, 'Completada', ''),
(44, 'pedido', '2025-10-21 03:18:16', 1, NULL, 1, 1500.00, 'Procesando', ''),
(45, 'pedido', '2025-10-28 00:48:54', 2, NULL, 8, 3000.00, 'Completada', 'eyyyy'),
(46, 'pedido', '2025-10-28 00:57:21', 24, NULL, 2, 6200.00, 'Procesando', ''),
(47, 'pedido', '2025-10-28 01:00:49', 1, NULL, 1, 5000.00, 'Completada', ''),
(48, 'pedido', '2025-10-28 11:50:04', 22, NULL, 3, 2000.00, 'Completada', 'wena loco'),
(49, 'pedido', '2025-10-30 22:41:02', NULL, NULL, 2, 4500.00, 'Procesando', 'ewwe'),
(58, 'pedido', '2025-10-31 23:59:36', 1, 2, 1, 3000.00, 'Procesando', 'ew'),
(59, 'pedido', '2025-11-05 10:17:07', 1, 2, 2, 3000.00, 'Procesando', 'awd'),
(62, 'pedido', '2025-11-02 02:33:35', 14, 3, 2, 3000.00, 'Completada', 'awd'),
(63, 'pedido', '2025-11-02 02:57:36', 14, 3, 2, 1500.00, 'Completada', 'awd'),
(64, 'pedido', '2025-11-02 03:13:06', 14, 3, 2, 3000.00, 'Completada', 'awd'),
(65, 'pedido', '2025-11-02 03:25:05', 14, 3, 2, 1500.00, 'Completada', 'awd'),
(67, 'pedido', '2025-11-02 08:54:40', 1, 2, 1, 3000.00, 'Completada', 'awd'),
(68, 'pedido', '2025-11-02 03:56:49', 1, 2, 2, 3000.00, 'Completada', 'awd'),
(69, 'pedido', '2025-11-02 04:52:21', 1, 3, 2, 2000.00, 'Completada', 'wda'),
(70, 'pedido', '2025-11-02 10:55:33', 14, 3, 2, 2000.00, 'Completada', 'Esperemos Haber'),
(73, 'pedido', '2025-11-02 01:33:01', 14, 3, 1, 2000.00, 'Pago pendiente', 'wena loco'),
(74, 'pedido', '2025-11-02 01:46:02', 1, 2, 2, 2000.00, 'Pago pendiente', 'wena loco'),
(75, 'pedido', '2025-11-02 12:05:48', 14, 3, 1, 2000.00, 'Completada', 'wena loco'),
(76, 'pedido', '2025-11-02 07:09:23', 1, 2, 2, 2000.00, 'Completada', 'wena loco'),
(77, 'pedido', '2025-11-04 22:03:11', 1, 2, 2, 2000.00, 'Pago pendiente', 'wena loco'),
(78, 'pedido', '2025-11-04 23:51:19', 1, 2, 10, 2000.00, 'Pago pendiente', 'wena');

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
(32, 48, 101, 0),
(33, 49, 102, 0),
(34, 58, 103, 0),
(35, 59, 104, 0),
(36, 59, 105, 0),
(39, 62, 108, 0),
(40, 63, 109, 0),
(41, 64, 110, 0),
(42, 65, 111, 0),
(43, 67, 112, 0),
(44, 68, 113, 0),
(45, 69, 114, 0),
(46, 70, 115, 0),
(48, 75, 117, 0),
(49, 76, 118, 0);

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
(54, 70, 43, 2, 1000.00, 2000.00),
(57, 73, 43, 2, 1000.00, 2000.00),
(58, 74, 43, 2, 1000.00, 2000.00),
(59, 75, 43, 2, 1000.00, 2000.00),
(60, 76, 43, 2, 1000.00, 2000.00),
(61, 77, 43, 2, 1000.00, 2000.00),
(62, 78, 43, 2, 1000.00, 2000.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id_Cliente`),
  ADD UNIQUE KEY `telefono_UNIQUE` (`telefono`),
  ADD UNIQUE KEY `correo_UNIQUE` (`correo`),
  ADD UNIQUE KEY `nit` (`nit`);

--
-- Indices de la tabla `insumos`
--
ALTER TABLE `insumos`
  ADD PRIMARY KEY (`id_ins`),
  ADD UNIQUE KEY `nombre` (`nombre`,`unidad_medida`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`id_pago`),
  ADD KEY `id_ven` (`id_ven`);

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
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion', AUTO_INCREMENT=119;

--
-- AUTO_INCREMENT de la tabla `produccion_recetas`
--
ALTER TABLE `produccion_recetas`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;

--
-- AUTO_INCREMENT de la tabla `recetas`
--
ALTER TABLE `recetas`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT de la tabla `receta_insumos`
--
ALTER TABLE `receta_insumos`
  MODIFY `id_rec_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)', AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta', AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT de la tabla `venta_produccion`
--
ALTER TABLE `venta_produccion`
  MODIFY `id_ven_prod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de la tabla `venta_recetas`
--
ALTER TABLE `venta_recetas`
  MODIFY `id_venta_receta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE CASCADE ON UPDATE CASCADE;

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

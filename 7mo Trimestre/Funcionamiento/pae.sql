-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-11-2025 a las 04:53:43
-- Versión del servidor: 10.11.15-MariaDB
-- Versión de PHP: 8.0.30

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_Cliente`, `nombre`, `nit`, `telefono`, `correo`) VALUES
(1, 'Empresa A', 'TEMP-1', '3112345678', 'contacto@empresaA.com'),
(2, 'Empresa B', 'TEMP-2', '3223456788', 'ventas@empresaB.com'),
(3, 'Juan Ruiz', 'TEMP-3', '3334567890', 'juan.ruiz@gmail.com'),
(4, 'Laura Niño', 'TEMP-4', '3445678901', 'laura.nino@hotmail.com'),
(5, 'Pedro Paz', 'TEMP-5', '3556789012', 'pedro.paz@gmail.com'),
(8, 'Mercado', 'TEMP-8', '3456784567', 'sebassmercado97@gmail.com'),
(9, 'fuquene', 'TEMP-9', '32224567867', 'fuquene@gmail.com'),
(10, 'Chatgpt', 'TEMP-10', '31222121212', 'chatopneai'),
(12, 'WENA', '223232323', '31441441441', 'sebassmercado154@gmail.com'),
(13, 'spartacus', '313131', '1231231321', 'spartacus13@gmail.com'),
(14, 'Sebastian', '123344-0', '1131313', 'sebass@gmail.com'),
(15, 'Giovanny', '23415-0', '3145525167', 'Giovanny@soy.sena.edu.co');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_insumo`
--

CREATE TABLE `detalle_insumo` (
  `id_detalle` int(11) NOT NULL,
  `id_ins` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `fecha_ingreso` datetime NOT NULL DEFAULT current_timestamp(),
  `fecha_vencimiento` date DEFAULT NULL,
  `estado` enum('Activo','Vencido','Agotado','Eliminado') DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_insumo`
--

INSERT INTO `detalle_insumo` (`id_detalle`, `id_ins`, `cantidad`, `fecha_ingreso`, `fecha_vencimiento`, `estado`) VALUES
(1, 3, 10.00, '2025-11-05 00:00:00', '2025-11-13', 'Activo'),
(2, 3, 12.00, '2025-11-05 00:00:00', '2025-11-02', 'Activo'),
(3, 3, 10.00, '2025-11-05 00:00:00', '2025-11-30', 'Activo'),
(4, 3, 10.00, '2025-11-05 00:00:00', '2025-11-30', 'Eliminado'),
(5, 3, 10.00, '2025-11-05 00:00:00', '2025-11-30', 'Eliminado'),
(6, 47, 0.00, '2025-11-05 00:00:00', '2025-11-25', 'Agotado'),
(7, 47, 19.00, '2025-11-05 00:00:00', '2025-11-18', 'Activo'),
(8, 46, 10.00, '2025-11-05 00:00:00', '2025-11-14', 'Activo'),
(9, 3, 2.00, '2025-11-05 00:00:00', '2025-11-29', 'Eliminado'),
(10, 52, 10.00, '2025-11-06 00:00:00', '2025-11-30', 'Eliminado'),
(11, 52, 5.00, '2025-11-06 00:00:00', '2025-11-30', 'Eliminado'),
(12, 52, 10.00, '2025-11-06 00:00:00', '2025-11-30', 'Eliminado'),
(13, 3, 11.00, '2025-11-06 00:00:00', '2025-11-30', 'Activo'),
(14, 3, 2.00, '2025-11-06 00:00:00', '2025-11-25', 'Activo'),
(15, 3, 1.00, '2025-11-06 13:25:49', '2025-11-27', 'Activo'),
(16, 3, 1.00, '2025-11-06 13:38:46', '2025-11-30', 'Activo'),
(17, 52, 10.00, '2025-11-06 13:48:35', '2025-11-30', 'Activo'),
(18, 52, 10.00, '2025-11-06 13:48:58', '2025-11-24', 'Activo'),
(19, 53, 0.00, '2025-11-06 14:19:04', '2025-11-23', 'Agotado'),
(20, 53, 4.00, '2025-11-06 14:19:17', '2025-11-21', 'Activo'),
(21, 54, 6.00, '2025-11-06 17:19:39', '2025-11-20', 'Eliminado'),
(22, 54, 5.00, '2025-11-06 17:20:11', '2025-11-11', 'Eliminado'),
(23, 54, 5.00, '2025-11-06 17:25:26', '2025-11-08', 'Eliminado'),
(24, 53, 0.00, '2025-11-06 20:27:13', '2025-11-08', 'Agotado'),
(25, 55, 0.00, '2025-11-08 14:58:11', '2025-11-10', 'Agotado'),
(26, 55, 0.00, '2025-11-08 14:58:21', '2025-11-04', 'Vencido'),
(27, 55, 0.00, '2025-11-08 15:02:24', '2025-11-09', 'Agotado'),
(28, 55, 5.00, '2025-11-08 15:19:46', '2025-11-06', 'Vencido'),
(29, 55, 1.00, '2025-11-08 15:34:27', '2025-11-06', 'Eliminado'),
(30, 55, 0.00, '2025-11-08 16:50:52', '2025-11-04', 'Vencido'),
(31, 55, 1.00, '2025-11-08 16:51:06', '2025-11-04', 'Vencido'),
(32, 53, 10.00, '2025-11-10 16:42:16', '2025-11-18', 'Eliminado'),
(33, 53, 10.00, '2025-11-10 17:33:55', '2025-11-12', 'Vencido'),
(34, 53, 10.00, '2025-11-16 10:45:07', '2025-11-17', 'Activo'),
(35, 53, 1.00, '2025-11-16 10:55:27', '2025-11-18', 'Activo'),
(36, 57, 5.00, '2025-11-16 11:13:20', '2025-11-18', 'Activo'),
(37, 57, 1.00, '2025-11-16 11:58:10', '2025-11-17', 'Activo'),
(38, 57, 1.00, '2025-11-16 12:08:27', '2025-11-17', 'Activo'),
(39, 57, 1.00, '2025-11-16 12:21:59', '2025-11-19', 'Activo'),
(40, 57, 1.00, '2025-11-16 12:41:14', '2025-11-18', 'Activo'),
(41, 57, 1.00, '2025-11-16 12:41:58', '2025-11-18', 'Activo'),
(42, 54, 1.00, '2025-11-16 14:31:27', '2025-11-17', 'Eliminado'),
(43, 54, 1.00, '2025-11-16 14:34:31', '2025-11-17', 'Eliminado'),
(44, 54, 0.00, '2025-11-16 14:36:14', '2025-11-18', 'Eliminado'),
(45, 54, 7.00, '2025-11-16 14:36:14', '2025-11-17', 'Eliminado'),
(46, 54, 0.00, '2025-11-16 14:37:09', '2025-11-17', 'Eliminado'),
(47, 54, 0.00, '2025-11-16 20:12:43', '2025-11-18', 'Agotado'),
(48, 54, 0.00, '2025-11-16 20:45:13', '2025-11-18', 'Agotado'),
(49, 54, 0.00, '2025-11-16 22:45:53', '2025-11-18', 'Agotado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `idHist` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `accion` enum('Entrada','Salida','Edición') NOT NULL,
  `estado` enum('Producción','Vencimiento','Edición','Ingreso de lote','Eliminado') NOT NULL DEFAULT 'Ingreso de lote',
  `cantidad` int(11) NOT NULL DEFAULT 0,
  `stock_actual` int(11) NOT NULL DEFAULT 0,
  `novedad` varchar(100) DEFAULT NULL,
  `id_ins` int(11) NOT NULL,
  `id_detalle` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `historial`
--

INSERT INTO `historial` (`idHist`, `fecha`, `accion`, `estado`, `cantidad`, `stock_actual`, `novedad`, `id_ins`, `id_detalle`) VALUES
(1, '2025-11-05 18:25:05', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (10.0 unidades)', 3, 1),
(2, '2025-11-05 18:27:58', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (20.0 unidades)', 3, 2),
(3, '2025-11-05 18:34:07', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (10.0 unidades)', 3, 3),
(4, '2025-11-05 18:45:26', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (10.0 unidades)', 3, 4),
(5, '2025-11-05 18:55:03', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (10.0 unidades)', 3, 5),
(6, '2025-11-05 18:58:41', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (10.0 unidades)', 47, 6),
(7, '2025-11-05 18:58:58', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (20.0 unidades)', 47, 7),
(8, '2025-11-05 19:04:00', 'Entrada', 'Ingreso de lote', 0, 0, 'Ingreso de lote (12.0 unidades)', 46, 8),
(9, '2025-11-05 20:07:54', 'Salida', 'Ingreso de lote', 0, 0, 'wda', 3, 1),
(10, '2025-11-05 20:21:52', 'Salida', 'Ingreso de lote', 0, 0, 'wdwd', 3, 1),
(11, '2025-11-05 20:36:11', 'Salida', 'Ingreso de lote', 0, 0, 'wenax2', 3, 5),
(12, '2025-11-05 20:57:52', 'Salida', 'Ingreso de lote', 0, 0, 'claro', 3, 5),
(13, '2025-11-05 21:49:04', 'Salida', 'Ingreso de lote', 0, 0, 'porquesi', 3, 4),
(14, '2025-11-05 21:53:40', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 2.0 unidades', 3, 9),
(15, '2025-11-05 21:53:56', 'Salida', 'Ingreso de lote', 0, 0, 'weuuu', 3, 9),
(16, '2025-11-06 05:48:21', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 52, 10),
(17, '2025-11-06 05:58:14', 'Salida', 'Ingreso de lote', 0, 0, 'eliminado', 52, 10),
(18, '2025-11-06 06:12:05', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 5.0 unidades', 52, 11),
(19, '2025-11-06 06:17:29', 'Salida', 'Ingreso de lote', 0, 0, 'se fue', 52, 11),
(20, '2025-11-06 07:21:38', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 52, 12),
(21, '2025-11-06 07:23:07', 'Salida', 'Ingreso de lote', 0, 0, 'Se vencio', 52, 12),
(22, '2025-11-06 12:03:56', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 11.0 unidades', 3, 13),
(23, '2025-11-06 13:07:42', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 2.0 unidades', 3, 14),
(24, '2025-11-06 13:25:49', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 1.0 unidades', 3, 15),
(25, '2025-11-06 13:38:46', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 1.0 unidades', 3, 16),
(26, '2025-11-06 13:48:35', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 52, 17),
(27, '2025-11-06 13:48:58', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 52, 18),
(28, '2025-11-06 14:19:04', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 53, 19),
(29, '2025-11-06 14:19:17', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 53, 20),
(30, '2025-11-06 17:19:39', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 20.0 unidades', 54, 21),
(31, '2025-11-06 17:20:11', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 54, 22),
(32, '2025-11-06 17:25:26', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 5.0 unidades', 54, 23),
(33, '2025-11-06 20:27:13', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 53, 24),
(34, '2025-11-08 14:58:11', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 55, 25),
(35, '2025-11-08 14:58:21', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 55, 26),
(36, '2025-11-08 15:02:24', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 1.0 unidades', 55, 27),
(37, '2025-11-08 15:19:46', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 55, 28),
(38, '2025-11-08 15:34:27', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 1.0 unidades', 55, 29),
(39, '2025-11-08 16:50:52', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 1.0 unidades', 55, 30),
(40, '2025-11-08 16:51:06', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 1.0 unidades', 55, 31),
(41, '2025-11-08 16:51:30', 'Salida', 'Ingreso de lote', 0, 0, 'se vencio', 55, 29),
(42, '2025-11-10 16:42:16', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 53, 32),
(43, '2025-11-10 17:33:03', 'Salida', 'Ingreso de lote', 0, 0, 'chao', 53, 32),
(44, '2025-11-10 17:33:55', 'Entrada', 'Ingreso de lote', 0, 0, 'Se agregó lote de 10.0 unidades', 53, 33),
(45, '2025-11-16 12:41:14', 'Entrada', 'Ingreso de lote', 1, 1, 'Se agregó lote de 1.0 unidades', 57, 40),
(46, '2025-11-16 12:41:58', 'Entrada', 'Ingreso de lote', 1, 2, 'Se agregó lote de 1.0 unidades', 57, 41),
(47, '2025-11-16 14:03:49', 'Salida', 'Ingreso de lote', 6, 0, 'bayss', 54, 21),
(48, '2025-11-16 14:04:05', 'Salida', 'Ingreso de lote', 5, 0, 'chao', 54, 23),
(49, '2025-11-16 14:31:27', 'Entrada', 'Ingreso de lote', 1, -10, 'Se agregó lote de 1.0 unidades', 54, 42),
(50, '2025-11-16 14:31:45', 'Salida', 'Eliminado', 1, 0, 'bayss', 54, 42),
(51, '2025-11-16 14:32:14', 'Salida', 'Vencimiento', 5, 0, 'wena', 54, 22),
(52, '2025-11-16 14:34:31', 'Entrada', 'Ingreso de lote', 1, -15, 'Se agregó lote de 1.0 unidades', 54, 43),
(53, '2025-11-16 14:36:14', 'Entrada', 'Ingreso de lote', 2, -13, 'Se agregó lote de 2.0 unidades', 54, 44),
(54, '2025-11-16 14:36:51', 'Entrada', 'Ingreso de lote', 20, 7, 'Se agregó lote de 20.0 unidades', 54, 45),
(55, '2025-11-16 14:37:09', 'Entrada', 'Ingreso de lote', 1, 8, 'Se agregó lote de 1.0 unidades', 54, 46),
(56, '2025-11-16 14:38:55', 'Salida', 'Eliminado', 1, 7, 'bayss', 54, 43),
(57, '2025-11-16 14:39:15', 'Salida', 'Eliminado', 7, 0, 'Chaooo', 54, 45),
(58, '2025-11-16 14:39:25', 'Salida', 'Eliminado', 0, 0, 'bayss', 54, 44),
(59, '2025-11-16 14:39:29', 'Salida', 'Eliminado', 0, 0, 'bayss', 54, 46),
(60, '2025-11-16 16:47:06', 'Edición', 'Edición', 19, 0, 'salida', 47, 7),
(61, '2025-11-16 16:47:50', 'Edición', 'Edición', 19, -19, 'Entarda', 47, 7),
(62, '2025-11-16 20:12:43', 'Entrada', 'Ingreso de lote', 10, 10, 'Se agregó lote de 10.0 unidades', 54, 47),
(63, '2025-11-16 20:45:13', 'Entrada', 'Ingreso de lote', 6, 16, 'Se agregó lote de 6.0 unidades', 54, 48),
(64, '2025-11-16 21:03:44', 'Edición', 'Edición', 6, 4, 'Entarda', 54, 48),
(65, '2025-11-16 21:04:23', 'Edición', 'Edición', 6, 10, 'Prueba', 54, 48),
(66, '2025-11-16 22:45:53', 'Entrada', 'Ingreso de lote', 2, 14, 'Se agregó lote de 2.0 unidades', 54, 49);

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
  `estado` enum('Activo','Inactivo','Stock insuficiente') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`id_ins`, `nombre`, `unidad_medida`, `stock_min`, `stock_actual`, `estado`) VALUES
(3, 'Azúcar', 'kg', 25.00, 47.00, 'Activo'),
(4, 'Sal', 'kg', 10.00, 0.00, 'Stock insuficiente'),
(23, 'Queso', 'kg', 1.00, 0.00, 'Stock insuficiente'),
(26, 'Pollo desmechado', 'kg', 20.00, 0.00, 'Stock insuficiente'),
(38, 'Pan', 'kg', 5.00, 0.00, 'Stock insuficiente'),
(39, 'Tomate', 'kg', 15.00, 0.00, 'Stock insuficiente'),
(40, 'Cebolla', 'kg', 10.00, 0.00, 'Stock insuficiente'),
(42, 'Carne desmecha\'', 'ml', 1.00, 0.00, 'Stock insuficiente'),
(43, 'Carnita', 'g', 10.00, 0.00, 'Stock insuficiente'),
(44, 'Huevo', 'unidad', 10.00, 0.00, 'Stock insuficiente'),
(45, 'Panela', 'g', 10.00, 0.00, 'Stock insuficiente'),
(46, 'Cardamomo', 'g', 5.00, 10.00, 'Activo'),
(47, 'Bechamel', 'g', 10.00, 19.00, 'Activo'),
(48, 'peperoni', 'g', 15.00, 0.00, 'Stock insuficiente'),
(49, 'Espaguetti', 'g', 50.00, 0.00, 'Stock insuficiente'),
(50, 'Piña', 'g', 40.00, 0.00, 'Stock insuficiente'),
(51, 'quesito', 'g', 50.00, 0.00, 'Stock insuficiente'),
(52, 'carnesita', 'g', 10.00, 20.00, 'Activo'),
(53, 'Baguet', 'g', 10.00, 15.00, 'Activo'),
(54, 'aniz', 'g', 10.00, 0.00, 'Stock insuficiente'),
(55, 'Maiz', 'g', 10.00, 0.00, 'Stock insuficiente'),
(57, 'polemo', 'g', 10.00, 10.00, 'Activo');

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
(35, 76, '2025-11-01 21:09:40', 2000.00, 'total'),
(36, 86, '2025-11-06 13:47:37', 2400.00, 'total'),
(37, 87, '2025-11-06 13:50:51', 4000.00, 'total'),
(38, 88, '2025-11-06 14:21:16', 2000.00, 'total'),
(39, 89, '2025-11-06 14:31:48', 1000.00, 'abono'),
(40, 89, '2025-11-06 14:32:03', 1000.00, 'abono'),
(41, 90, '2025-11-06 16:18:06', 1000.00, 'abono'),
(42, 90, '2025-11-06 16:18:45', 2400.00, 'abono'),
(43, 91, '2025-11-06 17:17:54', 1200.00, 'abono'),
(44, 92, '2025-11-06 17:27:18', 2000.00, 'total'),
(45, 93, '2025-11-06 20:22:00', 1000.00, 'abono'),
(46, 93, '2025-11-06 20:22:23', 2400.00, 'abono'),
(47, 94, '2025-11-06 20:33:26', 2000.00, 'total'),
(48, 95, '2025-11-06 20:36:38', 2000.00, 'total'),
(49, 96, '2025-11-08 10:25:17', 1000.00, 'total'),
(50, 97, '2025-11-08 11:52:35', 1000.00, 'abono'),
(51, 98, '2025-11-08 12:15:57', 1000.00, 'total'),
(52, 99, '2025-11-08 13:34:22', 1000.00, 'total'),
(53, 100, '2025-11-08 14:40:25', 1000.00, 'total'),
(54, 101, '2025-11-08 15:01:09', 6000.00, 'total'),
(55, 102, '2025-11-08 15:03:21', 1000.00, 'total'),
(56, 103, '2025-11-08 17:13:16', 1000.00, 'total'),
(57, 104, '2025-11-10 20:28:29', 3000.00, 'total'),
(58, 105, '2025-11-10 20:30:15', 1500.00, 'total'),
(59, 106, '2025-11-10 20:32:47', 6000.00, 'abono'),
(60, 106, '2025-11-10 20:33:06', 6000.00, 'abono'),
(61, 107, '2025-11-10 20:37:14', 2000.00, 'total'),
(62, 108, '2025-11-12 18:39:16', 2000.00, 'total'),
(65, 109, '2025-11-16 20:31:16', 1200.00, 'abono'),
(66, 109, '2025-11-16 20:32:24', 1400.00, 'abono'),
(68, 110, '2025-11-16 20:43:20', 2400.00, 'total'),
(69, 111, '2025-11-16 20:46:50', 6000.00, 'total');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id_proc` int(11) NOT NULL COMMENT 'Identificador de la prduccion',
  `estado` enum('Pendiente','Aceptada','Finalizada','Esperando insumos') NOT NULL DEFAULT 'Pendiente',
  `fecha_hora` datetime DEFAULT current_timestamp(),
  `fecha_aceptacion` datetime DEFAULT NULL,
  `fecha_finalizacion` datetime DEFAULT NULL,
  `id_usu` int(11) NOT NULL,
  `id_asignado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id_proc`, `estado`, `fecha_hora`, `fecha_aceptacion`, `fecha_finalizacion`, `id_usu`, `id_asignado`) VALUES
(75, 'Pendiente', '2025-10-15 14:04:38', NULL, NULL, 14, NULL),
(76, '', '2025-10-16 17:49:31', NULL, NULL, 14, NULL),
(77, 'Finalizada', '2025-10-16 18:25:27', NULL, NULL, 14, NULL),
(78, 'Finalizada', '2025-10-16 18:49:46', NULL, NULL, 14, NULL),
(79, 'Finalizada', '2025-10-16 19:42:28', NULL, NULL, 14, NULL),
(81, 'Finalizada', '2025-10-16 20:06:25', NULL, NULL, 14, NULL),
(82, 'Finalizada', '2025-10-16 20:17:57', NULL, NULL, 14, NULL),
(84, 'Finalizada', '2025-10-16 20:27:01', NULL, NULL, 14, NULL),
(85, 'Finalizada', '2025-10-16 21:07:07', NULL, NULL, 14, NULL),
(86, 'Finalizada', '2025-10-18 17:45:31', NULL, NULL, 14, NULL),
(87, 'Finalizada', '2025-10-18 17:52:23', NULL, NULL, 14, NULL),
(88, 'Pendiente', '2025-10-20 19:10:14', NULL, NULL, 14, NULL),
(89, 'Finalizada', '2025-10-20 20:58:41', NULL, NULL, 14, NULL),
(90, 'Pendiente', '2025-10-20 20:59:40', NULL, NULL, 14, NULL),
(91, 'Pendiente', '2025-10-20 20:59:41', NULL, NULL, 14, NULL),
(92, 'Finalizada', '2025-10-20 21:00:07', NULL, NULL, 14, NULL),
(93, 'Finalizada', '2025-10-20 21:13:36', NULL, NULL, 14, NULL),
(94, 'Finalizada', '2025-10-20 21:25:51', NULL, NULL, 14, NULL),
(95, 'Pendiente', '2025-10-20 21:28:05', NULL, NULL, 14, NULL),
(96, 'Finalizada', '2025-10-20 22:17:30', NULL, NULL, 14, NULL),
(97, 'Pendiente', '2025-10-20 22:18:16', NULL, NULL, 14, NULL),
(98, 'Pendiente', '2025-10-27 19:48:54', NULL, NULL, 14, NULL),
(99, 'Aceptada', '2025-10-27 19:57:21', '2025-10-28 11:51:26', NULL, 14, NULL),
(100, 'Finalizada', '2025-10-27 20:00:49', '2025-10-28 06:38:18', '2025-10-28 11:52:13', 14, NULL),
(101, 'Finalizada', '2025-10-28 06:50:04', '2025-10-28 06:50:23', '2025-10-28 06:50:44', 14, NULL),
(102, 'Pendiente', '2025-10-30 17:41:02', NULL, NULL, 14, NULL),
(103, 'Aceptada', '2025-10-31 18:59:36', '2025-10-31 19:45:34', NULL, 14, NULL),
(104, 'Pendiente', '2025-11-01 14:43:05', NULL, NULL, 14, NULL),
(105, 'Pendiente', '2025-11-01 16:18:15', NULL, NULL, 14, NULL),
(106, 'Finalizada', '2025-11-01 16:26:15', '2025-11-01 16:26:31', '2025-11-01 16:26:34', 14, NULL),
(107, 'Finalizada', '2025-11-01 16:30:44', '2025-11-01 16:32:02', '2025-11-01 16:32:03', 14, NULL),
(108, 'Finalizada', '2025-11-01 16:33:52', '2025-11-01 16:34:00', '2025-11-01 16:34:02', 14, NULL),
(109, 'Finalizada', '2025-11-01 16:57:43', '2025-11-01 16:57:53', '2025-11-01 16:57:54', 14, NULL),
(110, 'Finalizada', '2025-11-01 17:13:13', '2025-11-01 17:13:26', '2025-11-01 17:13:27', 14, NULL),
(111, 'Finalizada', '2025-11-01 17:25:22', '2025-11-01 17:25:31', '2025-11-01 17:25:33', 14, NULL),
(112, 'Finalizada', '2025-11-01 17:55:30', '2025-11-01 17:55:40', '2025-11-01 17:55:41', 14, NULL),
(113, 'Finalizada', '2025-11-01 17:56:58', '2025-11-01 17:57:04', '2025-11-01 17:57:06', 14, NULL),
(114, 'Finalizada', '2025-11-01 18:52:40', '2025-11-01 18:53:15', '2025-11-01 18:53:17', 14, NULL),
(115, 'Finalizada', '2025-11-01 19:55:53', '2025-11-01 19:56:09', '2025-11-01 19:56:10', 14, NULL),
(116, 'Aceptada', '2025-11-01 19:58:17', '2025-11-16 20:42:39', NULL, 14, NULL),
(117, 'Finalizada', '2025-11-01 21:06:38', '2025-11-01 21:06:54', '2025-11-01 21:07:06', 14, NULL),
(118, 'Finalizada', '2025-11-01 21:09:40', '2025-11-01 21:09:46', '2025-11-01 21:09:48', 14, NULL),
(119, 'Finalizada', '2025-11-06 13:47:37', '2025-11-06 13:48:04', '2025-11-06 13:48:06', 14, NULL),
(120, 'Finalizada', '2025-11-06 13:50:52', '2025-11-06 13:51:00', '2025-11-06 13:51:01', 14, NULL),
(121, 'Finalizada', '2025-11-06 14:21:16', '2025-11-06 14:21:30', '2025-11-06 14:21:31', 14, NULL),
(122, 'Finalizada', '2025-11-06 14:32:03', '2025-11-06 14:32:58', '2025-11-06 14:33:07', 14, NULL),
(123, 'Finalizada', '2025-11-06 16:18:45', '2025-11-06 16:19:08', '2025-11-06 16:19:09', 14, NULL),
(124, 'Aceptada', '2025-11-06 17:17:54', '2025-11-16 20:42:34', NULL, 14, NULL),
(125, 'Finalizada', '2025-11-06 17:27:18', '2025-11-06 17:27:33', '2025-11-06 17:27:36', 14, NULL),
(126, 'Finalizada', '2025-11-06 20:22:23', '2025-11-06 20:26:13', '2025-11-06 20:26:16', 14, NULL),
(127, 'Finalizada', '2025-11-06 20:33:26', '2025-11-06 20:34:01', '2025-11-06 20:34:03', 14, NULL),
(128, 'Finalizada', '2025-11-06 20:36:38', '2025-11-06 20:37:24', '2025-11-06 20:37:25', 14, NULL),
(129, 'Finalizada', '2025-11-08 10:25:17', '2025-11-08 10:43:36', '2025-11-08 10:43:38', 1, NULL),
(130, 'Finalizada', '2025-11-08 11:52:35', '2025-11-08 11:52:45', '2025-11-08 11:52:47', 1, NULL),
(131, 'Finalizada', '2025-11-08 12:15:57', '2025-11-08 12:16:02', '2025-11-08 12:16:03', 1, 3),
(133, 'Finalizada', '2025-11-08 14:40:25', '2025-11-08 14:40:51', '2025-11-08 14:40:53', 14, 2),
(134, 'Finalizada', '2025-11-08 15:01:09', '2025-11-08 15:01:17', '2025-11-08 15:01:19', 1, 2),
(135, 'Finalizada', '2025-11-08 15:03:21', '2025-11-08 15:03:35', '2025-11-08 15:03:36', 1, 2),
(136, 'Finalizada', '2025-11-08 17:13:16', '2025-11-08 17:13:23', '2025-11-08 17:13:25', 1, 2),
(137, 'Finalizada', '2025-11-10 20:28:29', '2025-11-10 20:28:51', '2025-11-10 20:28:55', 1, 2),
(138, 'Finalizada', '2025-11-10 20:30:15', '2025-11-10 20:30:24', '2025-11-10 20:30:27', 1, 2),
(139, 'Finalizada', '2025-11-10 20:33:06', '2025-11-10 20:33:17', '2025-11-10 20:33:21', 14, 2),
(140, 'Finalizada', '2025-11-10 20:37:14', '2025-11-10 20:37:31', '2025-11-10 20:37:36', 14, 3),
(141, 'Finalizada', '2025-11-12 18:39:16', '2025-11-12 18:39:35', '2025-11-12 18:40:22', 1, 2),
(142, 'Finalizada', '2025-11-16 20:15:27', '2025-11-16 20:35:07', '2025-11-16 20:43:56', 1, 3),
(143, 'Finalizada', '2025-11-16 20:43:20', '2025-11-16 20:43:38', '2025-11-16 20:43:55', 1, 3),
(144, 'Finalizada', '2025-11-16 20:46:50', '2025-11-16 20:47:12', '2025-11-16 22:46:22', 1, 17);

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
(99, 118, 43, 2),
(100, 119, 46, 2),
(101, 120, 45, 2),
(102, 121, 47, 2),
(103, 122, 47, 2),
(104, 123, 46, 2),
(105, 123, 47, 1),
(106, 124, 46, 1),
(107, 125, 48, 2),
(108, 126, 46, 2),
(109, 126, 47, 1),
(110, 127, 47, 2),
(111, 128, 47, 2),
(112, 129, 47, 1),
(113, 130, 47, 1),
(114, 131, 47, 1),
(116, 133, 47, 1),
(117, 134, 49, 6),
(118, 135, 49, 1),
(119, 136, 49, 1),
(120, 137, 50, 2),
(121, 138, 50, 1),
(122, 139, 50, 8),
(123, 140, 48, 2),
(124, 141, 52, 1),
(125, 142, 48, 3),
(126, 143, 46, 2),
(127, 144, 48, 6);

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
(43, 'Empanda de azucar', 'Una deliciosa empanda dulce', 1000.00, 'Activo'),
(45, 'carnesita', 'wena mi loco', 2000.00, 'Activo'),
(46, 'Azucar de', 'ojala', 1200.00, 'Activo'),
(47, 'Baguete', 'Chamo', 1000.00, 'Activo'),
(48, 'Empanada de Anis', 'deliciosa', 1000.00, 'Activo'),
(49, 'Empanda de Maiz', 'Prueba esta empanada', 1000.00, 'Inactivo'),
(50, 'Bechanis', 'Combinacion', 1500.00, 'Activo'),
(52, 'Cardamomo', 'wena', 2000.00, 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `receta_insumos`
--

CREATE TABLE `receta_insumos` (
  `id_rec_ins` int(11) NOT NULL,
  `id_rec` int(11) NOT NULL,
  `id_ins` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL,
  `unidad` varchar(20) NOT NULL,
  `estado` enum('Activo','Agotado') NOT NULL DEFAULT 'Activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `receta_insumos`
--

INSERT INTO `receta_insumos` (`id_rec_ins`, `id_rec`, `id_ins`, `cantidad`, `unidad`, `estado`) VALUES
(88, 43, 3, 2.00, 'kg', 'Activo'),
(89, 46, 3, 2.00, 'kg', 'Activo'),
(90, 45, 52, 2.00, 'g', 'Activo'),
(91, 47, 53, 2.00, 'g', 'Activo'),
(92, 48, 54, 2.00, 'g', 'Activo'),
(93, 49, 55, 2.00, 'g', 'Agotado'),
(94, 50, 47, 1.00, 'g', 'Activo'),
(95, 50, 54, 1.00, 'g', 'Activo'),
(96, 52, 46, 2.00, 'g', 'Activo');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usu`, `documento`, `nombres`, `apellidos`, `telefono`, `direccion`, `correo`, `rol`, `estado`, `password`) VALUES
(1, 1034280742, 'Marlon', 'Avila', 3214108646, 'Cll 6 #2-47', 'avilamarlon31@gmail.com', 'A', 'A', '81dc9bdb52d04dc20036dbd8313ed055'),
(2, 123456789, 'Ana', 'Pérez', 3001234567, 'Cra 10 #23-45', 'ana.perez@example.com', 'EP', 'A', '81dc9bdb52d04dc20036dbd8313ed055'),
(3, 987654321, 'Carlos', 'Ramírez', 3009876543, 'Av 5 #67-89', 'carlos.ramirez@example.com', 'EP', 'A', '$2a$10$mOUXQ3T7iwyJSLUZug7Xs.Iu/BjlxHn35BukcKo/guUWLFLmMPH5C'),
(4, 112233445, 'Luisa', 'Martínez', 3011122334, 'Cll 12 #34-56', 'luisa.martinez@example.com', 'EP', 'A', '$2a$10$JgJNP.G5pGDCbWSndvzV9.5nmfAg1NQoQo2wgulB7unbN8w/CEKcW'),
(5, 554433221, 'Andrés', 'Lopez', 3023344556, 'Cll 78 #90-12', 'andres.lopez@example.com', 'EV', 'A', '81dc9bdb52d04dc20036dbd8313ed055'),
(6, 665544332, 'María', 'Gómez', 3034455667, 'Cra 45 #12-34', 'maria.gomez@example.com', 'EV', 'A', '$2a$10$1Wncf/hqx2s7FMPcqIGIBuQQrIJ2WUqFXKQUuM5hva1gircNbWi0G'),
(14, 1103098783, 'Sebastián', 'Mercado', 0, 'Calle 18#123', 'sebassmercado97@gmail.com', 'A', 'A', '81dc9bdb52d04dc20036dbd8313ed055'),
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

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
(78, 'pedido', '2025-11-04 23:51:19', 1, 2, 10, 2000.00, 'Pago pendiente', 'wena'),
(79, 'pedido', '2025-11-05 12:21:16', 1, 2, 1, 2300.00, 'Pago pendiente', 'WDAD'),
(80, 'pedido', '2025-11-05 12:35:33', 1, 2, 12, 2300.00, 'Pago pendiente', 'wena'),
(81, 'pedido', '2025-11-05 13:02:05', 1, 2, 1, 1150.00, 'Pago pendiente', 'wena'),
(82, 'pedido', '2025-11-05 13:35:52', 1, 2, 13, 1300.00, 'Pago pendiente', 'wda'),
(83, 'pedido', '2025-11-05 13:42:48', 1, 2, 1, 1300.00, 'Pago pendiente', 'eyyy'),
(84, 'pedido', '2025-11-05 18:54:15', 1, 2, 14, 1300.00, 'Pago pendiente', 'wddwdw'),
(85, 'pedido', '2025-11-05 18:56:36', 14, 4, 15, 1300.00, 'Pago pendiente', 'Wena mi loco'),
(86, 'pedido', '2025-11-06 23:47:13', 1, 2, 1, 2400.00, 'Completada', 'wena'),
(87, 'pedido', '2025-11-06 23:50:37', 14, 3, 1, 4000.00, 'Completada', 'wena'),
(88, 'pedido', '2025-11-07 00:21:07', 14, 3, 2, 2000.00, 'Completada', 'chamo'),
(89, 'pedido', '2025-11-07 05:31:11', 1, 3, 1, 2000.00, 'Completada', 'quyew esrx'),
(90, 'pedido', '2025-11-07 07:17:50', 1, 2, 1, 3400.00, 'Completada', ''),
(91, 'pedido', '2025-11-07 03:17:42', 1, 2, 1, 1200.00, 'Pago completo', 'wena'),
(92, 'pedido', '2025-11-07 03:26:51', 1, 3, 4, 2000.00, 'Completada', 'una venta cualquiera'),
(93, 'pedido', '2025-11-07 11:21:45', 5, 2, 1, 3400.00, 'Completada', 'ahh'),
(94, 'pedido', '2025-11-07 06:33:16', 5, 2, 1, 2000.00, 'Completada', 'LOCO'),
(95, 'pedido', '2025-11-07 06:36:27', 5, 2, 1, 2000.00, 'Completada', 'rapido'),
(96, 'pedido', '2025-11-08 20:24:53', 1, 3, 2, 1000.00, 'Completada', 'esperemos'),
(97, 'pedido', '2025-11-08 21:52:25', 1, 2, 1, 1000.00, 'Completada', 'ooo'),
(98, 'pedido', '2025-11-08 22:15:48', 1, 3, 2, 1000.00, 'Completada', 'ñ'),
(99, 'pedido', '2025-11-08 23:34:14', 1, 2, 1, 1000.00, 'Pago completo', 'wena'),
(100, 'pedido', '2025-11-09 00:38:47', 14, 2, 10, 1000.00, 'Completada', 'wena'),
(101, 'pedido', '2025-11-09 01:00:55', 1, 2, 2, 6000.00, 'Completada', 'ojala'),
(102, 'pedido', '2025-11-09 01:03:01', 1, 2, 2, 1000.00, 'Completada', 'prueba 2'),
(103, 'pedido', '2025-11-09 03:13:04', 1, 2, 1, 1000.00, 'Completada', 'a'),
(104, 'pedido', '2025-11-11 06:28:05', 1, 2, 1, 3000.00, 'Completada', 'Combinacion'),
(105, 'pedido', '2025-11-11 06:30:05', 1, 2, 2, 1500.00, 'Completada', 'hola'),
(106, 'pedido', '2025-11-11 11:32:25', 14, 2, 2, 12000.00, 'Completada', 'wena'),
(107, 'pedido', '2025-11-11 06:37:02', 14, 3, 1, 2000.00, 'Completada', 'wena'),
(108, 'pedido', '2025-11-13 04:39:01', 1, 2, 1, 2000.00, 'Completada', 'wena'),
(109, 'pedido', '2025-11-18 02:15:06', 1, 3, 1, 3000.00, 'Completada', 'wena'),
(110, 'pedido', '2025-11-17 06:43:12', 1, 3, 1, 2400.00, 'Completada', '1'),
(111, 'pedido', '2025-11-17 06:46:40', 1, 17, 1, 6000.00, 'Completada', 'wena');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta_produccion`
--

CREATE TABLE `venta_produccion` (
  `id_ven_prod` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `id_produccion` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

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
(49, 76, 118, 0),
(50, 86, 119, 0),
(51, 87, 120, 0),
(52, 88, 121, 0),
(53, 89, 122, 0),
(54, 90, 123, 0),
(55, 91, 124, 0),
(56, 92, 125, 0),
(57, 93, 126, 0),
(58, 94, 127, 0),
(59, 95, 128, 0),
(60, 96, 129, 0),
(61, 97, 130, 0),
(62, 98, 131, 0),
(64, 100, 133, 0),
(65, 101, 134, 0),
(66, 102, 135, 0),
(67, 103, 136, 0),
(68, 104, 137, 0),
(69, 105, 138, 0),
(70, 106, 139, 0),
(71, 107, 140, 0),
(72, 108, 141, 0),
(73, 109, 142, 0),
(74, 110, 143, 0),
(75, 111, 144, 0);

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

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
(62, 78, 43, 2, 1000.00, 2000.00),
(63, 79, 43, 2, 1000.00, 2000.00),
(65, 80, 43, 2, 1000.00, 2000.00),
(67, 81, 43, 1, 1000.00, 1000.00),
(69, 82, 43, 1, 1000.00, 1000.00),
(71, 83, 43, 1, 1000.00, 1000.00),
(73, 84, 43, 1, 1000.00, 1000.00),
(75, 85, 43, 1, 1000.00, 1000.00),
(77, 86, 46, 2, 1200.00, 2400.00),
(78, 87, 45, 2, 2000.00, 4000.00),
(79, 88, 47, 2, 1000.00, 2000.00),
(80, 89, 47, 2, 1000.00, 2000.00),
(81, 90, 46, 2, 1200.00, 2400.00),
(82, 90, 47, 1, 1000.00, 1000.00),
(83, 91, 46, 1, 1200.00, 1200.00),
(84, 92, 48, 2, 1000.00, 2000.00),
(85, 93, 46, 2, 1200.00, 2400.00),
(86, 93, 47, 1, 1000.00, 1000.00),
(87, 94, 47, 2, 1000.00, 2000.00),
(88, 95, 47, 2, 1000.00, 2000.00),
(89, 96, 47, 1, 1000.00, 1000.00),
(90, 97, 47, 1, 1000.00, 1000.00),
(91, 98, 47, 1, 1000.00, 1000.00),
(92, 99, 47, 1, 1000.00, 1000.00),
(93, 100, 47, 1, 1000.00, 1000.00),
(94, 101, 49, 6, 1000.00, 6000.00),
(95, 102, 49, 1, 1000.00, 1000.00),
(96, 103, 49, 1, 1000.00, 1000.00),
(97, 104, 50, 2, 1500.00, 3000.00),
(98, 105, 50, 1, 1500.00, 1500.00),
(99, 106, 50, 8, 1500.00, 12000.00),
(100, 107, 48, 2, 1000.00, 2000.00),
(101, 108, 52, 1, 2000.00, 2000.00),
(102, 109, 48, 3, 1000.00, 3000.00),
(103, 110, 46, 2, 1200.00, 2400.00),
(104, 111, 48, 6, 1000.00, 6000.00);

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
-- Indices de la tabla `detalle_insumo`
--
ALTER TABLE `detalle_insumo`
  ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `id_ins` (`id_ins`);

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`idHist`),
  ADD KEY `fk_table1_insumos_idx` (`id_ins`),
  ADD KEY `fk_historial_detalle` (`id_detalle`);

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
  ADD KEY `fk_produccion_usuario` (`id_usu`);

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
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `detalle_insumo`
--
ALTER TABLE `detalle_insumo`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `idHist` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `id_pago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion', AUTO_INCREMENT=145;

--
-- AUTO_INCREMENT de la tabla `produccion_recetas`
--
ALTER TABLE `produccion_recetas`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=128;

--
-- AUTO_INCREMENT de la tabla `recetas`
--
ALTER TABLE `recetas`
  MODIFY `id_rec` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT de la tabla `receta_insumos`
--
ALTER TABLE `receta_insumos`
  MODIFY `id_rec_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Numero de identificacion (Cedula, tarjeta de identidad, etc)', AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta', AUTO_INCREMENT=112;

--
-- AUTO_INCREMENT de la tabla `venta_produccion`
--
ALTER TABLE `venta_produccion`
  MODIFY `id_ven_prod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT de la tabla `venta_recetas`
--
ALTER TABLE `venta_recetas`
  MODIFY `id_venta_receta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_insumo`
--
ALTER TABLE `detalle_insumo`
  ADD CONSTRAINT `detalle_insumo_ibfk_1` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`);

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `fk_historial_detalle` FOREIGN KEY (`id_detalle`) REFERENCES `detalle_insumo` (`id_detalle`),
  ADD CONSTRAINT `fk_table1_insumos` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `pago`
--
ALTER TABLE `pago`
  ADD CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD CONSTRAINT `fk_produccion_usuario` FOREIGN KEY (`id_usu`) REFERENCES `usuarios` (`id_usu`) ON UPDATE CASCADE;

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

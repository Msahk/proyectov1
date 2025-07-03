-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-07-2025 a las 23:02:35
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
-- Base de datos: `elvecino`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `docCliente` varchar(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellidos` varchar(45) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `categoriaCrediticia` enum('A','B','C') NOT NULL,
  `fechaRegistro` datetime NOT NULL,
  `limite_creditos` int(11) NOT NULL,
  `creditos_actuales` int(11) NOT NULL,
  `docUsuario` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`docCliente`, `nombre`, `apellidos`, `telefono`, `direccion`, `categoriaCrediticia`, `fechaRegistro`, `limite_creditos`, `creditos_actuales`, `docUsuario`) VALUES
('2001', 'Luis', 'Ramírez', '3001234567', 'Cra 10 #20-30', 'A', '2024-01-15 00:00:00', 3, 1, '1001'),
('2002', 'María', 'Pérez', '3109876543', 'Calle 45 #12-34', 'B', '2024-03-10 00:00:00', 2, 0, '1001'),
('2003', 'Carlos', 'Montoya', '3123456789', 'Calle 10 #15-20', 'B', '2024-04-05 00:00:00', 2, 1, '1003'),
('2004', 'Ana', 'Jiménez', '3112345678', 'Carrera 8 #45-67', 'C', '2024-04-10 00:00:00', 1, 0, '1004'),
('2005', 'Ricardo', 'Vargas', '3134567890', 'Av. 6 #34-56', 'A', '2024-04-12 00:00:00', 3, 2, '1005');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credito`
--

CREATE TABLE `credito` (
  `idCredito` varchar(11) NOT NULL,
  `fec_cred` date NOT NULL,
  `fec_venc` date NOT NULL,
  `monto_total` decimal(12,2) NOT NULL,
  `estado` enum('A','P','V') NOT NULL,
  `docCliente` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credito`
--

INSERT INTO `credito` (`idCredito`, `fec_cred`, `fec_venc`, `monto_total`, `estado`, `docCliente`) VALUES
('CRED001', '2025-05-01', '2025-11-01', 1200000.00, 'A', '2001'),
('CRED002', '2025-05-10', '2025-10-10', 2500000.00, 'P', '2001'),
('CRED003', '2024-04-15', '2024-10-15', 600000.00, 'A', '2003'),
('CRED004', '2024-04-20', '2024-11-20', 750000.00, 'P', '2004'),
('CRED005', '2024-04-25', '2024-09-25', 400000.00, 'V', '2005');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_credito`
--

CREATE TABLE `detalle_credito` (
  `idCredito` varchar(11) NOT NULL,
  `idProducto` varchar(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `sub_total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle_credito`
--

INSERT INTO `detalle_credito` (`idCredito`, `idProducto`, `cantidad`, `sub_total`) VALUES
('CRED001', 'P001', 1, 1200000.00),
('CRED002', 'P002', 1, 2500000.00),
('CRED003', 'P003', 1, 600000.00),
('CRED004', 'P004', 1, 750000.00),
('CRED005', 'P005', 2, 400000.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

CREATE TABLE `pagos` (
  `idPagos` varchar(11) NOT NULL,
  `numero_cuota` int(11) NOT NULL,
  `monto_pagado` decimal(10,2) NOT NULL,
  `fecha_pago` date NOT NULL,
  `tipo_pago` varchar(45) NOT NULL,
  `valor_pagado` decimal(10,2) NOT NULL,
  `observaciones` text NOT NULL,
  `idCredito` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pagos`
--

INSERT INTO `pagos` (`idPagos`, `numero_cuota`, `monto_pagado`, `fecha_pago`, `tipo_pago`, `valor_pagado`, `observaciones`, `idCredito`) VALUES
('PAG004', 1, 300000.00, '2024-04-18', 'Transferencia', 300000.00, 'Primer abono', 'CRED003'),
('PAG005', 1, 500000.00, '2024-04-23', 'Efectivo', 500000.00, 'Abono parcial', 'CRED004'),
('PAG006', 1, 400000.00, '2024-04-26', 'Tarjeta', 400000.00, 'Pago completo', 'CRED005'),
('PG001', 1, 400000.00, '2025-06-01', 'Transferencia', 400000.00, 'Primer pago puntual', 'CRED001'),
('PG002', 1, 1000000.00, '2025-06-05', 'Efectivo', 1000000.00, 'Pago parcial', 'CRED002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `idProducto` varchar(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `precio_Unitario` decimal(12,2) NOT NULL,
  `unidad_medida` varchar(20) NOT NULL,
  `stock_actual` int(11) NOT NULL,
  `pago` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`idProducto`, `nombre`, `descripcion`, `precio_Unitario`, `unidad_medida`, `stock_actual`, `pago`) VALUES
('P001', 'Celular Samsung', 'Samsung Galaxy A32', 1200000.00, 'unidad', 10, 0.00),
('P002', 'Laptop HP', 'HP 14 pulgadas, 8GB RAM', 2500000.00, 'unidad', 5, 0.00),
('P003', 'Monitor Samsung 24\"', 'Full HD, HDMI/VGA', 600000.00, 'unidad', 10, 0.00),
('P004', 'Impresora Epson', 'Multifuncional, WiFi', 750000.00, 'unidad', 5, 0.00),
('P005', 'Memoria RAM 8GB', 'DDR4, 2666MHz', 200000.00, 'unidad', 20, 0.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `docUsuario` varchar(11) NOT NULL,
  `nombreUsuario` varchar(45) NOT NULL,
  `rol` enum('A','E') NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`docUsuario`, `nombreUsuario`, `rol`, `password`, `email`) VALUES
('1001', 'Juana Mendez', 'E', 'clave123', 'juana@email.com'),
('1002', 'Carlos Rojas', 'A', 'admin456', 'carlos@email.com'),
('1003', 'Maria Fernandez', 'E', 'clave321', 'maria.fernandez@gmail.com'),
('1004', 'Juan Lopez', 'A', 'admin123', 'juan.lopez@empresa.com'),
('1005', 'Diana Murillo', 'E', 'pass456', 'diana.murillo@gmail.com');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`docCliente`),
  ADD KEY `fk_cliente_usuario1_idx` (`docUsuario`);

--
-- Indices de la tabla `credito`
--
ALTER TABLE `credito`
  ADD PRIMARY KEY (`idCredito`),
  ADD KEY `fk_credito_cliente1_idx` (`docCliente`);

--
-- Indices de la tabla `detalle_credito`
--
ALTER TABLE `detalle_credito`
  ADD PRIMARY KEY (`idCredito`,`idProducto`),
  ADD KEY `fk_Credito_has_Producto_Producto1` (`idProducto`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD PRIMARY KEY (`idPagos`),
  ADD KEY `fk_pagos_credito1_idx` (`idCredito`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`idProducto`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`docUsuario`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_cliente_usuario1` FOREIGN KEY (`docUsuario`) REFERENCES `usuario` (`docUsuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `credito`
--
ALTER TABLE `credito`
  ADD CONSTRAINT `fk_credito_cliente1` FOREIGN KEY (`docCliente`) REFERENCES `cliente` (`docCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `detalle_credito`
--
ALTER TABLE `detalle_credito`
  ADD CONSTRAINT `detalle_credito_ibfk_1` FOREIGN KEY (`idCredito`) REFERENCES `credito` (`idCredito`),
  ADD CONSTRAINT `fk_Credito_has_Producto_Producto1` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`);

--
-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
  ADD CONSTRAINT `fk_pagos_credito1` FOREIGN KEY (`idCredito`) REFERENCES `credito` (`idCredito`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

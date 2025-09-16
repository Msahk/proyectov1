-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-07-2025 a las 04:31:52
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
(5, 'Pedro Paz', '3556789012', 'pedro.paz@gmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_produccion`
--

CREATE TABLE `detalle_produccion` (
  `id_detpro` int(11) NOT NULL,
  `id_proc` int(11) NOT NULL,
  `id_sal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `detalle_produccion`
--

INSERT INTO `detalle_produccion` (`id_detpro`, `id_proc`, `id_sal`) VALUES
(96, 43, 96),
(97, 43, 97),
(98, 43, 98),
(99, 44, 99),
(100, 44, 100),
(101, 44, 101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_venta`
--

CREATE TABLE `detalle_venta` (
  `id_detalle` int(11) NOT NULL,
  `id_ven` int(11) NOT NULL,
  `id_prot` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL
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
  `stock_min` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `insumos`
--

INSERT INTO `insumos` (`id_ins`, `nombre`, `cantidad`, `unidad_medida`, `stock_min`) VALUES
(1, 'Carne molida', 35, 'kg', 20),
(2, 'Masa para empanada', 100, 'kg', 40),
(3, 'Pollo', 25, 'kg', 10),
(4, 'Queso', 40, 'kg', 15),
(5, 'Sal', 25, 'kg', 10),
(6, 'Azúcar', 20, 'kg', 10),
(7, 'Levadura', 20, 'kg', 5),
(8, 'Harina', 1000, 'gramos', 100),
(9, 'Levadura', 100, 'gramos', 20),
(10, 'Carne molida', 5000, 'gramos', 500),
(11, 'Cebolla', 1000, 'kg', 100);

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
  `cantidad` decimal(10,0) NOT NULL,
  `motivo` varchar(100) NOT NULL DEFAULT 'Producción',
  `id_ins` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `inv_salidas`
--

INSERT INTO `inv_salidas` (`id_sal`, `fecha`, `cantidad`, `motivo`, `id_ins`) VALUES
(14, '2025-07-02', 0, 'Producción', 1),
(15, '2025-07-02', 0, 'Producción', 6),
(16, '2025-07-02', 0, 'Producción', 1),
(17, '2025-07-02', 0, 'Producción', 2),
(18, '2025-07-02', 0, 'Producción', 3),
(25, '2025-07-02', 0, 'Producción', 1),
(26, '2025-07-02', 0, 'Producción', 2),
(27, '2025-07-02', 0, 'Producción', 3),
(28, '2025-07-02', 0, 'Producción', 1),
(29, '2025-07-02', 0, 'Producción', 2),
(30, '2025-07-02', 0, 'Producción', 3),
(31, '2025-07-02', 0, 'Producción', 1),
(32, '2025-07-02', 0, 'Producción', 4),
(33, '2025-07-02', 0, 'Producción', 1),
(34, '2025-07-02', 0, 'Producción', 2),
(35, '2025-07-02', 0, 'Producción', 3),
(36, '2025-07-02', 0, 'Producción', 1),
(37, '2025-07-02', 0, 'Producción', 2),
(38, '2025-07-02', 0, 'Producción', 3),
(39, '2025-07-02', 0, 'Producción', 1),
(40, '2025-07-02', 0, 'Producción', 2),
(41, '2025-07-02', 0, 'Producción', 3),
(42, '2025-07-02', 0, 'Producción', 1),
(43, '2025-07-02', 0, 'Producción', 2),
(44, '2025-07-02', 0, 'Producción', 3),
(45, '2025-07-02', 0, 'Producción', 1),
(46, '2025-07-02', 0, 'Producción', 2),
(47, '2025-07-02', 0, 'Producción', 3),
(48, '2025-07-02', 0, 'Producción', 1),
(49, '2025-07-02', 0, 'Producción', 2),
(50, '2025-07-02', 0, 'Producción', 3),
(51, '2025-07-02', 0, 'Producción', 1),
(52, '2025-07-02', 0, 'Producción', 2),
(53, '2025-07-02', 0, 'Producción', 3),
(54, '2025-07-02', 0, 'Producción', 1),
(55, '2025-07-02', 0, 'Producción', 2),
(56, '2025-07-02', 0, 'Producción', 3),
(57, '2025-07-02', 0, 'Producción', 1),
(58, '2025-07-02', 0, 'Producción', 2),
(59, '2025-07-02', 0, 'Producción', 3),
(60, '2025-07-02', 0, 'Producción', 1),
(61, '2025-07-02', 0, 'Producción', 2),
(62, '2025-07-02', 0, 'Producción', 3),
(63, '2025-07-02', 0, 'Producción', 1),
(64, '2025-07-02', 0, 'Producción', 2),
(65, '2025-07-02', 0, 'Producción', 3),
(66, '2025-07-02', 1, 'Producción', 1),
(67, '2025-07-02', 0, 'Producción', 2),
(68, '2025-07-02', 1, 'Producción', 3),
(69, '2025-07-02', 1, 'Producción', 1),
(70, '2025-07-02', 0, 'Producción', 2),
(71, '2025-07-02', 0, 'Producción', 3),
(72, '2025-07-02', 1, 'Producción', 1),
(73, '2025-07-02', 0, 'Producción', 2),
(74, '2025-07-02', 1, 'Producción', 3),
(75, '2025-07-02', 1, 'Producción', 1),
(76, '2025-07-02', 0, 'Producción', 2),
(77, '2025-07-02', 0, 'Producción', 3),
(78, '2025-07-02', 1, 'Producción', 1),
(79, '2025-07-02', 0, 'Producción', 2),
(80, '2025-07-02', 0, 'Producción', 3),
(81, '2025-07-02', 1, 'Producción', 1),
(82, '2025-07-02', 0, 'Producción', 2),
(83, '2025-07-02', 0, 'Producción', 3),
(84, '2025-07-02', 1, 'Producción', 1),
(85, '2025-07-02', 0, 'Producción', 2),
(86, '2025-07-02', 0, 'Producción', 3),
(87, '2025-07-02', 1, 'Producción', 1),
(88, '2025-07-02', 0, 'Producción', 2),
(89, '2025-07-02', 1, 'Producción', 3),
(90, '2025-07-02', 1, 'Producción', 1),
(91, '2025-07-02', 0, 'Producción', 2),
(92, '2025-07-02', 1, 'Producción', 3),
(93, '2025-07-02', 1, 'Producción', 1),
(94, '2025-07-02', 0, 'Producción', 2),
(95, '2025-07-02', 0, 'Producción', 3),
(96, '2025-07-02', 1, 'Producción', 1),
(97, '2025-07-02', 0, 'Producción', 2),
(98, '2025-07-02', 1, 'Producción', 3),
(99, '2025-07-02', 2, 'Producción', 1),
(100, '2025-07-02', 0, 'Producción', 2),
(101, '2025-07-02', 1, 'Producción', 3),
(102, '2025-07-02', 3, 'Producción', 1),
(103, '2025-07-02', 0, 'Producción', 2),
(104, '2025-07-02', 1, 'Producción', 3);

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
  `id_prot` int(11) DEFAULT NULL,
  `id_res` int(11) DEFAULT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id_proc`, `fecha_produccion`, `total_emp`, `tipo`, `cantidad`, `id_prot`, `id_res`, `estado`) VALUES
(30, '2025-07-02', 100, 'Carne', 100, NULL, NULL, 'pendiente'),
(43, '2005-12-17', 12, 'Carne', 12, NULL, NULL, 'pendiente'),
(44, '2005-12-17', 15, 'Carne', 15, NULL, NULL, 'produciendo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id_prot` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` mediumtext DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_prot`, `nombre`, `descripcion`, `precio`) VALUES
(1, 'Pan integral', 'Pan saludable de trigo integral', 3000.00),
(2, 'Pan de queso', 'Pan con relleno de queso', 2500.00),
(3, 'Galletas avena', 'Galletas caseras de avena y miel', 2000.00),
(4, 'Croissant', 'Croissant relleno de chocolate', 3500.00),
(5, 'Pan francés', 'Pan crocante tipo francés', 2800.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usu` int(11) NOT NULL,
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
(7, 1034280742, 'Marlon', 'Avila', 3214108646, 'Cll 6 #2-47', 'avilamarlon31@gmail.com', 'A', 'A', '1234'),
(8, 123456789, 'Ana', 'Pérez', 3001234567, 'Cra 10 #23-45', 'ana.perez@example.com', 'EP', 'A', 'pass123'),
(9, 987654321, 'Carlos', 'Ramírez', 3009876543, 'Av 5 #67-89', 'carlos.ramirez@example.com', 'EP', 'A', 'pass456'),
(10, 112233445, 'Luisa', 'Martínez', 3011122334, 'Cll 12 #34-56', 'luisa.martinez@example.com', 'EP', 'A', 'pass789'),
(11, 554433221, 'Andrés', 'Lopez', 3023344556, 'Cll 78 #90-12', 'andres.lopez@example.com', 'EV', 'A', 'pass321'),
(12, 665544332, 'María', 'Gómez', 3034455667, 'Cra 45 #12-34', 'maria.gomez@example.com', 'EV', 'A', 'pass654');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_ven` int(11) NOT NULL COMMENT 'Identificacion de la venta',
  `Tipo` enum('directa','pedido') NOT NULL COMMENT 'Fecha en la que se realizo la venta',
  `fecha` datetime NOT NULL COMMENT 'Valor total de la venta en esa fecha',
  `id_usu` int(11) NOT NULL,
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
  ADD KEY `fk_Detalle_venta_Productos1_idx` (`id_prot`),
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
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id_prot`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre`);

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
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  MODIFY `id_detpro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT de la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `insumos`
--
ALTER TABLE `insumos`
  MODIFY `id_ins` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `inv_entradas`
--
ALTER TABLE `inv_entradas`
  MODIFY `id_ent` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `inv_salidas`
--
ALTER TABLE `inv_salidas`
  MODIFY `id_sal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id_ped` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id_proc` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificador de la prduccion', AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id_prot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id_ven` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Identificacion de la venta', AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_produccion`
--
ALTER TABLE `detalle_produccion`
  ADD CONSTRAINT `fk_detalle_produccion_inv_salidas1` FOREIGN KEY (`id_sal`) REFERENCES `inv_salidas` (`id_sal`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_detalle_produccion_produccion1` FOREIGN KEY (`id_proc`) REFERENCES `produccion` (`id_proc`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `detalle_venta`
--
ALTER TABLE `detalle_venta`
  ADD CONSTRAINT `fk_Detalle_venta_Productos1` FOREIGN KEY (`id_prot`) REFERENCES `productos` (`id_prot`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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
  ADD CONSTRAINT `fk_inv_salidas_insumos` FOREIGN KEY (`id_ins`) REFERENCES `insumos` (`id_ins`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `fk_pedidos_Ventas2` FOREIGN KEY (`id_ven`) REFERENCES `ventas` (`id_ven`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD CONSTRAINT `fk_produccion_Productos1` FOREIGN KEY (`id_prot`) REFERENCES `productos` (`id_prot`) ON DELETE NO ACTION ON UPDATE NO ACTION,
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

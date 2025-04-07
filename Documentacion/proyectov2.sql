-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: proyectopaev2
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `empanadas`
--

DROP TABLE IF EXISTS `empanadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empanadas` (
  `idEmpanada` int(11) NOT NULL,
  `empanadaValorUVenta` int(11) NOT NULL,
  `empanadaValorUProd` int(11) NOT NULL,
  `fkTipoEmpanada` int(11) NOT NULL,
  PRIMARY KEY (`idEmpanada`),
  KEY `fkTipoEmpanada` (`fkTipoEmpanada`),
  CONSTRAINT `empanadas_ibfk_1` FOREIGN KEY (`fkTipoEmpanada`) REFERENCES `tipo_empanada` (`idTipoEmpanada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empanadas`
--

LOCK TABLES `empanadas` WRITE;
/*!40000 ALTER TABLE `empanadas` DISABLE KEYS */;
INSERT INTO `empanadas` VALUES (1,2000,1000,1),(2,2200,1200,2),(3,2500,1500,3),(4,2400,1400,4),(5,1500,1000,1),(6,1500,1000,2),(7,1500,1000,3),(8,1500,1000,4),(9,1500,1000,1),(10,1500,1000,2),(11,1500,1000,1),(12,1500,1000,2),(13,1500,1000,3),(14,1500,1000,4);
/*!40000 ALTER TABLE `empanadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `idEmpleado` int(11) NOT NULL,
  `tipoEmpleado` varchar(45) NOT NULL,
  `fkUsuario` int(11) NOT NULL,
  PRIMARY KEY (`idEmpleado`),
  KEY `fkUsuario` (`fkUsuario`),
  CONSTRAINT `empleados_ibfk_1` FOREIGN KEY (`fkUsuario`) REFERENCES `usuarios` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES (1,'Empleado Ventas',1),(2,'Empleado Producción',2),(3,'Empleado Ventas',3),(4,'Empleado Producción',4),(5,'Empleado Producción',5),(6,'Empleado Ventas',6),(7,'Empleado Producción',7),(8,'Empleado Ventas',8),(9,'Empleado Producción',9),(10,'Empleado Ventas',10),(11,'Empleado Producción',11),(12,'Empleado Ventas',12),(13,'Empleado Producción',13),(14,'Empleado Ventas',14),(15,'Empleado Producción',15),(16,'Empleado Ventas',16),(17,'Empleado Producción',17),(18,'Empleado Ventas',18);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produccion`
--

DROP TABLE IF EXISTS `produccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produccion` (
  `idProduccion` int(11) NOT NULL,
  `cantidadProduccion` int(11) NOT NULL,
  `fkEmpleado` int(11) NOT NULL,
  PRIMARY KEY (`idProduccion`),
  KEY `fkEmpleado` (`fkEmpleado`),
  CONSTRAINT `produccion_ibfk_1` FOREIGN KEY (`fkEmpleado`) REFERENCES `empleados` (`idEmpleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produccion`
--

LOCK TABLES `produccion` WRITE;
/*!40000 ALTER TABLE `produccion` DISABLE KEYS */;
INSERT INTO `produccion` VALUES (1,100,2),(2,150,4),(3,200,2),(4,120,4);
/*!40000 ALTER TABLE `produccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produccion_empanada`
--

DROP TABLE IF EXISTS `produccion_empanada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produccion_empanada` (
  `fkEmpanada` int(11) NOT NULL,
  `fkProduccion` int(11) NOT NULL,
  `fechaProd` date NOT NULL,
  `fechaVencimiento` date NOT NULL,
  PRIMARY KEY (`fkEmpanada`,`fkProduccion`),
  KEY `fkProduccion` (`fkProduccion`),
  CONSTRAINT `produccion_empanada_ibfk_1` FOREIGN KEY (`fkEmpanada`) REFERENCES `empanadas` (`idEmpanada`),
  CONSTRAINT `produccion_empanada_ibfk_2` FOREIGN KEY (`fkProduccion`) REFERENCES `produccion` (`idProduccion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produccion_empanada`
--

LOCK TABLES `produccion_empanada` WRITE;
/*!40000 ALTER TABLE `produccion_empanada` DISABLE KEYS */;
INSERT INTO `produccion_empanada` VALUES (1,1,'2024-11-01','2024-11-15'),(2,2,'2024-11-02','2024-11-16'),(3,3,'2024-11-03','2024-11-17'),(4,4,'2024-11-04','2024-11-18');
/*!40000 ALTER TABLE `produccion_empanada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_empanada`
--

DROP TABLE IF EXISTS `tipo_empanada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_empanada` (
  `idTipoEmpanada` int(11) NOT NULL,
  `tipoEmpanada` varchar(20) NOT NULL,
  PRIMARY KEY (`idTipoEmpanada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_empanada`
--

LOCK TABLES `tipo_empanada` WRITE;
/*!40000 ALTER TABLE `tipo_empanada` DISABLE KEYS */;
INSERT INTO `tipo_empanada` VALUES (1,'Carne'),(2,'Pollo'),(3,'Queso'),(4,'Vegetariana');
/*!40000 ALTER TABLE `tipo_empanada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_usuario`
--

DROP TABLE IF EXISTS `tipo_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_usuario` (
  `idTipoUsuario` int(11) NOT NULL,
  `tipoUsuario` varchar(45) NOT NULL,
  PRIMARY KEY (`idTipoUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_usuario`
--

LOCK TABLES `tipo_usuario` WRITE;
/*!40000 ALTER TABLE `tipo_usuario` DISABLE KEYS */;
INSERT INTO `tipo_usuario` VALUES (1,'Administrador'),(2,'Empleado');
/*!40000 ALTER TABLE `tipo_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `nombreUsuarios` varchar(45) NOT NULL,
  `apellidoUsuario` varchar(45) NOT NULL,
  `estadoUsuario` varchar(8) NOT NULL,
  `password` varchar(50) NOT NULL,
  `emailUsuario` varchar(100) NOT NULL,
  `fkTipoUsuario` int(11) NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fkTipoUsuario` (`fkTipoUsuario`),
  CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`fkTipoUsuario`) REFERENCES `tipo_usuario` (`idTipoUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Marlon','Avila','Activo','12345','avilamarlon31@gmail.com',1),(2,'María','Gómez','Activo','23456','maria.gomez@mail.com',2),(3,'Carlos','Ramírez','Inactivo','34567','carlos.ramirez@mail.com',2),(4,'Ana','López','Activo','45678','ana.lopez@mail.com',1),(5,'María','Gómez','Activo','password2','maria.gomez@example.com',2),(6,'Carlos','López','Activo','password3','carlos.lopez@example.com',2),(7,'Ana','Martínez','Activo','password4','ana.martinez@example.com',2),(8,'Luis','Ramírez','Inactivo','password5','luis.ramirez@example.com',2),(9,'Diana','Torres','Activo','password6','diana.torres@example.com',2),(10,'Pedro','García','Activo','password7','pedro.garcia@example.com',2),(11,'Laura','Ortiz','Inactivo','password8','laura.ortiz@example.com',2),(12,'Jorge','Vargas','Activo','password9','jorge.vargas@example.com',2),(13,'Carolina','Suárez','Activo','password10','carolina.suarez@example.com',2),(14,'Andrés','Medina','Activo','password11','andres.medina@example.com',2),(15,'Camila','Ruiz','Inactivo','password12','camila.ruiz@example.com',2),(16,'Sofía','Morales','Activo','password13','sofia.morales@example.com',2),(17,'Mateo','Cruz','Activo','password14','mateo.cruz@example.com',2),(18,'Isabella','Herrera','Inactivo','password15','isabella.herrera@example.com',2);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas` (
  `idVentas` int(11) NOT NULL,
  `fechaVenta` date NOT NULL,
  `observacionVenta` varchar(45) DEFAULT NULL,
  `estadoVenta` varchar(15) DEFAULT NULL,
  `fkEmpleado` int(11) NOT NULL,
  PRIMARY KEY (`idVentas`),
  KEY `fkEmpleado` (`fkEmpleado`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`fkEmpleado`) REFERENCES `empleados` (`idEmpleado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,'2024-11-01','Venta rápida','Completada',1),(2,'2024-11-02','Venta directa','Pendiente',3),(3,'2024-11-03','Venta cancelada','Cancelada',2),(4,'2024-11-04','Venta mayorista','Pendiente',4),(5,'2024-11-05','Sin observaciones','Completada',5),(6,'2024-11-06','Sin observaciones','Completada',1),(7,'2024-11-07','Sin observaciones','Completada',2),(8,'2024-11-08','Sin observaciones','Completada',3),(9,'2024-11-09','Sin observaciones','Completada',4),(10,'2024-11-10','Sin observaciones','Completada',5),(11,'2024-11-01','Sin observaciones','Completada',1),(12,'2024-11-02','Sin observaciones','Completada',2),(13,'2024-11-03','Sin observaciones','Completada',3),(14,'2024-11-04','Sin observaciones','Completada',4);
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ventas_has_empanada`
--

DROP TABLE IF EXISTS `ventas_has_empanada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ventas_has_empanada` (
  `fkVentas` int(11) NOT NULL,
  `fkEmpanada` int(11) NOT NULL,
  `totalBruto` int(11) NOT NULL,
  `totalNeto` int(11) NOT NULL,
  PRIMARY KEY (`fkVentas`,`fkEmpanada`),
  KEY `fkEmpanada` (`fkEmpanada`),
  CONSTRAINT `ventas_has_empanada_ibfk_1` FOREIGN KEY (`fkVentas`) REFERENCES `ventas` (`idVentas`),
  CONSTRAINT `ventas_has_empanada_ibfk_2` FOREIGN KEY (`fkEmpanada`) REFERENCES `empanadas` (`idEmpanada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas_has_empanada`
--

LOCK TABLES `ventas_has_empanada` WRITE;
/*!40000 ALTER TABLE `ventas_has_empanada` DISABLE KEYS */;
INSERT INTO `ventas_has_empanada` VALUES (1,1,20000,18000),(2,2,22000,20000),(3,3,25000,23000),(4,4,24000,22000),(5,5,9000,3000),(6,6,6000,2000),(7,7,7500,2500),(8,8,4500,1500),(9,9,3000,1000),(10,10,15000,5000),(11,11,4500,1500),(12,12,3000,1000),(13,13,7500,2500),(14,14,6000,2000);
/*!40000 ALTER TABLE `ventas_has_empanada` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-10  9:01:36

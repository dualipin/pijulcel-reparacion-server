-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.30 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para pijulcel_app
CREATE DATABASE IF NOT EXISTS `pijulcel_app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pijulcel_app`;

-- Volcando estructura para tabla pijulcel_app.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pijulcel_app.clientes: ~2 rows (aproximadamente)
DELETE FROM `clientes`;
INSERT INTO `clientes` (`id`, `nombre`, `telefono`) VALUES
	(15, 'sdfsdf', '34545'),
	(16, 'dfgdfgg', '34534545'),
	(18, 'Samuel Burelos Jeronimo', '9361165168');

-- Volcando estructura para tabla pijulcel_app.dispositivos
CREATE TABLE IF NOT EXISTS `dispositivos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pijulcel_app.dispositivos: ~2 rows (aproximadamente)
DELETE FROM `dispositivos`;
INSERT INTO `dispositivos` (`id`, `nombre`) VALUES
	(15, 'dfgdfg'),
	(16, 'fdghfghghf'),
	(18, 'Motorola G9');

-- Volcando estructura para tabla pijulcel_app.pedidos
CREATE TABLE IF NOT EXISTS `pedidos` (
  `bar_code` int NOT NULL,
  `cliente_id` int NOT NULL,
  `disp_id` int NOT NULL,
  `descrip` varchar(255) DEFAULT NULL,
  `audio` varchar(255) DEFAULT NULL,
  `img_1` varchar(255) DEFAULT NULL,
  `img_2` varchar(255) DEFAULT NULL,
  `estatus` enum('Pendiente','En proceso','Listo','Entregado') NOT NULL DEFAULT 'Pendiente',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`bar_code`) USING BTREE,
  KEY `FK_pedidos_clientes` (`cliente_id`),
  KEY `FK_pedidos_dispositivos` (`disp_id`),
  CONSTRAINT `FK_pedidos_clientes` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_pedidos_dispositivos` FOREIGN KEY (`disp_id`) REFERENCES `dispositivos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pijulcel_app.pedidos: ~3 rows (aproximadamente)
DELETE FROM `pedidos`;
INSERT INTO `pedidos` (`bar_code`, `cliente_id`, `disp_id`, `descrip`, `audio`, `img_1`, `img_2`, `estatus`, `created`, `updated`) VALUES
	(1763789080, 15, 15, 'dfgdfgfg', 'audios/06951b08-50a6-4383-b5bc-3f27bcf97ce6.webm', 'pedidos/2c70d8e2-49a7-4937-bf9b-f2f68820eec2.jpg', NULL, 'En proceso', '2025-11-22 05:26:28', '2025-11-22 06:40:35'),
	(1763789188, 16, 16, NULL, 'audios/a0ae8b09-7ef3-40d1-8a62-ddb8af7452b4.webm', 'pedidos/8e633535-2720-4211-afc5-ff2cc13f6d12.jpg', 'pedidos/a64b1faa-8f71-4236-a404-b730c197ade9.jpg', 'Listo', '2025-11-22 05:27:44', '2025-11-22 06:42:46'),
	(1763792059, 18, 18, 'hdfghdfghdghdf', 'audios/ab2ff231-1a14-4cca-af1d-cf0cb591b243.jpg', 'pedidos/431209d4-dacf-49ef-bf68-8b33358f7775.jpg', 'pedidos/472d5803-7410-4221-9d9c-5ad0fc132cba.jpg', 'Listo', '2025-11-22 06:17:11', '2025-11-22 06:39:00');

-- Volcando estructura para tabla pijulcel_app.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Volcando datos para la tabla pijulcel_app.usuarios: ~1 rows (aproximadamente)
DELETE FROM `usuarios`;
INSERT INTO `usuarios` (`id`, `nombre`, `username`, `password`) VALUES
	(1, 'Pijulcel', 'pijulcel', 'pijulcel');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;

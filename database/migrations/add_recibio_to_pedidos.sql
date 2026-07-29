-- --------------------------------------------------------
-- Database migration for adding 'recibio' (quien recibio) field to 'pedidos' table
-- --------------------------------------------------------

USE `pijulcel_app`;

ALTER TABLE `pedidos`
ADD COLUMN `recibio` VARCHAR(100) NULL DEFAULT NULL AFTER `descrip`;

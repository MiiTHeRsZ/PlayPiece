CREATE DATABASE  IF NOT EXISTS `playpiece` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `playpiece`;
-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: 127.0.0.1    Database: playpiece
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_pessoa` int(11) NOT NULL,
  `id_cargo` int(11) NOT NULL,
  `salario` double NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  `email_usuario` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_pessoa_2` (`id_pessoa`),
  UNIQUE KEY `email_usuario` (`email_usuario`),
  UNIQUE KEY `email_usuario_2` (`email_usuario`),
  KEY `id_pessoa` (`id_pessoa`),
  KEY `id_cargo` (`id_cargo`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `usuario_ibfk_2` FOREIGN KEY (`id_cargo`) REFERENCES `cargo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,1,1,1500.56,1,'vini@playpiece.com'),(2,2,2,15022,1,'user2@playpiece.com'),(3,9,1,15,1,'LucasLira@playpiece.com'),(4,5,1,15,1,'LucasSantos@playpiece.com'),(5,12,1,2344,1,'LeoLeo@playpiece.com'),(6,13,2,12000,1,'email_profissional'),(8,14,1,123123,1,'alevi.7@playpiece.com'),(9,16,1,123123,1,'amaia.9@playpiece.com'),(10,17,2,12312,1,'lpeixoto.10@playpiece.com'),(11,18,1,12312,1,'ldantas.11@playpiece.com'),(12,3,1,1500,1,'amenezes.pp12@playpiece.com'),(13,8,1,1590,1,'lsacoman.pp13@playpiece.com'),(14,19,2,1593,1,'ldeltoro.pp14@playpiece.com'),(15,20,1,1,1,'apaiva.pp15@playpiece.com'),(16,21,1,43,1,'lviella.pp16@playpiece.com');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-08-30  6:32:54

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
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `cpf` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `cep` bigint(20) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `id_contato` int(11) NOT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL,
  `ativo` tinyint(1) NOT NULL,
  `contato_id_contato` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `UK_e3gyt2y8r4yd3wnel3je03fok` (`contato_id_contato`),
  UNIQUE KEY `email` (`email`),
  KEY `id_contato` (`id_contato`),
  CONSTRAINT `pessoa_ibfk_1` FOREIGN KEY (`id_contato`) REFERENCES `contato` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'Vinicius Heitore',12312312301,'vinicius_mendesuser1@pisbrasil.com.br',12312301,'Rua João Barbi, 414',1,'foto bini',1,NULL),(2,'Vini Heitors',12312312302,'vinicius@pisbrasil.com.br',12312302,'Rua João Barbi, 424',2,'leo',1,NULL),(3,'Artur Menezes',12312312303,'arturMenezes@pisbrasil.com.br',12312303,'Rua João Barbi, 314',3,'foto Artur',1,NULL),(5,'Vinicius Heitor',12312312304,'vinicius_mendes@pisbrasil.com',12312304,'Rua João Barbi, 4',5,'foto t',0,NULL),(6,'Leonardo Fujimura',12312312305,'leonardo.fujimura123@gmail.com',12312305,'Rua Sócrates, 853',6,'foto leo',1,NULL),(7,'Lara Sacoman',12312312306,'lara@gmail.com',12312306,'Rua Sócrates, 843',7,'foto lara',1,NULL),(8,'Lara Sacoman 1',12312312307,'lara1@gmail.com',12312307,'Rua Sócrates, 843',8,'foto lara',1,NULL),(9,'Lucas Lira',12312312308,'LucasLira@pisbrasil.com.br',12312308,'Rua João Barbi, 486',9,'lira foto',1,NULL),(10,'Henrick Adrian',12312312309,'henrick@pisbrasil.com.br',12312309,'Rua João Barbi, 432',10,'henrick foto',1,NULL),(12,'Leonardo',12312312310,'LeoLeo@pisbrasil.com',12312310,'Rua João Barbi, 10',12,NULL,1,NULL),(13,'Alberto Aistai',12312312311,'alberto.aistai@gmail.com',12312311,'Rua João Barbi, 11',13,NULL,1,NULL),(14,'Alberto Levi',12312312312,'alberto.levi@gmail.com',12312312,'Rua João Barbi, 12',14,NULL,1,NULL),(16,'Alberto Maia',12312312313,'alberto.maia@gmail.com',12312313,'Rua João Barbi, 13',16,NULL,1,NULL),(17,'Leonardo Peixoto',12312312314,'LeoPeixe@pisbrasil.com',12312314,'Rua João Barbi, 14',17,NULL,1,NULL),(18,'Leonardo Dantas',12312312315,'LeoDantas@pisbrasil.com',12312315,'Rua João Barbi, 15',18,NULL,1,NULL),(19,'Lucas DelToro',12312312316,'lucas.deltoro@gmail.com',12312316,'Rua João Barbi, 16',19,NULL,1,NULL),(20,'Alberto Paiva',12312312317,'alberto.paiva@gmail.com',12312317,'Rua João Barbi, 17',20,NULL,1,NULL),(21,'Leonardo Viella',12312312318,'LeoViella@pisbrasil.com',12312318,'Rua João Barbi, 18',21,NULL,1,NULL);
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;
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

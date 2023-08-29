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
  UNIQUE KEY `UK_e3gyt2y8r4yd3wnel3je03fok` (`contato_id_contato`),
  KEY `id_contato` (`id_contato`),
  CONSTRAINT `pessoa_ibfk_1` FOREIGN KEY (`id_contato`) REFERENCES `contato` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pessoa`
--

LOCK TABLES `pessoa` WRITE;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` VALUES (1,'Vinicius Heitor Mendes',97876694993,'vinicius_mendes@pisbrasil.com.br',13904802,'Rua João Barbi, 414',1,'df',1,NULL),(2,'Vini Heitors',97876694223,'vinicius@pisbrasil.com.br',13904822,'Rua João Barbi, 424',2,'a',1,NULL),(3,'Artur Menezes',97834694993,'arturMenezes@pisbrasil.com.br',13903802,'Rua João Barbi, 314',3,'foto Artur',1,NULL),(5,'Vinicius Heitor',97876324993,'vinicius_mendes@pisbrasil.com',13904803,'Rua João Barbi, 4',5,'foto t',0,NULL),(6,'Leonardo Fujimura',12312312312,'leonardo.fujimura123@gmail.com',4671071,'Rua Sócrates, 853',6,'foto leo',1,NULL),(7,'Lara Sacoman',12312312334,'lara@gmail.com',4671034,'Rua Sócrates, 843',7,'foto lara',1,NULL),(8,'Lara Sacoman',12312312334,'lara@gmail.com',4671034,'Rua Sócrates, 843',8,'foto lara',1,NULL);
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

-- Dump completed on 2023-08-26 14:06:13

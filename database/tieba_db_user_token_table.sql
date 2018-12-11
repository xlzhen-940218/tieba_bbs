CREATE DATABASE  IF NOT EXISTS `tieba_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tieba_db`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: 103.219.193.171    Database: tieba_db
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_token_table`
--

DROP TABLE IF EXISTS `user_token_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_token_table` (
  `user_id` int(11) NOT NULL,
  `login_token` varchar(32) NOT NULL,
  `login_time` datetime NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_token_table`
--

LOCK TABLES `user_token_table` WRITE;
/*!40000 ALTER TABLE `user_token_table` DISABLE KEYS */;
INSERT INTO `user_token_table` VALUES (3,'fd4037bc0291e92601be5310dfb5f75d','2018-12-10 16:02:25'),(9,'de1a08dc3ee0a8a3989ad3c545defa3e','2018-12-07 18:25:29'),(11,'e52a873a14c5bf0e6b46f6bf1e06f317','2018-12-07 16:31:09'),(13,'5ecaa0b7b457f051ad00f9d92536989c','2018-12-07 15:20:38'),(16,'a38d55b4ab38a02962f301bf86b51d2b','2018-12-08 10:42:52'),(18,'4be6ecdd00e3e629f3b2c612352ba09a','2018-12-09 21:07:24'),(19,'2bca021bcf527c511f893badf0232026','2018-12-07 02:03:11'),(20,'86a44b09371eb84e5bc86d28546d42c6','2018-12-07 12:19:26'),(22,'ea8727e284c6878dce3e9fc8b023ad19','2018-12-07 16:31:01'),(23,'05a411aac89ef32d38b55524fd2fdcfd','2018-12-07 16:42:50'),(24,'06b55be94f90440ff5e6e323dde6eaf5','2018-12-07 21:30:28'),(25,'620d06119542031f098de7cd1511d83a','2018-12-09 03:28:37'),(26,'4ab1b9013ef41e05775396a099f7dbdb','2018-12-09 05:47:34'),(41,'93163294308b626b2f8bb46d15067473','2018-12-09 08:31:15'),(43,'4454b89413a6d7f39b430135179e9e89','2018-12-10 09:29:44'),(44,'89462a9a0cde1b8abf41fc1d55b29548','2018-12-10 13:06:21'),(46,'8a6545f807c84fc2b9f2954cfe900c70','2018-12-10 17:35:15');
/*!40000 ALTER TABLE `user_token_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:18:00

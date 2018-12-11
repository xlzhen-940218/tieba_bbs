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
-- Table structure for table `chat_friend_table`
--

DROP TABLE IF EXISTS `chat_friend_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chat_friend_table` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  `defriend` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`chat_id`),
  UNIQUE KEY `UK_chat` (`user_id`,`friend_id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_friend_table`
--

LOCK TABLES `chat_friend_table` WRITE;
/*!40000 ALTER TABLE `chat_friend_table` DISABLE KEYS */;
INSERT INTO `chat_friend_table` VALUES (75,9,16,0,'2018-12-05 00:39:07'),(80,3,16,0,'2018-12-07 11:19:04'),(87,11,3,0,'2018-12-05 20:54:57'),(89,3,6,0,'2018-12-05 20:38:03'),(101,3,17,0,'2018-12-05 20:44:05'),(102,17,16,0,'2018-12-05 20:49:03'),(105,11,13,0,'2018-12-05 20:55:57'),(106,3,3,0,'2018-12-06 23:25:52'),(108,3,18,0,'2018-12-06 23:45:10'),(111,22,3,0,'2018-12-07 15:25:59'),(115,11,22,0,'2018-12-07 16:29:53'),(116,23,3,0,'2018-12-07 16:41:43');
/*!40000 ALTER TABLE `chat_friend_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:18:27

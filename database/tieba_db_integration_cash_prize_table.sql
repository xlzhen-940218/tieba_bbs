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
-- Table structure for table `integration_cash_prize_table`
--

DROP TABLE IF EXISTS `integration_cash_prize_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `integration_cash_prize_table` (
  `cash_prize_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `money` varchar(45) NOT NULL,
  `month` int(11) NOT NULL,
  `award_code` varchar(64) NOT NULL,
  `to_void` tinyint(1) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`cash_prize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `integration_cash_prize_table`
--

LOCK TABLES `integration_cash_prize_table` WRITE;
/*!40000 ALTER TABLE `integration_cash_prize_table` DISABLE KEYS */;
INSERT INTO `integration_cash_prize_table` VALUES (1,3,'1',1,'QENOHAAZVRRLMFMT',1,'2018-12-06 16:10:38'),(2,3,'1',1,'HIM0WV4LOCJSJ3MC',1,'2018-12-06 16:12:36'),(3,3,'1',1,'NEPE6SA27FF6Y6RR',1,'2018-12-06 16:12:38'),(4,3,'1',1,'VPHLZXH3UQNLFJPC',0,'2018-12-06 16:12:40'),(5,3,'1',1,'5TMNVVEMSHNLWEAK',0,'2018-12-06 16:12:40'),(6,3,'3',3,'R54ZMZLCOYB7ZH3C',0,'2018-12-06 16:12:41'),(7,3,'3',3,'ORSCKKYIPL8MZGOE',0,'2018-12-06 16:12:42'),(8,3,'1',1,'MGFD3CNUI3HN05NH',0,'2018-12-06 16:14:29'),(9,3,'1',1,'OGRLH7RR010IXPKO',0,'2018-12-06 16:14:53');
/*!40000 ALTER TABLE `integration_cash_prize_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:17:33

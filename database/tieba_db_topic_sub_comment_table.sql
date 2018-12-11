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
-- Table structure for table `topic_sub_comment_table`
--

DROP TABLE IF EXISTS `topic_sub_comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `topic_sub_comment_table` (
  `sub_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `comment_create_time` datetime NOT NULL,
  `call_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`sub_comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic_sub_comment_table`
--

LOCK TABLES `topic_sub_comment_table` WRITE;
/*!40000 ALTER TABLE `topic_sub_comment_table` DISABLE KEYS */;
INSERT INTO `topic_sub_comment_table` VALUES (35,18,9,'你要怎么脱单(ﾟoﾟ;','2018-12-03 20:30:36',NULL),(36,19,11,'你为啥要在这里打广告','2018-12-03 20:43:47',NULL),(37,19,9,'你管我打不打广告，关你屁事','2018-12-03 20:44:17',11),(38,15,9,'是嘛，我也觉得好看(｡･ω･｡)ﾉ♡','2018-12-03 20:59:34',NULL),(39,14,13,'怎么不错了','2018-12-03 22:21:35',NULL),(40,20,13,'嗨！','2018-12-03 22:26:21',NULL),(41,22,14,'多谢点赞','2018-12-03 23:18:26',NULL),(42,23,15,'你答对了','2018-12-04 00:03:55',NULL),(43,23,9,'<a href=\'./tieba.group\'>首页</a>','2018-12-04 09:05:40',15),(44,24,9,'哈哈','2018-12-05 00:44:35',NULL),(45,25,3,'好看嘛','2018-12-06 13:27:18',NULL);
/*!40000 ALTER TABLE `topic_sub_comment_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:17:44

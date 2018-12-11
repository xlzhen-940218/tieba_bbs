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
-- Table structure for table `tieba_table`
--

DROP TABLE IF EXISTS `tieba_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tieba_table` (
  `tieba_id` int(11) NOT NULL AUTO_INCREMENT,
  `tieba_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tieba_description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tieba_cover` varchar(100) NOT NULL,
  PRIMARY KEY (`tieba_id`),
  UNIQUE KEY `tieba_name_UNIQUE` (`tieba_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tieba_table`
--

LOCK TABLES `tieba_table` WRITE;
/*!40000 ALTER TABLE `tieba_table` DISABLE KEYS */;
INSERT INTO `tieba_table` VALUES (1,'BTKitty-Only-Girls-Group','btkitty的专属女裙贴吧，欢迎来玩哦！','http://tieba.group/upload/img/nEoDCkvdrfkG.jpg'),(5,'赵今麦粉丝吧','赵今麦的铁粉们看过来啦','http://tieba.group/upload/img/qCE7aYmWMuPR.jpg'),(7,'邓恩熙粉丝吧','邓恩熙粉丝快来这里集结吧，这里每天都有邓恩熙美图哦','http://tieba.group/upload/img/yjaqJ3Yh2igM.jpg'),(8,'SNH48-张语格吧','张语格的粉丝吧，大家一起来粉她吧','http://tieba.group/upload/img/Gyj5r51YaREM.jpg'),(9,'李程程模特吧','李程程粉丝吧，过来看美图吧','http://tieba.group/upload/img/LG1KSbHNxrlD.jpg'),(10,'林允儿粉丝吧','林允儿粉丝吧，有她的粉丝嘛','http://tieba.group/upload/img/QCIbycE0isGK.jpg'),(11,'火箭少女 杨超越','火箭少女杨超越的粉丝贴吧','http://tieba.group/upload/jos5lJ3h5MI1.JPG'),(12,'奥仲麻琴吧','奥仲麻琴（Makoto Okunaga）1993年11月18日出生于埼玉县，**演员。她是女子联组P***PO的成员之一，隶属于PLATINUM PRODUCTION旗下。\n作品主要有《少女飞行》《假面**wizard》等。 [1] \n2015年1月1日于TOKYO DOME CITY HALL举办的全国公演最终场毕业。','http://tieba.group/upload/img/UJlCVmPh1lGF.jpg'),(13,'Cosplay 半半子吧','半半子粉丝吧','http://tieba.group/upload/img/sQbv19PwUS6b.jpg'),(14,'散文轶事','散文轶事吧，畅所欲言吧','http://tieba.group/upload/img/F1KhynX3jiYO.jpg'),(15,'南京女孩-孔君慈','南京女孩-孔君慈 专属贴吧','http://tieba.group/upload/img/rCce35ATIfl0.jpg'),(16,'领克03','汽车领克03吧，发放03美图，欢迎进吧讨论。','http://tieba.group/upload/img/BeOlUefmLWkM.jpg');
/*!40000 ALTER TABLE `tieba_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:17:38

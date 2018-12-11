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
-- Table structure for table `tieba_topic_comment_table`
--

DROP TABLE IF EXISTS `tieba_topic_comment_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tieba_topic_comment_table` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `comment_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `comment_create_time` datetime NOT NULL,
  `comment_img1` varchar(100) DEFAULT NULL,
  `comment_img2` varchar(100) DEFAULT NULL,
  `comment_img3` varchar(100) DEFAULT NULL,
  `comment_img4` varchar(100) DEFAULT NULL,
  `comment_img5` varchar(100) DEFAULT NULL,
  `comment_img6` varchar(100) DEFAULT NULL,
  `comment_img7` varchar(100) DEFAULT NULL,
  `comment_img8` varchar(100) DEFAULT NULL,
  `comment_img9` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tieba_topic_comment_table`
--

LOCK TABLES `tieba_topic_comment_table` WRITE;
/*!40000 ALTER TABLE `tieba_topic_comment_table` DISABLE KEYS */;
INSERT INTO `tieba_topic_comment_table` VALUES (10,9,3,'真漂亮呀真漂亮呀真漂亮呀','2018-12-01 18:03:29',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,9,3,'我是3楼，3楼哦！','2018-12-01 18:44:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,11,3,'你在说啥啊，完全不明白(°ㅂ° ╬)','2018-12-01 21:57:00','http://tieba.group/upload/img/rknJnBnhpgm2.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,12,3,'太美了✌(̿▀̿ ̿Ĺ̯̿̿▀̿ ̿)✌00后营养跟得上啊，厉害了(ง •̀_•́)ง','2018-12-01 22:50:36','http://tieba.group/upload/img/HHErDkNJ8vnq.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,18,3,'哇，真的不错哦！','2018-12-02 20:18:27','http://tieba.group/upload/RkQQLxBz4uzA.PNG',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,19,9,'真好看呀(｡･ω･｡)ﾉ♡','2018-12-02 21:28:01',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,14,9,'哈哈，来占个楼','2018-12-02 21:40:56','http://tieba.group/upload/img/zDwcTgMXiSux.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,9,9,'占个楼，嘿嘿','2018-12-02 22:02:00','http://tieba.group/upload/img/fZK7WRsH8zPP.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,18,9,'脱单走起','2018-12-02 22:13:14',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,20,9,'真的美呀(//∇//)','2018-12-03 20:43:13','http://tieba.group/upload/img/hQ66jkMKVcPF.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,20,6,'的大大方方\n\n','2018-12-03 20:52:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,20,13,'三星内置壁纸','2018-12-03 22:28:03','http://tieba.group/upload/img/AEfttRSBLgRE.jpg','http://tieba.group/upload/img/wbgdVx5IcZLR.jpg','http://tieba.group/upload/img/c8dRUmU3Qi4G.jpg','http://tieba.group/upload/img/estPMcxFzGID.jpg','http://tieba.group/upload/img/mq5U7tOZwSNp.jpg','http://tieba.group/upload/img/996vZoOPEooo.jpg','http://tieba.group/upload/img/NSCVtVZ7SxpE.jpg',NULL,NULL),(22,21,9,'真好看呀，点个赞','2018-12-03 23:17:39',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,22,9,'这不是星女郎嘛','2018-12-04 00:03:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,23,3,'杨超越真好看啊','2018-12-05 00:43:41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,23,3,'嘿嘿(º﹃º )','2018-12-05 00:46:09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,20,11,'真的很好看呀','2018-12-05 00:52:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,24,17,'不错啊','2018-12-05 20:47:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,31,9,'哈哈，有趣','2018-12-06 13:54:39','http://tieba.group/upload/img/03QY1GHuEH6B.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,25,16,'真好看呀','2018-12-06 14:06:54',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'http://tieba.group/upload/img/F8KzmK6xhQH2.JPG'),(30,32,3,'真好看(｡･ω･｡)ﾉ♡','2018-12-06 22:31:51',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,35,3,'?????⛱????????????✉️???⚽️??⚾️????⛳️???????⛷?⛸??????????????????????','2018-12-07 15:01:04','http://tieba.group/upload/img/aMktaoRt8CzZ.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tieba_topic_comment_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:18:32

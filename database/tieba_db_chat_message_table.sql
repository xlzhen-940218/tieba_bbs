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
-- Table structure for table `chat_message_table`
--

DROP TABLE IF EXISTS `chat_message_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chat_message_table` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `message_content` varchar(500) NOT NULL,
  `message_type` varchar(10) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message_table`
--

LOCK TABLES `chat_message_table` WRITE;
/*!40000 ALTER TABLE `chat_message_table` DISABLE KEYS */;
INSERT INTO `chat_message_table` VALUES (15,75,9,16,'小姐姐你好','text','2018-12-04 23:45:57'),(16,75,16,9,'你好呀','text','2018-12-04 23:47:34'),(17,75,9,16,'嘿嘿','text','2018-12-04 23:48:11'),(18,75,16,9,'哈喽','text','2018-12-04 23:50:31'),(19,75,9,16,'你好呀','text','2018-12-04 23:51:00'),(20,75,16,9,'给我发红包','text','2018-12-04 23:51:40'),(21,80,3,16,'你好呀','text','2018-12-04 23:52:59'),(22,80,16,3,'哈喽','text','2018-12-04 23:53:16'),(23,80,3,16,'你好呀','text','2018-12-04 23:56:31'),(24,80,16,3,'你也好','text','2018-12-04 23:56:50'),(25,80,16,3,'你好呀','text','2018-12-04 23:57:34'),(26,80,16,3,'你好呀','text','2018-12-04 23:58:11'),(27,75,9,16,'凭啥子','text','2018-12-05 00:39:21'),(28,75,16,9,'你说咧','text','2018-12-05 00:39:33'),(29,75,9,16,'没有为啥','text','2018-12-05 00:39:49'),(30,87,11,3,'哈喽<br/>','text','2018-12-05 00:53:40'),(31,87,3,11,'你是哪位呀','text','2018-12-05 00:54:05'),(32,87,11,3,'http://tieba.group/upload/img/B04KSq8zOnju.jpg','image','2018-12-05 00:54:40'),(33,89,3,6,'嗨，搭理我一下呀','text','2018-12-05 12:35:51'),(34,89,3,6,'http://tieba.group/upload/img/34SiZjSPa3Jh.jpg','image','2018-12-05 12:36:09'),(35,89,6,3,'在','text','2018-12-05 13:05:07'),(36,89,3,6,'嘿嘿(º﹃º )','text','2018-12-05 13:05:21'),(37,89,6,3,'这个还可可以聊','text','2018-12-05 13:05:49'),(38,89,3,6,'http://tieba.group/upload/img/zsG1BJoon8Cd.jpg','image','2018-12-05 13:06:06'),(39,89,3,6,'还能发图片','text','2018-12-05 13:06:40'),(40,89,6,3,'http://tieba.group/upload/img/qqYJO3Puk6CL.jpg','image','2018-12-05 13:06:49'),(41,89,3,6,'就是语音不行，需要客户端才行','text','2018-12-05 13:06:57'),(42,89,3,6,'图片会变2张是bug，今天下午修复','text','2018-12-05 13:07:20'),(43,89,6,3,'提示会盗号','text','2018-12-05 13:07:56'),(44,89,3,6,'你别在微信的浏览器里面聊天啊','text','2018-12-05 13:08:19'),(45,89,3,6,'任何网站只要有输入框，在微信里面都会提示会盗号','text','2018-12-05 13:09:17'),(46,89,6,3,'那怎么办','text','2018-12-05 13:09:58'),(47,89,3,6,'凉拌呗','text','2018-12-05 13:12:05'),(48,89,3,6,'你打开小米浏览器，然后登录tieba.group，再找我私信不就好啦','text','2018-12-05 13:13:37'),(49,87,3,11,'嗨','text','2018-12-05 19:09:29'),(50,87,11,3,'你好呀','text','2018-12-05 19:09:58'),(51,87,3,11,'http://tieba.group/upload/img/6hFvfWV8yv9j.jpg','image','2018-12-05 19:10:18'),(52,101,3,17,'嘿嘿','text','2018-12-05 20:44:11'),(53,101,17,3,'http://tieba.group/upload/img/vYommwup3ET8.jpg','image','2018-12-05 20:45:05'),(54,105,11,13,'hai','text','2018-12-05 20:56:02'),(55,105,11,13,'http://tieba.group/upload/img/nSW23kSqi0Oy.png','image','2018-12-05 20:56:34'),(56,105,13,11,'你传的是啥','text','2018-12-05 20:56:52'),(57,105,11,13,'图片啊','text','2018-12-05 20:57:04'),(58,105,13,11,'http://tieba.group/upload/img/ENv4UlIkSgwo.jpg','image','2018-12-05 20:57:19'),(59,105,13,11,'我也来一张','text','2018-12-05 20:57:25'),(60,105,11,13,'你了真厉害','text','2018-12-05 20:57:38'),(61,106,3,3,'你好( ^_^)／','text','2018-12-06 23:25:58'),(62,111,22,3,'你好呀','text','2018-12-07 15:24:43'),(63,111,3,22,'你也好','text','2018-12-07 15:24:55'),(64,115,11,22,'嗨','text','2018-12-07 16:29:59'),(65,115,11,22,'http://tieba.group/upload/img/UknyTBdV4lCK.jpg','image','2018-12-07 16:30:16'),(66,115,22,11,'你发的是啥','text','2018-12-07 16:30:28'),(67,115,11,22,'图片呀','text','2018-12-07 16:30:38'),(68,115,22,11,'那我也来一张','text','2018-12-07 16:30:45'),(69,115,22,11,'http://tieba.group/upload/img/QeACxqpCZUND.jpg','image','2018-12-07 16:31:00'),(70,115,11,22,'真好看','text','2018-12-07 16:31:09'),(71,116,23,3,'你好呀','text','2018-12-07 16:41:56'),(72,116,23,3,'http://tieba.group/upload/img/GofiAp0O7k3z.jpg','image','2018-12-07 16:42:03'),(73,116,3,23,'http://tieba.group/upload/img/7FNPJWmFvmLJ.jpg','image','2018-12-07 16:42:32'),(74,116,23,3,'哈哈','text','2018-12-07 16:42:41');
/*!40000 ALTER TABLE `chat_message_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:17:28

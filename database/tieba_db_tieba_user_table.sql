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
-- Table structure for table `tieba_user_table`
--

DROP TABLE IF EXISTS `tieba_user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tieba_user_table` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) NOT NULL,
  `user_pet_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_pwd` varchar(128) NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `user_integration` int(11) DEFAULT NULL,
  `user_head_img` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  UNIQUE KEY `user_email_UNIQUE` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tieba_user_table`
--

LOCK TABLES `tieba_user_table` WRITE;
/*!40000 ALTER TABLE `tieba_user_table` DISABLE KEYS */;
INSERT INTO `tieba_user_table` VALUES (3,'xlzhen','熊龙镇','6e81378e51f02544ffddbb2d7b4ecbdd','1124822214@qq.com',250,'http://tieba.group/upload/img/QqVtEWTafLvI.jpg'),(6,'荔心小一子',NULL,'961cceb06b7882e5a5a77d5df49b333c','840524405@qq.com',10,'http://tieba.group/upload/img/head.jpg'),(7,'laoyao1986',NULL,'acea0792d4d8a68e12cc7e0f461a3bc5','yaoyangyang1986@163.com',5,'http://tieba.group/upload/img/head.jpg'),(8,'hpkun',NULL,'e10adc3949ba59abbe56e057f20f883e','hpkun@qq.com',15,'http://tieba.group/upload/img/head.jpg'),(9,'zxiwei','希玮的小号','e10adc3949ba59abbe56e057f20f883e','1207948815@qq.com',10,'http://tieba.group/upload/img/OS23BlT1tcNp.jpg'),(10,'乌拉乌拉乌拉',NULL,'25f9e794323b453885f5181f1b624d0b','www.207932647@qq.com',10,'http://tieba.group/upload/img/head.jpg'),(11,'lalala','辢藞柆','e10adc3949ba59abbe56e057f20f883e','xionglongzhen@hotmail.com',15,'http://tieba.group/upload/img/V0M7Bzn2tJ0v.jpg'),(13,'Samsung','三星手机','e10adc3949ba59abbe56e057f20f883e','123456@tieba.group',10,'http://tieba.group/upload/img/WiCMnr4RvasD.jpg'),(14,'honor@','荣耀6','e10adc3949ba59abbe56e057f20f883e','honor@qq.com',5,'http://tieba.group/upload/img/9DhnyDuYcIIW.jpg'),(15,'魅族mx4 plus','魅族手机','e10adc3949ba59abbe56e057f20f883e','meizu@meizu.com',5,'http://tieba.group/upload/img/QeylC59dOaFl.jpg'),(16,'iphone5s','苹果5s','e10adc3949ba59abbe56e057f20f883e','iPhone5s@qq.com',55,'http://tieba.group/upload/gli1Rm0SFAAD.JPG'),(17,'xiongenfu',NULL,'e10adc3949ba59abbe56e057f20f883e','ahdjfkf@qq.com',5,'http://tieba.group/upload/img/head.jpg'),(18,'626080979',NULL,'9cf7e94c13ce2243f9c60b728ffbff46','626080979@qq.com',5,'http://tieba.group/upload/img/head.jpg'),(19,'854972252',NULL,'20faf2abfc87775aeef7a8b7787a3f6b','854972252@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(20,'2820889524',NULL,'25f9e794323b453885f5181f1b624d0b','2820889524@qq.com',5,'http://tieba.group/upload/img/head.jpg'),(21,'1640127701',NULL,'1a9d7ca3ab712565ffb7275e7a486963','gzc1640127701@163.com',0,'http://tieba.group/upload/img/head.jpg'),(22,'oppo x9077','find5','e10adc3949ba59abbe56e057f20f883e','oppo@qq.com',0,'http://tieba.group/upload/img/ifcpvegq3dFc.jpg'),(23,'坚果1手机','坚果1','e10adc3949ba59abbe56e057f20f883e','锤子@t.tt',0,'http://tieba.group/upload/img/PsJf3NsAC1e2.jpg'),(24,'Samsung note3',NULL,'e10adc3949ba59abbe56e057f20f883e','samsung@qq.com',5,'http://tieba.group/upload/img/head.jpg'),(25,'2243117873',NULL,'3ac50638220af07a0d038c7274f301c0','2243117873@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(26,'kokoman',NULL,'00b7691d86d96aebd21dd9e138f90840','hekewei28@163.com',0,'http://tieba.group/upload/img/head.jpg'),(27,'123456789',NULL,'8068c76c7376bc08e2836ab26359d4a4','qweasd@163.com',0,'http://tieba.group/upload/img/head.jpg'),(28,'1485099833',NULL,'42167ca70323171fcbdc898e01d4137f','qq.com1485099833@',0,'http://tieba.group/upload/img/head.jpg'),(31,'2323142511',NULL,'3cb4c9ab2534ae8034e75b4b8a8d3536','2323142511@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(41,'770764397',NULL,'fa3eacc54fc3f7126325a26aeb5a5b10','770764397@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(42,'xd502328365',NULL,'1e86d9813f851195587210ce5b39f2ea','1610624392@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(43,'a3270587419',NULL,'7690b2bb33f0ddaba55cae038e08c210','3270587419@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(44,'2660330202',NULL,'c8837b23ff8aaa8a2dde915473ce0991','2660330202@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(45,'你呢123',NULL,'25f9e794323b453885f5181f1b624d0b','165642775@qq.com',0,'http://tieba.group/upload/img/head.jpg'),(46,'有人njjj',NULL,'f14029217ff5e7a50cdc7e70f686cf29','sb@qq.com',0,'http://tieba.group/upload/img/head.jpg');
/*!40000 ALTER TABLE `tieba_user_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-10 19:18:17

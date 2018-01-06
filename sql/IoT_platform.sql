-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: IoT_platform
-- ------------------------------------------------------
-- Server version	5.7.17-1

/*!401t_data_typeid01 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
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
-- Table structure for table `t_data`
--

DROP TABLE IF EXISTS `t_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据信息id pk',
  `device_id` int(11) NOT NULL COMMENT '设备id fk',
  `data_value` varchar(1024) COLLATE utf8_unicode_ci NOT NULL COMMENT '数据信息(json)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `t_data_t_device_info_id_fk` (`device_id`),
  CONSTRAINT `t_data_t_device_info_id_fk` FOREIGN KEY (`device_id`) REFERENCES `t_device_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='设备上传信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_data`
--

LOCK TABLES `t_data` WRITE;
/*!40000 ALTER TABLE `t_data` DISABLE KEYS */;
INSERT INTO `t_data` VALUES (1,1,'100','2017-10-27 20:23:31'),(2,1,'200','2017-10-27 20:23:39'),(3,1,'250','2017-10-27 20:24:01');
/*!40000 ALTER TABLE `t_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_data_type`
--

DROP TABLE IF EXISTS `t_data_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_data_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `name` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='设备上传的数据类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_data_type`
--

LOCK TABLES `t_data_type` WRITE;
/*!40000 ALTER TABLE `t_data_type` DISABLE KEYS */;
INSERT INTO `t_data_type` VALUES (1,'数值型');
/*!40000 ALTER TABLE `t_data_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_device_info`
--

DROP TABLE IF EXISTS `t_device_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备id pk',
  `project_id` int(11) NOT NULL COMMENT '所属项目id fk',
  `protocol_id` int(11) NOT NULL COMMENT '协议id fk',
  `data_type` int(11) NOT NULL COMMENT '设备数据键(json)',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '设备名称',
  `number` varchar(512) COLLATE utf8_unicode_ci NOT NULL COMMENT '设备编号',
  `privacy` int(1) NOT NULL COMMENT '设备保密性\n0=公开\n1=保密',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_device_info_number_uindex` (`number`),
  KEY `t_device_info_t_project_info_id_fk` (`project_id`),
  KEY `t_device_info_t_protocol_info_id_fk` (`protocol_id`),
  KEY `t_device_info_t_data_type_id_fk` (`data_type`),
  CONSTRAINT `t_device_info_t_data_type_id_fk` FOREIGN KEY (`data_type`) REFERENCES `t_data_type` (`id`),
  CONSTRAINT `t_device_info_t_project_info_id_fk` FOREIGN KEY (`project_id`) REFERENCES `t_project_info` (`id`),
  CONSTRAINT `t_device_info_t_protocol_info_id_fk` FOREIGN KEY (`protocol_id`) REFERENCES `t_protocol_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_device_info`
--

LOCK TABLES `t_device_info` WRITE;
/*!40000 ALTER TABLE `t_device_info` DISABLE KEYS */;
INSERT INTO `t_device_info` VALUES (1,1,1,1,'传感器1','32fse',0,'2017-10-26 15:48:19');
/*!40000 ALTER TABLE `t_device_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_project_industry`
--

DROP TABLE IF EXISTS `t_project_industry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_project_industry` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '行业id pk',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '行业名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='项目行业表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_project_industry`
--

LOCK TABLES `t_project_industry` WRITE;
/*!40000 ALTER TABLE `t_project_industry` DISABLE KEYS */;
INSERT INTO `t_project_industry` VALUES (1,'智能家具'),(2,'车载设备'),(3,'可穿戴设备'),(4,'医疗保健'),(5,'智能玩具'),(6,'新能源'),(7,'运动监控'),(8,'智能教育'),(9,'环境监控'),(10,'办公设备');
/*!40000 ALTER TABLE `t_project_industry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_project_info`
--

DROP TABLE IF EXISTS `t_project_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_project_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '项目名称',
  `industry_id` int(11) NOT NULL COMMENT '行业id fk',
  `profile` varchar(500) COLLATE utf8_unicode_ci NOT NULL COMMENT '项目简介',
  `api_key` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'APIKey',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '用户id fk',
  PRIMARY KEY (`id`),
  KEY `t_project_info_t_project_industry_id_fk` (`industry_id`),
  KEY `t_project_info_t_user_info_id_fk` (`user_id`),
  CONSTRAINT `t_project_info_t_project_industry_id_fk` FOREIGN KEY (`industry_id`) REFERENCES `t_project_industry` (`id`),
  CONSTRAINT `t_project_info_t_user_info_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='项目信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_project_info`
--

LOCK TABLES `t_project_info` WRITE;
/*!40000 ALTER TABLE `t_project_info` DISABLE KEYS */;
INSERT INTO `t_project_info` VALUES (1,'项目1',1,'测试产品2','sdakasyri','2017-10-26 15:47:49',1),(2,'项目2',2,'测试项目2','sadkhjf324ysabd','2017-11-04 08:58:43',1);
/*!40000 ALTER TABLE `t_project_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_protocol_info`
--

DROP TABLE IF EXISTS `t_protocol_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_protocol_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '协议id pk',
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '协议名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='传输协议表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_protocol_info`
--

LOCK TABLES `t_protocol_info` WRITE;
/*!40000 ALTER TABLE `t_protocol_info` DISABLE KEYS */;
INSERT INTO `t_protocol_info` VALUES (1,'HTTP'),(2,'MQTT');
/*!40000 ALTER TABLE `t_protocol_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_record_info`
--

DROP TABLE IF EXISTS `t_record_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_record_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '行为id pk',
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '行为名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='行为信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_record_info`
--

LOCK TABLES `t_record_info` WRITE;
/*!40000 ALTER TABLE `t_record_info` DISABLE KEYS */;
INSERT INTO `t_record_info` VALUES (1,'添加新项目'),(2,'添加新设备'),(3,'删除一个项目'),(4,'删除一个设备');
/*!40000 ALTER TABLE `t_record_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_info`
--

DROP TABLE IF EXISTS `t_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识 pk',
  `username` varchar(12) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `bind_phone` varchar(11) COLLATE utf8_unicode_ci NOT NULL COMMENT '绑定手机',
  `bind_email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '绑定手机',
  `real_name` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '真实姓名',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `location` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '工作单位或者所在学校',
  `work_place` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '所在地',
  `personal_profile` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个人简介',
  `head_image` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `qq` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'QQ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_user_info_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_info`
--

LOCK TABLES `t_user_info` WRITE;
/*!40000 ALTER TABLE `t_user_info` DISABLE KEYS */;
INSERT INTO `t_user_info` VALUES (1,'admin','admin','10086','1@qq.com','张三','2017-10-27',NULL,NULL,NULL,NULL,NULL,NULL),(2,'user','user','10086','2@qq.com','李四','2017-10-27',NULL,NULL,NULL,NULL,NULL,NULL),(3,'test','test','789540','3@qq.com','老王',NULL,NULL,'wust 508',NULL,NULL,NULL,'12345');
/*!40000 ALTER TABLE `t_user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_record`
--

DROP TABLE IF EXISTS `t_user_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id pk',
  `user_id` int(11) NOT NULL COMMENT '用户id fk',
  `record_id` int(11) NOT NULL COMMENT '行为id',
  `data` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户操作数据',
  `create_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`),
  KEY `t_user_record_t_record_info_id_fk` (`record_id`),
  KEY `t_user_record_t_user_info_id_fk` (`user_id`),
  CONSTRAINT `t_user_record_t_record_info_id_fk` FOREIGN KEY (`record_id`) REFERENCES `t_record_info` (`id`),
  CONSTRAINT `t_user_record_t_user_info_id_fk` FOREIGN KEY (`user_id`) REFERENCES `t_user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户行为记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_record`
--

LOCK TABLES `t_user_record` WRITE;
/*!40000 ALTER TABLE `t_user_record` DISABLE KEYS */;
INSERT INTO `t_user_record` VALUES (1,1,1,'1','2017-10-27 16:14:05'),(2,1,2,'1','2017-10-27 16:14:17'),(3,2,1,'2','2017-10-27 16:23:32');
/*!40000 ALTER TABLE `t_user_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-08 10:14:18

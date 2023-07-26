-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 192.168.0.106    Database: data_delivery
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `GEN_EVENT_MGT`
--

DROP TABLE IF EXISTS `GEN_EVENT_MGT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GEN_EVENT_MGT` (
  `EVENT_ID` bigint(20) NOT NULL,
  `DISCOUNT` int(11) DEFAULT NULL,
  `EVENT_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FROM_DT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `INS_DT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TO_DT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATE_DT` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`EVENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GEN_EVENT_MGT`
--

LOCK TABLES `GEN_EVENT_MGT` WRITE;
/*!40000 ALTER TABLE `GEN_EVENT_MGT` DISABLE KEYS */;
INSERT INTO `GEN_EVENT_MGT` VALUES (1663571539707,43,'cvcvcv','2022-09-05','2022-09-19 14:12:19','2022-09-20',NULL);
/*!40000 ALTER TABLE `GEN_EVENT_MGT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GEN_LICENSE_MGT`
--

DROP TABLE IF EXISTS `GEN_LICENSE_MGT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GEN_LICENSE_MGT` (
  `LICENSE_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `CLIENT_ID` varchar(50) COLLATE utf8_bin NOT NULL,
  `EXPIRED_DT` datetime(6) DEFAULT NULL,
  `INS_DT` datetime(6) DEFAULT NULL,
  `START_DT` datetime(6) DEFAULT NULL,
  `TOKEN` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `UPDATE_DT` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`LICENSE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GEN_LICENSE_MGT`
--

LOCK TABLES `GEN_LICENSE_MGT` WRITE;
/*!40000 ALTER TABLE `GEN_LICENSE_MGT` DISABLE KEYS */;
/*!40000 ALTER TABLE `GEN_LICENSE_MGT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GEN_PROJECT_MGT`
--

DROP TABLE IF EXISTS `GEN_PROJECT_MGT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GEN_PROJECT_MGT` (
  `PROJECT_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DATA_FILE_PATH` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `GEN_CYCLE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TOPIC` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`PROJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GEN_PROJECT_MGT`
--

LOCK TABLES `GEN_PROJECT_MGT` WRITE;
/*!40000 ALTER TABLE `GEN_PROJECT_MGT` DISABLE KEYS */;
INSERT INTO `GEN_PROJECT_MGT` VALUES ('20220919101013001','20220919090926005','2022-09-19 10:38:16.370000','0ddafc35-577b-4e5b-b2ef-3f0c867e25cb','0 0/1 * 1/1 * ? *','demo','02-01','20220919101013001',NULL);
/*!40000 ALTER TABLE `GEN_PROJECT_MGT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GEN_TABLE_INFO`
--

DROP TABLE IF EXISTS `GEN_TABLE_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GEN_TABLE_INFO` (
  `TABLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `LIST_COLUMN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TABLE_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `TARGET_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TABLE_ID`),
  KEY `FKen0n9iocyvgi0ch0e7otc7yhn` (`TARGET_ID`),
  CONSTRAINT `FKen0n9iocyvgi0ch0e7otc7yhn` FOREIGN KEY (`TARGET_ID`) REFERENCES `GEN_TARGET_MGT` (`TARGET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GEN_TABLE_INFO`
--

LOCK TABLES `GEN_TABLE_INFO` WRITE;
/*!40000 ALTER TABLE `GEN_TABLE_INFO` DISABLE KEYS */;
/*!40000 ALTER TABLE `GEN_TABLE_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GEN_TARGET_MGT`
--

DROP TABLE IF EXISTS `GEN_TARGET_MGT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `GEN_TARGET_MGT` (
  `TARGET_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DATABASE_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FILE_SAVE_PATH` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FILE_TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PORT` int(11) DEFAULT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `TARGET_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `USER_UID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USERNAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CONNECT_TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROJECT_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TARGET_ID`),
  KEY `FKhevgobpjpkgnge5s1jwjas15w` (`CONNECT_TYPE`),
  KEY `FKp8qnib7n8ni4ilnrreb92elsm` (`PROJECT_ID`),
  CONSTRAINT `FKhevgobpjpkgnge5s1jwjas15w` FOREIGN KEY (`CONNECT_TYPE`) REFERENCES `TCCO_STD` (`COMM_CD`),
  CONSTRAINT `FKp8qnib7n8ni4ilnrreb92elsm` FOREIGN KEY (`PROJECT_ID`) REFERENCES `GEN_PROJECT_MGT` (`PROJECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GEN_TARGET_MGT`
--

LOCK TABLES `GEN_TARGET_MGT` WRITE;
/*!40000 ALTER TABLE `GEN_TARGET_MGT` DISABLE KEYS */;
/*!40000 ALTER TABLE `GEN_TARGET_MGT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQ_PROJECT_ID`
--

DROP TABLE IF EXISTS `SEQ_PROJECT_ID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SEQ_PROJECT_ID` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQ_PROJECT_ID`
--

LOCK TABLES `SEQ_PROJECT_ID` WRITE;
/*!40000 ALTER TABLE `SEQ_PROJECT_ID` DISABLE KEYS */;
INSERT INTO `SEQ_PROJECT_ID` VALUES (1000,1,999,1,1,1000,1,0);
/*!40000 ALTER TABLE `SEQ_PROJECT_ID` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQ_TOPIC`
--

DROP TABLE IF EXISTS `SEQ_TOPIC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SEQ_TOPIC` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQ_TOPIC`
--

LOCK TABLES `SEQ_TOPIC` WRITE;
/*!40000 ALTER TABLE `SEQ_TOPIC` DISABLE KEYS */;
INSERT INTO `SEQ_TOPIC` VALUES (1,1,999,1,1,1000,1,0);
/*!40000 ALTER TABLE `SEQ_TOPIC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SEQ_USER_UID`
--

DROP TABLE IF EXISTS `SEQ_USER_UID`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SEQ_USER_UID` (
  `next_not_cached_value` bigint(21) NOT NULL,
  `minimum_value` bigint(21) NOT NULL,
  `maximum_value` bigint(21) NOT NULL,
  `start_value` bigint(21) NOT NULL COMMENT 'start value when sequences is created or value if RESTART is used',
  `increment` bigint(21) NOT NULL COMMENT 'increment value',
  `cache_size` bigint(21) unsigned NOT NULL,
  `cycle_option` tinyint(1) unsigned NOT NULL COMMENT '0 if no cycles are allowed, 1 if the sequence should begin a new cycle when maximum_value is passed',
  `cycle_count` bigint(21) NOT NULL COMMENT 'How many cycles have been done'
) ENGINE=InnoDB SEQUENCE=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SEQ_USER_UID`
--

LOCK TABLES `SEQ_USER_UID` WRITE;
/*!40000 ALTER TABLE `SEQ_USER_UID` DISABLE KEYS */;
INSERT INTO `SEQ_USER_UID` VALUES (1000,1,999,1,1,1000,1,0);
/*!40000 ALTER TABLE `SEQ_USER_UID` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TCCO_FILE`
--

DROP TABLE IF EXISTS `TCCO_FILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TCCO_FILE` (
  `ATCH_FLE_SEQ` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `FLE_KEY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FLE_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FLE_PATH` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FLE_SZ` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FLE_TP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NEW_FLE_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ATCH_FLE_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TCCO_FILE`
--

LOCK TABLES `TCCO_FILE` WRITE;
/*!40000 ALTER TABLE `TCCO_FILE` DISABLE KEYS */;
INSERT INTO `TCCO_FILE` VALUES ('0ddafc35-577b-4e5b-b2ef-3f0c867e25cb','','2022-09-19 10:38:16.271000',NULL,'addresses.csv','0ddafc35-577b-4e5b-b2ef-3f0c867e25cb.csv','328','.csv','0ddafc35-577b-4e5b-b2ef-3f0c867e25cb.csv','','2022-09-19 10:38:16.271000');
/*!40000 ALTER TABLE `TCCO_FILE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TCCO_STD`
--

DROP TABLE IF EXISTS `TCCO_STD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TCCO_STD` (
  `COMM_CD` varchar(255) COLLATE utf8_bin NOT NULL,
  `COMM_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `COMM_NM_EN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `COMM_NM_VI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LEV` int(11) DEFAULT NULL,
  `UP_COMM_CD` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `USE_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`COMM_CD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TCCO_STD`
--

LOCK TABLES `TCCO_STD` WRITE;
/*!40000 ALTER TABLE `TCCO_STD` DISABLE KEYS */;
INSERT INTO `TCCO_STD` VALUES ('01','데이터베이스','Database','Cơ sở dữ liệu','1663552264002','2022-09-19 10:26:11.000000',NULL,1,NULL,NULL,NULL,'Y'),('01-01','MariaDB','MariaDB','MariaDB','1663552264002','2022-09-19 10:29:31.000000',NULL,2,'01',NULL,NULL,'Y'),('01-02','SQL Server','SQL Server','SQL Server','1663552264002','2022-09-19 10:33:52.450000',NULL,2,'01',NULL,NULL,'Y'),('01-03','Oracle','Oracle','Oracle','1663552264002','2022-09-19 10:34:07.329000',NULL,2,'01',NULL,NULL,'Y'),('01-04','PostgreSQL','PostgreSQL','PostgreSQL','1663552264002','2022-09-19 10:34:19.213000',NULL,2,'01',NULL,NULL,'Y'),('01-05','MySQL','MySQL','MySQL','1663552264002','2022-09-19 10:35:26.031000',NULL,2,'01',NULL,NULL,'Y'),('01-06','MongoDB','MongoDB','MongoDB','1663552264002','2022-09-19 10:35:34.730000',NULL,2,'01',NULL,NULL,'Y'),('01-07','FTP','FTP','FTP','1663552264002','2022-09-19 10:35:45.010000',NULL,2,'01',NULL,NULL,'Y'),('02','프로젝트현황','Project status','Trạng thái dự án','1663552264002','2022-09-19 10:41:33.000000',NULL,1,NULL,NULL,NULL,'Y'),('02-01','신규','New','Mới','1663552264002','2022-09-19 10:42:20.994000',NULL,2,'02',NULL,NULL,'Y'),('02-02','입니다.','Running','Đang chạy','1663552264002','2022-09-19 10:42:39.110000',NULL,2,'02',NULL,NULL,'Y'),('02-03','멈추다','Pause','Tạm dừng','1663552264002','2022-09-19 10:43:04.109000',NULL,2,'02',NULL,NULL,'Y'),('02-04','이제 그만','Stop','Dừng','1663552264002','2022-09-19 10:43:38.043000',NULL,2,'02',NULL,NULL,'Y'),('02-05','죽여','Killed','Huỷ','1663552264002','2022-09-19 10:44:05.491000',NULL,2,'02',NULL,NULL,'Y'),('03','파일 형식','File type','Kiểu file','1663552264002','2022-09-19 10:45:56.000000',NULL,1,NULL,NULL,NULL,'Y'),('03-01','CSV','CSV','CSV','1663552264002','2022-09-19 10:46:34.238000',NULL,2,'03',NULL,NULL,'Y'),('03-02','EXCEL','EXCEL','EXCEL','1663552264002','2022-09-19 10:46:43.853000',NULL,2,'03',NULL,NULL,'Y'),('03-03','JSON','JSON','JSON',NULL,'2022-09-19 14:20:57.000000',NULL,2,'03',NULL,NULL,'Y'),('03-04','XML','XML','XML',NULL,NULL,NULL,2,'03',NULL,NULL,'Y'),('04','위치','Position','Vị trí',NULL,'2022-09-19 10:47:27.000000',NULL,1,NULL,NULL,NULL,'Y'),('04-01','Developer','Developer','Developer',NULL,'2022-09-19 10:47:28.000000',NULL,2,'04',NULL,NULL,'Y'),('04-02','Tester','Tester','Tester',NULL,'2022-09-19 10:47:29.000000',NULL,2,'04',NULL,NULL,'Y'),('04-03','QC','QC','QC',NULL,'2022-09-19 10:47:29.000000',NULL,2,'04',NULL,NULL,'Y'),('04-04','PM','PM','PM',NULL,'2022-09-19 10:47:30.000000',NULL,2,'04',NULL,NULL,'Y'),('04-05','Client','Client','Client',NULL,'2022-09-19 10:48:47.000000',NULL,2,'04',NULL,NULL,'Y'),('04-06','Demo','Demo','Demo',NULL,'2022-09-19 10:49:18.000000',NULL,2,'04',NULL,NULL,'Y'),('04-07','Admin','Admin','Admin',NULL,'2022-09-19 10:50:18.000000',NULL,2,'04',NULL,NULL,'Y');
/*!40000 ALTER TABLE `TCCO_STD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TSST_MENU`
--

DROP TABLE IF EXISTS `TSST_MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TSST_MENU` (
  `MENU_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LEV` int(11) DEFAULT NULL,
  `MENU_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MENU_NM_EN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MENU_NM_VI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MENU_TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ORD_NO` int(11) DEFAULT NULL,
  `UP_MENU_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `URL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USE_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TSST_MENU`
--

LOCK TABLES `TSST_MENU` WRITE;
/*!40000 ALTER TABLE `TSST_MENU` DISABLE KEYS */;
INSERT INTO `TSST_MENU` VALUES ('MNU_01','1663552264002','2022-09-19 09:08:43.000000',NULL,1,'Dashboard','Dashboard','Dashboard',NULL,1,NULL,NULL,NULL,'/dashboard','Y'),('MNU_02','1663552264002','2022-09-19 09:42:30.414000',NULL,1,'General Management','General Management','Trung tâm quản lý',NULL,3,'',NULL,NULL,NULL,'Y'),('MNU_02_01','20220919090926005','2022-09-19 10:07:57.919000',NULL,2,'Project Management (Draw)','Project Management (Draw)','Quản lý dự án',NULL,11,'MNU_02',NULL,NULL,'/gen/gen0201','Y'),('MNU_02_02','1663552264002','2022-09-19 10:18:42.110000',NULL,2,'General Infomation','General Infomation','General Infomation',NULL,14,'MNU_02',NULL,NULL,'/gen/gen0301','Y'),('MNU_02_03','1663552264002','2022-09-19 10:19:40.041000',NULL,2,'대상 관리','Target Management','Quản lý mục tiêu',NULL,15,'MNU_02',NULL,NULL,'/gen/gen0101','Y'),('MNU_02_04','1663552264002','2022-09-19 10:20:25.494000',NULL,2,'프로젝트 관리','Project Management','Quản lý dự án',NULL,16,'MNU_02',NULL,NULL,'/gen/gen0501','Y'),('MNU_02_05','1663552264002','2022-09-19 11:16:35.432000',NULL,2,'Subscribe information','Subscribe information','Subscribe information',NULL,17,'MNU_02',NULL,NULL,'/gen/gen0601','N'),('MNU_03','1663552264002','2022-09-19 09:42:30.414000',NULL,1,'Business Management','Business Management','Quản lý nghiệp vụ',NULL,4,'',NULL,NULL,NULL,'Y'),('MNU_03_01','1663552264002','2022-09-19 10:07:53.946000',NULL,2,'Subscribe Management','Subscribe Management','Subscribe Management',NULL,10,'MNU_03',NULL,NULL,'/bus/bus0101','Y'),('MNU_03_02','1663552264002','2022-09-19 10:08:16.841000',NULL,2,'Event Management','Event Management','Quản lí sự kiện',NULL,12,'MNU_03',NULL,NULL,'/bus/bus0201','Y'),('MNU_04','1663552264002',NULL,'Hệ thống',1,'System Management','System Management','Hệ thống','',5,NULL,NULL,NULL,NULL,'Y'),('MNU_04_01','1663552264002',NULL,'Quản lý menu',2,'Menu Management','Menu Management','Quản lý menu','',1,'MNU_04',NULL,NULL,'/sys/sys0101','Y'),('MNU_04_02','1663552264002',NULL,'Quản lý người dùng',2,'User Management','User Management','Quản lý người dùng','',2,'MNU_04',NULL,NULL,'/sys/sys0103','Y'),('MNU_04_03','1663552264002',NULL,'Quản lý vai trò',2,'Role Management','Role Management','Quản lý vai trò','',3,'MNU_04',NULL,NULL,'/sys/sys0102','Y'),('MNU_04_04','1663552264002','2022-09-19 10:15:19.352000',NULL,2,'Comm Code Mangement','Comm Code Mangement','Comm Code Mangement',NULL,13,'MNU_04',NULL,NULL,'/sys/sys0104','Y'),('MNU_05','1663552264002','2022-09-19 09:08:43.000000',NULL,1,'Client Dashboard','Client Dashboard','Client Dashboard',NULL,2,NULL,NULL,NULL,'/client-dashboard','Y'),('MNU_06','1663552264002','2022-09-19 10:04:01.861000',NULL,1,'Sample','Sample','Sample',NULL,8,'',NULL,NULL,'','Y'),('MNU_06_01','1663552264002','2022-09-19 10:04:22.020000',NULL,2,'Form','Form','Form',NULL,9,'MNU_06',NULL,NULL,'/sam/sam0101','Y'),('MNU_06_02','1663552264002','2022-09-19 10:04:22.020000',NULL,2,'Table','Table','Table',NULL,9,'MNU_06',NULL,NULL,'/sam/sam0102','Y');
/*!40000 ALTER TABLE `TSST_MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TSST_ROLE`
--

DROP TABLE IF EXISTS `TSST_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TSST_ROLE` (
  `ROLE_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ROLE_NM` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `USE_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TSST_ROLE`
--

LOCK TABLES `TSST_ROLE` WRITE;
/*!40000 ALTER TABLE `TSST_ROLE` DISABLE KEYS */;
INSERT INTO `TSST_ROLE` VALUES ('R000',NULL,NULL,NULL,'R000',NULL,NULL,'Y'),('R001',NULL,NULL,NULL,'R001',NULL,NULL,'Y'),('R002',NULL,NULL,NULL,'R002',NULL,NULL,'Y'),('R013',NULL,NULL,NULL,'R013',NULL,NULL,'Y');
/*!40000 ALTER TABLE `TSST_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TSST_ROLE_MENU`
--

DROP TABLE IF EXISTS `TSST_ROLE_MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TSST_ROLE_MENU` (
  `MENU_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `ROLE_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `DEL_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXC_DN_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MNG_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MOD_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PNT_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `READ_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `WRT_YN` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`,`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TSST_ROLE_MENU`
--

LOCK TABLES `TSST_ROLE_MENU` WRITE;
/*!40000 ALTER TABLE `TSST_ROLE_MENU` DISABLE KEYS */;
INSERT INTO `TSST_ROLE_MENU` VALUES ('MNU_01','R000',NULL,'2022-09-19 09:13:30.000000','Y','Y','Y','Y','Y','Y','Y'),('MNU_02','R000',NULL,NULL,'Y','Y','Y','Y','Y','Y','Y'),('MNU_02_01','R000','20220919090926005','2022-09-19 10:08:40.372000','Y','Y','Y','Y','Y','Y','Y'),('MNU_02_02','R000','1663552264002','2022-09-19 10:19:19.425000','Y','Y','Y','Y','Y','Y','Y'),('MNU_02_03','R000','1663552264002','2022-09-19 10:22:27.264000','Y','Y','Y','Y','Y','Y','Y'),('MNU_02_04','R000','1663552264002','2022-09-19 10:22:27.264000','Y','Y','Y','Y','Y','Y','Y'),('MNU_02_05','R000','1663552264002','2022-09-19 11:17:14.653000','Y','Y','Y','Y','Y','Y','Y'),('MNU_03','R000',NULL,'2022-09-19 09:13:30.000000','Y','Y','Y','Y','Y','Y','Y'),('MNU_03_01','R000','1663552264002','2022-09-19 10:09:03.107000','Y','Y','Y','Y','Y','Y','Y'),('MNU_03_02','R000','1663552264002','2022-09-19 10:09:03.107000','Y','Y','Y','Y','Y','Y','Y'),('MNU_04','R000',NULL,NULL,'Y','Y','Y','Y','Y','Y','Y'),('MNU_04_01','R000',NULL,'2022-09-19 09:13:31.000000','Y','Y','Y','Y','Y','Y','Y'),('MNU_04_02','R000',NULL,'2022-09-19 09:13:32.000000','Y','Y','Y','Y','Y','Y','Y'),('MNU_04_03','R000',NULL,'2022-09-19 09:13:32.000000','Y','Y','Y','Y','Y','Y','Y'),('MNU_04_04','R000','1663552264002','2022-09-19 10:15:49.454000','Y','Y','Y','Y','Y','Y','Y'),('MNU_05','R000',NULL,NULL,'Y','Y','Y','Y','Y','Y','Y'),('MNU_06','R000','1663552264002','2022-09-19 10:05:49.079000','Y','Y','Y','Y','Y','Y','Y'),('MNU_06_01','R000','1663552264002','2022-09-19 10:05:49.079000','Y','Y','Y','Y','Y','Y','Y'),('MNU_06_02','R000','1663552264002',NULL,'Y','Y','Y','Y','Y','Y','Y');
/*!40000 ALTER TABLE `TSST_ROLE_MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TSST_USER`
--

DROP TABLE IF EXISTS `TSST_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TSST_USER` (
  `USER_UID` varchar(255) COLLATE utf8_bin NOT NULL,
  `CREATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_DATE` datetime(6) DEFAULT NULL,
  `PWD` varchar(255) COLLATE utf8_bin NOT NULL,
  `STATUS` bit(1) DEFAULT NULL,
  `UPDATED_BY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `UPDATED_DATE` datetime(6) DEFAULT NULL,
  `USER_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `USER_TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_INFO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`USER_UID`),
  UNIQUE KEY `UK_hgl1uyifr7okw89aisswog1t9` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TSST_USER`
--

LOCK TABLES `TSST_USER` WRITE;
/*!40000 ALTER TABLE `TSST_USER` DISABLE KEYS */;
INSERT INTO `TSST_USER` VALUES ('1663552264002','1663552264002','2022-09-19 08:51:06.000000','$2a$10$y0CpeQylobtKsz./zclXA.JtByiJOfUWzOZRnsm25ooa3O1BthcF2',_binary '','1663552264002','2022-09-19 10:50:59.880000','admin',NULL,1),('1663552386514','1663552264002','2022-09-19 08:53:08.000000','$2a$10$y0CpeQylobtKsz./zclXA.JtByiJOfUWzOZRnsm25ooa3O1BthcF2',_binary '','1663552264002','2022-09-19 10:51:06.684000','admin1',NULL,3),('1663552476258','1663552264002','2022-09-19 08:54:38.000000','$2a$10$y0CpeQylobtKsz./zclXA.JtByiJOfUWzOZRnsm25ooa3O1BthcF2',_binary '','1663552264002','2022-09-19 10:51:10.783000','trunganh',NULL,4),('1663552520618','1663552264002','2022-09-19 08:55:22.000000','$2a$10$QseNLWgE1Jna2uRQ/k5d/uAUu0cTyZ8Oekr53YpIV2RDqvd.C4L4m',_binary '','1663552264002','2022-09-19 10:51:14.960000','haunv',NULL,5),('1663555659494',NULL,'2022-09-19 09:48:19.000000','$2a$10$J2VJa3dLiy6Yg.HQ6lMmUONO2vZJx91agsf3GpkFpUXUxXbWK5kzu',_binary '','1663552264002','2022-09-19 13:17:25.850000','khangna',NULL,8),('1663557739437','1663552264002','2022-09-19 10:22:19.000000','$2a$10$y0CpeQylobtKsz./zclXA.JtByiJOfUWzOZRnsm25ooa3O1BthcF2',_binary '','1663552264002','2022-09-19 10:51:44.717000','anhnt',NULL,11),('20220919090926005','1663552264002','2022-09-19 09:24:58.588000','$2a$10$y0CpeQylobtKsz./zclXA.JtByiJOfUWzOZRnsm25ooa3O1BthcF2',_binary '','1663552264002','2022-09-19 10:52:28.933000','admin123',NULL,9),('20220919101035008','1663552264002','2022-09-19 10:12:17.732000','$2a$10$y0CpeQylobtKsz./zclXA.JtByiJOfUWzOZRnsm25ooa3O1BthcF2',_binary '','1663552264002','2022-09-19 10:52:36.508000','anhpv',NULL,10),('20220919150308025',NULL,'2022-09-19 15:15:08.000000','$2a$10$Nm3iO63XecI6I8bgx5XK7Oovb3kmeGsJhTsLNFxUvvjREeqIjhMf.',_binary '\0',NULL,NULL,'trunganh123',NULL,49),('20220919150324026',NULL,'2022-09-19 15:16:24.000000','$2a$10$3807edg9tnu10euiEGMoEOn9u.GmzTWuEs262EDqwFsvEDCqaIDMq',_binary '\0',NULL,NULL,'trunganhweyweh123',NULL,50),('20220919150348024',NULL,'2022-09-19 15:09:53.000000','$2a$10$y/Nyfj4FT2FumYJ4xgxBL.Plw10nKnNEO7bxPJ.XEKD8mIXVOZSHW',_binary '\0',NULL,NULL,'sdb',NULL,48);
/*!40000 ALTER TABLE `TSST_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TSST_USER_INFO`
--

DROP TABLE IF EXISTS `TSST_USER_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TSST_USER_INFO` (
  `USER_INFO_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CELL_PHONE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `DOB` timestamp NULL DEFAULT NULL,
  `EMAIL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_VERIFY_KEY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `FULL_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `GENDER` bit(1) DEFAULT NULL,
  `IMG_PATH` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ORGANIZATION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `POSITION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `2FA_ENABLE` bit(1) DEFAULT NULL,
  `2FA_KEY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`USER_INFO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TSST_USER_INFO`
--

LOCK TABLES `TSST_USER_INFO` WRITE;
/*!40000 ALTER TABLE `TSST_USER_INFO` DISABLE KEYS */;
INSERT INTO `TSST_USER_INFO` VALUES (1,'','','2000-01-01 17:00:00','trunganh691691@gmail.com','0PGPHD84RVVIICWKUA962EIBHCJOKB','Admin',_binary '',NULL,'VN','Admin',_binary '\0','IRSOTBKX5FSVGUUYG8JTSFB64C84HU'),(3,'','','2000-01-01 17:00:00','trunganh691691@gmail.com','2N9IVZ5QT2UZ2ZRWY18P3J6HJ8NWYV','Admin',_binary '',NULL,'VN','Admin',_binary '\0','5ANP2N9UPJY4FZ64AFIU7N5IR7I9UV'),(4,'','','2000-01-01 17:00:00','trunganh691691@gmail.com','7EFS67H8RQAIDHW7DVM16TBPFGMBXQ','Nguyễn Trung Anh',_binary '',NULL,'VN','Developer',_binary '\0','13EI8S1FYQM23G9KPI4HFWRDC8WPL4'),(5,'','','2000-01-01 17:00:00','trunganh691691@gmail.com','I9USDNCW2Z5HQHF9FZWNAGKAWL0C4O','Nguyễn Văn Hậu',_binary '',NULL,'VN','Developer',_binary '\0','898EL2KGMQH2MMVUQGMXBROY5BDJS0'),(7,'','','2000-01-01 17:00:00','trungganhh691@gmail.com','9NHE5CRZCOVBV7PC7ZKRJZ234G0RF1','Test',_binary '',NULL,'VN','Client',_binary '\0','6Z3H7HBF6YIDT27AYCTBIAZLZLO5JN'),(8,'sdsd','','2000-01-01 17:00:00','atwom.com.vn@gmail.com','GIVKOQNSYZB1JOSQXNW6X1E036QO1Y','khangna',_binary '',NULL,'sdsd','Client',_binary '\0','FD9YB2HACVZ6SG23B6P54KP2TID4RO'),(9,'','','2000-01-01 17:00:00','atwom.com.vn@gmail.com','8G8YAKPHQC27LRZU5BNQKCSC0YJ6FY','khangna',_binary '',NULL,'sdsd','PM',_binary '\0','KV0968G2QOHDK6QQ12TRWYYQS54OZC'),(10,'',NULL,'2000-01-01 17:00:00','anhpv315@gmail.com','1JL78OQQBCIMPTAGFMTPWJABWBX27U','PHAM VIET ANH',_binary '',NULL,NULL,'PM',_binary '\0','786D6VNQAHXT6XBAEKZFJNPBLMYPAI'),(11,'','','2000-01-01 17:00:00','trunganh691691@gmail.com','OM8DV7DYJBVH0BTJVJ53TP626B5PIN','Nguyễn Trung Anh',_binary '',NULL,'VN','Developer',_binary '\0','6WYEQ54AF9MOJPJ642L4X6XIK9B024'),(48,NULL,NULL,'2022-09-04 17:00:00','trungganhh691@gmail.com','9ZXCZINOBQO9Q5JCRNIA2VFDC076XX','AAAA',_binary '',NULL,'VN',NULL,NULL,'XWBJAL289CYWIBSNZXOU1W2H00BRMD'),(49,NULL,NULL,'2022-09-04 17:00:00','trungganhh691@gmail.com','TDZXMFQNMS8NE8XEX4MQH5PPV3CCBC','AAAAqwrqw',_binary '',NULL,'VN',NULL,NULL,'OZICGAOJQ8BDZZOPGWPOPKUKUAJW1H'),(50,NULL,NULL,'2022-09-04 17:00:00','trungganhh691@gmail.com','LFEYPAXXKBE80ABUS75KKOBVD3YAJW','AAAAqwrqwwe21',_binary '',NULL,'VN',NULL,NULL,'531BZSRRKCUI1FYHAB404JGGJTP3A6');
/*!40000 ALTER TABLE `TSST_USER_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TSST_USER_ROLE`
--

DROP TABLE IF EXISTS `TSST_USER_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TSST_USER_ROLE` (
  `ROLE_ID` varchar(255) COLLATE utf8_bin NOT NULL,
  `USER_UID` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ROLE_ID`,`USER_UID`),
  KEY `USER_ROLE_FK2` (`USER_UID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TSST_USER_ROLE`
--

LOCK TABLES `TSST_USER_ROLE` WRITE;
/*!40000 ALTER TABLE `TSST_USER_ROLE` DISABLE KEYS */;
INSERT INTO `TSST_USER_ROLE` VALUES ('R000','1663552264002'),('R000','20220919090926005'),('R000','20220919101035008'),('R002','1663552386514'),('R002','1663552476258'),('R002','1663552520618'),('R002','1663552774158'),('R013','20220919150308025'),('R013','20220919150324026');
/*!40000 ALTER TABLE `TSST_USER_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-19 15:15:52

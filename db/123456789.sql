CREATE DATABASE  IF NOT EXISTS `omnidb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `omnidb`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: omnidb
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `adm_app_setup`
--

DROP TABLE IF EXISTS `adm_app_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_app_setup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_delete` bit(1) NOT NULL,
  `sys_key` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `sys_value` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_app_setup`
--

LOCK TABLES `adm_app_setup` WRITE;
/*!40000 ALTER TABLE `adm_app_setup` DISABLE KEYS */;
INSERT INTO `adm_app_setup` VALUES (1,_binary '\0','VISIBILITY ASSETS TAX RATE (%)',_binary '','18','admin','2022-11-05 02:01:40','admin','2022-11-05 02:01:40'),(2,_binary '','OFF INVOICE MARGIN %',_binary '','18%','admin','2022-11-10 10:05:23','admin','2022-11-10 10:05:23'),(3,_binary '','RTV/NRTV',_binary '','5%','admin','2022-11-10 10:06:01','admin','2022-11-10 10:06:01'),(4,_binary '','SALES PROMOTION SUPPORT %',_binary '','10%','admin','2022-11-10 10:06:16','admin','2022-11-10 10:06:16'),(5,_binary '','NRTV EXPIRY ALLOWANCE',_binary '','3%','admin','2022-11-10 10:06:42','admin','2022-11-10 10:06:42'),(6,_binary '','DATA SHARING ALLOWANCE',_binary '','2%','admin','2022-11-10 10:06:56','admin','2022-11-10 10:06:56'),(7,_binary '','SALES TARGET INCENTIVE',_binary '','10%','admin','2022-11-10 10:07:10','admin','2022-11-10 10:07:10'),(8,_binary '','NSO LISTING FEES',_binary '','Rs. 5000 per store','admin','2022-11-10 10:07:40','admin','2022-11-10 10:07:40'),(9,_binary '','ULLAGE (DAMAGE)',_binary '','18%','admin','2022-11-10 10:08:05','admin','2022-11-10 10:08:57'),(10,_binary '','CENTRALISE DELIVERY DISCOUNT (CDD)',_binary '','18%','admin','2022-11-10 10:12:37','admin','2022-11-10 10:12:37');
/*!40000 ALTER TABLE `adm_app_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_approval_setup`
--

DROP TABLE IF EXISTS `adm_approval_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_approval_setup` (
  `approval_id` int NOT NULL AUTO_INCREMENT,
  `approv_ord` int NOT NULL,
  `menu_id` int NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `user_id` int NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`approval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_approval_setup`
--

LOCK TABLES `adm_approval_setup` WRITE;
/*!40000 ALTER TABLE `adm_approval_setup` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_approval_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_company`
--

DROP TABLE IF EXISTS `adm_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_company` (
  `company_id` int NOT NULL AUTO_INCREMENT,
  `addr1` varchar(255) NOT NULL,
  `addr2` varchar(255) DEFAULT NULL,
  `addr3` varchar(255) DEFAULT NULL,
  `cin_no` varchar(255) DEFAULT NULL,
  `countrycode` int DEFAULT NULL,
  `cst_no` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `fsa_no` varchar(255) DEFAULT NULL,
  `gst` varchar(255) DEFAULT NULL,
  `hd1` varchar(255) DEFAULT NULL,
  `hd2` varchar(255) DEFAULT NULL,
  `locationid` int DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `pan_no` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pin_code` int NOT NULL,
  `stateid` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `tin_no` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_company`
--

LOCK TABLES `adm_company` WRITE;
/*!40000 ALTER TABLE `adm_company` DISABLE KEYS */;
INSERT INTO `adm_company` VALUES (1,'4A Orbit Crystal','26B Alipore Road','Alipore','U52110WB2022PTC254028',103,NULL,'contactus@sumosave.in',NULL,NULL,'19ABICS5467K1ZL',NULL,NULL,1,'9836543858','SUMOSAVE RETAIL VENTURES PRIVATE LIMITED','ABICS5467K','',700027,37,_binary '',NULL,'www.sumosave.in','admin','2022-11-07 14:45:26','admin','2022-11-15 15:33:42');
/*!40000 ALTER TABLE `adm_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_company_doc`
--

DROP TABLE IF EXISTS `adm_company_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_company_doc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `document` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `ref_id` int DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `validity` varchar(255) DEFAULT NULL,
  `com_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK96wa58wgpl30sl2er8pav30al` (`com_id`),
  CONSTRAINT `FK96wa58wgpl30sl2er8pav30al` FOREIGN KEY (`com_id`) REFERENCES `adm_company` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_company_doc`
--

LOCK TABLES `adm_company_doc` WRITE;
/*!40000 ALTER TABLE `adm_company_doc` DISABLE KEYS */;
INSERT INTO `adm_company_doc` VALUES (1,'PAN',NULL,1,_binary '',NULL,1),(2,'GSTIN',NULL,2,_binary '',NULL,1),(3,'Certificate of Incorporation',NULL,3,_binary '',NULL,1),(4,'TAN',NULL,4,_binary '',NULL,1),(5,'e-AoA',NULL,5,_binary '',NULL,1),(6,'e-MoA',NULL,6,_binary '',NULL,1),(7,'Sumosave MSME Registration Certificate with annexure',NULL,7,_binary '\0',NULL,1),(8,'ESIC C-11',NULL,8,_binary '',NULL,1),(9,'PF Number',NULL,9,_binary '',NULL,1),(10,'Trade Licence',NULL,10,_binary '',NULL,1);
/*!40000 ALTER TABLE `adm_company_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_doc_dtl`
--

DROP TABLE IF EXISTS `adm_doc_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_doc_dtl` (
  `ref_id` int NOT NULL AUTO_INCREMENT,
  `document` varchar(255) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `doc_hdr_id` int DEFAULT NULL,
  PRIMARY KEY (`ref_id`),
  KEY `FKl4t04e3mjkifml9kc9ix10qgo` (`doc_hdr_id`),
  CONSTRAINT `FKl4t04e3mjkifml9kc9ix10qgo` FOREIGN KEY (`doc_hdr_id`) REFERENCES `adm_doc_hdr` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_doc_dtl`
--

LOCK TABLES `adm_doc_dtl` WRITE;
/*!40000 ALTER TABLE `adm_doc_dtl` DISABLE KEYS */;
INSERT INTO `adm_doc_dtl` VALUES (1,'PAN','C:/upload/document/e88ad7ec-212c-4df0-8032-50cff02144ef.pdf',_binary '',1),(2,'GSTIN','C:/upload/document/0f67c8d2-659e-4fb8-aae2-7b4f428109ab.pdf',_binary '',1),(3,'Certificate of Incorporation','C:/upload/document/1eec011e-4993-4350-a862-bbc3191efb39.pdf',_binary '',1),(4,'TAN','C:/upload/document/ff49b2d0-2ba2-4031-84e6-6012f76e1d22.pdf',_binary '',1),(5,'e-AoA','C:/upload/document/fb064442-cf54-46bf-b5ad-12f0d936bace.pdf',_binary '',1),(6,'e-MoA','C:/upload/document/7b9a263e-b01c-467c-aaff-8dd7f41fe87f.pdf',_binary '',1),(7,'Sumosave MSME Registration Certificate with annexure','C:/upload/document/7cd325e9-57c1-4b56-b712-ac7610294b3f.pdf',_binary '\0',1),(8,'ESIC C-11','C:/upload/document/a9e2c2da-a1aa-48da-b8cf-2b6c2ff47dff.pdf',_binary '',1),(9,'PF Number','C:/upload/document/11c0fc8d-0a21-498c-b7f8-10af19a370d2.pdf',_binary '',1),(10,'Trade Licence','',_binary '',1),(11,'FIre Licence','C:/upload/document/ae52c5e6-7348-4e14-a413-21dda3df2325.pdf',_binary '',3),(12,'FSSAI Licence','C:/upload/document/2ee4de8d-3101-44e6-8bb0-5d4f9a05701c.pdf',_binary '',3),(13,'Trade Licence','C:/upload/document/fde9a80c-23cb-4458-932e-eac8411f66ec.pdf',_binary '',3),(14,'Trade licence food (PART2)','C:/upload/document/0b3d8b1c-c7c2-4206-b822-84e864dd0798.pdf',_binary '\0',3),(15,'Legal Metrology','',_binary '',3),(16,'Insecticide Licence','',_binary '',3),(17,'Shop & Establishment','',_binary '',3),(18,'Shops & Establishment','',_binary '',4),(19,'FSSAI','',_binary '',4),(20,'Legal Metrology','',_binary '',4),(21,'Fire Licence','',_binary '',4),(22,'Trade Licence','',_binary '',4),(23,'Police NOC','',_binary '',4),(24,'Insecticide','',_binary '\0',4),(25,'VENDOR REGISTRATION FORM','',_binary '',6),(26,'PAN Card','',_binary '',6),(27,'TDS Number (TAN) Intimation','',_binary '',6),(28,'GST Certificate','',_binary '',6),(29,'Cancelled Cheque','',_binary '',6),(30,'MSME Certificate (If applicable)','',_binary '',6),(31,'TOT','',_binary '',5),(32,'VENDOR REGISTRATION FORM','',_binary '',5),(33,'PAN Card','',_binary '',5),(34,'TDS Number (TAN) Intimation','',_binary '',5),(35,'GST Certificate','',_binary '',5),(36,'Cancelled Cheque','',_binary '',5),(37,'MSME Certificate (If applicable)','',_binary '\0',5),(38,'FSSAI Licence','',_binary '',5),(39,'Shops & Establishment','',_binary '',2),(40,'Trade Licence','',_binary '',2),(41,'GSTIN','',_binary '',2),(42,'Fire Noc','',_binary '\0',2);
/*!40000 ALTER TABLE `adm_doc_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_doc_hdr`
--

DROP TABLE IF EXISTS `adm_doc_hdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_doc_hdr` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_doc_hdr`
--

LOCK TABLES `adm_doc_hdr` WRITE;
/*!40000 ALTER TABLE `adm_doc_hdr` DISABLE KEYS */;
INSERT INTO `adm_doc_hdr` VALUES (1,NULL,NULL,'admin','2022-11-09 15:44:28','Company','C',_binary ''),(2,NULL,NULL,'admin','2022-11-10 09:54:55','Office','O',_binary ''),(3,NULL,NULL,'admin','2022-11-10 09:55:11','Warehouse','W',_binary ''),(4,NULL,NULL,'admin','2022-11-10 09:56:24','Store','S',_binary ''),(5,NULL,NULL,'Mohit','2022-11-24 17:00:06','Trade Vendor','V',_binary ''),(6,NULL,NULL,'admin','2022-11-10 09:57:26','Non Trade Vendor','T',_binary '');
/*!40000 ALTER TABLE `adm_doc_hdr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_email_setup`
--

DROP TABLE IF EXISTS `adm_email_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_email_setup` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email_body1` varchar(255) NOT NULL,
  `email_body2` varchar(255) DEFAULT NULL,
  `email_approver` bit(1) NOT NULL,
  `email_hod` bit(1) NOT NULL,
  `email_to` varchar(255) DEFAULT NULL,
  `menu_id` int NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `email_sub` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_email_setup`
--

LOCK TABLES `adm_email_setup` WRITE;
/*!40000 ALTER TABLE `adm_email_setup` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_email_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_identification`
--

DROP TABLE IF EXISTS `adm_identification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_identification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_year_use` bit(1) NOT NULL,
  `menu_id` int NOT NULL,
  `no_of_length` varchar(255) DEFAULT NULL,
  `prefix` varchar(255) DEFAULT NULL,
  `separated_by` varchar(255) DEFAULT NULL,
  `sl_no` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `suffix` varchar(255) DEFAULT NULL,
  `trading_vendor` bit(1) DEFAULT NULL,
  `yr_cd` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_identification`
--

LOCK TABLES `adm_identification` WRITE;
/*!40000 ALTER TABLE `adm_identification` DISABLE KEYS */;
/*!40000 ALTER TABLE `adm_identification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_login_history`
--

DROP TABLE IF EXISTS `adm_login_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_login_history` (
  `loghist_id` int NOT NULL AUTO_INCREMENT,
  `login_dt_time` datetime DEFAULT NULL,
  `logout_dt_time` datetime DEFAULT NULL,
  `sessionid` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`loghist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_login_history`
--

LOCK TABLES `adm_login_history` WRITE;
/*!40000 ALTER TABLE `adm_login_history` DISABLE KEYS */;
INSERT INTO `adm_login_history` VALUES (1,'2022-11-05 17:48:02','2022-11-05 17:51:15','B3B4355393F784D04F565E973DA8C868','admin'),(2,'2022-11-05 17:55:08','2022-11-05 17:55:30','74768CAC70AF53292A2DDD228C8E1901','admin'),(3,'2022-11-05 18:03:14','2022-11-05 18:03:25','2E722B86E60C6564D6BDA13A36607E84','admin'),(4,'2022-11-05 18:03:48','2022-11-05 18:05:27','DD11C744A378CED32F0B1BAC7787F2DE','admin'),(5,'2022-11-06 21:24:03','2022-11-06 21:57:49','7723953D6C8A29F762EC56E7C1895123','admin'),(6,'2022-11-07 10:16:47','2022-11-07 10:47:57','21078412D14FBAC3DBBA676B8453F651','admin'),(7,'2022-11-07 14:38:46','2022-11-07 15:21:22','BD681CD46EFD6BF2C94F0508517379BE','admin'),(8,'2022-11-07 15:37:03','2022-11-07 15:37:50','F56F4DFB88DDBCE00989F60A1B63F3F2','admin'),(9,'2022-11-08 09:45:42','2022-11-08 10:21:05','11AB5E88F09173793850F0D276DA3146','admin'),(10,'2022-11-08 10:14:26','2022-11-08 10:45:08','613CEB8C8E7EEE2D5BE2B7B4B84ADF68','admin'),(11,'2022-11-08 12:27:01','2022-11-08 12:27:46','3832C4AAA2BD579E6B35B69321E8FA11','admin'),(12,'2022-11-09 10:39:28','2022-11-09 10:39:46','58D148A7E9DA9F3AD4A5B00D46C11895','admin'),(13,'2022-11-09 15:08:42','2022-11-09 16:34:11','1A557826031F54C6A2C209ADAE5A45C8','admin'),(14,'2022-11-10 09:46:56','2022-11-10 12:34:26','0B44E14702CB6B5CB8A775A64BA9429A','admin'),(15,'2022-11-10 12:12:23','2022-11-10 12:58:28','FBDFA35646C4C8B7FD8A50CAFB93928E','admin'),(16,'2022-11-10 14:16:16','2022-11-10 15:02:44','6649163A26B298CCCC0088FA7F1CCB3D','admin'),(17,'2022-11-11 11:47:22','2022-11-11 11:53:22','77295A9EC1FB233471B2DE4616147256','admin'),(18,'2022-11-11 11:54:04','2022-11-11 11:56:35','1EA9D55D5A9CB9EE298A2D57B198CCDC','michael'),(19,'2022-11-11 11:56:38','2022-11-11 11:56:40','EFF789F724970A5353BEAB6559BA1BA7','michael'),(20,'2022-11-11 13:37:46','2022-11-11 13:44:30','1CCE36B3907E6ACC1E6FEE7A92FD6F13','admin'),(21,'2022-11-11 13:54:16','2022-11-11 14:25:12','087992B9C51FDCC703C6AE05DFF39449','admin'),(22,'2022-11-11 16:19:36','2022-11-11 16:39:33','075C84070A05C302CA78893EAF461F05','admin'),(23,'2022-11-12 00:31:57','2022-11-12 01:02:53','9BD50C38276148C33A78A6CB5DC7B440','michael'),(24,'2022-11-13 01:39:21','2022-11-13 02:11:17','C0190886E9C794D5E978DF12CC822DB1','michael'),(25,'2022-11-13 02:40:00','2022-11-13 03:10:22','F045D5195F3878A637FE81886DF1D88B','michael'),(26,'2022-11-14 09:29:18','2022-11-14 10:00:06','FA67818F95A78AC30D053360A4660B0B','admin'),(27,'2022-11-14 10:03:11','2022-11-14 10:37:09','853408B5775467530497C8BAD486A004','admin'),(28,'2022-11-14 10:30:09','2022-11-14 11:24:52','2316D576AE16648B2F4147516F4C7545','admin'),(29,'2022-11-14 11:27:10','2022-11-14 12:17:18','962727ACE5ACA640B31FB12C15161397','admin'),(30,'2022-11-14 11:41:07','2022-11-14 12:11:18','69F12F99544E876A1C97CF110B8E0D0B','admin'),(31,'2022-11-14 11:42:55','2022-11-14 13:04:22','616DF38D9EC88A610E103C7909B4A38D','admin'),(32,'2022-11-14 12:21:26','2022-11-14 13:09:23','9AFC1F43A05E07140162C1E56DA7C293','admin'),(33,'2022-11-15 11:56:03','2022-11-15 12:26:25','9FE63A7ACEE1521C59A45877CBF3D096','admin'),(34,'2022-11-15 12:44:34','2022-11-15 13:35:31','594D368752855A982DCE26D8D4AEFB41','admin'),(35,'2022-11-15 12:45:56','2022-11-15 13:16:29','F845BE66D257D52A4D3C80281C868FD6','admin'),(36,'2022-11-15 15:15:04',NULL,'1AF3B59095A804561CC982247456E6A7','admin'),(37,'2022-11-15 15:33:21','2022-11-15 16:06:07','7461AF07899969BAE72D64C6F7738918','admin'),(38,'2022-11-15 16:10:30','2022-11-15 16:41:11','DDFCCC1747955DE1013EBE9338951C60','admin'),(39,'2022-11-15 16:36:34','2022-11-15 18:53:11','37882817EDC9B9862702375236D34523','admin'),(40,'2022-11-15 18:15:08','2022-11-15 18:46:21','BEE71B24A9952481A64C97482036A896','admin'),(41,'2022-11-16 10:13:19','2022-11-16 12:08:53','18D115909FD5BEC9F6B5634A50C16097','admin'),(42,'2022-11-16 10:44:27','2022-11-16 11:16:49','A13F667F9BB10BA50470778EFFC488D2','michael'),(43,'2022-11-16 11:32:15','2022-11-16 11:32:43','73CABE9D2C837F9B8D54447077F056B6','admin'),(44,'2022-11-16 12:18:54','2022-11-16 12:52:57','9E8D5E5476B27ED48F845B97D3865F7F','admin'),(45,'2022-11-16 14:01:26','2022-11-16 16:04:15','498A5C1ED704C6B1B75A1947AB895B7F','admin'),(46,'2022-11-16 14:30:47','2022-11-16 15:03:09','7B670F187339E308F2A784E4397B2BB8','admin'),(47,'2022-11-16 16:03:04','2022-11-16 16:04:11','A40D894FDCD78C81A0F21FAE67AF2F3B','admin'),(48,'2022-11-16 16:04:08','2022-11-16 16:43:18','876E11A16AD59D5EF83398F507A5CA6F','admin'),(49,'2022-11-16 16:59:46',NULL,'3381B968D48DEACD568D9E60BB115C5D','admin'),(50,'2022-11-16 17:07:32','2022-11-16 18:57:31','725BC55CC854E77EB87F8FFA567373D6','admin'),(51,'2022-11-16 17:08:32','2022-11-16 17:50:24','37094D3DED63E3C0D348699D39BC5AB6','admin'),(52,'2022-11-16 17:19:44','2022-11-16 19:00:31','2EE7C5170ED960247BD7DB640FB393AF','admin'),(53,'2022-11-16 17:19:53','2022-11-16 18:11:26','CBF9F6704B104D9CA98A41F4B643D69A','admin'),(54,'2022-11-16 17:19:55','2022-11-16 18:36:29','87C2A023B97A6563554DE6963BB52CF1','admin'),(55,'2022-11-16 17:25:44','2022-11-16 18:10:26','686240234EDAABF7035F2C8BA2E5DC96','admin'),(56,'2022-11-17 10:08:50','2022-11-17 11:49:00','CB9EA7B5C115AE0320AD9343C516AF2B','admin'),(57,'2022-11-17 10:35:36','2022-11-17 11:15:57','9024A8AE666EEEAC213C297A8317AC68','admin'),(58,'2022-11-17 12:03:52','2022-11-17 13:13:08','E71C206FAE9E0429923DBFCEEB6FF5DD','admin'),(59,'2022-11-17 14:50:38','2022-11-17 15:53:23','DE75269A511E21F359B56CCD41B7A66B','admin'),(60,'2022-11-18 12:17:49','2022-11-18 12:48:15','6F867A5BFE1BAC64A3F4FEAA9DF78A60','admin'),(61,'2022-11-18 13:07:37','2022-11-18 13:39:20','DA5BBEAE999D5ADEBC42B2F59525BA9E','admin'),(62,'2022-11-18 13:54:25',NULL,'8337F9B932C6ECF02485F112246629B3','admin'),(63,'2022-11-18 14:18:10','2022-11-18 16:21:35','F56AE5E4181C5344D18DAF42B72DEB13','admin'),(64,'2022-11-18 15:17:09',NULL,'5337C963F5AD2381A51F35587D0B2D59','admin'),(65,'2022-11-18 15:46:49',NULL,'56C68C0D6CA9B058D3F6BA4DA5712001','admin'),(66,'2022-11-18 16:21:34',NULL,'53B1AA313429D9AB19316DEF37ADCC24','admin'),(67,'2022-11-18 16:46:09','2022-11-18 17:17:40','69AB440F3C91665493891F2E4558A3F1','admin'),(68,'2022-11-18 16:57:10','2022-11-18 18:17:46','9CF2A635A8F8F874F837F276F9935445','admin'),(69,'2022-11-19 16:21:15','2022-11-19 16:59:47','04995F067A82EA460BEA37BF322F460F','admin'),(70,'2022-11-19 16:29:16','2022-11-19 16:59:47','52FF790196717DE57E1B379CAD6E3CC6','admin'),(71,'2022-11-19 17:44:56','2022-11-19 18:21:55','089D81E530E382C8DF24D886F363873B','admin'),(72,'2022-11-19 17:47:19','2022-11-19 18:20:55','F34273804D8ABCC40F46A65483674799','admin'),(73,'2022-11-19 19:34:37','2022-11-19 20:08:04','4A0C2B216DA9D724A097E15F802004F9','admin'),(74,'2022-11-21 11:47:25','2022-11-21 12:25:40','4149EB0001C8EA7C818A89E006F72624','admin'),(75,'2022-11-21 12:43:02','2022-11-21 13:25:45','E1D1D8D670791418B9F4777CF5F49F71','admin'),(76,'2022-11-21 13:02:46','2022-11-21 13:43:47','F13310951EF1FA48F439E234E7ABB790','admin'),(77,'2022-11-21 14:18:14','2022-11-21 14:48:53','E941F0CA45D5FD7D561A25BF2BCD4229','admin'),(78,'2022-11-21 15:28:06','2022-11-21 16:25:02','96DA8BA9F0B0F4039AC31D52606B352B','admin'),(79,'2022-11-21 17:04:24','2022-11-21 18:45:14','7E06943EDBB804E75A6E3EBA7C8E22EE','admin'),(80,'2022-11-22 10:31:39',NULL,'4A8B87776A2CDAB2AAB94DD21841BCA3','Mohit'),(81,'2022-11-22 10:58:27','2022-11-22 12:12:44','7F7372B5D17F4B254F3AD2F7B23ECED5','Mohit'),(82,'2022-11-22 12:23:41','2022-11-22 12:31:40','C6FA475A818E24E7879FE81D5544AA30','Mohit'),(83,'2022-11-22 13:01:19','2022-11-22 13:35:51','185EE5FA50903020713D3B5AE35200BF','Mohit'),(84,'2022-11-23 09:09:46','2022-11-23 09:52:36','1827C4DD214A275B2331B6A36F067398','Mohit'),(85,'2022-11-24 13:26:10','2022-11-24 13:26:18','5A29E01F2CF529928C3AD2818EE9EA06','admin'),(86,'2022-11-24 13:27:38','2022-11-24 14:06:04','288824F8953A93F48324DA490A7B608D','admin'),(87,'2022-11-24 13:27:45','2022-11-24 13:28:02','4A4F6FCC6AC158E27B00F78F98E8F8E4','admin'),(88,'2022-11-24 14:39:58','2022-11-24 15:10:10','679749A4C0DEB0DB78E2D7C653D347F3','admin'),(89,'2022-11-24 15:28:35','2022-11-24 16:01:15','89CC49728F886D7DD624F37F003D5872','admin'),(90,'2022-11-24 16:04:23','2022-11-24 16:35:18','4CD860EDDD04B57ACBA659405E7BFF3C','admin'),(91,'2022-11-24 16:27:15','2022-11-24 16:35:08','A8321AD48A8EDF12742BC7738C6F5E86','admin'),(92,'2022-11-24 16:35:26','2022-11-24 17:40:23','785EA73594DF6278CE712D5D167FA0AA','Mohit'),(93,'2022-11-25 12:24:00','2022-11-25 13:01:10','BD82EB3A13F0DDB3D4E02B405A6E877A','admin'),(94,'2022-11-25 13:15:39','2022-11-25 13:52:15','883EAE78826CEE6CE632596EEAC7D3F6','admin'),(95,'2022-11-25 16:04:25','2022-11-25 16:35:30','2FBB342AA6E10A7254F4F27EB1208ACB','admin'),(96,'2022-11-25 23:14:37','2022-11-25 23:45:09','E9F6D8DA2286A70DA0B0BF8EE46CB330','admin'),(97,'2022-11-26 09:49:08','2022-11-26 10:20:06','15E932554E8049870F107A8301321ECE','admin'),(98,'2022-11-26 11:09:28','2022-11-26 11:40:13','57C8C4A1CA477A831185735E0EE6E8EC','admin'),(99,'2022-11-26 15:38:24','2022-11-26 16:08:38','CDA30D8D97799D6232890E65031AD461','admin'),(100,'2022-11-26 17:47:21','2022-11-26 17:48:00','FC6FE0DDC597054045F103EBCC860981','admin'),(101,'2022-11-26 17:49:45','2022-11-26 17:49:56','B38F8C56736D954A33FFDED5F0FCC2B0','admin'),(102,'2022-11-26 18:00:02',NULL,'47F01102C06E6A4E918F79E9798A0009','admin');
/*!40000 ALTER TABLE `adm_login_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_tran_log`
--

DROP TABLE IF EXISTS `adm_tran_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_tran_log` (
  `tranlog_id` int NOT NULL AUTO_INCREMENT,
  `activity` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`tranlog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_tran_log`
--

LOCK TABLES `adm_tran_log` WRITE;
/*!40000 ALTER TABLE `adm_tran_log` DISABLE KEYS */;
INSERT INTO `adm_tran_log` VALUES (1,'SUMOSAVE RETAIL VENTURES PRIVATE LIMITED New Company details save Successfully','2022-11-07 14:45:26','admin'),(2,'Year details added','2022-11-07 14:47:53','admin'),(3,'Year details added','2022-11-07 14:48:23','admin'),(4,'SUMOSAVE RETAIL VENTURES PRIVATE LIMITED New Company details save Successfully','2022-11-09 15:25:50','admin'),(5,'KOLKATA 1 Cluster Saved','2022-11-10 11:30:54','admin'),(6,'CASH Paymentmode Saved','2022-11-10 11:31:46','admin'),(7,'CREDIT CARD Paymentmode Saved','2022-11-10 11:31:58','admin'),(8,'UPI Paymentmode Saved','2022-11-10 11:32:07','admin'),(9,'Assets type Added','2022-11-10 11:35:04','admin'),(10,'Reason Added','2022-11-10 11:37:36','admin'),(11,'Reason Added','2022-11-10 11:37:57','admin'),(12,'Reason Added','2022-11-10 11:38:37','admin'),(13,'Michael User Created','2022-11-11 11:50:53','admin'),(14,'SUMOSAVE RETAIL VENTURES PRIVATE LIMITED New Company details save Successfully','2022-11-14 09:30:01','admin'),(15,'Assets type Added','2022-11-14 10:54:01','admin'),(16,'Assets type Added','2022-11-14 10:54:27','admin'),(17,'Assets type Added','2022-11-14 10:55:13','admin'),(18,'Assets type Added','2022-11-14 10:55:52','admin'),(19,'Assets type Added','2022-11-14 10:56:32','admin'),(20,'Assets type Added','2022-11-14 10:56:52','admin'),(21,'Assets type Added','2022-11-14 10:57:33','admin'),(22,'Assets type Added','2022-11-14 10:57:59','admin'),(23,'Assets type Added','2022-11-14 10:58:14','admin'),(24,'Assets type Added','2022-11-14 10:58:29','admin'),(25,'Assets type Added','2022-11-14 10:58:57','admin'),(26,'Assets type Added','2022-11-14 10:59:14','admin'),(27,'Assets type Added','2022-11-14 11:01:30','admin'),(28,'Assets type Added','2022-11-14 11:03:22','admin'),(29,'Assets type Added','2022-11-14 11:04:30','admin'),(30,'Assets type updated','2022-11-14 11:05:17','admin'),(31,'Assets type Added','2022-11-14 11:06:29','admin'),(32,'Assets type Added','2022-11-14 11:06:53','admin'),(33,'Assets type Added','2022-11-14 11:07:28','admin'),(34,'Assets type Added','2022-11-14 11:08:19','admin'),(35,'Assets type updated','2022-11-14 11:08:39','admin'),(36,'Assets type updated','2022-11-14 11:09:10','admin'),(37,'Assets type updated','2022-11-14 11:09:26','admin'),(38,'Assets type updated','2022-11-14 11:09:57','admin'),(39,'Assets type updated','2022-11-14 11:10:20','admin'),(40,'Assets type Added','2022-11-14 11:11:24','admin'),(41,'Assets type updated','2022-11-14 11:11:46','admin'),(42,'Assets type updated','2022-11-14 11:12:00','admin'),(43,'Assets type updated','2022-11-14 11:12:14','admin'),(44,'Assets type updated','2022-11-14 11:12:55','admin'),(45,'Assets type Added','2022-11-14 11:13:54','admin'),(46,'Assets type Added','2022-11-14 11:14:33','admin'),(47,'Assets type Added','2022-11-14 11:15:07','admin'),(48,'Assets type Added','2022-11-14 11:15:52','admin'),(49,'Assets type Added','2022-11-14 11:16:30','admin'),(50,'Assets type Added','2022-11-14 11:17:20','admin'),(51,'Assets type Added','2022-11-14 11:17:57','admin'),(52,'Assets type Added','2022-11-14 11:18:29','admin'),(53,'Assets type Added','2022-11-14 11:19:44','admin'),(54,'Assets type Added','2022-11-14 11:20:19','admin'),(55,'Assets type Added','2022-11-14 11:20:44','admin'),(56,'Assets type Added','2022-11-14 11:21:37','admin'),(57,'Assets type Added','2022-11-14 11:22:11','admin'),(58,'SUMOSAVE RETAIL VENTURES PRIVATE LIMITED New Company details save Successfully','2022-11-15 15:33:42','admin'),(59,'LAKMEBrand Saved','2022-11-15 17:07:42','admin'),(60,'KG UOM added','2022-11-15 17:13:12','admin'),(61,'EA UOM added','2022-11-15 17:13:30','admin'),(62,'PC UOM updated','2022-11-15 17:14:22','admin'),(63,'PCS UOM updated','2022-11-15 17:14:35','admin'),(64,'Sent Email for vendor registration in sanjeev@regencysnacks.com','2022-11-15 17:20:12','admin'),(65,'BANK TRANSFER Paymentmode Saved','2022-11-16 14:34:13','admin'),(66,'New ROLE_BUYER added','2022-11-21 17:34:09','admin'),(67,'Mohit Kampani User Created','2022-11-21 17:50:23','admin'),(68,'Mohit Kampani User details Updated','2022-11-21 17:51:48','admin'),(69,'Michael User Account Suspended','2022-11-21 17:55:22','admin'),(70,'admin User Account Suspended','2022-11-21 17:55:28','admin'),(71,'New MUMBAI Location Added','2022-11-24 16:42:01','Mohit'),(72,'New KOLKATA Location Updated','2022-11-24 17:08:56','Mohit'),(73,'New KOLKATA Location Updated','2022-11-24 17:09:29','Mohit'),(74,'admin User details Updated','2022-11-26 17:47:46','admin'),(75,'admin User details Updated','2022-11-26 17:47:55','admin');
/*!40000 ALTER TABLE `adm_tran_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `adm_year_setup`
--

DROP TABLE IF EXISTS `adm_year_setup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adm_year_setup` (
  `year_id` int NOT NULL AUTO_INCREMENT,
  `status` bit(1) DEFAULT NULL,
  `end_date` varchar(255) NOT NULL,
  `start_date` varchar(255) NOT NULL,
  `year` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`year_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_year_setup`
--

LOCK TABLES `adm_year_setup` WRITE;
/*!40000 ALTER TABLE `adm_year_setup` DISABLE KEYS */;
INSERT INTO `adm_year_setup` VALUES (1,_binary '','2023-03-31','2022-04-01','2022-23','admin','2022-11-07 14:47:53','admin','2022-11-07 14:47:53'),(2,_binary '','2024-03-31','2023-04-01','2023-24','admin','2022-11-07 14:48:23','admin','2022-11-07 14:48:23');
/*!40000 ALTER TABLE `adm_year_setup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_cluster_mapping`
--

DROP TABLE IF EXISTS `article_cluster_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_cluster_mapping` (
  `cluster_id` int NOT NULL,
  `article_id` int NOT NULL,
  KEY `FKf7ophdohdptkhwdpyl6qd23do` (`article_id`),
  KEY `FK4syh9njcoi4gnx2ggry9ckqgk` (`cluster_id`),
  CONSTRAINT `FK4syh9njcoi4gnx2ggry9ckqgk` FOREIGN KEY (`cluster_id`) REFERENCES `mst_cluster` (`id`),
  CONSTRAINT `FKf7ophdohdptkhwdpyl6qd23do` FOREIGN KEY (`article_id`) REFERENCES `pro_article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_cluster_mapping`
--

LOCK TABLES `article_cluster_mapping` WRITE;
/*!40000 ALTER TABLE `article_cluster_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_cluster_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_booking_dtl`
--

DROP TABLE IF EXISTS `asset_booking_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_booking_dtl` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `article_id` int NOT NULL,
  `primary_doc` varchar(255) DEFAULT NULL,
  `secondary_doc` varchar(255) DEFAULT NULL,
  `asset_booking_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ju75s111henwit9uoorwn9w1` (`asset_booking_id`),
  CONSTRAINT `FK8ju75s111henwit9uoorwn9w1` FOREIGN KEY (`asset_booking_id`) REFERENCES `asset_booking_hdr` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_booking_dtl`
--

LOCK TABLES `asset_booking_dtl` WRITE;
/*!40000 ALTER TABLE `asset_booking_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_booking_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_booking_hdr`
--

DROP TABLE IF EXISTS `asset_booking_hdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_booking_hdr` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `asset_reg_id` int NOT NULL,
  `discount` double DEFAULT NULL,
  `end_mth` varchar(255) DEFAULT NULL,
  `narration` varchar(255) DEFAULT NULL,
  `no_mth` int NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `strt_mth` varchar(255) NOT NULL,
  `total_amount` double DEFAULT NULL,
  `vendor_id` int NOT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_booking_hdr`
--

LOCK TABLES `asset_booking_hdr` WRITE;
/*!40000 ALTER TABLE `asset_booking_hdr` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_booking_hdr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_execution`
--

DROP TABLE IF EXISTS `asset_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_execution` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `assetbooking_id` int NOT NULL,
  `execution_file` varchar(255) DEFAULT NULL,
  `narration` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `vendor_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_execution`
--

LOCK TABLES `asset_execution` WRITE;
/*!40000 ALTER TABLE `asset_execution` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asset_reg`
--

DROP TABLE IF EXISTS `asset_reg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset_reg` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `asset_no` varchar(255) NOT NULL,
  `assets_type_id` int NOT NULL,
  `cost` double DEFAULT NULL,
  `gst` double DEFAULT NULL,
  `maxsf` int NOT NULL,
  `narration` varchar(255) DEFAULT NULL,
  `outlet_id` int NOT NULL,
  `product_family` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `visibility_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset_reg`
--

LOCK TABLES `asset_reg` WRITE;
/*!40000 ALTER TABLE `asset_reg` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset_reg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_promo`
--

DROP TABLE IF EXISTS `gen_promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_promo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `end_date` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `percentage` double DEFAULT NULL,
  `start_date` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_promo`
--

LOCK TABLES `gen_promo` WRITE;
/*!40000 ALTER TABLE `gen_promo` DISABLE KEYS */;
/*!40000 ALTER TABLE `gen_promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn_dtl`
--

DROP TABLE IF EXISTS `grn_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn_dtl` (
  `grn_dtl_id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `article_id` int DEFAULT NULL,
  `basic_amt` double DEFAULT NULL,
  `cgst_amt` double DEFAULT NULL,
  `cp` double DEFAULT NULL,
  `ean_cd` varchar(255) DEFAULT NULL,
  `hsn_cd` varchar(255) DEFAULT NULL,
  `igst_amt` double DEFAULT NULL,
  `mrp` double DEFAULT NULL,
  `net_amt` double DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `recd_qty` double DEFAULT NULL,
  `sgst_amt` double DEFAULT NULL,
  `tax_per` double DEFAULT NULL,
  `uom_id` int DEFAULT NULL,
  `grn_hdr_id` int DEFAULT NULL,
  PRIMARY KEY (`grn_dtl_id`),
  KEY `FK7ihpj9lhmuf1vyg3ayewoak33` (`grn_hdr_id`),
  CONSTRAINT `FK7ihpj9lhmuf1vyg3ayewoak33` FOREIGN KEY (`grn_hdr_id`) REFERENCES `grn_hdr` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn_dtl`
--

LOCK TABLES `grn_dtl` WRITE;
/*!40000 ALTER TABLE `grn_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grn_hdr`
--

DROP TABLE IF EXISTS `grn_hdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grn_hdr` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `bill_date` varchar(255) DEFAULT NULL,
  `bill_no` varchar(255) DEFAULT NULL,
  `carrier_name` varchar(255) DEFAULT NULL,
  `grn_date` varchar(255) DEFAULT NULL,
  `grn_num` varchar(255) DEFAULT NULL,
  `outlet_id` int DEFAULT NULL,
  `po_id` int DEFAULT NULL,
  `road_permit_no` varchar(255) DEFAULT NULL,
  `shipment_no` varchar(255) DEFAULT NULL,
  `tot_basic_amt` double DEFAULT NULL,
  `tot_cgst_amt` double DEFAULT NULL,
  `tot_igst_amt` double DEFAULT NULL,
  `tot_net_amt` double DEFAULT NULL,
  `tot_sgst_amt` double DEFAULT NULL,
  `trans_doc_date` varchar(255) DEFAULT NULL,
  `trans_doc_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grn_hdr`
--

LOCK TABLES `grn_hdr` WRITE;
/*!40000 ALTER TABLE `grn_hdr` DISABLE KEYS */;
/*!40000 ALTER TABLE `grn_hdr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_assetstype`
--

DROP TABLE IF EXISTS `mst_assetstype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_assetstype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` bit(1) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `typeid` varchar(30) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_assetstype`
--

LOCK TABLES `mst_assetstype` WRITE;
/*!40000 ALTER TABLE `mst_assetstype` DISABLE KEYS */;
INSERT INTO `mst_assetstype` VALUES (1,_binary '','4 POLE WALL UNIT - MAIN UNIT (1200 WIDE)','WALL UNIT','admin','2022-11-10 11:35:04','admin','2022-11-10 11:35:04'),(2,_binary '','4 POLE WALL UNIT - ADD ON UNIT (1200MMWIDE)','WALL UNIT','admin','2022-11-14 10:54:01','admin','2022-11-14 10:54:01'),(3,_binary '','4 POLE WALL UNIT - MAIN UNIT (900MMWIDE)','WALL UNIT','admin','2022-11-14 10:54:27','admin','2022-11-14 10:54:27'),(4,_binary '','4 POLE TWO SIDE GONDOLA UNIT - MAIN UNIT (1200MMWIDE)','G - UNIT','admin','2022-11-14 10:55:13','admin','2022-11-14 10:55:13'),(5,_binary '','4 POLE TWO SIDE GONDOLA UNIT - ADD ON UNIT (1200MM WIDE)','G - UNIT','admin','2022-11-14 10:55:52','admin','2022-11-14 10:55:52'),(6,_binary '','4 POLE TWO SIDE GONDOLA UNIT - MAIN UNIT (1200 WIDE)','G - UNIT','admin','2022-11-14 10:56:32','admin','2022-11-14 10:56:32'),(7,_binary '','4 POLE TWO SIDE GONDOLA UNIT - ADD ON UNIT(1200 WIDE)','G - UNIT','admin','2022-11-14 10:56:52','admin','2022-11-14 10:56:52'),(8,_binary '','4 POLE TWO SIDE GONDOLA UNIT - MAIN UNIT (900 WIDE)','G - UNIT','admin','2022-11-14 10:57:33','admin','2022-11-14 10:57:33'),(9,_binary '','4 POLE COMPACT END CAP UNIT - MAIN UNIT(1200WIDE)','END CAP','admin','2022-11-14 10:57:59','admin','2022-11-14 10:57:59'),(10,_binary '','4 POLE COMPACT END CAP UNIT - MAIN UNIT(900WIDE)','END CAP','admin','2022-11-14 10:58:14','admin','2022-11-14 10:58:14'),(11,_binary '','4 POLE COMPACT END CAP UNIT - MAIN UNIT(600WIDE)','END CAP','admin','2022-11-14 10:58:29','admin','2022-11-14 10:58:29'),(12,_binary '','4 POLE ONE SIDE GANDOLA UNIT - MAIN UNIT ( 1200 WIDE )','G - UNIT','admin','2022-11-14 10:58:57','admin','2022-11-14 10:58:57'),(13,_binary '','4 POLE ONE SIDE GANDOLA UNIT - ADD ON UNIT ( 1200 WIDE )','G - UNIT','admin','2022-11-14 10:59:14','admin','2022-11-14 10:59:14'),(14,_binary '','FLAT LOAD BAR WIDTH -1250MM','FLAT LOAD','admin','2022-11-14 11:01:30','admin','2022-11-14 11:05:17'),(15,_binary '','DOUBLE LOOP HOOK WITH PEG HOLDER','HOOK','admin','2022-11-14 11:03:22','admin','2022-11-14 11:03:22'),(16,_binary '','FRONT GRID STOPPER W-1200MM H-50MM','STOPPER','admin','2022-11-14 11:04:30','admin','2022-11-14 11:08:39'),(17,_binary '','FRONT GRID STOPPER W-900MM H-50MM','STOPPER','admin','2022-11-14 11:06:29','admin','2022-11-14 11:11:46'),(18,_binary '','FRONT GRID STOPPER W- 600MM H-50MM','STOPPER','admin','2022-11-14 11:06:53','admin','2022-11-14 11:12:00'),(19,_binary '','SIDE CAP WITH ALL ACCESSORIES W-350MM D-175MM H-1500MM','SIDE CAP','admin','2022-11-14 11:07:28','admin','2022-11-14 11:12:14'),(20,_binary '','F&V MAIN RACK WITH SIGNAGE W-1225MM D-1099MM H-2100MM','F&V RACK','admin','2022-11-14 11:08:19','admin','2022-11-14 11:12:55'),(21,_binary '','F&V HALF RACK WITH SIGNAGE W-600MM D-1099MM H-2100MM','F&V RACK','admin','2022-11-14 11:11:24','admin','2022-11-14 11:11:24'),(22,_binary '','PAPER BAG HOLDER – TYPE A FOR F&V STAND W-250 D-200 H-300','BAG HOLDER','admin','2022-11-14 11:13:53','admin','2022-11-14 11:13:53'),(23,_binary '','PAPER BAG HOLDER – TYPE D FOR HEAPER W-400 D-200 H-300','BAG HOLDER','admin','2022-11-14 11:14:33','admin','2022-11-14 11:14:33'),(24,_binary '','PAPER BAG HOLDER – TYPE E FOR STAPLE BINS W-450 D-200 H-1020','BAG HOLDER','admin','2022-11-14 11:15:07','admin','2022-11-14 11:15:07'),(25,_binary '','PLASTIC BAG ON ROLL – TYPE A FOR STAPLE BINS 400DIA H-1250','BAG ONROLL','admin','2022-11-14 11:15:52','admin','2022-11-14 11:15:52'),(26,_binary '','PLASTIC BAG ON ROLLS – TYPE B FOR F&V HEAPERS 250DIA H-318','BAG ONROLL','admin','2022-11-14 11:16:30','admin','2022-11-14 11:16:30'),(27,_binary '','MS PODIUM BELOW LOOSE STAPLE BINS – 2 BINS W-970 D-490 H-150','MS PODIUM','admin','2022-11-14 11:17:20','admin','2022-11-14 11:17:20'),(28,_binary '','MS PODIUM BELOW LOOSE STAPLES BINS – 4 BINS W-970 D-970 H-150','MS PODIUM','admin','2022-11-14 11:17:57','admin','2022-11-14 11:17:57'),(29,_binary '','F&V HEAPER W-600 D-600 H-800','F&V HEAPER','admin','2022-11-14 11:18:29','admin','2022-11-14 11:18:29'),(30,_binary '','WEIGHING SCALE STATION – SINGLE W-600 D-900 H-900','W SCALE','admin','2022-11-14 11:19:44','admin','2022-11-14 11:19:44'),(31,_binary '','WEIGHING SCALE STATION - DOUBLE W-1200 D-900 H-900','W SCALE','admin','2022-11-14 11:20:19','admin','2022-11-14 11:20:19'),(32,_binary '','\"8\"\" DEEP M.S.PEGS WITH LABEL HOLDER 20 NOS FOR EACH SLAT WALL WITH PERFORATED SS BACK 900 MM X 600 MM.','M.S.PEGS','admin','2022-11-14 11:20:44','admin','2022-11-14 11:20:44'),(33,_binary '','REGULAR CHECK-OUT COUNTER WITH DOUBLE BASIN W-2200 D-1150 H-850','COUNTER','admin','2022-11-14 11:21:37','admin','2022-11-14 11:21:37'),(34,_binary '','SHOPPER TROLLEY W-750 D-400 H-550','S TROLLEY','admin','2022-11-14 11:22:11','admin','2022-11-14 11:22:11');
/*!40000 ALTER TABLE `mst_assetstype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_bay`
--

DROP TABLE IF EXISTS `mst_bay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_bay` (
  `bay_id` int NOT NULL AUTO_INCREMENT,
  `bay_no` varchar(255) NOT NULL,
  `des` varchar(255) NOT NULL,
  `outlet_id` int NOT NULL,
  `class` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`bay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_bay`
--

LOCK TABLES `mst_bay` WRITE;
/*!40000 ALTER TABLE `mst_bay` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_bay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_cluster`
--

DROP TABLE IF EXISTS `mst_cluster`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_cluster` (
  `id` int NOT NULL AUTO_INCREMENT,
  `des` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_cluster`
--

LOCK TABLES `mst_cluster` WRITE;
/*!40000 ALTER TABLE `mst_cluster` DISABLE KEYS */;
INSERT INTO `mst_cluster` VALUES (1,'KOLKATA 1',_binary '','admin','2022-11-10 11:30:54','admin','2022-11-10 11:30:54');
/*!40000 ALTER TABLE `mst_cluster` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_country`
--

DROP TABLE IF EXISTS `mst_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` int DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `symbol` varchar(255) DEFAULT NULL,
  `capital` varchar(255) DEFAULT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `continent` varchar(255) DEFAULT NULL,
  `continent_code` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_country`
--

LOCK TABLES `mst_country` WRITE;
/*!40000 ALTER TABLE `mst_country` DISABLE KEYS */;
INSERT INTO `mst_country` VALUES (1,93,'AF','Afghanistan','؋','Kabul','AFN','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(2,358,'AX','Aland Islands','€','Mariehamn','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(3,355,'AL','Albania','Lek','Tirana','ALL','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(4,213,'DZ','Algeria','دج','Algiers','DZD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(5,1684,'AS','American Samoa','$','Pago Pago','USD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(6,376,'AD','Andorra','€','Andorra la Vella','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(7,244,'AO','Angola','Kz','Luanda','AOA','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(8,1264,'AI','Anguilla','$','The Valley','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(9,672,'AQ','Antarctica','$','Antarctica','AAD','Antarctica','AN','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(10,1268,'AG','Antigua and Barbuda','$','St. John\'s','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(11,54,'AR','Argentina','$','Buenos Aires','ARS','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(12,374,'AM','Armenia','֏','Yerevan','AMD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(13,297,'AW','Aruba','ƒ','Oranjestad','AWG','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(14,61,'AU','Australia','$','Canberra','AUD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(15,43,'AT','Austria','€','Vienna','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(16,994,'AZ','Azerbaijan','m','Baku','AZN','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(17,1242,'BS','Bahamas','B$','Nassau','BSD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(18,973,'BH','Bahrain','.د.ب','Manama','BHD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(19,880,'BD','Bangladesh','৳','Dhaka','BDT','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(20,1246,'BB','Barbados','Bds$','Bridgetown','BBD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(21,375,'BY','Belarus','Br','Minsk','BYN','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(22,32,'BE','Belgium','€','Brussels','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(23,501,'BZ','Belize','$','Belmopan','BZD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(24,229,'BJ','Benin','CFA','Porto-Novo','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(25,1441,'BM','Bermuda','$','Hamilton','BMD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(26,975,'BT','Bhutan','Nu.','Thimphu','BTN','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(27,591,'BO','Bolivia','Bs.','Sucre','BOB','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(28,599,'BQ','Bonaire, Sint Eustatius and Saba','$','Kralendijk','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(29,387,'BA','Bosnia and Herzegovina','KM','Sarajevo','BAM','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(30,267,'BW','Botswana','P','Gaborone','BWP','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(31,55,'BV','Bouvet Island','kr','','NOK','Antarctica','AN','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(32,55,'BR','Brazil','R$','Brasilia','BRL','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(33,246,'IO','British Indian Ocean Territory','$','Diego Garcia','USD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(34,673,'BN','Brunei Darussalam','B$','Bandar Seri Begawan','BND','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(35,359,'BG','Bulgaria','Лв.','Sofia','BGN','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(36,226,'BF','Burkina Faso','CFA','Ouagadougou','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(37,257,'BI','Burundi','FBu','Bujumbura','BIF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(38,855,'KH','Cambodia','KHR','Phnom Penh','KHR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(39,237,'CM','Cameroon','FCFA','Yaounde','XAF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(40,1,'CA','Canada','$','Ottawa','CAD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(41,238,'CV','Cape Verde','$','Praia','CVE','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(42,1345,'KY','Cayman Islands','$','George Town','KYD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(43,236,'CF','Central African Republic','FCFA','Bangui','XAF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(44,235,'TD','Chad','FCFA','N\'Djamena','XAF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(45,56,'CL','Chile','$','Santiago','CLP','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(46,86,'CN','China','¥','Beijing','CNY','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(47,61,'CX','Christmas Island','$','Flying Fish Cove','AUD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(48,672,'CC','Cocos (Keeling) Islands','$','West Island','AUD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(49,57,'CO','Colombia','$','Bogota','COP','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(50,269,'KM','Comoros','CF','Moroni','KMF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(51,242,'CG','Congo','FC','Brazzaville','XAF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(52,242,'CD','Congo, Democratic Republic of the Congo','FC','Kinshasa','CDF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(53,682,'CK','Cook Islands','$','Avarua','NZD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(54,506,'CR','Costa Rica','₡','San Jose','CRC','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(55,225,'CI','Cote D\'Ivoire','CFA','Yamoussoukro','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(56,385,'HR','Croatia','kn','Zagreb','HRK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(57,53,'CU','Cuba','$','Havana','CUP','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(58,599,'CW','Curacao','ƒ','Willemstad','ANG','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(59,357,'CY','Cyprus','€','Nicosia','EUR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(60,420,'CZ','Czech Republic','Kč','Prague','CZK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(61,45,'DK','Denmark','Kr.','Copenhagen','DKK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(62,253,'DJ','Djibouti','Fdj','Djibouti','DJF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(63,1767,'DM','Dominica','$','Roseau','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(64,1809,'DO','Dominican Republic','$','Santo Domingo','DOP','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(65,593,'EC','Ecuador','$','Quito','USD','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(66,20,'EG','Egypt','ج.م','Cairo','EGP','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(67,503,'SV','El Salvador','$','San Salvador','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(68,240,'GQ','Equatorial Guinea','FCFA','Malabo','XAF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(69,291,'ER','Eritrea','Nfk','Asmara','ERN','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(70,372,'EE','Estonia','€','Tallinn','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(71,251,'ET','Ethiopia','Nkf','Addis Ababa','ETB','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(72,500,'FK','Falkland Islands (Malvinas)','£','Stanley','FKP','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(73,298,'FO','Faroe Islands','Kr.','Torshavn','DKK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(74,679,'FJ','Fiji','FJ$','Suva','FJD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(75,358,'FI','Finland','€','Helsinki','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(76,33,'FR','France','€','Paris','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(77,594,'GF','French Guiana','€','Cayenne','EUR','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(78,689,'PF','French Polynesia','₣','Papeete','XPF','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(79,262,'TF','French Southern Territories','€','Port-aux-Francais','EUR','Antarctica','AN','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(80,241,'GA','Gabon','FCFA','Libreville','XAF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(81,220,'GM','Gambia','D','Banjul','GMD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(82,995,'GE','Georgia','ლ','Tbilisi','GEL','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(83,49,'DE','Germany','€','Berlin','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(84,233,'GH','Ghana','GH₵','Accra','GHS','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(85,350,'GI','Gibraltar','£','Gibraltar','GIP','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(86,30,'GR','Greece','€','Athens','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(87,299,'GL','Greenland','Kr.','Nuuk','DKK','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(88,1473,'GD','Grenada','$','St. George\'s','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(89,590,'GP','Guadeloupe','€','Basse-Terre','EUR','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(90,1671,'GU','Guam','$','Hagatna','USD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(91,502,'GT','Guatemala','Q','Guatemala City','GTQ','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(92,44,'GG','Guernsey','£','St Peter Port','GBP','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(93,224,'GN','Guinea','FG','Conakry','GNF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(94,245,'GW','Guinea-Bissau','CFA','Bissau','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(95,592,'GY','Guyana','$','Georgetown','GYD','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(96,509,'HT','Haiti','G','Port-au-Prince','HTG','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(97,0,'HM','Heard Island and Mcdonald Islands','$','','AUD','Antarctica','AN','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(98,39,'VA','Holy See (Vatican City State)','€','Vatican City','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(99,504,'HN','Honduras','L','Tegucigalpa','HNL','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(100,852,'HK','Hong Kong','$','Hong Kong','HKD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(101,36,'HU','Hungary','Ft','Budapest','HUF','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(102,354,'IS','Iceland','kr','Reykjavik','ISK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(103,91,'IN','India','₹','New Delhi','INR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(104,62,'ID','Indonesia','Rp','Jakarta','IDR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(105,98,'IR','Iran, Islamic Republic of','﷼','Tehran','IRR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(106,964,'IQ','Iraq','د.ع','Baghdad','IQD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(107,353,'IE','Ireland','€','Dublin','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(108,44,'IM','Isle of Man','£','Douglas, Isle of Man','GBP','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(109,972,'IL','Israel','₪','Jerusalem','ILS','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(110,39,'IT','Italy','€','Rome','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(111,1876,'JM','Jamaica','J$','Kingston','JMD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(112,81,'JP','Japan','¥','Tokyo','JPY','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(113,44,'JE','Jersey','£','Saint Helier','GBP','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(114,962,'JO','Jordan','ا.د','Amman','JOD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(115,7,'KZ','Kazakhstan','лв','Astana','KZT','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(116,254,'KE','Kenya','KSh','Nairobi','KES','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(117,686,'KI','Kiribati','$','Tarawa','AUD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(118,850,'KP','Korea, Democratic People\'s Republic of','₩','Pyongyang','KPW','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(119,82,'KR','Korea, Republic of','₩','Seoul','KRW','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(120,381,'XK','Kosovo','€','Pristina','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(121,965,'KW','Kuwait','ك.د','Kuwait City','KWD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(122,996,'KG','Kyrgyzstan','лв','Bishkek','KGS','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(123,856,'LA','Lao People\'s Democratic Republic','₭','Vientiane','LAK','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(124,371,'LV','Latvia','€','Riga','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(125,961,'LB','Lebanon','£','Beirut','LBP','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(126,266,'LS','Lesotho','L','Maseru','LSL','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(127,231,'LR','Liberia','$','Monrovia','LRD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(128,218,'LY','Libyan Arab Jamahiriya','د.ل','Tripolis','LYD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(129,423,'LI','Liechtenstein','CHf','Vaduz','CHF','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(130,370,'LT','Lithuania','€','Vilnius','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(131,352,'LU','Luxembourg','€','Luxembourg','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(132,853,'MO','Macao','$','Macao','MOP','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(133,389,'MK','Macedonia, the Former Yugoslav Republic of','ден','Skopje','MKD','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(134,261,'MG','Madagascar','Ar','Antananarivo','MGA','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(135,265,'MW','Malawi','MK','Lilongwe','MWK','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(136,60,'MY','Malaysia','RM','Kuala Lumpur','MYR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(137,960,'MV','Maldives','Rf','Male','MVR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(138,223,'ML','Mali','CFA','Bamako','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(139,356,'MT','Malta','€','Valletta','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(140,692,'MH','Marshall Islands','$','Majuro','USD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(141,596,'MQ','Martinique','€','Fort-de-France','EUR','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(142,222,'MR','Mauritania','MRU','Nouakchott','MRO','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(143,230,'MU','Mauritius','₨','Port Louis','MUR','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(144,269,'YT','Mayotte','€','Mamoudzou','EUR','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(145,52,'MX','Mexico','$','Mexico City','MXN','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(146,691,'FM','Micronesia, Federated States of','$','Palikir','USD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(147,373,'MD','Moldova, Republic of','L','Chisinau','MDL','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(148,377,'MC','Monaco','€','Monaco','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(149,976,'MN','Mongolia','₮','Ulan Bator','MNT','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(150,382,'ME','Montenegro','€','Podgorica','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(151,1664,'MS','Montserrat','$','Plymouth','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(152,212,'MA','Morocco','DH','Rabat','MAD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(153,258,'MZ','Mozambique','MT','Maputo','MZN','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(154,95,'MM','Myanmar','K','Nay Pyi Taw','MMK','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(155,264,'NA','Namibia','$','Windhoek','NAD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(156,674,'NR','Nauru','$','Yaren','AUD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(157,977,'NP','Nepal','₨','Kathmandu','NPR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(158,31,'NL','Netherlands','€','Amsterdam','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(159,599,'AN','Netherlands Antilles','NAf','Willemstad','ANG','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(160,687,'NC','New Caledonia','₣','Noumea','XPF','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(161,64,'NZ','New Zealand','$','Wellington','NZD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(162,505,'NI','Nicaragua','C$','Managua','NIO','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(163,227,'NE','Niger','CFA','Niamey','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(164,234,'NG','Nigeria','₦','Abuja','NGN','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(165,683,'NU','Niue','$','Alofi','NZD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(166,672,'NF','Norfolk Island','$','Kingston','AUD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(167,1670,'MP','Northern Mariana Islands','$','Saipan','USD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(168,47,'NO','Norway','kr','Oslo','NOK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(169,968,'OM','Oman','.ع.ر','Muscat','OMR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(170,92,'PK','Pakistan','₨','Islamabad','PKR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(171,680,'PW','Palau','$','Melekeok','USD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(172,970,'PS','Palestinian Territory, Occupied','₪','East Jerusalem','ILS','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(173,507,'PA','Panama','B/.','Panama City','PAB','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(174,675,'PG','Papua New Guinea','K','Port Moresby','PGK','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(175,595,'PY','Paraguay','₲','Asuncion','PYG','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(176,51,'PE','Peru','S/.','Lima','PEN','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(177,63,'PH','Philippines','₱','Manila','PHP','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(178,64,'PN','Pitcairn','$','Adamstown','NZD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(179,48,'PL','Poland','zł','Warsaw','PLN','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(180,351,'PT','Portugal','€','Lisbon','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(181,1787,'PR','Puerto Rico','$','San Juan','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(182,974,'QA','Qatar','ق.ر','Doha','QAR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(183,262,'RE','Reunion','€','Saint-Denis','EUR','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(184,40,'RO','Romania','lei','Bucharest','RON','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(185,70,'RU','Russian Federation','₽','Moscow','RUB','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(186,250,'RW','Rwanda','FRw','Kigali','RWF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(187,590,'BL','Saint Barthelemy','€','Gustavia','EUR','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(188,290,'SH','Saint Helena','£','Jamestown','SHP','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(189,1869,'KN','Saint Kitts and Nevis','$','Basseterre','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(190,1758,'LC','Saint Lucia','$','Castries','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(191,590,'MF','Saint Martin','€','Marigot','EUR','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(192,508,'PM','Saint Pierre and Miquelon','€','Saint-Pierre','EUR','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(193,1784,'VC','Saint Vincent and the Grenadines','$','Kingstown','XCD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(194,684,'WS','Samoa','SAT','Apia','WST','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(195,378,'SM','San Marino','€','San Marino','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(196,239,'ST','Sao Tome and Principe','Db','Sao Tome','STD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(197,966,'SA','Saudi Arabia','﷼','Riyadh','SAR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(198,221,'SN','Senegal','CFA','Dakar','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(199,381,'RS','Serbia','din','Belgrade','RSD','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(200,381,'CS','Serbia and Montenegro','din','Belgrade','RSD','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(201,248,'SC','Seychelles','SRe','Victoria','SCR','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(202,232,'SL','Sierra Leone','Le','Freetown','SLL','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(203,65,'SG','Singapore','$','Singapur','SGD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(204,1,'SX','Sint Maarten','ƒ','Philipsburg','ANG','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(205,421,'SK','Slovakia','€','Bratislava','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(206,386,'SI','Slovenia','€','Ljubljana','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(207,677,'SB','Solomon Islands','Si$','Honiara','SBD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(208,252,'SO','Somalia','Sh.so.','Mogadishu','SOS','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(209,27,'ZA','South Africa','R','Pretoria','ZAR','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(210,500,'GS','South Georgia and the South Sandwich Islands','£','Grytviken','GBP','Antarctica','AN','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(211,211,'SS','South Sudan','£','Juba','SSP','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(212,34,'ES','Spain','€','Madrid','EUR','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(213,94,'LK','Sri Lanka','Rs','Colombo','LKR','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(214,249,'SD','Sudan','.س.ج','Khartoum','SDG','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(215,597,'SR','Suriname','$','Paramaribo','SRD','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(216,47,'SJ','Svalbard and Jan Mayen','kr','Longyearbyen','NOK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(217,268,'SZ','Swaziland','E','Mbabane','SZL','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(218,46,'SE','Sweden','kr','Stockholm','SEK','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(219,41,'CH','Switzerland','CHf','Berne','CHF','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(220,963,'SY','Syrian Arab Republic','LS','Damascus','SYP','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(221,886,'TW','Taiwan, Province of China','$','Taipei','TWD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(222,992,'TJ','Tajikistan','SM','Dushanbe','TJS','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(223,255,'TZ','Tanzania, United Republic of','TSh','Dodoma','TZS','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(224,66,'TH','Thailand','฿','Bangkok','THB','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(225,670,'TL','Timor-Leste','$','Dili','USD','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(226,228,'TG','Togo','CFA','Lome','XOF','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(227,690,'TK','Tokelau','$','','NZD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(228,676,'TO','Tonga','$','Nuku\'alofa','TOP','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(229,1868,'TT','Trinidad and Tobago','$','Port of Spain','TTD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(230,216,'TN','Tunisia','ت.د','Tunis','TND','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(231,90,'TR','Turkey','₺','Ankara','TRY','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(232,7370,'TM','Turkmenistan','T','Ashgabat','TMT','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(233,1649,'TC','Turks and Caicos Islands','$','Cockburn Town','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(234,688,'TV','Tuvalu','$','Funafuti','AUD','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(235,256,'UG','Uganda','USh','Kampala','UGX','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(236,380,'UA','Ukraine','₴','Kiev','UAH','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(237,971,'AE','United Arab Emirates','إ.د','Abu Dhabi','AED','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(238,44,'GB','United Kingdom','£','London','GBP','Europe','EU','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(239,1,'US','United States','$','Washington','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(240,1,'UM','United States Minor Outlying Islands','$','','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(241,598,'UY','Uruguay','$','Montevideo','UYU','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(242,998,'UZ','Uzbekistan','лв','Tashkent','UZS','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(243,678,'VU','Vanuatu','VT','Port Vila','VUV','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(244,58,'VE','Venezuela','Bs','Caracas','VEF','South America','SA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(245,84,'VN','Viet Nam','₫','Hanoi','VND','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(246,1284,'VG','Virgin Islands, British','$','Road Town','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(247,1340,'VI','Virgin Islands, U.s.','$','Charlotte Amalie','USD','North America','NA','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(248,681,'WF','Wallis and Futuna','₣','Mata Utu','XPF','Oceania','OC','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(249,212,'EH','Western Sahara','MAD','El-Aaiun','MAD','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(250,967,'YE','Yemen','﷼','Sanaa','YER','Asia','AS','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(251,260,'ZM','Zambia','ZK','Lusaka','ZMW','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(252,263,'ZW','Zimbabwe','$','Harare','ZWL','Africa','AF','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11');
/*!40000 ALTER TABLE `mst_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_location`
--

DROP TABLE IF EXISTS `mst_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `countryid` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `pincode` int NOT NULL,
  `stateid` int NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_location`
--

LOCK TABLES `mst_location` WRITE;
/*!40000 ALTER TABLE `mst_location` DISABLE KEYS */;
INSERT INTO `mst_location` VALUES (1,103,'KOLKATA',700027,37,_binary '','admin','2022-11-07 14:45:25','admin','2022-11-07 14:45:25'),(2,103,'MUMBAI',400705,22,_binary '','Mohit','2022-11-24 16:42:01','Mohit','2022-11-24 16:42:01');
/*!40000 ALTER TABLE `mst_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_outlet`
--

DROP TABLE IF EXISTS `mst_outlet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_outlet` (
  `id` int NOT NULL AUTO_INCREMENT,
  `add1` varchar(255) NOT NULL,
  `add2` varchar(255) DEFAULT NULL,
  `add3` varchar(255) DEFAULT NULL,
  `countrycode` int DEFAULT NULL,
  `email1` varchar(255) NOT NULL,
  `locationid` int DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pin` int NOT NULL,
  `stateid` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `warehouseid` int DEFAULT '0',
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_outlet`
--

LOCK TABLES `mst_outlet` WRITE;
/*!40000 ALTER TABLE `mst_outlet` DISABLE KEYS */;
INSERT INTO `mst_outlet` VALUES (1,'IRC Ltd','2 Sonai Road','2nd Lane',103,'contactus@sumosave.in',1,'9836543858','SONAI DC','9836543858',700027,37,_binary '','W',0,'admin','2022-11-10 11:30:01','admin','2022-11-10 11:30:01'),(2,'Satyam Towers','1St Floor','3 Alipur Road',103,'contactus@sumosave.in',1,'9836543858','HEAD OFFICE',NULL,700027,37,_binary '','O',0,'admin','2022-11-14 10:33:54','admin','2022-11-14 10:33:54'),(3,'Ground Floor, Active Acres Business Park','54/10 , Debendra Chandra Dey Road','PS- Tangra',103,'contactus@sumosave.in',1,'9836543858','ACTIVE ACRES',NULL,700015,37,_binary '','S',1,'admin','2022-11-14 10:37:24','admin','2022-11-14 10:37:24'),(4,'Maheshtala Municipality ,P.O-Maheshtala','Dist- South 24 Parganas',NULL,103,'contactus@sumosave.in',1,'9836543858','MAHESHTALA',NULL,700141,37,_binary '','S',1,'admin','2022-11-14 10:46:51','admin','2022-11-14 10:46:51');
/*!40000 ALTER TABLE `mst_outlet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_outlet_doc`
--

DROP TABLE IF EXISTS `mst_outlet_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_outlet_doc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `document` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `ref_id` int DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `validity` varchar(255) DEFAULT NULL,
  `outlet_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlx93j35g48qkpcd190146ow4h` (`outlet_id`),
  CONSTRAINT `FKlx93j35g48qkpcd190146ow4h` FOREIGN KEY (`outlet_id`) REFERENCES `mst_outlet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_outlet_doc`
--

LOCK TABLES `mst_outlet_doc` WRITE;
/*!40000 ALTER TABLE `mst_outlet_doc` DISABLE KEYS */;
INSERT INTO `mst_outlet_doc` VALUES (1,'FIre Licence',NULL,11,_binary '',NULL,1),(2,'FSSAI Licence',NULL,12,_binary '',NULL,1),(3,'Trade Licence',NULL,13,_binary '',NULL,1),(4,'Trade licence food (PART2)',NULL,14,_binary '\0',NULL,1),(5,'Legal Metrology',NULL,15,_binary '',NULL,1),(6,'Insecticide Licence',NULL,16,_binary '',NULL,1),(7,'Shop & Establishment',NULL,17,_binary '',NULL,1),(8,'Shops & Establishment',NULL,39,_binary '',NULL,2),(9,'Trade Licence',NULL,40,_binary '',NULL,2),(10,'GSTIN',NULL,41,_binary '',NULL,2),(11,'Fire Noc',NULL,42,_binary '\0',NULL,2),(12,'Shops & Establishment',NULL,18,_binary '',NULL,3),(13,'FSSAI',NULL,19,_binary '',NULL,3),(14,'Legal Metrology',NULL,20,_binary '',NULL,3),(15,'Fire Licence',NULL,21,_binary '',NULL,3),(16,'Trade Licence',NULL,22,_binary '',NULL,3),(17,'Police NOC',NULL,23,_binary '',NULL,3),(18,'Insecticide',NULL,24,_binary '\0',NULL,3),(19,'Shops & Establishment',NULL,18,_binary '',NULL,4),(20,'FSSAI',NULL,19,_binary '',NULL,4),(21,'Legal Metrology',NULL,20,_binary '',NULL,4),(22,'Fire Licence',NULL,21,_binary '',NULL,4),(23,'Trade Licence',NULL,22,_binary '',NULL,4),(24,'Police NOC',NULL,23,_binary '',NULL,4),(25,'Insecticide',NULL,24,_binary '\0',NULL,4);
/*!40000 ALTER TABLE `mst_outlet_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_paymentmode`
--

DROP TABLE IF EXISTS `mst_paymentmode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_paymentmode` (
  `paym_mode_id` int NOT NULL AUTO_INCREMENT,
  `payment_mode` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`paym_mode_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_paymentmode`
--

LOCK TABLES `mst_paymentmode` WRITE;
/*!40000 ALTER TABLE `mst_paymentmode` DISABLE KEYS */;
INSERT INTO `mst_paymentmode` VALUES (1,'CASH',_binary '','admin','2022-11-10 11:31:46','admin','2022-11-10 11:31:46'),(2,'CREDIT CARD',_binary '','admin','2022-11-10 11:31:57','admin','2022-11-10 11:31:57'),(3,'UPI',_binary '','admin','2022-11-10 11:32:07','admin','2022-11-10 11:32:07'),(4,'BANK TRANSFER',_binary '','admin','2022-11-16 14:34:13','admin','2022-11-16 14:34:13');
/*!40000 ALTER TABLE `mst_paymentmode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_reason`
--

DROP TABLE IF EXISTS `mst_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_reason` (
  `reason_id` int NOT NULL AUTO_INCREMENT,
  `reason_name` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`reason_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_reason`
--

LOCK TABLES `mst_reason` WRITE;
/*!40000 ALTER TABLE `mst_reason` DISABLE KEYS */;
INSERT INTO `mst_reason` VALUES (1,'LESS THAN 25% SHELF LIFE',_binary '','admin','2022-11-10 11:37:36','admin','2022-11-10 11:37:36'),(2,'DAMAGED GOODS',_binary '','admin','2022-11-10 11:37:57','admin','2022-11-10 11:37:57'),(3,'QUALITY FAIL',_binary '','admin','2022-11-10 11:38:37','admin','2022-11-10 11:38:37');
/*!40000 ALTER TABLE `mst_reason` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_region`
--

DROP TABLE IF EXISTS `mst_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_region` (
  `region_id` int NOT NULL AUTO_INCREMENT,
  `region_name` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_region`
--

LOCK TABLES `mst_region` WRITE;
/*!40000 ALTER TABLE `mst_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_schedule`
--

DROP TABLE IF EXISTS `mst_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `des` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_schedule`
--

LOCK TABLES `mst_schedule` WRITE;
/*!40000 ALTER TABLE `mst_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_state`
--

DROP TABLE IF EXISTS `mst_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_state` (
  `id` int NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  `tin` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `country_code` int DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_state`
--

LOCK TABLES `mst_state` WRITE;
/*!40000 ALTER TABLE `mst_state` DISABLE KEYS */;
INSERT INTO `mst_state` VALUES (1,'Andaman and Nicobar Islands','35','AN',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(2,'Andhra Pradesh','28','AP',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(3,'Andhra Pradesh (New)','37','AD',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(4,'Arunachal Pradesh','12','AR',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(5,'Assam','18','AS',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(6,'Bihar','10','BH',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(7,'Chandigarh','04','CH',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(8,'Chattisgarh','22','CT',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(9,'Dadra and Nagar Haveli','26','DN',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(10,'Daman and Diu','25','DD',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(11,'Delhi','07','DL',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(12,'Goa','30','GA',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(13,'Gujarat','24','GJ',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(14,'Haryana','06','HR',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(15,'Himachal Pradesh','02','HP',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(16,'Jammu and Kashmir','01','JK',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(17,'Jharkhand','20','JH',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(18,'Karnataka','29','KA',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(19,'Kerala','32','KL',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(20,'Lakshadweep Islands','31','LD',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(21,'Madhya Pradesh','23','MP',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(22,'Maharashtra','27','MH',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(23,'Manipur','14','MN',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(24,'Meghalaya','17','ME',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(25,'Mizoram','15','MI',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(26,'Nagaland','13','NL',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(27,'Odisha','21','OR',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(28,'Pondicherry','34','PY',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(29,'Punjab','03','PB',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(30,'Rajasthan','08','RJ',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(31,'Sikkim','11','SK',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(32,'Tamil Nadu','33','TN',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(33,'Telangana','36','TS',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(34,'Tripura','16','TR',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(35,'Uttar Pradesh','09','UP',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(36,'Uttarakhand','05','UT',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(37,'West Bengal','19','WB',103,'admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11');
/*!40000 ALTER TABLE `mst_state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_transport`
--

DROP TABLE IF EXISTS `mst_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_transport` (
  `id` int NOT NULL AUTO_INCREMENT,
  `add1` varchar(255) NOT NULL,
  `add2` varchar(255) DEFAULT NULL,
  `add3` varchar(255) DEFAULT NULL,
  `countrycode` int DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `locationid` int DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pin` int NOT NULL,
  `stateid` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_transport`
--

LOCK TABLES `mst_transport` WRITE;
/*!40000 ALTER TABLE `mst_transport` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mst_transport_doc`
--

DROP TABLE IF EXISTS `mst_transport_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mst_transport_doc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `document` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `ref_id` int DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `validity` varchar(255) DEFAULT NULL,
  `transport_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp0mkc072mdw9aahrvdwlnf0cd` (`transport_id`),
  CONSTRAINT `FKp0mkc072mdw9aahrvdwlnf0cd` FOREIGN KEY (`transport_id`) REFERENCES `mst_transport` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mst_transport_doc`
--

LOCK TABLES `mst_transport_doc` WRITE;
/*!40000 ALTER TABLE `mst_transport_doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `mst_transport_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outlet_cluster_mapping`
--

DROP TABLE IF EXISTS `outlet_cluster_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outlet_cluster_mapping` (
  `cluster_id` int NOT NULL,
  `outlet_id` int NOT NULL,
  KEY `FKi13vk9l8bixv2uq9gsn7rxx7b` (`outlet_id`),
  KEY `FKqe95hrjn54thgyq1kwvp2ye0c` (`cluster_id`),
  CONSTRAINT `FKi13vk9l8bixv2uq9gsn7rxx7b` FOREIGN KEY (`outlet_id`) REFERENCES `mst_outlet` (`id`),
  CONSTRAINT `FKqe95hrjn54thgyq1kwvp2ye0c` FOREIGN KEY (`cluster_id`) REFERENCES `mst_cluster` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outlet_cluster_mapping`
--

LOCK TABLES `outlet_cluster_mapping` WRITE;
/*!40000 ALTER TABLE `outlet_cluster_mapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `outlet_cluster_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_article`
--

DROP TABLE IF EXISTS `pro_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_article` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `block_procurement` bit(1) DEFAULT NULL,
  `ean_cd` varchar(255) NOT NULL,
  `article_name` varchar(255) NOT NULL,
  `product_id` int NOT NULL,
  `article_sh_nm` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_article`
--

LOCK TABLES `pro_article` WRITE;
/*!40000 ALTER TABLE `pro_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_article_stat_change`
--

DROP TABLE IF EXISTS `pro_article_stat_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_article_stat_change` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_id` int NOT NULL,
  `block_procurement` bit(1) DEFAULT NULL,
  `block_change_by` varchar(255) DEFAULT NULL,
  `block_end_dt` varchar(255) DEFAULT NULL,
  `block_st_dt` varchar(255) DEFAULT NULL,
  `narration` varchar(255) DEFAULT NULL,
  `product_id` int NOT NULL,
  `sta_change_by` varchar(255) DEFAULT NULL,
  `sta_end_dt` varchar(255) DEFAULT NULL,
  `sta_st_dt` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_article_stat_change`
--

LOCK TABLES `pro_article_stat_change` WRITE;
/*!40000 ALTER TABLE `pro_article_stat_change` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_article_stat_change` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_brand`
--

DROP TABLE IF EXISTS `pro_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `brand_name` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_brand`
--

LOCK TABLES `pro_brand` WRITE;
/*!40000 ALTER TABLE `pro_brand` DISABLE KEYS */;
INSERT INTO `pro_brand` VALUES (1,'admin','2022-11-15 17:07:42','admin','2022-11-15 17:07:42','LAKME',_binary '');
/*!40000 ALTER TABLE `pro_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_item`
--

DROP TABLE IF EXISTS `pro_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pro_class` varchar(100) NOT NULL,
  `pro_division` varchar(100) NOT NULL,
  `pro_family` varchar(100) NOT NULL,
  `pro_sub_class` varchar(100) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `item` (`pro_division`,`pro_family`,`pro_class`,`pro_sub_class`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_item`
--

LOCK TABLES `pro_item` WRITE;
/*!40000 ALTER TABLE `pro_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_manufacturer`
--

DROP TABLE IF EXISTS `pro_manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_manufacturer` (
  `manufac_id` int NOT NULL AUTO_INCREMENT,
  `manufac_name` varchar(255) NOT NULL,
  `off_invoice` double DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`manufac_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_manufacturer`
--

LOCK TABLES `pro_manufacturer` WRITE;
/*!40000 ALTER TABLE `pro_manufacturer` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_manufacturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_pack_type`
--

DROP TABLE IF EXISTS `pro_pack_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_pack_type` (
  `pack_type_id` int NOT NULL AUTO_INCREMENT,
  `pack_type_name` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`pack_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_pack_type`
--

LOCK TABLES `pro_pack_type` WRITE;
/*!40000 ALTER TABLE `pro_pack_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_pack_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_purchase_group`
--

DROP TABLE IF EXISTS `pro_purchase_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_purchase_group` (
  `purchase_gr_id` int NOT NULL AUTO_INCREMENT,
  `purchase_gr_name` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`purchase_gr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_purchase_group`
--

LOCK TABLES `pro_purchase_group` WRITE;
/*!40000 ALTER TABLE `pro_purchase_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_purchase_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pro_uom`
--

DROP TABLE IF EXISTS `pro_uom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pro_uom` (
  `uom_id` int NOT NULL AUTO_INCREMENT,
  `uom_name` varchar(255) NOT NULL,
  `uom_short_name` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`uom_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pro_uom`
--

LOCK TABLES `pro_uom` WRITE;
/*!40000 ALTER TABLE `pro_uom` DISABLE KEYS */;
INSERT INTO `pro_uom` VALUES (1,'KILOGRAM','KG',_binary '','admin','2022-11-15 17:13:12','admin','2022-11-15 17:13:12'),(2,'PIECES','PCS',_binary '','admin','2022-11-15 17:13:30','admin','2022-11-15 17:14:35');
/*!40000 ALTER TABLE `pro_uom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promo`
--

DROP TABLE IF EXISTS `promo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promo` (
  `promo_id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `article_id` int DEFAULT NULL,
  `cluster_id` int DEFAULT NULL,
  `offer_des` varchar(255) DEFAULT NULL,
  `offer_end_dt` varchar(255) DEFAULT NULL,
  `offer_st_dt` varchar(255) DEFAULT NULL,
  `outlet_id` int DEFAULT NULL,
  `self_approval` varchar(255) DEFAULT NULL,
  `self_dis_reason` varchar(255) DEFAULT NULL,
  `self_offer_per` int DEFAULT NULL,
  `self_offer_price` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `ven_approval` varchar(255) DEFAULT NULL,
  `ven_dis_reason` varchar(255) DEFAULT NULL,
  `ven_offer_per` int DEFAULT NULL,
  `ven_offer_price` int DEFAULT NULL,
  `vendor_id` int DEFAULT NULL,
  PRIMARY KEY (`promo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promo`
--

LOCK TABLES `promo` WRITE;
/*!40000 ALTER TABLE `promo` DISABLE KEYS */;
/*!40000 ALTER TABLE `promo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pur_bill_dtl`
--

DROP TABLE IF EXISTS `pur_bill_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pur_bill_dtl` (
  `bill_dtl_id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `article_id` int DEFAULT NULL,
  `basic_amt` double DEFAULT NULL,
  `cgst_amt` double DEFAULT NULL,
  `cp` double DEFAULT NULL,
  `ean_cd` varchar(255) DEFAULT NULL,
  `hsn_cd` varchar(255) DEFAULT NULL,
  `igst_amt` double DEFAULT NULL,
  `mrp` double DEFAULT NULL,
  `net_amt` double DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `sgst_amt` double DEFAULT NULL,
  `tax_per` double DEFAULT NULL,
  `uom_id` int DEFAULT NULL,
  `bill_hdr_id` int DEFAULT NULL,
  PRIMARY KEY (`bill_dtl_id`),
  KEY `FKn2ax7oi41cclf3kwvqsm3fty4` (`bill_hdr_id`),
  CONSTRAINT `FKn2ax7oi41cclf3kwvqsm3fty4` FOREIGN KEY (`bill_hdr_id`) REFERENCES `pur_bill_hdr` (`bill_hdr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pur_bill_dtl`
--

LOCK TABLES `pur_bill_dtl` WRITE;
/*!40000 ALTER TABLE `pur_bill_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `pur_bill_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pur_bill_hdr`
--

DROP TABLE IF EXISTS `pur_bill_hdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pur_bill_hdr` (
  `bill_hdr_id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `bill_dt` varchar(255) DEFAULT NULL,
  `bill_no` varchar(255) DEFAULT NULL,
  `company_id` int DEFAULT NULL,
  `outlate_id` int DEFAULT NULL,
  `po_dt` varchar(255) DEFAULT NULL,
  `po_no` varchar(255) DEFAULT NULL,
  `purc_dt` varchar(255) DEFAULT NULL,
  `purc_no` varchar(255) DEFAULT NULL,
  `tot_basic_amt` double DEFAULT NULL,
  `tot_cgst_amt` double DEFAULT NULL,
  `tot_igst_amt` double DEFAULT NULL,
  `tot_net_amt` double DEFAULT NULL,
  `tot_sgst_amt` double DEFAULT NULL,
  `vendor_id` int DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`bill_hdr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pur_bill_hdr`
--

LOCK TABLES `pur_bill_hdr` WRITE;
/*!40000 ALTER TABLE `pur_bill_hdr` DISABLE KEYS */;
/*!40000 ALTER TABLE `pur_bill_hdr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pur_po_dtl`
--

DROP TABLE IF EXISTS `pur_po_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pur_po_dtl` (
  `po_dtl_id` int NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `article_id` int DEFAULT NULL,
  `basic_amt` double DEFAULT NULL,
  `cgst_amt` double DEFAULT NULL,
  `cp` double DEFAULT NULL,
  `ean_cd` varchar(255) DEFAULT NULL,
  `hsn_cd` varchar(255) DEFAULT NULL,
  `igst_amt` double DEFAULT NULL,
  `mrp` double DEFAULT NULL,
  `net_amt` double DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `recd_qty` double DEFAULT NULL,
  `sgst_amt` double DEFAULT NULL,
  `tax_per` double DEFAULT NULL,
  `uom_id` int DEFAULT NULL,
  `po_hdr_id` int DEFAULT NULL,
  PRIMARY KEY (`po_dtl_id`),
  KEY `FK9s4kknlr4kcy2jv4lvbe1lt8d` (`po_hdr_id`),
  CONSTRAINT `FK9s4kknlr4kcy2jv4lvbe1lt8d` FOREIGN KEY (`po_hdr_id`) REFERENCES `pur_po_hdr` (`po_hdr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pur_po_dtl`
--

LOCK TABLES `pur_po_dtl` WRITE;
/*!40000 ALTER TABLE `pur_po_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `pur_po_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pur_po_hdr`
--

DROP TABLE IF EXISTS `pur_po_hdr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pur_po_hdr` (
  `po_hdr_id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `disapprove_reason` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `company_id` int DEFAULT NULL,
  `delv_dt` varchar(255) DEFAULT NULL,
  `expiry_dt` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `outlet_id` int DEFAULT NULL,
  `paym_terms` varchar(255) DEFAULT NULL,
  `po_dt` varchar(255) DEFAULT NULL,
  `po_no` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tot_basic_amt` double DEFAULT NULL,
  `tot_cgst_amt` double DEFAULT NULL,
  `tot_igst_amt` double DEFAULT NULL,
  `tot_net_amt` double DEFAULT NULL,
  `tot_sgst_amt` double DEFAULT NULL,
  `vendor_id` int DEFAULT NULL,
  PRIMARY KEY (`po_hdr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pur_po_hdr`
--

LOCK TABLES `pur_po_hdr` WRITE;
/*!40000 ALTER TABLE `pur_po_hdr` DISABLE KEYS */;
/*!40000 ALTER TABLE `pur_po_hdr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pur_terms`
--

DROP TABLE IF EXISTS `pur_terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pur_terms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sl_no` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `terms` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pur_terms`
--

LOCK TABLES `pur_terms` WRITE;
/*!40000 ALTER TABLE `pur_terms` DISABLE KEYS */;
/*!40000 ALTER TABLE `pur_terms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_adhoc_privileges`
--

DROP TABLE IF EXISTS `usm_adhoc_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_adhoc_privileges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp77kx0k14ii6pphjbm07dwp6o` (`user_id`),
  CONSTRAINT `FKp77kx0k14ii6pphjbm07dwp6o` FOREIGN KEY (`user_id`) REFERENCES `usm_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_adhoc_privileges`
--

LOCK TABLES `usm_adhoc_privileges` WRITE;
/*!40000 ALTER TABLE `usm_adhoc_privileges` DISABLE KEYS */;
/*!40000 ALTER TABLE `usm_adhoc_privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_adhoc_viewprivileges`
--

DROP TABLE IF EXISTS `usm_adhoc_viewprivileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_adhoc_viewprivileges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu` varchar(255) NOT NULL,
  `privilage` varchar(255) NOT NULL,
  `submenu` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq8g7qdf8pn28abacdooc3luv6` (`user_id`),
  CONSTRAINT `FKq8g7qdf8pn28abacdooc3luv6` FOREIGN KEY (`user_id`) REFERENCES `usm_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_adhoc_viewprivileges`
--

LOCK TABLES `usm_adhoc_viewprivileges` WRITE;
/*!40000 ALTER TABLE `usm_adhoc_viewprivileges` DISABLE KEYS */;
/*!40000 ALTER TABLE `usm_adhoc_viewprivileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_privileges`
--

DROP TABLE IF EXISTS `usm_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_privileges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_privileges`
--

LOCK TABLES `usm_privileges` WRITE;
/*!40000 ALTER TABLE `usm_privileges` DISABLE KEYS */;
INSERT INTO `usm_privileges` VALUES (1,'ADD','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(2,'EDIT','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(3,'DELETE','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(4,'VIEW','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(5,'EXPORT','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(6,'PRINT','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11');
/*!40000 ALTER TABLE `usm_privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_role`
--

DROP TABLE IF EXISTS `usm_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `decription` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_role`
--

LOCK TABLES `usm_role` WRITE;
/*!40000 ALTER TABLE `usm_role` DISABLE KEYS */;
INSERT INTO `usm_role` VALUES (1,'ROLE_ADMIN','System Admin',_binary '','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(2,'ROLE_VENDOR','Vendor',_binary '','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(3,'ROLE_BUYER','Category buyer',_binary '','admin','2022-11-21 17:34:09','admin','2022-11-21 17:34:09');
/*!40000 ALTER TABLE `usm_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_roles_privileges`
--

DROP TABLE IF EXISTS `usm_roles_privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_roles_privileges` (
  `role_id` int NOT NULL,
  `privilege_id` int NOT NULL,
  KEY `FKmi23qyxhc01jh7deg4b1rsiui` (`privilege_id`),
  KEY `FK8r4hgbmvf26vp847541p5trd0` (`role_id`),
  CONSTRAINT `FK8r4hgbmvf26vp847541p5trd0` FOREIGN KEY (`role_id`) REFERENCES `usm_role` (`id`),
  CONSTRAINT `FKmi23qyxhc01jh7deg4b1rsiui` FOREIGN KEY (`privilege_id`) REFERENCES `usm_privileges` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_roles_privileges`
--

LOCK TABLES `usm_roles_privileges` WRITE;
/*!40000 ALTER TABLE `usm_roles_privileges` DISABLE KEYS */;
INSERT INTO `usm_roles_privileges` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(3,1),(3,2),(3,4),(3,5),(3,6);
/*!40000 ALTER TABLE `usm_roles_privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_roles_viewprivileges`
--

DROP TABLE IF EXISTS `usm_roles_viewprivileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_roles_viewprivileges` (
  `role_id` int NOT NULL,
  `viewprivilege_id` int NOT NULL,
  KEY `FKg45n20gpnjh3jdhbe8w3f2m1q` (`viewprivilege_id`),
  KEY `FKf4t6ph64dkoaruj8qf3naubym` (`role_id`),
  CONSTRAINT `FKf4t6ph64dkoaruj8qf3naubym` FOREIGN KEY (`role_id`) REFERENCES `usm_role` (`id`),
  CONSTRAINT `FKg45n20gpnjh3jdhbe8w3f2m1q` FOREIGN KEY (`viewprivilege_id`) REFERENCES `usm_viewprivileges` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_roles_viewprivileges`
--

LOCK TABLES `usm_roles_viewprivileges` WRITE;
/*!40000 ALTER TABLE `usm_roles_viewprivileges` DISABLE KEYS */;
INSERT INTO `usm_roles_viewprivileges` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),(1,40),(1,41),(1,42),(1,43),(1,44),(1,45),(1,46),(1,47),(1,48),(1,49),(1,50),(1,51),(1,52),(1,53),(1,54),(1,55),(1,56),(1,57),(1,58),(1,59),(1,60),(2,1),(2,35),(2,37),(2,39),(2,41),(2,46),(2,58),(3,21),(3,24),(3,25),(3,26),(3,27),(3,28),(3,29),(3,30),(3,31),(3,32),(3,33),(3,34),(3,35),(3,36),(3,37),(3,38),(3,39),(3,40),(3,41),(3,42),(3,43),(3,44),(3,45),(3,46),(3,47),(3,48),(3,49),(3,50),(3,51),(3,52),(3,53),(3,54),(3,55),(3,56),(3,57),(3,58),(3,59),(3,60);
/*!40000 ALTER TABLE `usm_roles_viewprivileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_user`
--

DROP TABLE IF EXISTS `usm_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_not_expired` bit(1) DEFAULT NULL,
  `coid` int DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `employeeid` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `ishod` bit(1) DEFAULT NULL,
  `locationid` int DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parentuser` int DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `profile_pic_link` varchar(255) DEFAULT NULL,
  `superparentuser` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `vendor_id` int DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_user`
--

LOCK TABLES `usm_user` WRITE;
/*!40000 ALTER TABLE `usm_user` DISABLE KEYS */;
INSERT INTO `usm_user` VALUES (1,_binary '',1,'admin@omni.com','1',_binary '',_binary '\0',1,'1234567890','admin',NULL,'$2a$12$xs9dXFQVV/lKHbQmLrlsqez2uMPyjmazk0K71rNxHo0/MTr.Mr9j.','profilPic.png',NULL,'admin',0,NULL,NULL,'admin','2022-11-26 17:47:55'),(2,_binary '',1,'michael@vareli.co.in','OMNI/0002',_binary '',_binary '\0',1,'8573480054','Michael',NULL,'$2a$12$5wVuisw3Ji3k6gYaIUJTbOSLCbOKu5ZhKc.UrjZLJhXNEuWIPfjOe','profilPic.png',NULL,'michael',0,'admin','2022-11-11 11:50:53','admin','2022-11-21 17:55:22'),(3,_binary '',1,'mohit.kampani@sumosave.in','E00008',_binary '',_binary '',1,'9830069001','Mohit Kampani',NULL,'$2a$10$2VGFhKyvyldwQ3pnnj8wTeg4LKonHY.8uyFRnSiEYfBAeh7MHbbam','profilPic.png',NULL,'Mohit',0,NULL,NULL,'admin','2022-11-21 17:51:48');
/*!40000 ALTER TABLE `usm_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_users_roles`
--

DROP TABLE IF EXISTS `usm_users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `FKqll65l7b46qjl3mt0ob1ggo29` (`role_id`),
  KEY `FKa481h3yp0oaum3efoi0xyfhd7` (`user_id`),
  CONSTRAINT `FKa481h3yp0oaum3efoi0xyfhd7` FOREIGN KEY (`user_id`) REFERENCES `usm_user` (`id`),
  CONSTRAINT `FKqll65l7b46qjl3mt0ob1ggo29` FOREIGN KEY (`role_id`) REFERENCES `usm_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_users_roles`
--

LOCK TABLES `usm_users_roles` WRITE;
/*!40000 ALTER TABLE `usm_users_roles` DISABLE KEYS */;
INSERT INTO `usm_users_roles` VALUES (2,1),(3,1),(1,1);
/*!40000 ALTER TABLE `usm_users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usm_viewprivileges`
--

DROP TABLE IF EXISTS `usm_viewprivileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usm_viewprivileges` (
  `id` int NOT NULL AUTO_INCREMENT,
  `menu` varchar(255) NOT NULL,
  `submenu` varchar(255) NOT NULL,
  `privilage` varchar(255) NOT NULL,
  `is_approval` bit(1) NOT NULL,
  `is_email` bit(1) NOT NULL,
  `is_identify` bit(1) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usm_viewprivileges`
--

LOCK TABLES `usm_viewprivileges` WRITE;
/*!40000 ALTER TABLE `usm_viewprivileges` DISABLE KEYS */;
INSERT INTO `usm_viewprivileges` VALUES (1,'Home','Home','HOME',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-05 01:57:12'),(2,'Admin','Admin','ADMINMENU',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(3,'Admin','Company Setup','COMPANYSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(4,'Admin','Application Setup','APPLICATIONSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(5,'Admin','Menu Setup','MENUSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(6,'Admin','Document Setup','DOCUMENTSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(7,'Admin','Year Setup','YEARSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(8,'Admin','Identification Setup','IDENTIFICATIONSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(9,'Admin','Email Setup','EMAILSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(10,'Admin','Approval Setup','APPROVALSETUP',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(11,'Admin','Login History','LOGINHISTORY',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(12,'Admin','Transaction Log','TRANSACTIONLOG',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(13,'User Management','User Management','USERMANAGEMENT',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(14,'User Management','User Registration','USERREGISTRATION',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(15,'User Management','Users','USERS',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(16,'User Management','Roles','ROLES',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(17,'Master','Master','MASTER',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(18,'Master','Location Master','LOCATIONMASTER',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(19,'Master','Outlet Master','OUTLETMASTER',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(20,'Master','Payment Mode Master','PAYMENTMODEMASTER',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(21,'Master','Assets Type','ASSETSTYPE',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(22,'Master','Transportation','TRANSPORTATION',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(23,'Master','Reason','REASON',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(24,'Master','Bay','BAY(REMOVE)',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(25,'Master','Schedule','SCHEDULE',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(26,'Master','Cluster','CLUSTER',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(27,'Master','Cluster Mapping','CLUSTERMAPPING',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(28,'Product','Product','PRODUCT',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(29,'Product','Item','ITEM',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(30,'Product','Brand','BRAND',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(31,'Product','Manufacture','MANUFACTURE',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(32,'Product','UOM','UOM',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(33,'Product','Article','ARTICLE',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(34,'Product','Article Status','ARTICLESTATUS',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(35,'Vendor','Vendor','VENDOR',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(36,'Vendor','Invite Vendor','INVITEVENDOR',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(37,'Vendor','Vendor Registration','VENDORREGISTRATION',_binary '\0',_binary '',_binary '','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(38,'Vendor','Vendor Approval','VENDORAPPROVAL',_binary '',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(39,'Vendor','Vendor Product','VENDORPRODUCT',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(40,'Vendor','Product Approval','PRODUCTAPPROVAL',_binary '',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(41,'Vendor','Shipping Notification','SHIPPINGNOTIFICATION',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(42,'Vendor','Vendor Manufacturer Mapping','VENDORMANUFACTURE',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(43,'Vendor','Vendor Replacement','VENDORREPLACEMENT',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(44,'Promo','Promo','PROMO',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(45,'Promo','General Promo','GENERALPROMO',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(46,'Vendor','Vendor Promo','VENDORPROMO',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(47,'Promo','Buyer Promo','BUYERPROMO',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(48,'Promo','Vendor Promo Approval','VENDORPROMOAPPROVAL',_binary '',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(49,'Promo','Buyer Promo Approval','BUYERPROMOAPPROVAL',_binary '',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(50,'Gkm','Gkm','GKM',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(51,'Gkm','Gate Keeper Margin','GKMARGIN',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(52,'Purchase','Purchase','PURCHASE',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(53,'Purchase','Purchase Order','PURCHASEORDER',_binary '\0',_binary '\0',_binary '','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(54,'Purchase','Purchase Bill','PURCHASEBILL',_binary '\0',_binary '\0',_binary '','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(55,'Purchase','Purchase Order Approval','PURCHASEORDERAPPROVAL',_binary '',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(56,'Asset','Asset','ASSET',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(57,'Asset','Asset  Registration','ASSETREGISTRATION',_binary '\0',_binary '\0',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(58,'Asset','Asset  Booking','ASSETBOOKING',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(59,'Asset','Asset Booking Approval','ASSETBOOKINGAPPROVAL',_binary '',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11'),(60,'Asset','Asset Execution','ASSETEXECUTION',_binary '\0',_binary '',_binary '\0','admin','2022-11-04 22:57:11','admin','2022-11-04 22:57:11');
/*!40000 ALTER TABLE `usm_viewprivileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_invite`
--

DROP TABLE IF EXISTS `ven_invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_invite` (
  `inv_ven_id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `date` date DEFAULT NULL,
  `email_id` varchar(255) NOT NULL,
  `time` time DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`inv_ven_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_invite`
--

LOCK TABLES `ven_invite` WRITE;
/*!40000 ALTER TABLE `ven_invite` DISABLE KEYS */;
INSERT INTO `ven_invite` VALUES (1,'admin','2022-11-15 17:20:12','admin','2022-11-15 17:20:12','2022-11-15','sanjeev@regencysnacks.com','17:20:12','admin');
/*!40000 ALTER TABLE `ven_invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_manuf`
--

DROP TABLE IF EXISTS `ven_manuf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_manuf` (
  `ven_id` int NOT NULL,
  `manuf_id` int NOT NULL,
  KEY `FK7h6ukswonhcfwvuumy23tm4q1` (`manuf_id`),
  KEY `FKrv8nujktl6operhjd1ldqv0yv` (`ven_id`),
  CONSTRAINT `FK7h6ukswonhcfwvuumy23tm4q1` FOREIGN KEY (`manuf_id`) REFERENCES `pro_manufacturer` (`manufac_id`),
  CONSTRAINT `FKrv8nujktl6operhjd1ldqv0yv` FOREIGN KEY (`ven_id`) REFERENCES `ven_reg` (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_manuf`
--

LOCK TABLES `ven_manuf` WRITE;
/*!40000 ALTER TABLE `ven_manuf` DISABLE KEYS */;
/*!40000 ALTER TABLE `ven_manuf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_product`
--

DROP TABLE IF EXISTS `ven_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_product` (
  `vendor_product_id` int NOT NULL AUTO_INCREMENT,
  `article_id` int DEFAULT NULL,
  `article_name` varchar(255) DEFAULT NULL,
  `article_sh_nm` varchar(255) DEFAULT NULL,
  `brand_id` int DEFAULT NULL,
  `buying_pattern` varchar(255) DEFAULT NULL,
  `carton_units` varchar(255) DEFAULT NULL,
  `conces_ind` varchar(255) DEFAULT NULL,
  `delv_day` varchar(255) DEFAULT NULL,
  `dim_depth` varchar(255) DEFAULT NULL,
  `dim_height` varchar(255) DEFAULT NULL,
  `dim_width` varchar(255) DEFAULT NULL,
  `distr_type` varchar(255) DEFAULT NULL,
  `dsp_ind` varchar(255) DEFAULT NULL,
  `ean_cd` varchar(255) DEFAULT NULL,
  `hsn_cd` varchar(255) DEFAULT NULL,
  `inner_case` varchar(255) DEFAULT NULL,
  `inner_units` varchar(255) DEFAULT NULL,
  `item_id` int DEFAULT NULL,
  `manufac_id` int DEFAULT NULL,
  `mbq` double DEFAULT NULL,
  `movement` varchar(255) DEFAULT NULL,
  `net_content` varchar(255) DEFAULT NULL,
  `off_inv_margin` double DEFAULT NULL,
  `old_ean_cd` varchar(255) DEFAULT NULL,
  `on_inv_margin` double DEFAULT NULL,
  `order_day` varchar(255) DEFAULT NULL,
  `outer_case` varchar(255) DEFAULT NULL,
  `price_based` varchar(255) DEFAULT NULL,
  `product_pic` varchar(255) DEFAULT NULL,
  `prod_type` varchar(255) DEFAULT NULL,
  `reson` varchar(255) DEFAULT NULL,
  `return_type` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `s_deliv_sch` int DEFAULT NULL,
  `s_order_sch` int DEFAULT NULL,
  `uom_id` int DEFAULT NULL,
  `vendor_id` int DEFAULT NULL,
  `wh_deliv_sch` int DEFAULT NULL,
  `wh_order_sch` int DEFAULT NULL,
  `weight` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`vendor_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_product`
--

LOCK TABLES `ven_product` WRITE;
/*!40000 ALTER TABLE `ven_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `ven_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_product_dtl`
--

DROP TABLE IF EXISTS `ven_product_dtl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_product_dtl` (
  `ven_prod_dtl_id` int NOT NULL AUTO_INCREMENT,
  `basic_cost` double DEFAULT NULL,
  `final_cost` double DEFAULT NULL,
  `gst_per` double DEFAULT NULL,
  `mrp` double DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `ven_prod_id` int DEFAULT NULL,
  PRIMARY KEY (`ven_prod_dtl_id`),
  KEY `FK7ppdtuupbuhohok0sov3h4uai` (`ven_prod_id`),
  CONSTRAINT `FK7ppdtuupbuhohok0sov3h4uai` FOREIGN KEY (`ven_prod_id`) REFERENCES `ven_product` (`vendor_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_product_dtl`
--

LOCK TABLES `ven_product_dtl` WRITE;
/*!40000 ALTER TABLE `ven_product_dtl` DISABLE KEYS */;
/*!40000 ALTER TABLE `ven_product_dtl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_reg`
--

DROP TABLE IF EXISTS `ven_reg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_reg` (
  `vendor_id` int NOT NULL AUTO_INCREMENT,
  `disapprove_reason` varchar(255) DEFAULT NULL,
  `edit_reason` varchar(255) DEFAULT NULL,
  `add1` varchar(255) NOT NULL,
  `add2` varchar(255) DEFAULT NULL,
  `add3` varchar(255) DEFAULT NULL,
  `bank_ac_no` varchar(255) DEFAULT NULL,
  `bank_branch` varchar(255) DEFAULT NULL,
  `bank_ifsc` varchar(255) DEFAULT NULL,
  `bank_nm` varchar(255) DEFAULT NULL,
  `cont_mobile` varchar(255) DEFAULT NULL,
  `cont_pers` varchar(255) DEFAULT NULL,
  `countrycode` int NOT NULL,
  `credit_days` int DEFAULT NULL,
  `delv_sch` varchar(255) DEFAULT NULL,
  `email1` varchar(255) NOT NULL,
  `email2` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `fsa_no` varchar(255) DEFAULT NULL,
  `fssai_no` varchar(255) DEFAULT NULL,
  `gst_no` varchar(255) NOT NULL,
  `inv_ven_id` varchar(255) DEFAULT NULL,
  `invitee` int DEFAULT NULL,
  `lead_time` int DEFAULT NULL,
  `locationid` int NOT NULL,
  `min_ord_qty` double DEFAULT NULL,
  `min_ord_val` double DEFAULT NULL,
  `mobile` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `order_sch` varchar(255) DEFAULT NULL,
  `pan_no` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `paym_mode_id` int DEFAULT NULL,
  `paym_on` varchar(255) DEFAULT NULL,
  `paym_terms` int DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `pin` int NOT NULL,
  `stateid` int NOT NULL,
  `status` varchar(1) DEFAULT NULL,
  `tin_no` varchar(255) DEFAULT NULL,
  `trade` bit(1) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `vendor_cd` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`vendor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_reg`
--

LOCK TABLES `ven_reg` WRITE;
/*!40000 ALTER TABLE `ven_reg` DISABLE KEYS */;
INSERT INTO `ven_reg` VALUES (1,NULL,NULL,'A-675 TTC INDUSTRIAL AREA','Navi Mumbai Municipal corporation (thane Zone-2)',NULL,'6310163101','Navi Mumbai','KKBK0001370','KOTAK','8425953147','KIRAN SALVI',103,30,NULL,'sales@regencysnacks.com','prashant.thakur@sumosave.in',NULL,NULL,'11522998000484','27AAFCA0453R2ZC',NULL,3,NULL,2,NULL,NULL,'8425953147','AFRICAN AGRO PRODUCTS PRIVATE LIMITED',NULL,'AAFCA0453R','1234',4,NULL,30,NULL,400705,22,'O','MUMA25322D',_binary '','AFRICAN AGRO PRODUCTS PRIVATE LIMITED',NULL,'Mohit','2022-11-24 16:56:29','Mohit','2022-11-24 16:56:29');
/*!40000 ALTER TABLE `ven_reg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_reg_doc`
--

DROP TABLE IF EXISTS `ven_reg_doc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_reg_doc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `document` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `ref_id` int DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `validity` varchar(255) DEFAULT NULL,
  `vendor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe930ohiuc85xuw46bx48jfuap` (`vendor_id`),
  CONSTRAINT `FKe930ohiuc85xuw46bx48jfuap` FOREIGN KEY (`vendor_id`) REFERENCES `mst_outlet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_reg_doc`
--

LOCK TABLES `ven_reg_doc` WRITE;
/*!40000 ALTER TABLE `ven_reg_doc` DISABLE KEYS */;
INSERT INTO `ven_reg_doc` VALUES (1,'TOT','C:/upload/office/bbe5cc23-a030-47de-96b2-b1f81f64710e.pdf',31,_binary '',NULL,NULL),(2,'VENDOR REGISTRATION FORM','C:/upload/office/51b6c697-2004-459d-a033-fd511d70baaf.pdf',32,_binary '',NULL,NULL),(3,'PAN Card','C:/upload/office/1c5472c2-cf0f-455f-92ea-9c6af74391b0.pdf',33,_binary '',NULL,NULL),(4,'TDS Number (TAN) Intimation','C:/upload/office/cb9e3d43-08b7-40a4-b6ca-39ed7c28e577.pdf',34,_binary '',NULL,NULL),(5,'GST Certificate','C:/upload/office/099ce75a-8fef-4604-86d6-f1563d5eaea3.pdf',35,_binary '',NULL,NULL),(6,'Cancelled Cheque','C:/upload/office/a6bcec08-e327-4bbe-b2f2-11a42a57b05a.pdf',36,_binary '',NULL,NULL),(7,'MSME Certificate (If applicable)','C:/upload/office/4f2e4828-3ea4-44b1-855c-b355f8783ffc.pdf',37,_binary '',NULL,NULL),(8,'FSSAI Licence','C:/upload/office/78cb7c8a-e083-4d4c-a8e1-f58b3f434d3a.pdf',38,_binary '',NULL,NULL);
/*!40000 ALTER TABLE `ven_reg_doc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ven_ship_info`
--

DROP TABLE IF EXISTS `ven_ship_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ven_ship_info` (
  `ship_info_id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `tr_date` date DEFAULT NULL,
  `doc_link` varchar(255) DEFAULT NULL,
  `outlet_id` int DEFAULT NULL,
  `po_dt` varchar(255) DEFAULT NULL,
  `po_no` varchar(255) DEFAULT NULL,
  `ship_info` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `tr_time` time DEFAULT NULL,
  `vendor_id` int NOT NULL,
  PRIMARY KEY (`ship_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ven_ship_info`
--

LOCK TABLES `ven_ship_info` WRITE;
/*!40000 ALTER TABLE `ven_ship_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `ven_ship_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'omnidb'
--

--
-- Dumping routines for database 'omnidb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-26 18:01:51

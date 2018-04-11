start transaction;
create database `Acme-Newspaper`;
use `Acme-Newspaper`;
create user 'acme-user'@'%' identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';
create user 'acme-manager'@'%' identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';
grant select, insert, update, delete on `Acme-Newspaper`.* to 'acme-user'@'%';
grant select, insert, update, delete, create, drop, references, index, alter, create temporary tables, lock tables, create view, create routine, alter routine, execute, trigger, show view on `Acme-Newspaper`.* to 'acme-manager'@'%';
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: acme-newspaper
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gfgqmtp2f9i5wsojt33xm0uth` (`userAccount_id`),
  KEY `AdminUK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_gfgqmtp2f9i5wsojt33xm0uth` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (287,0,'admin@acmenewspaper.com','admin 1','+34617557203','postal Adress admin 1','surname admin 1',278);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `draftMode` bit(1) NOT NULL,
  `publishedMoment` date DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `newspaper_id` int(11) NOT NULL,
  `writer_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_ipgmt2t51ohhv3v3e7079qt1v` (`draftMode`,`publishedMoment`,`title`,`body`,`summary`,`newspaper_id`,`writer_id`),
  KEY `FK_pftm848lf5hu8sd0vghfs605l` (`newspaper_id`),
  KEY `FK_tushlj62v3v30iqifyful69d` (`writer_id`),
  CONSTRAINT `FK_tushlj62v3v30iqifyful69d` FOREIGN KEY (`writer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_pftm848lf5hu8sd0vghfs605l` FOREIGN KEY (`newspaper_id`) REFERENCES `newspaper` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (312,0,'body chirp 1','\0','2017-09-20','summary article 1 this article is so long','title article 1 sex',296,288),(314,0,'body chirp 2 cialis','\0','2017-10-21','summary article 1 this article is so long and interesting','title article 2',297,288),(315,0,'body chirp 3','',NULL,'summary article 1 this article is so refreshing','title article 3',298,288),(316,0,'body chirp 4','',NULL,'summary article 1 this article is so long','title article 4',298,288),(317,0,'body chirp 5','\0','2017-09-20','summary article 1 this article is so cool viagra','title article 5',299,290),(319,0,'body chirp 6','\0','2017-09-20','summary especial article 6 this article is so long','title article 6',301,289),(321,0,'body chirp 7','\0','2017-09-20','summary article 1 this article is so long','title article 7',299,290),(323,0,'body impresionante chirp 8','\0','2017-09-20','summary article 1 this article is so long','title article 8',300,291),(324,0,'body chirp 9','\0','2016-10-21','summary article 1 this article is so long','title article 9',301,289),(325,0,'body chirp 10','\0','2016-10-21','summary article 1 this article is so long','title article 10',302,290),(326,0,'body chirp 11','\0','2016-10-21','summary article 1 this article is so long','title article 11',303,289),(327,0,'body chirp 12','\0','2016-10-21','summary article 1 this article is so long','title article 12',304,291);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_pictures`
--

DROP TABLE IF EXISTS `article_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_pictures` (
  `Article_id` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_slh5rr6y2n4ml5s20v5nlr52g` (`Article_id`),
  CONSTRAINT `FK_slh5rr6y2n4ml5s20v5nlr52g` FOREIGN KEY (`Article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_pictures`
--

LOCK TABLES `article_pictures` WRITE;
/*!40000 ALTER TABLE `article_pictures` DISABLE KEYS */;
INSERT INTO `article_pictures` VALUES (312,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(312,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(312,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(314,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(314,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(314,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(315,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(315,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(315,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(316,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(316,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(316,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(317,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(317,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(317,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(319,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(319,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(319,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(321,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(321,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(321,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(323,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(323,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(323,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(324,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(324,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(324,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(325,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(325,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(325,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(326,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(326,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(326,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg'),(327,'https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg'),(327,'https://img.blogs.es/anexom/wp-content/uploads/2016/10/Peridicos-920x515.jpg'),(327,'https://cibercult.files.wordpress.com/2014/06/periodico1.jpg');
/*!40000 ALTER TABLE `article_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chirp`
--

DROP TABLE IF EXISTS `chirp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chirp` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `postedMoment` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_n6v0v2dwnaeokhvh1spb6qfui` (`postedMoment`,`title`,`description`,`user_id`),
  KEY `FK_t10lk4j2g8uw7k7et58ytrp70` (`user_id`),
  CONSTRAINT `FK_t10lk4j2g8uw7k7et58ytrp70` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chirp`
--

LOCK TABLES `chirp` WRITE;
/*!40000 ALTER TABLE `chirp` DISABLE KEYS */;
INSERT INTO `chirp` VALUES (305,0,'description chirp 1','2017-10-21','title chirp 1 cialis',288),(306,0,'description chirp 2','2018-01-21','title chirp 2 sexo',289),(307,0,'description chirp 3 sex','2018-01-21','title chirp 3',290),(308,0,'description chirp 4','2017-10-21','title chirp 4',288),(309,0,'description chirp 5','2016-01-21','title chirp 5',289),(310,0,'description chirp 6','2018-02-21','title chirp 6',290),(311,0,'description chirp 7','2018-01-21','title chirp 7',291);
/*!40000 ALTER TABLE `chirp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem`
--

DROP TABLE IF EXISTS `configurationsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem`
--

LOCK TABLES `configurationsystem` WRITE;
/*!40000 ALTER TABLE `configurationsystem` DISABLE KEYS */;
INSERT INTO `configurationsystem` VALUES (328,0);
/*!40000 ALTER TABLE `configurationsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configurationsystem_tabooword`
--

DROP TABLE IF EXISTS `configurationsystem_tabooword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationsystem_tabooword` (
  `ConfigurationSystem_id` int(11) NOT NULL,
  `tabooWords_id` int(11) NOT NULL,
  UNIQUE KEY `UK_atoy8roxhv9akeamxs61hujxr` (`tabooWords_id`),
  KEY `FK_4uqlduhykfkaeldgil1dx4ehl` (`ConfigurationSystem_id`),
  CONSTRAINT `FK_4uqlduhykfkaeldgil1dx4ehl` FOREIGN KEY (`ConfigurationSystem_id`) REFERENCES `configurationsystem` (`id`),
  CONSTRAINT `FK_atoy8roxhv9akeamxs61hujxr` FOREIGN KEY (`tabooWords_id`) REFERENCES `tabooword` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationsystem_tabooword`
--

LOCK TABLES `configurationsystem_tabooword` WRITE;
/*!40000 ALTER TABLE `configurationsystem_tabooword` DISABLE KEYS */;
INSERT INTO `configurationsystem_tabooword` VALUES (328,329),(328,330),(328,331),(328,332);
/*!40000 ALTER TABLE `configurationsystem_tabooword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwmktpkay2yx7v00mrwmuscl8` (`userAccount_id`),
  KEY `CustomerUK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_pwmktpkay2yx7v00mrwmuscl8` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (292,0,'customer1@acmenewspaper.com','customer 1','+34617557203','postal Adress customer 1','surname customer 1',279),(293,0,'customer2@acmenewspaper.com','customer 2','+34652582643','postal Adress customer 2','surname customer 2',280),(294,0,'customer3@acmenewspaper.com','customer 3','+34667437865','postal Adress customer 3','surname customer 3',281),(295,0,'customer4@acmenewspaper.com','customer 4','+34667437865','postal Adress customer 4','surname customer 4',282);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followup`
--

DROP TABLE IF EXISTS `followup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followup` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `publicationMoment` date DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `article_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_aer0q20rslre6418yqlfwmeek` (`article_id`),
  CONSTRAINT `FK_aer0q20rslre6418yqlfwmeek` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followup`
--

LOCK TABLES `followup` WRITE;
/*!40000 ALTER TABLE `followup` DISABLE KEYS */;
INSERT INTO `followup` VALUES (313,0,'2017-10-21','summary 2','text followUp2','title followUp 2',312),(318,0,'2017-10-21','summary 3','text followUp3','title followUp 3',317),(320,0,'2017-10-21','summary 3','text followUp4','title followUp 4',319),(322,0,'2017-10-21','summary 5','text followUp5','title followUp 5',321),(338,0,'2017-10-21','summary 1','text 1','title followUp 1',327);
/*!40000 ALTER TABLE `followup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followup_pictures`
--

DROP TABLE IF EXISTS `followup_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `followup_pictures` (
  `FollowUp_id` int(11) NOT NULL,
  `pictures` varchar(255) DEFAULT NULL,
  KEY `FK_be66suxjlinls1y3yymi3tc0d` (`FollowUp_id`),
  CONSTRAINT `FK_be66suxjlinls1y3yymi3tc0d` FOREIGN KEY (`FollowUp_id`) REFERENCES `followup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followup_pictures`
--

LOCK TABLES `followup_pictures` WRITE;
/*!40000 ALTER TABLE `followup_pictures` DISABLE KEYS */;
INSERT INTO `followup_pictures` VALUES (313,'http://creativo.site/sodeme/wp-content/uploads/2016/03/articulos.jpg'),(313,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXbsNo-dfvsa40IwGLZ5Cfn4msRIG1LtEzM_Vm0lOnTRsk6B0M'),(318,'http://creativo.site/sodeme/wp-content/uploads/2016/03/articulos.jpg'),(318,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXbsNo-dfvsa40IwGLZ5Cfn4msRIG1LtEzM_Vm0lOnTRsk6B0M'),(320,'http://creativo.site/sodeme/wp-content/uploads/2016/03/articulos.jpg'),(320,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXbsNo-dfvsa40IwGLZ5Cfn4msRIG1LtEzM_Vm0lOnTRsk6B0M'),(322,'http://creativo.site/sodeme/wp-content/uploads/2016/03/articulos.jpg'),(322,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXbsNo-dfvsa40IwGLZ5Cfn4msRIG1LtEzM_Vm0lOnTRsk6B0M'),(338,'http://creativo.site/sodeme/wp-content/uploads/2016/03/articulos.jpg'),(338,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXbsNo-dfvsa40IwGLZ5Cfn4msRIG1LtEzM_Vm0lOnTRsk6B0M');
/*!40000 ALTER TABLE `followup_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('DomainEntity',1);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newspaper`
--

DROP TABLE IF EXISTS `newspaper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newspaper` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `open` bit(1) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `publicationDate` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `publisher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_trr5i0tpor3p4h2hubeeiyct6` (`open`,`publicationDate`,`title`,`description`,`publisher_id`),
  KEY `FK_6w4ywp7unfjqi2kflvm35ie1g` (`publisher_id`),
  CONSTRAINT `FK_6w4ywp7unfjqi2kflvm35ie1g` FOREIGN KEY (`publisher_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newspaper`
--

LOCK TABLES `newspaper` WRITE;
/*!40000 ALTER TABLE `newspaper` DISABLE KEYS */;
INSERT INTO `newspaper` VALUES (296,0,'description newspaper 1','\0','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg','2017-09-21','title newspaper 1',288),(297,0,'description newspaper 2 sex','','https://supergracioso.com/wp-content/uploads/2015/05/Errores-en-peri%C3%B3dicos-%C2%A1INCRE%C3%8DBLES-Parte-I.jpg',NULL,'title newspaper 2',288),(298,0,'description newspaper 3','','https://supergracioso.com/wp-content/uploads/2015/05/Errores-en-peri%C3%B3dicos-%C2%A1INCRE%C3%8DBLES-Parte-I.jpg',NULL,'title accidente newspaper 3',288),(299,0,'description newspaper 4','\0','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg','2017-09-21','title newspaper 4',290),(300,0,'description newspaper 5 cialis','','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg','2017-09-21','title newspaper 5',291),(301,0,'description amanecer newspaper 6','','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg','2017-09-21','title newspaper 6',289),(302,0,'description newspaper 7','','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg','2017-09-21','title terror newspaper 7',290),(303,0,'description newspaper 8','\0','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg','2017-09-21','title newspaper 8',289),(304,0,'description newspaper 9','\0','https://www.cerebriti.com/uploads/0b8d55790024b6a75aeb4d9772ae2706.jpg',NULL,'title newspaper 9',291);
/*!40000 ALTER TABLE `newspaper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brandName` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `expirationMonth` varchar(255) DEFAULT NULL,
  `expirationYear` varchar(255) DEFAULT NULL,
  `holderName` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `customer_id` int(11) NOT NULL,
  `newspaper_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_nc1vf3np9y553nvhx2n6mxd1m` (`customer_id`,`newspaper_id`),
  KEY `FK_b3d3q413vlktogdjnnus3ep9e` (`newspaper_id`),
  CONSTRAINT `FK_b3d3q413vlktogdjnnus3ep9e` FOREIGN KEY (`newspaper_id`) REFERENCES `newspaper` (`id`),
  CONSTRAINT `FK_2i5w4btb7x3r6d2jsd213aqct` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (334,0,'Brand name 1',123,'06','20','holder name 1','4388576018410707',292,296),(335,0,'Brand name 2',123,'01','21','holder name 2','4388576018410707',293,296),(336,0,'Brand name 3',614,'01','19','holder name 3','4388576018410707',294,296),(337,0,'Brand name 4',614,'01','19','holder name 4','4388576018410707',295,299);
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabooword`
--

DROP TABLE IF EXISTS `tabooword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tabooword` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `default_word` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabooword`
--

LOCK TABLES `tabooword` WRITE;
/*!40000 ALTER TABLE `tabooword` DISABLE KEYS */;
INSERT INTO `tabooword` VALUES (329,0,'','sex'),(330,0,'','sexo'),(331,0,'','viagra'),(332,0,'','cialis'),(333,0,'\0','cialis 1');
/*!40000 ALTER TABLE `tabooword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `postalAddress` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`),
  KEY `UserUK_cgls5lrufx91ufsyh467spwa3` (`userAccount_id`),
  CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (288,0,'user1@acmenewspaper.com','user 1','+34617557203','postal Adress user 1','surname user 1',283),(289,0,'user2@acmenewspaper.com','user 2','+34617557203','postal Adress user 2','surname user 2',284),(290,0,'user3@acmenewspaper.com','user 3','+34648213455','postal Adress user 3','surname user 3',285),(291,0,'user4@acmenewspaper.com','user 4','+34648213455','postal Adress user 4','surname user 4',286);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_user`
--

DROP TABLE IF EXISTS `user_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_user` (
  `followers_id` int(11) NOT NULL,
  `followed_id` int(11) NOT NULL,
  KEY `FK_fkljans6a6pux89xnqbfcw3ho` (`followed_id`),
  KEY `FK_ipxcfus1p41qgn9xbfhg2aa0r` (`followers_id`),
  CONSTRAINT `FK_ipxcfus1p41qgn9xbfhg2aa0r` FOREIGN KEY (`followers_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_fkljans6a6pux89xnqbfcw3ho` FOREIGN KEY (`followed_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_user`
--

LOCK TABLES `user_user` WRITE;
/*!40000 ALTER TABLE `user_user` DISABLE KEYS */;
INSERT INTO `user_user` VALUES (288,289),(288,290),(288,291),(289,288),(289,290),(289,291),(290,288),(290,289),(290,291);
/*!40000 ALTER TABLE `user_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (278,0,'21232f297a57a5a743894a0e4a801fc3','admin'),(279,0,'ffbc4675f864e0e9aab8bdf7a0437010','customer1'),(280,0,'5ce4d191fd14ac85a1469fb8c29b7a7b','customer2'),(281,0,'033f7f6121501ae98285ad77f216d5e7','customer3'),(282,0,'55feb130be438e686ad6a80d12dd8f44','customer4'),(283,0,'24c9e15e52afc47c225b757e7bee1f9d','user1'),(284,0,'7e58d63b60197ceb55a1c487989a3720','user2'),(285,0,'92877af70a45fd6a2ed7fe81e1236b78','user3'),(286,0,'3f02ebe3d7929b091e3d8ccfde2f3bc6','user4');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount_authorities`
--

DROP TABLE IF EXISTS `useraccount_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `useraccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`),
  CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `useraccount` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount_authorities`
--

LOCK TABLES `useraccount_authorities` WRITE;
/*!40000 ALTER TABLE `useraccount_authorities` DISABLE KEYS */;
INSERT INTO `useraccount_authorities` VALUES (278,'ADMIN'),(279,'CUSTOMER'),(280,'CUSTOMER'),(281,'CUSTOMER'),(282,'CUSTOMER'),(283,'USER'),(284,'USER'),(285,'USER'),(286,'USER');
/*!40000 ALTER TABLE `useraccount_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-11 15:33:38
commit;
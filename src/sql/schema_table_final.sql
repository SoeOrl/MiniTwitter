SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `twitterdbTEST`;
-- -----------------------------------------------------
-- Schema twitterdb
-- -----------------------------------------------------
CREATE SCHEMA `twitterdbTEST` DEFAULT CHARACTER SET utf8mb4 ;
USE `twitterdbTEST` ;

CREATE TABLE `user` (
  `userID` mediumint(9) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(45) NOT NULL,
  `birthdate` date NOT NULL,
  `questionNo` mediumint(9) NOT NULL,
  `answer` varchar(45) NOT NULL,
  `profilePicture` varchar(200) DEFAULT NULL,
  `lastLogin` timestamp NULL DEFAULT NULL,
  `salt` varchar(64) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `twit` (
  `twitId` mediumint(9) NOT NULL AUTO_INCREMENT,
  `userId` mediumint(9) NOT NULL,
  `twit` varchar(300) NOT NULL,
  `postedDateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`twitId`),
  KEY `userId` (`userId`),
  CONSTRAINT `twit_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `usermention` (
  `mentionId` mediumint(9) NOT NULL AUTO_INCREMENT,
  `originUserId` mediumint(9) NOT NULL,
  `mentionedUserId` mediumint(9) NOT NULL,
  `twitId` mediumint(9) NOT NULL,
  PRIMARY KEY (`mentionId`),
  KEY `originUserId` (`originUserId`),
  KEY `mentionedUserId` (`mentionedUserId`),
  KEY `twitId` (`twitId`),
  CONSTRAINT `usermention_ibfk_1` FOREIGN KEY (`originUserId`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  CONSTRAINT `usermention_ibfk_2` FOREIGN KEY (`mentionedUserId`) REFERENCES `user` (`userid`),
  CONSTRAINT `usermention_ibfk_3` FOREIGN KEY (`twitId`) REFERENCES `twit` (`twitid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `follows` (
  `userId` mediumint(9) NOT NULL,
  `followedId` mediumint(9) NOT NULL,
  `followDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `followsUserID_idx` (`userId`),
  KEY `followedUSerId_idx` (`followedId`),
  CONSTRAINT `followedUSerId` FOREIGN KEY (`followedId`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `followsUserId` FOREIGN KEY (`userId`) REFERENCES `user` (`userid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
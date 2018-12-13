SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `twitterdb`;
-- -----------------------------------------------------
-- Schema twitterdb
-- -----------------------------------------------------
CREATE SCHEMA `twitterdb` DEFAULT CHARACTER SET utf8mb4 ;
USE `twitterdb` ;

CREATE TABLE `user` (
  `userID` varchar(36) NOT NULL,
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
  UNIQUE KEY `userID` (`userID`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `twit` (
  `twitId` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `twit` varchar(1000) NOT NULL,
  `postedDateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`twitId`),
  UNIQUE KEY `twitId` (`twitId`),
  KEY `userId` (`userId`),
  CONSTRAINT `twit_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `usermention` (
  `mentionId` varchar(36) NOT NULL,
  `originUserId` varchar(36) NOT NULL,
  `mentionedUserId` varchar(36) NOT NULL,
  `twitId` varchar(36) NOT NULL,
  PRIMARY KEY (`mentionId`),
  UNIQUE KEY `mentionId` (`mentionId`),
  KEY `originUserId` (`originUserId`),
  KEY `mentionedUserId` (`mentionedUserId`),
  KEY `twitId` (`twitId`),
  CONSTRAINT `usermention_ibfk_1` FOREIGN KEY (`originUserId`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  CONSTRAINT `usermention_ibfk_2` FOREIGN KEY (`mentionedUserId`) REFERENCES `user` (`userid`),
  CONSTRAINT `usermention_ibfk_3` FOREIGN KEY (`twitId`) REFERENCES `twit` (`twitid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `follows` (
  `userId` varchar(36) NOT NULL,
  `followedId` varchar(36) NOT NULL,
  `followDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `followedId` (`followedId`),
  KEY `userId` (`userId`),
  CONSTRAINT `follows_ibfk_1` FOREIGN KEY (`followedId`) REFERENCES `user` (`userid`) ON DELETE CASCADE,
  CONSTRAINT `follows_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hashtag` (
  `hashtagId` varchar(36) NOT NULL,
  `text` varchar(45) NOT NULL,
  `timesUsed` mediumint(9) NOT NULL DEFAULT '0',
  PRIMARY KEY (`hashtagId`),
  UNIQUE KEY `hashtagId` (`hashtagId`),
  UNIQUE KEY `text` (`text`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `twitHashtag` (
  `twitHashtagId` varchar(36) NOT NULL,
  `twitId` varchar(36) NOT NULL,
  `hashtagId` varchar(36) NOT NULL,
  PRIMARY KEY (`twitHashtagId`),
  UNIQUE KEY `twitHashtagId` (`twitHashtagId`),
  KEY `twitId` (`twitId`),
  KEY `hashtagId` (`hashtagId`),
  CONSTRAINT `twithashtag_ibfk_1` FOREIGN KEY (`twitId`) REFERENCES `twit` (`twitid`) ON DELETE CASCADE,
  CONSTRAINT `twithashtag_ibfk_2` FOREIGN KEY (`hashtagId`) REFERENCES `hashtag` (`hashtagid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
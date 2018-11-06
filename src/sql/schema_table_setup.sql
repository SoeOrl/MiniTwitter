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
  `userID` mediumint NOT NULL AUTO_INCREMENT,
  `fullname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL UNIQUE,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL UNIQUE,
  `birthdate` date NOT NULL,
  `questionNo` mediumint NOT NULL,
  `answer` varchar(45) NOT NULL,
  `profilePicture` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET utf8mb4;

CREATE TABLE `twit` (
  `twitId` mediumint NOT NULL AUTO_INCREMENT,
  `userId` mediumint NOT NULL,
  `twit` varchar(300) NOT NULL,
  `postedDateTime` timestamp DEFAULT CURRENT_TIMESTAMP, 
  PRIMARY KEY (`twitId`),
  FOREIGN KEY  (`userId`) 
    REFERENCES user(`userId`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET utf8mb4;

CREATE TABLE `userMention` (
  `mentionId` mediumint NOT NULL AUTO_INCREMENT,
  `originUserId` mediumint NOT NULL,
  `mentionedUserId` mediumint NOT NULL,
  `twitId` mediumint NOT NULL,
  PRIMARY KEY (`mentionId`),
  FOREIGN KEY (`originUserId`) 
    REFERENCES user(`userId`)
    ON DELETE CASCADE,
  FOREIGN KEY (`mentionedUserId`) REFERENCES user(`userId`),
  FOREIGN KEY (`twitId`) 
    REFERENCES twit(`twitId`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET utf8mb4;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
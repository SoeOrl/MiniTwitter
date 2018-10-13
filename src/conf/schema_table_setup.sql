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
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL UNIQUE,
  `emailAddress` varchar(45) NOT NULL UNIQUE,
  `birthdate` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `questionNo` int(11) NOT NULL,
  `answer` varchar(45) NOT NULL,
  `profilePicture` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET utf8mb4;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
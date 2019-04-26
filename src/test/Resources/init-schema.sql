#DROP SCHEMA IF EXISTS 'toutiao';
#CREATE SCHEMA `toutiao` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `toutiao`.`user` (
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL DEFAULT '',
  `password` VARCHAR(128) NOT NULL DEFAULT '',
  `salt` VARCHAR(32) NOT NULL DEFAULT '',
  `head_url` VARCHAR(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `toutiao`.`news` (
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(128) NOT NULL DEFAULT '',
  `link` VARCHAR(128) NOT NULL DEFAULT '',
  `image` VARCHAR(256) NOT NULL DEFAULT '',
  `like_count` INT NOT NULL,
  `comment_count` INT NOT NULL,
  `created_date` datetime NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `toutiao`.`login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `ticket` varchar(45) COLLATE utf8_bin NOT NULL,
  `expired` datetime NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

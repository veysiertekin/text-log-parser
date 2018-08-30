CREATE SCHEMA IF NOT EXISTS test;
USE test;

CREATE TABLE IF NOT EXISTS  `log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip` varchar(15) NOT NULL DEFAULT '',
  `request` text NOT NULL,
  `status` int(11) NOT NULL,
  `userAgent` varchar(512) NOT NULL DEFAULT '',
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `logs_ip_index` (`ip`),
  KEY `logs_date_index` (`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `blocked_ip` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) NOT NULL DEFAULT '',
  `count` int(11) NOT NULL,
  `comment` varchar(250) NOT NULL DEFAULT '',
  `insertDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `blocked_ip_index` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
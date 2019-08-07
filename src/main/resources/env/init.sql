CREATE DATABASE `coffee` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `register_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `device` varchar(255) NOT NULL DEFAULT '',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_key` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `app_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `en_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `category_element` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `star` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `star_element` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `star_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`star_id`) REFERENCES `star` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `random` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `random_element` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `random_id` int(11) NOT NULL,
  `content` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`random_id`) REFERENCES `random` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `upload_element` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `print_box` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `left_time` int(11) NOT NULL DEFAULT 0,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_key` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-----

INSERT INTO `category` (`id`, `name`)
VALUES
	(1, '体育'),
	(2, '动漫'),
	(3, '文字'),
	(4, '潮流'),
	(5, '明星'),
	(6, '小清新'),
	(7, '节日');

INSERT INTO `random` (`id`, `name`)
VALUES
	(1, '综合'),
	(2, '恶搞'),
	(3, '爱情'),
	(4, '表情'),
	(5, '事业'),
	(6, '健康');

INSERT INTO `star` (`id`, `name`)
VALUES
	(1, '白羊座'),
	(2, '金牛座'),
	(3, '双子座'),
	(4, '巨蟹座'),
	(5, '狮子座'),
	(6, '处女座'),
	(7, '天秤座'),
	(8, '天蝎座'),
	(9, '射手座'),
	(10, '魔蝎座'),
	(11, '水瓶座'),
	(12, '双鱼座');

-----

alter table `category` add `en_name` varchar(255) NOT NULL;
CREATE DATABASE `foodlog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
CREATE TABLE `fooditem` (
  `name` varchar(255) NOT NULL,
  `carbs` double NOT NULL,
  `fats` double NOT NULL,
  `fibre` double NOT NULL,
  `protein` double NOT NULL,
  `saturates` double NOT NULL,
  `sodium` double NOT NULL,
  `sugars` double NOT NULL,
  `units` varchar(255) DEFAULT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1009 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `meal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mealDateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `meal_mealfooditem` (
  `meal_id` bigint(20) NOT NULL,
  `mealfooditem_id` bigint(20) NOT NULL,
  PRIMARY KEY (`meal_id`,`mealfooditem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `mealfooditem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity` double DEFAULT NULL,
  `fooditem_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


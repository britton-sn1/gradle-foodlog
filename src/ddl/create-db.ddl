CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


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
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `meal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mealDateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `meal_fooditem` (
   `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `fooditem` varchar(255) NOT NULL,
   `meal` bigint(20),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


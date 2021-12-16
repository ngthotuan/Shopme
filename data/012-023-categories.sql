/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : shopmedb

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 16/12/2021 17:06:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT,
    `name`           varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `alias`          varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `image`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    `enabled`        bit(1)                                                        NOT NULL,
    `parent_id`      bigint                                                        NULL DEFAULT NULL,
    `all_parent_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `UK_jx1ptm0r46dop8v0o7nmgfbeq` (`alias`) USING BTREE,
    UNIQUE INDEX `UK_t8o6pivur7nn124jehx7cygw5` (`name`) USING BTREE,
    INDEX `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`) USING BTREE,
    CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories`
VALUES (1, 'Electronics', 'electronics', 'electronics.png', b'1', NULL, NULL);
INSERT INTO `categories`
VALUES (2, 'Camera & Photo', 'camera', 'camera.jpg', b'1', 1, '-1-');
INSERT INTO `categories`
VALUES (3, 'Computers', 'computers', 'computers.png', b'1', NULL, NULL);
INSERT INTO `categories`
VALUES (4, 'Cell Phones & Accessories', 'cellphones', 'cellphones.png', b'1', 1, '-1-');
INSERT INTO `categories`
VALUES (5, 'Desktops', 'desktop_computers', 'desktop computers.png', b'1', 3, '-3-');
INSERT INTO `categories`
VALUES (6, 'Laptops', 'laptop_computers', 'laptop computers.png', b'1', 3, '-3-');
INSERT INTO `categories`
VALUES (7, 'Tablets', 'tablet_computers', 'tablets.png', b'1', 3, '-3-');
INSERT INTO `categories`
VALUES (8, 'Computer Components', 'computer_components', 'computer components.png', b'1', 3, '-3-');
INSERT INTO `categories`
VALUES (9, 'Bags & Cases', 'camera_bags_cases', 'bags_cases.png', b'1', 2, '-1-2-');
INSERT INTO `categories`
VALUES (10, 'Digital Cameras', 'digital_cameras', 'digital cameras.png', b'1', 2, '-1-2-');
INSERT INTO `categories`
VALUES (11, 'Flashes', 'camera_flashes', 'flashes.png', b'1', 2, '-1-2-');
INSERT INTO `categories`
VALUES (12, 'Lenses', 'camera_lenses', 'lenses.png', b'1', 2, '-1-2-');
INSERT INTO `categories`
VALUES (13, 'Tripods & Monopods', 'camera_tripods_monopods', 'tripods_monopods.png', b'1', 2, '-1-2-');
INSERT INTO `categories`
VALUES (14, 'Carrier Cell Phones', 'carrier_cellphones', 'carrier cellphones.png', b'1', 4, '-1-4-');
INSERT INTO `categories`
VALUES (15, 'Unlocked Cell Phones', 'unlocked_cellphones', 'unlocked cellphones.png', b'1', 4, '-1-4-');
INSERT INTO `categories`
VALUES (16, 'Accessories', 'cellphone_accessories', 'cellphone accessories.png', b'1', 4, '-1-4-');
INSERT INTO `categories`
VALUES (17, 'Cables & Adapters', 'cellphone_cables_adapters', 'cables and adapters.png', b'1', 16, '-1-4-16-');
INSERT INTO `categories`
VALUES (18, 'MicroSD Cards', 'microsd_cards', 'microsd cards.png', b'1', 16, '-1-4-16-');
INSERT INTO `categories`
VALUES (19, 'Stands', 'cellphone_stands', 'cellphone_stands.png', b'1', 16, '-1-4-16-');
INSERT INTO `categories`
VALUES (20, 'Cases', 'cellphone_cases', 'cellphone cases.png', b'1', 16, '-1-4-16-');
INSERT INTO `categories`
VALUES (21, 'Headphones', 'headphones', 'headphones.png', b'1', 16, '-1-4-16-');
INSERT INTO `categories`
VALUES (22, 'CPU Processors Unit', 'computer_processors', 'computer processors.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (23, 'Graphic Cards', 'computer_graphic_cards', 'graphic cards.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (24, 'Internal Hard Drives', 'hard_drive', 'internal hard drive.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (25, 'Internal Optical Drives', 'computer_optical_drives', 'internal optical drives.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (26, 'Power Supplies', 'computer_power_supplies', 'power supplies.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (27, 'Solid State Drives', 'solid_state_drives', 'solid state drives.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (28, 'Sound Cards', 'computer_sound_cards', 'sound cards.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (29, 'Memory', 'computer_memory', 'computer memory.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (30, 'Motherboard', 'computer_motherboard', 'motherboards.png', b'1', 8, '-3-8-');
INSERT INTO `categories`
VALUES (31, 'Network Cards', 'computer_network_cards', 'network cards.png', b'1', 8, '-3-8-');

SET FOREIGN_KEY_CHECKS = 1;

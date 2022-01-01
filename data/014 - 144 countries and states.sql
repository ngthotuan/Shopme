/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : shopme

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 31/12/2021 22:54:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for countries
-- ----------------------------
DROP TABLE IF EXISTS `countries`;
CREATE TABLE `countries`
(
    `id`   bigint                                                       NOT NULL AUTO_INCREMENT,
    `code` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL,
    `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 251
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of countries
-- ----------------------------
INSERT INTO `countries`
VALUES (1, 'AD', 'Andorra');
INSERT INTO `countries`
VALUES (2, 'AE', 'United Arab Emirates');
INSERT INTO `countries`
VALUES (3, 'AF', 'Afghanistan');
INSERT INTO `countries`
VALUES (4, 'AG', 'Antigua and Barbuda');
INSERT INTO `countries`
VALUES (5, 'AI', 'Anguilla');
INSERT INTO `countries`
VALUES (6, 'AL', 'Albania');
INSERT INTO `countries`
VALUES (7, 'AM', 'Armenia');
INSERT INTO `countries`
VALUES (8, 'AN', 'Netherlands Antilles');
INSERT INTO `countries`
VALUES (9, 'AO', 'Angola');
INSERT INTO `countries`
VALUES (10, 'AQ', 'Antarctica');
INSERT INTO `countries`
VALUES (11, 'AR', 'Argentina');
INSERT INTO `countries`
VALUES (12, 'AS', 'American Samoa');
INSERT INTO `countries`
VALUES (13, 'AT', 'Austria');
INSERT INTO `countries`
VALUES (14, 'AU', 'Australia');
INSERT INTO `countries`
VALUES (15, 'AW', 'Aruba');
INSERT INTO `countries`
VALUES (16, 'AX', 'Åland Islands');
INSERT INTO `countries`
VALUES (17, 'AZ', 'Azerbaijan');
INSERT INTO `countries`
VALUES (18, 'BA', 'Bosnia and Herzegovina');
INSERT INTO `countries`
VALUES (19, 'BB', 'Barbados');
INSERT INTO `countries`
VALUES (20, 'BD', 'Bangladesh');
INSERT INTO `countries`
VALUES (21, 'BE', 'Belgium');
INSERT INTO `countries`
VALUES (22, 'BF', 'Burkina Faso');
INSERT INTO `countries`
VALUES (23, 'BG', 'Bulgaria');
INSERT INTO `countries`
VALUES (24, 'BH', 'Bahrain');
INSERT INTO `countries`
VALUES (25, 'BI', 'Burundi');
INSERT INTO `countries`
VALUES (26, 'BJ', 'Benin');
INSERT INTO `countries`
VALUES (27, 'BL', 'Saint Barthélemy');
INSERT INTO `countries`
VALUES (28, 'BM', 'Bermuda');
INSERT INTO `countries`
VALUES (29, 'BN', 'Brunei');
INSERT INTO `countries`
VALUES (30, 'BO', 'Bolivia');
INSERT INTO `countries`
VALUES (31, 'BQ', 'Bonaire, Sint Eustatius and Saba');
INSERT INTO `countries`
VALUES (32, 'BR', 'Brazil');
INSERT INTO `countries`
VALUES (33, 'BS', 'Bahamas');
INSERT INTO `countries`
VALUES (34, 'BT', 'Bhutan');
INSERT INTO `countries`
VALUES (35, 'BV', 'Bouvet Island');
INSERT INTO `countries`
VALUES (36, 'BW', 'Botswana');
INSERT INTO `countries`
VALUES (37, 'BY', 'Belarus');
INSERT INTO `countries`
VALUES (38, 'BZ', 'Belize');
INSERT INTO `countries`
VALUES (39, 'CA', 'Canada');
INSERT INTO `countries`
VALUES (40, 'CC', 'Cocos Islands');
INSERT INTO `countries`
VALUES (41, 'CD', 'The Democratic Republic Of Congo');
INSERT INTO `countries`
VALUES (42, 'CF', 'Central African Republic');
INSERT INTO `countries`
VALUES (43, 'CG', 'Congo');
INSERT INTO `countries`
VALUES (44, 'CH', 'Switzerland');
INSERT INTO `countries`
VALUES (45, 'CI', 'Côte d\'Ivoire');
INSERT INTO `countries`
VALUES (46, 'CK', 'Cook Islands');
INSERT INTO `countries`
VALUES (47, 'CL', 'Chile');
INSERT INTO `countries`
VALUES (48, 'CM', 'Cameroon');
INSERT INTO `countries`
VALUES (49, 'CN', 'China');
INSERT INTO `countries`
VALUES (50, 'CO', 'Colombia');
INSERT INTO `countries`
VALUES (51, 'CR', 'Costa Rica');
INSERT INTO `countries`
VALUES (52, 'CU', 'Cuba');
INSERT INTO `countries`
VALUES (53, 'CV', 'Cape Verde');
INSERT INTO `countries`
VALUES (54, 'CW', 'Curaçao');
INSERT INTO `countries`
VALUES (55, 'CX', 'Christmas Island');
INSERT INTO `countries`
VALUES (56, 'CY', 'Cyprus');
INSERT INTO `countries`
VALUES (57, 'CZ', 'Czech Republic');
INSERT INTO `countries`
VALUES (58, 'DE', 'Germany');
INSERT INTO `countries`
VALUES (59, 'DJ', 'Djibouti');
INSERT INTO `countries`
VALUES (60, 'DK', 'Denmark');
INSERT INTO `countries`
VALUES (61, 'DM', 'Dominica');
INSERT INTO `countries`
VALUES (62, 'DO', 'Dominican Republic');
INSERT INTO `countries`
VALUES (63, 'DZ', 'Algeria');
INSERT INTO `countries`
VALUES (64, 'EC', 'Ecuador');
INSERT INTO `countries`
VALUES (65, 'EE', 'Estonia');
INSERT INTO `countries`
VALUES (66, 'EG', 'Egypt');
INSERT INTO `countries`
VALUES (67, 'EH', 'Western Sahara');
INSERT INTO `countries`
VALUES (68, 'ER', 'Eritrea');
INSERT INTO `countries`
VALUES (69, 'ES', 'Spain');
INSERT INTO `countries`
VALUES (70, 'ET', 'Ethiopia');
INSERT INTO `countries`
VALUES (71, 'FI', 'Finland');
INSERT INTO `countries`
VALUES (72, 'FJ', 'Fiji');
INSERT INTO `countries`
VALUES (73, 'FK', 'Falkland Islands');
INSERT INTO `countries`
VALUES (74, 'FM', 'Micronesia');
INSERT INTO `countries`
VALUES (75, 'FO', 'Faroe Islands');
INSERT INTO `countries`
VALUES (76, 'FR', 'France');
INSERT INTO `countries`
VALUES (77, 'GA', 'Gabon');
INSERT INTO `countries`
VALUES (78, 'GB', 'United Kingdom');
INSERT INTO `countries`
VALUES (79, 'GD', 'Grenada');
INSERT INTO `countries`
VALUES (80, 'GE', 'Georgia');
INSERT INTO `countries`
VALUES (81, 'GF', 'French Guiana');
INSERT INTO `countries`
VALUES (82, 'GG', 'Guernsey');
INSERT INTO `countries`
VALUES (83, 'GH', 'Ghana');
INSERT INTO `countries`
VALUES (84, 'GI', 'Gibraltar');
INSERT INTO `countries`
VALUES (85, 'GL', 'Greenland');
INSERT INTO `countries`
VALUES (86, 'GM', 'Gambia');
INSERT INTO `countries`
VALUES (87, 'GN', 'Guinea');
INSERT INTO `countries`
VALUES (88, 'GP', 'Guadeloupe');
INSERT INTO `countries`
VALUES (89, 'GQ', 'Equatorial Guinea');
INSERT INTO `countries`
VALUES (90, 'GR', 'Greece');
INSERT INTO `countries`
VALUES (91, 'GS', 'South Georgia And The South Sandwich Islands');
INSERT INTO `countries`
VALUES (92, 'GT', 'Guatemala');
INSERT INTO `countries`
VALUES (93, 'GU', 'Guam');
INSERT INTO `countries`
VALUES (94, 'GW', 'Guinea-Bissau');
INSERT INTO `countries`
VALUES (95, 'GY', 'Guyana');
INSERT INTO `countries`
VALUES (96, 'HK', 'Hong Kong');
INSERT INTO `countries`
VALUES (97, 'HM', 'Heard Island And McDonald Islands');
INSERT INTO `countries`
VALUES (98, 'HN', 'Honduras');
INSERT INTO `countries`
VALUES (99, 'HR', 'Croatia');
INSERT INTO `countries`
VALUES (100, 'HT', 'Haiti');
INSERT INTO `countries`
VALUES (101, 'HU', 'Hungary');
INSERT INTO `countries`
VALUES (102, 'ID', 'Indonesia');
INSERT INTO `countries`
VALUES (103, 'IE', 'Ireland');
INSERT INTO `countries`
VALUES (104, 'IL', 'Israel');
INSERT INTO `countries`
VALUES (105, 'IM', 'Isle Of Man');
INSERT INTO `countries`
VALUES (106, 'IN', 'India');
INSERT INTO `countries`
VALUES (107, 'IO', 'British Indian Ocean Territory');
INSERT INTO `countries`
VALUES (108, 'IQ', 'Iraq');
INSERT INTO `countries`
VALUES (109, 'IR', 'Iran');
INSERT INTO `countries`
VALUES (110, 'IS', 'Iceland');
INSERT INTO `countries`
VALUES (111, 'IT', 'Italy');
INSERT INTO `countries`
VALUES (112, 'JE', 'Jersey');
INSERT INTO `countries`
VALUES (113, 'JM', 'Jamaica');
INSERT INTO `countries`
VALUES (114, 'JO', 'Jordan');
INSERT INTO `countries`
VALUES (115, 'JP', 'Japan');
INSERT INTO `countries`
VALUES (116, 'KE', 'Kenya');
INSERT INTO `countries`
VALUES (117, 'KG', 'Kyrgyzstan');
INSERT INTO `countries`
VALUES (118, 'KH', 'Cambodia');
INSERT INTO `countries`
VALUES (119, 'KI', 'Kiribati');
INSERT INTO `countries`
VALUES (120, 'KM', 'Comoros');
INSERT INTO `countries`
VALUES (121, 'KN', 'Saint Kitts And Nevis');
INSERT INTO `countries`
VALUES (122, 'KP', 'North Korea');
INSERT INTO `countries`
VALUES (123, 'KR', 'South Korea');
INSERT INTO `countries`
VALUES (124, 'KW', 'Kuwait');
INSERT INTO `countries`
VALUES (125, 'KY', 'Cayman Islands');
INSERT INTO `countries`
VALUES (126, 'KZ', 'Kazakhstan');
INSERT INTO `countries`
VALUES (127, 'LA', 'Laos');
INSERT INTO `countries`
VALUES (128, 'LB', 'Lebanon');
INSERT INTO `countries`
VALUES (129, 'LC', 'Saint Lucia');
INSERT INTO `countries`
VALUES (130, 'LI', 'Liechtenstein');
INSERT INTO `countries`
VALUES (131, 'LK', 'Sri Lanka');
INSERT INTO `countries`
VALUES (132, 'LR', 'Liberia');
INSERT INTO `countries`
VALUES (133, 'LS', 'Lesotho');
INSERT INTO `countries`
VALUES (134, 'LT', 'Lithuania');
INSERT INTO `countries`
VALUES (135, 'LU', 'Luxembourg');
INSERT INTO `countries`
VALUES (136, 'LV', 'Latvia');
INSERT INTO `countries`
VALUES (137, 'LY', 'Libya');
INSERT INTO `countries`
VALUES (138, 'MA', 'Morocco');
INSERT INTO `countries`
VALUES (139, 'MC', 'Monaco');
INSERT INTO `countries`
VALUES (140, 'MD', 'Moldova');
INSERT INTO `countries`
VALUES (141, 'ME', 'Montenegro');
INSERT INTO `countries`
VALUES (142, 'MF', 'Saint Martin');
INSERT INTO `countries`
VALUES (143, 'MG', 'Madagascar');
INSERT INTO `countries`
VALUES (144, 'MH', 'Marshall Islands');
INSERT INTO `countries`
VALUES (145, 'MK', 'Macedonia');
INSERT INTO `countries`
VALUES (146, 'ML', 'Mali');
INSERT INTO `countries`
VALUES (147, 'MM', 'Myanmar');
INSERT INTO `countries`
VALUES (148, 'MN', 'Mongolia');
INSERT INTO `countries`
VALUES (149, 'MO', 'Macao');
INSERT INTO `countries`
VALUES (150, 'MP', 'Northern Mariana Islands');
INSERT INTO `countries`
VALUES (151, 'MQ', 'Martinique');
INSERT INTO `countries`
VALUES (152, 'MR', 'Mauritania');
INSERT INTO `countries`
VALUES (153, 'MS', 'Montserrat');
INSERT INTO `countries`
VALUES (154, 'MT', 'Malta');
INSERT INTO `countries`
VALUES (155, 'MU', 'Mauritius');
INSERT INTO `countries`
VALUES (156, 'MV', 'Maldives');
INSERT INTO `countries`
VALUES (157, 'MW', 'Malawi');
INSERT INTO `countries`
VALUES (158, 'MX', 'Mexico');
INSERT INTO `countries`
VALUES (159, 'MY', 'Malaysia');
INSERT INTO `countries`
VALUES (160, 'MZ', 'Mozambique');
INSERT INTO `countries`
VALUES (161, 'NA', 'Namibia');
INSERT INTO `countries`
VALUES (162, 'NC', 'New Caledonia');
INSERT INTO `countries`
VALUES (163, 'NE', 'Niger');
INSERT INTO `countries`
VALUES (164, 'NF', 'Norfolk Island');
INSERT INTO `countries`
VALUES (165, 'NG', 'Nigeria');
INSERT INTO `countries`
VALUES (166, 'NI', 'Nicaragua');
INSERT INTO `countries`
VALUES (167, 'NL', 'Netherlands');
INSERT INTO `countries`
VALUES (168, 'NO', 'Norway');
INSERT INTO `countries`
VALUES (169, 'NP', 'Nepal');
INSERT INTO `countries`
VALUES (170, 'NR', 'Nauru');
INSERT INTO `countries`
VALUES (171, 'NU', 'Niue');
INSERT INTO `countries`
VALUES (172, 'NZ', 'New Zealand');
INSERT INTO `countries`
VALUES (173, 'OM', 'Oman');
INSERT INTO `countries`
VALUES (174, 'PA', 'Panama');
INSERT INTO `countries`
VALUES (175, 'PE', 'Peru');
INSERT INTO `countries`
VALUES (176, 'PF', 'French Polynesia');
INSERT INTO `countries`
VALUES (177, 'PG', 'Papua New Guinea');
INSERT INTO `countries`
VALUES (178, 'PH', 'Philippines');
INSERT INTO `countries`
VALUES (179, 'PK', 'Pakistan');
INSERT INTO `countries`
VALUES (180, 'PL', 'Poland');
INSERT INTO `countries`
VALUES (181, 'PM', 'Saint Pierre And Miquelon');
INSERT INTO `countries`
VALUES (182, 'PN', 'Pitcairn');
INSERT INTO `countries`
VALUES (183, 'PR', 'Puerto Rico');
INSERT INTO `countries`
VALUES (184, 'PS', 'Palestine');
INSERT INTO `countries`
VALUES (185, 'PT', 'Portugal');
INSERT INTO `countries`
VALUES (186, 'PW', 'Palau');
INSERT INTO `countries`
VALUES (187, 'PY', 'Paraguay');
INSERT INTO `countries`
VALUES (188, 'QA', 'Qatar');
INSERT INTO `countries`
VALUES (189, 'RE', 'Reunion');
INSERT INTO `countries`
VALUES (190, 'RO', 'Romania');
INSERT INTO `countries`
VALUES (191, 'RS', 'Serbia');
INSERT INTO `countries`
VALUES (192, 'RU', 'Russia');
INSERT INTO `countries`
VALUES (193, 'RW', 'Rwanda');
INSERT INTO `countries`
VALUES (194, 'SA', 'Saudi Arabia');
INSERT INTO `countries`
VALUES (195, 'SB', 'Solomon Islands');
INSERT INTO `countries`
VALUES (196, 'SC', 'Seychelles');
INSERT INTO `countries`
VALUES (197, 'SD', 'Sudan');
INSERT INTO `countries`
VALUES (198, 'SE', 'Sweden');
INSERT INTO `countries`
VALUES (199, 'SG', 'Singapore');
INSERT INTO `countries`
VALUES (200, 'SH', 'Saint Helena');
INSERT INTO `countries`
VALUES (201, 'SI', 'Slovenia');
INSERT INTO `countries`
VALUES (202, 'SJ', 'Svalbard And Jan Mayen');
INSERT INTO `countries`
VALUES (203, 'SK', 'Slovakia');
INSERT INTO `countries`
VALUES (204, 'SL', 'Sierra Leone');
INSERT INTO `countries`
VALUES (205, 'SM', 'San Marino');
INSERT INTO `countries`
VALUES (206, 'SN', 'Senegal');
INSERT INTO `countries`
VALUES (207, 'SO', 'Somalia');
INSERT INTO `countries`
VALUES (208, 'SR', 'Suriname');
INSERT INTO `countries`
VALUES (209, 'SS', 'South Sudan');
INSERT INTO `countries`
VALUES (210, 'ST', 'Sao Tome And Principe');
INSERT INTO `countries`
VALUES (211, 'SV', 'El Salvador');
INSERT INTO `countries`
VALUES (212, 'SX', 'Sint Maarten (Dutch part)');
INSERT INTO `countries`
VALUES (213, 'SY', 'Syria');
INSERT INTO `countries`
VALUES (214, 'SZ', 'Swaziland');
INSERT INTO `countries`
VALUES (215, 'TC', 'Turks And Caicos Islands');
INSERT INTO `countries`
VALUES (216, 'TD', 'Chad');
INSERT INTO `countries`
VALUES (217, 'TF', 'French Southern Territories');
INSERT INTO `countries`
VALUES (218, 'TG', 'Togo');
INSERT INTO `countries`
VALUES (219, 'TH', 'Thailand');
INSERT INTO `countries`
VALUES (220, 'TJ', 'Tajikistan');
INSERT INTO `countries`
VALUES (221, 'TK', 'Tokelau');
INSERT INTO `countries`
VALUES (222, 'TL', 'Timor-Leste');
INSERT INTO `countries`
VALUES (223, 'TM', 'Turkmenistan');
INSERT INTO `countries`
VALUES (224, 'TN', 'Tunisia');
INSERT INTO `countries`
VALUES (225, 'TO', 'Tonga');
INSERT INTO `countries`
VALUES (226, 'TR', 'Turkey');
INSERT INTO `countries`
VALUES (227, 'TT', 'Trinidad and Tobago');
INSERT INTO `countries`
VALUES (228, 'TV', 'Tuvalu');
INSERT INTO `countries`
VALUES (229, 'TW', 'Taiwan');
INSERT INTO `countries`
VALUES (230, 'TZ', 'Tanzania');
INSERT INTO `countries`
VALUES (231, 'UA', 'Ukraine');
INSERT INTO `countries`
VALUES (232, 'UG', 'Uganda');
INSERT INTO `countries`
VALUES (233, 'UM', 'United States Minor Outlying Islands');
INSERT INTO `countries`
VALUES (234, 'US', 'United States');
INSERT INTO `countries`
VALUES (235, 'UY', 'Uruguay');
INSERT INTO `countries`
VALUES (236, 'UZ', 'Uzbekistan');
INSERT INTO `countries`
VALUES (237, 'VA', 'Vatican');
INSERT INTO `countries`
VALUES (238, 'VC', 'Saint Vincent And The Grenadines');
INSERT INTO `countries`
VALUES (239, 'VE', 'Venezuela');
INSERT INTO `countries`
VALUES (240, 'VG', 'British Virgin Islands');
INSERT INTO `countries`
VALUES (241, 'VI', 'U.S. Virgin Islands');
INSERT INTO `countries`
VALUES (242, 'VN', 'Vietnam');
INSERT INTO `countries`
VALUES (243, 'VU', 'Vanuatu');
INSERT INTO `countries`
VALUES (244, 'WF', 'Wallis And Futuna');
INSERT INTO `countries`
VALUES (245, 'WS', 'Samoa');
INSERT INTO `countries`
VALUES (246, 'YE', 'Yemen');
INSERT INTO `countries`
VALUES (247, 'YT', 'Mayotte');
INSERT INTO `countries`
VALUES (248, 'ZA', 'South Africa');
INSERT INTO `countries`
VALUES (249, 'ZM', 'Zambia');
INSERT INTO `countries`
VALUES (250, 'ZW', 'Zimbabwe');
INSERT INTO `countries`
VALUES (251, 'GB', 'United Kingdom');

-- ----------------------------
-- Table structure for states
-- ----------------------------
DROP TABLE IF EXISTS `states`;
CREATE TABLE `states`
(
    `id`         bigint                                                       NOT NULL AUTO_INCREMENT,
    `name`       varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `country_id` bigint                                                       NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `FKskkdphjml9vjlrqn4m5hi251y` (`country_id`) USING BTREE,
    CONSTRAINT `FKskkdphjml9vjlrqn4m5hi251y` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 307
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of states
-- ----------------------------
INSERT INTO `states`
VALUES (1, 'Ha Noi', 242);
INSERT INTO `states`
VALUES (2, 'Da Nang', 242);
INSERT INTO `states`
VALUES (3, 'New York', 234);
INSERT INTO `states`
VALUES (4, 'California', 234);
INSERT INTO `states`
VALUES (5, 'Ho Chi Minh City', 242);
INSERT INTO `states`
VALUES (6, 'Hai Phong', 242);
INSERT INTO `states`
VALUES (7, 'Bac Giang', 242);
INSERT INTO `states`
VALUES (8, 'Son La', 242);
INSERT INTO `states`
VALUES (9, 'Quang Ninh', 242);
INSERT INTO `states`
VALUES (10, 'Nghe An', 242);
INSERT INTO `states`
VALUES (12, 'Utah', 234);
INSERT INTO `states`
VALUES (13, 'Washington', 234);
INSERT INTO `states`
VALUES (14, 'Florida', 234);
INSERT INTO `states`
VALUES (15, 'Illinois', 234);
INSERT INTO `states`
VALUES (16, 'Texas', 234);
INSERT INTO `states`
VALUES (18, 'Colorado', 234);
INSERT INTO `states`
VALUES (19, 'Alaska', 234);
INSERT INTO `states`
VALUES (20, 'Can Tho', 242);
INSERT INTO `states`
VALUES (22, 'Binh Duong', 242);
INSERT INTO `states`
VALUES (23, 'Thanh Hoa', 242);
INSERT INTO `states`
VALUES (24, 'Andhra Pradesh', 106);
INSERT INTO `states`
VALUES (25, 'Arunachal Pradesh', 106);
INSERT INTO `states`
VALUES (26, 'Assam', 106);
INSERT INTO `states`
VALUES (27, 'Bihar', 106);
INSERT INTO `states`
VALUES (28, 'Haryana', 106);
INSERT INTO `states`
VALUES (29, 'Himacha Pradesh', 106);
INSERT INTO `states`
VALUES (30, 'Karnataka', 106);
INSERT INTO `states`
VALUES (31, 'Maharashtra', 106);
INSERT INTO `states`
VALUES (32, 'Meghalaya', 106);
INSERT INTO `states`
VALUES (33, 'Punjab', 106);
INSERT INTO `states`
VALUES (34, 'Tamil Nadu', 106);
INSERT INTO `states`
VALUES (35, 'Telangana', 106);
INSERT INTO `states`
VALUES (36, 'Uttar Pradesh', 106);
INSERT INTO `states`
VALUES (37, 'West Bengal', 106);
INSERT INTO `states`
VALUES (38, 'Bac Ninh', 242);
INSERT INTO `states`
VALUES (40, 'Ha Tinh', 242);
INSERT INTO `states`
VALUES (41, 'Hue', 242);
INSERT INTO `states`
VALUES (42, 'Binh Dinh', 242);
INSERT INTO `states`
VALUES (43, 'An Giang', 242);
INSERT INTO `states`
VALUES (44, 'Blackpool', 78);
INSERT INTO `states`
VALUES (45, 'London', 78);
INSERT INTO `states`
VALUES (46, 'Liverpool', 78);
INSERT INTO `states`
VALUES (47, 'Manchester', 78);
INSERT INTO `states`
VALUES (48, 'Newcastle', 78);
INSERT INTO `states`
VALUES (49, 'Bristol', 78);
INSERT INTO `states`
VALUES (50, 'Cambridge', 78);
INSERT INTO `states`
VALUES (51, 'Lang Son', 242);
INSERT INTO `states`
VALUES (52, 'Delhi', 106);
INSERT INTO `states`
VALUES (53, 'Hawaii', 234);
INSERT INTO `states`
VALUES (54, 'Georgia', 234);
INSERT INTO `states`
VALUES (55, 'Pennsylvania', 234);
INSERT INTO `states`
VALUES (56, 'Virginia', 234);
INSERT INTO `states`
VALUES (57, 'Arizona', 234);
INSERT INTO `states`
VALUES (59, 'Kerala', 106);
INSERT INTO `states`
VALUES (60, 'Rajasthan', 106);
INSERT INTO `states`
VALUES (61, 'Odisha', 106);
INSERT INTO `states`
VALUES (62, 'Mizoram', 106);
INSERT INTO `states`
VALUES (63, 'Sikkim', 106);
INSERT INTO `states`
VALUES (64, 'Manipur', 106);
INSERT INTO `states`
VALUES (65, 'Nagaland', 106);
INSERT INTO `states`
VALUES (66, 'Tripura', 106);
INSERT INTO `states`
VALUES (67, 'Jharkhand', 106);
INSERT INTO `states`
VALUES (68, 'Uttarakhand', 106);
INSERT INTO `states`
VALUES (69, 'Goa', 106);
INSERT INTO `states`
VALUES (70, 'Madhya Pradesh', 106);
INSERT INTO `states`
VALUES (71, 'Gujarat', 106);
INSERT INTO `states`
VALUES (72, 'Massachusetts', 234);
INSERT INTO `states`
VALUES (73, 'Indiana', 234);
INSERT INTO `states`
VALUES (74, 'Michigan', 234);
INSERT INTO `states`
VALUES (75, 'Ohio', 234);
INSERT INTO `states`
VALUES (76, 'New Jersey', 234);
INSERT INTO `states`
VALUES (77, 'Minnesota', 234);
INSERT INTO `states`
VALUES (78, 'North Carolina', 234);
INSERT INTO `states`
VALUES (79, 'Oregon', 234);
INSERT INTO `states`
VALUES (80, 'Maryland', 234);
INSERT INTO `states`
VALUES (81, 'Tennessee', 234);
INSERT INTO `states`
VALUES (82, 'Montana', 234);
INSERT INTO `states`
VALUES (83, 'Maine', 234);
INSERT INTO `states`
VALUES (84, 'Alabama', 234);
INSERT INTO `states`
VALUES (85, 'Wisconsin', 234);
INSERT INTO `states`
VALUES (86, 'Louisiana', 234);
INSERT INTO `states`
VALUES (87, 'Connecticut', 234);
INSERT INTO `states`
VALUES (88, 'Missouri', 234);
INSERT INTO `states`
VALUES (89, 'South Carolina', 234);
INSERT INTO `states`
VALUES (90, 'Mississippi', 234);
INSERT INTO `states`
VALUES (91, 'New Mexico', 234);
INSERT INTO `states`
VALUES (92, 'Nevada', 234);
INSERT INTO `states`
VALUES (93, 'Kentucky', 234);
INSERT INTO `states`
VALUES (94, 'Arkansas', 234);
INSERT INTO `states`
VALUES (95, 'Wyoming', 234);
INSERT INTO `states`
VALUES (96, 'Kansas', 234);
INSERT INTO `states`
VALUES (97, 'Delaware', 234);
INSERT INTO `states`
VALUES (98, 'Iowa', 234);
INSERT INTO `states`
VALUES (99, 'Idaho', 234);
INSERT INTO `states`
VALUES (100, 'Nebraska', 234);
INSERT INTO `states`
VALUES (101, 'Rhode Island', 234);
INSERT INTO `states`
VALUES (102, 'Vermont', 234);
INSERT INTO `states`
VALUES (103, 'South Dakota', 234);
INSERT INTO `states`
VALUES (104, 'New Hamsphire', 234);
INSERT INTO `states`
VALUES (105, 'West Virginia', 234);
INSERT INTO `states`
VALUES (106, 'North Dakota', 234);
INSERT INTO `states`
VALUES (107, 'Oklahoma', 234);
INSERT INTO `states`
VALUES (109, 'Nam Dinh', 242);
INSERT INTO `states`
VALUES (110, 'Khanh Hoa', 242);
INSERT INTO `states`
VALUES (112, 'Tay Ninh', 242);
INSERT INTO `states`
VALUES (113, 'Kien Giang', 242);
INSERT INTO `states`
VALUES (114, 'Lai Chau', 242);
INSERT INTO `states`
VALUES (115, 'Lao Cai', 242);
INSERT INTO `states`
VALUES (116, 'Ha Giang', 242);
INSERT INTO `states`
VALUES (117, 'Hoa Binh', 242);
INSERT INTO `states`
VALUES (118, 'Thai Binh', 242);
INSERT INTO `states`
VALUES (119, 'Dong Nai', 242);
INSERT INTO `states`
VALUES (120, 'Soc Trang', 242);
INSERT INTO `states`
VALUES (121, 'Bac Lieu', 242);
INSERT INTO `states`
VALUES (123, 'Ca Mau', 242);
INSERT INTO `states`
VALUES (124, 'Quang Binh', 242);
INSERT INTO `states`
VALUES (125, 'Quang Tri', 242);
INSERT INTO `states`
VALUES (126, 'Quang Nam', 242);
INSERT INTO `states`
VALUES (127, 'Quang Ngai', 242);
INSERT INTO `states`
VALUES (128, 'Gia Lai', 242);
INSERT INTO `states`
VALUES (129, 'Vinh Phuc', 242);
INSERT INTO `states`
VALUES (130, 'Lam Dong', 242);
INSERT INTO `states`
VALUES (131, 'Dak Lak', 242);
INSERT INTO `states`
VALUES (132, 'Phu Yen', 242);
INSERT INTO `states`
VALUES (133, 'Binh Phuoc', 242);
INSERT INTO `states`
VALUES (134, 'Dak Nong', 242);
INSERT INTO `states`
VALUES (135, 'Dong Thap', 242);
INSERT INTO `states`
VALUES (136, 'Hau Giang', 242);
INSERT INTO `states`
VALUES (137, 'Ba Ria Vung Tau', 242);
INSERT INTO `states`
VALUES (138, 'Binh Thuan', 242);
INSERT INTO `states`
VALUES (139, 'Hai Duong', 242);
INSERT INTO `states`
VALUES (140, 'Hung Yen', 242);
INSERT INTO `states`
VALUES (141, 'Thai Nguyen', 242);
INSERT INTO `states`
VALUES (142, 'Long An', 242);
INSERT INTO `states`
VALUES (143, 'Ben Tre', 242);
INSERT INTO `states`
VALUES (144, 'Bac Kan', 242);
INSERT INTO `states`
VALUES (145, 'Ninh Binh', 242);
INSERT INTO `states`
VALUES (146, 'Kon Tum', 242);
INSERT INTO `states`
VALUES (147, 'Tra Vinh', 242);
INSERT INTO `states`
VALUES (148, 'Yen Bai', 242);
INSERT INTO `states`
VALUES (149, 'Tuyen Quang', 242);
INSERT INTO `states`
VALUES (150, 'Phu Tho', 242);
INSERT INTO `states`
VALUES (151, 'Vinh Long', 242);
INSERT INTO `states`
VALUES (152, 'New South Wales', 14);
INSERT INTO `states`
VALUES (153, 'Queensland', 14);
INSERT INTO `states`
VALUES (154, 'Tasmania', 14);
INSERT INTO `states`
VALUES (155, 'Western Australia', 14);
INSERT INTO `states`
VALUES (156, 'Victoria', 14);
INSERT INTO `states`
VALUES (157, 'South Australia', 14);
INSERT INTO `states`
VALUES (158, 'Ontario', 39);
INSERT INTO `states`
VALUES (159, 'Quebec', 39);
INSERT INTO `states`
VALUES (160, 'Nova Scotia', 39);
INSERT INTO `states`
VALUES (161, 'New Brunswick', 39);
INSERT INTO `states`
VALUES (162, 'Manitoba', 39);
INSERT INTO `states`
VALUES (163, 'British Columbia', 39);
INSERT INTO `states`
VALUES (164, 'Prince Edward Island', 39);
INSERT INTO `states`
VALUES (165, 'Saskatchewan', 39);
INSERT INTO `states`
VALUES (166, 'Alberta', 39);
INSERT INTO `states`
VALUES (167, 'Newfoundland and Labrador', 39);
INSERT INTO `states`
VALUES (168, 'Grand Est', 76);
INSERT INTO `states`
VALUES (169, 'Hauts-de-France', 76);
INSERT INTO `states`
VALUES (170, 'Normandy', 76);
INSERT INTO `states`
VALUES (171, 'Nouvelle-Aquitaine', 76);
INSERT INTO `states`
VALUES (172, 'Occitanie', 76);
INSERT INTO `states`
VALUES (173, 'Brittany', 76);
INSERT INTO `states`
VALUES (174, 'Centre-Val de Loire', 76);
INSERT INTO `states`
VALUES (175, 'Corsica', 76);
INSERT INTO `states`
VALUES (176, 'French Guiana', 76);
INSERT INTO `states`
VALUES (177, 'Guadeloupe', 76);
INSERT INTO `states`
VALUES (178, 'Île-de-France', 76);
INSERT INTO `states`
VALUES (179, 'Martinique', 76);
INSERT INTO `states`
VALUES (180, 'Mayotte', 76);
INSERT INTO `states`
VALUES (181, 'Pays de la Loire', 76);
INSERT INTO `states`
VALUES (182, 'Provence-Alpes-Côte d\'Azur', 76);
INSERT INTO `states`
VALUES (183, 'Réunion', 76);
INSERT INTO `states`
VALUES (184, 'Aichi', 115);
INSERT INTO `states`
VALUES (185, 'Akita', 115);
INSERT INTO `states`
VALUES (186, 'Chiba', 115);
INSERT INTO `states`
VALUES (187, 'Ehime', 115);
INSERT INTO `states`
VALUES (188, 'Fukui', 115);
INSERT INTO `states`
VALUES (189, 'Fukuoka', 115);
INSERT INTO `states`
VALUES (190, 'Fukushima', 115);
INSERT INTO `states`
VALUES (191, 'Gunma', 115);
INSERT INTO `states`
VALUES (192, 'Kobe', 115);
INSERT INTO `states`
VALUES (193, 'Hiroshima', 115);
INSERT INTO `states`
VALUES (194, 'Hokkaido', 115);
INSERT INTO `states`
VALUES (195, 'Ishikawa', 115);
INSERT INTO `states`
VALUES (196, 'Kagawa', 115);
INSERT INTO `states`
VALUES (197, 'Kochi', 115);
INSERT INTO `states`
VALUES (198, 'Kyoto', 115);
INSERT INTO `states`
VALUES (199, 'Nagasaki', 115);
INSERT INTO `states`
VALUES (200, 'Okinawa', 115);
INSERT INTO `states`
VALUES (201, 'Osaka', 115);
INSERT INTO `states`
VALUES (202, 'Saga', 115);
INSERT INTO `states`
VALUES (203, 'Tokushima', 115);
INSERT INTO `states`
VALUES (204, 'Tokyo', 115);
INSERT INTO `states`
VALUES (205, 'Yamagata', 115);
INSERT INTO `states`
VALUES (206, 'Yamaguchi', 115);
INSERT INTO `states`
VALUES (207, 'Yamanashi', 115);
INSERT INTO `states`
VALUES (208, 'Greater Poland', 180);
INSERT INTO `states`
VALUES (209, 'Kuyavia-Pomerania', 180);
INSERT INTO `states`
VALUES (210, 'Lesser Poland', 180);
INSERT INTO `states`
VALUES (211, 'Łódź', 180);
INSERT INTO `states`
VALUES (212, 'Lower Silesia', 180);
INSERT INTO `states`
VALUES (213, 'Lublin', 180);
INSERT INTO `states`
VALUES (214, 'Lubusz', 180);
INSERT INTO `states`
VALUES (215, 'Masovia', 180);
INSERT INTO `states`
VALUES (216, 'Opole', 180);
INSERT INTO `states`
VALUES (217, 'Podlaskie', 180);
INSERT INTO `states`
VALUES (218, 'Pomerania', 180);
INSERT INTO `states`
VALUES (219, 'Silesia', 180);
INSERT INTO `states`
VALUES (220, 'Subcarpathia', 180);
INSERT INTO `states`
VALUES (221, 'Holy Cross Province', 180);
INSERT INTO `states`
VALUES (222, 'Warmia-Masuria', 180);
INSERT INTO `states`
VALUES (223, 'West Pomerania', 180);
INSERT INTO `states`
VALUES (224, 'Baden-Württemberg', 58);
INSERT INTO `states`
VALUES (225, 'Bavaria', 58);
INSERT INTO `states`
VALUES (226, 'Berlin', 58);
INSERT INTO `states`
VALUES (227, 'Brandenburg', 58);
INSERT INTO `states`
VALUES (228, 'Bremen', 58);
INSERT INTO `states`
VALUES (229, 'Hamburg', 58);
INSERT INTO `states`
VALUES (230, 'Hesse', 58);
INSERT INTO `states`
VALUES (231, 'Lower Saxony', 58);
INSERT INTO `states`
VALUES (232, 'Mecklenburg-Vorpommern', 58);
INSERT INTO `states`
VALUES (233, 'North Rhine- Westphalia', 58);
INSERT INTO `states`
VALUES (234, 'Rhineland-Palatinate', 58);
INSERT INTO `states`
VALUES (235, 'Saarland', 58);
INSERT INTO `states`
VALUES (236, 'Saxony', 58);
INSERT INTO `states`
VALUES (237, 'Saxony-Anhalt', 58);
INSERT INTO `states`
VALUES (238, 'Schleswig-Holstein', 58);
INSERT INTO `states`
VALUES (239, 'Thuringia', 58);
INSERT INTO `states`
VALUES (240, 'Acre', 32);
INSERT INTO `states`
VALUES (241, 'Alagoas', 32);
INSERT INTO `states`
VALUES (242, 'Amapá', 32);
INSERT INTO `states`
VALUES (243, 'Amazonas', 32);
INSERT INTO `states`
VALUES (244, 'Bahia', 32);
INSERT INTO `states`
VALUES (245, 'Ceará', 32);
INSERT INTO `states`
VALUES (246, 'Distrito Federal', 32);
INSERT INTO `states`
VALUES (247, 'Espírito Santo', 32);
INSERT INTO `states`
VALUES (248, 'Goiás', 32);
INSERT INTO `states`
VALUES (249, 'Maranhão', 32);
INSERT INTO `states`
VALUES (250, 'Mato Grosso', 32);
INSERT INTO `states`
VALUES (251, 'Minas Gerais', 32);
INSERT INTO `states`
VALUES (252, 'Pará', 32);
INSERT INTO `states`
VALUES (253, 'Paraíba', 32);
INSERT INTO `states`
VALUES (254, 'Paraná', 32);
INSERT INTO `states`
VALUES (255, 'Pernambuco', 32);
INSERT INTO `states`
VALUES (256, 'Piauí', 32);
INSERT INTO `states`
VALUES (257, 'Rio de Janeiro', 32);
INSERT INTO `states`
VALUES (258, 'Rio Grande do Norte', 32);
INSERT INTO `states`
VALUES (259, 'Rio Grande do Sul', 32);
INSERT INTO `states`
VALUES (260, 'Rondônia', 32);
INSERT INTO `states`
VALUES (261, 'Roraima', 32);
INSERT INTO `states`
VALUES (262, 'Santa Catarina', 32);
INSERT INTO `states`
VALUES (263, 'São Paulo', 32);
INSERT INTO `states`
VALUES (264, 'Sergipe', 32);
INSERT INTO `states`
VALUES (265, 'Tocantins', 32);
INSERT INTO `states`
VALUES (266, 'Adana', 226);
INSERT INTO `states`
VALUES (267, 'Adıyaman', 226);
INSERT INTO `states`
VALUES (268, 'Ankara', 226);
INSERT INTO `states`
VALUES (269, 'Balıkesir', 226);
INSERT INTO `states`
VALUES (270, 'Bilecik', 226);
INSERT INTO `states`
VALUES (271, 'Bingöl', 226);
INSERT INTO `states`
VALUES (272, 'Bursa', 226);
INSERT INTO `states`
VALUES (273, 'Çanakkale', 226);
INSERT INTO `states`
VALUES (274, 'Çankırı', 226);
INSERT INTO `states`
VALUES (275, 'Çorum', 226);
INSERT INTO `states`
VALUES (276, 'Denizli', 226);
INSERT INTO `states`
VALUES (277, 'Diyarbakır', 226);
INSERT INTO `states`
VALUES (278, 'Edirne', 226);
INSERT INTO `states`
VALUES (279, 'Erzurum', 226);
INSERT INTO `states`
VALUES (280, 'Gaziantep', 226);
INSERT INTO `states`
VALUES (281, 'Giresun', 226);
INSERT INTO `states`
VALUES (282, 'Hakkâri', 226);
INSERT INTO `states`
VALUES (283, 'Hatay', 226);
INSERT INTO `states`
VALUES (284, 'Isparta', 226);
INSERT INTO `states`
VALUES (285, 'Istanbul', 226);
INSERT INTO `states`
VALUES (286, 'Konya', 226);
INSERT INTO `states`
VALUES (287, 'Malatya', 226);
INSERT INTO `states`
VALUES (288, 'Manisa', 226);
INSERT INTO `states`
VALUES (289, 'Iğdır', 226);
INSERT INTO `states`
VALUES (290, 'Karaman', 226);
INSERT INTO `states`
VALUES (291, 'Osmaniye', 226);
INSERT INTO `states`
VALUES (292, 'Tokat', 226);
INSERT INTO `states`
VALUES (293, 'Sivas', 226);
INSERT INTO `states`
VALUES (294, 'Sinop', 226);
INSERT INTO `states`
VALUES (295, 'Siirt', 226);
INSERT INTO `states`
VALUES (296, 'Batman', 226);
INSERT INTO `states`
VALUES (297, 'Kilis', 226);
INSERT INTO `states`
VALUES (298, 'Rize', 226);
INSERT INTO `states`
VALUES (299, 'Ordu', 226);
INSERT INTO `states`
VALUES (300, 'Tunceli', 226);
INSERT INTO `states`
VALUES (301, 'Yozgat', 226);
INSERT INTO `states`
VALUES (302, 'Van', 226);
INSERT INTO `states`
VALUES (303, 'Bayburt', 226);
INSERT INTO `states`
VALUES (304, 'Yalova', 226);
INSERT INTO `states`
VALUES (305, 'Samsun', 226);
INSERT INTO `states`
VALUES (306, 'Sakarya', 226);
INSERT INTO `states`
VALUES (308, 'D.NAI', 242);

SET FOREIGN_KEY_CHECKS = 1;

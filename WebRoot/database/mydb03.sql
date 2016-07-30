/*
Navicat MySQL Data Transfer

Source Server         : 10.5.113.239
Source Server Version : 50067
Source Host           : 10.5.113.239:3306
Source Database       : mydb03

Target Server Type    : MYSQL
Target Server Version : 50099
File Encoding         : 28591

Date: 2016-07-30 15:37:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tab_area_border`
-- ----------------------------
DROP TABLE IF EXISTS `tab_area_border`;
CREATE TABLE `tab_area_border` (
`id`  int(2) NOT NULL AUTO_INCREMENT COMMENT '??id' ,
`area_name`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`area_border`  varchar(8192) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=17 */

;

-- ----------------------------
-- Table structure for `tab_scene_classifys`
-- ----------------------------
DROP TABLE IF EXISTS `tab_scene_classifys`;
CREATE TABLE `tab_scene_classifys` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`time_location_id`  int(3) NULL DEFAULT NULL COMMENT '??id' ,
`classifys`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`),
INDEX `??` USING BTREE (`date`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=44896 */

;

-- ----------------------------
-- Table structure for `tab_scene_prefer`
-- ----------------------------
DROP TABLE IF EXISTS `tab_scene_prefer`;
CREATE TABLE `tab_scene_prefer` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`time_location_id`  int(3) NOT NULL COMMENT 'tab_time_location???id' ,
`classify`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???????' ,
`weight`  double(3,2) NOT NULL COMMENT '???????' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '?????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=78721 */

;

-- ----------------------------
-- Table structure for `tab_slide_count`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slide_count`;
CREATE TABLE `tab_slide_count` (
`dateym`  char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`device_number`  char(17) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`day01`  int(5) NULL DEFAULT 0 ,
`day02`  int(5) NULL DEFAULT 0 ,
`day03`  int(5) NULL DEFAULT 0 ,
`day04`  int(5) NULL DEFAULT 0 ,
`day05`  int(5) NULL DEFAULT 0 ,
`day06`  int(5) NULL DEFAULT 0 ,
`day07`  int(5) NULL DEFAULT 0 ,
`day08`  int(5) NULL DEFAULT 0 ,
`day09`  int(5) NULL DEFAULT 0 ,
`day10`  int(5) NULL DEFAULT 0 ,
`day11`  int(5) NULL DEFAULT 0 ,
`day12`  int(5) NULL DEFAULT 0 ,
`day13`  int(5) NULL DEFAULT 0 ,
`day14`  int(5) NULL DEFAULT 0 ,
`day15`  int(5) NULL DEFAULT 0 ,
`day16`  int(5) NULL DEFAULT 0 ,
`day17`  int(5) NULL DEFAULT 0 ,
`day18`  int(5) NULL DEFAULT 0 ,
`day19`  int(5) NULL DEFAULT 0 ,
`day20`  int(5) NULL DEFAULT 0 ,
`day21`  int(5) NULL DEFAULT 0 ,
`day22`  int(5) NULL DEFAULT 0 ,
`day23`  int(5) NULL DEFAULT 0 ,
`day24`  int(5) NULL DEFAULT 0 ,
`day25`  int(5) NULL DEFAULT 0 ,
`day26`  int(5) NULL DEFAULT 0 ,
`day27`  int(5) NULL DEFAULT 0 ,
`day28`  int(5) NULL DEFAULT 0 ,
`day29`  int(5) NULL DEFAULT 0 ,
`day30`  int(5) NULL DEFAULT 0 ,
`day31`  int(5) NULL DEFAULT 0 ,
PRIMARY KEY (`device_number`, `dateym`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_time_classifys`
-- ----------------------------
DROP TABLE IF EXISTS `tab_time_classifys`;
CREATE TABLE `tab_time_classifys` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`time`  varchar(17) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`time_id`  int(2) NULL DEFAULT NULL COMMENT '?????' ,
`classifys`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`),
INDEX `??` USING BTREE (`date`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=45386 */

;

-- ----------------------------
-- Table structure for `tab_time_divide`
-- ----------------------------
DROP TABLE IF EXISTS `tab_time_divide`;
CREATE TABLE `tab_time_divide` (
`id`  int(2) NOT NULL AUTO_INCREMENT COMMENT '??' ,
`name`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????' ,
`time`  char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????' ,
`time_id`  int(2) NOT NULL COMMENT '??????id' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=9 */

;

-- ----------------------------
-- Table structure for `tab_time_location`
-- ----------------------------
DROP TABLE IF EXISTS `tab_time_location`;
CREATE TABLE `tab_time_location` (
`id`  int(3) NOT NULL AUTO_INCREMENT ,
`time`  char(17) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`time_id`  int(2) NOT NULL COMMENT '???id' ,
`location`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=129 */

;

-- ----------------------------
-- Table structure for `tab_time_prefer`
-- ----------------------------
DROP TABLE IF EXISTS `tab_time_prefer`;
CREATE TABLE `tab_time_prefer` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '??id' ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`time`  char(17) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`time_id`  int(1) NOT NULL COMMENT '??id' ,
`classify`  char(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`weight`  double(3,2) NULL DEFAULT NULL COMMENT '??' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=4264 */

;

-- ----------------------------
-- Auto increment value for `tab_area_border`
-- ----------------------------
ALTER TABLE `tab_area_border` AUTO_INCREMENT=17;

-- ----------------------------
-- Auto increment value for `tab_scene_classifys`
-- ----------------------------
ALTER TABLE `tab_scene_classifys` AUTO_INCREMENT=44896;

-- ----------------------------
-- Auto increment value for `tab_scene_prefer`
-- ----------------------------
ALTER TABLE `tab_scene_prefer` AUTO_INCREMENT=78721;

-- ----------------------------
-- Auto increment value for `tab_time_classifys`
-- ----------------------------
ALTER TABLE `tab_time_classifys` AUTO_INCREMENT=45386;

-- ----------------------------
-- Auto increment value for `tab_time_divide`
-- ----------------------------
ALTER TABLE `tab_time_divide` AUTO_INCREMENT=9;

-- ----------------------------
-- Auto increment value for `tab_time_location`
-- ----------------------------
ALTER TABLE `tab_time_location` AUTO_INCREMENT=129;

-- ----------------------------
-- Auto increment value for `tab_time_prefer`
-- ----------------------------
ALTER TABLE `tab_time_prefer` AUTO_INCREMENT=4264;

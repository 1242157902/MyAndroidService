/*
Navicat MySQL Data Transfer

Source Server         : 10.5.113.239
Source Server Version : 50067
Source Host           : 10.5.113.239:3306
Source Database       : mydb02

Target Server Type    : MYSQL
Target Server Version : 50099
File Encoding         : 28591

Date: 2016-07-30 15:33:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tab_apkupdate_log`
-- ----------------------------
DROP TABLE IF EXISTS `tab_apkupdate_log`;
CREATE TABLE `tab_apkupdate_log` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`apk_version`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_version`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????????' ,
`apk_updatetime`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=252664 */

;

-- ----------------------------
-- Table structure for `tab_app_classify`
-- ----------------------------
DROP TABLE IF EXISTS `tab_app_classify`;
CREATE TABLE `tab_app_classify` (
`app_code`  char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`category`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_app_weight`
-- ----------------------------
DROP TABLE IF EXISTS `tab_app_weight`;
CREATE TABLE `tab_app_weight` (
`id`  int(4) NOT NULL AUTO_INCREMENT COMMENT 'id' ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????,???' ,
`app_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`weight`  double(10,3) NOT NULL COMMENT '??' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=44 */

;

-- ----------------------------
-- Table structure for `tab_appflow`
-- ----------------------------
DROP TABLE IF EXISTS `tab_appflow`;
CREATE TABLE `tab_appflow` (
`appflow_id`  int(10) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_name`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_flow`  int(10) NULL DEFAULT NULL ,
`receive_time`  timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`appflow_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=62386 */

;

-- ----------------------------
-- Table structure for `tab_apps`
-- ----------------------------
DROP TABLE IF EXISTS `tab_apps`;
CREATE TABLE `tab_apps` (
`apps_id`  int(10) NOT NULL AUTO_INCREMENT ,
`device_imei`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_names`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`apps_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=177 */

;

-- ----------------------------
-- Table structure for `tab_appsdic`
-- ----------------------------
DROP TABLE IF EXISTS `tab_appsdic`;
CREATE TABLE `tab_appsdic` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
PRIMARY KEY (`id`),
INDEX `id` USING BTREE (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=10372 */

;

-- ----------------------------
-- Table structure for `tab_beijing`
-- ----------------------------
DROP TABLE IF EXISTS `tab_beijing`;
CREATE TABLE `tab_beijing` (
`openinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`open_time`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`openinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=36321 */

;

-- ----------------------------
-- Table structure for `tab_countapps`
-- ----------------------------
DROP TABLE IF EXISTS `tab_countapps`;
CREATE TABLE `tab_countapps` (
`id`  int(5) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`use_nums`  int(5) NULL DEFAULT NULL ,
`use_flow`  int(10) NULL DEFAULT NULL ,
`app_classify`  char(9) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`use_date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=1307 */

;

-- ----------------------------
-- Table structure for `tab_day_classifys`
-- ----------------------------
DROP TABLE IF EXISTS `tab_day_classifys`;
CREATE TABLE `tab_day_classifys` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '??' ,
`phone_num`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`date`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`classifys`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '?????????????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=4439 */

;

-- ----------------------------
-- Table structure for `tab_deviceopeninfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_deviceopeninfo`;
CREATE TABLE `tab_deviceopeninfo` (
`openinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`open_time`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`openinfo_id`),
INDEX `openinfo_fk2` USING BTREE (`device_number`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=6686 */

;

-- ----------------------------
-- Table structure for `tab_deviceopeninfo_copy`
-- ----------------------------
DROP TABLE IF EXISTS `tab_deviceopeninfo_copy`;
CREATE TABLE `tab_deviceopeninfo_copy` (
`openinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`openinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=6686 */

;

-- ----------------------------
-- Table structure for `tab_frequent_item`
-- ----------------------------
DROP TABLE IF EXISTS `tab_frequent_item`;
CREATE TABLE `tab_frequent_item` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`classify`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`min_support`  double(3,2) NOT NULL COMMENT '?????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=34 */

;

-- ----------------------------
-- Table structure for `tab_hebei`
-- ----------------------------
DROP TABLE IF EXISTS `tab_hebei`;
CREATE TABLE `tab_hebei` (
`openinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`open_time`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`openinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=1263 */

;

-- ----------------------------
-- Table structure for `tab_history_weight`
-- ----------------------------
DROP TABLE IF EXISTS `tab_history_weight`;
CREATE TABLE `tab_history_weight` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`phone`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???--????' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`classify`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`weight`  double(10,3) NOT NULL DEFAULT 0.000 COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=50 */

;

-- ----------------------------
-- Table structure for `tab_location`
-- ----------------------------
DROP TABLE IF EXISTS `tab_location`;
CREATE TABLE `tab_location` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`longitude`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`latitude`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phone_num`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`locationtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=60162 */

;

-- ----------------------------
-- Table structure for `tab_locationcount`
-- ----------------------------
DROP TABLE IF EXISTS `tab_locationcount`;
CREATE TABLE `tab_locationcount` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`longitude`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????????' ,
`latitude`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????????' ,
`phone_num`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`locationtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '????' ,
`address`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????????' ,
`count`  int(5) NOT NULL ,
PRIMARY KEY (`id`, `latitude`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=41692 */

;

-- ----------------------------
-- Table structure for `tab_picprefer`
-- ----------------------------
DROP TABLE IF EXISTS `tab_picprefer`;
CREATE TABLE `tab_picprefer` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`classify`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_nums`  int(5) UNSIGNED ZEROFILL NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=1353 */

;

-- ----------------------------
-- Table structure for `tab_pushcontent`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushcontent`;
CREATE TABLE `tab_pushcontent` (
`cid`  int(11) NOT NULL ,
`sort`  tinyint(11) NULL DEFAULT NULL ,
`title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`operator`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`namex`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`size`  int(11) NULL DEFAULT NULL ,
`operdate`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`link`  varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`icon`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`scolor`  varchar(6) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`flag`  char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT '1' ,
`classify`  varchar(12) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`cid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for `tab_pushcontentclass`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushcontentclass`;
CREATE TABLE `tab_pushcontentclass` (
`id`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushcontentcopy`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushcontentcopy`;
CREATE TABLE `tab_pushcontentcopy` (
`cid`  int(11) NOT NULL ,
`sort`  tinyint(11) NULL DEFAULT NULL ,
`title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`operator`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`namex`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`size`  int(11) NULL DEFAULT NULL ,
`operdate`  varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`link`  varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`icon`  varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`scolor`  varchar(6) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`flag`  char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT '1' ,
`classify`  varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`cid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201504`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201504`;
CREATE TABLE `tab_pushlog_201504` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201505`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201505`;
CREATE TABLE `tab_pushlog_201505` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201506`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201506`;
CREATE TABLE `tab_pushlog_201506` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201507`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201507`;
CREATE TABLE `tab_pushlog_201507` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201508`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201508`;
CREATE TABLE `tab_pushlog_201508` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201509`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201509`;
CREATE TABLE `tab_pushlog_201509` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201511`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201511`;
CREATE TABLE `tab_pushlog_201511` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201512`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201512`;
CREATE TABLE `tab_pushlog_201512` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201602`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201602`;
CREATE TABLE `tab_pushlog_201602` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201603`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201603`;
CREATE TABLE `tab_pushlog_201603` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201604`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201604`;
CREATE TABLE `tab_pushlog_201604` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlog_201607`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlog_201607`;
CREATE TABLE `tab_pushlog_201607` (
`hid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pop`  tinyint(4) NULL DEFAULT 0 ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`htime`  datetime NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`hid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushmbtype`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushmbtype`;
CREATE TABLE `tab_pushmbtype` (
`id`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushmbtypeold`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushmbtypeold`;
CREATE TABLE `tab_pushmbtypeold` (
`id`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_queuelog_201604`
-- ----------------------------
DROP TABLE IF EXISTS `tab_queuelog_201604`;
CREATE TABLE `tab_queuelog_201604` (
`qlid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`qlid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_queuelog_201606`
-- ----------------------------
DROP TABLE IF EXISTS `tab_queuelog_201606`;
CREATE TABLE `tab_queuelog_201606` (
`qlid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`qlid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_queuelog_201607`
-- ----------------------------
DROP TABLE IF EXISTS `tab_queuelog_201607`;
CREATE TABLE `tab_queuelog_201607` (
`qlid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`qlid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201506`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201506`;
CREATE TABLE `tab_rcvlog_201506` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201507`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201507`;
CREATE TABLE `tab_rcvlog_201507` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201508`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201508`;
CREATE TABLE `tab_rcvlog_201508` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201509`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201509`;
CREATE TABLE `tab_rcvlog_201509` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201510`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201510`;
CREATE TABLE `tab_rcvlog_201510` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201511`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201511`;
CREATE TABLE `tab_rcvlog_201511` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201512`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201512`;
CREATE TABLE `tab_rcvlog_201512` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201601`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201601`;
CREATE TABLE `tab_rcvlog_201601` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201602`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201602`;
CREATE TABLE `tab_rcvlog_201602` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201603`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201603`;
CREATE TABLE `tab_rcvlog_201603` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_rcvlog_201604`
-- ----------------------------
DROP TABLE IF EXISTS `tab_rcvlog_201604`;
CREATE TABLE `tab_rcvlog_201604` (
`rid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`type`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puuid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`acctime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`rid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_screen_times`
-- ----------------------------
DROP TABLE IF EXISTS `tab_screen_times`;
CREATE TABLE `tab_screen_times` (
`id`  int(5) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`date`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`time`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`time_id`  int(2) NULL DEFAULT NULL ,
`nums`  int(5) NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=8524 */

;

-- ----------------------------
-- Table structure for `tab_screencount`
-- ----------------------------
DROP TABLE IF EXISTS `tab_screencount`;
CREATE TABLE `tab_screencount` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`dura_time`  int(11) NULL DEFAULT NULL COMMENT '??--??' ,
`times`  int(5) NULL DEFAULT NULL COMMENT '??' ,
`date`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=1351 */

;

-- ----------------------------
-- Table structure for `tab_screeninfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_screeninfo`;
CREATE TABLE `tab_screeninfo` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`screen_onoff`  tinyint(1) NULL DEFAULT NULL COMMENT '0 ?? 1??' ,
`operator_time`  varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=201599 */

;

-- ----------------------------
-- Table structure for `tab_screeninfo_copy`
-- ----------------------------
DROP TABLE IF EXISTS `tab_screeninfo_copy`;
CREATE TABLE `tab_screeninfo_copy` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`screen_onoff`  tinyint(1) NULL DEFAULT NULL COMMENT '0 ?? 1??' ,
`operator_time`  varchar(19) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=87144 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201408`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201408`;
CREATE TABLE `tab_slideinfo_201408` (
`id`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbno`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`picname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pictitle`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slidetime`  datetime NULL DEFAULT NULL ,
`com`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`gender`  varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`birth`  date NULL DEFAULT NULL ,
`mbos`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbarea`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbprovi`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbtype`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller_dep`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller_stuff`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regtime`  datetime NULL DEFAULT NULL ,
`sort`  int(11) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201412`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201412`;
CREATE TABLE `tab_slideinfo_201412` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL ,
`device_seller`  int(6) NULL DEFAULT NULL ,
`user_sex`  tinyint(1) NULL DEFAULT NULL ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_os_version`  int(4) NULL DEFAULT NULL ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=8322 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201501`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201501`;
CREATE TABLE `tab_slideinfo_201501` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL ,
`device_seller`  int(6) NULL DEFAULT NULL ,
`user_sex`  tinyint(1) NULL DEFAULT NULL ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_os_version`  int(4) NULL DEFAULT NULL ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=22465 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201502`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201502`;
CREATE TABLE `tab_slideinfo_201502` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL ,
`device_seller`  int(6) NULL DEFAULT NULL ,
`user_sex`  tinyint(1) NULL DEFAULT NULL ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_os_version`  int(4) NULL DEFAULT NULL ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=26426 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201503`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201503`;
CREATE TABLE `tab_slideinfo_201503` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL ,
`device_seller`  int(6) NULL DEFAULT NULL ,
`user_sex`  tinyint(1) NULL DEFAULT NULL ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_os_version`  int(4) NULL DEFAULT NULL ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=27876 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201504`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201504`;
CREATE TABLE `tab_slideinfo_201504` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL ,
`device_seller`  int(6) NULL DEFAULT NULL ,
`user_sex`  tinyint(1) NULL DEFAULT NULL ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_os_version`  int(4) NULL DEFAULT NULL ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=37576 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201505`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201505`;
CREATE TABLE `tab_slideinfo_201505` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL ,
`device_seller`  int(6) NULL DEFAULT NULL ,
`user_sex`  tinyint(1) NULL DEFAULT NULL ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_os_version`  int(4) NULL DEFAULT NULL ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_type`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=66217 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201506`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201506`;
CREATE TABLE `tab_slideinfo_201506` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=75897 */

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201507`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201507`;
CREATE TABLE `tab_slideinfo_201507` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=132233

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201508`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201508`;
CREATE TABLE `tab_slideinfo_201508` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=144177

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201509`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201509`;
CREATE TABLE `tab_slideinfo_201509` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=151740

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201510`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201510`;
CREATE TABLE `tab_slideinfo_201510` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=157376

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201511`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201511`;
CREATE TABLE `tab_slideinfo_201511` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=162731

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201512`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201512`;
CREATE TABLE `tab_slideinfo_201512` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=168936

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201601`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201601`;
CREATE TABLE `tab_slideinfo_201601` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=172497

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201602`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201602`;
CREATE TABLE `tab_slideinfo_201602` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=174114

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201603`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201603`;
CREATE TABLE `tab_slideinfo_201603` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=178781

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201604`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201604`;
CREATE TABLE `tab_slideinfo_201604` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=180963

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201606`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201606`;
CREATE TABLE `tab_slideinfo_201606` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=181001

;

-- ----------------------------
-- Table structure for `tab_slideinfo_201607`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slideinfo_201607`;
CREATE TABLE `tab_slideinfo_201607` (
`slideinfo_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`slide_time`  timestamp NULL DEFAULT NULL ,
`pic_score`  tinyint(11) NULL DEFAULT 0 ,
PRIMARY KEY (`slideinfo_id`),
INDEX `imei` USING BTREE (`device_imei`),
INDEX `slidetime` USING BTREE (`slide_time`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=181464

;

-- ----------------------------
-- Table structure for `tab_thmchglog_201505`
-- ----------------------------
DROP TABLE IF EXISTS `tab_thmchglog_201505`;
CREATE TABLE `tab_thmchglog_201505` (
`tid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbno`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`curtheme`  tinyint(4) NULL DEFAULT NULL ,
`exptheme`  tinyint(4) NULL DEFAULT NULL ,
`opersrc`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`operator`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`opertime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`tid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_thmchglog_201604`
-- ----------------------------
DROP TABLE IF EXISTS `tab_thmchglog_201604`;
CREATE TABLE `tab_thmchglog_201604` (
`tid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbno`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`curtheme`  tinyint(4) NULL DEFAULT NULL ,
`exptheme`  tinyint(4) NULL DEFAULT NULL ,
`opersrc`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`operator`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`opertime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`tid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_url_clicktime`
-- ----------------------------
DROP TABLE IF EXISTS `tab_url_clicktime`;
CREATE TABLE `tab_url_clicktime` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`click_url`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`click_time`  timestamp NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=405 */

;

-- ----------------------------
-- Table structure for `tab_userprofile`
-- ----------------------------
DROP TABLE IF EXISTS `tab_userprofile`;
CREATE TABLE `tab_userprofile` (
`id`  int(6) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`date`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`classify`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????,?????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=219 */

;

-- ----------------------------
-- Table structure for `tab_week_infor`
-- ----------------------------
DROP TABLE IF EXISTS `tab_week_infor`;
CREATE TABLE `tab_week_infor` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`phone`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`week`  char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`times`  int(5) NOT NULL COMMENT '????' ,
`dura_time`  int(11) NOT NULL COMMENT '????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=8 */

;

-- ----------------------------
-- Auto increment value for `tab_apkupdate_log`
-- ----------------------------
ALTER TABLE `tab_apkupdate_log` AUTO_INCREMENT=252664;

-- ----------------------------
-- Auto increment value for `tab_app_weight`
-- ----------------------------
ALTER TABLE `tab_app_weight` AUTO_INCREMENT=44;

-- ----------------------------
-- Auto increment value for `tab_appflow`
-- ----------------------------
ALTER TABLE `tab_appflow` AUTO_INCREMENT=62386;

-- ----------------------------
-- Auto increment value for `tab_apps`
-- ----------------------------
ALTER TABLE `tab_apps` AUTO_INCREMENT=177;

-- ----------------------------
-- Auto increment value for `tab_appsdic`
-- ----------------------------
ALTER TABLE `tab_appsdic` AUTO_INCREMENT=10372;

-- ----------------------------
-- Auto increment value for `tab_beijing`
-- ----------------------------
ALTER TABLE `tab_beijing` AUTO_INCREMENT=36321;

-- ----------------------------
-- Auto increment value for `tab_countapps`
-- ----------------------------
ALTER TABLE `tab_countapps` AUTO_INCREMENT=1307;

-- ----------------------------
-- Auto increment value for `tab_day_classifys`
-- ----------------------------
ALTER TABLE `tab_day_classifys` AUTO_INCREMENT=4439;

-- ----------------------------
-- Auto increment value for `tab_deviceopeninfo`
-- ----------------------------
ALTER TABLE `tab_deviceopeninfo` AUTO_INCREMENT=6686;

-- ----------------------------
-- Auto increment value for `tab_deviceopeninfo_copy`
-- ----------------------------
ALTER TABLE `tab_deviceopeninfo_copy` AUTO_INCREMENT=6686;

-- ----------------------------
-- Auto increment value for `tab_frequent_item`
-- ----------------------------
ALTER TABLE `tab_frequent_item` AUTO_INCREMENT=34;

-- ----------------------------
-- Auto increment value for `tab_hebei`
-- ----------------------------
ALTER TABLE `tab_hebei` AUTO_INCREMENT=1263;

-- ----------------------------
-- Auto increment value for `tab_history_weight`
-- ----------------------------
ALTER TABLE `tab_history_weight` AUTO_INCREMENT=50;

-- ----------------------------
-- Auto increment value for `tab_location`
-- ----------------------------
ALTER TABLE `tab_location` AUTO_INCREMENT=60162;

-- ----------------------------
-- Auto increment value for `tab_locationcount`
-- ----------------------------
ALTER TABLE `tab_locationcount` AUTO_INCREMENT=41692;

-- ----------------------------
-- Auto increment value for `tab_picprefer`
-- ----------------------------
ALTER TABLE `tab_picprefer` AUTO_INCREMENT=1353;

-- ----------------------------
-- Auto increment value for `tab_screen_times`
-- ----------------------------
ALTER TABLE `tab_screen_times` AUTO_INCREMENT=8524;

-- ----------------------------
-- Auto increment value for `tab_screencount`
-- ----------------------------
ALTER TABLE `tab_screencount` AUTO_INCREMENT=1351;

-- ----------------------------
-- Auto increment value for `tab_screeninfo`
-- ----------------------------
ALTER TABLE `tab_screeninfo` AUTO_INCREMENT=201599;

-- ----------------------------
-- Auto increment value for `tab_screeninfo_copy`
-- ----------------------------
ALTER TABLE `tab_screeninfo_copy` AUTO_INCREMENT=87144;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201412`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201412` AUTO_INCREMENT=8322;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201501`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201501` AUTO_INCREMENT=22465;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201502`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201502` AUTO_INCREMENT=26426;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201503`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201503` AUTO_INCREMENT=27876;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201504`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201504` AUTO_INCREMENT=37576;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201505`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201505` AUTO_INCREMENT=66217;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201506`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201506` AUTO_INCREMENT=75897;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201507`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201507` AUTO_INCREMENT=132233;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201508`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201508` AUTO_INCREMENT=144177;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201509`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201509` AUTO_INCREMENT=151740;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201510`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201510` AUTO_INCREMENT=157376;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201511`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201511` AUTO_INCREMENT=162731;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201512`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201512` AUTO_INCREMENT=168936;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201601`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201601` AUTO_INCREMENT=172497;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201602`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201602` AUTO_INCREMENT=174114;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201603`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201603` AUTO_INCREMENT=178781;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201604`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201604` AUTO_INCREMENT=180963;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201606`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201606` AUTO_INCREMENT=181001;

-- ----------------------------
-- Auto increment value for `tab_slideinfo_201607`
-- ----------------------------
ALTER TABLE `tab_slideinfo_201607` AUTO_INCREMENT=181464;

-- ----------------------------
-- Auto increment value for `tab_url_clicktime`
-- ----------------------------
ALTER TABLE `tab_url_clicktime` AUTO_INCREMENT=405;

-- ----------------------------
-- Auto increment value for `tab_userprofile`
-- ----------------------------
ALTER TABLE `tab_userprofile` AUTO_INCREMENT=219;

-- ----------------------------
-- Auto increment value for `tab_week_infor`
-- ----------------------------
ALTER TABLE `tab_week_infor` AUTO_INCREMENT=8;

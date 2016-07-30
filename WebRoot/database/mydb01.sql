/*
Navicat MySQL Data Transfer

Source Server         : 10.5.113.239
Source Server Version : 50067
Source Host           : 10.5.113.239:3306
Source Database       : mydb01

Target Server Type    : MYSQL
Target Server Version : 50099
File Encoding         : 28591

Date: 2016-07-30 15:32:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tab_accesstoken`
-- ----------------------------
DROP TABLE IF EXISTS `tab_accesstoken`;
CREATE TABLE `tab_accesstoken` (
`id`  int(1) NOT NULL ,
`access_token`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_apkinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_apkinfo`;
CREATE TABLE `tab_apkinfo` (
`id`  int(2) NOT NULL AUTO_INCREMENT ,
`apk_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`apk_version`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`apk_updatetime`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=51 */

;

-- ----------------------------
-- Table structure for `tab_apkuser`
-- ----------------------------
DROP TABLE IF EXISTS `tab_apkuser`;
CREATE TABLE `tab_apkuser` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`apkuser_birthyear`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`apkuser_sex`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`device_imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=151 */

;

-- ----------------------------
-- Table structure for `tab_appclass`
-- ----------------------------
DROP TABLE IF EXISTS `tab_appclass`;
CREATE TABLE `tab_appclass` (
`id`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_appstore`
-- ----------------------------
DROP TABLE IF EXISTS `tab_appstore`;
CREATE TABLE `tab_appstore` (
`app_id`  int(6) NOT NULL AUTO_INCREMENT ,
`app_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_class`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_downnum`  int(11) NULL DEFAULT 0 ,
`app_downurl`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_icon`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`app_score`  int(10) NULL DEFAULT NULL ,
`app_isshow`  bit(1) NULL DEFAULT b'00000001' ,
`user_id`  int(6) NULL DEFAULT NULL ,
`app_size`  int(11) NULL DEFAULT NULL ,
`app_uptime`  datetime NULL DEFAULT NULL ,
`app_memo`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`app_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=6 */

;

-- ----------------------------
-- Table structure for `tab_area_region`
-- ----------------------------
DROP TABLE IF EXISTS `tab_area_region`;
CREATE TABLE `tab_area_region` (
`id`  int(4) NOT NULL AUTO_INCREMENT ,
`depart_area`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`depart_region`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`com_no`  char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '100003' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=9 */

;

-- ----------------------------
-- Table structure for `tab_autopushlog`
-- ----------------------------
DROP TABLE IF EXISTS `tab_autopushlog`;
CREATE TABLE `tab_autopushlog` (
`logid`  int(11) NOT NULL AUTO_INCREMENT ,
`queid`  int(11) NULL DEFAULT NULL ,
`starttime`  datetime NULL DEFAULT NULL ,
`endtime`  datetime NULL DEFAULT NULL ,
`pushtime`  datetime NULL DEFAULT NULL ,
`users`  varchar(8000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`logid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=124 */

;

-- ----------------------------
-- Table structure for `tab_company`
-- ----------------------------
DROP TABLE IF EXISTS `tab_company`;
CREATE TABLE `tab_company` (
`com_no`  int(6) NOT NULL AUTO_INCREMENT ,
`com_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_linkman_name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_linkman_email`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_linkman_phonenum`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_address`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '????id' ,
`com_cardid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`com_status`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '????' ,
`com_description`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`queid`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`com_no`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=100003 */

;

-- ----------------------------
-- Table structure for `tab_curque`
-- ----------------------------
DROP TABLE IF EXISTS `tab_curque`;
CREATE TABLE `tab_curque` (
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`comm`  varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`spec`  varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`imei`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_department`
-- ----------------------------
DROP TABLE IF EXISTS `tab_department`;
CREATE TABLE `tab_department` (
`com_no`  int(6) NOT NULL ,
`depart_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`depart_linkman_name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`depart_linkman_email`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`depart_linkman_phonenum`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`depart_address`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '????id' ,
`depart_cardid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`depart_status`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '????' ,
`depart_description`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`depart_area`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '????' ,
`depart_region`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '????' ,
`depart_no`  int(6) NOT NULL AUTO_INCREMENT ,
PRIMARY KEY (`depart_no`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=100245 */

;

-- ----------------------------
-- Table structure for `tab_deviceinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_deviceinfo`;
CREATE TABLE `tab_deviceinfo` (
`device_id`  int(11) NOT NULL AUTO_INCREMENT ,
`device_number`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '????' ,
`device_type`  char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '????' ,
`device_imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`device_imsi`  char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '??' ,
`device_os_version`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '???????' ,
`mobile_area`  char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '???' ,
`mobile_type`  char(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '??? ?? ' ,
`user_sex`  tinyint(1) NULL DEFAULT NULL COMMENT '0 ??? 1?? 2? ?' ,
`user_regtime`  timestamp NULL DEFAULT NULL COMMENT '??????' ,
`user_birth`  char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user_unit`  int(6) NULL DEFAULT NULL COMMENT '??????' ,
`device_seller`  int(6) NULL DEFAULT NULL COMMENT '?????' ,
`seller_depid`  int(6) NULL DEFAULT NULL COMMENT '????????' ,
`seller_empid`  int(6) NULL DEFAULT NULL COMMENT '??????' ,
`enter_time`  timestamp NULL DEFAULT NULL COMMENT '??????????' ,
`app_mode`  tinyint(1) NULL DEFAULT NULL COMMENT '0???1???' ,
`app_modechanged_status`  tinyint(1) NULL DEFAULT NULL COMMENT '?????? 0??? 1????' ,
`modechage_task`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '??????' ,
`mode_change_type`  tinyint(1) NULL DEFAULT 1 COMMENT '1??????? 0??????' ,
`nick_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`update_key`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`device_id`),
UNIQUE INDEX `indeximei` USING BTREE (`device_imei`),
INDEX `indextime` USING BTREE (`enter_time`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=525 */

;

-- ----------------------------
-- Table structure for `tab_employee`
-- ----------------------------
DROP TABLE IF EXISTS `tab_employee`;
CREATE TABLE `tab_employee` (
`com_no`  int(6) NOT NULL ,
`depart_no`  int(6) NULL DEFAULT NULL ,
`emp_no`  int(6) NOT NULL AUTO_INCREMENT ,
`emp_sex`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_age`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_phonenum`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_email`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_status`  int(1) NULL DEFAULT 1 COMMENT '0???? 1????' ,
`depart_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`depart_area`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`depart_region`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_picname`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_duty`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`emp_id`  int(8) NULL DEFAULT NULL COMMENT '??????' ,
PRIMARY KEY (`emp_no`),
INDEX `emp_fk` USING BTREE (`depart_no`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=101246 */

;

-- ----------------------------
-- Table structure for `tab_holiday`
-- ----------------------------
DROP TABLE IF EXISTS `tab_holiday`;
CREATE TABLE `tab_holiday` (
`id`  int(11) NOT NULL DEFAULT 0 ,
`holiday`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_iconinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_iconinfo`;
CREATE TABLE `tab_iconinfo` (
`icon_id`  int(6) NOT NULL AUTO_INCREMENT ,
`icon_title`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`icon_url`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`icon_manager`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`icon_savetime`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`icon_name`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`flag`  tinyint(1) NOT NULL DEFAULT 1 ,
`icon_class`  varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`icon_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=29 */

;

-- ----------------------------
-- Table structure for `tab_mobileinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_mobileinfo`;
CREATE TABLE `tab_mobileinfo` (
`id`  int(10) NOT NULL ,
`mobile_num`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_area`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`area_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`post_code`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `phonenum` USING BTREE (`mobile_num`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_mobilepublickey`
-- ----------------------------
DROP TABLE IF EXISTS `tab_mobilepublickey`;
CREATE TABLE `tab_mobilepublickey` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`imei`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`public_key`  varchar(220) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`save_time`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`update_time`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=10 */

;

-- ----------------------------
-- Table structure for `tab_order`
-- ----------------------------
DROP TABLE IF EXISTS `tab_order`;
CREATE TABLE `tab_order` (
`order_id`  int(11) NOT NULL ,
`order_user`  int(11) NULL DEFAULT NULL ,
`order_prod`  int(11) NULL DEFAULT NULL ,
`order_creatime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`order_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_org`
-- ----------------------------
DROP TABLE IF EXISTS `tab_org`;
CREATE TABLE `tab_org` (
`org_id`  int(6) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT ,
`org_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`org_linkman_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`org_linkman_phonenum`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`org_linkman_email`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`org_status`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '????' ,
`org_address`  varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`org_parent_id`  int(6) NULL DEFAULT NULL ,
`org_description`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`depart_area`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`depart_region`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`org_idcard`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`org_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=100252 */

;

-- ----------------------------
-- Table structure for `tab_pic`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pic`;
CREATE TABLE `tab_pic` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`pic_info`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_weight`  int(2) NULL DEFAULT NULL ,
`pic_type`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_puttimebegin`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pic_puttimeend`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`internal_time`  int(4) NULL DEFAULT NULL ,
`display_time`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=29 */

;

-- ----------------------------
-- Table structure for `tab_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `tab_privilege`;
CREATE TABLE `tab_privilege` (
`privilege_id`  int(4) NOT NULL AUTO_INCREMENT ,
`privilege_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`privilege_description`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`privilege_id`),
UNIQUE INDEX `name` USING BTREE (`privilege_name`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=8 */

;

-- ----------------------------
-- Table structure for `tab_product`
-- ----------------------------
DROP TABLE IF EXISTS `tab_product`;
CREATE TABLE `tab_product` (
`prod_id`  int(11) NOT NULL ,
`prod_prov`  int(11) NULL DEFAULT NULL ,
`prod_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`prod_startime`  datetime NULL DEFAULT NULL ,
`prod_detail`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`prod_total`  int(11) NULL DEFAULT NULL ,
`prod_num`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`prod_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_provider`
-- ----------------------------
DROP TABLE IF EXISTS `tab_provider`;
CREATE TABLE `tab_provider` (
`prov_id`  int(11) NOT NULL ,
`prov_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`prov_weburl`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`prov_detail`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`prov_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pusharea`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pusharea`;
CREATE TABLE `tab_pusharea` (
`id`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushatom`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushatom`;
CREATE TABLE `tab_pushatom` (
`aid`  int(11) NOT NULL DEFAULT 0 ,
`cid`  int(11) NULL DEFAULT NULL ,
`showtime`  float NULL DEFAULT NULL ,
`enddate`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`aid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushauto_location`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushauto_location`;
CREATE TABLE `tab_pushauto_location` (
`location`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' ,
`name`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`que`  int(11) NULL DEFAULT NULL ,
`duration`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`location`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushauto_screen`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushauto_screen`;
CREATE TABLE `tab_pushauto_screen` (
`mini`  int(11) NOT NULL DEFAULT 0 ,
`que`  int(11) NULL DEFAULT NULL ,
`duration`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`mini`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushauto_time`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushauto_time`;
CREATE TABLE `tab_pushauto_time` (
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`exptime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`imei`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushcode`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushcode`;
CREATE TABLE `tab_pushcode` (
`codeid`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`codeid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

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
`classify`  varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
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
`code`  varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL ,
`value`  varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL ,
`que`  int(11) NULL DEFAULT NULL ,
`duration`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`code`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin

;

-- ----------------------------
-- Table structure for `tab_pushdefault`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushdefault`;
CREATE TABLE `tab_pushdefault` (
`id`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushdevice`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushdevice`;
CREATE TABLE `tab_pushdevice` (
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`mbno`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`company`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`gender`  int(1) NULL DEFAULT NULL ,
`birth`  date NULL DEFAULT NULL ,
`mbos`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`area`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`provider`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbtype`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller_depid`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller_stuffid`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regtime`  datetime NULL DEFAULT NULL ,
`tsort`  int(11) NULL DEFAULT NULL ,
`theme`  tinyint(1) NULL DEFAULT NULL ,
`themestatus`  tinyint(1) NULL DEFAULT NULL ,
`themetask`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  int(1) NULL DEFAULT 1 ,
PRIMARY KEY (`imei`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushdevice_copy`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushdevice_copy`;
CREATE TABLE `tab_pushdevice_copy` (
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`mbno`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`company`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`gender`  int(1) NULL DEFAULT NULL ,
`birth`  date NULL DEFAULT NULL ,
`mbos`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`area`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`provider`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mbtype`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller_depid`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seller_stuffid`  varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`imsi`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regtime`  datetime NULL DEFAULT NULL ,
`tsort`  int(11) NULL DEFAULT NULL ,
`theme`  tinyint(1) NULL DEFAULT NULL ,
`themestatus`  tinyint(1) NULL DEFAULT NULL ,
`themetask`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`imei`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pusherror`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pusherror`;
CREATE TABLE `tab_pusherror` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`type`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '??' ,
`info`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=74 */

;

-- ----------------------------
-- Table structure for `tab_pusherrorlog`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pusherrorlog`;
CREATE TABLE `tab_pusherrorlog` (
`eid`  int(11) NOT NULL AUTO_INCREMENT ,
`errid`  int(11) NULL DEFAULT NULL ,
`errtxt`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`errmethod`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`inmsg`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`outmsg`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`time`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`eid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=63034 */

;

-- ----------------------------
-- Table structure for `tab_pushinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushinfo`;
CREATE TABLE `tab_pushinfo` (
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`curque`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`recstatus`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`allque`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`imei`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushlist`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushlist`;
CREATE TABLE `tab_pushlist` (
`pid`  int(11) NOT NULL ,
`title`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`queid`  int(11) NULL DEFAULT NULL ,
`ptype`  int(1) NULL DEFAULT NULL ,
`pushtime`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`priori`  int(1) NOT NULL DEFAULT 0 ,
`enddate`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`puid`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flag`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' ,
PRIMARY KEY (`pid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushmbno`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushmbno`;
CREATE TABLE `tab_pushmbno` (
`mobile_num`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`mobile_area`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_type`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`area_code`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`post_code`  char(7) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`mobile_num`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushmbos`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushmbos`;
CREATE TABLE `tab_pushmbos` (
`id`  varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
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
-- Table structure for `tab_pushprovider`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushprovider`;
CREATE TABLE `tab_pushprovider` (
`id`  varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushque`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushque`;
CREATE TABLE `tab_pushque` (
`qid`  int(11) NOT NULL DEFAULT 0 ,
`title`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`que`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`oper`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flag`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' ,
PRIMARY KEY (`qid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushrecpics`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushrecpics`;
CREATE TABLE `tab_pushrecpics` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`imei`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`inputpics`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`exepcpics`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`retvalue`  bit(1) NULL DEFAULT NULL ,
`rettime`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=5606 */

;

-- ----------------------------
-- Table structure for `tab_pushtest`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushtest`;
CREATE TABLE `tab_pushtest` (
`id`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_pushthemetask`
-- ----------------------------
DROP TABLE IF EXISTS `tab_pushthemetask`;
CREATE TABLE `tab_pushthemetask` (
`tid`  int(11) NOT NULL ,
`tname`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`theme`  tinyint(1) NULL DEFAULT NULL ,
`ttime`  datetime NULL DEFAULT NULL ,
`opertime`  datetime NULL DEFAULT NULL ,
`operator`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`tid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_role`
-- ----------------------------
DROP TABLE IF EXISTS `tab_role`;
CREATE TABLE `tab_role` (
`role_id`  int(4) NOT NULL AUTO_INCREMENT ,
`role_name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`role_description`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`role_id`),
UNIQUE INDEX `name` USING BTREE (`role_name`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=4 */

;

-- ----------------------------
-- Table structure for `tab_role_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `tab_role_privilege`;
CREATE TABLE `tab_role_privilege` (
`role_id`  int(4) NOT NULL ,
`privilege_id`  int(4) NOT NULL ,
PRIMARY KEY (`role_id`, `privilege_id`),
FOREIGN KEY (`role_id`) REFERENCES `tab_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`privilege_id`) REFERENCES `tab_privilege` (`privilege_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `privilege_id_FK` USING BTREE (`privilege_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`role_id`) REFER `mydb01/tab_role`(`role_id`); (`privile'

;

-- ----------------------------
-- Table structure for `tab_serverkeys`
-- ----------------------------
DROP TABLE IF EXISTS `tab_serverkeys`;
CREATE TABLE `tab_serverkeys` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`private_key`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`public_key`  varchar(130) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`save_time`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`update_time`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=2 */

;

-- ----------------------------
-- Table structure for `tab_sharekey`
-- ----------------------------
DROP TABLE IF EXISTS `tab_sharekey`;
CREATE TABLE `tab_sharekey` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`imei`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`share_key`  varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`save_time`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`update_time`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=16 */

;

-- ----------------------------
-- Table structure for `tab_slidetabs_info`
-- ----------------------------
DROP TABLE IF EXISTS `tab_slidetabs_info`;
CREATE TABLE `tab_slidetabs_info` (
`tab_id`  int(11) NOT NULL AUTO_INCREMENT ,
`tab_name`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`tab_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=20 */

;

-- ----------------------------
-- Table structure for `tab_tabinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_tabinfo`;
CREATE TABLE `tab_tabinfo` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`tab_name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`area_code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile_area`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=4 */

;

-- ----------------------------
-- Table structure for `tab_test`
-- ----------------------------
DROP TABLE IF EXISTS `tab_test`;
CREATE TABLE `tab_test` (
`id`  char(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`vv`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_test1`
-- ----------------------------
DROP TABLE IF EXISTS `tab_test1`;
CREATE TABLE `tab_test1` (
`imei`  varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`hehe`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_time`
-- ----------------------------
DROP TABLE IF EXISTS `tab_time`;
CREATE TABLE `tab_time` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`interval_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`location_interval`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`update_pic_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=2 */

;

-- ----------------------------
-- Table structure for `tab_url`
-- ----------------------------
DROP TABLE IF EXISTS `tab_url`;
CREATE TABLE `tab_url` (
`id`  int(4) NOT NULL ,
`old_url`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`new_url`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_user`
-- ----------------------------
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user` (
`user_id`  int(4) NOT NULL AUTO_INCREMENT ,
`username`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`com_no`  char(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`is_seller`  int(1) NOT NULL DEFAULT 0 COMMENT '0????????? 1????????' ,
`user_status`  int(1) NOT NULL DEFAULT 1 COMMENT '0???????1????????' ,
PRIMARY KEY (`user_id`),
UNIQUE INDEX `username` USING BTREE (`username`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=14 */

;

-- ----------------------------
-- Table structure for `tab_user_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_privilege`;
CREATE TABLE `tab_user_privilege` (
`user_id`  int(4) NOT NULL ,
`privilege_id`  int(4) NOT NULL ,
PRIMARY KEY (`user_id`, `privilege_id`),
FOREIGN KEY (`user_id`) REFERENCES `tab_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`privilege_id`) REFERENCES `tab_privilege` (`privilege_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `user_privilege_fk2` USING BTREE (`privilege_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`user_id`) REFER `mydb01/tab_user`(`user_id`); (`privile'

;

-- ----------------------------
-- Table structure for `tab_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_role`;
CREATE TABLE `tab_user_role` (
`user_id`  int(4) NOT NULL ,
`role_id`  int(4) NOT NULL ,
PRIMARY KEY (`user_id`, `role_id`),
FOREIGN KEY (`user_id`) REFERENCES `tab_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`role_id`) REFERENCES `tab_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `role_id_FK1` USING BTREE (`role_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`user_id`) REFER `mydb01/tab_user`(`user_id`); (`role_id'

;

-- ----------------------------
-- Table structure for `tab_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_userinfo`;
CREATE TABLE `tab_userinfo` (
`user_id`  int(11) NOT NULL ,
`user_phone`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`user_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='?û????'

;

-- ----------------------------
-- Table structure for `tab_wechat`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wechat`;
CREATE TABLE `tab_wechat` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`wx_num`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`phone_num`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`regist_date`  date NULL DEFAULT NULL ,
PRIMARY KEY (`wx_num`),
INDEX `id` USING BTREE (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=80 */

;

-- ----------------------------
-- Table structure for `tab_wxcodeinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxcodeinfo`;
CREATE TABLE `tab_wxcodeinfo` (
`code_id`  int(2) NOT NULL AUTO_INCREMENT COMMENT '?????' ,
`consum_code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`device_number`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`name_id`  int(2) NULL DEFAULT NULL COMMENT '?????' ,
`start_date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`end_date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`content`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`type`  int(2) NULL DEFAULT NULL COMMENT '?? 0????1????' ,
`status`  int(2) NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`consum_code`),
FOREIGN KEY (`name_id`) REFERENCES `tab_company` (`com_no`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `code_id` USING BTREE (`code_id`),
INDEX `name_id` USING BTREE (`name_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`name_id`) REFER `mydb01/tab_company`(`com_no`)'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Table structure for `tab_wxcodeinfostatus`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxcodeinfostatus`;
CREATE TABLE `tab_wxcodeinfostatus` (
`id`  int(2) NOT NULL COMMENT '?????' ,
`name`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_wxcodetrade`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxcodetrade`;
CREATE TABLE `tab_wxcodetrade` (
`id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '?????' ,
`device_number`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`consum_code`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
`product_code`  int(5) NULL DEFAULT NULL COMMENT '??????' ,
`consum_date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`memo`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`),
FOREIGN KEY (`consum_code`) REFERENCES `tab_wxcodeinfo` (`consum_code`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `consum_code` USING BTREE (`consum_code`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`consum_code`) REFER `mydb01/tab_wxcodeinfo`(`consum_cod'
/*!50003 AUTO_INCREMENT=1 */

;

-- ----------------------------
-- Table structure for `tab_wxcounts`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxcounts`;
CREATE TABLE `tab_wxcounts` (
`id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '??????????' ,
`device_number`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`number_count`  int(10) NOT NULL COMMENT '????' ,
`score_count`  int(15) NOT NULL COMMENT '????' ,
`in_count`  int(10) NULL DEFAULT NULL ,
`out_count`  int(10) UNSIGNED NULL DEFAULT 0 ,
`nums`  int(5) UNSIGNED ZEROFILL NULL DEFAULT 00000 ,
PRIMARY KEY (`device_number`),
INDEX `id` USING BTREE (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=236 */

;

-- ----------------------------
-- Table structure for `tab_wxoperator`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxoperator`;
CREATE TABLE `tab_wxoperator` (
`id`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???id' ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '?????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_wxproduct`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxproduct`;
CREATE TABLE `tab_wxproduct` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '????' ,
`name`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`type`  int(5) NOT NULL COMMENT '????' ,
`operator`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`weight`  int(5) NOT NULL COMMENT '????' ,
`price`  int(5) NOT NULL COMMENT '??(????)' ,
`pic_path`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??????' ,
`nums`  int(5) NOT NULL COMMENT '????' ,
`sold`  int(5) NULL DEFAULT 0 COMMENT '???' ,
`status`  int(3) NOT NULL COMMENT '??:0 ???? 1??' ,
`start_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`end_date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
`manager`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`memo`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`),
FOREIGN KEY (`type`) REFERENCES `tab_wxproducttype` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`operator`) REFERENCES `tab_wxoperator` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `type` USING BTREE (`type`),
INDEX `operator` USING BTREE (`operator`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`type`) REFER `mydb01/tab_wxproducttype`(`id`); (`operat'
/*!50003 AUTO_INCREMENT=1004 */

;

-- ----------------------------
-- Table structure for `tab_wxproducttype`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxproducttype`;
CREATE TABLE `tab_wxproducttype` (
`id`  int(10) NOT NULL COMMENT '????' ,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_wxscoreinstatus`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxscoreinstatus`;
CREATE TABLE `tab_wxscoreinstatus` (
`id`  int(2) NOT NULL COMMENT '???????' ,
`name`  varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_wxscoreintrade`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxscoreintrade`;
CREATE TABLE `tab_wxscoreintrade` (
`id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '?????' ,
`device_number`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`score`  int(10) NOT NULL COMMENT '??' ,
`start_date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`end_date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`status`  int(2) NOT NULL COMMENT '??' ,
FOREIGN KEY (`status`) REFERENCES `tab_wxscoreinstatus` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `id` USING BTREE (`id`),
INDEX `status` USING BTREE (`status`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`status`) REFER `mydb01/tab_wxscoreinstatus`(`id`)'
/*!50003 AUTO_INCREMENT=1915 */

;

-- ----------------------------
-- Table structure for `tab_wxscoretrade`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxscoretrade`;
CREATE TABLE `tab_wxscoretrade` (
`trade_id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '???????' ,
`device_number`  varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '???' ,
`score`  int(10) NOT NULL COMMENT '??' ,
`date`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`status`  int(2) NOT NULL COMMENT '??' ,
PRIMARY KEY (`trade_id`),
FOREIGN KEY (`status`) REFERENCES `tab_wxscoretrade_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `status` USING BTREE (`status`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`status`) REFER `mydb01/tab_wxscoretrade_status`(`id`)'
/*!50003 AUTO_INCREMENT=2005 */

;

-- ----------------------------
-- Table structure for `tab_wxscoretrade_status`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxscoretrade_status`;
CREATE TABLE `tab_wxscoretrade_status` (
`id`  int(2) NOT NULL COMMENT '???????' ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '??' ,
`type`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for `tab_wxscoretradeinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxscoretradeinfo`;
CREATE TABLE `tab_wxscoretradeinfo` (
`trade_id`  int(10) NOT NULL COMMENT '???id ????' ,
`product_id`  int(10) NOT NULL COMMENT '????id' ,
PRIMARY KEY (`trade_id`),
FOREIGN KEY (`trade_id`) REFERENCES `tab_wxscoretrade` (`trade_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`product_id`) REFERENCES `tab_wxproduct` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
INDEX `trade_id` USING BTREE (`trade_id`),
INDEX `product_id` USING BTREE (`product_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='InnoDB free: 40960 kB; (`trade_id`) REFER `mydb01/tab_wxscoretrade`(`trade_id`);'

;

-- ----------------------------
-- Table structure for `tab_wxthemelog`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxthemelog`;
CREATE TABLE `tab_wxthemelog` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`phone_num`  char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '???' ,
`current_theme`  int(1) NULL DEFAULT NULL COMMENT '????' ,
`change_theme`  int(1) NULL DEFAULT NULL COMMENT '??????' ,
`date`  char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '????' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=4 */

;

-- ----------------------------
-- Table structure for `tab_wxtitle`
-- ----------------------------
DROP TABLE IF EXISTS `tab_wxtitle`;
CREATE TABLE `tab_wxtitle` (
`id`  int(5) NOT NULL AUTO_INCREMENT ,
`content`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '????' ,
`start_score`  int(5) NULL DEFAULT NULL COMMENT '??' ,
`end_score`  int(5) NULL DEFAULT NULL COMMENT '??' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
/*!50003 AUTO_INCREMENT=7 */

;

-- ----------------------------
-- View structure for `tab_pushdetail`
-- ----------------------------
DROP VIEW IF EXISTS `tab_pushdetail`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `tab_pushdetail` AS select `a`.`device_imei` AS `imei`,`a`.`device_number` AS `mbno`,`a`.`user_unit` AS `company`,`a`.`device_seller` AS `seller`,`a`.`user_sex` AS `gender`,`a`.`user_birth` AS `birth`,`a`.`device_os_version` AS `mbos`,`a`.`mobile_area` AS `area`,`a`.`mobile_type` AS `provider`,`a`.`device_type` AS `mbtype`,`a`.`seller_depid` AS `seller_depid`,`a`.`seller_empid` AS `seller_empid`,`a`.`device_imsi` AS `imsi`,`a`.`enter_time` AS `regtime`,`a`.`app_mode` AS `theme`,`a`.`app_modechanged_status` AS `themestatus`,`b`.`allque` AS `allque`,`b`.`curque` AS `curque`,`b`.`recstatus` AS `recstatus`,`b`.`queid` AS `queid` from (`tab_deviceinfo` `a` left join `tab_pushinfo` `b` on((`a`.`device_imei` = `b`.`imei`))) ;

-- ----------------------------
-- View structure for `view_deviceinfo`
-- ----------------------------
DROP VIEW IF EXISTS `view_deviceinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_deviceinfo` AS select `a`.`imei` AS `imei`,`a`.`mbno` AS `mbno`,`a`.`company` AS `company`,`a`.`seller` AS `seller`,`a`.`gender` AS `gender`,`a`.`birth` AS `birth`,`a`.`mbos` AS `mbos`,`a`.`area` AS `area`,`a`.`provider` AS `provider`,`a`.`mbtype` AS `mbtype`,`a`.`seller_depid` AS `seller_depid`,`a`.`seller_empid` AS `seller_empid`,`a`.`imsi` AS `imsi`,`a`.`regtime` AS `regtime`,`a`.`theme` AS `theme`,`a`.`themestatus` AS `themestatus`,`a`.`allque` AS `allque`,`a`.`curque` AS `curque`,`a`.`recstatus` AS `recstatus`,`a`.`queid` AS `queid`,`b`.`emp_duty` AS `duty`,concat(`a`.`company`,_utf8'_',`b`.`emp_duty`) AS `position` from (`tab_pushdetail` `a` left join `tab_employee` `b` on((`a`.`mbno` = `b`.`emp_phonenum`))) ;

-- ----------------------------
-- View structure for `view_pushcontent`
-- ----------------------------
DROP VIEW IF EXISTS `view_pushcontent`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_pushcontent` AS select `aa`.`cid` AS `cid`,`aa`.`sort` AS `sort`,`aa`.`title` AS `title`,`aa`.`operator` AS `operator`,`aa`.`namex` AS `namex`,`aa`.`classify` AS `classify`,`aa`.`size` AS `size`,`aa`.`operdate` AS `operdate`,`aa`.`link` AS `link`,`aa`.`icon` AS `icon`,`aa`.`scolor` AS `scolor`,`aa`.`flag` AS `flag`,`bb`.`com_no` AS `com_no`,`cc`.`name` AS `classifyName` from ((`tab_pushcontent` `aa` left join `tab_user` `bb` on((`aa`.`operator` = `bb`.`user_id`))) left join `tab_pushcontentclass` `cc` on((convert(`aa`.`classify` using utf8) = `cc`.`code`))) ;

-- ----------------------------
-- View structure for `view_pushlist`
-- ----------------------------
DROP VIEW IF EXISTS `view_pushlist`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_pushlist` AS select `a`.`pid` AS `pid`,`a`.`title` AS `title`,`a`.`queid` AS `queid`,`a`.`flag` AS `flag`,`a`.`ptype` AS `ptype`,`a`.`pushtime` AS `pushtime`,`a`.`priori` AS `priori`,`a`.`enddate` AS `enddate`,`a`.`oper` AS `oper`,`a`.`puid` AS `puid`,`b`.`com_no` AS `com_no` from (`tab_pushlist` `a` left join `tab_user` `b` on((`a`.`oper` = `b`.`user_id`))) ;

-- ----------------------------
-- View structure for `view_pushque`
-- ----------------------------
DROP VIEW IF EXISTS `view_pushque`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_pushque` AS select `a`.`qid` AS `qid`,`a`.`title` AS `title`,`a`.`que` AS `que`,`a`.`flag` AS `flag`,`a`.`oper` AS `oper`,`b`.`com_no` AS `com_no` from (`tab_pushque` `a` left join `tab_user` `b` on((`a`.`oper` = `b`.`user_id`))) ;

-- ----------------------------
-- Procedure structure for `AAAAA_Search`
-- ----------------------------
DROP PROCEDURE IF EXISTS `AAAAA_Search`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AAAAA_Search`(IN x int)
BEGIN  
select * from tab_pushcontent 
where 
LOCATE(CONCAT(",",cid,","),CONCAT(",",(select que from tab_pushque where qid=(select queid from tab_pushlist where pid=x))))>0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `ComQueList`
-- ----------------------------
DROP PROCEDURE IF EXISTS `ComQueList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `ComQueList`(IN id INT)
BEGIN  
IF id=100000 THEN 
select com_no,
case WHEN com_name='?????' then '??????'
else com_name
end as com_name ,queid as qid,(select title from tab_pushque where qid=queid) as title from tab_company; 
ELSE 
select com_no,com_name,queid as qid,(select title from tab_pushque where qid=queid) as title from tab_company where com_no=id; 
END IF;
-- if @x REGEXP '[[:digit:]]' 
-- then select 'sdsd';
-- END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `deleteaccepter`
-- ----------------------------
DROP PROCEDURE IF EXISTS `deleteaccepter`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `deleteaccepter`()
delete from tab_pushdetail where imei in 
('864205023394695','865864011262618','358312050267266','359786051321009')
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `DeletePushQueue`
-- ----------------------------
DROP PROCEDURE IF EXISTS `DeletePushQueue`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `DeletePushQueue`(IN aqid INT)
BEGIN  
DECLARE oldque VARCHAR(256);
select que INTO oldque from tab_pushque where qid=aqid;
set @sqlx=CONCAT("DELETE from tab_pushatom where aid in (",left(oldque,CHAR_LENGTH(oldque)-1),")");
prepare stmt from @sqlx;
EXECUTE stmt;    
set @sqly=CONCAT("delete from tab_pushque where qid=",aqid);
prepare stmt from @sqly;
EXECUTE stmt;  
deallocate prepare stmt; 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertMbthemeItem`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertMbthemeItem`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertMbthemeItem`(IN atname VARCHAR(100),IN atheme INT,IN attime VARCHAR(30),IN aoper varchar(40))
BEGIN  

DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT tid FROM tab_pushthemetask;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;
INSERT INTO tab_pushthemetask(tid,tname,theme,ttime,opertime,operator) VALUES (i,atname,atheme,attime,NOW(),aoper);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertPushAtom`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertPushAtom`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertPushAtom`(IN acid INT,IN ashowtime FLOAT,IN aenddate VARCHAR(30))
BEGIN  


DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT aid FROM tab_pushatom;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;
INSERT INTO tab_pushatom(aid,cid,showtime,enddate) VALUES (i,acid,ashowtime,aenddate);
select i;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertPushContent`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertPushContent`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertPushContent`(IN atitle VARCHAR(50),IN asort INT,IN aoperator VARCHAR(100),IN anamex VARCHAR(100),IN asize INT,IN aoperdate  varchar(30),IN alink varchar(200),IN aiconstr varchar(200),IN color varchar(6),IN aclassify varchar(50))
BEGIN  

DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT cid FROM tab_pushcontent where cid>=0;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;
INSERT INTO tab_pushcontent(cid,sort,title,operator,namex,size,operdate,link,icon,scolor,classify) VALUES (i,asort,atitle,aoperator,anamex,asize,aoperdate,alink,aiconstr,color,aclassify);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertPushItem`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertPushItem`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertPushItem`(IN atitle VARCHAR(100),IN aqueid INT,IN aptype INT,IN apushtime VARCHAR(30),IN apriori INT,IN aenddate  varchar(30),IN aoper varchar(40))
BEGIN  

DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT pid FROM tab_pushlist;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;
INSERT INTO tab_pushlist(pid,title,queid,ptype,pushtime,priori,enddate,oper,puid) VALUES (i,atitle,aqueid,aptype,apushtime,apriori,aenddate,aoper,UUID());
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertPushList`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertPushList`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertPushList`(IN aqueid INT,IN apushtime VARCHAR(30),IN aenddate  varchar(30))
BEGIN  
DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT pid FROM tab_pushlist;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;
INSERT INTO tab_pushlist(pid,title,queid,ptype,pushtime,priori,enddate,oper,puid,flag) VALUES (i,'auto',aqueid,2,apushtime,1,aenddate,'1',UUID(),2);
select i;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertPushLog`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertPushLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertPushLog`(IN aoper INT,IN aimeis text,IN apid INT,IN aoperator VARCHAR(30))
BEGIN 

set @imei='error';
set @puuid='error';
set @imeis='error';
select puid into @puuid  from tab_pushlist where pid=apid;
set @imeis=aimeis;
while IFNULL(@imeis,'')<>'' DO
set @imei=SUBSTRING_INDEX(@imeis,',',1);

set @nowdate=now();
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_pushlog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(
  `hid` char(36) NOT NULL,
  `imei` varchar(50) default NULL,
  `pop` tinyint default 0,
  `puid` char(36) default NULL,
  `htime` datetime default NULL,
  `oper` varchar(40) default NULL,
  PRIMARY KEY  (`hid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt;
SET @t_sql=CONCAT("INSERT INTO mydb02.tab_pushlog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(hid,imei,pop,puid,htime,oper) VALUES(UUID(),'",
                  @imei,"',",aoper,",'",@puuid,"','",@nowdate,"','",aoperator,"')");
prepare stmt from @t_sql;
EXECUTE stmt; 

set @imeis=REPLACE(@imeis,CONCAT(@imei,','),'');
END WHILE;

deallocate prepare stmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertPushQueue`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertPushQueue`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertPushQueue`(IN atitle VARCHAR(100),IN aque VARCHAR(256),IN aoperator VARCHAR(40))
BEGIN  

DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT qid FROM tab_pushque;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;
INSERT INTO tab_pushque(qid,title,que,oper) VALUES(i,atitle,aque,aoperator);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertSlideRecord`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertSlideRecord`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertSlideRecord`(IN aid VARCHAR(36),IN aimei VARCHAR(50),IN apicname VARCHAR(50),IN aslidetime VARCHAR(30))
BEGIN 
IF IFNULL(aslidetime,'') REGEXP '[0-9]{4}-[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]' THEN
SET @tbname=CONCAT(LEFT(aslidetime,4),SUBSTRING(aslidetime,6,2));
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_slideinfo_",@tbname,"(
  `id` varchar(36) NOT NULL,
  `imei` varchar(50) default NULL,
  `mbno` varchar(20) default NULL,
  `picname` varchar(50) default NULL,
  `pictitle` varchar(50) default NULL,
  `slidetime` datetime default NULL,
  `com` varchar(50) default NULL,
  `seller` varchar(50) default NULL,
  `gender` varchar(2) default NULL,
  `birth` date default NULL,
  `mbos` varchar(20) default NULL,
  `mbarea` varchar(30) default NULL,
  `mbprovi` varchar(30) default NULL,
  `mbtype` varchar(50) default NULL,
  `seller_dep` varchar(50) default NULL,
  `seller_stuff` varchar(50) default NULL,
  `imsi` varchar(50) default NULL,
  `regtime` datetime default NULL,
  `sort` int default 0,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt; 
SELECT mbno,EnCompany(company),EnCompany(seller),gender,birth,EnMbOs(mbos),EnMbArea(area),EnMbProvider(provider),EnMbType(mbtype),seller_depid,seller_stuffid,imsi,regtime
INTO @ambno,@acompany,@aseller,@agender,@abirth,@ambos,@aarea,@aprovider,@ambtype,@aseller_depid,@aseller_stuffid,@aimsi,@aregtime
from tab_pushdevice where imei=aimei LIMIT 0,1;
SELECT title,sort INTO @apictitle,@asort from tab_pushcontent where namex=apicname LIMIT 0,1;
SELECT depart_name,CONCAT(emp_id,':',emp_name) INTO @aseller_dep,@aseller_stuff from tab_employee where depart_no=@aseller_depid and emp_no=@aseller_stuffid LIMIT 0,1;
IF @aseller_stuff=':' THEN SET @aseller_stuff=''; END IF;
if CONCAT("'",@abirth,"'")<>'' then SET @abirth=CONCAT("'",@abirth,"'"); END if;
if CONCAT("'",@aregtime,"'")<>'' then SET @aregtime=CONCAT("'",@aregtime,"'"); END if;
SET @t_sql=CONCAT("INSERT INTO  mydb02.tab_slideinfo_",@tbname," VALUES ('",
aid,"','",
aimei,"','",
IFNULL(@ambno,''),  "','",
apicname,"','",
IFNULL(@apictitle,''), "','",
aslidetime ,"','",
IFNULL(@acompany, ''), "','",
IFNULL(@aseller, ''), "','",
IFNULL(@agender, ''), "',",
IFNULL(@abirth, 'Null'),",'",
IFNULL(@ambos, ''), "','",
IFNULL(@aarea,''), "','",
IFNULL(@aprovider, ''), "','",
IFNULL(@ambtype,''), "','",
IFNULL(@aseller_dep, ''), "','",
IFNULL(@aseller_stuff, ''), "','",
IFNULL(@aimsi, ''), "',",
IFNULL(@aregtime, 'Null'),",",
IFNULL(@asort, 0),
")");
prepare stmt from @t_sql;
EXECUTE stmt;
 deallocate prepare stmt;
UPDATE tab_pushdevice set tsort=IFNULL(tsort,0)+@asort where  imei=aimei ;
END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertThmChgLogByBatch`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertThmChgLogByBatch`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertThmChgLogByBatch`(IN nowdate DATETIME)
BEGIN  

Declare id int DEFAULT 0;
Declare thm TINYINT(1) DEFAULT 0;
Declare oper VARCHAR(40);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR select tid,theme,operator from tab_pushthemetask where timediff(nowdate,ttime)<=50000 and nowdate<ttime and status=0 order by opertime asc;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM nowdate),"(
	`tid` char(36) NOT NULL,
	`imei` varchar(50) default NULL,
	`mbno` varchar(15) default NULL,
	`curtheme` tinyint default NULL,
	`exptheme` tinyint default NULL,
	`opersrc` varchar(30) default NULL,
	`operator` varchar(40) default NULL,
	`opertime` datetime default NULL,
  PRIMARY KEY  (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt;
OPEN cur;
  aloop: LOOP
  FETCH cur INTO id,thm,oper;
   IF done THEN
     LEAVE aloop;
   ELSE 
    UPDATE tab_pushdevice  SET theme=thm,themestatus=0 where status<>0 and LOCATE(CONCAT(',',id,','),CONCAT(',',IFNULL(themetask,'')))>0;
    SET @t_sql=CONCAT("INSERT INTO mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM nowdate),"(tid,imei,mbno,curtheme,exptheme,opersrc,operator,opertime) 
                    select UUID() as tid,imei,mbno,theme as curtheme,",thm," as exptheme,'batch' as opensrc,",oper," as operator,NOW() as opertime from tab_pushdevice");
		prepare stmt from @t_sql;
		EXECUTE stmt; 
    UPDATE tab_pushthemetask set status=1 where tid=id;
	 END IF;  	
   END LOOP;
CLOSE cur;
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertThmChgLogByClient`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertThmChgLogByClient`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertThmChgLogByClient`(IN aimei varchar(50),IN thme TINYINT)
BEGIN 

set @mbno='error';
set @curtheme=0;

set @imei=aimei;
if IFNULL(@imei,'')<>'' THEN
select ifnull(mbno,''),IFNULL(theme,0) into @mbno,@curtheme from tab_pushdevice where imei=@imei limit 0,1;

set @nowdate=now();
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(
	`tid` char(36) NOT NULL,
	`imei` varchar(50) default NULL,
	`mbno` varchar(15) default NULL,
	`curtheme` tinyint default NULL,
	`exptheme` tinyint default NULL,
	`opersrc` varchar(30) default NULL,
	`operator` varchar(40) default NULL,
	`opertime` datetime default NULL,
  PRIMARY KEY  (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt;
SET @t_sql=CONCAT("INSERT INTO mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(tid,imei,mbno,curtheme,exptheme,opersrc,operator,opertime) VALUES(UUID(),'",
                  @imei,"','",@mbno,"',null,",thme,",'client',NULL,'",@nowdate,"')");

prepare stmt from @t_sql;
EXECUTE stmt; 

END IF;
deallocate prepare stmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertThmChgLogBySingle`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertThmChgLogBySingle`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertThmChgLogBySingle`(IN aimei varchar(50),IN thme TINYINT,IN oper VARCHAR(40))
BEGIN 

set @mbno='error';
set @curtheme=0;

set @imei=aimei;
if IFNULL(@imei,'')<>'' THEN
select ifnull(mbno,''),IFNULL(theme,0) into @mbno,@curtheme from tab_pushdevice where imei=@imei limit 0,1;

set @nowdate=now();
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(
	`tid` char(36) NOT NULL,
	`imei` varchar(50) default NULL,
	`mbno` varchar(15) default NULL,
	`curtheme` tinyint default NULL,
	`exptheme` tinyint default NULL,
	`opersrc` varchar(30) default NULL,
	`operator` varchar(40) default NULL,
	`opertime` datetime default NULL,
  PRIMARY KEY  (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt;
SET @t_sql=CONCAT("INSERT INTO mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(tid,imei,mbno,curtheme,exptheme,opersrc,operator,opertime) VALUES(UUID(),'",
                  @imei,"','",@mbno,"',",@curtheme,",",thme,",'single','",oper,"','",@nowdate,"')");

prepare stmt from @t_sql;
EXECUTE stmt; 

END IF;
deallocate prepare stmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `InsertThmChgLogByWX`
-- ----------------------------
DROP PROCEDURE IF EXISTS `InsertThmChgLogByWX`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `InsertThmChgLogByWX`(IN ambno VARCHAR(15),IN cthme TINYINT,IN ethme TINYINT)
BEGIN 

set @imei='error';
set @mbno='error';

IF IFNULL(ambno,'')<>'' THEN

set @mbno=ambno;
select imei into @imei from tab_pushdevice where mbno=@mbno limit 0,1;

set @nowdate=now();
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(
	`tid` char(36) NOT NULL,
	`imei` varchar(50) default NULL,
	`mbno` varchar(15) default NULL,
	`curtheme` tinyint default NULL,
	`exptheme` tinyint default NULL,
	`opersrc` varchar(30) default NULL,
	`operator` varchar(40) default NULL,
	`opertime` datetime default NULL,
  PRIMARY KEY  (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt;
SET @t_sql=CONCAT("INSERT INTO mydb02.tab_ThmChgLog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(tid,imei,mbno,curtheme,exptheme,opersrc,operator,opertime) VALUES(UUID(),'",
                  @imei,"','",ambno,"',",cthme,",",ethme,",'weixin',null,'",@nowdate,"')");
prepare stmt from @t_sql;
EXECUTE stmt; 

END IF;

deallocate prepare stmt;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `PushString`
-- ----------------------------
DROP PROCEDURE IF EXISTS `PushString`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `PushString`(IN aimei VARCHAR(255))
BEGIN  
set @str='recieved';
set @com='';
set @sell='';
set @recs='';
set @qqid='';
set @qid='';
set @preque='';
set @curq='';
set @tmpstr='';


if ifnull(aimei,'') REGEXP '^0*$'   then SELECT @str; 
else 

select user_unit,device_seller into @com,@sell from tab_deviceinfo where device_imei=aimei;
select cmpqueid(allque),cmpque(allque),curque,recstatus  into @qqid, @preque,@curq,@recs from tab_pushinfo where imei=aimei;

if @sell='?' THEN set @sell=''; end IF;
if @com='?' THEN set @com=''; end IF;

set @comid=100000;
IF ifnull(@com,'')<>'' then set @comid =@com; END IF;
IF ifnull(@com,'')='' and ifnull(@sell,'')<>''  then set @comid =@sell; END IF;

if ifnull(@preque,'')='' THEN -- ?????
select queid,defaultque(queid) into @qid,@tmpstr from tab_company where com_no=@comid;
IF ifnull(@tmpstr,'')<>ifnull(@curq,'') or @recs<>CONCAT('a',@qid,'&1') THEN 
set @str=@tmpstr; 
update tab_pushinfo set curque=@str,recstatus=CONCAT('a',@qid,'&0') where imei=aimei;
END IF;
END IF;

if ifnull(@preque,'')<>'' AND ( ifnull(@preque,'')<>ifnull(@curq,'') or @recs<>CONCAT(@qqid,'&1') )  THEN -- ??????
set @str=@preque;
update tab_pushinfo set curque=@str,recstatus=CONCAT(@qqid,'&0') where imei=aimei;
END IF;

end if;


SELECT @str;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `RegisterAccepter`
-- ----------------------------
DROP PROCEDURE IF EXISTS `RegisterAccepter`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `RegisterAccepter`(IN aimei VARCHAR(50),IN ambno VARCHAR(15),IN acompany VARCHAR(6),IN aseller VARCHAR(6),IN agender INT,IN abirth VARCHAR(30),IN ambos varchar(4),IN aarea varchar(4),IN aprovider varchar(5),IN ambtype varchar(6),IN aimsi VARCHAR(50),IN aregtime VARCHAR(30),IN aseller_depid VARCHAR(6),IN aseller_stuffid VARCHAR(6))
BEGIN   
set @imi=0;
SELECT count(*) INTO @imi from tab_pushdevice WHERE imei=aimei;
IF @imi<=0 THEN 
INSERT INTO tab_pushdevice(imei,mbno,company,seller,gender,birth,mbos,area,provider,mbtype,imsi,regtime,seller_depid,seller_stuffid) 
VALUES (aimei,ambno,acompany,aseller,agender,abirth,ambos,aarea,aprovider,ambtype,aimsi,aregtime,aseller_depid,aseller_stuffid);
Insert Into tab_pushinfo(imei) values(aimei);
CALL InsertSlideRecord(UUID(),aimei,'FirstRegister',Now());
ELSE
UPDATE tab_pushdevice SET 
mbno=(CASE WHEN IFNULL(ambno,'')<>''  THEN ambno ELSE mbno END),
company=(CASE WHEN IFNULL(acompany,'')<>''  THEN acompany ELSE company END),
seller=(CASE WHEN IFNULL(aseller,'')<>''  THEN aseller ELSE seller END),
gender=(CASE WHEN IFNULL(agender,0)<>0  THEN agender ELSE gender END),
birth=(CASE WHEN IFNULL(abirth,'')<>''  THEN abirth ELSE birth END),
mbos=(CASE WHEN IFNULL(ambos,'')<>''  THEN ambos ELSE mbos END),
area=(CASE WHEN IFNULL(aarea,'')<>''  THEN aarea ELSE area END),
provider=(CASE WHEN IFNULL(aprovider,'')<>''  THEN aprovider ELSE provider END),
mbtype =(CASE WHEN IFNULL(ambtype,'')<>''  THEN ambtype ELSE mbtype END) ,
imsi=(CASE WHEN IFNULL(aimsi,'')<>''  THEN aimsi ELSE imsi END),
seller_depid=(CASE WHEN IFNULL(aseller_depid,'')<>''  THEN aseller_depid ELSE seller_depid END),
seller_stuffid=(CASE WHEN IFNULL(aseller_stuffid,'')<>''  THEN aseller_stuffid ELSE seller_stuffid END)
WHERE imei=aimei;

UPDATE tab_pushdevice SET mbno=NULL where IFNULL(mbno,'')=IFNULL(ambno,'') and IFNULL(imei,'')<>IFNULL(aimei,'');


END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectPushLog`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectPushLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectPushLog`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6),IN varx INT,IN vary INT)
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_pushlog_%' AND pushlogmonth(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select a.hid,a.imei,a.pop,(select title from tab_pushlist where puid=a.puid) as phmsg,a.htime,(select username from tab_user where user_id=a.oper) as oper,b.mbno from (",@t_sql,") a LEFT JOIN tab_pushdevice as b on a.imei=b.imei ",con," LIMIT ",varx,",",vary);
ELSE
-- SET @t_sql=CONCAT("select * from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 'fyy' as imei";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectPushLogTotal`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectPushLogTotal`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectPushLogTotal`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_pushlog_%' AND pushlogmonth(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select count(*) from (",@t_sql,") a LEFT JOIN tab_pushdevice as b on a.imei=b.imei ",con);
ELSE
-- SET @t_sql=CONCAT("select * from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectRcvLogTotal`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectRcvLogTotal`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `SelectRcvLogTotal`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_rcvlog_%' AND month_rcvlog(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select count(*) from (",@t_sql,") a LEFT JOIN tab_deviceinfo as b on a.imei=b.device_imei ",con);
ELSE
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectSlideDayScore`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectSlideDayScore`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectSlideDayScore`(IN phoneno VARCHAR(20),IN dat VARCHAR(20))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_slideinfo_%' );
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
set @imi='none';
select imei into @imi from tab_pushdevice where mbno=phoneno  limit 0,1;
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF IFNULL(@t_sql,'')<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select sum(sort) from (",@t_sql,") x WHERE imei='",@imi,"' and slidetime like '",dat,"%'");
ELSE
-- SET @t_sql=CONCAT("select count(*) from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectSlideInfo`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectSlideInfo`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `SelectSlideInfo`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6),IN varx INT,IN vary INT)
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_slideinfo_%' AND slidemonth(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select * from (",@t_sql,") x ",con," LIMIT ",varx,",",vary);
ELSE
-- SET @t_sql=CONCAT("select * from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 'fyy' as imei";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectSlideTotal`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectSlideTotal`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `SelectSlideTotal`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_slideinfo_%' AND slidemonth(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select count(*) from (",@t_sql,") x ",con);
ELSE
-- SET @t_sql=CONCAT("select count(*) from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectSlideTotalCount`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectSlideTotalCount`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectSlideTotalCount`(IN phoneno VARCHAR(20))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_slideinfo_%' );
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
set @imi='none';
select imei into @imi from tab_pushdetail where mbno=phoneno limit 0,1;
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF IFNULL(@t_sql,'')<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select count(*) from (",@t_sql,") x WHERE imei='",@imi,"'");
ELSE
-- SET @t_sql=CONCAT("select count(*) from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectSlideTotalScore`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectSlideTotalScore`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SelectSlideTotalScore`(IN phoneno VARCHAR(20))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_slideinfo_%' );
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
set @imi='none';
select imei into @imi from tab_pushdevice where mbno=phoneno  limit 0,1;
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF IFNULL(@t_sql,'')<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select sum(sort) from (",@t_sql,") x WHERE imei='",@imi,"'");
ELSE
-- SET @t_sql=CONCAT("select count(*) from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectThemeLog`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectThemeLog`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `SelectThemeLog`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6),IN varx INT,IN vary INT)
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_thmchglog_%' AND month_themelog(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select a.tid,a.imei,a.mbno,a.curtheme,a.exptheme,a.opersrc,(select username from tab_user where user_id=a.operator) as operator,a.opertime from (",@t_sql,") as a ",con," LIMIT ",varx,",",vary);
ELSE
-- SET @t_sql=CONCAT("select * from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 'fyy' as imei";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `SelectThemeLogTotal`
-- ----------------------------
DROP PROCEDURE IF EXISTS `SelectThemeLogTotal`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `SelectThemeLogTotal`(IN con VARCHAR(300),IN startm VARCHAR(6),IN endm VARCHAR(6))
BEGIN
DECLARE tname VARCHAR(50);
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR (SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'mydb02' and TABLE_NAME like 'tab_thmchglog_%' AND month_themelog(TABLE_NAME,startm,endm)=1);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
set @t_sql=''; 
OPEN cur;
aloop: LOOP
FETCH cur INTO tname;
  IF done THEN
    LEAVE aloop;
  END IF;   
  set @t_sql= CONCAT(@t_sql," UNION ALL SELECT * FROM mydb02.",tname);
  END LOOP;
CLOSE cur;
IF @t_sql<>'' THEN
set @t_sql=SUBSTRING(@t_sql,12);
SET @t_sql=CONCAT("select count(*) from (",@t_sql,") as a ",con);
ELSE
-- SET @t_sql=CONCAT("select * from mydb02.tab_slideinfo_",left(now(),4),SUBSTRING(now(),6,2)," where 1=0");
SET @t_sql="select 0";
END IF;
prepare stmt from @t_sql;
EXECUTE stmt;    
deallocate prepare stmt;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateAccepter`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateAccepter`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UpdateAccepter`(IN aimei VARCHAR(50),IN ambno VARCHAR(15),IN acompany VARCHAR(6),IN aseller VARCHAR(6),IN agender INT,IN abirth VARCHAR(30),IN ambos varchar(4),IN aarea varchar(4),IN aprovider varchar(5),IN ambtype varchar(6),IN aimsi VARCHAR(50),IN aregtime VARCHAR(30),IN aseller_depid VARCHAR(6),IN aseller_stuffid VARCHAR(6))
BEGIN   
set @imi=0;
SELECT count(*) INTO @imi from tab_pushdevice WHERE imei=aimei;
IF @imi>0 THEN 
UPDATE tab_pushdevice SET 
-- curque=cmpque(allque),
-- recstatus=CONCAT(cmpqueid(allque),'&1'),
mbno=(CASE WHEN IFNULL(ambno,'')<>''  THEN ambno ELSE mbno END),
company=(CASE WHEN IFNULL(acompany,'')<>''  THEN acompany ELSE company END),
seller=(CASE WHEN IFNULL(aseller,'')<>''  THEN aseller ELSE seller END),
gender=(CASE WHEN IFNULL(agender,0)<>0  THEN agender ELSE gender END),
birth=(CASE WHEN IFNULL(abirth,'')<>''  THEN abirth ELSE birth END),
mbos=(CASE WHEN IFNULL(ambos,'')<>''  THEN ambos ELSE mbos END),
area=(CASE WHEN IFNULL(aarea,'')<>''  THEN aarea ELSE area END),
provider=(CASE WHEN IFNULL(aprovider,'')<>''  THEN aprovider ELSE provider END),
mbtype =(CASE WHEN IFNULL(ambtype,'')<>''  THEN ambtype ELSE mbtype END)  ,
imsi=(CASE WHEN IFNULL(aimsi,'')<>''  THEN aimsi ELSE imsi END),
seller_depid=(CASE WHEN IFNULL(aseller_depid,'')<>''  THEN aseller_depid ELSE seller_depid END),
seller_stuffid=(CASE WHEN IFNULL(aseller_stuffid,'')<>''  THEN aseller_stuffid ELSE seller_stuffid END)
WHERE imei=aimei;
UPDATE tab_pushdevice SET mbno=NULL where IFNULL(mbno,'')=IFNULL(ambno,'') and IFNULL(imei,'')<>IFNULL(aimei,'');
END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateAccepterFmPics`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateAccepterFmPics`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UpdateAccepterFmPics`(IN aimei VARCHAR(50),IN pics VARCHAR(1000))
BEGIN   
UPDATE tab_pushinfo SET 
-- recstatus=(CASE WHEN cmppics(curque,pics)=0  THEN CONCAT(cmpqueid(allque),'&0') ELSE CONCAT(cmpqueid(allque),'&1') END)
recstatus=(CASE WHEN cmppics(curque,pics)=0  THEN CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&0') ELSE CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&1') END)
WHERE imei=aimei;
set @rs='0';
SELECT SUBSTRING_INDEX(recstatus,'&',1),SUBSTRING_INDEX(recstatus,'&',-1) INTO @info,@rs from tab_pushinfo WHERE imei=aimei;
IF @rs='1' THEN 

set @nowdate=now();
SET @type='';
set @puuid='';
SET @queid=null;

if LOCATE('a',@info)=1 THEN SET @type='default';SET @puuid='default';SET @queid=SUBSTR(@info,2);
else 
select queid,puid,(case WHEN priori>0 then 'special' else 'common' END) as pri INTO @queid,@puuid,@type from tab_pushlist where pid=@info;
end IF;
SET @t_sql=CONCAT("CREATE TABLE IF NOT EXISTS mydb02.tab_queuelog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(
  `qlid` char(36) NOT NULL,
  `imei` varchar(50) default NULL,
  `type` char(7) default NULL,
  `puuid` char(36) default NULL,
  `queid` int(11) default NULL,
  `acctime` datetime default NULL,
  PRIMARY KEY  (`qlid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
prepare stmt from @t_sql;
EXECUTE stmt;
SET @t_sql=CONCAT("INSERT INTO mydb02.tab_queuelog_",EXTRACT(YEAR_MONTH FROM @nowdate),"(qlid,imei,type,puuid,queid,acctime) VALUES(UUID(),'",
                  aimei,"','",@type,"','",@puuid,"',",@queid,",'",@nowdate,"')");
prepare stmt from @t_sql;
 EXECUTE stmt; 
deallocate prepare stmt;

END IF;
 select @rs;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateMbOs`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateMbOs`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UpdateMbOs`(IN mbos VARCHAR(30))
BEGIN   
set @enos='';
SELECT id INTO @enos from tab_pushmbos WHERE name=mbos or name=CONCAT('Andriod ',mbos);
IF IFNULL(@enos,'')<>'' THEN  select @enos as i; INSERT INTO tab_pushtest(id,name) VALUES (UUID(),@enos);
ELSE

IF LOCATE('iphone',LOWER(mbos))>0 THEN
select count(*) INTO @i from tab_pushmbos where id like '1%';
set @mid=CONCAT('1',RIGHT(CONCAT('00',@i),3));
set @mos=mbos;
ELSE
select count(*) INTO @i from tab_pushmbos where id like '2%';
set @mid=CONCAT('2',RIGHT(CONCAT('00',@i),3));
set @mos=CONCAT('Andriod ',mbos);
END IF;

INSERT INTO tab_pushmbos(id,name) VALUES (@mid,@mos);
SELECT @mid as i;

END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateMbType`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateMbType`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UpdateMbType`(IN mbtype VARCHAR(30))
BEGIN   
set @en='';
SELECT id INTO @en from tab_pushmbtype WHERE name=mbtype;
IF IFNULL(@en,'')<>'' THEN  select @en;
ELSE

select count(*) INTO @i from tab_pushmbtype where id like '99%';
set @tid=CONCAT('99',RIGHT(CONCAT('000',@i),4));
set @tpe=mbtype;

INSERT INTO tab_pushmbtype(id,name) VALUES (@tid,@tpe);
SELECT @tid;

END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdatePushAtom`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdatePushAtom`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdatePushAtom`(IN aaid INT,IN acid INT,IN ashowtime FLOAT,IN aenddate VARCHAR(30))
BEGIN   
    
DECLARE i INT; 
DECLARE j INT;
DECLARE done INT DEFAULT FALSE;
DECLARE cur CURSOR FOR SELECT aid FROM tab_pushatom;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
SET i = 0; 
OPEN cur;
aloop: LOOP
FETCH cur INTO j;
  IF done THEN
    LEAVE aloop;
  END IF;   
  IF i<>j THEN
    LEAVE aloop;
  END IF;  
  set i=i+1;
  END LOOP;
CLOSE cur;

set @g_aaid=aaid; 
set @g_acid=acid; 
set @g_ashowtime=ashowtime; 
set @g_aenddate=aenddate; 

IF aaid<>-1 THEN UPDATE tab_pushatom set cid=@g_acid,showtime=@g_ashowtime,enddate=@g_aenddate where aid=@g_aaid; set i=@g_aaid;
ELSE INSERT INTO tab_pushatom(aid,cid,showtime,enddate) VALUES (i,acid,ashowtime,aenddate);
End IF;
SELECT i;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdatePushContent`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdatePushContent`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdatePushContent`(IN acid INT,IN atitle VARCHAR(50),IN asort INT,IN aoperator VARCHAR(100),IN anamex VARCHAR(100),IN asize INT,IN aoperdate  varchar(30),IN alink varchar(200),IN aiconstr varchar(200),IN color varchar(6))
BEGIN   

UPDATE tab_pushcontent SET title=atitle,sort=asort,operator=aoperator,namex=anamex,size=asize,operdate=aoperdate,link=alink,icon=aiconstr,scolor=color WHERE cid=acid;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdatePushQueue`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdatePushQueue`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UpdatePushQueue`(IN aqid INT, IN atitle VARCHAR(100),IN newque VARCHAR(256))
BEGIN  

DECLARE oldque VARCHAR(256);
select que INTO oldque from tab_pushque where qid=aqid;
set @sqlx=CONCAT("DELETE from tab_pushatom where aid in (",left(oldque,CHAR_LENGTH(oldque)-1),") and aid not in(",left(newque,CHAR_LENGTH(newque)-1),")");
prepare stmt from @sqlx;
EXECUTE stmt;    
set @sqly=CONCAT("Update tab_pushque set title='",atitle,"' ,que='",newque,"' where qid=",aqid);
prepare stmt from @sqly;
EXECUTE stmt;  
deallocate prepare stmt; 


END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateRecFmCt`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateRecFmCt`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateRecFmCt`(in acid INT)
BEGIN  
 set @x=null;
 set @y=null;
 select group_concat(CONVERT(aid,CHAR)) into @x from tab_pushatom where cid=acid ;
 SELECT group_concat(CONVERT(pid,CHAR)) into @y from tab_pushlist where queid in (select qid  from tab_pushque where comparestr(que,@x)=1);
 update tab_pushinfo set recstatus=CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&0')  where IFNULL(FIND_IN_SET(SUBSTRING_INDEX(recstatus,'&',1),@y),0)>0;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateRecFmPh`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateRecFmPh`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `UpdateRecFmPh`(in apid INT)
BEGIN  
update tab_pushinfo set recstatus=
(
CASE WHEN cmpque(allque)=curque THEN CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&1')  -- ???
ELSE CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&0') END
) -- ???
where SUBSTRING_INDEX(recstatus,'&',1)=apid;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `UpdateRecFmQue`
-- ----------------------------
DROP PROCEDURE IF EXISTS `UpdateRecFmQue`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateRecFmQue`(in aqid INT)
BEGIN  
update tab_pushinfo set recstatus=
(
CASE WHEN cmpque(allque)=curque THEN CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&1')  -- ???
ELSE CONCAT(SUBSTRING_INDEX(recstatus,'&',1),'&0') END
) -- ???
where SUBSTRING_INDEX(recstatus,'&',1) in (SELECT pid from tab_pushlist where queid=aqid);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `cmppics`
-- ----------------------------
DROP FUNCTION IF EXISTS `cmppics`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `cmppics`(expectpic varchar(2000),factpic varchar(2000)) RETURNS bit(1)
BEGIN -- ???????????????
DECLARE msg VARCHAR(500);
set @fact=IFNULL(factpic,'');
set @exep=IFNULL(expectpic,'');
WHILE LOCATE(',',@fact)>0 DO 
if LOCATE(SUBSTRING_INDEX(@fact,',',1),@exep)>0 then 
SET @exep=REPLACE(@exep,SUBSTRING_INDEX(@fact,',',1),'');
else return 0; end if;
SET @fact=SUBSTRING(@fact,LOCATE(',',@fact)+1); 
END WHILE;

IF @exep REGEXP 'P_[0-9]*_[0-9]*_[0-9]*.' THEN return 0; 
ELSE return 1;
END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `cmpque`
-- ----------------------------
DROP FUNCTION IF EXISTS `cmpque`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `cmpque`(questr varchar(1000)) RETURNS varchar(800) CHARSET utf8
BEGIN
DECLARE ind INT;

set ind=0;
set @result='';
set @pri='';
set @q='';
set @y='';
set @z='';
set @x='';
set @c='';
set @d='';
set @e='';
set @k='';
set @m='';
set @rgb='';

IF IFNULL(questr,'')<>''  and cmpqueid(questr)<>'' THEN 
set @qstr=questr;
select pid,priori,queid,pushtime,enddate into @ppid,@pri,@q,@y,@z from tab_pushlist where pid=cmpqueid(@qstr);
ELSE return @result; END IF;

IF IFNULL(@q,'')<>'' THEN 
if @pri=0 THEN set @result=CONCAT('b_',@ppid,'*',@y,'*',@z);   END IF;
if @pri>0 THEN set @result=CONCAT('c_',@ppid,'*',@y,'*',@z);  END IF;

select que into @x from tab_pushque where qid =@q;
WHILE IFNULL(@x,'')<>'' DO 
	 set ind=LOCATE(',',@x);
	 set @p=left(@x,ind-1);
	 select cid,showtime,enddate into @m,@n,@k  from tab_pushatom where aid=@p;
	 select namex,link,sort,scolor into @c,@d,@e,@rgb from tab_pushcontent where cid=@m;
	 if @k>NOW() THEN set @result=CONCAT(@result,'*',@c,',',@n,',',@k,',',@d,',',@e,',',@rgb); END IF;
	 SET @x=SUBSTRING(@x,ind+1);
END WHILE;
END IF;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `cmpqueid`
-- ----------------------------
DROP FUNCTION IF EXISTS `cmpqueid`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `cmpqueid`(questr varchar(1000)) RETURNS varchar(20) CHARSET utf8
BEGIN

IF IFNULL(questr,'')<>'' THEN 

set @i=-2;
set @msg=CONCAT(',',questr);
set @id=IFNULL(SUBSTRING_INDEX(SUBSTRING_INDEX(@msg,',',@i),',',1),'');
set @pri='';
WHILE @id<>'' DO
  select priori into @pri from tab_pushlist where pid=@id and flag<>0 and enddate>NOW() and pushtime<enddate; 
  IF IfNULL(@pri,'')<>'' THEN return @id; END IF;
	set @i=@i-1;
	set @id=IFNULL(SUBSTRING_INDEX(SUBSTRING_INDEX(@msg,',',@i),',',1),'');
END WHILE;

END IF;

return '';

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `comparestr`
-- ----------------------------
DROP FUNCTION IF EXISTS `comparestr`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `comparestr`(questr varchar(200),aidstr varchar(200)) RETURNS bit(1)
BEGIN -- ???????????????
DECLARE msg VARCHAR(200);
set msg=questr;
WHILE LOCATE(',',IFNULL(msg,''))>0 DO
IF FIND_IN_SET(SUBSTRING_INDEX(msg,',',1),IFNULL(aidstr,''))>0 THEN return 1;
ELSE SET msg=SUBSTRING(msg,LOCATE(',',msg)+1);
END IF;
END WHILE;
return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `defaultque`
-- ----------------------------
DROP FUNCTION IF EXISTS `defaultque`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `defaultque`(qqid int) RETURNS varchar(800) CHARSET utf8
BEGIN
DECLARE ind INT;
set ind=0;
set @result='';
set @q=qqid;
set @x='';
set @p='';
set @c='';
set @d='';
set @e='';
set @k='';
set @m='';
set @n='';
set @rgb='';


IF IFNULL(@q,'')<>'' THEN 
set @result=CONCAT('0','*x*x'); 
select que into @x from tab_pushque where qid =@q;
WHILE IFNULL(@x,'')<>'' DO 
	 set ind=LOCATE(',',@x);
	 set @p=left(@x,ind-1);
	 select cid,showtime,enddate into @m,@n,@k  from tab_pushatom where aid=@p;
	 select namex,link,sort,scolor into @c,@d,@e,@rgb from tab_pushcontent where cid=@m;
	 if @k>NOW() THEN set @result=CONCAT(@result,'*',@c,',',@n,',',@k,',',@d,',',@e,',',@rgb); END IF;
	 SET @x=SUBSTRING(@x,ind+1);
END WHILE;
END IF;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `DeMb`
-- ----------------------------
DROP FUNCTION IF EXISTS `DeMb`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `DeMb`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="";
select id into @x from tab_pushprovider where name=enstr;
return @x;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `EnCompany`
-- ----------------------------
DROP FUNCTION IF EXISTS `EnCompany`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `EnCompany`(enstr varchar(6)) RETURNS varchar(100) CHARSET utf8
BEGIN
set @result = '?';
select com_name into @result from tab_company where com_no=enstr;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `EnCurque`
-- ----------------------------
DROP FUNCTION IF EXISTS `EnCurque`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `EnCurque`(enstr varchar(6)) RETURNS varchar(100) CHARSET utf8
BEGIN
set @result = '?';
if IFNULL(enstr,'')<>'' THEN
if left(enstr,1)='a' THEN select CONCAT('??',title) into @result from tab_pushque where qid=SUBSTRING(substring_index(enstr,'&',1),2);
else select CONCAT('??',title) into @result from tab_pushlist where pid=substring_index(enstr,'&',1);
END IF;
END IF;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `EnMbArea`
-- ----------------------------
DROP FUNCTION IF EXISTS `EnMbArea`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `EnMbArea`(enstr varchar(6)) RETURNS varchar(100) CHARSET utf8
BEGIN
set @province = 'none';
set @city = '?????';
select name into @province from tab_pusharea where id=left(enstr,2);
select name into @city from tab_pusharea where id=enstr;
IF @province <> 'none' and @city <> '?????' THEN RETURN CONCAT(@province,' ',@city);
ELSE RETURN @city; END IF;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `EnMbOs`
-- ----------------------------
DROP FUNCTION IF EXISTS `EnMbOs`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `EnMbOs`(enstr varchar(6)) RETURNS varchar(100) CHARSET utf8
BEGIN
set @xos = '????';
select name into @xos from tab_pushmbos where id=enstr;
return @xos;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `EnMbProvider`
-- ----------------------------
DROP FUNCTION IF EXISTS `EnMbProvider`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `EnMbProvider`(enstr varchar(30)) RETURNS varchar(100) CHARSET utf8
BEGIN
set @provi = '?';
select name into @provi from tab_pushprovider where id=enstr;
return @provi;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `EnMbType`
-- ----------------------------
DROP FUNCTION IF EXISTS `EnMbType`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `EnMbType`(enstr varchar(6)) RETURNS varchar(100) CHARSET utf8
BEGIN
set @xbrand = '????';
set @xtype= '????';
select name into @xtype from tab_pushmbtype where id=enstr;
select name into @xbrand from tab_pushmbtype  where id=LEFT(enstr,2);
return CONCAT(@xbrand,' ',@xtype);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `func_iconname`
-- ----------------------------
DROP FUNCTION IF EXISTS `func_iconname`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `func_iconname`(iconstr varchar(200)) RETURNS varchar(1000) CHARSET utf8
BEGIN
set @result='';
if  iconstr REGEXP '^([a-zA-Z0-9]+,)+$'  THEN 
select GROUP_CONCAT(icontable.str) into @result from 
(
select CONCAT('"',icon_name,'"') as str,instr(CONCAT(',',iconstr),CONCAT(',',icon_id, ',')) as sort
from tab_iconinfo 
where LOCATE(CONCAT(',',icon_id,','),CONCAT(',',iconstr))>0 
order by sort
) as icontable;
END IF;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `func_iconstr`
-- ----------------------------
DROP FUNCTION IF EXISTS `func_iconstr`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `func_iconstr`(iconstr varchar(200)) RETURNS varchar(1000) CHARSET utf8
BEGIN
set @result='';
if  iconstr REGEXP '^([a-zA-Z0-9]+,)+$'  THEN 
select GROUP_CONCAT(icontable.str) into @result from 
(
select CONCAT(icon_name,'*',icon_url,'*',icon_id) as str,instr(CONCAT(',',iconstr),CONCAT(',',icon_id, ',')) as sort
from tab_iconinfo 
where LOCATE(CONCAT(',',icon_id,','),CONCAT(',',iconstr))>0 
order by sort
) as icontable;
END IF;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `func_iconurl`
-- ----------------------------
DROP FUNCTION IF EXISTS `func_iconurl`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `func_iconurl`(iconstr varchar(200)) RETURNS varchar(1000) CHARSET utf8
BEGIN
set @result='';
if  iconstr REGEXP '^([a-zA-Z0-9]+,)+$'  THEN 
select GROUP_CONCAT(icontable.str) into @result from 
(
select CONCAT('"http://',icon_url,'"') as str,instr(CONCAT(',',iconstr),CONCAT(',',icon_id, ',')) as sort
from tab_iconinfo 
where LOCATE(CONCAT(',',icon_id,','),CONCAT(',',iconstr))>0 
order by sort
) as icontable;
END IF;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `func_taskpri`
-- ----------------------------
DROP FUNCTION IF EXISTS `func_taskpri`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `func_taskpri`(task varchar(100)) RETURNS int(11)
BEGIN  -- 5???????
set @result=-1;
select tid into @result from tab_pushthemetask where timediff(now(),ttime)<=5 and  now()<ttime and LOCATE(CONCAT(',',tid,','),CONCAT(',',IFNULL(task,'')))>0 ORDER BY LOCATE(CONCAT(',',tid,','),REVERSE(CONCAT(',',IFNULL(task,''))))  limit 0,1 ;
return @result;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `month_pushlog`
-- ----------------------------
DROP FUNCTION IF EXISTS `month_pushlog`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `month_pushlog`(tbname VARCHAR(30),startm VARCHAR(6),endm VARCHAR(6)) RETURNS bit(1)
BEGIN
IF IFNULL(tbname,'') REGEXP 'tab_pushlog_[0-9]{6}' and IFNULL(startm,'') REGEXP '[0-9]{6}' and IFNULL(endm,'') REGEXP '[0-9]{6}' THEN
SET @m= CAST(SUBSTRING(tbname,13) as UNSIGNED);
SET @s=CAST(startm AS UNSIGNED);
SET @e=CAST(endm AS UNSIGNED);
IF  @m<=@e AND @m>=@s THEN return 1; END IF;
END IF;
return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `month_rcvlog`
-- ----------------------------
DROP FUNCTION IF EXISTS `month_rcvlog`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `month_rcvlog`(tbname VARCHAR(30),startm VARCHAR(6),endm VARCHAR(6)) RETURNS bit(1)
BEGIN
IF IFNULL(tbname,'') REGEXP 'tab_rcvlog_[0-9]{6}' and IFNULL(startm,'') REGEXP '[0-9]{6}' and IFNULL(endm,'') REGEXP '[0-9]{6}' THEN
SET @m= CAST(SUBSTRING(tbname,12) as UNSIGNED);
SET @s=CAST(startm AS UNSIGNED);
SET @e=CAST(endm AS UNSIGNED);
IF  @m<=@e AND @m>=@s THEN return 1; END IF;
END IF;
return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `month_themelog`
-- ----------------------------
DROP FUNCTION IF EXISTS `month_themelog`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `month_themelog`(tbname VARCHAR(30),startm VARCHAR(6),endm VARCHAR(6)) RETURNS bit(1)
BEGIN
IF IFNULL(tbname,'') REGEXP 'tab_thmchglog_[0-9]{6}' and IFNULL(startm,'') REGEXP '[0-9]{6}' and IFNULL(endm,'') REGEXP '[0-9]{6}' THEN
SET @m= CAST(SUBSTRING(tbname,15) as UNSIGNED);
SET @s=CAST(startm AS UNSIGNED);
SET @e=CAST(endm AS UNSIGNED);
IF  @m<=@e AND @m>=@s THEN return 1; END IF;
END IF;
return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `pushlogmonth`
-- ----------------------------
DROP FUNCTION IF EXISTS `pushlogmonth`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `pushlogmonth`(tbname VARCHAR(30),startm VARCHAR(6),endm VARCHAR(6)) RETURNS bit(1)
BEGIN
IF IFNULL(tbname,'') REGEXP 'tab_pushlog_[0-9]{6}' and IFNULL(startm,'') REGEXP '[0-9]{6}' and IFNULL(endm,'') REGEXP '[0-9]{6}' THEN
SET @m= CAST(SUBSTRING(tbname,13) as UNSIGNED);
SET @s=CAST(startm AS UNSIGNED);
SET @e=CAST(endm AS UNSIGNED);
IF  @m<=@e AND @m>=@s THEN return 1; END IF;
END IF;
return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `slidemonth`
-- ----------------------------
DROP FUNCTION IF EXISTS `slidemonth`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `slidemonth`(tbname VARCHAR(30),startm VARCHAR(6),endm VARCHAR(6)) RETURNS bit(1)
BEGIN
IF IFNULL(tbname,'') REGEXP 'tab_slideinfo_[0-9]{6}' and IFNULL(startm,'') REGEXP '[0-9]{6}' and IFNULL(endm,'') REGEXP '[0-9]{6}' THEN
SET @m= CAST(SUBSTRING(tbname,15) as UNSIGNED);
SET @s=CAST(startm AS UNSIGNED);
SET @e=CAST(endm AS UNSIGNED);
IF  @m<=@e AND @m>=@s THEN return 1; END IF;
END IF;
return 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `Z_DeMb_area`
-- ----------------------------
DROP FUNCTION IF EXISTS `Z_DeMb_area`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `Z_DeMb_area`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="";
select id into @x from tab_pusharea where LOCATE(name,enstr)>0 and CHAR_LENGTH(id)=4;
return @x;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `Z_DeMb_birth`
-- ----------------------------
DROP FUNCTION IF EXISTS `Z_DeMb_birth`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `Z_DeMb_birth`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="-9-1";
return CONCAT(enstr,@x);
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `Z_DeMb_gender`
-- ----------------------------
DROP FUNCTION IF EXISTS `Z_DeMb_gender`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `Z_DeMb_gender`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="";
IF enstr='?' THEN SET @x='1'; END IF;
IF enstr='?' THEN SET @x='2'; END IF;
return @x;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `Z_DeMb_mbos`
-- ----------------------------
DROP FUNCTION IF EXISTS `Z_DeMb_mbos`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `Z_DeMb_mbos`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="";
select id into @x from tab_pushmbos where REPLACE(name,'Andriod ','')=enstr ;
return @x;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `Z_DeMb_mbtype`
-- ----------------------------
DROP FUNCTION IF EXISTS `Z_DeMb_mbtype`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `Z_DeMb_mbtype`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="";
select id into @x from tab_pushmbtype where name=enstr;
return @x;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `Z_DeMb_provider`
-- ----------------------------
DROP FUNCTION IF EXISTS `Z_DeMb_provider`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `Z_DeMb_provider`(enstr varchar(50)) RETURNS varchar(10) CHARSET utf8
BEGIN
set @x="";
select id into @x from tab_pushprovider where name=enstr;
return @x;
END
;;
DELIMITER ;

-- ----------------------------
-- Auto increment value for `tab_apkinfo`
-- ----------------------------
ALTER TABLE `tab_apkinfo` AUTO_INCREMENT=51;

-- ----------------------------
-- Auto increment value for `tab_apkuser`
-- ----------------------------
ALTER TABLE `tab_apkuser` AUTO_INCREMENT=151;

-- ----------------------------
-- Auto increment value for `tab_appstore`
-- ----------------------------
ALTER TABLE `tab_appstore` AUTO_INCREMENT=6;

-- ----------------------------
-- Auto increment value for `tab_area_region`
-- ----------------------------
ALTER TABLE `tab_area_region` AUTO_INCREMENT=9;

-- ----------------------------
-- Auto increment value for `tab_autopushlog`
-- ----------------------------
ALTER TABLE `tab_autopushlog` AUTO_INCREMENT=124;

-- ----------------------------
-- Auto increment value for `tab_company`
-- ----------------------------
ALTER TABLE `tab_company` AUTO_INCREMENT=100003;

-- ----------------------------
-- Auto increment value for `tab_department`
-- ----------------------------
ALTER TABLE `tab_department` AUTO_INCREMENT=100245;

-- ----------------------------
-- Auto increment value for `tab_deviceinfo`
-- ----------------------------
ALTER TABLE `tab_deviceinfo` AUTO_INCREMENT=525;

-- ----------------------------
-- Auto increment value for `tab_employee`
-- ----------------------------
ALTER TABLE `tab_employee` AUTO_INCREMENT=101246;

-- ----------------------------
-- Auto increment value for `tab_iconinfo`
-- ----------------------------
ALTER TABLE `tab_iconinfo` AUTO_INCREMENT=29;

-- ----------------------------
-- Auto increment value for `tab_mobilepublickey`
-- ----------------------------
ALTER TABLE `tab_mobilepublickey` AUTO_INCREMENT=10;

-- ----------------------------
-- Auto increment value for `tab_org`
-- ----------------------------
ALTER TABLE `tab_org` AUTO_INCREMENT=100252;

-- ----------------------------
-- Auto increment value for `tab_pic`
-- ----------------------------
ALTER TABLE `tab_pic` AUTO_INCREMENT=29;

-- ----------------------------
-- Auto increment value for `tab_privilege`
-- ----------------------------
ALTER TABLE `tab_privilege` AUTO_INCREMENT=8;

-- ----------------------------
-- Auto increment value for `tab_pusherror`
-- ----------------------------
ALTER TABLE `tab_pusherror` AUTO_INCREMENT=74;

-- ----------------------------
-- Auto increment value for `tab_pusherrorlog`
-- ----------------------------
ALTER TABLE `tab_pusherrorlog` AUTO_INCREMENT=63034;
DROP TRIGGER IF EXISTS `tri_allque`;
DELIMITER ;;
CREATE TRIGGER `tri_allque` BEFORE UPDATE ON `tab_pushinfo` FOR EACH ROW BEGIN     
IF Length(OLD.allque)>=485 THEN     
SET NEW.allque=''; 
END IF;     
END
;;
DELIMITER ;

-- ----------------------------
-- Auto increment value for `tab_pushrecpics`
-- ----------------------------
ALTER TABLE `tab_pushrecpics` AUTO_INCREMENT=5606;

-- ----------------------------
-- Auto increment value for `tab_role`
-- ----------------------------
ALTER TABLE `tab_role` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `tab_serverkeys`
-- ----------------------------
ALTER TABLE `tab_serverkeys` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for `tab_sharekey`
-- ----------------------------
ALTER TABLE `tab_sharekey` AUTO_INCREMENT=16;

-- ----------------------------
-- Auto increment value for `tab_slidetabs_info`
-- ----------------------------
ALTER TABLE `tab_slidetabs_info` AUTO_INCREMENT=20;

-- ----------------------------
-- Auto increment value for `tab_tabinfo`
-- ----------------------------
ALTER TABLE `tab_tabinfo` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `tab_time`
-- ----------------------------
ALTER TABLE `tab_time` AUTO_INCREMENT=2;

-- ----------------------------
-- Auto increment value for `tab_user`
-- ----------------------------
ALTER TABLE `tab_user` AUTO_INCREMENT=14;

-- ----------------------------
-- Auto increment value for `tab_wechat`
-- ----------------------------
ALTER TABLE `tab_wechat` AUTO_INCREMENT=80;

-- ----------------------------
-- Auto increment value for `tab_wxcodeinfo`
-- ----------------------------
ALTER TABLE `tab_wxcodeinfo` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `tab_wxcodetrade`
-- ----------------------------
ALTER TABLE `tab_wxcodetrade` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `tab_wxcounts`
-- ----------------------------
ALTER TABLE `tab_wxcounts` AUTO_INCREMENT=236;

-- ----------------------------
-- Auto increment value for `tab_wxproduct`
-- ----------------------------
ALTER TABLE `tab_wxproduct` AUTO_INCREMENT=1004;

-- ----------------------------
-- Auto increment value for `tab_wxscoreintrade`
-- ----------------------------
ALTER TABLE `tab_wxscoreintrade` AUTO_INCREMENT=1915;

-- ----------------------------
-- Auto increment value for `tab_wxscoretrade`
-- ----------------------------
ALTER TABLE `tab_wxscoretrade` AUTO_INCREMENT=2005;

-- ----------------------------
-- Auto increment value for `tab_wxthemelog`
-- ----------------------------
ALTER TABLE `tab_wxthemelog` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `tab_wxtitle`
-- ----------------------------
ALTER TABLE `tab_wxtitle` AUTO_INCREMENT=7;

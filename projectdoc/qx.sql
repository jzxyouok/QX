/*
Navicat MySQL Data Transfer

Source Server         : HPLUS
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : qx

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-07-22 00:12:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `current_value` int(11) NOT NULL,
  `increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('hSeq', '17', '1');

-- ----------------------------
-- Table structure for `t_qx_directory`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_directory`;
CREATE TABLE `t_qx_directory` (
  `USER_ID` bigint(20) NOT NULL,
  `DIRECTORY_ID` bigint(20) NOT NULL,
  `TYPE` smallint(6) NOT NULL COMMENT '0:USER 1:GROUP',
  PRIMARY KEY (`USER_ID`,`DIRECTORY_ID`,`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_directory
-- ----------------------------

-- ----------------------------
-- Table structure for `t_qx_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_group`;
CREATE TABLE `t_qx_group` (
  `ID` bigint(20) NOT NULL,
  `GROUPNAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `GROUPBZ` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_group
-- ----------------------------

-- ----------------------------
-- Table structure for `t_qx_groupuser`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_groupuser`;
CREATE TABLE `t_qx_groupuser` (
  `GROUP_ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `REJECT_FLAG` smallint(6) NOT NULL COMMENT '免打扰',
  `TOP_FLAG` smallint(6) NOT NULL COMMENT '置顶',
  PRIMARY KEY (`GROUP_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_groupuser
-- ----------------------------

-- ----------------------------
-- Table structure for `t_qx_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_message`;
CREATE TABLE `t_qx_message` (
  `ID` bigint(20) NOT NULL,
  `SEND_USER_ID` bigint(20) NOT NULL,
  `SEND_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `RECEIVE_USER_ID` bigint(20) NOT NULL,
  `RECEIVE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `ISGROUP` smallint(6) NOT NULL COMMENT '0:否，1:是',
  `GROUP_ID` bigint(20) DEFAULT NULL,
  `MESSAGE` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGETYPE` smallint(6) NOT NULL COMMENT '0:文字/表情  1:图片  2:文件',
  `SENDTIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发送时间',
  `ISSEND` smallint(6) DEFAULT NULL COMMENT '0:否 1:是',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_message
-- ----------------------------

-- ----------------------------
-- Table structure for `t_qx_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_user`;
CREATE TABLE `t_qx_user` (
  `ID` bigint(20) NOT NULL,
  `USERNAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `NAME` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `SEX` smallint(6) NOT NULL COMMENT '性别',
  `BZ` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `AREA_ID` bigint(20) DEFAULT NULL COMMENT '地区ID',
  `PHOTO_SMALL` mediumblob COMMENT '小头像',
  `PHOTO_BIG` longblob COMMENT '大头像',
  `PHOTO_FILENAME` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_user
-- ----------------------------
INSERT INTO `t_qx_user` VALUES ('1', 'xusi', '111', '徐思', '0', '', null, null, null, null);
INSERT INTO `t_qx_user` VALUES ('2', 'test1', '111', '测试1', '0', '20楼', null, null, null, null);
INSERT INTO `t_qx_user` VALUES ('3', 'test2', '111', '测试2', '1', '', null, null, null, null);

-- ----------------------------
-- Table structure for `t_qx_userblack`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_userblack`;
CREATE TABLE `t_qx_userblack` (
  `USER_ID` bigint(20) NOT NULL,
  `BLACK_USER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`BLACK_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_userblack
-- ----------------------------

-- ----------------------------
-- Table structure for `t_qx_userrecent`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_userrecent`;
CREATE TABLE `t_qx_userrecent` (
  `USER_ID` bigint(20) NOT NULL,
  `RECENT_ID` bigint(20) NOT NULL,
  `TYPE` smallint(6) NOT NULL COMMENT '0:USER 1:GROUP',
  `TOP_FLAG` smallint(6) NOT NULL,
  PRIMARY KEY (`USER_ID`,`RECENT_ID`,`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_userrecent
-- ----------------------------
INSERT INTO `t_qx_userrecent` VALUES ('1', '2', '0', '0');
INSERT INTO `t_qx_userrecent` VALUES ('1', '3', '0', '0');

-- ----------------------------
-- Table structure for `t_qx_userwhite`
-- ----------------------------
DROP TABLE IF EXISTS `t_qx_userwhite`;
CREATE TABLE `t_qx_userwhite` (
  `USER_ID` bigint(20) NOT NULL,
  `WHITE_USER_ID` bigint(20) NOT NULL COMMENT '白名单人员ID',
  `REJECT_FLAG` smallint(6) NOT NULL COMMENT '0:不屏蔽 1:屏蔽',
  PRIMARY KEY (`USER_ID`,`WHITE_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_qx_userwhite
-- ----------------------------
INSERT INTO `t_qx_userwhite` VALUES ('1', '2', '0');
INSERT INTO `t_qx_userwhite` VALUES ('1', '3', '1');

-- ----------------------------
-- Function structure for `currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_name VARCHAR(50)) RETURNS bigint(11)
BEGIN
	#Routine body goes here...
	DECLARE value BIGINT;
	SET value = 0;
	SELECT current_value INTO value FROM sequence
	WHERE name = seq_name;
	RETURN value;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `getPY`
-- ----------------------------
DROP FUNCTION IF EXISTS `getPY`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getPY`(in_string varchar(1000)) RETURNS varchar(1000) CHARSET utf8
BEGIN
DECLARE tmp_str VARCHAR(1000) charset gbk DEFAULT '' ; #截取字符串，每次做截取后的字符串存放在该变量中，初始为函数参数in_string值 
DECLARE tmp_len SMALLINT DEFAULT 0;#tmp_str的长度 
DECLARE tmp_char VARCHAR(2) charset gbk DEFAULT '';#截取字符，每次 left(tmp_str,1) 返回值存放在该变量中 
DECLARE tmp_rs VARCHAR(1000) charset gbk DEFAULT '';#结果字符串 
DECLARE tmp_cc VARCHAR(2) charset gbk DEFAULT '';#拼音字符，存放单个汉字对应的拼音首字符 
SET tmp_str = in_string;#初始化，将in_string赋给tmp_str 
SET tmp_len = LENGTH(tmp_str);#初始化长度 
WHILE tmp_len > 0 DO #如果被计算的tmp_str长度大于0则进入该while 
SET tmp_char = LEFT(tmp_str,1);#获取tmp_str最左端的首个字符，注意这里是获取首个字符，该字符可能是汉字，也可能不是。 
SET tmp_cc = tmp_char;#左端首个字符赋值给拼音字符 
IF LENGTH(tmp_char)>1 THEN#判断左端首个字符是多字节还是单字节字符，要是多字节则认为是汉字且作以下拼音获取，要是单字节则不处理。 
SELECT ELT(INTERVAL(CONV(HEX(tmp_char),16,10),0xB0A1,0xB0C5,0xB2C1,0xB4EE,0xB6EA,0xB7A2,0xB8C1,0xB9FE,0xBBF7,0xBFA6,0xC0AC
,0xC2E8,0xC4C3,0xC5B6,0xC5BE,0xC6DA,0xC8BB,0xC8F6,0xCBFA,0xCDDA ,0xCEF4,0xD1B9,0xD4D1), 
'A','B','C','D','E','F','G','H','J','K','L','M','N','O','P','Q','R','S','T','W','X','Y','Z') INTO tmp_cc; #获得汉字拼音首字符 
END IF; 
SET tmp_rs = CONCAT(tmp_rs,tmp_cc);#将当前tmp_str左端首个字符拼音首字符与返回字符串拼接 
SET tmp_str = SUBSTRING(tmp_str,2);#将tmp_str左端首字符去除 
SET tmp_len = LENGTH(tmp_str);#计算当前字符串长度 
END WHILE; 
RETURN tmp_rs;#返回结果字符串 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS bigint(11)
BEGIN
	#Routine body goes here...
	UPDATE sequence
	SET current_value = current_value + increment
 WHERE name = seq_name;
	RETURN currval(seq_name);
END
;;
DELIMITER ;

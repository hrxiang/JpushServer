CREATE DATABASE IF NOT EXISTS `jpush` DEFAULT CHARACTER SET utf8;

USE `jpush`;

/*Table structure for table `jpush_logger` */

DROP TABLE IF EXISTS `jpush_logger`;

CREATE TABLE `jpush_logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `alert` varchar(200) DEFAULT NULL COMMENT '推送的内容',
  `logger_type` varchar(2) DEFAULT '0' COMMENT '类型(0-通知，1-自定义消息)',
  `recipient_type` varchar(2) DEFAULT '0' COMMENT '接收对象(0-广播，1-标签，2-别名，3-Reg.ID..)',
  `target_android` boolean  COMMENT '目标',
  `target_ios` boolean  COMMENT '目标',
  `delivery_type` varchar(2) DEFAULT NULL COMMENT '推送类型(0-立即，1-定时，2-时间段)',
  `delivery_time` datetime DEFAULT NULL COMMENT '推送的时间',
  `time_to_live` varchar(20) DEFAULT NULL COMMENT '离线消息保留时间',
  `status` varchar(2) DEFAULT null COMMENT '是否成功(0-成功,1-失败)',
  `reason` varchar(200) DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`)
);

/*标签表，同一个终端只保存一套有效标签*/
DROP TABLE IF EXISTS `jpush_valid_tags`;


CREATE TABLE `jpush_valid_tags` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`tag_` varchar(200) DEFAULT NULL COMMENT '标签',
	`registration_id`varchar(200) DEFAULT  NULL COMMENT '终端 唯一标示',
  	PRIMARY KEY (`id`)
);

/*
DROP TABLE IF EXISTS `jpush_tags`;
CREATE TABLE `jpush_tags` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`tag_` varchar(200) DEFAULT NULL UNIQUE COMMENT '标签',
	`used_quantity` int(11) DEFAULT  0 COMMENT '用户量',
	`description` varchar(100) DEFAULT  NULL COMMENT '描述',
	`create_time` varchar(40) DEFAULT  NULL COMMENT '创建时间',
  	PRIMARY KEY (`id`)
);
   
*/
/*别名表，同一终端只保存一个有效别名*/
/*
DROP TABLE IF EXISTS `jpush_alias`;
CREATE TABLE `jpush_alias` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`alias_` varchar(200) DEFAULT NULL UNIQUE COMMENT '别名(不可重复)',
	`registration_id` varchar(200) DEFAULT NULL COMMENT '终端 唯一标示',
  	PRIMARY KEY (`id`)
);
*/

/*定时任务*/
DROP TABLE IF EXISTS `jpush_timer_logger`;
CREATE TABLE `jpush_timer_logger` (
	`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`timer_time` varchar(40) DEFAULT NULL COMMENT '预定时间',
	`alert` varchar(200) DEFAULT NULL COMMENT '发送内容',
	`logger_type` varchar(2) DEFAULT '0' COMMENT '类型(0-通知，1-自定义消息)',
	`recipient_type` varchar(2) DEFAULT '0' COMMENT '接收对象(0-广播，1-标签，2-别名，3-Reg.ID..)',
	`target_android` boolean  COMMENT '目标',
  	`target_ios` boolean  COMMENT '目标',
  	`device_tags` text  COMMENT '标签',
  	`device_aliases` text  COMMENT '别名',
  	`device_registrationids` text COMMENT '注册号',
  	PRIMARY KEY (`id`)
);

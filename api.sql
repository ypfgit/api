/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 60011
Source Host           : localhost:3306
Source Database       : api

Target Server Type    : MYSQL
Target Server Version : 60011
File Encoding         : 65001

Date: 2017-03-01 10:32:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ts_column
-- ----------------------------
DROP TABLE IF EXISTS `ts_column`;
CREATE TABLE `ts_column` (
  `id` varchar(100) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '栏目名称',
  `parent_id` varchar(100) DEFAULT NULL COMMENT '父栏目ID',
  `custom_id` varchar(20) DEFAULT NULL,
  `sort_no` smallint(4) DEFAULT NULL COMMENT '序号',
  `leaf` varchar(2) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `enabled` varchar(1) DEFAULT '1' COMMENT '是否显示',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_column
-- ----------------------------
INSERT INTO `ts_column` VALUES ('01', '全部栏目', '0', null, '1', '0', null, '1');
INSERT INTO `ts_column` VALUES ('0105', '新闻中心', '01', null, '11', '0', '', '1');
INSERT INTO `ts_column` VALUES ('010503', '政策法规', '0105', null, '2', '1', null, '1');
INSERT INTO `ts_column` VALUES ('010502', '公安信息', '0105', null, '1', '1', null, '1');
INSERT INTO `ts_column` VALUES ('0106', '通知公告', '01', null, '1', '1', null, '1');
INSERT INTO `ts_column` VALUES ('0107', '服务承诺', '01', null, null, '1', null, '1');
INSERT INTO `ts_column` VALUES ('0108', '注册协议', '01', null, null, '1', null, '1');
INSERT INTO `ts_column` VALUES ('0109', '首页幻灯图', '01', null, null, '1', null, '1');

-- ----------------------------
-- Table structure for ts_dept
-- ----------------------------
DROP TABLE IF EXISTS `ts_dept`;
CREATE TABLE `ts_dept` (
  `id` varchar(100) NOT NULL DEFAULT '',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `parent_id` varchar(100) DEFAULT NULL COMMENT '父结点',
  `custom_id` varchar(20) DEFAULT NULL COMMENT ' 业务代码',
  `sort_no` smallint(4) DEFAULT NULL COMMENT '排序号',
  `leaf` varchar(2) DEFAULT NULL COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `remark` varchar(100) DEFAULT NULL,
  `enabled` varchar(1) DEFAULT NULL COMMENT '1启用2禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_c0017791` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_dept
-- ----------------------------
INSERT INTO `ts_dept` VALUES ('001002', '政秘科', '001', null, null, '1', null, '1');
INSERT INTO `ts_dept` VALUES ('001003', '督察处', '001', null, null, '1', null, '1');
INSERT INTO `ts_dept` VALUES ('001004', '治安处', '001', null, null, '1', null, '1');
INSERT INTO `ts_dept` VALUES ('001', '森林公安', '0', '01        ', '1', '0', null, '1');
INSERT INTO `ts_dept` VALUES ('001001', '综合处', '001', null, null, '1', '1', '1');
INSERT INTO `ts_dept` VALUES ('001005', '刑侦处', '001', null, null, '1', null, '1');
INSERT INTO `ts_dept` VALUES ('001006', '装备处', '001', '', null, '1', '', '1');
INSERT INTO `ts_dept` VALUES ('001001001', '综合处一组', '001001', null, '1', '1', null, null);

-- ----------------------------
-- Table structure for ts_file
-- ----------------------------
DROP TABLE IF EXISTS `ts_file`;
CREATE TABLE `ts_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `size` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `operate_date` datetime DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL COMMENT 'company',
  `form_id` int(11) DEFAULT NULL,
  `lat` varchar(20) DEFAULT NULL COMMENT '经度',
  `lng` varchar(20) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_file
-- ----------------------------
INSERT INTO `ts_file` VALUES ('13', 'IMG_20161103_143938.jpg', 'upload/case/20161102163121320/IMG_20161103_143938.jpg', '446863', null, '606', '2016-11-03 14:52:45', 'case', '15', '', '');
INSERT INTO `ts_file` VALUES ('14', '11111.jpg', 'upload/case/20161102163121320/11111.jpg', '382630', null, '606', '2016-11-03 14:59:46', 'case', '15', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('15', '11111.jpg', 'upload/case/20161102163121320/11111.jpg', '382630', null, '606', '2016-11-03 15:25:59', 'case', '15', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('18', '11112.jpg', 'upload/case/20161103105801733/11112.jpg', '382630', null, '606', '2016-11-03 15:39:45', 'case', '16', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('19', '222.png', 'upload/case/20161114151754913/222.png', '200387', null, '606', '2016-11-14 15:19:40', 'case', '19', '', '');
INSERT INTO `ts_file` VALUES ('20', '1112.jpg', 'upload/case/20161118104346928/1112.jpg', '8253', null, '606', '2016-11-18 10:45:35', 'case', '35', '', '');
INSERT INTO `ts_file` VALUES ('21', '11112.jpg', 'upload/case/20161130100515921/11112.jpg', '432877', null, '606', '2016-11-30 10:07:40', 'case', '70', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('22', '11112.jpg', 'upload/case/20161130101036600/11112.jpg', '432877', null, '606', '2016-11-30 10:10:47', 'case', '71', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('23', '11112.jpg', 'upload/case/20161130101202119/11112.jpg', '432877', null, '606', '2016-11-30 10:12:12', 'case', '72', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('24', '11112.jpg', 'upload/case/20161130101340662/11112.jpg', '432877', null, '606', '2016-11-30 10:13:46', 'case', '73', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('25', '11112.jpg', 'upload/case/20161130102122476/11112.jpg', '432877', null, '606', '2016-11-30 10:22:04', 'case', '76', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('26', '11112.jpg', 'upload/case/20161130102254457/11112.jpg', '432877', null, '606', '2016-11-30 10:23:35', 'case', '77', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('27', '11112.jpg', 'upload/case/20161130104829349/11112.jpg', '432877', null, '606', '2016-11-30 10:52:40', 'case', '88', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('28', '11112.jpg', 'upload/case/20161130104829349/11112.jpg', '432877', null, '606', '2016-11-30 10:54:05', 'case', '88', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('29', '647986010108678613.jpg', 'upload/case/20161203144626863/647986010108678613.jpg', '74914', null, '606', '2016-12-03 14:47:42', 'case', '92', '', '');
INSERT INTO `ts_file` VALUES ('30', '384272627a0f2d1da2703a4318b2c587_r.jpg', 'upload/case/20161203144626863/384272627a0f2d1da2703a4318b2c587_r.jpg', '141070', null, '606', '2016-12-03 14:55:05', 'case', '92', '', '');
INSERT INTO `ts_file` VALUES ('31', '11113.jpg', 'upload/case/20161228094807454/11113.jpg', '432877', null, '606', '2016-12-28 09:48:16', 'case', '97', '34.740239', '113.712506');
INSERT INTO `ts_file` VALUES ('32', '营业执照副本2016(1).JPG', 'upload/case/20170111092738361/营业执照副本2016(1).JPG', '313849', null, '606', '2017-01-11 09:29:14', 'case', '144', '', '');
INSERT INTO `ts_file` VALUES ('33', '20170112120957x1_1.1363225015_1122_768_58027.jpg', 'upload/case/20170112120654812/20170112120957x1_1.1363225015_1122_768_58027.jpg', '86001', null, '606', '2017-01-12 12:09:58', 'case', '145', '', '');
INSERT INTO `ts_file` VALUES ('34', '20170112122427机房空调3.jpg', 'upload/case/20170112120654812/20170112122427机房空调3.jpg', '33562', null, '606', '2017-01-12 12:24:27', 'case', '145', '', '');
INSERT INTO `ts_file` VALUES ('35', '20170113022141机房空调3.jpg', 'upload/case/20170112120654812/20170113022141机房空调3.jpg', '33562', null, '606', '2017-01-13 02:21:41', 'case', '145', '', '');

-- ----------------------------
-- Table structure for ts_icon
-- ----------------------------
DROP TABLE IF EXISTS `ts_icon`;
CREATE TABLE `ts_icon` (
  `id` varchar(8) NOT NULL DEFAULT '',
  `file_name` varchar(50) DEFAULT NULL,
  `css_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_c0017822` (`id`),
  UNIQUE KEY `sys_c0017811` (`file_name`),
  UNIQUE KEY `sys_c0017812` (`css_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_icon
-- ----------------------------
INSERT INTO `ts_icon` VALUES ('10000018', 'accept.png', 'acceptIcon');
INSERT INTO `ts_icon` VALUES ('10000046', 'add.png', 'addIcon');
INSERT INTO `ts_icon` VALUES ('10000008', 'application.png', 'applicationIcon');
INSERT INTO `ts_icon` VALUES ('10000010', 'application_cascade.png', 'application_cascadeIcon');
INSERT INTO `ts_icon` VALUES ('10000009', 'application_double.png', 'application_doubleIcon');
INSERT INTO `ts_icon` VALUES ('10000005', 'application_home.png', 'application_homeIcon');
INSERT INTO `ts_icon` VALUES ('10000028', 'application_view_list.png', 'application_view_listIcon');
INSERT INTO `ts_icon` VALUES ('10000063', 'app_boxes.png', 'app_boxesIcon');
INSERT INTO `ts_icon` VALUES ('10000064', 'app_left.png', 'app_leftIcon');
INSERT INTO `ts_icon` VALUES ('10000065', 'app_right.png', 'app_rightIcon');
INSERT INTO `ts_icon` VALUES ('10000022', 'arrow_refresh.png', 'arrow_refreshIcon');
INSERT INTO `ts_icon` VALUES ('10000066', 'arrow_switch.png', 'arrow_switchIcon');
INSERT INTO `ts_icon` VALUES ('10000048', 'award_star_silver_3.png', 'award_star_silver_3Icon');
INSERT INTO `ts_icon` VALUES ('10000025', 'book_previous.png', 'book_previousIcon');
INSERT INTO `ts_icon` VALUES ('10000053', 'bug.png', 'bugIcon');
INSERT INTO `ts_icon` VALUES ('10000033', 'building.png', 'buildingIcon');
INSERT INTO `ts_icon` VALUES ('10000032', 'chart_organisation.png', 'chart_organisationIcon');
INSERT INTO `ts_icon` VALUES ('10000037', 'collapse-all.gif', 'collapse-allIcon');
INSERT INTO `ts_icon` VALUES ('10000007', 'comments.png', 'commentsIcon');
INSERT INTO `ts_icon` VALUES ('10000012', 'comments_add.png', 'comments_addIcon');
INSERT INTO `ts_icon` VALUES ('10000017', 'comment_add.png', 'comment_addIcon');
INSERT INTO `ts_icon` VALUES ('10000054', 'config.png', 'configIcon');
INSERT INTO `ts_icon` VALUES ('10000004', 'cup.png', 'cupIcon');
INSERT INTO `ts_icon` VALUES ('10000051', 'database_connect.png', 'database_connectIcon');
INSERT INTO `ts_icon` VALUES ('10000056', 'database_refresh.png', 'database_refreshIcon');
INSERT INTO `ts_icon` VALUES ('10000019', 'delete.png', 'deleteIcon');
INSERT INTO `ts_icon` VALUES ('10000049', 'disconnect.png', 'disconnectIcon');
INSERT INTO `ts_icon` VALUES ('10000058', 'docs.gif', 'docsIcon');
INSERT INTO `ts_icon` VALUES ('10000062', 'download.png', 'downloadIcon');
INSERT INTO `ts_icon` VALUES ('10000047', 'edit1.png', 'edit1Icon');
INSERT INTO `ts_icon` VALUES ('10000052', 'exclamation.png', 'exclamationIcon');
INSERT INTO `ts_icon` VALUES ('10000036', 'expand-all.gif', 'expand-allIcon');
INSERT INTO `ts_icon` VALUES ('10000001', 'folder_camera.png', 'folder_cameraIcon');
INSERT INTO `ts_icon` VALUES ('10000003', 'folder_feed.png', 'folder_feedIcon');
INSERT INTO `ts_icon` VALUES ('10000002', 'folder_user.png', 'folder_userIcon');
INSERT INTO `ts_icon` VALUES ('10000000', 'folder_wrench.png', 'folder_wrenchIcon');
INSERT INTO `ts_icon` VALUES ('10000006', 'group.png', 'groupIcon');
INSERT INTO `ts_icon` VALUES ('10000039', 'group_link.png', 'group_linkIcon');
INSERT INTO `ts_icon` VALUES ('10000027', 'image.png', 'imageIcon');
INSERT INTO `ts_icon` VALUES ('10000029', 'key.png', 'keyIcon');
INSERT INTO `ts_icon` VALUES ('10000031', 'layout_content.png', 'layout_contentIcon');
INSERT INTO `ts_icon` VALUES ('10000023', 'lock.png', 'lockIcon');
INSERT INTO `ts_icon` VALUES ('10000041', 'medal_gold_1.png', 'medal_gold_1Icon');
INSERT INTO `ts_icon` VALUES ('10000040', 'medal_silver_3.png', 'medal_silver_3Icon');
INSERT INTO `ts_icon` VALUES ('10000060', 'message_edit.png', 'message_editIcon');
INSERT INTO `ts_icon` VALUES ('10000013', 'page_add.png', 'page_addIcon');
INSERT INTO `ts_icon` VALUES ('10000015', 'page_del.png', 'page_delIcon');
INSERT INTO `ts_icon` VALUES ('10000014', 'page_edit_1.png', 'page_edit_1Icon');
INSERT INTO `ts_icon` VALUES ('10000044', 'page_excel.png', 'page_excelIcon');
INSERT INTO `ts_icon` VALUES ('10000030', 'page_find.png', 'page_findIcon');
INSERT INTO `ts_icon` VALUES ('10000016', 'page_refresh.png', 'page_refreshIcon');
INSERT INTO `ts_icon` VALUES ('10000045', 'plugin.png', 'pluginIcon');
INSERT INTO `ts_icon` VALUES ('10000043', 'preview.png', 'previewIcon');
INSERT INTO `ts_icon` VALUES ('10000042', 'printer.png', 'printerIcon');
INSERT INTO `ts_icon` VALUES ('10000055', 'server_connect.png', 'server_connectIcon');
INSERT INTO `ts_icon` VALUES ('10000020', 'status_away.png', 'status_awayIcon');
INSERT INTO `ts_icon` VALUES ('10000021', 'status_busy.png', 'status_busyIcon');
INSERT INTO `ts_icon` VALUES ('10000035', 'status_online.png', 'status_onlineIcon');
INSERT INTO `ts_icon` VALUES ('10000050', 'table.png', 'tableIcon');
INSERT INTO `ts_icon` VALUES ('10000011', 'tbar_synchronize.png', 'tbar_synchronizeIcon');
INSERT INTO `ts_icon` VALUES ('10000059', 'theme2.png', 'theme2Icon');
INSERT INTO `ts_icon` VALUES ('10000061', 'upload.png', 'uploadIcon');
INSERT INTO `ts_icon` VALUES ('10000038', 'user.png', 'userIcon');
INSERT INTO `ts_icon` VALUES ('10000057', 'user_comment.png', 'user_commentIcon');
INSERT INTO `ts_icon` VALUES ('10000034', 'user_female.png', 'user_femaleIcon');
INSERT INTO `ts_icon` VALUES ('10000026', 'window_caise_list.png', 'window_caise_listIcon');
INSERT INTO `ts_icon` VALUES ('10000024', 'wrench.png', 'wrenchIcon');
INSERT INTO `ts_icon` VALUES ('10000067', 'plugin_add.png', 'plugin_addIcon');
INSERT INTO `ts_icon` VALUES ('10000068', 'plugin_edit.png', 'plugin_editIcon');

-- ----------------------------
-- Table structure for ts_menu
-- ----------------------------
DROP TABLE IF EXISTS `ts_menu`;
CREATE TABLE `ts_menu` (
  `id` varchar(60) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `text` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` varchar(60) DEFAULT NULL COMMENT '上级菜单编号',
  `iconcls` varchar(50) DEFAULT NULL COMMENT '节点图标css类名',
  `collapsed` varchar(2) DEFAULT '0' COMMENT '展开状态(1:展开;0:收缩)',
  `url` varchar(100) DEFAULT NULL COMMENT '请求地址',
  `leaf` varchar(2) DEFAULT '0' COMMENT '叶子节点(0:树枝节点;1:叶子节点)',
  `sort_no` smallint(4) DEFAULT NULL COMMENT '排序号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `icon` varchar(50) DEFAULT NULL COMMENT '节点图标',
  `type` varchar(2) DEFAULT '0' COMMENT '菜单类型(1:系统菜单;0:业务菜单)',
  `en_name` varchar(50) DEFAULT NULL COMMENT '英文名',
  `module_id` int(22) DEFAULT NULL COMMENT '模块编号',
  `iconcss` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_c0017839` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_menu
-- ----------------------------
INSERT INTO `ts_menu` VALUES ('010201', '菜单管理', '0102', 'group_linkIcon', '0', '/menu/index', '1', '2', null, 'upload/criminal/icon-yellow-1.png', '1', 'menu', '3', null);
INSERT INTO `ts_menu` VALUES ('010202', '平台设置', '0102', 'group_linkIcon', '0', '/setting/index', '1', '1', null, 'upload/criminal/ionic-blue-01.png', '1', 'sys', '3', null);
INSERT INTO `ts_menu` VALUES ('0102', '系统管理', '01', null, '1', null, '0', '9', null, '', '1', null, '3', 'Hui-iconfont-system');
INSERT INTO `ts_menu` VALUES ('0103', '权限管理', '01', null, '1', null, '0', '8', null, '', '1', null, '3', 'Hui-iconfont-zhongguoyinxing');
INSERT INTO `ts_menu` VALUES ('010301', '角色管理', '0103', 'chart_organisationIcon', '0', '/role/index', '1', '1', null, 'upload/criminal/ionic-jsgl.png', '1', 'role', '3', null);
INSERT INTO `ts_menu` VALUES ('01', '功能菜单', '0', null, '1', null, '0', '0', '全部菜单', null, '1', null, '1', null);
INSERT INTO `ts_menu` VALUES ('010203', '栏目管理', '0102', null, '0', '/column/index', '1', '3', null, 'upload/criminal/ionic-green-01.png', '0', 'column', '3', null);
INSERT INTO `ts_menu` VALUES ('0106', '信息管理', '01', null, '1', null, '0', '2', null, '', '0', '', '1', null);
INSERT INTO `ts_menu` VALUES ('010603', '信息管理', '0106', null, '1', '/news/index', '1', '1', null, 'upload/criminal/gouwu.png', '0', 'new', '1', null);
INSERT INTO `ts_menu` VALUES ('010303', '菜单操作', '0103', null, '0', '/action/index', '1', '2', null, 'upload/criminal/ionic-cdgl.png', '1', 'action', '3', null);
INSERT INTO `ts_menu` VALUES ('010304', '部门管理', '0103', null, '0', '/dept/index', '1', '1', null, 'upload/criminal/ionic-bmgl.png', '0', null, '3', null);
INSERT INTO `ts_menu` VALUES ('010305', '人员管理', '0103', null, '0', '/user/index', '1', '2', null, 'upload/criminal/ionic-blue-user.png', '0', null, '3', null);

-- ----------------------------
-- Table structure for ts_menu_action
-- ----------------------------
DROP TABLE IF EXISTS `ts_menu_action`;
CREATE TABLE `ts_menu_action` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `menu_id` varchar(100) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_menu_action
-- ----------------------------
INSERT INTO `ts_menu_action` VALUES ('6', '添加', null, '010603', 'add');
INSERT INTO `ts_menu_action` VALUES ('7', '编辑', null, '010603', 'edit');
INSERT INTO `ts_menu_action` VALUES ('8', '删除', null, '010603', 'delete');
INSERT INTO `ts_menu_action` VALUES ('9', '添加', null, '010701', 'add');
INSERT INTO `ts_menu_action` VALUES ('10', '编辑', null, '010701', 'edit');
INSERT INTO `ts_menu_action` VALUES ('11', '删除', null, '010701', 'delete');
INSERT INTO `ts_menu_action` VALUES ('12', '添加', null, '010801', 'add');
INSERT INTO `ts_menu_action` VALUES ('13', '编辑', null, '010801', 'edit');
INSERT INTO `ts_menu_action` VALUES ('14', '删除', null, '010801', 'delete');
INSERT INTO `ts_menu_action` VALUES ('15', '添加', null, '010901', 'add');
INSERT INTO `ts_menu_action` VALUES ('16', '编辑', null, '010901', 'edit');
INSERT INTO `ts_menu_action` VALUES ('17', '删除', null, '010901', 'delete');
INSERT INTO `ts_menu_action` VALUES ('18', '添加', null, '010902', 'add');
INSERT INTO `ts_menu_action` VALUES ('19', '编辑', null, '010902', 'edit');
INSERT INTO `ts_menu_action` VALUES ('20', '删除', null, '010902', 'delete');
INSERT INTO `ts_menu_action` VALUES ('21', '添加', null, '010903', 'add');
INSERT INTO `ts_menu_action` VALUES ('22', '编辑', null, '010903', 'edit');
INSERT INTO `ts_menu_action` VALUES ('23', '删除', null, '010903', 'delete');
INSERT INTO `ts_menu_action` VALUES ('24', '添加', null, '011001', 'add');
INSERT INTO `ts_menu_action` VALUES ('25', '编辑', null, '011001', 'edit');
INSERT INTO `ts_menu_action` VALUES ('26', '删除', null, '011001', 'delete');
INSERT INTO `ts_menu_action` VALUES ('27', '添加', null, '011101', 'add');
INSERT INTO `ts_menu_action` VALUES ('28', '编辑', null, '011101', 'edit');
INSERT INTO `ts_menu_action` VALUES ('29', '删除', null, '011101', 'delete');
INSERT INTO `ts_menu_action` VALUES ('30', '添加', null, '010303', 'add');
INSERT INTO `ts_menu_action` VALUES ('31', '编辑', null, '010303', 'edit');
INSERT INTO `ts_menu_action` VALUES ('32', '删除', null, '010303', 'delete');
INSERT INTO `ts_menu_action` VALUES ('33', '添加', null, '010301', 'add');
INSERT INTO `ts_menu_action` VALUES ('34', '编辑', null, '010301', 'edit');
INSERT INTO `ts_menu_action` VALUES ('35', '删除', null, '010301', 'delete');
INSERT INTO `ts_menu_action` VALUES ('36', '添加', null, '010302', 'add');
INSERT INTO `ts_menu_action` VALUES ('37', '编辑', null, '010302', 'edit');
INSERT INTO `ts_menu_action` VALUES ('38', '删除', null, '010302', 'delete');
INSERT INTO `ts_menu_action` VALUES ('39', '添加', null, '010202', 'add');
INSERT INTO `ts_menu_action` VALUES ('40', '编辑', null, '010202', 'edit');
INSERT INTO `ts_menu_action` VALUES ('41', '删除', null, '010202', 'delete');
INSERT INTO `ts_menu_action` VALUES ('42', '添加', null, '010201', 'add');
INSERT INTO `ts_menu_action` VALUES ('43', '编辑', null, '010201', 'edit');
INSERT INTO `ts_menu_action` VALUES ('44', '删除', null, '010201', 'delete');
INSERT INTO `ts_menu_action` VALUES ('45', '添加', null, '010203', 'add');
INSERT INTO `ts_menu_action` VALUES ('46', '编辑', null, '010203', 'edit');
INSERT INTO `ts_menu_action` VALUES ('47', '删除', null, '010203', 'delete');
INSERT INTO `ts_menu_action` VALUES ('51', '添加', null, '010501', 'add');
INSERT INTO `ts_menu_action` VALUES ('52', '编辑', null, '010501', 'edit');
INSERT INTO `ts_menu_action` VALUES ('53', '删除', null, '010501', 'delete');
INSERT INTO `ts_menu_action` VALUES ('54', '自己报价', '供货商查看自己的报价', '010801', 'person');
INSERT INTO `ts_menu_action` VALUES ('55', '所有供货商报价', '能查看所有的报价单,一般是管理员的', '010801', 'all');
INSERT INTO `ts_menu_action` VALUES ('56', '添加', null, '010502', 'add');
INSERT INTO `ts_menu_action` VALUES ('57', '编辑', null, '010502', 'edit');
INSERT INTO `ts_menu_action` VALUES ('58', '删除', null, '010502', 'delete');
INSERT INTO `ts_menu_action` VALUES ('62', '审核通过按钮', '提交报价后需要管理员审核', '010801', 'pass');
INSERT INTO `ts_menu_action` VALUES ('63', '审核不通过按钮', '提交报价后需要管理员审核', '010801', 'nopass');
INSERT INTO `ts_menu_action` VALUES ('64', '自己的供货商报价', '用于采购查看自己的供货商报价', '010801', 'allmy');

-- ----------------------------
-- Table structure for ts_module
-- ----------------------------
DROP TABLE IF EXISTS `ts_module`;
CREATE TABLE `ts_module` (
  `id` int(22) NOT NULL DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `en_name` varchar(50) DEFAULT NULL,
  `home_page` varchar(100) DEFAULT NULL,
  `sort_no` smallint(4) DEFAULT NULL,
  `collapsed` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_module
-- ----------------------------
INSERT INTO `ts_module` VALUES ('1', '办公', 'office', 's_myorder', '1', '1');
INSERT INTO `ts_module` VALUES ('3', '系统', 'system', null, '3', '1');

-- ----------------------------
-- Table structure for ts_notice
-- ----------------------------
DROP TABLE IF EXISTS `ts_notice`;
CREATE TABLE `ts_notice` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `title` text,
  `content` text,
  `createdate` datetime DEFAULT NULL,
  `userid` varchar(8) DEFAULT NULL,
  `enabled` varchar(2) DEFAULT NULL,
  `noticetype` varchar(2) DEFAULT NULL,
  `columnid` int(11) DEFAULT NULL COMMENT '栏目编号',
  `summary` varchar(500) DEFAULT NULL,
  `source` varchar(100) DEFAULT NULL COMMENT '来源',
  `iscomment` varchar(1) DEFAULT '0' COMMENT '0不允许1允许',
  `author` varchar(100) DEFAULT NULL COMMENT '作者',
  `thumb` varchar(200) DEFAULT NULL,
  `sortno` smallint(6) DEFAULT NULL COMMENT '排序号',
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `status` varchar(1) DEFAULT '0' COMMENT '0起草1发布',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_c0017860` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000022 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_notice
-- ----------------------------
INSERT INTO `ts_notice` VALUES ('10000017', '图1', '<p>图1</p>', '2016-11-01 11:23:57', null, null, null, '109', null, null, '0', null, 'upload/news/20170228165915/s31.jpg', null, '4', '1');
INSERT INTO `ts_notice` VALUES ('10000019', '图3', '<p>图3</p>', '2016-11-01 11:24:31', null, null, null, '109', null, null, '0', null, 'upload/news/20170228164542/s21.jpg', null, '1', '1');
INSERT INTO `ts_notice` VALUES ('10000020', '森林公安突击查处救鸟千只', '<p style=\"font-family: &quot;Microsoft YaHei&quot;; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; line-height: 30px; white-space: normal;\"><strong style=\"margin: 0px; padding: 0px;\">小餐馆菜谱竟首推炸麻雀　　</strong></p><p style=\"font-family: Verdana, Arial, Helvetica, sans-serif; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; white-space: normal; text-indent: 2em; color: rgb(51, 51, 51); word-break: break-all; word-wrap: break-word;\">果林里，三张粘鸟网相连长达百余米；餐馆里，菜谱上的第一道菜就是“炸麻雀”……正值候鸟迁徙，人类的滥捕滥杀让不少鸟遭灭顶之灾。近日有爱鸟人士向北京晨报反映，通州乔庄建材市场内的上营花鸟市场贩卖北京市一二级保护鸟类。北京晨报记者走访发现，这些鸟的价格在几十元至几百元间，十分抢手，商家还接受预约服务。北京晨报记者随即向市森林公安反映此事。</p><p style=\"font-family: Verdana, Arial, Helvetica, sans-serif; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; white-space: normal; text-indent: 2em; color: rgb(51, 51, 51); word-break: break-all; word-wrap: break-word;\">昨天，市园林局森林公安局与执法监察大队联手，&nbsp;分14路突击通州、大兴的鸟市、果园和餐馆，查处捕猎和售卖野鸟的违法行为。</p><p style=\"font-family: &quot;Microsoft YaHei&quot;; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; line-height: 30px; white-space: normal;\"><br/></p><p style=\"font-family: Verdana, Arial, Helvetica, sans-serif; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; white-space: normal; text-indent: 2em; color: rgb(51, 51, 51); word-break: break-all; word-wrap: break-word;\"><strong style=\"font-family: &quot;Microsoft YaHei&quot;; margin: 0px; padding: 0px;\">记者走访&nbsp;&nbsp;违法鸟贩藏身建材市场</strong><span style=\"color:#FFFFFF;font-family: &quot;Microsoft YaHei&quot;; margin: 0px; padding: 0px;\"></span></p><p style=\"font-family: Verdana, Arial, Helvetica, sans-serif; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; white-space: normal; text-indent: 2em; color: rgb(51, 51, 51); word-break: break-all; word-wrap: break-word;\">上周六上午，记者随爱鸟人士李先生来到通州运河西街附近的乔庄建材市场内，此时十多个鸟贩就地摆摊售卖，他们把鸟笼一字铺开，十分抢眼，其中不乏一些罕见鸟类。</p><p style=\"font-family: Verdana, Arial, Helvetica, sans-serif; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; white-space: normal; text-indent: 2em; color: rgb(51, 51, 51); word-break: break-all; word-wrap: break-word;\">李先生介绍说，这个野市上常能看到保护鸟类。在一家摊位，他指着一只如麻雀大小的鸟说，“这就是野生的燕雀儿，是北京市二级保护鸟类”。只见这只鸟儿背部呈黄褐色，嘴为深色，脚为黄色。而在另一家摊位上，李先生还认出了北京市二级保护鸟类，“隼”。商贩对于出售野生鸟类的情况也不避讳，“都是在果园里抓的，有的买家就喜欢野趣儿。”<span style=\"color:#FFFFFF;font-family: &quot;Microsoft YaHei&quot;; margin: 0px; padding: 0px;\"></span></p><p style=\"font-family: Verdana, Arial, Helvetica, sans-serif; margin-top: 0px; margin-bottom: 0px; padding: 0px; font-size: medium; white-space: normal; text-indent: 2em; color: rgb(51, 51, 51); word-break: break-all; word-wrap: break-word;\">“你想要什么鸟可以预订，什么时候抓到，我给你打电话，但是具体时间说不准。”一摊贩称，他干这行有一年的时间了，货源有保障，“大部分鸟我们都能抓到”。</p><p><br/></p>', '2016-11-01 11:34:18', null, null, null, '10502', null, null, '0', null, 'upload/news/20170228165957/Koala.jpg', null, '6', '1');
INSERT INTO `ts_notice` VALUES ('10000021', '平台上线通知', '<p>平台正式上线。</p>', '2016-11-01 11:36:07', null, null, null, '106', null, null, '0', null, null, null, '6', '1');

-- ----------------------------
-- Table structure for ts_param
-- ----------------------------
DROP TABLE IF EXISTS `ts_param`;
CREATE TABLE `ts_param` (
  `paramid` varchar(8) NOT NULL DEFAULT '',
  `paramkey` varchar(50) DEFAULT NULL,
  `paramvalue` text,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`paramid`),
  UNIQUE KEY `sys_c0017871` (`paramid`),
  UNIQUE KEY `sys_c0017861` (`paramkey`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_param
-- ----------------------------
INSERT INTO `ts_param` VALUES ('10000008', 'SYS_DEFAULT_THEME', 'default', '系统默认主题皮肤');
INSERT INTO `ts_param` VALUES ('10000009', 'DEFAULT_ADMIN_ACCOUNT', 'super', '默认超级管理员帐户');
INSERT INTO `ts_param` VALUES ('10000010', 'DEFAULT_DEVELOP_ACCOUNT', 'developer', '系统默认开发专用帐户');
INSERT INTO `ts_param` VALUES ('10000001', 'SYS_TITLE', '微信公众号管理平台', null);

-- ----------------------------
-- Table structure for ts_role
-- ----------------------------
DROP TABLE IF EXISTS `ts_role`;
CREATE TABLE `ts_role` (
  `id` int(22) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_c0017878` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_role
-- ----------------------------
INSERT INTO `ts_role` VALUES ('1', '管理员', '系统管理员');
INSERT INTO `ts_role` VALUES ('2', '主办民警', null);
INSERT INTO `ts_role` VALUES ('3', '主管领导', null);
INSERT INTO `ts_role` VALUES ('4', '录入员', null);
INSERT INTO `ts_role` VALUES ('6', '测试', null);

-- ----------------------------
-- Table structure for ts_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `ts_role_auth`;
CREATE TABLE `ts_role_auth` (
  `role_id` varchar(8) NOT NULL,
  `menu_id` varchar(60) NOT NULL,
  `actions` varchar(100) DEFAULT NULL,
  `ui` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_role_auth
-- ----------------------------
INSERT INTO `ts_role_auth` VALUES ('1', '0102', null, null);
INSERT INTO `ts_role_auth` VALUES ('1', '010201', 'menu:add,menu:edit,menu:delete', 'button:J_add:hide,button:J_edit:hide,bar:J_bar:hide');
INSERT INTO `ts_role_auth` VALUES ('1', '010202', 'sys:add,sys:edit,sys:delete', null);
INSERT INTO `ts_role_auth` VALUES ('1', '010203', 'column:add,column:edit,column:delete', null);
INSERT INTO `ts_role_auth` VALUES ('1', '0103', null, null);
INSERT INTO `ts_role_auth` VALUES ('1', '010301', 'role:add,role:edit,role:delete', null);
INSERT INTO `ts_role_auth` VALUES ('1', '010303', 'action:add,action:edit,action:delete', null);
INSERT INTO `ts_role_auth` VALUES ('1', '01', null, null);
INSERT INTO `ts_role_auth` VALUES ('1', '010304', null, null);
INSERT INTO `ts_role_auth` VALUES ('1', '010305', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '01', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '010305', null, null);
INSERT INTO `ts_role_auth` VALUES ('2', '01', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '0113', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011306', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011301', null, null);
INSERT INTO `ts_role_auth` VALUES ('2', '011201', 'case:add', null);
INSERT INTO `ts_role_auth` VALUES ('2', '0112', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011302', null, null);
INSERT INTO `ts_role_auth` VALUES ('20', '01', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '0102', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '010205', '', null);
INSERT INTO `ts_role_auth` VALUES ('20', '0107', null, null);
INSERT INTO `ts_role_auth` VALUES ('20', '010701', null, null);
INSERT INTO `ts_role_auth` VALUES ('20', '0108', null, null);
INSERT INTO `ts_role_auth` VALUES ('20', '010801', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '010202', 'sys:add,sys:edit', null);
INSERT INTO `ts_role_auth` VALUES ('3', '0112', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011303', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011304', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011305', null, null);
INSERT INTO `ts_role_auth` VALUES ('4', '01', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '010303', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '010301', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '010304', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '0103', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '010603', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '0106', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011201', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '011202', null, null);
INSERT INTO `ts_role_auth` VALUES ('1', '010603', 'new:add,new:edit,new:delete', null);
INSERT INTO `ts_role_auth` VALUES ('6', '0112', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011305', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011304', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011303', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011302', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011301', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '0113', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '011306', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '01', null, null);
INSERT INTO `ts_role_auth` VALUES ('4', '0112', null, null);
INSERT INTO `ts_role_auth` VALUES ('4', '011202', null, null);
INSERT INTO `ts_role_auth` VALUES ('4', '011201', 'case:add', null);
INSERT INTO `ts_role_auth` VALUES ('3', '011201', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '011202', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '0106', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010603', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '0103', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010304', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010301', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010303', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010305', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '0102', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010205', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010202', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010201', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010203', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010204', null, null);
INSERT INTO `ts_role_auth` VALUES ('3', '010206', null, null);
INSERT INTO `ts_role_auth` VALUES ('6', '010201', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '010203', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '010204', '', null);
INSERT INTO `ts_role_auth` VALUES ('6', '010206', null, null);
INSERT INTO `ts_role_auth` VALUES ('1', '0106', null, null);

-- ----------------------------
-- Table structure for ts_setting
-- ----------------------------
DROP TABLE IF EXISTS `ts_setting`;
CREATE TABLE `ts_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL COMMENT '网站标题',
  `keywords` varchar(500) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `siteurl` varchar(100) DEFAULT NULL,
  `icp` varchar(500) DEFAULT NULL,
  `logo` varchar(200) DEFAULT NULL,
  `banner1` varchar(100) DEFAULT NULL,
  `banner2` varchar(100) DEFAULT NULL,
  `banner3` varchar(100) DEFAULT NULL,
  `banner4` varchar(100) DEFAULT NULL,
  `banner5` varchar(100) DEFAULT NULL,
  `promise` text COMMENT '服务承诺',
  `agreement` text COMMENT '网站协议',
  `contact` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_setting
-- ----------------------------
INSERT INTO `ts_setting` VALUES ('9', '北京通晟xx管理平台', '1234', '1234', '  ', '版权所有 ©2016-2017  北京通晟  京ICP备：16017658号', 'upload/site/electric_shock2.png', null, null, null, null, null, null, null, '400-1234-5678');

-- ----------------------------
-- Table structure for ts_user
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `id` int(22) NOT NULL AUTO_INCREMENT,
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '用户姓名',
  `account` varchar(50) NOT NULL COMMENT '登录账号',
  `dept_id` varchar(20) DEFAULT NULL COMMENT '部门id',
  `status` varchar(1) DEFAULT '1' COMMENT '状态 1 正常 0停用',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机',
  `remark` text COMMENT '备注',
  `sex` varchar(1) DEFAULT '0' COMMENT '默认0  1 男 2 女',
  `v_company` varchar(1) DEFAULT '0' COMMENT '企业资料认证:0未完善1审核中2已完善',
  `v_card` varchar(1) DEFAULT '0' COMMENT '是否已购买会员卡：0未购买1审核中2已购买',
  `card_begin` date DEFAULT NULL,
  `card_end` date DEFAULT NULL,
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `card_id` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `SYS_C0017890` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=613 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_user
-- ----------------------------
INSERT INTO `ts_user` VALUES ('606', '1', '管理员', 'admin', '001', '1', '3333', '管理员账号', '0', '2', '2', '2016-09-13', '2099-12-13', '2016-09-12 00:25:14', '1');
INSERT INTO `ts_user` VALUES ('607', '1', '小李', '15237120000', '001002', '1', '18337160000', '18337160404', '0', '1', '2', '2016-10-17', '2017-04-17', '2016-09-18 00:25:14', '1');
INSERT INTO `ts_user` VALUES ('611', '123', '李四', 'ceshi', '001001', '1', '13566667777', null, '0', '1', '0', null, null, '2017-01-16 09:41:10', null);
INSERT INTO `ts_user` VALUES ('609', '001', 'YUAN', '001', '001001', '1', '13612344321', null, '0', '1', '0', null, null, '2017-01-13 23:48:35', null);
INSERT INTO `ts_user` VALUES ('610', '1', '张三', 'luru001', '001', '1', '13516789999', null, '0', '1', '0', null, null, '2017-01-16 09:40:16', null);
INSERT INTO `ts_user` VALUES ('612', '1', '黄三', 'luru002', '001007', '1', '1233', null, '0', '1', '0', null, null, '2017-01-16 15:44:04', null);

-- ----------------------------
-- Table structure for ts_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ts_user_role`;
CREATE TABLE `ts_user_role` (
  `user_id` int(22) NOT NULL,
  `role_id` int(22) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  UNIQUE KEY `SYS_C0017897` (`user_id`,`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ts_user_role
-- ----------------------------
INSERT INTO `ts_user_role` VALUES ('606', '1');
INSERT INTO `ts_user_role` VALUES ('607', '2');
INSERT INTO `ts_user_role` VALUES ('609', '4');
INSERT INTO `ts_user_role` VALUES ('610', '4');
INSERT INTO `ts_user_role` VALUES ('611', '4');
INSERT INTO `ts_user_role` VALUES ('612', '4');
INSERT INTO `ts_user_role` VALUES ('632', '3');
INSERT INTO `ts_user_role` VALUES ('633', '3');

-- ----------------------------
-- View structure for v_user_role
-- ----------------------------
DROP VIEW IF EXISTS `v_user_role`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER  VIEW `v_user_role` AS select `u`.`user_id` AS `user_id`,group_concat(`r`.`name` separator ',') AS `role_name`,group_concat(cast(`r`.`id` as char charset utf8) separator ',') AS `role_id` from (`ts_role` `r` join `ts_user_role` `u`) where (`r`.`id` = `u`.`role_id`) group by `u`.`user_id` ;

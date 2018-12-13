/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50544
Source Host           : localhost:3306
Source Database       : pps

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2018-12-13 16:09:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bank_account_1
-- ----------------------------
DROP TABLE IF EXISTS `bank_account_1`;
CREATE TABLE `bank_account_1` (
  `id` varchar(20) NOT NULL COMMENT '科目编号',
  `name` varchar(20) NOT NULL COMMENT '科目名称',
  `type` tinyint(4) NOT NULL COMMENT '账户类型，1：现金，2：银行卡，3：支付宝，4：微信',
  `head` varchar(20) DEFAULT NULL COMMENT '户主名',
  `account` varchar(50) DEFAULT NULL COMMENT '账户',
  `gathering` tinyint(4) DEFAULT '0' COMMENT '是否用于商城收款，0：否，1：是',
  `qrCode` varchar(50) DEFAULT NULL COMMENT '收款码',
  `procurement` tinyint(4) DEFAULT '0' COMMENT '是否用于订货平台，0：否，1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bank_account_1
-- ----------------------------
INSERT INTO `bank_account_1` VALUES ('1001', '库存现金', '1', null, null, '0', '/upload/bank_account/123.jpg', '0');
INSERT INTO `bank_account_1` VALUES ('1002', '银行存款', '2', null, null, '0', '/upload/bank_account/123.jpg', '0');
INSERT INTO `bank_account_1` VALUES ('100201', '微信账户', '3', null, null, '0', '/upload/bank_account/123.jpg', '0');
INSERT INTO `bank_account_1` VALUES ('100202', '支付宝账户', '4', null, null, '0', '/upload/bank_account/123.jpg', '0');
INSERT INTO `bank_account_1` VALUES ('100203', '建设银行', '2', null, null, '0', '/upload/bank_account/123.jpg', '0');
INSERT INTO `bank_account_1` VALUES ('100204', '招商银行', '2', null, null, '0', '/upload/bank_account/123.jpg', '0');
INSERT INTO `bank_account_1` VALUES ('100205', '华夏银行', '2', null, null, '0', '/upload/bank_account/123.jpg', '0');

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` varchar(50) NOT NULL COMMENT '客户编号',
  `name` varchar(10) NOT NULL COMMENT '客户姓名',
  `username` varchar(50) NOT NULL COMMENT '客户用户名',
  `password` varchar(50) NOT NULL COMMENT '客户密码',
  `phone` varchar(20) NOT NULL COMMENT '客户电话',
  `birthday` datetime DEFAULT NULL COMMENT '客户生日',
  `inviter_id` varchar(50) DEFAULT NULL COMMENT '邀请人',
  `inviter_name` varchar(10) DEFAULT NULL COMMENT '邀请人姓名',
  `integral` int(11) NOT NULL COMMENT '积分',
  `address` varchar(100) DEFAULT NULL COMMENT '客户地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '邮编',
  `membership_number` varchar(100) DEFAULT NULL COMMENT '会员卡号',
  `last_deal_time` datetime DEFAULT NULL COMMENT '最近交易时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `disabled` tinyint(4) NOT NULL COMMENT '是否停用，0：否，1：是',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('054774c2-f784-11e8-9dc7-54ee75c0f47a', '客户1', 'kh1', 'kh1', '17360034522', '2018-12-05 14:44:36', '', '', '0', '', '', '001', '2018-12-05 14:44:36', '2018-12-05 14:44:36', '0', '');
INSERT INTO `client` VALUES ('054b1e2c-f784-11e8-9dc7-54ee75c0f47a', '客户2', 'kh2', 'kh2', '17360034523', '2018-12-05 14:44:36', '', '', '0', '', '', '002', '2018-12-05 14:44:36', '2018-12-05 14:44:36', '0', '');
INSERT INTO `client` VALUES ('054e2add-f784-11e8-9dc7-54ee75c0f47a', '客户3', 'kh3', 'kh3', '17360034524', '2018-12-05 14:44:36', '', '', '100', '', '', '003', '2018-12-05 14:44:36', '2018-12-05 14:44:36', '0', '');
INSERT INTO `client` VALUES ('05510d52-f784-11e8-9dc7-54ee75c0f47a', '客户4', 'kh4', 'kh4', '17360034525', '2018-12-05 14:44:36', '', '', '0', '', '', null, '2018-12-05 14:44:36', '2018-12-05 14:44:36', '0', '');
INSERT INTO `client` VALUES ('kh001', '测试', 'username', 'password', '17760041487', '1995-05-25 00:00:00', null, null, '0', '', '', null, null, '2018-12-06 18:14:37', '0', '');
INSERT INTO `client` VALUES ('kh002', '测试2', 'username2', 'password', '17760041488', '1995-05-25 00:00:00', '05510d52-f784-11e8-9dc7-54ee75c0f47a', '客户4', '0', '', '', null, null, '2018-12-06 18:20:54', '0', '');
INSERT INTO `client` VALUES ('kh003', '测试3', 'username3', 'password', '17760041489', '1995-05-25 00:00:00', '05510d52-f784-11e8-9dc7-54ee75c0f47a', '客户4', '0', '', '', '600', null, '2018-12-06 18:23:24', '0', '');

-- ----------------------------
-- Table structure for client_client_level
-- ----------------------------
DROP TABLE IF EXISTS `client_client_level`;
CREATE TABLE `client_client_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户客户级别id',
  `client_id` varchar(50) NOT NULL COMMENT '客户编号',
  `level_id` int(11) NOT NULL COMMENT '客户级别id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client_client_level
-- ----------------------------
INSERT INTO `client_client_level` VALUES ('1', '054774c2-f784-11e8-9dc7-54ee75c0f47a', '1');
INSERT INTO `client_client_level` VALUES ('2', '054b1e2c-f784-11e8-9dc7-54ee75c0f47a', '2');
INSERT INTO `client_client_level` VALUES ('3', '054e2add-f784-11e8-9dc7-54ee75c0f47a', '3');
INSERT INTO `client_client_level` VALUES ('4', '05510d52-f784-11e8-9dc7-54ee75c0f47a', '4');
INSERT INTO `client_client_level` VALUES ('5', 'kh001', '4');
INSERT INTO `client_client_level` VALUES ('6', 'kh002', '4');
INSERT INTO `client_client_level` VALUES ('7', 'kh003', '4');

-- ----------------------------
-- Table structure for client_integral_detail
-- ----------------------------
DROP TABLE IF EXISTS `client_integral_detail`;
CREATE TABLE `client_integral_detail` (
  `id` varchar(50) NOT NULL COMMENT '积分明细id',
  `client_id` varchar(50) NOT NULL COMMENT '客户编号',
  `create_time` datetime NOT NULL COMMENT '发生日期',
  `type` tinyint(4) NOT NULL COMMENT '操作类型，1：后台增加，3：消费增加，4：提成增加，5：后台减少，6：提现减少',
  `change_integral` int(11) NOT NULL COMMENT '改变积分',
  `after_change_integral` int(11) NOT NULL COMMENT '改变后的积分',
  `invoices_date` datetime DEFAULT NULL COMMENT '单据日期',
  `invoices_id` varchar(50) DEFAULT NULL COMMENT '单据编号',
  `invoices_type` tinyint(4) DEFAULT NULL COMMENT '单据类型，？？',
  `handled_by` varchar(50) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client_integral_detail
-- ----------------------------
INSERT INTO `client_integral_detail` VALUES ('d067a8bd-766e-44b1-b998-8cbf214cff08', '054e2add-f784-11e8-9dc7-54ee75c0f47a', '2018-12-05 22:51:10', '1', '100', '100', null, null, null, 'dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '备注');

-- ----------------------------
-- Table structure for client_level
-- ----------------------------
DROP TABLE IF EXISTS `client_level`;
CREATE TABLE `client_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户级别id',
  `name` varchar(20) NOT NULL COMMENT '客户级别',
  `price_type` tinyint(4) NOT NULL COMMENT '级别价格类型，1：零售价， 2：vip售价',
  `price` decimal(10,2) NOT NULL COMMENT '级别默认价格，级别价格类型*0.几',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client_level
-- ----------------------------
INSERT INTO `client_level` VALUES ('1', '总店店长', '2', '0.60');
INSERT INTO `client_level` VALUES ('2', '分店店长', '2', '0.80');
INSERT INTO `client_level` VALUES ('3', '普通vip', '1', '1.00');
INSERT INTO `client_level` VALUES ('4', '普通客户', '1', '1.00');

-- ----------------------------
-- Table structure for client_membership_number
-- ----------------------------
DROP TABLE IF EXISTS `client_membership_number`;
CREATE TABLE `client_membership_number` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员卡号id',
  `number` varchar(100) NOT NULL COMMENT '会员卡号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of client_membership_number
-- ----------------------------
INSERT INTO `client_membership_number` VALUES ('1', '001');
INSERT INTO `client_membership_number` VALUES ('2', '002');
INSERT INTO `client_membership_number` VALUES ('3', '003');
INSERT INTO `client_membership_number` VALUES ('4', '004');
INSERT INTO `client_membership_number` VALUES ('5', '005');
INSERT INTO `client_membership_number` VALUES ('8', '600');
INSERT INTO `client_membership_number` VALUES ('9', '6546');

-- ----------------------------
-- Table structure for department_1
-- ----------------------------
DROP TABLE IF EXISTS `department_1`;
CREATE TABLE `department_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(20) NOT NULL COMMENT '部门名',
  `contacts` varchar(20) DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department_1
-- ----------------------------
INSERT INTO `department_1` VALUES ('1', '老板部门', null, null, null);

-- ----------------------------
-- Table structure for department_user_1
-- ----------------------------
DROP TABLE IF EXISTS `department_user_1`;
CREATE TABLE `department_user_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门用户id',
  `department_id` int(11) NOT NULL COMMENT '部门id',
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department_user_1
-- ----------------------------
INSERT INTO `department_user_1` VALUES ('1', '1', 'dcb71baa-f384-11e8-b25b-54ee75c0f47a');
INSERT INTO `department_user_1` VALUES ('2', '1', 'dcb9fa13-f384-11e8-b25b-54ee75c0f47a');

-- ----------------------------
-- Table structure for function
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '功能id',
  `name` varchar(10) NOT NULL COMMENT '功能名',
  `level` tinyint(4) NOT NULL COMMENT '功能级别',
  `parnet_id` int(11) NOT NULL COMMENT '父功能id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of function
-- ----------------------------
INSERT INTO `function` VALUES ('1', '销售', '1', '0');
INSERT INTO `function` VALUES ('2', '采购', '1', '0');
INSERT INTO `function` VALUES ('3', '仓库', '1', '0');
INSERT INTO `function` VALUES ('4', '资金', '1', '0');
INSERT INTO `function` VALUES ('5', '营销', '1', '0');
INSERT INTO `function` VALUES ('6', 'CRM', '1', '0');
INSERT INTO `function` VALUES ('7', '报表', '1', '0');
INSERT INTO `function` VALUES ('8', '资料', '1', '0');
INSERT INTO `function` VALUES ('9', '设置', '1', '0');
INSERT INTO `function` VALUES ('10', '批发业务', '2', '1');
INSERT INTO `function` VALUES ('11', '零售业务', '2', '1');
INSERT INTO `function` VALUES ('12', '销售结果', '2', '1');
INSERT INTO `function` VALUES ('13', '采购业务', '2', '2');
INSERT INTO `function` VALUES ('14', '采购结果', '2', '2');
INSERT INTO `function` VALUES ('15', '收发货', '2', '3');
INSERT INTO `function` VALUES ('16', '其他出入库', '2', '3');
INSERT INTO `function` VALUES ('17', '盘点', '2', '3');
INSERT INTO `function` VALUES ('18', '预警', '2', '3');
INSERT INTO `function` VALUES ('19', '收款', '2', '4');
INSERT INTO `function` VALUES ('20', '付款', '2', '4');
INSERT INTO `function` VALUES ('21', '往来对账', '2', '4');
INSERT INTO `function` VALUES ('22', '往来调整', '2', '4');
INSERT INTO `function` VALUES ('23', '收入支出', '2', '4');
INSERT INTO `function` VALUES ('24', '营销活动', '2', '5');
INSERT INTO `function` VALUES ('25', '外勤', '2', '6');
INSERT INTO `function` VALUES ('26', 'CRM', '2', '6');
INSERT INTO `function` VALUES ('27', '单据中心', '2', '7');
INSERT INTO `function` VALUES ('28', '库存报表', '2', '7');
INSERT INTO `function` VALUES ('29', '资金报表', '2', '7');
INSERT INTO `function` VALUES ('30', '订单统计', '2', '7');
INSERT INTO `function` VALUES ('31', '销售报表', '2', '7');
INSERT INTO `function` VALUES ('32', '采购报表', '2', '7');
INSERT INTO `function` VALUES ('33', '经营中心', '2', '7');
INSERT INTO `function` VALUES ('34', '商品管理', '2', '8');
INSERT INTO `function` VALUES ('35', '往来单位', '2', '8');
INSERT INTO `function` VALUES ('36', '机构管理', '2', '8');
INSERT INTO `function` VALUES ('37', '财务账户', '2', '8');
INSERT INTO `function` VALUES ('38', '岗位权限', '2', '8');
INSERT INTO `function` VALUES ('39', '系统配置', '2', '9');
INSERT INTO `function` VALUES ('40', '期初录入', '2', '9');
INSERT INTO `function` VALUES ('41', '账套操作', '2', '9');
INSERT INTO `function` VALUES ('42', '批发业务', '2', '1');
INSERT INTO `function` VALUES ('43', '零售业务', '2', '1');
INSERT INTO `function` VALUES ('44', '销售结果', '2', '1');
INSERT INTO `function` VALUES ('45', '采购业务', '2', '2');
INSERT INTO `function` VALUES ('46', '采购结果', '2', '2');
INSERT INTO `function` VALUES ('47', '收发货', '2', '3');
INSERT INTO `function` VALUES ('48', '其他出入库', '2', '3');
INSERT INTO `function` VALUES ('49', '盘点', '2', '3');
INSERT INTO `function` VALUES ('50', '预警', '2', '3');
INSERT INTO `function` VALUES ('51', '收款', '2', '4');
INSERT INTO `function` VALUES ('52', '付款', '2', '4');
INSERT INTO `function` VALUES ('53', '往来对账', '2', '4');
INSERT INTO `function` VALUES ('54', '往来调整', '2', '4');
INSERT INTO `function` VALUES ('55', '收入支出', '2', '4');
INSERT INTO `function` VALUES ('56', '营销活动', '2', '5');
INSERT INTO `function` VALUES ('57', '外勤', '2', '6');
INSERT INTO `function` VALUES ('58', 'CRM', '2', '6');
INSERT INTO `function` VALUES ('59', '单据中心', '2', '7');
INSERT INTO `function` VALUES ('60', '库存报表', '2', '7');
INSERT INTO `function` VALUES ('61', '资金报表', '2', '7');
INSERT INTO `function` VALUES ('62', '订单统计', '2', '7');
INSERT INTO `function` VALUES ('63', '销售报表', '2', '7');
INSERT INTO `function` VALUES ('64', '采购报表', '2', '7');
INSERT INTO `function` VALUES ('65', '经营中心', '2', '7');
INSERT INTO `function` VALUES ('66', '商品管理', '2', '8');
INSERT INTO `function` VALUES ('67', '往来单位', '2', '8');
INSERT INTO `function` VALUES ('68', '机构管理', '2', '8');
INSERT INTO `function` VALUES ('69', '财务账户', '2', '8');
INSERT INTO `function` VALUES ('70', '岗位权限', '2', '8');
INSERT INTO `function` VALUES ('71', '系统配置', '2', '9');
INSERT INTO `function` VALUES ('72', '期初录入', '2', '9');
INSERT INTO `function` VALUES ('73', '账套操作', '2', '9');
INSERT INTO `function` VALUES ('74', '销售订单', '3', '10');
INSERT INTO `function` VALUES ('75', '销售退货申请', '3', '10');
INSERT INTO `function` VALUES ('76', '销售换货申请', '3', '10');
INSERT INTO `function` VALUES ('77', '零售单', '3', '11');
INSERT INTO `function` VALUES ('78', '零售交班历史', '3', '11');
INSERT INTO `function` VALUES ('79', '销售历史', '3', '12');
INSERT INTO `function` VALUES ('80', '采购订单', '3', '13');
INSERT INTO `function` VALUES ('81', '采购退货申请', '3', '13');
INSERT INTO `function` VALUES ('82', '采购换货申请', '3', '13');
INSERT INTO `function` VALUES ('83', '采购历史', '3', '14');
INSERT INTO `function` VALUES ('84', '待发货', '3', '15');
INSERT INTO `function` VALUES ('85', '待收货', '3', '15');
INSERT INTO `function` VALUES ('86', '其他入库单', '3', '16');
INSERT INTO `function` VALUES ('87', '其他出库单', '3', '16');
INSERT INTO `function` VALUES ('88', '其他出入库历史', '3', '16');
INSERT INTO `function` VALUES ('89', '报损单', '3', '17');
INSERT INTO `function` VALUES ('90', '报溢单', '3', '17');
INSERT INTO `function` VALUES ('91', '库存盘点单', '3', '17');
INSERT INTO `function` VALUES ('92', '成本调价单', '3', '17');
INSERT INTO `function` VALUES ('93', '库存预警设置', '3', '18');
INSERT INTO `function` VALUES ('94', '库存预警查询', '3', '18');
INSERT INTO `function` VALUES ('95', '近效期设置', '3', '18');
INSERT INTO `function` VALUES ('96', '近效期查询', '3', '18');
INSERT INTO `function` VALUES ('97', '按单收款', '3', '19');
INSERT INTO `function` VALUES ('98', '收款单', '3', '19');
INSERT INTO `function` VALUES ('99', '预收款单', '3', '19');
INSERT INTO `function` VALUES ('100', '待确认款项', '3', '19');
INSERT INTO `function` VALUES ('101', '收款统计', '3', '19');
INSERT INTO `function` VALUES ('102', '按单付款', '3', '20');
INSERT INTO `function` VALUES ('103', '付款单', '3', '20');
INSERT INTO `function` VALUES ('104', '预付款单', '3', '20');
INSERT INTO `function` VALUES ('105', '付款统计', '3', '20');
INSERT INTO `function` VALUES ('106', '查应收', '3', '21');
INSERT INTO `function` VALUES ('107', '职员部门应收款', '3', '21');
INSERT INTO `function` VALUES ('108', '超期应收查询', '3', '21');
INSERT INTO `function` VALUES ('109', '查应付', '3', '21');
INSERT INTO `function` VALUES ('110', '应收应付调整', '3', '22');
INSERT INTO `function` VALUES ('111', '往来清账', '3', '22');
INSERT INTO `function` VALUES ('112', '应收应付调整', '3', '23');
INSERT INTO `function` VALUES ('113', '其他收入', '3', '23');
INSERT INTO `function` VALUES ('114', '发短信', '3', '24');
INSERT INTO `function` VALUES ('115', '优惠券', '3', '24');
INSERT INTO `function` VALUES ('116', '促销管理', '3', '24');
INSERT INTO `function` VALUES ('117', '客户分布', '3', '25');
INSERT INTO `function` VALUES ('118', '客户管理', '3', '26');
INSERT INTO `function` VALUES ('119', '客户跟进', '3', '26');
INSERT INTO `function` VALUES ('120', '合同', '3', '26');
INSERT INTO `function` VALUES ('121', '待记账单据', '3', '27');
INSERT INTO `function` VALUES ('122', '业务草稿', '3', '27');
INSERT INTO `function` VALUES ('123', '查库存', '3', '28');
INSERT INTO `function` VALUES ('124', '进销存分析', '3', '28');
INSERT INTO `function` VALUES ('125', '出入库明细', '3', '28');
INSERT INTO `function` VALUES ('126', '查应收', '3', '29');
INSERT INTO `function` VALUES ('127', '查应付', '3', '29');
INSERT INTO `function` VALUES ('128', '查资金', '3', '29');
INSERT INTO `function` VALUES ('129', '查回款', '3', '29');
INSERT INTO `function` VALUES ('130', '查费用', '3', '29');
INSERT INTO `function` VALUES ('131', '订单分析', '3', '30');
INSERT INTO `function` VALUES ('132', '商品订货分析', '3', '30');
INSERT INTO `function` VALUES ('133', '客户订货分析', '3', '30');
INSERT INTO `function` VALUES ('134', '销售日月年报', '3', '31');
INSERT INTO `function` VALUES ('135', '商品销售分析', '3', '31');
INSERT INTO `function` VALUES ('136', '客户销售分析', '3', '31');
INSERT INTO `function` VALUES ('137', '业绩统计', '3', '31');
INSERT INTO `function` VALUES ('138', '回款统计', '3', '31');
INSERT INTO `function` VALUES ('139', '提成设置', '3', '31');
INSERT INTO `function` VALUES ('140', '提成统计', '3', '31');
INSERT INTO `function` VALUES ('141', '商品采购分析', '3', '32');
INSERT INTO `function` VALUES ('142', '供应商采购分析', '3', '32');
INSERT INTO `function` VALUES ('143', '采购订单分析', '3', '32');
INSERT INTO `function` VALUES ('144', '销售经营分析', '3', '33');
INSERT INTO `function` VALUES ('145', '资金经营分析', '3', '33');
INSERT INTO `function` VALUES ('146', '往来经营分析', '3', '33');
INSERT INTO `function` VALUES ('147', '库存经营分析', '3', '33');
INSERT INTO `function` VALUES ('148', '利润经营分析', '3', '33');
INSERT INTO `function` VALUES ('149', '老板中心', '3', '33');
INSERT INTO `function` VALUES ('150', '商品', '3', '34');
INSERT INTO `function` VALUES ('151', '商品SKU及条码', '3', '34');
INSERT INTO `function` VALUES ('152', '商品价格管理', '3', '34');
INSERT INTO `function` VALUES ('153', '商品辅助资料', '3', '34');
INSERT INTO `function` VALUES ('154', '图片管理', '3', '34');
INSERT INTO `function` VALUES ('155', '客户', '3', '35');
INSERT INTO `function` VALUES ('156', '供应商', '3', '35');
INSERT INTO `function` VALUES ('157', '仓库信息', '3', '36');
INSERT INTO `function` VALUES ('158', '职员部门', '3', '36');
INSERT INTO `function` VALUES ('159', '银行账户', '3', '37');
INSERT INTO `function` VALUES ('160', '费用类型', '3', '37');
INSERT INTO `function` VALUES ('161', '其他收入', '3', '37');
INSERT INTO `function` VALUES ('162', '费用单', '3', '38');
INSERT INTO `function` VALUES ('163', '全部操作员', '3', '38');
INSERT INTO `function` VALUES ('164', '系统参数', '3', '39');
INSERT INTO `function` VALUES ('165', '审核设置', '3', '39');
INSERT INTO `function` VALUES ('166', '支付配置', '3', '39');
INSERT INTO `function` VALUES ('167', '企业信息', '3', '39');
INSERT INTO `function` VALUES ('168', '库存期初', '3', '40');
INSERT INTO `function` VALUES ('169', '现金银行期初', '3', '40');
INSERT INTO `function` VALUES ('170', '应收期初', '3', '40');
INSERT INTO `function` VALUES ('171', '应付期初', '3', '40');
INSERT INTO `function` VALUES ('172', '系统开账', '3', '41');
INSERT INTO `function` VALUES ('173', '系统重建', '3', '41');
INSERT INTO `function` VALUES ('174', '操作日志', '3', '41');

-- ----------------------------
-- Table structure for fund_order_1
-- ----------------------------
DROP TABLE IF EXISTS `fund_order_1`;
CREATE TABLE `fund_order_1` (
  `id` varchar(50) NOT NULL COMMENT '单据编号',
  `type` tinyint(4) NOT NULL COMMENT '单据类型，1：付款单，2：收款单',
  `create_time` datetime NOT NULL COMMENT '单据日期',
  `apply_order_id` varchar(50) NOT NULL COMMENT '来源订单',
  `order_status` tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  `bank_account_id` varchar(20) NOT NULL COMMENT '银行账户编号',
  `money` decimal(10,2) NOT NULL COMMENT '金额',
  `user_id` varchar(50) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund_order_1
-- ----------------------------
INSERT INTO `fund_order_1` VALUES ('FKD_5e41b1c3-756d-4ad1-a423-29d7353cc21f', '2', '2018-12-13 15:43:19', 'CGDD_001', '1', '1001', '40.00', 'string', 'remark');
INSERT INTO `fund_order_1` VALUES ('FKD_6d26ed67-d23e-4fee-ba22-dbe03f85013c', '2', '2018-12-13 15:36:57', 'CGDD_001', '1', '1001', '60.00', 'string', 'remark');

-- ----------------------------
-- Table structure for goods_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_1`;
CREATE TABLE `goods_1` (
  `id` varchar(50) NOT NULL COMMENT '商品货号',
  `name` varchar(200) NOT NULL COMMENT '商品名',
  `bar_code` varchar(50) NOT NULL COMMENT '条码',
  `type_id` int(11) NOT NULL COMMENT '分类id',
  `putaway` tinyint(4) NOT NULL COMMENT '上架状态，0：未上架，1：已上架',
  `skus` mediumtext COMMENT '商品规格',
  `origin` varchar(200) DEFAULT NULL COMMENT '产地',
  `image` varchar(200) DEFAULT NULL COMMENT '图片',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_1
-- ----------------------------
INSERT INTO `goods_1` VALUES ('00ffd356-d220-4fd6-8fe7-77d5b8f77493', '测试商品1', 'kjasdlkj', '1', '1', null, '', '', '', '2018-12-09 16:24:24');
INSERT INTO `goods_1` VALUES ('bf7299c6-ec0c-4d97-b122-281b89bf9c32', '测试商品', 'kjasdlkj', '1', '1', null, '', '', '', '2018-12-09 16:23:15');
INSERT INTO `goods_1` VALUES ('e6e94cae-a08d-4164-a25f-77ac55d3d540', '舍得典藏款1', '176', '1', '1', '', '四川', '/upload/goods/123123.jpg', '', '2018-12-09 14:14:04');
INSERT INTO `goods_1` VALUES ('sp001', '舍得典藏款', '176', '1', '1', '[{\"key\":\"品牌\",\"value\":[\"舍得\"]},{\"key\":\"单位\",\"value\":[\"件\", \"瓶\"]}]', '四川', '/upload/goods/123123.jpg', '', '2018-12-09 03:54:11');
INSERT INTO `goods_1` VALUES ('sp002', '美的电压力锅', '177', '2', '1', '[{\"key\":\"品牌\",\"value\":[\"美的\"]},{\"key\":\"单位\",\"value\":[\"个\"]}]', '浙江', '/upload/goods/123123.jpg', '', '2018-12-09 03:54:11');

-- ----------------------------
-- Table structure for goods_goods_label_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_goods_label_1`;
CREATE TABLE `goods_goods_label_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品/商品标签关系id',
  `goods_id` varchar(50) NOT NULL COMMENT '商品货号',
  `label_id` int(11) NOT NULL COMMENT '商品标签id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_goods_label_1
-- ----------------------------
INSERT INTO `goods_goods_label_1` VALUES ('1', 'sp001', '1');
INSERT INTO `goods_goods_label_1` VALUES ('2', 'sp001', '2');
INSERT INTO `goods_goods_label_1` VALUES ('3', 'sp002', '3');
INSERT INTO `goods_goods_label_1` VALUES ('4', 'sp002', '4');
INSERT INTO `goods_goods_label_1` VALUES ('23', '00ffd356-d220-4fd6-8fe7-77d5b8f77493', '1');
INSERT INTO `goods_goods_label_1` VALUES ('24', '00ffd356-d220-4fd6-8fe7-77d5b8f77493', '3');

-- ----------------------------
-- Table structure for goods_label_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_label_1`;
CREATE TABLE `goods_label_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品标签id',
  `name` varchar(20) NOT NULL COMMENT '商品标签名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_label_1
-- ----------------------------
INSERT INTO `goods_label_1` VALUES ('3', '促销');
INSERT INTO `goods_label_1` VALUES ('2', '推荐');
INSERT INTO `goods_label_1` VALUES ('1', '新品');
INSERT INTO `goods_label_1` VALUES ('4', '清仓');

-- ----------------------------
-- Table structure for goods_property_key_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_property_key_1`;
CREATE TABLE `goods_property_key_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性名id',
  `name` varchar(20) NOT NULL COMMENT '商品属性名',
  `type_id` int(11) NOT NULL COMMENT '商品分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_property_key_1
-- ----------------------------
INSERT INTO `goods_property_key_1` VALUES ('1', '品牌', '1');
INSERT INTO `goods_property_key_1` VALUES ('2', '单位', '1');
INSERT INTO `goods_property_key_1` VALUES ('3', '品牌', '2');

-- ----------------------------
-- Table structure for goods_property_value_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_property_value_1`;
CREATE TABLE `goods_property_value_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性值id',
  `name` varchar(20) NOT NULL COMMENT '商品属性值',
  `property_key_id` int(11) NOT NULL COMMENT '商品属性名id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_property_value_1
-- ----------------------------
INSERT INTO `goods_property_value_1` VALUES ('1', '舍得', '1');
INSERT INTO `goods_property_value_1` VALUES ('2', '二锅头', '1');
INSERT INTO `goods_property_value_1` VALUES ('3', '瓶', '2');
INSERT INTO `goods_property_value_1` VALUES ('4', '件', '2');
INSERT INTO `goods_property_value_1` VALUES ('5', '美的', '3');
INSERT INTO `goods_property_value_1` VALUES ('6', '个', '3');

-- ----------------------------
-- Table structure for goods_sku_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_sku_1`;
CREATE TABLE `goods_sku_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品规格id',
  `goods_id` varchar(50) NOT NULL COMMENT '商品货号',
  `sku` mediumtext NOT NULL COMMENT '商品规格',
  `purchase_price` decimal(10,2) NOT NULL COMMENT '进价',
  `retail_price` decimal(10,2) NOT NULL COMMENT '零售价',
  `vip_price` decimal(10,2) NOT NULL COMMENT 'vip售价',
  `inventory` int(11) NOT NULL COMMENT '可用库存',
  `integral` int(11) NOT NULL COMMENT '商品积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_sku_1
-- ----------------------------
INSERT INTO `goods_sku_1` VALUES ('1', 'sp001', '[{\"key\":\"品牌\",\"value\":\"舍得\"},{\"key\":\"单位\",\"value\":\"瓶\"}]', '555.00', '777.00', '666.00', '34', '0');
INSERT INTO `goods_sku_1` VALUES ('2', 'sp001', '[{\"key\":\"品牌\",\"value\":\"舍得\"},{\"key\":\"单位\",\"value\":\"件\"}]', '5550.00', '7770.00', '6660.00', '36', '0');
INSERT INTO `goods_sku_1` VALUES ('3', 'sp002', '[{\"key\":\"品牌\",\"value\":\"美的\"},{\"key\":\"单位\",\"value\":\"个\"}]', '200.00', '380.00', '300.00', '10', '0');
INSERT INTO `goods_sku_1` VALUES ('4', 'sp002', '[{\"key\":\"品牌\",\"value\":\"美的\"}]', '220.00', '400.00', '320.00', '5', '0');

-- ----------------------------
-- Table structure for goods_type_1
-- ----------------------------
DROP TABLE IF EXISTS `goods_type_1`;
CREATE TABLE `goods_type_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品分类id',
  `name` varchar(20) NOT NULL COMMENT '商品分类名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_type_1
-- ----------------------------
INSERT INTO `goods_type_1` VALUES ('1', '酒类');
INSERT INTO `goods_type_1` VALUES ('2', '锅类');

-- ----------------------------
-- Table structure for income_expenses_1
-- ----------------------------
DROP TABLE IF EXISTS `income_expenses_1`;
CREATE TABLE `income_expenses_1` (
  `id` varchar(20) NOT NULL COMMENT '科目编号',
  `name` varchar(20) NOT NULL COMMENT '科目名称',
  `check_item` tinyint(4) DEFAULT NULL COMMENT '核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门',
  `debit_credit` tinyint(4) NOT NULL COMMENT '借贷，1：贷，2：借',
  `type` tinyint(4) NOT NULL COMMENT '收支，1：收入，2：支出',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of income_expenses_1
-- ----------------------------
INSERT INTO `income_expenses_1` VALUES ('6052', '其他收入', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('605201', '利息收入', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('605202', '损赠收入', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('605203', '租赁收入', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('605204', '罚没收入', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('6111', '投资收益', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('6301', '营业外收入', null, '1', '1');
INSERT INTO `income_expenses_1` VALUES ('6403', '营业税金及附加', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6601', '销售费用', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660101', '优惠金额', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660102', '包装费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660103', '运输费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660104', '广告费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660105', '装卸费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660106', '保险费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660107', '展览费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660108', '租赁费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660109', '销售服务费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660110', '差旅费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660111', '办公费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660112', '折旧费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660113', '修理费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660114', '网店运费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660115', '快递费', '2', '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660116', '招待费', '2', '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660117', '水电气费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6602', '管理费用', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660202', '工会经费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660203', '职工教育经费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660204', '业务招待费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660205', '技术转让费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660206', '无形资产摊销', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660207', '咨询费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660208', '诉讼费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660209', '坏账损失', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660210', '工资', '4', '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660211', '房租', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660212', '水电气', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6603', '财务费用', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660302', '汇兑净损失', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('660303', '金融机构手续费', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6701', '资产减值损失', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6711', '营业外支出', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6801', '所得税费用', null, '2', '2');
INSERT INTO `income_expenses_1` VALUES ('6901', '以前年度损益调整', null, '2', '2');

-- ----------------------------
-- Table structure for procurement_apply_order_1
-- ----------------------------
DROP TABLE IF EXISTS `procurement_apply_order_1`;
CREATE TABLE `procurement_apply_order_1` (
  `id` varchar(50) NOT NULL COMMENT '单据编号',
  `type` tinyint(4) NOT NULL COMMENT '单据类型，1：采购订单，2：采购退货申请，3,：采购换货申请',
  `create_time` datetime NOT NULL COMMENT '单据日期',
  `apply_order_id` varchar(50) DEFAULT NULL COMMENT '来源订单',
  `order_status` tinyint(4) NOT NULL COMMENT '单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发',
  `clear_status` tinyint(4) NOT NULL COMMENT '结算状态：0：未完成，1：已完成',
  `supplier_id` varchar(20) NOT NULL COMMENT '供应商编号',
  `in_warehouse_id` int(11) DEFAULT NULL COMMENT '入库仓库编号，对应收货',
  `in_total_quantity` int(11) DEFAULT NULL COMMENT '总收货数量',
  `in_received_quantity` int(11) DEFAULT NULL COMMENT '已收货数量',
  `in_not_received_quantity` int(11) DEFAULT NULL COMMENT '未收货数量',
  `out_warehouse_id` int(11) DEFAULT NULL COMMENT '出库仓库编号，对应发货',
  `out_total_quantity` int(11) DEFAULT NULL COMMENT '总发货数量',
  `out_sent_quantity` int(11) DEFAULT NULL COMMENT '已发货数量',
  `out_not_sent_quantity` int(11) DEFAULT NULL COMMENT '未发货数量',
  `total_money` decimal(10,2) NOT NULL COMMENT '总商品金额',
  `total_discount_money` decimal(10,2) NOT NULL COMMENT '总优惠金额',
  `order_money` decimal(10,2) NOT NULL COMMENT '本单金额',
  `cleared_money` decimal(10,2) NOT NULL COMMENT '已结算金额',
  `not_cleared_money` decimal(10,2) NOT NULL COMMENT '未结算金额',
  `user_id` varchar(50) NOT NULL COMMENT '经手人编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procurement_apply_order_1
-- ----------------------------
INSERT INTO `procurement_apply_order_1` VALUES ('CGDD_001', '1', '2018-12-13 14:37:09', null, '1', '1', 'gys001', '1', '20', '0', '20', null, null, null, null, '100.00', '0.00', '100.00', '100.00', '0.00', 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', null);
INSERT INTO `procurement_apply_order_1` VALUES ('CGHHSQD_001', '3', '2018-12-13 14:37:09', null, '7', '0', 'gys001', '1', '20', '0', '20', '2', '10', '0', '10', '50.00', '0.00', '50.00', '0.00', '50.00', 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', null);
INSERT INTO `procurement_apply_order_1` VALUES ('CGTHSQD_001', '2', '2018-12-13 14:37:09', null, '4', '0', 'gys001', null, null, null, null, '1', '20', '0', '20', '-100.00', '0.00', '-100.00', '0.00', '-100.00', 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', null);

-- ----------------------------
-- Table structure for procurement_apply_order_goods_sku_1
-- ----------------------------
DROP TABLE IF EXISTS `procurement_apply_order_goods_sku_1`;
CREATE TABLE `procurement_apply_order_goods_sku_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '采购申请订单/商品规格关系编号',
  `type` tinyint(4) NOT NULL COMMENT '入库或出库，1：入库，0：出库',
  `apply_order_id` varchar(50) NOT NULL COMMENT '采购申请订单编号',
  `goods_sku_id` int(11) NOT NULL COMMENT '商品规格编号',
  `quantity` int(11) NOT NULL COMMENT '总数量',
  `finish_quantity` int(11) DEFAULT NULL COMMENT '完成数量',
  `not_finish_quantity` int(11) DEFAULT NULL COMMENT '未完成数量',
  `money` decimal(10,2) NOT NULL COMMENT '金额',
  `discount_money` decimal(10,2) NOT NULL COMMENT '优惠金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procurement_apply_order_goods_sku_1
-- ----------------------------
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('1', '1', 'CGDD_001', '1', '10', '10', '0', '50.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('2', '1', 'CGDD_001', '2', '10', '10', '0', '50.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('3', '0', 'CGTHSQD_001', '1', '10', '0', '10', '50.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('4', '0', 'CGTHSQD_001', '2', '10', '0', '10', '50.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('5', '1', 'CGHHSQD_001', '1', '10', '0', '10', '50.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('6', '1', 'CGHHSQD_001', '2', '10', '0', '10', '50.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('7', '0', 'CGHHSQD_001', '1', '6', '0', '6', '30.00', '0.00', null);
INSERT INTO `procurement_apply_order_goods_sku_1` VALUES ('8', '0', 'CGHHSQD_001', '2', '4', '0', '4', '20.00', '0.00', null);

-- ----------------------------
-- Table structure for procurement_result_order_1
-- ----------------------------
DROP TABLE IF EXISTS `procurement_result_order_1`;
CREATE TABLE `procurement_result_order_1` (
  `id` varchar(50) NOT NULL COMMENT '单据编号',
  `type` tinyint(4) NOT NULL COMMENT '单据类型，1：采购入库单，2：采购退货单，3,：采购换货单',
  `create_time` datetime NOT NULL COMMENT '单据日期',
  `apply_order_id` varchar(50) NOT NULL COMMENT '来源订单',
  `order_status` tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  `total_quantity` int(11) NOT NULL COMMENT '总商品数量',
  `total_money` decimal(10,2) NOT NULL COMMENT '总商品金额',
  `total_discount_money` decimal(10,2) NOT NULL COMMENT '总优惠金额',
  `order_money` decimal(10,2) NOT NULL COMMENT '本单金额',
  `user_id` varchar(50) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of procurement_result_order_1
-- ----------------------------
INSERT INTO `procurement_result_order_1` VALUES ('CGRKD_5bdebf6f-4b28-4b1c-90c1-2b0ca210cdd6', '1', '2018-12-12 23:49:03', 'CGDD_001', '-1', '20', '100.00', '0.00', '100.00', 'string', null);
INSERT INTO `procurement_result_order_1` VALUES ('HC_CGRKD_5bdebf6f-4b28-4b1c-90c1-2b0ca210cdd6', '1', '2018-12-13 00:11:32', 'CGDD_001', '-2', '-20', '-100.00', '0.00', '-100.00', 'string', null);

-- ----------------------------
-- Table structure for role_1
-- ----------------------------
DROP TABLE IF EXISTS `role_1`;
CREATE TABLE `role_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(10) NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_1
-- ----------------------------
INSERT INTO `role_1` VALUES ('1', '老板');
INSERT INTO `role_1` VALUES ('2', '销售经理');
INSERT INTO `role_1` VALUES ('3', '销售');
INSERT INTO `role_1` VALUES ('4', '采购');
INSERT INTO `role_1` VALUES ('5', '库管');
INSERT INTO `role_1` VALUES ('6', '财务');

-- ----------------------------
-- Table structure for role_function_1
-- ----------------------------
DROP TABLE IF EXISTS `role_function_1`;
CREATE TABLE `role_function_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `function_id` int(11) NOT NULL COMMENT '功能id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_function_1
-- ----------------------------
INSERT INTO `role_function_1` VALUES ('1', '1', '1');
INSERT INTO `role_function_1` VALUES ('2', '1', '2');
INSERT INTO `role_function_1` VALUES ('3', '1', '3');
INSERT INTO `role_function_1` VALUES ('4', '1', '4');
INSERT INTO `role_function_1` VALUES ('5', '1', '5');
INSERT INTO `role_function_1` VALUES ('6', '1', '6');
INSERT INTO `role_function_1` VALUES ('7', '1', '7');
INSERT INTO `role_function_1` VALUES ('8', '1', '8');
INSERT INTO `role_function_1` VALUES ('9', '1', '9');
INSERT INTO `role_function_1` VALUES ('10', '1', '10');
INSERT INTO `role_function_1` VALUES ('11', '1', '11');
INSERT INTO `role_function_1` VALUES ('12', '1', '12');
INSERT INTO `role_function_1` VALUES ('13', '1', '13');
INSERT INTO `role_function_1` VALUES ('14', '1', '14');
INSERT INTO `role_function_1` VALUES ('15', '1', '15');
INSERT INTO `role_function_1` VALUES ('16', '1', '16');
INSERT INTO `role_function_1` VALUES ('17', '1', '17');
INSERT INTO `role_function_1` VALUES ('18', '1', '18');
INSERT INTO `role_function_1` VALUES ('19', '1', '19');
INSERT INTO `role_function_1` VALUES ('20', '1', '20');
INSERT INTO `role_function_1` VALUES ('21', '1', '21');
INSERT INTO `role_function_1` VALUES ('22', '1', '22');
INSERT INTO `role_function_1` VALUES ('23', '1', '23');
INSERT INTO `role_function_1` VALUES ('24', '1', '24');
INSERT INTO `role_function_1` VALUES ('25', '1', '25');
INSERT INTO `role_function_1` VALUES ('26', '1', '26');
INSERT INTO `role_function_1` VALUES ('27', '1', '27');
INSERT INTO `role_function_1` VALUES ('28', '1', '28');
INSERT INTO `role_function_1` VALUES ('29', '1', '29');
INSERT INTO `role_function_1` VALUES ('30', '1', '30');
INSERT INTO `role_function_1` VALUES ('31', '1', '31');
INSERT INTO `role_function_1` VALUES ('32', '1', '32');
INSERT INTO `role_function_1` VALUES ('33', '1', '33');
INSERT INTO `role_function_1` VALUES ('34', '1', '34');
INSERT INTO `role_function_1` VALUES ('35', '1', '35');
INSERT INTO `role_function_1` VALUES ('36', '1', '36');
INSERT INTO `role_function_1` VALUES ('37', '1', '37');
INSERT INTO `role_function_1` VALUES ('38', '1', '38');
INSERT INTO `role_function_1` VALUES ('39', '1', '39');
INSERT INTO `role_function_1` VALUES ('40', '1', '40');
INSERT INTO `role_function_1` VALUES ('41', '1', '41');
INSERT INTO `role_function_1` VALUES ('42', '1', '42');
INSERT INTO `role_function_1` VALUES ('43', '1', '43');
INSERT INTO `role_function_1` VALUES ('44', '1', '44');
INSERT INTO `role_function_1` VALUES ('45', '1', '45');
INSERT INTO `role_function_1` VALUES ('46', '1', '46');
INSERT INTO `role_function_1` VALUES ('47', '1', '47');
INSERT INTO `role_function_1` VALUES ('48', '1', '48');
INSERT INTO `role_function_1` VALUES ('49', '1', '49');
INSERT INTO `role_function_1` VALUES ('50', '1', '50');
INSERT INTO `role_function_1` VALUES ('51', '1', '51');
INSERT INTO `role_function_1` VALUES ('52', '1', '52');
INSERT INTO `role_function_1` VALUES ('53', '1', '53');
INSERT INTO `role_function_1` VALUES ('54', '1', '54');
INSERT INTO `role_function_1` VALUES ('55', '1', '55');
INSERT INTO `role_function_1` VALUES ('56', '1', '56');
INSERT INTO `role_function_1` VALUES ('57', '1', '57');
INSERT INTO `role_function_1` VALUES ('58', '1', '58');
INSERT INTO `role_function_1` VALUES ('59', '1', '59');
INSERT INTO `role_function_1` VALUES ('60', '1', '60');
INSERT INTO `role_function_1` VALUES ('61', '1', '61');
INSERT INTO `role_function_1` VALUES ('62', '1', '62');
INSERT INTO `role_function_1` VALUES ('63', '1', '63');
INSERT INTO `role_function_1` VALUES ('64', '1', '64');
INSERT INTO `role_function_1` VALUES ('65', '1', '65');
INSERT INTO `role_function_1` VALUES ('66', '1', '66');
INSERT INTO `role_function_1` VALUES ('67', '1', '67');
INSERT INTO `role_function_1` VALUES ('68', '1', '68');
INSERT INTO `role_function_1` VALUES ('69', '1', '69');
INSERT INTO `role_function_1` VALUES ('70', '1', '70');
INSERT INTO `role_function_1` VALUES ('71', '1', '71');
INSERT INTO `role_function_1` VALUES ('72', '1', '72');
INSERT INTO `role_function_1` VALUES ('73', '1', '73');
INSERT INTO `role_function_1` VALUES ('74', '1', '74');
INSERT INTO `role_function_1` VALUES ('75', '1', '75');
INSERT INTO `role_function_1` VALUES ('76', '1', '76');
INSERT INTO `role_function_1` VALUES ('77', '1', '77');
INSERT INTO `role_function_1` VALUES ('78', '1', '78');
INSERT INTO `role_function_1` VALUES ('79', '1', '79');
INSERT INTO `role_function_1` VALUES ('80', '1', '80');
INSERT INTO `role_function_1` VALUES ('81', '1', '81');
INSERT INTO `role_function_1` VALUES ('82', '1', '82');
INSERT INTO `role_function_1` VALUES ('83', '1', '83');
INSERT INTO `role_function_1` VALUES ('84', '1', '84');
INSERT INTO `role_function_1` VALUES ('85', '1', '85');
INSERT INTO `role_function_1` VALUES ('86', '1', '86');
INSERT INTO `role_function_1` VALUES ('87', '1', '87');
INSERT INTO `role_function_1` VALUES ('88', '1', '88');
INSERT INTO `role_function_1` VALUES ('89', '1', '89');
INSERT INTO `role_function_1` VALUES ('90', '1', '90');
INSERT INTO `role_function_1` VALUES ('91', '1', '91');
INSERT INTO `role_function_1` VALUES ('92', '1', '92');
INSERT INTO `role_function_1` VALUES ('93', '1', '93');
INSERT INTO `role_function_1` VALUES ('94', '1', '94');
INSERT INTO `role_function_1` VALUES ('95', '1', '95');
INSERT INTO `role_function_1` VALUES ('96', '1', '96');
INSERT INTO `role_function_1` VALUES ('97', '1', '97');
INSERT INTO `role_function_1` VALUES ('98', '1', '98');
INSERT INTO `role_function_1` VALUES ('99', '1', '99');
INSERT INTO `role_function_1` VALUES ('100', '1', '100');
INSERT INTO `role_function_1` VALUES ('101', '1', '101');
INSERT INTO `role_function_1` VALUES ('102', '1', '102');
INSERT INTO `role_function_1` VALUES ('103', '1', '103');
INSERT INTO `role_function_1` VALUES ('104', '1', '104');
INSERT INTO `role_function_1` VALUES ('105', '1', '105');
INSERT INTO `role_function_1` VALUES ('106', '1', '106');
INSERT INTO `role_function_1` VALUES ('107', '1', '107');
INSERT INTO `role_function_1` VALUES ('108', '1', '108');
INSERT INTO `role_function_1` VALUES ('109', '1', '109');
INSERT INTO `role_function_1` VALUES ('110', '1', '110');
INSERT INTO `role_function_1` VALUES ('111', '1', '111');
INSERT INTO `role_function_1` VALUES ('112', '1', '112');
INSERT INTO `role_function_1` VALUES ('113', '1', '113');
INSERT INTO `role_function_1` VALUES ('114', '1', '114');
INSERT INTO `role_function_1` VALUES ('115', '1', '115');
INSERT INTO `role_function_1` VALUES ('116', '1', '116');
INSERT INTO `role_function_1` VALUES ('117', '1', '117');
INSERT INTO `role_function_1` VALUES ('118', '1', '118');
INSERT INTO `role_function_1` VALUES ('119', '1', '119');
INSERT INTO `role_function_1` VALUES ('120', '1', '120');
INSERT INTO `role_function_1` VALUES ('121', '1', '121');
INSERT INTO `role_function_1` VALUES ('122', '1', '122');
INSERT INTO `role_function_1` VALUES ('123', '1', '123');
INSERT INTO `role_function_1` VALUES ('124', '1', '124');
INSERT INTO `role_function_1` VALUES ('125', '1', '125');
INSERT INTO `role_function_1` VALUES ('126', '1', '126');
INSERT INTO `role_function_1` VALUES ('127', '1', '127');
INSERT INTO `role_function_1` VALUES ('128', '1', '128');
INSERT INTO `role_function_1` VALUES ('129', '1', '129');
INSERT INTO `role_function_1` VALUES ('130', '1', '130');
INSERT INTO `role_function_1` VALUES ('131', '1', '131');
INSERT INTO `role_function_1` VALUES ('132', '1', '132');
INSERT INTO `role_function_1` VALUES ('133', '1', '133');
INSERT INTO `role_function_1` VALUES ('134', '1', '134');
INSERT INTO `role_function_1` VALUES ('135', '1', '135');
INSERT INTO `role_function_1` VALUES ('136', '1', '136');
INSERT INTO `role_function_1` VALUES ('137', '1', '137');
INSERT INTO `role_function_1` VALUES ('138', '1', '138');
INSERT INTO `role_function_1` VALUES ('139', '1', '139');
INSERT INTO `role_function_1` VALUES ('140', '1', '140');
INSERT INTO `role_function_1` VALUES ('141', '1', '141');
INSERT INTO `role_function_1` VALUES ('142', '1', '142');
INSERT INTO `role_function_1` VALUES ('143', '1', '143');
INSERT INTO `role_function_1` VALUES ('144', '1', '144');
INSERT INTO `role_function_1` VALUES ('145', '1', '145');
INSERT INTO `role_function_1` VALUES ('146', '1', '146');
INSERT INTO `role_function_1` VALUES ('147', '1', '147');
INSERT INTO `role_function_1` VALUES ('148', '1', '148');
INSERT INTO `role_function_1` VALUES ('149', '1', '149');
INSERT INTO `role_function_1` VALUES ('150', '1', '150');
INSERT INTO `role_function_1` VALUES ('151', '1', '151');
INSERT INTO `role_function_1` VALUES ('152', '1', '152');
INSERT INTO `role_function_1` VALUES ('153', '1', '153');
INSERT INTO `role_function_1` VALUES ('154', '1', '154');
INSERT INTO `role_function_1` VALUES ('155', '1', '155');
INSERT INTO `role_function_1` VALUES ('156', '1', '156');
INSERT INTO `role_function_1` VALUES ('157', '1', '157');
INSERT INTO `role_function_1` VALUES ('158', '1', '158');
INSERT INTO `role_function_1` VALUES ('159', '1', '159');
INSERT INTO `role_function_1` VALUES ('160', '1', '160');
INSERT INTO `role_function_1` VALUES ('161', '1', '161');
INSERT INTO `role_function_1` VALUES ('162', '1', '162');
INSERT INTO `role_function_1` VALUES ('163', '1', '163');
INSERT INTO `role_function_1` VALUES ('164', '1', '164');
INSERT INTO `role_function_1` VALUES ('165', '1', '165');
INSERT INTO `role_function_1` VALUES ('166', '1', '166');
INSERT INTO `role_function_1` VALUES ('167', '1', '167');
INSERT INTO `role_function_1` VALUES ('168', '1', '168');
INSERT INTO `role_function_1` VALUES ('169', '1', '169');
INSERT INTO `role_function_1` VALUES ('170', '1', '170');
INSERT INTO `role_function_1` VALUES ('171', '1', '171');
INSERT INTO `role_function_1` VALUES ('172', '1', '172');
INSERT INTO `role_function_1` VALUES ('173', '1', '173');
INSERT INTO `role_function_1` VALUES ('174', '1', '174');

-- ----------------------------
-- Table structure for storage_order_1
-- ----------------------------
DROP TABLE IF EXISTS `storage_order_1`;
CREATE TABLE `storage_order_1` (
  `id` varchar(50) NOT NULL COMMENT '单据编号',
  `type` tinyint(4) NOT NULL COMMENT '单据类型，1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单',
  `create_time` datetime NOT NULL COMMENT '单据日期',
  `apply_order_id` varchar(50) NOT NULL COMMENT '来源订单',
  `order_status` tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `user_id` varchar(50) DEFAULT NULL COMMENT '经手人',
  `remark` varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of storage_order_1
-- ----------------------------
INSERT INTO `storage_order_1` VALUES ('CGDD_SHD_fc06241f-1c0a-4e33-84c6-33111109a559', '1', '2018-12-12 23:49:03', 'CGDD_001', '0', '20', 'userId', 'remark');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `name` varchar(50) NOT NULL COMMENT '店铺名',
  `user_id` varchar(50) DEFAULT NULL COMMENT '店铺老板id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES ('1', '总店', '9ba15c62-f2ef-11e8-bbaf-00155d031a09');

-- ----------------------------
-- Table structure for supplier_1
-- ----------------------------
DROP TABLE IF EXISTS `supplier_1`;
CREATE TABLE `supplier_1` (
  `id` varchar(20) NOT NULL COMMENT '供应商编号',
  `name` varchar(50) NOT NULL COMMENT '供应商名称',
  `contacts` varchar(20) NOT NULL COMMENT '联系人',
  `contact_number` varchar(20) NOT NULL COMMENT '联系电话',
  `contact_address` varchar(100) DEFAULT NULL COMMENT '联系地址',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier_1
-- ----------------------------
INSERT INTO `supplier_1` VALUES ('gys001', '美特斯邦威', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys002', '耐克', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys003', '阿迪达斯', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys004', '新百伦', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys005', '雅鹿', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys006', 'HSTYLE/韩都衣舍', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys008', '秋水伊人', '小明', '17760041487', '四川省成都市', '17760041487', '备注');
INSERT INTO `supplier_1` VALUES ('gys009', '测试123', '测试', '17760041487', '测试', '17760041487', '测试');

-- ----------------------------
-- Table structure for user_1
-- ----------------------------
DROP TABLE IF EXISTS `user_1`;
CREATE TABLE `user_1` (
  `id` varchar(50) NOT NULL COMMENT '用户id',
  `name` varchar(10) NOT NULL COMMENT '用户姓名',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `phone` varchar(20) NOT NULL COMMENT '电话',
  `warehouse_id` int(11) NOT NULL COMMENT '仓库id',
  `disabled` tinyint(4) NOT NULL COMMENT '是否禁用，0：不禁用，1：禁用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_1
-- ----------------------------
INSERT INTO `user_1` VALUES ('dcb71baa-f384-11e8-b25b-54ee75c0f47a', '总店老板', 'lb', 'lb', '17760041487', '1', '0', null);
INSERT INTO `user_1` VALUES ('dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '总店销售经理', 'xsjl', 'xsjl', '17760041487', '1', '0', null);
INSERT INTO `user_1` VALUES ('dcbd0207-f384-11e8-b25b-54ee75c0f47a', '总店销售', 'xs', 'xs', '17760041487', '1', '0', null);
INSERT INTO `user_1` VALUES ('dcc00d94-f384-11e8-b25b-54ee75c0f47a', '总店采购', 'cg', 'cg', '17760041487', '1', '0', null);
INSERT INTO `user_1` VALUES ('dcc30169-f384-11e8-b25b-54ee75c0f47a', '总店库管', 'kg', 'kg', '17760041487', '1', '0', null);
INSERT INTO `user_1` VALUES ('dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '总店财务', 'cw', 'cw', '17760041487', '1', '0', null);

-- ----------------------------
-- Table structure for user_role_1
-- ----------------------------
DROP TABLE IF EXISTS `user_role_1`;
CREATE TABLE `user_role_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role_1
-- ----------------------------
INSERT INTO `user_role_1` VALUES ('1', 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', '1');
INSERT INTO `user_role_1` VALUES ('2', 'dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '2');
INSERT INTO `user_role_1` VALUES ('3', 'dcbd0207-f384-11e8-b25b-54ee75c0f47a', '3');
INSERT INTO `user_role_1` VALUES ('4', 'dcc00d94-f384-11e8-b25b-54ee75c0f47a', '4');
INSERT INTO `user_role_1` VALUES ('5', 'dcc30169-f384-11e8-b25b-54ee75c0f47a', '5');
INSERT INTO `user_role_1` VALUES ('6', 'dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '6');
INSERT INTO `user_role_1` VALUES ('7', 'dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '3');

-- ----------------------------
-- Table structure for warehouse_1
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_1`;
CREATE TABLE `warehouse_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `name` varchar(20) DEFAULT NULL COMMENT '仓库名',
  `contacts` varchar(20) DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '邮编',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of warehouse_1
-- ----------------------------
INSERT INTO `warehouse_1` VALUES ('1', '第一个仓库', null, null, null, null, null);
INSERT INTO `warehouse_1` VALUES ('2', '第二个仓库', null, null, null, null, null);

/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50544
Source Host           : localhost:3306
Source Database       : pps

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2018-11-30 00:44:17
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_function_1
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES ('1', '总店', '9ba15c62-f2ef-11e8-bbaf-00155d031a09');
INSERT INTO `store` VALUES ('2', '分店2', '43b69d4a-c23c-44f1-9a51-d7adf626f5c9');
INSERT INTO `store` VALUES ('3', '分店3', 'c506b03f-119f-4b9e-aa13-ce1cf63363c8');
INSERT INTO `store` VALUES ('4', '分店4', '1422eae9-4dc4-4b65-884e-0d84ade3b471');
INSERT INTO `store` VALUES ('5', '分店5', 'decca0a4-0b32-4259-be1a-cce1a2b823ee');

-- ----------------------------
-- Table structure for user_1
-- ----------------------------
DROP TABLE IF EXISTS `user_1`;
CREATE TABLE `user_1` (
  `id` varchar(50) NOT NULL COMMENT '用户id',
  `name` varchar(10) NOT NULL COMMENT '用户姓名',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_1
-- ----------------------------
INSERT INTO `user_1` VALUES ('dcb71baa-f384-11e8-b25b-54ee75c0f47a', '总店老板', 'lb', 'lb', null);
INSERT INTO `user_1` VALUES ('dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '总店销售经理', 'xsjl', 'xsjl', null);
INSERT INTO `user_1` VALUES ('dcbd0207-f384-11e8-b25b-54ee75c0f47a', '总店销售', 'xs', 'xs', null);
INSERT INTO `user_1` VALUES ('dcc00d94-f384-11e8-b25b-54ee75c0f47a', '总店采购', 'cg', 'cg', null);
INSERT INTO `user_1` VALUES ('dcc30169-f384-11e8-b25b-54ee75c0f47a', '总店库管', 'kg', 'kg', null);
INSERT INTO `user_1` VALUES ('dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '总店财务', 'cw', 'cw', null);

-- ----------------------------
-- Table structure for user_role_1
-- ----------------------------
DROP TABLE IF EXISTS `user_role_1`;
CREATE TABLE `user_role_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` varchar(50) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role_1
-- ----------------------------
INSERT INTO `user_role_1` VALUES ('1', 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', '1');
INSERT INTO `user_role_1` VALUES ('2', 'dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '2');
INSERT INTO `user_role_1` VALUES ('3', 'dcbd0207-f384-11e8-b25b-54ee75c0f47a', '3');
INSERT INTO `user_role_1` VALUES ('4', 'dcc00d94-f384-11e8-b25b-54ee75c0f47a', '4');
INSERT INTO `user_role_1` VALUES ('5', 'dcc30169-f384-11e8-b25b-54ee75c0f47a', '5');
INSERT INTO `user_role_1` VALUES ('6', 'dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '6');

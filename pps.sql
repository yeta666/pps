

DROP TABLE IF EXISTS bank_account_1;
CREATE TABLE bank_account_1 (
  id varchar(20) NOT NULL COMMENT '科目编号',
  name varchar(20) NOT NULL COMMENT '科目名称',
  type tinyint(4) NOT NULL COMMENT '账户类型，1：现金，2：支付宝，3：微信，4：银行卡',
  opening_money double(10,2) NOT NULL COMMENT '期初金额',
  head varchar(20) DEFAULT NULL COMMENT '户主名',
  account varchar(50) DEFAULT NULL COMMENT '账户',
  gathering tinyint(4) DEFAULT '0' COMMENT '是否用于商城收款，0：否，1：是',
  qrCode varchar(50) DEFAULT NULL COMMENT '收款码',
  procurement tinyint(4) DEFAULT '0' COMMENT '是否用于订货平台，0：否，1：是',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO bank_account_1 VALUES ('1001', '库存现金', '1', '0.00', null, null, '0', null, '0');
INSERT INTO bank_account_1 VALUES ('1002', '支付宝账户', '2', '0.00', null, null, '0', null, '0');
INSERT INTO bank_account_1 VALUES ('1003', '微信账户', '3', '0.00', null, null, '0', null, '0');
INSERT INTO bank_account_1 VALUES ('1004', '银行存款', '4', '0.00', null, null, '0', null, '0');
INSERT INTO bank_account_1 VALUES ('100401', '建设银行', '4', '0.00', null, null, '0', null, '0');
INSERT INTO bank_account_1 VALUES ('100402', '招商银行', '4', '0.00', null, null, '0', null, '0');
INSERT INTO bank_account_1 VALUES ('100403', '华夏银行', '4', '0.00', null, null, '0', null, '0');


DROP TABLE IF EXISTS client;
CREATE TABLE client (
  id varchar(50) NOT NULL COMMENT '客户编号',
  name varchar(10) NOT NULL COMMENT '姓名',
  username varchar(50) NOT NULL COMMENT '用户名',
  password varchar(50) NOT NULL COMMENT '密码',
  phone varchar(20) NOT NULL COMMENT '电话',
  level_id int(11) NOT NULL COMMENT '级别编号',
  birthday datetime DEFAULT NULL COMMENT '生日',
  inviter_id varchar(50) DEFAULT NULL COMMENT '邀请人编号',
  address varchar(100) DEFAULT NULL COMMENT '客户地址',
  postcode varchar(10) DEFAULT NULL COMMENT '邮编',
  membership_number varchar(100) NOT NULL COMMENT '会员卡号',
  last_deal_time datetime DEFAULT NULL COMMENT '最近交易时间',
  create_time datetime NOT NULL COMMENT '创建时间',
  disabled tinyint(4) NOT NULL COMMENT '是否停用，0：否，1：是',
  remark varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY username (username),
  UNIQUE KEY phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS client_discount_coupon;
CREATE TABLE client_discount_coupon (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '客户/优惠券关系编号',
  store_id int(11) NOT NULL COMMENT '店铺编号',
  client_id varchar(50) NOT NULL COMMENT '客户编号',
  discount_coupon_id int(11) NOT NULL COMMENT '优惠券编号',
  quantity int(11) NOT NULL COMMENT '数量',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS client_level;
CREATE TABLE client_level (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '客户级别编号',
  name varchar(20) NOT NULL COMMENT '客户级别',
  price_type tinyint(4) NOT NULL COMMENT '级别价格类型，1：零售价，2：vip售价',
  price decimal(10,2) NOT NULL COMMENT '级别默认价格，级别价格类型*0.几',
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS discount_coupon_1;
CREATE TABLE discount_coupon_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '优惠券编号',
  name varchar(50) NOT NULL COMMENT '名称',
  type tinyint(4) NOT NULL COMMENT '类型，1：现金券，2：折扣券，3：满减券',
  money double(20,2) NOT NULL COMMENT '金额/折扣',
  discount_money double(20,2) NOT NULL COMMENT '满减券与money搭配使用',
  start_time datetime NOT NULL COMMENT '开始时间',
  end_time datetime NOT NULL COMMENT '结束时间',
  use_offline tinyint(4) NOT NULL COMMENT '线下使用，0：否，1：是',
  use_online tinyint(4) NOT NULL COMMENT '线上使用，0：否，1：是',
  quantity int(11) NOT NULL COMMENT '数量',
  given_quantity int(11) NOT NULL COMMENT '已发放数量',
  used_quantity int(11) NOT NULL COMMENT '已使用数量',
  status tinyint(4) NOT NULL COMMENT '状态，0：已作废，1：正常',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS function;
CREATE TABLE function (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '功能id',
  name varchar(10) NOT NULL COMMENT '功能名',
  level tinyint(4) NOT NULL COMMENT '功能级别',
  parnet_id int(11) NOT NULL COMMENT '父功能id',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;


INSERT INTO function VALUES ('1', '销售', '1', '0');
INSERT INTO function VALUES ('2', '采购', '1', '0');
INSERT INTO function VALUES ('3', '仓库', '1', '0');
INSERT INTO function VALUES ('4', '资金', '1', '0');
INSERT INTO function VALUES ('5', '营销', '1', '0');
INSERT INTO function VALUES ('6', 'CRM', '1', '0');
INSERT INTO function VALUES ('7', '报表', '1', '0');
INSERT INTO function VALUES ('8', '资料', '1', '0');
INSERT INTO function VALUES ('9', '设置', '1', '0');
INSERT INTO function VALUES ('10', '批发业务', '2', '1');
INSERT INTO function VALUES ('11', '零售业务', '2', '1');
INSERT INTO function VALUES ('12', '销售结果', '2', '1');
INSERT INTO function VALUES ('13', '采购业务', '2', '2');
INSERT INTO function VALUES ('14', '采购结果', '2', '2');
INSERT INTO function VALUES ('15', '收发货', '2', '3');
INSERT INTO function VALUES ('16', '其他出入库', '2', '3');
INSERT INTO function VALUES ('17', '盘点', '2', '3');
INSERT INTO function VALUES ('18', '预警', '2', '3');
INSERT INTO function VALUES ('19', '收款', '2', '4');
INSERT INTO function VALUES ('20', '付款', '2', '4');
INSERT INTO function VALUES ('21', '往来对账', '2', '4');
INSERT INTO function VALUES ('22', '往来调整', '2', '4');
INSERT INTO function VALUES ('23', '收入支出', '2', '4');
INSERT INTO function VALUES ('24', '营销活动', '2', '5');
INSERT INTO function VALUES ('25', '外勤', '2', '6');
INSERT INTO function VALUES ('26', 'CRM', '2', '6');
INSERT INTO function VALUES ('27', '单据中心', '2', '7');
INSERT INTO function VALUES ('28', '库存报表', '2', '7');
INSERT INTO function VALUES ('29', '资金报表', '2', '7');
INSERT INTO function VALUES ('30', '订单统计', '2', '7');
INSERT INTO function VALUES ('31', '销售报表', '2', '7');
INSERT INTO function VALUES ('32', '采购报表', '2', '7');
INSERT INTO function VALUES ('33', '经营中心', '2', '7');
INSERT INTO function VALUES ('34', '商品管理', '2', '8');
INSERT INTO function VALUES ('35', '往来单位', '2', '8');
INSERT INTO function VALUES ('36', '机构管理', '2', '8');
INSERT INTO function VALUES ('37', '财务账户', '2', '8');
INSERT INTO function VALUES ('38', '岗位权限', '2', '8');
INSERT INTO function VALUES ('39', '系统配置', '2', '9');
INSERT INTO function VALUES ('40', '期初录入', '2', '9');
INSERT INTO function VALUES ('41', '账套操作', '2', '9');
INSERT INTO function VALUES ('42', '批发业务', '2', '1');
INSERT INTO function VALUES ('43', '零售业务', '2', '1');
INSERT INTO function VALUES ('44', '销售结果', '2', '1');
INSERT INTO function VALUES ('45', '采购业务', '2', '2');
INSERT INTO function VALUES ('46', '采购结果', '2', '2');
INSERT INTO function VALUES ('47', '收发货', '2', '3');
INSERT INTO function VALUES ('48', '其他出入库', '2', '3');
INSERT INTO function VALUES ('49', '盘点', '2', '3');
INSERT INTO function VALUES ('50', '预警', '2', '3');
INSERT INTO function VALUES ('51', '收款', '2', '4');
INSERT INTO function VALUES ('52', '付款', '2', '4');
INSERT INTO function VALUES ('53', '往来对账', '2', '4');
INSERT INTO function VALUES ('54', '往来调整', '2', '4');
INSERT INTO function VALUES ('55', '收入支出', '2', '4');
INSERT INTO function VALUES ('56', '营销活动', '2', '5');
INSERT INTO function VALUES ('57', '外勤', '2', '6');
INSERT INTO function VALUES ('58', 'CRM', '2', '6');
INSERT INTO function VALUES ('59', '单据中心', '2', '7');
INSERT INTO function VALUES ('60', '库存报表', '2', '7');
INSERT INTO function VALUES ('61', '资金报表', '2', '7');
INSERT INTO function VALUES ('62', '订单统计', '2', '7');
INSERT INTO function VALUES ('63', '销售报表', '2', '7');
INSERT INTO function VALUES ('64', '采购报表', '2', '7');
INSERT INTO function VALUES ('65', '经营中心', '2', '7');
INSERT INTO function VALUES ('66', '商品管理', '2', '8');
INSERT INTO function VALUES ('67', '往来单位', '2', '8');
INSERT INTO function VALUES ('68', '机构管理', '2', '8');
INSERT INTO function VALUES ('69', '财务账户', '2', '8');
INSERT INTO function VALUES ('70', '岗位权限', '2', '8');
INSERT INTO function VALUES ('71', '系统配置', '2', '9');
INSERT INTO function VALUES ('72', '期初录入', '2', '9');
INSERT INTO function VALUES ('73', '账套操作', '2', '9');
INSERT INTO function VALUES ('74', '销售订单', '3', '10');
INSERT INTO function VALUES ('75', '销售退货申请', '3', '10');
INSERT INTO function VALUES ('76', '销售换货申请', '3', '10');
INSERT INTO function VALUES ('77', '零售单', '3', '11');
INSERT INTO function VALUES ('78', '零售交班历史', '3', '11');
INSERT INTO function VALUES ('79', '销售历史', '3', '12');
INSERT INTO function VALUES ('80', '采购订单', '3', '13');
INSERT INTO function VALUES ('81', '采购退货申请', '3', '13');
INSERT INTO function VALUES ('82', '采购换货申请', '3', '13');
INSERT INTO function VALUES ('83', '采购历史', '3', '14');
INSERT INTO function VALUES ('84', '待发货', '3', '15');
INSERT INTO function VALUES ('85', '待收货', '3', '15');
INSERT INTO function VALUES ('86', '其他入库单', '3', '16');
INSERT INTO function VALUES ('87', '其他出库单', '3', '16');
INSERT INTO function VALUES ('88', '其他出入库历史', '3', '16');
INSERT INTO function VALUES ('89', '报损单', '3', '17');
INSERT INTO function VALUES ('90', '报溢单', '3', '17');
INSERT INTO function VALUES ('91', '库存盘点单', '3', '17');
INSERT INTO function VALUES ('92', '成本调价单', '3', '17');
INSERT INTO function VALUES ('93', '库存预警设置', '3', '18');
INSERT INTO function VALUES ('94', '库存预警查询', '3', '18');
INSERT INTO function VALUES ('95', '近效期设置', '3', '18');
INSERT INTO function VALUES ('96', '近效期查询', '3', '18');
INSERT INTO function VALUES ('97', '按单收款', '3', '19');
INSERT INTO function VALUES ('98', '收款单', '3', '19');
INSERT INTO function VALUES ('99', '预收款单', '3', '19');
INSERT INTO function VALUES ('100', '待确认款项', '3', '19');
INSERT INTO function VALUES ('101', '收款统计', '3', '19');
INSERT INTO function VALUES ('102', '按单付款', '3', '20');
INSERT INTO function VALUES ('103', '付款单', '3', '20');
INSERT INTO function VALUES ('104', '预付款单', '3', '20');
INSERT INTO function VALUES ('105', '付款统计', '3', '20');
INSERT INTO function VALUES ('106', '查应收', '3', '21');
INSERT INTO function VALUES ('107', '职员部门应收款', '3', '21');
INSERT INTO function VALUES ('108', '超期应收查询', '3', '21');
INSERT INTO function VALUES ('109', '查应付', '3', '21');
INSERT INTO function VALUES ('110', '应收应付调整', '3', '22');
INSERT INTO function VALUES ('111', '往来清账', '3', '22');
INSERT INTO function VALUES ('112', '应收应付调整', '3', '23');
INSERT INTO function VALUES ('113', '其他收入', '3', '23');
INSERT INTO function VALUES ('114', '发短信', '3', '24');
INSERT INTO function VALUES ('115', '优惠券', '3', '24');
INSERT INTO function VALUES ('116', '促销管理', '3', '24');
INSERT INTO function VALUES ('117', '客户分布', '3', '25');
INSERT INTO function VALUES ('118', '客户管理', '3', '26');
INSERT INTO function VALUES ('119', '客户跟进', '3', '26');
INSERT INTO function VALUES ('120', '合同', '3', '26');
INSERT INTO function VALUES ('121', '待记账单据', '3', '27');
INSERT INTO function VALUES ('122', '业务草稿', '3', '27');
INSERT INTO function VALUES ('123', '查库存', '3', '28');
INSERT INTO function VALUES ('124', '进销存分析', '3', '28');
INSERT INTO function VALUES ('125', '出入库明细', '3', '28');
INSERT INTO function VALUES ('126', '查应收', '3', '29');
INSERT INTO function VALUES ('127', '查应付', '3', '29');
INSERT INTO function VALUES ('128', '查资金', '3', '29');
INSERT INTO function VALUES ('129', '查回款', '3', '29');
INSERT INTO function VALUES ('130', '查费用', '3', '29');
INSERT INTO function VALUES ('131', '订单分析', '3', '30');
INSERT INTO function VALUES ('132', '商品订货分析', '3', '30');
INSERT INTO function VALUES ('133', '客户订货分析', '3', '30');
INSERT INTO function VALUES ('134', '销售日月年报', '3', '31');
INSERT INTO function VALUES ('135', '商品销售分析', '3', '31');
INSERT INTO function VALUES ('136', '客户销售分析', '3', '31');
INSERT INTO function VALUES ('137', '业绩统计', '3', '31');
INSERT INTO function VALUES ('138', '回款统计', '3', '31');
INSERT INTO function VALUES ('139', '提成设置', '3', '31');
INSERT INTO function VALUES ('140', '提成统计', '3', '31');
INSERT INTO function VALUES ('141', '商品采购分析', '3', '32');
INSERT INTO function VALUES ('142', '供应商采购分析', '3', '32');
INSERT INTO function VALUES ('143', '采购订单分析', '3', '32');
INSERT INTO function VALUES ('144', '销售经营分析', '3', '33');
INSERT INTO function VALUES ('145', '资金经营分析', '3', '33');
INSERT INTO function VALUES ('146', '往来经营分析', '3', '33');
INSERT INTO function VALUES ('147', '库存经营分析', '3', '33');
INSERT INTO function VALUES ('148', '利润经营分析', '3', '33');
INSERT INTO function VALUES ('149', '老板中心', '3', '33');
INSERT INTO function VALUES ('150', '商品', '3', '34');
INSERT INTO function VALUES ('151', '商品SKU及条码', '3', '34');
INSERT INTO function VALUES ('152', '商品价格管理', '3', '34');
INSERT INTO function VALUES ('153', '商品辅助资料', '3', '34');
INSERT INTO function VALUES ('154', '图片管理', '3', '34');
INSERT INTO function VALUES ('155', '客户', '3', '35');
INSERT INTO function VALUES ('156', '供应商', '3', '35');
INSERT INTO function VALUES ('157', '仓库信息', '3', '36');
INSERT INTO function VALUES ('158', '职员部门', '3', '36');
INSERT INTO function VALUES ('159', '银行账户', '3', '37');
INSERT INTO function VALUES ('160', '费用类型', '3', '37');
INSERT INTO function VALUES ('161', '其他收入', '3', '37');
INSERT INTO function VALUES ('162', '费用单', '3', '38');
INSERT INTO function VALUES ('163', '全部操作员', '3', '38');
INSERT INTO function VALUES ('164', '系统参数', '3', '39');
INSERT INTO function VALUES ('165', '审核设置', '3', '39');
INSERT INTO function VALUES ('166', '支付配置', '3', '39');
INSERT INTO function VALUES ('167', '企业信息', '3', '39');
INSERT INTO function VALUES ('168', '库存期初', '3', '40');
INSERT INTO function VALUES ('169', '现金银行期初', '3', '40');
INSERT INTO function VALUES ('170', '应收期初', '3', '40');
INSERT INTO function VALUES ('171', '应付期初', '3', '40');
INSERT INTO function VALUES ('172', '系统开账', '3', '41');
INSERT INTO function VALUES ('173', '系统重建', '3', '41');
INSERT INTO function VALUES ('174', '操作日志', '3', '41');


DROP TABLE IF EXISTS fund_check_order_1;
CREATE TABLE fund_check_order_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '资金对账记录编号',
  order_id varchar(50) NOT NULL COMMENT '单据编号',
  create_time datetime NOT NULL COMMENT '创建时间',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  target_id varchar(50) DEFAULT NULL COMMENT '往来单位编号',
  bank_account_id varchar(20) NOT NULL COMMENT '银行账户编号',
  in_money double(10,2) NOT NULL COMMENT '收入金额',
  out_money double(10,2) NOT NULL COMMENT '支出金额',
  balance_money double(10,2) NOT NULL COMMENT '当前余额',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS fund_order_1;
CREATE TABLE fund_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：收款单，2：付款单，3：预收款单，4：预付款单',
  create_time datetime NOT NULL COMMENT '单据日期',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  order_id varchar(50) DEFAULT NULL COMMENT '结算单据编号',
  target_id varchar(50) NOT NULL COMMENT '往来单位',
  money double(10,2) NOT NULL COMMENT '金额',
  discount_money double(10,2) NOT NULL COMMENT '优惠金额',
  bank_account_id varchar(20) NOT NULL COMMENT '银行账户编号',
  advance_money double(10,2) NOT NULL COMMENT '使用预收/付款',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS fund_result_order_1;
CREATE TABLE fund_result_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：其他收入单，2：费用单',
  create_time datetime NOT NULL COMMENT '单据日期',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  target_id varchar(50) DEFAULT NULL COMMENT '往来单位编号',
  bank_account_id varchar(20) NOT NULL COMMENT '账户编号',
  money double(10,2) NOT NULL COMMENT '金额',
  income_expenses_id varchar(20) NOT NULL COMMENT '收入/支出费用编号',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS fund_target_check_order_1;
CREATE TABLE fund_target_check_order_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '往来对账记录编号',
  order_id varchar(50) NOT NULL COMMENT '单据编号',
  create_time datetime NOT NULL COMMENT '创建时间',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  target_id varchar(50) DEFAULT NULL COMMENT '往来单位编号',
  need_in_money_increase double(10,2) NOT NULL COMMENT '应收增加',
  need_in_money_decrease double(10,2) NOT NULL COMMENT '应收减少',
  need_in_money double(10,2) NOT NULL COMMENT '期末应收',
  advance_in_money_increase double(10,2) NOT NULL COMMENT '预收增加',
  advance_in_money_decrease double(10,2) NOT NULL COMMENT '预收增加',
  advance_in_money double(10,2) NOT NULL COMMENT '期末预收',
  need_out_money_increase double(10,2) NOT NULL COMMENT '应付增加',
  need_out_money_decrease double(10,2) NOT NULL COMMENT '应付减少',
  need_out_money double(10,2) NOT NULL COMMENT '期末应付',
  advance_out_money_increase double(10,2) NOT NULL COMMENT '预付增加',
  advance_out_money_decrease double(10,2) NOT NULL COMMENT '预付减少',
  advance_out_money double(10,2) NOT NULL COMMENT '期末预付',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_1;
CREATE TABLE goods_1 (
  id varchar(50) NOT NULL COMMENT '商品货号',
  name varchar(200) NOT NULL COMMENT '商品名',
  bar_code varchar(50) NOT NULL COMMENT '条码',
  type_id int(11) NOT NULL COMMENT '分类id',
  putaway tinyint(4) NOT NULL COMMENT '上架状态，0：未上架，1：已上架',
  skus mediumtext COMMENT '商品规格',
  origin varchar(200) DEFAULT NULL COMMENT '产地',
  image varchar(200) DEFAULT NULL COMMENT '图片',
  remark varchar(200) DEFAULT NULL COMMENT '备注',
  create_time datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_goods_label_1;
CREATE TABLE goods_goods_label_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品/商品标签关系id',
  goods_id varchar(50) NOT NULL COMMENT '商品货号',
  label_id int(11) NOT NULL COMMENT '商品标签id',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_label_1;
CREATE TABLE goods_label_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品标签id',
  name varchar(20) NOT NULL COMMENT '商品标签名',
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_property_key_1;
CREATE TABLE goods_property_key_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性名id',
  name varchar(20) NOT NULL COMMENT '商品属性名',
  type_id int(11) NOT NULL COMMENT '商品分类id',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_property_value_1;
CREATE TABLE goods_property_value_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品属性值id',
  name varchar(20) NOT NULL COMMENT '商品属性值',
  property_key_id int(11) NOT NULL COMMENT '商品属性名id',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_sku_1;
CREATE TABLE goods_sku_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品规格编号',
  goods_id varchar(50) NOT NULL COMMENT '商品货号',
  sku mediumtext NOT NULL COMMENT '商品规格',
  purchase_price double(10,2) NOT NULL COMMENT '进价',
  retail_price double(10,2) NOT NULL COMMENT '零售价',
  vip_price double(10,2) NOT NULL COMMENT 'vip售价',
  integral int(11) NOT NULL COMMENT '积分',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS goods_type_1;
CREATE TABLE goods_type_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '商品分类id',
  name varchar(20) NOT NULL COMMENT '商品分类名',
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS income_expenses_1;
CREATE TABLE income_expenses_1 (
  id varchar(20) NOT NULL COMMENT '科目编号',
  name varchar(20) NOT NULL COMMENT '科目名称',
  check_item tinyint(4) DEFAULT NULL COMMENT '核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门',
  debit_credit tinyint(4) NOT NULL COMMENT '借贷，1：贷，2：借',
  type tinyint(4) NOT NULL COMMENT '收支，1：收入，2：支出',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO income_expenses_1 VALUES ('6052', '其他收入', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('605201', '利息收入', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('605202', '损赠收入', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('605203', '租赁收入', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('605204', '罚没收入', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('6111', '投资收益', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('6301', '营业外收入', null, '1', '1');
INSERT INTO income_expenses_1 VALUES ('6403', '营业税金及附加', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6601', '销售费用', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660101', '优惠金额', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660102', '包装费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660103', '运输费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660104', '广告费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660105', '装卸费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660106', '保险费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660107', '展览费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660108', '租赁费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660109', '销售服务费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660110', '差旅费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660111', '办公费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660112', '折旧费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660113', '修理费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660114', '网店运费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660115', '快递费', '2', '2', '2');
INSERT INTO income_expenses_1 VALUES ('660116', '招待费', '2', '2', '2');
INSERT INTO income_expenses_1 VALUES ('660117', '水电气费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660118', '积分提现', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660119', '提成提现', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6602', '管理费用', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660202', '工会经费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660203', '职工教育经费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660204', '业务招待费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660205', '技术转让费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660206', '无形资产摊销', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660207', '咨询费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660208', '诉讼费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660209', '坏账损失', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660210', '工资', '4', '2', '2');
INSERT INTO income_expenses_1 VALUES ('660211', '房租', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660212', '水电气', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6603', '财务费用', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660302', '汇兑净损失', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('660303', '金融机构手续费', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6701', '资产减值损失', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6711', '营业外支出', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6801', '所得税费用', null, '2', '2');
INSERT INTO income_expenses_1 VALUES ('6901', '以前年度损益调整', null, '2', '2');


DROP TABLE IF EXISTS membership_number;
CREATE TABLE membership_number (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '会员卡号编号',
  number varchar(100) NOT NULL COMMENT '会员卡号',
  disabled tinyint(4) NOT NULL COMMENT '是否停用，0：否，1：是',
  PRIMARY KEY (id),
  UNIQUE KEY number (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS order_goods_sku_1;
CREATE TABLE order_goods_sku_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '订单/商品规格关系编号',
  type tinyint(4) DEFAULT NULL COMMENT '类型，0：出库，1：入库',
  order_id varchar(50) NOT NULL COMMENT '订单编号',
  goods_sku_id int(11) NOT NULL COMMENT '商品规格编号',
  quantity int(11) DEFAULT NULL COMMENT '总数量',
  finish_quantity int(11) DEFAULT NULL COMMENT '完成数量',
  not_finish_quantity int(11) DEFAULT NULL COMMENT '未完成数量',
  money double(10,2) DEFAULT NULL COMMENT '金额',
  discount_money double(10,2) DEFAULT NULL COMMENT '优惠金额',
  operated_quantity int(11) DEFAULT NULL COMMENT '已操作数量',
  check_quantity int(11) DEFAULT NULL COMMENT '结存数量/盘点数量',
  check_money double(10,2) DEFAULT NULL COMMENT '结存成本单价',
  check_total_money double(10,2) DEFAULT NULL COMMENT '结存金额',
  after_change_check_money double(10,2) DEFAULT NULL COMMENT '调整后成本单价',
  change_check_total_money double(10,2) DEFAULT NULL COMMENT '调整金额',
  in_quantity int(11) DEFAULT NULL COMMENT '盘盈数量',
  in_money double(10,2) DEFAULT NULL COMMENT '盘盈金额',
  out_quantity int(11) DEFAULT NULL COMMENT '盘亏数量',
  out_money double(10,2) DEFAULT NULL COMMENT '盘亏金额',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS procurement_apply_order_1;
CREATE TABLE procurement_apply_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：采购订单，2：采购退货申请单，3：采购换货申请单',
  create_time datetime NOT NULL COMMENT '单据日期',
  result_order_id varchar(50) DEFAULT NULL COMMENT '来源订单，采购退货申请单和采购换货申请单应该有该字段',
  order_status tinyint(4) NOT NULL COMMENT '单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发',
  clear_status tinyint(4) NOT NULL COMMENT '结算状态：0：未完成，1：已完成',
  supplier_id varchar(20) NOT NULL COMMENT '供应商编号',
  in_warehouse_id int(11) DEFAULT NULL COMMENT '入库仓库编号，对应收货',
  in_total_quantity int(11) DEFAULT NULL COMMENT '总收货数量',
  in_received_quantity int(11) DEFAULT NULL COMMENT '已收货数量',
  in_not_received_quantity int(11) DEFAULT NULL COMMENT '未收货数量',
  out_warehouse_id int(11) DEFAULT NULL COMMENT '出库仓库编号，对应发货',
  out_total_quantity int(11) DEFAULT NULL COMMENT '总发货数量',
  out_sent_quantity int(11) DEFAULT NULL COMMENT '已发货数量',
  out_not_sent_quantity int(11) DEFAULT NULL COMMENT '未发货数量',
  total_money double(10,2) NOT NULL COMMENT '总商品金额',
  total_discount_money double(10,2) NOT NULL COMMENT '总优惠金额',
  order_money double(10,2) NOT NULL COMMENT '本单金额',
  cleared_money double(10,2) NOT NULL COMMENT '已结算金额',
  not_cleared_money double(10,2) NOT NULL COMMENT '未结算金额',
  user_id varchar(50) NOT NULL COMMENT '经手人编号',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS procurement_result_order_1;
CREATE TABLE procurement_result_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：采购入库单，2：采购退货单，3：采购换货单',
  create_time datetime NOT NULL COMMENT '单据日期',
  apply_order_id varchar(50) NOT NULL COMMENT '来源订单',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  total_quantity int(11) NOT NULL COMMENT '总商品数量',
  total_money double(10,2) NOT NULL COMMENT '总商品金额',
  total_discount_money double(10,2) NOT NULL COMMENT '总优惠金额',
  order_money double(10,2) NOT NULL COMMENT '本单金额',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS role_1;
CREATE TABLE role_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  name varchar(10) NOT NULL COMMENT '角色名',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


INSERT INTO role_1 VALUES ('1', '老板');


DROP TABLE IF EXISTS role_function_1;
CREATE TABLE role_function_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  role_id int(11) NOT NULL COMMENT '角色id',
  function_id int(11) NOT NULL COMMENT '功能id',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;


INSERT INTO role_function_1 VALUES ('1', '1', '1');
INSERT INTO role_function_1 VALUES ('2', '1', '2');
INSERT INTO role_function_1 VALUES ('3', '1', '3');
INSERT INTO role_function_1 VALUES ('4', '1', '4');
INSERT INTO role_function_1 VALUES ('5', '1', '5');
INSERT INTO role_function_1 VALUES ('6', '1', '6');
INSERT INTO role_function_1 VALUES ('7', '1', '7');
INSERT INTO role_function_1 VALUES ('8', '1', '8');
INSERT INTO role_function_1 VALUES ('9', '1', '9');
INSERT INTO role_function_1 VALUES ('10', '1', '10');
INSERT INTO role_function_1 VALUES ('11', '1', '11');
INSERT INTO role_function_1 VALUES ('12', '1', '12');
INSERT INTO role_function_1 VALUES ('13', '1', '13');
INSERT INTO role_function_1 VALUES ('14', '1', '14');
INSERT INTO role_function_1 VALUES ('15', '1', '15');
INSERT INTO role_function_1 VALUES ('16', '1', '16');
INSERT INTO role_function_1 VALUES ('17', '1', '17');
INSERT INTO role_function_1 VALUES ('18', '1', '18');
INSERT INTO role_function_1 VALUES ('19', '1', '19');
INSERT INTO role_function_1 VALUES ('20', '1', '20');
INSERT INTO role_function_1 VALUES ('21', '1', '21');
INSERT INTO role_function_1 VALUES ('22', '1', '22');
INSERT INTO role_function_1 VALUES ('23', '1', '23');
INSERT INTO role_function_1 VALUES ('24', '1', '24');
INSERT INTO role_function_1 VALUES ('25', '1', '25');
INSERT INTO role_function_1 VALUES ('26', '1', '26');
INSERT INTO role_function_1 VALUES ('27', '1', '27');
INSERT INTO role_function_1 VALUES ('28', '1', '28');
INSERT INTO role_function_1 VALUES ('29', '1', '29');
INSERT INTO role_function_1 VALUES ('30', '1', '30');
INSERT INTO role_function_1 VALUES ('31', '1', '31');
INSERT INTO role_function_1 VALUES ('32', '1', '32');
INSERT INTO role_function_1 VALUES ('33', '1', '33');
INSERT INTO role_function_1 VALUES ('34', '1', '34');
INSERT INTO role_function_1 VALUES ('35', '1', '35');
INSERT INTO role_function_1 VALUES ('36', '1', '36');
INSERT INTO role_function_1 VALUES ('37', '1', '37');
INSERT INTO role_function_1 VALUES ('38', '1', '38');
INSERT INTO role_function_1 VALUES ('39', '1', '39');
INSERT INTO role_function_1 VALUES ('40', '1', '40');
INSERT INTO role_function_1 VALUES ('41', '1', '41');
INSERT INTO role_function_1 VALUES ('42', '1', '42');
INSERT INTO role_function_1 VALUES ('43', '1', '43');
INSERT INTO role_function_1 VALUES ('44', '1', '44');
INSERT INTO role_function_1 VALUES ('45', '1', '45');
INSERT INTO role_function_1 VALUES ('46', '1', '46');
INSERT INTO role_function_1 VALUES ('47', '1', '47');
INSERT INTO role_function_1 VALUES ('48', '1', '48');
INSERT INTO role_function_1 VALUES ('49', '1', '49');
INSERT INTO role_function_1 VALUES ('50', '1', '50');
INSERT INTO role_function_1 VALUES ('51', '1', '51');
INSERT INTO role_function_1 VALUES ('52', '1', '52');
INSERT INTO role_function_1 VALUES ('53', '1', '53');
INSERT INTO role_function_1 VALUES ('54', '1', '54');
INSERT INTO role_function_1 VALUES ('55', '1', '55');
INSERT INTO role_function_1 VALUES ('56', '1', '56');
INSERT INTO role_function_1 VALUES ('57', '1', '57');
INSERT INTO role_function_1 VALUES ('58', '1', '58');
INSERT INTO role_function_1 VALUES ('59', '1', '59');
INSERT INTO role_function_1 VALUES ('60', '1', '60');
INSERT INTO role_function_1 VALUES ('61', '1', '61');
INSERT INTO role_function_1 VALUES ('62', '1', '62');
INSERT INTO role_function_1 VALUES ('63', '1', '63');
INSERT INTO role_function_1 VALUES ('64', '1', '64');
INSERT INTO role_function_1 VALUES ('65', '1', '65');
INSERT INTO role_function_1 VALUES ('66', '1', '66');
INSERT INTO role_function_1 VALUES ('67', '1', '67');
INSERT INTO role_function_1 VALUES ('68', '1', '68');
INSERT INTO role_function_1 VALUES ('69', '1', '69');
INSERT INTO role_function_1 VALUES ('70', '1', '70');
INSERT INTO role_function_1 VALUES ('71', '1', '71');
INSERT INTO role_function_1 VALUES ('72', '1', '72');
INSERT INTO role_function_1 VALUES ('73', '1', '73');
INSERT INTO role_function_1 VALUES ('74', '1', '74');
INSERT INTO role_function_1 VALUES ('75', '1', '75');
INSERT INTO role_function_1 VALUES ('76', '1', '76');
INSERT INTO role_function_1 VALUES ('77', '1', '77');
INSERT INTO role_function_1 VALUES ('78', '1', '78');
INSERT INTO role_function_1 VALUES ('79', '1', '79');
INSERT INTO role_function_1 VALUES ('80', '1', '80');
INSERT INTO role_function_1 VALUES ('81', '1', '81');
INSERT INTO role_function_1 VALUES ('82', '1', '82');
INSERT INTO role_function_1 VALUES ('83', '1', '83');
INSERT INTO role_function_1 VALUES ('84', '1', '84');
INSERT INTO role_function_1 VALUES ('85', '1', '85');
INSERT INTO role_function_1 VALUES ('86', '1', '86');
INSERT INTO role_function_1 VALUES ('87', '1', '87');
INSERT INTO role_function_1 VALUES ('88', '1', '88');
INSERT INTO role_function_1 VALUES ('89', '1', '89');
INSERT INTO role_function_1 VALUES ('90', '1', '90');
INSERT INTO role_function_1 VALUES ('91', '1', '91');
INSERT INTO role_function_1 VALUES ('92', '1', '92');
INSERT INTO role_function_1 VALUES ('93', '1', '93');
INSERT INTO role_function_1 VALUES ('94', '1', '94');
INSERT INTO role_function_1 VALUES ('95', '1', '95');
INSERT INTO role_function_1 VALUES ('96', '1', '96');
INSERT INTO role_function_1 VALUES ('97', '1', '97');
INSERT INTO role_function_1 VALUES ('98', '1', '98');
INSERT INTO role_function_1 VALUES ('99', '1', '99');
INSERT INTO role_function_1 VALUES ('100', '1', '100');
INSERT INTO role_function_1 VALUES ('101', '1', '101');
INSERT INTO role_function_1 VALUES ('102', '1', '102');
INSERT INTO role_function_1 VALUES ('103', '1', '103');
INSERT INTO role_function_1 VALUES ('104', '1', '104');
INSERT INTO role_function_1 VALUES ('105', '1', '105');
INSERT INTO role_function_1 VALUES ('106', '1', '106');
INSERT INTO role_function_1 VALUES ('107', '1', '107');
INSERT INTO role_function_1 VALUES ('108', '1', '108');
INSERT INTO role_function_1 VALUES ('109', '1', '109');
INSERT INTO role_function_1 VALUES ('110', '1', '110');
INSERT INTO role_function_1 VALUES ('111', '1', '111');
INSERT INTO role_function_1 VALUES ('112', '1', '112');
INSERT INTO role_function_1 VALUES ('113', '1', '113');
INSERT INTO role_function_1 VALUES ('114', '1', '114');
INSERT INTO role_function_1 VALUES ('115', '1', '115');
INSERT INTO role_function_1 VALUES ('116', '1', '116');
INSERT INTO role_function_1 VALUES ('117', '1', '117');
INSERT INTO role_function_1 VALUES ('118', '1', '118');
INSERT INTO role_function_1 VALUES ('119', '1', '119');
INSERT INTO role_function_1 VALUES ('120', '1', '120');
INSERT INTO role_function_1 VALUES ('121', '1', '121');
INSERT INTO role_function_1 VALUES ('122', '1', '122');
INSERT INTO role_function_1 VALUES ('123', '1', '123');
INSERT INTO role_function_1 VALUES ('124', '1', '124');
INSERT INTO role_function_1 VALUES ('125', '1', '125');
INSERT INTO role_function_1 VALUES ('126', '1', '126');
INSERT INTO role_function_1 VALUES ('127', '1', '127');
INSERT INTO role_function_1 VALUES ('128', '1', '128');
INSERT INTO role_function_1 VALUES ('129', '1', '129');
INSERT INTO role_function_1 VALUES ('130', '1', '130');
INSERT INTO role_function_1 VALUES ('131', '1', '131');
INSERT INTO role_function_1 VALUES ('132', '1', '132');
INSERT INTO role_function_1 VALUES ('133', '1', '133');
INSERT INTO role_function_1 VALUES ('134', '1', '134');
INSERT INTO role_function_1 VALUES ('135', '1', '135');
INSERT INTO role_function_1 VALUES ('136', '1', '136');
INSERT INTO role_function_1 VALUES ('137', '1', '137');
INSERT INTO role_function_1 VALUES ('138', '1', '138');
INSERT INTO role_function_1 VALUES ('139', '1', '139');
INSERT INTO role_function_1 VALUES ('140', '1', '140');
INSERT INTO role_function_1 VALUES ('141', '1', '141');
INSERT INTO role_function_1 VALUES ('142', '1', '142');
INSERT INTO role_function_1 VALUES ('143', '1', '143');
INSERT INTO role_function_1 VALUES ('144', '1', '144');
INSERT INTO role_function_1 VALUES ('145', '1', '145');
INSERT INTO role_function_1 VALUES ('146', '1', '146');
INSERT INTO role_function_1 VALUES ('147', '1', '147');
INSERT INTO role_function_1 VALUES ('148', '1', '148');
INSERT INTO role_function_1 VALUES ('149', '1', '149');
INSERT INTO role_function_1 VALUES ('150', '1', '150');
INSERT INTO role_function_1 VALUES ('151', '1', '151');
INSERT INTO role_function_1 VALUES ('152', '1', '152');
INSERT INTO role_function_1 VALUES ('153', '1', '153');
INSERT INTO role_function_1 VALUES ('154', '1', '154');
INSERT INTO role_function_1 VALUES ('155', '1', '155');
INSERT INTO role_function_1 VALUES ('156', '1', '156');
INSERT INTO role_function_1 VALUES ('157', '1', '157');
INSERT INTO role_function_1 VALUES ('158', '1', '158');
INSERT INTO role_function_1 VALUES ('159', '1', '159');
INSERT INTO role_function_1 VALUES ('160', '1', '160');
INSERT INTO role_function_1 VALUES ('161', '1', '161');
INSERT INTO role_function_1 VALUES ('162', '1', '162');
INSERT INTO role_function_1 VALUES ('163', '1', '163');
INSERT INTO role_function_1 VALUES ('164', '1', '164');
INSERT INTO role_function_1 VALUES ('165', '1', '165');
INSERT INTO role_function_1 VALUES ('166', '1', '166');
INSERT INTO role_function_1 VALUES ('167', '1', '167');
INSERT INTO role_function_1 VALUES ('168', '1', '168');
INSERT INTO role_function_1 VALUES ('169', '1', '169');
INSERT INTO role_function_1 VALUES ('170', '1', '170');
INSERT INTO role_function_1 VALUES ('171', '1', '171');
INSERT INTO role_function_1 VALUES ('172', '1', '172');
INSERT INTO role_function_1 VALUES ('173', '1', '173');
INSERT INTO role_function_1 VALUES ('174', '1', '174');


DROP TABLE IF EXISTS sell_apply_order_1;
CREATE TABLE sell_apply_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单',
  create_time datetime NOT NULL COMMENT '单据日期',
  result_order_id varchar(50) DEFAULT NULL COMMENT '来源订单，销售退货申请单和销售换货申请单应该有该字段',
  prodcing_way tinyint(4) NOT NULL COMMENT '产生方式，1：线下录单，2：线上下单',
  order_status tinyint(4) NOT NULL COMMENT '单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发',
  clear_status tinyint(4) NOT NULL COMMENT '结算状态：0：未完成，1：已完成',
  client_id varchar(50) DEFAULT NULL COMMENT '客户编号',
  in_warehouse_id int(11) DEFAULT NULL COMMENT '入库仓库编号，对应收货',
  in_total_quantity int(11) DEFAULT NULL COMMENT '总收货数量',
  in_received_quantity int(11) DEFAULT NULL COMMENT '已收货数量',
  in_not_received_quantity int(11) DEFAULT NULL COMMENT '未收货数量',
  out_warehouse_id int(11) DEFAULT NULL COMMENT '出库仓库编号，对应发货',
  out_total_quantity int(11) DEFAULT NULL COMMENT '总发货数量',
  out_sent_quantity int(11) DEFAULT NULL COMMENT '已发货数量',
  out_not_sent_quantity int(11) DEFAULT NULL COMMENT '未发货数量',
  total_money double(10,2) NOT NULL COMMENT '总商品金额',
  discount_money double(10,2) NOT NULL COMMENT '直接优惠金额',
  discount_coupon_id int(11) DEFAULT NULL COMMENT '优惠券编号',
  total_discount_money double(10,2) NOT NULL COMMENT '总优惠金额',
  order_money double(10,2) NOT NULL COMMENT '本单金额',
  cleared_money double(10,2) NOT NULL COMMENT '已结算金额',
  not_cleared_money double(10,2) NOT NULL COMMENT '未结算金额',
  user_id varchar(50) NOT NULL COMMENT '经手人编号',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS sell_result_order_1;
CREATE TABLE sell_result_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单',
  create_time datetime NOT NULL COMMENT '单据日期',
  apply_order_id varchar(50) NOT NULL COMMENT '来源订单',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  total_quantity int(11) NOT NULL COMMENT '总商品数量',
  total_money double(10,2) NOT NULL COMMENT '总商品金额',
  total_discount_money double(10,2) NOT NULL COMMENT '总优惠金额',
  order_money double(10,2) NOT NULL COMMENT '本单金额',
  cost_money double(10,2) NOT NULL COMMENT '成本',
  gross_margin_money double(10,2) NOT NULL COMMENT '毛利',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS storage_check_order_1;
CREATE TABLE storage_check_order_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '库存对账记录编号',
  order_id varchar(50) NOT NULL COMMENT '单据编号',
  create_time datetime NOT NULL COMMENT '创建时间',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  target_id varchar(50) DEFAULT NULL COMMENT '往来单位编号',
  goods_id varchar(50) NOT NULL COMMENT '商品货号',
  goods_sku_id int(11) NOT NULL COMMENT '商品规格编号',
  warehouse_id int(11) NOT NULL COMMENT '仓库编号',
  in_quantity int(11) NOT NULL COMMENT '入库数量',
  in_money double(10,2) NOT NULL COMMENT '入库成本单价',
  in_total_money double(10,2) NOT NULL COMMENT '入库成本金额',
  out_quantity int(11) NOT NULL COMMENT '出库数量',
  out_money double(10,2) NOT NULL COMMENT '出库成本单价',
  out_total_money double(10,2) NOT NULL COMMENT '出库成本金额',
  check_quantity int(11) NOT NULL COMMENT '商品规格-仓库结存数量',
  check_money double(10,2) NOT NULL COMMENT '商品规格-仓库结存成本单价',
  check_total_money double(10,2) NOT NULL COMMENT '商品规格-仓库结存成本金额',
  check_quantity1 int(11) NOT NULL COMMENT '商品规格结存数量',
  check_money1 double(10,2) NOT NULL COMMENT '商品规格结存成本单价',
  check_total_money1 double(10,2) NOT NULL COMMENT '商品规格结存成本金额',
  check_quantity2 int(11) NOT NULL COMMENT '商品结存数量',
  check_money2 double(10,2) NOT NULL COMMENT '商品结存成本单价',
  check_total_money2 double(10,2) NOT NULL COMMENT '商品结存成本金额',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS storage_order_1;
CREATE TABLE storage_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单',
  create_time datetime NOT NULL COMMENT '单据日期',
  apply_order_id varchar(50) NOT NULL COMMENT '来源订单',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  quantity int(11) NOT NULL COMMENT '数量',
  logistics_company varchar(50) DEFAULT NULL COMMENT '物流公司',
  waybill_number varchar(50) DEFAULT NULL COMMENT '运单号',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS storage_result_order_1;
CREATE TABLE storage_result_order_1 (
  id varchar(50) NOT NULL COMMENT '单据编号',
  type tinyint(4) NOT NULL COMMENT '单据类型，1：其他入库单，2：其他出库单，3：报溢单，4：报损单，5：成本调价单，6：库存盘点单',
  create_time datetime NOT NULL COMMENT '单据日期',
  order_status tinyint(4) NOT NULL COMMENT '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  target_id varchar(50) DEFAULT NULL COMMENT '往来单位编号，类型1、2会用到',
  warehouse_id int(11) NOT NULL COMMENT '仓库编号',
  total_quantity int(11) NOT NULL COMMENT '总数量，类型1-6都会用到',
  total_money double(10,2) DEFAULT NULL COMMENT '总金额，类型1-5会用到',
  total_check_quantity int(11) DEFAULT NULL COMMENT '总库存数量，类型6会用到',
  total_in_quantity int(11) DEFAULT NULL COMMENT '总报溢数量，类型6会用到',
  total_in_money double(10,2) DEFAULT NULL COMMENT '总报溢金额，类型6会用到',
  total_out_quantity int(11) DEFAULT NULL COMMENT '总报损数量，类型6会用到',
  total_out_money double(10,2) DEFAULT NULL COMMENT '总报损金额，类型6会用到',
  user_id varchar(50) NOT NULL COMMENT '经手人',
  remark varchar(255) DEFAULT NULL COMMENT '单据备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS store;
CREATE TABLE store (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺编号',
  name varchar(50) NOT NULL COMMENT '店铺名',
  address varchar(100) NOT NULL COMMENT '地址',
  client_id varchar(50) NOT NULL COMMENT '店长会员编号',
  PRIMARY KEY (id),
  UNIQUE KEY name (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS store_client;
CREATE TABLE store_client (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺/客户关系编号',
  store_id int(11) NOT NULL COMMENT '店铺编号',
  client_id varchar(50) NOT NULL COMMENT '客户编号',
  integral int(11) NOT NULL COMMENT '积分',
  advance_in_money_opening double(10,2) NOT NULL COMMENT '预收款期初',
  need_in_money_opening double(10,2) NOT NULL COMMENT '应收款期初',
  push_money double(10,2) NOT NULL COMMENT '提成',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS store_client_detail;
CREATE TABLE store_client_detail (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺/客户明细关系编号',
  store_id int(11) NOT NULL COMMENT '店铺编号',
  client_id varchar(50) NOT NULL COMMENT '客户编号',
  create_time datetime NOT NULL COMMENT '创建时间',
  update_time datetime NOT NULL COMMENT '修改时间',
  type tinyint(4) NOT NULL COMMENT '类型，1：积分增加，2：积分减少，3：提成增加，4：提成减少',
  change_integral int(11) DEFAULT NULL COMMENT '改变积分',
  change_push_money double(10,2) DEFAULT NULL COMMENT '改变提成',
  order_id varchar(50) DEFAULT NULL COMMENT '单据编号',
  status tinyint(4) NOT NULL COMMENT '状态，0：待审核，1：审核通过，2：审核未通过',
  user_id varchar(50) DEFAULT NULL COMMENT '经手人',
  withdrawal_way tinyint(4) DEFAULT NULL COMMENT '提现方式，1：提现到客户手机号码对应的支付宝账户，2：其他方式需填写备注',
  remark varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS supplier_1;
CREATE TABLE supplier_1 (
  id varchar(20) NOT NULL COMMENT '供应商编号',
  name varchar(50) NOT NULL COMMENT '供应商名称',
  advance_out_money_opening double(10,2) DEFAULT NULL COMMENT '预付款期初',
  need_out_money_opening double(10,2) DEFAULT NULL COMMENT '应付款期初',
  contacts varchar(20) NOT NULL COMMENT '联系人',
  contact_number varchar(20) NOT NULL COMMENT '联系电话',
  contact_address varchar(100) DEFAULT NULL COMMENT '联系地址',
  fax varchar(20) DEFAULT NULL COMMENT '传真',
  remark varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS system;
CREATE TABLE system (
  store_id int(11) NOT NULL COMMENT '店铺编号',
  push_money_rate double(10,2) NOT NULL COMMENT '提成比例',
  start_bill tinyint NOT NULL COMMENT '系统开账，0：否，1：是',
  retail_warehouse_id int comment '零售默认仓库编号',
  retail_bank_account_id varchar(20) comment '零售默认银行账户编号',
  PRIMARY KEY (store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO system VALUES (0, 0.5, 6, null, null);
INSERT INTO system VALUES (1, 6, 0, null, null);


DROP TABLE IF EXISTS user_1;
CREATE TABLE user_1 (
  id varchar(50) NOT NULL COMMENT '用户id',
  name varchar(10) NOT NULL COMMENT '用户姓名',
  username varchar(50) NOT NULL COMMENT '用户名',
  password varchar(50) NOT NULL COMMENT '密码',
  phone varchar(20) NOT NULL COMMENT '电话',
  disabled tinyint(4) NOT NULL COMMENT '是否禁用，0：不禁用，1：禁用',
  remark varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY username (username),
  UNIQUE KEY phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO user_1 VALUES ('dcb71baaf38411e8b25b54ee75c0f47a', '老板', 'lb', 'lb', '11111111111', '0', null);


DROP TABLE IF EXISTS user_role_1;
CREATE TABLE user_role_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  user_id varchar(50) NOT NULL COMMENT '用户id',
  role_id int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


INSERT INTO user_role_1 VALUES ('1', 'dcb71baaf38411e8b25b54ee75c0f47a', '1');


DROP TABLE IF EXISTS warehouse_1;
CREATE TABLE warehouse_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  name varchar(20) DEFAULT NULL COMMENT '仓库名',
  contacts varchar(20) DEFAULT NULL COMMENT '联系人',
  contact_number varchar(20) DEFAULT NULL COMMENT '联系电话',
  address varchar(100) DEFAULT NULL COMMENT '地址',
  postcode varchar(10) DEFAULT NULL COMMENT '邮编',
  remark varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS warehouse_goods_sku_1;
CREATE TABLE warehouse_goods_sku_1 (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库/商品规格关系编号',
  warehouse_id int(11) NOT NULL COMMENT '仓库编号',
  goods_sku_id int(11) NOT NULL COMMENT '商品规格编号',
  real_inventory int(11) NOT NULL COMMENT '实物库存',
  not_sent_quantity int(11) NOT NULL COMMENT '待发货数量',
  not_received_quantity int(11) NOT NULL COMMENT '待收货数量',
  can_use_inventory int(11) NOT NULL COMMENT '可用库存',
  book_inventory int(11) NOT NULL COMMENT '账面库存',
  inventory_upper_limit int(11) DEFAULT NULL COMMENT '库存上限',
  inventory_low_limit int(11) DEFAULT NULL COMMENT '库存下限',
  opening_quantity int(11) NOT NULL COMMENT '期初数量',
  opening_money double(10,2) NOT NULL COMMENT '期初成本单价',
  opening_total_money double(10,2) NOT NULL COMMENT '期初金额',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


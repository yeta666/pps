
/*店铺*/
drop table if exists store;
create table store(
  id int primary key auto_increment COMMENT '店铺编号',
  name varchar(50) not null unique comment '店铺名',
  address varchar(100) not null comment '地址',
  boss varchar(20) not null comment '店长',
  phone varchar(20) not null unique comment '电话'
) engine InnoDB default charset=utf8;


/*客户*/
drop table if exists client;
create table client(
  id varchar(50) primary key comment '客户编号',
  name varchar(10) not null comment '姓名',
  username varchar(50) not null unique comment '用户名',
  password varchar(50) not null comment '密码',
  phone varchar(20) not null unique comment '电话',
  level_id int not null comment '级别编号',
  birthday datetime comment '生日',
  inviter_id varchar(50) comment '邀请人编号',
  address varchar(100) comment '客户地址',
  postcode varchar(10) comment '邮编',
  membership_number varchar(100) not null comment '会员卡号',
  last_deal_time datetime comment '最近交易时间',
  create_time datetime not null comment '创建时间',
  disabled tinyint not null comment '是否停用，0：否，1：是',
  remark varchar(200) comment '备注'
) engine InnoDB default charset=utf8;


/*店铺/积分关系*/
drop table if exists store_integral;
create table store_integral(
  id int primary key auto_increment comment '店铺/客户积分关系编号',
  store_id int not null comment '店铺编号',
  client_id varchar(50) not null comment '客户编号',
  integral int not null comment '积分'
) engine InnoDB default charset=utf8;


/*客户/积分明细关系*/
drop table if exists client_integral_detail;
create table client_integral_detail(
  id int primary key auto_increment comment '客户/积分明细关系编号',
  store_id int not null comment '店铺编号',
  client_id varchar(50) not null comment '客户编号',
  create_time datetime not null comment '创建时间',
  type tinyint not null comment '操作类型，0：减少，1：增加',
  change_integral int not null comment '改变积分',
  after_change_integral int not null comment '改变后的积分',
  order_id varchar(50) comment '单据编号'
) engine InnoDB default charset=utf8;


/*客户级别*/
drop table if exists client_level;
create table client_level(
  id int primary key auto_increment comment '客户级别编号',
  name varchar(20) not null unique comment '客户级别',
  price_type tinyint not null comment '级别价格类型，1：零售价，2：vip售价',
  price decimal(10, 2) not null comment '级别默认价格，级别价格类型*0.几'
) engine InnoDB default charset=utf8;


/*会员卡号*/
drop table if exists membership_number;
create table membership_number(
  id int not null primary key auto_increment comment '会员卡号编号',
  number varchar(100) not null unique comment '会员卡号',
  disabled tinyint not null comment '是否停用，0：否，1：是'
) engine InnoDB default charset=utf8;



drop table if exists user_1;
create table user_1(
  id varchar(50) NOT NULL primary key COMMENT '用户id',
  name varchar(10) NOT NULL COMMENT '用户姓名',
  username varchar(50) not null unique comment '用户名',
  password varchar(50) not null comment '密码',
  phone varchar(20) not null unique comment '电话',
  warehouse_id int not null comment '仓库id',
  disabled tinyint not null comment '是否禁用，0：不禁用，1：禁用',
  remark varchar(200) comment '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into user_1(id, name, username, password, phone, warehouse_id, disabled) values ('dcb71baa-f384-11e8-b25b-54ee75c0f47a', '老板', 'lb', 'lb', '17760041487', 1, 0);
insert into user_1(id, name, username, password, phone, warehouse_id, disabled) values ('dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '销售经理', 'xsjl', 'xsjl', '17760041488', 1, 0);
insert into user_1(id, name, username, password, phone, warehouse_id, disabled) values ('dcbd0207-f384-11e8-b25b-54ee75c0f47a', '销售', 'xs', 'xs', '17760041489', 1, 0);
insert into user_1(id, name, username, password, phone, warehouse_id, disabled) values ('dcc00d94-f384-11e8-b25b-54ee75c0f47a', '采购', 'cg', 'cg', '17760041490', 1, 0);
insert into user_1(id, name, username, password, phone, warehouse_id, disabled) values ('dcc30169-f384-11e8-b25b-54ee75c0f47a', '库管', 'kg', 'kg', '17760041491', 1, 0);
insert into user_1(id, name, username, password, phone, warehouse_id, disabled) values ('dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '财务', 'cw', 'cw', '17760041492', 1, 0);

drop table if exists role_1;
create table role_1(
  id int not null auto_increment comment '角色id',
  name varchar(10) not null comment '角色名',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into role_1(name) values ('老板');
insert into role_1(name) values ('销售经理');
insert into role_1(name) values ('销售');
insert into role_1(name) values ('采购');
insert into role_1(name) values ('库管');
insert into role_1(name) values ('财务');

drop table if exists user_role_1;
create table user_role_1(
  id int not null auto_increment comment 'id',
  user_id varchar(50) NOT NULL COMMENT '用户id',
  role_id int not null comment '角色id',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into user_role_1 (user_id, role_id) VALUES ('dcb71baa-f384-11e8-b25b-54ee75c0f47a', 1);
insert into user_role_1 (user_id, role_id) VALUES ('dcb9fa13-f384-11e8-b25b-54ee75c0f47a', 2);
insert into user_role_1 (user_id, role_id) VALUES ('dcbd0207-f384-11e8-b25b-54ee75c0f47a', 3);
insert into user_role_1 (user_id, role_id) VALUES ('dcc00d94-f384-11e8-b25b-54ee75c0f47a', 4);
insert into user_role_1 (user_id, role_id) VALUES ('dcc30169-f384-11e8-b25b-54ee75c0f47a', 5);
insert into user_role_1 (user_id, role_id) VALUES ('dcc55a1b-f384-11e8-b25b-54ee75c0f47a', 6);

drop table function;
create table function(
  id int NOT NULL auto_increment COMMENT '功能id',
  name varchar(10) NOT NULL COMMENT '功能名',
  level tinyint not null comment '功能级别',
  parnet_id int not null comment '父功能id',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO function (name, level, parnet_id) VALUES ('销售', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('采购', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('仓库', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('资金', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('营销', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('CRM', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('报表', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('资料', '1', 0);
INSERT INTO function (name, level, parnet_id) VALUES ('设置', '1', 0);
insert into function (name, level, parnet_id) values ('批发业务', 2, 1);
insert into function (name, level, parnet_id) values ('零售业务', 2, 1);
insert into function (name, level, parnet_id) values ('销售结果', 2, 1);
insert into function (name, level, parnet_id) values ('采购业务', 2, 2);
insert into function (name, level, parnet_id) values ('采购结果', 2, 2);
insert into function (name, level, parnet_id) values ('收发货', 2, 3);
insert into function (name, level, parnet_id) values ('其他出入库', 2, 3);
insert into function (name, level, parnet_id) values ('盘点', 2, 3);
insert into function (name, level, parnet_id) values ('预警', 2, 3);
insert into function (name, level, parnet_id) values ('收款', 2, 4);
insert into function (name, level, parnet_id) values ('付款', 2, 4);
insert into function (name, level, parnet_id) values ('往来对账', 2, 4);
insert into function (name, level, parnet_id) values ('往来调整', 2, 4);
insert into function (name, level, parnet_id) values ('收入支出', 2, 4);
insert into function (name, level, parnet_id) values ('营销活动', 2, 5);
insert into function (name, level, parnet_id) values ('外勤', 2, 6);
insert into function (name, level, parnet_id) values ('CRM', 2, 6);
insert into function (name, level, parnet_id) values ('单据中心', 2, 7);
insert into function (name, level, parnet_id) values ('库存报表', 2, 7);
insert into function (name, level, parnet_id) values ('资金报表', 2, 7);
insert into function (name, level, parnet_id) values ('订单统计', 2, 7);
insert into function (name, level, parnet_id) values ('销售报表', 2, 7);
insert into function (name, level, parnet_id) values ('采购报表', 2, 7);
insert into function (name, level, parnet_id) values ('经营中心', 2, 7);
insert into function (name, level, parnet_id) values ('商品管理', 2, 8);
insert into function (name, level, parnet_id) values ('往来单位', 2, 8);
insert into function (name, level, parnet_id) values ('机构管理', 2, 8);
insert into function (name, level, parnet_id) values ('财务账户', 2, 8);
insert into function (name, level, parnet_id) values ('岗位权限', 2, 8);
insert into function (name, level, parnet_id) values ('系统配置', 2, 9);
insert into function (name, level, parnet_id) values ('期初录入', 2, 9);
insert into function (name, level, parnet_id) values ('账套操作', 2, 9);
insert into function (name, level, parnet_id) values ('销售订单', 3, 10);
insert into function (name, level, parnet_id) values ('销售退货申请', 3, 10);
insert into function (name, level, parnet_id) values ('销售换货申请', 3, 10);
insert into function (name, level, parnet_id) values ('零售单', 3, 11);
insert into function (name, level, parnet_id) values ('零售交班历史', 3, 11);
insert into function (name, level, parnet_id) values ('销售历史', 3, 12);
insert into function (name, level, parnet_id) values ('采购订单', 3, 13);
insert into function (name, level, parnet_id) values ('采购退货申请', 3, 13);
insert into function (name, level, parnet_id) values ('采购换货申请', 3, 13);
insert into function (name, level, parnet_id) values ('采购历史', 3, 14);
insert into function (name, level, parnet_id) values ('待发货', 3, 15);
insert into function (name, level, parnet_id) values ('待收货', 3, 15);
insert into function (name, level, parnet_id) values ('其他入库单', 3, 16);
insert into function (name, level, parnet_id) values ('其他出库单', 3, 16);
insert into function (name, level, parnet_id) values ('其他出入库历史', 3, 16);
insert into function (name, level, parnet_id) values ('报损单', 3, 17);
insert into function (name, level, parnet_id) values ('报溢单', 3, 17);
insert into function (name, level, parnet_id) values ('库存盘点单', 3, 17);
insert into function (name, level, parnet_id) values ('成本调价单', 3, 17);
insert into function (name, level, parnet_id) values ('库存预警设置', 3, 18);
insert into function (name, level, parnet_id) values ('库存预警查询', 3, 18);
insert into function (name, level, parnet_id) values ('近效期设置', 3, 18);
insert into function (name, level, parnet_id) values ('近效期查询', 3, 18);
insert into function (name, level, parnet_id) values ('按单收款', 3, 19);
insert into function (name, level, parnet_id) values ('收款单', 3, 19);
insert into function (name, level, parnet_id) values ('预收款单', 3, 19);
insert into function (name, level, parnet_id) values ('待确认款项', 3, 19);
insert into function (name, level, parnet_id) values ('收款统计', 3, 19);
insert into function (name, level, parnet_id) values ('按单付款', 3, 20);
insert into function (name, level, parnet_id) values ('付款单', 3, 20);
insert into function (name, level, parnet_id) values ('预付款单', 3, 20);
insert into function (name, level, parnet_id) values ('付款统计', 3, 20);
insert into function (name, level, parnet_id) values ('查应收', 3, 21);
insert into function (name, level, parnet_id) values ('职员部门应收款', 3, 21);
insert into function (name, level, parnet_id) values ('超期应收查询', 3, 21);
insert into function (name, level, parnet_id) values ('查应付', 3, 21);
insert into function (name, level, parnet_id) values ('应收应付调整', 3, 22);
insert into function (name, level, parnet_id) values ('往来清账', 3, 22);
insert into function (name, level, parnet_id) values ('应收应付调整', 3, 23);
insert into function (name, level, parnet_id) values ('其他收入', 3, 23);
insert into function (name, level, parnet_id) values ('发短信', 3, 24);
insert into function (name, level, parnet_id) values ('优惠券', 3, 24);
insert into function (name, level, parnet_id) values ('促销管理', 3, 24);
insert into function (name, level, parnet_id) values ('客户分布', 3, 25);
insert into function (name, level, parnet_id) values ('客户管理', 3, 26);
insert into function (name, level, parnet_id) values ('客户跟进', 3, 26);
insert into function (name, level, parnet_id) values ('合同', 3, 26);
insert into function (name, level, parnet_id) values ('待记账单据', 3, 27);
insert into function (name, level, parnet_id) values ('业务草稿', 3, 27);
insert into function (name, level, parnet_id) values ('查库存', 3, 28);
insert into function (name, level, parnet_id) values ('进销存分析', 3, 28);
insert into function (name, level, parnet_id) values ('出入库明细', 3, 28);
insert into function (name, level, parnet_id) values ('查应收', 3, 29);
insert into function (name, level, parnet_id) values ('查应付', 3, 29);
insert into function (name, level, parnet_id) values ('查资金', 3, 29);
insert into function (name, level, parnet_id) values ('查回款', 3, 29);
insert into function (name, level, parnet_id) values ('查费用', 3, 29);
insert into function (name, level, parnet_id) values ('订单分析', 3, 30);
insert into function (name, level, parnet_id) values ('商品订货分析', 3, 30);
insert into function (name, level, parnet_id) values ('客户订货分析', 3, 30);
insert into function (name, level, parnet_id) values ('销售日月年报', 3, 31);
insert into function (name, level, parnet_id) values ('商品销售分析', 3, 31);
insert into function (name, level, parnet_id) values ('客户销售分析', 3, 31);
insert into function (name, level, parnet_id) values ('业绩统计', 3, 31);
insert into function (name, level, parnet_id) values ('回款统计', 3, 31);
insert into function (name, level, parnet_id) values ('提成设置', 3, 31);
insert into function (name, level, parnet_id) values ('提成统计', 3, 31);
insert into function (name, level, parnet_id) values ('商品采购分析', 3, 32);
insert into function (name, level, parnet_id) values ('供应商采购分析', 3, 32);
insert into function (name, level, parnet_id) values ('采购订单分析', 3, 32);
insert into function (name, level, parnet_id) values ('销售经营分析', 3, 33);
insert into function (name, level, parnet_id) values ('资金经营分析', 3, 33);
insert into function (name, level, parnet_id) values ('往来经营分析', 3, 33);
insert into function (name, level, parnet_id) values ('库存经营分析', 3, 33);
insert into function (name, level, parnet_id) values ('利润经营分析', 3, 33);
insert into function (name, level, parnet_id) values ('老板中心', 3, 33);
insert into function (name, level, parnet_id) values ('商品', 3, 34);
insert into function (name, level, parnet_id) values ('商品SKU及条码', 3, 34);
insert into function (name, level, parnet_id) values ('商品价格管理', 3, 34);
insert into function (name, level, parnet_id) values ('商品辅助资料', 3, 34);
insert into function (name, level, parnet_id) values ('图片管理', 3, 34);
insert into function (name, level, parnet_id) values ('客户', 3, 35);
insert into function (name, level, parnet_id) values ('供应商', 3, 35);
insert into function (name, level, parnet_id) values ('仓库信息', 3, 36);
insert into function (name, level, parnet_id) values ('职员部门', 3, 36);
insert into function (name, level, parnet_id) values ('银行账户', 3, 37);
insert into function (name, level, parnet_id) values ('费用类型', 3, 37);
insert into function (name, level, parnet_id) values ('其他收入', 3, 37);
insert into function (name, level, parnet_id) values ('费用单', 3, 38);
insert into function (name, level, parnet_id) values ('全部操作员', 3, 38);
insert into function (name, level, parnet_id) values ('系统参数', 3, 39);
insert into function (name, level, parnet_id) values ('审核设置', 3, 39);
insert into function (name, level, parnet_id) values ('支付配置', 3, 39);
insert into function (name, level, parnet_id) values ('企业信息', 3, 39);
insert into function (name, level, parnet_id) values ('库存期初', 3, 40);
insert into function (name, level, parnet_id) values ('现金银行期初', 3, 40);
insert into function (name, level, parnet_id) values ('应收期初', 3, 40);
insert into function (name, level, parnet_id) values ('应付期初', 3, 40);
insert into function (name, level, parnet_id) values ('系统开账', 3, 41);
insert into function (name, level, parnet_id) values ('系统重建', 3, 41);
insert into function (name, level, parnet_id) values ('操作日志', 3, 41);

drop table if exists role_function_1;
create table role_function_1(
  id int not null auto_increment comment 'id',
  role_id int not null comment '角色id',
  function_id int NOT NULL COMMENT '功能id',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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


/*仓库*/
drop table warehouse_1;
create table warehouse_1(
  id int not null primary key auto_increment comment '仓库编号',
  name varchar(20) not null null unique comment '仓库名',
  contacts varchar(20) comment '联系人',
  contact_number varchar(20) comment '联系电话',
  address varchar(100) comment '地址',
  postcode varchar(10) comment '邮编',
  remark varchar(200) comment '备注'
) engine InnoDB default charset=utf8;
insert into warehouse_1 (name) values ('第一个仓库');


/*商品*/
drop table if exists goods_1;
create table goods_1(
  id varchar(50) not null primary key comment '商品货号',
  name varchar(200) not null unique comment '商品名',
  bar_code varchar(50) not null comment '条码',
  type_id int not null comment '分类id',
  putaway tinyint not null comment '上架状态，0：未上架，1：已上架',
  skus MEDIUMTEXT comment '商品规格',
  origin varchar(200) comment '产地',
  image varchar(200) comment '图片',
  remark varchar(200) comment '备注',
  create_time datetime not null comment '创建时间'
) engine InnoDB default charset=utf8;
insert into goods_1 (id, name, bar_code, type_id, putaway, skus, origin, image, remark, create_time)
VALUES ('sp001', '舍得典藏款', '176', 1, 1, '[{"key":"品牌","value":["舍得"]},{"key":"单位","value":["瓶","件"]}]', '四川', '/upload/goods/123123.jpg', '', now());
insert into goods_1 (id, name, bar_code, type_id, putaway, skus, origin, image, remark, create_time)
VALUES ('sp002', '美的电压力锅', '177', 2, 1, '[{"key":"品牌","value":["美的"]},{"key":"单位","value":["个"]}]', '浙江', '/upload/goods/123123.jpg', '', now());

drop table if exists goods_type_1;
create table goods_type_1(
  id int primary key auto_increment comment '商品分类id',
  name varchar(20) not null unique comment '商品分类名'
) engine InnoDB default charset=utf8;
insert into goods_type_1 (name) values ('酒类');
insert into goods_type_1 (name) values ('锅类');

drop table if exists goods_label_1;
create table goods_label_1(
  id int primary key auto_increment comment '商品标签id',
  name varchar(20) not null unique comment '商品标签名'
) engine InnoDB default charset=utf8;
insert into goods_label_1 (name) values ('新品');
insert into goods_label_1 (name) values ('推荐');
insert into goods_label_1 (name) values ('促销');
insert into goods_label_1 (name) values ('清仓');

drop table if exists goods_goods_label_1;
create table goods_goods_label_1(
  id int primary key auto_increment comment '商品/商品标签关系id',
  goods_id varchar(50) not null comment '商品货号',
  label_id int not null comment '商品标签id'
) engine InnoDB default charset=utf8;
insert into goods_goods_label_1 (goods_id, label_id) values ('sp001', 1);
insert into goods_goods_label_1 (goods_id, label_id) values ('sp001', 2);
insert into goods_goods_label_1 (goods_id, label_id) values ('sp002', 3);
insert into goods_goods_label_1 (goods_id, label_id) values ('sp002', 4);

drop table if exists goods_property_key_1;
create table goods_property_key_1 (
  id int primary key auto_increment comment '商品属性名id',
  name varchar(20) not null comment '商品属性名',
  type_id int not null comment '商品分类id'
) engine InnoDB default charset=utf8;
insert into goods_property_key_1 (name, type_id) values ('品牌', 1);
insert into goods_property_key_1 (name, type_id) values ('单位', 1);
insert into goods_property_key_1 (name, type_id) values ('品牌', 2);

drop table if exists goods_property_value_1;
create table goods_property_value_1(
  id int primary key auto_increment comment '商品属性值id',
  name varchar(20) not null comment '商品属性值',
  property_key_id int not null comment '商品属性名id'
) engine InnoDB default charset=utf8;
insert into goods_property_value_1 (name, property_key_id) values ('舍得', 1);
insert into goods_property_value_1 (name, property_key_id) values ('二锅头', 1);
insert into goods_property_value_1 (name, property_key_id) values ('瓶', 2);
insert into goods_property_value_1 (name, property_key_id) values ('件', 2);
insert into goods_property_value_1 (name, property_key_id) values ('美的', 3);
insert into goods_property_value_1 (name, property_key_id) values ('个', 3);


/*商品规格*/
drop table if exists goods_sku_1;
create table goods_sku_1(
  id int primary key auto_increment comment '商品规格编号',
  goods_id varchar(50) not null comment '商品货号',
  sku MEDIUMTEXT not null comment '商品规格',
  purchase_price decimal(10, 2) not null comment '进价',
  retail_price decimal(10, 2) not null comment '零售价',
  vip_price decimal(10, 2) not null comment 'vip售价',
  integral int not null comment '积分'
) engine InnoDB default charset=utf8;
insert into goods_sku_1 (goods_id, sku, purchase_price, retail_price, vip_price, integral) values ('sp001', '[{"key":"品牌","value":"舍得"},{"key":"单位","value":"瓶"}]', 10, 30, 20, 66);
insert into goods_sku_1 (goods_id, sku, purchase_price, retail_price, vip_price, integral) values ('sp001', '[{"key":"品牌","value":"舍得"},{"key":"单位","value":"件"}]', 10, 30, 20, 66);
insert into goods_sku_1 (goods_id, sku, purchase_price, retail_price, vip_price, integral) values ('sp002', '[{"key":"品牌","value":"美的"},{"key":"单位","value":"个"}]', 10, 30, 20, 66);


drop table if exists bank_account_1;
create table bank_account_1(
  id varchar(20) not null primary key comment '科目编号',
  name varchar(20) not null comment '科目名称',
  type tinyint not null comment '账户类型，1：现金，2：银行卡，3：支付宝，4：微信',
  head varchar(20) comment '户主名',
  account varchar(50) comment '账户',
  gathering tinyint default 0 comment '是否用于商城收款，0：否，1：是',
  qrCode varchar(50) comment '收款码',
  procurement tinyint default 0 comment '是否用于订货平台，0：否，1：是'
) engine InnoDB default charset=utf8;

insert into bank_account_1 (id, name, type) VALUES ('1001', '库存现金', 1);
insert into bank_account_1 (id, name, type) VALUES ('1002', '银行存款', 2);
insert into bank_account_1 (id, name, type) VALUES ('100201', '微信账户', 3);
insert into bank_account_1 (id, name, type) VALUES ('100202', '支付宝账户', 4);
insert into bank_account_1 (id, name, type) VALUES ('100203', '建设银行', 2);
insert into bank_account_1 (id, name, type) VALUES ('100204', '招商银行', 2);
insert into bank_account_1 (id, name, type) VALUES ('100205', '华夏银行', 2);

drop table income_expenses_1;
create table income_expenses_1(
  id varchar(20) not null primary key comment '科目编号',
  name varchar(20) not null comment '科目名称',
  check_item tinyint comment '核算项，1：供应商，2：客户，3：往来单位，4：职员，5：部门',
  debit_credit tinyint not null comment '借贷，1：贷，2：借',
  type tinyint not null comment '收支，1：收入，2：支出'
) engine InnoDB default charset=utf8;

insert into income_expenses_1 (id, name, debit_credit, type) values ('6403', '营业税金及附加', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6601', '销售费用', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660101', '优惠金额', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660102', '包装费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660103', '运输费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660104', '广告费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660105', '装卸费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660106', '保险费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660107', '展览费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660108', '租赁费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660109', '销售服务费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660110', '差旅费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660111', '办公费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660112', '折旧费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660113', '修理费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660114', '网店运费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, check_item, type) values ('660115', '快递费', 2, 2, 2);
insert into income_expenses_1 (id, name, debit_credit, check_item, type) values ('660116', '招待费', 2, 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660117', '水电气费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6602', '管理费用', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660202', '工会经费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660203', '职工教育经费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660204', '业务招待费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660205', '技术转让费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660206', '无形资产摊销', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660207', '咨询费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660208', '诉讼费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660209', '坏账损失', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, check_item, type) values ('660210', '工资', 2, 4, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660211', '房租', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660212', '水电气', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6603', '财务费用', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660302', '汇兑净损失', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('660303', '金融机构手续费', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6701', '资产减值损失', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6711', '营业外支出', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6801', '所得税费用', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6901', '以前年度损益调整', 2, 2);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6052', '其他收入', 1, 1);
insert into income_expenses_1 (id, name, debit_credit, type) values ('605201', '利息收入', 1, 1);
insert into income_expenses_1 (id, name, debit_credit, type) values ('605202', '损赠收入', 1, 1);
insert into income_expenses_1 (id, name, debit_credit, type) values ('605203', '租赁收入', 1, 1);
insert into income_expenses_1 (id, name, debit_credit, type) values ('605204', '罚没收入', 1, 1);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6111', '投资收益', 1, 1);
insert into income_expenses_1 (id, name, debit_credit, type) values ('6301', '营业外收入', 1, 1);


drop table supplier_1;
create table supplier_1 (
  id varchar(20) not null primary key comment '供应商编号',
  name varchar(50) not null comment '供应商名称',
  contacts varchar(20) not null comment '联系人',
  contact_number varchar(20) not null comment '联系电话',
  contact_address varchar(100) comment '联系地址',
  fax varchar(20) comment '传真',
  remark varchar(200) comment '备注'
) engine InnoDB default charset=utf8;

insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys001', '美特斯邦威', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys002', '耐克', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys003', '阿迪达斯', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys004', '新百伦', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys005', '雅鹿', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys006', 'HSTYLE/韩都衣舍', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys007', '森马', '小明', '17760041487', '', '', '');
insert into supplier_1 (id, name, contacts, contact_number, contact_address, fax, remark) VALUES ('gys008', '秋水伊人', '小明', '17760041487', '', '', '');





/*采购申请单*/
drop table if exists procurement_apply_order_1;
create table procurement_apply_order_1(
  id varchar(50) primary key comment '单据编号',
  type tinyint not null comment '单据类型，1：采购订单，2：采购退货申请单，3：采购换货申请单',
  create_time datetime not null comment '单据日期',
  result_order_id varchar(50) comment '来源订单，采购退货申请单和采购换货申请单应该有该字段',
  order_status tinyint not null comment '单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发',
  clear_status tinyint not null comment '结算状态：0：未完成，1：已完成',
  supplier_id varchar(20) not null comment '供应商编号',
  in_warehouse_id int comment '入库仓库编号，对应收货',
  in_total_quantity int comment '总收货数量',
  in_received_quantity int comment '已收货数量',
  in_not_received_quantity int comment '未收货数量',
  out_warehouse_id int comment '出库仓库编号，对应发货',
  out_total_quantity int comment '总发货数量',
  out_sent_quantity int comment '已发货数量',
  out_not_sent_quantity int comment '未发货数量',
  total_money decimal(10, 2) not null comment '总商品金额',
  total_discount_money decimal(10, 2) not null comment '总优惠金额',
  order_money decimal(10, 2) not null comment '本单金额',
  cleared_money decimal(10, 2) not null comment '已结算金额',
  not_cleared_money decimal(10, 2) not null comment '未结算金额',
  user_id varchar(50) not null comment '经手人编号',
  remark varchar(255) comment '单据备注'
) engine InnoDB default charset=utf8;
insert into
procurement_apply_order_1 (id, type, create_time, order_status, clear_status, supplier_id, in_warehouse_id, in_total_quantity, in_received_quantity, in_not_received_quantity, out_warehouse_id, out_total_quantity, out_sent_quantity, out_not_sent_quantity, total_money, total_discount_money, order_money, cleared_money, not_cleared_money, user_id, remark)
values ('CGDD_001', 1, now(), 1, 0, 'gys001', 1, 20, 0, 20, null, null, null, null, 100, 0, 100, 0, 100, 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', null);
insert into
procurement_apply_order_1 (id, type, create_time, order_status, clear_status, supplier_id, in_warehouse_id, in_total_quantity, in_received_quantity, in_not_received_quantity, out_warehouse_id, out_total_quantity, out_sent_quantity, out_not_sent_quantity, total_money, total_discount_money, order_money, cleared_money, not_cleared_money, user_id, remark)
values ('CGDD_002', 1, now(), 1, 0, 'gys001', 1, 20, 0, 20, null, null, null, null, 100, 0, 100, 0, 100, 'dcb71baa-f384-11e8-b25b-54ee75c0f47a', null);


/*采购结果单*/
drop table if exists procurement_result_order_1;
create table procurement_result_order_1(
  id varchar(50) primary key comment '单据编号',
  type tinyint not null comment '单据类型，1：采购入库单，2：采购退货单，3：采购换货单',
  create_time datetime not null comment '单据日期',
  apply_order_id varchar(50) not null comment '来源订单',
  order_status tinyint not null comment '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  total_quantity int not null comment '总商品数量',
  total_money decimal(10, 2) not null comment '总商品金额',
  total_discount_money decimal(10, 2) not null comment '总优惠金额',
  order_money decimal(10, 2) not null comment '本单金额',
  user_id varchar(50) not null comment '经手人',
  remark varchar(255) comment '单据备注'
) engine InnoDB default charset=utf8;



/*销售申请单*/
drop table if exists sell_apply_order_1;
create table sell_apply_order_1(
  id varchar(50) primary key comment '单据编号',
  type tinyint not null comment '单据类型，1：零售单，2：销售订单，3：销售退货申请单，4：销售换货申请单',
  create_time datetime not null comment '单据日期',
  result_order_id varchar(50) comment '来源订单，销售退货申请单和销售换货申请单应该有该字段',
  prodcing_way tinyint not null comment '产生方式，1：线下录单，2：线上下单',
  order_status tinyint not null comment '单据状态，1：未收，2：部分收，3：已收，4：未发，5：部分发，6：已发，7：未收未发，8：未收部分发，9：未收已发，10：部分收未发，11：部分收部分发，12：部分收已发，13：已收未发，14：已收部分发：15：已收已发',
  clear_status tinyint not null comment '结算状态：0：未完成，1：已完成',
  client_id varchar(50) not null comment '客户编号',
  in_warehouse_id int comment '入库仓库编号，对应收货',
  in_total_quantity int comment '总收货数量',
  in_received_quantity int comment '已收货数量',
  in_not_received_quantity int comment '未收货数量',
  out_warehouse_id int comment '出库仓库编号，对应发货',
  out_total_quantity int comment '总发货数量',
  out_sent_quantity int comment '已发货数量',
  out_not_sent_quantity int comment '未发货数量',
  total_money decimal(10, 2) not null comment '总商品金额',
  discount_money decimal(10, 2) not null comment '直接优惠金额',
  discount_coupon_id int comment '优惠券编号',
  total_discount_money decimal(10, 2) not null comment '总优惠金额',
  order_money decimal(10, 2) not null comment '本单金额',
  cleared_money decimal(10, 2) not null comment '已结算金额',
  not_cleared_money decimal(10, 2) not null comment '未结算金额',
  user_id varchar(50) not null comment '经手人编号',
  remark varchar(255) comment '单据备注'
) engine InnoDB default charset=utf8;


/*销售结果单*/
drop table if exists sell_result_order_1;
create table sell_result_order_1(
  id varchar(50) primary key comment '单据编号',
  type tinyint not null comment '单据类型，1：零售单，2：销售出库单，3：销售退货单，4：销售换货单',
  create_time datetime not null comment '单据日期',
  apply_order_id varchar(50) not null comment '来源订单',
  order_status tinyint not null comment '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  total_quantity int not null comment '总商品数量',
  total_money decimal(10, 2) not null comment '总商品金额',
  total_discount_money decimal(10, 2) not null comment '总优惠金额',
  order_money decimal(10, 2) not null comment '本单金额',
  cost_money decimal(10,2 ) not null comment '成本',
  gross_margin_money decimal(10, 2) not null comment '毛利',
  user_id varchar(50) not null comment '经手人',
  remark varchar(255) comment '单据备注'
) engine InnoDB default charset=utf8;



/*订单/商品规格关系*/
drop table if exists order_goods_sku_1;
create table order_goods_sku_1(
  id int primary key auto_increment comment '订单/商品规格关系编号',
  type tinyint not null comment '入库或出库，0：出库，1：入库',
  order_id varchar(50) not null comment '订单编号',
  goods_sku_id int not null comment '商品规格编号',
  quantity int not null comment '总数量',
  finish_quantity int comment '完成数量',
  not_finish_quantity int comment '未完成数量',
  money decimal(10, 2) not null comment '金额',
  discount_money decimal(10, 2) not null comment '优惠金额',
  operated_quantity int not null comment '已操作数量',
  remark varchar(255) comment '备注'
) engine InnoDB default charset=utf8;


/*收/发货单*/
drop table if exists storage_order_1;
create table storage_order_1(
  id varchar(50) primary key comment '单据编号',
  type tinyint not null comment '单据类型，1：采购订单收货单，2：退换货申请收货单，3：销售订单发货单，4：退换货申请发货单',
  create_time datetime not null comment '单据日期',
  apply_order_id varchar(50) not null comment '来源订单',
  order_status tinyint not null comment '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  quantity int not null comment '数量',
  logistics_company varchar(50) comment '物流公司',
  waybill_number varchar(50) comment '运单号',
  user_id varchar(50) not null comment '经手人',
  remark varchar(255) comment '单据备注'
) engine InnoDB default charset=utf8;


/*收付款单*/
drop table if exists fund_order_1;
create table fund_order_1(
  id varchar(50) primary key comment '单据编号',
  type tinyint not null comment '单据类型，1：付款单，2：收款单',
  create_time datetime not null comment '单据日期',
  apply_order_id varchar(50) not null comment '来源订单',
  order_status tinyint not null comment '单据状态：-2：红冲红单，-1：红冲蓝单，1：未红冲',
  bank_account_id varchar(20) not null comment '银行账户编号',
  money decimal(10, 2) not null comment '金额',
  user_id varchar(50) not null comment '经手人',
  remark varchar(255) comment '单据备注'
) engine InnoDB default charset=utf8;






drop table store;
create table store(
  id int NOT NULL COMMENT '店铺id' auto_increment primary key,
  name varchar(50) NOT NULL unique COMMENT '店铺名',
  user_id varchar(50) COMMENT '店铺老板id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into store(name, user_id) values ('总店', '9ba15c62-f2ef-11e8-bbaf-00155d031a09');

drop table user_1;
create table user_1(
  id varchar(50) NOT NULL COMMENT '用户id',
  name varchar(10) NOT NULL COMMENT '用户姓名',
  username varchar(50) not null unique comment '用户名',
  password varchar(50) not null comment '密码',
  phone varchar(20) not null comment '电话',
  remark varchar(200) comment '备注',
  disabled tinyint not null comment '是否禁用，0：不禁用，1：禁用',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user_1(id, name, username, password, phone, disabled) values ('dcb71baa-f384-11e8-b25b-54ee75c0f47a', '总店老板', 'lb', 'lb', '17760041487', 0);
insert into user_1(id, name, username, password, phone, disabled) values ('dcb9fa13-f384-11e8-b25b-54ee75c0f47a', '总店销售经理', 'xsjl', 'xsjl', '17760041487', 0);
insert into user_1(id, name, username, password, phone, disabled) values ('dcbd0207-f384-11e8-b25b-54ee75c0f47a', '总店销售', 'xs', 'xs', '17760041487', 0);
insert into user_1(id, name, username, password, phone, disabled) values ('dcc00d94-f384-11e8-b25b-54ee75c0f47a', '总店采购', 'cg', 'cg', '17760041487', 0);
insert into user_1(id, name, username, password, phone, disabled) values ('dcc30169-f384-11e8-b25b-54ee75c0f47a', '总店库管', 'kg', 'kg', '17760041487', 0);
insert into user_1(id, name, username, password, phone, disabled) values ('dcc55a1b-f384-11e8-b25b-54ee75c0f47a', '总店财务', 'cw', 'cw', '17760041487', 0);

drop table role_1;
create table role_2(
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

drop table user_role_1;
create table user_role(
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

drop table role_function_1;
create table role_function(
  id int not null auto_increment comment 'id',
  role_id int not null comment '角色id',
  function_id int NOT NULL COMMENT '功能id',
  primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table department;
create table department_1(
  id int not null primary key auto_increment comment '部门id',
  name varchar(20) not null unique comment '部门名',
  contacts varchar(20) comment '联系人',
  contact_number varchar(20) comment '联系电话',
  remark varchar(200) comment '备注'
) engine InnoDB default charset=utf8;

insert into department_1 (name) VALUES ('老板部门');

drop table department_user;
create table department_user_1(
  id int not null primary key auto_increment comment '部门用户id',
  department_id int not null comment '部门id',
  user_id varchar(50) not null comment '用户id'
) engine InnoDB default charset=utf8;

insert into department_user_1 (department_id, user_id) VALUES (1, 'dcb71baa-f384-11e8-b25b-54ee75c0f47a');
insert into department_user_1 (department_id, user_id) VALUES (1, 'dcb9fa13-f384-11e8-b25b-54ee75c0f47a');

drop table warehouse;
create table warehouse_1(
  id int not null primary key auto_increment comment '仓库id',
  name varchar(20) not null null unique comment '仓库名',
  contacts varchar(20) comment '联系人',
  contact_number varchar(20) comment '联系电话',
  address varchar(100) comment '地址',
  postcode varchar(10) comment '邮编',
  remark varchar(200) comment '备注'
) engine InnoDB default charset=utf8;

insert into warehouse_1 (name) values ('第一个仓库');

drop table warehouse_user;
create table warehouse_user_1(
  id int not null primary key auto_increment comment '仓库用户id',
  warehouse_id int not null comment '仓库id',
  user_id varchar(50) not null comment '用户id'
) engine InnoDB default charset=utf8;

insert into warehouse_user_1 (warehouse_id, user_id) VALUES (1, 'dcb71baa-f384-11e8-b25b-54ee75c0f47a');
insert into warehouse_user_1 (warehouse_id, user_id) VALUES (1, 'dcb9fa13-f384-11e8-b25b-54ee75c0f47a');


drop table goods_1;
create table goods_1(
  id varchar(50) not null primary key comment '商品id',
  name varchar(200) not null comment '商品名',
  code varchar(50) not null comment '货号',
  bar_code varchar(50) not null comment '条码',
  type_id int not null comment '分类id',
  brand_id int not null comment '品牌id',
  unit_id int not null comment '单位id',
  label_id int not null comment '标签id',
  retail_price decimal(10, 2) not null comment '零售价',
  trade_price decimal(10, 2) not null comment '批发价',
  purchase_price decimal(10, 2) not null comment '预设进价',
  vip_price decimal(10, 2) not null comment 'vip售价',
  inventory int default 0 comment '可用库存',
  origin varchar(100) comment '产地',
  image varchar(200) comment '图片',
  oder_type varchar(10) comment '香型',
  degree varchar(10) comment '度数',
  net_content varchar(10) comment '净含量',
  integral int default 0 comment '商品积分',
  remark varchar(200) comment '备注',
  putaway tinyint not null comment '上架状态，0：未上架，1：已上架'
) engine InnoDB default charset=utf8;

insert into goods_1 (id, name, code, bar_code, type_id, brand_id, unit_id, label_id, retail_price, trade_price, purchase_price, vip_price, inventory, origin, image, oder_type, degree, net_content, integral, remark, putaway)
VALUES (uuid(), '商品1', 'sp001', '176', 1, 1, 1, 1, 250, 200, 150, 180, 300, '产地', '/upload/goods/123123.jpg', '浓香型', '43%', '500ml', 0, '', 1);

drop table goods_type_1;
create table goods_type_1(
  id int not null primary key auto_increment comment '商品分类id',
  name varchar(10) not null unique comment '商品分类名'
) engine InnoDB default charset=utf8;

insert into goods_type_1 (name) values ('分类1');
insert into goods_type_1 (name) values ('分类2');

drop table goods_brand_1;
create table goods_brand_1(
  id int not null primary key auto_increment comment '商品品牌id',
  name varchar(50) not null unique comment '商品品牌名'
) engine InnoDB default charset=utf8;

insert into goods_brand_1 (name) values ('舍得');
insert into goods_brand_1 (name) values ('茅台');

drop table goods_unit_1;
create table goods_unit_1(
  id int not null primary key auto_increment comment '商品单位id',
  name varchar(10) not null unique comment '商品单位名'
) engine InnoDB default charset=utf8;

insert into goods_unit_1 (name) values ('瓶');
insert into goods_unit_1 (name) values ('箱');

drop table goods_label_1;
create table goods_label_1(
  id int not null primary key auto_increment comment '商品标签id',
  name varchar(10) not null unique comment '商品便签名'
) engine InnoDB default charset=utf8;

insert into goods_label_1 (name) values ('新品');
insert into goods_label_1 (name) values ('推荐');
insert into goods_label_1 (name) values ('促销');
insert into goods_label_1 (name) values ('清仓');


drop table bank_account_1;
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


drop table if exists client;
create table client(
  id varchar(50) not null primary key comment '客户编号',
  name varchar(10) not null comment '客户姓名',
  username varchar(50) not null unique comment '客户用户名',
  password varchar(50) not null comment '客户密码',
  phone varchar(20) not null unique comment '客户电话',
  birthday datetime comment '客户生日',
  inviter_id varchar(50)  comment '邀请人',
  inviter_name varchar(10) comment '邀请人姓名',
  integral int not null comment '积分',
  address varchar(100) comment '客户地址',
  postcode varchar(10) comment '邮编',
  last_deal_time datetime comment '最近交易时间',
  create_time datetime not null comment '创建时间',
  disabled tinyint not null comment '是否停用，0：否，1：是',
  remark varchar(200) comment '备注'
) engine InnoDB default charset=utf8;

insert into client (id, name, username, password, phone, birthday, inviter_id, inviter_name, integral, address, postcode, last_deal_time, create_time, disabled, remark)
values ('054774c2-f784-11e8-9dc7-54ee75c0f47a', '客户1', 'kh1', 'kh1', '17360034522', now(), '', '', 0, '', '', now(), now(), 0, '');
insert into client (id, name, username, password, phone, birthday, inviter_id, inviter_name, integral, address, postcode, last_deal_time, create_time, disabled, remark)
values ('054b1e2c-f784-11e8-9dc7-54ee75c0f47a', '客户2', 'kh2', 'kh2', '17360034523', now(), '', '', 0, '', '', now(), now(), 0, '');
insert into client (id, name, username, password, phone, birthday, inviter_id, inviter_name, integral, address, postcode, last_deal_time, create_time, disabled, remark)
values ('054e2add-f784-11e8-9dc7-54ee75c0f47a', '客户3', 'kh3', 'kh3', '17360034524', now(), '', '', 0, '', '', now(), now(), 0, '');
insert into client (id, name, username, password, phone, birthday, inviter_id, inviter_name, integral, address, postcode, last_deal_time, create_time, disabled, remark)
values ('05510d52-f784-11e8-9dc7-54ee75c0f47a', '客户4', 'kh4', 'kh4', '17360034525', now(), '', '', 0, '', '', now(), now(), 0, '');

drop table if exists client_level;
create table client_level(
  id int not null primary key auto_increment comment '客户级别id',
  name varchar(20) not null unique comment '客户级别',
  price_type tinyint not null comment '级别价格类型，1：零售价， 2：vip售价',
  price decimal(10, 2) not null comment '级别默认价格，级别价格类型*0.几'
) engine InnoDB default charset=utf8;

insert into client_level (name, price_type, price) values ('总店店长', 2, 0.6);
insert into client_level (name, price_type, price) values ('分店店长', 2, 0.8);
insert into client_level (name, price_type, price) values ('普通vip', 1, 1);
insert into client_level (name, price_type, price) values ('普通客户', 1, 1);

drop table if exists client_client_level;
create table client_client_level(
  id int not null primary key auto_increment comment '客户客户级别id',
  client_id varchar(50) not null unique comment '客户编号',
  level_id int not null comment '客户级别id'
) engine InnoDB default charset=utf8;

insert into client_client_level (client_id, level_id) values ('054774c2-f784-11e8-9dc7-54ee75c0f47a', 1);
insert into client_client_level (client_id, level_id) values ('054b1e2c-f784-11e8-9dc7-54ee75c0f47a', 2);
insert into client_client_level (client_id, level_id) values ('054e2add-f784-11e8-9dc7-54ee75c0f47a', 3);
insert into client_client_level (client_id, level_id) values ('05510d52-f784-11e8-9dc7-54ee75c0f47a', 4);

drop table if exists client_membership_number;
create table client_membership_number(
  id int not null primary key auto_increment comment '会员卡号id',
  number varchar(100) not null unique comment '会员卡号'
) engine InnoDB default charset=utf8;

insert into client_membership_number (number) values ('001');
insert into client_membership_number (number) values ('002');
insert into client_membership_number (number) values ('003');
insert into client_membership_number (number) values ('004');
insert into client_membership_number (number) values ('005');
insert into client_membership_number (number) values ('006');

drop table if exists client_client_membership_number;
create table client_client_membership_number(
  id int not null primary key auto_increment comment '客户会员卡号id',
  client_id varchar(50) not null unique comment '客户编号',
  membership_number_id int not null unique comment '会员卡号id'
) engine InnoDB default charset=utf8;

insert into client_client_membership_number (client_id, membership_number_id) values ('054774c2-f784-11e8-9dc7-54ee75c0f47a', 1);
insert into client_client_membership_number (client_id, membership_number_id) values ('054b1e2c-f784-11e8-9dc7-54ee75c0f47a', 2);
insert into client_client_membership_number (client_id, membership_number_id) values ('054e2add-f784-11e8-9dc7-54ee75c0f47a', 3);
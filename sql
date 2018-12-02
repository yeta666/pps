
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
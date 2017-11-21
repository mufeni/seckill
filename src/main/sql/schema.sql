--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;
--使用数据库
use seckill;
--创建秒杀库存表
create TABLE seckill(
'seckill_id' bigint NOT null auto_increment comment '商品库存id',
'name' VARCHAR(120) not null comment '商品名称',
'number' int NOT NULL comment '库存数量',
'start_time' TIMESTAMP not null comment '秒杀开启时间',
'end_time' TIMESTAMP not null comment '秒杀结束时间',
'create_time' timestamp not null  comment '创建时间',
primary KEY (seckill_id),
KEY idx_start_time(seckill_id),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)engine=TnnoDB auto_increment=1000 DEFAULT charset=utf-8 comment='秒杀库存表';

--初始化数据
inset into seckill(name,number,start_time,end_time)
VALUES
('1000元秒杀iPhone6',100,'2017--11-12 00:00:00','2017--11-13 00:00:00'),
('500元秒杀ipad2',200,'2017--11-12 00:00:00','2017--11-13 00:00:00'),
('200元秒杀小米4',300,'2017--11-12 00:00:00','2017--11-13 00:00:00'),
('800元秒杀华为10',100,'2017--11-12 00:00:00','2017--11-13 00:00:00'),
('200元秒杀小米note',100,'2017--11-12 00:00:00','2017--11-13 00:00:00');

--秒杀成功明细表
--用户登录认证相关的信息
CREATE table sucess_killed(
'seckill_id' bitint not null comment '秒杀商品id',
'user_phone' bitint not null comment '用户手机号',
'state' tinyint not null DEFAULT -1 comment '状态：-1 无效，0 成功，1 已付款',
'create_time' TIMESTAMP  not null comment '创建时间',
PRIMARY  key (seckill_id,,user_phone),/*联合主键*/
key idex_create_time(create_time)
)engine=TnnoDB  DEFAULT charset=utf-8 comment='秒杀成功明细表';

--链接数据库控制台
mysql -root -p



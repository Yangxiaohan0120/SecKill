--- 数据库初始化脚本 ---

-- 创建数据库 --
CREATE DATABASE seckill;

-- 使用数据库 --
use seckill;

-- 创建秒杀库存表 --
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '商品数量',
`start_time` timestamp NOT NULL COMMENT '秒杀开启时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据 --
insert into
    seckill(name,number,start_time,end_time)
values
    ('1800元秒杀Switch',100,'2022-05-20 00:00:00','2022-05-23 00:00:00'),
    ('3000元秒杀iPad3',100,'2022-05-20 00:00:00','2022-05-23 00:00:00'),
    ('2000元秒杀Xbox',100,'2022-05-20 00:00:00','2022-05-23 00:00:00'),
    ('2200元秒杀PS4',100,'2022-05-20 00:00:00','2022-05-23 00:00:00');

-- 秒杀成功明细表 --
-- 用户登录认证 --
create table success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态表示：-1：无效，0：成功，1：已付款',
`create_time` timestamp NOT NULL COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone), /* 联合主键 */
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 连接数据库控制台 --
mysql -uroot -p

-- 上线V1.1
ALTER TABLE seckill
DROP INDEX idx_create_time,
ADD index idx_c_s(start_time,create_time);
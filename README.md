# SecKill

基于Spring+SpringMVC+Mabytis构建的秒杀网站


### 技术栈
|类别|技术|注释|
|-----|-----|-----|
|注解|junit|使用注解的方式开发|
|日志|slf4j|日志接口|
||logbck|日志框架|
|数据库|mysql|数据库系统|
||c3p0|数据库连接池|
|DAO框架|mybatis|持久层框架|
||mybatis-spring|mybatis与Spring整合|
|Web|taglib|标签库|
||jstl|标签库|
||fasterxml|xml解析器|
||servlet|web请求容器|
|Spring|core|Spring核心库|
||beans|Spring bean管理|
||context|对象和容器的管理|
|Spring DAO依赖|spring-jdbc|jdbc连接简化模版|
||spring-tx|事务管理|
|Spring web依赖|spring-web|HTTP集成|
||spring-webmvc|实现MVC框架|
|Spring test依赖|spring-test|单元测试框架|
|Redis|jedis|开源redis数据库客户端|
|序列化|protostuff|序列化库|

### 功能模块

[Entity（实体类）](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/entity)

[DAO（持久化）](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/dao)

[DTO（数据传输）](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/dto)

[秒杀状态](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/enum)

[异常类型](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/exception)

[Service（执行）](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/service)

[Controller（url传递）](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/java/web)

[Mapper（sql语句）](https://github.com/Yangxiaohan0120/SecKill/tree/master/src/main/resources/mappers)

### 展示界面

##### 商品界面

![]()

##### 秒杀登记手机号

![]()

##### 秒杀已结束

![]()

##### 秒杀进行中

![]()

##### 秒杀未开始

![]()

##### 秒杀成功

![]()



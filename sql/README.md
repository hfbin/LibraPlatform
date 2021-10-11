# 数据库说明
先执行create-database.sql创建对应的数据库，再将对应库的文件在对应的库执行

文件说明：
```
create-database.sql 创建库sql语句
libra-base.sql      基础服务数据库
libra-gen.sql       在线开发服务数据库
libra-tr.sql        租户服务数据库
libra-ucpm.sql      用户权限服务数据库
nacos.sql           nacos数据库【里面默认包含dev、prod配置中心文件】
```
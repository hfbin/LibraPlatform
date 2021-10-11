# libra-common-mybatis 说明
此组件依赖mybatis-plus，重新实现了mybatis-plus多租户插件、公共字段填充功能

# libra-common-mybatis 接入说明
1、在对应服务pom.xml文件引入对应依赖
```xml
<dependency>
    <groupId>cn.hfbin</groupId>
    <artifactId>libra-common-mybatis</artifactId>
</dependency>
```
2、在配置文件添加对应链接数据库配置，以及一些mybatis-plus公共配置

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai
    username: root
    password: 1010
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource

# mybatis-plus 配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/*.xml
  typeAliasesPackage: cn.hfbin.*.entity
  global-config:
    banner: false
    #原生配置
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
    call-setters-on-nulls: true
    jdbc-type-for-null: 'null'
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```
这些配置目前是放在nacos配置中心，具体可以参考libra-ucpm-service服务配置
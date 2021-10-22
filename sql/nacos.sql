/*
 Navicat Premium Data Transfer

 Source Server         : node1
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 180.163.81.139:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 22/10/2021 14:53:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (6, 'common.yml', 'COMMON', 'mybatis-plus:\n  mapper-locations: classpath*:/mapper/*.xml\n  typeAliasesPackage: cn.hfbin.*.entity\n  global-config:\n    banner: false\n    #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl', 'b4ad08faa7607975134180d8216d5257', '2021-09-02 04:37:38', '2021-09-12 07:14:50', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'libra-base', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra-base?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: \n    openTenant: false\n\nlogging:\n  level:\n    cn.hfbin: info', 'cc7ea605fe0638fb5aacad8648373185', '2021-09-02 10:52:35', '2021-10-22 06:52:03', 'nacos', '202.105.107.178', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (8, 'prometheus.yml', 'COMMON', 'management:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    prometheus:\r\n      enabled: true\r\n  metrics:\r\n    export:\r\n      prometheus:\r\n        enabled: true\r\n    tags:\r\n      application: ${spring.application.name}', 'fd9c6dcc11287633e9ff3aaf070c2cb4', '2021-09-06 11:03:54', '2021-09-06 11:03:54', NULL, '0:0:0:0:0:0:0:1', '', 'dev', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (9, 'sentinel.yml', 'COMMON', 'feign:\n  # Sentinel 对 Feign 的支持开启\n  sentinel:\n    enabled: true\nspring:\n  cloud:\n    sentinel:\n      transport:\n        #配置sentinel dashboard地址\n        dashboard: 127.0.0.1:8808\n        # 指定和控制通信的端口，默认值8719，如不设置，会自动从8719开始扫描，依次+1，直到找到未被占用的端口\n        port: 8719\n        #心跳发送周期，默认值null，但在SimpleHttpHeartbeatSender会用默认值10秒\n        heartbeat-interval-ms: 10000\n      filter:\n        # spring mav端点的保护 默认true\n        enabled: true\n      #服务启动直接建立心跳连接\n      eager: true\n      # authority（授权规则），degrade（降级规则），flow（流控规则），param（热点规则），system（系统规则）\n      datasource:\n        # flow（流控规则）\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-flow-rules\n            rule-type: flow\n        # degrade（降级规则）\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-degrade-rules\n            rule-type: degrade\n        # param（热点规则）\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-param-rules\n            rule-type: param-flow\n        # system（系统规则）\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-system-rules\n            rule-type: system\n        # authority（授权规则）\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-authority-rules\n            rule-type: authority\n', 'd3e8a766d4baa42334e26404b02e20d2', '2021-09-06 11:04:24', '2021-09-08 09:47:24', '', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (10, 'redis.yml', 'COMMON', 'spring:\r\n# redis 配置\r\n  redis:\r\n    # Redis数据库索引（默认为0）\r\n    database: 0\r\n    # Redis服务器地址\r\n    host: 127.0.0.1\r\n    # Redis服务器连接密码（默认为空）\r\n    password:\r\n    # Redis服务器连接端口\r\n    port: 6379\r\n    # 连接超时时间（毫秒）\r\n    timeout: 1000\r\n    jedis:\r\n      pool:\r\n        # 连接池最大连接数（使用负值表示没有限制）\r\n        max-active: 50\r\n        # 连接池中的最大空闲连接\r\n        max-idle: 20\r\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\r\n        max-wait: -1\r\n        # 连接池中的最小空闲连接\r\n        min-idle: 0', '638bfefaf7310c08aa24a2ba7785297d', '2021-09-06 11:04:44', '2021-09-06 11:04:44', NULL, '0:0:0:0:0:0:0:1', '', 'dev', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (13, 'libra-tenant', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra-tr?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', 'e1963eac66620c9b2d47a02ab6bf02ba', '2021-09-07 00:29:11', '2021-10-22 06:52:22', 'nacos', '202.105.107.178', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (15, 'libra-ucpm', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra-ucpm?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: ucpm_datasource_conf,ucpm_interface,ucpm_menu,ucpm_menu_interface_ref,ucpm_version,ucpm_client\n    openTenant: true\n\nlogging:\n  level:\n    cn.hfbin: info', '3f1361b9c32dda08fb9a2a7ba149d1d2', '2021-09-07 00:42:51', '2021-10-22 06:52:41', 'nacos', '202.105.107.178', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (17, 'libra-auth', 'DEFAULT_GROUP', 'logging:\n  level:\n    cn.hfbin: info', 'e43074962a55163d52543cf2a767ce0f', '2021-09-07 00:46:05', '2021-09-11 02:06:55', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (19, 'libra-gateway', 'DEFAULT_GROUP', 'spring:\n  cloud:\n    # 网关服务转发配置\n    gateway:\n      discovery:\n        locator:\n          enabled: true  #开启从注册中心动态创建路由的功能，利用微服务名进行路由\n      routes:\n        - id: base # 基础服务\n          uri: lb://libra-base #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/base/** #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: ucpm # 提供用户中心、资源权限相关\n          uri: lb://libra-ucpm #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/ucpm/** #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: tenant # 租户服务\n          uri: lb://libra-tenant #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/tr/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: auth # 认证授权服务\n          uri: lb://libra-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/auth/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: gen #代码生成服务\n          uri: lb://libra-gen #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/gen/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n\nlibra:\n  gateway:\n    uri:\n      filter: \n        - /api/ucpm/depart/delete\n        - /api/ucpm/role/delete', '141fd266ce33d48212bf81ccf8e11d70', '2021-09-07 00:56:45', '2021-09-11 16:36:43', 'nacos', '0:0:0:0:0:0:0:1', '', 'dev', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (23, 'libra-ucpm-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-09-08 09:44:16', '2021-09-08 09:58:58', '', '0:0:0:0:0:0:0:1', '', 'sentinel-dev', '', '', '', 'json', '');
INSERT INTO `config_info` VALUES (24, 'libra-ucpm-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 09:44:36', '2021-09-08 09:44:36', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (25, 'libra-ucpm-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 09:44:51', '2021-09-08 09:44:51', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (26, 'libra-ucpm-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 09:45:01', '2021-09-08 09:45:01', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (27, 'libra-ucpm-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 09:45:11', '2021-09-08 09:45:11', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (30, 'libra-base-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-09-08 10:08:27', '2021-09-08 10:08:27', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (31, 'libra-base-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:27', '2021-09-08 10:08:27', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (32, 'libra-base-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:27', '2021-09-08 10:08:27', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (33, 'libra-base-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:27', '2021-09-08 10:08:27', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (34, 'libra-base-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:27', '2021-09-08 10:08:27', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (35, 'libra-auth-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-09-08 10:08:44', '2021-09-08 10:08:44', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (36, 'libra-auth-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:44', '2021-09-08 10:08:44', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (37, 'libra-auth-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:44', '2021-09-08 10:08:44', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (38, 'libra-auth-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:44', '2021-09-08 10:08:44', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (39, 'libra-auth-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:08:44', '2021-09-08 10:08:44', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (40, 'libra-tenant-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-09-08 10:09:09', '2021-09-08 10:09:09', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (41, 'libra-tenant-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:09', '2021-09-08 10:09:09', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (42, 'libra-tenant-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:09', '2021-09-08 10:09:09', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (43, 'libra-tenant-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:09', '2021-09-08 10:09:09', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (44, 'libra-tenant-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:09', '2021-09-08 10:09:09', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (45, 'libra-gateway-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-09-08 10:09:42', '2021-09-08 10:09:42', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (46, 'libra-gateway-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:42', '2021-09-08 10:09:42', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (47, 'libra-gateway-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:42', '2021-09-08 10:09:42', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (48, 'libra-gateway-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:42', '2021-09-08 10:09:42', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (49, 'libra-gateway-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-09-08 10:09:42', '2021-09-08 10:09:42', NULL, '0:0:0:0:0:0:0:1', '', 'sentinel-dev', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (69, 'common.yml', 'COMMON', 'mybatis-plus:\n  mapper-locations: classpath*:/mapper/*.xml\n  typeAliasesPackage: cn.hfbin.*.entity\n  global-config:\n    banner: false\n    #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl', 'b4ad08faa7607975134180d8216d5257', '2021-10-06 02:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', '', 'prod', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (70, 'libra-base', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra-base?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: \n    openTenant: false\n\nlogging:\n  level:\n    cn.hfbin: info', 'ec5f487e0384223fc6c688b2c4782279', '2021-10-06 02:47:48', '2021-10-08 14:12:59', 'nacos', '223.73.1.70', '', 'prod', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (71, 'prometheus.yml', 'COMMON', 'management:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    prometheus:\r\n      enabled: true\r\n  metrics:\r\n    export:\r\n      prometheus:\r\n        enabled: true\r\n    tags:\r\n      application: ${spring.application.name}', 'fd9c6dcc11287633e9ff3aaf070c2cb4', '2021-10-06 02:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', '', 'prod', NULL, NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (72, 'sentinel.yml', 'COMMON', 'feign:\n  # Sentinel 对 Feign 的支持开启\n  sentinel:\n    enabled: true\nspring:\n  cloud:\n    sentinel:\n      transport:\n        #配置sentinel dashboard地址\n        dashboard: node1:8088\n        # 指定和控制通信的端口，默认值8719，如不设置，会自动从8719开始扫描，依次+1，直到找到未被占用的端口\n        port: 8719\n        #心跳发送周期，默认值null，但在SimpleHttpHeartbeatSender会用默认值10秒\n        heartbeat-interval-ms: 10000\n      filter:\n        # spring mav端点的保护 默认true\n        enabled: true\n      #服务启动直接建立心跳连接\n      eager: true\n      # authority（授权规则），degrade（降级规则），flow（流控规则），param（热点规则），system（系统规则）\n      datasource:\n        # flow（流控规则）\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-flow-rules\n            rule-type: flow\n        # degrade（降级规则）\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-degrade-rules\n            rule-type: degrade\n        # param（热点规则）\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-param-rules\n            rule-type: param-flow\n        # system（系统规则）\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-system-rules\n            rule-type: system\n        # authority（授权规则）\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-authority-rules\n            rule-type: authority\n', 'de368f0b52a6a142f707638086cee863', '2021-10-06 02:47:48', '2021-10-06 02:49:12', 'nacos', '202.105.107.178', '', 'prod', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (73, 'redis.yml', 'COMMON', 'spring:\n# redis 配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: 127.0.0.1\n    # Redis服务器连接密码（默认为空）\n    password:\n    # Redis服务器连接端口\n    port: 6379\n    # 连接超时时间（毫秒）\n    timeout: 1000\n    jedis:\n      pool:\n        # 连接池最大连接数（使用负值表示没有限制）\n        max-active: 50\n        # 连接池中的最大空闲连接\n        max-idle: 20\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1\n        # 连接池中的最小空闲连接\n        min-idle: 0', '50f5f10318f1d546be4ca3262b50f88a', '2021-10-06 02:47:48', '2021-10-06 02:57:40', 'nacos', '202.105.107.178', '', 'prod', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (74, 'libra-tenant', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra-tr?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', '58f0c581014789783f0b47ff536e14a8', '2021-10-06 02:47:48', '2021-10-08 14:13:40', 'nacos', '223.73.1.70', '', 'prod', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (75, 'libra-ucpm', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra-ucpm?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: ucpm_datasource_conf,ucpm_interface,ucpm_menu,ucpm_menu_interface_ref,ucpm_version,ucpm_client\n    openTenant: true\n\nlogging:\n  level:\n    cn.hfbin: info', '03a717f7325a9423d1b0530f95b92af7', '2021-10-06 02:47:48', '2021-10-08 14:13:54', 'nacos', '223.73.1.70', '', 'prod', '', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (76, 'libra-auth', 'DEFAULT_GROUP', 'logging:\n  level:\n    cn.hfbin: info', 'e43074962a55163d52543cf2a767ce0f', '2021-10-06 02:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', '', 'prod', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (77, 'libra-gateway', 'DEFAULT_GROUP', 'spring:\n  cloud:\n    # 网关服务转发配置\n    gateway:\n      discovery:\n        locator:\n          enabled: true  #开启从注册中心动态创建路由的功能，利用微服务名进行路由\n      routes:\n        - id: base # 基础服务\n          uri: lb://libra-base #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/base/** #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: ucpm # 提供用户中心、资源权限相关\n          uri: lb://libra-ucpm #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/ucpm/** #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: tenant # 租户服务\n          uri: lb://libra-tenant #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/tr/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: auth # 认证授权服务\n          uri: lb://libra-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/auth/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: gen #代码生成服务\n          uri: lb://libra-gen #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/gen/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n\nlibra:\n  gateway:\n    uri:\n      filter: \n        - /api/ucpm/depart/delete\n        - /api/ucpm/role/delete', '141fd266ce33d48212bf81ccf8e11d70', '2021-10-06 02:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', '', 'prod', '', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (78, 'libra-ucpm-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (79, 'libra-ucpm-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (80, 'libra-ucpm-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (81, 'libra-ucpm-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (82, 'libra-ucpm-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (83, 'libra-base-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (84, 'libra-base-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (85, 'libra-base-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (86, 'libra-base-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (87, 'libra-base-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (88, 'libra-auth-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (89, 'libra-auth-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (90, 'libra-auth-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (91, 'libra-auth-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (92, 'libra-auth-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (93, 'libra-tenant-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (94, 'libra-tenant-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (95, 'libra-tenant-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (96, 'libra-tenant-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (97, 'libra-tenant-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (98, 'libra-gateway-flow-rules', 'SENTINEL_GROUP', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', '', NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (99, 'libra-gateway-degrade-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (100, 'libra-gateway-param-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (101, 'libra-gateway-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);
INSERT INTO `config_info` VALUES (102, 'libra-gateway-authority-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 02:48:00', '2021-10-06 02:48:00', NULL, '202.105.107.178', '', 'sentinel-prod', NULL, NULL, NULL, 'json', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(64) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 121 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 74, 'common.yml', 'COMMON', '', 'mybatis-plus:\n  mapper-locations: classpath*:/mapper/*.xml\n  typeAliasesPackage: cn.hfbin.*.entity\n  global-config:\n    banner: false\n    #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl', 'b4ad08faa7607975134180d8216d5257', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 75, 'libra-base', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: \n    openTenant: false\n\nlogging:\n  level:\n    cn.hfbin: info', '642718f7449336103182c35c70736d6c', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 76, 'prometheus.yml', 'COMMON', '', 'management:\r\n  endpoints:\r\n    web:\r\n      exposure:\r\n        include: \'*\'\r\n  endpoint:\r\n    prometheus:\r\n      enabled: true\r\n  metrics:\r\n    export:\r\n      prometheus:\r\n        enabled: true\r\n    tags:\r\n      application: ${spring.application.name}', 'fd9c6dcc11287633e9ff3aaf070c2cb4', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 77, 'sentinel.yml', 'COMMON', '', 'feign:\n  # Sentinel 对 Feign 的支持开启\n  sentinel:\n    enabled: true\nspring:\n  cloud:\n    sentinel:\n      transport:\n        #配置sentinel dashboard地址\n        dashboard: 127.0.0.1:8808\n        # 指定和控制通信的端口，默认值8719，如不设置，会自动从8719开始扫描，依次+1，直到找到未被占用的端口\n        port: 8719\n        #心跳发送周期，默认值null，但在SimpleHttpHeartbeatSender会用默认值10秒\n        heartbeat-interval-ms: 10000\n      filter:\n        # spring mav端点的保护 默认true\n        enabled: true\n      #服务启动直接建立心跳连接\n      eager: true\n      # authority（授权规则），degrade（降级规则），flow（流控规则），param（热点规则），system（系统规则）\n      datasource:\n        # flow（流控规则）\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-flow-rules\n            rule-type: flow\n        # degrade（降级规则）\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-degrade-rules\n            rule-type: degrade\n        # param（热点规则）\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-param-rules\n            rule-type: param-flow\n        # system（系统规则）\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-system-rules\n            rule-type: system\n        # authority（授权规则）\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-authority-rules\n            rule-type: authority\n', 'd3e8a766d4baa42334e26404b02e20d2', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 78, 'redis.yml', 'COMMON', '', 'spring:\r\n# redis 配置\r\n  redis:\r\n    # Redis数据库索引（默认为0）\r\n    database: 0\r\n    # Redis服务器地址\r\n    host: 127.0.0.1\r\n    # Redis服务器连接密码（默认为空）\r\n    password:\r\n    # Redis服务器连接端口\r\n    port: 6379\r\n    # 连接超时时间（毫秒）\r\n    timeout: 1000\r\n    jedis:\r\n      pool:\r\n        # 连接池最大连接数（使用负值表示没有限制）\r\n        max-active: 50\r\n        # 连接池中的最大空闲连接\r\n        max-idle: 20\r\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\r\n        max-wait: -1\r\n        # 连接池中的最小空闲连接\r\n        min-idle: 0', '638bfefaf7310c08aa24a2ba7785297d', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 79, 'libra-tenant', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', 'c54e5bc7adc6048690e53ebbdc93a51c', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 80, 'libra-ucpm', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: ucpm_datasource_conf,ucpm_interface,ucpm_menu,ucpm_menu_interface_ref,ucpm_version,ucpm_client\n    openTenant: true\n\nlogging:\n  level:\n    cn.hfbin: info', '3c176aacefca10e62b9b7b4d1f90736b', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 81, 'libra-auth', 'DEFAULT_GROUP', '', 'logging:\n  level:\n    cn.hfbin: info', 'e43074962a55163d52543cf2a767ce0f', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 82, 'libra-gateway', 'DEFAULT_GROUP', '', 'spring:\n  cloud:\n    # 网关服务转发配置\n    gateway:\n      discovery:\n        locator:\n          enabled: true  #开启从注册中心动态创建路由的功能，利用微服务名进行路由\n      routes:\n        - id: base # 基础服务\n          uri: lb://libra-base #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/base/** #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: ucpm # 提供用户中心、资源权限相关\n          uri: lb://libra-ucpm #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/ucpm/** #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: tenant # 租户服务\n          uri: lb://libra-tenant #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/tr/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: auth # 认证授权服务\n          uri: lb://libra-auth #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/auth/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n        - id: gen #代码生成服务\n          uri: lb://libra-gen #匹配后提供服务的路由地址\n          predicates:\n            - Path=/api/gen/**  #断言，路径相匹配的进行路由\n          filters:\n            - StripPrefix=1\n\nlibra:\n  gateway:\n    uri:\n      filter: \n        - /api/ucpm/depart/delete\n        - /api/ucpm/role/delete', '141fd266ce33d48212bf81ccf8e11d70', '2021-10-06 10:47:48', '2021-10-06 02:47:48', NULL, '202.105.107.178', 'I', 'prod');
INSERT INTO `his_config_info` VALUES (0, 83, 'libra-ucpm-flow-rules', 'SENTINEL_GROUP', '', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 84, 'libra-ucpm-degrade-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 85, 'libra-ucpm-param-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 86, 'libra-ucpm-system-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 87, 'libra-ucpm-authority-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 88, 'libra-base-flow-rules', 'SENTINEL_GROUP', '', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 89, 'libra-base-degrade-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 90, 'libra-base-param-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 91, 'libra-base-system-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 92, 'libra-base-authority-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 93, 'libra-auth-flow-rules', 'SENTINEL_GROUP', '', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 94, 'libra-auth-degrade-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 95, 'libra-auth-param-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 96, 'libra-auth-system-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 97, 'libra-auth-authority-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 98, 'libra-tenant-flow-rules', 'SENTINEL_GROUP', '', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 99, 'libra-tenant-degrade-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 100, 'libra-tenant-param-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 101, 'libra-tenant-system-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 102, 'libra-tenant-authority-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 103, 'libra-gateway-flow-rules', 'SENTINEL_GROUP', '', '[\n    {\n        \"resource\": \"/interface/list\",\n        \"limitApp\": \"default\",\n        \"grade\": 1,\n        \"count\": 1,\n        \"strategy\": 0,\n        \"controlBehavior\": 0,\n        \"clusterMode\": false\n    }\n]', '69112466ff1c66d85160005ecd8a433d', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 104, 'libra-gateway-degrade-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 105, 'libra-gateway-param-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 106, 'libra-gateway-system-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (0, 107, 'libra-gateway-authority-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2021-10-06 10:47:59', '2021-10-06 02:48:00', NULL, '202.105.107.178', 'I', 'sentinel-prod');
INSERT INTO `his_config_info` VALUES (72, 108, 'sentinel.yml', 'COMMON', '', 'feign:\n  # Sentinel 对 Feign 的支持开启\n  sentinel:\n    enabled: true\nspring:\n  cloud:\n    sentinel:\n      transport:\n        #配置sentinel dashboard地址\n        dashboard: 127.0.0.1:8808\n        # 指定和控制通信的端口，默认值8719，如不设置，会自动从8719开始扫描，依次+1，直到找到未被占用的端口\n        port: 8719\n        #心跳发送周期，默认值null，但在SimpleHttpHeartbeatSender会用默认值10秒\n        heartbeat-interval-ms: 10000\n      filter:\n        # spring mav端点的保护 默认true\n        enabled: true\n      #服务启动直接建立心跳连接\n      eager: true\n      # authority（授权规则），degrade（降级规则），flow（流控规则），param（热点规则），system（系统规则）\n      datasource:\n        # flow（流控规则）\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-flow-rules\n            rule-type: flow\n        # degrade（降级规则）\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-degrade-rules\n            rule-type: degrade\n        # param（热点规则）\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-param-rules\n            rule-type: param-flow\n        # system（系统规则）\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-system-rules\n            rule-type: system\n        # authority（授权规则）\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            namespace: sentinel-${spring.profiles.active}\n            groupId: SENTINEL_GROUP\n            dataId: ${spring.application.name}-authority-rules\n            rule-type: authority\n', 'd3e8a766d4baa42334e26404b02e20d2', '2021-10-06 10:49:12', '2021-10-06 02:49:12', 'nacos', '202.105.107.178', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (73, 109, 'redis.yml', 'COMMON', '', 'spring:\r\n# redis 配置\r\n  redis:\r\n    # Redis数据库索引（默认为0）\r\n    database: 0\r\n    # Redis服务器地址\r\n    host: 127.0.0.1\r\n    # Redis服务器连接密码（默认为空）\r\n    password:\r\n    # Redis服务器连接端口\r\n    port: 6379\r\n    # 连接超时时间（毫秒）\r\n    timeout: 1000\r\n    jedis:\r\n      pool:\r\n        # 连接池最大连接数（使用负值表示没有限制）\r\n        max-active: 50\r\n        # 连接池中的最大空闲连接\r\n        max-idle: 20\r\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\r\n        max-wait: -1\r\n        # 连接池中的最小空闲连接\r\n        min-idle: 0', '638bfefaf7310c08aa24a2ba7785297d', '2021-10-06 10:49:23', '2021-10-06 02:49:24', 'nacos', '202.105.107.178', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (75, 110, 'libra-ucpm', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: ucpm_datasource_conf,ucpm_interface,ucpm_menu,ucpm_menu_interface_ref,ucpm_version,ucpm_client\n    openTenant: true\n\nlogging:\n  level:\n    cn.hfbin: info', '3c176aacefca10e62b9b7b4d1f90736b', '2021-10-06 10:49:47', '2021-10-06 02:49:48', 'nacos', '202.105.107.178', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (74, 111, 'libra-tenant', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', 'c54e5bc7adc6048690e53ebbdc93a51c', '2021-10-06 10:50:04', '2021-10-06 02:50:04', 'nacos', '202.105.107.178', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (70, 112, 'libra-base', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: \n    openTenant: false\n\nlogging:\n  level:\n    cn.hfbin: info', '642718f7449336103182c35c70736d6c', '2021-10-06 10:50:19', '2021-10-06 02:50:19', 'nacos', '202.105.107.178', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (73, 113, 'redis.yml', 'COMMON', '', 'spring:\n# redis 配置\n  redis:\n    # Redis数据库索引（默认为0）\n    database: 0\n    # Redis服务器地址\n    host: node2\n    # Redis服务器连接密码（默认为空）\n    password:\n    # Redis服务器连接端口\n    port: 6379\n    # 连接超时时间（毫秒）\n    timeout: 1000\n    jedis:\n      pool:\n        # 连接池最大连接数（使用负值表示没有限制）\n        max-active: 50\n        # 连接池中的最大空闲连接\n        max-idle: 20\n        # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1\n        # 连接池中的最小空闲连接\n        min-idle: 0', '4504ef6f4ff3bbef0a67ce736cf95b0e', '2021-10-06 10:57:40', '2021-10-06 02:57:40', 'nacos', '202.105.107.178', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (70, 114, 'libra-base', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: \n    openTenant: false\n\nlogging:\n  level:\n    cn.hfbin: info', '75381418d6bfd3441a1010b44d96ba10', '2021-10-08 22:12:59', '2021-10-08 14:12:59', 'nacos', '223.73.1.70', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (74, 115, 'libra-tenant', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', 'cb08c9fdf3830d3d45a9570569d21362', '2021-10-08 22:13:11', '2021-10-08 14:13:12', 'nacos', '223.73.1.70', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (74, 116, 'libra-tenant', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra-tr?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', '58f0c581014789783f0b47ff536e14a8', '2021-10-08 22:13:39', '2021-10-08 14:13:40', 'nacos', '223.73.1.70', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (75, 117, 'libra-ucpm', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://node1:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: 123456\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: ucpm_datasource_conf,ucpm_interface,ucpm_menu,ucpm_menu_interface_ref,ucpm_version,ucpm_client\n    openTenant: true\n\nlogging:\n  level:\n    cn.hfbin: info', '07f6447922207d51b9606514c616312d', '2021-10-08 22:13:54', '2021-10-08 14:13:54', 'nacos', '223.73.1.70', 'U', 'prod');
INSERT INTO `his_config_info` VALUES (7, 118, 'libra-base', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: \n    openTenant: false\n\nlogging:\n  level:\n    cn.hfbin: info', '642718f7449336103182c35c70736d6c', '2021-10-22 14:52:05', '2021-10-22 06:52:03', 'nacos', '202.105.107.178', 'U', 'dev');
INSERT INTO `his_config_info` VALUES (13, 119, 'libra-tenant', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: tr_menu_template_ref,tr_tenant\n    openTenant: false\n  log:\n    enabled: false\n\nlogging:\n  level:\n    cn.hfbin: info\n\n  ', 'c54e5bc7adc6048690e53ebbdc93a51c', '2021-10-22 14:52:22', '2021-10-22 06:52:22', 'nacos', '202.105.107.178', 'U', 'dev');
INSERT INTO `his_config_info` VALUES (15, 120, 'libra-ucpm', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    url: jdbc:mysql://localhost:3306/libra?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai\n    username: root\n    password: \n    driver-class-name: com.mysql.cj.jdbc.Driver\n    type: com.zaxxer.hikari.HikariDataSource\n\nlibra:\n  mybatisPlus:\n    ignoreTable: ucpm_datasource_conf,ucpm_interface,ucpm_menu,ucpm_menu_interface_ref,ucpm_version,ucpm_client\n    openTenant: true\n\nlogging:\n  level:\n    cn.hfbin: info', '3c176aacefca10e62b9b7b4d1f90736b', '2021-10-22 14:52:40', '2021-10-22 06:52:41', 'nacos', '202.105.107.178', 'U', 'dev');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (3, '1', 'dev', 'libra-dev', 'libra-dev', 'nacos', 1625793754718, 1625793754718);
INSERT INTO `tenant_info` VALUES (4, '1', 'sentinel-dev', 'sentinel-dev', 'sentinel-dev', 'nacos', 1631093790306, 1631093790306);
INSERT INTO `tenant_info` VALUES (5, '1', 'sentinel-prod', 'sentinel-prod', 'sentinel-prod', 'nacos', 1633488410467, 1633488410467);
INSERT INTO `tenant_info` VALUES (7, '1', 'prod', 'libra-prod', 'libra-prod', 'nacos', 1633488446450, 1633488446450);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$.HnSDad.aV1gMpF6T56wL.a8IclgwnBCyaLiMT2.QT7P786WzqxUO', 1);

SET FOREIGN_KEY_CHECKS = 1;

CREATE DATABASE `libra_plugin` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `plugin_link`  (
  `id` bigint NOT NULL COMMENT '主键',
  `route_id` varchar(64) NULL DEFAULT NULL COMMENT 'routeId',
  `link_json` longtext NULL DEFAULT NULL COMMENT '链路json',
  `description` varchar(255) NULL DEFAULT '' COMMENT '描述',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '链路编排表';

CREATE TABLE `plugin_bgg`  (
  `id` bigint NOT NULL COMMENT '主键',
  `service_type` tinyint NULL DEFAULT NULL COMMENT '服务类型：1、网关 2、非网关',
  `service_name` varchar(128) NULL DEFAULT '' COMMENT '服务名称',
  `strategy_json` longtext NULL DEFAULT NULL COMMENT '策略json',
  `description` varchar(255) NULL DEFAULT '' COMMENT '描述',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '蓝绿灰度发布表';


CREATE TABLE `plugin_gateway_route`  (
  `id` bigint NOT NULL COMMENT '主键',
  `gateway_name` varchar(128) NULL DEFAULT '' COMMENT '网关名称',
  `uri` varchar(128) NULL DEFAULT NULL COMMENT '目标地址：lb://service、http://127.0.0.1:9990',
  `service_name` varchar(128) NULL DEFAULT '' COMMENT '服务名称',
  `predicates` longtext NULL DEFAULT NULL COMMENT '断言器',
  `filters` longtext NULL DEFAULT NULL COMMENT '过滤器',
  `metadata` longtext NULL DEFAULT NULL COMMENT '元数据',
  `order` int NULL DEFAULT 1 COMMENT '优先级',
  `description` varchar(255) NULL DEFAULT '' COMMENT '描述',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '网关路由配置表';


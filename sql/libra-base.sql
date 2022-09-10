DROP TABLE IF EXISTS `base_opt_log`;
CREATE TABLE `base_opt_log`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(64) NULL DEFAULT NULL COMMENT '用户名',
  `opt_type` tinyint(1) NULL DEFAULT NULL COMMENT '日志类型',
  `opt_behavior` tinyint(1) NULL DEFAULT NULL COMMENT '操作行为',
  `ip` varchar(128) NULL DEFAULT '' COMMENT 'IP',
  `location` varchar(128) NULL DEFAULT '' COMMENT 'IP解析地址',
  `client_code` varchar(100) NULL DEFAULT NULL COMMENT '客户端编码',
  `req_url` varchar(256) NULL DEFAULT '' COMMENT '请求地址',
  `user_agent` varchar(1024) NULL DEFAULT '' COMMENT '浏览器信息',
  `http_method` varchar(10) NULL DEFAULT '' COMMENT '请求类型(GET:GET请求;POST:POST请求;PUT:PUT请求;DELETE:DELETE请求;等)',
  `req_param` longtext NULL COMMENT '操作请求参数',
  `res_info` longtext NULL COMMENT '操作响应结果',
  `res_status` tinyint(1) NULL DEFAULT NULL COMMENT '响应状态',
  `description` varchar(255) NULL DEFAULT '' COMMENT '描述',
  `tenant_code` varchar(100) NULL DEFAULT '' COMMENT '租户编码',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '系统操作日志表';

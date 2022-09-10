DROP TABLE IF EXISTS `gen_datasource_conf`;
CREATE TABLE `gen_datasource_conf`  (
  `id` bigint(20) NOT NULL COMMENT '主键Id',
  `name` varchar(64) NULL DEFAULT NULL COMMENT '数据源名字',
  `url` varchar(255) NULL DEFAULT NULL COMMENT '链接URL',
  `username` varchar(64) NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) NULL DEFAULT NULL COMMENT '密码',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '数据源表' ;

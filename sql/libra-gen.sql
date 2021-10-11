/*
 Navicat Premium Data Transfer

 Source Server         : node1
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 180.163.81.139:3306
 Source Schema         : libra-gen

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 11/10/2021 08:56:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_datasource_conf
-- ----------------------------
DROP TABLE IF EXISTS `gen_datasource_conf`;
CREATE TABLE `gen_datasource_conf`  (
  `id` bigint(20) NOT NULL COMMENT '主键Id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名字',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接URL',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除状态(0-正常,1-已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_datasource_conf
-- ----------------------------
INSERT INTO `gen_datasource_conf` VALUES (1430800224523649025, 'gen', 'jdbc:mysql://node1:3306/libra-gen?characterEncoding=utf8&serverTimezone=GMT', 'root', 'zBfm9texMG2AhYT6oIHRuA==', NULL, '2021-08-26 15:52:11', 0, NULL, 0);
INSERT INTO `gen_datasource_conf` VALUES (1431080741055873025, 'libra', 'jdbc:mysql://node1:3306/libra?characterEncoding=utf8&serverTimezone=GMT', 'root', 'tW48KuzTeswEpMHGP9N9wA==', NULL, '2021-08-27 10:26:51', NULL, NULL, 0);
INSERT INTO `gen_datasource_conf` VALUES (1431083419358007298, 'nacos', 'jdbc:mysql://node1:3306/nacos?characterEncoding=utf8&serverTimezone=GMT', 'root', 'S7Kn3+gCf/ws9eWREiRoQA==', NULL, '2021-08-27 10:37:30', NULL, NULL, 0);
INSERT INTO `gen_datasource_conf` VALUES (1431083957956972545, 'mysql', 'jdbc:mysql://node1:3306/mysql?characterEncoding=utf8&serverTimezone=GMT', 'root', 'B8/xsqojvy3H4pMo7cmt2g==', NULL, '2021-08-27 10:39:38', NULL, NULL, 0);
INSERT INTO `gen_datasource_conf` VALUES (1445652481639718914, 'libra-ucpm', 'jdbc:mysql://node1:3306/libra-ucpm?characterEncoding=utf8&serverTimezone=GMT', 'root', 'g8u9ZwC8hMd32Q3FTRA+Lg==', NULL, '2021-10-06 15:29:45', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;

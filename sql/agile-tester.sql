/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : agile-tester

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 23/11/2021 21:38:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '产品名称',
  `team_id` bigint NOT NULL COMMENT '所属团队id',
  `memo` varchar(1024) DEFAULT NULL COMMENT '产品描述信息',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用标识，1：已启用，0：未启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识，1：已删除，0：未删除',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDU_product_name` (`name`),
  KEY `IDX_product_team_id` (`team_id`),
  KEY `IDX_product_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pid` bigint NOT NULL DEFAULT '0' COMMENT '上级团队id',
  `name` varchar(32) NOT NULL COMMENT '团队名称',
  `memo` varchar(512) DEFAULT NULL COMMENT '团队描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用标识，1：已启用，0：未启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识，1：已删除，0：未删除',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(4000) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for test_plan
-- ----------------------------
DROP TABLE IF EXISTS `test_plan`;
CREATE TABLE `test_plan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '所属产品id',
  `title` varchar(64) NOT NULL COMMENT '测试计划标题',
  `env` varchar(64) NOT NULL COMMENT '执行环境',
  `memo` varchar(512) DEFAULT NULL COMMENT '测试计划备注信息',
  `executor_ids` json NOT NULL COMMENT '执行者id列表，如：[userId1,userId2]',
  `executors` json NOT NULL COMMENT '执行者名称列表，如：["张三","李四"]',
  `total_count` int NOT NULL DEFAULT '0' COMMENT '待执行的用例总数',
  `choose_content` json NOT NULL COMMENT '用例选择规则',
  `success_count` int NOT NULL DEFAULT '0' COMMENT '测试通过的用例总数',
  `success_content` json NOT NULL COMMENT '测试通过的用例id列表，如：["case_1", "case_2"]',
  `ignore_count` int NOT NULL DEFAULT '0' COMMENT '测试跳过的用例总数',
  `ignore_content` json NOT NULL COMMENT '测试跳过的用例id列表，如：["case_1", "case_2"]',
  `block_count` int NOT NULL DEFAULT '0' COMMENT '测试阻塞的用例总数',
  `block_content` json NOT NULL COMMENT '测试阻塞的用例id列表，如：["case_1", "case_2"]',
  `failed_count` int NOT NULL DEFAULT '0' COMMENT '测试失败的用例总数',
  `failed_content` json NOT NULL COMMENT '测试失败的用例id列表，如：["case_1", "case_2"]',
  `expect_start_time` datetime NOT NULL COMMENT '预计开始时间',
  `expect_end_time` datetime NOT NULL COMMENT '预计结束时间',
  `actual_start_time` datetime DEFAULT NULL COMMENT '实际开始时间',
  `actual_end_time` datetime DEFAULT NULL COMMENT '实际结束时间',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用标识，1：已启用，0：未启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识，1：已删除，0：未删除',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDU_test_plan_title` (`title`),
  KEY `IDX_test_plan_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for test_review
-- ----------------------------
DROP TABLE IF EXISTS `test_review`;
CREATE TABLE `test_review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '所属产品',
  `title` varchar(64) NOT NULL COMMENT '测试评审标题',
  `memo` varchar(512) DEFAULT NULL COMMENT '测试评审备注信息',
  `reviewer_ids` json NOT NULL COMMENT '评审者id列表，如：[userId1,userId2]',
  `reviewers` json NOT NULL COMMENT '评审者名称列表，如：["张三","李四"]',
  `total_count` int NOT NULL DEFAULT '0' COMMENT '待评审的用例总数',
  `choose_content` json NOT NULL COMMENT '用例选择规则',
  `success_count` int NOT NULL DEFAULT '0' COMMENT '评审通过的用例总数',
  `success_content` json NOT NULL COMMENT '测试通过的用例id列表，如：["case_1", "case_2"]',
  `failed_count` int NOT NULL COMMENT '评审失败的用例总数',
  `failed_content` longtext NOT NULL COMMENT '测试失败的用例id列表，如：["case_1", "case_2"]',
  `expect_start_time` datetime NOT NULL COMMENT '预期开始时间',
  `expect_end_time` datetime NOT NULL COMMENT '预计结束时间',
  `actual_start_time` datetime DEFAULT NULL,
  `actual_end_time` datetime DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDU_test_review_title` (`title`),
  KEY `IDX_test_reiew_creator_idC8F8` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for testcase
-- ----------------------------
DROP TABLE IF EXISTS `testcase`;
CREATE TABLE `testcase` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL COMMENT '用例集标题',
  `suite_id` bigint NOT NULL COMMENT '所属集合id',
  `content` json NOT NULL COMMENT '思维导图json',
  `memo` varchar(255) DEFAULT NULL COMMENT '用例集备注信息',
  `extra` json DEFAULT NULL COMMENT '用例集扩展信息',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用标识，1：已启用，0：未启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识，1：已删除，0：未删除',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDU_testcase_title` (`title`),
  KEY `IDX_testcase_suite_id` (`suite_id`),
  KEY `IDX_testcase_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for testsuite
-- ----------------------------
DROP TABLE IF EXISTS `testsuite`;
CREATE TABLE `testsuite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '所属产品id',
  `content` json NOT NULL COMMENT '测试用例集目录树 json',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用标识，1：已启用，0：未启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识，1：已删除，0：未删除',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_testsuite_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login_name` varchar(64) NOT NULL COMMENT '登录名称',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `name` varchar(64) NOT NULL COMMENT '用户姓名',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `team_id` bigint NOT NULL COMMENT '所属团队',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用标识，1：已启用，0：未启用',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识，1：已删除，0：未删除',
  `creator_id` bigint DEFAULT NULL,
  `creator_name` varchar(512) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `updater_name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDU_s_user_login_name` (`login_name`),
  UNIQUE KEY `IDU_s_user_phone` (`phone`),
  KEY `IDX_s_user_team_id` (`team_id`),
  KEY `IDX_s_user_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;

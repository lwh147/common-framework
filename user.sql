/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : 182.92.106.123:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 27/02/2022 17:07:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` tinyint(11) NULL DEFAULT NULL,
  `profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  `version` int(10) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1234567899876546211, '李sdd', '男d', 12, '个人dsfd资料', '2021-12-09 02:13:00', '2021-12-25 15:22:07', 1, 1);
INSERT INTO `user` VALUES (1471056209262968832, '李四', '男', 34, '个人资料', '2021-12-15 17:55:05', '2021-12-15 17:55:05', 0, 0);
INSERT INTO `user` VALUES (1471766456692842496, '李四', '男', 34, '个人资料', '2021-12-17 16:57:23', '2021-12-17 16:57:23', 0, 0);
INSERT INTO `user` VALUES (1474637669035147264, '李四', '男', 34, '个人资料', '2021-12-25 15:06:32', '2021-12-25 15:22:23', 1, 0);
INSERT INTO `user` VALUES (1474641554994888704, '李四', '男', 34, '个人资料', '2021-12-25 15:21:58', '2021-12-25 15:21:58', 0, 0);

SET FOREIGN_KEY_CHECKS = 1;

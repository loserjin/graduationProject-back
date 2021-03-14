/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : canteen

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/03/2021 21:40:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `adminId` int(0) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `adminRole` int(0) NOT NULL DEFAULT 0 COMMENT '管理员角色 0为普通管理员 1为超级管理员',
  `adminName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员名',
  `adminPwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员密码',
  `adminTel` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员电话',
  `adminIdcard` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员身份证',
  `adminEmail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '管理员邮箱',
  `adminSex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员性别',
  `adminStatus` int(0) NOT NULL DEFAULT 1 COMMENT '管理员状态0为未激活，1为激活',
  `departmentId` int(0) NOT NULL COMMENT '所属饭堂ID',
  `departmentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `departmentfloorId` int(0) NOT NULL COMMENT '所属楼层',
  `departmentfloorName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `adminCreatime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '管理员创建时间',
  PRIMARY KEY (`adminId`) USING BTREE,
  INDEX `departmentId`(`departmentId`, `departmentName`) USING BTREE,
  INDEX `departmentfloorId`(`departmentfloorId`, `departmentfloorName`) USING BTREE,
  INDEX `adminId`(`adminId`, `adminName`) USING BTREE,
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`departmentId`, `departmentName`) REFERENCES `department` (`departmentId`, `departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `admin_ibfk_2` FOREIGN KEY (`departmentfloorId`, `departmentfloorName`) REFERENCES `departmentfloor` (`departmentfloorId`, `departmentfloorName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 1, 'admin', 'admin', '123456', '123456789123', '123456', '男', 1, 1, '全部', 1, '全部', '2021-03-14 11:45:54');

-- ----------------------------
-- Table structure for component
-- ----------------------------
DROP TABLE IF EXISTS `component`;
CREATE TABLE `component`  (
  `componentId` int(0) NOT NULL AUTO_INCREMENT COMMENT '配料ID',
  `componentName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配料名',
  `componentMoney` int(0) NOT NULL COMMENT '价钱',
  `componentPic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配料图片',
  PRIMARY KEY (`componentId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of component
-- ----------------------------

-- ----------------------------
-- Table structure for dailymenu
-- ----------------------------
DROP TABLE IF EXISTS `dailymenu`;
CREATE TABLE `dailymenu`  (
  `dailymenuId` int(0) NOT NULL AUTO_INCREMENT COMMENT '每日菜单ID',
  `menuId` int(0) NOT NULL COMMENT '菜的ID',
  `menuName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜名',
  `menuMoney` int(0) NOT NULL COMMENT '菜的金额',
  `menuFMoney` int(0) NOT NULL COMMENT '应付定金',
  `menuPic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜的图片',
  `departmentId` int(0) NOT NULL COMMENT '所属饭堂ID',
  `departmentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属饭堂名字',
  `departmentfoorId` int(0) NOT NULL COMMENT '所属饭堂楼层ID',
  `departmentfloorName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属饭堂楼层名字',
  `dailymenuCreatime` datetime(0) NOT NULL COMMENT '生成时间',
  PRIMARY KEY (`dailymenuId`) USING BTREE,
  INDEX `departmentfoorId`(`departmentfoorId`, `departmentfloorName`) USING BTREE,
  INDEX `departmentId`(`departmentId`, `departmentName`) USING BTREE,
  INDEX `menuId`(`menuId`, `menuName`, `menuMoney`, `menuFMoney`) USING BTREE,
  INDEX `menuPic`(`menuPic`) USING BTREE,
  CONSTRAINT `dailymenu_ibfk_1` FOREIGN KEY (`departmentfoorId`, `departmentfloorName`) REFERENCES `departmentfloor` (`departmentfloorId`, `departmentfloorName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dailymenu_ibfk_2` FOREIGN KEY (`departmentId`, `departmentName`) REFERENCES `department` (`departmentId`, `departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dailymenu_ibfk_3` FOREIGN KEY (`menuId`, `menuName`, `menuMoney`, `menuFMoney`) REFERENCES `menu` (`menuId`, `menuName`, `menuMoney`, `menuFMoney`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dailymenu_ibfk_4` FOREIGN KEY (`menuPic`) REFERENCES `menu` (`menuPic`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dailymenu
-- ----------------------------

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `departmentId` int(0) NOT NULL AUTO_INCREMENT COMMENT '饭堂ID',
  `departmentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '饭堂名称',
  PRIMARY KEY (`departmentId`) USING BTREE,
  INDEX `departmentId`(`departmentId`, `departmentName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '全部');

-- ----------------------------
-- Table structure for departmentfloor
-- ----------------------------
DROP TABLE IF EXISTS `departmentfloor`;
CREATE TABLE `departmentfloor`  (
  `departmentfloorId` int(0) NOT NULL AUTO_INCREMENT COMMENT '楼层ID',
  `departmentfloorName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '楼层名字',
  `departmentId` int(0) NOT NULL COMMENT '所属饭堂ID',
  `departmentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属饭堂名字',
  PRIMARY KEY (`departmentfloorName`) USING BTREE,
  INDEX `departmentfloorId`(`departmentfloorId`, `departmentfloorName`) USING BTREE,
  INDEX `departmentId`(`departmentId`, `departmentName`) USING BTREE,
  CONSTRAINT `departmentfloor_ibfk_1` FOREIGN KEY (`departmentId`, `departmentName`) REFERENCES `department` (`departmentId`, `departmentName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of departmentfloor
-- ----------------------------
INSERT INTO `departmentfloor` VALUES (1, '全部', 1, '全部');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menuId` int(0) NOT NULL AUTO_INCREMENT COMMENT '菜ID',
  `menuName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜名',
  `menuMoney` int(0) NOT NULL COMMENT '实际金额',
  `menuFMoney` int(0) NOT NULL COMMENT '应付定金',
  `menuPic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜的图片',
  `menuComponent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配料',
  `departmentId` int(0) NOT NULL COMMENT '所属饭堂ID',
  `departmentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属楼层',
  `departmentfloorId` int(0) NOT NULL,
  `departmentfloorName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `adminId` int(0) NOT NULL COMMENT '创建管理员ID',
  `adminName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `menuCreatime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`menuId`) USING BTREE,
  INDEX `departmentId`(`departmentId`, `departmentName`) USING BTREE,
  INDEX `departmentfloorId`(`departmentfloorId`, `departmentfloorName`) USING BTREE,
  INDEX `adminId`(`adminId`, `adminName`) USING BTREE,
  INDEX `menuId`(`menuId`, `menuName`, `menuPic`, `menuFMoney`, `menuMoney`) USING BTREE,
  INDEX `menuId_2`(`menuId`, `menuName`) USING BTREE,
  INDEX `menuId_3`(`menuId`, `menuName`, `menuMoney`, `menuFMoney`) USING BTREE,
  INDEX `menuPic`(`menuPic`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`departmentId`, `departmentName`) REFERENCES `department` (`departmentId`, `departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `menu_ibfk_2` FOREIGN KEY (`departmentfloorId`, `departmentfloorName`) REFERENCES `departmentfloor` (`departmentfloorId`, `departmentfloorName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `menu_ibfk_3` FOREIGN KEY (`adminId`, `adminName`) REFERENCES `admin` (`adminId`, `adminName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------

-- ----------------------------
-- Table structure for purchase
-- ----------------------------
DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase`  (
  `purchaseId` int(0) NOT NULL AUTO_INCREMENT,
  `purchaseType` int(0) NOT NULL COMMENT '材料类别，0为蔬菜，1为肉类',
  `purchaseName` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '材料名称',
  `purchaseTotal` float(8, 2) NOT NULL COMMENT '材料数量',
  `purchaseMoney` float(5, 2) UNSIGNED ZEROFILL NOT NULL COMMENT '材料单价',
  `purchaseTotalmoney` float(8, 2) UNSIGNED ZEROFILL NOT NULL COMMENT '创建总价',
  `departmentId` int(0) NOT NULL COMMENT '所属饭堂',
  `departmentName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `departmentfloorId` int(0) NOT NULL,
  `departmentfloorName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `purchaseCreatime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`purchaseId`) USING BTREE,
  INDEX `departmentId`(`departmentId`, `departmentName`) USING BTREE,
  INDEX `departmentfloorId`(`departmentfloorId`, `departmentfloorName`) USING BTREE,
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`departmentId`, `departmentName`) REFERENCES `department` (`departmentId`, `departmentName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `purchase_ibfk_2` FOREIGN KEY (`departmentfloorId`, `departmentfloorName`) REFERENCES `departmentfloor` (`departmentfloorId`, `departmentfloorName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchase
-- ----------------------------

-- ----------------------------
-- Table structure for userorder
-- ----------------------------
DROP TABLE IF EXISTS `userorder`;
CREATE TABLE `userorder`  (
  `userorderId` int(0) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属用户名',
  `userId` int(0) NOT NULL COMMENT '所属用户ID',
  `userorderDetail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单详情，包括菜名。价格。数量',
  `userorderStatus` int(0) NOT NULL COMMENT '0为未支付尾款，1为已支付尾款',
  `userorderIdSmoney` int(0) NOT NULL COMMENT '总价',
  `userorderIdFmoney` int(0) NOT NULL COMMENT '已经支付订金的金额',
  `userorderIdMmoney` int(0) NOT NULL COMMENT '应支付的尾款金额',
  PRIMARY KEY (`userorderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userorder
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

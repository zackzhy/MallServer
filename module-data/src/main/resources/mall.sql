/*
 Navicat Premium Data Transfer

 Source Server         : LK_Connection
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : mall

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 07/08/2021 14:28:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cart_goods
-- ----------------------------
DROP TABLE IF EXISTS `cart_goods`;
CREATE TABLE `cart_goods`  (
  `cart_goods_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `product_id` int(10) UNSIGNED NOT NULL COMMENT '商品ID',
  `product_amount` int(11) NOT NULL COMMENT '商品数量',
  `product_type_id` smallint(5) UNSIGNED NOT NULL COMMENT '商品型号ID',
  PRIMARY KEY (`cart_goods_id`) USING BTREE,
  INDEX `customer_idx`(`customer_id`) USING BTREE,
  INDEX `product_id`(`product_id`) USING BTREE,
  INDEX `cart_goods_ibfk_3`(`product_type_id`) USING BTREE,
  CONSTRAINT `cart_goods_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer_login` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cart_goods_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cart_goods_ibfk_3` FOREIGN KEY (`product_type_id`) REFERENCES `product_type` (`product_type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '购物车商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer_addr
-- ----------------------------
DROP TABLE IF EXISTS `customer_addr`;
CREATE TABLE `customer_addr`  (
  `customer_addr_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT 'customer_login表的自增ID',
  `zipcode` int(6) NOT NULL COMMENT '邮政编码',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '具体的地址门牌号',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为默认地址',
  PRIMARY KEY (`customer_addr_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  CONSTRAINT `customer_addr_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer_login` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer_inf
-- ----------------------------
DROP TABLE IF EXISTS `customer_inf`;
CREATE TABLE `customer_inf`  (
  `customer_inf_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT 'customer_login表的自增ID',
  `customer_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户真实姓名',
  `identity_card_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '证件类型：1 身份证，2 军官\r\n证，3 护照',
  `identity_card_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `mobile_phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `user_point` int(11) NOT NULL DEFAULT 0 COMMENT '用户积分',
  PRIMARY KEY (`customer_inf_id`) USING BTREE,
  INDEX `customer_userpoint`(`customer_id`, `user_point`) USING BTREE,
  CONSTRAINT `customer_inf_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer_login` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer_login
-- ----------------------------
DROP TABLE IF EXISTS `customer_login`;
CREATE TABLE `customer_login`  (
  `customer_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户登录名',
  `password` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'md5加密的密码',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`customer_id`) USING BTREE,
  INDEX `login_name_password`(`login_name`, `password`) USING BTREE COMMENT '登录名与密码联合索引',
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `customer_login_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master`  (
  `order_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_sn` bigint(20) UNSIGNED NOT NULL COMMENT '订单编号 yyyymmddnnnnnnnn',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT '下单人ID',
  `order_addr` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单信息',
  `pic_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单图片信息',
  `product_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `product_type_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品类型名',
  `product_cnt` int(10) NOT NULL COMMENT '商品数量',
  `order_money` decimal(8, 2) NOT NULL COMMENT '订单金额',
  `district_money` decimal(8, 2) NOT NULL COMMENT '优惠金额',
  `pay_money` decimal(8, 2) NOT NULL COMMENT '支付金额',
  `pay_time` timestamp(0) NOT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `order_customer_cusaddr_id`(`order_id`, `customer_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  CONSTRAINT `order_master_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer_login` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_addition_info
-- ----------------------------
DROP TABLE IF EXISTS `product_addition_info`;
CREATE TABLE `product_addition_info`  (
  `product_addition_info_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `product_id` int(10) UNSIGNED NOT NULL COMMENT '商品ID',
  `product_type_id` int(10) NOT NULL COMMENT '商品型号ID',
  PRIMARY KEY (`product_addition_info_id`) USING BTREE,
  INDEX `product_type_id`(`product_id`, `product_type_id`) USING BTREE,
  CONSTRAINT `product_addition_info_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品附加信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `category_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `product_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `product_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `category_id` smallint(5) UNSIGNED NOT NULL COMMENT '分类ID',
  `supplier_id` int(10) UNSIGNED NOT NULL COMMENT '商品的供应商ID',
  `price` decimal(8, 2) NOT NULL COMMENT '商品销售价格',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品描述',
  `inventory` int(10) NOT NULL COMMENT '商品库存',
  `pic_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品图片',
  `rest_time` timestamp(0) NULL DEFAULT NULL COMMENT '秒杀字段',
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE,
  INDEX `supplier_id`(`supplier_id`) USING BTREE,
  CONSTRAINT `product_info_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_info_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `supplier_info` (`supplier_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type`  (
  `product_type_id` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '型号id',
  `product_type_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品型号名',
  PRIMARY KEY (`product_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品型号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for punch_in
-- ----------------------------
DROP TABLE IF EXISTS `punch_in`;
CREATE TABLE `punch_in`  (
  `punch_in_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT ' 自增主键',
  `customer_id` int(10) UNSIGNED NOT NULL COMMENT '用户ID',
  `year` smallint(4) NOT NULL COMMENT '年份',
  `month` tinyint(2) NOT NULL COMMENT '月份',
  `daily_bitmap` int(255) NOT NULL COMMENT '签到图',
  PRIMARY KEY (`punch_in_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  CONSTRAINT `punch_in_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer_login` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_permission_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `permission_id` int(11) UNSIGNED NOT NULL COMMENT '权限ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`role_permission_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`permission_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supplier_info
-- ----------------------------
DROP TABLE IF EXISTS `supplier_info`;
CREATE TABLE `supplier_info`  (
  `supplier_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商名称',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商地址',
  PRIMARY KEY (`supplier_id`) USING BTREE,
  INDEX `supplier_id_name`(`supplier_id`, `supplier_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '供应商信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for product_detail_view
-- ----------------------------
DROP VIEW IF EXISTS `product_detail_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `product_detail_view` AS select row_number() OVER (ORDER BY `product_info`.`product_id` )  AS `id`,`product_category`.`category_name` AS `category_name`,`product_info`.`product_id` AS `product_id`,`product_info`.`product_name` AS `product_name`,`product_info`.`pic_url` AS `pic_url`,`product_info`.`price` AS `price`,`product_info`.`inventory` AS `inventory`,`product_info`.`description` AS `description`,`product_type`.`product_type_name` AS `product_type_name`,`supplier_info`.`supplier_name` AS `supplier_name`,`supplier_info`.`address` AS `supplier_address`,`product_info`.`rest_time` AS `rest_time` from ((((`product_info` join `supplier_info`) join `product_type`) join `product_addition_info`) join `product_category`) where ((`product_info`.`product_id` = `product_addition_info`.`product_id`) and (`product_info`.`supplier_id` = `supplier_info`.`supplier_id`) and (`product_type`.`product_type_id` = `product_addition_info`.`product_type_id`) and (`product_info`.`category_id` = `product_category`.`category_id`) and (`product_type`.`product_type_id` = `product_addition_info`.`product_type_id`));

-- ----------------------------
-- View structure for product_view
-- ----------------------------
DROP VIEW IF EXISTS `product_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`localhost` SQL SECURITY DEFINER VIEW `product_view` AS select `product_info`.`category_id` AS `category_id`,`product_info`.`product_id` AS `product_id`,`product_info`.`product_name` AS `product_name`,`product_info`.`price` AS `price`,`product_info`.`pic_url` AS `pic_url`,`supplier_info`.`supplier_name` AS `supplier_name` from (`product_info` join `supplier_info`) where (`product_info`.`supplier_id` = `supplier_info`.`supplier_id`);

SET FOREIGN_KEY_CHECKS = 1;

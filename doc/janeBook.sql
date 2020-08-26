/*
 Navicat Premium Data Transfer

 Source Server         : lghcode
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : lghcode.cn:3306
 Source Schema         : janeBook

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 26/08/2020 14:47:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章标题',
  `cont` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文章内容',
  `corpus_id` bigint(20) NULL DEFAULT NULL COMMENT '文章所属的文集',
  `word_count` bigint(20) NULL DEFAULT 0 COMMENT '文章字数',
  `read_count` bigint(20) NULL DEFAULT 0 COMMENT '阅读量',
  `like_count` bigint(20) NULL DEFAULT 0 COMMENT '点赞数量',
  `diamond_count` decimal(7, 3) NULL DEFAULT 0.000 COMMENT '简钻数量',
  `is_open_comment` int(2) NULL DEFAULT 0 COMMENT '0-打开评论，1-关闭评论',
  `access_status` int(2) NULL DEFAULT NULL COMMENT '0-公开，1-私密',
  `status` int(2) NULL DEFAULT 0 COMMENT '0--正常，1--回收，2--删除',
  `is_settle` int(1) NULL DEFAULT 1 COMMENT '0-未清算，1-已清算',
  `cache_day` int(11) NULL DEFAULT NULL COMMENT '回收站缓存天数',
  `publish_time` datetime(0) NULL DEFAULT NULL COMMENT '文章发布时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '文章更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `titleIndex`(`title`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, 'www', 'sdgf', 1, 500, 0, 0, 0.000, 0, 0, 0, 1, NULL, '2020-08-18 16:15:20', '2020-08-25 15:50:18');
INSERT INTO `article` VALUES (2, 'mysql安装', '打开mysql官网。。。', 2, 130, 2, 8, 0.000, 0, 0, 0, 1, NULL, '2020-08-14 17:23:33', '2020-08-25 15:47:11');
INSERT INTO `article` VALUES (6, '猴子四杀213', '轻微的群无多群无请问启动器', 1, 120, 0, 0, 1.202, 0, 0, 1, 1, 59, '2020-08-18 16:45:17', '2020-08-25 15:50:32');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(20) NULL DEFAULT NULL COMMENT '文章 id',
  `p_id` bigint(20) NULL DEFAULT NULL COMMENT '父评论id',
  `from_user_id` bigint(20) NULL DEFAULT NULL COMMENT '发表/回复评论的用户',
  `to_user_id` bigint(20) NULL DEFAULT NULL COMMENT '被回复的用户',
  `cont` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `like_num` int(11) NULL DEFAULT NULL COMMENT '点赞数量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1297088848819990532 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1297088848819990531, 6, 0, 1, 0, '哈哈哈无敌好吧', 0, '2020-08-22 16:33:00');

-- ----------------------------
-- Table structure for corpus
-- ----------------------------
DROP TABLE IF EXISTS `corpus`;
CREATE TABLE `corpus`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(55) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文集名称',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文集表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of corpus
-- ----------------------------
INSERT INTO `corpus` VALUES (1, 'Golang', 1, '2020-08-21 15:10:13');
INSERT INTO `corpus` VALUES (2, 'Java', 1, '2020-08-25 10:07:55');
INSERT INTO `corpus` VALUES (3, 'Linux', 1, '2020-08-25 10:08:20');

-- ----------------------------
-- Table structure for demo_user
-- ----------------------------
DROP TABLE IF EXISTS `demo_user`;
CREATE TABLE `demo_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'demo示例表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of demo_user
-- ----------------------------
INSERT INTO `demo_user` VALUES (1, 'Jam', '1231A', 1);

-- ----------------------------
-- Table structure for sms_code
-- ----------------------------
DROP TABLE IF EXISTS `sms_code`;
CREATE TABLE `sms_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `type` int(1) NULL DEFAULT NULL COMMENT '0--登录，1--重置密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信验证码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_code
-- ----------------------------
INSERT INTO `sms_code` VALUES (2, '13870474773', 'OlGNMuk9VGw49W7lfMw8eg==', 0, '2020-08-17 18:24:05');
INSERT INTO `sms_code` VALUES (3, '13870474773', '365201', 1, '2020-08-11 18:18:38');
INSERT INTO `sms_code` VALUES (4, '15079726411', '764307', 1, '2020-08-11 19:32:08');
INSERT INTO `sms_code` VALUES (5, '18146729253', 'r9kNGsLwY2FvlPGYE9rYlw==', 0, '2020-08-12 18:57:35');
INSERT INTO `sms_code` VALUES (6, '13870474773', 'LkOvhBX6BIYEPyYhTVDFFA==', 2, '2020-08-13 11:15:56');
INSERT INTO `sms_code` VALUES (7, '15079726411', 'lUn7wSrg/Djqm0fnsvl3Xg==', 2, '2020-08-13 11:26:24');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户手机号',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户登录密码',
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `sex` int(3) NOT NULL DEFAULT 0 COMMENT '0-保密，1-男，2-女',
  `birthday` date NULL DEFAULT NULL COMMENT '用户生日',
  `home_page` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户个人主页地址',
  `profile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户个人简介',
  `diamond_total` decimal(7, 3) NULL DEFAULT 0.000 COMMENT '用户的简钻数量总和',
  `is_creator` int(1) NULL DEFAULT 0 COMMENT '0--非简书创作者，1--是简书创作者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `nameIndex`(`nick_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '13870474773', 'faker01', '4QrcOUm6Wau+VuBX8g+IPg==', 'https://lgh-chat-im.oss-cn-hangzhou.aliyuncs.com/briefBook/2020-08-11/1d4ead0cde46422f966a22e739f8aec5-big_bgcolor.jpg', 2, '1997-08-12', 'lghcode.cn', '做一个快乐的程序员', 1.202, 0, '2020-08-10 10:53:18', '2020-08-17 18:51:28');
INSERT INTO `user` VALUES (2, '15079726411', 'Kngnit', '4QrcOUm6Wau+VuBX8g+IPg==', NULL, 0, NULL, NULL, NULL, 0.000, 0, NULL, '2020-08-17 19:06:53');
INSERT INTO `user` VALUES (3, '18146729253', 'lGh', NULL, 'http://lgh-chat-im.oss-cn-hangzhou.aliyuncs.com/briefBook/2020-08-12/d1b9233040ff48ff814fbb1438430935-timg.jpg', 0, NULL, NULL, NULL, 0.000, 0, '2020-08-12 18:58:43', '2020-08-12 19:03:37');

-- ----------------------------
-- Table structure for user_action
-- ----------------------------
DROP TABLE IF EXISTS `user_action`;
CREATE TABLE `user_action`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `action` int(2) NULL DEFAULT NULL COMMENT '0-关注，1-发表，2-赞，3-评论，4-收藏，5-赞赏，6-注册，7-订阅',
  `obj_id` bigint(20) NULL DEFAULT NULL COMMENT '对象id',
  `type` int(2) NULL DEFAULT NULL COMMENT '0-用户，1-文章，2-评论，3-文集',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户动态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_action
-- ----------------------------
INSERT INTO `user_action` VALUES (1, 1, 6, NULL, 0, '2020-08-23 16:04:47');
INSERT INTO `user_action` VALUES (2, 2, 6, NULL, 0, '2020-08-23 16:04:50');
INSERT INTO `user_action` VALUES (3, 3, 6, NULL, 0, '2020-08-23 16:04:52');
INSERT INTO `user_action` VALUES (4, 3, 0, 1, 0, '2020-08-23 16:05:06');
INSERT INTO `user_action` VALUES (5, 2, 0, 1, 0, '2020-08-23 16:05:08');
INSERT INTO `user_action` VALUES (6, 2, 1, 1, 1, '2020-08-23 16:05:17');
INSERT INTO `user_action` VALUES (7, 1, 1, 2, 1, '2020-08-23 16:05:18');
INSERT INTO `user_action` VALUES (8, 1, 1, 6, 1, '2020-08-23 16:05:27');
INSERT INTO `user_action` VALUES (9, 1, 2, 2, 1, '2020-08-23 16:05:39');
INSERT INTO `user_action` VALUES (10, 1, 2, 6, 1, '2020-08-23 16:05:40');
INSERT INTO `user_action` VALUES (11, 1, 3, 1297088848819990531, 2, '2020-08-23 16:05:51');
INSERT INTO `user_action` VALUES (12, 1, 4, 6, 1, '2020-08-23 16:06:00');
INSERT INTO `user_action` VALUES (13, 2, 5, 2, 1, '2020-08-23 16:06:02');
INSERT INTO `user_action` VALUES (14, 3, 5, 2, 1, '2020-08-23 16:06:04');
INSERT INTO `user_action` VALUES (15, 1, 5, 6, 1, '2020-08-23 16:06:11');

-- ----------------------------
-- Table structure for user_article
-- ----------------------------
DROP TABLE IF EXISTS `user_article`;
CREATE TABLE `user_article`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `article_id` bigint(20) NULL DEFAULT NULL COMMENT '文章id',
  `type` int(4) NULL DEFAULT 0 COMMENT '0--用户发布文章，1--用户点赞文章，2--用户赞赏文章，3-用户收藏文章',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-文章 关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_article
-- ----------------------------
INSERT INTO `user_article` VALUES (1, 2, 1, 0, '2020-08-14 17:21:12');
INSERT INTO `user_article` VALUES (2, 1, 2, 0, '2020-08-14 17:21:15');
INSERT INTO `user_article` VALUES (3, 1, 6, 0, '2020-08-18 16:45:17');
INSERT INTO `user_article` VALUES (4, 2, 2, 2, '2020-08-04 22:47:59');
INSERT INTO `user_article` VALUES (5, 3, 2, 2, '2020-08-11 22:48:02');
INSERT INTO `user_article` VALUES (7, 1, 2, 1, '2020-08-22 11:20:34');
INSERT INTO `user_article` VALUES (9, 1, 6, 3, '2020-08-22 11:33:29');
INSERT INTO `user_article` VALUES (11, 1, 6, 1, '2020-08-22 12:11:24');
INSERT INTO `user_article` VALUES (12, 1, 6, 2, '2020-08-22 14:32:09');

-- ----------------------------
-- Table structure for user_corpus
-- ----------------------------
DROP TABLE IF EXISTS `user_corpus`;
CREATE TABLE `user_corpus`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `corpus_id` bigint(20) NULL DEFAULT NULL COMMENT '文集id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1297742974006919172 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-关注-文集 关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_corpus
-- ----------------------------
INSERT INTO `user_corpus` VALUES (1, 2, 1, '2020-08-24 09:53:19');
INSERT INTO `user_corpus` VALUES (1297742974006919171, 1, 1, '2020-08-24 11:53:07');

-- ----------------------------
-- Table structure for user_fans
-- ----------------------------
DROP TABLE IF EXISTS `user_fans`;
CREATE TABLE `user_fans`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `fans_id` bigint(20) NULL DEFAULT NULL COMMENT '粉丝id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户粉丝关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_fans
-- ----------------------------
INSERT INTO `user_fans` VALUES (7, 3, 1, '2020-08-12 13:13:13');
INSERT INTO `user_fans` VALUES (8, 2, 1, '2020-08-21 18:13:56');

-- ----------------------------
-- Table structure for user_like_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_like_comment`;
CREATE TABLE `user_like_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `comment_id` bigint(20) NULL DEFAULT NULL COMMENT '评论id',
  `like_time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-点赞-评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_like_comment
-- ----------------------------
INSERT INTO `user_like_comment` VALUES (1, 1, 1, '2020-08-26 14:46:49');

SET FOREIGN_KEY_CHECKS = 1;

/*
 Navicat MySQL Data Transfer

 Source Server         : baitianyu
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : holyins

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 17/05/2021 10:33:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comments
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `comment_id` int NOT NULL AUTO_INCREMENT COMMENT '评论的id',
  `comment_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论的文字',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `comment_user_id` int NULL DEFAULT NULL COMMENT '评论的人',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (19, '评论', '2021-04-19 22:12:38', 1);

-- ----------------------------
-- Table structure for forwarding
-- ----------------------------
DROP TABLE IF EXISTS `forwarding`;
CREATE TABLE `forwarding`  (
  `forward_id` int NOT NULL AUTO_INCREMENT COMMENT '转发的id',
  `forward_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `forward_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `forward_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`forward_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of forwarding
-- ----------------------------
INSERT INTO `forwarding` VALUES (2, '1', '1号用户又转发了', '2021-04-04 16:42:34');
INSERT INTO `forwarding` VALUES (7, '1', '1号用户转发', '2021-04-04 21:49:48');

-- ----------------------------
-- Table structure for hashtags
-- ----------------------------
DROP TABLE IF EXISTS `hashtags`;
CREATE TABLE `hashtags`  (
  `hashtag_id` int NOT NULL AUTO_INCREMENT,
  `hashtag_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`hashtag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hashtags
-- ----------------------------
INSERT INTO `hashtags` VALUES (1, '第一条测试');
INSERT INTO `hashtags` VALUES (5, '这是第三条hashtag');

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `like_user_id` int NULL DEFAULT NULL,
  `like_id` int NOT NULL AUTO_INCREMENT,
  `date_created` datetime NULL DEFAULT NULL COMMENT '何时创建',
  `date_updated` datetime NULL DEFAULT NULL COMMENT '上次的更新时间',
  PRIMARY KEY (`like_id`) USING BTREE,
  INDEX `user_id`(`like_user_id`) USING BTREE,
  INDEX `photo_id`(`like_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of likes
-- ----------------------------
INSERT INTO `likes` VALUES (5, 90, '2021-05-13 11:48:49', '2021-05-13 11:48:49');

-- ----------------------------
-- Table structure for logininfo
-- ----------------------------
DROP TABLE IF EXISTS `logininfo`;
CREATE TABLE `logininfo`  (
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `e_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`e_mail`, `account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of logininfo
-- ----------------------------
INSERT INTO `logininfo` VALUES ('672053', '1748383809@qq.com', '123', 'baige');

-- ----------------------------
-- Table structure for loginlog
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog`  (
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `login_time` datetime NULL DEFAULT NULL,
  `device_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `log_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`log_id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of loginlog
-- ----------------------------

-- ----------------------------
-- Table structure for photo_forward
-- ----------------------------
DROP TABLE IF EXISTS `photo_forward`;
CREATE TABLE `photo_forward`  (
  `forward_id` int NULL DEFAULT NULL,
  `photo_id` int NULL DEFAULT NULL,
  INDEX `forward_id`(`forward_id`) USING BTREE,
  INDEX `user_id`(`photo_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of photo_forward
-- ----------------------------
INSERT INTO `photo_forward` VALUES (2, 1);
INSERT INTO `photo_forward` VALUES (7, 3);

-- ----------------------------
-- Table structure for photo_hashtags
-- ----------------------------
DROP TABLE IF EXISTS `photo_hashtags`;
CREATE TABLE `photo_hashtags`  (
  `photo_id` int NULL DEFAULT NULL,
  `hashtag_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of photo_hashtags
-- ----------------------------
INSERT INTO `photo_hashtags` VALUES (1, 1);
INSERT INTO `photo_hashtags` VALUES (3, 5);

-- ----------------------------
-- Table structure for photo_likes
-- ----------------------------
DROP TABLE IF EXISTS `photo_likes`;
CREATE TABLE `photo_likes`  (
  `like_id` int NULL DEFAULT NULL,
  `photo_id` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of photo_likes
-- ----------------------------
INSERT INTO `photo_likes` VALUES (90, 16);

-- ----------------------------
-- Table structure for photos
-- ----------------------------
DROP TABLE IF EXISTS `photos`;
CREATE TABLE `photos`  (
  `photo_id` int NOT NULL AUTO_INCREMENT COMMENT '照片id',
  `user_id` int NULL DEFAULT NULL COMMENT '拥有该照片的用户id',
  `photo_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '照片标题',
  `photo_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径',
  `photo_size` int NULL DEFAULT NULL COMMENT '大小',
  `date_created` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `date_updated` datetime NULL DEFAULT NULL COMMENT '上次的更新日期',
  PRIMARY KEY (`photo_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of photos
-- ----------------------------
INSERT INTO `photos` VALUES (1, 1, '这是外太空吧', '/pictures/head_pic/defaultheadpic.jpg', 123, '2021-04-03 14:53:44', '2021-04-03 14:53:50');
INSERT INTO `photos` VALUES (3, 2, '这是外太空', '/pictures/head_pic/defaultheadpic.jpg', 23, '2021-04-17 11:35:22', '2021-04-17 11:35:24');
INSERT INTO `photos` VALUES (5, 1, '666666', '/pictures/head_pic/defaultheadpic.jpg', 200390, '2021-04-24 17:45:04', '2021-04-24 17:45:04');
INSERT INTO `photos` VALUES (8, 1, '太空', '/pictures/head_pic/defaultheadpic.jpg', 4095327, '2021-04-24 18:43:34', '2021-04-24 18:43:34');
INSERT INTO `photos` VALUES (16, 5, '太空啊', '/pictures/photo/665199f208c045a4ab3b49ff584d266c.jpg', 200390, '2021-04-27 20:32:29', '2021-04-27 20:32:29');
INSERT INTO `photos` VALUES (17, 5, 'zhegedalaji\n', '/pictures/photo/5395111225bf40afaca15b1eabdf5777.jpg', 200390, '2021-04-29 19:41:36', '2021-04-29 19:41:36');
INSERT INTO `photos` VALUES (18, 5, '1231', '/pictures/photo/6edac6b7dc7441ab8f92ae82d20dd085.png', 1467322, '2021-04-29 19:52:41', '2021-04-29 19:52:41');
INSERT INTO `photos` VALUES (19, 5, '太空啊', '/pictures/photo/e6400cb6a02b4313bd3f4b29d050c778.png', 1467322, '2021-04-29 21:38:47', '2021-04-29 21:38:47');
INSERT INTO `photos` VALUES (20, 5, '2', '/pictures/photo/7622fb1c7d5f4b4da7c87961e38dd029.jpg', 200381, '2021-04-29 21:38:53', '2021-04-29 21:38:53');
INSERT INTO `photos` VALUES (21, 5, '4', '/pictures/photo/4c56bbf74da243908721d0825a6b0f72.jpg', 200390, '2021-04-29 21:38:58', '2021-04-29 21:38:58');
INSERT INTO `photos` VALUES (22, 5, '111111', '/pictures/photo/e01750228db4402e8f6bb15f8d957f5b.png', 1467322, '2021-04-30 13:39:18', '2021-04-30 13:39:18');

-- ----------------------------
-- Table structure for photos_comments
-- ----------------------------
DROP TABLE IF EXISTS `photos_comments`;
CREATE TABLE `photos_comments`  (
  `photo_id` int NULL DEFAULT NULL,
  `comment_id` int NULL DEFAULT NULL,
  INDEX `photo_id`(`photo_id`) USING BTREE,
  INDEX `comment_id`(`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of photos_comments
-- ----------------------------
INSERT INTO `photos_comments` VALUES (3, 19);

-- ----------------------------
-- Table structure for subscribes
-- ----------------------------
DROP TABLE IF EXISTS `subscribes`;
CREATE TABLE `subscribes`  (
  `now_user_id` int NULL DEFAULT NULL COMMENT '当前用户的id',
  `subscribe_userid` int NULL DEFAULT NULL COMMENT '关注的用户的id',
  `subscribe_time` datetime NULL DEFAULT NULL COMMENT '关注时间',
  INDEX `now_userid`(`now_user_id`) USING BTREE,
  INDEX `subscribe_userid`(`subscribe_userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of subscribes
-- ----------------------------
INSERT INTO `subscribes` VALUES (2, 1, '2021-04-18 22:34:26');
INSERT INTO `subscribes` VALUES (1, 1, '2021-04-23 22:55:03');
INSERT INTO `subscribes` VALUES (5, 1, '2021-04-27 22:28:23');
INSERT INTO `subscribes` VALUES (5, 2, '2021-04-27 22:31:49');

-- ----------------------------
-- Table structure for user_dynamic_attribute
-- ----------------------------
DROP TABLE IF EXISTS `user_dynamic_attribute`;
CREATE TABLE `user_dynamic_attribute`  (
  `user_id` int NOT NULL COMMENT 'user_id',
  `subscribers_count` int NULL DEFAULT NULL COMMENT '订阅的人数',
  `posts_count` int NULL DEFAULT NULL COMMENT '发表的帖子的数量',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_dynamic_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `e_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `headPicturePath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `headPictureSize` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `registerTime` datetime NULL DEFAULT NULL,
  `user_id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  UNIQUE INDEX `e_mail`(`e_mail`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('bai', '111', '156', '/pictures/head_pic/defaultheadpic.jpg', '111', '111', '2021-04-03 14:02:14', 1);
INSERT INTO `userinfo` VALUES ('bai2', '123', '231', '/pictures/head_pic/defaultheadpic.jpg', '212', '112', '2021-04-03 14:03:35', 2);
INSERT INTO `userinfo` VALUES ('baige', '672053', '1748383809@qq.com', '/pictures/head_pic/6584f1e3bdd445dcba6d499664a078c0.jpg', '18584', '男孩子', '2021-04-27 17:42:26', 5);

SET FOREIGN_KEY_CHECKS = 1;

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

 Date: 08/07/2021 12:48:01
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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES (26, '真好', '2021-07-05 14:23:21', 10);
INSERT INTO `comments` VALUES (27, '这那好哦', '2021-07-06 10:51:42', 10);

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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of forwarding
-- ----------------------------
INSERT INTO `forwarding` VALUES (18, '10', '真好', '2021-07-06 10:34:52');
INSERT INTO `forwarding` VALUES (19, '10', '测试转发', '2021-07-06 10:56:03');

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
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of likes
-- ----------------------------
INSERT INTO `likes` VALUES (10, 101, '2021-07-05 12:24:43', '2021-07-05 12:24:43');
INSERT INTO `likes` VALUES (10, 105, '2021-07-06 10:53:34', '2021-07-06 10:53:34');

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
INSERT INTO `logininfo` VALUES ('156452', '1748383809@qq.com', 'bai20010129', 'baitianyu147258');
INSERT INTO `logininfo` VALUES ('884203', '746652478@qq.com', 'bai20010129', 'baigege2');
INSERT INTO `logininfo` VALUES ('102192', 'baitianyu.epic@foxmail.com', '123456', 'baitianyuepic');

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
INSERT INTO `photo_forward` VALUES (18, 25);
INSERT INTO `photo_forward` VALUES (19, 27);

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
INSERT INTO `photo_likes` VALUES (101, 25);
INSERT INTO `photo_likes` VALUES (105, 27);

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of photos
-- ----------------------------
INSERT INTO `photos` VALUES (24, 11, 'this', '/pictures/photo/a4508eeaa0c94f0e9c1f66fb254dcafa.jpg', 200390, '2021-07-01 15:17:40', '2021-07-01 15:17:40');
INSERT INTO `photos` VALUES (25, 11, '我发的东西', '/pictures/photo/afd0da81975242429a6dc1fd15b502e9.png', 1467322, '2021-07-01 15:19:28', '2021-07-01 15:19:28');
INSERT INTO `photos` VALUES (27, 10, '我发的东西2', '/pictures/photo/086a53ce4e2245f4b63e55e7a7a536e1.png', 1467322, '2021-07-02 09:29:41', '2021-07-02 09:29:41');
INSERT INTO `photos` VALUES (29, 10, '发表测试', '/pictures/photo/5c77c03e365d4f3483a69f192740fc62.jpg', 200390, '2021-07-06 11:58:00', '2021-07-06 11:58:00');

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
INSERT INTO `photos_comments` VALUES (25, 26);
INSERT INTO `photos_comments` VALUES (27, 27);

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
INSERT INTO `subscribes` VALUES (11, 10, '2021-07-01 15:16:34');
INSERT INTO `subscribes` VALUES (10, 11, '2021-07-05 12:24:30');

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
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('baitianyu147258', '156452', '1748383809@qq.com', '/pictures/head_pic/d24d60175583418ebf0b234c3cb01370.png', '1467322', '男孩子', '2021-07-01 15:11:57', 10);
INSERT INTO `userinfo` VALUES ('baigege2', '884203', '746652478@qq.com', '/pictures/head_pic/defaultheadpic.jpg', '50', '男孩子', '2021-07-01 15:16:06', 11);
INSERT INTO `userinfo` VALUES ('baitianyuepic', '102192', 'baitianyu.epic@foxmail.com', '/pictures/head_pic/defaultheadpic.jpg', '50', '男孩子', '2021-07-06 09:24:37', 12);

SET FOREIGN_KEY_CHECKS = 1;

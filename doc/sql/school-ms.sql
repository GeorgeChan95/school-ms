/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 106.14.137.255:3306
 Source Schema         : school-ms

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 17/11/2020 09:10:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_course
-- ----------------------------
DROP TABLE IF EXISTS `sys_course`;
CREATE TABLE `sys_course`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程代码',
  `publish_flag` tinyint(0) NULL DEFAULT 0 COMMENT '是否发布 0-为未发布  1-已发布',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '选课开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '选课截止时间',
  `remark` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_course
-- ----------------------------
INSERT INTO `sys_course` VALUES ('1', '数据结构', 'sjjg', 1, '2020-08-01 00:00:00', '2020-08-18 00:00:00', '数据结构必修课', '2020-08-10 21:53:42', '2020-08-13 22:21:28', 0);
INSERT INTO `sys_course` VALUES ('1293181487205695490', '测试课程', 'test111', 1, '2020-08-04 00:00:00', '2020-09-25 00:00:00', '加个备注', '2020-08-11 21:44:28', '2020-08-11 22:43:17', 0);
INSERT INTO `sys_course` VALUES ('2', '算法', 'sf', 0, '2020-08-01 00:00:00', '2020-08-30 00:00:00', '算法课', '2020-08-10 21:54:16', '2020-08-12 19:57:21', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告名称',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '公告内容',
  `target` tinyint(0) NULL DEFAULT 3 COMMENT '公告对象  1-学生  2-老师  3-全体人员',
  `status` tinyint(0) NULL DEFAULT 0 COMMENT '发布状态 0-未发布  1-已发布',
  `release_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '校园公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1297096364831875073', '开学提示', '经校委会一致决定，2020年秋季开学时间定位2020年9月1日。本次通知真实有效，请各位同学，给位老师做好开学准备。', 3, 1, '2020-08-22 17:02:26', '2020-08-22 17:00:47', '2020-08-23 17:14:02', 0);

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '代号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `type` tinyint(0) NULL DEFAULT 1 COMMENT '类型 1-院 2-系 3-专业',
  `parent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '父节点id',
  `sort` tinyint(0) NULL DEFAULT 0 COMMENT '排序',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '院系组织结构表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '01', '商学院', 1, '0', 0, '2020-08-10 12:26:09', NULL, 0);
INSERT INTO `sys_organization` VALUES ('1292789171529760770', '02', '信息学院', 1, '0', 2, '2020-08-10 19:45:32', NULL, 0);
INSERT INTO `sys_organization` VALUES ('1292789334906290178', '0201', '计算机系', 2, '1292789171529760770', 1, '2020-08-10 19:46:11', NULL, 0);
INSERT INTO `sys_organization` VALUES ('1292790526751326209', '020101', '软件工程专业', 3, '1292789334906290178', 1, '2020-08-10 19:50:55', NULL, 0);
INSERT INTO `sys_organization` VALUES ('1293041460391731202', '03', '文学院', 1, '0', 3, '2020-08-11 12:28:03', NULL, 0);
INSERT INTO `sys_organization` VALUES ('1293041521880227842', '0301', '中文系', 2, '1293041460391731202', 1, '2020-08-11 12:28:17', NULL, 0);
INSERT INTO `sys_organization` VALUES ('1293041702499540994', '030102', '书法', 3, '1293041521880227842', 1, '2020-08-11 12:29:00', NULL, 0);
INSERT INTO `sys_organization` VALUES ('2', '0102', '国际贸易系', 2, '1', 0, '2020-08-10 12:26:38', NULL, 0);
INSERT INTO `sys_organization` VALUES ('3', '010201', '市场营销', 3, '2', 0, '2020-08-10 12:27:05', NULL, 0);

-- ----------------------------
-- Table structure for sys_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_resources`;
CREATE TABLE `sys_resources`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `type` tinyint(0) NULL DEFAULT 1 COMMENT '资源类型（1-菜单  2-按钮）',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源url',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `parent_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '父节点id',
  `sort` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '排序',
  `external` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '是否外部链接（0-否  1-是）',
  `status` tinyint(0) UNSIGNED NULL DEFAULT 0 COMMENT '状态（0-启动  2-禁用）',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统资源表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_resources
-- ----------------------------
INSERT INTO `sys_resources` VALUES ('1292019753954648065', '用户添加', 2, '/api/user/save', 'sys:user:add', '3', 1, 0, 0, '', '2020-08-08 16:48:09', '2020-08-12 23:04:06', 0);
INSERT INTO `sys_resources` VALUES ('1292020081018085378', '用户删除', 2, '/api/user/delUser', 'sys:user:del', '3', 3, 0, 0, '', '2020-08-08 16:49:26', '2020-08-08 23:32:12', 0);
INSERT INTO `sys_resources` VALUES ('1292020309360189442', '用户编辑', 2, '/api/user/save', 'sys:user:edit', '3', 3, 0, 0, '', '2020-08-08 16:50:21', '2020-08-08 20:35:23', 0);
INSERT INTO `sys_resources` VALUES ('1292126089111441409', '学生平台', 1, '', '', '0', 3, 0, 0, 'layui-icon layui-icon-app', '2020-08-08 23:50:41', '2020-08-12 23:00:16', 0);
INSERT INTO `sys_resources` VALUES ('1292126193788686337', '学生列表', 1, '/index/student/list', '', '1292796212302364674', 3, 0, 0, '', '2020-08-08 23:51:06', '2020-08-12 23:02:27', 0);
INSERT INTO `sys_resources` VALUES ('1292672434326999042', '组织管理', 1, '/index/sys/organization', '', '2', 4, 0, 0, '', '2020-08-10 12:01:41', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1292796212302364674', '教务中心', 1, '', '', '0', 2, 0, 0, 'layui-icon layui-icon-read', '2020-08-10 20:13:31', '2020-08-12 22:54:08', 0);
INSERT INTO `sys_resources` VALUES ('1292796427587600386', '课程管理', 1, '/index/teach/course', '', '1292796212302364674', 1, 0, 0, '', '2020-08-10 20:14:22', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293529265689636865', '课程添加', 2, '/api/course/save', 'sys:course:add', '1292796427587600386', 1, 0, 0, '', '2020-08-12 20:46:23', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293529488151326721', '课程删除', 2, '/api/course/delete', 'sys:course:del', '1292796427587600386', 2, 0, 0, '', '2020-08-12 20:47:16', '2020-08-12 20:47:30', 0);
INSERT INTO `sys_resources` VALUES ('1293529702870331394', '课程编辑', 2, '/api/course/edit', 'sys:course:edit', '1292796427587600386', 3, 0, 0, '', '2020-08-12 20:48:07', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293529889550413826', '课程选择', 2, '/api/course/select', 'sys:course:select', '1292796427587600386', 4, 0, 0, '', '2020-08-12 20:48:52', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293530018529456129', '取消选课', 2, '/api/course/unselect', 'sys:course:unselect', '1292796427587600386', 5, 0, 0, '', '2020-08-12 20:49:23', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293530196355362818', '课程发布', 2, '/api/course/publish', 'sys:course:publish', '1292796427587600386', 6, 0, 0, '', '2020-08-12 20:50:05', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293530372918784001', '取消发布', 2, '/api/course/unpublish', 'sys:course:unpublish', '1292796427587600386', 7, 0, 0, '', '2020-08-12 20:50:47', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1293561826721722370', '成绩评定', 1, '/index/score/list', '', '1292796212302364674', 2, 0, 0, '', '2020-08-12 22:55:46', '2020-08-12 23:03:32', 0);
INSERT INTO `sys_resources` VALUES ('1293563250763427841', '成绩查询', 1, '/index/record/view', '', '1292126089111441409', 1, 0, 0, '', '2020-08-12 23:01:26', '2020-08-13 22:54:07', 0);
INSERT INTO `sys_resources` VALUES ('1294279545603559426', '校务通知', 1, '', '', '0', 4, 0, 0, 'layui-icon layui-icon-notice', '2020-08-14 22:27:45', NULL, 0);
INSERT INTO `sys_resources` VALUES ('1294280141127618562', '公告管理', 1, '/index/notice/view', '', '1294279545603559426', 0, 0, 0, '', '2020-08-14 22:30:07', NULL, 0);
INSERT INTO `sys_resources` VALUES ('2', '系统管理', 1, '', '', '0', 1, 0, 0, 'layui-icon layui-icon-set', '2020-08-01 20:06:38', '2020-08-12 23:06:12', 0);
INSERT INTO `sys_resources` VALUES ('3', '用户管理', 1, '/index/sys/user', 'sys:user', '2', 1, 0, 0, NULL, '2020-08-01 20:10:35', NULL, 0);
INSERT INTO `sys_resources` VALUES ('4', '角色管理', 1, '/index/sys/role', 'sys:role', '2', 1, 0, 0, NULL, '2020-08-01 20:10:35', NULL, 0);
INSERT INTO `sys_resources` VALUES ('5', '资源管理', 1, '/index/sys/menu', 'sys:resource', '2', 1, 0, 0, NULL, '2020-08-01 20:10:35', '2020-08-07 20:30:53', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色备注',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'root', '超级管理员', '所有权限拥有者', '2017-12-20 16:40:24', '2020-08-09 16:16:12', 0);
INSERT INTO `sys_role` VALUES ('1292385607829512194', 'test', '测试', '11', '2020-08-09 17:01:55', NULL, 0);
INSERT INTO `sys_role` VALUES ('1293050267201183745', 'manager', '校园管理员', '师生数据管理员', '2020-08-11 13:03:02', NULL, 0);
INSERT INTO `sys_role` VALUES ('1293050421316689921', 'student', '学生', '普通学生', '2020-08-11 13:03:39', NULL, 0);
INSERT INTO `sys_role` VALUES ('1293050543450628097', 'teacher', '老师', '校园老师', '2020-08-11 13:04:08', NULL, 0);

-- ----------------------------
-- Table structure for sys_role_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resources`;
CREATE TABLE `sys_role_resources`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色表主键',
  `resources_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源表主键',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_resources
-- ----------------------------
INSERT INTO `sys_role_resources` VALUES ('1292388348626845697', '1292385607829512194', '1292126089111441409', '2020-08-09 17:12:48', NULL);
INSERT INTO `sys_role_resources` VALUES ('1292388348635234306', '1292385607829512194', '1292126193788686337', '2020-08-09 17:12:48', NULL);
INSERT INTO `sys_role_resources` VALUES ('1292388348635234307', '1292385607829512194', '2', '2020-08-09 17:12:48', NULL);
INSERT INTO `sys_role_resources` VALUES ('1292388348635234308', '1292385607829512194', '3', '2020-08-09 17:12:48', NULL);
INSERT INTO `sys_role_resources` VALUES ('1292388348635234309', '1292385607829512194', '1292020081018085378', '2020-08-09 17:12:48', NULL);
INSERT INTO `sys_role_resources` VALUES ('1292388348635234310', '1292385607829512194', '1292020309360189442', '2020-08-09 17:12:48', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625925632002', '1293050421316689921', '1292126089111441409', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625934020610', '1293050421316689921', '1293563250763427841', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625934020611', '1293050421316689921', '1292796212302364674', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625934020612', '1293050421316689921', '1292126193788686337', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625934020613', '1293050421316689921', '1292796427587600386', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625934020614', '1293050421316689921', '1293529889550413826', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928625934020615', '1293050421316689921', '1293530018529456129', '2020-08-13 23:13:19', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252802', '1293050543450628097', '1292796212302364674', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252803', '1293050543450628097', '1292126193788686337', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252804', '1293050543450628097', '1292796427587600386', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252805', '1293050543450628097', '1293529265689636865', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252806', '1293050543450628097', '1293529488151326721', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252807', '1293050543450628097', '1293529702870331394', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252808', '1293050543450628097', '1293530196355362818', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252809', '1293050543450628097', '1293530372918784001', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1293928702786252810', '1293050543450628097', '1293561826721722370', '2020-08-13 23:13:38', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656193', '1293050267201183745', '1292126089111441409', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656194', '1293050267201183745', '1293563250763427841', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656195', '1293050267201183745', '1292796212302364674', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656196', '1293050267201183745', '1292126193788686337', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656197', '1293050267201183745', '1292796427587600386', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656198', '1293050267201183745', '1293529265689636865', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656199', '1293050267201183745', '1293529488151326721', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656200', '1293050267201183745', '1293529702870331394', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656201', '1293050267201183745', '1293530196355362818', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656202', '1293050267201183745', '1293530372918784001', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656203', '1293050267201183745', '1294279545603559426', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656204', '1293050267201183745', '1294280141127618562', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656205', '1293050267201183745', '2', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656206', '1293050267201183745', '1292672434326999042', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656207', '1293050267201183745', '3', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656208', '1293050267201183745', '1292019753954648065', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656209', '1293050267201183745', '1292020081018085378', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280317531656210', '1293050267201183745', '1292020309360189442', '2020-08-14 22:30:49', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339023269889', '1', '1292126089111441409', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339023269890', '1', '1293563250763427841', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339023269891', '1', '1292796212302364674', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339023269892', '1', '1292126193788686337', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339023269893', '1', '1292796427587600386', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339023269894', '1', '1293529265689636865', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852801', '1', '1293529488151326721', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852802', '1', '1293529702870331394', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852803', '1', '1293529889550413826', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852804', '1', '1293530018529456129', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852805', '1', '1293530196355362818', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852806', '1', '1293530372918784001', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852807', '1', '1293561826721722370', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852808', '1', '1294279545603559426', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852809', '1', '1294280141127618562', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852810', '1', '2', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852811', '1', '1292672434326999042', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852812', '1', '3', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852813', '1', '1292019753954648065', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339035852814', '1', '1292020081018085378', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339052630018', '1', '1292020309360189442', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339052630019', '1', '4', '2020-08-14 22:30:55', NULL);
INSERT INTO `sys_role_resources` VALUES ('1294280339052630020', '1', '5', '2020-08-14 22:30:55', NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '昵称',
  `mobile` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `qq` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'QQ',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别（1-男  2-女）',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `user_type` tinyint(0) NULL DEFAULT 0 COMMENT '用户类型（0-学生 1-老师 3-管理员 4-系统管理员）',
  `reg_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册IP',
  `last_login_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最近登录IP',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最近登录时间',
  `login_count` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '登录次数',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户备注',
  `status` int(0) UNSIGNED NULL DEFAULT 0 COMMENT '用户状态(0-正常 1-锁定)',
  `org_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在组织ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1288054243617472513', 'admin', '4b252ef32f83fdec9ce52366a161dbc0', '超级管理员', '18326088610', 'george_95@126.com', '1109536667', '1995-11-09', 1, '/images/z30ajiwcspc3rwcpemxbq6zja3as461597142292197.jpg', 3, NULL, '220.180.239.202', '2020-11-09 18:42:16', 149, '程序的缔造者', 0, '1292789334906290178', '2020-07-28 18:10:36', '2020-11-09 18:42:16', 0);
INSERT INTO `sys_user` VALUES ('1293047602220113921', 'yangkang', '00ead09c36c0be8019c49db22ee8fd1c', '杨康', '15678972354', 'yangkang@qq.com', '', '1933-07-20', 1, '/images/nsf0kuimdzd4duju3m8vn6fmv99ur31597121544671.jpg', 0, '127.0.0.1', '127.0.0.1', '2020-08-23 17:14:35', 9, '人生就是一场旅行', 0, '1292790526751326209', '2020-08-11 12:52:27', '2020-08-23 17:14:36', 0);
INSERT INTO `sys_user` VALUES ('1291206486868897794', 'sunen', '881dfc3f30f747fced77f5b162e95d90', '孙二娘', '18325678765', '', '', '2020-08-04', 2, '/images/dzam7thd1pbiscnxs4tzvkxhaz74ug1596682496630.jpg', 0, '127.0.0.1', '127.0.0.1', '2020-08-07 12:09:16', 1, '我是二娘', 0, '3', '2020-08-06 10:56:30', '2020-08-12 22:36:21', 0);
INSERT INTO `sys_user` VALUES ('1293049095094542337', 'huangyaoshi', 'aa2ce5e63e8c2400611203911407f7f2', '黄药师', '13456789009', 'huangyaoshi@qq.com', '', '1942-07-12', 1, '/images/kiq30qroq8d182b0zgzwtrwaffjcr51597121899473.jpg', 0, '127.0.0.1', NULL, NULL, 0, '我不是卖药的', 0, '1293041702499540994', '2020-08-11 12:58:23', '2020-08-12 22:36:28', 0);
INSERT INTO `sys_user` VALUES ('1293048488526880770', 'munianci', '075c4ac6a4e1898cbe66cf4c7083ef56', '穆念慈', '16789765643', 'munianci@qq.com', '', '2014-08-14', 2, '/images/j1jmz9xjv4plmmfilswmk04xf9b8m01597121755975.jpg', 0, '127.0.0.1', NULL, NULL, 0, '没有存在感', 0, '1293041702499540994', '2020-08-11 12:55:58', '2020-08-12 22:36:47', 0);
INSERT INTO `sys_user` VALUES ('1293047989534728193', 'hongqigong', '218754aa213b7be62310ec64340ad38f', '洪七公', '15689765643', 'hongqigong@qq.com', '', '1902-09-12', 1, '/images/ok7f0fwgyup9wosd4jb27zhaa65e5q1597121637031.jpg', 0, '127.0.0.1', '127.0.0.1', '2020-08-12 21:08:22', 1, '我就是个要饭的', 0, '1292790526751326209', '2020-08-11 12:53:59', '2020-08-12 22:37:08', 0);
INSERT INTO `sys_user` VALUES ('1293047233125556226', 'huangrong', '078974bc13125a9f4db336a5436fc302', '黄蓉', '18987653423', 'huangrong@qq.com', '', '1927-08-05', 2, '/images/w8bv81hykvrsxm8uo6dvehztdywxu91597121434832.jpg', 0, '127.0.0.1', NULL, NULL, 0, '我最好看', 0, '1292790526751326209', '2020-08-11 12:50:59', '2020-08-12 22:37:15', 0);
INSERT INTO `sys_user` VALUES ('1293046319522258945', 'guojing', 'f7ba12263c93b21c9a102c1529dfdf0a', '郭靖', '13456789876', 'guojing@qq.com', '', '1941-08-03', 1, '/images/2nz6rwjuamvvsobm8ow4cg6lef68fy1597121237224.jpg', 0, '127.0.0.1', NULL, NULL, 0, '射雕大侠', 0, '1292790526751326209', '2020-08-11 12:47:21', '2020-08-12 22:37:23', 0);
INSERT INTO `sys_user` VALUES ('1293051157857443841', 'yemaozhong', '3b8384eb5d7c488dae339120c7b49f30', '叶茂中', '15878765690', 'yemaozhong@qq.com', '', '1983-07-04', 1, '/images/94o460xnkraxpcwkzmgh93b36bhku81597122377895.jpg', 1, '127.0.0.1', NULL, NULL, 0, '营销专家叶茂中', 0, '2', '2020-08-11 13:06:35', '2020-08-12 22:41:03', 0);
INSERT INTO `sys_user` VALUES ('1293043818387480578', 'luxun', '55688015c2ed41daf56aea52e26c8fb3', '鲁迅', '13923456722', 'luxun@126.com', '', '1985-08-11', 1, '/images/xebj963955igo8efxz22yghbqkzuyp1597120640900.jpg', 1, '127.0.0.1', '127.0.0.1', '2020-08-12 21:08:44', 2, '著名文学家', 0, '1293041521880227842', '2020-08-11 12:37:25', '2020-08-12 22:41:12', 0);
INSERT INTO `sys_user` VALUES ('1293044488846974978', 'zhuziqing', '44ad5c9dda331e2a028fc5371f1a430d', '朱自清', '18945673245', 'zhuziqing@126.com', '1109536667', '1967-02-11', 1, '/images/fz32jftmnjw7n4qdzt3a2vnrwee80q1597120800591.jpg', 1, '127.0.0.1', NULL, NULL, 0, '著名散文家', 0, '1293041521880227842', '2020-08-11 12:40:05', '2020-08-12 22:41:21', 0);
INSERT INTO `sys_user` VALUES ('1293044862664318977', 'bingxin', '347c21aaefa30902360628838ad7a058', '冰心', '15987655678', 'bingxin@qq.com', '', '1942-08-09', 2, '/images/jia0sndx2k5ibit2fd88et1io47s0x1597120890473.jpg', 1, '127.0.0.1', NULL, NULL, 0, '文学家和小说家', 0, '1293041702499540994', '2020-08-11 12:41:34', '2020-08-12 22:41:28', 0);
INSERT INTO `sys_user` VALUES ('1293051736247771137', 'linus', '9d36873d749d95328dd57d65bc0f22cb', 'Linus Torvalds', '15898765654', 'linus@126.com', '', '1984-01-10', 1, '/images/jmmp5som9mirgh39v0jz0hwakvcu9b1597122528799.jpg', 1, '127.0.0.1', '112.32.12.80', '2020-08-20 20:24:37', 4, '你可能不知道我，但是你一定知道Linux', 0, '1292789334906290178', '2020-08-11 13:08:53', '2020-08-20 20:24:36', 0);
INSERT INTO `sys_user` VALUES ('1293133606276952066', 'sydx', '5e14d92b65149c0a1230bedf6f169f4a', '石油大学', '15789765432', 'sydx@qq.com', '', '2010-04-05', 2, '/images/xqkzqo232qf7lg6lthpfvxg45zoo841597142048658.jpg', 2, '127.0.0.1', '113.5.4.29', '2020-09-21 17:01:34', 3, '校园管理员', 0, '1292789171529760770', '2020-08-11 18:34:12', '2020-09-21 17:01:33', 0);

-- ----------------------------
-- Table structure for sys_user_course
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_course`;
CREATE TABLE `sys_user_course`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
  `course_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '课程ID',
  `is_teacher` tinyint(0) NULL DEFAULT 0 COMMENT '是否是老师 0-否  1-是',
  `score` double NULL DEFAULT 0 COMMENT '分数',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_flag` tinyint(0) NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人员与课程关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_course
-- ----------------------------
INSERT INTO `sys_user_course` VALUES ('1', '1293051736247771137', '1', 1, 0, '2020-08-10 21:55:40', '2020-08-13 22:21:28', 0);
INSERT INTO `sys_user_course` VALUES ('1293181530985840642', '1293044488846974978', '1293181487205695490', 1, 0, '2020-08-11 21:44:38', NULL, 0);
INSERT INTO `sys_user_course` VALUES ('1293531923682996225', '1293047602220113921', '1', 0, 41, '2020-08-12 20:56:57', '2020-08-13 21:23:12', 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户表主键',
  `role_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色表主键',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '添加时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1293052280701984769', '1288054243617472513', '1', '2020-08-11 13:11:02', NULL);
INSERT INTO `sys_user_role` VALUES ('1293200640507428865', '1293047602220113921', '1293050421316689921', '2020-08-11 23:00:34', NULL);
INSERT INTO `sys_user_role` VALUES ('1293200707624681473', '1291206486868897794', '1293050421316689921', '2020-08-11 23:00:50', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533592999292930', '1293049095094542337', '1293050421316689921', '2020-08-12 21:03:35', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533656094208002', '1293048488526880770', '1293050421316689921', '2020-08-12 21:03:50', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533697210970113', '1293047989534728193', '1293050421316689921', '2020-08-12 21:04:00', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533725107286018', '1293047233125556226', '1293050421316689921', '2020-08-12 21:04:06', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533762935713794', '1293046319522258945', '1293050421316689921', '2020-08-12 21:04:15', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533802483806209', '1293051157857443841', '1293050543450628097', '2020-08-12 21:04:25', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533825409871874', '1293043818387480578', '1293050543450628097', '2020-08-12 21:04:30', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533848486932482', '1293044488846974978', '1293050543450628097', '2020-08-12 21:04:36', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533866979618818', '1293044862664318977', '1293050543450628097', '2020-08-12 21:04:40', NULL);
INSERT INTO `sys_user_role` VALUES ('1293533887812726786', '1293051736247771137', '1293050543450628097', '2020-08-12 21:04:45', NULL);
INSERT INTO `sys_user_role` VALUES ('1293534301744394241', '1293133606276952066', '1293050267201183745', '2020-08-12 21:06:24', NULL);
INSERT INTO `sys_user_role` VALUES ('4', '2', '2', '2019-05-17 12:43:33', '2019-05-17 12:43:33');

SET FOREIGN_KEY_CHECKS = 1;

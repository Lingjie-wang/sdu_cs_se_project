-- --------------------------
-- user table
-- --------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(256) DEFAULT NULL COMMENT '用户名',
  `user_account` varchar(256) NOT NULL COMMENT '用户账号',
  `user_password` varchar(256) NOT NULL COMMENT '密码',
  `user_avatar` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `user_role` int DEFAULT '0' COMMENT '用户角色 0-普通用户| 1-管理员',
  `register_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- --------------------------
-- position table
-- --------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(256) NOT NULL COMMENT '级别名称',
  `duty` varchar(512) NOT NULL COMMENT '级别职责',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='级别表';

-- --------------------------
-- department table
-- --------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT '部门名称',
  `responsibility` varchar(512) NOT NULL COMMENT '部门职责',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';

-- --------------------------
-- employee table
-- --------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '员工编号',
  `emp_name` varchar(256) NOT NULL COMMENT '员工姓名',
  `emp_gender` tinyint NOT NULL COMMENT '员工性别',
  `emp_email` varchar(256) NOT NULL COMMENT '员工邮箱',
  `emp_phone` varchar(256) NOT NULL COMMENT '员工电话',
  `post_id` int NOT NULL COMMENT '级别编号',
  `dept_id` int NOT NULL COMMENT '部门编号',
  `hire_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '员工入职时间',
  `resign_date` datetime DEFAULT NULL COMMENT '员工离职时间',
  `emp_status` tinyint NOT NULL DEFAULT '0' COMMENT '工作状态 逻辑删除',
  PRIMARY KEY (`id`),
  KEY `employee_department_id_fk` (`dept_id`),
  KEY `employee_position_id_fk` (`post_id`),
  CONSTRAINT `employee_department_id_fk` FOREIGN KEY (`dept_id`) REFERENCES `department` (`id`),
  CONSTRAINT `employee_position_id_fk` FOREIGN KEY (`post_id`) REFERENCES `position` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';

-- --------------------------
-- training table
-- --------------------------
DROP TABLE IF EXISTS `training`;
CREATE TABLE `training` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '培训编号',
  `emp_id` int NOT NULL COMMENT '员工编号',
  `emp_name` varchar(256) NOT NULL COMMENT '员工姓名',
  `training_project` varchar(256) NOT NULL COMMENT '培训项目',
  `training_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '培训日期',
  `training_duration` int NOT NULL COMMENT '培训时长',
  `trainer` varchar(256) NOT NULL COMMENT '培训讲师',
  PRIMARY KEY (`id`),
  KEY `training_employee_id_fk` (`emp_id`),
  CONSTRAINT `training_employee_id_fk` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工培训表';

-- --------------------------
-- salary table
-- --------------------------
DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '工资编号',
  `emp_id` int NOT NULL COMMENT '员工编号',
  `emp_name` varchar(256) NOT NULL COMMENT '员工姓名',
  `post_id` int NOT NULL COMMENT '员工级别',
  `wage` int NOT NULL COMMENT '基本工资',
  `bonus` int NOT NULL COMMENT '奖金',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '工资发放时间',
  PRIMARY KEY (`id`),
  KEY `salary_employee_id_fk` (`emp_id`),
  CONSTRAINT `salary_employee_id_fk` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工工资表';

-- --------------------------
-- attendance table
-- --------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '考勤编号',
  `emp_id` int NOT NULL COMMENT '员工编号',
  `emp_name` varchar(256) NOT NULL COMMENT '员工姓名',
  `attendance_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '考勤日期',
  `attendance_type` int NOT NULL COMMENT '考勤类型 0-正常、1-迟到、2-缺勤、3请假',
  PRIMARY KEY (`id`),
  KEY `attendance_employee_id_fk` (`emp_id`),
  CONSTRAINT `attendance_employee_id_fk` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工考勤表';

-- --------------------------
-- user records
-- passwoed: 12345678
-- --------------------------
INSERT INTO `user` VALUES (1,'孤星','123456788','b50b670da2db980836671b5a84ab5fab','https://avatars.githubusercontent.com/u/166907626?v=4',1,'2025-05-30 16:55:15');
INSERT INTO `user` VALUES (2,'老渣','12345677','b50b670da2db980836671b5a84ab5fab','https://avatars.githubusercontent.com/u/186826245?v=4',1,'2025-05-30 17:07:34');
INSERT INTO `user` VALUES (3,'Gump' ,'12345678','b50b670da2db980836671b5a84ab5fab','https://avatars.githubusercontent.com/u/143979333?v=4',1,'2025-06-02 20:25:34');
INSERT INTO `user` VALUES (4,'王姐','12345679','b50b670da2db980836671b5a84ab5fab','https://avatars.githubusercontent.com/u/115408883?v=4',1,'2025-06-02 20:27:42');
INSERT INTO `user` VALUES (5,'蒋骐','123456780','b50b670da2db980836671b5a84ab5fab','https://avatars.githubusercontent.com/u/147612749?v=4',1,'2025-06-02 20:28:26');
INSERT INTO `user` VALUES (6,'小渣','123456789','b50b670da2db980836671b5a84ab5fab','https://avatars.githubusercontent.com/u/16256802?v=4',0,'2025-06-02 20:32:38');

-- --------------------------
-- position records
-- --------------------------
INSERT INTO `position` VALUES (1,'总经理','负责公司日常业务的经营管理，实施公司的总体战略');
INSERT INTO `position` VALUES (2,'部门经理','负责部门内部控制管理，协调部门员工工作，负责部门整体业绩');
INSERT INTO `position` VALUES (3,'项目组组长','负责具体任务的分解、实施，确保生产计划顺利完成，领导管理本项目组员工');
INSERT INTO `position` VALUES (4,'普通员工','完成分配的工作任务，遵守公司的规章制度，认真履行工作职责');
INSERT INTO `position` VALUES (5,'试用员工','尽快熟悉掌握公司相关业务信息及知识，认真贯彻执行公司的管理规定和方案实施细则');

-- --------------------------
-- department records
-- --------------------------
INSERT INTO `department` VALUES (1,'市场部','公司的市场开发，营销策划，客户管理，招投标等工作');
INSERT INTO `department` VALUES (2,'研发部','开展新产品、新技术、新服务开发,负责提供技术支持、试验管理、工艺管理等工作');
INSERT INTO `department` VALUES (3,'销售部','负责总体的营销活动,并对营销工作进行评估和监控,包括公共关系、销售、客户服务等');
INSERT INTO `department` VALUES (4,'财务部','财务规划和预算编制,财务报表和分析,资金管理和现金流量控制,投资和资本结构管理');
INSERT INTO `department` VALUES (5,'生产部','负责编制生产作业计划、组织安排生产,对现场进行标识管理,并对生产过程中的产品质量组织实施自检');

-- --------------------------
-- employee records
-- --------------------------
INSERT INTO `employee` VALUES (1,'T1 Faker',0,'30228619@qq.com','18199781234',1,1,'2025-06-02 20:09:22',NULL,0);
INSERT INTO `employee` VALUES (2,'T1 Zeus',0,'3022619@163.com','181997843121',2,1,'2025-06-02 20:10:24',NULL,0);
INSERT INTO `employee` VALUES (3,'T1 Oner',0,'3022669@firefox.com','18199783134',3,1,'2025-06-02 20:11:16','2025-06-02 20:12:44',1);
INSERT INTO `employee` VALUES (4,'T1 Gumayuzi',0,'302866619@sdu.mail.com','18111223134',4,1,'2025-06-02 20:12:00',NULL,0);
INSERT INTO `employee` VALUES (5,'T1 Keria',0,'302269@T1.mail.com','18111223344',5,1,'2025-06-02 20:12:36',NULL,0);
INSERT INTO `employee` VALUES (6,'WBG Crisp',0,'maxingfl@163.com','112233',2,3,'2025-06-02 20:37:26',NULL,0);
INSERT INTO `employee` VALUES (7,'WBG Xiaohu',0,'fasdf@163.com','112233fsaf',2,5,'2025-06-02 20:38:17',NULL,0);
INSERT INTO `employee` VALUES (8,'IG Rookie',1,'ibi3396@163.com','1234rtyu',4,2,'2025-06-02 20:39:15',NULL,0);

-- --------------------------
-- training records
-- --------------------------
INSERT INTO `training` VALUES (1,1,'T1 Faker','企业文化','2025-06-02 20:20:08',2,'蒋嘚');
INSERT INTO `training` VALUES (2,5,'T1 Keria','职业技能','2025-06-02 20:21:56',8,'TheShy');
INSERT INTO `training` VALUES (3,2,'T1 Zeus','规章制度','2025-06-02 20:22:18',6,'TheShy');

-- --------------------------
-- salary records
-- --------------------------
INSERT INTO `salary` VALUES (1,1,'T1 Faker',1,10000,5000,'2025-06-02 20:23:14');
INSERT INTO `salary` VALUES (2,3,'T1 Oner',3,2500,3000,'2025-06-02 20:23:42');
INSERT INTO `salary` VALUES (3,5,'T1 Keria',5,500,1000,'2025-06-02 20:24:09');

-- --------------------------
-- attendance records
-- --------------------------
INSERT INTO `attendance` VALUES (1,1,'T1 Faker','2025-06-03 20:16:24',1);
INSERT INTO `attendance` VALUES (2,2,'T1 Zeus','2025-06-17 20:37:02',0);
INSERT INTO `attendance` VALUES (5,5,'T1 Keria','2025-06-02 20:34:49',2);
INSERT INTO `attendance` VALUES (6,3,'T1 Oner','2025-06-23 20:35:19',3);

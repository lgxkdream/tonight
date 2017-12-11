
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `TB_STAFF`
-- ----------------------------
DROP TABLE IF EXISTS `TB_STAFF`;
CREATE TABLE `TB_STAFF` (
 		`STAFF_ID` varchar(100) NOT NULL,
		`NAME` varchar(255) DEFAULT NULL COMMENT '姓名',
		`GENDER` varchar(255) DEFAULT NULL COMMENT '性别',
		`JOB` varchar(255) DEFAULT NULL COMMENT '职务',
		`JOB_NAME` varchar(255) DEFAULT NULL COMMENT '职务名',
		`CREATE_TIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
		`CREATOR` varchar(255) DEFAULT NULL COMMENT '创建人ID',
		`CREATOR_NAME` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
		`DELETED` int(11) NOT NULL COMMENT '是否删除',
  		PRIMARY KEY (`STAFF_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

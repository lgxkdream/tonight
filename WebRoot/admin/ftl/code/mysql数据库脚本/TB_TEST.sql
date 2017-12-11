
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `TB_TEST`
-- ----------------------------
DROP TABLE IF EXISTS `TB_TEST`;
CREATE TABLE `TB_TEST` (
 		`TEST_ID` varchar(100) NOT NULL,
		`NAME` varchar(255) DEFAULT NULL COMMENT '姓名',
		`AGE` int(11) NOT NULL COMMENT '年龄',
  		PRIMARY KEY (`TEST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

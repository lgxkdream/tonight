-- ----------------------------
-- Table structure for "C##NEWO"."TB_TEST"
-- ----------------------------
-- DROP TABLE "C##NEWO"."TB_TEST";
CREATE TABLE "C##NEWO"."TB_TEST" (
	"NAME" VARCHAR2(255 BYTE) NULL ,
	"AGE" NUMBER(10) NULL ,
	"TEST_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "C##NEWO"."TB_TEST"."NAME" IS '姓名';
COMMENT ON COLUMN "C##NEWO"."TB_TEST"."AGE" IS '年龄';
COMMENT ON COLUMN "C##NEWO"."TB_TEST"."TEST_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table TB_TEST
-- ----------------------------

-- ----------------------------
-- Checks structure for table "C##NEWO"."TB_TEST"

-- ----------------------------

ALTER TABLE "C##NEWO"."TB_TEST" ADD CHECK ("TEST_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "C##NEWO"."TB_TEST"
-- ----------------------------
ALTER TABLE "C##NEWO"."TB_TEST" ADD PRIMARY KEY ("TEST_ID");

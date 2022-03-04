CREATE TABLE `b_user` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `user_image` varchar(256) DEFAULT NULL COMMENT '用户头像',
  `delete_flag` int(1) DEFAULT '0' COMMENT '删除状态（0：正常，1：删除）',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `work_start_time` time DEFAULT NULL COMMENT '上班时间',
  `work_end_time` time DEFAULT NULL COMMENT '下班时间',
  `creator` bigint(16) DEFAULT NULL COMMENT '创建人',
  `modifier` bigint(16) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户';
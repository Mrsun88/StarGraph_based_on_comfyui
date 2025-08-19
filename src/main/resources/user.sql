CREATE TABLE `sg_user` (
                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                           `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机',
                           `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录密码',
                           `status` tinyint unsigned DEFAULT NULL COMMENT '状态 0 正常  1超时锁定  2锁定  9无效 ',
                           `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
                           `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
                           `vip_level` tinyint unsigned DEFAULT '0' COMMENT '会员等级',
                           `deleted` tinyint unsigned DEFAULT '0' COMMENT '逻辑删除',
                           `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
                           `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
                           `gender` tinyint unsigned DEFAULT NULL COMMENT '性别',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user`
(
    id int not null PRIMARY KEY AUTO_INCREMENT,
    login_id varchar(20) not null comment '登录ID',
    `password` varchar(100) not null comment '密码',
    username varchar(40) not null comment '用户名',
    status tinyint(1) not null default 1 comment '状态 1:启用 2:禁用',
    phone_number varchar(20) null comment '电话号码',
    email varchar(50) null comment '邮件地址',
    create_at timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by int not null comment '创建人',
    update_at timestamp not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    update_by int null comment '最后修改人',
    delete_at timestamp null  comment '删除时间',
    delete_by int null comment '删除人',
    unique index `user_login_id`(login_id, delete_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
COMMENT='用户表';


INSERT INTO `t_user` (`login_id`, `password`, `username`, `status`, `phone_number`, `email`,
                      `create_at`, `update_at`, `delete_at`,`create_by`)
VALUES ('admin', '$2a$10$Iu7hTvpHXFdStTX0aWiox.lC58Np5nIy0s/YmEv.7Oqmf8fLJHM6.', '超级管理员', 1, '18180898191',
        'chenhao.chengdu.china@gmail.com', '2023-03-20 14:26:56', '2023-03-20 14:51:56', NULL, 1);


ALTER TABLE t_user
   ADD COLUMN `department_id` int null comment '部门ID';


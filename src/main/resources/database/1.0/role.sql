DROP TABLE IF EXISTS `t_role`;
CREATE TABLE IF NOT EXISTS `t_role`
(
    id               int         not null AUTO_INCREMENT,
    role_code        varchar(30) not null comment '角色代码',
    role_name        varchar(50) not null comment '角色名称',
    role_description varchar(80) null comment '角色描述',
    create_at        timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by        int         not null comment '创建人',
    update_at        timestamp   not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    update_by        int         null comment '最后修改人',
    delete_at        timestamp   null comment '删除时间',
    delete_by        int         null comment '删除人',
    primary key (id),
    unique index `role_code` (role_code, delete_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '角色表';

INSERT INTO t_role (role_code, role_name, role_description, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('ADMIN', '管理员角色', '管理员角色', '2023-03-28 06:29:25', 1, '2023-03-28 06:29:25', null, null, null);


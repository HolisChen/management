DROP TABLE IF EXISTS `t_role_department`;
CREATE TABLE IF NOT EXISTS `t_role_department`
(
    id            int       not null AUTO_INCREMENT,
    role_id       int       not null comment '角色ID',
    department_id int       not null comment '部门ID',
    create_at     timestamp not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by     int       not null comment '创建人',
    primary key (id),
    unique index `role_department` (role_id, department_id),
    index `department` (department_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT = '角色部门关联表';


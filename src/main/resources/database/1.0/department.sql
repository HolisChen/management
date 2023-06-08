DROP TABLE IF EXISTS `t_department`;
CREATE TABLE IF NOT EXISTS `t_department`
(
    id                     int         not null AUTO_INCREMENT,
    department_name        varchar(30) not null comment '部门名称',
    department_description varchar(80) null comment '部门描述',
    parent_department_id   int         not null default -1 comment '父级部门ID',
    create_at              timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by              int         not null comment '创建人',
    update_at              timestamp   not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    update_by              int         null comment '最后修改人',
    delete_at              timestamp   null comment '删除时间',
    delete_by              int         null comment '删除人',
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
    COMMENT = '部门信息表';



DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE IF NOT EXISTS `t_resource`
(
    id               int         not null AUTO_INCREMENT,
    resource_code    varchar(30) not null comment '资源code',
    resource_name    varchar(50) not null comment '资源名称',
    resource_description varchar(80) null comment '资源描述',
    resource_url     varchar(100) null comment '资源URL,比如菜单路由，API URL',
    resource_type    tinyint not null comment '资源类型：1:功能 2:菜单 3:集合',
    icon             varchar(20) null comment '图标',
    parent_id        int not null default 0 comment '父级资源ID',
    create_at        timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by        int         not null comment '创建人',
    update_at        timestamp   not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    update_by        int         null comment '最后修改人',
    delete_at        timestamp   null comment '删除时间',
    delete_by        int         null comment '删除人',
    primary key (id),
    unique index `resource_code` (resource_code, delete_at)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '资源表';


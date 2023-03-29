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
    sort             int not null default 0 comment '排序',
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
INSERT INTO t_resource (resource_code, resource_name, resource_description, resource_url, resource_type, icon, sort, parent_id, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('DASHBOARD', '工作台', '', '/dashboard', 2, '', -1, 0, '2023-03-29 07:24:26', 1, '2023-03-29 07:24:26', null, null, null);
INSERT INTO t_resource (resource_code, resource_name, resource_description, resource_url, resource_type, icon, sort, parent_id, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('SYSTEM', '系统管理', '系统管理', '/systemSetting', 2, '', 0, 0, '2023-03-28 06:24:07', 1, '2023-03-29 07:26:13', null, null, null);
INSERT INTO t_resource (resource_code, resource_name, resource_description, resource_url, resource_type, icon, sort, parent_id, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('USER', '用户管理', '', '/systemSetting/user', 2, '', 0, 2, '2023-03-28 06:25:58', 1, '2023-03-28 06:25:58', null, null, null);
INSERT INTO t_resource (resource_code, resource_name, resource_description, resource_url, resource_type, icon, sort, parent_id, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('ROLE', '角色管理', '', '/systemSetting/role', 2, '', 0, 2, '2023-03-28 06:26:18', 1, '2023-03-28 06:26:18', null, null, null);
INSERT INTO t_resource (resource_code, resource_name, resource_description, resource_url, resource_type, icon, sort, parent_id, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('RESOURCE', '菜单管理', '', '/systemSetting/menu', 2, '', 0, 2, '2023-03-28 06:27:02', 1, '2023-03-28 06:27:02', null, null, null);
INSERT INTO t_resource (resource_code, resource_name, resource_description, resource_url, resource_type, icon, sort, parent_id, create_at, create_by, update_at, update_by, delete_at, delete_by) VALUES ('LOG QUERY', '日志查询', '日志查询', '/logQuery', 2, '', 0, 0, '2023-03-28 06:25:31', 1, '2023-03-28 06:25:31', null, null, null);

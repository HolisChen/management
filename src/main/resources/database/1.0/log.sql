DROP TABLE IF EXISTS `t_log`;
CREATE TABLE IF NOT EXISTS `t_log`
(
    id               int         not null AUTO_INCREMENT,
    operation_type   varchar(10) not null comment '操作类型：CREATE,UPDATE,DELETE,UPLOAD',
    operation_target varchar(20) not null  comment '操作对象，t_user， t_menu',
    success_flag     tinyint(4)  not null default 1 comment '是否成功 0：否 1：是',
    exception_info   varchar(200) null comment '异常信息',
    parameter        varchar(500)  null comment '请求参数',
    response         varchar(1000) null comment '响应结果',
    create_at        timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by        int         not null comment '创建人',
    primary key (id),
    index `role_code` (operation_target, operation_type)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '操作日志表';


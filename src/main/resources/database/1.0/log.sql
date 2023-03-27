DROP TABLE IF EXISTS `t_log`;
CREATE TABLE IF NOT EXISTS `t_log`
(
    id               int         not null AUTO_INCREMENT,
    operation_type   varchar(10) not null comment '操作类型：CREATE,UPDATE,DELETE,UPLOAD',
    operation_target varchar(20) not null  comment '操作对象，t_user， t_menu',
    content          text        null comment '操作内容',
    success_flag     tinyint(4)  not null default 1 comment '是否成功 0：否 1：是',
    exception_info   text null comment '异常信息',
    parameter        text  null comment '请求参数',
    response         text null comment '响应结果',
    create_at        timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by        int         not null comment '创建人',
    ip               varchar(20) null comment 'ip地址',
    cost_time        bigint      not null comment  '耗时',
    primary key (id),
    index `role_code` (operation_target, operation_type)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '操作日志表';


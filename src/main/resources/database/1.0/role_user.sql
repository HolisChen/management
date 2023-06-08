DROP TABLE IF EXISTS `t_role_user`;
CREATE TABLE IF NOT EXISTS `t_role_user`
(
    id               int         not null AUTO_INCREMENT,
    role_id          int not null comment '角色ID',
    user_id          int not null comment '用户ID',
    create_at        timestamp   not null default CURRENT_TIMESTAMP comment '创建时间',
    create_by        int         not null comment '创建人',
    primary key (id),
    unique index `role_user` (role_id, user_id),
    index `user_id` (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '角色用户关联表';


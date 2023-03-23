package com.mg.domain.dto.permission;

import com.mg.dao.entity.RoleEntity;
import com.mg.utils.UserUtil;
import lombok.Data;

@Data
public class UpdateRoleDto extends CreateRoleDto{
    private Integer id;

    public RoleEntity update(RoleEntity entity) {
        entity.setRoleCode(getRoleCode());
        entity.setRoleName(getRoleName());
        entity.setRoleDescription(getRoleDescription());
        entity.setUpdateBy(UserUtil.getCurrentUserId());
        return entity;
    }
}

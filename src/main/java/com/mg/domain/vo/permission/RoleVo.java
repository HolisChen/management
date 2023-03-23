package com.mg.domain.vo.permission;

import com.mg.domain.vo.base.BaseVo;
import lombok.Data;

@Data
public class RoleVo extends BaseVo {
    private String roleCode;
    private String roleName;
    private String roleDescription;
}

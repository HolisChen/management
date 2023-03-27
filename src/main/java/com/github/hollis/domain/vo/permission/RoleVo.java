package com.github.hollis.domain.vo.permission;

import com.github.hollis.domain.vo.base.BaseVo;
import lombok.Data;

@Data
public class RoleVo extends BaseVo {
    private String roleCode;
    private String roleName;
    private String roleDescription;
}

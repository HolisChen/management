package com.github.hollis.domain.dto.permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoleDto {
    private String roleCode;
    private String roleName;
    private String roleDescription;
}

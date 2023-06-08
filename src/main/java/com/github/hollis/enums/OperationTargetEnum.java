package com.github.hollis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTargetEnum {
    ROLE("t_role"),
    USER("t_user"),
    RESOURCE("t_resource"),
    ROLE_USER("t_role_user"),
    ROLE_RESOURCE("t_role_resource"),
    ROLE_DEPARTMENT("t_role_department"),
    DEPARTMENT("t_department"),
    ;
    private final String code;
}

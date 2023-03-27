package com.github.hollis.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTargetEnum {
    ROLE("t_role"),
    USER("t_user"),
    RESOURCE("t_resource"),

    ;
    private final String code;
}

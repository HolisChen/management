package com.mg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceTypeEnum {
    FUNCTION((byte) 1),
    MENU((byte) 2),
    COLLECTION((byte) 3);
    private final Byte code;

}

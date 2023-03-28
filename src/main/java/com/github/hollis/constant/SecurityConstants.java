package com.github.hollis.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {
    public static final String HEADER_TOKEN = "x-header-token";

    public static final int DEFAULT_TOKEN_EXPIRE = 60;  //minutes
}

package com.github.hollis.security;

import com.github.hollis.dao.entity.UserEntity;

public interface TokenGenerator {
    String generateToken(UserEntity user);
}

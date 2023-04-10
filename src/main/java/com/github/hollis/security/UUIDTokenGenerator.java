package com.github.hollis.security;

import com.github.hollis.dao.entity.UserEntity;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Component
public class UUIDTokenGenerator implements TokenGenerator{
    @Override
    public String generateToken(@NotNull UserEntity user) {
        Integer userId = user.getId();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return userId + ":" +uuid;
    }
}

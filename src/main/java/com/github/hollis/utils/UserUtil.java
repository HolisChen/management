package com.github.hollis.utils;

import com.github.hollis.security.LoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtil {
    public static Integer getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser()).map(LoginUser::getUserId).orElse(-9999);
    }

    public static LoginUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof LoginUser) {
                return (LoginUser) principal;
            }
        }
        return null;
    }
}

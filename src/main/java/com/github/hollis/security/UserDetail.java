package com.github.hollis.security;

import com.github.hollis.domain.bo.base.UserBo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class UserDetail implements UserDetails {
    private final UserBo userBo;
    private Date loginTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return Optional.ofNullable(userBo).map(UserBo::getPassword).orElse(null);
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(userBo).map(UserBo::getUsername).orElse(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Optional.ofNullable(userBo).map(UserBo::isActive).orElse(false);
    }

    public Integer getUserId() {
        return Optional.ofNullable(userBo).map(UserBo::getId).orElse(null);
    }
}

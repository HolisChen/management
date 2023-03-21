package com.mg.security;

import com.mg.domain.bo.base.UserBo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserDetail implements UserDetails {
    private final UserBo userBo;
    private final List<GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
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
}

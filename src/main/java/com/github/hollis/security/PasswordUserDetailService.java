package com.github.hollis.security;

import com.github.hollis.domain.bo.base.UserBo;
import com.github.hollis.service.permission.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PasswordUserDetailService implements UserDetailsService {
    private final UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserBo> userBoOpt = userService.getByLoginId(username);
        if (!userBoOpt.isPresent()) {
            throw new UsernameNotFoundException(String.format("User[%s] not found", username));
        }
        return new UserDetail(userBoOpt.get());
    }
}

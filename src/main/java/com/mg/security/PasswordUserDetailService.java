package com.mg.security;

import com.mg.dao.entity.ResourceEntity;
import com.mg.domain.bo.base.UserBo;
import com.mg.enums.ResourceTypeEnum;
import com.mg.service.base.UserService;
import com.mg.service.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PasswordUserDetailService implements UserDetailsService {
    private final UserService userService;
    private final PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserBo> userBoOpt = userService.getByLoginId(username);
        if (!userBoOpt.isPresent()) {
            throw new UsernameNotFoundException(String.format("User[%s] not found", username));
        }
        UserBo userBo = userBoOpt.get();
        List<GrantedAuthority> grantedAuthorities = permissionService.getAuthorizedResource(userBo.getId())
                .stream()
                .filter(item -> Objects.equals(ResourceTypeEnum.FUNCTION.getCode(), item.getResourceType()))
                .map(ResourceEntity::getResourceCode)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UserDetail(userBo,grantedAuthorities);
    }
}

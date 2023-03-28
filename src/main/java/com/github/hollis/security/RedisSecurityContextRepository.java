package com.github.hollis.security;

import com.alibaba.fastjson.JSON;
import com.github.hollis.constant.SecurityConstants;
import com.github.hollis.dao.entity.ResourceEntity;
import com.github.hollis.enums.ResourceTypeEnum;
import com.github.hollis.service.permission.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedisSecurityContextRepository implements SecurityContextRepository {
    private final StringRedisTemplate stringRedisTemplate;
    private final PermissionService permissionService;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        String token = requestResponseHolder.getRequest().getHeader(SecurityConstants.HEADER_TOKEN);
        if (token != null) {
            String userJson = stringRedisTemplate.opsForValue().get(token);
            if (StringUtils.hasText(userJson)) {
                LoginUser loginUser = JSON.parseObject(userJson, LoginUser.class);
                List<SimpleGrantedAuthority> authorities = permissionService.getAuthorizedResource(loginUser.getUserId())
                        .stream()
                        .filter(item -> Objects.equals(ResourceTypeEnum.FUNCTION.getCode(), item.getResourceType()))
                        .map(ResourceEntity::getResourceCode)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                SecurityContextImpl securityContext = new SecurityContextImpl();
                Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
                securityContext.setAuthentication(authentication);
                return securityContext;
            }
        }
        return SecurityContextHolder.createEmptyContext();
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = context.getAuthentication();
        if (null != authentication) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof LoginUser) {
                LoginUser loginUser = (LoginUser) principal;
                //续期
                stringRedisTemplate.expire(loginUser.getToken(),SecurityConstants.DEFAULT_TOKEN_EXPIRE, TimeUnit.MINUTES);
            }
        }
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_TOKEN);
        return token != null;
    }
}

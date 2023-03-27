package com.github.hollis.configuration;

import com.github.hollis.dao.entity.ResourceEntity;
import com.github.hollis.enums.ResourceTypeEnum;
import com.github.hollis.security.UserDetail;
import com.github.hollis.service.permission.PermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationSuccessHandler successHandler,
                                                   AuthenticationEntryPoint entryPoint) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/**")
                .permitAll()
                .and()
                .logout()
                .disable()
                .formLogin()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 使用BCrypt强哈希函数加密方案，密钥迭代次数设为10（默认即为10）
        return new BCryptPasswordEncoder();
    }

    @Bean
    RoleHierarchy roleHierarchy(PermissionService permissionService){
        return authorities -> {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if ( principal instanceof UserDetail) {
                UserDetail userDetail = (UserDetail) principal;
                Integer userId = userDetail.getUserId();
                return permissionService.getAuthorizedResource(userId)
                        .stream()
                        .filter(item -> Objects.equals(ResourceTypeEnum.FUNCTION.getCode(), item.getResourceType()))
                        .map(ResourceEntity::getResourceCode)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
            return authorities;
        };
    }
}

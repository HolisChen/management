package com.github.hollis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationEntryPoint entryPoint,
                                                   SecurityContextRepository securityContextRepository) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/api/login","/api/logout","/swagger-resources","/v2/api-docs","/webjars/**","/doc.html")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .disable()
                .formLogin()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint);
        http.setSharedObject(SecurityContextRepository.class, securityContextRepository);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 使用BCrypt强哈希函数加密方案，密钥迭代次数设为10（默认即为10）
        return new BCryptPasswordEncoder();
    }
}

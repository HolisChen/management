package com.mg.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationSuccessHandler successHandler,
                                                   AuthenticationFailureHandler failureHandler) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .loginProcessingUrl("/api/doLogin")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll();
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // 使用BCrypt强哈希函数加密方案，密钥迭代次数设为10（默认即为10）
        return new BCryptPasswordEncoder();
    }
}

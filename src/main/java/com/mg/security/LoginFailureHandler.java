package com.mg.security;

import com.alibaba.fastjson.JSON;
import com.mg.domain.vo.base.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 自定义登录失败处理器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // TODO: 2023/3/20 判断登录失败错误原因，将错误信息返回给前端
        String errorMsg;
        if (e instanceof AccountExpiredException) {
            // 账号过期
            errorMsg = "[登录失败] - 用户账号过期";
        } else if (e instanceof BadCredentialsException) {
            // 密码错误
            errorMsg = "[登录失败] - 用户密码错误";
        } else if (e instanceof CredentialsExpiredException) {
            // 密码过期
            errorMsg = "[登录失败] - 用户密码过期";
        } else if (e instanceof DisabledException) {
            // 用户被禁用
            errorMsg = "[登录失败] - 用户被禁用";
        } else if (e instanceof LockedException) {
            // 用户被锁定
            errorMsg = "[登录失败] - 用户被锁定";
        } else {
            // 其他错误
            errorMsg = String.format("[登录失败] - [%s]其他错误", e.getMessage());
        }

        Result<String> result = Result.success(errorMsg);
        try (PrintWriter writer = response.getWriter()) {
            response.setContentType(MediaType.APPLICATION_JSON.getType());
            response.setCharacterEncoding("UTF-8");
            writer.write(JSON.toJSONString(result));
            writer.flush();
        }
    }
}

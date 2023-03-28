package com.github.hollis.controller;

import com.github.hollis.constant.SecurityConstants;
import com.github.hollis.domain.dto.login.LoginDto;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.security.LoginUser;
import com.github.hollis.service.login.LoginService;
import com.github.hollis.utils.UserUtil;
import com.github.hollis.utils.WebUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequestMapping
@Api
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDto loginDto) {
        return Result.success(loginService.doLogin(loginDto));
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        String token = Optional.ofNullable(UserUtil.getCurrentUser()).map(LoginUser::getToken).orElse(null);
        loginService.doLogout(token);
        return Result.success();
    }
}

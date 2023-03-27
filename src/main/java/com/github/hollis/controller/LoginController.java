package com.github.hollis.controller;

import com.github.hollis.domain.dto.login.LoginDto;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.service.login.LoginService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public Result<Void> logout(@RequestHeader(value = "token", required = false) String token) {
        loginService.doLogout(token);
        return Result.success();
    }
}

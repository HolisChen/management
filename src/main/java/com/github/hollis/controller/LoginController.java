package com.github.hollis.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.lang.UUID;
import com.github.hollis.domain.dto.login.LoginDto;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.permission.CaptureResponseVo;
import com.github.hollis.security.LoginUser;
import com.github.hollis.service.login.LoginService;
import com.github.hollis.utils.UserUtil;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RequestMapping("/api")
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

    @GetMapping("/capture")
    public Result<CaptureResponseVo> capture() {
        return Result.success(loginService.createCapture());
    }

}

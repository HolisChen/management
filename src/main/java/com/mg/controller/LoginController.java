package com.mg.controller;

import com.mg.domain.vo.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @PostMapping("/doLogin")
    public Result<String> doLogin() {
        log.info("doLogin");
        return Result.success("登录成功");
    }
}

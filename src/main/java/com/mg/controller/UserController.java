package com.mg.controller;

import com.mg.domain.vo.base.Result;
import com.mg.domain.vo.permission.UserVo;
import com.mg.mapper.UserMapper;
import com.mg.service.permission.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @GetMapping("/list")
    public Result<List<UserVo>> getUsers() {
        return Result.success(userMapper.entityToVoList(userService.findAll()));
    }
}

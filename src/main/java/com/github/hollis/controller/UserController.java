package com.github.hollis.controller;

import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.permission.UserVo;
import com.github.hollis.mapper.UserMapper;
import com.github.hollis.service.permission.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @PreAuthorize("hasAuthority('GetUserList')")
    public Result<List<UserVo>> getUsers() {
        return Result.success(userMapper.entityToVoList(userService.findAll()));
    }
}

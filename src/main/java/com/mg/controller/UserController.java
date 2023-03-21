package com.mg.controller;

import com.mg.dao.entity.UserEntity;
import com.mg.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @GetMapping("/list")
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }
}

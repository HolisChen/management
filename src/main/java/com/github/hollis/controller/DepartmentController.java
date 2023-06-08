package com.github.hollis.controller;

import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.permission.DepartmentTree;
import com.github.hollis.service.permission.DepartmentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@Api(tags = {"部门管理"})
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/tree")
    public Result<List<DepartmentTree>> getDepartmentTree() {
        return Result.success(departmentService.queryTree());
    }

}

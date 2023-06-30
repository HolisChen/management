package com.github.hollis.controller;

import cn.hutool.core.text.finder.Finder;
import com.github.hollis.dao.entity.DepartmentEntity;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.permission.DepartmentTree;
import com.github.hollis.domain.vo.permission.DepartmentVo;
import com.github.hollis.mapper.DepartmentMapper;
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
    private final DepartmentMapper departmentMapper;

    @GetMapping("/tree")
    public Result<List<DepartmentTree>> getDepartmentTree() {
        return Result.success(departmentService.queryTree());
    }

    @GetMapping("/list")
    public Result<List<DepartmentVo>> getAllDepartments() {
        List<DepartmentEntity> departmentEntityList = departmentService.findAll();
        return Result.success(departmentMapper.entityToVoList(departmentEntityList));
    }

}

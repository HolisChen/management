package com.mg.controller;

import com.mg.domain.dto.permission.CreateRoleDto;
import com.mg.domain.dto.permission.UpdateRoleDto;
import com.mg.domain.vo.base.Result;
import com.mg.domain.vo.permission.RoleVo;
import com.mg.mapper.RoleMapper;
import com.mg.service.RoleService;
import com.mg.service.permission.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
@Api
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    @ApiOperation(value = "获取角色列表")
    @GetMapping
    public Result<List<RoleVo>> getRoleList() {
        return Result.success(roleMapper.entityToVos(roleService.findAllRoles()));
    }

    @ApiOperation(value = "添加一个角色")
    @PostMapping
    public Result<Void> addRole(@RequestBody CreateRoleDto dto) {
        roleService.addRole(dto);
        return Result.success();
    }

    @ApiOperation(value = "编辑一个角色")
    @PutMapping
    public Result<Void> updateRole(@RequestBody UpdateRoleDto dto) {
        roleService.updateRole(dto);
        return Result.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{roleId}")
    public Result<Void> deleteRole(@PathVariable Integer roleId) {
        permissionService.deleteRole(roleId);
        return Result.success();
    }
}

package com.github.hollis.controller;

import com.github.hollis.aspect.OperationLog;
import com.github.hollis.domain.dto.permission.CreateRoleDto;
import com.github.hollis.domain.dto.permission.UpdateRoleDto;
import com.github.hollis.domain.vo.permission.RoleVo;
import com.github.hollis.enums.OperationTargetEnum;
import com.github.hollis.enums.OperationTypeEnum;
import com.github.hollis.service.permission.RoleService;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.mapper.RoleMapper;
import com.github.hollis.service.permission.PermissionService;
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
    @OperationLog(type = OperationTypeEnum.QUERY, target = OperationTargetEnum.ROLE)
    public Result<List<RoleVo>> getRoleList() {
        return Result.success(roleMapper.entityToVos(roleService.findAllRoles()));
    }

    @ApiOperation(value = "添加一个角色")
    @PostMapping
    @OperationLog(type = OperationTypeEnum.CREATE, target = OperationTargetEnum.ROLE)
    public Result<Void> addRole(@RequestBody CreateRoleDto dto) {
        roleService.addRole(dto);
        return Result.success();
    }

    @ApiOperation(value = "编辑一个角色")
    @PutMapping
    @OperationLog(type = OperationTypeEnum.UPDATE, target = OperationTargetEnum.ROLE)
    public Result<Void> updateRole(@RequestBody UpdateRoleDto dto) {
        roleService.updateRole(dto);
        return Result.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{roleId}")
    @OperationLog(type = OperationTypeEnum.DELETE, target = OperationTargetEnum.ROLE)
    public Result<Void> deleteRole(@PathVariable Integer roleId) {
        permissionService.deleteRole(roleId);
        return Result.success();
    }
}

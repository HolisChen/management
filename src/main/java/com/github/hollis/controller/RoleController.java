package com.github.hollis.controller;

import com.github.hollis.aspect.OperationLog;
import com.github.hollis.domain.dto.permission.CreateRoleDto;
import com.github.hollis.domain.dto.permission.UpdateRoleDto;
import com.github.hollis.domain.vo.permission.ResourceTree;
import com.github.hollis.domain.vo.permission.RoleVo;
import com.github.hollis.domain.vo.permission.UserVo;
import com.github.hollis.enums.OperationTargetEnum;
import com.github.hollis.enums.OperationTypeEnum;
import com.github.hollis.mapper.UserMapper;
import com.github.hollis.service.permission.RoleService;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.mapper.RoleMapper;
import com.github.hollis.service.permission.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
@Api
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final PermissionService permissionService;

    @ApiOperation(value = "获取角色列表")
    @GetMapping
//    @OperationLog(type = OperationTypeEnum.QUERY, target = OperationTargetEnum.ROLE)
    public Result<List<RoleVo>> getRoleList() {
        return Result.success(roleMapper.entityToVos(roleService.findAllRoles()));
    }

    @ApiOperation(value = "添加一个角色")
    @PostMapping
    @PreAuthorize("hasAuthority('addRole') or hasRole('ADMIN')")
    @OperationLog(type = OperationTypeEnum.CREATE, target = OperationTargetEnum.ROLE, content = "创建角色")
    public Result<Void> addRole(@RequestBody CreateRoleDto dto) {
        roleService.addRole(dto);
        return Result.success();
    }

    @ApiOperation(value = "编辑一个角色")
    @PutMapping
    @PreAuthorize("hasAuthority('updateRole') or hasRole('ADMIN')")
    @OperationLog(type = OperationTypeEnum.UPDATE, target = OperationTargetEnum.ROLE, content = "修改角色信息")
    public Result<Void> updateRole(@RequestBody UpdateRoleDto dto) {
        roleService.updateRole(dto);
        return Result.success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('deleteRole') or hasRole('ADMIN')")
    @OperationLog(type = OperationTypeEnum.DELETE, target = OperationTargetEnum.ROLE, content = "删除角色")
    public Result<Void> deleteRole(@PathVariable Integer roleId) {
        permissionService.deleteRole(roleId);
        return Result.success();
    }

    @ApiOperation(value = "获取角色绑定的用户列表")
    @GetMapping("/{roleId}/userList")
    public Result<List<UserVo>> userListByRole(@PathVariable Integer roleId) {
        return Result.success(userMapper.entityToVoList(permissionService.getUsersByRole(roleId)));
    }

    @ApiOperation(value = "保存角色绑定的用户列表")
    @PostMapping("/{roleId}/userList")
    @PreAuthorize("hasAuthority('saveRoleUser') or hasRole('ADMIN')")
    @OperationLog(type = OperationTypeEnum.SAVE, target = OperationTargetEnum.ROLE_USER, content = "保存用户角色关联表")
    public Result<Void> saveRoleUser(@PathVariable Integer roleId, @RequestBody List<Integer> userIds) {
        permissionService.saveRoleUser(roleId, userIds);
        return Result.success();
    }

    @ApiOperation(value = "获取角色绑定的资源列表")
    @GetMapping("/{roleId}/resourceTree")
    public Result<List<ResourceTree>> getResourceTreeByRole(@PathVariable Integer roleId) {
        return Result.success(permissionService.getResourceTreeByRole(roleId));
    }

    @ApiOperation(value = "保存角色绑定的资源列表")
    @PostMapping("/{roleId}/resource")
    @PreAuthorize("hasAuthority('saveRoleResource') or hasRole('ADMIN')")
    @OperationLog(type = OperationTypeEnum.SAVE, target = OperationTargetEnum.ROLE_RESOURCE, content = "保存角色资源关联表")
    public Result<Void> saveRoleResource(@PathVariable Integer roleId, @RequestBody List<Integer> resourceIds) {
        permissionService.saveRoleResource(roleId, resourceIds);
        return Result.success();
    }

}

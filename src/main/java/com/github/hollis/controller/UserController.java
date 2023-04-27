package com.github.hollis.controller;

import com.github.hollis.aspect.OperationLog;
import com.github.hollis.dao.entity.RoleEntity;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.dto.permission.CreateUserDto;
import com.github.hollis.domain.dto.permission.QueryUserDto;
import com.github.hollis.domain.dto.permission.UpdateUserDto;
import com.github.hollis.domain.vo.base.BaseVo;
import com.github.hollis.domain.vo.base.PageResponse;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.permission.UserVo;
import com.github.hollis.enums.OperationTargetEnum;
import com.github.hollis.enums.OperationTypeEnum;
import com.github.hollis.event.UserForceLogoutEvent;
import com.github.hollis.mapper.RoleMapper;
import com.github.hollis.mapper.UserMapper;
import com.github.hollis.security.LoginUser;
import com.github.hollis.service.permission.RoleService;
import com.github.hollis.service.permission.UserService;
import com.github.hollis.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('GetUserList')")
    public Result<List<UserVo>> getUsers() {
        return Result.success(userMapper.entityToVoList(userService.findAll()));
    }


    @PostMapping("/page")
    @ApiOperation(value = "分页获取用户列表")
    public Result<PageResponse<UserVo>> getPages(@RequestBody QueryUserDto dto) {
        Page<UserEntity> userEntities = userService.queryUserByPage(dto);
        List<UserVo> userVos = userMapper.entityToVoList(userEntities.getContent());
        List<Integer> userIds = userVos.stream().map(BaseVo::getId).collect(Collectors.toList());
        Map<Integer, List<RoleEntity>> userRoleMap = roleService.findRolesByUserIds(userIds);
        userVos.forEach(item -> item.setRoles(roleMapper.entityToVos(userRoleMap.get(item.getId()))));
        return Result.success(PageResponse.from(userEntities.getTotalElements(), userVos, dto));
    }


    @GetMapping("/currentUserInfo")
    @ApiOperation(value = "获取当前登录用户信息")
    public Result<LoginUser> getCurrentUserInfo() {
        return Result.success(UserUtil.getCurrentUser());
    }

    @PostMapping
    @ApiOperation(value = "添加用户")
    @OperationLog(type = OperationTypeEnum.CREATE, target = OperationTargetEnum.USER, content = "创建用户")
    public Result<Void> createUser(@Validated @RequestBody CreateUserDto reqDto) {
        userService.addUser(reqDto);
        return Result.success();
    }

    @PutMapping
    @ApiOperation(value = "修改用户信息")
    @OperationLog(type = OperationTypeEnum.UPDATE, target = OperationTargetEnum.USER, content = "修改用户信息")
    public Result<Void> updateUserInfo(@Validated @RequestBody UpdateUserDto reqDto) {
        userService.updateUser(reqDto);
        return Result.success();
    }

    @PutMapping("/{userId}/disable")
    @ApiOperation(value = "禁用用户")
    @OperationLog(type = OperationTypeEnum.UPDATE, target = OperationTargetEnum.USER, content = "禁用用户")
    public Result<Void> disableUser(@PathVariable Integer userId) {
        userService.disableUser(userId);
        return Result.success();
    }

    @PutMapping("/{userId}/enable")
    @ApiOperation(value = "启用用户")
    @OperationLog(type = OperationTypeEnum.UPDATE, target = OperationTargetEnum.USER, content = "启用用户")
    public Result<Void> enableUser(@PathVariable Integer userId) {
        userService.enableUser(userId);
        return Result.success();
    }

    @PutMapping("/{userId}/resetPassword")
    @ApiOperation(value = "重置用户密码")
    @OperationLog(type = OperationTypeEnum.UPDATE, target = OperationTargetEnum.USER, content = "重置用户密码")
    public Result<String> resetPassword(@PathVariable Integer userId) {
        return Result.success(userService.resetPassword(userId));
    }


    @DeleteMapping("/{userId}/delete")
    @ApiOperation(value = "删除用户")
    @OperationLog(type = OperationTypeEnum.DELETE, target = OperationTargetEnum.USER, content = "删除用户")
    public Result<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return Result.success();
    }
}

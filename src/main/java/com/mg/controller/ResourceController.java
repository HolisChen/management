package com.mg.controller;

import com.mg.dao.entity.ResourceEntity;
import com.mg.domain.dto.permission.CreateResourceDto;
import com.mg.domain.dto.permission.UpdateResourceDto;
import com.mg.domain.vo.permission.ResourceTree;
import com.mg.domain.vo.base.Result;
import com.mg.mapper.ResourceMapper;
import com.mg.service.permission.PermissionService;
import com.mg.service.permission.ResourceService;
import com.mg.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resource")
@Api
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;
    private final ResourceMapper resourceMapper;
    private final PermissionService permissionService;

    @PostMapping
    @ApiOperation(value = "创建一个资源")
    public Result<Void> addResource(@RequestBody CreateResourceDto requestDto) {
        ResourceEntity entity = resourceMapper.dtoToEntity(requestDto);
        entity.setCreateBy(UserUtil.getCurrentUserId());
        resourceService.addResource(entity);
        return Result.success();
    }

    @DeleteMapping()
    @ApiOperation(value = "删除资源")
    public Result<Void> removeResource(@RequestBody List<Integer> resourceIds) {
        permissionService.removeResources(resourceIds);
        return Result.success();
    }

    @PutMapping()
    @ApiOperation(value = "修改资源")
    public Result<Void> updateResource(@RequestBody UpdateResourceDto requestDto) {
        resourceService.updateResource(requestDto);
        return Result.success();
    }

    @GetMapping("/resourceTree")
    @ApiOperation(value = "获取资源树")
    public Result<List<ResourceTree>> getMenuTree(@RequestParam("resourceTypes") List<Byte> resourceTypes) {
        return Result.success(resourceService.getResourceTree(resourceTypes));
    }
}

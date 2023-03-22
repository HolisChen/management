package com.mg.service.permission;

import com.mg.dao.entity.ResourceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final RoleUserService roleUserService;
    private final RoleResourceService roleResourceService;
    private final ResourceService resourceService;

    /**
     * 通过userId获取用户所有已授权的resource
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = "RESOURCE", key = "#userId")
    public List<ResourceEntity> getAuthorizedResource(Integer userId) {
        //获取所有角色
        List<Integer> roleIds = roleUserService.getRoleIdByUserId(userId);
        //获取角色关联的所有resourceId集合
        List<Integer> resourceIds = roleResourceService.getResourceIdsByRoleIds(roleIds);
        //获取所有Resources
        return resourceService.getByResourceIds(resourceIds);
    }
}

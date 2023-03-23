package com.mg.service.permission;

import com.mg.dao.entity.ResourceEntity;
import com.mg.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final RoleUserService roleUserService;
    private final RoleResourceService roleResourceService;
    private final ResourceService resourceService;
    private final RoleService roleService;

    /**
     * 通过userId获取用户所有已授权的resource
     * @param userId
     * @return
     */
    @Cacheable(cacheNames = "RESOURCE", key = "#userId")
    public List<ResourceEntity> getAuthorizedResource(Integer userId) {
//        //获取所有角色
//        List<Integer> roleIds = roleUserService.getRoleIdByUserId(userId);
//        //获取角色关联的所有resourceId集合
//        List<Integer> resourceIds = roleResourceService.getResourceIdsByRoleIds(roleIds);
//        //获取所有Resources
//        return resourceService.getByResourceIds(resourceIds);
        return resourceService.getAuthorizedResources(userId);
    }


    @Transactional(rollbackFor = Exception.class)
    public void removeResources(List<Integer> resourceIds) {
        resourceService.removeResources(resourceIds);
        roleResourceService.removeByResourceIds(resourceIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Integer roleId) {
        if (roleService.deleteRole(roleId)) {
            roleUserService.deleteByRoleId(roleId);
            roleResourceService.deleteByRoleId(roleId);
        }
    }
}

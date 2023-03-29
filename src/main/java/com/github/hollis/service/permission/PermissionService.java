package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.ResourceEntity;
import com.github.hollis.dao.entity.RoleResourceEntity;
import com.github.hollis.dao.entity.RoleUserEntity;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.vo.permission.ResourceTree;
import com.github.hollis.enums.ResourceTypeEnum;
import com.github.hollis.utils.UserUtil;
import com.sun.org.apache.regexp.internal.RE;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final RoleUserService roleUserService;
    private final RoleResourceService roleResourceService;
    private final ResourceService resourceService;
    private final RoleService roleService;
    private final UserService userService;

    /**
     * 通过userId获取用户所有已授权的resource
     * @param userId
     * @return
     */
    public List<ResourceEntity> getAuthorizedResource(Integer userId) {
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

    public List<UserEntity> getUsersByRole(Integer roleId) {
        return userService.findByRoleId(roleId);
    }

    @Transactional
    public void saveRoleUser(Integer roleId, List<Integer> userIds) {
        Integer currentUserId = UserUtil.getCurrentUserId();
        List<RoleUserEntity> roleUsers = roleUserService.findByRoleId(roleId);
        Set<Integer> existedUserSet = roleUsers.stream().map(RoleUserEntity::getUserId).collect(Collectors.toSet());
        roleUsers.removeIf(item -> userIds.contains(item.getUserId()));
        userIds.removeIf(existedUserSet::contains);

        List<Integer> deletedList = roleUsers.stream().map(RoleUserEntity::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deletedList)) {
            roleUserService.deleteByIds(deletedList);
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            List<RoleUserEntity> inserted = userIds.stream()
                    .map(userId -> {
                        RoleUserEntity roleUserEntity = new RoleUserEntity();
                        roleUserEntity.setCreateBy(currentUserId);
                        roleUserEntity.setRoleId(roleId);
                        roleUserEntity.setUserId(userId);
                        return roleUserEntity;
                    }).collect(Collectors.toList());
            roleUserService.saveAll(inserted);
        }
    }

    /**
     * 获取角色资源树
     * @param roleId
     * @return
     */
    public List<ResourceTree> getResourceTreeByRole(Integer roleId) {
        //获取基础资源树
        List<ResourceTree> resourceTree = resourceService.getResourceTree(Arrays.stream(ResourceTypeEnum.values()).map(ResourceTypeEnum::getCode).collect(Collectors.toList()));
        //获取角色绑定的resource集合
        Set<Integer> authorizedResourceIdSet = roleResourceService.findByRoleId(roleId).stream().map(RoleResourceEntity::getResourceId).collect(Collectors.toSet());
        //遍历树
        setChecked(resourceTree, authorizedResourceIdSet);
        return resourceTree;
    }

    private void setChecked(List<ResourceTree> resourceTrees,Set<Integer> authorizedResourceIds) {
        if (CollectionUtils.isEmpty(resourceTrees)) {
            return;
        }
        resourceTrees.forEach(tree -> {
            if (authorizedResourceIds.contains(tree.getId())) {
                tree.setChecked(true);
            }
            setChecked(tree.getChildren(), authorizedResourceIds);
        });
    }

    public void saveRoleResource(Integer roleId, List<Integer> resourceIds) {
        Integer currentUserId = UserUtil.getCurrentUserId();
        List<RoleResourceEntity> existedResourceList = roleResourceService.findByRoleId(roleId);
        Set<Integer> existedResourceIdSet = existedResourceList.stream().map(RoleResourceEntity::getResourceId).collect(Collectors.toSet());
        existedResourceList.removeIf(item -> resourceIds.contains(item.getResourceId()));
        resourceIds.removeIf(existedResourceIdSet::contains);
        List<Integer> deletedList = existedResourceList.stream().map(RoleResourceEntity::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deletedList)) {
            roleResourceService.deleteByIds(deletedList);
        }
        if (!CollectionUtils.isEmpty(resourceIds)) {
            List<RoleResourceEntity> inserted = resourceIds.stream().map(resourceId -> {
                RoleResourceEntity roleResourceEntity = new RoleResourceEntity();
                roleResourceEntity.setCreateBy(currentUserId);
                roleResourceEntity.setRoleId(roleId);
                roleResourceEntity.setResourceId(resourceId);
                return roleResourceEntity;
            }).collect(Collectors.toList());
            roleResourceService.saveAll(inserted);
        }
    }

    public List<ResourceTree> getAuthorizedMenu(Integer userId) {
        List<ResourceEntity> authorizedResource = this.getAuthorizedResource(userId)
                .stream()
                .filter(item -> Objects.equals(item.getResourceType(),ResourceTypeEnum.MENU.getCode()))
                .collect(Collectors.toList());
        return resourceService.buildTree(0, authorizedResource);

    }
}

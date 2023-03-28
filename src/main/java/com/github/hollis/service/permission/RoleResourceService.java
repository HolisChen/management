package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleResourceEntity;
import com.github.hollis.dao.repository.RoleResourceRepository;
import com.github.hollis.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleResourceService extends CRUDService<RoleResourceEntity, Integer, RoleResourceRepository> {
    private final RoleResourceRepository roleResourceRepository;


    public List<Integer> getResourceIdsByRoleIds(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return roleResourceRepository.findByRoleIdIn(roleIds)
                .stream()
                .map(RoleResourceEntity::getResourceId)
                .collect(Collectors.toList());
    }

    public void removeByResourceIds(List<Integer> resourceIds) {
        roleResourceRepository.deleteByResourceIdIn(resourceIds);
    }

    public void deleteByRoleId(Integer roleId) {
        roleResourceRepository.deleteByRoleId(roleId);
    }

    public List<RoleResourceEntity> findByRoleId(Integer roleId) {
        return roleResourceRepository.findByRoleIdIn(Collections.singletonList(roleId));
    }

    @Override
    protected RoleResourceRepository getDao() {
        return roleResourceRepository;
    }
}

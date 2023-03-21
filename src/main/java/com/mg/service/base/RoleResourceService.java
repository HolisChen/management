package com.mg.service.base;

import com.mg.dao.entity.RoleResourceEntity;
import com.mg.dao.repository.RoleResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleResourceService {
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
}

package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleUserEntity;
import com.github.hollis.dao.repository.RoleUserRepository;
import com.github.hollis.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleUserService extends CRUDService<RoleUserEntity, Integer, RoleUserRepository> {
    private final RoleUserRepository roleUserRepository;

    public void deleteByRoleId(Integer roleId) {
        roleUserRepository.deleteByRoleId(roleId);
    }

    public List<RoleUserEntity> findByRoleId(Integer roleId) {
        return roleUserRepository.findByRoleId(roleId);
    }

    @Override
    protected RoleUserRepository getDao() {
        return roleUserRepository;
    }

    public List<RoleUserEntity> findByUserId(Integer userId) {
        return roleUserRepository.findByUserId(userId);
    }

    @Transactional
    public void saveUserRole(Integer userId, List<Integer> roleIds, Integer operationId) {
        List<RoleUserEntity> existRoleUserList = this.findByUserId(userId);
        Set<Integer> existRoleIds = existRoleUserList.stream().map(RoleUserEntity::getRoleId).collect(Collectors.toSet());

        //新增的
        List<RoleUserEntity> insertList = roleIds.stream()
                .filter(roleId -> !existRoleIds.contains(roleId))
                .map(roleId -> {
                    RoleUserEntity entity = new RoleUserEntity();
                    entity.setUserId(userId);
                    entity.setCreateBy(operationId);
                    entity.setRoleId(roleId);
                    return entity;
                }).collect(Collectors.toList());
        List<Integer> deleteIds = existRoleUserList.stream()
                .filter(existRole -> !roleIds.contains(existRole.getRoleId()))
                .map(RoleUserEntity::getId)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deleteIds)) {
            roleUserRepository.deleteAllById(deleteIds);
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            roleUserRepository.saveAll(insertList);
        }
    }
}

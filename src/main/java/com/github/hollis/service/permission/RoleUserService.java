package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleUserEntity;
import com.github.hollis.dao.repository.RoleUserRepository;
import com.github.hollis.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleUserService extends CRUDService<RoleUserEntity, Integer, RoleUserRepository> {
    private final RoleUserRepository roleUserRepository;

    /**
     * 通过userId获取roleId集合
     * @param userId
     * @return
     */
    public List<Integer> getRoleIdByUserId(Integer userId) {
        return roleUserRepository.findByUserId(userId)
                .stream()
                .map(RoleUserEntity::getRoleId)
                .collect(Collectors.toList());
    }


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
}

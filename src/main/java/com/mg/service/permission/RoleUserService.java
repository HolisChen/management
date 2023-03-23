package com.mg.service.permission;

import com.mg.dao.entity.RoleUserEntity;
import com.mg.dao.repository.RoleUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleUserService {
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
}

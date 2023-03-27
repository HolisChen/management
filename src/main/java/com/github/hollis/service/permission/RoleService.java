package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleEntity;
import com.github.hollis.domain.dto.permission.CreateRoleDto;
import com.github.hollis.domain.dto.permission.UpdateRoleDto;
import com.github.hollis.dao.repository.RoleRepository;
import com.github.hollis.mapper.RoleMapper;
import com.github.hollis.service.CRUDService;
import com.github.hollis.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService extends CRUDService<RoleEntity, Integer, RoleRepository> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    public List<RoleEntity> findAllRoles() {
        return roleRepository.findAll();
    }

    public void addRole(CreateRoleDto dto) {
        if (roleRepository.findByRoleCodeAndDeleteAtIsNull(dto.getRoleCode()).isPresent()) {
            throw new IllegalArgumentException("角色code已存在");
        }
        RoleEntity roleEntity = roleMapper.dtoToEntity(dto);
        roleEntity.setCreateBy(UserUtil.getCurrentUserId());
        roleRepository.save(roleEntity);
    }


    @Override
    protected RoleRepository getDao() {
        return roleRepository;
    }

    public void updateRole(UpdateRoleDto dto) {
        Optional<RoleEntity> roleExists = roleRepository.findById(dto.getId());
        if (!roleExists.isPresent()) {
            throw new IllegalArgumentException("修改的角色不存在");
        }
        RoleEntity roleEntity = roleExists.get();
        if (!Objects.equals(dto.getRoleCode(), roleEntity.getRoleCode()) && roleRepository.findByRoleCodeAndDeleteAtIsNull(dto.getRoleCode()).isPresent()) {
            throw new IllegalArgumentException("角色code已存在");
        }
        roleEntity = dto.update(roleEntity);
        roleRepository.save(roleEntity);
    }

    public boolean deleteRole(Integer roleId) {
        return roleRepository.logicDelete(roleId, UserUtil.getCurrentUserId()) > 0;
    }
}

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
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService extends CRUDService<RoleEntity, Integer, RoleRepository> {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleUserService roleUserService;
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

    public Map<Integer, List<RoleEntity>> findRolesByUserIds(List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        Map<Integer, List<Integer>> userRoleIdMap = roleUserService.findRoleIdByUserId(userIds);
        List<Integer> roleIds = userRoleIdMap.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
        List<RoleEntity> roles = roleRepository.findAllById(roleIds);
        Map<Integer, List<RoleEntity>> result = new HashMap<>(userIds.size());
        userIds.forEach(userId -> {
                    if (userRoleIdMap.containsKey(userId)) {
                        result.put(userId, roles.stream().filter(item -> userRoleIdMap.get(userId).contains(item.getId())).collect(Collectors.toList()));
                    }else {
                        result.put(userId,Collections.emptyList());
                    }
                });
        return result;
    }
}

package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.RoleDepartmentEntity;
import com.github.hollis.dao.repository.RoleDepartmentRepository;
import com.github.hollis.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleDepartmentService {
    private final RoleDepartmentRepository roleDepartmentRepository;

    public List<RoleDepartmentEntity> findByDepartmentId(Integer departmentId) {
        return roleDepartmentRepository.findByDepartmentId(departmentId);
    }

    public List<RoleDepartmentEntity> findByRoleId(Integer roleId) {
        return roleDepartmentRepository.findByRoleId(roleId);
    }

    public void saveRoleDepartment(Integer roleId, List<Integer> departmentIds) {
        Integer userId = UserUtil.getCurrentUserId();
        List<RoleDepartmentEntity> existsData = this.findByRoleId(roleId);
        List<Integer> existsDepIds = existsData.stream().map(RoleDepartmentEntity::getDepartmentId).collect(Collectors.toList());

        List<Integer> deleteItems = existsData.stream()
                .filter(item -> !departmentIds.contains(item.getDepartmentId()))
                .map(RoleDepartmentEntity::getId)
                .collect(Collectors.toList());

        List<RoleDepartmentEntity> newItems = departmentIds.stream()
                .filter(depId -> !existsDepIds.contains(depId))
                .map(depId -> {
                    RoleDepartmentEntity roleDepartment = new RoleDepartmentEntity();
                    roleDepartment.setCreateBy(userId);
                    roleDepartment.setRoleId(roleId);
                    roleDepartment.setDepartmentId(depId);
                    return roleDepartment;
                }).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(deleteItems)) {
            roleDepartmentRepository.deleteAllById(deleteItems);
        }
        if (!CollectionUtils.isEmpty(newItems)) {
            roleDepartmentRepository.saveAll(newItems);
        }
    }
}

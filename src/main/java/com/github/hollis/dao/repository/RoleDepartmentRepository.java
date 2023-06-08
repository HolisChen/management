package com.github.hollis.dao.repository;

import com.github.hollis.dao.entity.RoleDepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDepartmentRepository extends JpaRepository<RoleDepartmentEntity, Integer> {

    List<RoleDepartmentEntity> findByDepartmentId(Integer departmentId);

    List<RoleDepartmentEntity> findByRoleId(Integer roleId);
}

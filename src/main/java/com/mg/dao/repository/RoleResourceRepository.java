package com.mg.dao.repository;

import com.mg.dao.entity.RoleResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResourceEntity, Integer> {

    List<RoleResourceEntity> findByRoleIdIn(List<Integer> roleIds);
}

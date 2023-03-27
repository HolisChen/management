package com.github.hollis.dao.repository;

import com.github.hollis.dao.entity.RoleResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResourceEntity, Integer> {

    List<RoleResourceEntity> findByRoleIdIn(List<Integer> roleIds);

    void deleteByResourceIdIn(List<Integer> resourceIds);

    void deleteByRoleId(Integer roleId);
}

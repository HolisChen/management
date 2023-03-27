package com.github.hollis.dao.repository;

import com.github.hollis.dao.entity.RoleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUserEntity, Integer> {
    List<RoleUserEntity> findByUserId(Integer userId);
    int deleteByRoleId(Integer roleId);
}

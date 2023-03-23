package com.mg.dao.repository;

import com.mg.dao.entity.RoleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUserRepository extends JpaRepository<RoleUserEntity, Integer> {
    List<RoleUserEntity> findByUserId(Integer userId);
    int deleteByRoleId(Integer roleId);
}

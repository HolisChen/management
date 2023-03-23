package com.mg.dao.repository;

import com.mg.dao.BaseRepository;
import com.mg.dao.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleCodeAndDeleteAtIsNull(String roleCode);
}

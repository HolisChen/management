package com.github.hollis.dao.repository;

import com.github.hollis.dao.BaseRepository;
import com.github.hollis.dao.entity.RoleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleCodeAndDeleteAtIsNull(String roleCode);
}

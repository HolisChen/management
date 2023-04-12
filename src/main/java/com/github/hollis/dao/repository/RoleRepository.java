package com.github.hollis.dao.repository;

import com.github.hollis.dao.BaseRepository;
import com.github.hollis.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.hibernate.loader.Loader.SELECT;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleCodeAndDeleteAtIsNull(String roleCode);
}

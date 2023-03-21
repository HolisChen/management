package com.mg.dao.repository;

import com.mg.dao.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Integer> {
    List<ResourceEntity> findByIdInAndDeleteAtIsNull(List<Integer> resourceIds);
}

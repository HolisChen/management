package com.mg.dao.repository;

import com.mg.dao.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Integer> {
    List<ResourceEntity> findByIdInAndDeleteAtIsNull(List<Integer> resourceIds);

    Optional<ResourceEntity> findByResourceCodeAndDeleteAtIsNull(String resourceCode);

    List<ResourceEntity> findByParentId(Integer parentId);

    List<ResourceEntity> findAllByResourceTypeIn(List<Byte> resourceTypes);
}

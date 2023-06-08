package com.github.hollis.dao.repository;

import com.github.hollis.dao.BaseRepository;
import com.github.hollis.dao.entity.ResourceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends BaseRepository<ResourceEntity, Integer> {

    Optional<ResourceEntity> findByResourceCodeAndDeleteAtIsNull(String resourceCode);

    List<ResourceEntity> findByParentId(Integer parentId);

    List<ResourceEntity> findAllByResourceTypeInAndDeleteAtIsNull(List<Byte> resourceTypes);

    @Query("SELECT distinct e " +
            "FROM UserEntity a join RoleUserEntity b on a.id = b.userId " +
            "join RoleResourceEntity c on c.roleId = b.roleId " +
            "join RoleEntity d on b.roleId = d.id " +
            "join ResourceEntity e on c.resourceId = e.id " +
            "where a.deleteAt is null and d.deleteAt is null and e.deleteAt is null " +
            "and a.id = ?1 ")
    List<ResourceEntity> findAuthorizedByUserId(Integer userId);

    @Query( "SELECT distinct e " +
            "FROM UserEntity a join RoleDepartmentEntity b on a.departmentId = b.departmentId " +
            "join RoleResourceEntity c on c.roleId = b.roleId " +
            "join ResourceEntity e on c.resourceId = e.id " +
            "where a.deleteAt is null and e.deleteAt is null " +
            "and a.id = ?1 ")
    List<ResourceEntity> findDepartmentAuthorizedByUserId(Integer userId);
}

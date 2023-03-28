package com.github.hollis.dao.repository;

import com.github.hollis.dao.BaseRepository;
import com.github.hollis.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    UserEntity findByLoginIdAndDeleteAtIsNull(String loginId);

    @Query("SELECT c FROM RoleEntity a JOIN RoleUserEntity b ON a.id = b.roleId JOIN UserEntity c on c.id = b.userId " +
            "WHERE a.deleteAt is null and c.deleteAt is null and a.id = ?1")
    List<UserEntity> findByRoleId(Integer roleId);
}

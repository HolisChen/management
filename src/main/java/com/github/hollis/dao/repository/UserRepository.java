package com.github.hollis.dao.repository;

import com.github.hollis.dao.BaseRepository;
import com.github.hollis.dao.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    UserEntity findByLoginIdAndDeleteAtIsNull(String loginId);
}

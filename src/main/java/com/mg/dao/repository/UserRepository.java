package com.mg.dao.repository;

import com.mg.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLoginIdAndDeleteAtIsNull(String loginId);
}

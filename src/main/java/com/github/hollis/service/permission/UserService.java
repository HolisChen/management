package com.github.hollis.service.permission;

import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.dao.repository.UserRepository;
import com.github.hollis.mapper.UserMapper;
import com.github.hollis.domain.bo.base.UserBo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * 通过loginId查询用户信息
     * @param loginId
     * @return
     */
    public Optional<UserEntity> getByLoginId(String loginId) {
        return Optional.ofNullable(userRepository.findByLoginIdAndDeleteAtIsNull(loginId));
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public List<UserEntity> findByRoleId(Integer roleId) {
        return userRepository.findByRoleId(roleId);
    }

}

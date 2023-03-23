package com.mg.service.permission;

import com.mg.dao.entity.UserEntity;
import com.mg.dao.repository.UserRepository;
import com.mg.domain.bo.base.UserBo;
import com.mg.mapper.UserMapper;
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
    public Optional<UserBo> getByLoginId(String loginId) {
        return Optional.ofNullable(userRepository.findByLoginIdAndDeleteAtIsNull(loginId))
                .map(userMapper::entityToBo);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

}

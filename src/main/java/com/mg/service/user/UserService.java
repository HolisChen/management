package com.mg.service.user;

import com.mg.dao.repository.UserRepository;
import com.mg.domain.bo.user.UserBo;
import com.mg.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return Optional.ofNullable(userRepository.findByLoginId(loginId))
                .map(userMapper::entityToBo);
    }
}

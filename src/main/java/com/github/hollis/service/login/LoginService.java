package com.github.hollis.service.login;

import com.alibaba.fastjson.JSON;
import com.github.hollis.domain.bo.base.UserBo;
import com.github.hollis.domain.dto.login.LoginDto;
import com.github.hollis.service.permission.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 执行登录
     * @param loginDto
     * @return token
     */
    public String doLogin(LoginDto loginDto) {
        Optional<UserBo> loginUser = userService.getByLoginId(loginDto.getLoginId());
        if (!loginUser.isPresent()) {
            throw new IllegalArgumentException("用户不存在");
        }
        UserBo user = loginUser.get();
        if (!Objects.equals(user.getStatus(), (byte) 1)) {
            throw new IllegalArgumentException("用户账户被禁用禁止登录");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        stringRedisTemplate.opsForValue().set(token, JSON.toJSONString(user), 30, TimeUnit.MINUTES);
        return token;
    }

    public void doLogout(String token) {
        if (null != token) {
            stringRedisTemplate.delete(token);
        }
    }
}

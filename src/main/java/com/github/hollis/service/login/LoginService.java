package com.github.hollis.service.login;

import com.alibaba.fastjson.JSON;
import com.github.hollis.constant.RedisConstants;
import com.github.hollis.constant.SecurityConstants;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.dto.login.LoginDto;
import com.github.hollis.security.LoginUser;
import com.github.hollis.security.TokenGenerator;
import com.github.hollis.service.permission.UserService;
import com.github.hollis.utils.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    private final TokenGenerator tokenGenerator;

    /**
     * 执行登录
     * @param loginDto
     * @return token
     */
    public String doLogin(LoginDto loginDto) {
        HttpServletRequest request =  WebUtil.getHttpServletRequest();
        String ip = WebUtil.getIpAddress(request);
        Optional<UserEntity> loginUser = userService.getByLoginId(loginDto.getLoginId());
        if (!loginUser.isPresent()) {
            throw new IllegalArgumentException("用户不存在");
        }
        UserEntity user = loginUser.get();
        if (!Objects.equals(user.getStatus(), (byte) 1)) {
            throw new IllegalArgumentException("用户账户被禁用禁止登录");
        }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        //生成token
        String token = tokenGenerator.generateToken(user);
        stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_TOKEN_PREFIX + "" + token, JSON.toJSONString(new LoginUser(user, ip, token)), SecurityConstants.DEFAULT_TOKEN_EXPIRE, TimeUnit.MINUTES);
        return token;
    }

    public void doLogout(String token) {
        if (null != token) {
            stringRedisTemplate.delete(token);
        }
    }
}

package com.github.hollis.service.login;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.math.Calculator;
import com.alibaba.fastjson.JSON;
import com.github.hollis.constant.RedisConstants;
import com.github.hollis.constant.SecurityConstants;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.dto.login.LoginDto;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.domain.vo.permission.CaptureResponseVo;
import com.github.hollis.security.LoginUser;
import com.github.hollis.security.TokenGenerator;
import com.github.hollis.service.permission.UserService;
import com.github.hollis.utils.WebUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoginService {
    private static final String CAPTURE_KEY = "capture:%s";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;
    private final TokenGenerator tokenGenerator;

    /**
     * 执行登录
     *
     * @param loginDto
     * @return token
     */
    public String doLogin(LoginDto loginDto) {
        //校验验证码
        String key = String.format(CAPTURE_KEY, loginDto.getCaptureId());
        String value = Optional.ofNullable(stringRedisTemplate.opsForValue().get(key)).orElseThrow(() -> new IllegalArgumentException("验证码已失效"));
        try {
            if (!value.equals(loginDto.getCapture())) {
                throw new IllegalArgumentException("验证码有误");
            }
            HttpServletRequest request = WebUtil.getHttpServletRequest();
            String ip = WebUtil.getIpAddress(request);
            Optional<UserEntity> loginUser = userService.getByLoginId(loginDto.getLoginId());
            if (!loginUser.isPresent()) {
                throw new IllegalArgumentException("用户不存在");
            }
            UserEntity user = loginUser.get();
            if (!Objects.equals(user.getStatus(), (byte) 1)) {
                throw new IllegalArgumentException("用户账户被禁用，禁止登录");
            }
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                throw new IllegalArgumentException("密码错误");
            }
            //生成token
            String token = tokenGenerator.generateToken(user);
            stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_TOKEN_PREFIX + token, JSON.toJSONString(new LoginUser(user, ip, token)), SecurityConstants.DEFAULT_TOKEN_EXPIRE, TimeUnit.MINUTES);
            return token;
        } finally {
            stringRedisTemplate.delete(key);
        }
    }

    public void doLogout(String token) {
        if (null != token) {
            stringRedisTemplate.delete(token);
        }
    }

    public CaptureResponseVo createCapture() {
        String captureId = UUID.fastUUID().toString();
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 32, 4, 4);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        // 重新生成code
        captcha.createCode();
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            captcha.write(os);
            int verifyResult = (int) Calculator.conversion(captcha.getCode());
            stringRedisTemplate.opsForValue().set(String.format(CAPTURE_KEY, captureId), Integer.toString(verifyResult), Duration.ofMinutes(2));
            return new CaptureResponseVo(captureId, Base64.getEncoder().encodeToString(os.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

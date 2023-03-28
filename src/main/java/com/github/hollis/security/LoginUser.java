package com.github.hollis.security;

import com.github.hollis.dao.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LoginUser {
    private Integer userId;
    private String loginId;
    private String username;
    private Byte status;
    private Date loginTime;
    private String loginIp;
    private String token;

    public LoginUser(UserEntity userEntity, String loginIp, String token) {
        this.userId = userEntity.getId();
        this.loginId = userEntity.getLoginId();
        this.username = userEntity.getUsername();
        this.status = userEntity.getStatus();
        this.loginTime = new Date();
        this.loginIp = loginIp;
        this.token = token;
    }
}

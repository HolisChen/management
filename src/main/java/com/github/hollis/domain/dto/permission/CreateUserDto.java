package com.github.hollis.domain.dto.permission;

import com.github.hollis.dao.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CreateUserDto {
    @NotBlank(message = "登录ID不能为空")
    private String loginId;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String phoneNumber;

    private String email;

    private List<Integer> bindingRoles;

    public UserEntity newUser(String encodedPassword, Integer createBy) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginId(this.getLoginId());
        userEntity.setUsername(this.getUsername());
        userEntity.setPassword(encodedPassword);
        userEntity.setStatus((byte) 1);
        userEntity.setEmail(this.getEmail());
        userEntity.setPhoneNumber(this.getPhoneNumber());
        userEntity.setCreateBy(createBy);
        return userEntity;
    }
}

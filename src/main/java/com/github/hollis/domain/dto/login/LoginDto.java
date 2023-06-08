package com.github.hollis.domain.dto.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {
    @NotBlank(message = "用户名不能为空")
    private String loginId;
    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码有误")
    private String captureId;

    @NotBlank(message = "请输入验证码")
    private String capture;


    public boolean isStartup() {
        return  captureId == null;
    }
}

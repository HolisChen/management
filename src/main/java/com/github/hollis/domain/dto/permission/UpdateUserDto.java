package com.github.hollis.domain.dto.permission;


import com.github.hollis.dao.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UpdateUserDto {
    @NotNull
    private Integer id;

    @NotBlank
    private String username;

    private String email;

    private String phoneNumber;

    private List<Integer> bindingRoles;

    private Integer departmentId;

    public List<Integer> getBindingRoles() {
        return null == bindingRoles ? Collections.emptyList() : bindingRoles;
    }

    public UserEntity convert4Update(UserEntity origin, Integer updateBy) {
        origin.setUpdateBy(updateBy);
        origin.setUsername(username);
        origin.setEmail(email);
        origin.setPhoneNumber(phoneNumber);
        origin.setDepartmentId(departmentId);
        return origin;
    }
}

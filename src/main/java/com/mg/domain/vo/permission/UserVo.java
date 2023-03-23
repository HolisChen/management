package com.mg.domain.vo.permission;

import com.mg.domain.vo.base.BaseVo;
import lombok.Data;


@Data
public class UserVo extends BaseVo {
    private String loginId;

    private String username;

    private Byte status;

    private String email;

    private String phoneNumber;
}

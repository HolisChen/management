package com.github.hollis.domain.vo.permission;

import com.github.hollis.domain.vo.base.BaseVo;
import lombok.Data;


@Data
public class UserVo extends BaseVo {
    private String loginId;

    private String username;

    private Byte status;

    private String email;

    private String phoneNumber;
}

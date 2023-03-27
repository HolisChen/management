package com.github.hollis.domain.bo.base;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBo {
    private Integer id;
    private String loginId;
    private String username;
    private String password;
    private Byte status;
    private LocalDateTime creatAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public boolean isDeleted() {
        return null != this.deleteAt;
    }

    public boolean isActive() {
        if (null == this.status) {
            return false;
        }
        return 1 == status;
    }

    public boolean isDisabled() {
        if (null == this.status) {
            return false;
        }
        return 2 == status;
    }
}

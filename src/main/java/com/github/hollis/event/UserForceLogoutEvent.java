package com.github.hollis.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserForceLogoutEvent extends ApplicationEvent {
    private Integer userId;

    public UserForceLogoutEvent(Integer userId) {
        super(userId);
        this.userId = userId;
    }
}

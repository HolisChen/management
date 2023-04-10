package com.github.hollis.event.listener;

import com.github.hollis.constant.RedisConstants;
import com.github.hollis.event.UserForceLogoutEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class UserForceLogoutEventListener implements ApplicationListener<UserForceLogoutEvent> {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    @Async
    public void onApplicationEvent(UserForceLogoutEvent event) {
        Integer userId = event.getUserId();
        if (userId == null) {
            return;
        }
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(RedisConstants.LOGIN_TOKEN_PREFIX + userId + ":*")
                .type(DataType.STRING)
                .build();
        Cursor<String> cursor = stringRedisTemplate.scan(scanOptions);
        List<String> deleteKeys = new ArrayList<>();
        while (cursor.hasNext()) {
            deleteKeys.add(cursor.next());
        }
        stringRedisTemplate.delete(deleteKeys);
    }
}

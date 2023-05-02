package com.github.hollis.aspect;

import com.github.hollis.dao.BaseEntity;
import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.vo.base.BaseVo;
import com.github.hollis.domain.vo.base.PageResponse;
import com.github.hollis.domain.vo.base.Result;
import com.github.hollis.service.permission.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ResponseUserInfoAspect {
    private final UserService userService;

    @Pointcut("execution(public com.github.hollis.domain.vo.base.Result com.github.hollis.controller.*.*(..))")
    public void pointcut() {

    }

    @AfterReturning(value = "pointcut()", returning = "res")
    public void afterReturn(Result<?> res) {
        Object data = res.getData();
        if (data instanceof BaseVo) {
            this.handleBaseVo((BaseVo) data);
        } else if (data instanceof Collection<?>) {
            Collection<?> collection = (Collection<?>) data;
            if (!CollectionUtils.isEmpty(collection)) {
                collection.stream().findFirst()
                        .ifPresent(item -> {
                            if (item instanceof BaseVo) {
                                this.handleBaseVo((Collection<BaseVo>) collection);
                            }
                        });
            }
        } else if (data instanceof PageResponse) {
            PageResponse<?> pageResponse = (PageResponse<?>) data;
            List<?> contents = pageResponse.getContents();
            if (!CollectionUtils.isEmpty(contents)) {
                contents.stream().findFirst()
                        .ifPresent(item -> {
                            if (item instanceof BaseVo) {
                                this.handleBaseVo((List<BaseVo>) contents);
                            }
                        });
            }
        }

    }

    private void handleBaseVo(BaseVo baseVo) {
        this.handleBaseVo(Collections.singletonList(baseVo));
    }

    private void handleBaseVo(Collection<BaseVo> baseVoCollection) {
        if (CollectionUtils.isEmpty(baseVoCollection)) {
            return;
        }

        List<Integer> userIds = baseVoCollection.stream()
                .flatMap(item -> Stream.of(item.getCreateBy(), item.getUpdateBy()))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<Integer, String> usernameMap = userService.findByIds(userIds)
                .stream()
                .collect(Collectors.toMap(BaseEntity::getId, UserEntity::getUsername));

        baseVoCollection.forEach(item -> {
            item.setCreateByName(usernameMap.get(item.getCreateBy()));
            item.setUpdateByName(usernameMap.get(item.getUpdateBy()));
        });
    }
}

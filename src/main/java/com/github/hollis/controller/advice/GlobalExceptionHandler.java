package com.github.hollis.controller.advice;

import com.github.hollis.domain.vo.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.github.hollis.controller")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> accessDenied(AccessDeniedException ex) {
        log.error(ex.getMessage(),ex);
        return Result.accessDenied("无权访问");
    }

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error(e.getMessage(),e);
        return Result.failure(e.getMessage());
    }

}

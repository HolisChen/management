package com.github.hollis.aspect;

import com.alibaba.fastjson.JSON;
import com.github.hollis.dao.entity.LogEntity;
import com.github.hollis.service.base.LogService;
import com.github.hollis.utils.UserUtil;
import com.github.hollis.utils.WebUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {
    private final LogService logService;

    @Pointcut("@annotation(com.github.hollis.aspect.OperationLog)")
    public void pointCut(){

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Integer userId = UserUtil.getCurrentUserId();
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog log = signature.getMethod().getAnnotation(OperationLog.class);
        HttpServletRequest request =  WebUtil.getHttpServletRequest();
        LogEntity logEntity = new LogEntity();
        logEntity.setCreateBy(userId);
        logEntity.setOperationType(log.type().name());
        logEntity.setOperationTarget(log.target().getCode());
        logEntity.setContent(log.content());
        logEntity.setParameter(args == null ? "" : JSON.toJSONString(args));
        logEntity.setIp(WebUtil.getIpAddress(request));
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed(args);
            logEntity.setSuccessFlag((byte) 1);
            logEntity.setResponse(null == result ? "" : JSON.toJSONString(result));
            return result;
        }catch (Throwable e) {
            logEntity.setExceptionInfo(e.getMessage());
            logEntity.setSuccessFlag((byte) 0);
            throw e;
        } finally {
            logEntity.setCostTime(System.currentTimeMillis() - start);
            logService.saveLog(logEntity);
        }

    }
}

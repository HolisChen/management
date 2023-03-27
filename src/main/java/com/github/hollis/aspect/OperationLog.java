package com.github.hollis.aspect;

import com.github.hollis.enums.OperationTargetEnum;
import com.github.hollis.enums.OperationTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface OperationLog {

    OperationTypeEnum type();

    OperationTargetEnum target();

    String content() default "";
}

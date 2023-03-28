package com.github.hollis.domain.vo.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;


    public static <T> Result<T> success(T data) {
        Result<T> r = success();
        r.setData(data);
        return r;
    }

    public static <T> Result<T> success() {
        Result<T> r = new Result<>();
        r.setCode(HttpStatus.OK.value());
        return r;
    }

    public static <T> Result<T> failure(String msg) {
        Result<T> r = new Result<>();
        r.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        r.setMsg(msg);
        return r;
    }

    public static <T>  Result<T> accessDenied(String msg) {
        Result<T> r = new Result<>();
        r.setCode(HttpStatus.FORBIDDEN.value());
        r.setMsg(msg);
        return r;
    }
}

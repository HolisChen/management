package com.github.hollis.domain.vo.log;

import lombok.Data;

import java.util.Date;

@Data
public class LogVo {
    private Integer id;

    private String operationType;

    private String operationTarget;

    private String content;

    private Byte successFlag;

    private String exceptionInfo;

    private String parameter;

    private String response;

    private Date createAt;

    private Integer createBy;

    private Long costTime;

    private String ip;
}

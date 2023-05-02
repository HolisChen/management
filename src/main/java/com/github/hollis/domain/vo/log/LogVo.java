package com.github.hollis.domain.vo.log;

import com.github.hollis.domain.vo.base.BaseVo;
import lombok.Data;

import java.util.Date;

@Data
public class LogVo extends BaseVo {

    private String operationType;

    private String operationTarget;

    private String content;

    private Byte successFlag;

    private String exceptionInfo;

    private String parameter;

    private String response;

    private Long costTime;

    private String ip;
}

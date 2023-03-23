package com.mg.domain.vo.base;

import lombok.Data;

import java.util.Date;

@Data
public class BaseVo {
    private Integer id;
    private Date createAt;
    private Date updateAt;
    private Integer createBy;
    private Integer updateBy;
}

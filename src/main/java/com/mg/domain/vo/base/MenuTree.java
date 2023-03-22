package com.mg.domain.vo.base;

import lombok.Data;

import java.util.Date;

@Data
public class MenuTree extends Tree<MenuTree>{
    private Integer id;
    private String resourceCode;
    private String resourceName;
    private String resourceDescription;
    private Byte resourceType;
    private String icon;
    private String resourceUrl;
    private Integer createBy;
    private Date createAt;
}

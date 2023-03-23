package com.mg.domain.vo.permission;

import com.mg.domain.vo.base.Tree;
import lombok.Data;

import java.util.Date;

@Data
public class ResourceTree extends Tree<ResourceTree> {
    private Integer id;
    private String resourceCode;
    private String resourceName;
    private String resourceDescription;
    private Byte resourceType;
    private String icon;
    private String resourceUrl;
    private Integer createBy;
    private Date createAt;
    private Integer sort;
}

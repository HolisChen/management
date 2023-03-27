package com.github.hollis.domain.dto.permission;

import lombok.Data;

@Data
public class CreateResourceDto {
    private Integer parentId;
    private String resourceCode;
    private String resourceName;
    private String resourceDescription;
    private Byte resourceType;
    private String resourceUrl;
    private String icon;
    private Integer sort;
}

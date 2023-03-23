package com.mg.domain.dto.permission;

import com.mg.dao.entity.ResourceEntity;
import com.mg.utils.UserUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateResourceDto extends CreateResourceDto{
    private Integer id;

    public ResourceEntity update(ResourceEntity origin) {
        origin.setResourceCode(getResourceCode());
        origin.setResourceName(getResourceName());
        origin.setResourceType(getResourceType());
        origin.setResourceDescription(getResourceDescription());
        origin.setSort(getSort());
        origin.setResourceUrl(getResourceUrl());
        origin.setIcon(getIcon());
        origin.setUpdateBy(UserUtil.getCurrentUserId());
        return origin;
    }
}

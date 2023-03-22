package com.mg.mapper;

import com.mg.dao.entity.ResourceEntity;
import com.mg.domain.dto.permission.CreateResourceDto;
import com.mg.domain.vo.base.MenuTree;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {

    ResourceEntity dtoToEntity(CreateResourceDto dto);

    MenuTree entityToMenuTree(ResourceEntity entity);
}

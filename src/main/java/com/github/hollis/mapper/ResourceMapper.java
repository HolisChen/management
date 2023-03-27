package com.github.hollis.mapper;

import com.github.hollis.domain.vo.permission.ResourceTree;
import com.github.hollis.dao.entity.ResourceEntity;
import com.github.hollis.domain.dto.permission.CreateResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {

    ResourceEntity dtoToEntity(CreateResourceDto dto);

    ResourceTree entityToMenuTree(ResourceEntity entity);
}

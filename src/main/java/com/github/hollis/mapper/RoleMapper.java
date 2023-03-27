package com.github.hollis.mapper;

import com.github.hollis.dao.entity.RoleEntity;
import com.github.hollis.domain.dto.permission.CreateRoleDto;
import com.github.hollis.domain.vo.permission.RoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleEntity dtoToEntity(CreateRoleDto dto);

    List<RoleVo> entityToVos(List<RoleEntity> roles);
}

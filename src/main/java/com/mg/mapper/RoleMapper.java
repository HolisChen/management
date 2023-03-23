package com.mg.mapper;

import com.mg.dao.entity.RoleEntity;
import com.mg.domain.dto.permission.CreateRoleDto;
import com.mg.domain.vo.permission.RoleVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleEntity dtoToEntity(CreateRoleDto dto);

    List<RoleVo> entityToVos(List<RoleEntity> roles);
}

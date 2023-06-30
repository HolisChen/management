package com.github.hollis.mapper;

import com.github.hollis.dao.entity.DepartmentEntity;
import com.github.hollis.domain.vo.permission.DepartmentVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    List<DepartmentVo> entityToVoList(List<DepartmentEntity> entityList);
}

package com.github.hollis.mapper;

import com.github.hollis.dao.entity.LogEntity;
import com.github.hollis.domain.vo.log.LogVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LogMapper {
    List<LogVo> entityToVo(List<LogEntity> entityList);
}

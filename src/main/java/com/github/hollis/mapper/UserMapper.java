package com.github.hollis.mapper;

import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.vo.permission.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    List<UserVo> entityToVoList(List<UserEntity> entity);
}

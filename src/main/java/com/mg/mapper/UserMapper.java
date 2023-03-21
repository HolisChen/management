package com.mg.mapper;

import com.mg.dao.entity.UserEntity;
import com.mg.domain.bo.base.UserBo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserBo entityToBo(UserEntity entity);
}

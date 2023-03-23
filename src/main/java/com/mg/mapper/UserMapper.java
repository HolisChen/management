package com.mg.mapper;

import com.mg.dao.entity.UserEntity;
import com.mg.domain.bo.base.UserBo;
import com.mg.domain.vo.permission.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserBo entityToBo(UserEntity entity);

    List<UserVo> entityToVoList(List<UserEntity> entity);
}

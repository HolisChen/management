package com.github.hollis.mapper;

import com.github.hollis.dao.entity.UserEntity;
import com.github.hollis.domain.vo.permission.UserVo;
import com.github.hollis.domain.bo.base.UserBo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserBo entityToBo(UserEntity entity);

    List<UserVo> entityToVoList(List<UserEntity> entity);
}

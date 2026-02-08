package com.eunho.vuejs.mapper;

import com.eunho.vuejs.dto.ResponseUser;
import com.eunho.vuejs.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    ResponseUser from(User user);
}

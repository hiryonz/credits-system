package com.sistemaCreditos.demo.mapper;

import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.dto.UserResponseDto;
import com.sistemaCreditos.demo.model.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    UserEntity convertToEntity(UserRequestDto request);

    UserResponseDto convertToDto(UserEntity user);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "token", source = "token")
    UserResponseDto convertToDto(UserEntity user, String token);
}

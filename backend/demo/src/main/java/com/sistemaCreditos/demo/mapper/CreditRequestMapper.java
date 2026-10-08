package com.sistemaCreditos.demo.mapper;

import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.model.CreditRequestEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CreditRequestEntity convertToEntity(CreditRequestDto creditRequestDto);

    CreditRequestDto convertToDto(CreditRequestEntity creditRequestEntity);

    List<CreditRequestDto> convertToDtoList(List<CreditRequestEntity> creditRequestEntities);
}

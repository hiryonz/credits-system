package com.sistemaCreditos.demo.mapper;

import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.model.CreditRequestEntity;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CreditRequestMapper {

    CreditRequestEntity convertToEntity(CreditRequestDto creditRequestDto);

    CreditRequestDto convertToDto(CreditRequestEntity creditRequestEntity);

    List<CreditRequestDto> convertToDtoList(List<CreditRequestEntity> creditRequestEntities);
}

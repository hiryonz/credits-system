package com.sistemaCreditos.demo.mapper;

import com.sistemaCreditos.demo.dto.StatusDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;

public final class StatusMapper {

    private StatusMapper() {
    }

    public static StatusDto toStatusDto(GlobalStatusCodes statusCode) {
        return new StatusDto(statusCode.getCode(), statusCode.getDescription());
    }
}

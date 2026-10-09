package com.sistemaCreditos.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.mapper.StatusMapper;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(StatusDto status, T body) {

    public static <T> ApiResponse<T> of(GlobalStatusCodes statusCode, T body) {
        return new ApiResponse<>(StatusMapper.toStatusDto(statusCode), body);
    }

    public static ApiResponse<Void> of(GlobalStatusCodes statusCode) {
        return new ApiResponse<>(StatusMapper.toStatusDto(statusCode), null);
    }
}

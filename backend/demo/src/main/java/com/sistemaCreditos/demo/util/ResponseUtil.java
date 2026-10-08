package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.CreditDetailResponseDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.dto.CreditResponseDto;
import com.sistemaCreditos.demo.dto.StatusDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.mapper.StatusMapper;

import org.springframework.http.ResponseEntity;

import java.util.List;

public final class ResponseUtil {

    private ResponseUtil() {
    }

    public static ResponseEntity<StatusDto> build(GlobalStatusCodes statusCode) {
        return ResponseEntity
                .status(statusCode.getHttpStatus())
                .body(StatusMapper.toStatusDto(statusCode));
    }

    public static ResponseEntity<CreditResponseDto> build(GlobalStatusCodes statusCode,
                                                          List<CreditRequestDto> body) {
        return ResponseEntity
                .status(statusCode.getHttpStatus())
                .body(new CreditResponseDto(StatusMapper.toStatusDto(statusCode), body));
    }

    public static ResponseEntity<CreditDetailResponseDto> buildDetail(GlobalStatusCodes statusCode,
                                                                      CreditRequestDto body) {
        return ResponseEntity
                .status(statusCode.getHttpStatus())
                .body(new CreditDetailResponseDto(StatusMapper.toStatusDto(statusCode), body));
    }
}

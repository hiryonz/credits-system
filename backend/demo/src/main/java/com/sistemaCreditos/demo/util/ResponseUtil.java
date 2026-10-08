package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;

import org.springframework.http.ResponseEntity;


public final class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> ResponseEntity<ApiResponse<T>> build(GlobalStatusCodes statusCode, T body) {
        return ResponseEntity
                .status(statusCode.getHttpStatus())
                .body(ApiResponse.of(statusCode, body));
    }

    public static ResponseEntity<ApiResponse<Void>> build(GlobalStatusCodes statusCode) {
        return ResponseEntity
                .status(statusCode.getHttpStatus())
                .body(ApiResponse.of(statusCode));
    }
}

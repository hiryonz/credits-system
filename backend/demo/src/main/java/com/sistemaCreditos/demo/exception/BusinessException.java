package com.sistemaCreditos.demo.exception;

import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException {

    private final GlobalStatusCodes statusCode;

    public BusinessException(GlobalStatusCodes statusCode) {
        super(statusCode.getDescription());
        this.statusCode = statusCode;
    }
}

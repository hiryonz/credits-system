package com.sistemaCreditos.demo.exception;

import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException ex) {
        return ResponseUtil.build(ex.getStatusCode());
    }

    /** JSON mal formado o tipos inválidos en el body (ej. un id que no es UUID). */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnreadableBody(HttpMessageNotReadableException ex) {
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_INVALID_DATA);
    }

    /** Parámetro de la URL con tipo inválido (ej. GET /credit-requests/abc). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_INVALID_DATA);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {
        log.error("Error inesperado procesando la petición", ex);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_INTERNAL_ERROR);
    }
}

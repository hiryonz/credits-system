package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditRequestValidatorTest {

    @Test
    void shouldRejectCreateWithoutData() {
        CreditRequestDto request = new CreditRequestDto();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> CreditRequestValidator.validateForCreate(request));

        assertEquals(GlobalStatusCodes.CREDIT_INVALID_DATA, exception.getStatusCode());
    }

    @Test
    void shouldRequireCommentToChangeStatus() {
        CreditRequestDto request = new CreditRequestDto();
        request.setId(UUID.randomUUID());
        request.setStatus("APPROVED");
        request.setComment(" ");

        BusinessException exception = assertThrows(BusinessException.class,
                () -> CreditRequestValidator.validateForStatusChange(request));

        assertEquals(GlobalStatusCodes.CREDIT_INVALID_DATA, exception.getStatusCode());
    }

    @Test
    void shouldRejectUnknownFilterStatus() {
        CreditFilterRequestDto request = new CreditFilterRequestDto("CANCELLED");

        BusinessException exception = assertThrows(BusinessException.class,
                () -> CreditRequestValidator.validateFilter(request));

        assertEquals(GlobalStatusCodes.CREDIT_INVALID_STATUS, exception.getStatusCode());
    }

    @Test
    void shouldAcceptFilterStatusIgnoringCase() {
        CreditFilterRequestDto request = new CreditFilterRequestDto(" pending ");

        assertDoesNotThrow(() -> CreditRequestValidator.validateFilter(request));
    }
}

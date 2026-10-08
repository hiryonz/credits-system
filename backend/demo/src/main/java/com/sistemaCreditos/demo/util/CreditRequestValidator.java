package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.CreditStatus;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;

import java.util.Arrays;
import java.util.UUID;


public final class CreditRequestValidator {

    private CreditRequestValidator() {
    }

    public static void validateForCreate(CreditRequestDto request) {
        if (request == null
                || request.getAmount() == null
                || request.getTermMonths() == null
                || isBlank(request.getApplicantDocument())) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_INVALID_DATA);
        }
    }

    public static void validateForStatusChange(CreditRequestDto request) {
        if (request == null
                || request.getId() == null
                || isBlank(request.getStatus())
                || isBlank(request.getComment())) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_INVALID_DATA);
        }
    }


    public static void validateFilter(CreditFilterRequestDto request) {
        if (request == null) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_NOT_FOUND);
        }
        if (isBlank(request.getStatus())) {
            return;
        }
        String normalized = request.getStatus().trim().toUpperCase();
        boolean exists = Arrays.stream(CreditStatus.values())
                .anyMatch(s -> s.getStatus().equals(normalized));
        if (!exists) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_INVALID_STATUS);
        }
    }

    public static void validateId(UUID id) {
        if (id == null) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_NOT_FOUND);
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

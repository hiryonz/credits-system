package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.CreditStatus;

import java.util.Arrays;


public final class CreditRequestValidator {

    private CreditRequestValidator() {
    }

    public static boolean isValidForCreate(CreditRequestDto request) {
        return request != null
                && request.getAmount() != null
                && request.getTermMonths() != null
                && isNotBlank(request.getApplicantDocument());
    }

    public static boolean isValidForStatusChange(CreditRequestDto request) {
        return request != null
                && request.getId() != null
                && isNotBlank(request.getStatus())
                && isNotBlank(request.getComment());
    }


    public static boolean isValidFilter(CreditFilterRequestDto request) {
        if (request == null || !isNotBlank(request.getStatus())) {
            return true;
        }
        String normalized = request.getStatus().trim().toUpperCase();
        return Arrays.stream(CreditStatus.values())
                .anyMatch(s -> s.getStatus().equals(normalized));
    }

    private static boolean isNotBlank(String value) {
        return value != null && !value.isBlank();
    }
}

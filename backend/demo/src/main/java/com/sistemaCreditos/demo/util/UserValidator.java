package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;

public final class UserValidator {

    private UserValidator() {
    }

    public static void validateCredentials(UserRequestDto request) {
        if (request == null
                || isBlank(request.getUsername())
                || isBlank(request.getPassword())) {
            throw new BusinessException(GlobalStatusCodes.USER_INVALID_DATA);
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

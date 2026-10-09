package com.sistemaCreditos.demo.util;

import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;

public final class UserValidator {

    private static final String BEARER_PREFIX = "Bearer ";

    private UserValidator() {
    }

    public static void validateCredentials(UserRequestDto request) {
        if (request == null
                || isBlank(request.getUsername())
                || isBlank(request.getPassword())) {
            throw new BusinessException(GlobalStatusCodes.USER_INVALID_DATA);
        }
    }

    public static String extractBearerToken(String authorizationHeader) {
        if (isBlank(authorizationHeader) || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new BusinessException(GlobalStatusCodes.USER_INVALID_TOKEN);
        }
        return authorizationHeader.substring(BEARER_PREFIX.length()).trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

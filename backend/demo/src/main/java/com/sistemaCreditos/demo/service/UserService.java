package com.sistemaCreditos.demo.service;

import com.sistemaCreditos.demo.dto.UserResponseDto;
import com.sistemaCreditos.demo.dto.UserRequestDto;

public interface UserService {

    UserResponseDto register(UserRequestDto request);

    UserResponseDto login(UserRequestDto request);

    UserResponseDto refreshToken(String token);
}

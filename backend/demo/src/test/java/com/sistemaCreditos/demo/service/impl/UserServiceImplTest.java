package com.sistemaCreditos.demo.service.impl;

import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;
import com.sistemaCreditos.demo.mapper.UserMapper;
import com.sistemaCreditos.demo.model.UserEntity;
import com.sistemaCreditos.demo.repository.UserRepository;
import com.sistemaCreditos.demo.util.JwtUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void shouldRejectExistingUsername() {
        when(repository.existsByUsername("ana")).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.register(new UserRequestDto(" ana ", "Secreto#1")));

        assertEquals(GlobalStatusCodes.USER_ALREADY_EXISTS, exception.getStatusCode());
    }

    @Test
    void shouldRejectWrongPassword() {
        UserEntity user = new UserEntity();
        user.setPassword("hash");
        when(repository.findByUsername("ana")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("incorrecta", "hash")).thenReturn(false);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> service.login(new UserRequestDto("ana", "incorrecta")));

        assertEquals(GlobalStatusCodes.USER_INVALID_CREDENTIALS, exception.getStatusCode());
    }

    @Test
    void shouldRejectInvalidRefreshToken() {
        when(jwtUtil.extractUsername("token")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> service.refreshToken("token"));

        assertEquals(GlobalStatusCodes.USER_INVALID_TOKEN, exception.getStatusCode());
    }
}

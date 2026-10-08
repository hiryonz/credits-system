package com.sistemaCreditos.demo.service.impl;

import com.sistemaCreditos.demo.dto.UserResponseDto;
import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;
import com.sistemaCreditos.demo.mapper.UserMapper;
import com.sistemaCreditos.demo.model.UserEntity;
import com.sistemaCreditos.demo.repository.UserRepository;
import com.sistemaCreditos.demo.service.UserService;
import com.sistemaCreditos.demo.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper mapper;

    @Override
    public UserResponseDto register(UserRequestDto request) {
        request.setUsername(request.getUsername().trim());

        if (repository.existsByUsername(request.getUsername())) {
            throw new BusinessException(GlobalStatusCodes.USER_ALREADY_EXISTS);
        }

        UserEntity user = mapper.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return mapper.convertToDto(repository.save(user));
    }

    @Override
    public UserResponseDto login(UserRequestDto request) {
        String username = request.getUsername().trim();

        UserEntity user = repository.findByUsername(username)
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new BusinessException(GlobalStatusCodes.USER_INVALID_CREDENTIALS));

        return mapper.convertToDto(user, jwtUtil.generateToken(user.getUsername()));
    }

    @Override
    public UserResponseDto refreshToken(String token) {
        String username = jwtUtil.extractUsername(token)
                .orElseThrow(() -> new BusinessException(GlobalStatusCodes.USER_INVALID_TOKEN));

        UserEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(GlobalStatusCodes.USER_INVALID_TOKEN));

        return mapper.convertToDto(user, jwtUtil.generateToken(user.getUsername()));
    }
}

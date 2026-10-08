package com.sistemaCreditos.demo.controller;

import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.dto.UserResponseDto;
import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.service.UserService;
import com.sistemaCreditos.demo.util.ResponseUtil;
import com.sistemaCreditos.demo.util.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@RequestBody UserRequestDto request) {
        UserValidator.validateCredentials(request);
        return ResponseUtil.build(GlobalStatusCodes.USER_CREATED, userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody UserRequestDto request) {
        UserValidator.validateCredentials(request);
        return ResponseUtil.build(GlobalStatusCodes.USER_LOGIN_SUCCESS, userService.login(request));
    }
}

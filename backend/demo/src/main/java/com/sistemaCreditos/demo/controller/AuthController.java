package com.sistemaCreditos.demo.controller;

import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.dto.UserResponseDto;
import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.service.UserService;
import com.sistemaCreditos.demo.util.ResponseUtil;
import com.sistemaCreditos.demo.util.UserValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Registro e inicio de sesión de usuarios")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario con usuario y contraseña")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@RequestBody UserRequestDto request) {
        UserValidator.validateCredentials(request);
        return ResponseUtil.build(GlobalStatusCodes.USER_CREATED, userService.register(request));
    }

    @Operation(summary = "Iniciar sesión", description = "Valida las credenciales y devuelve un token JWT")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody UserRequestDto request) {
        UserValidator.validateCredentials(request);
        return ResponseUtil.build(GlobalStatusCodes.USER_LOGIN_SUCCESS, userService.login(request));
    }
}

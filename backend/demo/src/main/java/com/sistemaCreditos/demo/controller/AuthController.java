package com.sistemaCreditos.demo.controller;

import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.dto.UserResponseDto;
import com.sistemaCreditos.demo.dto.UserRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.service.UserService;
import com.sistemaCreditos.demo.util.ResponseUtil;
import com.sistemaCreditos.demo.util.UserValidator;

import com.sistemaCreditos.demo.config.OpenApiConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @Operation(summary = "Renovar token",
            description = "Valida el token JWT enviado en el header Authorization y, si sigue vigente, devuelve uno nuevo")
    @SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<UserResponseDto>> refreshToken(
            @Parameter(hidden = true) @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String token = UserValidator.extractBearerToken(authorization);
        return ResponseUtil.build(GlobalStatusCodes.USER_TOKEN_REFRESHED, userService.refreshToken(token));
    }
}

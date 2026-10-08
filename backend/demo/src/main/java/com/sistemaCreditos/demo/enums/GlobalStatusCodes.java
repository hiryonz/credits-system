package com.sistemaCreditos.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum GlobalStatusCodes {

    
    CREDIT_SUCCESS("TRX-000", "Operación exitosa", HttpStatus.OK),
    CREDIT_CREATED("TRX-001", "Solicitud de crédito creada correctamente", HttpStatus.CREATED),
    CREDIT_STATUS_UPDATED("TRX-002", "Estado de la solicitud actualizado correctamente", HttpStatus.OK),
    CREDIT_INVALID_AMOUNT("TRX-003", "El monto debe estar entre $500 y $50,000", HttpStatus.BAD_REQUEST),
    CREDIT_INVALID_TERM("TRX-004", "El plazo debe estar entre 6 y 60 meses", HttpStatus.BAD_REQUEST),
    CREDIT_INVALID_STATUS("TRX-005", "Estado de solicitud inválido", HttpStatus.BAD_REQUEST),
    CREDIT_NOT_FOUND("TRX-006", "Solicitud de crédito no encontrada", HttpStatus.NOT_FOUND),
    CREDIT_STATUS_ALREADY_PROCESSED("TRX-007", "La solicitud ya fue procesada y no puede cambiar de estado", HttpStatus.CONFLICT),
    CREDIT_INVALID_DATA("TRX-008", "Datos de la solicitud inválidos", HttpStatus.BAD_REQUEST),
    CREDIT_INTERNAL_ERROR("TRX-999", "Error interno procesando la solicitud", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_SUCCESS("ACCT-000", "Operación exitosa", HttpStatus.OK),
    USER_CREATED("ACCT-001", "Usuario creado correctamente", HttpStatus.CREATED),
    USER_LOGIN_SUCCESS("ACCT-002", "Inicio de sesión exitoso", HttpStatus.OK),
    USER_INVALID_CREDENTIALS("ACCT-003", "Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXISTS("ACCT-004", "El usuario ya existe", HttpStatus.CONFLICT),
    USER_NOT_FOUND("ACCT-005", "Usuario no encontrado", HttpStatus.NOT_FOUND),
    USER_INVALID_TOKEN("ACCT-006", "Token inválido o expirado", HttpStatus.UNAUTHORIZED),
    USER_INVALID_DATA("ACCT-007", "Datos del usuario inválidos", HttpStatus.BAD_REQUEST),
    USER_INTERNAL_ERROR("ACCT-999", "Error interno procesando la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String description;
    private final HttpStatus httpStatus;
}

package com.sistemaCreditos.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum GlobalStatusCodes {

    CREDIT_SUCCESS("TRX-000", "Operación exitosa"),
    CREDIT_CREATED("TRX-001", "Solicitud de crédito creada correctamente"),
    CREDIT_STATUS_UPDATED("TRX-002", "Estado de la solicitud actualizado correctamente"),
    CREDIT_INVALID_AMOUNT("TRX-003", "El monto debe estar entre $500 y $50,000"),
    CREDIT_INVALID_TERM("TRX-004", "El plazo debe estar entre 6 y 60 meses"),
    CREDIT_INVALID_STATUS("TRX-005", "Estado de solicitud inválido"),
    CREDIT_NOT_FOUND("TRX-006", "Solicitud de crédito no encontrada"),
    CREDIT_STATUS_ALREADY_PROCESSED("TRX-007", "La solicitud ya fue procesada y no puede cambiar de estado"),
    CREDIT_INVALID_DATA("TRX-008", "Datos de la solicitud inválidos"),
    CREDIT_INTERNAL_ERROR("TRX-999", "Error interno procesando la solicitud"),

    USER_SUCCESS("ACCT-000", "Operación exitosa"),
    USER_CREATED("ACCT-001", "Usuario creado correctamente"),
    USER_LOGIN_SUCCESS("ACCT-002", "Inicio de sesión exitoso"),
    USER_INVALID_CREDENTIALS("ACCT-003", "Usuario o contraseña incorrectos"),
    USER_ALREADY_EXISTS("ACCT-004", "El usuario ya existe"),
    USER_NOT_FOUND("ACCT-005", "Usuario no encontrado"),
    USER_INVALID_TOKEN("ACCT-006", "Token inválido o expirado"),
    USER_INVALID_DATA("ACCT-007", "Datos del usuario inválidos"),
    USER_INTERNAL_ERROR("ACCT-999", "Error interno procesando la solicitud");

    private final String code;
    private final String description;
}

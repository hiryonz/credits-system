package com.sistemaCreditos.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDto {

    @Schema(description = "Id de la solicitud (solo para cambio de estado)", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Monto solicitado, entre 500 y 50000", example = "15000.50")
    private BigDecimal amount;

    @Schema(description = "Plazo en meses, entre 6 y 60", example = "24")
    private Integer termMonths;

    @Schema(description = "Documento del solicitante", example = "8-888-8888")
    private String applicantDocument;

    @Schema(description = "Estado: PENDING, APPROVED o REJECTED", example = "APPROVED")
    private String status;

    @Schema(description = "Comentario al aprobar o rechazar", example = "Cumple con los requisitos")
    private String comment;

    @Schema(description = "Fecha de creación de la solicitud", example = "2026-10-08T10:15:30",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Fecha de la última actualización de la solicitud", example = "2026-10-08T12:40:00",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

}

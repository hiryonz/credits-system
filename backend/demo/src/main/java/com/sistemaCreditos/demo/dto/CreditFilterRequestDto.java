package com.sistemaCreditos.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditFilterRequestDto {

    @Schema(description = "Filtro opcional por estado: PENDING, APPROVED o REJECTED", example = "PENDING")
    private String status;

}

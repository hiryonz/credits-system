package com.sistemaCreditos.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditResponseDto {

    private StatusDto status;

    private List<CreditRequestDto> body;

}

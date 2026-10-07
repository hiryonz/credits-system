package com.sistemaCreditos.demo.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDto {

    private String id;

    private BigDecimal amount;

    private Integer termMonths;

    private String applicantDocument;

    private String status;

    private String comment;

}

package com.sistemaCreditos.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDto {

    private UUID id;

    private Integer amount;

    private Integer termMonths;

    private String applicantDocument;

    private String status;

    private String comment;

}

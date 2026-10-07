package com.sistemaCreditos.demo.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

package com.sistemaCreditos.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequestDto {

    private String id;

    private Integer amount;

    private Integer termMonths;

    private String applicantDocument;

    private String status;

    private String comment;

}

package com.sistemaCreditos.demo.service;

import com.sistemaCreditos.demo.dto.CreditDetailResponseDto;
import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.dto.CreditResponseDto;
import com.sistemaCreditos.demo.dto.StatusDto;

import org.springframework.http.ResponseEntity;

public interface CreditRequestService {

    ResponseEntity<CreditDetailResponseDto> create(CreditRequestDto request);

    ResponseEntity<CreditResponseDto> findAll(CreditFilterRequestDto request);

    ResponseEntity<StatusDto> changeStatus(CreditRequestDto request);
}

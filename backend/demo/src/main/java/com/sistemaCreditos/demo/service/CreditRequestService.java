package com.sistemaCreditos.demo.service;

import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;

import java.util.List;
import java.util.UUID;

public interface CreditRequestService {

    CreditRequestDto create(CreditRequestDto request);

    List<CreditRequestDto> findAll(CreditFilterRequestDto request);

    CreditRequestDto findById(UUID id);

    CreditRequestDto changeStatus(CreditRequestDto request);
}

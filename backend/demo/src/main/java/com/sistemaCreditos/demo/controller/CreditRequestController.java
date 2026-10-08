package com.sistemaCreditos.demo.controller;

import com.sistemaCreditos.demo.dto.CreditDetailResponseDto;
import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.dto.CreditResponseDto;
import com.sistemaCreditos.demo.dto.StatusDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.service.CreditRequestService;
import com.sistemaCreditos.demo.util.CreditRequestValidator;
import com.sistemaCreditos.demo.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/credit-requests")
public class CreditRequestController {

    @Autowired
    private CreditRequestService creditRequestService;

    @PostMapping("/create-credits")
    public ResponseEntity<CreditDetailResponseDto> create(@RequestBody CreditRequestDto request) {
        if (!CreditRequestValidator.isValidForCreate(request)) {
            return ResponseUtil.buildDetail(GlobalStatusCodes.CREDIT_INVALID_DATA, null);
        }
        return creditRequestService.create(request);
    }

    @PostMapping("/get-credits")
    public ResponseEntity<CreditResponseDto> findAll(@RequestBody(required = false) CreditFilterRequestDto request) {
        if (!CreditRequestValidator.isValidFilter(request)) {
            return ResponseUtil.build(GlobalStatusCodes.CREDIT_INVALID_STATUS, null);
        }
        return creditRequestService.findAll(request);
    }

    @PostMapping("/update-credit-status")
    public ResponseEntity<StatusDto> changeStatus(@RequestBody CreditRequestDto request) {
        if (!CreditRequestValidator.isValidForStatusChange(request)) {
            return ResponseUtil.build(GlobalStatusCodes.CREDIT_INVALID_DATA);
        }
        return creditRequestService.changeStatus(request);
    }
}

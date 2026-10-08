package com.sistemaCreditos.demo.controller;

import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.service.CreditRequestService;
import com.sistemaCreditos.demo.util.CreditRequestValidator;
import com.sistemaCreditos.demo.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/credit-requests")
public class CreditRequestController {

    @Autowired
    private CreditRequestService creditRequestService;

    @PostMapping("/create-credits")
    public ResponseEntity<ApiResponse<CreditRequestDto>> create(@RequestBody CreditRequestDto request) {
        CreditRequestValidator.validateForCreate(request);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_CREATED, creditRequestService.create(request));
    }

    @PostMapping("/get-credits")
    public ResponseEntity<ApiResponse<List<CreditRequestDto>>> findAll(
            @RequestBody(required = false) CreditFilterRequestDto request) {
        CreditRequestValidator.validateFilter(request);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_SUCCESS, creditRequestService.findAll(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreditRequestDto>> findById(@PathVariable UUID id) {
        CreditRequestValidator.validateId(id);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_SUCCESS, creditRequestService.findById(id));
    }

    @PostMapping("/update-credit-status")
    public ResponseEntity<ApiResponse<CreditRequestDto>> changeStatus(@RequestBody CreditRequestDto request) {
        CreditRequestValidator.validateForStatusChange(request);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_STATUS_UPDATED, creditRequestService.changeStatus(request));
    }
}

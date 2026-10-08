package com.sistemaCreditos.demo.controller;

import com.sistemaCreditos.demo.config.OpenApiConfig;
import com.sistemaCreditos.demo.dto.ApiResponse;
import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.service.CreditRequestService;
import com.sistemaCreditos.demo.util.CreditRequestValidator;
import com.sistemaCreditos.demo.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Solicitudes de crédito", description = "Creación, consulta y cambio de estado de solicitudes de crédito")
@SecurityRequirement(name = OpenApiConfig.BEARER_AUTH)
public class CreditRequestController {

    @Autowired
    private CreditRequestService creditRequestService;

    @Operation(summary = "Crear solicitud",
            description = "Crea una solicitud en estado PENDING. Monto entre 500 y 50000, plazo entre 6 y 60 meses")
    @PostMapping("/create-credits")
    public ResponseEntity<ApiResponse<CreditRequestDto>> create(@RequestBody CreditRequestDto request) {
        CreditRequestValidator.validateForCreate(request);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_CREATED, creditRequestService.create(request));
    }

    @Operation(summary = "Listar solicitudes",
            description = "Devuelve las solicitudes ordenadas por fecha de creación. Se puede filtrar por estado")
    @PostMapping("/get-credits")
    public ResponseEntity<ApiResponse<List<CreditRequestDto>>> findAll(
            @RequestBody(required = false) CreditFilterRequestDto request) {
        CreditRequestValidator.validateFilter(request);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_SUCCESS, creditRequestService.findAll(request));
    }

    @Operation(summary = "Obtener solicitud por id")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreditRequestDto>> findById(
            @Parameter(description = "Id de la solicitud") @PathVariable UUID id) {
        CreditRequestValidator.validateId(id);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_SUCCESS, creditRequestService.findById(id));
    }

    @Operation(summary = "Cambiar estado",
            description = "Aprueba o rechaza una solicitud en estado PENDING. Requiere id, status (APPROVED o REJECTED) y comment")
    @PostMapping("/update-credit-status")
    public ResponseEntity<ApiResponse<CreditRequestDto>> changeStatus(@RequestBody CreditRequestDto request) {
        CreditRequestValidator.validateForStatusChange(request);
        return ResponseUtil.build(GlobalStatusCodes.CREDIT_STATUS_UPDATED, creditRequestService.changeStatus(request));
    }
}

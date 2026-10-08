package com.sistemaCreditos.demo.service.impl;

import com.sistemaCreditos.demo.dto.CreditDetailResponseDto;
import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.dto.CreditResponseDto;
import com.sistemaCreditos.demo.dto.StatusDto;
import com.sistemaCreditos.demo.enums.CreditStatus;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;

import com.sistemaCreditos.demo.mapper.CreditRequestMapper;
import com.sistemaCreditos.demo.model.CreditRequestEntity;
import com.sistemaCreditos.demo.repository.CreditRequestRepository;
import com.sistemaCreditos.demo.service.CreditRequestService;
import com.sistemaCreditos.demo.util.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreditRequestServiceImpl implements CreditRequestService {

    private static final Integer MIN_AMOUNT = 500;
    private static final Integer MAX_AMOUNT = 50000;
    private static final Integer MIN_TERM_MONTHS = 6;
    private static final Integer MAX_TERM_MONTHS = 60;

    @Autowired
    private CreditRequestRepository repository;

    @Autowired
    private CreditRequestMapper mapper;

    @Override
    public ResponseEntity<CreditDetailResponseDto> create(CreditRequestDto request) {
        try {
            final var amount = request.getAmount();
            final var terms = request.getTermMonths();

            if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
                return ResponseUtil.buildDetail(GlobalStatusCodes.CREDIT_INVALID_AMOUNT, null);
            }

            if (terms < MIN_TERM_MONTHS || terms > MAX_TERM_MONTHS) {
                return ResponseUtil.buildDetail(GlobalStatusCodes.CREDIT_INVALID_TERM, null);
            }

            CreditRequestEntity entity = mapper.convertToEntity(request);

            entity.setStatus(CreditStatus.PENDING.getStatus());

            CreditRequestEntity saved = repository.save(entity);

            return ResponseUtil.buildDetail(GlobalStatusCodes.CREDIT_CREATED, mapper.convertToDto(saved));

        } catch (Exception e) {
            System.out.println(e);
            return ResponseUtil.buildDetail(GlobalStatusCodes.CREDIT_INTERNAL_ERROR, null);
        }

    }

    @Override
    public ResponseEntity<CreditResponseDto> findAll(CreditFilterRequestDto request) {
        try {
            String status = request.getStatus();

            List<CreditRequestEntity> requests = (status == null || status.isBlank())
                    ? repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                    : repository.findCreditByStatus(status.trim().toUpperCase());

            return ResponseUtil.build(GlobalStatusCodes.CREDIT_SUCCESS, mapper.convertToDtoList(requests));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseUtil.build(GlobalStatusCodes.CREDIT_INTERNAL_ERROR, List.of());
        }
    }

    @Override
    public ResponseEntity<StatusDto> changeStatus(CreditRequestDto request) {
        try {
            String normalized = request.getStatus().trim().toUpperCase();

            if (isInvalidStatus(normalized) || CreditStatus.PENDING.getStatus().equals(normalized)) {
                return ResponseUtil.build(GlobalStatusCodes.CREDIT_INVALID_STATUS);
            }

            Optional<CreditRequestEntity> entity = repository.findById(request.getId());

            if(entity.isEmpty()) {
                return ResponseUtil.build(GlobalStatusCodes.CREDIT_NOT_FOUND);
            }

            if(!CreditStatus.PENDING.getStatus().equals(entity.get().getStatus())) {
                return ResponseUtil.build(GlobalStatusCodes.CREDIT_STATUS_ALREADY_PROCESSED);
            }

            entity.get().setStatus(normalized);
            entity.get().setComment(request.getComment());
            repository.save(entity.get());
            return ResponseUtil.build(GlobalStatusCodes.CREDIT_STATUS_UPDATED);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseUtil.build(GlobalStatusCodes.CREDIT_INTERNAL_ERROR);
        }
    }

    private boolean isInvalidStatus(String status) {
        return Arrays.stream(CreditStatus.values())
                .noneMatch(s -> s.getStatus().equals(status));
    }
}

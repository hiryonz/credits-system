package com.sistemaCreditos.demo.service.impl;

import com.sistemaCreditos.demo.dto.CreditFilterRequestDto;
import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.CreditStatus;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;
import com.sistemaCreditos.demo.mapper.CreditRequestMapper;
import com.sistemaCreditos.demo.model.CreditRequestEntity;
import com.sistemaCreditos.demo.repository.CreditRequestRepository;
import com.sistemaCreditos.demo.service.CreditRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public CreditRequestDto create(CreditRequestDto request) {
        final var amount = request.getAmount();
        final var terms = request.getTermMonths();

        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_INVALID_AMOUNT);
        }

        if (terms < MIN_TERM_MONTHS || terms > MAX_TERM_MONTHS) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_INVALID_TERM);
        }

        CreditRequestEntity entity = mapper.convertToEntity(request);
        entity.setStatus(CreditStatus.PENDING.getStatus());

        return mapper.convertToDto(repository.save(entity));
    }

    @Override
    public List<CreditRequestDto> findAll(CreditFilterRequestDto request) {
        String status = request.getStatus();

        List<CreditRequestEntity> requests = (status == null || status.isBlank())
                ? repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                : repository.findCreditByStatus(status.trim().toUpperCase());

        if (requests.isEmpty()) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_NOT_FOUND);
        }

        return mapper.convertToDtoList(requests);
    }

    @Override
    public CreditRequestDto findById(UUID id) {
        return mapper.convertToDto(getExisting(id));
    }

    @Override
    public CreditRequestDto changeStatus(CreditRequestDto request) {
        String newStatus = request.getStatus().trim().toUpperCase();

        boolean isFinalStatus = CreditStatus.APPROVED.getStatus().equals(newStatus)
                || CreditStatus.REJECTED.getStatus().equals(newStatus);
        if (!isFinalStatus) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_INVALID_STATUS);
        }

        CreditRequestEntity entity = getExisting(request.getId());

        if (!CreditStatus.PENDING.getStatus().equals(entity.getStatus())) {
            throw new BusinessException(GlobalStatusCodes.CREDIT_STATUS_ALREADY_PROCESSED);
        }

        entity.setStatus(newStatus);
        entity.setComment(request.getComment());

        return mapper.convertToDto(repository.save(entity));
    }

    private CreditRequestEntity getExisting(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(GlobalStatusCodes.CREDIT_NOT_FOUND));
    }
}

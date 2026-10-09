package com.sistemaCreditos.demo.service.impl;

import com.sistemaCreditos.demo.dto.CreditRequestDto;
import com.sistemaCreditos.demo.enums.CreditStatus;
import com.sistemaCreditos.demo.enums.GlobalStatusCodes;
import com.sistemaCreditos.demo.exception.BusinessException;
import com.sistemaCreditos.demo.mapper.CreditRequestMapper;
import com.sistemaCreditos.demo.model.CreditRequestEntity;
import com.sistemaCreditos.demo.repository.CreditRequestRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditRequestServiceImplTest {

    @Mock
    private CreditRequestRepository repository;

    @Mock
    private CreditRequestMapper mapper;

    @InjectMocks
    private CreditRequestServiceImpl service;

    @Test
    void shouldRejectAmountOutOfRange() {
        CreditRequestDto request = newRequest(new BigDecimal("100"), 12);

        BusinessException exception = assertThrows(BusinessException.class, () -> service.create(request));

        assertEquals(GlobalStatusCodes.CREDIT_INVALID_AMOUNT, exception.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void shouldRejectTermOutOfRange() {
        CreditRequestDto request = newRequest(new BigDecimal("1000"), 72);

        BusinessException exception = assertThrows(BusinessException.class, () -> service.create(request));

        assertEquals(GlobalStatusCodes.CREDIT_INVALID_TERM, exception.getStatusCode());
    }

    @Test
    void shouldCreateCreditAsPending() {
        CreditRequestDto request = newRequest(new BigDecimal("1000"), 12);
        CreditRequestEntity entity = new CreditRequestEntity();
        when(mapper.convertToEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);

        service.create(request);

        assertEquals(CreditStatus.PENDING.getStatus(), entity.getStatus());
    }

    @Test
    void shouldNotChangeStatusOfProcessedCredit() {
        UUID id = UUID.randomUUID();
        CreditRequestEntity entity = new CreditRequestEntity();
        entity.setStatus(CreditStatus.APPROVED.getStatus());
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        CreditRequestDto request = new CreditRequestDto();
        request.setId(id);
        request.setStatus("REJECTED");
        request.setComment("Sin fondos");

        BusinessException exception = assertThrows(BusinessException.class, () -> service.changeStatus(request));

        assertEquals(GlobalStatusCodes.CREDIT_STATUS_ALREADY_PROCESSED, exception.getStatusCode());
    }

    private CreditRequestDto newRequest(BigDecimal amount, Integer termMonths) {
        CreditRequestDto request = new CreditRequestDto();
        request.setAmount(amount);
        request.setTermMonths(termMonths);
        request.setApplicantDocument("8-888-8888");
        return request;
    }
}

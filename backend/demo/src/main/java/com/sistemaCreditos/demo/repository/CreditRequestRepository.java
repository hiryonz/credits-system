package com.sistemaCreditos.demo.repository;

import com.sistemaCreditos.demo.model.CreditRequestEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditRequestRepository extends JpaRepository<CreditRequestEntity, UUID> {

    List<CreditRequestEntity> findCreditByStatus(String status);

}

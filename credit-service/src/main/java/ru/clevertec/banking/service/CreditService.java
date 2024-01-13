package ru.clevertec.banking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.banking.dto.CreditRequest;
import ru.clevertec.banking.dto.CreditRequestForUpdate;
import ru.clevertec.banking.dto.CreditResponse;

import java.util.UUID;

public interface CreditService {
    CreditResponse create(CreditRequest request);

    Page<CreditResponse> findByCustomer(UUID customerId, Pageable pageable);

    CreditResponse findByContractNumber(String contractNumber);

    Page<CreditResponse> getAll(Pageable pageable);

    CreditResponse update(CreditRequestForUpdate request);

    void delete(String contractNumber);
}

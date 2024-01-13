package ru.clevertec.banking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.banking.advice.exception.ResourceNotFoundException;
import ru.clevertec.banking.dto.CreditRequest;
import ru.clevertec.banking.dto.CreditRequestForUpdate;
import ru.clevertec.banking.dto.CreditResponse;
import ru.clevertec.banking.exception.CreditOperationException;
import ru.clevertec.banking.mapper.CreditMapper;
import ru.clevertec.banking.repository.CreditRepository;
import ru.clevertec.banking.service.CreditService;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final CreditRepository repository;
    private final CreditMapper mapper;

    @Transactional
    @Override
    public CreditResponse create(CreditRequest request) {
        return Optional.of(request)
                .map(mapper::fromRequest)
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new CreditOperationException("Failed to create credit"));
    }

    @Override
    public Page<CreditResponse> findByCustomer(UUID customerId, Pageable pageable) {
        return repository.findCreditsByCustomerId(customerId, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public CreditResponse findByContractNumber(String contractNumber) {
        return Optional.of(contractNumber)
                .map(repository::findCreditByContractNumber)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Credit with contractNumber: %s not found"
                        .formatted(contractNumber)));
    }

    @Override
    public Page<CreditResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    @Override
    public CreditResponse update(CreditRequestForUpdate request) {
        return Optional.of(request)
                .map(CreditRequestForUpdate::contractNumber)
                .map(this::findByContractNumber)
                .map(mapper::fromResponse)
                .map(credit -> mapper.updateFromRequest(request, credit))
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new CreditOperationException("Failed to update credit with contractNumber: %s"
                        .formatted(request.contractNumber())));
    }

    @Transactional
    @Override
    public void delete(String contractNumber) {
        repository.deleteByContractNumber(contractNumber);
    }
}

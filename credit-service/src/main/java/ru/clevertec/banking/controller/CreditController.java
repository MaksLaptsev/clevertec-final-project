package ru.clevertec.banking.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.CreditRequest;
import ru.clevertec.banking.dto.CreditRequestForUpdate;
import ru.clevertec.banking.dto.CreditResponse;
import ru.clevertec.banking.service.CreditService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/credits")
public class CreditController {
    private final CreditService service;

    @GetMapping
    public Page<CreditResponse> getAll(@PageableDefault(sort = {"contractNumber"}) Pageable pageable) {
        return service.getAll(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreditResponse create(@RequestBody @Valid CreditRequest request) {
        return service.save(request);
    }

    @PatchMapping
    public CreditResponse update(@RequestBody @Valid CreditRequestForUpdate request){
        return service.update(request);
    }

    @DeleteMapping("/{contractNumber}")
    public void deleteByContractNumber(@PathVariable String contractNumber){
        service.delete(contractNumber);
    }

    @GetMapping("/by-contract-number/{contractNumber}")
    public CreditResponse getByContractNumber(@PathVariable String contractNumber) {
        return service.findByContractNumber(contractNumber);
    }

    @GetMapping("/by-customer-id/{customerId}")
    public Page<CreditResponse> getByCustomerId(@PathVariable UUID customerId,
                                                @PageableDefault(sort = {"contractNumber"}) Pageable pageable) {
        return service.findByCustomer(customerId, pageable);
    }
}

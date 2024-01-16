package ru.clevertec.banking.controller;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.CreditRequest;
import ru.clevertec.banking.dto.CreditRequestForUpdate;
import ru.clevertec.banking.dto.CreditResponse;
import ru.clevertec.banking.service.CreditService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/credits")
public class CreditController {
    private final CreditService service;
    private final ApplicationContext context;

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

    //@PreAuthorize("hasRole('ROLE_ADMIN') or authentication.principal == '3'")
    @GetMapping("/by-customer-id/{customerId}")
    public List<CreditResponse> getByCustomerId(@PathVariable UUID customerId) {
        return service.findByCustomer(customerId);
    }
}

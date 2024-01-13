package ru.clevertec.banking.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.account.AccountRequest;
import ru.clevertec.banking.dto.account.AccountRequestForUpdate;
import ru.clevertec.banking.dto.account.AccountResponse;
import ru.clevertec.banking.dto.account.AccountWithCardResponse;
import ru.clevertec.banking.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public AccountResponse create(@RequestBody @Valid AccountRequest request) {
        log.info(request.toString());
        return service.save(request);
    }

    @GetMapping
    public Page<AccountWithCardResponse> getAll(@PageableDefault(sort = {"iban"}) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/by-customer-id/{uuid}")
    public Page<AccountWithCardResponse> findByCustomer(@PathVariable String uuid,@PageableDefault(sort = {"iban"}) Pageable pageable) {
        return service.findByCustomer(uuid,pageable);
    }

    @GetMapping("/by-iban/{iban}")
    public AccountResponse findByIban(@PathVariable String iban) {
        return service.findByIban(iban);
    }

    @PatchMapping
    public AccountResponse update(@RequestBody @Valid AccountRequestForUpdate request) {
        return service.update(request);
    }

    @DeleteMapping({"/{iban}"})
    public void delete(@PathVariable String iban) {
        service.deleteByIban(iban);
    }

}

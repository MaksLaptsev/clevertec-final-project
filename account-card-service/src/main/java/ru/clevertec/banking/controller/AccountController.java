package ru.clevertec.banking.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.account.AccountRequest;
import ru.clevertec.banking.dto.account.AccountResponse;
import ru.clevertec.banking.dto.account.AccountWithCardResponse;
import ru.clevertec.banking.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/test")
    public AccountResponse create(@RequestBody @Valid AccountRequest request){
        log.info(request.toString());
        return service.save(request);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/getAll")
    public List<AccountWithCardResponse> getAll(@PageableDefault(sort = {"id"}) Pageable pageable){
        return service.getAll(pageable);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("customer_id/{uuid}")
    public List<AccountResponse> getByCustomer(@PathVariable String uuid){
        return service.findByCustomer(uuid);
    }

}

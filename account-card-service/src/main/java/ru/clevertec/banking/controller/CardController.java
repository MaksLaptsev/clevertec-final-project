package ru.clevertec.banking.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.card.CardCurrencyResponse;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.service.CardService;

@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
@Slf4j
public class CardController {
    private final CardService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public CardResponse create(@RequestBody @Valid CardRequest request) {
        return service.save(request);
    }

    @PatchMapping
    public CardResponse update(@RequestBody @Valid CardRequestForUpdate request) {
        return service.update(request);
    }

    @GetMapping
    public Page<CardResponse> getAll(@PageableDefault(sort = {"iban"}) Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/by-customer-id/{uuid}")
    public Page<CardResponse> findByCustomer(@PathVariable String uuid, @PageableDefault(sort = {"iban"}) Pageable pageable) {
        return service.findByCustomer(uuid, pageable);
    }

    @GetMapping("/by-iban/{iban}")
    public Page<CardResponse> findByIban(@PathVariable String iban, @PageableDefault(sort = {"iban"}) Pageable pageable) {
        return service.findByIban(iban, pageable);
    }

    @GetMapping("/by-card-number/{cardNumber}")
    public CardCurrencyResponse findByCardNumber(@PathVariable String cardNumber) {
        return service.findByCardNumber(cardNumber);
    }

    @DeleteMapping("/{cardNumber}")
    public void delete(@PathVariable String cardNumber) {
        service.deleteByCardNumber(cardNumber);
    }
}

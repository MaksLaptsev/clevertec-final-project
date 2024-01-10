package ru.clevertec.banking.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.service.CardService;

@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor
@Slf4j
public class CardController {
    private final CardService service;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/test")
    public CardResponse create(@RequestBody @Valid CardRequest request){
        log.info(request.toString());
        return service.save(request);
    }
}

package ru.clevertec.banking.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.banking.advice.exception.ResourceNotFoundException;
import ru.clevertec.banking.dto.card.CardCurrencyResponse;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.entity.Card;
import ru.clevertec.banking.exception.ResourceCreateException;
import ru.clevertec.banking.mapper.CardMapper;
import ru.clevertec.banking.repository.CardRepository;
import ru.clevertec.banking.repository.specifications.FilterSpecifications;
import ru.clevertec.banking.service.CardService;
import ru.clevertec.banking.util.CardBalanceUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {
    private final CardRepository repository;
    private final CardMapper mapper;
    private final CardBalanceUtils balanceUtils;
    private final FilterSpecifications<Card> specifications;

    @Override
    public Page<CardResponse> findByCustomer(String uuid, Pageable pageable) {
        return repository.findAll(specifications.filter(uuid), pageable)
                .map(mapper::toResponse);
    }

    @Override
    public Page<CardResponse> findByIban(String iban, Pageable pageable) {
        return repository.findAll(specifications.filter(null, iban), pageable)
                .map(mapper::toResponse);
    }

    @Override
    public CardCurrencyResponse findByCardNumber(String cardNumber) {
        return Optional.ofNullable(repository.findCardByCardNumber(cardNumber))
                .map(card -> {
                    try {
                        return mapper.toCardWithBalance(card, balanceUtils.getBalance(card));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException("Card with card_number: %s not found".formatted(cardNumber)));
    }

    @Override
    public Page<CardResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional
    public CardResponse save(CardRequest request) {
        return Optional.of(request)
                .map(mapper::fromRequest)
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceCreateException("Failed to create card"));
    }

    @Override
    @Transactional
    public CardResponse update(CardRequestForUpdate request) {
        return Optional.of(request)
                .map(CardRequestForUpdate::card_number)
                .map(repository::findCardByCardNumber)
                .map(card -> mapper.updateFromRequest(request, card))
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Card with card_number: %s not found".
                        formatted(request.card_number())));
    }

    @Override
    @Transactional
    public void deleteByCardNumber(String cardNumber) {
        repository.deleteCardByCardNumber(cardNumber);
    }
}

package ru.clevertec.banking.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.banking.dto.card.CardCurrencyResponse;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.entity.Card;
import ru.clevertec.banking.mapper.CardMapper;
import ru.clevertec.banking.repository.CardRepository;
import ru.clevertec.banking.repository.specifications.FilterSpecifications;
import ru.clevertec.banking.service.CardService;
import ru.clevertec.banking.util.CardBalanceUtils;

import java.util.List;
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
    public CardResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("card not found id: " + id));
    }

    @Override
    public List<CardResponse> findByCustomer(String uuid) {
        return mapper.toListResponse(repository.findAll(specifications.filter(uuid)));
    }

    @Override
    public List<CardResponse> findByIban(String iban) {
        return mapper.toListResponse(repository.findAll(specifications.filter(null, iban)));
    }

    @Override
    public CardCurrencyResponse findByCardNumber(String cardNumber) {
        return Optional.of(repository.findCardByCardNumber(cardNumber))
                .map(car -> {
                    try {
                        return mapper.toCardWithBalance(car, balanceUtils.getBalance(car));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("card not found card_number: " + cardNumber));
    }

    @Override
    public List<CardResponse> findAll(Pageable pageable) {
        return Optional.of(repository.findAll(pageable).getContent())
                .map(mapper::toListResponse)
                .orElseThrow(() -> new RuntimeException("cards not found"));
    }

    @Override
    @Transactional
    public CardResponse save(CardRequest request) {
        return Optional.of(request)
                .map(mapper::fromRequest)
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("card not save"));
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
                .orElseThrow(() -> new RuntimeException("card not update"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByCardNumber(String cardNumber) {
        repository.deleteCardByCardNumber(cardNumber);
    }
}

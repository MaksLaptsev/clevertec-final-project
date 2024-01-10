package ru.clevertec.banking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.entity.Account;
import ru.clevertec.banking.entity.Card;
import ru.clevertec.banking.mapper.CardMapper;
import ru.clevertec.banking.repository.CardRepository;
import ru.clevertec.banking.repository.specifications.FilterSpecifications;
import ru.clevertec.banking.service.CardService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository repository;
    private final CardMapper mapper;

    private final FilterSpecifications<Card> specifications;

    @Override
    public CardResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("user not found id: " + id));
    }

    @Override
    public List<CardResponse> findByCustomer(String uuid) {
        return mapper.toListResponse(repository.findAll(specifications.filter(uuid)));
    }

    @Override
    public List<CardResponse> findAll(Pageable pageable) {
        return Optional.of(repository.findAll(pageable).getContent())
                .map(mapper::toListResponse)
                .orElseThrow(() -> new RuntimeException("cards not found"));
    }

    @Override
    public CardResponse save(CardRequest request) {
        return Optional.of(request)
                .map(mapper::fromRequest)
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("card not save"));
    }

    @Override
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
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

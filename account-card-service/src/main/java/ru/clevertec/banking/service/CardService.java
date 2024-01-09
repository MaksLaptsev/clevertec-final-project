package ru.clevertec.banking.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.banking.dto.card.CardRequest;
import ru.clevertec.banking.dto.card.CardRequestForUpdate;
import ru.clevertec.banking.dto.card.CardResponse;

import java.util.List;

public interface CardService {
    CardResponse findById(Long id);
    List<CardResponse> findAll(Pageable pageable);
    CardResponse save (CardRequest request);
    CardResponse update (CardRequestForUpdate request);
    void deleteById (Long id);
}

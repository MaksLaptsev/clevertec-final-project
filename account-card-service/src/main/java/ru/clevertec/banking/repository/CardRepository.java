package ru.clevertec.banking.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.clevertec.banking.entity.Card;

import java.util.Optional;

public interface CardRepository extends PagingAndSortingRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Optional<Card> findById(Long id);

    Card save(Card card);

    Card findCardByCardNumber(String cardNumber);

    void deleteById(Long id);
}

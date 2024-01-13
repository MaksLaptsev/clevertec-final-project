package ru.clevertec.banking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.clevertec.banking.entity.Credit;

import java.util.UUID;

public interface CreditRepository extends PagingAndSortingRepository<Credit, String> {
    Page<Credit> findCreditsByCustomerId(UUID customerId, Pageable pageable);

    Credit findCreditByContractNumber(String contractNumber);

    Credit save(Credit credit);

    void deleteByContractNumber(String contractNumber);

    boolean existsCreditByContractNumber(String contractNumber);

    boolean existsCreditByIban (String iban);
}

package ru.clevertec.banking.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.clevertec.banking.entity.Account;

import java.util.Optional;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findById(Long id);
}

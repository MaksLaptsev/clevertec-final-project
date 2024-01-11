package ru.clevertec.banking.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.clevertec.banking.entity.Account;

import java.util.Optional;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findById(Long id);

    Account save(Account account);

    Account findAccountByIban(String iban);

    void deleteById(Long id);

    void deleteAccountByIban(String iban);

    boolean existsAccountByIban(String iban);
}

package ru.clevertec.banking.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.banking.dto.account.AccountRequest;
import ru.clevertec.banking.dto.account.AccountRequestForUpdate;
import ru.clevertec.banking.dto.account.AccountResponse;
import ru.clevertec.banking.dto.account.AccountWithCardResponse;

import java.util.List;

public interface AccountService {
    AccountResponse save(AccountRequest request);

    List<AccountWithCardResponse> getAll(Pageable pageable);

    List<AccountResponse> findByCustomer(String uuid);

    List<AccountResponse> findByIban(String iban);

    AccountResponse update(AccountRequestForUpdate request);
}

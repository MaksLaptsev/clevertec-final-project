package ru.clevertec.banking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.banking.dto.account.AccountRequest;
import ru.clevertec.banking.dto.account.AccountRequestForUpdate;
import ru.clevertec.banking.dto.account.AccountResponse;
import ru.clevertec.banking.dto.account.AccountWithCardResponse;
import ru.clevertec.banking.entity.Account;
import ru.clevertec.banking.mapper.AccountMapper;
import ru.clevertec.banking.repository.AccountRepository;
import ru.clevertec.banking.repository.specifications.FilterSpecifications;
import ru.clevertec.banking.service.AccountService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final FilterSpecifications<Account> specifications;

    @Override
    @Transactional
    public AccountResponse save(AccountRequest request) {
        return Optional.of(request)
                .map(mapper::fromRequest)
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("error save account"));
    }

    @Override
    public AccountResponse findByIban(String iban) {
        return Optional.of(repository.findAccountByIban(iban))
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Account not found with iban: " + iban));
    }

    @Override
    public List<AccountWithCardResponse> findByCustomer(String uuid) {
        return repository.findAll(specifications.filter(uuid))
                .stream()
                .map(account -> mapper.toResponseWithCards(account, account.getCards()))
                .toList();

    }

    @Override
    public List<AccountWithCardResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).stream()
                .map(acc -> mapper.toResponseWithCards(acc, acc.getCards()))
                .toList();
    }

    @Override
    @Transactional
    public AccountResponse update(AccountRequestForUpdate request) {
        return Optional.of(request)
                .map(AccountRequestForUpdate::iban)
                .map(repository::findAccountByIban)
                .map(acc -> mapper.updateFromRequest(request, acc))
                .map(repository::save)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("account not update"));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIban(String iban) {
        repository.deleteAccountByIban(iban);
    }
}

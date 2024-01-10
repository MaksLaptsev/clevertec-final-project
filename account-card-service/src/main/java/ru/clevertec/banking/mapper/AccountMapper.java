package ru.clevertec.banking.mapper;

import org.mapstruct.*;
import ru.clevertec.banking.dto.account.AccountRequest;
import ru.clevertec.banking.dto.account.AccountRequestForUpdate;
import ru.clevertec.banking.dto.account.AccountResponse;
import ru.clevertec.banking.dto.account.AccountWithCardResponse;
import ru.clevertec.banking.entity.Account;
import ru.clevertec.banking.entity.Card;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CardMapper.class)
public interface AccountMapper {

    @Mapping(target = "currency_code", source = "account.currencyCode")
    @Mapping(target = "open_date", source = "account.openDate")
    @Mapping(target = "main_acc", source = "account.mainAcc")
    @Mapping(target = "customer_id", source = "account.customerId")
    @Mapping(target = "customer_type", source = "account.customerType")
    @Mapping(target = "cards", source = "cards")
    AccountWithCardResponse toResponseWithCards(Account account, List<Card> cards);

    @Mapping(target = "currency_code", source = "account.currencyCode")
    @Mapping(target = "open_date", source = "account.openDate")
    @Mapping(target = "main_acc", source = "account.mainAcc")
    @Mapping(target = "customer_id", source = "account.customerId")
    @Mapping(target = "customer_type", source = "account.customerType")
    AccountResponse toResponse(Account account);

    List<AccountResponse> toListResponse(List<Account> accounts);

    @Mapping(target = "account.mainAcc", source = "request.main_acc",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "account.customerType", source = "request.customer_type",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateFromRequest(AccountRequestForUpdate request, @MappingTarget Account account);

    @Mapping(target = "currencyCode", source = "request.currency_code")
    @Mapping(target = "openDate", source = "request.open_date")
    @Mapping(target = "mainAcc", source = "request.main_acc")
    @Mapping(target = "customerId", source = "request.customer_id")
    @Mapping(target = "customerType", source = "request.customer_type")
    Account fromRequest(AccountRequest request);
}

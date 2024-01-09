package ru.clevertec.banking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.clevertec.banking.dto.account.AccountResponse;
import ru.clevertec.banking.dto.account.AccountWithCardResponse;
import ru.clevertec.banking.dto.card.CardResponse;
import ru.clevertec.banking.entity.Account;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "currency_code", source = "account.currencyCode")
    @Mapping(target = "open_date", source = "account.openDate")
    @Mapping(target = "main_acc", source = "account.mainAcc")
    @Mapping(target = "customer_id", source = "account.customerId")
    @Mapping(target = "customer_type", source = "account.customerType")
    @Mapping(target = "cards", source = "cards")
    AccountWithCardResponse toResponseWithCards(Account account, List<CardResponse> cards);

    @Mapping(target = "currency_code", source = "account.currencyCode")
    @Mapping(target = "open_date", source = "account.openDate")
    @Mapping(target = "main_acc", source = "account.mainAcc")
    @Mapping(target = "customer_id", source = "account.customerId")
    @Mapping(target = "customer_type", source = "account.customerType")
    AccountResponse toResponse(Account account);
}

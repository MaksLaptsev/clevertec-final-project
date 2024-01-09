package ru.clevertec.banking.dto.account;

import ru.clevertec.banking.dto.card.CardResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AccountWithCardResponse(String name,
                                      String iban,
                                      String iban_readable,
                                      BigDecimal amount,
                                      String currency_code,
                                      LocalDate open_date,
                                      boolean main_acc,
                                      String customer_id,
                                      String customer_type,
                                      BigDecimal rate,
                                      List<CardResponse> cards) {
}

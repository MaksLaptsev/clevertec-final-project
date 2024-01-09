package ru.clevertec.banking.dto.card;

public record CardResponse(String card_number,
                           String card_number_readable,
                           String iban,
                           String customer_id,
                           String customer_type,
                           String cardholder,
                           String card_status) {
}

package ru.clevertec.banking.dto.card;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CardRequest(
        @NotNull(message = "The card_number cannot be empty")
        String card_number,
        String card_number_readable,
        @NotNull(message = "The iban cannot be empty")
        String iban,
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Invalid customer_id format")
        String customer_id,
        @Pattern(regexp = "LEGAL|PHYSIC", message = "Acceptable customer_type are only: LEGAL or PHYSIC")
        String customer_type,
        @NotNull(message = "The cardholder cannot be empty")
        String cardholder,
        @Pattern(regexp = "ACTIVE|INACTIVE|BLOCKED|NEW",
                message = "Acceptable card_status are only: ACTIVE, INACTIVE, BLOCKED or NEW")
        String card_status) {
}

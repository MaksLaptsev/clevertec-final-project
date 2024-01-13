package ru.clevertec.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreditResponse(
        UUID customer_id,
        String contractNumber,
        LocalDate contractStartDate,
        BigDecimal totalDebt,
        BigDecimal currentDebt,
        String currency,
        LocalDate repaymentDate,
        BigDecimal rate,
        String iban,
        Boolean possibleRepayment,
        Boolean isClosed,
        String customer_type
) {
}

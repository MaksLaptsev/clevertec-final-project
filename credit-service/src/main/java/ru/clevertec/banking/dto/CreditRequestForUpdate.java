package ru.clevertec.banking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreditRequestForUpdate(
        @NotNull
        @JsonProperty("contractNumber")
        String contractNumber,
        @Null
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
        LocalDate repaymentDate,
        @Positive
        BigDecimal rate,
        Boolean possibleRepayment,
        Boolean isClosed,
        @Pattern(regexp = "LEGAL|PHYSIC", message = "Acceptable customer_type are only: LEGAL or PHYSIC")
        String customer_type
){
}

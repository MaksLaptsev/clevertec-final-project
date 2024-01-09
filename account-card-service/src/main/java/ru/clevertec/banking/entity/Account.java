package ru.clevertec.banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @UniqueElements
    private String iban;
    private BigDecimal amount;
    private String currencyCode;
    private LocalDate openDate;
    private boolean mainAcc;
    private String customerId;
    private String customerType;
    private BigDecimal rate;
    @OneToMany
    @JoinColumn(name = "account_id")
    private List<Card> cards;
}

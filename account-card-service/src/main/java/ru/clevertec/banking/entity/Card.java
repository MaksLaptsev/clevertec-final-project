package ru.clevertec.banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String iban;
    private String customerId;
    private String customerType;
    private String cardholder;
    private String cardStatus;
    @ManyToOne
    @JoinColumn(name = "iban", referencedColumnName = "iban", insertable = false, updatable = false)
    private Account account;
}

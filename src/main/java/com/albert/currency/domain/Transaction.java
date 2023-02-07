package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name="TRANSACTION_ID")
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXCHANGE_OPERATION")
    private ExchangeOperation exchangeOperation;
    @Column(name = "TRANSACTION_VALUE")
    private BigDecimal transactionValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private Cart cart;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXCHANGE_ORDER_ID")
    private ExchangeOrder exchangeOrder;

}

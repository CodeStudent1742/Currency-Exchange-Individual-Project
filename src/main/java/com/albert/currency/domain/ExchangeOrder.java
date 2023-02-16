package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "EXCHANGE_ORDERS")
public class ExchangeOrder {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "EXCHANGE_ORDER_ID", unique = true)
    private Long exchangeOrderId;

    @Column(name="EXCHANGE_DATE")
    private LocalDate exchangeDate;
    @Column(name="EXCHANGE_STATUS")
    private ExchangeStatus exchangeStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECORD_ID")
    private AccountRecord accountRecord;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(
            targetEntity = Transaction.class,
            mappedBy = "exchangeOrder",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Transaction> orderTransactions = new ArrayList<>();

    public ExchangeOrder(LocalDate exchangeDate, ExchangeStatus exchangeStatus, User user, List<Transaction> orderTransactions) {
        this.exchangeDate = exchangeDate;
        this.exchangeStatus = exchangeStatus;
        this.user = user;
        this.orderTransactions = orderTransactions;
    }

}

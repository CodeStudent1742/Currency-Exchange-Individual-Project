package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXCHANGE_OPERATION")
    private ExchangeOperation exchangeOperation;
    @Column(name = "TRANSACTION_VOLUME")
    private Double transactionVolume;
    @Column(name = "TRANSACTION_VALUE")
    private Double transactionValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CART_ID")
    private Cart cart;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EXCHANGE_ORDER_ID")
    private ExchangeOrder exchangeOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CANTOR_ID")
    Cantor cantor;

    public Transaction(Long transactionId, ExchangeOperation exchangeOperation, Double transactionVolume, Cart cart, ExchangeOrder exchangeOrder, Cantor cantor) {
        this.transactionId = transactionId;
        this.exchangeOperation = exchangeOperation;
        this.transactionVolume = transactionVolume;
        this.cart = cart;
        this.exchangeOrder = exchangeOrder;
        this.cantor = cantor;
        this.transactionValue = calculateValue();
    }

    public Transaction(ExchangeOperation exchangeOperation, Double transactionVolume, Cart cart, ExchangeOrder exchangeOrder, Cantor cantor) {
        this.exchangeOperation = exchangeOperation;
        this.transactionVolume = transactionVolume;
        this.cart = cart;
        this.exchangeOrder = exchangeOrder;
        this.cantor = cantor;
        this.transactionValue = calculateValue();
    }

    public Transaction(ExchangeOperation exchangeOperation, Double transactionVolume, Cart cart, Cantor cantor) {
        this.exchangeOperation = exchangeOperation;
        this.transactionVolume = transactionVolume;
        this.cart = cart;
        this.cantor = cantor;
        transactionValue = calculateValue();
    }

    private Double calculateValue() {
        Double value = Double.valueOf(0);
        switch (exchangeOperation) {
            case PLN_TO_EUR -> {
                value = transactionVolume * cantor.getSellingRateEUR();
            }
            case EUR_TO_PLN -> {
                value = transactionVolume * cantor.getPurchaseRateEUR();
            }
            case PLN_TO_USD -> {
                value = transactionVolume * cantor.getSellingRateUSD();
            }
            case USD_TO_PLN -> {
                value = transactionVolume * cantor.getPurchaseRateUSD();
            }
            case CHF_TO_PLN -> {
                value = transactionVolume * cantor.getPurchaseRateCHF();
            }
            case PLN_TO_CHF -> {
                value = transactionVolume * cantor.getSellingRateCHF();
            }
            case GBP_TO_PLN -> {
                value = transactionVolume * cantor.getPurchaseRateGBP();
            }
            case PLN_TO_GBP -> {
                value = transactionVolume * cantor.getSellingRateGBP();
            }
        }
        return value;
    }
    public void updateValue(){
        transactionValue = calculateValue();
    }
}

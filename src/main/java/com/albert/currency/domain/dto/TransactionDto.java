package com.albert.currency.domain.dto;

import com.albert.currency.domain.ExchangeOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long transactionId;
    private ExchangeOperation exchangeOperation;
    private Double transactionVolume;
    private Double transactionValue;

    private Long cartId;
    private Long exchangeOrderId;
    private Long cantorRatesId;

    public TransactionDto(ExchangeOperation exchangeOperation, Double transactionVolume, Long cartId) {
        this.exchangeOperation = exchangeOperation;
        this.transactionVolume = transactionVolume;
        this.cartId = cartId;
    }

    public TransactionDto(ExchangeOperation exchangeOperation, Double transactionVolume, Long cartId, Long cantorRatesId) {
        this.exchangeOperation = exchangeOperation;
        this.transactionVolume = transactionVolume;
        this.cartId = cartId;
        this.cantorRatesId = cantorRatesId;

    }
}

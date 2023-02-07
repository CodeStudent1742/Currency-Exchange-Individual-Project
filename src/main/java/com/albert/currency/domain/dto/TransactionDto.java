package com.albert.currency.domain.dto;

import com.albert.currency.domain.ExchangeOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long transactionId;
    private ExchangeOperation exchangeOperation;
    private BigDecimal transactionValue;

    private Long cartId;
    private Long exchangeOrderId;

}

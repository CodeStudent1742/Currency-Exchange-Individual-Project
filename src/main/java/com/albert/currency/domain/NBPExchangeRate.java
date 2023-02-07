package com.albert.currency.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NBPExchangeRate {


    private BigDecimal EUR_PLN;
    private BigDecimal USD_PLN;
    private BigDecimal CBP_PLN;
    private BigDecimal CHF_PLN;
}

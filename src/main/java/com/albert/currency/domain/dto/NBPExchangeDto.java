package com.albert.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NBPExchangeDto {

    private BigDecimal EUR_PLN;
    private BigDecimal USD_PLN;
    private BigDecimal CBP_PLN;
    private BigDecimal CHF_PLN;

}

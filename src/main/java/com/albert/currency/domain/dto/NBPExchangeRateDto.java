package com.albert.currency.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NBPExchangeRateDto {

    private Double bid_EUR_PLN;
    private Double ask_EUR_PLN;
    private Double bid_USD_PLN;
    private Double ask_USD_PLN;
    private Double bid_GBP_PLN;
    private Double ask_GBP_PLN;
    private Double bid_CHF_PLN;
    private Double ask_CHF_PLN;

}

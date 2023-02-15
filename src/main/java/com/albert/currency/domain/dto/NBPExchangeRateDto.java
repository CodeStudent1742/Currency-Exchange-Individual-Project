package com.albert.currency.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NBPExchangeRateDto {

    private LocalDate rateCheckDate = LocalDate.now();

    private Double bid_EUR_PLN;
    private Double ask_EUR_PLN;
    private Double bid_USD_PLN;
    private Double ask_USD_PLN;
    private Double bid_GBP_PLN;
    private Double ask_GBP_PLN;
    private Double bid_CHF_PLN;
    private Double ask_CHF_PLN;

}

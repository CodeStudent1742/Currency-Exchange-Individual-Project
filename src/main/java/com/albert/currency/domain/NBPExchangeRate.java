package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="NBP_EXCHANGE_RATES")
public class NBPExchangeRate {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name="EXCHANGE_RATE_ID", unique = true)
    private Long exchangeRateId;
    @Column( name ="RATE_CHECK_DATE")
    private LocalDate rateCheckDate = LocalDate.now();

    private BigDecimal bid_EUR_PLN;
    private BigDecimal ask_EUR_PLN;
    private BigDecimal bid_USD_PLN;
    private BigDecimal ask_USD_PLN;
    private BigDecimal bid_CBP_PLN;
    private BigDecimal ask_CBP_PLN;
    private BigDecimal bid_CHF_PLN;
    private BigDecimal ask_CHF_PLN;
}

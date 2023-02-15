package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private LocalDate rateCheckDate;

    @Column( name ="bid_EUR_PLN")
    private Double bid_EUR_PLN;
    @Column( name ="ask_EUR_PLN")
    private Double ask_EUR_PLN;
    @Column( name ="bid_USD_PLN")
    private Double bid_USD_PLN;
    @Column( name ="ask_USD_PLN")
    private Double ask_USD_PLN;
    @Column( name ="bid_GBP_PLN")
    private Double bid_GBP_PLN;
    @Column( name ="ask_GBP_PLN")
    private Double ask_GBP_PLN;
    @Column( name ="bid_CHF_PLN")
    private Double bid_CHF_PLN;
    @Column( name ="ask_CHF_PLN")
    private Double ask_CHF_PLN;

    public NBPExchangeRate(LocalDate rateCheckDate, Double bid_EUR_PLN, Double ask_EUR_PLN, Double bid_USD_PLN, Double ask_USD_PLN, Double bid_GBP_PLN, Double ask_GBP_PLN, Double bid_CHF_PLN, Double ask_CHF_PLN) {
        this.rateCheckDate = rateCheckDate;
        this.bid_EUR_PLN = bid_EUR_PLN;
        this.ask_EUR_PLN = ask_EUR_PLN;
        this.bid_USD_PLN = bid_USD_PLN;
        this.ask_USD_PLN = ask_USD_PLN;
        this.bid_GBP_PLN = bid_GBP_PLN;
        this.ask_GBP_PLN = ask_GBP_PLN;
        this.bid_CHF_PLN = bid_CHF_PLN;
        this.ask_CHF_PLN = ask_CHF_PLN;
    }
}

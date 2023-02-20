package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="CANTOR")
public class Cantor {

    @Id
    @GeneratedValue
    @NotNull
    @Column( name ="CANTOR_RATES_ID", unique = true)
    private Long cantorRatesId;

    @Column( name ="CANTOR_RATES_DATE")
    private LocalDate ratesCheckDate = LocalDate.now();

    @Column( name ="PURCHASE_EUR")
    private Double purchaseRateEUR;
    @Column( name ="SELLING_EUR")
    private Double sellingRateEUR;
    @Column( name ="PURCHASE_USD")
    private Double purchaseRateUSD;
    @Column( name ="SELLING_USD")
    private Double sellingRateUSD;
    @Column( name ="PURCHASE_GBP")
    private Double purchaseRateGBP;
    @Column( name ="SELLING_GBP")
    private Double sellingRateGBP;
    @Column( name ="PURCHASE_CHF")
    private Double purchaseRateCHF;
    @Column( name ="SELLING_CHF")
    private Double sellingRateCHF;

    @OneToOne(mappedBy = "cantor")
    private Transaction transaction;

    public Cantor(NBPExchangeRate nbpExchangeRate){
        calculateCantorRates(nbpExchangeRate);
    }

    private void calculateCantorRates(NBPExchangeRate nbpExchangeRate){
        if(nbpExchangeRate.getAsk_EUR_PLN() - nbpExchangeRate.getBid_EUR_PLN() > 2){
            purchaseRateEUR = nbpExchangeRate.getBid_EUR_PLN()+1;
            sellingRateEUR = nbpExchangeRate.getAsk_EUR_PLN()-1;

        }else{
            purchaseRateEUR = nbpExchangeRate.getBid_EUR_PLN();
            sellingRateEUR = nbpExchangeRate.getAsk_EUR_PLN();
        }
        if(nbpExchangeRate.getAsk_USD_PLN() - nbpExchangeRate.getBid_USD_PLN() > 2){
            purchaseRateUSD = nbpExchangeRate.getBid_USD_PLN()+1;
            sellingRateUSD = nbpExchangeRate.getAsk_USD_PLN()-1;
        }else{
            purchaseRateUSD = nbpExchangeRate.getBid_USD_PLN();
            sellingRateUSD = nbpExchangeRate.getAsk_USD_PLN();
        }

        if(nbpExchangeRate.getAsk_GBP_PLN() - nbpExchangeRate.getBid_GBP_PLN() > 2){
            purchaseRateGBP = nbpExchangeRate.getBid_GBP_PLN()+1;
            sellingRateGBP = nbpExchangeRate.getAsk_GBP_PLN()-1;
        }else{
            purchaseRateGBP = nbpExchangeRate.getBid_GBP_PLN();
            sellingRateGBP = nbpExchangeRate.getAsk_GBP_PLN();
        }

        if(nbpExchangeRate.getAsk_CHF_PLN() - nbpExchangeRate.getBid_CHF_PLN() > 2){
            purchaseRateCHF = nbpExchangeRate.getBid_CHF_PLN()+1;
            sellingRateCHF = nbpExchangeRate.getAsk_CHF_PLN()-1;
        }else{
            purchaseRateCHF = nbpExchangeRate.getBid_CHF_PLN();
            sellingRateCHF = nbpExchangeRate.getAsk_CHF_PLN();
        }
    }
}

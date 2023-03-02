package com.albert.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CantorDto {

    private Long cantorRatesId;

    private LocalDate ratesCheckDate;

    private Double purchaseRateEUR;

    private Double sellingRateEUR;

    private Double purchaseRateUSD;

    private Double sellingRateUSD;

    private Double purchaseRateGBP;

    private Double sellingRateGBP;

    private Double purchaseRateCHF;

    private Double sellingRateCHF;
}

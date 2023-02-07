package com.albert.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CantorDto {

    private BigDecimal purchaseRateEUR;
    private BigDecimal sellingRateEUR;
    private BigDecimal purchaseRateUSD;
    private BigDecimal sellingRateUSD;
    private BigDecimal purchaseRateGPD;
    private BigDecimal sellingRateGPD;
    private BigDecimal purchaseRateCHF;
    private BigDecimal sellingRateCHF;

}

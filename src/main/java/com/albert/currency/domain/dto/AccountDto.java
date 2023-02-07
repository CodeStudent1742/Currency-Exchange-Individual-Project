package com.albert.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long accountId;

    private BigDecimal balancePLN;
    private BigDecimal balanceEUR;
    private BigDecimal balanceUSD;
    private BigDecimal balanceCHF;
    private BigDecimal balanceGPD;

    private Long userId;

}

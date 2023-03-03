package com.albert.currency.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartBalanceDto {
    private Long cartBalanceId;

    private BigDecimal balancePLN = BigDecimal.valueOf(0);
    private BigDecimal balanceEUR = BigDecimal.valueOf(0);
    private BigDecimal balanceUSD = BigDecimal.valueOf(0);
    private BigDecimal balanceCHF = BigDecimal.valueOf(0);
    private BigDecimal balanceGBP = BigDecimal.valueOf(0);

    private Long cartId;
}

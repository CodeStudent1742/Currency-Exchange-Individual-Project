package com.albert.currency.domain.dto;

import com.albert.currency.domain.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewExchangeOrderDto {

    private LocalDate exchangeDate;
    private ExchangeStatus exchangeStatus;
    private Long userId;
    private List<Long> orderTransactionIds;

}

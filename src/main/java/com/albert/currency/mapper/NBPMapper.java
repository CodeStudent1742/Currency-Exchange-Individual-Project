package com.albert.currency.mapper;

import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.domain.dto.NBPExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NBPMapper {

    public NBPExchangeRate mapToNBPExchangeRate(NBPExchangeRateDto nbpExchangeRateDto) {
        return new NBPExchangeRate(
                nbpExchangeRateDto.getRateCheckDate(),
                nbpExchangeRateDto.getBid_EUR_PLN(),
                nbpExchangeRateDto.getAsk_EUR_PLN(),
                nbpExchangeRateDto.getBid_USD_PLN(),
                nbpExchangeRateDto.getAsk_USD_PLN(),
                nbpExchangeRateDto.getBid_GBP_PLN(),
                nbpExchangeRateDto.getAsk_GBP_PLN(),
                nbpExchangeRateDto.getBid_CHF_PLN(),
                nbpExchangeRateDto.getAsk_CHF_PLN());
    }
}

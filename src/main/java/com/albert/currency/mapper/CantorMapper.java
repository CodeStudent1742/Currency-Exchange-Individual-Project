package com.albert.currency.mapper;

import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.dto.CantorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CantorMapper {

    public CantorDto mapToCantorDto(Cantor cantor){
        return new CantorDto(
                cantor.getCantorRatesId(),
                cantor.getRatesCheckDate(),
                cantor.getPurchaseRateEUR(),
                cantor.getSellingRateEUR(),
                cantor.getPurchaseRateUSD(),
                cantor.getSellingRateUSD(),
                cantor.getPurchaseRateGBP(),
                cantor.getSellingRateGBP(),
                cantor.getPurchaseRateCHF(),
                cantor.getSellingRateCHF()
        );
    }
}

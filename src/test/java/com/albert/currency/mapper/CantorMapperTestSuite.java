package com.albert.currency.mapper;


import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.dto.CantorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CantorMapperTestSuite {



    @Autowired
    CantorMapper cantorMapper;

    @Test
    public void testMapToCantorDto(){
        //GIVEN
        Cantor cantor = new Cantor(1L, LocalDate.of(2022, 10, 22),3.0,4.0,3.1,4.1,3.2,4.2,3.3,4.3,null);
        //WHEN
        CantorDto cantorDto = cantorMapper.mapToCantorDto(cantor);
        //THEN
        assertEquals(1L, cantorDto.getCantorRatesId());
        assertEquals(LocalDate.of(2022, 10, 22), cantorDto.getRatesCheckDate());
        assertEquals(3.0,cantorDto.getPurchaseRateEUR());
        assertEquals(4.0,cantorDto.getSellingRateEUR());
        assertEquals(3.1,cantorDto.getPurchaseRateUSD());
        assertEquals(4.1,cantorDto.getSellingRateUSD());
        assertEquals(3.2,cantorDto.getPurchaseRateGBP());
        assertEquals(4.2,cantorDto.getSellingRateGBP());
        assertEquals(3.3,cantorDto.getPurchaseRateCHF());
        assertEquals(4.3,cantorDto.getSellingRateCHF());
    }

}

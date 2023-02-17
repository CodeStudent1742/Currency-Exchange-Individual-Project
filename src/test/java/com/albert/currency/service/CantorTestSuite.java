package com.albert.currency.service;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import com.albert.currency.repository.CantorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Transactional
@SpringBootTest
public class CantorTestSuite {

    @Autowired
    CantorService cantorService;
    @Autowired
    NBPClient nbpClient;
    @Autowired
    NBPExchangeRateMapper nbpExchangeRateMapper;
    @Autowired
    private CantorRepository cantorRepository;

    @Test
    public void testGetCantorRates(){
        //GIVEN&WHEN
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor2 = new Cantor(nbpExchangeRate);
        cantorService.saveCantor(cantor2);
        Cantor cantorTested = cantorService.getCantorRates();

        //THEN
        assertNotEquals(null,cantorTested.getSellingRateEUR());
        assertNotEquals(null,cantorTested.getPurchaseRateEUR());
        assertNotEquals(null,cantorTested.getSellingRateUSD());
        assertNotEquals(null,cantorTested.getPurchaseRateUSD());
        assertNotEquals(null,cantorTested.getSellingRateCHF());
        assertNotEquals(null,cantorTested.getPurchaseRateCHF());
        assertNotEquals(null,cantorTested.getSellingRateGBP());
        assertNotEquals(null,cantorTested.getPurchaseRateGBP());
        System.out.println(cantorTested);
        //CLEANUP
        cantorRepository.deleteAll();
    }

}

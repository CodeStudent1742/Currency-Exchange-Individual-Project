package com.albert.currency.toDelete;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import com.albert.currency.repository.CantorRepository;
import com.albert.currency.repository.NBPExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Cantor {

    @Autowired
    NBPExchangeRateRepository nbpExchangeRateRepository;
    @Autowired
    CantorRepository cantorRepository;
    @Autowired
    NBPExchangeRateMapper nbpExchangeRateMapper;
    @Autowired
    NBPClient nbpClient;

    @Test
    public void addNBPandCantorRates(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        nbpExchangeRateRepository.save(nbpExchangeRate);
        com.albert.currency.domain.Cantor cantor = new com.albert.currency.domain.Cantor(nbpExchangeRate);
        cantorRepository.save(cantor);
    }
}

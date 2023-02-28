package com.albert.currency.scheduler;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import com.albert.currency.repository.CantorRepository;
import com.albert.currency.repository.NBPExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatesScheduler {

    private final NBPClient nbpClient;
    private final NBPExchangeRateRepository nbpExchangeRateRepository;
    private final NBPExchangeRateMapper nbpExchangeRateMapper;
    private final CantorRepository cantorRepository;

    @Scheduled(cron = "0 16 8 ? * MON-FRI")
//   @Scheduled(fixedDelay = 1000)
    public void getAndSaveNBPRates() {
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        nbpExchangeRateRepository.save(nbpExchangeRate);
    }

    @Scheduled(cron = "0 16 8 ? * MON-FRI")
//   @Scheduled(fixedDelay = 1000)
    public void changeCantorRates() {
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        cantorRepository.save(cantor);
    }

}

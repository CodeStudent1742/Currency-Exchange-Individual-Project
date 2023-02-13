package com.albert.currency.controller;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.dto.NBPExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/nbp")
public class NBPExchangeRateController {

    private final NBPClient nbpClient;

    //For "manual" implementation testing only
    @GetMapping("rates")
    public void getRates() {
        NBPExchangeRateDto nbpExchangeRateDto = nbpClient.getNBPRates();
        System.out.println(
                "EUR bid " + nbpExchangeRateDto.getBid_EUR_PLN() + "\n" +
                        "EUR ask " + nbpExchangeRateDto.getAsk_EUR_PLN() + "\n" +
                        "USD bid " + nbpExchangeRateDto.getBid_USD_PLN() + "\n" +
                        "USD ask " + nbpExchangeRateDto.getAsk_USD_PLN() + "\n" +
                        "CHF bid " + nbpExchangeRateDto.getBid_CHF_PLN() + "\n" +
                        "CHF ask " + nbpExchangeRateDto.getAsk_CHF_PLN() + "\n" +
                        "GBP bid " + nbpExchangeRateDto.getBid_CHF_PLN() + "\n" +
                        "GBP ask " + nbpExchangeRateDto.getAsk_CHF_PLN() + "\n");
    }
}

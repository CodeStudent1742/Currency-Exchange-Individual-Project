package com.albert.currency.client;

import com.albert.currency.domain.dto.NBPExchangeRateDto;
import com.albert.currency.domain.dto.nbp.NBPResponse;
import com.albert.currency.domain.dto.nbp.Rate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NBPClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NBPClient.class);
    private final RestTemplate restTemplate;
    @Value("${nbp.rates.all}")
    private String npbApiEndpoint;

    // NBP update each working day , between 7:45 and 8:15
    public NBPExchangeRateDto getNBPRates() {
        List<Rate> exchangeRates = getExchangeRates();
        return getNBPExchangeRateDto(exchangeRates);

    }

    private List<Rate> getExchangeRates() {
        URI url = urlCreation();
        try {
            NBPResponse[] response = restTemplate.getForObject(url, NBPResponse[].class);
            return Arrays.stream(Objects.requireNonNull(response)).toList().stream()
                    .map(NBPResponse::getRates)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private URI urlCreation() {
        return UriComponentsBuilder.fromHttpUrl(npbApiEndpoint + "/exchangerates/tables/c/last")
                .queryParam("format","json")
                .build()
                .encode()
                .toUri();
    }


    private NBPExchangeRateDto getNBPExchangeRateDto(List<Rate> currencyRates) {
        NBPExchangeRateDto nbpExchangeRateDto = new NBPExchangeRateDto();
        for (Rate rate : currencyRates) {
            switch (rate.getCode()) {
                case "EUR" -> {
                    nbpExchangeRateDto.setBid_EUR_PLN(rate.getBid());
                    nbpExchangeRateDto.setAsk_EUR_PLN(rate.getAsk());
                }
                case "USD" -> {
                    nbpExchangeRateDto.setBid_USD_PLN(rate.getBid());
                    nbpExchangeRateDto.setAsk_USD_PLN(rate.getAsk());
                }
                case "CHF" -> {
                    nbpExchangeRateDto.setBid_CHF_PLN(rate.getBid());
                    nbpExchangeRateDto.setAsk_CHF_PLN(rate.getAsk());
                }
                case "GBP" -> {
                    nbpExchangeRateDto.setBid_GBP_PLN(rate.getBid());
                    nbpExchangeRateDto.setAsk_GBP_PLN(rate.getAsk());
                }
                default -> {
                }
            }
        }
        return nbpExchangeRateDto;
    }
}



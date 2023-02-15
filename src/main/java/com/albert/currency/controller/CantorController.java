package com.albert.currency.controller;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/cantor")
public class CantorController {

   private final NBPClient nbpClient;
   private final NBPExchangeRateMapper nbpExchangeRateMapper;

   //To do: Change to read from database, if no ask between 7:45 and 8:30 when NBP change values

   @GetMapping(value="/bid/EUR")
    public ResponseEntity<Double> getEURBid(){
       NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
       Cantor cantor = new Cantor(nbpExchangeRate);
      return  ResponseEntity.ok(cantor.getPurchaseRateEUR());
   }
    @GetMapping(value="/ask/EUR")
    public ResponseEntity<Double> getEURAsk(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getSellingRateEUR());
    }
    @GetMapping(value="/bid/USD")
    public ResponseEntity<Double> getUSDBid(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getPurchaseRateUSD());
    }
    @GetMapping(value="/ask/USD")
    public ResponseEntity<Double> getUSDAsk(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getSellingRateUSD());
    }

    @GetMapping(value="/bid/GBP")
    public ResponseEntity<Double> getGBPBid(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getPurchaseRateGBP());
    }
    @GetMapping(value="/ask/GBP")
    public ResponseEntity<Double> getGBPAsk(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getSellingRateGBP());
    }

    @GetMapping(value="/bid/CHF")
    public ResponseEntity<Double> getCHFBid(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getPurchaseRateCHF());
    }
    @GetMapping(value="/ask/CHF")
    public ResponseEntity<Double> getCHFAsk(){
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        return  ResponseEntity.ok(cantor.getSellingRateCHF());
    }
}

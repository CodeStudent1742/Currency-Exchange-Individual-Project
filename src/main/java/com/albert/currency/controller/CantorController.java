package com.albert.currency.controller;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import com.albert.currency.service.CantorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/cantor")
public class CantorController {

   private final CantorService cantorService;
   private final NBPClient nbpClient;
   private final NBPExchangeRateMapper nbpExchangeRateMapper;

    //NBP Rates Update between 7:45 and 8:15 MON-FRI
   private final LocalTime startTime = LocalTime.of(7, 45);
   private final LocalTime endTime = LocalTime.of(8, 15);
   private final LocalTime now = LocalTime.now();



   @GetMapping(value="/bid/EUR")
    public ResponseEntity<Double> getEURBid(){
       if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
               now.isAfter(startTime) && now.isBefore(endTime)) {
               NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
               Cantor cantor = new Cantor(nbpExchangeRate);
               return  ResponseEntity.ok(cantor.getPurchaseRateEUR());
       }else{
           Cantor cantor = cantorService.getCantorRates();
           return  ResponseEntity.ok(cantor.getPurchaseRateEUR());
       }
   }
    @GetMapping(value="/ask/EUR")
    public ResponseEntity<Double> getEURAsk(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getSellingRateEUR());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getSellingRateEUR());
        }
    }

    @GetMapping(value="/bid/USD")
    public ResponseEntity<Double> getUSDBid(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getPurchaseRateUSD());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getPurchaseRateUSD());
        }
    }

    @GetMapping(value="/ask/USD")
    public ResponseEntity<Double> getUSDAsk(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getSellingRateUSD());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getSellingRateUSD());
        }
    }


    @GetMapping(value="/bid/GBP")
    public ResponseEntity<Double> getGBPBid(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getPurchaseRateGBP());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getPurchaseRateGBP());
        }
    }

    @GetMapping(value="/ask/GBP")
    public ResponseEntity<Double> getGBPAsk(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getSellingRateGBP());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getSellingRateGBP());
        }
    }

    @GetMapping(value="/bid/CHF")
    public ResponseEntity<Double> getCHFBid(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getPurchaseRateCHF());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getPurchaseRateCHF());
        }
    }
    @GetMapping(value="/ask/CHF")
    public ResponseEntity<Double> getCHFAsk(){
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime)) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            return  ResponseEntity.ok(cantor.getSellingRateCHF());
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return  ResponseEntity.ok(cantor.getSellingRateCHF());
        }
    }
}

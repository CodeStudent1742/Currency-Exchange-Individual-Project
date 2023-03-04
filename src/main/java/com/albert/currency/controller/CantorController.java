package com.albert.currency.controller;

import com.albert.currency.client.NBPClient;
import com.albert.currency.domain.Cantor;
import com.albert.currency.domain.NBPExchangeRate;
import com.albert.currency.domain.dto.CantorDto;
import com.albert.currency.mapper.CantorMapper;
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

    private final CantorMapper cantorMapper;
    private final CantorService cantorService;
    private final NBPClient nbpClient;
    private final NBPExchangeRateMapper nbpExchangeRateMapper;

    //NBP Rates Update between 7:45 and 8:15 MON-FRI
    private final LocalTime startTime = LocalTime.of(7, 45);
    private final LocalTime endTime = LocalTime.of(8, 15);
    private final LocalTime now = LocalTime.now();

    @GetMapping(value = "/all")
    public ResponseEntity<CantorDto> getCantor() {
        if (LocalDate.now().getDayOfWeek().getValue() >= 1 && LocalDate.now().getDayOfWeek().getValue() <= 5 &&
                now.isAfter(startTime) && now.isBefore(endTime) || cantorService.getCantorRates() == null) {
            NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
            Cantor cantor = new Cantor(nbpExchangeRate);
            cantorService.saveCantor(cantor);
            return ResponseEntity.ok(cantorMapper.mapToCantorDto(cantor));
        } else {
            Cantor cantor = cantorService.getCantorRates();
            return ResponseEntity.ok(cantorMapper.mapToCantorDto(cantor));
        }
    }

}

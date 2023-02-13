package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.AccountRecordNotFoundException;
import com.albert.currency.controller.exceptions.ExchangeOrderNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.dto.ExchangeOrderDto;
import com.albert.currency.domain.dto.NewExchangeOrderDto;
import com.albert.currency.mapper.ExchangeOrderMapper;
import com.albert.currency.service.ExchangeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/exchange")
public class ExchangeOrderController {

    private final ExchangeOrderMapper exchangeOrderMapper;
    private final ExchangeOrderService exchangeOrderService;

    @GetMapping
    public ResponseEntity<List<ExchangeOrderDto>> getExchangeOrders() {
        List<ExchangeOrder> exchangeOrders = exchangeOrderService.getAllExchangeOrders();
        return ResponseEntity.ok(exchangeOrderMapper.mapToExchangeOrdersDto(exchangeOrders));

    }

    @GetMapping(value = "exchangeOrderId")
    public ResponseEntity<ExchangeOrderDto> getExchangeOrder(Long exchangeOrderId) throws ExchangeOrderNotFoundException {
        ExchangeOrder exchangeOrder = exchangeOrderService.getExchangeOrderById(exchangeOrderId);
        return ResponseEntity.ok(exchangeOrderMapper.mapToExchangeOrderDto(exchangeOrder));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createExchangeOrder(@RequestBody NewExchangeOrderDto newExchangeOrderDto) throws UserNotFoundException, AccountRecordNotFoundException {
        ExchangeOrder exchangeOrder = exchangeOrderMapper.mapToNewExchangeOrder(newExchangeOrderDto);
        exchangeOrderService.save(exchangeOrder);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExchangeOrderDto> updateExchangeOrder(@RequestBody ExchangeOrderDto exchangeOrderDto) throws ExchangeOrderNotFoundException, AccountRecordNotFoundException, UserNotFoundException {
        ExchangeOrder exchangeOrder = exchangeOrderMapper.mapToExchangeOrder(exchangeOrderDto);
        exchangeOrderService.save(exchangeOrder);
        return ResponseEntity.ok(exchangeOrderMapper.mapToExchangeOrderDto(exchangeOrder));
    }

    @DeleteMapping(value = "exchangeOrderId")
    public ResponseEntity<Void> deleteExchangeOrder(Long exchangeOrderId) throws ExchangeOrderNotFoundException {
        exchangeOrderService.delete(exchangeOrderId);
        return ResponseEntity.ok().build();
    }
}

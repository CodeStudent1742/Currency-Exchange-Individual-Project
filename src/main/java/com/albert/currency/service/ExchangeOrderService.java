package com.albert.currency.service;

import com.albert.currency.controller.exceptions.ExchangeOrderNotFoundException;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.repository.ExchangeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeOrderService {

    private final ExchangeOrderRepository exchangeOrderRepository;

    public ExchangeOrder getExchangeOrderById(Long exchangeOrderId) throws ExchangeOrderNotFoundException {
        return exchangeOrderRepository.findById(exchangeOrderId).orElseThrow(ExchangeOrderNotFoundException::new);
    }

    public List<ExchangeOrder> getAllExchangeOrders() {
        return exchangeOrderRepository.findAll();
    }

    public void save(ExchangeOrder exchangeOrder) {
        exchangeOrderRepository.save(exchangeOrder);
    }

    public void delete(Long exchangeOrderId) {
        exchangeOrderRepository.deleteById(exchangeOrderId);
    }
}

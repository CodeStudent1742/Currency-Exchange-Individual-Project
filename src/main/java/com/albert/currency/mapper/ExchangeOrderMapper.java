package com.albert.currency.mapper;

import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.repository.ExchangeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExchangeOrderMapper {

    private final ExchangeOrderRepository exchangeOrderRepository;
    public List<ExchangeOrder> mapToExchangeOrders(List<Long> exchangeOrderIds) {
        List<ExchangeOrder> results = new ArrayList<>();
        if(Objects.nonNull(exchangeOrderIds)){
            for (Long id : exchangeOrderIds) {
                exchangeOrderRepository.findById(id).ifPresent(results::add);
            }
        }
        return results;
    }
}

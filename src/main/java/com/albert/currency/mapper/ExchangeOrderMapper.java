package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.ExchangeOrderNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.ExchangeOrderDto;
import com.albert.currency.domain.dto.NewExchangeOrderDto;
import com.albert.currency.repository.ExchangeOrderRepository;
import com.albert.currency.repository.TransactionRepository;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ExchangeOrderMapper {
    private final ExchangeOrderRepository exchangeOrderRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public ExchangeOrderDto mapToExchangeOrderDto(ExchangeOrder exchangeOrder) {
        return new ExchangeOrderDto(
                exchangeOrder.getExchangeOrderId(),
                exchangeOrder.getExchangeDate(),
                exchangeOrder.getExchangeStatus(),
                exchangeOrder.getUser().getUserId(),
                mapToTransactionsId(exchangeOrder.getOrderTransactions())
        );
    }

    public List<Long> mapToTransactionsId(List<Transaction> orderTransactions) {
        return orderTransactions.stream()
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList());
    }

    public List<ExchangeOrderDto> mapToExchangeOrdersDto(List<ExchangeOrder> exchangeOrders) {
        return exchangeOrders.stream()
                .map(this::mapToExchangeOrderDto)
                .collect(Collectors.toList());
    }

    public ExchangeOrder mapToNewExchangeOrder(NewExchangeOrderDto newExchangeOrderDto) throws  UserNotFoundException {
        return new ExchangeOrder(
                newExchangeOrderDto.getExchangeDate(),
                newExchangeOrderDto.getExchangeStatus(),
                userRepository.findById(newExchangeOrderDto.getUserId()).orElseThrow(UserNotFoundException::new),
                transactionRepository.findAllById(newExchangeOrderDto.getOrderTransactionIds())
        );
    }

    public ExchangeOrder mapToExchangeOrder(ExchangeOrderDto exchangeOrderDto) throws ExchangeOrderNotFoundException {
        ExchangeOrder exchangeOrder = exchangeOrderRepository.findById(exchangeOrderDto.getOrderId()).orElseThrow(ExchangeOrderNotFoundException::new);
        exchangeOrder.setExchangeStatus(exchangeOrderDto.getExchangeStatus());
        return exchangeOrder;
    }

}

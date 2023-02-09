package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.AccountRecordNotFoundException;
import com.albert.currency.controller.exceptions.ExchangeOrderNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.ExchangeOrderDto;
import com.albert.currency.domain.dto.NewExchangeOrderDto;
import com.albert.currency.repository.AccountRecordRepository;
import com.albert.currency.repository.ExchangeOrderRepository;
import com.albert.currency.repository.TransactionRepository;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ExchangeOrderMapper {

    private final ExchangeOrderRepository exchangeOrderRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final AccountRecordRepository accountRecordRepository;

    public List<ExchangeOrder> mapToExchangeOrdersById(List<Long> exchangeOrderIds) {
        List<ExchangeOrder> results = new ArrayList<>();
        if (Objects.nonNull(exchangeOrderIds)) {
            for (Long id : exchangeOrderIds) {
                exchangeOrderRepository.findById(id).ifPresent(results::add);
            }
        }
        return results;
    }

    public ExchangeOrderDto mapToExchangeOrderDto(ExchangeOrder exchangeOrder) {
        return new ExchangeOrderDto(
                exchangeOrder.getExchangeOrderId(),
                exchangeOrder.getExchangeDate(),
                exchangeOrder.getExchangeStatus(),
                exchangeOrder.getAccountRecord().getRecordId(),
                exchangeOrder.getUser().getUserId(),
                mapToTransactionsId(exchangeOrder.getOrderTransactions())
        );
    }

    private List<Long> mapToTransactionsId(List<Transaction> orderTransactions) {
        return orderTransactions.stream()
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList());
    }

    public List<ExchangeOrderDto> mapToExchangeOrdersDtos(List<ExchangeOrder> exchangeOrders) {
        return exchangeOrders.stream()
                .map(this::mapToExchangeOrderDto)
                .collect(Collectors.toList());
    }

    public ExchangeOrder mapToNewExchangeOrder(NewExchangeOrderDto newExchangeOrderDto) throws AccountRecordNotFoundException, UserNotFoundException {
        return new ExchangeOrder(
                newExchangeOrderDto.getExchangeDate(),
                newExchangeOrderDto.getExchangeStatus(),
                accountRecordRepository.findById(newExchangeOrderDto.getAccountRecordId()).orElseThrow(AccountRecordNotFoundException::new),
                userRepository.findById(newExchangeOrderDto.getUserId()).orElseThrow(UserNotFoundException::new),
                transferToTransactions(transactionRepository.findAllById(newExchangeOrderDto.getOrderTransactionIds()))
        );
    }

    public ExchangeOrder mapToExchangeOrder(ExchangeOrderDto exchangeOrderDto) throws ExchangeOrderNotFoundException, UserNotFoundException, AccountRecordNotFoundException {
        ExchangeOrder exchangeOrder = exchangeOrderRepository.findById(exchangeOrderDto.getOrderId()).orElseThrow(ExchangeOrderNotFoundException::new);
        exchangeOrder.setExchangeDate(exchangeOrderDto.getExchangeDate());
        exchangeOrder.setExchangeStatus(exchangeOrderDto.getExchangeStatus());
        exchangeOrder.setAccountRecord(accountRecordRepository.findById(exchangeOrderDto.getAccountRecordId()).orElseThrow(AccountRecordNotFoundException::new));
        exchangeOrder.setUser(userRepository.findById(exchangeOrderDto.getUserId()).orElseThrow(UserNotFoundException::new));
        exchangeOrder.setOrderTransactions(transferToTransactions(transactionRepository.findAllById(exchangeOrderDto.getOrderTransactionIds())));
        return exchangeOrder;
    }

    private List<Transaction> transferToTransactions(Iterable<Transaction> transactionsIterable) {
        return StreamSupport.
                stream(transactionsIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

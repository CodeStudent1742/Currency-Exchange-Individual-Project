package com.albert.currency.mapper;

import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.TransactionDto;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class TransactionMapper {

    public TransactionDto mapToTransactionDto(Transaction transaction){
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getExchangeOperation(),
                transaction.getTransactionValue(),
                transaction.getCart().getCartId(),
                transaction.getExchangeOrder().getExchangeOrderId()
        );
    }
    public List<TransactionDto> mapToTransactionsDto(List<Transaction> transactions) {
        return transactions.stream().map(this::mapToTransactionDto).collect(Collectors.toList());
    }
}

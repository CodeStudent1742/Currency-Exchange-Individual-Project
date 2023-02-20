package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.repository.CantorRepository;
import com.albert.currency.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionMapper {
    CartRepository cartRepository;
    CantorRepository cantorRepository;

    public TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getExchangeOperation(),
                transaction.getTransactionVolume(),
                transaction.getTransactionValue(),
                transaction.getCart().getCartId(),
                transaction.getExchangeOrder().getExchangeOrderId(),
                transaction.getCantor().getCantorRatesId()
        );
    }

    public List<TransactionDto> mapToTransactionsDto(List<Transaction> transactions) {
        return transactions.stream().map(this::mapToTransactionDto).collect(Collectors.toList());
    }

    public Transaction mapToTransaction(TransactionDto transactionDto) throws CartNotFoundException {
        return new Transaction(
                transactionDto.getExchangeOperation(),
                transactionDto.getTransactionVolume(),
                cartRepository.findById(transactionDto.getCartId()).orElseThrow(CartNotFoundException::new),
                cantorRepository.findTopByOrderByCantorRatesIdDesc());
    }
}

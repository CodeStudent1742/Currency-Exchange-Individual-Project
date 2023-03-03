package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.CantorNotFoundException;
import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.repository.CantorRepository;
import com.albert.currency.repository.CartRepository;
import com.albert.currency.repository.ExchangeOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionMapper {
   private final CartRepository cartRepository;
    private final CantorRepository cantorRepository;
    private final ExchangeOrderRepository exchangeOrderRepository;

    public TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getExchangeOperation(),
                transaction.getTransactionVolume(),
                transaction.getTransactionValue(),
                transaction.getCart() != null ? transaction.getCart().getCartId() : null,
                transaction.getExchangeOrder() != null ? transaction.getExchangeOrder().getExchangeOrderId() : null,
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
    public Transaction mapToUpdateTransaction(TransactionDto transactionDto) throws CartNotFoundException, CantorNotFoundException {
        return new Transaction(
                transactionDto.getTransactionId(),
                transactionDto.getExchangeOperation(),
                transactionDto.getTransactionVolume(),
                cartRepository.findById(transactionDto.getCartId()).orElseThrow(CartNotFoundException::new),
                exchangeOrderRepository.findById(transactionDto.getExchangeOrderId()).orElseThrow(null),
                cantorRepository.findById(transactionDto.getCantorRatesId()).orElseThrow(CantorNotFoundException::new));
    }
}

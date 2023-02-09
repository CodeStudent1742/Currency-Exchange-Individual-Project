package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.TransactionNotFoundException;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactionMapper.mapToTransactionsDtos(transactions));
    }

    @GetMapping(value = "{transactionId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable Long transactionId) throws TransactionNotFoundException {
        Transaction transaction = transactionService.getTransaction(transactionId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionDto(transaction));
    }
    @DeleteMapping(value = "{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId){
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok().build();
    }
}

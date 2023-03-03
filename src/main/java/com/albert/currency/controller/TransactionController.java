package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.CantorNotFoundException;
import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.controller.exceptions.TransactionNotFoundException;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
        return ResponseEntity.ok(transactionMapper.mapToTransactionsDto(transactions));
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
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionDto transactionDto) throws CartNotFoundException {
        Transaction transaction = transactionMapper.mapToTransaction(transactionDto);
        transactionService.saveTransaction(transaction);
        return ResponseEntity.ok().build();
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTransaction(@RequestBody TransactionDto transactionDto) throws CartNotFoundException, CantorNotFoundException {
       Transaction transaction= transactionMapper.mapToUpdateTransaction(transactionDto);
        transactionService.saveTransaction(transaction);
        return ResponseEntity.ok().build();
    }

}

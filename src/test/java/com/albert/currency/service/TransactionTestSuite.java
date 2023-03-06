package com.albert.currency.service;

import com.albert.currency.client.NBPClient;
import com.albert.currency.controller.exceptions.TransactionNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class TransactionTestSuite {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CantorService cantorService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    NBPExchangeRateMapper nbpExchangeRateMapper;
    @Autowired
    NBPClient nbpClient;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CantorRepository cantorRepository;
    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void testGetTransaction() throws TransactionNotFoundException {
        //GIVEN
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        cantorService.saveCantor(cantor);
        Cart cart1 = new Cart();
        cartService.saveCart(cart1);
        User user1 = new User("user1");
        user1.setCart(cart1);
        userService.saveUser(user1);
        Transaction transaction = new Transaction(ExchangeOperation.PLN_TO_CHF,400.0,cart1,cantor);
        transactionService.saveTransaction(transaction);
        Long id = transaction.getTransactionId();

        //WHEN
        Transaction testedTransaction = transactionService.getTransaction(id);

        //THEN
        assertEquals(400.0, testedTransaction.getTransactionVolume());
        assertEquals(ExchangeOperation.PLN_TO_CHF,testedTransaction.getExchangeOperation());

        //CLEAN UP
        transactionRepository.deleteById(id);
        cantorRepository.delete(cantor);
        cartRepository.delete(cart1);
        userRepository.delete(user1);
    }
    @Test
    public void testGetAllTransactions() {
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        cantorService.saveCantor(cantor);
        Cart cart1 = new Cart();
        cartService.saveCart(cart1);
        Account account1 = new Account();
        accountService.save(account1);
        User user1 = new User("user1");
        user1.setCart(cart1);
        user1.setAccount(account1);
        userService.saveUser(user1);
        Transaction transaction1 = new Transaction(ExchangeOperation.PLN_TO_CHF,400.0,cart1,cantor);
        transactionService.saveTransaction(transaction1);
        Long id1 = transaction1.getTransactionId();
        Transaction transaction2 = new Transaction(ExchangeOperation.USD_TO_PLN,400.0,cart1,cantor);
        transactionService.saveTransaction(transaction2);
        Long id2 = transaction2.getTransactionId();
        Transaction transaction3 = new Transaction(ExchangeOperation.EUR_TO_PLN,400.0,cart1,cantor);
        transactionService.saveTransaction(transaction3);
        Long id3 = transaction3.getTransactionId();
        List<Long> ids = List.of(id1,id2,id3);


        //WHEN
        List<Transaction> transactions = transactionService.getAllTransactions();

        //THEN
        assertEquals(3, transactions.size());

        //CLEAN UP
        transactionRepository.deleteAll(transactions);
        cantorRepository.delete(cantor);
        cartRepository.delete(cart1);
        accountRepository.delete(account1);
        userRepository.delete(user1);
    }
    @Test
    public void deleteTransactionsByIds(){
        //GIVEN
        Transaction transaction1 = new Transaction();
        transactionService.saveTransaction(transaction1);
        Long id1 = transaction1.getTransactionId();
        Transaction transaction2 = new Transaction();
        transactionService.saveTransaction(transaction2);
        Long id2 = transaction2.getTransactionId();
        Transaction transaction3 = new Transaction();
        transactionService.saveTransaction(transaction3);
        Long id3 = transaction3.getTransactionId();
        List<Long> ids = List.of(id1,id2,id3);
        //WHEN
        assertEquals(3,transactionRepository.findAll().size());
        transactionService.deleteTransactions(ids);
        assertEquals(0, transactionRepository.findAll().size());

    }

}

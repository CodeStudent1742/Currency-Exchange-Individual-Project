package com.albert.currency.service;

import com.albert.currency.client.NBPClient;
import com.albert.currency.controller.exceptions.ExchangeOrderNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.mapper.NBPExchangeRateMapper;
import com.albert.currency.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class ExchangeOrderTestSuite {
    @Autowired
    private ExchangeOrderService exchangeOrderService;
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
    @Autowired
    private ExchangeOrderRepository exchangeOrderRepository;

    @Test
    public void testGetExchangeOrder() throws ExchangeOrderNotFoundException {
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        cantorService.saveCantor(cantor);
        Cart cart1 = new Cart();
        cartService.saveCart(cart1);
        Account account1 = new Account(new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000));
        accountService.save(account1);
        User user1 = new User("user1");
        user1.setCart(cart1);
        user1.setAccount(account1);
        userService.saveUser(user1);
        Transaction transaction1 = new Transaction(ExchangeOperation.PLN_TO_CHF, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction1);
        Transaction transaction2 = new Transaction(ExchangeOperation.USD_TO_PLN, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction2);
        Transaction transaction3 = new Transaction(ExchangeOperation.EUR_TO_PLN, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction3);
        List<Transaction> transactions = new ArrayList<>(List.of(transaction1, transaction2, transaction3));
        ExchangeOrder exchangeOrder = new ExchangeOrder(ExchangeStatus.DONE, user1, transactions);
        exchangeOrderService.save(exchangeOrder);
        Long id = exchangeOrder.getExchangeOrderId();

        //WHEN
        ExchangeOrder testedExchangeOrder = exchangeOrderService.getExchangeOrderById(id);

        //THEN
        assertEquals(ExchangeStatus.DONE, testedExchangeOrder.getExchangeStatus());
        assertEquals(3, testedExchangeOrder.getOrderTransactions().size());

        //CLEAN UP
        exchangeOrderRepository.deleteAll();
        transactionRepository.deleteAll();
        cantorRepository.delete(cantor);
        cartRepository.delete(cart1);
        accountRepository.delete(account1);
        userRepository.delete(user1);

    }

    @Test
    public void testGetAllExchangeOrders() {
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        cantorService.saveCantor(cantor);
        Cart cart1 = new Cart();
        cartService.saveCart(cart1);
        Account account1 = new Account(new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000));
        accountService.save(account1);
        User user1 = new User("user1");
        user1.setCart(cart1);
        user1.setAccount(account1);
        userService.saveUser(user1);
        Transaction transaction1 = new Transaction(ExchangeOperation.PLN_TO_CHF, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction1);
        Transaction transaction2 = new Transaction(ExchangeOperation.USD_TO_PLN, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction2);
        Transaction transaction3 = new Transaction(ExchangeOperation.EUR_TO_PLN, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction3);
        List<Transaction> transactions1 = new ArrayList<>(List.of(transaction1, transaction2));
        List<Transaction> transactions2 = new ArrayList<>(List.of(transaction3));
        ExchangeOrder exchangeOrder1 = new ExchangeOrder(ExchangeStatus.DONE, user1, transactions1);
        exchangeOrderService.save(exchangeOrder1);
        ExchangeOrder exchangeOrder2 = new ExchangeOrder(ExchangeStatus.DONE, user1, transactions2);
        exchangeOrderService.save(exchangeOrder2);

        //WHEN
        List<ExchangeOrder> exchangeOrders = exchangeOrderService.getAllExchangeOrders();

        //THEN

        assertEquals(2, exchangeOrders.size());

        //CLEAN UP
        exchangeOrderRepository.deleteAll();
        transactionRepository.deleteAll();
        cantorRepository.delete(cantor);
        cartRepository.delete(cart1);
        accountRepository.delete(account1);
        userRepository.delete(user1);

    }

    @Test
    public void testUpdateExchangeOrder() throws ExchangeOrderNotFoundException {
        NBPExchangeRate nbpExchangeRate = nbpExchangeRateMapper.mapToNBPExchangeRate(nbpClient.getNBPRates());
        Cantor cantor = new Cantor(nbpExchangeRate);
        cantorService.saveCantor(cantor);
        Cart cart1 = new Cart();
        cartService.saveCart(cart1);
        Account account1 = new Account(new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000), new BigDecimal(1000));
        accountService.save(account1);
        User user1 = new User("user1");
        user1.setCart(cart1);
        user1.setAccount(account1);
        userService.saveUser(user1);
        Transaction transaction1 = new Transaction(ExchangeOperation.PLN_TO_CHF, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction1);
        Transaction transaction2 = new Transaction(ExchangeOperation.USD_TO_PLN, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction2);
        Transaction transaction3 = new Transaction(ExchangeOperation.EUR_TO_PLN, 400.0, cart1, cantor);
        transactionService.saveTransaction(transaction3);
        List<Transaction> transactions = new ArrayList<>(List.of(transaction1, transaction2, transaction3));
        ExchangeOrder exchangeOrder = new ExchangeOrder(ExchangeStatus.ERROR, user1, transactions);
        exchangeOrderService.save(exchangeOrder);
        Long id = exchangeOrder.getExchangeOrderId();

        //WHEN
        ExchangeOrder testedExchangeOrder = exchangeOrderService.getExchangeOrderById(id);
        testedExchangeOrder.setExchangeStatus(ExchangeStatus.DONE);
        exchangeOrderService.save(testedExchangeOrder);

        //THEN
        assertEquals(ExchangeStatus.DONE, testedExchangeOrder.getExchangeStatus());

        //CLEAN UP
        exchangeOrderRepository.deleteAll();
        transactionRepository.deleteAll();
        cantorRepository.delete(cantor);
        cartRepository.delete(cart1);
        accountRepository.delete(account1);
        userRepository.delete(user1);

    }

}

package com.albert.currency.service;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class UserTestSuite {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ExchangeOrderService exchangeOrderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ExchangeOrderRepository exchangeOrderRepository;

    @Test
    public void testGetUserById() throws UserNotFoundException {
        //GIVEN
        User user1 = new User("user1");
        userService.saveUser(user1);
        Long id1 = user1.getUserId();

        //WHEN
        User userById1 = userService.getUser(id1);

        //THEN
        assertEquals("user1",userById1.getUserName());

        //CLEAN UP
        userService.deleteUser(id1);
    }
    @Test
    public void testUpdateUser() throws UserNotFoundException {
        //GIVEN
        User user1 = new User("user1");
        userService.saveUser(user1);
        Long id1 = user1.getUserId();

        //WHEN
        User userById1 = userService.getUser(id1);
        userById1.setUserName("userNameChanged");
        userService.saveUser(userById1);

        //THEN
        assertEquals("userNameChanged",userById1.getUserName());

        //CLEAN UP
        userService.deleteUser(id1);
    }
    @Test
    public void testGetAllUsers() {
        //GIVEN
        Account account1 = new Account();
        Account account2= new Account();
        Account account3 = new Account();
        accountService.save(account1);
        accountService.save(account2);
        accountService.save(account3);
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        Cart cart3 = new Cart();
        cartService.saveCart(cart1);
        cartService.saveCart(cart2);
        cartService.saveCart(cart3);
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");
        user1.setAccount(account1);
        user1.setCart(cart1);
        user2.setAccount(account2);
        user2.setCart(cart2);
        user3.setAccount(account3);
        user3.setCart(cart3);
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        //WHEN
        List<User> users = userService.getAllUsers();

        //THEN
        assertEquals(3,users.size());

        //CLEAN UP
        userRepository.deleteAll();
        accountRepository.deleteAll();
        cartRepository.deleteAll();
    }
    @Test
    public void testGetUserTransactions() throws UserNotFoundException {
        //GIVEN
        Account account1 = new Account();
        accountService.save(account1);
        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();
        Transaction transaction3 = new Transaction();
        transactionService.saveTransaction(transaction1);
        transactionService.saveTransaction(transaction2);
        transactionService.saveTransaction(transaction3);
        Cart cart1 = new Cart();
        cart1.setTransactions(List.of(transaction1,transaction2));
        cartService.saveCart(cart1);
        ExchangeOrder exchangeOrder1 = new ExchangeOrder();
        exchangeOrder1.setOrderTransactions(List.of(transaction3));
        exchangeOrderService.save(exchangeOrder1);
        User user1 = new User("user1");
        user1.setAccount(account1);
        user1.setCart(cart1);
        user1.setExchangeOrders(List.of(exchangeOrder1));
        userService.saveUser(user1);
        Long id1 = user1.getUserId();

        //WHEN
        List<Transaction> transactions = userService.getAllTransactions(id1);

        //THEN
        assertEquals(3,transactions.size());

        //CLEAN UP
        userRepository.deleteAll();
        accountRepository.deleteAll();
        cartRepository.deleteAll();
        transactionRepository.deleteAll();
        exchangeOrderRepository.deleteAll();
    }

}

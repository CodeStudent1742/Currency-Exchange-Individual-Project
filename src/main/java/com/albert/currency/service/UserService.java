package com.albert.currency.service;

import com.albert.currency.controller.exceptions.CartBalanceNotFoundException;
import com.albert.currency.controller.exceptions.UserAlreadyExistsException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;

    private final UserRepository userRepository;
    private final CartBalanceRepository cartBalanceRepository;
    private final ExchangeOrderRepository exchangeOrderRepository;
    private final TransactionRepository transactionRepository;


    public List<Transaction> getAllTransactions(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Transaction> transactionsInCart = user.getCart().getTransactions();
        List<Transaction> transactionsFromOrders = user.getExchangeOrders().stream().map(ExchangeOrder::getOrderTransactions).flatMap(List::stream).toList();
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(transactionsInCart);
        allTransactions.addAll(transactionsFromOrders);
        return allTransactions;
    }

    public User getUser(long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void saveUser(final User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }

    public void deleteUserByUserName(final String userName) throws UserNotFoundException, CartBalanceNotFoundException {
        User user = userRepository.findUserByUserName(userName).orElseThrow(UserNotFoundException::new);
        transactionsDelete(user);
        exchangeOrderDelete(user);
        Long id = user.getCart().getCartBalance().getCartBalanceId();
        CartBalance cartBalance = cartBalanceRepository.findById(id).orElseThrow(CartBalanceNotFoundException::new);
        user.getCart().setCartBalance(null);
        cartRepository.save(user.getCart());
        cartRepository.delete(user.getCart());
        cartBalanceRepository.delete(cartBalance);
        accountRepository.delete(user.getAccount());
        userRepository.deleteById(user.getUserId());
    }



    public void createUserFromNewUser(final User user) throws UserAlreadyExistsException {
        if (userRepository.findUserByUserName(user.getUserName()).isEmpty()) {
            Cart cart = new Cart();
            Account account = new Account();
            CartBalance cartBalance = new CartBalance();
            cartBalanceRepository.save(cartBalance);
            cart.setCartBalance(cartBalance);
            cartRepository.save(cart);
            accountRepository.save(account);
            user.setCart(cart);
            user.setAccount(account);
            userRepository.save(user);
            cart.setUser(user);
            account.setUser(user);
            cartRepository.save(cart);
            accountRepository.save(account);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    public List<ExchangeOrder> getAllOrders(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getExchangeOrders();
    }
    private void transactionsDelete(User user) throws UserNotFoundException {
        List<Transaction> transactions = getAllTransactions(user.getUserId());
        for(Transaction transaction: transactions){
            transaction.setCart(null);
            transaction.setExchangeOrder(null);
            transaction.setCantor(null);
            transactionRepository.save(transaction);
        }
        transactionRepository.deleteAllById((getAllTransactions(user.getUserId())).stream()
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList()));

    }
    private void exchangeOrderDelete(User user) {
        List<ExchangeOrder> exchangeOrders = user.getExchangeOrders();
        for(ExchangeOrder exchangeOrder: exchangeOrders){
            exchangeOrder.setUser(null);
            exchangeOrderRepository.save(exchangeOrder);
        }
        exchangeOrderRepository.deleteAllById(user.getExchangeOrders().stream()
                .map(ExchangeOrder::getExchangeOrderId)
                .collect(Collectors.toList()));
    }
}


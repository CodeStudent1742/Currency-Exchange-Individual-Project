package com.albert.currency.service;

import com.albert.currency.controller.exceptions.UserAlreadyExistsException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.repository.AccountRepository;
import com.albert.currency.repository.CartBalanceRepository;
import com.albert.currency.repository.CartRepository;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;

    private final UserRepository userRepository;
    private final CartBalanceRepository cartBalanceRepository;


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

    public void deleteUserByUserName(final String userName) throws UserNotFoundException {
        User user = userRepository.findUserByUserName(userName).orElseThrow(UserNotFoundException::new);
//        cartBalanceRepository.delete(user.getCart().getCartBalance());
        cartRepository.delete(user.getCart());
        accountRepository.delete(user.getAccount());
        userRepository.delete(user);
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
            cart.setUser(user);
            account.setUser(user);
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    public List<ExchangeOrder> getAllOrders(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getExchangeOrders();
    }
}


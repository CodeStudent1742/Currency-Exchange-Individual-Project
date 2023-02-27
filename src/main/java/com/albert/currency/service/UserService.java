package com.albert.currency.service;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.User;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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
         userRepository.delete(user);
    }
}

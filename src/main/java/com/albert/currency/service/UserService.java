package com.albert.currency.service;

import com.albert.currency.controller.exceptions.AccountNotFoundException;
import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.UserDto;
import com.albert.currency.mapper.AccountRecordMapper;
import com.albert.currency.mapper.ExchangeOrderMapper;
import com.albert.currency.repository.AccountRepository;
import com.albert.currency.repository.CartRepository;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final AccountRepository accountRepository;
    private final AccountRecordMapper accountRecordMapper;
    private final ExchangeOrderMapper exchangeOrderMapper;

    public User findById(long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

    }

    public void saveUser(final User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(final UserDto userDto) throws UserNotFoundException, CartNotFoundException, AccountNotFoundException {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(UserNotFoundException::new);
        user.setUserName(userDto.getUserName());
        user.setCart(cartRepository.findById(userDto.getCartId()).orElseThrow(CartNotFoundException::new));
        user.setAccount(accountRepository.findById(userDto.getAccountId()).orElseThrow(AccountNotFoundException::new));
        user.setAccountRecords(accountRecordMapper.mapToAccountRecords(userDto.getAccountStatusCopyIds()));
        user.setExchangeOrders(exchangeOrderMapper.mapToExchangeOrdersById(userDto.getExchangeOrderIds()));
        userRepository.save(user);
        return user;
    }

    public void deleteUser(final Long userId) {
        userRepository.deleteById(userId);
    }
}

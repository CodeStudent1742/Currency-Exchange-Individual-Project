package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.CartBalanceNotFoundException;
import com.albert.currency.controller.exceptions.UserAlreadyExistsException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.CartBalance;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.ExchangeOrderDto;
import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.domain.dto.UserDto;
import com.albert.currency.mapper.ExchangeOrderMapper;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.mapper.UserMapper;
import com.albert.currency.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/user")
public class UserController {
    private final UserService userService;
    private final CartBalanceService cartBalanceService;
    private final CartService cartService;
    private final AccountService accountService;
    private final ExchangeOrderService exchangeOrderService;
    private final TransactionService transactionService;
    private final ExchangeOrderMapper exchangeOrderMapper;
    private final UserMapper userMapper;
    private final TransactionMapper transactionMapper;



    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUsersDto(users));
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        return ResponseEntity.ok((userMapper.mapToUserDto(user)));
    }

    @GetMapping(value = "{userId}/transactions")
    public ResponseEntity<List<TransactionDto>> getUserTransactions(@PathVariable Long userId) throws UserNotFoundException {
        List<Transaction> allTransactions = userService.getAllTransactions(userId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionsDto(allTransactions));
    }
    @GetMapping(value = "{userId}/exchangeOrders")
    public ResponseEntity<List<ExchangeOrderDto>> getUserExchangeOrders(@PathVariable Long userId) throws UserNotFoundException {
        List<ExchangeOrder> allExchangeOrders = userService.getAllOrders(userId);
        return ResponseEntity.ok(exchangeOrderMapper.mapToExchangeOrdersDto(allExchangeOrders));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody NewUserDto newUserDto) throws UserAlreadyExistsException {
        userService.createUserFromNewUser(userMapper.mapToUser(newUserDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userMapper.mapToUser(userDto);
        userService.saveUser(user);
        return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }

    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        CartBalance cartBalance = user.getCart().getCartBalance();
        cartService.deleteCart(user.getCart().getCartId());
        cartBalanceService.deleteCartBalance(cartBalance);
        accountService.delete(user.getAccount().getAccountId());
        transactionService.deleteTransactions(exchangeOrderMapper.mapToTransactionsId(userService.getAllTransactions(userId)));
        exchangeOrderService.deleteAllUserExchangeOrders(userMapper.mapToExchangeOrderIds(user.getExchangeOrders()));
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserByUserName(@RequestParam String userName) throws UserNotFoundException, CartBalanceNotFoundException {
        userService.deleteUserByUserName(userName);
        return ResponseEntity.ok().build();
    }
}

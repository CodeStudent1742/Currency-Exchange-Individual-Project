package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.domain.dto.UserDto;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.mapper.UserMapper;
import com.albert.currency.service.UserService;
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
    private final UserMapper userMapper;
    private final TransactionMapper transactionMapper;


    @GetMapping
    public ResponseEntity <List<UserDto>> getAllUsers(){
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
        User user = userService.getUser(userId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionsDtos(user.getCart().getTransactions()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody NewUserDto newUserDto) {
        User user = userMapper.mapToUser(newUserDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userMapper.mapToUser(userDto);
        userService.saveUser(user);
       return ResponseEntity.ok(userMapper.mapToUserDto(user));
    }
    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}

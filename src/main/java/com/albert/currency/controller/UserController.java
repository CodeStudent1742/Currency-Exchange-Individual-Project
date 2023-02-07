package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.AccountNotFoundException;
import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.UserDto;
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

    @GetMapping
    public ResponseEntity <List<UserDto>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.mapToUsersDto(users));
    }
    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) throws UserNotFoundException {
        User user = userService.findById(userId);
        return ResponseEntity.ok((userMapper.mapToUserDto(user)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody NewUserDto newUserDto) {
        User user = userMapper.mapToUser(newUserDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException, CartNotFoundException, AccountNotFoundException {
        User updatedUser = userService.updateUser(userDto);
       return ResponseEntity.ok(userMapper.mapToUserDto(updatedUser));
    }
    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@RequestParam  Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}

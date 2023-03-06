package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.google.gson.Gson;
import com.albert.currency.domain.Account;
import com.albert.currency.domain.Cart;
import com.albert.currency.domain.User;

import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.UserDto;
import com.albert.currency.mapper.ExchangeOrderMapper;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.mapper.UserMapper;
import com.albert.currency.service.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CartBalanceService cartBalanceService;
    @MockBean
    private CartService cartService;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private ExchangeOrderService exchangeOrderService;
    @MockBean
    private ExchangeOrderMapper exchangeOrderMapper;
    @MockBean
    private TransactionMapper transactionMapper;
    @MockBean
    private UserMapper userMapper;


    @Test
    public void shouldFetchEmptyList() throws Exception {
        //GIVEN
        when(userService.getAllUsers()).thenReturn(List.of());

        //WHEN&THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldFetchUsers() throws Exception {
        //GIVEN
        User user1 = new User();
        User user2 = new User();
        List<User> users = List.of(user1, user2);
        UserDto userDto1 = new UserDto();
        UserDto userDto2 = new UserDto();
        List<UserDto> usersDto = List.of(userDto1, userDto2);
        when(userService.getAllUsers()).thenReturn(users);
        when(userMapper.mapToUsersDto(users)).thenReturn(usersDto);
        //WHEN&THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void shouldFetchUserById() throws Exception {
        //GIVEN
        Cart cart = new Cart(1L, null, new ArrayList<>());
        Account account = new Account(1L, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), null);
        User user = new User(1L, cart, account, new ArrayList<>(), "user1");
        UserDto userDto = new UserDto(1L, 1L, 1L, new ArrayList<>(), "user1");
        when(userService.getUser(1)).thenReturn(user);
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        //WHEN&THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/user/{userId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                //Cart Fields
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is("user1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartId", Matchers.is(1)));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        //GIVEN
        Cart cart = new Cart(1L, null, new ArrayList<>());
        Account account = new Account(1L, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), null);
        User user = new User(1L, cart, account, new ArrayList<>(), "user1");
        NewUserDto newUserDto = new NewUserDto("user1");
        userMapper.mapToUser(newUserDto);
        when(userMapper.mapToUser(newUserDto)).thenReturn(user);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(newUserDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
    @Test
    public void shouldUpdateUser() throws Exception {
        //GIVEN
        Cart cart = new Cart(1L, null, new ArrayList<>());
        Account account = new Account(1L, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), null);
        User user = new User(1L, cart, account, new ArrayList<>(), "user1");
        UserDto userDto = new UserDto(1L, 1L, 1L, new ArrayList<>(), "user1");
        when(userMapper.mapToUser(userDto)).thenReturn(user);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}


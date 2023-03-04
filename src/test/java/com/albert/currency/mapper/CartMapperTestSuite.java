package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.CartDto;
import com.albert.currency.domain.dto.NewCartDto;
import com.albert.currency.repository.CartBalanceRepository;
import com.albert.currency.repository.CartRepository;
import com.albert.currency.repository.TransactionRepository;
import com.albert.currency.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class CartMapperTestSuite {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartBalanceRepository cartBalanceRepository;

    @Test
    public void testMapToCartDto() {
        //GIVEN
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        Transaction transaction1 = new Transaction(1L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        Transaction transaction2 = new Transaction(2L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        List<Transaction> transactions = List.of(transaction1, transaction2);

        User user = new User();
        user.setUserId(1L);
        Cart cart = new Cart(1L, user, transactions);
        CartBalance cartBalance = new CartBalance(1L, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), cart);
        cart.setCartBalance(cartBalance);


        //WHEN
        CartDto cartDto = cartMapper.mapToCartDto(cart);
        //THEN
        assertEquals(1L, cartDto.getCartId());
        assertEquals(2, cartDto.getTransactions().size());
        assertEquals(1L, cartDto.getUserId());
        assertEquals(1L, cartDto.getCartBalanceId());
    }

    @Test
    public void testMapToCartsDto() {
        //GIVEN
        User user = new User();
        user.setUserId(1L);
        User user2 = new User();
        user2.setUserId(2L);
        Cart cart = new Cart(1L, user, new ArrayList<>());
        CartBalance cartBalance = new CartBalance(1L, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), cart);
        cart.setCartBalance(cartBalance);
        Cart cart2 = new Cart(2L, user2, new ArrayList<>());
        CartBalance cartBalance2 = new CartBalance(1L, BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0), cart);
        cart2.setCartBalance(cartBalance2);
        List<Cart> carts = List.of(cart, cart2);
        //WHEN
        List<CartDto> cartDtos = cartMapper.mapToCartsDto(carts);
        //THEN
        assertEquals(2, cartDtos.size());
    }

    @Test
    public void testMapToCart() throws UserNotFoundException {
        //GIVEN
        User user = new User("user1");
        userRepository.save(user);
        Long userId = user.getUserId();
        NewCartDto newCartDto = new NewCartDto(userId);

        //WHEN
        Cart cartTested = cartMapper.mapToCart(newCartDto);
        //THEN
        assertNotEquals(null,cartTested.getUser());
        //CLEAN UP
        transactionRepository.deleteAll();
        cartRepository.deleteAll();
        cartBalanceRepository.deleteAll();
        userRepository.deleteAll();
    }
}

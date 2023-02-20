package com.albert.currency.service;

import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.domain.Account;
import com.albert.currency.domain.Cart;
import com.albert.currency.domain.User;
import com.albert.currency.repository.AccountRepository;
import com.albert.currency.repository.CartRepository;
import com.albert.currency.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class CartTestSuite {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testGetCart() throws CartNotFoundException {
        //GIVEN
        User user1 = new User();
        user1.setUserName("user1");
        userService.saveUser(user1);
        Cart cart1 = new Cart();
        cart1.setUser(user1);
        cartService.saveCart(cart1);

        Long id = cart1.getCartId();

        //WHEN
        Cart cartTested = cartService.getCart(id);

        //THEN
        assertEquals("user1", cartTested.getUser().getUserName());

        //CLEAN UP
        userRepository.deleteAll();
        accountRepository.deleteAll();
        cartRepository.deleteAll();

    }
    @Test
    public void testGetAllCarts() throws CartNotFoundException {
        //GIVEN
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        Cart cart3 = new Cart();
        cartService.saveCart(cart1);
        cartService.saveCart(cart2);
        cartService.saveCart(cart3);


        //WHEN
        List<Cart> carts = cartService.getAllCarts();

        //THEN
        assertEquals(3, carts.size());

        //CLEAN UP
        cartRepository.deleteAll();

    }

}

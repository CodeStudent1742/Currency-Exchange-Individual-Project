package com.albert.currency.domain;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class CartBalanceTestSuite {

    @Test
    public void testCalculateBalance() {
        //GIVEN
        User user = new User();
        Cantor cantor = new Cantor();
        cantor.setSellingRateCHF(3.0);
        Transaction transaction1 = new Transaction(ExchangeOperation.PLN_TO_CHF, 400.0, null, cantor);
        List<Transaction> transactions = List.of(transaction1);
        Cart cart = new Cart(1L, user, transactions);
        CartBalance cartBalance = new CartBalance();
        cart.setCartBalance(cartBalance);
        cartBalance.setCart(cart);

        //WHEN
        cartBalance.calculateCartBalance();

        //THEN
        assertEquals(1200.0, cartBalance.getBalancePLN().doubleValue());
    }
    @Test
    public void testClearBalance(){
        //GIVEN
        User user = new User();
        Cantor cantor = new Cantor();
        cantor.setSellingRateCHF(3.0);
        Transaction transaction1 = new Transaction(ExchangeOperation.PLN_TO_CHF, 400.0, null, cantor);
        List<Transaction> transactions = List.of(transaction1);
        Cart cart = new Cart(1L, user, transactions);
        CartBalance cartBalance = new CartBalance();
        cart.setCartBalance(cartBalance);
        cartBalance.setCart(cart);
        cartBalance.calculateCartBalance();
        //WHEN
        cartBalance.clearBalance();
        //THEN
        assertEquals(0.0, cartBalance.getBalancePLN().doubleValue());
    }
}

package com.albert.currency.controller.facade;

import com.albert.currency.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CartControllerFacadeMethodsTest {

    @Autowired
    CartControllerFacade cartControllerFacade;

    @Test
    public void testIsSufficient() {


        //GIVEN
        Cart cart = new Cart();
        Account account = new Account(BigDecimal.valueOf(20000), BigDecimal.valueOf(2000), BigDecimal.valueOf(2000), BigDecimal.valueOf(2000), BigDecimal.valueOf(2000));
        CartBalance cartBalance = new CartBalance(1L, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), BigDecimal.valueOf(1000), cart);
        boolean result = cartControllerFacade.isSufficientFunds(cartBalance,account);
        //WHEN&THEN
        assertTrue(result);

    }
//    @Test
//    public void testConvertToExchangeOrder() {
//
//        //GIVEN
//        Cart cart = new Cart();
//        Cantor cantor = new Cantor(1L, LocalDate.now(),3.0,3.0,3.0,3.0,3.0,3.0,3.0,3.0,null);
//        Transaction transaction = new Transaction(ExchangeOperation.PLN_TO_CHF,400.0,cart,cantor);
//        cart.setTransactions(List.of(transaction));
//
//        //WHEN
//        cartControllerFacade.convertCartToExchangeOrder(cart);
//
//        // THEN
//        assertEquals(null,transaction.getCart());
//
//    }

}


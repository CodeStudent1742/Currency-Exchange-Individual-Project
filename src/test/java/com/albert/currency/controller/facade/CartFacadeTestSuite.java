package com.albert.currency.controller.facade;

import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.CartDto;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.mapper.CartMapper;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartFacadeTestSuite {

    @InjectMocks
    private CartControllerFacade cartControllerFacade;
    @Mock
    private CartService cartService;
    @Mock
    private CartMapper cartMapper;
    @Mock
    private TransactionMapper transactionMapper;

    @Test
    public void shouldFetchCarts() {
        //GIVEN
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        Cart cart3 = new Cart();
        List<Cart> carts = new ArrayList<>(List.of(cart1, cart2, cart3));
        CartDto cartDto1 = new CartDto();
        CartDto cartDto2 = new CartDto();
        CartDto cartDto3 = new CartDto();
        List<CartDto> cartDtos = new ArrayList<>(List.of(cartDto1, cartDto2, cartDto3));
        when(cartService.getAllCarts()).thenReturn(carts);
        when(cartMapper.mapToCartsDto(carts)).thenReturn(cartDtos);
        //WHEN
        List<CartDto> cartDtoResults = cartControllerFacade.fetchCarts();

        //THEN
        assertNotNull(cartDtoResults);
        assertEquals(3, cartDtoResults.size());
    }

    @Test
    public void shouldFetchCartById() throws CartNotFoundException {
        //GIVEN
        CartBalance cartBalance = new CartBalance();
        User user = new User();
        user.setUserId(1L);
        Cart cart1 = new Cart(1L, user, new ArrayList<>(), cartBalance);
        CartDto cartDto1 = new CartDto(1L, 1L, new ArrayList<>());

        when(cartService.getCart(1L)).thenReturn(cart1);
        when(cartMapper.mapToCartDto(cart1)).thenReturn(cartDto1);
        //WHEN
        CartDto recevicedCartDto = cartControllerFacade.fetchCart(1L);

        //THEN
        assertNotNull(recevicedCartDto);
    }

    @Test
    public void shouldFetchAllTransactionsInCart() throws CartNotFoundException {
        //GIVEN
        CartBalance cartBalance = new CartBalance();
        User user = new User();
        user.setUserId(1L);
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        Cart cart1 = new Cart(1L, user, new ArrayList<>(), cartBalance);
        Transaction transaction1 = new Transaction(1L, ExchangeOperation.USD_TO_PLN, 400.0, cart1, null, cantor);
        Transaction transaction2 = new Transaction(2L, ExchangeOperation.USD_TO_PLN, 400.0, cart1, null, cantor);
        Transaction transaction3 = new Transaction(3L, ExchangeOperation.USD_TO_PLN, 400.0, cart1, null, cantor);
        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);
        cart1.setTransactions(transactions);
        TransactionDto transactionDto1 = new TransactionDto(1L, ExchangeOperation.USD_TO_PLN, 400.0, 1200.0, 1L, null, 1L);
        TransactionDto transactionDto2 = new TransactionDto(2L, ExchangeOperation.USD_TO_PLN, 400.0, 1200.0, 1L, null, 1L);
        TransactionDto transactionDto3 = new TransactionDto(3L, ExchangeOperation.USD_TO_PLN, 400.0, 1200.0, 1L, null, 1L);
        List<TransactionDto> tractionsDto = List.of(transactionDto3, transactionDto2, transactionDto1);

        when(cartService.getCart(1L)).thenReturn(cart1);
        when(transactionMapper.mapToTransactionsDto(cart1.getTransactions())).thenReturn(tractionsDto);
        //WHEN
        List<TransactionDto> receivedTransactions = cartControllerFacade.fetchAllTransactionsInCart(1L);

        //THEN
        assertNotNull(receivedTransactions);
        assertEquals(3, receivedTransactions.size());
    }

}

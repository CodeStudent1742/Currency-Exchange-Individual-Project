package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TransactionMapperTestSuite {

    @Autowired
    TransactionMapper transactionMapper;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CantorRepository cantorRepository;

    @Test
    public void mapToTransactionDto() {
        //GIVEN
        Cantor cantor = new Cantor();
        cantor.setSellingRateCHF(3.0);
        Cart cart1 = new Cart();
        cart1.setCartId(1L);
        User user1 = new User("user1");
        user1.setCart(cart1);
        Transaction transaction1 = new Transaction(1L, ExchangeOperation.PLN_TO_CHF, 400.0, cart1, null, cantor);

        //WHEN
        TransactionDto transactionDto = transactionMapper.mapToTransactionDto(transaction1);

        //THEN
        assertEquals(1L,transactionDto.getTransactionId());
        assertEquals(ExchangeOperation.PLN_TO_CHF,transactionDto.getExchangeOperation());
        assertEquals(400.0,transactionDto.getTransactionVolume());
        assertEquals(1200.0,transactionDto.getTransactionValue());

    }
    @Test
    public void mapToTransactionsDto(){
        //GIVEN
        Cantor cantor = new Cantor();
        cantor.setSellingRateCHF(3.0);
        Cart cart1 = new Cart();
        cart1.setCartId(1L);
        User user1 = new User("user1");
        user1.setCart(cart1);
        Transaction transaction1 = new Transaction(1L, ExchangeOperation.PLN_TO_CHF, 400.0, cart1, null, cantor);
        Transaction transaction2 = new Transaction(1L, ExchangeOperation.PLN_TO_CHF, 400.0, cart1, null, cantor);
        Transaction transaction3 = new Transaction(1L, ExchangeOperation.PLN_TO_CHF, 400.0, cart1, null, cantor);
        List<Transaction> transactionList = List.of(transaction3,transaction2,transaction1);
        //WHEN
        List<TransactionDto> transactionDtos = transactionMapper.mapToTransactionsDto(transactionList);

        //THEN
        assertEquals(3,transactionDtos.size());
    }
    @Test
    public void mapToTransaction() throws CartNotFoundException {
        //GIVEN
        Account account1 = new Account();
        accountRepository.save(account1);
        Cart cart1 = new Cart();
        cartRepository.save(cart1);
        Long cartId = cart1.getCartId();
        User user1 = new User("user1");
        user1.setAccount(account1);
        user1.setCart(cart1);
        userRepository.save(user1);
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        cantorRepository.save(cantor);
        Long cantorId = cantor.getCantorRatesId();
        TransactionDto transactionDto= new TransactionDto(ExchangeOperation.USD_TO_PLN, 400.0, cartId,cantorId);
        //WHEN
        Transaction transaction = transactionMapper.mapToTransaction(transactionDto);


        //THEN
        assertEquals(ExchangeOperation.USD_TO_PLN,transaction.getExchangeOperation());
        assertEquals(400.0,transaction.getTransactionVolume().doubleValue());
        assertEquals(cartId,transaction.getCart().getCartId());
        assertEquals(cantorId,transaction.getCantor().getCantorRatesId());

        //CLEAN UP
        userRepository.deleteAll();
        accountRepository.deleteAll();
        cartRepository.deleteAll();
        transactionRepository.deleteAll();
    }

}

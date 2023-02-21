package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.ExchangeOrderDto;
import com.albert.currency.domain.dto.NewExchangeOrderDto;
import com.albert.currency.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ExchangeOrderMapperTestSuite {
    @Autowired
    ExchangeOrderMapper exchangeOrderMapper;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CantorRepository cantorRepository;
    @Autowired
    private ExchangeOrderRepository exchangeOrderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testMapToExchangeOrderDto() {
        //GIVEN
        User user1 = new User("user1");
        user1.setUserId(1L);
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        Transaction transaction1 = new Transaction(1L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        Transaction transaction2 = new Transaction(2L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        Transaction transaction3 = new Transaction(3L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        List<Transaction> orderTransactions1 = List.of(transaction1,transaction2,transaction3);
        ExchangeOrder exchangeOrder = new ExchangeOrder(1L, LocalDate.of(2023, 2, 20), ExchangeStatus.DONE,user1,orderTransactions1);

        //WHEN
        ExchangeOrderDto exchangeOrderDto = exchangeOrderMapper.mapToExchangeOrderDto(exchangeOrder);

        //THEN
        assertEquals(1L,exchangeOrderDto.getOrderId());
        assertEquals(LocalDate.of(2023, 2, 20),exchangeOrderDto.getExchangeDate());
        assertEquals(ExchangeStatus.DONE,exchangeOrderDto.getExchangeStatus());
        assertEquals(1L,exchangeOrderDto.getUserId());
        assertEquals(3,exchangeOrderDto.getOrderTransactionIds().size());
    }
    @Test
    public void testMapToExchangeOrdersDto() {
        //GIVEN
        User user1 = new User("user1");
        user1.setUserId(1L);
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        Transaction transaction1 = new Transaction(1L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        Transaction transaction2 = new Transaction(2L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        Transaction transaction3 = new Transaction(3L, ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        List<Transaction> orderTransactions1 = List.of(transaction1,transaction2);
        ExchangeOrder exchangeOrder = new ExchangeOrder(1L, LocalDate.of(2023, 2, 20), ExchangeStatus.DONE,user1,orderTransactions1);
        ExchangeOrder exchangeOrder2 = new ExchangeOrder(1L, LocalDate.of(2023, 2, 20), ExchangeStatus.DONE,user1,List.of(transaction3));
        List<ExchangeOrder> exchangeOrders = List.of(exchangeOrder2,exchangeOrder);
        //WHEN
        List<ExchangeOrderDto> exchangeOrdersDto = exchangeOrderMapper.mapToExchangeOrdersDto(exchangeOrders);

        //THEN
        assertEquals(2,exchangeOrdersDto.size());
        assertEquals(1L,exchangeOrdersDto.get(0).getOrderId());
    }
    @Test
    public void testMapToNewExchangeOrder() throws UserNotFoundException {
        //GIVEN
        Account account1 = new Account();
        accountRepository.save(account1);
        Cart cart1 = new Cart();
        cartRepository.save(cart1);
        User user1 = new User("user1");
        user1.setAccount(account1);
        user1.setCart(cart1);
        userRepository.save(user1);
        Long userId = user1.getUserId();
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        cantorRepository.save(cantor);
        Transaction transaction1 = new Transaction( ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        transactionRepository.save(transaction1);
        Transaction transaction2 = new Transaction( ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        transactionRepository.save(transaction2);
        Transaction transaction3 = new Transaction( ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        transactionRepository.save(transaction3);
        Long id1 = transaction1.getTransactionId();
        Long id2 = transaction2.getTransactionId();
        Long id3 = transaction3.getTransactionId();
        List<Long> transactionIds = List.of(id1,id2,id3);
        NewExchangeOrderDto newExchangeOrderDto = new NewExchangeOrderDto( LocalDate.of(2023, 2, 20), ExchangeStatus.DONE,userId,transactionIds);

        //WHEN
       ExchangeOrder exchangeOrder = exchangeOrderMapper.mapToNewExchangeOrder(newExchangeOrderDto);

        //THEN
        assertEquals(LocalDate.of(2023, 2, 20),exchangeOrder.getExchangeDate());
        assertEquals(ExchangeStatus.DONE,exchangeOrder.getExchangeStatus());
        assertEquals(userId,exchangeOrder.getUser().getUserId());
        assertEquals(3,exchangeOrder.getOrderTransactions().size());

        //CLEAN UP
        exchangeOrderRepository.deleteAll();
        userRepository.deleteAll();
        accountRepository.deleteAll();
        cartRepository.deleteAll();
        transactionRepository.deleteAll();

    }
}

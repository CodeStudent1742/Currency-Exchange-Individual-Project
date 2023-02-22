package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.UserDto;
import com.albert.currency.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTestSuite {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private ExchangeOrderRepository exchangeOrderRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CantorRepository cantorRepository;

    @Test
    public void testMapToUserDto(){
        //GIVEN
        Cart cart1 = new Cart();
        Account account1 = new Account();
        User user = new User(1L,cart1,account1,null,"user1");

        //WHEN
         UserDto userDto = userMapper.mapToUserDto(user);

        //THEN
        assertEquals(1L,userDto.getUserId());
        assertEquals("user1", userDto.getUserName());
    }
    @Test
    public void testMapToUserFromNewUserDto (){
        //GIVEN
        NewUserDto newUserDto = new NewUserDto("user1");
        //WHEN
        User user = userMapper.mapToUser(newUserDto);

        //THEN
        assertEquals("user1",user.getUserName());

    }
    @Test
    public void testMapToUsersDto(){
        //GIVEN
        Cart cart1 = new Cart();
        Account account1 = new Account();
        User user = new User(1L,cart1,account1,null,"user1");
        Cart cart2 = new Cart();
        Account account2 = new Account();
        User user2 = new User(1L,cart1,account1,null,"user1");
        List<User> users =List.of(user,user2);
        //WHEN
        List<UserDto> usersDto = userMapper.mapToUsersDto(users);

        //THEN
        assertEquals(2,usersDto.size());

    }
    @Test
    public void testMapToUser() throws UserNotFoundException {
        //GIVEN
        Account account1 = new Account();
        accountRepository.save(account1);
        Long accountId = account1.getAccountId();
        Cart cart1 = new Cart();
        cartRepository.save(cart1);
        Long cartId = cart1.getCartId();
        User user1 = new User("user1");
        user1.setAccount(account1);
        user1.setCart(cart1);
        userRepository.save(user1);
        Long userId = user1.getUserId();
        Cantor cantor = new Cantor();
        cantor.setPurchaseRateUSD(4.0);
        cantorRepository.save(cantor);
        Transaction transaction1 = new Transaction( ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        List<Transaction> orderTransactions1 = List.of(transaction1);
        ExchangeOrder exchangeOrder = new ExchangeOrder( LocalDate.of(2023, 2, 20), ExchangeStatus.DONE,user1,orderTransactions1);
        exchangeOrderRepository.save(exchangeOrder);
        Transaction transaction2 = new Transaction( ExchangeOperation.USD_TO_PLN, 400.0, null, null, cantor);
        List<Transaction> orderTransactions2 = List.of(transaction2);
        ExchangeOrder exchangeOrder2 = new ExchangeOrder( LocalDate.of(2023, 2, 21), ExchangeStatus.DONE,user1,orderTransactions2);
        exchangeOrderRepository.save(exchangeOrder2);
        Long id1 = exchangeOrder.getExchangeOrderId();
        Long id2 = exchangeOrder2.getExchangeOrderId();
        List<Long> exchangeOrderIds = List.of(id1,id2);
        UserDto userDto = new UserDto(userId,cartId,accountId,exchangeOrderIds,"user1");

        //WHEN
        User user = userMapper.mapToUser(userDto);

        //THEN
        assertEquals("user1",user.getUserName());
        assertEquals(2, exchangeOrderIds.size());

        //CLEAN UP
        userRepository.deleteAll();
        accountRepository.deleteAll();
        exchangeOrderRepository.deleteAll();
        transactionRepository.deleteAll();
        cartRepository.deleteAll();
    }

}

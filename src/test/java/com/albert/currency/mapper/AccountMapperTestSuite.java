package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.Account;
import com.albert.currency.domain.Cart;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.AccountDto;
import com.albert.currency.domain.dto.NewAccountDto;
import com.albert.currency.repository.AccountRepository;
import com.albert.currency.repository.CartRepository;
import com.albert.currency.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountMapperTestSuite {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    CartRepository cartRepository;

    @Test
    public void testMapToAccount() throws UserNotFoundException {
        //GIVEN
        Account account1 = new Account();
        accountRepository.save(account1);
        Cart cart1 = new Cart();
        cartRepository.save(cart1);
        User user1 = new User("user1");
        user1.setAccount(account1);
        user1.setCart(cart1);
        userRepository.save(user1);
        Long id = user1.getUserId();
        NewAccountDto newAccountDto = new NewAccountDto(id);

        //WHEN
        Account account = accountMapper.mapToAccount(newAccountDto);
        //THEN
        assertEquals(id, account.getUser().getUserId());
        //CLEAN UP
        userRepository.deleteAll();
        accountRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void testMapToAccountDto() {
        //GIVEN
        User user1 = new User("user1");
        user1.setUserId(1L);
        Account account = new Account(1L, new BigDecimal(10), new BigDecimal(20), new BigDecimal(30), new BigDecimal(40), new BigDecimal(50), user1);
        //WHEN
        accountMapper.mapToAccountDto(account);
        //THEN
        assertEquals(1L, account.getAccountId());
        assertEquals(new BigDecimal(10), account.getBalancePLN());
        assertEquals(new BigDecimal(20), account.getBalanceEUR());
        assertEquals(new BigDecimal(30), account.getBalanceUSD());
        assertEquals(new BigDecimal(40), account.getBalanceCHF());
        assertEquals(new BigDecimal(50), account.getBalanceGBP());
        assertEquals(1L, account.getUser().getUserId());
    }

    @Test
    public void testMapToAccountsDto() {
        //GIVEN
        User user1 = new User("user1");
        user1.setUserId(1L);
        Account account = new Account(1L, new BigDecimal(10), new BigDecimal(20), new BigDecimal(30), new BigDecimal(40), new BigDecimal(50), user1);
        User user2 = new User("user1");
        user2.setUserId(2L);
        Account account2 = new Account(1L, new BigDecimal(10), new BigDecimal(20), new BigDecimal(30), new BigDecimal(40), new BigDecimal(50), user1);
        List<Account> accounts = List.of(account,account2);
        //WHEN
        List<AccountDto> accountDtos =accountMapper.mapToAccountsDto(accounts);
        //THEN
        assertEquals(2,accountDtos.size());
    }
}

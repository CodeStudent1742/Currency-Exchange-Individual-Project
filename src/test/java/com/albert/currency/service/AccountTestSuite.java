package com.albert.currency.service;

import com.albert.currency.controller.exceptions.AccountNotFoundException;
import com.albert.currency.controller.exceptions.CurrencyNotFoundException;
import com.albert.currency.controller.exceptions.ValueOutOfBalanceException;
import com.albert.currency.domain.Account;
import com.albert.currency.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class AccountTestSuite {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testGetAccountById() throws AccountNotFoundException {
        //GIVEN
        Account account = new Account();
        account.setBalancePLN(new BigDecimal(1000));
        accountService.save(account);
        Long id = account.getAccountId();

        //WHEN
        Account testedAccount = accountService.getAccountById(id);

        //THEN
        assertEquals(new BigDecimal(1000),testedAccount.getBalancePLN());
        //CLEAN UP
        accountService.delete(id);
    }
    @Test
    public void testGetAllAccounts() {
        //GIVEN
        Account account1 = new Account();
        Account account2= new Account();
        Account account3 = new Account();
        accountService.save(account1);
        accountService.save(account2);
        accountService.save(account3);
        //WHEN
        List<Account> accounts = accountService.getAllAccounts();
        //THEN
        assertEquals(3,accounts.size());
        //CLEAN UP
        accountRepository.deleteAll();
    }
    @Test
    public void testPutMoneyIntoAccount() throws AccountNotFoundException, CurrencyNotFoundException {
        //GIVEN
        Account account = new Account();
        accountService.save(account);
        Long id = account.getAccountId();

        //WHEN
        Account testedAccount = accountService.getAccountById(id);
        accountService.putIntoAccount(id,"EUR",1000.0);
        accountService.save(account);

        //THEN
        assertEquals(1000,  testedAccount.getBalanceEUR().doubleValue());
        //CLEAN UP
        accountService.delete(id);
    }
    @Test
    public void testWithdrawMoneyFromAccount() throws AccountNotFoundException, CurrencyNotFoundException, ValueOutOfBalanceException {
        //GIVEN
        Account account = new Account();
        accountService.save(account);
        Long id = account.getAccountId();

        Account testedAccount = accountService.getAccountById(id);
        accountService.putIntoAccount(id,"EUR",1000.0);
        accountService.save(testedAccount);
//        System.out.println("Account EUR balance" + testedAccount.getBalanceEUR());

        //WHEN
        accountService.withdrawFromAccount(id,"EUR",500.0);
//        System.out.println("Account EUR balance" + testedAccount.getBalanceEUR());

        //THEN
        assertEquals(500, testedAccount.getBalanceEUR().doubleValue());
        //CLEAN UP
        accountService.delete(id);
    }

}

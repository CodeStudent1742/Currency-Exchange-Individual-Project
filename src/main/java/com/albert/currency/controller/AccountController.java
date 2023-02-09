package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.AccountNotFoundException;
import com.albert.currency.controller.exceptions.CurrencyNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.controller.exceptions.ValueOutOfBalanceException;
import com.albert.currency.domain.Account;
import com.albert.currency.domain.dto.AccountDto;
import com.albert.currency.domain.dto.NewAccountDto;
import com.albert.currency.mapper.AccountMapper;
import com.albert.currency.service.AccountService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/account")
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    @GetMapping(value = "{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId) throws AccountNotFoundException {
        Account account = accountService.getAccountById(accountId);
        return ResponseEntity.ok(accountMapper.mapToAccountDto(account));
    }
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accountMapper.mapToAccountDtos(accounts));
    }
    @DeleteMapping(value = "{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId){
        accountService.delete(accountId);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{accountId}/put")
    public ResponseEntity<Void> putIntoAccount(@PathVariable Long accountId, @RequestParam String currency, @RequestParam Integer value) throws AccountNotFoundException, CurrencyNotFoundException {
        accountService.putIntoAccount(accountId,currency,value);
        return ResponseEntity.ok().build();
    }
    @PutMapping("{accountId}/withdraw")
    public ResponseEntity<Void> withdrawFromAccount(@PathVariable Long accountId, @RequestParam String currency, @RequestParam Integer value) throws AccountNotFoundException, CurrencyNotFoundException, ValueOutOfBalanceException {
        accountService.withdrawFromAccount(accountId,currency,value);
        return ResponseEntity.ok().build();
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccount(@RequestBody NewAccountDto newAccountDto) throws UserNotFoundException {
        Account account = accountMapper.mapToAccount(newAccountDto);
        accountService.save(account);
        return ResponseEntity.ok().build();
    }
}

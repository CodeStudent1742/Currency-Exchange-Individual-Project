package com.albert.currency.service;

import com.albert.currency.controller.exceptions.AccountNotFoundException;
import com.albert.currency.controller.exceptions.CurrencyNotFoundException;
import com.albert.currency.controller.exceptions.ValueOutOfBalanceException;
import com.albert.currency.domain.Account;
import com.albert.currency.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void save(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountById(Long accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void delete(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public void putIntoAccount(Long accountId, String currency, Double value) throws AccountNotFoundException, CurrencyNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        putProcess(account,currency,value);

    }

    private void putProcess(Account account, String currency, Double value) throws CurrencyNotFoundException {

        switch (currency.toUpperCase()) {
            case "PLN" -> {
                account.setBalancePLN(account.getBalancePLN().add(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "EUR" -> {
                account.setBalanceEUR(account.getBalanceEUR().add(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "USD" -> {
                account.setBalanceUSD(account.getBalanceUSD().add(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "GBP" -> {
                account.setBalanceGBP(account.getBalanceGBP().add(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "CHF" -> {
                account.setBalanceCHF(account.getBalanceCHF().add(BigDecimal.valueOf(value)))  ;
                accountRepository.save(account);
            }
            default -> throw new CurrencyNotFoundException();
        }

    }

    public void withdrawFromAccount(Long accountId, String currency, Double value) throws AccountNotFoundException, CurrencyNotFoundException, ValueOutOfBalanceException {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        withdrawProcess(account,currency,value);
    }

    private void withdrawProcess(Account account, String currency, Double value) throws CurrencyNotFoundException, ValueOutOfBalanceException {
        switch (currency.toUpperCase()) {
            case "PLN" -> {
                if (account.getBalancePLN().compareTo(BigDecimal.valueOf(value)) == 1){
                    throw new ValueOutOfBalanceException();
                }
                account.setBalancePLN(account.getBalancePLN().subtract(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "EUR" -> {
                if (account.getBalancePLN().compareTo(BigDecimal.valueOf(value)) == 1){
                    throw new ValueOutOfBalanceException();
                }
                account.setBalanceEUR(account.getBalanceEUR().subtract(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "USD" -> {
                if (account.getBalancePLN().compareTo(BigDecimal.valueOf(value)) == 1){
                    throw new ValueOutOfBalanceException();
                }
                account.setBalanceUSD(account.getBalanceUSD().subtract(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "GBP" -> {
                if (account.getBalancePLN().compareTo(BigDecimal.valueOf(value)) == 1){
                    throw new ValueOutOfBalanceException();
                }
                account.setBalanceGBP(account.getBalanceGBP().subtract(BigDecimal.valueOf(value)));
                accountRepository.save(account);
            }
            case "CHF" -> {
                if (account.getBalancePLN().compareTo(BigDecimal.valueOf(value)) == 1){
                    throw new ValueOutOfBalanceException();
                }
                account.setBalanceCHF(account.getBalanceCHF().subtract(BigDecimal.valueOf(value)))  ;
                accountRepository.save(account);
            }
            default -> throw new CurrencyNotFoundException();
        }
    }
}

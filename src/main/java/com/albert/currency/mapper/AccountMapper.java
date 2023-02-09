package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.Account;
import com.albert.currency.domain.AccountRecord;
import com.albert.currency.domain.dto.AccountDto;
import com.albert.currency.domain.dto.NewAccountDto;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountMapper {

    private final UserRepository userRepository;

    public AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getAccountId(),
                account.getBalancePLN(),
                account.getBalanceEUR(),
                account.getBalanceUSD(),
                account.getBalanceCHF(),
                account.getBalanceGBP(),
                account.getUser().getUserId(),
                mapToRecordIds(account.getAccountRecords())
        );

    }

    public List<AccountDto> mapToAccountDtos(List<Account> accounts) {
        return accounts.stream()
                .map(this::mapToAccountDto)
                .collect(Collectors.toList());
    }

    private List<Long> mapToRecordIds(List<AccountRecord> accountRecords) {
        return accountRecords.stream()
                .map(AccountRecord::getRecordId)
                .collect(Collectors.toList());
    }
    public Account mapToAccount(NewAccountDto newAccountDto) throws UserNotFoundException {
        return new Account(
                userRepository.findById(newAccountDto.getUserId()).orElseThrow(UserNotFoundException::new));
    }
}

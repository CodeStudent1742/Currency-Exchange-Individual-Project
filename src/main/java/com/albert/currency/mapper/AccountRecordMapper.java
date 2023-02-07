package com.albert.currency.mapper;

import com.albert.currency.domain.AccountRecord;
import com.albert.currency.repository.AccountRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountRecordMapper {

    private final AccountRecordRepository accountRecordRepository;

    public List<AccountRecord> mapToAccountRecords(List<Long> accountRecordIds) {
        List<AccountRecord> results = new ArrayList<>();
        if (Objects.nonNull(accountRecordIds)) {
            for (Long id : accountRecordIds) {
                accountRecordRepository.findById(id).ifPresent(results::add);
            }
        }
        return results;
    }
}

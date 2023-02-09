package com.albert.currency.repository;

import com.albert.currency.domain.AccountRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRecordRepository extends CrudRepository<AccountRecord, Long> {

    @Override
    Optional<AccountRecord> findById(Long accountRecordId);

}

package com.albert.currency.repository;


import com.albert.currency.domain.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends CrudRepository<Transaction,Long> {



    @Override
    Iterable<Transaction> findAllById(Iterable<Long> longs);
}

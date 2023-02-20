package com.albert.currency.repository;

import com.albert.currency.domain.ExchangeOrder;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ExchangeOrderRepository extends CrudRepository<ExchangeOrder,Long> {

    @Override
    Optional<ExchangeOrder> findById(Long id);

    List<ExchangeOrder> findAll();

    @Override
    List<ExchangeOrder> findAllById(Iterable<Long> longs);
}

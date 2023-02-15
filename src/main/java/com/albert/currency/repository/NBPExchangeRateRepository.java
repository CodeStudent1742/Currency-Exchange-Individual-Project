package com.albert.currency.repository;

import com.albert.currency.domain.NBPExchangeRate;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface NBPExchangeRateRepository extends CrudRepository<NBPExchangeRate,Long> {

}

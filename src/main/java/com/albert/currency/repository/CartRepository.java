package com.albert.currency.repository;

import com.albert.currency.domain.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends CrudRepository<Cart,Long> {

    @Override
    Optional<Cart> findById(Long id);

    @Override
    List<Cart> findAll();
}

package com.albert.currency.repository;

import com.albert.currency.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User,Long> {

    @Override
    Optional<User> findById(Long id);
    @Override
    List<User> findAll();
    void deleteById(Long id);


}

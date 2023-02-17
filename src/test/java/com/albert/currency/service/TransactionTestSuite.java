package com.albert.currency.service;

import com.albert.currency.service.TransactionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class TransactionTestSuite {

    @Autowired
    private TransactionService transactionService;

    @Test
    public void getTransactionByIdTest(){
        //GIVEN
        //WHEN
        //THEN

    }
}

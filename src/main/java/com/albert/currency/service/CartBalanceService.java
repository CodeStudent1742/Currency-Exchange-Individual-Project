package com.albert.currency.service;

import com.albert.currency.domain.CartBalance;
import com.albert.currency.repository.CartBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartBalanceService {
    private final CartBalanceRepository cartBalanceRepository;

    public void saveCartBalance(CartBalance cartbalance) {
        cartBalanceRepository.save(cartbalance);
    }

}

package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.Cart;
import com.albert.currency.domain.Transaction;
import com.albert.currency.domain.dto.CartDto;
import com.albert.currency.domain.dto.NewCartDto;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartMapper {
    private final UserRepository userRepository;

    public CartDto mapToCartDto(Cart cart) {
        return new CartDto.CartDtoBuilder()
                .cartId(cart.getCartId())
                .userId(cart.getUser().getUserId())
                .transactions(mapToTransactionsIds(cart.getTransactions()))
                .build();
    }

    private List<Long> mapToTransactionsIds(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getTransactionId)
                .collect(Collectors.toList());
    }

    public List<CartDto> mapToCartsDto(List<Cart> carts) {
        return carts.stream()
                .map(this::mapToCartDto)
                .collect(Collectors.toList());
    }

    public Cart mapToCart(NewCartDto newCartDto) throws UserNotFoundException {
        return new Cart.CartBuilder()
                .user(userRepository.findById(newCartDto.getUserId()).orElseThrow(UserNotFoundException::new))
                .build();
    }
}

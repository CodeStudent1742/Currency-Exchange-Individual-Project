package com.albert.currency.service;

import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.domain.Cart;
import com.albert.currency.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart getCart(Long cartId) throws CartNotFoundException {
        return cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }
}

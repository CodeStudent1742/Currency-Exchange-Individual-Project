package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.*;
import com.albert.currency.domain.dto.CartDto;
import com.albert.currency.domain.dto.NewCartDto;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.controller.facade.CartControllerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/cart")
public class CartController {
    private final CartControllerFacade cartControllerFacade;

    @GetMapping
    public ResponseEntity<List<CartDto>> getCarts() {
        return ResponseEntity.ok(cartControllerFacade.fetchCarts());
    }
    @GetMapping(value = "{cartId}")
    public ResponseEntity<CartDto> getUser(@PathVariable Long cartId) throws CartNotFoundException {
        return ResponseEntity.ok(cartControllerFacade.fetchCart(cartId));
    }
    @GetMapping(value = "{cartId}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactionsInCart(@PathVariable Long cartId) throws CartNotFoundException {
        return ResponseEntity.ok(cartControllerFacade.fetchAllTransactionsInCart(cartId));
    }
    @DeleteMapping(value = "{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartControllerFacade.deleteCartById(cartId);
        return ResponseEntity.ok().build();
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCart(@RequestBody NewCartDto newCartDto) throws UserNotFoundException {
        cartControllerFacade.createCartFromNewCartDto(newCartDto);
        return ResponseEntity.ok().build();
    }
    @PutMapping(value = "{cartId}/{transactionId}")
    public ResponseEntity<Void> addTransactionToCart(@PathVariable Long cartId, @PathVariable Long transactionId) throws CartNotFoundException, TransactionNotFoundException {
        cartControllerFacade.appendTransactionToCart(cartId, transactionId);
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "order/{cartId}")
    public ResponseEntity<Void> makeExchangeOrderFromCart(@PathVariable Long cartId) throws CartNotFoundException, NoProductsInCartException, NoSufficientFundsException {
        cartControllerFacade.makeOrderFromCart(cartId);
        return ResponseEntity.ok().build();
    }

}

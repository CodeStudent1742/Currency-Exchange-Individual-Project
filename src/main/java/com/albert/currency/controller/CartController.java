package com.albert.currency.controller;

import com.albert.currency.controller.exceptions.CartNotFoundException;
import com.albert.currency.controller.exceptions.NoProductsInCartException;
import com.albert.currency.controller.exceptions.TransactionNotFoundException;
import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.CartDto;
import com.albert.currency.domain.dto.NewCartDto;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.mapper.CartMapper;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.service.AccountService;
import com.albert.currency.service.CartService;
import com.albert.currency.service.ExchangeOrderService;
import com.albert.currency.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/cart")
public class CartController {
    private final CartService cartService;
    private final ExchangeOrderService exchangeOrderService;
    private final AccountService accountService;
    private final CartMapper cartMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<CartDto>> getCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(cartMapper.mapToCartsDto(carts));
    }

    @GetMapping(value = "{cartId}")
    public ResponseEntity<CartDto> getUser(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @GetMapping(value = "{cartId}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactionsInCart(@PathVariable Long cartId) throws CartNotFoundException {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(transactionMapper.mapToTransactionsDto(cart.getTransactions()));
    }

    @DeleteMapping(value = "{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCart(@RequestBody NewCartDto newCartDto) throws UserNotFoundException {
        Cart cart = cartMapper.mapToNewCart(newCartDto);
        cartService.saveCart(cart);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{cartId}/{transactionId}")
    public ResponseEntity<Void> addTransactionToCart(@PathVariable Long cartId, @PathVariable Long transactionId) throws CartNotFoundException, TransactionNotFoundException {
        Cart cart = cartService.getCart(cartId);
        cart.getTransactions().add(transactionService.getTransaction(transactionId));
        cartService.saveCart(cart);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "order/{cartId}")
    public ResponseEntity<Void> makeOrderFromCart(@PathVariable Long cartId) throws CartNotFoundException, NoProductsInCartException {
        Cart cart = cartService.getCart(cartId);
        CartBalance cartBalance = cart.getCartBalance();
        Account account = cart.getUser().getAccount();
        if (cart.getTransactions().isEmpty()) {
            throw new NoProductsInCartException();
        }
        if (cart.isSufficientFunds()) {
            account.subtractCartBalanceFromAccountBalance(cartBalance);
            accountService.save(account);
            cartBalance.clearBalance();
            ExchangeOrder exchangeOrder = convertCartToExchangeOrder(cart);
            exchangeOrderService.save(exchangeOrder);
        } else {
            throw new NoProductsInCartException();
        }
    }
    private  ExchangeOrder convertCartToExchangeOrder(Cart cart) {
        List<Transaction> transactions = new ArrayList<>(cart.getTransactions());
        for (Transaction transaction : transactions) {
            transaction.setCart(null);
        }
        ExchangeOrder exchangeOrder = new ExchangeOrder(
                LocalDate.now(),
                ExchangeStatus.DONE,
                cart.getUser(),
                transactions
        );
        return exchangeOrder;
    }
}

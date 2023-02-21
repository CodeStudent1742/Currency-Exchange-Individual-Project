package com.albert.currency.controller.facade;

import com.albert.currency.controller.exceptions.*;
import com.albert.currency.domain.*;
import com.albert.currency.domain.dto.CartDto;
import com.albert.currency.domain.dto.NewCartDto;
import com.albert.currency.domain.dto.TransactionDto;
import com.albert.currency.mapper.CartMapper;
import com.albert.currency.mapper.TransactionMapper;
import com.albert.currency.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartControllerFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartControllerFacade.class);
    private final CartService cartService;
    private final CartBalanceService cartBalanceService;
    private final ExchangeOrderService exchangeOrderService;
    private final AccountService accountService;
    private final CartMapper cartMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;


    public List<CartDto> fetchCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return cartMapper.mapToCartsDto(carts);
    }


    public CartDto fetchCart(Long cartId) throws CartNotFoundException {
        Cart cart = cartService.getCart(cartId);
        return cartMapper.mapToCartDto(cart);
    }


    public List<TransactionDto> fetchAllTransactionsInCart(Long cartId) throws CartNotFoundException {
        Cart cart = cartService.getCart(cartId);
        return transactionMapper.mapToTransactionsDto(cart.getTransactions());
    }


    public void deleteCartById( Long cartId) {
        cartService.deleteCart(cartId);
        LOGGER.info("Cart deleted");
    }

    public void createCartFromNewCartDto(NewCartDto newCartDto) throws UserNotFoundException {
        Cart cart = cartMapper.mapToCart(newCartDto);
        cartService.saveCart(cart);
        LOGGER.info("Cart saved");
    }

    public void appendTransactionToCart(Long cartId, Long transactionId) throws CartNotFoundException, TransactionNotFoundException {
        Cart cart = cartService.getCart(cartId);
        cart.getTransactions().add(transactionService.getTransaction(transactionId));
        cartService.saveCart(cart);
        LOGGER.info("Transaction with id " +transactionId + " has been added to the Cart with id " +cartId+ " .");
    }

    public void makeOrderFromCart(Long cartId) throws CartNotFoundException, NoProductsInCartException, NoSufficientFundsException {
        LOGGER.info("Starting making Exchange Order from Cart...");
        Cart cart = cartService.getCart(cartId);
        CartBalance cartBalance = cart.getCartBalance();
        Account account = cart.getUser().getAccount();
        if (cart.getTransactions().isEmpty()) {
            LOGGER.error("No products in Cart found to make order");
            throw new NoProductsInCartException();
        }
        if (cart.isSufficientFunds()) {
            cartBalance.calculateCartBalance();
            accountService.subtractCartBalanceFromAccountBalance(account,cartBalance);
            cartBalance.clearBalance();
            cartBalanceService.saveCartBalance(cartBalance);
            ExchangeOrder exchangeOrder = convertCartToExchangeOrder(cart);
            exchangeOrderService.save(exchangeOrder);
            cart.setTransactions(new ArrayList<>());
            cartService.saveCart(cart);
            LOGGER.info("Order made from Cart, operation saved");
        } else {
            LOGGER.error("No enough founds to make Order");
            throw new NoSufficientFundsException();
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

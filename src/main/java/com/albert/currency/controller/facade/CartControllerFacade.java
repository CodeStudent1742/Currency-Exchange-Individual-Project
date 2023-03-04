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


    public void deleteCartById(Long cartId) {
        cartService.deleteCart(cartId);
        LOGGER.info("Cart deleted");
    }

    public void createCartFromNewCartDto(NewCartDto newCartDto) throws UserNotFoundException {
        Cart cart = cartMapper.mapToCart(newCartDto);
        cartService.saveCart(cart);
        LOGGER.info("Cart saved");
    }

    public void appendTransactionToCart(Long cartId, TransactionDto transactionDto) throws CartNotFoundException {
        Cart cart = cartService.getCart(cartId);
        Transaction transaction = transactionMapper.mapToTransaction(transactionDto);
        transactionService.saveTransaction(transaction);
        cart.getTransactions().add(transaction);
        cartService.saveCart(cart);
        LOGGER.info("Transaction has been added to the Cart with id " + cartId + " .");
    }

    public void makeOrderFromCart(Long cartId) throws CartNotFoundException, NoProductsInCartException, NoSufficientFundsException {
        LOGGER.info("Starting making Exchange Order from Cart...");
        Cart cart = cartService.getCart(cartId);
        CartBalance cartBalance = cart.getCartBalance();
        Account account = cart.getUser().getAccount();
        if (cart.getTransactions().isEmpty()) {
            LOGGER.error("No products in Cart found to make order");
            throw new NoProductsInCartException();
        } else if (isSufficientFunds(cartBalance, account)) {
            cartBalance.calculateCartBalance();
            accountService.exchangeOperationAccountBalanceChange(account, cart);
            cartBalance.clearBalance();
            cartBalanceService.saveCartBalance(cartBalance);
            convertCartToExchangeOrder(cart);
            LOGGER.info("Order made from Cart, operation saved");
        } else {
            LOGGER.error("No enough founds to make Order");
            throw new NoSufficientFundsException();
        }
    }

    public void convertCartToExchangeOrder(Cart cart) {
        ExchangeOrder exchangeOrder = new ExchangeOrder(
                LocalDate.now(),
                ExchangeStatus.DONE,
                cart.getUser(),
                new ArrayList<>()
        );
        exchangeOrderService.save(exchangeOrder);
        for (Transaction transaction : cart.getTransactions()) {
            exchangeOrder.getOrderTransactions().add(transaction);
            transaction.setExchangeOrder(exchangeOrder);
            transaction.setCart(null);
            transactionService.saveTransaction(transaction);
        }

        exchangeOrderService.save(exchangeOrder);
        cart.setTransactions(new ArrayList<>());
        cartService.saveCart(cart);
    }

    public boolean isSufficientFunds(CartBalance cartBalance, Account account) {
        if (cartBalance.getBalancePLN().compareTo(account.getBalancePLN()) > 0) {
            return false;
        }
        if (cartBalance.getBalanceEUR().compareTo(account.getBalanceEUR()) > 0) {
            return false;
        }
        if (cartBalance.getBalanceUSD().compareTo(account.getBalanceUSD()) > 0) {
            return false;
        }
        if (cartBalance.getBalanceCHF().compareTo(account.getBalanceCHF()) > 0) {
            return false;
        }
        if (cartBalance.getBalanceGBP().compareTo(account.getBalanceGBP()) > 0) {
            return false;
        }
        return true;
    }

    public void deleteTransactionFromCart(Long cartId, Long transactionId) throws CartNotFoundException, TransactionNotFoundException {
        Cart cart = cartService.getCart(cartId);
        cart.getTransactions().remove(transactionService.getTransaction(transactionId));
        transactionService.getTransaction(transactionId).setCart(null);
        transactionService.saveTransaction(transactionService.getTransaction(transactionId));
        cartService.saveCart(cart);
        transactionService.deleteTransaction(transactionId);
        LOGGER.info("Transaction with id " + transactionId + " has been deleted from the Cart with id " + cartId + " .");
    }
}

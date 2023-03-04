package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="CARTS")
public class Cart {

    @Id
    @GeneratedValue
    @NotNull
    @Column( name ="CART_ID", unique = true)
    private Long cartId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "USER_ID")
    private User user;

    @OneToMany(
            targetEntity = Transaction.class,
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Transaction> transactions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "CART_BALANCE_ID")
    private CartBalance cartBalance;

    public Cart(Long cartId, User user, List<Transaction> transactions) {
        this.cartId = cartId;
        this.user = user;
        this.transactions = transactions;
    }

    public static class CartBuilder {
        private Long cartId;
        private User user;
        private List<Transaction> transactions = new ArrayList<>();
        private CartBalance cartBalance;

        public CartBuilder cartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public CartBuilder user(User user) {
            this.user = user;
            return this;
        }

        public CartBuilder transaction(Transaction transaction) {
            this.transactions.add(transaction);
            return this;
        }

        public CartBuilder transactions(List<Transaction> transactions) {
            this.transactions = transactions;
            return this;
        }

        public CartBuilder cartBalance(CartBalance cartBalance) {
            this.cartBalance = cartBalance;
            return this;
        }

        public Cart build() {
            Cart cart = new Cart();
            cart.setCartId(this.cartId);
            cart.setUser(this.user);
            cart.setTransactions(this.transactions);
            cart.setCartBalance(this.cartBalance);
            return cart;
        }
    }
}

package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "USER_ID", unique = true)
    private Long userId;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToOne(mappedBy = "user")
    private Account account;

    @OneToMany(
            targetEntity = AccountRecord.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<AccountRecord> accountRecords = new ArrayList<>();

    @OneToMany(
            targetEntity = ExchangeOrder.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<ExchangeOrder> exchangeOrders = new ArrayList<>();

    @NotNull
    @Column(name = "USER_NAME")
    private String userName;


    public User(String userName){
        this.userName = userName;
        this.account = new Account(this);
        this.cart = new Cart(this);
    }
}

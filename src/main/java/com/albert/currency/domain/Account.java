package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ACCOUNT_ID", unique = true)
    private Long accountId;

    @Column(name = "PLN_BALANCE")
    private BigDecimal balancePLN = new BigDecimal(0);
    @Column(name = "EUR_BALANCE")
    private BigDecimal balanceEUR = new BigDecimal(0);
    @Column(name = "USD_BALANCE")
    private BigDecimal balanceUSD = new BigDecimal(0);
    @Column(name = "CHF_BALANCE")
    private BigDecimal balanceCHF = new BigDecimal(0);
    @Column(name = "GBP_BALANCE")
    private BigDecimal balanceGBP = new BigDecimal(0);

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    User user;

    public Account(User user) {
        this.user = user;
    }

    public void subtractCartBalanceFromAccountBalance(CartBalance cartBalance) {
        setBalancePLN(getBalancePLN().subtract(cartBalance.getBalancePLN()));
        setBalanceEUR(getBalanceEUR().subtract(cartBalance.getBalanceEUR()));
        setBalanceUSD(getBalanceUSD().subtract(cartBalance.getBalanceUSD()));
        setBalanceCHF(getBalanceCHF().subtract(cartBalance.getBalanceCHF()));
        setBalanceGBP(getBalanceGBP().subtract(cartBalance.getBalanceGBP()));
    }

    public Account(BigDecimal balancePLN, BigDecimal balanceEUR, BigDecimal balanceUSD, BigDecimal balanceCHF, BigDecimal balanceGBP) {
        this.balancePLN = balancePLN;
        this.balanceEUR = balanceEUR;
        this.balanceUSD = balanceUSD;
        this.balanceCHF = balanceCHF;
        this.balanceGBP = balanceGBP;
    }
}
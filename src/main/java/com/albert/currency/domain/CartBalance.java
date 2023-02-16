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
@Table(name="CART_BALANCE")
public class CartBalance {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "CART_BALANCE_ID", unique = true)
    private Long cartBalanceId;

    @Column(name = "CART_PLN_BALANCE")
    private BigDecimal balancePLN;
    @Column(name = "CART_EUR_BALANCE")
    private BigDecimal balanceEUR;
    @Column(name = "CART_USD_BALANCE")
    private BigDecimal balanceUSD;
    @Column(name = "CART_CHF_BALANCE")
    private BigDecimal balanceCHF;
    @Column(name = "CART_GBP_BALANCE")
    private BigDecimal balanceGBP;

    @OneToOne(mappedBy = "cartBalance")
    private Cart cart;

    public void calculateCartBalance(){
         balancePLN = BigDecimal.valueOf(0);
         balanceEUR = BigDecimal.valueOf(0);
         balanceUSD = BigDecimal.valueOf(0);
         balanceCHF = BigDecimal.valueOf(0);
         balanceGBP = BigDecimal.valueOf(0);
        for(Transaction transaction : cart.getTransactions()){
            switch(transaction.getExchangeOperation()){
                case EUR_TO_PLN -> {
                    balanceEUR = balanceEUR.add(BigDecimal.valueOf(transaction.getTransactionValue()));
                }
                case PLN_TO_EUR, PLN_TO_CHF, PLN_TO_GBP, PLN_TO_USD -> {
                    balancePLN = balancePLN.add(BigDecimal.valueOf(transaction.getTransactionValue()));
                }
                case USD_TO_PLN -> {
                    balanceUSD = balanceUSD.add(BigDecimal.valueOf(transaction.getTransactionValue()));
                }
                case CHF_TO_PLN -> {
                    balanceCHF = balanceCHF.add(BigDecimal.valueOf(transaction.getTransactionValue()));
                }
                case GBP_TO_PLN -> {
                    balanceGBP = balanceGBP.add(BigDecimal.valueOf(transaction.getTransactionValue()));
                }
            }
        }
    }
    public void clearBalance(){
        setBalancePLN(BigDecimal.valueOf(0));
        setBalanceEUR(BigDecimal.valueOf(0));
        setBalanceCHF(BigDecimal.valueOf(0));
        setBalanceGBP(BigDecimal.valueOf(0));
        setBalanceGBP(BigDecimal.valueOf(0));
    }
}

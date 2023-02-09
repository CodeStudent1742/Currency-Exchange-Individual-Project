package com.albert.currency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal balancePLN;
    @Column(name = "EUR_BALANCE")
    private BigDecimal balanceEUR;
    @Column(name = "USD_BALANCE")
    private BigDecimal balanceUSD;
    @Column(name = "CHF_BALANCE")
    private BigDecimal balanceCHF;
    @Column(name = "GBP_BALANCE")
    private BigDecimal balanceGBP;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    User user;

    @OneToMany(
            targetEntity = AccountRecord.class,
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    List<AccountRecord> accountRecords;

    public Account(User user){
        this.user = user;
        this.balancePLN = new BigDecimal(0);
        this.balanceEUR = new BigDecimal(0);
        this.balanceUSD = new BigDecimal(0);
        this.balanceGBP = new BigDecimal(0);
        this.balanceCHF = new BigDecimal(0);
    }

}
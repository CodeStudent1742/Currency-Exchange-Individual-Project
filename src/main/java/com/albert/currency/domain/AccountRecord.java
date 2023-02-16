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
@Table(name = "ACCOUNTS_RECORDS")
public class AccountRecord {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "RECORD_ID")
    Long recordId;

    @Column(name = "PLN_BALANCE")
    private BigDecimal balancePLN;
    @Column(name = "EUR_BALANCE")
    private BigDecimal balanceEUR;
    @Column(name = "USD_BALANCE")
    private BigDecimal balanceUSD;
    @Column(name = "CHF_BALANCE")
    private BigDecimal balanceCHF;
    @Column(name = "GPD_BALANCE")
    private BigDecimal balanceGPD;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    Account account;

}

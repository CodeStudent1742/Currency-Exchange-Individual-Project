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
@Table(name = "ACCOUNTS_STATUS_HISTORY")
public class AccountStatusCopy {

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "COPY_ID")
    Long copyId;

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


}

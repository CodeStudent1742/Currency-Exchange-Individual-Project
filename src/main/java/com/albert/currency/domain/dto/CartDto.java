package com.albert.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long cartId;
    private Long userId;
    private List<Long> transactions;
    private Long cartBalanceId;

    public static class CartDtoBuilder {
        private Long cartId;
        private Long userId;
        private List<Long> transactions;
        private Long cartBalanceId;

        public CartDtoBuilder cartId(Long cartId) {
            this.cartId = cartId;
            return this;
        }

        public CartDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public CartDtoBuilder transactions(List<Long> transactions) {
            this.transactions = transactions;
            return this;
        }
        public CartDtoBuilder transaction(Long transactionId) {
            this.transactions.add(transactionId);
            return this;
        }
        public CartDtoBuilder cartBalanceId (Long cartBalanceId){
            this.cartBalanceId = cartBalanceId;
            return this;
        }

        public CartDto build() {
            return new CartDto(cartId, userId, transactions,cartBalanceId);
        }
    }

    public CartDto(Long cartId, Long userId, List<Long> transactions) {
        this.cartId = cartId;
        this.userId = userId;
        this.transactions = transactions;
    }

}

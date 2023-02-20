package com.albert.currency.domain.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long cartId;
    private Long userId;
    private List<Long> transactions;

    public static class CartDtoBuilder {
        private Long cartId;
        private Long userId;
        private List<Long> transactions;

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

        public CartDto build() {
            return new CartDto(cartId, userId, transactions);
        }
    }
}

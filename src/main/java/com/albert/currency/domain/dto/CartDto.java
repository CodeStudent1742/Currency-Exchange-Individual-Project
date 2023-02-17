package com.albert.currency.domain.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long cartId;
    private Long userId;
    private List<Long> transactions;

}

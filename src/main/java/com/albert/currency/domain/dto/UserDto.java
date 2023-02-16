package com.albert.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private Long cartId;
    private Long accountId;
    private List<Long> exchangeOrderIds ;
    private String userName;

}

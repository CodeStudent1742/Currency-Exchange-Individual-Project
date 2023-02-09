package com.albert.currency.mapper;

import com.albert.currency.domain.AccountRecord;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getCart().getCartId(),
                user.getAccount().getAccountId(),
                mapToRecordIds(user.getAccountRecords()),
                mapToExchangeOrderIds(user.getExchangeOrders()),
                user.getUserName()
        );
    }

    private List<Long> mapToExchangeOrderIds(List<ExchangeOrder> exchangeOrders) {
        return exchangeOrders.stream()
                .map(ExchangeOrder::getExchangeOrderId)
                .collect(Collectors.toList());
    }

    private List<Long> mapToRecordIds(List<AccountRecord> accountStatusCopies) {
        return accountStatusCopies.stream()
                .map(AccountRecord::getRecordId)
                .collect(Collectors.toList());
    }

    public User mapToUser(NewUserDto newUserDto) {
        return new User (
          newUserDto.getUserName()
        );
    }

    public List<UserDto> mapToUsersDto(List<User> users) {
       return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}

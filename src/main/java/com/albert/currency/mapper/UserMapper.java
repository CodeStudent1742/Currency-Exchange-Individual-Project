package com.albert.currency.mapper;

import com.albert.currency.controller.exceptions.UserNotFoundException;
import com.albert.currency.domain.ExchangeOrder;
import com.albert.currency.domain.User;
import com.albert.currency.domain.dto.NewUserDto;
import com.albert.currency.domain.dto.UserDto;
import com.albert.currency.repository.ExchangeOrderRepository;
import com.albert.currency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;
    private final ExchangeOrderRepository exchangeOrderRepository;

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUserId(),
                user.getCart().getCartId(),
                user.getAccount().getAccountId(),
                mapToExchangeOrderIds(user.getExchangeOrders()),
                user.getUserName()
        );
    }

    private List<Long> mapToExchangeOrderIds(List<ExchangeOrder> exchangeOrders) {
        return exchangeOrders.stream()
                .map(ExchangeOrder::getExchangeOrderId)
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
    public User mapToUser(UserDto userDto) throws UserNotFoundException {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(UserNotFoundException::new);
        user.setUserName(userDto.getUserName());
        user.setExchangeOrders(transferToExchangeOrder(exchangeOrderRepository.findAllById(userDto.getExchangeOrderIds())));
        return user;
    }

    private List<ExchangeOrder> transferToExchangeOrder(Iterable<ExchangeOrder> exchangesIterable) {
        return StreamSupport.
                stream(exchangesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

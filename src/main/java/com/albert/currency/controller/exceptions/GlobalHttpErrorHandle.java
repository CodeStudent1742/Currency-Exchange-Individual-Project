package com.albert.currency.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Currency;

@ControllerAdvice
public class GlobalHttpErrorHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>("Requested data doesn't exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(CartNotFoundException exception){
        return new ResponseEntity<>("Requested data doesn't exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(AccountNotFoundException exception){
        return new ResponseEntity<>("Requested data doesn't exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(ExchangeOrderNotFoundException exception){
        return new ResponseEntity<>("Requested data doesn't exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(AccountRecordNotFoundException exception){
        return new ResponseEntity<>("Requested data doesn't exist", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(CurrencyNotFoundException exception){
        return new ResponseEntity<>("Currency not available", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundException(ValueOutOfBalanceException exception){
        return new ResponseEntity<>("You want to withdraw more than you have on Account", HttpStatus.BAD_REQUEST);
    }

}

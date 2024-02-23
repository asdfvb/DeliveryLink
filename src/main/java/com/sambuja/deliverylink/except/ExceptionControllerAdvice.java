package com.sambuja.deliverylink.except;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorCode> notifyToCustomerException(ErrorException e){

        return ResponseEntity.status(e.errorCode.getStatus()).body(e.errorCode);
    }
}

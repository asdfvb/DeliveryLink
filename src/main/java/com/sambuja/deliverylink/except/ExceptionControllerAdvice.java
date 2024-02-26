package com.sambuja.deliverylink.except;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ResponseDto> notifyToCustomerException(ErrorException e){

        return ResponseEntity.status(e.responseDto.getStatus()).body(e.responseDto);
    }
}

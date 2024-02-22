package com.sambuja.deliverylink.except;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorException extends RuntimeException{

    String message;

    public ErrorException(Throwable e){
        super(e);
    }

    public ErrorException(Throwable e, String message){
        super(e);
        this.message = message;
    }

}

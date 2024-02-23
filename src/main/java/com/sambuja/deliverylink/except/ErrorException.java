package com.sambuja.deliverylink.except;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorException extends RuntimeException{

    ErrorCode errorCode;

    public ErrorException(Throwable e){
        super(e);
    }

    public ErrorException(Throwable e, ErrorCode errorCode){
        super(e);
        this.errorCode = errorCode;
    }

}

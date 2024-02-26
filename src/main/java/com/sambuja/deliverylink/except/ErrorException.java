package com.sambuja.deliverylink.except;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorException extends RuntimeException{

    ResponseDto responseDto;

    public ErrorException(Throwable e){
        super(e);
    }

    public ErrorException(Throwable e, ResponseDto responseDto){
        super(e);
        this.responseDto = responseDto;
    }

}

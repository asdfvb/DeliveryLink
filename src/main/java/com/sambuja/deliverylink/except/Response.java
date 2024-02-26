package com.sambuja.deliverylink.except;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

@Setter
@Getter
@Builder
public class Response {
    ResponseDto dto;
    T data;
}

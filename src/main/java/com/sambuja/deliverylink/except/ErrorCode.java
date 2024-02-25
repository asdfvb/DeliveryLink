package com.sambuja.deliverylink.except;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ARRAY_INDEX_OUT(3000, HttpStatus.BAD_REQUEST, "엑셀 양식을 확인해주세요. 열의 개수가 중요합니다.");

    private int code;

    private HttpStatus status;

    private String message;

//    private

    private ErrorCode(int code, HttpStatus status, String message){
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}

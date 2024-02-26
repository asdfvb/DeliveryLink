package com.sambuja.deliverylink.except;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@JsonFormat(shape= JsonFormat.Shape.OBJECT)
public enum ResponseDto {
    SUCCESS(1000, HttpStatus.OK, "정상적으로 완료되었습니다."),
    ARRAY_INDEX_OUT(3000, HttpStatus.BAD_REQUEST, "엑셀 양식을 확인해주세요. \n열의 개수가 중요합니다."),
    WRONG_FILE_EXTENTION(4000, HttpStatus.BAD_REQUEST, "엑셀 파일만 업로드 가능합니다. \n선택한 파일을 확인해주세요.")
    ;

    private static final ResponseDto[] VALUES = values();

    private int code;

    private HttpStatus status;

    private String message;

//    private

    private ResponseDto(int code, HttpStatus status, String message){
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

    public static ResponseDto valueOf(int code) {
        return Arrays.stream(VALUES)
                .filter(dto -> dto.code == code)
                .findFirst()
                .orElseThrow(() -> new RuntimeException( code + "-> Not definition response code."))
                ;
    }
}

package com.sambuja.deliverylink.dto;

import lombok.Data;

import java.util.Objects;

@Data
public class NaverDto implements DtoInterface{

    private String[] headerList = {
            "상품주문번호",
            "수취인명",
            "수취인연락처1",
            "통합배송지",
            "배송메세지",
            "상품명",
            "상품종류",
            "수량",
            "배송방법(구매자 요청)",
            "배송방법",
            "택배사",
            "송장번호",
            "발송일",
            "주문번호"
    };


    private String productNumber; //상품주문번호
    private String customerName; //수취인명
    private String phoneNumber; //수취인연락처1
    private String fullAddress; //통합배송지
    private String message; //배송메세지
    private String productName; //상품명
    private String productType; //상품종류
    private String cnt; //수량
    private String deliveryMethodByConsumer; //배송방법(구매자 요청)
    private String deliveryMethod; //배송방법
    private String courierCompany; //택배사
    private String deliveryNumber; //송장번호
    private String departureDate; //발송일
    private String orderNumber; //주문번호

    public void setValueByCellIndex(String headerText, String value){
        switch (headerText){
            case "상품주문번호":
                this.productNumber = value;
                break;
            case "수취인명":
                this.customerName = value;
                break;
            case "수취인연락처1":
                this.phoneNumber = value;
                break;
            case "통합배송지":
                this.fullAddress = value;
                break;
            case "배송메세지":
                this.message = value;
                break;
            case "상품명":
                this.productName = value;
                break;
            case "상품종류":
                this.productType = value;
                break;
            case "수량":
                this.cnt = value;
                break;
            case "배송방법(구매자 요청)":
                this.deliveryMethodByConsumer = value;
                break;
            case "배송방법":
                this.deliveryMethod = value;
                break;
            case "택배사":
                this.courierCompany = value;
                break;
            case "송장번호":
                this.deliveryNumber = value;
                break;
            case "발송일":
                this.deliveryMethodByConsumer = value;
                break;
            case "주문번호":
                this.orderNumber = value;
                break;
        }
    }

    public String getValueByCellIndex(String headerText){
        switch (headerText){
            case "상품주문번호":
                return this.productNumber;
            case "수취인명":
                return this.customerName;
            case "수취인연락처1":
                return this.phoneNumber;
            case "통합배송지":
                return this.fullAddress;
            case "배송메세지":
                return this.message;
            case "상품명":
                return this.productName;
            case "상품종류":
                return this.productType;
            case "수량":
                return this.cnt;
            case "배송방법(구매자 요청)":
                return this.deliveryMethodByConsumer;
            case "배송방법":
                return this.deliveryMethod;
            case "택배사":
                return this.courierCompany;
            case "송장번호":
                return this.deliveryNumber;
            case "발송일":
                return this.deliveryMethodByConsumer;
            case "주문번호":
                return this.orderNumber;
            default:
                return "";
        }
    }


    @Override
    public String getHeaderList(int index){
        return this.headerList[index];
    }

    @Override
    public int getHeaderSize(){
        return this.headerList.length;
    }

    /*@Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NaverDto other = (NaverDto) obj;
        return Objects.equals(this.customerName, other.customerName);
    }*/
}

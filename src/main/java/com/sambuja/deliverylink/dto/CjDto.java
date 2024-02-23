package com.sambuja.deliverylink.dto;

import lombok.Data;

@Data
public class CjDto implements DtoInterface{

    private String[] headerList = {
            "송장번호",
            "받는분명",
            "받는분전화번호",
            "받는분우편번호",
            "받는분주소",
            "접수일자",
            "고객주문번호",
            "집회일자",
            "배송일자",
            "수량",
            "배송메세지1",
            "배송메세지2",
            "상태",
            "예약구분",
            "배송예정사원",
            "배송사원닉네임",
            "에러메시지",
            "택배사",
            "택배방법"
    };


    private String deliveryNumber; //송장번호
    private String customerName; //받는분명
    private String phoneNumber; //받는분전화번호
    private String postNumber; //받는분우편번호
    private String fullAddress; //통합배송지
    private String receptionDate; //접수일자
    private String productNumber; //고객주문번호
    private String gatheringDate; //집회일자
    private String deliveryDate; //배송일자
    private String cnt; //수량
    private String message1; //배송메세지1
    private String message2; //배송메세지2
    private String status; //상태
    private String reservation; //예약구분
    private String deliveryEmployee; //배송예정사원
    private String deliveryEmployeeNickname; //배송사원닉네임
    private String errorMessage; //에러메시지
    private String courierCompany = "CJ대한통운"; //택배사
    private String deliveryMethod = "택배"; //택배방법

    public void setValueByCellIndex(String headerText, String value){
        switch (headerText){
            case "송장번호":
                this.deliveryNumber = value;
                break;
            case "받는분명":
                this.customerName = value;
                break;
            case "받는분전화번호":
                this.phoneNumber = value;
                break;
            case "받는분우편번호":
                this.postNumber = value;
                break;
            case "받는분주소":
                this.fullAddress = value;
                break;
            case "접수일자":
                this.receptionDate = value;
                break;
            case "고객주문번호":
                this.productNumber = value;
                break;
            case "집회일자":
                this.gatheringDate = value;
                break;
            case "배송일자":
                this.deliveryDate = value;
                break;
            case "수량":
                this.cnt = value;
                break;
            case "배송메세지1":
                this.message1 = value;
                break;
            case "배송메세지2":
                this.message2 = value;
                break;
            case "상태":
                this.status = value;
                break;
            case "예약구분":
                this.reservation = value;
                break;
            case "배송예정사원":
                this.deliveryEmployee = value;
                break;
            case "배송사원닉네임":
                this.deliveryEmployeeNickname = value;
                break;
            case "에러메시지":
                this.errorMessage = value;
                break;
            default:
                break;
        }
    }

    public String getValueByCellIndex(String headerText){
        switch (headerText) {
            case "송장번호":
                return this.deliveryNumber;
            case "받는분명":
                return this.customerName;
            case "받는분전화번호":
                return this.phoneNumber;
            case "받는분우편번호":
                return this.postNumber;
            case "받는분주소":
                return this.fullAddress;
            case "접수일자":
                return this.receptionDate;
            case "고객주문번호":
                return this.productNumber;
            case "집회일자":
                return this.gatheringDate;
            case "배송일자":
                return this.deliveryDate;
            case "수량":
                return this.cnt;
            case "배송메세지1":
                return this.message1;
            case "배송메세지2":
                return this.message2;
            case "상태":
                return this.status;
            case "예약구분":
                return this.reservation;
            case "배송예정사원":
                return this.deliveryEmployee;
            case "배송사원닉네임":
                return this.deliveryEmployeeNickname;
            case "에러메시지":
                return this.errorMessage;

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

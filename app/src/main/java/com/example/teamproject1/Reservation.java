package com.example.teamproject1;

public class Reservation {

    //방문예약자 확인
    private String idToken;

    //담당자 확인
    private String idToken2;

    //방문날짜
    private String start_date;
    private String end_date;

    //방문목적
    private String purpose;
    //차량정보
    private String carNum;
    private String carInfo;
    //반입 물품
    private String goods;
    //방문장소
    private String location;

    public Reservation(){

    }

    public Reservation(String idToken, String idToken2, String start_date, String end_date, String purpose, String carNum, String carInfo, String goods, String location) {
        this.idToken = idToken;
        this.idToken2 = idToken2;
        this.start_date = start_date;
        this.end_date = end_date;
        this.purpose = purpose;
        this.carNum = carNum;
        this.carInfo = carInfo;
        this.goods = goods;
        this.location = location;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getIdToken2() {
        return idToken2;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getCarNum() {
        return carNum;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public String getGoods() {
        return goods;
    }

    public String getLocation() {
        return location;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setIdToken2(String idToken2) {
        this.idToken2 = idToken2;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

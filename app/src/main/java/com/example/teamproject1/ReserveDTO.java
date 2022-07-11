package com.example.teamproject1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//방문 예약객체
public class ReserveDTO implements Serializable {

    //기본키 토큰
    private String reserveToken;

    //방문예약자 확인
    private String idToken;

    //담당자 확인
    private String idToken2;

    //방문날짜
    private String start_date;
    private String end_date;
    //방문시간
    private String start_time;
    private String end_time;
    //방문목적
    private String purpose;
    //차량정보
    private String carNum;
    private String carInfo;
    //반입 물품
    private String goods;

    public Map<String, Object> reserveMap(){
        HashMap<String, Object> reserve = new HashMap<>();
        reserve.put("reserveNum",idToken);
        reserve.put("manageNum",idToken2);
        reserve.put("start_date",start_date);
        reserve.put("end_date",end_date);
        reserve.put("start_time",start_time);
        reserve.put("end_time",end_time);
        reserve.put("purpose",purpose);
        reserve.put("carNum",carNum);
        reserve.put("carInfo",carInfo);
        reserve.put("goods",goods);
        return reserve;
    }


    public ReserveDTO() {

    }

    public String getReserveToken() {
        return reserveToken;
    }

    public void setReserveToken(String reserveToken) {
        this.reserveToken = reserveToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken2() {
        return idToken2;
    }

    public void setIdToken2(String idToken2) {
        this.idToken2 = idToken2;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }
}

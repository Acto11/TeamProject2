package com.example.teamproject1;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class VisitLocation {
    private String location1;
    private String location2;
    private String grade;
    private String used;
    private String note;

    public VisitLocation(){

    }
    //생성자
    public VisitLocation (String location1, String location2, String grade, String used, String note){
        this.location1 = location1;
        this.location2 = location2;
        this.grade = grade;
        this.used = used;
        this.note = note;
    }

//    //HashMap<> 구조 포함
//    @Exclude
//    public Map<String, Object> toMap() {
//       HashMap<String, Object> result = new HashMap<>();
//        result.put("location1", location1);
//        result.put("location2", location2);
//        result.put("grade", grade);
//        result.put("used", used);
//        result.put("note", note);
//
//        return result;
//    }

    //Getter
    public String getLocation1() {
        return location1;
    }

    public String getLocation2() {
        return location2;
    }

    public String getGrade() {
        return grade;
    }

    public String getUsed() {
        return used;
    }

    public String getNote() {
        return note;
    }
    //Setter
    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

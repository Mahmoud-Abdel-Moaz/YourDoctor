package com.example.healthyapp.pojo;

public class FirstAid {

    String ar_name, en_name;
    String ar_body, en_body;

    public FirstAid() {
    }

    public FirstAid(String ar_name, String en_name, String ar_body, String en_body) {
        this.ar_name = ar_name;
        this.en_name = en_name;
        this.ar_body = ar_body;
        this.en_body = en_body;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getAr_body() {
        return ar_body;
    }

    public void setAr_body(String ar_body) {
        this.ar_body = ar_body;
    }

    public String getEn_body() {
        return en_body;
    }

    public void setEn_body(String en_body) {
        this.en_body = en_body;
    }
}

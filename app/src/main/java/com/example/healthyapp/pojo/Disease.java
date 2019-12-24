package com.example.healthyapp.pojo;

public class Disease {
    String ar_name, en_name;
    String ar_description, en_description;
    String ar_symptoms, en_symptoms;
    String ar_reasons, en_reasons;
    String ar_treatment, en_treatment;
    String ar_resource, en_resource;


    public Disease() {
    }

    public Disease(String ar_name, String en_name, String ar_description, String en_description, String ar_symptoms, String en_symptoms, String ar_reasons, String en_reasons, String ar_treatment, String en_treatment, String ar_resource, String en_resource) {
        this.ar_name = ar_name;
        this.en_name = en_name;
        this.ar_description = ar_description;
        this.en_description = en_description;
        this.ar_symptoms = ar_symptoms;
        this.en_symptoms = en_symptoms;
        this.ar_reasons = ar_reasons;
        this.en_reasons = en_reasons;
        this.ar_treatment = ar_treatment;
        this.en_treatment = en_treatment;
        this.ar_resource = ar_resource;
        this.en_resource = en_resource;
    }

    public String getAr_resource() {
        return ar_resource;
    }

    public void setAr_resource(String ar_resource) {
        this.ar_resource = ar_resource;
    }

    public String getEn_resource() {
        return en_resource;
    }

    public void setEn_resource(String en_resource) {
        this.en_resource = en_resource;
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

    public String getAr_description() {
        return ar_description;
    }

    public void setAr_description(String ar_description) {
        this.ar_description = ar_description;
    }

    public String getEn_description() {
        return en_description;
    }

    public void setEn_description(String en_description) {
        this.en_description = en_description;
    }

    public String getAr_symptoms() {
        return ar_symptoms;
    }

    public void setAr_symptoms(String ar_symptoms) {
        this.ar_symptoms = ar_symptoms;
    }

    public String getEn_symptoms() {
        return en_symptoms;
    }

    public void setEn_symptoms(String en_symptoms) {
        this.en_symptoms = en_symptoms;
    }

    public String getAr_reasons() {
        return ar_reasons;
    }

    public void setAr_reasons(String ar_reasons) {
        this.ar_reasons = ar_reasons;
    }

    public String getEn_reasons() {
        return en_reasons;
    }

    public void setEn_reasons(String en_reasons) {
        this.en_reasons = en_reasons;
    }

    public String getAr_treatment() {
        return ar_treatment;
    }

    public void setAr_treatment(String ar_treatment) {
        this.ar_treatment = ar_treatment;
    }

    public String getEn_treatment() {
        return en_treatment;
    }

    public void setEn_treatment(String en_treatment) {
        this.en_treatment = en_treatment;
    }
}
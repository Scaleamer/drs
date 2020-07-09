package com.scaleamer.domain;

import java.util.Date;
/*
案例
*/
public class Case {
    private Integer case_id;
    private String patient_name;
    private String patient_id;
    private String patient_gender;
    private Date patient_birth_date;
    private String patient_native_place;
    private String disease_type;
    private Date disease_time;
    private Integer disease_place_id;
    private String disease_description;
    private int publisher_id;


    public Integer getCase_id() {
        return case_id;
    }

    public void setCase_id(Integer case_id) {
        this.case_id = case_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_gender() {
        return patient_gender;
    }

    public void setPatient_gender(String patient_gender) {
        this.patient_gender = patient_gender;
    }

    public Date getPatient_birth_date() {
        return patient_birth_date;
    }

    public void setPatient_birth_date(Date patient_birth_date) {
        this.patient_birth_date = patient_birth_date;
    }

    public String getPatient_native_place() {
        return patient_native_place;
    }

    public void setPatient_native_place(String patient_native_place) {
        this.patient_native_place = patient_native_place;
    }

    public String getDisease_type() {
        return disease_type;
    }

    public void setDisease_type(String disease_type) {
        this.disease_type = disease_type;
    }

    public Date getDisease_time() {
        return disease_time;
    }

    public void setDisease_time(Date disease_time) {
        this.disease_time = disease_time;
    }

    public Integer getDisease_place_id() {
        return disease_place_id;
    }

    public void setDisease_place_id(Integer disease_place_id) {
        this.disease_place_id = disease_place_id;
    }

    public String getDisease_description() {
        return disease_description;
    }

    public void setDisease_description(String disease_description) {
        this.disease_description = disease_description;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    @Override
    public String toString() {
        return "Case{" +
                "case_id=" + case_id +
                ", patient_name='" + patient_name + '\'' +
                ", patient_id='" + patient_id + '\'' +
                ", patient_gender='" + patient_gender + '\'' +
                ", patient_birth_date=" + patient_birth_date +
                ", patient_native_place='" + patient_native_place + '\'' +
                ", disease_type='" + disease_type + '\'' +
                ", disease_time=" + disease_time +
                ", disease_place_id=" + disease_place_id +
                ", disease_description='" + disease_description + '\'' +
                ", publisher_id=" + publisher_id +
                '}';
    }
}

package com.scaleamer.domain.statistics;

import com.scaleamer.utils.DateUtil;

import java.util.Date;

public class StatisticsPerPlaceDay {
    Date date;
    Long number;
    Integer placeId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public StatisticsPerPlaceDay(Date date, Long number, Integer placeId) {
        this.date = date;
        this.number = number;
        this.placeId = placeId;
    }

    public StatisticsPerPlaceDay() {
    }

    @Override
    public String toString() {
        return "StatisticsPerPlaceDay{" +
                "date=" + DateUtil.Date2String(date) +
                ", number=" + number +
                ", placeId=" + placeId +
                '}';
    }
}

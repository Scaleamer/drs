package com.scaleamer.service.syn;

import com.scaleamer.domain.statistics.StatisticsPerPlaceDay;

import java.util.Date;
import java.util.List;

public interface StatisticsPerDayPlaceService {
    void clearAll();
    void updateFromDataBase() throws InterruptedException;
    StatisticsPerPlaceDay getByPlaceIdAndDate(int place_id, Date date);
    List<StatisticsPerPlaceDay> getByPlaceId(int place_id);
    List<StatisticsPerPlaceDay> getByDate(Date date);
}

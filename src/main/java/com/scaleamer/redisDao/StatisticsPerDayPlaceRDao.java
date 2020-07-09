package com.scaleamer.redisDao;

import com.scaleamer.domain.statistics.StatisticsPerPlaceDay;

import java.util.Date;
import java.util.List;

/**
 * 按日划分
 */
public interface StatisticsPerDayPlaceRDao {
    List<StatisticsPerPlaceDay> getAllByPlaceId(int placeId);
    List<StatisticsPerPlaceDay> getAllByDate(Date date, List<Integer> placeIds);//致于为什么要id，因为redis储存结构问题决定了要id
    StatisticsPerPlaceDay getByPlaceIdAndDate(int placeId, Date date);
    void increaseStatisticsByPlace(int placeId, Date date);
    void decreaseStatisticsByPlace(int placeId, Date date);
    void clearByPlaceId(List<Integer> placeIds);
    void updateStatisticPerPlaceDay(List<StatisticsPerPlaceDay> statisticsPerPlaceDayList);
}

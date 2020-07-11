package com.scaleamer.service.syn.impl;

import com.scaleamer.dao.database.CaseMapper;
import com.scaleamer.dao.database.PlaceMapper;
import com.scaleamer.domain.statistics.StatisticsPerPlaceDay;
import com.scaleamer.dao.redisDao.StatisticsPerDayPlaceRDao;
import com.scaleamer.service.syn.StatisticsPerDayPlaceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RabbitListener
public class StatisticsPerDayPlaceServiceImpl implements StatisticsPerDayPlaceService {

    @Autowired
    private StatisticsPerDayPlaceRDao statisticsPerDayPlaceRDao;
    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private CaseMapper caseMapper;

    @Override
    public void clearAll() {
        List<Integer> ids = placeMapper.getIds();
        statisticsPerDayPlaceRDao.clearByPlaceId(ids);
    }

    @Override
    @Transactional
    public void updateFromDataBase() throws InterruptedException {

        List<StatisticsPerPlaceDay> statistics = caseMapper.getStatistics();
        Thread.sleep(10000);
        statisticsPerDayPlaceRDao.updateStatisticPerPlaceDay(statistics);
    }

    @Override
    public StatisticsPerPlaceDay getByPlaceIdAndDate(int place_id, Date date) {
        return statisticsPerDayPlaceRDao.getByPlaceIdAndDate(place_id, date);
    }

    @Override
    public List<StatisticsPerPlaceDay> getByPlaceId(int place_id) {
        return statisticsPerDayPlaceRDao.getAllByPlaceId(place_id);
    }

    @Override
    public List<StatisticsPerPlaceDay> getByDate(Date date) {
        List<Integer> ids = placeMapper.getIds();
        return statisticsPerDayPlaceRDao.getAllByDate(date, ids);
    }
}

package com.scaleamer.service.async.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaleamer.dao.CaseMapper;
import com.scaleamer.domain.Case;
import com.scaleamer.redisDao.StatisticsPerDayPlaceRDao;

import com.scaleamer.service.async.CaseServiceAsync;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class CaseServiceAsyncImpl implements CaseServiceAsync, MessageListener {
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private StatisticsPerDayPlaceRDao statisticsByPlaceDao;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void addCase(Case the_case) {
        caseMapper.addCase(the_case);
        statisticsByPlaceDao.increaseStatisticsByPlace(the_case.getDisease_place_id(), the_case.getDisease_time());
    }

    @Override
    public void onMessage(Message message) {
        byte[] body = message.getBody();
        try {
            Case the_case = objectMapper.readValue(body, Case.class);
//            System.out.println("CONSUMER");
//            System.out.println(the_case);
            addCase(the_case);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

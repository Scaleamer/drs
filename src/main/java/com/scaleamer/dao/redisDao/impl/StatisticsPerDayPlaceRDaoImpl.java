package com.scaleamer.dao.redisDao.impl;

import com.scaleamer.domain.statistics.StatisticsPerPlaceDay;
import com.scaleamer.dao.redisDao.StatisticsPerDayPlaceRDao;
import com.scaleamer.utils.DateUtil;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采用hash
 */
@Repository
public class StatisticsPerDayPlaceRDaoImpl implements StatisticsPerDayPlaceRDao {
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<StatisticsPerPlaceDay> getAllByPlaceId(int placeId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        Map<String, String> entries = hashOperations.entries(getKey(placeId));

        long size_long = hashOperations.size(getKey(placeId));
        int size_int  = (int)size_long;
        List<StatisticsPerPlaceDay> resultList = new ArrayList<>(size_int);

        for(Map.Entry<String, String> entry : entries.entrySet()){
            Date date = DateUtil.String2Date(entry.getKey());
            String result =  entry.getValue();
            resultList.add(new StatisticsPerPlaceDay(date, String2Long(result), placeId));
        }

        return resultList;
    }

    @Override
    public List<StatisticsPerPlaceDay> getAllByDate(Date date, List<Integer>placeIds) {
        List<StatisticsPerPlaceDay> resultList = new ArrayList<>(placeIds.size());
        HashOperations<String, String, String> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        for(Integer id: placeIds){
            String num = stringObjectObjectHashOperations.get(getKey(id), DateUtil.Date2String(date));
            resultList.add(new StatisticsPerPlaceDay(date,String2Long(num), id));
        }
        return resultList;
    }

    @Override
    public StatisticsPerPlaceDay getByPlaceIdAndDate(int placeId, Date date) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String result =  hashOperations.get(getKey(placeId), getHashKey(date));

        return new StatisticsPerPlaceDay(date,String2Long(result), placeId);
    }

    @Override
    public void increaseStatisticsByPlace(int placeId, Date date) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.increment(getKey(placeId),getHashKey(date),1L);
    }

    @Override
    public void decreaseStatisticsByPlace(int placeId, Date date) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.increment(getKey(placeId),getHashKey(date),-1L);
    }

    @Override
    public void clearByPlaceId(List<Integer>placeIds) {
        for(Integer id: placeIds){
            redisTemplate.delete(getKey(id));
        }
    }

    @Override
    public void updateStatisticPerPlaceDay(List<StatisticsPerPlaceDay> statisticsPerPlaceDayList) {
        HashOperations<String, String, String> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        for(StatisticsPerPlaceDay statisticsPerPlaceDay: statisticsPerPlaceDayList){
            stringObjectObjectHashOperations.put(getKey(statisticsPerPlaceDay.getPlaceId()),
                    getHashKey(statisticsPerPlaceDay.getDate()),
                    String.valueOf(statisticsPerPlaceDay.getNumber()));
        }
    }


    private static String getKey(int placeId){
        return "statistics:nums_per_place:" + placeId;
    }

    private static String getHashKey(Date date){
        return DateUtil.Date2String(date);
    }
    
    private static Long String2Long(String str){
        if(str==null){
            return 0L;
        }
        return Long.valueOf(str);
    }
}

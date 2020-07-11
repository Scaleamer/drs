package com.scaleamer.service.syn.impl;

import com.scaleamer.dao.cache.PlaceCacheDao;
import com.scaleamer.domain.Place;
import com.scaleamer.service.syn.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 当一个结果不存在的时候做法可能是 也存入redis服务器，但是设置一个过期的时间
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceCacheDao placeCacheDao;

    @Override
    public List<Place> getPlaces() {
        return placeCacheDao.getPlaces();
    }

    @Override
    public Place getPlaceById(int place_id) {
        return placeCacheDao.getPlaceById(place_id);
    }

    @Override
    public void insertPlace(Place place) {
        if(placeCacheDao.insertPlace(place)<=0){
            throw new RuntimeException("插入Place失败了");
        }
    }

    @Override
    public void deletePlaceById(int id) {
        if(placeCacheDao.deletePlaceById(id)<=0){
            throw new RuntimeException("删除Place失败了");
        }
    }

    @Override
    public void modifyPlace(Place place) {
        if(placeCacheDao.modifyPlace(place)<=0){
            throw new RuntimeException("更新Place失败了");
        }
    }


}

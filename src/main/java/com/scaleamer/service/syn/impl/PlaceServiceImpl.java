package com.scaleamer.service.syn.impl;

import com.scaleamer.cache.PlaceCacheDao;
import com.scaleamer.domain.Place;
import com.scaleamer.service.syn.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

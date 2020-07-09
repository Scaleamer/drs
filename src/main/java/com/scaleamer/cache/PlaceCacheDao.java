package com.scaleamer.cache;


import com.scaleamer.dao.PlaceMapper;
import com.scaleamer.domain.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaceCacheDao implements PlaceMapper {

    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Place> getPlaces() {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        List<Place> result = (List<Place>) stringObjectValueOperations.get(keyOfPlaces());
        if(result==null){
            result = placeMapper.getPlaces();
            stringObjectValueOperations.set(keyOfPlaces(),result);
        }
        return result;
    }

    @Override
    public Place getPlaceById(int place_id) {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        Place place = (Place) stringObjectValueOperations.get(keyOfPlace(place_id));
        if(place==null){
            place = placeMapper.getPlaceById(place_id);
            stringObjectValueOperations.set(keyOfPlace(place_id),place);
        }
        return place;
    }

    @Override
    public List<Integer> getIds() {
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        List<Integer> list = (List<Integer>) stringObjectValueOperations.get(keyOfIds());
        if(list==null){
            list = placeMapper.getIds();
            stringObjectValueOperations.set(keyOfIds(),list);
        }
        return list;
    }

    private String keyOfPlaces(){
        return "place:places";
    }

    private String keyOfPlace(int place_id){
        return "place:place:" + place_id;
    }

    private String keyOfIds(){
        return "place:ids";
    }
}

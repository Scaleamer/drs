package com.scaleamer.dao.database;


import com.scaleamer.domain.Place;

import java.util.List;

public interface PlaceMapper {
    List<Place> getPlaces();
    Place getPlaceById(int place_id);
    List<Integer> getIds();
    int insertPlace(Place place);
    int deletePlaceById(int id);
    int modifyPlace(Place place);
}

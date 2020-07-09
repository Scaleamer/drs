package com.scaleamer.dao;


import com.scaleamer.domain.Place;

import java.util.List;

public interface PlaceMapper {
    List<Place> getPlaces();
    Place getPlaceById(int place_id);
    List<Integer> getIds();
}

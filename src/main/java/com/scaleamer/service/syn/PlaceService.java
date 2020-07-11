package com.scaleamer.service.syn;

import com.scaleamer.domain.Place;

import java.util.List;

public interface PlaceService {
    List<Place> getPlaces();
    Place getPlaceById(int place_id);
    void insertPlace(Place place);
    void deletePlaceById(int id);
    void modifyPlace(Place place);
}

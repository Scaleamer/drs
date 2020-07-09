package com.scaleamer.service.syn;

import com.scaleamer.domain.Place;

import java.util.List;

public interface PlaceService {
    List<Place> getPlaces();
    Place getPlaceById(int place_id);
}

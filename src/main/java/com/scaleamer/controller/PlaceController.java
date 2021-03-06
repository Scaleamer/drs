package com.scaleamer.controller;

import com.scaleamer.domain.Place;
import com.scaleamer.service.syn.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/place")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @RequestMapping(value = "/getPlaces", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getPlaces(){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            List<Place> places = placeService.getPlaces();
            resultMap.put("success",true);
            resultMap.put("places",places);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/getPlaceById", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getPlaceById(@RequestParam(value = "place_id", required = true)int place_id){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            Place place = placeService.getPlaceById(place_id);
            resultMap.put("success",true);
            resultMap.put("places",place);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/addPlace", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addPlace(@RequestParam(value = "place_name", required = true)String placeName){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            Place place = new Place();
            place.setPlace_name(placeName);
            placeService.insertPlace(place);
            resultMap.put("success",true);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/deletePlace", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> deletePlace(@RequestParam(value = "place_id", required = true)int place_id){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            placeService.deletePlaceById(place_id);
            resultMap.put("success",true);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/modifyPlace", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyPlace(@RequestParam(value = "place_name", required = true)String placeName,
                                            @RequestParam(value = "place_id", required = true)int place_id){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            Place place = new Place();
            place.setPlace_name(placeName);
            place.setPlace_id(place_id);
            placeService.modifyPlace(place);
            resultMap.put("success",true);
            return resultMap;
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }
}

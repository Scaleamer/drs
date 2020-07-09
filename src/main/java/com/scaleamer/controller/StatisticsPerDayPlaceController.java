package com.scaleamer.controller;

import com.scaleamer.domain.statistics.StatisticsPerPlaceDay;
import com.scaleamer.service.syn.StatisticsPerDayPlaceService;
import com.scaleamer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statisticsPerDayPlace")
public class StatisticsPerDayPlaceController {
    @Autowired
    private StatisticsPerDayPlaceService statisticsPerDayPlaceService;

    @RequestMapping("/getByPlaceIdAndDate")
    @ResponseBody
    private Map<String, Object> getByPlaceIdAndDate(@RequestParam(name = "place_id",required = true) int place_id,
                                                    @RequestParam(name = "date",required = true)String date_str){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            Date date = DateUtil.String2Date(date_str);
            StatisticsPerPlaceDay data = statisticsPerDayPlaceService.getByPlaceIdAndDate(place_id, date);
            resultMap.put("success",true);
            resultMap.put("data", data);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping("/getByPlaceId")
    @ResponseBody
    private Map<String, Object> getByPlaceId(@RequestParam(name = "place_id",required = true) int place_id){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            List<StatisticsPerPlaceDay> data = statisticsPerDayPlaceService.getByPlaceId(place_id);
            resultMap.put("success",true);
            resultMap.put("data", data);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping("/getByDate")
    @ResponseBody
    private Map<String, Object> getByPlaceId(@RequestParam(name = "date",required = true)String date_str){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            Date date = DateUtil.String2Date(date_str);
            List<StatisticsPerPlaceDay> data = statisticsPerDayPlaceService.getByDate(date);
            resultMap.put("success",true);
            resultMap.put("data", data);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("errMsg", e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/clearAll", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> clearAll(){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            statisticsPerDayPlaceService.clearAll();
            resultMap.put("success",true);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }

    @RequestMapping(value = "/renew", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> renew(){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            statisticsPerDayPlaceService.clearAll();
            statisticsPerDayPlaceService.updateFromDataBase();
            resultMap.put("success",true);
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("success",false);
            resultMap.put("errMsg",e.getMessage());
            return resultMap;
        }
    }
}

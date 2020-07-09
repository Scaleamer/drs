package com.scaleamer.controller;

import com.scaleamer.domain.Case;
import com.scaleamer.domain.User;
import com.scaleamer.service.syn.CaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("CaseController")
@RequestMapping("/case")
public class CaseController {

    @Autowired
    public CaseService caseService;

    //增加案例
    @RequestMapping(value="/addCase",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String
    addCase(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String patient_name= request.getParameter("patient_name");
        String patient_id=request.getParameter("patient_id");
        String patient_gender=request.getParameter("patient_gender");
        String patient_birth_date= request.getParameter("patient_birth_date");
        String patient_native_place=request.getParameter("patient_native_place");
        String disease_type=request.getParameter("disease_type");
        String disease_time= request.getParameter("disease_time");
        String disease_place_id=request.getParameter("disease_place_id");
        String disease_description=request.getParameter("disease_description");
//        String publisher_id=request.getParameter("publisher_id");
        int publisher_id = ((User)SecurityUtils.getSubject().getPrincipal()).getUser_id();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Case the_case=new Case();
        the_case.setPatient_name(patient_name);
        the_case.setPatient_id(patient_id);
        the_case.setPatient_gender(patient_gender);
        the_case.setPatient_birth_date(format.parse(patient_birth_date));
        the_case.setPatient_native_place(patient_native_place);
        the_case.setDisease_type(disease_type);
        the_case.setDisease_time(format.parse(disease_time));
        the_case.setDisease_place_id(Integer.valueOf(disease_place_id));
        the_case.setDisease_description(disease_description);
        the_case.setPublisher_id(publisher_id);
        System.out.println(the_case);
        String code="";
        String msg="";
        //调用service方法
        try{
            caseService.addCase(the_case);
            code="1000";
            msg="添加成功";
        }catch (Exception e){
            e.printStackTrace();
            code="1001";
            msg="添加失败";
            the_case=null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("case",the_case);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(format);
        String resultString = "";

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    //删除案例
    @RequestMapping(value="/deleteCase",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String
    deleteCase(String case_id,HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        String code="";
        String msg="";
        User operator = ((User)SecurityUtils.getSubject().getPrincipal());
        //调用service方法
        try{

            caseService.deleteCase(Integer.valueOf(case_id));
            code="1000";
            msg="删除成功";
        }catch (Exception e){
            e.printStackTrace();
            code="1001";
            msg="删除失败";

        }
        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);

        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    //修改案例
    @RequestMapping(value="/updateCase",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String
    updateCase(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String case_id= request.getParameter("case_id");
        String patient_name= request.getParameter("patient_name");
        String patient_id=request.getParameter("patient_id");
        String patient_gender=request.getParameter("patient_gender");
        String patient_birth_date= request.getParameter("patient_birth_date");
        String patient_native_place=request.getParameter("patient_native_place");
        String disease_type=request.getParameter("disease_type");
        String disease_time= request.getParameter("disease_time");
        String disease_place_id=request.getParameter("disease_place_id");
        String disease_description=request.getParameter("disease_description");
        //String publisher_id=request.getParameter("publisher_id");

        User operator = ((User)SecurityUtils.getSubject().getPrincipal());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Case the_case=new Case();
        the_case.setCase_id(Integer.valueOf(case_id));
        the_case.setPatient_name(patient_name);
        the_case.setPatient_id(patient_id);
        the_case.setPatient_gender(patient_gender);
        if(patient_birth_date!=null){
            the_case.setPatient_birth_date(format.parse(patient_birth_date));
        }
        the_case.setPatient_native_place(patient_native_place);
        the_case.setDisease_type(disease_type);
        if(disease_time!=null){
            the_case.setDisease_time(format.parse(disease_time));
        }
        the_case.setDisease_place_id(Integer.valueOf(disease_place_id));
        the_case.setDisease_description(disease_description);
//        if(publisher_id!=null){
//            the_case.setPublisher_id(operator.getUser_id());
//        }

        String code="";
        String msg="";
        //调用service方法
        try{
            caseService.updateCase(the_case);
            code="1000";
            msg="修改成功";
            //更新要返回的case数据
            the_case=caseService.findCase(the_case.getCase_id());
        }catch (Exception e){
            e.printStackTrace();
            code="1001";
            msg="修改失败";
            the_case=null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("case",the_case);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(format);
        String resultString = "";

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    //查询单个案例
    @RequestMapping(value="/findCase",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String
    findCase(String case_id,HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String code="";
        String msg="";
        //调用service方法
        Case the_case=caseService.findCase(Integer.valueOf(case_id));
        if(the_case!=null){
            code="1000";
            msg="查询成功";
        }else{
            code="1001";
            msg="查询失败";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("case",the_case);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(format);
        String resultString = "";

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    //按条件查询多个案例
    @RequestMapping(value="/findMultiCase",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String
    findMultiCase(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        String patient_name= request.getParameter("patient_name");
        String patient_id=request.getParameter("patient_id");
        String patient_gender=request.getParameter("patient_gender");
        String patient_native_place=request.getParameter("patient_native_place");
        String disease_type=request.getParameter("disease_type");
        String disease_place=request.getParameter("disease_place");

        Map<String,String>conditionMap=new HashMap<>();
        conditionMap.put("patient_name",patient_name);
        conditionMap.put("patient_id",patient_id);
        conditionMap.put("patient_gender",patient_gender);
        conditionMap.put("patient_native_place",patient_native_place);
        conditionMap.put("disease_type",disease_type);
        conditionMap.put("disease_place",disease_place);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String code="";
        String msg="";
        int record_num=0;
        //调用service方法
        List<Case> cases=caseService.findMultiCase(conditionMap);
        record_num=cases.size();
        if(record_num>0){
            code="1000";
            msg="查询成功";

        }else{
            code="1001";
            msg="未查询到匹配的数据";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("record_num",record_num);
        map.put("cases",cases);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(format);
        String resultString = "";

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    //查询所有案例
    @RequestMapping(value="/findAllCase",produces = "application/json;charset=UTF-8")
    public @ResponseBody
    String
    findAllCase(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String code="";
        String msg="";
        int record_num=0;
        //调用service方法
        List<Case> cases=caseService.findAllCase();
        record_num=cases.size();
        if(record_num>0){
            code="1000";
            msg="查询成功";

        }else{
            code="1001";
            msg="未查询到匹配的数据";
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        map.put("record_num",record_num);
        map.put("cases",cases);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(format);
        String resultString = "";

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}

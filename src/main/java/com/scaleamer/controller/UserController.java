package com.scaleamer.controller;

import com.scaleamer.domain.User;
import com.scaleamer.service.syn.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller("UserController")
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;

    //注册
    @RequestMapping(value="/register",method = RequestMethod.POST)
    @ResponseBody
    public  String register( HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username= request.getParameter("username");
        String email_addr=request.getParameter("email_addr");
        String password=request.getParameter("password");
        String role_id=request.getParameter("role_id");
        User user=new User();
        user.setUsername(username);
        user.setEmail_addr(email_addr);
        user.setPassword(password);
        String code="";
        String msg="";
        System.out.println(user);
        //调用service方法
        try{
            userService.register(user, Integer.parseInt(role_id));
            code="1000";
            msg="注册成功";
        }catch (Exception e){
            e.printStackTrace();
            code="1001";
            msg="注册失败，可能账号已注册过,也可能是role不对嗷";
            user=null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
//        map.put("user",user);

        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";

        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
    }
    //登录
    @RequestMapping(value="/login",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public  String login( HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email_addr=request.getParameter("email_addr");
        String password=request.getParameter("password");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email_addr, password);

        try {
            //3 让 shiro 框架进行登录验证：
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return "loginError";
        }
        return "redirect:/user/index";
            //调用service方法
//        int state=userService.login(email_addr,password, user);
//        String code="";
//        String msg="";
//        if(state==1){
//            //登录成功
//            code="1000";
//            msg="登录成功";
//        }else if(state==2){
//            //账号不存在
//            code="1001";
//            msg="账号不存在";
//        }else{
//            //密码错误
//            code="1002";
//            msg="密码错误";
//        }
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("code",code);
//        map.put("msg",msg);
//        map.put("user",user);
//        ObjectMapper mapper = new ObjectMapper();
//        String resultString = "";
//
//        try {
//            resultString = mapper.writeValueAsString(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return resultString;
    }

    @RequestMapping("/logout")
    public String shirologout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject.isAuthenticated()) {
                subject.logout();
                //登出成功
                return "redirect:/login.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/user/login";
    }

}

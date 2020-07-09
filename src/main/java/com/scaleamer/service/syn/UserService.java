package com.scaleamer.service.syn;

import com.scaleamer.domain.User;

import java.util.List;

public interface UserService {

    //注册
    public void register(User user, int role_id);

    //登录 返回操作结果 1：登录成功 2：账号不存在 3：密码错误 User参数用于存放成功登录的用户
    public int login(String email_addr, String password, User user);


}

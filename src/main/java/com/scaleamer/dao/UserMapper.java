package com.scaleamer.dao;

import com.scaleamer.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserMapper {

    //新增用户
    public void addUser(User user);

    //查找用户--用于登录
    public User findUser(String email_addr);

    //查询所有用户接口
    public List<User> findAll();

    void addRole(@Param("user") User user, @Param("role_id") int role_id);
    User getUserByEmailAddr(@Param("emailAddr") String emailAddr);
    User getUserById(@Param("id") int id);
    User getUserByIdLite(@Param("id") int id);
}

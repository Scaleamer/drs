package com.scaleamer.service.syn.impl;

import com.scaleamer.dao.database.UserMapper;
import com.scaleamer.domain.User;
import com.scaleamer.service.syn.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired()
    private UserMapper userMapper;
    private SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();

    @Override
    @Transactional
    public void register(User user,int role_id) {
        String salt = secureRandomNumberGenerator.nextBytes().toHex();
        String cipherText = new Md5Hash(user.getPassword(),salt).toString();
        user.setSalt(salt);
        user.setPassword(cipherText);
        userMapper.addUser(user);
        userMapper.addRole(user,role_id);
        //可以在这里用role参数来设置该用户的权限
    }

    @Deprecated
    @Override
    public int login(String email_addr, String password,User user) {
        User got_user=userMapper.findUser(email_addr);
        if(got_user==null){
            //该账号不存在
            return 2;
        }else{
            if(got_user.getPassword().equals(password)){
                //找到对应用户，登录成功
                user.setEmail_addr(got_user.getEmail_addr());
                user.setPassword(got_user.getPassword());
                user.setUsername(got_user.getUsername());
                user.setUser_id(got_user.getUser_id());
                return 1;
            }else{
                //密码错误
                return 3;
            }
        }
    }


}

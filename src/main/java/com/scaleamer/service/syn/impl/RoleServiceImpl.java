package com.scaleamer.service.syn.impl;

import com.scaleamer.dao.RoleMapper;
import com.scaleamer.domain.Role;
import com.scaleamer.service.syn.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoleList(int user_id) {
        return roleMapper.getByUserId(user_id);
    }
}

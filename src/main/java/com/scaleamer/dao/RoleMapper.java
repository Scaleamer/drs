package com.scaleamer.dao;

import com.scaleamer.domain.Role;

import java.util.List;

public interface RoleMapper {

    List<Role> getByUserId(int user_id);
}

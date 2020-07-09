package com.scaleamer.service.syn;

import com.scaleamer.domain.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoleList(int user_id);
}

package com.scaleamer.dao.database;

import com.scaleamer.domain.Permission;
import com.scaleamer.domain.Role;

import java.util.List;

public interface PermissionMapper {
    List<Permission> getByUserId(int user_id);
}

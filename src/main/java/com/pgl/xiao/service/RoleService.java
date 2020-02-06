package com.pgl.xiao.service;

import com.pgl.xiao.domain.Role;


import java.util.Set;

public interface RoleService extends BasicService<Role> {
    Set<String> listRoles(String userName);
}

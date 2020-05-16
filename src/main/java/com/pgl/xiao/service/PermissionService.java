package com.pgl.xiao.service;

import com.pgl.xiao.domain.Permission;

import java.util.Set;

public interface PermissionService extends BasicService<Permission> {
	
    Set<String> listPermissions(String userName);
    
}

package com.pgl.xiao.service;

import com.pgl.xiao.domain.User;

import java.util.List;

public interface UserService extends BasicService<User> {

    User getUserByloginnameAndPasswd(String loginname, String passwd);

    User getUserByUsername(String username);

    List<String> getPermissionsByUser(String username);

    String getPassword(String loginname);

}

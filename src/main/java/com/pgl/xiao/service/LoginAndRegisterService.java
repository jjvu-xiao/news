package com.pgl.xiao.service;

import com.pgl.xiao.domain.Customer;

/**
 * 普通用户、系统管理员的注册、登录、注销
 */
public interface LoginAndRegisterService {

    // 用户的登录操作
    boolean login(Customer customer);

    // 用户的注册操作
    boolean register(Customer customer);

    // 用户的注销操作
    boolean logout(Customer customer);

    // 管理员的登录操作
    boolean loginAdmin(String username, String passwd);

}

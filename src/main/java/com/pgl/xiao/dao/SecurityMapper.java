package com.pgl.xiao.dao;

import com.pgl.xiao.domain.Menu;
import com.pgl.xiao.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecurityMapper {

    //  通过用户找到所属的菜单
    public List<Menu> getMenusByUser(Integer userId);

    // 后台用户登录
    public User getUserByLoginnameAndPasswd(@Param("loginname") String loginname, @Param("passwd") String passwd);

    // 通过角色找到所属的菜单
    public List<Menu> getMenusByRole(Integer roleId);

    // 新增角色的菜单
    public int updateRoleMenu(Integer rid, Integer pid);
}

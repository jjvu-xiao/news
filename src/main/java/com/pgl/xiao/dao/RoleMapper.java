package com.pgl.xiao.dao;

import com.pgl.xiao.domain.Role;
import com.pgl.xiao.domain.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 权限相关的实体类
 * 有自定义的方法
 */
public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> listRolesByUserName(String userName);
}
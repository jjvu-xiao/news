package com.pgl.xiao.dao;

import com.pgl.xiao.domain.Permission;
import com.pgl.xiao.domain.PermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 关于权限的实体类
 * 有自定义方法
 */
public interface PermissionMapper {
    long countByExample(PermissionExample example);

    int deleteByExample(PermissionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);

    Permission selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionExample example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> listPermissionsByUserName(String userName);
}
package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.PermissionMapper;
import com.pgl.xiao.domain.Permission;
import com.pgl.xiao.domain.PermissionExample;
import com.pgl.xiao.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper mapper;

    @Override
    public Permission findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Permission type) {
        mapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(Permission type) {
        mapper.updateByPrimaryKeySelective(type);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<Permission> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        PermissionExample example = new PermissionExample();
        PermissionExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(indexPage, maxRows);
        List<Permission> datas = mapper.selectByExample(example);
        PageInfo<Permission> info = new PageInfo<>(datas);
        return info;
    }

    @Override
    public List<Permission> findAll() {
        return null;
    }

    @Override
    public Set<String> listPermissions(String userName) {
        List<Permission> permissions = mapper.listPermissionsByUserName(userName);
        Set<String> result = new HashSet<>();
        for (Permission permission: permissions) {
            result.add(permission.getName());
        }
        return result;
    }
}

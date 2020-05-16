package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.RoleMapper;
import com.pgl.xiao.domain.Role;
import com.pgl.xiao.domain.RoleExample;
import com.pgl.xiao.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper mapper;

    @Override
    public Role findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Role type) {
        mapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(Role type) {
        mapper.updateByPrimaryKeySelective(type);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<Role> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(indexPage, maxRows);
        List<Role> datas = mapper.selectByExample(example);
        PageInfo<Role> info = new PageInfo<>(datas);
        return info;
    }

    @Override
    public List<Role> findAll() {
        return null;
    }

    @Override
    public Set<String> listRoles(String userName) {
        List<Role> roles = mapper.listRolesByUserName(userName);
        Set<String> result = new HashSet<>();
        for (Role role: roles) {
            result.add(role.getName());
        }
        return result;
    }
}

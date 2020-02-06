package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.PermissionMapper;
import com.pgl.xiao.dao.UserMapper;
import com.pgl.xiao.domain.User;
import com.pgl.xiao.domain.UserExample;
import com.pgl.xiao.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public User findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(User type) {
        mapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(User type) {
        mapper.updateByPrimaryKeySelective(type);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<User> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        PageHelper.startPage(indexPage, maxRows);
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        String name, loginame, passwd, description;
        Integer status;
        if (!StringUtils.isEmpty((CharSequence) params.get("name"))) {
            name = params.get("name").toString();
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("loginname"))) {
            loginame = params.get("loginname").toString();
            criteria.andLoginnameLike("%" + loginame + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("passwd"))) {
            passwd = params.get("passwd").toString();
            criteria.andPasswdLike("%" + passwd + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("description"))) {
            description = params.get("description").toString();
            criteria.andDescriptionLike("%" + description + "%");
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("status"))) {
            status = new Integer(params.get("status").toString());
            criteria.andStatusEqualTo(status);
        }
        List<User> users = mapper.selectByExample(example);
        PageInfo<User> info = new PageInfo<>(users);
        return info;
    }


    @Override
    public List<User> findAll() {
        return mapper.selectByExample(null);
    }

    @Override
    public User getUserByloginnameAndPasswd(String loginname, String passwd) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginnameEqualTo(loginname);
        criteria.andPasswdEqualTo(passwd);
        List<User> users = mapper.selectByExample(example);
        if (users.isEmpty())
            return null;
        return users.get(0);
    }

    /**
     * 通过递归算法生成菜单树
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username);
        return mapper.selectByExample(example).get(0);
    }

    @Override
    public List<String> getPermissionsByUser(String username) {
        if (username == null)
            return null;
        List<String> list = new ArrayList<>();
        User user = getUserByUsername(username);
//        for (Userrole userRole : user.getUserRoles()) {
//            Role role = userRole.getRole();
//            PermissionExample example = new PermissionExample();
//            PermissionExample.Criteria criteria = example.createCriteria();
//            criteria.andRoleidEqualTo(role.getId());
//            List<Permission> permissions = permissionMapper.selectByExample(example);
//            for (Permission p : permissions) {
//                list.add(p.getUrl());
//            }
//        }
        return list;
    }

    @Override
    public String getPassword(String loginname) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andLoginnameEqualTo(loginname);
        User u = mapper.selectByExample(example).get(0);
        if (null == u)
            return null;
        return u.getPasswd();
    }
}

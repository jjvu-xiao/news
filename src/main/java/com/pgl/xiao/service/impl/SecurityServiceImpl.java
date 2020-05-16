package com.pgl.xiao.service.impl;

import com.pgl.xiao.core.ui.EasyUIOptionalTreeNode;
import com.pgl.xiao.dao.MenuMapper;
import com.pgl.xiao.dao.SecurityMapper;
import com.pgl.xiao.domain.Menu;
import com.pgl.xiao.domain.MenuExample;
import com.pgl.xiao.domain.User;
import com.pgl.xiao.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private SecurityMapper securityMapper;

    @Resource
    private JedisPool jedisPool;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Menu> getMenusByUser(Integer userId) {
        List<Menu> oneMenu;
        List<Menu> menus = securityMapper.getMenusByUser(userId);
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidIsNull();
        oneMenu = menuMapper.selectByExample(example);
        for (Menu temp : oneMenu) {
            addMenuitem(temp, menus);
        }
        logger.info("菜单数据：" + oneMenu);
        return oneMenu;
    }

    @Override
    public List<Menu> getMenusByRole(Integer roleId) {
        List<Menu> oneMenu;
        List<Menu> menus = securityMapper.getMenusByRole(roleId);
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidIsNull();
        oneMenu = menuMapper.selectByExample(example);
        for (Menu temp : oneMenu) {
            temp.setChecked(false);
            setMenuitemChecked(temp, menus);
        }
        logger.info("菜单数据：" + oneMenu);
        return oneMenu;
    }

    private void addMenuitem(Menu menu, List<Menu> menuList) {
        MenuExample example  = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(menu.getId());
        List<Menu> menus = menuMapper.selectByExample(example);
        for(Menu temp: menus) {
            if (menuList.contains(temp)) {
                menu.setChildren(menus);
                addMenuitem(temp, menuList);
            }
        }
    }

    private void setMenuitemChecked(Menu menu, List<Menu> menuList) {
        MenuExample example  = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(menu.getId());
        List<Menu> menus = menuMapper.selectByExample(example);
        for(Menu temp: menus) {
            if (menuList.contains(temp)) {
                menu.setChecked(true);
            }
            menu.setChildren(menus);
            setMenuitemChecked(temp, menuList);
        }
    }
}

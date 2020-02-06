package com.pgl.xiao.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.MenuMapper;
import com.pgl.xiao.domain.Menu;
import com.pgl.xiao.domain.MenuExample;
import com.pgl.xiao.service.MenuService;
import com.pgl.xiao.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper mapper;

    @Resource
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Menu findById(Integer id) {
        return mapper.selectByPrimaryKey(id.toString());
    }

    @Override
    public boolean save(Menu type) {
        mapper.insertSelective(type);
        redisUtil.del("menu_list");
        return true;
    }

    @Override
    public boolean update(Menu type) {
        mapper.updateByPrimaryKeySelective(type);
        redisUtil.del("menu_list");
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id.toString());
        redisUtil.del("menu_list");
        return true;
    }

    @Override
    public PageInfo<Menu> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        PageHelper.startPage(indexPage, maxRows);
        MenuExample example = new MenuExample();
        List<Menu> datas = mapper.selectByExample(example);
        PageInfo<Menu> info = new PageInfo<>(datas);
        return info;
    }

    /**
     * 递归算法生成菜单栏
     * 先读取Redis数据库中是否有缓存，有缓存就会优先使用缓存数据
     * 当Redis中无缓存，就会进行一次查询操作
     * @return
     */
    @Override
    public List<Menu> findAll() {
        List<Menu> oneMenu = null;
        try {
            // 缓存中得到管理菜单栏的json数据
            String jsonMenus = (String) redisUtil.get("menu_list");
            oneMenu = JSON.parseArray(jsonMenus, Menu.class);
            logger.info("使用缓存数据");
            if (!oneMenu.isEmpty())
                return oneMenu;
        } catch (Exception e) {
            logger.info("缓存中无管理菜单栏");
        }
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidIsNull();
        oneMenu = mapper.selectByExample(example);
        for (Menu temp : oneMenu) {
            addMenuitem(temp);
        }
        String jsonMenu = JSONObject.toJSONString(oneMenu);
        redisUtil.set("menu_list", jsonMenu);
        return oneMenu;
    }

    private void addMenuitem(Menu menu) {
        MenuExample example  = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(menu.getId());
        List<Menu> menus = mapper.selectByExample(example);
        for(Menu temp: menus) {
            menu.setChildren(menus);
            addMenuitem(temp);
        }
    }
}

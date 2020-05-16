package com.pgl.xiao.service;

import com.pgl.xiao.core.ui.EasyUIOptionalTreeNode;
import com.pgl.xiao.domain.Menu;

import java.util.List;

public interface SecurityService {

    public List<Menu> getMenusByUser(Integer userId);

    public List<Menu> getMenusByRole(Integer roleId);
}

package com.pgl.xiao.core.security;

import com.pgl.xiao.dao.SecurityMapper;
import com.pgl.xiao.domain.Menu;
import com.pgl.xiao.domain.User;
import com.pgl.xiao.service.MenuService;
import com.pgl.xiao.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class NewsRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private MenuService menusService;

    @Resource
    private SecurityMapper securityMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 授权方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.debug("授权中");
        //获取当前登录的用户
        User user = (User) principals.getPrimaryPrincipal();
        // 获取当前用户能访问得菜单
        List<Menu> menus = securityMapper.getMenusByUser(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Menu menu : menus) {
            info.addStringPermission(menu.getText());
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.debug("认证中");
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String pwd = new String(upt.getPassword());
        // 根据用户名和密码查找用户
        User user = userService.getUserByloginnameAndPasswd(upt.getUsername(), pwd);
        if (user != null) {
            //返回认证信息
            //参数1：主角，就是登陆的用户
            //参数2：证书，就是凭证，对应密码
            //参数3：当前realm的名称
            return new SimpleAuthenticationInfo(user, pwd, getName());
        }
        return null;
    }
}

package com.pgl.xiao.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.pgl.xiao.domain.User;
import com.pgl.xiao.service.MenuService;
import com.pgl.xiao.service.UserService;
import com.pgl.xiao.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemController {

    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 管理员登录
     * @param user  管理员登录号与密码
     * @return
     */
    @PostMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public Object loginAdmin(@RequestBody User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getPasswd());
        HashMap<String, Object> msg = new HashMap<>();
        try {
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute("subject", subject);
            msg.put("suc", true);
        } catch (AuthenticationException e) {
            msg.put("error", "验证失败");
        }
        return msg;
    }

    @GetMapping
    public Object getUserByToken(String token) {
        Map<String, Object> message = new HashMap<>();
        String szJson = (String) redisUtil.get(token);
        if (!StringUtils.isEmpty(szJson)) {
            User user = (User) JSONUtils.parse(szJson);
            redisUtil.expire(token, 3000);
        }
        return message;
    }

    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public Object getMenus() {
        return menuService.findAll();
    }
}

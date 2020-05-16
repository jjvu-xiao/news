package com.pgl.xiao.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.pgl.xiao.dao.SecurityMapper;
import com.pgl.xiao.domain.User;
import com.pgl.xiao.service.MenuService;
import com.pgl.xiao.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
public class SystemController {

    @Resource
    private MenuService menuService;

    @Resource
    private JedisPool jedisPool;

    @Resource
    private SecurityMapper securityMapper;

    /**
     * 管理员登录
     * @param user  管理员登录号与密码
     * @return
     */
    @PostMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
    public Object loginAdmin(@RequestBody User user, HttpServletResponse resp) {
        Subject subject = SecurityUtils.getSubject();
        Jedis jedis = jedisPool.getResource();
        // 创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginname(), user.getPasswd());
        // 查询出用户
        User getUser = securityMapper.getUserByLoginnameAndPasswd(user.getLoginname(), user.getPasswd());
        HashMap<String, Object> msg = new HashMap<>();
        if (getUser != null) {
            try {
                subject.login(token);
                Session session = subject.getSession();
                session.setAttribute("subject", subject);
                jedis.set(getUser.getName(), JSON.toJSONString(getUser));
                Cookie cookie = new Cookie("name", getUser.getName());
                cookie.setMaxAge(30 * 60);
//                resp.addCookie(cookie);
                msg.put("suc", true);
                msg.put("msg", "登录成功");
                msg.put("user", user);
            } catch (AuthenticationException e) {
                msg.put("msg", "验证失败");
                msg.put("suc", false);
            }
        }
        else {
            msg.put("msg", "账号或者密码不正确");
            msg.put("suc", false);
        }
        return msg;
    }

    @GetMapping
    public Object getUserByToken(String token) {
        Map<String, Object> message = new HashMap<>();
        Jedis jedis = jedisPool.getResource();
        String szJson = jedis.get(token);
        if (!StringUtils.isEmpty(szJson)) {
            User user = (User) JSONUtils.parse(szJson);
            jedis.expire(token, 3000);
        }
        return message;
    }

    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public Object getMenus() {
        return menuService.findAll();
    }

    /**
     * 获取服务器系统的信息
     */
    @CrossOrigin
    @GetMapping(value = "/getSystemInfo", produces = {"application/json;charset=UTF-8"})
    public Object getSystemInfo(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        Map<String, Object> message = new HashMap<>();
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        Map<String, String> map = System.getenv();
        // JVM可以使用的总内存
        message.put("totalMemory", r.totalMemory() / 1024 / 1024);
        // JVM可以使用的剩余内存
        message.put("freeMemory", r.freeMemory() / 1024 / 1024);
        // JVM可以使用的处理器个数
        message.put("cpus", r.availableProcessors());
        // 操作系统的名称
        message.put("system", props.getProperty("os.name"));
        //java版本号
        message.put("jdkversion", System.getProperty("java.version"));
        // 得到Tomcat信息
        message.put("info", context.getServerInfo());
        System.out.println(message);
        return message;
    }
}

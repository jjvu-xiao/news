package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.User;
import com.pgl.xiao.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.pgl.xiao.core.Constants.USER_URL;

@RestController
@RequestMapping(value = USER_URL)
public class UserController implements BasicController<User> {

    @Resource
    private UserService service;

    @Override
    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = Objects.isNull(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = Objects.isNull(params.get("rows")) ? 10 : Integer.valueOf(params.get("rows"));
        PageInfo<User> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    @CrossOrigin
    @PostMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object update(@RequestBody User user) {
        Map<String, Object> messages = new HashMap<>();
        service.update(user);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @CrossOrigin
    @Override
    @GetMapping(value = "/find", produces = {"application/json;charset=UTF-8"})
    public Object find(Integer id) {
        User data = service.findById(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("datas", data);
        messages.put("suc", true);
        messages.put("msg", "查询成功");
        return messages;
    }

    @CrossOrigin
    @Override
    @DeleteMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public Object delete(@RequestBody Integer id) {
        service.delete(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("suc", true);
        messages.put("msg", "已删除");
        return messages;
    }

    @CrossOrigin
    @Override
    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object save(@RequestBody Map<String, String> params) {
        User user = new User();
        String name = params.get("username"),
                description = params.get("description"),
                loginname = params.get("loginname"),
                passwd = params.get("passwd"),
                status = params.get("status");
        user.setName(name);
        user.setDescription(description);
        user.setLoginname(loginname);
        user.setPasswd(passwd);
        user.setStatus(Integer.valueOf(status));
        service.save(user);
        Map<String, Object> message = new HashMap<>();
        message.put("suc", true);
        message.put("message", "已保存");
        return message;
    }
}

package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Role;
import com.pgl.xiao.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/role")
public class RoleController implements BasicController<Role> {

    @Resource
    private RoleService service;

    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = new Integer(params.get("page").toString());
        int rows = new Integer(params.get("rows").toString());
        PageInfo<Role> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    @PutMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object update(Role role) {
        Map<String, Object> messages = new HashMap<>();
        service.update(role);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @GetMapping(value = "/find", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object find(Integer id) {
        Role data = service.findById(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("datas", data);
        messages.put("suc", true);
        return data;
    }

    @DeleteMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object delete(@RequestBody Integer id) {
        service.delete(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("datas", "删除成功");
        messages.put("suc", true);
        return messages;
    }

    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public Object save(Role role) {
        Map<String, Object> messages = new HashMap<>();
        service.save(role);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @Override
    public Object save(Map<String, String> params) {
        return null;
    }

}

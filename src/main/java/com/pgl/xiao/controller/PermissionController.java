package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Permission;
import com.pgl.xiao.service.PermissionService;
import com.pgl.xiao.core.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = Constants.PERMISSION_URL)
public class PermissionController implements BasicController<Permission> {

    @Resource
    private PermissionService service;

    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = Objects.isNull(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = Objects.isNull(params.get("rows")) ? 10 : Integer.valueOf(params.get("rows"));
        PageInfo<Permission> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    @PutMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object update(@RequestBody Permission permission) {
        Map<String, Object> messages = new HashMap<>();
        service.update(permission);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @GetMapping(value = "/find", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object find(@RequestParam Integer id) {
        Permission data = service.findById(id);
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
    public Object save(@RequestBody Permission permission) {
        Map<String, Object> messages = new HashMap<>();
        service.save(permission);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @Override
    public Object save(Map<String, String> params) {
        return null;
    }
}

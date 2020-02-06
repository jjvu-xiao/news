package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Menu;
import com.pgl.xiao.service.MenuService;
import com.pgl.xiao.utils.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin()
@RequestMapping(Constants.MENU_URL)
public class MenuController {

    @Resource
    private MenuService service;

    // 服务端载入菜单
    @GetMapping(value = "/load", produces = {"application/json;charset=UTF-8"})
    public Object loadMenu() {
        return service.findAll();
    }

    /**
     * 查询菜单栏功能列表
     *
     * @param params 请求参数
     * @return 数据的总数，与数据本身
     */
    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = Objects.isNull(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = Objects.isNull(params.get("rows")) ? 10 : Integer.valueOf(params.get("rows"));
        PageInfo<Menu> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }
}

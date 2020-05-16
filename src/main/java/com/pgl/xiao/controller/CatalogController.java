package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Catalog;
import com.pgl.xiao.service.CatalogService;
import com.pgl.xiao.core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台管理----栏目管理
 */
@RestController
@RequestMapping(value = Constants.CATALOG_URL)
public class CatalogController implements BasicController<Catalog> {

    @Resource
    private CatalogService service;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 列表查询栏目信息
     * @param params    page 当前页数   rows 每页的最大记录数
     * @return
     */
    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object findByPage(@RequestParam Map<String, String> params) {
        logger.info("查询");
        int page = StringUtils.isNumeric(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = StringUtils.isNumeric(params.get("rows")) ? 10 : Integer.valueOf(params.get("page"));
        PageInfo<Catalog> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    /**
     * 保存新增的栏目信息
     */
    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin
    public Object save(@RequestBody Catalog t) {
        Map<String, Object> messages = new HashMap<>();
        service.save(t);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    /**
     * 更新栏目的信息
     * @param t     栏目更新后的信息
     * @return  提示信息
     */
    @PutMapping(value = "/update",  produces = {"application/json;charset=UTF-8"})
    @CrossOrigin
    @Override
    public Object update(@RequestBody Catalog t) {
        Map<String, Object> messages = new HashMap<>();
        service.update(t);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    /**
     * 根据ID查询栏目信息
     * @param id    栏目的ID
     * @return  提示信息
     */
    @GetMapping(value = "/find",  produces = {"application/json;charset=UTF-8"})
    @Override
    @CrossOrigin
    public Object find(Integer id) {
        Catalog data = service.findById(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("datas", data);
        messages.put("suc", true);
        return data;
    }

    /**
     * 删除相关的栏目
     * @param id    栏目的ID
     * @return  删除提示信息
     */
    @CrossOrigin
    @DeleteMapping(value = "/delete",  produces = {"application/json;charset=UTF-8"})
    @Override
    public Object delete(@RequestBody  Integer id) {
        service.delete(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("suc", true);
        messages.put("msg", "已删除");
        return messages;
    }

    @Override
    public Object save(Map<String, String> params) {
        return null;
    }
}

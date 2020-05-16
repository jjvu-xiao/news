package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Comment;
import com.pgl.xiao.service.CommentService;
import com.pgl.xiao.core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = Constants.COMMENT_URL)
public class CommentController implements BasicController<Comment> {

    @Resource
    private CommentService service;

    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = StringUtils.isNumeric(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = StringUtils.isNumeric(params.get("rows")) ? 10 : Integer.valueOf(params.get("page"));
        PageInfo<Comment> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    @CrossOrigin
    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object save(@RequestBody Map<String, String> params) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Comment comment = new Comment();
        comment.setPraise(Integer.valueOf((params.get("praise").toString())));
        comment.setStatus(Integer.valueOf(params.get("status").toString()));
        comment.setAddate(LocalDateTime.parse(params.get("addate").toString(), df));
        comment.setContent(params.get("content").toString());
        service.save(comment);
        Map<String, Object> messages = new HashMap<>();
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @CrossOrigin
    @PutMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object update(@RequestBody Comment t) {
        Map<String, Object> messages = new HashMap<>();
        service.update(t);
        messages.put("suc", true);
        messages.put("msg", "更新成功");
        return messages;
    }

    @CrossOrigin
    @GetMapping(value = "/find", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object find(Integer id) {
        Comment data = service.findById(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("datas", data);
        messages.put("suc", true);
        messages.put("msg", "查询成功");
        return messages;
    }

    @CrossOrigin
    @DeleteMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object delete(@RequestBody Integer id) {
        service.delete(id);
        Map<String, Object> messages = new HashMap<>();
        messages.put("suc", true);
        messages.put("msg", "已删除");
        return messages;
    }

}



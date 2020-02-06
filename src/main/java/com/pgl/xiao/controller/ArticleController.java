package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Article;
import com.pgl.xiao.service.ArticleService;
import com.pgl.xiao.utils.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = Constants.ARTICLE_URL)
public class ArticleController implements BasicController<Article> {

    //最大上传文件大小
    @Value("${upload.maxSize}")
    private long maxSize;

    @Resource
    private ArticleService service;

    @Value("${upload.path}")
    private String uploadPath;

    @CrossOrigin
    @GetMapping(value = "/list", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object findByPage(@RequestParam Map<String, String> params) {
        int page = Objects.isNull(params.get("page")) ? 10 : Integer.valueOf(params.get("page"));
        int rows = Objects.isNull(params.get("rows")) ? 10 : Integer.valueOf(params.get("rows"));
        PageInfo<Article> datas = service.findByParams(params, page, rows);
        Map<String, Object> messages = new HashMap<>();
        messages.put("total", datas.getTotal());
        messages.put("rows", datas.getList());
        return messages;
    }

    @Override
    @CrossOrigin
    @PutMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    public Object update(@RequestBody  Article t) {
        Map<String, Object> messages = new HashMap<>();
        service.update(t);
        messages.put("suc", true);
        messages.put("msg", "添加成功");
        return messages;
    }

    @CrossOrigin
    @GetMapping(value = "/find", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object find(Integer id) {
        Article data = service.findById(id);
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


    @CrossOrigin
    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    @Override
    public Object save(@RequestBody Map<String, String> params) {
        Map<String, Object> messages = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Article article = new Article();
        boolean suc = true;
        String msg = "发布成功！";
        String content = params.get("content").trim();
        String releasedate = params.get("releasedate").trim();
        String lastupdate = params.get("lastupdate");
        String title = params.get("title");
        String clicks = params.get("clicks");
        String praise = params.get("praise");
        String author = params.get("author");
        String status =  params.get("status");
        String type =  params.get("type");

        article.setContent(content);
        article.setReleasedate(StringUtils.isBlank(releasedate) ?
                LocalDateTime.now() : LocalDateTime.parse(releasedate, formatter));

        article.setLastupdate(StringUtils.isBlank(lastupdate) ?
                LocalDateTime.now() : LocalDateTime.parse(lastupdate, formatter));

        article.setTitle(StringUtils.isBlank(title) ? "" : title);

        article.setClicks(StringUtils.isNumeric(clicks) ? Long.valueOf(clicks) : 0);

        article.setPraise(StringUtils.isNumeric(praise) ? Long.valueOf(praise) : 0);

        article.setAuthor(StringUtils.isEmpty(author) ? "Mr Xiao" : author);

        article.setStatus(StringUtils.isEmpty(status) ? "正常" : status);

        article.setType(StringUtils.isNumeric(type) ? 1 : Integer.valueOf(type));

        suc = service.save(article);

        messages.put("suc", suc);
        messages.put("msg", msg);
        return messages;
    }
}

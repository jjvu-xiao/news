package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.ArticleMapper;
import com.pgl.xiao.domain.Article;
import com.pgl.xiao.domain.ArticleExample;
import com.pgl.xiao.service.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper mapper;

    // 查询具体的一篇文章
    @Override
    public Article findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    // 保存文章
    @Override
    public boolean save(Article type) {
        mapper.insertSelective(type);
        return true;
    }

    // 更新文章
    @Override
    public boolean update(Article type) {
        mapper.updateByPrimaryKey(type);
        return true;
    }

    // 删除文章
    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    // 根据查询条件查询文章
    @Override
    public PageInfo<Article> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        String startRelease = params.get("start"), endRelease = params.get("end"),
                startUpdate = params.get("startupdate"), endUpdate = params.get("endupdate");
        LocalDateTime temp;
        String title = params.get("title"), status = params.get("status"),
                author = params.get("author"), type = params.get("type");

        if (!StringUtils.isBlank(title))
            criteria.andTitleLike("%" + title + "%");

        if (!StringUtils.isBlank(author))
            criteria.andTitleLike("%" + title + "%");

        if (!StringUtils.isBlank(status))
            criteria.andStatusEqualTo(status);

        if (!StringUtils.isBlank(startRelease)) {
            temp = LocalDateTime.parse(startRelease, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            criteria.andReleasedateGreaterThanOrEqualTo(temp);
        }

        if (!StringUtils.isBlank(endRelease)) {
            temp = LocalDateTime.parse(endRelease, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            criteria.andReleasedateLessThanOrEqualTo(temp);
        }

        if (!StringUtils.isBlank(startUpdate)) {
            temp = LocalDateTime.parse(startUpdate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            criteria.andLastupdateGreaterThanOrEqualTo(temp);
        }
        if (!StringUtils.isBlank(endUpdate)) {
            temp = LocalDateTime.parse(endUpdate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            criteria.andLastupdateLessThanOrEqualTo(temp);
        }
        if (!StringUtils.isBlank((type)))
            criteria.andTypeEqualTo(Integer.valueOf(type));

        PageHelper.startPage(indexPage, maxRows);
        List<Article> datas = mapper.selectByExample(example);
        return new PageInfo<>(datas);
    }

    // 查询所有的文章
    @Override
    public List<Article> findAll() {
        return mapper.selectByExample(null);
    }

    // 查询评价最好的文章
    @Override
    public PageInfo<Article> showGreateArticle() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("praise desc");
        ArticleExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(1, 3);
        List<Article> data = mapper.selectByExample(example);
        PageInfo<Article> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    // 查询点击率最高的文章
    @Override
    public PageInfo<Article> showClicksArticle() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("clicks desc");
        ArticleExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(1, 3);
        List<Article> data = mapper.selectByExample(example);
        PageInfo<Article> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    // 查询最新的文章
    @Override
    public PageInfo<Article> showNewArticle() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("lastupdate desc");
        ArticleExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(1, 8);
        List<Article> data = mapper.selectByExample(example);
        PageInfo<Article> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    // 查询最新的文章
    @Override
    public PageInfo<Article> showAboutArticle() {
        ArticleExample example = new ArticleExample();
        example.setOrderByClause("lastupdate asc");
        ArticleExample.Criteria criteria = example.createCriteria();
        PageHelper.startPage(1, 6);
        List<Article> data = mapper.selectByExample(example);
        PageInfo<Article> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

}

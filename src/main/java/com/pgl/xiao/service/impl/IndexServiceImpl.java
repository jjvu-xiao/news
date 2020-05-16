package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.ArticleMapper;
import com.pgl.xiao.dao.CatalogMapper;
import com.pgl.xiao.dao.MeMapper;
import com.pgl.xiao.domain.*;
import com.pgl.xiao.service.IndexService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("indexService")
public class IndexServiceImpl implements IndexService {

    @Resource
    private CatalogMapper catalogMapper;

    @Resource
    private ArticleMapper articleMapper;

    // 首页的文章列表数量
    @Value("${settings.indexArticleSize}")
    private int articleMaxSize;

    @Resource
    private MeMapper meMapper;

    /**
     * 显示主页的数据内容
     * @return  所有的栏目，按照更新日期发布的文章从大到小 12篇，按照点赞数，点击率，发布时间排序的文章
     */
    @Override
    public Map<String, Object> indexData() {
        List<Catalog> catalogs = catalogMapper.selectByExample(null);
        PageHelper.startPage(1, articleMaxSize);
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        criteria.andStatusNotEqualTo("注销");
        example.setOrderByClause("lastupdate DESC");
        List<Article> articles = articleMapper.selectByExample(example);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        Map<String, Object> datas = new HashMap<>();
        datas.put("catalogs", catalogs);
        datas.put("articles", pageInfo.getList());
        return datas;
    }


    @Override
    public Map<String, Object> about() {
        Map<String, Object> datas = new HashMap<>();
        List<Catalog> catalogs = catalogMapper.selectByExample(null);
        Me me = meMapper.selectByPrimaryKey(1);
        datas.put("me", me);
        datas.put("catalogs", catalogs);
        return datas;
    }

    @Override
    public List<Catalog> loadCatalogs() {
        return catalogMapper.selectByExample(null);
    }


    @Override
    public PageInfo<Article> showArticleByCatalog(Map<String, Object> params, Integer indexPage, Integer maxRows) {
        Map<String, Object> datas = new HashMap<>();
        ArticleExample example = new ArticleExample();
        ArticleExample.Criteria criteria = example.createCriteria();
        if (params != null && !params.isEmpty()) {
            if (params.get("type") != null) {
                int type = Integer.parseInt(params.get("type").toString());
                criteria.andTypeEqualTo(type);
            }
            if (params.get("title") != null) {
                String title = (String) params.get("title");
                criteria.andTitleLike("%" + title + "%");
            }
            if (params.get("keywork") != null) {
                String keyword = (String) params.get("keyword");
                criteria.andContentLike("%" + keyword + "%");
            }
//            if (params.get("page") != null) {
//                page = new Integer(params.get("page").toString());
//                page += 1;
//            }
//            if (params.get("rows") != null) {
//                rows = new Integer(params.get("rows").toString());
//            }
        }
        example.setOrderByClause("lastupdate desc");
        PageHelper.startPage(indexPage, maxRows);
        List<Article> articles = articleMapper.selectByExample(example);
        PageInfo<Article> info = new PageInfo<>(articles);
        return info;
    }
}

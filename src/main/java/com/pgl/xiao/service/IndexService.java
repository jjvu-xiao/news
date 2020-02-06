package com.pgl.xiao.service;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Article;
import com.pgl.xiao.domain.Catalog;

import java.util.List;
import java.util.Map;

public interface IndexService {

    Map<String, Object> indexData();

    Map<String, Object> about();

    List<Catalog> loadCatalogs();

    PageInfo<Article> showArticleByCatalog(Map<String, Object> params, Integer indexPage, Integer maxRows);

}

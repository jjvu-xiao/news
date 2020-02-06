package com.pgl.xiao.service;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Article;

import java.util.Map;

public interface ArticleService extends BasicService<Article> {

    /**
     * 按照文章的点赞数排序，选出最受好评的6篇文章
     * @return
     */
    PageInfo<Article> showGreateArticle();


    /**
     * 按照文章的点击数排序，选出最受好评的6篇文章
     * @return
     */
    PageInfo<Article> showClicksArticle();


    /**
     * 按照文章的更新时间进行排序，选出最新的6篇文章
     * @return
     */
    PageInfo<Article> showNewArticle();


    /**
     * 在关于我的介绍中，推荐最早的几篇文章
     * @return
     */
    PageInfo<Article> showAboutArticle();

}

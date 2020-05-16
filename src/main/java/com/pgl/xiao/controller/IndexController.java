package com.pgl.xiao.controller;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Article;
import com.pgl.xiao.domain.Catalog;
import com.pgl.xiao.domain.State;
import com.pgl.xiao.service.ArticleService;
import com.pgl.xiao.service.CatalogService;
import com.pgl.xiao.service.IndexService;
import com.pgl.xiao.service.StateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin()
public class IndexController {

    @Resource
    private IndexService indexService;

    @Resource
    private StateService stateService;

    @Resource
    private ArticleService articleService;

    @Resource
    private CatalogService catalogService;

    @CrossOrigin
    @RequestMapping(value = "/getState", produces = {"application/json;charset=UTF-8"})
    public Object getState() {
        State state = new State();
        state.setId(-1);
        state.setText("全部");
        List<State> states = stateService.findAll();
        states.add(0, state);
        return states;
    }

    /**
     * 关于我的界面
     *
     * @return
     */
    @GetMapping(value = "/about", produces = {"application/json;charset=UTF-8"})
    public Object about() {
        Map<String, Object> message;
        message = indexService.about();
        List<Article> articles = articleService.showAboutArticle().getList();
        message.put("articles", articles);
        return message;
    }

    /**
     * 载入客户端的菜单栏
     *
     * @return
     */
    @GetMapping(value = "/loadCatalogs", produces = {"application/json;charset=UTF-8"})
    public Object loadCatalogs(HttpServletRequest request) {
        return indexService.loadCatalogs();
    }

    /**
     * 载入主页的数据
     * 载入最新，点赞数最高的，点击率最高的分别6篇文章
     *
     * @return
     */
    @GetMapping(value = "/index", produces = {"application/json;charset=UTF-8"})
    public Object index() {
        Map<String, Object> message = new HashMap<>();
        List<Article> greateArticles = articleService.showGreateArticle().getList();
        List<Article> clicksArticles = articleService.showClicksArticle().getList();
        List<Article> newArticles = articleService.showNewArticle().getList();
        message.put("greateArticle", greateArticles);
        message.put("clicksArticles", clicksArticles);
        message.put("newArticles", newArticles);
        return message;
    }

    /**
     * 浏览文章的内容
     *
     * @param id 文章的id
     * @return 文章的详细内容
     */
    @GetMapping(value = "/article", produces = {"application/json;charset=UTF-8"})
    public Object article(@RequestParam Integer id) {
        return articleService.findById(id);
    }

    /**
     * 在种类栏目中分页浏览文章
     *
     * @param params
     * @return
     */
    @GetMapping(value = "/info", produces = {"application/json;charset=UTF-8"})
    public Object showArticleByCatalog(@RequestParam Map<String, Object> params) {
        Map<String, Object> data = new HashMap<>();
        int page = 1, rows = 6;
        if (params.get("page") != null) {
            page = new Integer(params.get("page").toString());
            page += 1;
        }
        if (params.get("rows") != null) {
            rows = new Integer(params.get("rows").toString());
        }
        if (params.get("type") != null) {
            int cata = Integer.parseInt(params.get("type").toString());
            Catalog catalog = catalogService.findById(cata);
            data.put("nowCatalog", catalog);
        }
        List<Article> greateArticles = articleService.showGreateArticle().getList();
        List<Article> clicksArticles = articleService.showClicksArticle().getList();
        PageInfo<Article> info = indexService.showArticleByCatalog(params, page, rows);
        List<Article> articles = info.getList();

        data.put("greateArticles", greateArticles);
        data.put("clicksArticles", clicksArticles);
        data.put("total", info.getTotal());
        data.put("articles", articles);
        return data;
    }
}

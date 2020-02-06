package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.ArticleMapper;
import com.pgl.xiao.dao.CommentMapper;
import com.pgl.xiao.dao.CustomerMapper;
import com.pgl.xiao.domain.*;
import com.pgl.xiao.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper mapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Override
    public Comment findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Comment type) {
        mapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(Comment type) {
        mapper.updateByPrimaryKeySelective(type);
        return true;
    }


    @Override
    public boolean delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<Comment> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        CommentExample example = new CommentExample();
        CommentExample.Criteria criteria = example.createCriteria();
        Integer praisemin;
        Integer praisemax;
        LocalDateTime start;
        LocalDateTime end;
        String articlename;
        String user, status;
        if (!StringUtils.isEmpty((CharSequence) params.get("articlename"))) {
            articlename = params.get("articlename");
            ArticleExample example1 = new ArticleExample();
            ArticleExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andTitleLike("%" + articlename + "%");
            List<Article> articles = articleMapper.selectByExample(example1);
            if (!articles.isEmpty()) {
                List<Integer> articleIds = new LinkedList<>();
                for (Article temp: articles) {
                    articleIds.add(temp.getId());
                }
                criteria.andArticleidIn(articleIds);
            }
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("user"))) {
            user = params.get("user");
            CustomerExample example1 = new CustomerExample();
            CustomerExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andUsernameLike("%" + user + "%");
            List<Customer> customers = customerMapper.selectByExample(example1);
            if (!customers.isEmpty()) {
                List<Integer> userIds = new LinkedList<>();
                for (Customer temp : customers) {
                    userIds.add(temp.getId());
                }
                criteria.andUseridIn(userIds);
            }
        }
        if (!StringUtils.isBlank(params.get("status")) && !params.get("status").equals("0") ) {
            criteria.andStatusEqualTo(Integer.valueOf(params.get("status")));
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("praisemin"))) {
            praisemin = Integer.valueOf(params.get("praisemin"));
            criteria.andPraiseGreaterThanOrEqualTo(praisemin);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("priasemax"))) {
            praisemax = Integer.valueOf(params.get("priasemax"));
            criteria.andPraiseLessThanOrEqualTo(praisemax);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("start"))) {
            start = LocalDateTime.parse(params.get("start"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            criteria.andAddateGreaterThanOrEqualTo(start);
        }
        if (!StringUtils.isEmpty((CharSequence) params.get("end"))) {
            end = LocalDateTime.parse(params.get("end"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            criteria.andAddateLessThanOrEqualTo(end);
        }
        PageHelper.startPage(indexPage, maxRows);
        List<Comment> datas = mapper.selectByExample(example);
//        List<Comment> commentList = setUserAndArticle(datas);
        return new PageInfo<>(datas);
    }

    /**
     * 关联查询评论所属的文章，及发布者信息
     * @param comments  查询到的评论数据
     * @return  已完成管理的评论数据
     */
    private List<Comment> setUserAndArticle(List<Comment> comments) {
        List<Comment> linkedList = new LinkedList<>();
        for (Comment comment : comments) {
            Article article = articleMapper.selectByPrimaryKey(comment.getArticleid());
            comment.setArticle(article);
            Customer customer = customerMapper.selectByPrimaryKey(comment.getUserid());
            comment.setCustomer(customer);
            linkedList.add(comment);
        }
        return linkedList;
    }

    @Override
    public List<Comment> findAll() {
        return mapper.selectByExample(null);
    }
}

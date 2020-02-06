package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.CatalogMapper;
import com.pgl.xiao.domain.Catalog;
import com.pgl.xiao.domain.CatalogExample;
import com.pgl.xiao.service.CatalogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

    @Resource
    private CatalogMapper catalogMapper;

    @Override
    public Catalog findById(Integer id) {
       return catalogMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Catalog type) {
        catalogMapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(Catalog type) {
        catalogMapper.updateByPrimaryKeySelective(type);
        return true;
    }


    @Override
    public boolean delete(Integer id) {
        catalogMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<Catalog> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows) {
        CatalogExample example = new CatalogExample();
        CatalogExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty((CharSequence) params.get("name")))
            criteria.andNameLike("%" + params.get("name") + "%");
        if (!StringUtils.isEmpty((CharSequence) params.get("status"))) {
            Integer status = new Integer(params.get("status").toString());
            if (status != 0)
                criteria.andStatusEqualTo(status);
        }
        PageHelper.startPage(indexPage, maxRows);
        List<Catalog> datas = catalogMapper.selectByExample(example);
        PageInfo<Catalog> info = new PageInfo<>(datas);
        return info;
    }

    @Override
    public List<Catalog> findAll() {
        CatalogExample example = new CatalogExample();
        return catalogMapper.selectByExample(example);
    }
}

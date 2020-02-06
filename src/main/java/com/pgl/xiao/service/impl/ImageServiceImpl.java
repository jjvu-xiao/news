package com.pgl.xiao.service.impl;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.dao.ImageMapper;
import com.pgl.xiao.domain.Image;
import com.pgl.xiao.domain.ImageExample;
import com.pgl.xiao.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageMapper mapper;


    public Image findById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean save(Image type) {
        mapper.insertSelective(type);
        return true;
    }

    @Override
    public boolean update(Image type) {
        mapper.updateByPrimaryKeySelective(type);
        return true;
    }

    @Override
    public boolean delete(Long id) {
        mapper.deleteByPrimaryKey(id);
        return true;
    }

    @Override
    public PageInfo<Image> findByParams(Map<String, Object> params, Integer indexPage, Integer maxRows) {
        return null;
    }

    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public Image findByPath(String path) {
        ImageExample example = new ImageExample();
        ImageExample.Criteria criteria = example.createCriteria();
        criteria.andPathEqualTo(path);
        List<Image> images = mapper.selectByExample(example);
        return images.get(0);
    }
}

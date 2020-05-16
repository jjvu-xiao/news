package com.pgl.xiao.service;

import com.github.pagehelper.PageInfo;
import com.pgl.xiao.domain.Image;

import java.util.List;
import java.util.Map;

public interface ImageService {

    boolean save(Image type);

    boolean update(Image type);

    boolean delete(Long id);

    PageInfo<Image> findByParams(Map<String, Object> params, Integer indexPage, Integer maxRows);

    List<Image> findAll();

    Image findById(Long id);

    Image findByPath(String path);
}

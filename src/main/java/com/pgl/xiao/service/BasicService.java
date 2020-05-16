package com.pgl.xiao.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 基础服务接口
 * @param <T>
 */
public interface BasicService<T> {

    T findById(Integer id);

    boolean save(T type);

    boolean update(T type);

    boolean delete(Integer id);

    PageInfo<T> findByParams(Map<String, String> params, Integer indexPage, Integer maxRows);

    List<T> findAll();
}

package com.pgl.xiao.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

/**
 * 后台管理的接口
 * @param <T>   所属pojo
 */
@CrossOrigin()
public interface BasicController<T> {

    Object findByPage(Map<String, String> params);

    Object update(T t);

    Object find(Integer id);

    Object delete(Integer id);

    Object save(Map<String, String> params);
}
